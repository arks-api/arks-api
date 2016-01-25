package org.wordcounthbase;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.cbsa.api.model.Keyword;

public class PdfReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    private LongWritable total = new LongWritable();

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values,
            Context context) throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        int sum = 0;
        for (LongWritable value : values) {
            sum += value.get();
        }

        total.set(sum);
        String strKey = key.toString();
        strKey = strKey.trim();
        strKey = strKey.replaceAll("(\n)", "#");

        StringTokenizer stringTokenizer = new StringTokenizer(strKey, "#");
        while (stringTokenizer.hasMoreTokens()) {
            String subKey = stringTokenizer.nextToken();
            JobManager.keywordList.add(new Keyword(subKey.toString(), String
                    .valueOf(sum)));

            context.write(new Text(subKey), total);
        }
    }
}