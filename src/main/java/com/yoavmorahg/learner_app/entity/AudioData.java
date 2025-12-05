package com.yoavmorahg.learner_app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="audio_data")

public class AudioData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "pt_term")
    private String ptTerm;

    @Column(name = "filename")
    private String filename;

    @Lob
    @Column(name = "audio_data", nullable = false)
    private byte[] data;

    @Column(name = "is_archived")
    private boolean archived = false;

    @Column(name = "created_ts", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdTs = LocalDateTime.now();

    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;

    @OneToOne(mappedBy = "audioData")
    private VocabItem vocabItem;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPtTerm() {
        return ptTerm;
    }

    public void setPtTerm(String ptTerm) {
        this.ptTerm = ptTerm;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    public VocabItem getVocabItem() {
        return vocabItem;
    }

    public void setVocabItem(VocabItem vocabItem) {
        this.vocabItem = vocabItem;
    }
}
