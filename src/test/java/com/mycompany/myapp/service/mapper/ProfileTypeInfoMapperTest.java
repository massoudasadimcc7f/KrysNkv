package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProfileTypeInfoMapperTest {

    private ProfileTypeInfoMapper profileTypeInfoMapper;

    @BeforeEach
    public void setUp() {
        profileTypeInfoMapper = new ProfileTypeInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(profileTypeInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(profileTypeInfoMapper.fromId(null)).isNull();
    }
}
