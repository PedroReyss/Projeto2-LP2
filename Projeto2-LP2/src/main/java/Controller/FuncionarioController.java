package Controller;

import model.Funcionario;
import service.FuncionarioService;
import repository.FuncionarioRepositoryImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/funcionarios")
public class FuncionarioController extends HttpServlet {
    private FuncionarioService funcionarioService;

    @Override
    public void init() {
        this.funcionarioService = new FuncionarioService(new FuncionarioRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("listar".equals(action)) {
            listarFuncionarios(request, response);
        } else if ("buscar".equals(action)) {
            buscarFuncionario(request, response);
        } else {
            // Action padrão - listar
            listarFuncionarios(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("cadastrar".equals(action)) {
            cadastrarFuncionario(request, response);
        } else if ("atualizar".equals(action)) {
            atualizarFuncionario(request, response);
        } else if ("excluir".equals(action)) {
            excluirFuncionario(request, response);
        }
    }

    private void cadastrarFuncionario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String dataNascStr = request.getParameter("dataNascimento");
            String cargo = request.getParameter("cargo");
            double salarioBase = Double.parseDouble(request.getParameter("salarioBase"));
            String departamento = request.getParameter("departamento");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataNascimento = sdf.parse(dataNascStr);
            Date dataContratacao = new Date(); // Data atual

            Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento,
                    cargo, salarioBase, dataContratacao, departamento);

            funcionarioService.cadastrarFuncionario(funcionario);

            request.setAttribute("mensagem", "Funcionário cadastrado com sucesso! Matrícula: " + funcionario.getMatricula());

        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao cadastrar funcionário: " + e.getMessage());
        }

        listarFuncionarios(request, response);
    }

    private void listarFuncionarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        request.setAttribute("funcionarios", funcionarios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/funcionarios.jsp");
        dispatcher.forward(request, response);
    }

    private void buscarFuncionario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Funcionario funcionario = funcionarioService.buscarFuncionario(id);
            request.setAttribute("funcionario", funcionario);
        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/detalhesFuncionario.jsp");
        dispatcher.forward(request, response);
    }

    private void atualizarFuncionario(HttpServletRequest request, HttpServletResponse response) {
        // Implementar atualização
    }

    private void excluirFuncionario(HttpServletRequest request, HttpServletResponse response) {
        // Implementar exclusão
    }
}