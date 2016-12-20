package anomaly;

import org.apache.flink.api.common.functions.FoldFunction;


public class FlightAnomalyDetectTrigger implements FoldFunction<Event, Events> {

    private AnomalyDetector anomalyDetector = new FlightAnomalyDetector();

    @Override
    public Events fold(Events events, Event event) throws Exception {
        events.add(event);
        if(anomalyDetector.detectAnomaly(events)) {
            events.setAnomalyDetected(true);
            publishAnomaly(events);
        }
        return events;
    }

    public void publishAnomaly(Events events) {
        System.out.println("\n **************** Anomaly detected for " + events + " *********\n");
    }


}
