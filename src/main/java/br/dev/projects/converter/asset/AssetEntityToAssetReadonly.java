package br.dev.projects.converter.asset;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.bean.asset.AssetReadonly;
import br.dev.projects.entity.AssetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class AssetEntityToAssetReadonly implements Function<AssetEntity, AssetReadonly> {
    @Override
    public AssetReadonly apply(AssetEntity entity) {
        return AssetReadonly
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .details(entity.getDetails())
                .initialValue(entity.getInitialValue())
                .endValue(entity.getEndValue())
                .initialDate(entity.getInitialDate())
                .endDate(entity.getEndDate())
                .redemptionDate(entity.getRedemptionDate())
                .type(entity.getType())
                .rendaFixaType(entity.getRendaFixaType())
                .rendaFixaRateType(entity.getRendaFixaRateType())
                .bank(entity.getBank())
                .rate(entity.getRate())
                .liquidez(entity.getLiquidez())
                .tags(ofNullable(entity.getTags())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .map(tag -> new Tag(tag.getId(), tag.getName()))
                        .collect(toSet()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
