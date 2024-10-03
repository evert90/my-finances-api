package br.com.erp.bean;

import lombok.Builder;

@Builder(toBuilder = true)
public record PushNotificationMessage(String title, String message) {
}
