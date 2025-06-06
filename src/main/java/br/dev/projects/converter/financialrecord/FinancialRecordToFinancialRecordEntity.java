package br.dev.projects.converter.financialrecord;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.bean.financialrecord.FinancialRecord;
import br.dev.projects.entity.FinancialRecordEntity;
import br.dev.projects.entity.TagEntity;
import br.dev.projects.entity.UserEntity;
import br.dev.projects.exception.NotFoundException;
import br.dev.projects.repository.FinancialRecordRepository;
import br.dev.projects.repository.TagRepository;
import br.dev.projects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Function;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
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
                .notification(financialRecord.notification())
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
