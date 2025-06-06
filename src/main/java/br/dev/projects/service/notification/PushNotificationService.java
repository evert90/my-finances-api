package br.dev.projects.service.notification;

import br.dev.projects.bean.notification.PushNotificationMessage;
import br.dev.projects.bean.notification.PushSubscription;
import br.dev.projects.entity.AssetEntity;
import br.dev.projects.entity.FinancialRecordEntity;
import br.dev.projects.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.format.DateTimeFormatter;

import static java.util.Optional.of;

@Service
public class PushNotificationService {

    @Value("${vapid.public-key}")
    private String publicKey;

    @Value("${vapid.private-key}")
    private String privateKey;

    @Value("${vapid.subject}")
    private String subject;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PushSubscriptionService pushSubscriptionService;

    public PushNotificationService() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public void sendPushNotificationTest() throws Exception {
        var subscriptions = of(pushSubscriptionService.findByCurrentUser())
                .filter(it -> !it.isEmpty())
                .orElseThrow(() -> new NotFoundException("Notificação não habilitada"))
                .stream()
                .map(it -> pushSubscriptionService.getPushSubscription(it))
                .toList();

        for (PushSubscription subscription : subscriptions) {
            Notification notification = new Notification(
                    subscription.endpoint(),
                    subscription.keys().p256dh(),
                    subscription.keys().auth(),
                    objectMapper.writeValueAsString(PushNotificationMessage
                            .builder()
                            .title("Notificação de teste")
                            .message("Recebida com sucesso")
                            .build()
                    )
            );

            PushService pushService = new PushService()
                    .setPublicKey(Utils.loadPublicKey(publicKey))
                    .setPrivateKey(Utils.loadPrivateKey(privateKey))
                    .setSubject(subject);

            pushService.send(notification);
        }

    }

    public void sendPushNotification(PushSubscription subscription, PushNotificationMessage message) throws Exception {

        Notification notification = new Notification(
                subscription.endpoint(),
                subscription.keys().p256dh(),
                subscription.keys().auth(),
                objectMapper.writeValueAsString(message)
        );

        PushService pushService = new PushService()
                .setPublicKey(Utils.loadPublicKey(publicKey))
                .setPrivateKey(Utils.loadPrivateKey(privateKey))
                .setSubject(subject);

        pushService.send(notification);
    }

    public PushNotificationMessage getMessage(AssetEntity asset) {
        return PushNotificationMessage
                .builder()
                .title("Aviso de vencimento")
                .message("Seu investimento do banco %s irá vencer em %s"
                        .formatted(asset.getBank(), asset.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .build();
    }

    public PushNotificationMessage getMessage(FinancialRecordEntity financialRecord) {
        return PushNotificationMessage
                .builder()
                .title("Aviso de vencimento")
                .message("%s irá vencer em %s no valor de R$ %s".formatted(
                        financialRecord.getName(),
                        financialRecord.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        new java.text.DecimalFormat("#,###,##0.00").format(financialRecord.getValue())
                ))
                .build();
    }
}
