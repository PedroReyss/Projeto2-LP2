package model;

import java.util.Date;

// Single Responsibility Principle - Gerencia apenas dados de funcionário
public class Funcionario extends Pessoa {
    private String matricula;
    private String cargo;
    private double salarioBase;
    private Date dataContratacao;
    private String departamento;

    public Funcionario(String nome, String cpf, Date dataNascimento,
                       String cargo, double salarioBase, Date dataContratacao,
                       String departamento) {
        super(nome, cpf, dataNascimento);
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.dataContratacao = dataContratacao;
        this.departamento = departamento;
    }

    public double calcularSalarioTotal() {
        double bonus = 0;
        switch (this.cargo.toLowerCase()) {
            case "gerente": bonus = 2000; break;
            case "coordenador": bonus = 1000; break;
            case "analista": bonus = 500; break;
        }
        return this.salarioBase + bonus;
    }

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public Date getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(Date dataContratacao) { this.dataContratacao = dataContratacao; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}