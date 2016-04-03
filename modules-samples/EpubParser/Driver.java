import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Driver 
{
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
	{
		Scanner scanner = new Scanner(System.in);
		
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration, "EPUB READ");
		//job.addFileToClassPath(new Path("/lib/pdfbox-app-1.8.9.jar"));
		job.setJarByClass(Driver.class);
		
		job.setInputFormatClass(EpubInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		System.out.println("Enter the Epub Path");
		FileInputFormat.setInputPaths(job,new Path(scanner.nextLine()));
		System.out.println("Enter the output Directory Name");
		FileOutputFormat.setOutputPath(job, new Path(scanner.nextLine()));
		
		job.setMapperClass(GenericMapper.class);
		job.setCombinerClass(GenericReducer.class);
		job.setReducerClass(GenericReducer.class);
		
		System.out.println(job.waitForCompletion(true));
		scanner.close();
	}
}