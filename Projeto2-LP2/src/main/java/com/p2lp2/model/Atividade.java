package com.p2lp2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Atividade")
public class Atividade extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Pessoa funcionario;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "horas_trabalhadas", precision = 4, scale = 2)
    private BigDecimal horasTrabalhadas;

    // Construtores
    public Atividade() {}

    public Atividade(Pessoa funcionario, String descricao, LocalDateTime dataRegistro) {
        this.funcionario = funcionario;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
    }

    public Atividade(Pessoa funcionario, String descricao, LocalDateTime dataRegistro, BigDecimal horasTrabalhadas) {
        this.funcionario = funcionario;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
        this.horasTrabalhadas = horasTrabalhadas;
    }

    // Getters e Setters
    public Pessoa getFuncionario() { return funcionario; }
    public void setFuncionario(Pessoa funcionario) { this.funcionario = funcionario; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDateTime dataRegistro) { this.dataRegistro = dataRegistro; }

    public BigDecimal getHorasTrabalhadas() { return horasTrabalhadas; }
    public void setHorasTrabalhadas(BigDecimal horasTrabalhadas) { this.horasTrabalhadas = horasTrabalhadas; }
}