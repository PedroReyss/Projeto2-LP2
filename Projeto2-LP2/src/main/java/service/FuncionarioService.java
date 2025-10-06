package service;

import model.Funcionario;
import repository.PessoaRepository;

import java.util.List;

// Dependency Inversion Principle - Depende de abstração (repository)
public class FuncionarioService {
    private final PessoaRepository<Funcionario> funcionarioRepository;

    // Injeção de dependência
    public FuncionarioService(PessoaRepository<Funcionario> funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        // Validações de negócio
        if (funcionario.getSalarioBase() <= 0) {
            throw new IllegalArgumentException("Salário base deve ser maior que zero");
        }

        String matricula = "FUNC" + System.currentTimeMillis();
        funcionario.setMatricula(matricula);

        funcionarioRepository.salvar(funcionario);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.listarTodos();
    }

    public Funcionario buscarFuncionario(int id) {
        return funcionarioRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }
}
