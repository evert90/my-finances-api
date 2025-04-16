package br.com.erp.service;

import br.com.erp.bean.financialrecord.FinancialRecord;
import br.com.erp.converter.financialrecord.FinancialRecordRecurrenceEntityToFinancialRecord;
import br.com.erp.converter.financialrecord.FinancialRecordToFinancialRecordRecurrenceEntity;
import br.com.erp.exception.NotFoundException;
import br.com.erp.repository.FinancialRecordRecurrenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

import static java.util.Optional.of;

@Service
@RequiredArgsConstructor
public class FinancialRecordRecurrenceService {

    private final FinancialRecordRecurrenceRepository repository;

    private final FinancialRecordToFinancialRecordRecurrenceEntity toRecurrenceEntity;

    private final FinancialRecordRecurrenceEntityToFinancialRecord toApi;

    private final UserService userService;

    public void save(FinancialRecord financialRecord) {
        if(financialRecord.recurrence()){
            of(financialRecord)
                    .map(toRecurrenceEntity)
                    .map(repository::save)
                    .orElseThrow(() -> new RuntimeException("Erro ao salvar registro de recorrência"));
        }
    }

    public List<FinancialRecord> getAll() {
        return repository.findByUserOrderByName(userService.getCurrentUser())
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
