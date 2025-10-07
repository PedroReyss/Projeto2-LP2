package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FuncionarioTerceirizado")
@PrimaryKeyJoinColumn(name = "id")
public class FuncionarioTerceirizado extends Pessoa {

    @Column(name = "funcao", nullable = false, length = 100)
    private String funcao;

    @Column(name = "empresa_prestadora", nullable = false, length = 100)
    private String empresaPrestadora;

    @Column(name = "data_inicio_contrato", nullable = false)
    private LocalDate dataInicioContrato;

    @Column(name = "data_fim_contrato", nullable = false)
    private LocalDate dataFimContrato;

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private Funcionario responsavel;

    @ManyToOne
    @JoinColumn(name = "id_departamento") // Coluna direta na tabela
    private Departamento departamento;

    // Construtor
    public FuncionarioTerceirizado() {}

    public FuncionarioTerceirizado(String nome, String cpf, String funcao,
                                   String empresaPrestadora, LocalDate dataInicioContrato,
                                   LocalDate dataFimContrato, Funcionario responsavel) {
        super(nome, cpf, "terceirizado");
        this.funcao = funcao;
        this.empresaPrestadora = empresaPrestadora;
        this.dataInicioContrato = dataInicioContrato;
        this.dataFimContrato = dataFimContrato;
        this.responsavel = responsavel;
    }

    // Getters e Setters
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public String getEmpresaPrestadora() { return empresaPrestadora; }
    public void setEmpresaPrestadora(String empresaPrestadora) { this.empresaPrestadora = empresaPrestadora; }

    public LocalDate getDataInicioContrato() { return dataInicioContrato; }
    public void setDataInicioContrato(LocalDate dataInicioContrato) { this.dataInicioContrato = dataInicioContrato; }

    public LocalDate getDataFimContrato() { return dataFimContrato; }
    public void setDataFimContrato(LocalDate dataFimContrato) { this.dataFimContrato = dataFimContrato; }

    public Funcionario getResponsavel() { return responsavel; }
    public void setResponsavel(Funcionario responsavel) { this.responsavel = responsavel; }
}