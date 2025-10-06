package service;

import repository.PessoaRepository;
import model.Funcionario;

import java.util.List;

// Open/Closed Principle - Aberto para extensão, fechado para modificação
public class RelatorioServiceImpl implements RelatorioService {
    private final PessoaRepository<Funcionario> funcionarioRepository;

    public RelatorioServiceImpl(PessoaRepository<Funcionario> funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public void gerarRelatorioFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.listarTodos();

        System.out.println("\n=== RELATÓRIO DE FUNCIONÁRIOS ===");
        for (Funcionario func : funcionarios) {
            System.out.printf("Nome: %s | Matrícula: %s | Depto: %s | Salário Total: R$ %.2f%n",
                    func.getNome(), func.getMatricula(), func.getDepartamento(),
                    func.calcularSalarioTotal());
        }
    }

    @Override
    public void gerarRelatorioVisitantes() {
        // Implementação similar para visitantes
        System.out.println("Relatório de Visitantes - Implementar");
    }

    @Override
    public void gerarRelatorioTerceirizados() {
        // Implementação similar para terceirizados
        System.out.println("Relatório de Terceirizados - Implementar");
    }
}