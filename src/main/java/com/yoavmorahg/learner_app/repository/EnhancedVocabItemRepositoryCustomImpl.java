package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.EnhancedVocabItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnhancedVocabItemRepositoryCustomImpl implements EnhancedVocabItemRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EnhancedVocabItem> getRandomTerms(Integer maxTerms) {
        return entityManager.createQuery("SELECT vi FROM EnhancedVocabItem vi ORDER BY random()",
                        EnhancedVocabItem.class)
                .setMaxResults(maxTerms)
                .getResultList();

    }

    /*
    @Override
    public List<EnhancedVocabItem> getRandomWithFilters(String omit, String filter) {
        String query = "SELECT vi FROM EnhancedVocabItem vi ORDER BY random()";
        if (omit != null) {
            query = "SELECT vi FROM EnhancedVocabItem vi " +
                    "JOIN VocabCollection vc " +
                    "WHERE vc.name != ? ORDER BY random()";
            return entityManager.("SELECT vi FROM EnhancedVocabItem vi ORDER BY random()",
                    EnhancedVocabItem.class)
                    .setMaxResults(1)
                    .getResultList();
        } else {
            return entityManager.createQuery("SELECT vi FROM EnhancedVocabItem vi ORDER BY random()",
                        EnhancedVocabItem.class)
                .setMaxResults(1)
                .getResultList();
        }
        return null;
    }*/
}
