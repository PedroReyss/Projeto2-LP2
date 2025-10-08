package com.p2lp2;

import com.p2lp2.model.*;
import com.p2lp2.service.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("sistema-funcionarios");
            em = emf.createEntityManager();

            System.out.println("=== SISTEMA DE GESTÃO ORGANIZATEC ===\n");

            // Serviços
            FuncionarioService funcService = new FuncionarioService(em);
            CargoService cargoService = new CargoService(em);
            PontoService pontoService = new PontoService(em);
            RelatorioService relatorioService = new RelatorioService(em);

            // 1. CRIAR DADOS DE TESTE
            criarDadosTeste();

            // 2. TESTAR FUNCIONALIDADES
            testarFuncionalidades(funcService, cargoService, pontoService);

            // 3. GERAR RELATÓRIOS
            gerarRelatorios(relatorioService, cargoService);

            System.out.println("\n=== DEMONSTRAÇÃO CONCLUÍDA ===");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    private static void criarDadosTeste() {
        System.out.println("1. CRIANDO DADOS DE TESTE...");

        em.getTransaction().begin();

        // Departamentos
        Departamento rh = new Departamento("Recursos Humanos");
        Departamento ti = new Departamento("Tecnologia da Informação");
        Departamento financeiro = new Departamento("Financeiro");
        em.persist(rh);
        em.persist(ti);
        em.persist(financeiro);

        // Cargos
        Cargo analista = new Cargo("Analista", new BigDecimal("5000.00"));
        Cargo gerente = new Cargo("Gerente", new BigDecimal("8000.00"));
        Cargo estagiario = new Cargo("Estagiário", new BigDecimal("1500.00"));
        em.persist(analista);
        em.persist(gerente);
        em.persist(estagiario);

        // Funcionários próprios
        Funcionario joao = new Funcionario("João Silva", "12345678901",
                LocalDate.of(1990, 5, 15), "00001", analista, ti);
        Funcionario maria = new Funcionario("Maria Santos", "98765432100",
                LocalDate.of(1985, 8, 20), "00002", gerente, rh);
        Funcionario pedro = new Funcionario("Pedro Costa", "45612378900",
                LocalDate.of(1995, 3, 10), "00003", estagiario, ti);
        em.persist(joao);
        em.persist(maria);
        em.persist(pedro);

        // Funcionários terceirizados
        FuncionarioTerceirizado terceirizado1 = new FuncionarioTerceirizado(
                "Carlos Lima", "11122233344", "Segurança", "SegurMax",
                LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(10),
                maria, ti);
        FuncionarioTerceirizado terceirizado2 = new FuncionarioTerceirizado(
                "Ana Souza", "55566677788", "Limpeza", "CleanService",
                LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(6),
                joao, rh);
        em.persist(terceirizado1);
        em.persist(terceirizado2);

        // Visitantes
        Visitante visitante1 = new Visitante("Roberto Alves", "99988877766",
                "Reunião comercial", LocalDateTime.now().minusHours(2), maria);
        Visitante visitante2 = new Visitante("Fernanda Oliveira", "44433322211",
                "Entrevista de emprego", LocalDateTime.now().minusHours(1), ti);
        em.persist(visitante1);
        em.persist(visitante2);

        em.getTransaction().commit();
        System.out.println("✅ Dados de teste criados com sucesso!\n");
    }

    private static void testarFuncionalidades(FuncionarioService funcService,
                                              CargoService cargoService,
                                              PontoService pontoService) {
        System.out.println("2. TESTANDO FUNCIONALIDADES...");

        // Buscar funcionários
        Funcionario joao = em.find(Funcionario.class, 1);
        FuncionarioTerceirizado carlos = em.find(FuncionarioTerceirizado.class, 4);

        // Testar bater ponto
        pontoService.baterPonto(joao);
        pontoService.baterPonto(joao);
        pontoService.baterPonto(carlos);

        // Testar registrar atividade
        funcService.registrarAtividade(joao, "Desenvolvimento do módulo de relatórios",
                new BigDecimal("4.5"));

        System.out.println("✅ Funcionalidades testadas com sucesso!\n");
    }

    private static void gerarRelatorios(RelatorioService relatorioService,
                                        CargoService cargoService) {
        System.out.println("3. GERANDO RELATÓRIOS...\n");

        // Relatório de funcionários por departamento
        System.out.println("--- FUNCIONÁRIOS POR DEPARTAMENTO ---");
        relatorioService.funcionariosPorDepartamento();

        // Relatório de folha de pagamento
        System.out.println("\n--- FOLHA DE PAGAMENTO ---");
        BigDecimal folhaTotal = cargoService.calcularFolhaPagamentoTotal();
        System.out.println("Total: R$ " + folhaTotal);

        // Relatório de visitantes
        System.out.println("\n--- VISITANTES ATIVOS ---");
        relatorioService.visitantesAtivos();

        // Relatório de terceirizados
        System.out.println("\n--- FUNCIONÁRIOS TERCEIRIZADOS ---");
        relatorioService.terceirizadosPorResponsavel();
    }
}