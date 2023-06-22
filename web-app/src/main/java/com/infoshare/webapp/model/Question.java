package com.infoshare.webapp.model;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idQuestion;
    private Category category;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;
    @NotEmpty(message = "The question text cannot be empty")
    @NotBlank(message = "The question text cannot be blank")
    @Size(min=10, max=200, message = "The question text should contain between 10 and 200 characters")
    private String questionText;
    @Positive(message = "You can't enter a negative value")
    @Max(value = 20, message = "The maximum number of points you can enter is 20")
    private int score;

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + idQuestion +
                ", category=" + category +
                ", answers=" + answers +
                ", questionText='" + questionText + '\'' +
                ", score=" + score +
                '}';
    }
}

