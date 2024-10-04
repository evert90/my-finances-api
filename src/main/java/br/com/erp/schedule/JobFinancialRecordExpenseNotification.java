package br.com.erp.schedule;

import br.com.erp.entity.FinancialRecordEntity;
import br.com.erp.entity.UserEntity;
import br.com.erp.repository.FinancialRecordRepository;
import br.com.erp.service.notification.PushNotificationService;
import br.com.erp.service.notification.PushSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static br.com.erp.bean.financialrecord.FinancialRecordType.EXPENSE;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobFinancialRecordExpenseNotification {

    private final FinancialRecordRepository financialRecordRepository;

    private final PushNotificationService pushNotificationService;

    private final PushSubscriptionService pushSubscriptionService;

    @Scheduled(cron = "0 58 11 * * ?") // 11h
    public void run() {
        log.info("Starting expense notification job");

        pushSubscriptionService
                .getAll()
                .forEach(entity -> getFinancialRecords(entity.getUser()).forEach(financialRecord -> {
                    try {
                        log.info("Sending expense push notification to {}", entity.getUser().getEmail());
                        pushNotificationService.sendPushNotification(
                                pushSubscriptionService.getPushSubscription(entity),
                                pushNotificationService.getMessage(financialRecord)
                        );
                    } catch (Exception e) {
                        throw new RuntimeException("Error sending expense push notification to " + entity.getUser().getEmail(), e);
                    }
                }));

        log.info("Finished expense notification job");
    }

    private List<FinancialRecordEntity> getFinancialRecords(UserEntity user) {
        return financialRecordRepository.findByUserAndNotificationAndPaidAndTypeAndDateBetweenOrderByDateDesc(
                user, true, false, EXPENSE, LocalDate.now(), LocalDate.now().plusDays(1));
    }
}
