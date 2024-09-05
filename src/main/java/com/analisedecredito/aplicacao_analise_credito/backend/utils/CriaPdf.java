package com.analisedecredito.aplicacao_analise_credito.backend.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.EmprestimoRequisicaoReadDto;
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

    // Propriedades globais de estilização
    Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    Font boldFontBlack = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font boldFontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    Font footerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.GRAY);
    int hex = 0x071D41; 
    BaseColor colorBackground = new BaseColor((hex >> 16) & 0xFF, (hex >> 8) & 0xFF, hex & 0xFF);

    public ByteArrayOutputStream criaPdfImprimir(EmprestimoRequisicaoReadDto emprestimoRequisicao)
            throws DocumentException, FileNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document documentoPdf = new Document();
        PdfWriter writer = PdfWriter.getInstance(documentoPdf, baos);

        // Adiciona eventos de página (cabeçalho e rodapé)
        writer.setPageEvent(this);

        // Define a margem superior para deixar espaço para o cabeçalho
        documentoPdf.setMargins(documentoPdf.leftMargin(), documentoPdf.rightMargin(),
                100, documentoPdf.bottomMargin());

        // Insere conteúdo no PDF
        documentoPdf.open();
        addDadosRequerente(documentoPdf, emprestimoRequisicao);
        addResultado(documentoPdf, emprestimoRequisicao);
        documentoPdf.close();

        return baos;
    }

    public void addDadosRequerente(Document documentoPdf, EmprestimoRequisicaoReadDto emprestimoRequisicao)
            throws DocumentException {

        var cliente = emprestimoRequisicao.getCliente();

        // Cria a tabela com 2 colunas
        PdfPTable tabelaRequerente = new PdfPTable(2);

        float[] columnWidths = new float[] { 2, 8 };
        tabelaRequerente.setWidths(columnWidths);
        tabelaRequerente.setWidthPercentage(100);

        // Adiciona o título da tabela
        PdfPCell cellTitulo = new PdfPCell(new Paragraph("DADOS DO REQUERENTE",
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        cellTitulo.setColspan(2);
        cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitulo.setBackgroundColor(colorBackground);
        cellTitulo.setPadding(10f);
        tabelaRequerente.addCell(cellTitulo);

        // Adiciona os dados do cliente
        addTableRequerente(tabelaRequerente, "Nome:", cliente.getNome());
        addTableRequerente(tabelaRequerente, "Data de nascimento:", formataData(cliente.getDataNascimento()));
        addTableRequerente(tabelaRequerente, "Cpf:", formataCpf(cliente.getCpf().toString()));
        addTableRequerente(tabelaRequerente, "Telefone:", formataTelefone(cliente.getTelefone().toString()));
        addTableRequerente(tabelaRequerente, "Email:", cliente.getEmail());
        addTableRequerente(tabelaRequerente, "Endereço:", cliente.getEndereco());

        // Adiciona a tabela ao documento PDF
        documentoPdf.add(tabelaRequerente);
    }

    private void addTableRequerente(PdfPTable table, String label, String value) {
        PdfPCell cellLabel = new PdfPCell(new Paragraph(label, boldFontBlack));

        cellLabel.setBackgroundColor(BaseColor.WHITE);
        cellLabel.setBorder(0);
        cellLabel.setPadding(8f);
        table.addCell(cellLabel);

        PdfPCell cellValue = new PdfPCell(new Paragraph(value, normalFont));
        cellValue.setBackgroundColor(BaseColor.WHITE);
        cellValue.setBorder(0);
        cellValue.setPadding(8f);
        table.addCell(cellValue);
    }

    public void addResultado(Document documentoPdf, EmprestimoRequisicaoReadDto dto)
            throws DocumentException {

        Double jurosCalculados = dto.getJurosCalculado();
        Double taxaIof = dto.getIof().getTaxaIof();
        String taxaIofPorcentagem = String.format("%.2f %%", taxaIof * 100);
        Double iofCalculado = dto.getIofCalculado();
        Double valorParcela = dto.getValorParcela();
        Double valorTotal = dto.getValorTotal();

        // Adiciona um espaço em branco antes da tabela
        Paragraph spaceParagraph = new Paragraph();
        spaceParagraph.setSpacingBefore(30f);
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
        cellTitulo.setBackgroundColor(colorBackground);
        cellTitulo.setPadding(10f);
        tabelaResultado.addCell(cellTitulo);

        var aprovado = dto.getAprovado();

        addTableResultado(tabelaResultado, "Número do Empréstimo:", String.valueOf(dto.getIdRequisicao()),
                BaseColor.BLACK);
        BaseColor statusColor = aprovado ? new BaseColor(0, 128, 0) : new BaseColor(255, 0, 0);
        addTableResultado(tabelaResultado, "Situação:", aprovado ? "Aprovado" : "Negado", statusColor);
        if (!aprovado) {
            addTableResultado(tabelaResultado, "Motivo da reprovação:",
                    (dto.getDescricaoResultado()), BaseColor.BLACK);
        }
        addTableResultado(tabelaResultado, "Data da requisição:",
                formataData(dto.getDataRequisicao()), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Data da análise:",
                formataData(dto.getDataResultado()), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Modalidade:",
                dto.getEmprestimoModalidade().getDescricaoModalidade(), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Valor requerido:",
                formataValor(dto.getValorRequerido()), BaseColor.BLACK);
        if (aprovado) {
            addTableResultado(tabelaResultado, "Taxa de juros mensal:",
                    String.format("%.2f %%", dto.getJuros().getTaxaJurosMensal()),
                    BaseColor.BLACK);
            addTableResultado(tabelaResultado, "Valor de juros a ser pago:",
                    formataValor(jurosCalculados), BaseColor.BLACK);
            addTableResultado(tabelaResultado, "Taxa de iof:", taxaIofPorcentagem, BaseColor.BLACK);
            addTableResultado(tabelaResultado, "Valor de iof a ser pago:",
                    formataValor(iofCalculado), BaseColor.BLACK);
            addTableResultado(tabelaResultado, "Valor das parcelas:",
                    formataValor(valorParcela), BaseColor.BLACK);
            addTableResultado(tabelaResultado, "Prazo para pagamento:",
                    dto.getPrazoMes() + " meses", BaseColor.BLACK);
            addTableResultado(tabelaResultado, "Valor final a ser pago:",
                    formataValor(valorTotal), BaseColor.BLACK);
        }
 
        documentoPdf.add(tabelaResultado);
    }

    private void addTableResultado(PdfPTable table, String label, String value, BaseColor textColor) {

        PdfPCell cellLabel = new PdfPCell(new Paragraph(label, boldFontBlack));
        cellLabel.setBackgroundColor(BaseColor.WHITE);
        cellLabel.setBorder(0);
        cellLabel.setPadding(8f);
        table.addCell(cellLabel);

        PdfPCell cellValue = new PdfPCell(
                new Paragraph(value, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, textColor)));
        cellValue.setBackgroundColor(BaseColor.WHITE);
        cellValue.setBorder(0);
        cellValue.setPadding(8f);
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
            return telefone;
        }

        return "(" + telefone.substring(0, 2) + ") " +
                telefone.substring(2, 7) + "-" +
                telefone.substring(7);
    }

    public String formataData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(data);
        return dataFormatada;
    }

    public String formataValor(Double valor) {
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
        var formatado = dinheiro.format(valor);
        return formatado;
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
            header.addCell(new Phrase("RELATÓRIO DE OPERAÇÕES DE CRÉDITO", boldFontTitulo));
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
    }

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
}
