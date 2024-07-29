package com.analisedecredito.aplicacao_analise_credito.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoRequisicaoService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class CriaPdfGeral extends PdfPageEventHelper {

    EmprestimoRequisicaoService emprestimoRequisicaoService;

    // Propriedades globais de estilização
    Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    Font boldFontBlack = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font boldFontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    Font footerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.GRAY);

    // Método para criar PDF com base em uma lista de resultados
    public ByteArrayOutputStream criaPdfPeriodo(List<EmprestimoResultadoReadDto> emprestimoResultadoDto)
            throws DocumentException, FileNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document documentoPdf = new Document();
        PdfWriter writer = PdfWriter.getInstance(documentoPdf, baos);

        // Adiciona eventos de página (cabeçalho e rodapé)
        writer.setPageEvent(this);

        // Define margens do documento
        documentoPdf.setMargins(36, 36, 100, 36);

        // Abre o documento PDF
        documentoPdf.open();

        // Adiciona o conteúdo ao documento
        addCabecalho(documentoPdf, emprestimoResultadoDto);

        // Fecha o documento
        documentoPdf.close();

        return baos;
    }

    // Adiciona o cabeçalho e o conteúdo do PDF
    private void addCabecalho(Document documentoPdf, List<EmprestimoResultadoReadDto> dtoRead)
            throws DocumentException {
        // Cria e configura a tabela
        PdfPTable tabelaResultados = new PdfPTable(6);
        tabelaResultados.setWidthPercentage(100);
        tabelaResultados.setSpacingBefore(10f);
        tabelaResultados.setSpacingAfter(10f);
        tabelaResultados.setWidths(new float[] { 1f, 2f, 1f, 1.5f, 1.5f, 1.5f });

        // Adiciona o cabeçalho da tabela
        addTableHeader(tabelaResultados);

        // Adiciona os dados dos empréstimos
        for (EmprestimoResultadoReadDto dto : dtoRead) {
            addTableRow(tabelaResultados, dto);
        }

        // Adiciona a tabela ao documento
        documentoPdf.add(tabelaResultados);
    }

    // Adiciona cabeçalho à tabela
    private void addTableHeader(PdfPTable tabela) {
        addTableCell(tabela, "ID", boldFont);
        addTableCell(tabela, "Nome", boldFont);
        addTableCell(tabela, "Modalidade", boldFont);
        addTableCell(tabela, "Valor Requerido", boldFont);
        addTableCell(tabela, "Data Requisição", boldFont);
        addTableCell(tabela, "Status", boldFont);
    }

    // Adiciona uma célula de dados à tabela
    private void addTableRow(PdfPTable tabela, EmprestimoResultadoReadDto dto) {
        addTableCell(tabela, dto.getIdResultado().toString(), normalFont);
        addTableCell(tabela, dto.getEmprestimoRequisicao().getCliente().getNome(), normalFont);
        addTableCell(tabela, dto.getEmprestimoRequisicao().getEmprestimoModalidade().getDescricaoModalidade(),
                normalFont);
        addTableCell(tabela, formataValor(dto.getEmprestimoRequisicao().getValorRequerido()),
                normalFont);
        addTableCell(tabela, formataData(dto.getEmprestimoRequisicao().getDataRequisicao()), normalFont);
        // Define a cor da fonte com base na aprovação
        Font statusFont = dto.getAprovado()
                ? new Font(normalFont.getFamily(), normalFont.getSize(), normalFont.getStyle(),
                        new BaseColor(0, 128, 0))
                : new Font(normalFont.getFamily(), normalFont.getSize(), normalFont.getStyle(), BaseColor.RED);

        addTableCell(tabela, dto.getAprovado() ? "Aprovado" : "Reprovado", statusFont);
    }

    // Adiciona uma célula à tabela
    private void addTableCell(PdfPTable tabela, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5f);
        if (font.equals(boldFont)) {
            cell.setBackgroundColor(new BaseColor(35, 48, 43));
        }
        tabela.addCell(cell);
    }

    // Formata a data para o padrão dd/MM/yyyy
    private String formataData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    // Adiciona o cabeçalho ao PDF
    private void addHeader(PdfWriter writer) {
        PdfPTable header = new PdfPTable(1);
        try {
            header.setWidths(new int[] { 24 });
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(PdfPCell.BOTTOM);
            header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            header.addCell(new Phrase("RELATÓRIO DE OPERAÇÕES DE CRÉDITO", boldFontTitulo));
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
    }

    // Adiciona o rodapé ao PDF
    private void addFooter(PdfWriter writer) {
        PdfPTable footer = new PdfPTable(1);
        Date dataLocal = new Date();

        try {
            footer.setWidths(new int[] { 24 });
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(PdfPCell.TOP);
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(new Phrase("Relatório gerado em: " + formataData(dataLocal), footerFont));
            footer.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
    }

    public String formataValor(Double valor) {
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
        var formatado = dinheiro.format(valor);
        return formatado;
    }

}