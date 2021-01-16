package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProfileTypeRatingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileTypeRating.class);
        ProfileTypeRating profileTypeRating1 = new ProfileTypeRating();
        profileTypeRating1.setId(1L);
        ProfileTypeRating profileTypeRating2 = new ProfileTypeRating();
        profileTypeRating2.setId(profileTypeRating1.getId());
        assertThat(profileTypeRating1).isEqualTo(profileTypeRating2);
        profileTypeRating2.setId(2L);
        assertThat(profileTypeRating1).isNotEqualTo(profileTypeRating2);
        profileTypeRating1.setId(null);
        assertThat(profileTypeRating1).isNotEqualTo(profileTypeRating2);
    }
}
