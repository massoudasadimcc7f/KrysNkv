package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Scoring} entity.
 */
public class ScoringDTO implements Serializable {

    private Long id;

    private Integer score1;

    private Integer score2;


    private Long profileVariantId;

    private String profileVariantName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Long getProfileVariantId() {
        return profileVariantId;
    }

    public void setProfileVariantId(Long profileVariantId) {
        this.profileVariantId = profileVariantId;
    }

    public String getProfileVariantName() {
        return profileVariantName;
    }

    public void setProfileVariantName(String profileVariantName) {
        this.profileVariantName = profileVariantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScoringDTO scoringDTO = (ScoringDTO) o;
        if (scoringDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scoringDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScoringDTO{" +
            "id=" + getId() +
            ", score1=" + getScore1() +
            ", score2=" + getScore2() +
            ", profileVariant=" + getProfileVariantId() +
            ", profileVariant='" + getProfileVariantName() + "'" +
            "}";
    }
}
