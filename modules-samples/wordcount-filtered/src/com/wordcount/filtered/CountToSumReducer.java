package com.wordcount.filtered;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CountToSumReducer extends
		Reducer<Text, IntWritable, Text, IntWritable> {

	private IntWritable result = new IntWritable();

	@Override
	protected void reduce(Text key, Iterable<IntWritable> value,
			Context context)
			throws IOException, InterruptedException {

		/*
		 * We will use Loop to combine words word count from mapper output
		 * e.g <This, 1> <is, 1> <Hello, 1> <World, 1> <Hello, 1> <Hadoop, 1>
		 * Output after reducing <This, 1> <is, 1> <Hello, 2> <World, 1> <Hadoop, 1>
		 */

		int sum = 0;
		for(IntWritable val : value){

			sum += val.get();
		}

		result.set(sum);
		context.write(key, result);
	}
}
