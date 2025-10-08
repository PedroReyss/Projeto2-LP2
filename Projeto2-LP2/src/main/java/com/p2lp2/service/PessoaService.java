package com.p2lp2.service;

import com.p2lp2.model.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PessoaService {
    private EntityManager em;

    public PessoaService(EntityManager em) {
        this.em = em;
    }

    public void registrarAtividade(Pessoa pessoa, String descricao, BigDecimal horasTrabalhadas) {
        if (pessoa instanceof Funcionario) {
            em.getTransaction().begin();
            Atividade atividade = new Atividade(pessoa, descricao, java.time.LocalDateTime.now(), horasTrabalhadas);
            em.persist(atividade);
            em.getTransaction().commit();
            System.out.println("✅ Atividade registrada para " + pessoa.getNome());
        } else {
            System.out.println("❌ Apenas funcionários podem registrar atividades");
        }
    }

    public void renovarContrato(Pessoa pessoa, int meses) {
        if (pessoa instanceof Terceirizado) {
            Terceirizado terceirizado = (Terceirizado) pessoa;
            em.getTransaction().begin();
            LocalDate novaDataFim = terceirizado.getDataFimContrato().plusMonths(meses);
            terceirizado.setDataFimContrato(novaDataFim);
            em.merge(terceirizado);
            em.getTransaction().commit();
            System.out.println("✅ Contrato de " + pessoa.getNome() + " renovado até " + novaDataFim);
        } else {
            System.out.println("❌ Apenas terceirizados podem ter contratos renovados");
        }
    }
}