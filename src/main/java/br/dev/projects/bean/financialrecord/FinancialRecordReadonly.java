package br.dev.projects.bean.financialrecord;

import br.dev.projects.bean.tag.Tag;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record FinancialRecordReadonly(
        Long id,
        String name,
        String details,
        BigDecimal value,
        LocalDate date,
        FinancialRecordType type,
        Set<Tag> tags,
        Boolean paid,
        Boolean notification,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    @Builder
    public FinancialRecordReadonly {}
}