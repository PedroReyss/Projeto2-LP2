package com.p2lp2.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Construtor
    public EntidadeBase() {}

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

}