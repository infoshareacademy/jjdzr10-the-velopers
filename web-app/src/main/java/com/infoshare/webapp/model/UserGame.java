package com.infoshare.webapp.model;

import com.infoshare.webapp.Dto.AnswerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
@Component
@Setter
@Getter
@NoArgsConstructor
@Entity
public class UserGame extends Game{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> gameQuestions;
//    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private List<AnswerDto> userAnswers;
    private int userScore;

}
