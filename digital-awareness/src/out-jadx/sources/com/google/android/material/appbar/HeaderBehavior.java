package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int activePointerId;
    private Runnable flingRunnable;
    private boolean isBeingDragged;
    private int lastMotionY;
    OverScroller scroller;
    private int touchSlop;
    private VelocityTracker velocityTracker;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class FlingRunnable implements Runnable {
        private final V layout;
        private final CoordinatorLayout parent;

        FlingRunnable(CoordinatorLayout parent, V v) {
            this.parent = parent;
            this.layout = v;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.layout == null || HeaderBehavior.this.scroller == null) {
                return;
            }
            if (!HeaderBehavior.this.scroller.computeScrollOffset()) {
                HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
                return;
            }
            HeaderBehavior headerBehavior = HeaderBehavior.this;
            headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
            ViewCompat.postOnAnimation(this.layout, this);
        }
    }

    public HeaderBehavior() {
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
    }

    boolean canDragView(V v) {
        return false;
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V v, int minOffset, int maxOffset, float velocityY) {
        Runnable runnable = this.flingRunnable;
        if (runnable != null) {
            v.removeCallbacks(runnable);
            this.flingRunnable = null;
        }
        if (this.scroller == null) {
            this.scroller = new OverScroller(v.getContext());
        }
        this.scroller.fling(0, getTopAndBottomOffset(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
        if (!this.scroller.computeScrollOffset()) {
            onFlingFinished(coordinatorLayout, v);
            return false;
        }
        FlingRunnable flingRunnable = new FlingRunnable(coordinatorLayout, v);
        this.flingRunnable = flingRunnable;
        ViewCompat.postOnAnimation(v, flingRunnable);
        return true;
    }

    int getMaxDragOffset(V v) {
        return -v.getHeight();
    }

    int getScrollRangeForDragFling(V v) {
        return v.getHeight();
    }

    int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    void onFlingFinished(CoordinatorLayout parent, V v) {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V v, MotionEvent ev) {
        int findPointerIndex;
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        if (ev.getActionMasked() == 2 && this.isBeingDragged) {
            int i = this.activePointerId;
            if (i == -1 || (findPointerIndex = ev.findPointerIndex(i)) == -1) {
                return false;
            }
            int y = (int) ev.getY(findPointerIndex);
            if (Math.abs(y - this.lastMotionY) > this.touchSlop) {
                this.lastMotionY = y;
                return true;
            }
        }
        if (ev.getActionMasked() == 0) {
            this.activePointerId = -1;
            int x = (int) ev.getX();
            int y2 = (int) ev.getY();
            boolean z = canDragView(v) && parent.isPointInChildBounds(v, x, y2);
            this.isBeingDragged = z;
            if (z) {
                this.lastMotionY = y2;
                this.activePointerId = ev.getPointerId(0);
                ensureVelocityTracker();
                OverScroller overScroller = this.scroller;
                if (overScroller != null && !overScroller.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(ev);
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0084 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r13, V r14, android.view.MotionEvent r15) {
        /*
            r12 = this;
            r0 = 0
            int r1 = r15.getActionMasked()
            r2 = 1
            r3 = -1
            r4 = 0
            switch(r1) {
                case 1: goto L47;
                case 2: goto L27;
                case 3: goto L6b;
                case 4: goto Lb;
                case 5: goto Lb;
                case 6: goto Ld;
                default: goto Lb;
            }
        Lb:
            goto L79
        Ld:
            int r1 = r15.getActionIndex()
            if (r1 != 0) goto L15
            r1 = r2
            goto L16
        L15:
            r1 = r4
        L16:
            int r3 = r15.getPointerId(r1)
            r12.activePointerId = r3
            float r3 = r15.getY(r1)
            r5 = 1056964608(0x3f000000, float:0.5)
            float r3 = r3 + r5
            int r3 = (int) r3
            r12.lastMotionY = r3
            goto L79
        L27:
            int r1 = r12.activePointerId
            int r1 = r15.findPointerIndex(r1)
            if (r1 != r3) goto L30
            return r4
        L30:
            float r3 = r15.getY(r1)
            int r3 = (int) r3
            int r5 = r12.lastMotionY
            int r5 = r5 - r3
            r12.lastMotionY = r3
            int r10 = r12.getMaxDragOffset(r14)
            r11 = 0
            r6 = r12
            r7 = r13
            r8 = r14
            r9 = r5
            r6.scroll(r7, r8, r9, r10, r11)
            goto L79
        L47:
            android.view.VelocityTracker r1 = r12.velocityTracker
            if (r1 == 0) goto L6b
            r0 = 1
            r1.addMovement(r15)
            android.view.VelocityTracker r1 = r12.velocityTracker
            r5 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r5)
            android.view.VelocityTracker r1 = r12.velocityTracker
            int r5 = r12.activePointerId
            float r1 = r1.getYVelocity(r5)
            int r5 = r12.getScrollRangeForDragFling(r14)
            int r9 = -r5
            r10 = 0
            r6 = r12
            r7 = r13
            r8 = r14
            r11 = r1
            r6.fling(r7, r8, r9, r10, r11)
        L6b:
            r12.isBeingDragged = r4
            r12.activePointerId = r3
            android.view.VelocityTracker r1 = r12.velocityTracker
            if (r1 == 0) goto L79
            r1.recycle()
            r1 = 0
            r12.velocityTracker = r1
        L79:
            android.view.VelocityTracker r1 = r12.velocityTracker
            if (r1 == 0) goto L80
            r1.addMovement(r15)
        L80:
            boolean r1 = r12.isBeingDragged
            if (r1 != 0) goto L88
            if (r0 == 0) goto L87
            goto L88
        L87:
            r2 = r4
        L88:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.HeaderBehavior.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int scroll(CoordinatorLayout coordinatorLayout, V v, int dy, int minOffset, int maxOffset) {
        return setHeaderTopBottomOffset(coordinatorLayout, v, getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int setHeaderTopBottomOffset(CoordinatorLayout parent, V v, int newOffset) {
        return setHeaderTopBottomOffset(parent, v, newOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V v, int newOffset, int minOffset, int maxOffset) {
        int newOffset2;
        int topAndBottomOffset = getTopAndBottomOffset();
        if (minOffset == 0 || topAndBottomOffset < minOffset || topAndBottomOffset > maxOffset || topAndBottomOffset == (newOffset2 = MathUtils.clamp(newOffset, minOffset, maxOffset))) {
            return 0;
        }
        setTopAndBottomOffset(newOffset2);
        return topAndBottomOffset - newOffset2;
    }
}
