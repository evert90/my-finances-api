package br.dev.projects.repository;

import br.dev.projects.bean.asset.AssetType;
import br.dev.projects.entity.AssetEntity;
import br.dev.projects.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {
    Optional<AssetEntity> findByUserAndId(UserEntity user, Long id);

    List<AssetEntity> findByUserOrderByEndDateAsc(UserEntity user);

    List<AssetEntity> findByUserAndTypeAndEndDateBetweenAndEndValueIsNull(UserEntity user, AssetType type, LocalDate start, LocalDate end);
}
