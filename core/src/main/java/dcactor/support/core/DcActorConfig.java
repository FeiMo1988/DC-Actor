package dcactor.support.core;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by chendingwei on 17/3/14.
 */

public class DcActorConfig {

    public static final long DEFAULT_OVERTIME = 500L;

    private long overtime = DEFAULT_OVERTIME;
    private Set<DcActor.DcActorCallback> callbacks = new CopyOnWriteArraySet<>();


    public final DcActorConfig setOverTime(long overtime) {
        this.overtime = overtime;
        return this;
    }

    public final DcActorConfig addCallback(DcActor.DcActorCallback callback) {
        if (callback != null) {
            callbacks.add(callback);
        }
        return this;
    }

    public final DcActorConfig removeCallback(DcActor.DcActorCallback callback) {
        if (callback != null) {
            callbacks.remove(callback);
        }
        return this;
    }

    public Collection<DcActor.DcActorCallback> getCallbacks() {
        return this.callbacks;
    }


    public final long getOvertime() {
        return this.overtime;
    }

}
