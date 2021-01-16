package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProfileTypeInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileTypeInfoDTO.class);
        ProfileTypeInfoDTO profileTypeInfoDTO1 = new ProfileTypeInfoDTO();
        profileTypeInfoDTO1.setId(1L);
        ProfileTypeInfoDTO profileTypeInfoDTO2 = new ProfileTypeInfoDTO();
        assertThat(profileTypeInfoDTO1).isNotEqualTo(profileTypeInfoDTO2);
        profileTypeInfoDTO2.setId(profileTypeInfoDTO1.getId());
        assertThat(profileTypeInfoDTO1).isEqualTo(profileTypeInfoDTO2);
        profileTypeInfoDTO2.setId(2L);
        assertThat(profileTypeInfoDTO1).isNotEqualTo(profileTypeInfoDTO2);
        profileTypeInfoDTO1.setId(null);
        assertThat(profileTypeInfoDTO1).isNotEqualTo(profileTypeInfoDTO2);
    }
}
