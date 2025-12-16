package com.yoavmorahg.learner_app.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class EnhancedVocabItemDto {

    private Long id;

    private boolean skip = false;

    private String enTerm;

    private String ptTermMasculine;

    private String ptTermFeminine;

    private String termType;

    private String verbRule;

    private String gender;

    private String notes;


    private String enTermNormalized;

    private String ptTermMasculineNormalized;

    private String ptTermFeminineNormalized;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
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

    public String getEnTermNormalized() {
        return enTermNormalized;
    }

    public void setEnTermNormalized(String enTermNormalized) {
        this.enTermNormalized = enTermNormalized;
    }

    public String getPtTermMasculineNormalized() {
        return ptTermMasculineNormalized;
    }

    public void setPtTermMasculineNormalized(String ptTermMasculineNormalized) {
        this.ptTermMasculineNormalized = ptTermMasculineNormalized;
    }

    public String getPtTermFeminineNormalized() {
        return ptTermFeminineNormalized;
    }

    public void setPtTermFeminineNormalized(String ptTermFeminineNormalized) {
        this.ptTermFeminineNormalized = ptTermFeminineNormalized;
    }

    public EnhancedVocabItemDto() {

    }

    public EnhancedVocabItemDto(Long id, String enTerm, String ptTermMasculine, String ptTermFeminine, String termType, String verbRule, String gender, String notes) {
        this.id = id;
        this.skip = false;
        this.enTerm = enTerm;
        this.ptTermMasculine = ptTermMasculine;
        this.ptTermFeminine = ptTermFeminine;
        this.termType = termType;
        this.verbRule = verbRule;
        this.gender = gender;
        this.notes = notes;
        if (this.enTerm != null) {
            this.enTermNormalized = StringUtils.stripAccents(this.enTerm);
        }
        if (this.ptTermMasculine != null) {
            this.ptTermMasculineNormalized = StringUtils.stripAccents(this.ptTermMasculine);
        }
        if (this.ptTermFeminine != null) {
            this.ptTermFeminineNormalized = StringUtils.stripAccents(this.ptTermFeminine);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnhancedVocabItemDto that = (EnhancedVocabItemDto) o;
        return Objects.equals(enTerm, that.enTerm) && Objects.equals(ptTermMasculine, that.ptTermMasculine) && Objects.equals(ptTermFeminine, that.ptTermFeminine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enTerm, ptTermMasculine, ptTermFeminine);
    }
}
