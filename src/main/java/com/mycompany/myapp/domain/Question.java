package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "question_set")
    private Integer questionSet;

    @Column(name = "question")
    private Integer question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "description")
    private String description;

    @Column(name = "tag")
    private String tag;

    @Column(name = "score")
    private Integer score;

    @ManyToMany(mappedBy = "questions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Profile> profiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuestionSet() {
        return questionSet;
    }

    public Question questionSet(Integer questionSet) {
        this.questionSet = questionSet;
        return this;
    }

    public void setQuestionSet(Integer questionSet) {
        this.questionSet = questionSet;
    }

    public Integer getQuestion() {
        return question;
    }

    public Question question(Integer question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public Question answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public Question description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public Question tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getScore() {
        return score;
    }

    public Question score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public Question profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Question addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getQuestions().add(this);
        return this;
    }

    public Question removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getQuestions().remove(this);
        return this;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", questionSet=" + getQuestionSet() +
            ", question=" + getQuestion() +
            ", answer='" + getAnswer() + "'" +
            ", description='" + getDescription() + "'" +
            ", tag='" + getTag() + "'" +
            ", score=" + getScore() +
            "}";
    }
}
