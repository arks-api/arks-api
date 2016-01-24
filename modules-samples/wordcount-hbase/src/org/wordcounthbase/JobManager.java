package org.wordcounthbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.cbsa.api.controller.metadata.MetadataManager;
import org.cbsa.api.model.FileMetadata;
import org.cbsa.api.model.Keyword;

public class JobManager {

    private static final String PDFBOX_PATH = "/lib/pdfbox-1.8.10.jar";
    private static final String FONTBOX_PATH = "/lib/fontbox-1.8.10.jar";

    private static final String PDFBOX_PATH2 = "/home/aditya/apps-store/pdfbox-1.8.10/pdfbox-1.8.10.jar";
    private static final String FONTBOX_PATH2 = "/home/aditya/apps-store/pdfbox-1.8.10/fontbox-1.8.10.jar";

    private Job job;
    private String jobName = null;
    private String inputPath = null;
    public static List<Keyword> keywordList;

    static {

        keywordList = new ArrayList<Keyword>();
    }

    public Job createJob() throws ClassNotFoundException, IOException,
            InterruptedException {

        MetadataManager metadataManager = new MetadataManager();

        Configuration configuration = new Configuration();

        try {

            job = Job.getInstance(configuration, "WordCount");
            job.addFileToClassPath(new Path(PDFBOX_PATH2));
            job.addFileToClassPath(new Path(FONTBOX_PATH2));
            job.setJarByClass(this.getClass());

            job.setInputFormatClass(PdfInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);

            FileInputFormat.setInputPaths(job, new Path(
                    "/opt/dataset/Linux Kernel Development.pdf"));
            FileOutputFormat.setOutputPath(job,
                    new Path("/opt/dataset/results"));

            job.setMapperClass(PdfMapper.class);
            job.setCombinerClass(PdfReducer.class);
            job.setReducerClass(PdfReducer.class);

        } catch (IOException e) {

            e.printStackTrace();
        }

        if (job.waitForCompletion(true)) {

            FileMetadata fileMetadata = new FileMetadata("1",
                    "Linux Kernel Development.pdf",
                    "/opt/dataset/Linux Kernel Development.pdf", "50", "600",
                    "programming", keywordList);

            metadataManager.addNewFileMetadata(fileMetadata);
        }

        return job;
    }
}
