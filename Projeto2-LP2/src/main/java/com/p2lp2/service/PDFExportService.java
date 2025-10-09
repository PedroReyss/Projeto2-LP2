package com.p2lp2.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.p2lp2.model.*;
import jakarta.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFExportService {

    public void exportarRelatorios(EntityManager em) throws FileNotFoundException {
        String fileName = "relatorios_organizatec_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss")) + ".pdf";

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Título
        document.add(new Paragraph("RELATÓRIOS ORGANIZATEC")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(18));

        document.add(new Paragraph("Gerado em: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10));

        document.add(new Paragraph("\n"));

        // Circulação Diária
        document.add(new Paragraph("CIRCULAÇÃO DIÁRIA").setBold());
        document.add(criarTabelaCirculacao(em));

        document.add(new Paragraph("\n"));

        // Funcionários por Departamento
        document.add(new Paragraph("FUNCIONÁRIOS POR DEPARTAMENTO").setBold());
        document.add(criarTabelaDepartamentos(em));

        document.add(new Paragraph("\n"));

        // Visitantes Ativos
        document.add(new Paragraph("VISITANTES ATIVOS").setBold());
        document.add(criarTabelaVisitantes(em));

        document.close();

        System.out.println("📄 PDF gerado: " + fileName);
    }

    private Table criarTabelaCirculacao(EntityManager em) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho
        table.addHeaderCell(new Cell().add(new Paragraph("Tipo")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Quantidade")).setBold());

        // Dados
        Long totalFuncionarios = em.createQuery(
                "SELECT COUNT(f) FROM Funcionario f", Long.class).getSingleResult();
        Long totalTerceirizados = em.createQuery(
                "SELECT COUNT(t) FROM Terceirizado t", Long.class).getSingleResult();
        Long totalVisitantesHoje = em.createQuery(
                "SELECT COUNT(v) FROM Visitante v WHERE CAST(v.dataHoraEntrada AS date) = CAST(CURRENT_TIMESTAMP AS date)",
                Long.class).getSingleResult();

        table.addCell("Funcionários Próprios");
        table.addCell(totalFuncionarios.toString());
        table.addCell("Terceirizados");
        table.addCell(totalTerceirizados.toString());
        table.addCell("Visitantes Hoje");
        table.addCell(totalVisitantesHoje.toString());

        // Total
        Cell cellTotalLabel = new Cell(1, 1).add(new Paragraph("TOTAL"));
        cellTotalLabel.setBold();
        table.addCell(cellTotalLabel);

        Cell cellTotalValue = new Cell(1, 1).add(new Paragraph(
                String.valueOf(totalFuncionarios + totalTerceirizados + totalVisitantesHoje)));
        cellTotalValue.setBold();
        table.addCell(cellTotalValue);

        return table;
    }

    private Table criarTabelaDepartamentos(EntityManager em) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho
        table.addHeaderCell(new Cell().add(new Paragraph("Departamento")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Funcionários")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Terceirizados")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Total")).setBold());

        // Dados
        List<Departamento> departamentos = em.createQuery(
                "SELECT d FROM Departamento d", Departamento.class).getResultList();

        for (Departamento depto : departamentos) {
            Long qtdFuncionarios = em.createQuery(
                            "SELECT COUNT(f) FROM Funcionario f WHERE f.departamento = :depto", Long.class)
                    .setParameter("depto", depto).getSingleResult();
            Long qtdTerceirizados = em.createQuery(
                            "SELECT COUNT(t) FROM Terceirizado t WHERE t.departamento = :depto", Long.class)
                    .setParameter("depto", depto).getSingleResult();

            table.addCell(depto.getNome());
            table.addCell(qtdFuncionarios.toString());
            table.addCell(qtdTerceirizados.toString());
            table.addCell(String.valueOf(qtdFuncionarios + qtdTerceirizados));
        }

        return table;
    }

    private Table criarTabelaVisitantes(EntityManager em) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho
        table.addHeaderCell(new Cell().add(new Paragraph("Visitante")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Motivo")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Status")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Tempo (min)")).setBold());

        // veriifca ultimo ponto batido
        List<Visitante> visitantes = em.createQuery(
                "SELECT v FROM Visitante v", Visitante.class).getResultList();

        if (visitantes.isEmpty()) {
            Cell emptyCell = new Cell(1, 4).add(new Paragraph("Nenhum visitante cadastrado"));
            table.addCell(emptyCell);
        } else {
            for (Visitante v : visitantes) {
                // Verifica o último ponto do visitante
                String ultimoPonto = em.createQuery(
                                "SELECT p.tipo FROM Ponto p WHERE p.pessoa = :pessoa ORDER BY p.dataHora DESC", String.class)
                        .setParameter("pessoa", v)
                        .setMaxResults(1)
                        .getResultList()
                        .stream()
                        .findFirst()
                        .orElse("Nenhum");

                String status = "Fora";
                if ("entrada".equals(ultimoPonto)) {
                    status = "Dentro";
                }

                table.addCell(v.getNome());
                table.addCell(v.getMotivoVisita());
                table.addCell(status);
                table.addCell(String.valueOf(v.getTempoPermanenciaMinutos()));
            }
        }

        return table;
    }
}