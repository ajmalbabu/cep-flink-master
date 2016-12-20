package cep.flight.anomaly.event;

import cep.lib.CheckPointedRichSourceFunction;
import cep.lib.Configs;
import cep.lib.Event;
import org.apache.flink.api.java.tuple.Tuple2;

import java.time.Duration;
import java.util.List;

import static cep.flight.anomaly.event.FlightEvent.FLIGHT_EVENT_KAFKA_TOPIC_OFFSET;

public class FlightEventStreamSource extends CheckPointedRichSourceFunction {

    private SampleEvents sampleEvents = new SampleEvents();

    public FlightEventStreamSource(Configs configs, Duration dataCleanupDuration) {
        super(configs, dataCleanupDuration);
    }

    @Override
    protected long nextEventsReqFreqMillie() {
        return 500;
    }

    @Override
    protected Tuple2<List<Event>, Configs> nextEvents(Configs configs) {

        long flightTopicOffset = (long) configs.findConfig(FLIGHT_EVENT_KAFKA_TOPIC_OFFSET).get().getValue();

        return new Tuple2<>(sampleEvents.nextEvents(flightTopicOffset), configs);
    }


}
