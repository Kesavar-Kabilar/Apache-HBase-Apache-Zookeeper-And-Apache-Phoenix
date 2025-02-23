import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
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

public class TablePartF {

	public static void main(String[] args) throws IOException {

		Configuration config = HBaseConfiguration.create();

		try (Connection connection = ConnectionFactory.createConnection(config)) {
			try (Table table = connection.getTable(TableName.valueOf("powers"))) {

				// 1. Update the "color" qualifier for "row7" to "purple"
				Put put = new Put(Bytes.toBytes("row7"));
				put.addColumn(Bytes.toBytes("custom"), Bytes.toBytes("color"), Bytes.toBytes("purple"));
				table.put(put);

				// 2. Retrieve all columns for "row7", including all versions of "color"
				Get get = new Get(Bytes.toBytes("row7"));
				get.setMaxVersions(); // Retrieve all versions

				Result result = table.get(get);

				for (Cell cell : result.rawCells()) {
					byte[] family = CellUtil.cloneFamily(cell);
					byte[] qualifier = CellUtil.cloneQualifier(cell);
					byte[] value = CellUtil.cloneValue(cell);
					long timestamp = cell.getTimestamp();

					System.out.println(
							"row: " + Bytes.toString(result.getRow()) +
									", family: " + Bytes.toString(family) +
									", qualifier: " + Bytes.toString(qualifier) +
									", value: " + Bytes.toString(value) +
									", timestamp: " + timestamp);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
