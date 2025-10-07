package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Visitante")
@PrimaryKeyJoinColumn(name = "id")
public class Visitante extends Pessoa {

    @Column(name = "motivo_visita", nullable = false, length = 200)
    private String motivoVisita;

    @Column(name = "data_hora_entrada", nullable = false)
    private LocalDateTime dataHoraEntrada;

    @Column(name = "data_hora_saida")
    private LocalDateTime dataHoraSaida;

    @ManyToOne
    @JoinColumn(name = "id_funcionario_visitado")
    private Funcionario funcionarioVisitado;

    @ManyToOne
    @JoinColumn(name = "id_departamento_visitado")
    private Departamento departamentoVisitado;

    @Column(name = "numero_cracha", unique = true, length = 10)
    private String numeroCracha;

    // Construtor
    public Visitante() {}

    // Construtor funcionario
    public Visitante(String nome, String cpf, String motivoVisita,
                     LocalDateTime dataHoraEntrada, Funcionario funcionarioVisitado) {
        super(nome, cpf, "visitante");
        this.motivoVisita = motivoVisita;
        this.dataHoraEntrada = dataHoraEntrada;
        this.funcionarioVisitado = funcionarioVisitado;
    }

    // construtor departamento
    public Visitante(String nome, String cpf, String motivoVisita,
                     LocalDateTime dataHoraEntrada, Departamento departamentoVisitado) {
        super(nome, cpf, "visitante");
        this.motivoVisita = motivoVisita;
        this.dataHoraEntrada = dataHoraEntrada;
        this.departamentoVisitado = departamentoVisitado;
    }

    // Getters e Setters
    public String getMotivoVisita() { return motivoVisita; }
    public void setMotivoVisita(String motivoVisita) { this.motivoVisita = motivoVisita; }

    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) { this.dataHoraEntrada = dataHoraEntrada; }

    public LocalDateTime getDataHoraSaida() { return dataHoraSaida; }
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) { this.dataHoraSaida = dataHoraSaida; }

    public Funcionario getFuncionarioVisitado() { return funcionarioVisitado; }
    public void setFuncionarioVisitado(Funcionario funcionarioVisitado) { this.funcionarioVisitado = funcionarioVisitado; }

    public Departamento getDepartamentoVisitado() { return departamentoVisitado; }
    public void setDepartamentoVisitado(Departamento departamentoVisitado) { this.departamentoVisitado = departamentoVisitado; }

    public String getNumeroCracha() { return numeroCracha; }
    public void setNumeroCracha(String numeroCracha) { this.numeroCracha = numeroCracha; }
}