package br.dev.projects.controller;

import br.dev.projects.bean.user.UserReadonly;
import br.dev.projects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    UserReadonly getUser() {
        return userService.getUser();
    }

}
