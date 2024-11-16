### Employee Analysis Application

## Overview

The Employee Analysis Application processes employee data from a CSV file and performs various analyses, such as salary
analysis and reporting line analysis.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building the Project

To build the project, run the following command in the project root directory:

```sh
mvn clean install
```

This will compile the project, run the tests, and package the application into a JAR file located in the target
directory.

## Running the Application

To run the application, execute the following command:

```sh
java -jar target/employee-analysis-1.0-SNAPSHOT.jar <path-to-csv-file>
```

Replace <path-to-csv-file> with the path to your CSV file containing employee data.

## Example

```sh
java -jar target/employees-analysis-1.0.jar src/main/resources/input.csv
```

## Running the Tests

To run the tests, execute the following command:

```sh
mvn test
```

## Code Coverage

To run the application with code coverage in IntelliJ IDEA:  

1. Open the project in IntelliJ IDEA.
2. Navigate to the package "src/test/java"
3. Right-click on the package and select 'Run All Test with Coverage'
