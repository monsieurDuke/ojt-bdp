package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {
    private int overlayTop;
    final Rect tempRect1;
    final Rect tempRect2;
    private int verticalLayoutGap;

    public HeaderScrollingViewBehavior() {
        this.tempRect1 = new Rect();
        this.tempRect2 = new Rect();
        this.verticalLayoutGap = 0;
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.tempRect1 = new Rect();
        this.tempRect2 = new Rect();
        this.verticalLayoutGap = 0;
    }

    private static int resolveGravity(int gravity) {
        if (gravity == 0) {
            return 8388659;
        }
        return gravity;
    }

    abstract View findFirstDependency(List<View> list);

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int getOverlapPixelsForOffset(View header) {
        if (this.overlayTop == 0) {
            return 0;
        }
        float overlapRatioForOffset = getOverlapRatioForOffset(header);
        int i = this.overlayTop;
        return MathUtils.clamp((int) (overlapRatioForOffset * i), 0, i);
    }

    float getOverlapRatioForOffset(View header) {
        return 1.0f;
    }

    public final int getOverlayTop() {
        return this.overlayTop;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getScrollRange(View v) {
        return v.getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int getVerticalLayoutGap() {
        return this.verticalLayoutGap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.appbar.ViewOffsetBehavior
    public void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        View findFirstDependency = findFirstDependency(parent.getDependencies(child));
        if (findFirstDependency == null) {
            super.layoutChild(parent, child, layoutDirection);
            this.verticalLayoutGap = 0;
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        Rect rect = this.tempRect1;
        rect.set(parent.getPaddingLeft() + layoutParams.leftMargin, findFirstDependency.getBottom() + layoutParams.topMargin, (parent.getWidth() - parent.getPaddingRight()) - layoutParams.rightMargin, ((parent.getHeight() + findFirstDependency.getBottom()) - parent.getPaddingBottom()) - layoutParams.bottomMargin);
        WindowInsetsCompat lastWindowInsets = parent.getLastWindowInsets();
        if (lastWindowInsets != null && ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            rect.left += lastWindowInsets.getSystemWindowInsetLeft();
            rect.right -= lastWindowInsets.getSystemWindowInsetRight();
        }
        Rect rect2 = this.tempRect2;
        GravityCompat.apply(resolveGravity(layoutParams.gravity), child.getMeasuredWidth(), child.getMeasuredHeight(), rect, rect2, layoutDirection);
        int overlapPixelsForOffset = getOverlapPixelsForOffset(findFirstDependency);
        child.layout(rect2.left, rect2.top - overlapPixelsForOffset, rect2.right, rect2.bottom - overlapPixelsForOffset);
        this.verticalLayoutGap = rect2.top - findFirstDependency.getBottom();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        View findFirstDependency;
        WindowInsetsCompat lastWindowInsets;
        int i = child.getLayoutParams().height;
        if ((i == -1 || i == -2) && (findFirstDependency = findFirstDependency(parent.getDependencies(child))) != null) {
            int size = View.MeasureSpec.getSize(parentHeightMeasureSpec);
            if (size <= 0) {
                size = parent.getHeight();
            } else if (ViewCompat.getFitsSystemWindows(findFirstDependency) && (lastWindowInsets = parent.getLastWindowInsets()) != null) {
                size += lastWindowInsets.getSystemWindowInsetTop() + lastWindowInsets.getSystemWindowInsetBottom();
            }
            int scrollRange = getScrollRange(findFirstDependency) + size;
            int measuredHeight = findFirstDependency.getMeasuredHeight();
            if (shouldHeaderOverlapScrollingChild()) {
                child.setTranslationY(-measuredHeight);
            } else {
                scrollRange -= measuredHeight;
            }
            parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, View.MeasureSpec.makeMeasureSpec(scrollRange, i == -1 ? BasicMeasure.EXACTLY : Integer.MIN_VALUE), heightUsed);
            return true;
        }
        return false;
    }

    public final void setOverlayTop(int overlayTop) {
        this.overlayTop = overlayTop;
    }

    protected boolean shouldHeaderOverlapScrollingChild() {
        return false;
    }
}