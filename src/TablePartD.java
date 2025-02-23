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

public class TablePartD {
	public static void main(String[] args) throws IOException {

		Configuration config = HBaseConfiguration.create();

		try (Connection connection = ConnectionFactory.createConnection(config)) {
			try (Table table = connection.getTable(TableName.valueOf("powers"))) {

				// Row 1: hero, power, name, xp, color
				Get get1 = new Get(Bytes.toBytes("row1"));
				Result result1 = table.get(get1);

				String hero = Bytes.toString(result1.getValue(Bytes.toBytes("personal"), Bytes.toBytes("hero")));
				String power = Bytes.toString(result1.getValue(Bytes.toBytes("personal"), Bytes.toBytes("power")));
				String name = Bytes.toString(result1.getValue(Bytes.toBytes("professional"), Bytes.toBytes("name")));
				String xp = Bytes.toString(result1.getValue(Bytes.toBytes("professional"), Bytes.toBytes("xp")));
				String color = Bytes.toString(result1.getValue(Bytes.toBytes("custom"), Bytes.toBytes("color")));

				System.out.println("hero: " + hero + ", power: " + power + ", name: " + name + ", xp: " + xp
						+ ", color: " + color);

				// Row 19: hero, color
				Get get19 = new Get(Bytes.toBytes("row19"));
				Result result19 = table.get(get19);

				hero = Bytes.toString(result19.getValue(Bytes.toBytes("personal"), Bytes.toBytes("hero")));
				color = Bytes.toString(result19.getValue(Bytes.toBytes("custom"), Bytes.toBytes("color")));

				System.out.println("hero: " + hero + ", color: " + color);

				// Row 1: hero, name, color
				Get get1_2 = new Get(Bytes.toBytes("row1")); // Reusing get1 is okay
				Result result1_2 = table.get(get1_2);

				hero = Bytes.toString(result1_2.getValue(Bytes.toBytes("personal"), Bytes.toBytes("hero")));
				name = Bytes.toString(result1_2.getValue(Bytes.toBytes("professional"), Bytes.toBytes("name")));
				color = Bytes.toString(result1_2.getValue(Bytes.toBytes("custom"), Bytes.toBytes("color")));

				System.out.println("hero: " + hero + ", name: " + name + ", color: " + color);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
