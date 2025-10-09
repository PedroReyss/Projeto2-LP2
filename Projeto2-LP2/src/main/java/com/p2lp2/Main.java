package com.p2lp2;

import com.p2lp2.service.MenuPrincipal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("sistema-funcionarios");
            em = emf.createEntityManager();

            System.out.println("=== SISTEMA ORGANIZATEC INICIADO ===");

            limparBanco(em);

            criarDadosIniciais(em);

            MenuPrincipal menu = new MenuPrincipal(em);
            menu.exibirMenu();

        } catch (Exception e) {
            System.err.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    private static void limparBanco(EntityManager em) {
        System.out.println("🔄 Limpando banco de dados...");
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Ponto").executeUpdate();
        em.createQuery("DELETE FROM Atividade").executeUpdate();
        em.createQuery("DELETE FROM HistoricoCargo").executeUpdate();
        em.createQuery("DELETE FROM FuncionarioProjeto").executeUpdate();
        em.createQuery("DELETE FROM Pessoa").executeUpdate();
        em.createQuery("DELETE FROM Cargo").executeUpdate();
        em.createQuery("DELETE FROM Departamento").executeUpdate();
        em.createQuery("DELETE FROM Projeto").executeUpdate();

        em.getTransaction().commit();
        System.out.println("✅ Banco limpo com sucesso!\n");
    }

    private static void criarDadosIniciais(EntityManager em) {
        System.out.println("🏗️ Criando dados iniciais...");
        em.getTransaction().begin();

        // Departamentos
        com.p2lp2.model.Departamento rh = new com.p2lp2.model.Departamento("Recursos Humanos");
        com.p2lp2.model.Departamento ti = new com.p2lp2.model.Departamento("Tecnologia da Informação");
        em.persist(rh);
        em.persist(ti);

        // Cargos
        com.p2lp2.model.Cargo analista = new com.p2lp2.model.Cargo("Analista", new java.math.BigDecimal("5000.00"));
        com.p2lp2.model.Cargo gerente = new com.p2lp2.model.Cargo("Gerente", new java.math.BigDecimal("8000.00"));
        com.p2lp2.model.Cargo estagiario = new com.p2lp2.model.Cargo("Estagiário", new java.math.BigDecimal("1500.00"));
        em.persist(analista);
        em.persist(gerente);
        em.persist(estagiario);

        em.getTransaction().commit();
        System.out.println("✅ Dados iniciais criados!\n");
    }
}