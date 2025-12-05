package com.yoavmorahg.learner_app.entity;

import java.util.Objects;

public class VocabItemDto {

    private Long id;

    private String sideA;

    private String sideB;

    private Long audioDataId;

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

    public Long getAudioDataId() {
        return audioDataId;
    }

    public void setAudioDataId(Long audioDataId) {
        this.audioDataId = audioDataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabItemDto that = (VocabItemDto) o;
        return Objects.equals(id, that.id) && Objects.equals(sideA, that.sideA) && Objects.equals(sideB, that.sideB) && Objects.equals(audioDataId, that.audioDataId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sideA, sideB, audioDataId);
    }

    @Override
    public String toString() {
        return "VocabItemDto{" +
                "id=" + id +
                ", sideA='" + sideA + '\'' +
                ", sideB='" + sideB + '\'' +
                ", audioDataId=" + audioDataId +
                '}';
    }
}
