package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProfileTypeRatingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileTypeRatingDTO.class);
        ProfileTypeRatingDTO profileTypeRatingDTO1 = new ProfileTypeRatingDTO();
        profileTypeRatingDTO1.setId(1L);
        ProfileTypeRatingDTO profileTypeRatingDTO2 = new ProfileTypeRatingDTO();
        assertThat(profileTypeRatingDTO1).isNotEqualTo(profileTypeRatingDTO2);
        profileTypeRatingDTO2.setId(profileTypeRatingDTO1.getId());
        assertThat(profileTypeRatingDTO1).isEqualTo(profileTypeRatingDTO2);
        profileTypeRatingDTO2.setId(2L);
        assertThat(profileTypeRatingDTO1).isNotEqualTo(profileTypeRatingDTO2);
        profileTypeRatingDTO1.setId(null);
        assertThat(profileTypeRatingDTO1).isNotEqualTo(profileTypeRatingDTO2);
    }
}
