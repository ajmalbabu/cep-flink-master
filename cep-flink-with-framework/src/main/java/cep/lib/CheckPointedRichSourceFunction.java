package cep.lib;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.checkpoint.CheckpointedAsynchronously;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public abstract class CheckPointedRichSourceFunction extends RichSourceFunction<Event> implements CheckpointedAsynchronously<Snapshot> {

    private volatile boolean isRunning = true;

    private Snapshot snapshot;
    private boolean stateWasRestored = false;

    public CheckPointedRichSourceFunction(Configs configs, Duration dataCleanupDuration) {
        snapshot = new Snapshot(configs, dataCleanupDuration);
    }

    @Override
    public void run(SourceContext<Event> ctx) throws Exception {

        while (isRunning) {

            Thread.sleep(nextEventsReqFreqMillie());

            // Output collection and state update should be atomic
            synchronized (ctx.getCheckpointLock()) {

                collectIfSnapshotWasRestored(ctx);

                collectNextEvents(ctx);

            }

        }
    }

    /**
     * If snap shot was restored because of a failure, then valid events in the snapshots should be collected and sent.
     */
    private void collectIfSnapshotWasRestored(SourceContext<Event> ctx) throws Exception {

        if (stateWasRestored) {
            snapshot.removeOlderEvents(Instant.now().toEpochMilli()).getEvents().stream().forEach(ctx::collect);
            stateWasRestored = false;
        }
    }


    private void collectNextEvents(SourceContext<Event> ctx) {

        Tuple2<List<Event>, Configs> result = nextEvents(snapshot.getConfigs());

        result.f0.stream().forEach(event -> {
            // Output collection
            ctx.collect(event);
            // State update
            snapshot.add(event);
        });

        snapshot.setConfigs(result.f1);
    }


    @Override
    public void cancel() {
        isRunning = false;
    }


    @Override
    public Snapshot snapshotState(long checkpointId, long checkpointTimestamp) throws Exception {

        System.out.println("Take source SnapShot with checkpointId: " + checkpointId + " and CheckpointTimestamp: " + checkpointTimestamp);

        return snapshot.removeOlderEvents(checkpointTimestamp);
    }

    @Override
    public void restoreState(Snapshot snapshot) throws Exception {

        System.out.println("**************** Restore check pointed snapshot: " + snapshot + " ************");

        this.snapshot = snapshot;
        stateWasRestored = true;
    }

    protected abstract long nextEventsReqFreqMillie();

    /**
     * Gets called every X millie seconds, where X is configured with nextEventsReqFreqMillie.
     *
     * @param configs - The configuration that was registered with the source gets passed in
     * @return Should return the Tuple of List<events> and the updated configs.>
     */
    protected abstract Tuple2<List<Event>, Configs> nextEvents(Configs configs);
}
