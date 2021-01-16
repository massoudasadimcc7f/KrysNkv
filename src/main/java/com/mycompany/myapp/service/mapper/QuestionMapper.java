package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {


    @Mapping(target = "profiles", ignore = true)
    @Mapping(target = "removeProfile", ignore = true)
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
