package com.infoshare.webapp.Dao;

import com.infoshare.webapp.model.Question;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Component
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Question findById(Long id) {
        return this.entityManager.find(Question.class, id);
    }

    @Transactional
    public Question save(Question question) {
        this.entityManager.persist(question);
        return question;
    }

    @Transactional
    public void deleteById(Long questionId) {
        Question question = this.findById(questionId);
        this.entityManager.remove(question);
    }

    @Transactional
    public Question update(Question question) {
        this.entityManager.merge(question);
        return question;
    }


    public List<Question> findAll() {
        TypedQuery<Question> query = this.entityManager.createQuery("select q from Question q", Question.class);
        return query.getResultList();
    }
}
