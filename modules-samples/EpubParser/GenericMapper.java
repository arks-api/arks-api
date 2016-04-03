import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class GenericMapper extends Mapper<LongWritable,Text,Text,LongWritable>
{
	private Text word = new Text();
	private final static LongWritable one  = new LongWritable(1);
	
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		// TODO Auto-generated method stub
		String line = value.toString().replaceAll("(?!\")\\p{Punct}","").replaceAll("\"","").replaceAll("[0-9]*","").toLowerCase();
		StringTokenizer tokenizer = new StringTokenizer(line);/*value.toString(), " \t\n\r\f,.()<>/\\#:;?![]'"*/
		while (tokenizer.hasMoreTokens())
		{
			word.set(tokenizer.nextToken());
			context.progress();
			context.write(word, one);
		}
}
}
