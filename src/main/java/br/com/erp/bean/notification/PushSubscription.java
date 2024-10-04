package br.com.erp.bean.notification;

import lombok.Builder;

@Builder(toBuilder = true)
public record PushSubscription(String endpoint, Keys keys) {
}
