package capstone;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/** * HBase DAO class that provides different operational handlers. */
public class HbaseDAO {
   /* public static void main(String[] args) throws Exception {
	//getScore(null);
	getLookupData(null);
    }*/

  public static void getLookupData(TransactionData transactionData) throws IOException {
      Admin hBaseAdmin1 = HbaseConnection.getHbaseAdmin();
      try {
	  /*TransactionData data = new TransactionData();
	  data.setCardId(6591175617713393l);*/
	  Get g = new Get(Bytes.toBytes(String.valueOf(transactionData.getCardId())));
	 // Get g = new Get(Bytes.toBytes("6591175617713393")); SCORE: 568 UCL: 8647587.0

	  Table htable = hBaseAdmin1.getConnection().getTable(TableName.valueOf("card_lookup_table"));
	  Result result = htable.get(g);

	      // Reading values from Result class object
	      byte [] score = result.getValue(Bytes.toBytes("clt_details"),Bytes.toBytes("score"));

	      byte [] ucl = result.getValue(Bytes.toBytes("clt_details"),Bytes.toBytes("ucl"));
	      
	      byte [] lastTrasDate = result.getValue(Bytes.toBytes("clt_details"),Bytes.toBytes("transaction_dt"));
	      
	      byte [] lastPostcode = result.getValue(Bytes.toBytes("clt_details"),Bytes.toBytes("postcode"));

	      transactionData.setScore(Bytes.toString(score));
	      transactionData.setUcl(Bytes.toString(ucl));
	      if(null!=lastPostcode) {
	      transactionData.setLastPostCode(Bytes.toLong(lastPostcode));
	      }
	      if(null!=lastTrasDate) {
	      transactionData.setLastTransDate(new Timestamp(Bytes.toLong(lastTrasDate)));
	      }
	      System.out.println("SCORE: " + transactionData.getScore() + " UCL: " + transactionData.getUcl());	
	     // htable.close();
      
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
      }
    }
  
public static void insertTransactionDataList(TransactionData tran) throws IOException{
    Admin hBaseAdmin1 = HbaseConnection.getHbaseAdmin();
    Table htable = hBaseAdmin1.getConnection().getTable(TableName.valueOf("card_transactions"));

    	    // Define row key
	    Put p = new Put(Bytes.toBytes(tran.getCardId()));

	    // p.addColumn(byte[] ColumnFamily, byte[] CoulmnQualifier, long TimeStamp, byte[] value);
	    p.addColumn(Bytes.toBytes("card_tran_det"), Bytes.toBytes("member_id"), Bytes.toBytes(String.valueOf(tran.getMemberId())));
	    p.addColumn(Bytes.toBytes("card_tran_det"), Bytes.toBytes("amount"), Bytes.toBytes(String.valueOf(tran.getAmont())));
	    p.addColumn(Bytes.toBytes("card_tran_det"), Bytes.toBytes("postcode"), Bytes.toBytes(String.valueOf(tran.getPostCode())));
	    p.addColumn(Bytes.toBytes("card_tran_det"), Bytes.toBytes("pos_id"), Bytes.toBytes(String.valueOf(tran.getPosId())));
	    p.addColumn(Bytes.toBytes("card_tran_det"), Bytes.toBytes("transaction_dt"), Bytes.toBytes(String.valueOf(tran.getTransactionDate())));
	    p.addColumn(Bytes.toBytes("card_tran_det"), Bytes.toBytes("status"), Bytes.toBytes(String.valueOf(tran.getStatus())));
   
    // add the List to table
    htable.put(p);
    System.out.println("Table is Populated for card id ...  " + tran.getCardId());
    
    
}
  
}
