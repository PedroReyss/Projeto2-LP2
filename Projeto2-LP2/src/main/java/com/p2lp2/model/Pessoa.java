package com.p2lp2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends EntidadeBase {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "CPF", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @OneToMany(mappedBy = "pessoa")
    private List<Ponto> registrosPonto = new ArrayList<>();

    // Construtor
    public Pessoa() {}

    public Pessoa(String nome, String cpf, String tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<Ponto> getRegistrosPonto() { return registrosPonto; }
    public void setRegistrosPonto(List<Ponto> registrosPonto) { this.registrosPonto = registrosPonto; }
}