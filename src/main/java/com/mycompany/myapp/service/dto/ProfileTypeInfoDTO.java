package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProfileTypeInfo} entity.
 */
public class ProfileTypeInfoDTO implements Serializable {

    private Long id;

    private String chapter;

    private Integer rank;

    private String h1;

    private String h2;

    private String content;


    private Long profileTypeId;

    private String profileTypeName;

    private Long profileVariantId;

    private String profileVariantName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String geth1() {
        return h1;
    }

    public void seth1(String h1) {
        this.h1 = h1;
    }

    public String geth2() {
        return h2;
    }

    public void seth2(String h2) {
        this.h2 = h2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

        ProfileTypeInfoDTO profileTypeInfoDTO = (ProfileTypeInfoDTO) o;
        if (profileTypeInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileTypeInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileTypeInfoDTO{" +
            "id=" + getId() +
            ", chapter='" + getChapter() + "'" +
            ", rank=" + getRank() +
            ", h1='" + geth1() + "'" +
            ", h2='" + geth2() + "'" +
            ", content='" + getContent() + "'" +
            ", profileType=" + getProfileTypeId() +
            ", profileType='" + getProfileTypeName() + "'" +
            ", profileVariant=" + getProfileVariantId() +
            ", profileVariant='" + getProfileVariantName() + "'" +
            "}";
    }
}
