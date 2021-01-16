package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProfileVariantMapperTest {

    private ProfileVariantMapper profileVariantMapper;

    @BeforeEach
    public void setUp() {
        profileVariantMapper = new ProfileVariantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(profileVariantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(profileVariantMapper.fromId(null)).isNull();
    }
}
