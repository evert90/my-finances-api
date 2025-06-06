package br.dev.projects.controller;

import br.dev.projects.bean.notification.PushSubscription;
import br.dev.projects.service.notification.PushSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/push-subscription")
@RequiredArgsConstructor
public class PushSubscriptionController {

    private final PushSubscriptionService service;

    @PostMapping
    public void subscribe(@RequestBody PushSubscription subscription) {
        service.save(subscription);
    }
}
