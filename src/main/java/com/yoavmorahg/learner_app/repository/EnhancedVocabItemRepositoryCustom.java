package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.EnhancedVocabItem;

import java.util.List;

public interface EnhancedVocabItemRepositoryCustom {


    List<EnhancedVocabItem> getRandomTerms(Integer maxTerms);

    EnhancedVocabItem getRandomTerm(String typeFilter);

//    EnhancedVocabItem getRandomWithFilters(String omit, String filter);
}
