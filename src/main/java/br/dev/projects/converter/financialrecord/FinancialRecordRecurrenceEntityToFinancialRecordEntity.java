package br.dev.projects.converter.financialrecord;

import br.dev.projects.bean.financialrecord.FinancialRecordType;
import br.dev.projects.entity.FinancialRecordEntity;
import br.dev.projects.entity.FinancialRecordRecurrenceEntity;
import br.dev.projects.repository.TagRepository;
import br.dev.projects.service.recurrence.RecurrenceDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.function.Function;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class FinancialRecordRecurrenceEntityToFinancialRecordEntity
        implements Function<FinancialRecordRecurrenceEntity, FinancialRecordEntity> {

    private final TagRepository tagRepository;

    private final RecurrenceDateService recurrenceDateService;

    @Override
    public FinancialRecordEntity apply(FinancialRecordRecurrenceEntity financialRecord) {

        return FinancialRecordEntity
                .builder()
                .name(financialRecord.getName())
                .details(financialRecord.getDetails())
                .value(financialRecord.getEmptyValue() ? valueOf(0) : financialRecord.getValue())
                .type(financialRecord.getType())
                .date(recurrenceDateService.getNext(financialRecord))
                .tags(ofNullable(financialRecord.getTags())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .map(it -> tagRepository.findByUserAndName(financialRecord.getUser(), it.getName())
                                .orElseThrow(() ->
                                        new RuntimeException("Erro ao buscar tags para cadastro de registro recorrente")))
                        .collect(toSet()))
                .user(financialRecord.getUser())
                .paid(financialRecord.getType() == FinancialRecordType.EXPENSE ? false : null)
                .notification(financialRecord.getNotification())
                .createdAt(now())
                .updatedAt(now())
                .build();
    }

}
