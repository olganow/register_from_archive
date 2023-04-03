package main.java;

import main.util.SaveException;
import main.util.XMLParser;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class InMemoryArchive {
    // private final File historyFile;
    private static final String TITLE_LINE = "номер_кв, доля, площадь_кв, фамилия,имя,отчество\n";
    private static String pathToFolderWithRawData = "src/main/resources/raw_data";
    private static File ownersFile = new File("src/main/resources/owners.csv");
    private static String partOfName = "[0].xml";

    XMLParser xmlParser = new XMLParser();

    //перебор по папке с необработанными архивами
    public void getFileFromZips() {
        File path = new File(pathToFolderWithRawData);

        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) { //this line weeds out other directories/folders
                System.out.println(files[i]);
                try {
                    getFileName(files[i].getPath());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //определение файла xml
    public String getFileName(String zipPackagePath) throws IOException {
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
                System.out.println(entryName);////
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
