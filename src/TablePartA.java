import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

public class TablePartA {

	public static void main(String[] args) throws IOException {
		// Configuration
		Configuration config = HBaseConfiguration.create();

		// Connection
		try (Connection connection = ConnectionFactory.createConnection(config)) {
			Admin admin = connection.getAdmin();

			// Table 1: powers
			TableName tableNamePowers = TableName.valueOf("powers");

			// Check if table exists, delete if it does (for testing)
			if (admin.tableExists(tableNamePowers)) {
				admin.disableTable(tableNamePowers);
				admin.deleteTable(tableNamePowers);
			}

			TableDescriptorBuilder tableDescriptorBuilderPowers = TableDescriptorBuilder.newBuilder(
					tableNamePowers);

			// Column Families with max versions
			tableDescriptorBuilderPowers.setColumnFamily(
					ColumnFamilyDescriptorBuilder
							.newBuilder(Bytes.toBytes("personal"))
							.setMaxVersions(3)
							.build());
			tableDescriptorBuilderPowers.setColumnFamily(
					ColumnFamilyDescriptorBuilder
							.newBuilder(Bytes.toBytes("professional"))
							.setMaxVersions(3)
							.build());
			tableDescriptorBuilderPowers.setColumnFamily(
					ColumnFamilyDescriptorBuilder
							.newBuilder(Bytes.toBytes("custom"))
							.setMaxVersions(3)
							.build());

			TableDescriptor tableDescriptorPowers = tableDescriptorBuilderPowers.build();

			admin.createTable(tableDescriptorPowers);

			// Table 2: food
			TableName tableNameFood = TableName.valueOf("food");

			// Check if table exists, delete if it does (for testing)
			if (admin.tableExists(tableNameFood)) {
				admin.disableTable(tableNameFood);
				admin.deleteTable(tableNameFood);
			}

			TableDescriptorBuilder tableDescriptorBuilderFood = TableDescriptorBuilder.newBuilder(
					tableNameFood);

			// Column Families
			tableDescriptorBuilderFood.setColumnFamily(
					ColumnFamilyDescriptorBuilder
							.newBuilder(Bytes.toBytes("nutrition"))
							.build());
			tableDescriptorBuilderFood.setColumnFamily(
					ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("taste")).build());

			TableDescriptor tableDescriptorFood = tableDescriptorBuilderFood.build();

			admin.createTable(tableDescriptorFood);

			admin.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
