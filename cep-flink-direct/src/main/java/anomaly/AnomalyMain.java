package anomaly;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

public class AnomalyMain {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment see = StreamExecutionEnvironment.getExecutionEnvironment();

        see.setStateBackend(new FsStateBackend("file:///temp/state"));

        see.enableCheckpointing(2000);

        see.setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10));

        DataStream<Event> eventDataStream = see.addSource(new EventStreamSource());

        KeyedStream<Event, Integer> eventStreamByFlightNumber = eventDataStream.keyBy((KeySelector<Event, Integer>) event -> event.key());

        DataStream<Events> eventStream = eventStreamByFlightNumber
                .timeWindow(Time.seconds(5), Time.seconds(5))
                .fold(new Events(), new FlightAnomalyDetectTrigger());


//		eventStream.print();

        see.execute();

    }

}
