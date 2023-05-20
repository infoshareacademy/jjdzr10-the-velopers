package com.infoshare.webapp.Dao;

import com.infoshare.webapp.model.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Question save(Question question) {
        this.entityManager.persist(question);
        return question;
    }
}
