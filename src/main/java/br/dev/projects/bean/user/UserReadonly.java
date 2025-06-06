package br.dev.projects.bean.user;

import lombok.Builder;

import java.util.Set;

public record UserReadonly(Long id, String name, String email, Set<Role> roles) {
    @Builder
    public UserReadonly {}
}