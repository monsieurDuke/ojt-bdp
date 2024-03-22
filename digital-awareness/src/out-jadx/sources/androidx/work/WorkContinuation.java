package androidx.work;

import androidx.lifecycle.LiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class WorkContinuation {
    public static WorkContinuation combine(List<WorkContinuation> continuations) {
        return continuations.get(0).combineInternal(continuations);
    }

    protected abstract WorkContinuation combineInternal(List<WorkContinuation> continuations);

    public abstract Operation enqueue();

    public abstract ListenableFuture<List<WorkInfo>> getWorkInfos();

    public abstract LiveData<List<WorkInfo>> getWorkInfosLiveData();

    public final WorkContinuation then(OneTimeWorkRequest work) {
        return then(Collections.singletonList(work));
    }

    public abstract WorkContinuation then(List<OneTimeWorkRequest> work);
}
