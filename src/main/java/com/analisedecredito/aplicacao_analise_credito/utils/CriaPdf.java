package com.analisedecredito.aplicacao_analise_credito.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public class CriaPdf {
    
    public ByteArrayOutputStream criaPdfImprimir
    (EmprestimoResultadoDto emprestimoResultadoDto) 
    throws DocumentException, FileNotFoundException{
        return iniciarPdf();
    }

    public ByteArrayOutputStream iniciarPdf() 
    throws DocumentException, FileNotFoundException{
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();
                
        Document documentoPdf = new Document();
        PdfWriter writer = PdfWriter.getInstance(documentoPdf, new
        FileOutputStream("C:\\Encrypted.pdf"));
       // PdfWriter writer = PdfWriter.getInstance(documentoPdf, baos);
    
        documentoPdf.open();
        documentoPdf.close();
        
        return baos;
    }

    public void addCabecalho(){

    }
}
