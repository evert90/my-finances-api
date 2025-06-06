package br.dev.projects.service.recurrence;

import br.dev.projects.bean.recurrence.RecurrencePeriod;
import br.dev.projects.entity.FinancialRecordRecurrenceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.HashMap;

import static java.time.LocalDateTime.of;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecurrenceDateWeeklyProcessorTest {

    private RecurrenceDateWeeklyProcessor processor;

    @Test
    void matches() {
        //given
        processor = new RecurrenceDateWeeklyProcessor(getFixedClock(of(2022, 4, 10, 14, 0)));
        var map = new HashMap<RecurrencePeriod, Boolean>();
        stream(RecurrencePeriod.values())
                .forEach(recurrencePeriod -> map.put(recurrencePeriod, recurrencePeriod == RecurrencePeriod.WEEKLY));

        //then
        map.forEach((recurrencePeriod, expected) -> assertEquals(expected, processor.matches(recurrencePeriod)));
    }

    @Test
    @DisplayName("Deve retornar data da proxima sexta, referente a data atual")
    void getNext() {
        //given
        var dateTime = of(2022, 4, 10, 14, 0); //DOMINGO
        processor = new RecurrenceDateWeeklyProcessor(getFixedClock(dateTime));

        var entity = FinancialRecordRecurrenceEntity
                .builder()
                .date(LocalDate.of(2022, 4, 22)) //SEXTA
                .periodQuantity(1)
                .build();
        var expected = LocalDate.of(2022, 4, 15);

        //when
        var result = processor.getNext(entity);

        //then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Deve retornar data daqui a duas sextas, referente a data atual")
    void getNext2() {
        //given
        var dateTime = of(2022, 4, 10, 14, 0); //DOMINGO
        processor = new RecurrenceDateWeeklyProcessor(getFixedClock(dateTime));

        var entity = FinancialRecordRecurrenceEntity
                .builder()
                .date(LocalDate.of(2022, 4, 15)) //SEXTA
                .periodQuantity(2)
                .build();
        var expected = LocalDate.of(2022, 4, 22);

        //when
        var result = processor.getNext(entity);

        //then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Deve retornar data da proxima sexta, se hoje for sexta")
    void getNext3() {
        //given
        var dateTime = of(2022, 4, 15, 14, 0); //SEXTA
        processor = new RecurrenceDateWeeklyProcessor(getFixedClock(dateTime));

        var entity = FinancialRecordRecurrenceEntity
                .builder()
                .date(LocalDate.of(2022, 4, 15)) //SEXTA
                .periodQuantity(1)
                .build();
        var expected = LocalDate.of(2022, 4, 22);

        //when
        var result = processor.getNext(entity);

        //then
        assertEquals(expected, result);
    }

    private Clock getFixedClock(LocalDateTime dateTime) {
        var instant = ZonedDateTime.of(dateTime, ZoneId.systemDefault()).toInstant();
        return Clock.fixed(instant, ZoneId.systemDefault());
    }
}