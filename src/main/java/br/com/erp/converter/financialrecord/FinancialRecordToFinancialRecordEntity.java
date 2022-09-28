package br.com.erp.converter.financialrecord;

import br.com.erp.bean.tag.Tag;
import br.com.erp.bean.financialrecord.FinancialRecord;
import br.com.erp.entity.FinancialRecordEntity;
import br.com.erp.entity.TagEntity;
import br.com.erp.entity.UserEntity;
import br.com.erp.exception.NotFoundException;
import br.com.erp.repository.FinancialRecordRepository;
import br.com.erp.repository.TagRepository;
import br.com.erp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Function;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class FinancialRecordToFinancialRecordEntity implements Function<FinancialRecord, FinancialRecordEntity> {

    private final TagRepository tagRepository;

    private final FinancialRecordRepository financialRecordRepository;            ;

    private final UserService userService;

    @Override
    public FinancialRecordEntity apply(FinancialRecord financialRecord) {
        var user = userService.getCurrentUser();

        return FinancialRecordEntity
                .builder()
                .id(financialRecord.id())
                .name(financialRecord.name())
                .details(financialRecord.details())
                .value(financialRecord.value())
                .type(financialRecord.type())
                .date(financialRecord.date())
                .tags(ofNullable(financialRecord.tags())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .map(it -> tagRepository.findByUserAndName(user, it.name())
                                .orElseGet(() -> saveTag(it, user)))
                        .collect(toSet()))
                .user(user)
                .paid(financialRecord.paid())
                .createdAt(getCreatedAt(financialRecord.id(), user))
                .updatedAt(now())
                .build();
    }

    private TagEntity saveTag(Tag tag, UserEntity user) {
        return tagRepository.save(new TagEntity(tag.id(), tag.name(), user));
    }

    private LocalDateTime getCreatedAt(Long id, UserEntity user) {
        return id == null ?
                now() :
                financialRecordRepository.findByUserAndId(user, id)
                        .orElseThrow(() -> new NotFoundException("Registro não encontrado"))
                        .getCreatedAt();
    }

}
