package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.VocabCollection;

import java.util.List;

public interface VocabCollectionRepositoryCustom {

    List<VocabCollection> findAllWithItems();
}
