package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class LifecycleRegistry extends Lifecycle {
    private int mAddingObserverCounter;
    private final boolean mEnforceMainThread;
    private boolean mHandlingEvent;
    private final WeakReference<LifecycleOwner> mLifecycleOwner;
    private boolean mNewEventOccurred;
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap;
    private ArrayList<Lifecycle.State> mParentStates;
    private Lifecycle.State mState;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ObserverWithState {
        LifecycleEventObserver mLifecycleObserver;
        Lifecycle.State mState;

        ObserverWithState(LifecycleObserver observer, Lifecycle.State initialState) {
            this.mLifecycleObserver = Lifecycling.lifecycleEventObserver(observer);
            this.mState = initialState;
        }

        void dispatchEvent(LifecycleOwner owner, Lifecycle.Event event) {
            Lifecycle.State targetState = event.getTargetState();
            this.mState = LifecycleRegistry.min(this.mState, targetState);
            this.mLifecycleObserver.onStateChanged(owner, event);
            this.mState = targetState;
        }
    }

    public LifecycleRegistry(LifecycleOwner provider) {
        this(provider, true);
    }

    private LifecycleRegistry(LifecycleOwner provider, boolean enforceMainThread) {
        this.mObserverMap = new FastSafeIterableMap<>();
        this.mAddingObserverCounter = 0;
        this.mHandlingEvent = false;
        this.mNewEventOccurred = false;
        this.mParentStates = new ArrayList<>();
        this.mLifecycleOwner = new WeakReference<>(provider);
        this.mState = Lifecycle.State.INITIALIZED;
        this.mEnforceMainThread = enforceMainThread;
    }

    private void backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator<Map.Entry<LifecycleObserver, ObserverWithState>> descendingIterator = this.mObserverMap.descendingIterator();
        while (descendingIterator.hasNext() && !this.mNewEventOccurred) {
            Map.Entry<LifecycleObserver, ObserverWithState> next = descendingIterator.next();
            ObserverWithState value = next.getValue();
            while (value.mState.compareTo(this.mState) > 0 && !this.mNewEventOccurred && this.mObserverMap.contains(next.getKey())) {
                Lifecycle.Event downFrom = Lifecycle.Event.downFrom(value.mState);
                if (downFrom == null) {
                    throw new IllegalStateException("no event down from " + value.mState);
                }
                pushParentState(downFrom.getTargetState());
                value.dispatchEvent(lifecycleOwner, downFrom);
                popParentState();
            }
        }
    }

    private Lifecycle.State calculateTargetState(LifecycleObserver observer) {
        Map.Entry<LifecycleObserver, ObserverWithState> ceil = this.mObserverMap.ceil(observer);
        Lifecycle.State state = null;
        Lifecycle.State state2 = ceil != null ? ceil.getValue().mState : null;
        if (!this.mParentStates.isEmpty()) {
            state = this.mParentStates.get(r1.size() - 1);
        }
        return min(min(this.mState, state2), state);
    }

    public static LifecycleRegistry createUnsafe(LifecycleOwner owner) {
        return new LifecycleRegistry(owner, false);
    }

    private void enforceMainThreadIfNeeded(String methodName) {
        if (this.mEnforceMainThread && !ArchTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException("Method " + methodName + " must be called on the main thread");
        }
    }

    private void forwardPass(LifecycleOwner lifecycleOwner) {
        SafeIterableMap<LifecycleObserver, ObserverWithState>.IteratorWithAdditions iteratorWithAdditions = this.mObserverMap.iteratorWithAdditions();
        while (iteratorWithAdditions.hasNext() && !this.mNewEventOccurred) {
            Map.Entry next = iteratorWithAdditions.next();
            ObserverWithState observerWithState = (ObserverWithState) next.getValue();
            while (observerWithState.mState.compareTo(this.mState) < 0 && !this.mNewEventOccurred && this.mObserverMap.contains((LifecycleObserver) next.getKey())) {
                pushParentState(observerWithState.mState);
                Lifecycle.Event upFrom = Lifecycle.Event.upFrom(observerWithState.mState);
                if (upFrom == null) {
                    throw new IllegalStateException("no event up from " + observerWithState.mState);
                }
                observerWithState.dispatchEvent(lifecycleOwner, upFrom);
                popParentState();
            }
        }
    }

    private boolean isSynced() {
        if (this.mObserverMap.size() == 0) {
            return true;
        }
        Lifecycle.State state = this.mObserverMap.eldest().getValue().mState;
        Lifecycle.State state2 = this.mObserverMap.newest().getValue().mState;
        return state == state2 && this.mState == state2;
    }

    static Lifecycle.State min(Lifecycle.State state1, Lifecycle.State state2) {
        return (state2 == null || state2.compareTo(state1) >= 0) ? state1 : state2;
    }

    private void moveToState(Lifecycle.State next) {
        Lifecycle.State state = this.mState;
        if (state == next) {
            return;
        }
        if (state == Lifecycle.State.INITIALIZED && next == Lifecycle.State.DESTROYED) {
            throw new IllegalStateException("no event down from " + this.mState);
        }
        this.mState = next;
        if (this.mHandlingEvent || this.mAddingObserverCounter != 0) {
            this.mNewEventOccurred = true;
            return;
        }
        this.mHandlingEvent = true;
        sync();
        this.mHandlingEvent = false;
        if (this.mState == Lifecycle.State.DESTROYED) {
            this.mObserverMap = new FastSafeIterableMap<>();
        }
    }

    private void popParentState() {
        this.mParentStates.remove(r0.size() - 1);
    }

    private void pushParentState(Lifecycle.State state) {
        this.mParentStates.add(state);
    }

    private void sync() {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner.get();
        if (lifecycleOwner == null) {
            throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
        }
        while (!isSynced()) {
            this.mNewEventOccurred = false;
            if (this.mState.compareTo(this.mObserverMap.eldest().getValue().mState) < 0) {
                backwardPass(lifecycleOwner);
            }
            Map.Entry<LifecycleObserver, ObserverWithState> newest = this.mObserverMap.newest();
            if (!this.mNewEventOccurred && newest != null && this.mState.compareTo(newest.getValue().mState) > 0) {
                forwardPass(lifecycleOwner);
            }
        }
        this.mNewEventOccurred = false;
    }

    @Override // androidx.lifecycle.Lifecycle
    public void addObserver(LifecycleObserver observer) {
        LifecycleOwner lifecycleOwner;
        enforceMainThreadIfNeeded("addObserver");
        ObserverWithState observerWithState = new ObserverWithState(observer, this.mState == Lifecycle.State.DESTROYED ? Lifecycle.State.DESTROYED : Lifecycle.State.INITIALIZED);
        if (this.mObserverMap.putIfAbsent(observer, observerWithState) == null && (lifecycleOwner = this.mLifecycleOwner.get()) != null) {
            boolean z = this.mAddingObserverCounter != 0 || this.mHandlingEvent;
            Lifecycle.State calculateTargetState = calculateTargetState(observer);
            this.mAddingObserverCounter++;
            while (observerWithState.mState.compareTo(calculateTargetState) < 0 && this.mObserverMap.contains(observer)) {
                pushParentState(observerWithState.mState);
                Lifecycle.Event upFrom = Lifecycle.Event.upFrom(observerWithState.mState);
                if (upFrom == null) {
                    throw new IllegalStateException("no event up from " + observerWithState.mState);
                }
                observerWithState.dispatchEvent(lifecycleOwner, upFrom);
                popParentState();
                calculateTargetState = calculateTargetState(observer);
            }
            if (!z) {
                sync();
            }
            this.mAddingObserverCounter--;
        }
    }

    @Override // androidx.lifecycle.Lifecycle
    public Lifecycle.State getCurrentState() {
        return this.mState;
    }

    public int getObserverCount() {
        enforceMainThreadIfNeeded("getObserverCount");
        return this.mObserverMap.size();
    }

    public void handleLifecycleEvent(Lifecycle.Event event) {
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    @Deprecated
    public void markState(Lifecycle.State state) {
        enforceMainThreadIfNeeded("markState");
        setCurrentState(state);
    }

    @Override // androidx.lifecycle.Lifecycle
    public void removeObserver(LifecycleObserver observer) {
        enforceMainThreadIfNeeded("removeObserver");
        this.mObserverMap.remove(observer);
    }

    public void setCurrentState(Lifecycle.State state) {
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state);
    }
}
