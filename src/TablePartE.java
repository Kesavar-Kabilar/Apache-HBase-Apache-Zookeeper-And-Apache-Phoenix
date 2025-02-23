import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;

import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;

import org.apache.hadoop.hbase.util.Bytes;

public class TablePartE{

	public static void main(String[] args) throws IOException {

        Configuration config = HBaseConfiguration.create();

        try (Connection connection = ConnectionFactory.createConnection(config)) {
            try (Table table = connection.getTable(TableName.valueOf("powers"))) {

                Scan scan = new Scan(); // Create a new Scan object

                try (ResultScanner scanner = table.getScanner(scan)) { // Get the scanner from the table
                    for (Result result = scanner.next(); result != null; result = scanner.next()) {
                        System.out.println(result); // Print the result (as required)
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

