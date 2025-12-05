package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.VocabCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VocabCollectionRepository extends JpaRepository<VocabCollection, Long> {

    Optional<VocabCollection> findByName(String name);



}
