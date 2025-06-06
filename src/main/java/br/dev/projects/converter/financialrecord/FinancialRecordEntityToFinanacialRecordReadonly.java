package br.dev.projects.converter.financialrecord;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.bean.financialrecord.FinancialRecordReadonly;
import br.dev.projects.entity.FinancialRecordEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Service
public class FinancialRecordEntityToFinanacialRecordReadonly implements Function<FinancialRecordEntity, FinancialRecordReadonly> {
    @Override
    public FinancialRecordReadonly apply(FinancialRecordEntity entity) {
        return FinancialRecordReadonly
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .details(entity.getDetails())
                .value(entity.getValue())
                .date(entity.getDate())
                .type(entity.getType())
                .tags(ofNullable(entity.getTags())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .map(tag -> new Tag(tag.getId(), tag.getName()))
                        .collect(toSet()))
                .paid(entity.getPaid())
                .notification(entity.getNotification())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
