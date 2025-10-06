package model;

public class Cargo {
    private String nome;
    private double salarioBase;
    private double bonus;

    public Cargo(String nome, double salarioBase, double bonus) {
        this.nome = nome;
        this.salarioBase = salarioBase;
        this.bonus = bonus;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}