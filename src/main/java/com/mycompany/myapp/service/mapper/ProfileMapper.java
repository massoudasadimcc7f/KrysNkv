package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileVariantMapper.class, UserMapper.class, QuestionMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "profileVariant.id", target = "profileVariantId")
    @Mapping(source = "profileVariant.name", target = "profileVariantName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "profileVariantId", target = "profileVariant")
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeQuestion", ignore = true)
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
