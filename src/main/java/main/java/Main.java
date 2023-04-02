package main.java;

import java.io.*;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String zipPackagePath = "src/main/resources/attachment (1).zip";
        String partOfName = "[0].pdf";
        String pathFileToBeParsed = "src/main/resources/temp/report-a8ed2efc-e4e3-4700-85b3-993578b91cad-OfSite-2023-02-22-1492774-78-01[0].xml";
        String fileToBeExtracted = "";

        InMemoryArchive inMemoryArchive = new InMemoryArchive();
        XMLParser xmlParser = new XMLParser();
        inMemoryArchive.getFileName(zipPackagePath, partOfName);
        xmlParser.xmlParser(pathFileToBeParsed);
        List<Owner> owners = xmlParser.getOwners();
        inMemoryArchive.save(owners);
    }
}
