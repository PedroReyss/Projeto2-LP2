package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("FUNCIONARIO")
public class Funcionario extends Pessoa {

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    public Funcionario() {}

    public Funcionario(String nome, String cpf, LocalDate dataNascimento, Cargo cargo, Departamento departamento) {
        setNome(nome);
        setCpf(cpf);
        this.dataNascimento = dataNascimento;
        this.cargo = cargo;
        setDepartamento(departamento);
    }

    // Método para ser chamado após a persistência
    public void gerarDadosFuncionario() {
        if (getMatricula() == null) {
            setMatricula(gerarMatricula());
        }
    }

    // Getters e Setters
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    @Override
    public String getTipoPessoa() {
        return "FUNCIONARIO";
    }
}