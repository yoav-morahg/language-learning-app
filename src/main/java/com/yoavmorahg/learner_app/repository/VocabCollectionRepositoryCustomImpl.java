package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.VocabCollection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VocabCollectionRepositoryCustomImpl implements VocabCollectionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<VocabCollection> findAllWithItems() {
        String hql = "SELECT vc FROM VocabCollection vc JOIN FETCH vc.vocabItems";
//        return entityManager.createQuery("FROM VocabCollection").getResultList();
        return entityManager.createQuery(hql, VocabCollection.class).getResultList();
    }
}
