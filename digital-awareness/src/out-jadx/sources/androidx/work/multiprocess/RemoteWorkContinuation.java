package androidx.work.multiprocess;

import androidx.work.OneTimeWorkRequest;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class RemoteWorkContinuation {
    protected RemoteWorkContinuation() {
    }

    public static RemoteWorkContinuation combine(List<RemoteWorkContinuation> continuations) {
        return continuations.get(0).combineInternal(continuations);
    }

    protected abstract RemoteWorkContinuation combineInternal(List<RemoteWorkContinuation> continuations);

    public abstract ListenableFuture<Void> enqueue();

    public final RemoteWorkContinuation then(OneTimeWorkRequest work) {
        return then(Collections.singletonList(work));
    }

    public abstract RemoteWorkContinuation then(List<OneTimeWorkRequest> work);
}
