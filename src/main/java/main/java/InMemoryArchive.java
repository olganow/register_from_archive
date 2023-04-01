package main.java;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class InMemoryArchive {

    public String getFileName (  String zipPackagePath, String partOfName) throws IOException {
        String fileToBeExtracted = "";
        ZipFile zipFile = new ZipFile(zipPackagePath);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            // InputStream stream = zipFile.getInputStream(entry);
            String entryName = entry.getName();
            // 1. Найти нужный файл в архиве
            if (entryName.endsWith(partOfName)) {
                fileToBeExtracted = entryName;
            }
        }

        return fileToBeExtracted;
    }


}
