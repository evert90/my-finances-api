package br.dev.projects.converter.financialrecord;

import br.dev.projects.bean.financialrecord.FinancialRecord;
import br.dev.projects.bean.tag.Tag;
import br.dev.projects.entity.FinancialRecordRecurrenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FinancialRecordRecurrenceEntityToFinancialRecord implements Function<FinancialRecordRecurrenceEntity, FinancialRecord> {

    @Override
    public FinancialRecord apply(FinancialRecordRecurrenceEntity entity) {
        return FinancialRecord
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .details(entity.getDetails())
                .value(entity.getValue())
                .date(entity.getDate())
                .type(entity.getType())
                .tags(entity.getTags().stream().map(it -> new Tag(it.getId(), it.getName())).collect(Collectors.toSet()))
                .paid(entity.getPaid())
                .notification(entity.getNotification())
                .recurrence(true)
                .recurrencePeriod(entity.getPeriod())
                .recurrenceQuantity(entity.getPeriodQuantity())
                .recurrenceEmptyValue(entity.getEmptyValue())
                .build();
    }
}
