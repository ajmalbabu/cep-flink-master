package cep.lib;

import org.apache.flink.api.common.functions.FoldFunction;


public class AnomalyDetectTrigger implements FoldFunction<Event, Events> {


    private AnomalyDetector anomalyDetector;
    private AnomalyPublisher anomalyPublisher;

    public AnomalyDetectTrigger(AnomalyDetector anomalyDetector, AnomalyPublisher anomalyPublisher) {
        this.anomalyDetector = anomalyDetector;
        this.anomalyPublisher = anomalyPublisher;
    }

    @Override
    public Events fold(Events events, Event event) throws Exception {

        events = events.add(event);
        if (anomalyDetector.detectAnomaly(events)) {
            events = events.setAnomalyDetected(true);
            anomalyPublisher.publishAnomaly(events);
        }
        return events;
    }


}
