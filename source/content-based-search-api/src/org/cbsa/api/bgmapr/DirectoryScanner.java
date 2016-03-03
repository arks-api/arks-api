package org.cbsa.api.bgmapr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DirectoryScanner {

    private static List<File> fileList;

    // private static List<File> fileList = new ArrayList<String>();

    private static List<File> getFiles(String directoryPath) {
        fileList = new ArrayList<File>();
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

    public static List<String> getFileList(String directoryPath) {
        List<String> filePaths = new ArrayList<String>();

        getFiles(directoryPath);

        for (File file : fileList) {
            filePaths.add(file.getAbsolutePath());
        }

        return filePaths;
    }

    /*
    private static void readFilesRecursively(String directoryPath) {

        FileSystem hdfs = null;
        FileStatus[] fileStatus = null;

        Configuration configuration = new Configuration();

        try {

            hdfs = FileSystem.get(new URI(directoryPath), configuration);
            fileStatus = hdfs.listStatus(new Path(directoryPath));

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        Path[] paths = FileUtil.stat2Paths(fileStatus);

        for (int i = 0; i < paths.length; i++) {
            if (fileStatus[i].isDirectory()) {
                readFilesRecursively(fileStatus[i].getPath().toString());
            } else {
                // System.out.println(paths[i]);
                fileList.add(paths[i].toString());
            }

        }
    }

    public static List<String> getFileList(String directoryPath) {

        readFilesRecursively(directoryPath);

        return fileList;
    }*/
}