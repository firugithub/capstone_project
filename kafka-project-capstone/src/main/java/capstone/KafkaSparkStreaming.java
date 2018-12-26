package capstone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
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

	DistanceUtility disUtil=new DistanceUtility();

        SparkConf sparkConf = new SparkConf().setAppName("KafkaSparkStreamingDemo").setMaster("local");

        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "100.24.223.181:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "firozkafkaspark24");
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", true);

        Collection<String> topics = Arrays.asList("transactions-topic-verified");

        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        JavaDStream<String> jds = stream.map(x -> x.value());

        jds.foreachRDD(x -> System.out.println(x.count()));
        List<TransactionData> listTransactions = new ArrayList<>();
        

        jds.foreachRDD(new VoidFunction<JavaRDD<String>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(JavaRDD<String> rdd) {
        	
                rdd.foreach(jsonString -> 
                {
                    System.out.println(jsonString);
                    TransactionData cardTransaction = new ObjectMapper().readValue(jsonString, TransactionData.class);
                   
                    listTransactions.add(cardTransaction);
                    System.out.println("...........................................................");
                  //Fetch lookup table for this transaction 
                    HbaseDAO.getLookupData(cardTransaction);
                    System.out.println(cardTransaction);
                    System.out.println("...........................................................");
                    
                    
                    //Check UCL 
                    //Check Score < 200 
                    //Check zip code distance
                    String status = "GENUINE";
                    if(Long.valueOf(cardTransaction.getUcl()) < cardTransaction.getAmont()  || Integer.valueOf(cardTransaction.getScore()) < 200  || 
                	    checkDistanceCriteria(cardTransaction)) {
                	status= "FRAUD";
                	
                    }
                    
                    cardTransaction.setStatus(status);
                    System.out.println("Transaction Status : ......................." + status); 
                    
                    //insert data into HBASE
                    System.out.println("Insert Transaction into HBASE"); 
                    HbaseDAO.insertTransactionDataList(cardTransaction);
                    System.out.println("Insertion Completed into HBASE"); 

                });
                

            }

      private boolean checkDistanceCriteria(TransactionData cardTransaction) {
	
	double distance = disUtil.getDistanceViaZipCode(String.valueOf(cardTransaction.getPostCode()), String.valueOf(cardTransaction.getLastPostCode()));
	long diffMs = cardTransaction.getTransactionDate().getTime() - cardTransaction.getLastTransDate().getTime();
	long diffSec = diffMs / 1000;
	long min = diffSec / 60;
	long sec = diffSec % 60;
	long totSec = min*60 + sec;
	return (distance/totSec < 0.25);
      }
        });

        jssc.start();
        // / Add Await Termination to respond to Ctrl+C and gracefully close Spark
        // Streams
       jssc.awaitTermination();

    }

}