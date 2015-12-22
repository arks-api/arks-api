import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public class WordCount 
{
  private static final FlatMapFunction<String, String> WORDS_EXTRACTOR = new FlatMapFunction<String, String>() 
  {
	private static final long serialVersionUID = 1L;

		@Override
        public Iterable<String> call(String s) throws Exception {
          return Arrays.asList(s.split(" "));
        }
  };

  private static final PairFunction<String, String, Integer> WORDS_MAPPER = new PairFunction<String, String, Integer>() 
  {
		private static final long serialVersionUID = 1L;

		@Override
        public Tuple2<String, Integer> call(String s) throws Exception 
        {
          return new Tuple2<String, Integer>(s, 1);
        }
  };

  private static final Function2<Integer, Integer, Integer> WORDS_REDUCER = new Function2<Integer, Integer, Integer>() 
  {
	  	private static final long serialVersionUID = 1L;

		@Override
        public Integer call(Integer a, Integer b) throws Exception 
		{
          return a + b;
        }
  };

  public static void main(String[] args) 
  {
	  SparkConf conf = new SparkConf();
	  conf.setAppName("Wordcount Background");
	  conf.setMaster("local");
    
	  
	  JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(15));
	  
	  
	  JavaDStream<String> lines = ssc.textFileStream("/home/rahul/DATASET");
	  JavaDStream<String> words = lines.flatMap(WORDS_EXTRACTOR);
	  JavaPairDStream<String, Integer> pairs = words.mapToPair(WORDS_MAPPER);
	  JavaPairDStream<String, Integer> counter = pairs.reduceByKey(WORDS_REDUCER);
	  
	  counter.print();
	  
	  ssc.start();
	  
	  ssc.awaitTermination();
	  

	  /*JavaRDD<String> file = context.textFile("/home/rahul/Desktop/palestine.txt");
	  JavaRDD<String> words = file.flatMap(WORDS_EXTRACTOR);
	  JavaPairRDD<String, Integer> pairs = words.mapToPair(WORDS_MAPPER);
	  JavaPairRDD<String, Integer> counter = pairs.reduceByKey(WORDS_REDUCER);
	  counter.saveAsTextFile("/home/rahul/Desktop/wc"); 
	  context.close();*/
  }
}
