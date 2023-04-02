package main.java;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Deprecated
public class PDFParser {
    private ArrayList<Owner> owners = new ArrayList<>();
    private int appNumber;

    public void pdfParser(String path) {
        //
        String textFromPdf = null;
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(path));

            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                textFromPdf = stripper.getText(document);
            }
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // получить номер квартиры
        String adress = "Местоположение: Санкт-Петербург, ул. Курская, д. 110,  кв. ";
        String numberOfFlat = "";

        final String[] content = textFromPdf.split("\n");
        for (String lineFromString : content) {
            if (lineFromString.contains(adress)) {
                numberOfFlat = lineFromString.substring(adress.length());
                System.out.println("Номер квартиры: " + numberOfFlat);
            }
        }

    }

}

