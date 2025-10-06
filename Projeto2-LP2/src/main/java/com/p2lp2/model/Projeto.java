package com.p2lp2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Projeto")
public class Projeto extends EntidadeBase {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_termino")
    private LocalDateTime dataTermino;

    @OneToMany(mappedBy = "projeto")
    private List<FuncionarioProjeto> funcionariosProjetos = new ArrayList<>();

    // Construtor
    public Projeto() {
    }

    public Projeto(String nome) {
        this.nome = nome;
    }

    // Getters & Setters

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataTermino() { return dataTermino; }
    public void setDataTermino(LocalDateTime dataTermino) { this.dataTermino = dataTermino; }

    public List<FuncionarioProjeto> getFuncionariosProjetos() {
        return funcionariosProjetos;
    }
    public void setFuncionariosProjetos(List<FuncionarioProjeto> funcionariosProjetos) {
        this.funcionariosProjetos = funcionariosProjetos;
    }


}
