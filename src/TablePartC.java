import java.io.IOException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

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

public class TablePartC{

    public static void main(String[] args) throws IOException {

        Configuration config = HBaseConfiguration.create();

        try (Connection connection = ConnectionFactory.createConnection(config)) {
            try (Admin admin = connection.getAdmin();
                 Table table = connection.getTable(TableName.valueOf("powers"))) {

                BufferedReader br = new BufferedReader(new FileReader("input.csv"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) { // Ensure correct number of values
                        String rowKey = parts[0];
                        Put put = new Put(Bytes.toBytes(rowKey));

                        put.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("hero"), Bytes.toBytes(parts[1]));
                        put.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("power"), Bytes.toBytes(parts[2]));
                        put.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("name"), Bytes.toBytes(parts[3]));
                        put.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("xp"), Bytes.toBytes(parts[4]));
                        put.addColumn(Bytes.toBytes("custom"), Bytes.toBytes("color"), Bytes.toBytes(parts[5]));

                        table.put(put);
                    } else {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
                br.close();

            } catch (FileNotFoundException e) {
                System.err.println("input.csv not found: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error processing HBase or file: " + e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

