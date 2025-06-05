package br.com.erp.service;

import br.com.erp.bean.user.UserInfo;
import br.com.erp.bean.user.UserReadonly;
import br.com.erp.client.IdpClient;
import br.com.erp.converter.user.UserEntityToUserReadOnly;
import br.com.erp.entity.UserEntity;
import br.com.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Service;

import static br.com.erp.bean.user.Role.ROLE_USER;

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
