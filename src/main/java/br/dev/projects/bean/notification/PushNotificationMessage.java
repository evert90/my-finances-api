package br.dev.projects.bean.notification;

import lombok.Builder;

@Builder(toBuilder = true)
public record PushNotificationMessage(String title, String message) {
}
