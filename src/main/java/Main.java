package main.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
//https://javascopes.com/working-with-zip-files-in-java-2aaed9d3/
//https://releases.groupdocs.com/ru/parser/java/

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "src/main/resources/attachment (1).zip";
        String partOfName = "[0].pdf";
        File file;

        ZipFile zipFile = new ZipFile(path);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            // InputStream stream = zipFile.getInputStream(entry);
            String entryName = entry.getName();
            // 1. Найти нужный файл в архиве
            if (entryName.endsWith(partOfName)) {
                System.out.println(entryName);

                // 2. Извлечь из файла номер квартиры - сейчас смотрю какие библиотеки для этого нужны
                //  3. Вынуть файл из Архива и положить в папку Result
                //  4. Переименовать: имя файла = номер квартиры

            }
        }
    }
}
