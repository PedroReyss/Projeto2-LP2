package com.p2lp2.service;

import com.p2lp2.model.Cargo;
import com.p2lp2.model.Funcionario;
import com.p2lp2.model.Ponto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PontoService {
    private EntityManager em;

    public PontoService(EntityManager em) {
        this.em = em;
    }

    public void baterPonto(Funcionario funcionario) {
        // Busca o último ponto do funcionário
        Ponto ultimoPonto = getUltimoPonto(funcionario);

        // Determina entrada/saída baseado no último ponto
        String tipo = determinarTipoPonto(ultimoPonto);

        // Cria e salva o novo ponto
        Ponto novoPonto = new Ponto(funcionario, LocalDateTime.now(), tipo);

        em.getTransaction().begin();
        em.persist(novoPonto);
        em.getTransaction().commit();

        System.out.println(funcionario.getNome() + " bateu ponto de " + tipo);
    }

    private Ponto getUltimoPonto(Funcionario funcionario) {
        try {
            return em.createQuery(
                            "SELECT p FROM Ponto p WHERE p.funcionario = :func " +
                                    "ORDER BY p.dataHora DESC", Ponto.class)
                    .setParameter("func", funcionario)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private String determinarTipoPonto(Ponto ultimoPonto) {
        if (ultimoPonto == null) {
            return "entrada"; // Primeiro ponto
        }

        // Alterna entre entrada e saída
        return ultimoPonto.getTipo().equals("entrada") ? "saida" : "entrada";
    }
}
