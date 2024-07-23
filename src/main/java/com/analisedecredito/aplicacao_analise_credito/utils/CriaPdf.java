package com.analisedecredito.aplicacao_analise_credito.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Component;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class CriaPdf extends PdfPageEventHelper {

    // Propriedades de estilização
    // Fontes para texto bold e normal
    Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    Font boldFontBlack = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

    public ByteArrayOutputStream criaPdfImprimir(EmprestimoResultadoReadDto emprestimoResultadoDto)
            throws DocumentException, FileNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document documentoPdf = new Document();
        PdfWriter writer = PdfWriter.getInstance(documentoPdf, baos);

        // Adiciona eventos de página (cabeçalho e rodapé)
        writer.setPageEvent(this);

        // Define a margem superior para deixar espaço para o cabeçalho
        documentoPdf.setMargins(documentoPdf.leftMargin(), documentoPdf.rightMargin(),
                100, documentoPdf.bottomMargin());

        documentoPdf.open();
        addDadosRequerente(documentoPdf, emprestimoResultadoDto);
        addResultado(documentoPdf, emprestimoResultadoDto);
        // Adiciona conteúdo ao PDF
        documentoPdf.add(new Paragraph("Resultado do Empréstimo"));
        documentoPdf.add(new Paragraph("ID: " + emprestimoResultadoDto.getIdResultado()));
        documentoPdf.add(new Paragraph("Descrição: " + emprestimoResultadoDto.getDescricaoResultado()));
        documentoPdf.add(new Paragraph("Aprovado: " + emprestimoResultadoDto.getAprovado()));

        // O que o PDF deve mostrar://
        // resultado das operações de crédito inclusas (se aprovadas ou não)
        // com os dados principais dos clientes
        // datas da aprovação, modalidades do empréstimo, prazos, taxas de juros,
        // tributos,
        // valores aprovados para crédito e valores finais a serem pagos

        documentoPdf.close();

        return baos;
    }

    public void addDadosRequerente(Document documentoPdf, EmprestimoResultadoReadDto emprestimoResultadoDto)
            throws DocumentException {

        var cliente = emprestimoResultadoDto.getEmprestimoRequisicao().getCliente();

        // Cria a tabela com 2 colunas
        PdfPTable tabelaRequerente = new PdfPTable(2);

        float[] columnWidths = new float[] { 2, 8 };
        tabelaRequerente.setWidths(columnWidths);
        tabelaRequerente.setWidthPercentage(100);

        // Adiciona o título da tabela
        PdfPCell cellTitulo = new PdfPCell(new Paragraph("RESULTADO REQUISIÇÃO",
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        cellTitulo.setColspan(2);
        cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitulo.setBackgroundColor(new BaseColor(35, 48, 43));
        cellTitulo.setPadding(10f);
        tabelaRequerente.addCell(cellTitulo);

        // Adiciona os dados do cliente
        addDataRow(tabelaRequerente, "Situação:", cliente.getNome());
        addDataRow(tabelaRequerente, "Motivo:", formataCpf(cliente.getCpf().toString()));
        addDataRow(tabelaRequerente, "Telefone:", formataTelefone(cliente.getTelefone().toString()));
        addDataRow(tabelaRequerente, "Email:", cliente.getEmail());

        // Adiciona a tabela ao documento PDF
        documentoPdf.add(tabelaRequerente);
    }

    private void addDataRow(PdfPTable table, String label, String value) {
        PdfPCell cellLabel = new PdfPCell(new Paragraph(label, boldFontBlack));
        cellLabel.setBackgroundColor(BaseColor.WHITE);
        cellLabel.setBorderColor(new BaseColor(35, 48, 43));
        cellLabel.setPadding(8f);
        table.addCell(cellLabel);

        PdfPCell cellValue = new PdfPCell(new Paragraph(value, normalFont));
        cellValue.setBackgroundColor(BaseColor.WHITE);
        cellValue.setBorderColor(new BaseColor(35, 48, 43));
        cellValue.setPadding(8f);
        table.addCell(cellValue);
    }

    public void addResultado(Document documentoPdf, EmprestimoResultadoReadDto emprestimoResultadoDto)
            throws DocumentException {

        // Adiciona um espaço em branco antes da tabela
        Paragraph spaceParagraph = new Paragraph();
        spaceParagraph.setSpacingBefore(50f); 
        documentoPdf.add(spaceParagraph);

        // Cria a tabela com 2 colunas
        PdfPTable tabelaResultado = new PdfPTable(2);
        tabelaResultado.setPaddingTop(50);

        float[] columnWidths = new float[] { 2, 8 };
        tabelaResultado.setWidths(columnWidths);
        tabelaResultado.setWidthPercentage(100);

        // Adiciona o título da tabela
        PdfPCell cellTitulo = new PdfPCell(new Paragraph("INFORMAÇÕES DA REQUISIÇÃO",
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        cellTitulo.setColspan(2);
        cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitulo.setBackgroundColor(new BaseColor(35, 48, 43));
        cellTitulo.setPadding(10f);
        tabelaResultado.addCell(cellTitulo);

        var aprovado = emprestimoResultadoDto.getAprovado();
        BaseColor statusColor = aprovado ? new BaseColor(0, 128, 0) : new BaseColor(255, 0, 0); // Verde para aprovado,
                                                                                                // vermelho para
                                                                                                // reprovado

        addDataRowResultado(tabelaResultado, "Situação:", aprovado ? "Aprovado" : "Reprovado", statusColor);
        // addDataRowResultado(tabelaResultado, "Telefone:",
        // formataTelefone(cliente.getTelefone().toString()));
        // addDataRowResultado(tabelaResultado, "Email:", cliente.getEmail());

        // Adiciona a tabela ao documento PDF
        documentoPdf.add(tabelaResultado);
    }

    private void addDataRowResultado(PdfPTable table, String label, String value, BaseColor textColor) {
        // Cria a célula para o rótulo com a fonte negrito e cor preta
        PdfPCell cellLabel = new PdfPCell(new Paragraph(label, boldFontBlack));
        cellLabel.setBackgroundColor(BaseColor.WHITE);
        cellLabel.setBorderColor(new BaseColor(35, 48, 43)); // Cor da borda
        cellLabel.setPadding(8f); // Adiciona um pouco de preenchimento
        table.addCell(cellLabel);

        // Cria a célula para o valor com a fonte normal e a cor de texto especificada
        PdfPCell cellValue = new PdfPCell(
                new Paragraph(value, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, textColor)));
        cellValue.setBackgroundColor(BaseColor.WHITE); // Cor de fundo para os dados
        cellValue.setBorderColor(new BaseColor(35, 48, 43)); // Cor da borda
        cellValue.setPadding(8f); // Adiciona um pouco de preenchimento
        table.addCell(cellValue);
    }

    public String formataCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf; // Retorna o CPF não formatado se estiver em um formato inesperado
        }

        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9);
    }

    public String formataTelefone(String telefone) {
        if (telefone == null || telefone.length() != 11) {
            return telefone; // Retorna o celular se tiver no formato esperado
        }

        return "(" + telefone.substring(0, 2) + ") " +
                telefone.substring(2, 7) + "-" +
                telefone.substring(7);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    private void addHeader(PdfWriter writer) {
        PdfPTable header = new PdfPTable(1);
        try {
            header.setWidths(new int[] { 24 });
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(PdfPCell.BOTTOM);
            header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            header.addCell(new Phrase("Cabeçalho do Relatório"));
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
    }

    private void addFooter(PdfWriter writer) {
        PdfPTable footer = new PdfPTable(1);
        try {
            footer.setWidths(new int[] { 24 });
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(PdfPCell.TOP);
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(new Phrase("Rodapé do Relatório"));
            footer.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
    }
}
