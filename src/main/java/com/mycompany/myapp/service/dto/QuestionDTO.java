package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Question} entity.
 */
public class QuestionDTO implements Serializable {

    private Long id;

    private Integer questionSet;

    private Integer question;

    private String answer;

    private String description;

    private String tag;

    private Integer score;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(Integer questionSet) {
        this.questionSet = questionSet;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionDTO questionDTO = (QuestionDTO) o;
        if (questionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
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
