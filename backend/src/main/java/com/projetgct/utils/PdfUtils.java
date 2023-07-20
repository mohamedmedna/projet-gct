package com.projetgct.utils;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.text.PDFTextStripper;

import com.projetgct.entities.Formulaire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PdfUtils {
	public static String loadOriginalPdf(byte[] pdfContent) {
	    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfContent)) {
	        PDDocument document = PDDocument.load(inputStream);

	        PDFTextStripper stripper = new PDFTextStripper();
	        String text = stripper.getText(document);

	        document.close();

	        return text;
	    } catch (IOException e) {
	        System.err.println("Erreur lors du chargement du PDF : " + e.getMessage());
	        return null;
	    }
	}

    
    public static byte[] generateNewPdf(byte[] originalPdfContent, Formulaire formulaire) throws IOException {
        String originalPdfText = PdfUtils.loadOriginalPdf(originalPdfContent);

        // Use regex to find placeholders in the original PDF
        String regexNumConsultation = "#numConsultation";
        String regexTitreConsultation = "#titreConsultation";
        String regexObjetConsultation = "#objetConsultation";
        String regexConditionsParticipation = "#conditionsParticipation";
        String regexDelaiLivraison = "#delaiLivraison";
        String regexDureeGarantie = "#dureeGarantie";

        StringBuilder updatedPdfText = new StringBuilder(originalPdfText);

        if (originalPdfText.contains(regexNumConsultation)) {
            Matcher matcher = Pattern.compile(regexNumConsultation).matcher(updatedPdfText);
            updatedPdfText = new StringBuilder(matcher.replaceFirst(formulaire.getNumConsultation()));
        }

        if (originalPdfText.contains(regexTitreConsultation)) {
            Matcher matcher = Pattern.compile(regexTitreConsultation).matcher(updatedPdfText);
            updatedPdfText = new StringBuilder(matcher.replaceFirst(formulaire.getTitreConsultation()));
        }

        if (originalPdfText.contains(regexObjetConsultation)) {
            Matcher matcher = Pattern.compile(regexObjetConsultation).matcher(updatedPdfText);
            updatedPdfText = new StringBuilder(matcher.replaceFirst(formulaire.getObjetConsultation()));
        }

        if (originalPdfText.contains(regexConditionsParticipation)) {
            Matcher matcher = Pattern.compile(regexConditionsParticipation).matcher(updatedPdfText);
            updatedPdfText = new StringBuilder(matcher.replaceFirst(formulaire.getConditionsParticipation()));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (originalPdfText.contains(regexDelaiLivraison)) {
            Matcher matcher = Pattern.compile(regexDelaiLivraison).matcher(updatedPdfText);
            updatedPdfText = new StringBuilder(matcher.replaceFirst(dateFormat.format(formulaire.getDelaiLivraison())));
        }

        if (originalPdfText.contains(regexDureeGarantie)) {
            Matcher matcher = Pattern.compile(regexDureeGarantie).matcher(updatedPdfText);
            updatedPdfText = new StringBuilder(matcher.replaceFirst(dateFormat.format(formulaire.getDureeGarantie())));
        }

        byte[] updatedPdfContent;
        try (PDDocument document = PDDocument.load(originalPdfContent)) {
            PDPage page = document.getPage(0);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                InputStream fontStream = PdfUtils.class.getResourceAsStream("/fonts/DejaVuSans.ttf");
                if (fontStream == null) {
                    throw new IOException("Font file not found.");
                }
                // Load the font from the fontStream
                PDFont font = PDType0Font.load(document, fontStream);
                contentStream.setFont(font, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText(updatedPdfText.toString());
                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            updatedPdfContent = outputStream.toByteArray();
        }

        return updatedPdfContent;
    
	}





    


	
	
  
    }

