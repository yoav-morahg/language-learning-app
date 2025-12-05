package com.yoavmorahg.learner_app.repository;

import com.yoavmorahg.learner_app.entity.AudioData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudioDataRepository extends JpaRepository<AudioData, Long> {


    Optional<AudioData> findByPtTerm(String term);

    @Query("SELECT ad.id from AudioData ad ORDER BY ad.id")
    List<Long> getIds();
}
