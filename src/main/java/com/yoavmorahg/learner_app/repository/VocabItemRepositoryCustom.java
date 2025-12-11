package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.VocabItem;

import java.util.List;

public interface VocabRepositoryCustom {


    List<VocabItem> getRandomTerms(int maxTerms);
}
