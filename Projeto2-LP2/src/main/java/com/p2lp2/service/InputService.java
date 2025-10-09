package com.p2lp2.service;

import com.p2lp2.model.*;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputService {
    private Scanner scanner;
    private EntityManager em;
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InputService(Scanner scanner, EntityManager em) {
        this.scanner = scanner;
        this.em = em;
    }

    public Pessoa criarPessoa(int tipo) {
        try {
            System.out.print("Nome: ");
            String nome = lerTextoNaoVazio("Nome não pode estar vazio");

            System.out.print("CPF (apenas números): ");
            String cpf = lerCPFValido();

            switch (tipo) {
                case 1: // Funcionário
                    return criarFuncionario(nome, cpf);
                case 2: // Terceirizado
                    return criarTerceirizado(nome, cpf);
                case 3: // Visitante
                    return criarVisitante(nome, cpf);
                default:
                    System.out.println("Tipo inválido!");
                    return null;
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar pessoa: " + e.getMessage());
            return null;
        }
    }

    private Funcionario criarFuncionario(String nome, String cpf) {
        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        LocalDate dataNascimento = lerDataValida();

        Cargo cargo = selecionarCargo();
        Departamento departamento = selecionarDepartamento();

        return new Funcionario(nome, cpf, dataNascimento, cargo, departamento);
    }

    private Terceirizado criarTerceirizado(String nome, String cpf) {
        System.out.print("Função: ");
        String funcao = lerTextoNaoVazio("Função não pode estar vazia");

        System.out.print("Empresa prestadora: ");
        String empresa = lerTextoNaoVazio("Empresa não pode estar vazia");

        System.out.print("Data início contrato (dd/MM/yyyy): ");
        LocalDate dataInicio = lerDataValida();

        System.out.print("Data fim contrato (dd/MM/yyyy): ");
        LocalDate dataFim = lerDataValida();

        // Verificar se data fim é depois da data início
        if (dataFim.isBefore(dataInicio)) {
            System.out.println("❌ Data fim deve ser posterior à data início");
            return null;
        }

        Funcionario responsavel = selecionarFuncionario("responsável");
        if (responsavel == null) return null;

        Departamento departamento = selecionarDepartamento();

        return new Terceirizado(nome, cpf, funcao, empresa, dataInicio, dataFim,
                responsavel, departamento);
    }

    private Visitante criarVisitante(String nome, String cpf) {
        System.out.print("Motivo da visita: ");
        String motivo = lerTextoNaoVazio("Motivo não pode estar vazio");

        Funcionario funcionarioVisitado = selecionarFuncionario("para visitar");
        if (funcionarioVisitado == null) return null;

        return new Visitante(nome, cpf, motivo, LocalDateTime.now(), funcionarioVisitado);
    }

    // ========== MÉTODOS AUXILIARES COM VALIDAÇÃO ==========

    private String lerTextoNaoVazio(String mensagemErro) {
        while (true) {
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("❌ " + mensagemErro);
            System.out.print("Tente novamente: ");
        }
    }

    private String lerCPFValido() {
        while (true) {
            String cpf = scanner.nextLine().trim();

            // Verificar formato
            if (!CPF_PATTERN.matcher(cpf).matches()) {
                System.out.println("❌ CPF deve conter exatamente 11 números");
                System.out.print("Digite novamente: ");
                continue;
            }

            // Verificar se CPF já existe
            Long count = em.createQuery(
                            "SELECT COUNT(p) FROM Pessoa p WHERE p.cpf = :cpf", Long.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            if (count > 0) {
                System.out.println("❌ CPF já cadastrado no sistema");
                System.out.print("Digite outro CPF: ");
                continue;
            }

            return cpf;
        }
    }

    private LocalDate lerDataValida() {
        while (true) {
            try {
                String dataStr = scanner.nextLine().trim();
                LocalDate data = LocalDate.parse(dataStr, DATE_FORMATTER);

                // Verificar se data não é futura (para nascimento)
                if (data.isAfter(LocalDate.now())) {
                    System.out.println("❌ Data não pode ser futura");
                    System.out.print("Digite novamente: ");
                    continue;
                }

                return data;
            } catch (DateTimeParseException e) {
                System.out.println("❌ Formato de data inválido. Use dd/MM/yyyy (ex: 15/05/1990)");
                System.out.print("Digite novamente: ");
            }
        }
    }

    private Cargo selecionarCargo() {
        List<Cargo> cargos = em.createQuery("SELECT c FROM Cargo c", Cargo.class).getResultList();

        if (cargos.isEmpty()) {
            System.out.println("❌ Nenhum cargo cadastrado no sistema");
            return null;
        }

        System.out.println("Cargos disponíveis:");
        for (int i = 0; i < cargos.size(); i++) {
            System.out.println((i + 1) + ". " + cargos.get(i).getNome() +
                    " (R$ " + cargos.get(i).getSalarioBase() + ")");
        }

        return selecionarDaLista(cargos, "cargo");
    }

    private Departamento selecionarDepartamento() {
        List<Departamento> departamentos = em.createQuery("SELECT d FROM Departamento d", Departamento.class).getResultList();

        if (departamentos.isEmpty()) {
            System.out.println("❌ Nenhum departamento cadastrado no sistema");
            return null;
        }

        System.out.println("Departamentos disponíveis:");
        for (int i = 0; i < departamentos.size(); i++) {
            System.out.println((i + 1) + ". " + departamentos.get(i).getNome());
        }

        return selecionarDaLista(departamentos, "departamento");
    }

    private Funcionario selecionarFuncionario(String contexto) {
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();

        if (funcionarios.isEmpty()) {
            System.out.println("❌ Nenhum funcionário cadastrado no sistema");
            return null;
        }

        System.out.println("Funcionários disponíveis:");
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            System.out.println((i + 1) + ". " + f.getNome() +
                    " - " + f.getCargo().getNome() +
                    " (" + f.getDepartamento().getNome() + ")");
        }

        return selecionarDaLista(funcionarios, "funcionário " + contexto);
    }

    private <T> T selecionarDaLista(List<T> lista, String tipo) {
        while (true) {
            System.out.print("Escolha o " + tipo + " (1-" + lista.size() + "): ");
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); // limpar buffer

                if (escolha >= 1 && escolha <= lista.size()) {
                    return lista.get(escolha - 1);
                } else {
                    System.out.println("❌ Opção inválida. Escolha entre 1 e " + lista.size());
                }
            } catch (Exception e) {
                System.out.println("❌ Digite um número válido");
                scanner.nextLine(); // limpar buffer inválido
            }
        }
    }

    public Pessoa selecionarPessoa() {
        List<Pessoa> pessoas = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();

        if (pessoas.isEmpty()) {
            System.out.println("❌ Nenhuma pessoa cadastrada!");
            return null;
        }

        System.out.println("Pessoas cadastradas:");
        for (int i = 0; i < pessoas.size(); i++) {
            Pessoa p = pessoas.get(i);
            String infoExtra = "";

            if (p instanceof Funcionario) {
                infoExtra = " - " + ((Funcionario) p).getCargo().getNome();
            } else if (p instanceof Terceirizado) {
                infoExtra = " - " + ((Terceirizado) p).getFuncao();
            } else if (p instanceof Visitante) {
                infoExtra = " - " + ((Visitante) p).getMotivoVisita();
            }

            System.out.println((i + 1) + ". " + p.getNome() + " (" + p.getTipoPessoa() + ")" + infoExtra);
        }

        return selecionarDaLista(pessoas, "pessoa");
    }

    public BigDecimal lerHorasTrabalhadas() {
        while (true) {
            System.out.print("Horas trabalhadas: ");
            try {
                BigDecimal horas = scanner.nextBigDecimal();
                scanner.nextLine(); // limpar buffer

                if (horas.compareTo(BigDecimal.ZERO) > 0 && horas.compareTo(new BigDecimal("24")) <= 0) {
                    return horas;
                } else {
                    System.out.println("❌ Horas devem ser entre 0.1 e 24");
                }
            } catch (Exception e) {
                System.out.println("❌ Digite um número válido (ex: 2.5)");
                scanner.nextLine(); // limpar buffer inválido
            }
        }
    }

    public int lerMesesRenovacao() {
        while (true) {
            System.out.print("Meses para renovar: ");
            try {
                int meses = scanner.nextInt();
                scanner.nextLine(); // limpar buffer

                if (meses > 0 && meses <= 120) { // máximo 10 anos
                    return meses;
                } else {
                    System.out.println("❌ Meses devem ser entre 1 e 120");
                }
            } catch (Exception e) {
                System.out.println("❌ Digite um número válido");
                scanner.nextLine(); // limpar buffer inválido
            }
        }
    }

    public Terceirizado selecionarTerceirizado() {
        List<Terceirizado> terceirizados = em.createQuery("SELECT t FROM Terceirizado t", Terceirizado.class).getResultList();

        if (terceirizados.isEmpty()) {
            System.out.println("❌ Nenhum terceirizado cadastrado no sistema!");
            return null;
        }

        System.out.println("Terceirizados disponíveis:");
        for (int i = 0; i < terceirizados.size(); i++) {
            Terceirizado t = terceirizados.get(i);
            System.out.println((i + 1) + ". " + t.getNome() +
                    " - " + t.getFuncao() +
                    " (" + t.getEmpresaPrestadora() + ")" +
                    " - Contrato até: " + t.getDataFimContrato());
        }

        return selecionarDaLista(terceirizados, "terceirizado");
    }
}