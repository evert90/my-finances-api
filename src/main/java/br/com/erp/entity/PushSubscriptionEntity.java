package br.com.erp.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.TABLE;

@Entity
@Table(name = "push_subscription")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class PushSubscriptionEntity {

    @Id
    @GeneratedValue(strategy = TABLE, generator = "pushNotificationGenerator")
    @TableGenerator(name = "pushNotificationGenerator", table = "hibernate_sequences")
    private Long id;

    @Column(length = 1000)
    private String endpoint;

    private String authKey;

    private String p256dhKey;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
