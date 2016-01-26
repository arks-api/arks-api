package org.wordcounthbase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DirectoryScanner {

    public static List<File> getFileList(String directoryPath) {
        List<File> fileList = new ArrayList<File>();
        addTree(new File(directoryPath), fileList);
        return fileList;
    }

    private static void addTree(File file, Collection<File> all) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                all.add(child);
                addTree(child, all);
            }
        }
    }
}