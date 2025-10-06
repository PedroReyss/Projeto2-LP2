package com.p2lp2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Historico_Cargo")
public class HistoricoCargo extends EntidadeBase {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    // Construtor
    public HistoricoCargo() {}

    public HistoricoCargo(Funcionario funcionario, Cargo cargo, LocalDateTime dataInicio) {
        this.funcionario = funcionario;
        this.cargo = cargo;
        this.dataInicio = dataInicio;
    }

    // Getters & Setters
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
}
