package com.wordcount.filtered;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

	private static final IntWritable one = new IntWritable(1);
	private Text word = new Text();

	@Override
	protected void map(Object key, Text value,
			Mapper<Object, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {

		/*
		 * We will use StringTokenizer to extract words from given line of input
		 * e.g 1 This is Hello World ( Here 1 is key and Statement is value )
		 * <This, 1> <is, 1> <Hello, 1> <World, 1>
		 */

		StringTokenizer stringTokenizer = new StringTokenizer(value.toString());
		while (stringTokenizer.hasMoreElements()) {

			String wordTemp = stringTokenizer.nextToken();

			if(wordTemp.equals("Java")){

				word.set(wordTemp);
				context.write(word, one);
			}
		}
	}

}
