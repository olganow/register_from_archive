package main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class InMemoryArchive {
    // private final File historyFile;
    private static final String TITLE_LINE = "номер_кв, доля, площадь_кв, фамилия,имя,отчество\n";

    File ownersFile = new File("src/main/resources/owners.csv");

    public String getFileName(String zipPackagePath, String partOfName) throws IOException {
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

    protected void save(List<Owner> owners) {
        try (Writer fileWriter = new FileWriter(ownersFile)) {

            fileWriter.write(TITLE_LINE);

            for (Owner owner : owners) {
                fileWriter.write(toString(owner) + "\n");
            }

        } catch (IOException ex) {
            throw new SaveException("Запись не корректная");
        }
    }

    private String toString(Owner owner) {
        return String.format("%d,%s,%s,%s,%s,%s",
                owner.getFlatNumber(),
                owner.getShareInFlat(),
                owner.getArea(),
                owner.getSurname(),
                owner.getName(),
                owner.getPatronymic()
        );
    }


}
