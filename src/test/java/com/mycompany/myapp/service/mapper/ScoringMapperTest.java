package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ScoringMapperTest {

    private ScoringMapper scoringMapper;

    @BeforeEach
    public void setUp() {
        scoringMapper = new ScoringMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(scoringMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scoringMapper.fromId(null)).isNull();
    }
}
