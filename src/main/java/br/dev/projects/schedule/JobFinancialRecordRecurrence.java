package br.dev.projects.schedule;

import br.dev.projects.converter.financialrecord.FinancialRecordRecurrenceEntityToFinancialRecordEntity;
import br.dev.projects.entity.FinancialRecordRecurrenceEntity;
import br.dev.projects.repository.FinancialRecordRecurrenceRepository;
import br.dev.projects.repository.FinancialRecordRepository;
import br.dev.projects.service.recurrence.RecurrenceDateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Slf4j
public class JobFinancialRecordRecurrence {

    private final FinancialRecordRecurrenceRepository recurrenceRepository;

    private final FinancialRecordRepository repository;

    private final FinancialRecordRecurrenceEntityToFinancialRecordEntity toFinancialRecordEntity;

    private final RecurrenceDateService recurrenceDateService;

    @Scheduled(fixedDelay = 60000 * 10) //10 MINUTES
    @Transactional
    public void run() {
        log.info("Starting recurrence job");
        recurrenceRepository
                .findAll()
                .forEach(entity -> {
                    var nextDate = recurrenceDateService.getNext(entity);
                    if(nextDate.isAfter(entity.getDate()) && notExist(entity, nextDate)) {
                        repository.save(toFinancialRecordEntity.apply(entity));
                        log.info("Added recurrence");
                    }
                });
        log.info("Finished recurrence job");
    }

    private Boolean notExist(FinancialRecordRecurrenceEntity entity, LocalDate nextDate) {
        return repository
                .countByUserAndDateAndName(entity.getUser(), nextDate, entity.getName()) == 0;
    }

}
