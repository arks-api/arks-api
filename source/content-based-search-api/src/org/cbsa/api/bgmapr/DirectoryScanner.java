package org.cbsa.api.bgmapr;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

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

    public static List<File> getFileListTest(String directoryPath) {

        List<File> fileList = new ArrayList<File>();

        FileSystem hdfs = null;
        FileStatus[] fileStatus = null;

        // 1. Get the Configuration instance
        Configuration configuration = new Configuration();

        try {

            // 2. Get the instance of the HDFS
            hdfs = FileSystem.get(new URI("hdfs://localhost:9000"),
                    configuration);
            // 3. Get the meta data of the desired directory
            fileStatus = hdfs.listStatus(new Path(
                    "hdfs://localhost:9000/usr/lib"));

        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 4. Using FileUtil, getting the Paths for all the FileStatus
        Path[] paths = FileUtil.stat2Paths(fileStatus);

        // 5. Iterate through the directory and display the files in it
        System.out.println("***** Contents of the Directory *****");
        for (Path path : paths) {
            System.out.println(path);
        }

        return fileList;
    }
}