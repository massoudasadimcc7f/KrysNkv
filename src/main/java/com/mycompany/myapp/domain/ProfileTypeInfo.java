package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProfileTypeInfo.
 */
@Entity
@Table(name = "profile_type_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfileTypeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "h_1")
    private String h1;

    @Column(name = "h_2")
    private String h2;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties("profileTypeInfos")
    private ProfileType profileType;

    @ManyToOne
    @JsonIgnoreProperties("profileTypeInfos")
    private ProfileVariant profileVariant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public ProfileTypeInfo chapter(String chapter) {
        this.chapter = chapter;
        return this;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Integer getRank() {
        return rank;
    }

    public ProfileTypeInfo rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String geth1() {
        return h1;
    }

    public ProfileTypeInfo h1(String h1) {
        this.h1 = h1;
        return this;
    }

    public void seth1(String h1) {
        this.h1 = h1;
    }

    public String geth2() {
        return h2;
    }

    public ProfileTypeInfo h2(String h2) {
        this.h2 = h2;
        return this;
    }

    public void seth2(String h2) {
        this.h2 = h2;
    }

    public String getContent() {
        return content;
    }

    public ProfileTypeInfo content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public ProfileTypeInfo profileType(ProfileType profileType) {
        this.profileType = profileType;
        return this;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    public ProfileVariant getProfileVariant() {
        return profileVariant;
    }

    public ProfileTypeInfo profileVariant(ProfileVariant profileVariant) {
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
        if (!(o instanceof ProfileTypeInfo)) {
            return false;
        }
        return id != null && id.equals(((ProfileTypeInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProfileTypeInfo{" +
            "id=" + getId() +
            ", chapter='" + getChapter() + "'" +
            ", rank=" + getRank() +
            ", h1='" + geth1() + "'" +
            ", h2='" + geth2() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
