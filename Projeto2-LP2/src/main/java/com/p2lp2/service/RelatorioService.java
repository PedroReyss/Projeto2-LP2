package com.p2lp2.service;

import com.p2lp2.model.*;
import javax.persistence.*;
import java.util.List;

public class RelatorioService {
    private EntityManager em;

    public RelatorioService(EntityManager em) {
        this.em = em;
    }

    public void funcionariosPorDepartamento() {
        List<Departamento> departamentos = em.createQuery(
                "SELECT d FROM Departamento d", Departamento.class).getResultList();

        for (Departamento depto : departamentos) {
            System.out.println(depto.getNome() + ": " +
                    depto.getFuncionarios().size() + " funcionários, " +
                    depto.getTerceirizados().size() + " terceirizados");
        }
    }

    public void visitantesAtivos() {
        List<Visitante> visitantes = em.createQuery(
                "SELECT v FROM Visitante v WHERE v.dataHoraSaida IS NULL",
                Visitante.class).getResultList();

        for (Visitante v : visitantes) {
            System.out.println(v.getNome() + " - " + v.getMotivoVisita() +
                    " (desde " + v.getDataHoraEntrada() + ")");
        }
    }

    public void terceirizadosPorResponsavel() {
        List<Funcionario> responsaveis = em.createQuery(
                "SELECT f FROM Funcionario f WHERE SIZE(f.terceirizadosResponsaveis) > 0",
                Funcionario.class).getResultList();

        for (Funcionario resp : responsaveis) {
            System.out.println(resp.getNome() + " gerencia " +
                    resp.getTerceirizadosResponsaveis().size() + " terceirizados");
        }
    }
}