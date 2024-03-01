package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        System.out.println();
        System.out.println("Read directory");
        System.out.println();

        File dir = new File("./src");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        System.out.println();
        System.out.println("Read inputstream");
        System.out.println();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
        System.out.println("Get dir");
        MainFile mainFile = new MainFile();
        mainFile.getDir(dir, "");
    }

    public void getDir(File dir, String space) throws IOException {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(space + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(space + "Directory: " + file.getName());
                    getDir(file, space + "   ");
                }
            }
        }

    }
}
