package cep.lib;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

public class Snapshot implements Serializable {

    public static final long serialVersionUID = 1L;


    private ArrayList<Event> events = new ArrayList<>();
    private Duration dataCleanupDuration;
    private Configs configs;

    public Snapshot(Configs configs, Duration dataCleanupDuration) {
        this.configs = configs;
        this.dataCleanupDuration = dataCleanupDuration;
    }


    public ArrayList<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public Snapshot removeOlderEvents(long checkpointTimestamp) throws Exception {
        Snapshot snapshot = new Snapshot(configs, dataCleanupDuration);
        events.stream().filter(event -> (event.createTime().toEpochMilli() + dataCleanupDuration.toMillis()) >= checkpointTimestamp).forEach(snapshot::add);
        return snapshot;
    }

    public void add(Event event) {
        events.add(event);
    }


    public Configs getConfigs() {
        return configs;
    }

    public void setConfigs(Configs configs) {
        this.configs = configs;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "events=" + events +
                ", configs=" + configs +
                ", dataCleanupDuration=" + dataCleanupDuration +
                '}';
    }
}
