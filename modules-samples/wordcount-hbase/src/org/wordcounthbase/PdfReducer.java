package org.wordcounthbase;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.cbsa.api.model.Keyword;

public class PdfReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values,
            Context context) throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        int sum = 0;
        for (LongWritable value : values) {
            sum += value.get();
        }
        context.write(key, new LongWritable(sum));
        JobManager.keywordList.add(new Keyword(key.toString(), String
                .valueOf(sum)));
    }
}
