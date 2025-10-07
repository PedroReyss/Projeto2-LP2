package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ponto")
public class Ponto extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa pessoa;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo; // "entrada" || "saida"

    // Construtores
    public Ponto() {}

    public Ponto(Pessoa pessoa, LocalDateTime dataHora, String tipo) {
        this.pessoa = pessoa;
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    // Getters e Setters
    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}