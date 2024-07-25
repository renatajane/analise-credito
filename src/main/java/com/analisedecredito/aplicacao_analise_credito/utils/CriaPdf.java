package com.analisedecredito.aplicacao_analise_credito.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoRequisicaoService;
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

    @Autowired
    EmprestimoRequisicaoService emprestimoRequisicaoService;

    // Propriedades globais de estilização
    Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    Font boldFontBlack = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font boldFontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    Font footerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.GRAY);

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

        // Insere conteúdo no PDF
        documentoPdf.open();
       // addDadosRequerente(documentoPdf, emprestimoResultadoDto);
        addResultado(documentoPdf, emprestimoResultadoDto);
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
        PdfPCell cellTitulo = new PdfPCell(new Paragraph("DADOS DO REQUERENTE",
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        cellTitulo.setColspan(2);
        cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitulo.setBackgroundColor(new BaseColor(35, 48, 43));
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

    public void addResultado(Document documentoPdf, EmprestimoResultadoReadDto dto)
            throws DocumentException {

        Double jurosCalculados = emprestimoRequisicaoService.calculaJuros(dto.getIdResultado());

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
        cellTitulo.setBackgroundColor(new BaseColor(35, 48, 43));
        cellTitulo.setPadding(10f);
        tabelaResultado.addCell(cellTitulo);

        var aprovado = dto.getAprovado();
        addTableResultado(tabelaResultado, "Código da requisição:", String.valueOf(dto.getIdResultado()),
                BaseColor.BLACK);
        BaseColor statusColor = aprovado ? new BaseColor(0, 128, 0) : new BaseColor(255, 0, 0);
        addTableResultado(tabelaResultado, "Situação:", aprovado ? "Aprovado" : "Reprovado", statusColor);
        addTableResultado(tabelaResultado, "Data da requisição:",
                formataData(dto.getEmprestimoRequisicao().getDataRequisicao()), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Modalidade:",
                dto.getEmprestimoRequisicao().getEmprestimoModalidade().getDescricaoModalidade(), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Valor requerido:",
                formataValor(dto.getEmprestimoRequisicao().getValorRequerido()), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Taxa de juros mensal:",
                dto.getEmprestimoRequisicao().getJuros().getTaxaJurosMensal() + " %", BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Valor de juros a ser pago:",
                formataValor(jurosCalculados), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Iof:",
                dto.getEmprestimoRequisicao().getIof().getIofTotal().toString(), BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Prazo para pagamento:",
                dto.getEmprestimoRequisicao().getPrazoMes().toString() + " meses", BaseColor.BLACK);
        addTableResultado(tabelaResultado, "Valor final a ser pago:",
                formataValor(dto.getEmprestimoRequisicao().getValorFinal()), BaseColor.BLACK);
        // PENSAR NOS JUROS********
        // O que o PDF deve mostrar://
        // resultado das operações de crédito inclusas (se aprovadas ou não)
        // com os dados principais dos clientes
        // ***datas da aprovação****, modalidades do empréstimo, **prazos, taxas de
        // juros******,
        // tributos,
        // valores aprovados para crédito e valores finais a serem pagos

        // Adiciona a tabela ao documento PDF
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
