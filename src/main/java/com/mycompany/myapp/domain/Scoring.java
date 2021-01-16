package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Scoring.
 */
@Entity
@Table(name = "scoring")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Scoring implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "score_1")
    private Integer score1;

    @Column(name = "score_2")
    private Integer score2;

    @ManyToOne
    @JsonIgnoreProperties("scorings")
    private ProfileVariant profileVariant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore1() {
        return score1;
    }

    public Scoring score1(Integer score1) {
        this.score1 = score1;
        return this;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public Scoring score2(Integer score2) {
        this.score2 = score2;
        return this;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public ProfileVariant getProfileVariant() {
        return profileVariant;
    }

    public Scoring profileVariant(ProfileVariant profileVariant) {
        this.profileVariant = profileVariant;
        return this;
    }

    public void setProfileVariant(ProfileVariant profileVariant) {
        this.profileVariant = profileVariant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scoring)) {
            return false;
        }
        return id != null && id.equals(((Scoring) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Scoring{" +
            "id=" + getId() +
            ", score1=" + getScore1() +
            ", score2=" + getScore2() +
            "}";
    }
}
