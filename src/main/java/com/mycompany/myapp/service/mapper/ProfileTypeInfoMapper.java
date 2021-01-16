package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProfileTypeInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProfileTypeInfo} and its DTO {@link ProfileTypeInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileTypeMapper.class, ProfileVariantMapper.class})
public interface ProfileTypeInfoMapper extends EntityMapper<ProfileTypeInfoDTO, ProfileTypeInfo> {

    @Mapping(source = "profileType.id", target = "profileTypeId")
    @Mapping(source = "profileType.name", target = "profileTypeName")
    @Mapping(source = "profileVariant.id", target = "profileVariantId")
    @Mapping(source = "profileVariant.name", target = "profileVariantName")
    ProfileTypeInfoDTO toDto(ProfileTypeInfo profileTypeInfo);

    @Mapping(source = "profileTypeId", target = "profileType")
    @Mapping(source = "profileVariantId", target = "profileVariant")
    ProfileTypeInfo toEntity(ProfileTypeInfoDTO profileTypeInfoDTO);

    default ProfileTypeInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfileTypeInfo profileTypeInfo = new ProfileTypeInfo();
        profileTypeInfo.setId(id);
        return profileTypeInfo;
    }
}
