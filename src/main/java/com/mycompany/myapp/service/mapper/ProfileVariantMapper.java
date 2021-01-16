package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProfileVariantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProfileVariant} and its DTO {@link ProfileVariantDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileTypeMapper.class})
public interface ProfileVariantMapper extends EntityMapper<ProfileVariantDTO, ProfileVariant> {

    @Mapping(source = "profileType.id", target = "profileTypeId")
    @Mapping(source = "profileType.name", target = "profileTypeName")
    ProfileVariantDTO toDto(ProfileVariant profileVariant);

    @Mapping(source = "profileTypeId", target = "profileType")
    ProfileVariant toEntity(ProfileVariantDTO profileVariantDTO);

    default ProfileVariant fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfileVariant profileVariant = new ProfileVariant();
        profileVariant.setId(id);
        return profileVariant;
    }
}
