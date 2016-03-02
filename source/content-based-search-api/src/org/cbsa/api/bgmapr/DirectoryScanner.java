package org.cbsa.api.bgmapr;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

public class DirectoryScanner {

    /*
     * public static List<File> getFileList(String directoryPath) { List<File>
     * fileList = new ArrayList<File>(); addTree(new File(directoryPath),
     * fileList); return fileList; }
     * 
     * private static void addTree(File file, Collection<File> all) { File[]
     * children = file.listFiles(); if (children != null) { for (File child :
     * children) { all.add(child); addTree(child, all); } } }
     */

    private static List<String> fileList = new ArrayList<String>();

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
    }
}