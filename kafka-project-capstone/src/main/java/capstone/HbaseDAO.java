package capstone;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.HTable;

/** * HBase DAO class that provides different operational handlers. */
public class HbaseDAO {
    public static void main(String[] args) throws Exception {
	getScore(null);
    }

  /**
   * * * @param transactionData * @return get member's score from look up HBase table. * @throws
   * IOException
   */
  public static Integer getScore(TransactionData transactionData) throws IOException {
    Admin hBaseAdmin1 = HbaseConnection.getHbaseAdmin();
    HTable table = null;
    try {
	
	// list the tables
	    Arrays.stream(hBaseAdmin1.listTables()).forEach(System.out::println);
	/*
      Get g = new Get(Bytes.toBytes("6598830758632447"));
      Result r = table.get(g);					

      byte [] value = r.getValue(Bytes.toBytes("card_tran_det"),Bytes.toBytes("member_id"));											
      byte [] value1 = r.getValue(Bytes.toBytes("card_tran_det"),Bytes.toBytes("amount"));											
        String valueStr = Bytes.toString(value);							

      String valueStr1 = Bytes.toString(value1);							
      System.out.println("GET: " +"member_id: "+ valueStr+"amount: "+valueStr1);												
     // table = new HTable(hBaseAdmin1.getConfiguration(), "member_lookup_table");
      //Table tab = hBaseAdmin1.getT
  //    HTableDescriptor table1 = new HTableDescriptor(TableName.valueOf("card_transactions"));
   //   System.out.println(table1.getTableName());
//      byte[] value = result.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("member_score"));
//      if (value != null) {
//        return Integer.parseInt(Bytes.toString(value));
//      }
    */} catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (table != null) table.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
