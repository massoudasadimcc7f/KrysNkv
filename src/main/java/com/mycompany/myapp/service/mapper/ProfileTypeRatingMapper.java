package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProfileTypeRatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProfileTypeRating} and its DTO {@link ProfileTypeRatingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileTypeMapper.class})
public interface ProfileTypeRatingMapper extends EntityMapper<ProfileTypeRatingDTO, ProfileTypeRating> {

    @Mapping(source = "profileType.id", target = "profileTypeId")
    @Mapping(source = "profileType.name", target = "profileTypeName")
    ProfileTypeRatingDTO toDto(ProfileTypeRating profileTypeRating);

    @Mapping(source = "profileTypeId", target = "profileType")
    ProfileTypeRating toEntity(ProfileTypeRatingDTO profileTypeRatingDTO);

    default ProfileTypeRating fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfileTypeRating profileTypeRating = new ProfileTypeRating();
        profileTypeRating.setId(id);
        return profileTypeRating;
    }
}
