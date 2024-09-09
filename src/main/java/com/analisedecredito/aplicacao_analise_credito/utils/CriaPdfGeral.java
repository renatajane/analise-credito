package com.analisedecredito.aplicacao_analise_credito.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
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
public class CriaPdfGeral extends PdfPageEventHelper {

    // Propriedades globais de estilização
    Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    Font boldFontBlack = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font boldFontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    Font footerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.GRAY);
    int hex = 0x071D41; 
    BaseColor colorBackground = new BaseColor((hex >> 16) & 0xFF, (hex >> 8) & 0xFF, hex & 0xFF);

    public ByteArrayOutputStream criaPdfPorPeriodo(List<EmprestimoRequisicaoReadDto> requisicoes, Date dataInicio,
            Date dataFim)
            throws DocumentException, FileNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document documentoPdf = new Document();
        PdfWriter writer = PdfWriter.getInstance(documentoPdf, baos);

        // Adiciona eventos de página (cabeçalho e rodapé)
        writer.setPageEvent(this);

        // Define a margem superior para deixar espaço para o cabeçalho
        documentoPdf.setMargins(documentoPdf.leftMargin(), documentoPdf.rightMargin(), 100,
                documentoPdf.bottomMargin());

        // Insere conteúdo no PDF
        documentoPdf.open();
        addCabecalhoPeriodo(documentoPdf, dataInicio, dataFim);

        // Adiciona dados do requerente
        if (!requisicoes.isEmpty()) {
            // Adiciona os dados do requerente apenas uma vez
            addDadosRequerente(documentoPdf, requisicoes.get(0));

            // Adiciona tabela de requisições
            addTabelaRequisicoes(documentoPdf, requisicoes);
        }

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
        tabelaRequerente.setSpacingAfter(10f);

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

    private void addCabecalhoPeriodo(Document documentoPdf, Date dataInicio, Date dataFim) throws DocumentException {
        // Adiciona o cabeçalho com o período
        Paragraph cabecalho = new Paragraph("Período: " + formataData(dataInicio) + " a " + formataData(dataFim),
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK));
        cabecalho.setSpacingBefore(10f);
        cabecalho.setSpacingAfter(10f);
        documentoPdf.add(cabecalho);
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

    private void addTabelaRequisicoes(Document documentoPdf, List<EmprestimoRequisicaoReadDto> requisicoes)
            throws DocumentException {
        // Cria a tabela com 2 colunas
        PdfPTable tabela = new PdfPTable(2);
        tabela.setWidths(new float[] { 2, 8 });
        tabela.setWidthPercentage(100);

        // Adiciona o título da tabela
        PdfPCell cellTitulo = new PdfPCell(new Paragraph("REQUISIÇÕES",
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        cellTitulo.setColspan(2);
        cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitulo.setBackgroundColor(colorBackground);
        cellTitulo.setPadding(10f);
        tabela.addCell(cellTitulo);

        // Adiciona as linhas da tabela
        for (EmprestimoRequisicaoReadDto dto : requisicoes) {
            // Adiciona uma linha com o código da requisição
            addTableRow(tabela, "Número do Empréstimo:", String.valueOf(dto.getIdRequisicao()));

            // Cria o texto das informações com espaçamento extra
            StringBuilder infoBuilder = new StringBuilder();
            infoBuilder.append("Situação: ").append(dto.getAprovado() ? "Aprovado" : "Negado")
                    .append("\n\n"); // Adiciona um espaçamento extra
            infoBuilder.append("Data da requisição: ").append(formataData(dto.getDataRequisicao()))
                    .append("\n\n"); // Adiciona um espaçamento extra
            infoBuilder.append("Data da análise: ").append(formataData(dto.getDataResultado()))
                    .append("\n\n");
            infoBuilder.append("Modalidade: ").append(dto.getEmprestimoModalidade().getDescricaoModalidade())
                    .append("\n\n");
            infoBuilder.append("Valor requerido: ").append(formataValor(dto.getValorRequerido()))
                    .append("\n\n");

            if (dto.getAprovado()) {
                infoBuilder.append("Taxa de juros mensal: ")
                        .append(String.format("%.2f %%", dto.getJuros().getTaxaJurosMensal()))
                        .append("\n\n");
                infoBuilder.append("Valor de juros a ser pago: ").append(formataValor(dto.getJurosCalculado()))
                        .append("\n\n");
                infoBuilder.append("Taxa de IOF: ").append(String.format("%.2f %%", dto.getIof().getTaxaIof() * 100))
                        .append("\n\n");
                infoBuilder.append("Valor de IOF a ser pago: ").append(formataValor(dto.getIofCalculado()))
                        .append("\n\n");
                infoBuilder.append("Valor das parcelas: ").append(formataValor(dto.getValorParcela()))
                        .append("\n\n");
                infoBuilder.append("Prazo para pagamento: ").append(dto.getPrazoMes()).append(" meses")
                        .append("\n\n");
                infoBuilder.append("Valor final a ser pago: ").append(formataValor(dto.getValorTotal()))
                        .append("\n\n");
            } else {
                infoBuilder.append("Motivo da reprovação: ").append(dto.getDescricaoResultado())
                        .append("\n\n");
            }

            // Adiciona uma linha com as informações detalhadas
            addTableRow(tabela, "Informações:", infoBuilder.toString());
        }

        // Adiciona a tabela ao documento PDF
        documentoPdf.add(tabela);
    }

    private void addTableRow(PdfPTable table, String label, String value) {
        // Cria a fonte em negrito para os rótulos
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        // Cria a fonte normal para os valores
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        // Adiciona o rótulo com negrito
        PdfPCell cellLabel = new PdfPCell();
        Phrase labelPhrase = new Phrase(label, boldFont);
        cellLabel.setPhrase(labelPhrase);
        cellLabel.setBackgroundColor(BaseColor.WHITE);
        cellLabel.setBorder(0);
        cellLabel.setPaddingTop(10f); // Ajusta o padding superior
        cellLabel.setPaddingBottom(10f); // Ajusta o padding inferior
        table.addCell(cellLabel);

        // Adiciona o valor
        PdfPCell cellValue = new PdfPCell();
        Phrase valuePhrase = new Phrase(value, normalFont);
        cellValue.setPhrase(valuePhrase);
        cellValue.setBackgroundColor(BaseColor.WHITE);
        cellValue.setBorder(0);
        cellValue.setPaddingTop(10f); // Ajusta o padding superior
        cellValue.setPaddingBottom(10f); // Ajusta o padding inferior
        table.addCell(cellValue);
    }

    public String formataData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    public String formataValor(Double valor) {
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
        return dinheiro.format(valor);
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
}
