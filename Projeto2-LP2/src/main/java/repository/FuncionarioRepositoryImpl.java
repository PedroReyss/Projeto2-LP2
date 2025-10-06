package repository;

import model.Funcionario;
import DAO.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Single Responsibility Principle - Apenas operações CRUD de Funcionário
public class FuncionarioRepositoryImpl implements PessoaRepository<Funcionario> {

    @Override
    public void salvar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, cpf, data_nascimento, matricula, salario_base, data_contratacao, cargo_id, departamento_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, new java.sql.Date(funcionario.getDataNascimento().getTime()));
            stmt.setString(4, funcionario.getMatricula());
            stmt.setDouble(5, funcionario.getSalarioBase());
            stmt.setDate(6, new java.sql.Date(funcionario.getDataContratacao().getTime()));
            // cargo_id e departamento_id seriam obtidos de outras tabelas
            stmt.setInt(7, 1); // Exemplo
            stmt.setInt(8, 1); // Exemplo

            stmt.executeUpdate();

            // Obter ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    funcionario.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário", e);
        }
    }

    @Override
    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario func = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("data_nascimento"),
                        "Cargo", // Buscar do join com cargos
                        rs.getDouble("salario_base"),
                        rs.getDate("data_contratacao"),
                        "Departamento" // Buscar do join com departamentos
                );
                func.setId(rs.getInt("id"));
                func.setMatricula(rs.getString("matricula"));
                funcionarios.add(func);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários", e);
        }

        return funcionarios;
    }

    @Override
    public Optional<Funcionario> buscarPorId(int id) {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Funcionario func = new Funcionario(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getDate("data_nascimento"),
                            "Cargo",
                            rs.getDouble("salario_base"),
                            rs.getDate("data_contratacao"),
                            "Departamento"
                    );
                    func.setId(rs.getInt("id"));
                    func.setMatricula(rs.getString("matricula"));
                    return Optional.of(func);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário", e);
        }

        return Optional.empty();
    }

    @Override
    public void atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, data_nascimento = ?, salario_base = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, new java.sql.Date(funcionario.getDataNascimento().getTime()));
            stmt.setDouble(4, funcionario.getSalarioBase());
            stmt.setInt(5, funcionario.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário", e);
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir funcionário", e);
        }
    }
}