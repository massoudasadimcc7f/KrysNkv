package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProfileVariantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileVariantDTO.class);
        ProfileVariantDTO profileVariantDTO1 = new ProfileVariantDTO();
        profileVariantDTO1.setId(1L);
        ProfileVariantDTO profileVariantDTO2 = new ProfileVariantDTO();
        assertThat(profileVariantDTO1).isNotEqualTo(profileVariantDTO2);
        profileVariantDTO2.setId(profileVariantDTO1.getId());
        assertThat(profileVariantDTO1).isEqualTo(profileVariantDTO2);
        profileVariantDTO2.setId(2L);
        assertThat(profileVariantDTO1).isNotEqualTo(profileVariantDTO2);
        profileVariantDTO1.setId(null);
        assertThat(profileVariantDTO1).isNotEqualTo(profileVariantDTO2);
    }
}
