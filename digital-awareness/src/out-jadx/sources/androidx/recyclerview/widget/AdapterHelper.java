package androidx.recyclerview.widget;

import androidx.core.util.Pools;
import androidx.recyclerview.widget.OpReorderer;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AdapterHelper implements OpReorderer.Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final String TAG = "AHT";
    final Callback mCallback;
    final boolean mDisableRecycler;
    private int mExistingUpdateTypes;
    Runnable mOnItemProcessedCallback;
    final OpReorderer mOpReorderer;
    final ArrayList<UpdateOp> mPendingUpdates;
    final ArrayList<UpdateOp> mPostponedList;
    private Pools.Pool<UpdateOp> mUpdateOpPool;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface Callback {
        RecyclerView.ViewHolder findViewHolder(int i);

        void markViewHoldersUpdated(int i, int i2, Object obj);

        void offsetPositionsForAdd(int i, int i2);

        void offsetPositionsForMove(int i, int i2);

        void offsetPositionsForRemovingInvisible(int i, int i2);

        void offsetPositionsForRemovingLaidOutOrNewView(int i, int i2);

        void onDispatchFirstPass(UpdateOp updateOp);

        void onDispatchSecondPass(UpdateOp updateOp);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: 0088.java */
    /* loaded from: classes.dex */
    public static class UpdateOp {
        static final int ADD = 1;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        int cmd;
        int itemCount;
        Object payload;
        int positionStart;

        UpdateOp(int cmd, int positionStart, int itemCount, Object payload) {
            this.cmd = cmd;
            this.positionStart = positionStart;
            this.itemCount = itemCount;
            this.payload = payload;
        }

        String cmdToString() {
            switch (this.cmd) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                case 4:
                    return "up";
                case 8:
                    return "mv";
                default:
                    return "??";
            }
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UpdateOp updateOp = (UpdateOp) o;
            int i = this.cmd;
            if (i != updateOp.cmd) {
                return false;
            }
            if (i == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == updateOp.positionStart && this.positionStart == updateOp.itemCount) {
                return true;
            }
            if (this.itemCount != updateOp.itemCount || this.positionStart != updateOp.positionStart) {
                return false;
            }
            Object obj = this.payload;
            if (obj != null) {
                if (!obj.equals(updateOp.payload)) {
                    return false;
                }
            } else if (updateOp.payload != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.cmd * 31) + this.positionStart) * 31) + this.itemCount;
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String hexString = Integer.toHexString(System.identityHashCode(this));
            Log5ECF72.a(hexString);
            LogE84000.a(hexString);
            Log229316.a(hexString);
            return sb.append(hexString).append("[").append(cmdToString()).append(",s:").append(this.positionStart).append("c:").append(this.itemCount).append(",p:").append(this.payload).append("]").toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AdapterHelper(Callback callback) {
        this(callback, false);
    }

    AdapterHelper(Callback callback, boolean disableRecycler) {
        this.mUpdateOpPool = new Pools.SimplePool(30);
        this.mPendingUpdates = new ArrayList<>();
        this.mPostponedList = new ArrayList<>();
        this.mExistingUpdateTypes = 0;
        this.mCallback = callback;
        this.mDisableRecycler = disableRecycler;
        this.mOpReorderer = new OpReorderer(this);
    }

    private void applyAdd(UpdateOp op) {
        postponeAndUpdateViewHolders(op);
    }

    private void applyMove(UpdateOp op) {
        postponeAndUpdateViewHolders(op);
    }

    private void applyRemove(UpdateOp op) {
        int i = op.positionStart;
        int i2 = 0;
        int i3 = op.positionStart + op.itemCount;
        char c = 65535;
        int i4 = op.positionStart;
        while (i4 < i3) {
            boolean z = false;
            if (this.mCallback.findViewHolder(i4) != null || canFindInPreLayout(i4)) {
                if (c == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(2, i, i2, null));
                    z = true;
                }
                c = 1;
            } else {
                if (c == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(2, i, i2, null));
                    z = true;
                }
                c = 0;
            }
            if (z) {
                i4 -= i2;
                i3 -= i2;
                i2 = 1;
            } else {
                i2++;
            }
            i4++;
        }
        if (i2 != op.itemCount) {
            recycleUpdateOp(op);
            op = obtainUpdateOp(2, i, i2, null);
        }
        if (c == 0) {
            dispatchAndUpdateViewHolders(op);
        } else {
            postponeAndUpdateViewHolders(op);
        }
    }

    private void applyUpdate(UpdateOp op) {
        int i = op.positionStart;
        int i2 = 0;
        int i3 = op.positionStart + op.itemCount;
        char c = 65535;
        for (int i4 = op.positionStart; i4 < i3; i4++) {
            if (this.mCallback.findViewHolder(i4) != null || canFindInPreLayout(i4)) {
                if (c == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(4, i, i2, op.payload));
                    i2 = 0;
                    i = i4;
                }
                c = 1;
            } else {
                if (c == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(4, i, i2, op.payload));
                    i2 = 0;
                    i = i4;
                }
                c = 0;
            }
            i2++;
        }
        if (i2 != op.itemCount) {
            Object obj = op.payload;
            recycleUpdateOp(op);
            op = obtainUpdateOp(4, i, i2, obj);
        }
        if (c == 0) {
            dispatchAndUpdateViewHolders(op);
        } else {
            postponeAndUpdateViewHolders(op);
        }
    }

    private boolean canFindInPreLayout(int position) {
        int size = this.mPostponedList.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = this.mPostponedList.get(i);
            if (updateOp.cmd == 8) {
                if (findPositionOffset(updateOp.itemCount, i + 1) == position) {
                    return true;
                }
            } else if (updateOp.cmd == 1) {
                int i2 = updateOp.positionStart + updateOp.itemCount;
                for (int i3 = updateOp.positionStart; i3 < i2; i3++) {
                    if (findPositionOffset(i3, i + 1) == position) {
                        return true;
                    }
                }
            } else {
                continue;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void dispatchAndUpdateViewHolders(androidx.recyclerview.widget.AdapterHelper.UpdateOp r13) {
        /*
            r12 = this;
            int r0 = r13.cmd
            r1 = 1
            if (r0 == r1) goto L8d
            int r0 = r13.cmd
            r2 = 8
            if (r0 == r2) goto L8d
            int r0 = r13.positionStart
            int r2 = r13.cmd
            int r0 = r12.updatePositionWithPostponed(r0, r2)
            r2 = 1
            int r3 = r13.positionStart
            int r4 = r13.cmd
            switch(r4) {
                case 2: goto L36;
                case 3: goto L1b;
                case 4: goto L34;
                default: goto L1b;
            }
        L1b:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "op should be remove or update."
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r13)
            java.lang.String r4 = r4.toString()
            r1.<init>(r4)
            throw r1
        L34:
            r4 = 1
            goto L38
        L36:
            r4 = 0
        L38:
            r5 = 1
        L39:
            int r6 = r13.itemCount
            if (r5 >= r6) goto L79
            int r6 = r13.positionStart
            int r7 = r4 * r5
            int r6 = r6 + r7
            int r7 = r13.cmd
            int r7 = r12.updatePositionWithPostponed(r6, r7)
            r8 = 0
            int r9 = r13.cmd
            r10 = 0
            switch(r9) {
                case 2: goto L57;
                case 3: goto L4f;
                case 4: goto L50;
                default: goto L4f;
            }
        L4f:
            goto L5b
        L50:
            int r9 = r0 + 1
            if (r7 != r9) goto L55
            r10 = r1
        L55:
            r8 = r10
            goto L5b
        L57:
            if (r7 != r0) goto L5a
            r10 = r1
        L5a:
            r8 = r10
        L5b:
            if (r8 == 0) goto L60
            int r2 = r2 + 1
            goto L76
        L60:
            int r9 = r13.cmd
            java.lang.Object r10 = r13.payload
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r9 = r12.obtainUpdateOp(r9, r0, r2, r10)
            r12.dispatchFirstPassAndUpdateViewHolders(r9, r3)
            r12.recycleUpdateOp(r9)
            int r10 = r13.cmd
            r11 = 4
            if (r10 != r11) goto L74
            int r3 = r3 + r2
        L74:
            r0 = r7
            r2 = 1
        L76:
            int r5 = r5 + 1
            goto L39
        L79:
            java.lang.Object r1 = r13.payload
            r12.recycleUpdateOp(r13)
            if (r2 <= 0) goto L8c
            int r5 = r13.cmd
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r5 = r12.obtainUpdateOp(r5, r0, r2, r1)
            r12.dispatchFirstPassAndUpdateViewHolders(r5, r3)
            r12.recycleUpdateOp(r5)
        L8c:
            return
        L8d:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "should not dispatch add or move for pre layout"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.AdapterHelper.dispatchAndUpdateViewHolders(androidx.recyclerview.widget.AdapterHelper$UpdateOp):void");
    }

    private void postponeAndUpdateViewHolders(UpdateOp op) {
        this.mPostponedList.add(op);
        switch (op.cmd) {
            case 1:
                this.mCallback.offsetPositionsForAdd(op.positionStart, op.itemCount);
                return;
            case 2:
                this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(op.positionStart, op.itemCount);
                return;
            case 4:
                this.mCallback.markViewHoldersUpdated(op.positionStart, op.itemCount, op.payload);
                return;
            case 8:
                this.mCallback.offsetPositionsForMove(op.positionStart, op.itemCount);
                return;
            default:
                throw new IllegalArgumentException("Unknown update op type for " + op);
        }
    }

    private int updatePositionWithPostponed(int pos, int cmd) {
        int i;
        int i2;
        for (int size = this.mPostponedList.size() - 1; size >= 0; size--) {
            UpdateOp updateOp = this.mPostponedList.get(size);
            if (updateOp.cmd == 8) {
                if (updateOp.positionStart < updateOp.itemCount) {
                    i = updateOp.positionStart;
                    i2 = updateOp.itemCount;
                } else {
                    i = updateOp.itemCount;
                    i2 = updateOp.positionStart;
                }
                if (pos < i || pos > i2) {
                    if (pos < updateOp.positionStart) {
                        if (cmd == 1) {
                            updateOp.positionStart++;
                            updateOp.itemCount++;
                        } else if (cmd == 2) {
                            updateOp.positionStart--;
                            updateOp.itemCount--;
                        }
                    }
                } else if (i == updateOp.positionStart) {
                    if (cmd == 1) {
                        updateOp.itemCount++;
                    } else if (cmd == 2) {
                        updateOp.itemCount--;
                    }
                    pos++;
                } else {
                    if (cmd == 1) {
                        updateOp.positionStart++;
                    } else if (cmd == 2) {
                        updateOp.positionStart--;
                    }
                    pos--;
                }
            } else if (updateOp.positionStart <= pos) {
                if (updateOp.cmd == 1) {
                    pos -= updateOp.itemCount;
                } else if (updateOp.cmd == 2) {
                    pos += updateOp.itemCount;
                }
            } else if (cmd == 1) {
                updateOp.positionStart++;
            } else if (cmd == 2) {
                updateOp.positionStart--;
            }
        }
        for (int size2 = this.mPostponedList.size() - 1; size2 >= 0; size2--) {
            UpdateOp updateOp2 = this.mPostponedList.get(size2);
            if (updateOp2.cmd == 8) {
                if (updateOp2.itemCount == updateOp2.positionStart || updateOp2.itemCount < 0) {
                    this.mPostponedList.remove(size2);
                    recycleUpdateOp(updateOp2);
                }
            } else if (updateOp2.itemCount <= 0) {
                this.mPostponedList.remove(size2);
                recycleUpdateOp(updateOp2);
            }
        }
        return pos;
    }

    AdapterHelper addUpdateOp(UpdateOp... ops) {
        Collections.addAll(this.mPendingUpdates, ops);
        return this;
    }

    public int applyPendingUpdatesToPosition(int position) {
        int size = this.mPendingUpdates.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = this.mPendingUpdates.get(i);
            switch (updateOp.cmd) {
                case 1:
                    if (updateOp.positionStart <= position) {
                        position += updateOp.itemCount;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (updateOp.positionStart > position) {
                        continue;
                    } else {
                        if (updateOp.positionStart + updateOp.itemCount > position) {
                            return -1;
                        }
                        position -= updateOp.itemCount;
                        break;
                    }
                case 8:
                    if (updateOp.positionStart == position) {
                        position = updateOp.itemCount;
                        break;
                    } else {
                        if (updateOp.positionStart < position) {
                            position--;
                        }
                        if (updateOp.itemCount <= position) {
                            position++;
                            break;
                        } else {
                            break;
                        }
                    }
            }
        }
        return position;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void consumePostponedUpdates() {
        int size = this.mPostponedList.size();
        for (int i = 0; i < size; i++) {
            this.mCallback.onDispatchSecondPass(this.mPostponedList.get(i));
        }
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x0016. Please report as an issue. */
    public void consumeUpdatesInOnePass() {
        consumePostponedUpdates();
        int size = this.mPendingUpdates.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = this.mPendingUpdates.get(i);
            switch (updateOp.cmd) {
                case 1:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
                    break;
                case 2:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForRemovingInvisible(updateOp.positionStart, updateOp.itemCount);
                    break;
                case 4:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                    break;
                case 8:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
                    break;
            }
            Runnable runnable = this.mOnItemProcessedCallback;
            if (runnable != null) {
                runnable.run();
            }
        }
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    void dispatchFirstPassAndUpdateViewHolders(UpdateOp op, int offsetStart) {
        this.mCallback.onDispatchFirstPass(op);
        switch (op.cmd) {
            case 2:
                this.mCallback.offsetPositionsForRemovingInvisible(offsetStart, op.itemCount);
                return;
            case 3:
            default:
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            case 4:
                this.mCallback.markViewHoldersUpdated(offsetStart, op.itemCount, op.payload);
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int findPositionOffset(int position) {
        return findPositionOffset(position, 0);
    }

    int findPositionOffset(int position, int firstPostponedItem) {
        int size = this.mPostponedList.size();
        for (int i = firstPostponedItem; i < size; i++) {
            UpdateOp updateOp = this.mPostponedList.get(i);
            if (updateOp.cmd == 8) {
                if (updateOp.positionStart == position) {
                    position = updateOp.itemCount;
                } else {
                    if (updateOp.positionStart < position) {
                        position--;
                    }
                    if (updateOp.itemCount <= position) {
                        position++;
                    }
                }
            } else if (updateOp.positionStart > position) {
                continue;
            } else if (updateOp.cmd == 2) {
                if (position < updateOp.positionStart + updateOp.itemCount) {
                    return -1;
                }
                position -= updateOp.itemCount;
            } else if (updateOp.cmd == 1) {
                position += updateOp.itemCount;
            }
        }
        return position;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasAnyUpdateTypes(int updateTypes) {
        return (this.mExistingUpdateTypes & updateTypes) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasPendingUpdates() {
        return this.mPendingUpdates.size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasUpdates() {
        return (this.mPostponedList.isEmpty() || this.mPendingUpdates.isEmpty()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.OpReorderer.Callback
    public UpdateOp obtainUpdateOp(int cmd, int positionStart, int itemCount, Object payload) {
        UpdateOp acquire = this.mUpdateOpPool.acquire();
        if (acquire == null) {
            return new UpdateOp(cmd, positionStart, itemCount, payload);
        }
        acquire.cmd = cmd;
        acquire.positionStart = positionStart;
        acquire.itemCount = itemCount;
        acquire.payload = payload;
        return acquire;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        if (itemCount < 1) {
            return false;
        }
        this.mPendingUpdates.add(obtainUpdateOp(4, positionStart, itemCount, payload));
        this.mExistingUpdateTypes |= 4;
        return this.mPendingUpdates.size() == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean onItemRangeInserted(int positionStart, int itemCount) {
        if (itemCount < 1) {
            return false;
        }
        this.mPendingUpdates.add(obtainUpdateOp(1, positionStart, itemCount, null));
        this.mExistingUpdateTypes |= 1;
        return this.mPendingUpdates.size() == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean onItemRangeMoved(int from, int to, int itemCount) {
        if (from == to) {
            return false;
        }
        if (itemCount != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.mPendingUpdates.add(obtainUpdateOp(8, from, to, null));
        this.mExistingUpdateTypes |= 8;
        return this.mPendingUpdates.size() == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean onItemRangeRemoved(int positionStart, int itemCount) {
        if (itemCount < 1) {
            return false;
        }
        this.mPendingUpdates.add(obtainUpdateOp(2, positionStart, itemCount, null));
        this.mExistingUpdateTypes |= 2;
        return this.mPendingUpdates.size() == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x001a. Please report as an issue. */
    public void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int size = this.mPendingUpdates.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = this.mPendingUpdates.get(i);
            switch (updateOp.cmd) {
                case 1:
                    applyAdd(updateOp);
                    break;
                case 2:
                    applyRemove(updateOp);
                    break;
                case 4:
                    applyUpdate(updateOp);
                    break;
                case 8:
                    applyMove(updateOp);
                    break;
            }
            Runnable runnable = this.mOnItemProcessedCallback;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.mPendingUpdates.clear();
    }

    @Override // androidx.recyclerview.widget.OpReorderer.Callback
    public void recycleUpdateOp(UpdateOp op) {
        if (this.mDisableRecycler) {
            return;
        }
        op.payload = null;
        this.mUpdateOpPool.release(op);
    }

    void recycleUpdateOpsAndClearList(List<UpdateOp> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            recycleUpdateOp(list.get(i));
        }
        list.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reset() {
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }
}
