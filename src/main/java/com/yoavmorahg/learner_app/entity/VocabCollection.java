package com.yoavmorahg.learner_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "collection_id")
    private Long id;

    @Column(name = "collection_name", unique = true)
    private String name;

    @Column(name = "created_ts", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    public LocalDateTime createdTs = LocalDateTime.now();

    @Column(name = "updated_ts")
    public LocalDateTime updatedTs;

//    @ManyToMany(mappedBy = "collections")
    @ManyToMany
    @JoinTable(
            name = "collection_item",
            joinColumns = @JoinColumn(name = "vocab_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "vocab_item_id")
    )
    private Set<EnhancedVocabItem> vocabItems = new HashSet<>();


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

    public Set<EnhancedVocabItem> getVocabItems() {
        return vocabItems;
    }

    public void setVocabItems(Set<EnhancedVocabItem> vocabItems) {
        this.vocabItems = vocabItems;
    }

    public void addItem(EnhancedVocabItem item) {
        this.vocabItems.add(item);
        item.getCollections().add(this);
    }

    public void removerItem(EnhancedVocabItem item) {
        this.vocabItems.remove(item);
        item.getCollections().remove(this);
    }

}
