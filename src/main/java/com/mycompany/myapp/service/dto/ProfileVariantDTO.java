package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProfileVariant} entity.
 */
public class ProfileVariantDTO implements Serializable {

    private Long id;

    private String name;

    private String color;


    private Long profileTypeId;

    private String profileTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getProfileTypeId() {
        return profileTypeId;
    }

    public void setProfileTypeId(Long profileTypeId) {
        this.profileTypeId = profileTypeId;
    }

    public String getProfileTypeName() {
        return profileTypeName;
    }

    public void setProfileTypeName(String profileTypeName) {
        this.profileTypeName = profileTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileVariantDTO profileVariantDTO = (ProfileVariantDTO) o;
        if (profileVariantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileVariantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileVariantDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", profileType=" + getProfileTypeId() +
            ", profileType='" + getProfileTypeName() + "'" +
            "}";
    }
}
