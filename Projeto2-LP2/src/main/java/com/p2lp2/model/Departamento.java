package com.p2lp2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Departamento")
public class Departamento extends EntidadeBase {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "departamento")
    private List<Funcionario> funcionarios = new ArrayList<>();

    // Construtor
    public Departamento() {
    }

    public Departamento(String nome) {
        this.nome = nome;
    }

    // Getters & Setters

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
