package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "notes")
    private String notes;

    @Column(name = "me")
    private Boolean me;

    @Column(name = "relation")
    private String relation;

    @ManyToOne
    @JsonIgnoreProperties("profiles")
    private ProfileVariant profileVariant;

    @ManyToOne
    @JsonIgnoreProperties("profiles")
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "profile_question",
               joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Profile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Profile jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getNotes() {
        return notes;
    }

    public Profile notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean isMe() {
        return me;
    }

    public Profile me(Boolean me) {
        this.me = me;
        return this;
    }

    public void setMe(Boolean me) {
        this.me = me;
    }

    public String getRelation() {
        return relation;
    }

    public Profile relation(String relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public ProfileVariant getProfileVariant() {
        return profileVariant;
    }

    public Profile profileVariant(ProfileVariant profileVariant) {
        this.profileVariant = profileVariant;
        return this;
    }

    public void setProfileVariant(ProfileVariant profileVariant) {
        this.profileVariant = profileVariant;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Profile questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Profile addQuestion(Question question) {
        this.questions.add(question);
        question.getProfiles().add(this);
        return this;
    }

    public Profile removeQuestion(Question question) {
        this.questions.remove(question);
        question.getProfiles().remove(this);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", notes='" + getNotes() + "'" +
            ", me='" + isMe() + "'" +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
