package com.p2lp2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cargo")
public class Cargo extends EntidadeBase {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "SalarioBase", nullable = false)
    private BigDecimal salarioBase;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionarios = new ArrayList<>();

    @OneToMany(mappedBy = "cargo")
    private List<HistoricoCargo> historicoCargos = new ArrayList<>();

    // Construtor
    public Cargo() {}

    public Cargo(String nome, BigDecimal salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    // Métodos
    public BigDecimal getSalarioTotal() {
        if (funcionarios == null || funcionarios.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return salarioBase.multiply(new BigDecimal(funcionarios.size()));
    }

    // Getters & Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getSalarioBase() { return salarioBase; }
    public void setSalarioBase(BigDecimal salarioBase) { this.salarioBase = salarioBase; }

    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public void setFuncionarios(List<Funcionario> funcionarios) { this.funcionarios = funcionarios; }

    public List<HistoricoCargo> getHistoricoCargos() { return historicoCargos; }
    public void setHistoricoCargos(List<HistoricoCargo> historicoCargos) { this.historicoCargos = historicoCargos; }
}