package br.dev.projects.repository;

import br.dev.projects.entity.TagEntity;
import br.dev.projects.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Set<TagEntity> findByUserOrderByNameAsc(UserEntity entity);
    Optional<TagEntity> findByUserAndName(UserEntity entity, String name);
}
