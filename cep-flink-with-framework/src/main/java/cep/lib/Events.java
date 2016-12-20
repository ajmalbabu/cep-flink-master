package cep.lib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Events implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Event> elements = new ArrayList<>();
    private boolean anomalyDetected = false;

    public Events() {
    }

    public Events(List<Event> events, boolean anomalyDetected) {
        this.elements = events;
        this.anomalyDetected = anomalyDetected;
    }

    public ArrayList<Event> getElements() {
        return new ArrayList<>(elements);
    }

    public Events add(Event event) {
        List<Event> resultEvents = new ArrayList<>(this.elements);
        resultEvents.add(event);
        return new Events(resultEvents, this.anomalyDetected);
    }

    public boolean isAnomalyDetected() {
        return anomalyDetected;
    }

    public Events setAnomalyDetected(boolean anomalyDetected) {
        return new Events(this.elements, anomalyDetected);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        return "Events{" +
                " elements=" + elements +
                ", anomalyDetected=" + anomalyDetected +
                '}';
    }
}
