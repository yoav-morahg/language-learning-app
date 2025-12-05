package com.yoavmorahg.learner_app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name="vocab_collection")
@NoArgsConstructor
public class VocabCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "collection_name", unique = true)
    private String name;

    @Column(name = "created_ts", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    public LocalDateTime createdTs = LocalDateTime.now();

    @Column(name = "updated_ts")
    public LocalDateTime updatedTs;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<VocabItem> vocabItems = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<VocabItem> getVocabItems() {
        return vocabItems;
    }

    public void setVocabItems(Set<VocabItem> vocabItems) {
        this.vocabItems = vocabItems;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabCollection that = (VocabCollection) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(vocabItems, that.vocabItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vocabItems);
    }
}
