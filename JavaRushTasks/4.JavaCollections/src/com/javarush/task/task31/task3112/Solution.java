package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* 
Загрузчик файлов
*/

public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt", Paths.get("D:/MyDownloads"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        URL url = new URL(urlString);
        Path urlFileName = Paths.get(url.getPath());
        Path download = downloadDirectory.resolve(urlFileName.getFileName());
        Path tempFile = Files.createTempFile("temp", ".tmp");
        try(InputStream in = url.openStream()){
            Files.copy(in, tempFile);
        }
        if(tempFile != null) {
            download = Files.move(tempFile, downloadDirectory.resolve(download), StandardCopyOption.REPLACE_EXISTING);
            // implement this method
        }
        return download;
    }
}
