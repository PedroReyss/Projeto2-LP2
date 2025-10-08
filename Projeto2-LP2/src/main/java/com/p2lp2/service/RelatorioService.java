package com.p2lp2.service;

import com.p2lp2.model.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class RelatorioService {
    private EntityManager em;

    public RelatorioService(EntityManager em) {
        this.em = em;
    }

    public void circulacaoDiaria() {
        Long totalFuncionarios = em.createQuery(
                "SELECT COUNT(f) FROM Funcionario f", Long.class).getSingleResult();

        Long totalTerceirizados = em.createQuery(
                "SELECT COUNT(t) FROM Terceirizado t", Long.class).getSingleResult();

        Long totalVisitantesHoje = em.createQuery(
                "SELECT COUNT(v) FROM Visitante v WHERE CAST(v.dataHoraEntrada AS date) = CAST(CURRENT_TIMESTAMP AS date)",
                Long.class).getSingleResult();

        System.out.println("🏢 Circulação Diária:");
        System.out.println("   • Funcionários próprios: " + totalFuncionarios);
        System.out.println("   • Terceirizados: " + totalTerceirizados);
        System.out.println("   • Visitantes hoje: " + totalVisitantesHoje);
        System.out.println("   • Total: " + (totalFuncionarios + totalTerceirizados + totalVisitantesHoje));
    }

    public void funcionariosPorDepartamento() {
        List<Departamento> departamentos = em.createQuery(
                "SELECT d FROM Departamento d", Departamento.class).getResultList();

        if (departamentos.isEmpty()) {
            System.out.println("Nenhum departamento cadastrado");
            return;
        }

        for (Departamento depto : departamentos) {
            Long qtdFuncionarios = em.createQuery(
                            "SELECT COUNT(f) FROM Funcionario f WHERE f.departamento = :depto",
                            Long.class)
                    .setParameter("depto", depto)
                    .getSingleResult();

            Long qtdTerceirizados = em.createQuery(
                            "SELECT COUNT(t) FROM Terceirizado t WHERE t.departamento = :depto",
                            Long.class)
                    .setParameter("depto", depto)
                    .getSingleResult();

            System.out.println("📊 " + depto.getNome() + ":");
            System.out.println("   • Funcionários: " + qtdFuncionarios);
            System.out.println("   • Terceirizados: " + qtdTerceirizados);
            System.out.println("   • Total: " + (qtdFuncionarios + qtdTerceirizados));
        }
    }
}