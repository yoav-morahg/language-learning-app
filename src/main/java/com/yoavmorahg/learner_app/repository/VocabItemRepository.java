package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.VocabItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VocabItemRepository extends JpaRepository<VocabItem, Long> {

    Optional<VocabItem> findBySideA(String sideA);


}

