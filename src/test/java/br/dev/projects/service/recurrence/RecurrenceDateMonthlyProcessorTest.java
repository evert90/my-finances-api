package br.dev.projects.service.recurrence;

import br.dev.projects.bean.recurrence.RecurrencePeriod;
import br.dev.projects.entity.FinancialRecordRecurrenceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.HashMap;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecurrenceDateMonthlyProcessorTest {

    private RecurrenceDateMonthlyProcessor processor;

    @BeforeEach
    void setUp() {
        var datetime = LocalDateTime.of(2022, 4, 10, 14, 0);
        var instant = ZonedDateTime.of(datetime, ZoneId.systemDefault()).toInstant();
        var clock = Clock.fixed(instant, ZoneId.systemDefault());

        processor = new RecurrenceDateMonthlyProcessor(clock);
    }

    @Test
    void matches() {
        //given
        var map = new HashMap<RecurrencePeriod, Boolean>();
        stream(RecurrencePeriod.values())
                .forEach(recurrencePeriod -> map.put(recurrencePeriod, recurrencePeriod == RecurrencePeriod.MONTHLY));

        //then
        map.forEach((recurrencePeriod, expected) -> assertEquals(expected, processor.matches(recurrencePeriod)));
    }

    @Test
    @DisplayName("Deve retornar o mesmo dia no proximo mes")
    void getNext() {
        //given
        var entity = FinancialRecordRecurrenceEntity
                .builder()
                .date(LocalDate.of(2022, 4, 20))
                .period(RecurrencePeriod.MONTHLY)
                .periodQuantity(1)
                .build();
        var expected = LocalDate.of(2022, 5, 20);

        //when
        var result = processor.getNext(entity);

        //then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Deve retornar o mesmo dia daqui 3 meses")
    void getNextV2() {
        //given
        var entity = FinancialRecordRecurrenceEntity
                .builder()
                .date(LocalDate.of(2022, 4, 20))
                .period(RecurrencePeriod.MONTHLY)
                .periodQuantity(3)
                .build();
        var expected = LocalDate.of(2022, 7, 20);

        //when
        var result = processor.getNext(entity);

        //then
        assertEquals(expected, result);
    }
}