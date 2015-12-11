package com.wordcount.filtered;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCountFilter {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {

		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();

		if (otherArgs.length < 2) {

			System.err.println("Usage: WordCount input output");
			System.exit(1);
		}

		Job wordCountJob = new Job(conf, "WordcountFiltered");
		wordCountJob.setJarByClass(WordCountFilter.class);
		wordCountJob.setMapperClass(TokenizerMapper.class);
		wordCountJob.setCombinerClass(CountToSumReducer.class);
		wordCountJob.setReducerClass(CountToSumReducer.class);
		wordCountJob.setOutputKeyClass(Text.class);
		wordCountJob.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(wordCountJob, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(wordCountJob, new Path(otherArgs[1]));
		System.exit(wordCountJob.waitForCompletion(true) ? 0 : 1);

	}

}
