package org.cbsa.api.bgmapr;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.cbsa.api.conf.ConfigCBSI;
import org.cbsa.api.conf.StorageModes;

public class DirectoryScanner {

    private static final Logger logger = Logger
            .getLogger(DirectoryScanner.class.getName());
    private static List<File> fileList;
    private static List<String> filePaths = new ArrayList<String>();

    static {

        logger.setLevel(Level.INFO);
    }

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

        if (ConfigCBSI.getStorageMode().equals(StorageModes.LOCAL_STORAGE)) {

            logger.info("reading local storage");
            getFiles(directoryPath);

            for (File file : fileList) {
                filePaths.add(file.getAbsolutePath());
            }

        } else if (ConfigCBSI.getStorageMode()
                .equals(StorageModes.HDFS_STORAGE)) {

            logger.info("reading hdfs storage");
            readFilesRecursively(directoryPath);
        }

        return filePaths;
    }

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
                filePaths.add(paths[i].toString());
            }

        }
    }
}