package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.EnhancedVocabItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnhancedVocabItemRepository extends JpaRepository<EnhancedVocabItem, Long> {

    Optional<EnhancedVocabItem> findByEnTerm(String enTerm);


}

