package org.cbsa.api.bgmapr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cbsa.api.conf.ConfigCBSI;

public class WordCount {

    private final Logger logger = Logger.getLogger(WordCount.class.getName());

    public WordCount() {
        logger.setLevel(Level.INFO);
    }

    public void wordCountHBase() throws ClassNotFoundException, IOException,
            InterruptedException {

        List<String> inputPathList = new ArrayList<String>();
        List<String> outputPathList = new ArrayList<String>();

        List<File> fileList = DirectoryScanner.getFileList(ConfigCBSI
                .getDatasetPath());

        for (int i = 0; i < fileList.size(); i++) {

            inputPathList.add(fileList.get(i).getAbsolutePath());
            outputPathList.add(ConfigCBSI.getWcResultPath() + i);

        }

        JobManager jobManager = new JobManager();

        for (int i = 0; i < inputPathList.size(); i++) {

            logger.info("job_" + inputPathList.get(i) + " called");
            jobManager.createJob(inputPathList.get(i), outputPathList.get(i));
        }

    }
}