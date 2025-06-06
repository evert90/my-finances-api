package br.dev.projects.service.recurrence;

import br.dev.projects.bean.recurrence.RecurrencePeriod;
import br.dev.projects.entity.FinancialRecordRecurrenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

import static java.time.LocalDate.*;
import static java.time.temporal.TemporalAdjusters.next;

@RequiredArgsConstructor
@Service
public class RecurrenceDateWeeklyProcessor implements RecurrenceDateProcessor {

    private final Clock clock;

    @Override
    public Boolean matches(RecurrencePeriod recurrencePeriod) {
        return recurrencePeriod == RecurrencePeriod.WEEKLY;
    }

    @Override
    public LocalDate getNext(FinancialRecordRecurrenceEntity entity) {
        return now(clock)
                .with(next(entity.getDate().getDayOfWeek()))
                .plusWeeks(entity.getPeriodQuantity() - 1);
    }
}
