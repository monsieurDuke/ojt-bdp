package androidx.work.impl.constraints.trackers;

import android.content.Context;
import androidx.work.Logger;
import androidx.work.impl.constraints.ConstraintListener;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 00C9.java */
/* loaded from: classes.dex */
public abstract class ConstraintTracker<T> {
    private static final String TAG;
    protected final Context mAppContext;
    T mCurrentState;
    protected final TaskExecutor mTaskExecutor;
    private final Object mLock = new Object();
    private final Set<ConstraintListener<T>> mListeners = new LinkedHashSet();

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    static {
        String tagWithPrefix = Logger.tagWithPrefix("ConstraintTracker");
        Log5ECF72.a(tagWithPrefix);
        LogE84000.a(tagWithPrefix);
        Log229316.a(tagWithPrefix);
        TAG = tagWithPrefix;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConstraintTracker(Context context, TaskExecutor taskExecutor) {
        this.mAppContext = context.getApplicationContext();
        this.mTaskExecutor = taskExecutor;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public void addListener(ConstraintListener<T> listener) {
        synchronized (this.mLock) {
            if (this.mListeners.add(listener)) {
                if (this.mListeners.size() == 1) {
                    this.mCurrentState = getInitialState();
                    Logger logger = Logger.get();
                    String str = TAG;
                    String format = String.format("%s: initial state = %s", getClass().getSimpleName(), this.mCurrentState);
                    Log5ECF72.a(format);
                    LogE84000.a(format);
                    Log229316.a(format);
                    logger.debug(str, format, new Throwable[0]);
                    startTracking();
                }
                listener.onConstraintChanged(this.mCurrentState);
            }
        }
    }

    public abstract T getInitialState();

    public void removeListener(ConstraintListener<T> listener) {
        synchronized (this.mLock) {
            if (this.mListeners.remove(listener) && this.mListeners.isEmpty()) {
                stopTracking();
            }
        }
    }

    public void setState(T newState) {
        synchronized (this.mLock) {
            T t = this.mCurrentState;
            if (t != newState && (t == null || !t.equals(newState))) {
                this.mCurrentState = newState;
                final ArrayList arrayList = new ArrayList(this.mListeners);
                this.mTaskExecutor.getMainThreadExecutor().execute(new Runnable() { // from class: androidx.work.impl.constraints.trackers.ConstraintTracker.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            ((ConstraintListener) it.next()).onConstraintChanged(ConstraintTracker.this.mCurrentState);
                        }
                    }
                });
            }
        }
    }

    public abstract void startTracking();

    public abstract void stopTracking();
}
