package org.cbsa.api.bgmapr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.cbsa.api.controller.metadata.Stopwords;

public class PdfMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    private Text word = new Text();
    private final static LongWritable one = new LongWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        /*
         * String line = value.toString().replaceAll("\\.", " ")
         * .replaceAll("(?!\")\\p{Punct}", "").replaceAll("\"", "")
         * .replaceAll("[0-9]*", "").toLowerCase(); StringTokenizer tokenizer =
         * new StringTokenizer(line);
         *
         * while (tokenizer.hasMoreTokens()) { word.set(tokenizer.nextToken());
         * context.progress(); context.write(word, one); }
         */

        String text = value.toString();
        String[] words = text.replaceAll("\\p{Punct}|\\d", "").split(" ");
        for (String i : words) {
            if (!i.equals("") && !i.equals(null)
                    && !Stopwords.genStopWordsSet().contains(i)) {
                word.set(i);
                context.write(word, one);
            }
        }
    }
}