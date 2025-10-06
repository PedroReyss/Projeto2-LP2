package model;

public class Departamento {
    private String nome;
    private Funcionario responsavel;

    public Departamento(String nome, Funcionario responsavel) {
        this.nome = nome;
        this.responsavel = responsavel;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }
}