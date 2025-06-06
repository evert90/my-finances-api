package br.dev.projects.converter.tag;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.entity.TagEntity;
import br.dev.projects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class TagToTagEntity implements Function<Tag, TagEntity> {

    private final UserService userService;

    @Override
    public TagEntity apply(Tag tag) {
        return new TagEntity(
                tag.id(),
                tag.name(),
                userService.getCurrentUser()
        );
    }
}
