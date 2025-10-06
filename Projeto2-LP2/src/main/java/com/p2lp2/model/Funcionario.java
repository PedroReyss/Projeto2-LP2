package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Funcionario")
public class Funcionario extends EntidadeBase {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "CPF", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "matricula", nullable = false, unique = true, length = 5)
    private String matricula;

    // FK id_cargo
    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;

    // FK id_departamento
    @ManyToOne
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    @OneToMany(mappedBy = "funcionario")
    private List<FuncionarioProjeto> projetosFuncionarios = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario")
    private List<HistoricoCargo> historicoCargos = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario")
    private List<Ponto> registrosPonto = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario")
    private List<Atividade> atividades = new ArrayList<>();

    // Construtor
    public Funcionario() {}

    public Funcionario(String nome, String cpf, LocalDate dataNascimento,
                       String matricula, Cargo cargo, Departamento departamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.matricula = matricula;
        this.cargo = cargo;
        this.departamento = departamento;
    }

    // Getters & Setters

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public List<HistoricoCargo> getHistoricoCargos() { return historicoCargos; }
    public void setHistoricoCargos(List<HistoricoCargo> historicoCargos) { this.historicoCargos = historicoCargos; };

    public List<FuncionarioProjeto> getProjetosFuncionarios() { return projetosFuncionarios; }
    public void setProjetosFuncionarios(List<FuncionarioProjeto> projetosFuncionarios) { this.projetosFuncionarios = projetosFuncionarios; }

    public List<Ponto> getRegistrosPonto() { return registrosPonto; }
    public void setRegistrosPonto(List<Ponto> registrosPonto) { this.registrosPonto = registrosPonto; }

    public List<Atividade> getAtividades() { return atividades; }
    public void setAtividades(List<Atividade> atividades) { this.atividades = atividades; }
}