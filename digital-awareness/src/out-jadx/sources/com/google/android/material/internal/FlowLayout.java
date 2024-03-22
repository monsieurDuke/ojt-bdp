package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;

/* loaded from: classes.dex */
public class FlowLayout extends ViewGroup {
    private int itemSpacing;
    private int lineSpacing;
    private int rowCount;
    private boolean singleLine;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.singleLine = false;
        loadFromAttributes(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.singleLine = false;
        loadFromAttributes(context, attrs);
    }

    private static int getMeasuredDimension(int size, int mode, int childrenEdge) {
        switch (mode) {
            case Integer.MIN_VALUE:
                return Math.min(childrenEdge, size);
            case BasicMeasure.EXACTLY /* 1073741824 */:
                return size;
            default:
                return childrenEdge;
        }
    }

    private void loadFromAttributes(Context context, AttributeSet attrs) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0);
        this.lineSpacing = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
        this.itemSpacing = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
        obtainStyledAttributes.recycle();
    }

    protected int getItemSpacing() {
        return this.itemSpacing;
    }

    protected int getLineSpacing() {
        return this.lineSpacing;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRowCount() {
        return this.rowCount;
    }

    public int getRowIndex(View child) {
        Object tag = child.getTag(R.id.row_index_key);
        if (tag instanceof Integer) {
            return ((Integer) tag).intValue();
        }
        return -1;
    }

    public boolean isSingleLine() {
        return this.singleLine;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean sizeChanged, int left, int top, int right, int bottom) {
        int i;
        if (getChildCount() == 0) {
            this.rowCount = 0;
            return;
        }
        this.rowCount = 1;
        boolean z = ViewCompat.getLayoutDirection(this) == 1;
        int paddingRight = z ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = z ? getPaddingLeft() : getPaddingRight();
        int i2 = paddingRight;
        int paddingTop = getPaddingTop();
        int i3 = paddingTop;
        int i4 = (right - left) - paddingLeft;
        int i5 = 0;
        while (i5 < getChildCount()) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() == 8) {
                childAt.setTag(R.id.row_index_key, -1);
                i = paddingRight;
            } else {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                int i6 = 0;
                int i7 = 0;
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i6 = MarginLayoutParamsCompat.getMarginStart(marginLayoutParams);
                    i7 = MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
                }
                int measuredWidth = i2 + i6 + childAt.getMeasuredWidth();
                if (!this.singleLine && measuredWidth > i4) {
                    i2 = paddingRight;
                    paddingTop = i3 + this.lineSpacing;
                    this.rowCount++;
                }
                i = paddingRight;
                childAt.setTag(R.id.row_index_key, Integer.valueOf(this.rowCount - 1));
                int measuredWidth2 = i2 + i6 + childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                if (z) {
                    childAt.layout(i4 - measuredWidth2, paddingTop, (i4 - i2) - i6, measuredHeight);
                } else {
                    childAt.layout(i2 + i6, paddingTop, measuredWidth2, measuredHeight);
                }
                i2 += i6 + i7 + childAt.getMeasuredWidth() + this.itemSpacing;
                i3 = measuredHeight;
            }
            i5++;
            paddingRight = i;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int i2 = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i3 = paddingTop;
        int i4 = 0;
        int paddingRight = i2 - getPaddingRight();
        int i5 = 0;
        while (i5 < getChildCount()) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() == 8) {
                i = i2;
            } else {
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                i = i2;
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                int i6 = 0;
                int i7 = 0;
                int i8 = paddingTop;
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i6 = 0 + marginLayoutParams.leftMargin;
                    i7 = 0 + marginLayoutParams.rightMargin;
                }
                if (paddingLeft + i6 + childAt.getMeasuredWidth() <= paddingRight || isSingleLine()) {
                    paddingTop = i8;
                } else {
                    paddingLeft = getPaddingLeft();
                    paddingTop = this.lineSpacing + i3;
                }
                int measuredWidth = paddingLeft + i6 + childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                if (measuredWidth > i4) {
                    i4 = measuredWidth;
                }
                paddingLeft += i6 + i7 + childAt.getMeasuredWidth() + this.itemSpacing;
                if (i5 == getChildCount() - 1) {
                    i4 += i7;
                    i3 = measuredHeight;
                } else {
                    i3 = measuredHeight;
                }
            }
            i5++;
            i2 = i;
        }
        setMeasuredDimension(getMeasuredDimension(size, mode, i4 + getPaddingRight()), getMeasuredDimension(size2, mode2, i3 + getPaddingBottom()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setItemSpacing(int itemSpacing) {
        this.itemSpacing = itemSpacing;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }
}
