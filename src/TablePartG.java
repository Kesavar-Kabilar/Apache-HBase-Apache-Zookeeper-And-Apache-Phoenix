import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class TablePartG{

   public static void main(String[] args) throws IOException {

        Configuration config = HBaseConfiguration.create();

        try (Connection connection = ConnectionFactory.createConnection(config)) {
            try (Table table = connection.getTable(TableName.valueOf("powers"))) {

                Scan scan = new Scan();
                Map<String, Map<String, String>> powers = new HashMap<>();

                try (ResultScanner scanner = table.getScanner(scan)) {
                    for (Result result : scanner) {
                        String rowKey = Bytes.toString(result.getRow());
                        String name = Bytes.toString(result.getValue(Bytes.toBytes("professional"), Bytes.toBytes("name")));
                        String power = Bytes.toString(result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("power")));
                        String color = Bytes.toString(result.getValue(Bytes.toBytes("custom"), Bytes.toBytes("color")));

                        Map<String, String> attributes = new HashMap<>();
                        attributes.put("name", name);
                        attributes.put("power", power);
                        attributes.put("color", color);

                        powers.put(rowKey, attributes);
                    }
                }

				Set<String> rowKeys = powers.keySet();
				List<String> sortedRowKeys = new ArrayList<>(rowKeys);
				Collections.sort(sortedRowKeys);


                for (String rowKey1 : sortedRowKeys) {
					Map<String, String> attributes1 = powers.get(rowKey1);
                    String name1 = attributes1.get("name");
                    String power1 = attributes1.get("power");
                    String color1 = attributes1.get("color");

                    for (String rowKey2 : sortedRowKeys) {
						if (rowKey1.equals(rowKey2)) continue;

						Map<String, String> attributes2 = powers.get(rowKey2);
                        String name2 = attributes2.get("name");
                        String power2 = attributes2.get("power");
                        String color2 = attributes2.get("color");

                        if (color1.equals(color2) && !name1.equals(name2)) {
                            System.out.println(name1 + ", " + power1 + ", " + name2 + ", " + power2 + ", " + color1);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}