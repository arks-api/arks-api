package org.cbsa.api.bgmapr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cbsa.api.conf.ConfigCBSI;

public class WordCount {

	public WordCount() {

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

			System.out.println("job_" + inputPathList.get(i) + " called");
			jobManager.createJob(inputPathList.get(i), outputPathList.get(i));
		}

	}
}