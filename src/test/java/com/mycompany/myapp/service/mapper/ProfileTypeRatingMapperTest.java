package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProfileTypeRatingMapperTest {

    private ProfileTypeRatingMapper profileTypeRatingMapper;

    @BeforeEach
    public void setUp() {
        profileTypeRatingMapper = new ProfileTypeRatingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(profileTypeRatingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(profileTypeRatingMapper.fromId(null)).isNull();
    }
}
