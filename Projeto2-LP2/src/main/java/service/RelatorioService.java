package service;

import java.util.List;

// Dependency Inversion Principle - Depender de abstrações
public interface RelatorioService {
    void gerarRelatorioFuncionarios();
    void gerarRelatorioVisitantes();
    void gerarRelatorioTerceirizados();
}