package capstone;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaSparkStreaming {

    public static void main(String[] args) throws Exception {

        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);

        SparkConf sparkConf = new SparkConf().setAppName("KafkaSparkStreamingDemo").setMaster("local");

        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "100.24.223.181:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "firozkafkaspark8");
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", true);

        Collection<String> topics = Arrays.asList("transactions-topic-verified");

        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        JavaDStream<String> jds = stream.map(x -> x.value());

        jds.foreachRDD(x -> System.out.println(x.count()));

        jds.foreachRDD(new VoidFunction<JavaRDD<String>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(JavaRDD<String> rdd) {
        	System.out.println("...........................................................");
                rdd.foreach(jsonString -> 
                {
                    System.out.println(jsonString);
                    TransactionData cardTransaction = new ObjectMapper().readValue(jsonString, TransactionData.class);
                    System.out.println("TransactionDate : .......................START"); 
                    System.out.println(cardTransaction);
                    System.out.println("TransactionDate : .......................END"); 
                    
                    //Fetch lookup table for this transaction 
                    
                    //Check UCL 
                    
                    //Check Score < 200 
                    
                    //Check zip code distance 
                    

                });

            }
        });

        jssc.start();
        // / Add Await Termination to respond to Ctrl+C and gracefully close Spark
        // Streams
        jssc.awaitTermination();

    }
}