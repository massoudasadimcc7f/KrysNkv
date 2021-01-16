package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProfileTypeInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileTypeInfo.class);
        ProfileTypeInfo profileTypeInfo1 = new ProfileTypeInfo();
        profileTypeInfo1.setId(1L);
        ProfileTypeInfo profileTypeInfo2 = new ProfileTypeInfo();
        profileTypeInfo2.setId(profileTypeInfo1.getId());
        assertThat(profileTypeInfo1).isEqualTo(profileTypeInfo2);
        profileTypeInfo2.setId(2L);
        assertThat(profileTypeInfo1).isNotEqualTo(profileTypeInfo2);
        profileTypeInfo1.setId(null);
        assertThat(profileTypeInfo1).isNotEqualTo(profileTypeInfo2);
    }
}
