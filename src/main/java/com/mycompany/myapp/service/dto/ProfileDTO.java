package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Profile} entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    private String name;

    private String jobTitle;

    private String notes;

    private Boolean me;

    private String relation;


    private Long profileVariantId;

    private String profileVariantName;

    private Long userId;

    private String userLogin;

    private Set<QuestionDTO> questions = new HashSet<>();

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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean isMe() {
        return me;
    }

    public void setMe(Boolean me) {
        this.me = me;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if (profileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", notes='" + getNotes() + "'" +
            ", me='" + isMe() + "'" +
            ", relation='" + getRelation() + "'" +
            ", profileVariant=" + getProfileVariantId() +
            ", profileVariant='" + getProfileVariantName() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
