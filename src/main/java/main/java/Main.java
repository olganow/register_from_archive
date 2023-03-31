package main.java;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Main {
    public static void main(String[] args) throws IOException {
        String zipPackagePath = "src/main/resources/attachment (1).zip";
        String partOfName = "[0].pdf";
        String fileToBeExtracted = "";

        //Создайте экземпляр PdfDocument
        //  PdfDocument doc=new PdfDocument();

        ZipFile zipFile = new ZipFile(zipPackagePath);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            // InputStream stream = zipFile.getInputStream(entry);
            String entryName = entry.getName();
            // 1. Найти нужный файл в архиве
            if (entryName.endsWith(partOfName)) {
                fileToBeExtracted = entryName;
                System.out.println(fileToBeExtracted);

            }
        }

        //
        String textFromPdf = null;
        String path = "src/main/resources/temp/report-2d8a0d89-8e56-41b5-a941-5647c07a062d-OfSite-2023-02-22-1494271-78-01[0].pdf";
        PDDocument document = PDDocument.load(new File(path));
        if (!document.isEncrypted()) {
            PDFTextStripper stripper = new PDFTextStripper();
            textFromPdf = stripper.getText(document);
        }
        document.close();


        // получить номер квартиры
        String adress = "Местоположение: Санкт-Петербург, ул. Кузнецовская, д. 10, литера. А, кв. ";
        String numberOfFlat = "";

            final String[] content = textFromPdf.split("\n");
            for (String lineFromString : content) {
                if (lineFromString.contains(adress)){
                    numberOfFlat = lineFromString.substring(adress.length());
                    System.out.println("Номер квартиры: " + numberOfFlat);
                }
            }

    }
}
