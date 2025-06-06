package br.dev.projects.repository;

import br.dev.projects.entity.PushSubscriptionEntity;
import br.dev.projects.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushSubscriptionRepository extends JpaRepository<PushSubscriptionEntity, Long> {
    PushSubscriptionEntity findByEndpointAndAuthKeyAndP256dhKey(String endpoint, String authKey, String p256dhKey);
    List<PushSubscriptionEntity> findByUser(UserEntity user);
}
