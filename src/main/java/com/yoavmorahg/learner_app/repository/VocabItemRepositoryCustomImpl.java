package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.VocabItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VocabRepositoryCustomImpl implements VocabRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VocabItem> getRandomTerms(int maxTerms) {
        return entityManager.createQuery("SELECT * FROM VocabItem vc ORDER BY rand()",
                        VocabItem.class)
                .setMaxResults(maxTerms)
                .getResultList();
    }
}
/*
session.createQuery("select o from Object o order by rand()")
   .setMaxResults(10)
   .list()

 */