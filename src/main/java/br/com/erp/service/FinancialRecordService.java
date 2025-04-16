package br.com.erp.service;

import br.com.erp.bean.financialrecord.FinancialRecord;
import br.com.erp.bean.financialrecord.FinancialRecordReadonly;
import br.com.erp.bean.financialrecord.FinancialRecordTotal;
import br.com.erp.bean.financialrecord.FinancialRecordType;
import br.com.erp.bean.tag.TagTotal;
import br.com.erp.converter.TagTotalRepositoryToTagTotal;
import br.com.erp.converter.financialrecord.FinancialRecordEntityToFinanacialRecordReadonly;
import br.com.erp.converter.financialrecord.FinancialRecordToFinancialRecordEntity;
import br.com.erp.exception.NotFoundException;
import br.com.erp.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
@Slf4j
public class FinancialRecordService {
    private final FinancialRecordRepository repository;

    private final FinancialRecordToFinancialRecordEntity toEntity;

    private final FinancialRecordEntityToFinanacialRecordReadonly toApi;

    private final FinancialRecordRecurrenceService financialRecordRecurrenceService;

    private final TagTotalRepositoryToTagTotal toTagTotal;

    private final UserService userService;

    @Transactional
    public FinancialRecordReadonly save(FinancialRecord financialRecord) {
        var financialRecordReadonly = ofNullable(financialRecord)
                .map(toEntity)
                .map(repository::save)
                .map(toApi)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar/retornar o registro financeiro"));

        financialRecordRecurrenceService.save(financialRecord);

        return financialRecordReadonly;
    }

    public Set<FinancialRecordReadonly> getByType(FinancialRecordType type) {
        return repository.findByType(type)
                .stream()
                .map(toApi)
                .collect(toSet());
    }

    public Set<FinancialRecordReadonly> getByPeriod(LocalDate start, LocalDate end) {
        return repository.findByUserAndDateBetweenOrderByDateDesc(userService.getCurrentUser(), start, end)
                .stream()
                .map(toApi)
                .collect(toCollection(LinkedHashSet::new));
    }

    public Set<FinancialRecordTotal> getTotal() {
        return repository.getTotalReport(userService.getCurrentUser());
    }

    public Set<FinancialRecordTotal> getTotalByPeriod(LocalDate start, LocalDate end) {
        return repository.getTotalReportByPeriod(start, end, userService.getCurrentUser());
    }

    public Set<TagTotal> getTotalByPeriodAndTags(LocalDate start, LocalDate end, Set<Long> tagIds) {
        return repository.getTotalReportByPeriodAndTagIds(
                    start,
                    end,
                    userService.getCurrentUser().getId(),
                    tagIds
                )
                .stream()
                .map(toTagTotal)
                .collect(toSet());
    }

    public List<FinancialRecordReadonly> getAll() {
        return repository.findByUserOrderByDateAndNameAsc(userService.getCurrentUser())
                .stream()
                .map(toApi)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        var entity = repository.findByUserAndId(userService.getCurrentUser(), id)
                .orElseThrow(() -> new NotFoundException("Registro não encontrado"));

        entity.getTags().clear();
        repository.delete(entity);
    }

}
