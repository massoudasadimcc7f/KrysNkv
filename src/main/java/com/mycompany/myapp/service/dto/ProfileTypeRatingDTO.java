package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProfileTypeRating} entity.
 */
public class ProfileTypeRatingDTO implements Serializable {

    private Long id;

    private String characteristic;

    private Integer rating;


    private Long profileTypeId;

    private String profileTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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

        ProfileTypeRatingDTO profileTypeRatingDTO = (ProfileTypeRatingDTO) o;
        if (profileTypeRatingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileTypeRatingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileTypeRatingDTO{" +
            "id=" + getId() +
            ", characteristic='" + getCharacteristic() + "'" +
            ", rating=" + getRating() +
            ", profileType=" + getProfileTypeId() +
            ", profileType='" + getProfileTypeName() + "'" +
            "}";
    }
}
