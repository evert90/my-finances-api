package br.dev.projects.schedule;

import br.dev.projects.entity.AssetEntity;
import br.dev.projects.entity.UserEntity;
import br.dev.projects.repository.AssetRepository;
import br.dev.projects.service.notification.PushNotificationService;
import br.dev.projects.service.notification.PushSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static br.dev.projects.bean.asset.AssetType.RENDA_FIXA;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobAssetNotification {

    private final AssetRepository assetRepository;

    private final PushNotificationService pushNotificationService;

    private final PushSubscriptionService pushSubscriptionService;

    @Scheduled(cron = "0 0 11 * * ?") // 11h
    public void run() {
        log.info("Starting asset notification job");

        pushSubscriptionService
                .getAll()
                .forEach(entity -> {
                    getAssets(entity.getUser()).forEach(asset -> {
                        try {
                            log.info("Sending push notification to {}", entity.getUser().getEmail());
                            pushNotificationService.sendPushNotification(
                                    pushSubscriptionService.getPushSubscription(entity),
                                    pushNotificationService.getMessage(asset)
                            );
                        } catch (Exception e) {
                            throw new RuntimeException("Error sending push notification to " + entity.getUser().getEmail(), e);
                        }
                    });
                });

        log.info("Finished asset notification job");
    }

    private List<AssetEntity> getAssets(UserEntity user) {
        return assetRepository.findByUserAndTypeAndEndDateBetweenAndEndValueIsNull(user, RENDA_FIXA, LocalDate.now(), LocalDate.now().plusDays(1));
    }
}
