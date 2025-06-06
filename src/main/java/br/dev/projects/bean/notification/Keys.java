package br.dev.projects.bean.notification;

import lombok.Builder;

@Builder(toBuilder = true)
public record Keys(String p256dh, String auth) {
}
