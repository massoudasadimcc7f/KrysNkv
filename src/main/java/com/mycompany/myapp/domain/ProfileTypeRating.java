package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProfileTypeRating.
 */
@Entity
@Table(name = "profile_type_rating")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfileTypeRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "characteristic")
    private String characteristic;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne
    @JsonIgnoreProperties("profileTypeRatings")
    private ProfileType profileType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public ProfileTypeRating characteristic(String characteristic) {
        this.characteristic = characteristic;
        return this;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public Integer getRating() {
        return rating;
    }

    public ProfileTypeRating rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public ProfileTypeRating profileType(ProfileType profileType) {
        this.profileType = profileType;
        return this;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileTypeRating)) {
            return false;
        }
        return id != null && id.equals(((ProfileTypeRating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProfileTypeRating{" +
            "id=" + getId() +
            ", characteristic='" + getCharacteristic() + "'" +
            ", rating=" + getRating() +
            "}";
    }
}
