package main.java;

import main.util.SaveException;
import main.util.XMLParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class InMemoryArchive {
    private static final String TITLE_LINE = "номер_кв, доля, площадь_кв, фамилия,имя,отчество\n";
    private static final String INPUT_FOLDER_ZIP_FILES = "src/main/resources/input_with_zips";
    private static final String TEMP_FOLDER = "src/main/resources/temp";
    private static final String OUTPUT_FOLDER = "src/main/resources/output";
    private static File ownersFile = new File("src/main/resources/owners.csv");
    private static String partOfName = "[0].xml";
    private static String path = "src/main/resources/temp/java11.xml";


    //перебор по папке с необработанными архивами
    public void getFileFromZips() {
        XMLParser xmlParser = new XMLParser();
        File path = new File(INPUT_FOLDER_ZIP_FILES);
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) { //this line weeds out other directories/folders
                System.out.println("имя архива: " + files[i]);
                try {
                    String getFileNameToBeParsed = getFileName(files[i].getPath());

                    //распаковка архива
                    unzipFolder(Path.of(String.valueOf(files[i])), Path.of(TEMP_FOLDER));
                    String pathFileToBeParsed = TEMP_FOLDER + "/" + getFileNameToBeParsed;

                    // прочитать файл с определенным именем и получить номер квартиры
                    xmlParser.xmlParser(pathFileToBeParsed);
                    int appNumber = xmlParser.getAppNumber();
                    save(xmlParser.getOwners());

                    //перемещение
                    String pathFileToRemovedFile = TEMP_FOLDER + "/" + appNumber + ".xml";
                    renameFile(pathFileToBeParsed, pathFileToRemovedFile);
 //                   String checkedFileName = checkCreatedFileName(pathFileToRemovedFile, appNumber);
 //                   removeFile(pathFileToRemovedFile, checkedFileName);
                    String checkedFileName = appNumber + ".xml";
                    removeFile(pathFileToRemovedFile, checkedFileName);

                    //очистка временной папки
                    deleteFolder(new File(TEMP_FOLDER));

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
                System.out.println("имя xml файла: " + entryName);
            }
        }
        return fileToBeExtracted;
    }

    //распаковка архива
    public static void unzipFolder(Path source, Path target) throws IOException {

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {

            // list files in zip
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {

                boolean isDirectory = false;
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }

                Path newPath = zipSlipProtect(zipEntry, target);

                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }

                    // copy files, nio
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }

                zipEntry = zis.getNextEntry();

            }
            zis.closeEntry();

        }

    }

    // protect zip slip attack
    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
            throws IOException {

        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }

        return normalizePath;
    }


    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
    }

    //рекурсия пока не определено имя файла
    static String checkCreatedFileName(String filename, int appNumber) {
        File file = new File(filename);
        String newPathFile = OUTPUT_FOLDER;
        String newFilename = "";
        File dir = new File(newPathFile);
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.getName().equals(file.getName())) {
                    System.out.println("вызвали рекурсию, переименовали в дубликат " + appNumber + "-1" + ".xml");
                    newPathFile = newPathFile + file.getName().substring(0, file.getName().length() - 4) + "-1" + ".xml";
                    System.out.println(newPathFile);
                    newFilename = file.getName().substring(0, file.getName().length() - 4) + "-1" + ".xml";
                    checkCreatedFileName(newPathFile, appNumber);
                }
            }
        }
        return newFilename;
    }

    public void renameFile(String fileName, String newFileName) {
        //переименование и перемещение файла
        File file = new File(fileName);
        File newFile = new File(newFileName);
        if (file.renameTo(newFile)) {
            System.out.println("Файл переименован успешно");
        } else {
            System.out.println("Файл не был переименован");
        }
    }

    public void removeFile(String currentPath, String checkedFileName) {
        //переименование и перемещение файла
        File file = new File(currentPath);
        File newFile = new File(OUTPUT_FOLDER + "/" + checkedFileName);
        if (file.renameTo(newFile)) {
            System.out.println("Файл перемещен успешно в output");
        } else {
            System.out.println("Файл не был перемещен");
        }
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
