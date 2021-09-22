package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* 
Что внутри папки?
*/

public class Solution {
    static int folders = 0;
    static int files = 0;
    static long fileSize = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        Path path = Paths.get(line);
        if(!Files.isDirectory(path)) {
            System.out.println(path.normalize() + " - не папка");
            return;
        }
        Files.walkFileTree(path, new Visitor());
        System.out.println("Всего папок - " + (folders-1));
        System.out.println("Всего файлов - " + files);
        System.out.println("Общий размер - " + fileSize);




    }
    public static class Visitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            folders++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            files++;
            fileSize += attrs.size();
            return FileVisitResult.CONTINUE;
        }
    }


}
