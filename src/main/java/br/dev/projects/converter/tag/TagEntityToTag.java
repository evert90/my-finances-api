package br.dev.projects.converter.tag;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.entity.TagEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TagEntityToTag implements Function<TagEntity, Tag> {
    @Override
    public Tag apply(TagEntity tagEntity) {
        return new Tag(
                tagEntity.getId(),
                tagEntity.getName()
        );
    }
}
