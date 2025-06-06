package br.dev.projects.service;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.converter.tag.TagEntityToTag;
import br.dev.projects.converter.tag.TagToTagEntity;
import br.dev.projects.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagToTagEntity toEntity;

    private final TagEntityToTag toApi;

    private final TagRepository repository;

    private final UserService userService;

    public Tag save(Tag tag) {
        return ofNullable(tag)
                .map(toEntity)
                .map(repository::save)
                .map(toApi)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar/retornar tag"));
    }

    public Set<Tag> getAll() {
        return repository.findByUserOrderByNameAsc(userService.getCurrentUser())
                .stream()
                .map(toApi)
                .collect(toCollection(LinkedHashSet::new));
    }
}
