package model;

import java.util.Date;

    public class FuncionarioTerceirizado extends Pessoa {
    private String empresaPrestadora;
    private Date periodoInicio;
    private Date periodoFim;
    private Funcionario responsavel;
    private Departamento departamento;
    private Date entrada;
    private Date saida;

    public FuncionarioTerceirizado(String nome, String cpf, Date dataNascimento, String empresaPrestadora, Date periodoInicio, Date periodoFim, Funcionario responsavel, Departamento departamento) {
        super(nome, cpf, dataNascimento);
        this.empresaPrestadora = empresaPrestadora;
        this.periodoInicio = periodoInicio;
        this.periodoFim = periodoFim;
        this.responsavel = responsavel;
        this.departamento = departamento;
    }

    public void renovarVinculo(Date novoFim) {
        this.periodoFim = novoFim;
    }

    public void registrarEntrada() {
        this.entrada = new Date();
    }

    public void registrarSaida() {
        this.saida = new Date();
    }

    // Getters e Setters

    public String getEmpresaPrestadora() {
        return empresaPrestadora;
    }

    public void setEmpresaPrestadora(String empresaPrestadora) {
        this.empresaPrestadora = empresaPrestadora;
    }

    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public Date getPeriodoFim() {
        return periodoFim;
    }

    public void setPeriodoFim(Date periodoFim) {
        this.periodoFim = periodoFim;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    public Date getSaida() {
        return saida;
    }

    public void setSaida(Date saida) {
        this.saida = saida;
    }
}