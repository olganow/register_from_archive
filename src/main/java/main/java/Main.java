package main.java;

import main.util.XMLParser;

import java.io.*;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String zipPackagePath = "src/main/resources/raw_data/attachment (1).zip";

        //String pathFileToBeParsed = "src/main/resources/temp/report-a8ed2efc-e4e3-4700-85b3-993578b91cad-OfSite-2023-02-22-1492774-78-01[0].xml";
        String pathFileToBeParsed = "src/main/resources/temp/report-2d8a0d89-8e56-41b5-a941-5647c07a062d-OfSite-2023-02-22-1494271-78-01[0].xml";
        String pathFileToBeParsed1 = "src/main/resources/raw_data/attachment (2).zip/" + pathFileToBeParsed;


        String fileToBeExtracted = "";

        InMemoryArchive inMemoryArchive = new InMemoryArchive();
        XMLParser xmlParser = new XMLParser();
        xmlParser.xmlParser(pathFileToBeParsed);
        List<Owner> owners = xmlParser.getOwners();
        inMemoryArchive.save(owners);


        inMemoryArchive.getFileFromZips();

    }
}
