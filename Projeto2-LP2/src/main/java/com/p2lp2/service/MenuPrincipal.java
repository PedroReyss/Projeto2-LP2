package com.p2lp2.service;

import com.p2lp2.model.*;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private EntityManager em;
    private Scanner scanner;
    private InputService inputService;
    private PessoaService pessoaService;
    private PontoService pontoService;
    private RelatorioService relatorioService;
    private PDFExportService pdfExportService;

    public MenuPrincipal(EntityManager em) {
        this.em = em;
        this.scanner = new Scanner(System.in);
        this.inputService = new InputService(scanner, em);
        this.pessoaService = new PessoaService(em);
        this.pontoService = new PontoService(em);
        this.relatorioService = new RelatorioService(em);
        this.pdfExportService = new PDFExportService();
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA ORGANIZATEC ===");
            System.out.println("1. Cadastrar Pessoa");
            System.out.println("2. Bater Ponto");
            System.out.println("3. Registrar Atividade");
            System.out.println("4. Renovar Contrato");
            System.out.println("5. Gerar Relatórios");
            System.out.println("6. Exportar Relatórios para PDF");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarPessoa();
                    break;
                case 2:
                    baterPonto();
                    break;
                case 3:
                    registrarAtividade();
                    break;
                case 4:
                    renovarContrato();
                    break;
                case 5:
                    gerarRelatorios();
                    break;
                case 6:
                    exportarRelatoriosPDF();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarPessoa() {
        System.out.println("\n--- CADASTRAR PESSOA ---");
        System.out.println("1. Funcionário");
        System.out.println("2. Terceirizado");
        System.out.println("3. Visitante");
        System.out.print("Tipo: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        try {
            Pessoa pessoa = inputService.criarPessoa(tipo);
            if (pessoa != null) {
                em.getTransaction().begin();
                em.persist(pessoa);
                em.getTransaction().commit();
                System.out.println("✅ " + pessoa.getTipoPessoa() + " cadastrado(a) com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void baterPonto() {
        System.out.println("\n--- BATER PONTO ---");
        Pessoa pessoa = inputService.selecionarPessoa();
        if (pessoa != null) {
            pontoService.baterPonto(pessoa);
        }
    }

    private void registrarAtividade() {
        System.out.println("\n--- REGISTRAR ATIVIDADE ---");
        Pessoa pessoa = inputService.selecionarPessoa();
        if (pessoa != null) {
            System.out.print("Descrição da atividade: ");
            String descricao = scanner.nextLine();
            BigDecimal horas = inputService.lerHorasTrabalhadas();

            pessoaService.registrarAtividade(pessoa, descricao, horas);
        }
    }

    private void renovarContrato() {
        System.out.println("\n--- RENOVAR CONTRATO ---");

        //  Seleciona apenas terceirizados
        Terceirizado terceirizado = inputService.selecionarTerceirizado();

        if (terceirizado != null) {
            int meses = inputService.lerMesesRenovacao();
            pessoaService.renovarContrato(terceirizado, meses);
        }
    }

    private void gerarRelatorios() {
        System.out.println("\n--- RELATÓRIOS ---");
        relatorioService.circulacaoDiaria();
        relatorioService.funcionariosPorDepartamento();

        // Mostrar visitantes ativos
        List<Visitante> visitantesAtivos = em.createQuery(
                        "SELECT v FROM Visitante v WHERE v.dataHoraSaida IS NULL", Visitante.class)
                .getResultList();

        System.out.println("\n--- VISITANTES ATIVOS ---");
        if (visitantesAtivos.isEmpty()) {
            System.out.println("Nenhum visitante ativo no momento");
        } else {
            for (Visitante v : visitantesAtivos) {
                System.out.println(v.getNome() + " - " + v.getMotivoVisita() +
                        " (" + v.getTempoPermanenciaMinutos() + " minutos)");
            }
        }
    }

    private void exportarRelatoriosPDF() {
        System.out.println("\n--- EXPORTAR PDF ---");
        try {
            pdfExportService.exportarRelatorios(em);
            System.out.println("✅ Relatórios exportados para PDF!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao exportar PDF: " + e.getMessage());
        }
    }
}