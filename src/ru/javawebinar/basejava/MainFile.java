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

        File dir = new File("./src/ru/javawebinar/basejava");
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
        mainFile.getDir(new File("./"));
    }

    public void getDir(File dir) throws IOException {
        System.out.println(dir.getCanonicalPath());
        if (dir.isDirectory()) {
            String[] dirs = dir.list();
            if (dirs != null) {
                for (String file : dirs) {
                    getDir(new File(dir.getCanonicalPath() + "/" + file));
                }
            }
        }
    }
}
