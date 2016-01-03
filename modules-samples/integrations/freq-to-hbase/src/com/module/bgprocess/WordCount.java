package com.module.bgprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public class WordCount implements StopWords {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        final MetadataManager metadataManager = new MetadataManager();

        final FileMetadata javaBookData = new FileMetadata();
        javaBookData.setFileName("java complete reference");
        javaBookData.setFileSize("50");
        javaBookData.setTotalPages("600");

        ArrayList<String> javaBookDomains = new ArrayList<String>();
        javaBookDomains.add("java");
        javaBookDomains.add("programming");
        javaBookDomains.add("coding");

        final List<Keyword> javaBookKeywords = new ArrayList<Keyword>();

        javaBookData.setFileDomains(javaBookDomains);
        javaBookData.setKeywords(javaBookKeywords);

        SparkConf conf = new SparkConf();
        conf.setAppName("Wordcount Background");
        conf.setMaster("local[2]");

        JavaStreamingContext ssc = new JavaStreamingContext(conf,
                Durations.minutes(1));

        // GET THE FILE INTO RDD
        JavaDStream<String> lines = ssc
                .textFileStream("hdfs://localhost:9000/user/aditya/documents");

        // APPLY FLAT MAP TO RETURN SEQUENCE
        JavaDStream<String> words = lines
                .flatMap(new FlatMapFunction<String, String>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public Iterable<String> call(String s) throws Exception {
                        // TODO Auto-generated method stub
                        return Arrays.asList(s.toLowerCase()
                                .replaceAll("\\p{Punct}|\\d", "").split(" "));
                    }
                });

        // APPLY FILTER TO CHECK IF WORD NOT NULL
        JavaDStream<String> filter = words
                .filter(new Function<String, Boolean>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public Boolean call(String s) throws Exception {
                        // TODO Auto-generated method stub
                        /* return (!s.isEmpty()); */

                        Set<String> patternsToSkip = new HashSet<String>();

                        for (int i = 0; i < STOPWORDS1.length; i++) {
                            patternsToSkip.add(STOPWORDS1[i]);
                        }
                        for (int i = 0; i < STOPWORDS2.length; i++) {
                            patternsToSkip.add(STOPWORDS2[i]);
                        }
                        if (s.isEmpty() || patternsToSkip.contains(s)) {
                            return false;
                        }
                        return true;

                    }
                });

        // MAPPER WHICH MAPS EACH WORD TO 1
        JavaPairDStream<String, Integer> mapper = filter
                .mapToPair(new PairFunction<String, String, Integer>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public Tuple2<String, Integer> call(String s)
                            throws Exception {
                        // TODO Auto-generated method stub
                        return new Tuple2<String, Integer>(s, 1);
                    }
                });

        JavaPairDStream<String, Integer> reducer = mapper
                .reduceByKey(new Function2<Integer, Integer, Integer>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public Integer call(Integer a, Integer b) throws Exception {
                        // TODO Auto-generated method stub
                        return (a + b);
                    }
                });

        JavaPairDStream<Integer, String> wctocw = reducer
                .mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public Tuple2<Integer, String> call(
                            Tuple2<String, Integer> tuple2) throws Exception {
                        // TODO Auto-generated method stub
                        return tuple2.swap();
                    }
                });

        JavaPairDStream<Integer, String> sorted = wctocw
                .transformToPair(new Function<JavaPairRDD<Integer, String>, JavaPairRDD<Integer, String>>() {

                    @Override
                    public JavaPairRDD<Integer, String> call(
                            JavaPairRDD<Integer, String> t) throws Exception {
                        // TODO Auto-generated method stub
                        return t.sortByKey(false);
                    }
                });

        JavaPairDStream<String, Integer> cwtowc = sorted
                .mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {

                    @Override
                    public Tuple2<String, Integer> call(
                            Tuple2<Integer, String> arg0) throws Exception {
                        // TODO Auto-generated method stub
                        // System.out.println(arg0._1+" : "+arg0._2);
                        return arg0.swap();
                    }
                });

        cwtowc.foreach(new Function<JavaPairRDD<String, Integer>, Void>() {

            @Override
            public Void call(JavaPairRDD<String, Integer> rdd) throws Exception {
                // TODO Auto-generated method stub
                for (Tuple2<String, Integer> l : rdd.toArray()) {
                    // System.out.println(l._1 + ":" + l._2);
                    javaBookKeywords.add(new Keyword(l._1, String.valueOf(l._2)));

                }

                metadataManager.addNewFileMetadata(javaBookData);
                return null;
            }
        });

        ssc.start();

        ssc.awaitTermination();

        ssc.close();

    }
}
