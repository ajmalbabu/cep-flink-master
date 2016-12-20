
## Showcases how to build compex event processing engine using flink api directly.

###To run inside IDE, go and exedcute AnomalyMain.java

###To run by submitting to Flink:

cd C:\software\flink-1.1.3-bin-hadoop27-scala_2.11\flink-1.1.3

run => bin\start-local.bat

Build sample job: 

cd C:\software\workspace\flink-cep-direct
mvn clean package

Submit job: 

cd C:\software\flink-1.1.3-bin-hadoop27-scala_2.11\flink-1.1.3
bin\flink run C:\software\workspace\flink-cep\target\flink-cep-1.0-FlightAnomaly.jar

Look at the logs @ C:\software\flink-1.1.3-bin-hadoop27-scala_2.11\flink-1.1.3\log
