package com.p2lp2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pessoa", discriminatorType = DiscriminatorType.STRING)
public abstract class Pessoa extends EntidadeBase {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "CPF", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "matricula", unique = true, length = 5)
    private String matricula;

    @Column(name = "numero_cracha", unique = true, length = 10)
    private String numeroCracha;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    @OneToMany(mappedBy = "pessoa")
    private List<Ponto> registrosPonto = new ArrayList<>();

    public Pessoa() {}

    // Método para gerar matrícula baseada no ID (será chamado após persist)
    protected String gerarMatricula() {
        if (getId() != null) {
            return String.format("%05d", getId()); // Usa o ID do banco
        }
        return null;
    }

    // Método para gerar número do crachá baseado no ID
    protected String gerarNumeroCracha() {
        if (getId() != null) {
            return "CR" + String.format("%04d", getId()); // Usa o ID do banco
        }
        return null;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNumeroCracha() { return numeroCracha; }
    public void setNumeroCracha(String numeroCracha) { this.numeroCracha = numeroCracha; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public List<Ponto> getRegistrosPonto() { return registrosPonto; }
    public void setRegistrosPonto(List<Ponto> registrosPonto) { this.registrosPonto = registrosPonto; }

    // Métodos abstratos
    public abstract String getTipoPessoa();

    public long getTempoPermanenciaMinutos() { return 0; }
}