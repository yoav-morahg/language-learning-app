package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.EnhancedVocabItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    @Override
    public EnhancedVocabItem getRandomTerm(String typeFilter) {
        if (typeFilter != null) {
            TypedQuery<EnhancedVocabItem> query = entityManager.createQuery(
                    "SELECT vi FROM EnhancedVocabItem vi WHERE vi.termType = ?1 ORDER BY random() LIMIT 1",
                    EnhancedVocabItem.class);
            return query.setParameter(1, typeFilter).getSingleResult();
        }
        TypedQuery<EnhancedVocabItem> query = entityManager.createQuery(
                "SELECT vi FROM EnhancedVocabItem vi ORDER BY random() LIMIT 1",
                EnhancedVocabItem.class);
        return query.getSingleResult();

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
