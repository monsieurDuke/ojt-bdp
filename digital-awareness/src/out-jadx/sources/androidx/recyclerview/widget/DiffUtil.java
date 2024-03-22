package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* loaded from: classes.dex */
public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>() { // from class: androidx.recyclerview.widget.DiffUtil.1
        @Override // java.util.Comparator
        public int compare(Snake o1, Snake o2) {
            int i = o1.x - o2.x;
            return i == 0 ? o1.y - o2.y : i;
        }
    };

    /* loaded from: classes.dex */
    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    /* compiled from: 008C.java */
    /* loaded from: classes.dex */
    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_IGNORE = 16;
        private static final int FLAG_MASK = 31;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 5;
        public static final int NO_POSITION = -1;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        DiffResult(Callback callback, List<Snake> list, int[] oldItemStatuses, int[] newItemStatuses, boolean detectMoves) {
            this.mSnakes = list;
            this.mOldItemStatuses = oldItemStatuses;
            this.mNewItemStatuses = newItemStatuses;
            Arrays.fill(oldItemStatuses, 0);
            Arrays.fill(newItemStatuses, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = detectMoves;
            addRootSnake();
            findMatchingItems();
        }

        private void addRootSnake() {
            Snake snake = this.mSnakes.isEmpty() ? null : this.mSnakes.get(0);
            if (snake != null && snake.x == 0 && snake.y == 0) {
                return;
            }
            Snake snake2 = new Snake();
            snake2.x = 0;
            snake2.y = 0;
            snake2.removal = false;
            snake2.size = 0;
            snake2.reverse = false;
            this.mSnakes.add(0, snake2);
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        private void dispatchAdditions(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onInserted(i, i2);
                return;
            }
            for (int i4 = i2 - 1; i4 >= 0; i4--) {
                int[] iArr = this.mNewItemStatuses;
                int i5 = iArr[i3 + i4] & 31;
                switch (i5) {
                    case 0:
                        listUpdateCallback.onInserted(i, 1);
                        Iterator<PostponedUpdate> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().currentPos++;
                        }
                        break;
                    case 4:
                    case 8:
                        int i6 = iArr[i3 + i4] >> 5;
                        listUpdateCallback.onMoved(removePostponedUpdate(list, i6, true).currentPos, i);
                        if (i5 == 4) {
                            listUpdateCallback.onChanged(i, 1, this.mCallback.getChangePayload(i6, i3 + i4));
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        list.add(new PostponedUpdate(i3 + i4, i, false));
                        break;
                    default:
                        StringBuilder append = new StringBuilder().append("unknown flag for pos ").append(i3 + i4).append(" ");
                        String binaryString = Long.toBinaryString(i5);
                        Log5ECF72.a(binaryString);
                        LogE84000.a(binaryString);
                        Log229316.a(binaryString);
                        throw new IllegalStateException(append.append(binaryString).toString());
                }
            }
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        private void dispatchRemovals(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onRemoved(i, i2);
                return;
            }
            for (int i4 = i2 - 1; i4 >= 0; i4--) {
                int[] iArr = this.mOldItemStatuses;
                int i5 = iArr[i3 + i4] & 31;
                switch (i5) {
                    case 0:
                        listUpdateCallback.onRemoved(i + i4, 1);
                        Iterator<PostponedUpdate> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().currentPos--;
                        }
                        break;
                    case 4:
                    case 8:
                        int i6 = iArr[i3 + i4] >> 5;
                        PostponedUpdate removePostponedUpdate = removePostponedUpdate(list, i6, false);
                        listUpdateCallback.onMoved(i + i4, removePostponedUpdate.currentPos - 1);
                        if (i5 == 4) {
                            listUpdateCallback.onChanged(removePostponedUpdate.currentPos - 1, 1, this.mCallback.getChangePayload(i3 + i4, i6));
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        list.add(new PostponedUpdate(i3 + i4, i + i4, true));
                        break;
                    default:
                        StringBuilder append = new StringBuilder().append("unknown flag for pos ").append(i3 + i4).append(" ");
                        String binaryString = Long.toBinaryString(i5);
                        Log5ECF72.a(binaryString);
                        LogE84000.a(binaryString);
                        Log229316.a(binaryString);
                        throw new IllegalStateException(append.append(binaryString).toString());
                }
            }
        }

        private void findAddition(int x, int y, int snakeIndex) {
            if (this.mOldItemStatuses[x - 1] != 0) {
                return;
            }
            findMatchingItem(x, y, snakeIndex, false);
        }

        private boolean findMatchingItem(int x, int y, int snakeIndex, boolean removal) {
            int i;
            int i2;
            int i3;
            int i4;
            if (removal) {
                i = y - 1;
                i2 = x;
                i3 = y - 1;
            } else {
                i = x - 1;
                i2 = x - 1;
                i3 = y;
            }
            for (int i5 = snakeIndex; i5 >= 0; i5--) {
                Snake snake = this.mSnakes.get(i5);
                int i6 = snake.x + snake.size;
                int i7 = snake.y + snake.size;
                if (removal) {
                    for (int i8 = i2 - 1; i8 >= i6; i8--) {
                        if (this.mCallback.areItemsTheSame(i8, i)) {
                            i4 = this.mCallback.areContentsTheSame(i8, i) ? 8 : 4;
                            this.mNewItemStatuses[i] = (i8 << 5) | 16;
                            this.mOldItemStatuses[i8] = (i << 5) | i4;
                            return true;
                        }
                    }
                } else {
                    for (int i9 = i3 - 1; i9 >= i7; i9--) {
                        if (this.mCallback.areItemsTheSame(i, i9)) {
                            i4 = this.mCallback.areContentsTheSame(i, i9) ? 8 : 4;
                            this.mOldItemStatuses[x - 1] = (i9 << 5) | 16;
                            this.mNewItemStatuses[i9] = ((x - 1) << 5) | i4;
                            return true;
                        }
                    }
                }
                i2 = snake.x;
                i3 = snake.y;
            }
            return false;
        }

        private void findMatchingItems() {
            int i = this.mOldListSize;
            int i2 = this.mNewListSize;
            for (int size = this.mSnakes.size() - 1; size >= 0; size--) {
                Snake snake = this.mSnakes.get(size);
                int i3 = snake.x + snake.size;
                int i4 = snake.y + snake.size;
                if (this.mDetectMoves) {
                    while (i > i3) {
                        findAddition(i, i2, size);
                        i--;
                    }
                    while (i2 > i4) {
                        findRemoval(i, i2, size);
                        i2--;
                    }
                }
                for (int i5 = 0; i5 < snake.size; i5++) {
                    int i6 = snake.x + i5;
                    int i7 = snake.y + i5;
                    int i8 = this.mCallback.areContentsTheSame(i6, i7) ? 1 : 2;
                    this.mOldItemStatuses[i6] = (i7 << 5) | i8;
                    this.mNewItemStatuses[i7] = (i6 << 5) | i8;
                }
                i = snake.x;
                i2 = snake.y;
            }
        }

        private void findRemoval(int x, int y, int snakeIndex) {
            if (this.mNewItemStatuses[y - 1] != 0) {
                return;
            }
            findMatchingItem(x, y, snakeIndex, true);
        }

        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int pos, boolean removal) {
            for (int size = list.size() - 1; size >= 0; size--) {
                PostponedUpdate postponedUpdate = list.get(size);
                if (postponedUpdate.posInOwnerList == pos && postponedUpdate.removal == removal) {
                    list.remove(size);
                    for (int i = size; i < list.size(); i++) {
                        list.get(i).currentPos += removal ? 1 : -1;
                    }
                    return postponedUpdate;
                }
            }
            return null;
        }

        public int convertNewPositionToOld(int newListPosition) {
            if (newListPosition < 0 || newListPosition >= this.mNewListSize) {
                throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + newListPosition + ", new list size = " + this.mNewListSize);
            }
            int i = this.mNewItemStatuses[newListPosition];
            if ((i & 31) == 0) {
                return -1;
            }
            return i >> 5;
        }

        public int convertOldPositionToNew(int oldListPosition) {
            if (oldListPosition < 0 || oldListPosition >= this.mOldListSize) {
                throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + oldListPosition + ", old list size = " + this.mOldListSize);
            }
            int i = this.mOldItemStatuses[oldListPosition];
            if ((i & 31) == 0) {
                return -1;
            }
            return i >> 5;
        }

        public void dispatchUpdatesTo(ListUpdateCallback updateCallback) {
            int i;
            int i2;
            BatchingListUpdateCallback batchingListUpdateCallback = updateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) updateCallback : new BatchingListUpdateCallback(updateCallback);
            ArrayList arrayList = new ArrayList();
            int i3 = this.mOldListSize;
            int i4 = this.mNewListSize;
            for (int size = this.mSnakes.size() - 1; size >= 0; size--) {
                Snake snake = this.mSnakes.get(size);
                int i5 = snake.size;
                int i6 = snake.x + i5;
                int i7 = snake.y + i5;
                if (i6 < i3) {
                    i = i7;
                    dispatchRemovals(arrayList, batchingListUpdateCallback, i6, i3 - i6, i6);
                } else {
                    i = i7;
                }
                if (i < i4) {
                    i2 = i5;
                    dispatchAdditions(arrayList, batchingListUpdateCallback, i6, i4 - i, i);
                } else {
                    i2 = i5;
                }
                for (int i8 = i2 - 1; i8 >= 0; i8--) {
                    if ((this.mOldItemStatuses[snake.x + i8] & 31) == 2) {
                        batchingListUpdateCallback.onChanged(snake.x + i8, 1, this.mCallback.getChangePayload(snake.x + i8, snake.y + i8));
                    }
                }
                i3 = snake.x;
                i4 = snake.y;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }

        public void dispatchUpdatesTo(RecyclerView.Adapter adapter) {
            dispatchUpdatesTo(new AdapterListUpdateCallback(adapter));
        }

        List<Snake> getSnakes() {
            return this.mSnakes;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(T t, T t2);

        public abstract boolean areItemsTheSame(T t, T t2);

        public Object getChangePayload(T t, T t2) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int posInOwnerList, int currentPos, boolean removal) {
            this.posInOwnerList = posInOwnerList;
            this.currentPos = currentPos;
            this.removal = removal;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int oldListStart, int oldListEnd, int newListStart, int newListEnd) {
            this.oldListStart = oldListStart;
            this.oldListEnd = oldListEnd;
            this.newListStart = newListStart;
            this.newListEnd = newListEnd;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Snake {
        boolean removal;
        boolean reverse;
        int size;
        int x;
        int y;

        Snake() {
        }
    }

    private DiffUtil() {
    }

    public static DiffResult calculateDiff(Callback cb) {
        return calculateDiff(cb, true);
    }

    public static DiffResult calculateDiff(Callback cb, boolean detectMoves) {
        int oldListSize = cb.getOldListSize();
        int newListSize = cb.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Range(0, oldListSize, 0, newListSize));
        int abs = oldListSize + newListSize + Math.abs(oldListSize - newListSize);
        int[] iArr = new int[abs * 2];
        int[] iArr2 = new int[abs * 2];
        ArrayList arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            Range range = (Range) arrayList2.remove(arrayList2.size() - 1);
            Snake diffPartial = diffPartial(cb, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, iArr, iArr2, abs);
            if (diffPartial != null) {
                if (diffPartial.size > 0) {
                    arrayList.add(diffPartial);
                }
                diffPartial.x += range.oldListStart;
                diffPartial.y += range.newListStart;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range) arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                if (diffPartial.reverse) {
                    range2.oldListEnd = diffPartial.x;
                    range2.newListEnd = diffPartial.y;
                } else if (diffPartial.removal) {
                    range2.oldListEnd = diffPartial.x - 1;
                    range2.newListEnd = diffPartial.y;
                } else {
                    range2.oldListEnd = diffPartial.x;
                    range2.newListEnd = diffPartial.y - 1;
                }
                arrayList2.add(range2);
                if (!diffPartial.reverse) {
                    range.oldListStart = diffPartial.x + diffPartial.size;
                    range.newListStart = diffPartial.y + diffPartial.size;
                } else if (diffPartial.removal) {
                    range.oldListStart = diffPartial.x + diffPartial.size + 1;
                    range.newListStart = diffPartial.y + diffPartial.size;
                } else {
                    range.oldListStart = diffPartial.x + diffPartial.size;
                    range.newListStart = diffPartial.y + diffPartial.size + 1;
                }
                arrayList2.add(range);
            } else {
                arrayList3.add(range);
            }
        }
        Collections.sort(arrayList, SNAKE_COMPARATOR);
        return new DiffResult(cb, arrayList, iArr, iArr2, detectMoves);
    }

    private static Snake diffPartial(Callback cb, int startOld, int endOld, int startNew, int endNew, int[] forward, int[] backward, int kOffset) {
        int i;
        boolean z;
        int i2;
        int i3;
        boolean z2;
        int i4 = endOld - startOld;
        int i5 = endNew - startNew;
        if (endOld - startOld >= 1 && endNew - startNew >= 1) {
            int i6 = i4 - i5;
            int i7 = ((i4 + i5) + 1) / 2;
            Arrays.fill(forward, (kOffset - i7) - 1, kOffset + i7 + 1, 0);
            Arrays.fill(backward, ((kOffset - i7) - 1) + i6, kOffset + i7 + 1 + i6, i4);
            boolean z3 = i6 % 2 != 0;
            for (int i8 = 0; i8 <= i7; i8++) {
                for (int i9 = -i8; i9 <= i8; i9 += 2) {
                    if (i9 == (-i8) || (i9 != i8 && forward[(kOffset + i9) - 1] < forward[kOffset + i9 + 1])) {
                        i3 = forward[kOffset + i9 + 1];
                        z2 = false;
                    } else {
                        i3 = forward[(kOffset + i9) - 1] + 1;
                        z2 = true;
                    }
                    for (int i10 = i3 - i9; i3 < i4 && i10 < i5 && cb.areItemsTheSame(startOld + i3, startNew + i10); i10++) {
                        i3++;
                    }
                    forward[kOffset + i9] = i3;
                    if (z3 && i9 >= (i6 - i8) + 1 && i9 <= (i6 + i8) - 1 && forward[kOffset + i9] >= backward[kOffset + i9]) {
                        Snake snake = new Snake();
                        snake.x = backward[kOffset + i9];
                        snake.y = snake.x - i9;
                        snake.size = forward[kOffset + i9] - backward[kOffset + i9];
                        snake.removal = z2;
                        snake.reverse = false;
                        return snake;
                    }
                }
                int i11 = -i8;
                while (i11 <= i8) {
                    int i12 = i11 + i6;
                    if (i12 == i8 + i6 || (i12 != (-i8) + i6 && backward[(kOffset + i12) - 1] < backward[kOffset + i12 + 1])) {
                        i = backward[(kOffset + i12) - 1];
                        z = false;
                    } else {
                        i = backward[(kOffset + i12) + 1] - 1;
                        z = true;
                    }
                    int i13 = i - i12;
                    while (i > 0 && i13 > 0) {
                        i2 = i4;
                        if (!cb.areItemsTheSame((startOld + i) - 1, (startNew + i13) - 1)) {
                            break;
                        }
                        i--;
                        i13--;
                        i4 = i2;
                    }
                    i2 = i4;
                    backward[kOffset + i12] = i;
                    if (!z3 && i11 + i6 >= (-i8) && i11 + i6 <= i8 && forward[kOffset + i12] >= backward[kOffset + i12]) {
                        Snake snake2 = new Snake();
                        snake2.x = backward[kOffset + i12];
                        snake2.y = snake2.x - i12;
                        snake2.size = forward[kOffset + i12] - backward[kOffset + i12];
                        snake2.removal = z;
                        snake2.reverse = true;
                        return snake2;
                    }
                    i11 += 2;
                    i4 = i2;
                }
            }
            throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
        }
        return null;
    }
}
