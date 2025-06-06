package br.dev.projects.controller;

import br.dev.projects.service.notification.PushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/push-notification")
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService service;

    @PostMapping
    public void notificationTest() throws Exception {
        service.sendPushNotificationTest();
    }
}
