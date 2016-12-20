package cep.flight.anomaly;

import cep.flight.anomaly.event.FlightEventStreamSource;
import cep.lib.*;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import static cep.flight.anomaly.event.FlightEvent.FLIGHT_EVENT_KAFKA_TOPIC_OFFSET;
import static cep.flight.anomaly.event.FlightMaintenanceEvent.MAINTENANCE_EVENT_KAFKA_TOPIC_OFFSET;

public class Main {

    public static void main(String[] args) throws Exception {

        new Main().run();
    }

    public void run() throws Exception {

        StreamExecutionEnvironment see = createStreamExecutionEnvironment();

        DataStream<Event> eventDataStream = see.addSource(createStreamSource());

        KeyedStream<Event, Integer> eventStreamByFlightNumber = eventDataStream.keyBy(
                (KeySelector<Event, Integer>) event -> event.key());


        AnomalyDetectTrigger anomalyDetectTrigger = new AnomalyDetectTrigger(new FlightAnomalyDetector(),
                new ConsoleAnomalyPublisher());

        DataStream<Events> eventStream = eventStreamByFlightNumber
                .timeWindow(Time.seconds(5), Time.seconds(5))
                .fold(new Events(), anomalyDetectTrigger);


        //		eventStream.print();

        see.execute();

    }

    private StreamExecutionEnvironment createStreamExecutionEnvironment() throws IOException {

        StreamExecutionEnvironment see = StreamExecutionEnvironment.getExecutionEnvironment();

        see.setStateBackend(new FsStateBackend("file:///temp/state"));

        see.enableCheckpointing(2000);

        see.setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10));

        return see;
    }

    private FlightEventStreamSource createStreamSource() {

        Configs configs = new Configs(Arrays.asList(new Config<>(FLIGHT_EVENT_KAFKA_TOPIC_OFFSET, 10L),
                new Config<>(MAINTENANCE_EVENT_KAFKA_TOPIC_OFFSET, 21L)));

        return new FlightEventStreamSource(configs, Duration.ofSeconds(5));
    }

}
