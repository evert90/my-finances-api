package br.dev.projects.converter;

import br.dev.projects.bean.tag.Tag;
import br.dev.projects.bean.tag.TagTotal;
import br.dev.projects.bean.tag.TagTotalDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TagTotalRepositoryToTagTotal implements Function<TagTotalDTO, TagTotal> {
    @Override
    public TagTotal apply(TagTotalDTO tagTotalDTO) {
        return new TagTotal(
                new Tag(tagTotalDTO.id(), tagTotalDTO.name()),
                tagTotalDTO.total()
        );
    }
}
