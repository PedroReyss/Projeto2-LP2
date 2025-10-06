package model;

import java.util.Date;

public class Visitante extends Pessoa {
    private String documento;
    private String motivoVisita;
    private Date horaEntrada;
    private Date horaSaida;
    private Funcionario funcionarioVisitado;
    private Departamento departamentoVisitado;
    private String cracha;

    public Visitante(String nome, String cpf, Date dataNascimento, String documento, String motivoVisita, Funcionario funcionarioVisitado, Departamento departamentoVisitado) {
        super(nome, cpf, dataNascimento);
        this.documento = documento;
        this.motivoVisita = motivoVisita;
        this.funcionarioVisitado = funcionarioVisitado;
        this.departamentoVisitado = departamentoVisitado;
        this.horaEntrada = new Date();
        this.cracha = gerarCracha();
    }

    private String gerarCracha() {
        return "Crachá: " + getNome() + " - " + documento;
    }

    public void registrarSaida() {
        this.horaSaida = new Date();
    }

    // Getters e Setters

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getMotivoVisita() {
        return motivoVisita;
    }

    public void setMotivoVisita(String motivoVisita) {
        this.motivoVisita = motivoVisita;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Date horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Funcionario getFuncionarioVisitado() {
        return funcionarioVisitado;
    }

    public void setFuncionarioVisitado(Funcionario funcionarioVisitado) {
        this.funcionarioVisitado = funcionarioVisitado;
    }

    public Departamento getDepartamentoVisitado() {
        return departamentoVisitado;
    }

    public void setDepartamentoVisitado(Departamento departamentoVisitado) {
        this.departamentoVisitado = departamentoVisitado;
    }

    public String getCracha() {
        return cracha;
    }

    public void setCracha(String cracha) {
        this.cracha = cracha;
    }
}