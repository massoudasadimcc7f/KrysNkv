package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ScoringDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scoring} and its DTO {@link ScoringDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileVariantMapper.class})
public interface ScoringMapper extends EntityMapper<ScoringDTO, Scoring> {

    @Mapping(source = "profileVariant.id", target = "profileVariantId")
    @Mapping(source = "profileVariant.name", target = "profileVariantName")
    ScoringDTO toDto(Scoring scoring);

    @Mapping(source = "profileVariantId", target = "profileVariant")
    Scoring toEntity(ScoringDTO scoringDTO);

    default Scoring fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scoring scoring = new Scoring();
        scoring.setId(id);
        return scoring;
    }
}
