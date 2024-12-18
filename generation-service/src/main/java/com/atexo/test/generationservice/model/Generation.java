package com.atexo.test.generationservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GENERATION")
public class Generation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String generatedValue;

    public Generation() {
    }

    public Generation(Long id, String generatedValue) {
        this.id = id;
        this.generatedValue = generatedValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneratedValue() {
        return generatedValue;
    }

    public void setGeneratedValue(String generatedValue) {
        this.generatedValue = generatedValue;
    }
}