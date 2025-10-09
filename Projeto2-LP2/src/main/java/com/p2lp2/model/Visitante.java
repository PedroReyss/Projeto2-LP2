package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("VISITANTE")
public class Visitante extends Pessoa {

    @Column(name = "motivo_visita", length = 200)
    private String motivoVisita;

    @Column(name = "data_hora_entrada")
    private LocalDateTime dataHoraEntrada;

    @Column(name = "data_hora_saida")
    private LocalDateTime dataHoraSaida;

    @ManyToOne
    @JoinColumn(name = "id_funcionario_visitado")
    private Pessoa funcionarioVisitado;

    @ManyToOne
    @JoinColumn(name = "id_departamento_visitado")
    private Departamento departamentoVisitado;

    public Visitante() {}

    public Visitante(String nome, String cpf, String motivoVisita,
                     LocalDateTime dataHoraEntrada, Pessoa funcionarioVisitado) {
        setNome(nome);
        setCpf(cpf);
        this.motivoVisita = motivoVisita;
        this.dataHoraEntrada = dataHoraEntrada;
        this.funcionarioVisitado = funcionarioVisitado;

        // Define o departamento visitado baseado no funcionário
        if (funcionarioVisitado != null && funcionarioVisitado.getDepartamento() != null) {
            this.departamentoVisitado = funcionarioVisitado.getDepartamento();
        }
    }

    // Método para ser chamado após a persistência
    public void gerarDadosVisitante() {
        if (getNumeroCracha() == null) {
            setNumeroCracha(gerarNumeroCracha());
        }
    }

    // Getters e Setters
    public String getMotivoVisita() { return motivoVisita; }
    public void setMotivoVisita(String motivoVisita) { this.motivoVisita = motivoVisita; }

    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) { this.dataHoraEntrada = dataHoraEntrada; }

    public LocalDateTime getDataHoraSaida() { return dataHoraSaida; }
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) { this.dataHoraSaida = dataHoraSaida; }

    public Pessoa getFuncionarioVisitado() { return funcionarioVisitado; }
    public void setFuncionarioVisitado(Pessoa funcionarioVisitado) { this.funcionarioVisitado = funcionarioVisitado; }

    public Departamento getDepartamentoVisitado() { return departamentoVisitado; }
    public void setDepartamentoVisitado(Departamento departamentoVisitado) { this.departamentoVisitado = departamentoVisitado; }

    @Override
    public String getTipoPessoa() {
        return "VISITANTE";
    }

    @Override
    public long getTempoPermanenciaMinutos() {
        if (dataHoraEntrada == null) return 0;
        if (dataHoraSaida == null) {
            return java.time.Duration.between(dataHoraEntrada, LocalDateTime.now()).toMinutes();
        }
        return java.time.Duration.between(dataHoraEntrada, dataHoraSaida).toMinutes();
    }
}