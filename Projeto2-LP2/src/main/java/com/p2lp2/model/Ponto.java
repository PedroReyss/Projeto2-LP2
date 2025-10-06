package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ponto")
public class Ponto extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo; // "entrada" || "saida"

    // Construtores
    public Ponto() {}

    public Ponto(Funcionario funcionario, LocalDateTime dataHora, String tipo) {
        this.funcionario = funcionario;
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    // Getters e Setters
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}