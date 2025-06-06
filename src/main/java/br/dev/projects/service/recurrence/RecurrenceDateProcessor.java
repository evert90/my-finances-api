package br.dev.projects.service.recurrence;

import br.dev.projects.bean.recurrence.RecurrencePeriod;
import br.dev.projects.entity.FinancialRecordRecurrenceEntity;

import java.time.LocalDate;

public interface RecurrenceDateProcessor {
    Boolean matches(RecurrencePeriod recurrencePeriod);
    LocalDate getNext(FinancialRecordRecurrenceEntity entity);
}
