package com.p2lp2;

import com.p2lp2.model.*;
import com.p2lp2.service.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    // Variáveis para armazenar os objetos criados
    private static Funcionario joao;
    private static Funcionario maria;
    private static Terceirizado seguranca;
    private static Visitante visitante;
    private static Cargo analista;
    private static Cargo gerente;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("sistema-funcionarios");
            em = emf.createEntityManager();

            System.out.println("=== SISTEMA DE GESTÃO ORGANIZATEC ===\n");

            // 1. LIMPAR TABELAS EXISTENTES
            limparTabelas();

            // Services
            PessoaService pessoaService = new PessoaService(em);
            PontoService pontoService = new PontoService(em);
            RelatorioService relatorioService = new RelatorioService(em);

            // 2. CRIAR DADOS DE TESTE
            criarDadosTeste();

            // 3. TESTAR FUNCIONALIDADES
            testarFuncionalidades(pessoaService, pontoService);

            // 4. GERAR RELATÓRIOS
            gerarRelatorios(relatorioService);

            System.out.println("\n✅ DEMONSTRAÇÃO CONCLUÍDA!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    private static void limparTabelas() {
        System.out.println("🔄 LIMPANDO TABELAS EXISTENTES...");
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
        System.out.println("✅ Tabelas limpas!\n");
    }

    private static void criarDadosTeste() {
        System.out.println("1. CRIANDO DADOS DE TESTE...");

        em.getTransaction().begin();

        // Departamentos
        Departamento rh = new Departamento("Recursos Humanos");
        Departamento ti = new Departamento("Tecnologia da Informação");
        em.persist(rh);
        em.persist(ti);

        // Cargos
        analista = new Cargo("Analista", new BigDecimal("5000.00"));
        gerente = new Cargo("Gerente", new BigDecimal("8000.00"));
        em.persist(analista);
        em.persist(gerente);

        // Funcionário 1
        joao = new Funcionario("João Silva", "11111111111",
                LocalDate.of(1990, 5, 15), analista, ti);
        em.persist(joao);
        em.flush();
        joao.gerarDadosFuncionario();
        em.merge(joao);

        // Funcionário 2 (responsável)
        maria = new Funcionario("Maria Santos", "22222222222",
                LocalDate.of(1985, 8, 20), gerente, rh);
        em.persist(maria);
        em.flush();
        maria.gerarDadosFuncionario();
        em.merge(maria);

        // Terceirizado
        seguranca = new Terceirizado("Carlos Lima", "33333333333", "Segurança", "SegurMax",
                LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(10),
                maria, ti);
        em.persist(seguranca);
        em.flush();
        seguranca.gerarDadosTerceirizado();
        em.merge(seguranca);

        // Visitante
        visitante = new Visitante("Roberto Alves", "44444444444",
                "Reunião comercial", LocalDateTime.now().minusHours(2), maria);
        em.persist(visitante);
        em.flush();
        visitante.gerarDadosVisitante();
        em.merge(visitante);

        em.getTransaction().commit();

        System.out.println("✅ Dados criados!");
        System.out.println("   - João (ID: " + joao.getId() + ", Matrícula: " + joao.getMatricula() + ")");
        System.out.println("   - Maria (ID: " + maria.getId() + ", Matrícula: " + maria.getMatricula() + ")");
        System.out.println("   - Segurança (ID: " + seguranca.getId() + ", Crachá: " + seguranca.getNumeroCracha() + ")");
        System.out.println("   - Visitante (ID: " + visitante.getId() + ", Crachá: " + visitante.getNumeroCracha() + ")\n");
    }

    private static void testarFuncionalidades(PessoaService pessoaService,
                                              PontoService pontoService) {
        System.out.println("2. TESTANDO FUNCIONALIDADES...");

        System.out.println("--- BUSCANDO PESSOAS ---");
        System.out.println("✅ Pessoas carregadas: " + joao.getNome() + " (ID: " + joao.getId() + "), " +
                maria.getNome() + " (ID: " + maria.getId() + "), " +
                seguranca.getNome() + " (ID: " + seguranca.getId() + "), " +
                visitante.getNome() + " (ID: " + visitante.getId() + ")");

        // Testar bater ponto
        System.out.println("--- BATENDO PONTO ---");
        try {
            pontoService.baterPonto(joao);
            pontoService.baterPonto(joao);
            pontoService.baterPonto(seguranca);
        } catch (Exception e) {
            System.out.println("❌ Erro ao bater ponto: " + e.getMessage());
            e.printStackTrace();
        }

        // Testar registrar atividade
        System.out.println("--- REGISTRANDO ATIVIDADE ---");
        try {
            pessoaService.registrarAtividade(maria, "Entrevista com candidato", new BigDecimal("2.0"));
        } catch (Exception e) {
            System.out.println("❌ Erro ao registrar atividade: " + e.getMessage());
        }

        // Testar renovar contrato
        System.out.println("--- RENOVANDO CONTRATO ---");
        try {
            pessoaService.renovarContrato(seguranca, 6);
        } catch (Exception e) {
            System.out.println("❌ Erro ao renovar contrato: " + e.getMessage());
        }

        // Testar registrar saída do visitante
        System.out.println("--- REGISTRANDO SAÍDA DO VISITANTE ---");
        try {
            visitante.registrarSaida();
            em.getTransaction().begin();
            em.merge(visitante);
            em.getTransaction().commit();
            System.out.println("✅ Saída registrada para " + visitante.getNome());
        } catch (Exception e) {
            System.out.println("❌ Erro ao registrar saída: " + e.getMessage());
        }

        System.out.println("✅ Funcionalidades testadas!\n");
    }

    private static void gerarRelatorios(RelatorioService relatorioService) {
        System.out.println("3. GERANDO RELATÓRIOS...\n");

        System.out.println("--- SALÁRIOS TOTAIS ---");
        System.out.println("Analistas: R$ " + analista.getSalarioTotal());
        System.out.println("Gerentes: R$ " + gerente.getSalarioTotal());

        System.out.println("\n--- CIRCULAÇÃO DIÁRIA ---");
        relatorioService.circulacaoDiaria();

        System.out.println("\n--- VISITANTES ATIVOS ---");
        List<Visitante> visitantesAtivos = em.createQuery(
                        "SELECT v FROM Visitante v WHERE v.dataHoraSaida IS NULL", Visitante.class)
                .getResultList();

        if (visitantesAtivos.isEmpty()) {
            System.out.println("Nenhum visitante ativo no momento");
        } else {
            for (Visitante v : visitantesAtivos) {
                System.out.println(v.getNome() + " - " + v.getMotivoVisita() +
                        " (" + v.getTempoPermanenciaMinutos() + " minutos)");
            }
        }

        System.out.println("\n--- FUNCIONÁRIOS POR DEPARTAMENTO ---");
        relatorioService.funcionariosPorDepartamento();
    }
}