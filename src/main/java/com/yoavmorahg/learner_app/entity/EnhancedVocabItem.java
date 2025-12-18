package com.yoavmorahg.learner_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "enhanced_vocab_item")
public class EnhancedVocabItem {
    public EnhancedVocabItem() {
    }

    public EnhancedVocabItem(String enTerm, String ptTermMasculine, String ptTermFeminine, String termType, String verbRule, String gender, String notes, AudioData audioData) {
        this.enTerm = enTerm;
        this.ptTermMasculine = ptTermMasculine;
        this.ptTermFeminine = ptTermFeminine;
        this.termType = termType;
        this.verbRule = verbRule;
        this.gender = gender;
        this.notes = notes;
        this.audioData = audioData;
    }

    public EnhancedVocabItem(String enTerm, String ptTermMasculine, String ptTermFeminine, String termType, String verbRule, String gender, String notes, AudioData audioData, Set<VocabCollection> collections) {
        this.enTerm = enTerm;
        this.ptTermMasculine = ptTermMasculine;
        this.ptTermFeminine = ptTermFeminine;
        this.termType = termType;
        this.verbRule = verbRule;
        this.gender = gender;
        this.notes = notes;
        this.audioData = audioData;
        this.collections = collections;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vocab_item_id")
    private Long id;

    @Column(name="en_term", unique=true)
    private String enTerm;

    @Column(name="pt_term_m")
    private String ptTermMasculine;

    @Column(name="pt_term_f")
    private String ptTermFeminine;

    @Column(name = "term_type")
    private String termType;

    @Column(name = "verb_rule")
    private String verbRule;

    @Column(name = "term_gender")
    private String gender;

    @Column(name = "term_notes")
    private String notes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    @JsonManagedReference
    private AudioData audioData;


    @ManyToMany(mappedBy = "vocabItems")
    @JsonIgnore
    private Set<VocabCollection> collections = new HashSet<>();


    @Column(name="is_archived")
    public boolean archived = false;

    @Column(name = "created_ts", nullable = false)
    public LocalDateTime createdTs = LocalDateTime.now();

    @Column(name = "updated_ts")
    public LocalDateTime updatedTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnTerm() {
        return enTerm;
    }

    public void setEnTerm(String enTerm) {
        this.enTerm = enTerm;
    }

    public String getPtTermMasculine() {
        return ptTermMasculine;
    }

    public void setPtTermMasculine(String ptTermMasculine) {
        this.ptTermMasculine = ptTermMasculine;
    }

    public String getPtTermFeminine() {
        return ptTermFeminine;
    }

    public void setPtTermFeminine(String ptTermFeminine) {
        this.ptTermFeminine = ptTermFeminine;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getVerbRule() {
        return verbRule;
    }

    public void setVerbRule(String verbRule) {
        this.verbRule = verbRule;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public AudioData getAudioData() {
        return audioData;
    }

    public void setAudioData(AudioData audioData) {
        this.audioData = audioData;
    }

    public Set<VocabCollection> getCollections() {
        return collections;
    }

    public void setCollections(Set<VocabCollection> collections) {
        this.collections = collections;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    public void addCollection(VocabCollection collection) {
        this.collections.add(collection);
        collection.getVocabItems().add(this);
    }

    public void removeCollection(VocabCollection collection) {
        this.collections.remove(collection);
        collection.getVocabItems().remove(this);
    }



}
