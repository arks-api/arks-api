package org.wordcounthbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfRecordReader extends RecordReader<LongWritable, Text> {
    private String[] lines = null;
    private LongWritable key = null;
    private Text value = null;

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public LongWritable getCurrentKey() throws IOException,
            InterruptedException {
        // TODO Auto-generated method stub
        return key;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void initialize(InputSplit genericSplit, TaskAttemptContext context)
            throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        /*
         * PDFParser parser = new PDFParser(new FileInputStream(file));
         * parser.parse(); cosDoc = parser.getDocument(); pdfStripper = new
         * PDFTextStripper(); pdDoc = new PDDocument(cosDoc); String parsedText
         * = pdfStripper.getText(pdDoc); //System.out.println(parsedText);
         */
        FileSplit split = (FileSplit) genericSplit;
        Configuration job = context.getConfiguration();
        final Path file = split.getPath();

        FileSystem fs = file.getFileSystem(job);
        FSDataInputStream fileIn = fs.open(split.getPath());
        PDDocument pdf = null;
        String parsedText = null;
        PDFTextStripper stripper;
        pdf = PDDocument.load(fileIn);
        stripper = new PDFTextStripper();
        parsedText = stripper.getText(pdf);
        this.lines = parsedText.split("\n");
        // System.out.println(parsedText);

    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        if (key == null) {
            key = new LongWritable();
            key.set(1);
            value = new Text();
            value.set(lines[0]);
        } else {
            int temp = (int) key.get();
            if (temp < (lines.length - 1)) {
                int count = (int) key.get();
                value = new Text();
                value.set(lines[count]);
                count = count + 1;
                key = new LongWritable(count);
            } else {
                return false;
            }
        }
        if (key == null || value == null) {
            return false;
        } else {
            return true;
        }
    }

}