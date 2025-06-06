package br.dev.projects.converter.asset;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.bean.asset.Asset;
import br.dev.projects.entity.AssetEntity;
import br.dev.projects.entity.TagEntity;
import br.dev.projects.entity.UserEntity;
import br.dev.projects.exception.NotFoundException;
import br.dev.projects.repository.AssetRepository;
import br.dev.projects.repository.TagRepository;
import br.dev.projects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Function;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class AssetToAssetEntity implements Function<Asset, AssetEntity> {

    private final TagRepository tagRepository;

    private final AssetRepository assetRepository;            ;

    private final UserService userService;

    @Override
    public AssetEntity apply(Asset asset) {
        var user = userService.getCurrentUser();

        return AssetEntity
                .builder()
                .id(asset.id())
                .name(asset.name())
                .details(asset.details())
                .initialValue(asset.initialValue())
                .endValue(asset.endValue())
                .initialDate(asset.initialDate())
                .endDate(asset.endDate())
                .redemptionDate(asset.redemptionDate())
                .type(asset.type())
                .rendaFixaType(asset.rendaFixaType())
                .rendaFixaRateType(asset.rendaFixaRateType())
                .bank(asset.bank())
                .rate(asset.rate())
                .liquidez(asset.liquidez())
                .tags(ofNullable(asset.tags())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .map(it -> tagRepository.findByUserAndName(user, it.name())
                                        .orElseGet(() -> saveTag(it, user)))
                        .collect(toSet()))
                .user(user)
                .createdAt(getCreatedAt(asset.id(), user))
                .updatedAt(now())
                .build();
    }

    private TagEntity saveTag(Tag tag, UserEntity user) {
        return tagRepository.save(new TagEntity(tag.id(), tag.name(), user));
    }

    private LocalDateTime getCreatedAt(Long id, UserEntity user) {
        return id == null ?
                now() :
                assetRepository.findByUserAndId(user, id)
                        .orElseThrow(() -> new NotFoundException("Bem não encontrado"))
                        .getCreatedAt();
    }
}
