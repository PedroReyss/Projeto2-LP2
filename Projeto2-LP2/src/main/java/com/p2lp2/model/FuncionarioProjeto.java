package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Funcionario_Projeto")
public class FuncionarioProjeto extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_projeto", nullable = false)
    private Projeto projeto;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    // Construtores
    public FuncionarioProjeto() {}

    public FuncionarioProjeto(Funcionario funcionario, Projeto projeto, LocalDate dataInicio) {
        this.funcionario = funcionario;
        this.projeto = projeto;
        this.dataInicio = dataInicio;
    }

    // Getters e Setters
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
}