<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Funcionario" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Gestão de Funcionários</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>Gestão de Funcionários</h1>
        <nav>
            <a href="index.html">Home</a>
        </nav>
    </header>

    <% String mensagem = (String) request.getAttribute("mensagem"); %>
    <% String erro = (String) request.getAttribute("erro"); %>

    <% if (mensagem != null) { %>
    <div class="alert success">
        <%= mensagem %>
    </div>
    <% } %>

    <% if (erro != null) { %>
    <div class="alert error">
        <%= erro %>
    </div>
    <% } %>

    <section class="cadastro">
        <h2>Cadastrar Novo Funcionário</h2>
        <form action="funcionarios" method="post">
            <input type="hidden" name="action" value="cadastrar">

            <div class="form-group">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" required>
            </div>

            <div class="form-group">
                <label for="cpf">CPF:</label>
                <input type="text" id="cpf" name="cpf" required>
            </div>

            <div class="form-group">
                <label for="dataNascimento">Data Nascimento:</label>
                <input type="date" id="dataNascimento" name="dataNascimento" required>
            </div>

            <div class="form-group">
                <label for="cargo">Cargo:</label>
                <select id="cargo" name="cargo" required>
                    <option value="Gerente">Gerente</option>
                    <option value="Coordenador">Coordenador</option>
                    <option value="Analista">Analista</option>
                    <option value="Desenvolvedor">Desenvolvedor</option>
                </select>
            </div>

            <div class="form-group">
                <label for="salarioBase">Salário Base:</label>
                <input type="number" id="salarioBase" name="salarioBase" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="departamento">Departamento:</label>
                <select id="departamento" name="departamento" required>
                    <option value="TI">TI</option>
                    <option value="RH">RH</option>
                    <option value="Financeiro">Financeiro</option>
                    <option value="Vendas">Vendas</option>
                </select>
            </div>

            <button type="submit">Cadastrar Funcionário</button>
        </form>
    </section>

    <section class="lista">
        <h2>Funcionários Cadastrados</h2>

        <% List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios"); %>

        <% if (funcionarios != null && !funcionarios.isEmpty()) { %>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Matrícula</th>
                <th>Cargo</th>
                <th>Departamento</th>
                <th>Salário Total</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <% for (Funcionario func : funcionarios) { %>
            <tr>
                <td><%= func.getId() %></td>
                <td><%= func.getNome() %></td>
                <td><%= func.getCpf() %></td>
                <td><%= func.getMatricula() %></td>
                <td><%= func.getCargo() %></td>
                <td><%= func.getDepartamento() %></td>
                <td>R$ <%= String.format("%.2f", func.calcularSalarioTotal()) %></td>
                <td>
                    <a href="funcionarios?action=buscar&id=<%= func.getId() %>">Detalhes</a>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <p>Nenhum funcionário cadastrado.</p>
        <% } %>
    </section>
</div>
</body>
</html>