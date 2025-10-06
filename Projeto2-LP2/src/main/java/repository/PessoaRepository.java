package repository;

import java.util.List;
import java.util.Optional;

// Interface Segregation Principle - Interfaces específicas
public interface PessoaRepository<T> {
    void salvar(T entity);
    void atualizar(T entity);
    Optional<T> buscarPorId(int id);
    List<T> listarTodos();
    void excluir(int id);
}