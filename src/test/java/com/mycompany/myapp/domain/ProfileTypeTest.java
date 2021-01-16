package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProfileTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileType.class);
        ProfileType profileType1 = new ProfileType();
        profileType1.setId(1L);
        ProfileType profileType2 = new ProfileType();
        profileType2.setId(profileType1.getId());
        assertThat(profileType1).isEqualTo(profileType2);
        profileType2.setId(2L);
        assertThat(profileType1).isNotEqualTo(profileType2);
        profileType1.setId(null);
        assertThat(profileType1).isNotEqualTo(profileType2);
    }
}
