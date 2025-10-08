package com.p2lp2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("TERCEIRIZADO")
public class Terceirizado extends Pessoa {

    @Column(name = "funcao", length = 100)
    private String funcao;

    @Column(name = "empresa_prestadora", length = 100)
    private String empresaPrestadora;

    @Column(name = "data_inicio_contrato")
    private LocalDate dataInicioContrato;

    @Column(name = "data_fim_contrato")
    private LocalDate dataFimContrato;

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private Pessoa responsavel;

    public Terceirizado() {}

    public Terceirizado(String nome, String cpf, String funcao,
                        String empresaPrestadora, LocalDate dataInicioContrato,
                        LocalDate dataFimContrato, Pessoa responsavel, Departamento departamento) {
        setNome(nome);
        setCpf(cpf);
        this.funcao = funcao;
        this.empresaPrestadora = empresaPrestadora;
        this.dataInicioContrato = dataInicioContrato;
        this.dataFimContrato = dataFimContrato;
        this.responsavel = responsavel;
        setDepartamento(departamento);
    }

    // Método para ser chamado após a persistência
    public void gerarDadosTerceirizado() {
        if (getNumeroCracha() == null) {
            setNumeroCracha(gerarNumeroCracha());
        }
    }

    // Getters e Setters
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public String getEmpresaPrestadora() { return empresaPrestadora; }
    public void setEmpresaPrestadora(String empresaPrestadora) { this.empresaPrestadora = empresaPrestadora; }

    public LocalDate getDataInicioContrato() { return dataInicioContrato; }
    public void setDataInicioContrato(LocalDate dataInicioContrato) { this.dataInicioContrato = dataInicioContrato; }

    public LocalDate getDataFimContrato() { return dataFimContrato; }
    public void setDataFimContrato(LocalDate dataFimContrato) { this.dataFimContrato = dataFimContrato; }

    public Pessoa getResponsavel() { return responsavel; }
    public void setResponsavel(Pessoa responsavel) { this.responsavel = responsavel; }

    @Override
    public String getTipoPessoa() {
        return "TERCEIRIZADO";
    }
}