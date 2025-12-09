package com.yoavmorahg.learner_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vocab_item")
public class VocabItem {
    public VocabItem() {
    }

    public VocabItem(String sideA, String sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="side_a", unique=true)
    private String sideA;

    @Column(name="side_b")
    private String sideB;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    @JsonManagedReference
    private AudioData audioData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vocab_collection_vocab_item",
            joinColumns = @JoinColumn(name = "vocab_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "vocab_item_id")
    )
    @JsonIgnore
    private Set<VocabCollection> collections = new HashSet<>();

    @Column(name="is_archived")
    public boolean archived = false;

    @Column(name = "created_ts", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    public LocalDateTime createdTs = LocalDateTime.now();

    @Column(name = "updated_ts")
    public LocalDateTime updatedTs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSideA() {
        return sideA;
    }

    public void setSideA(String sideA) {
        this.sideA = sideA;
    }

    public String getSideB() {
        return sideB;
    }

    public void setSideB(String sideB) {
        this.sideB = sideB;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabItem vocabItem = (VocabItem) o;
        return id.equals(vocabItem.id) && sideA.equals(vocabItem.sideA) && sideB.equals(vocabItem.sideB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sideA, sideB);
    }

    @Override
    public String toString() {
        return "VocabItem{" +
                "id=" + id +
                ", challengeTerm='" + sideA + '\'' +
                ", solution='" + sideB + '\'' +
                ", audioDataID=" + audioData != null ? audioData.getId().toString() : "null" +
                ", archived=" + archived +
                ", createdTs=" + createdTs +
                ", updatedTs=" + updatedTs +
                '}';
    }
}
