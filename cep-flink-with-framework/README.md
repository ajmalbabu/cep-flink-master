
## Showcases how to build compex event processing engine using flink, but created a thin layer around flink in the lib package and example inside cep.flight.anomaly package uses that layer from the cep.lib package.

###To run inside IDE, go and execute cep.flight.anomaly.Main.java

###To run by submitting to Flink:

cd C:\software\flink-1.1.3-bin-hadoop27-scala_2.11\flink-1.1.3

run => bin\start-local.bat

Build sample job: 

cd C:\software\workspace\flink-cep-with-framework
mvn clean package

Submit job: 

cd C:\software\flink-1.1.3-bin-hadoop27-scala_2.11\flink-1.1.3
bin\flink run C:\software\workspace\flink-cep\target\flink-cep-1.0-FlightAnomaly.jar

Look at the logs @ C:\software\flink-1.1.3-bin-hadoop27-scala_2.11\flink-1.1.3\log
