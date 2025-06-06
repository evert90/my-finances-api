package br.dev.projects.service;

import br.dev.projects.bean.user.UserInfo;
import br.dev.projects.bean.user.UserReadonly;
import br.dev.projects.client.IdpClient;
import br.dev.projects.converter.user.UserEntityToUserReadOnly;
import br.dev.projects.entity.UserEntity;
import br.dev.projects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Service;

import static br.dev.projects.bean.user.Role.ROLE_USER;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    private final UserEntityToUserReadOnly userEntityToUserReadOnly;

    private final IdpClient idpClient;

    public UserReadonly getUser() {
        return userEntityToUserReadOnly.apply(getUserEntity());
    }

    public UserEntity getUserEntity() {
        var userInfo = idpClient.getUserInfo(getToken());

        return repository.findByEmail(userInfo.email())
                .orElseGet(() -> createAndSaveUser(userInfo));
    }

    public UserEntity getCurrentUser() {
        var userInfo = idpClient.getUserInfo(getToken());
        return repository
                .findByEmail(userInfo.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário inválido ou não encontrado"));
    }

    private UserEntity createAndSaveUser(UserInfo userInfo) {
        var user = UserEntity
                .builder()
                .name(userInfo.name())
                .email(userInfo.email())
                .role(ROLE_USER)
                .build();

        return repository.save(user);
    }

    private String getToken() {
        var jwt = (AbstractOAuth2Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getTokenValue();
    }

}
