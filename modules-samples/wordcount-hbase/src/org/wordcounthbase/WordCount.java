package org.wordcounthbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.mapreduce.Job;

public class WordCount {

    public WordCount() {

    }

    public List<String> wordCountHBase() throws ClassNotFoundException,
            IOException, InterruptedException {

        List<String> pagesList = new ArrayList<String>();

        JobManager jobManager = new JobManager();
        Job sampleJob = jobManager.createJob();

        try {

            System.out.println(sampleJob.waitForCompletion(true));

        } catch (IOException | ClassNotFoundException | InterruptedException e) {

            e.printStackTrace();
        }

        return pagesList;
    }

}
