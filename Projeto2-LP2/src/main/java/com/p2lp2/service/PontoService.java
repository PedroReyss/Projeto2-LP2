package com.p2lp2.service;

import com.p2lp2.model.Pessoa;
import com.p2lp2.model.Ponto;
import jakarta.persistence.*;
import java.time.LocalDateTime;

public class PontoService {
    private EntityManager em;

    public PontoService(EntityManager em) {
        this.em = em;
    }

    public void baterPonto(Pessoa pessoa) {
        // Busca a pessoa no banco para garantir que está managed
        Pessoa pessoaManaged = em.find(Pessoa.class, pessoa.getId());
        if (pessoaManaged == null) {
            System.out.println("❌ Pessoa não encontrada: ID " + pessoa.getId());
            return;
        }

        Ponto ultimoPonto = getUltimoPonto(pessoaManaged);
        String tipo = determinarTipoPonto(ultimoPonto);

        System.out.println("🕒 " + pessoaManaged.getNome() + " - último ponto: " +
                (ultimoPonto != null ? ultimoPonto.getTipo() : "Nenhum") +
                " -> novo: " + tipo);

        Ponto novoPonto = new Ponto();
        novoPonto.setPessoa(pessoaManaged);
        novoPonto.setDataHora(LocalDateTime.now());
        novoPonto.setTipo(tipo);

        try {
            em.getTransaction().begin();
            em.persist(novoPonto);
            em.getTransaction().commit();
            System.out.println("✅ " + pessoaManaged.getNome() + " bateu ponto de " + tipo);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("❌ Erro ao bater ponto: " + e.getMessage());
            throw e;
        }
    }

    private Ponto getUltimoPonto(Pessoa pessoa) {
        try {
            return em.createQuery(
                            "SELECT p FROM Ponto p WHERE p.pessoa = :pessoa ORDER BY p.dataHora DESC",
                            Ponto.class)
                    .setParameter("pessoa", pessoa)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private String determinarTipoPonto(Ponto ultimoPonto) {
        if (ultimoPonto == null) return "entrada";
        return ultimoPonto.getTipo().equals("entrada") ? "saida" : "entrada";
    }
}