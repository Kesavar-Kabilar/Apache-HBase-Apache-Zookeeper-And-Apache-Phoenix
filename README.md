# Apache HBase, Apache Zookeeper, And Apache Phoenix

This project demonstrates the setup and use of a Dockerized data management system using Apache HBase for NoSQL storage, Apache ZooKeeper for coordination, Apache Phoenix for SQL querying, and Java for data manipulation and join simulation.

## Overview

The focus is on building a data management system using Apache HBase, Apache ZooKeeper, and Apache Phoenix, all within a Dockerized environment. The goal is to demonstrate proficiency in setting up and interacting with these technologies to store, manage, and query data. This involved creating HBase tables, loading data, simulating SQL joins using Java, and utilizing Phoenix for SQL-based querying.

* **Apache HBase:**
    * HBase was used as the NoSQL database for storing structured data. Java code was written to create tables, define column families, and insert data from CSV files. Direct HBase API calls were used to perform table scans and simulate SQL joins.
* **Apache ZooKeeper:**
    * ZooKeeper was configured as the coordination service for HBase. It managed cluster configuration, server discovery, and fault tolerance. The `hbase-site.xml` file was examined to understand ZooKeeper quorum settings and the root ZNode.
* **Apache Phoenix:**
    * Phoenix was used as an SQL layer over HBase. It allowed for querying HBase data using SQL. Phoenix views and tables were created to map HBase data to relational schemas. SQL `JOIN` queries were written to extract and manipulate data.
* **Docker:**
    * Docker was used to containerize the HBase, ZooKeeper, and Phoenix environment. This ensured consistency and portability across different systems. Docker images and containers were used to set up a reproducible environment for development and testing.

## Docker Setup

This assignment utilized Docker to create a consistent and portable environment for HBase, ZooKeeper, and Phoenix. The Docker setup facilitated easy deployment and ensured that the application behaved the same way across different systems.

**Docker Build and Run Instructions:**

1.  **Building the Docker Image:**

    ```bash
    docker build -t mp7 .
    ```

    * This command builds a Docker image named `mp7` using the Dockerfile located in the current directory (`.`). The Dockerfile contains the instructions for setting up the HBase, ZooKeeper, and Phoenix environment.

2.  **Running the Docker Container:**

    ```bash
    docker run -v <Path>:/src --name mp7-cntr -it mp7
    ```

    * This command runs a Docker container named `mp7-cntr` from the `mp7` image.
    * `--name mp7-cntr`: Assigns the name `mp7-cntr` to the container.
    * `-it`: Runs the container in interactive mode with a pseudo-TTY, allowing you to interact with the container's shell.

3.  **Setting the CLASSPATH:**

    ```bash
    export CLASSPATH=$(hbase classpath)
    export CLASSPATH="/src:${CLASSPATH}"
    ```

    * These commands set the `CLASSPATH` environment variable within the container.
    * `export CLASSPATH=$(hbase classpath)`: This retrieves the HBase classpath and sets it as part of the `CLASSPATH`. This is essential for compiling and running Java code that interacts with HBase.
    * `export CLASSPATH="/src:${CLASSPATH}"`: This adds the `/src` directory to the `CLASSPATH`. This allows the Java compiler and runtime to find the Java source files and compiled classes within the mounted directory.

4.  **Starting HBase and ZooKeeper:**

    ```bash
    zkServer.sh start
    start-hbase.sh
    ```

    * `zkServer.sh start`: This command starts the ZooKeeper server within the container. ZooKeeper is required for HBase to function.
    * `start-hbase.sh`: This command starts the HBase master and region servers within the container.

5.  **Accessing the Container's Shell:**

    ```bash
    docker exec -it mp7-cntr bash
    ```

    * This command opens a new bash shell within the running `mp7-cntr` container. This allows you to execute commands and interact with the HBase, ZooKeeper, and Phoenix environment. This command is useful if you have closed the original terminal that you used to run the docker container.


## Apache HBase 

To interact with HBase directly, and indirectly verify Phoenix operations, the HBase shell was used:

```bash
/usr/local/hbase/bin/hbase shell
```

## Tasks

This assignment was divided into several parts, each focusing on different aspects of HBase, ZooKeeper, Phoenix, and Docker.

**Part A: HBase Table Creation**

* **Description:**
    * This part focused on creating HBase tables (`powers` and `food`) with specified column families.
    * Used the HBase Java API to define table descriptors and column family descriptors.
* **How to Run:**
    ```bash
    javac TablePartA.java && java TablePartA
    ```

**Part B: HBase Data Insertion**

* **Description:**
    * This part focused on inserting data into the `food` table using Java code and `Put` operations.
    * Used the HBase Java API's `Put` class to add rows and column values.
* **How to Run:**
    ```bash
    javac TablePartB.java && java TablePartB
    ```

**Part C: HBase Data Insertion from CSV**

* **Description:**
    * This part involved reading data from a CSV file (`input.csv`) and inserting it into the `powers` table using Java code.
    * Used Java's `BufferedReader` to read the CSV and the HBase Java API's `Put` class to insert data.
* **How to Run:**
    ```bash
    javac TablePartC.java && java TablePartC
    ```

**Part D: HBase Data Retrieval**

* **Description:**
    * This part focused on retrieving specific data from the `food` table using Java code and `Get` operations.
    * Used the HBase Java API's `Get` class to retrieve specific rows based on row keys.
* **How to Run:**
    ```bash
    javac TablePartD.java && java TablePartD
    ```

**Part E: HBase Data Scanning**

* **Description:**
    * This part involved scanning the `powers` table and retrieving all rows using Java code.
    * Used the HBase Java API `Scan` class and `ResultScanner` to retrieve all rows.
* **How to Run:**
    ```bash
    javac TablePartE.java && java TablePartE
    ```

**Part F: HBase Data Filtering**

* **Description:**
    * This part involved filtering data from the `powers` table based on specific criteria using Java code.
    * Used the HBase Java API and filters to retrieve rows matching criteria.
* **How to Run:**
    ```bash
    javac TablePartF.java && java TablePartF
    ```

**Part G: HBase Join Simulation (Java)**

* **Description:**
    * This part involved simulating a SQL `JOIN` operation using Java code by scanning the `powers` table and implementing the join logic in Java.
    * Used the HBase Java API's `Scan` and `ResultScanner` classes, and nested loops, to simulate a join.
* **How to Run:**
    ```bash
    javac TablePartG.java && java TablePartG
    ```

**Part H: Phoenix SQL Joins**

* **Description:**
    * This part focused on using Apache Phoenix to create a view over the `powers` table and writing SQL `JOIN` queries.
    * Used Phoenix SQL to create a view and perform join operations.
* **How to Run:**
    ```bash
    /usr/local/phoenix/bin/sqlline.py "$(cat url.txt)" phoenix.sql

    ```
