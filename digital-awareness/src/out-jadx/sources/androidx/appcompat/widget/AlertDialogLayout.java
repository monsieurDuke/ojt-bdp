package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class AlertDialogLayout extends LinearLayoutCompat {
    public AlertDialogLayout(Context context) {
        super(context);
    }

    public AlertDialogLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), BasicMeasure.EXACTLY);
        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i2 = layoutParams.height;
                    layoutParams.height = childAt.getMeasuredHeight();
                    measureChildWithMargins(childAt, makeMeasureSpec, 0, heightMeasureSpec, 0);
                    layoutParams.height = i2;
                }
            }
        }
    }

    private static int resolveMinimumHeight(View v) {
        int minimumHeight = ViewCompat.getMinimumHeight(v);
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            if (viewGroup.getChildCount() == 1) {
                return resolveMinimumHeight(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    private boolean tryOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View view = null;
        View view2 = null;
        View view3 = null;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                int id = childAt.getId();
                if (id == R.id.topPanel) {
                    view = childAt;
                } else if (id == R.id.buttonPanel) {
                    view2 = childAt;
                } else {
                    if ((id != R.id.contentPanel && id != R.id.customPanel) || view3 != null) {
                        return false;
                    }
                    view3 = childAt;
                }
            }
        }
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(heightMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(widthMeasureSpec);
        int i2 = 0;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (view != null) {
            view.measure(widthMeasureSpec, 0);
            paddingTop += view.getMeasuredHeight();
            i2 = View.combineMeasuredStates(0, view.getMeasuredState());
        }
        int i3 = 0;
        int i4 = 0;
        if (view2 != null) {
            view2.measure(widthMeasureSpec, 0);
            i3 = resolveMinimumHeight(view2);
            i4 = view2.getMeasuredHeight() - i3;
            paddingTop += i3;
            i2 = View.combineMeasuredStates(i2, view2.getMeasuredState());
        }
        int i5 = 0;
        if (view3 != null) {
            view3.measure(widthMeasureSpec, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingTop), mode));
            i5 = view3.getMeasuredHeight();
            paddingTop += i5;
            i2 = View.combineMeasuredStates(i2, view3.getMeasuredState());
        }
        int i6 = size - paddingTop;
        if (view2 != null) {
            int i7 = paddingTop - i3;
            int min = Math.min(i6, i4);
            if (min > 0) {
                i6 -= min;
                i3 += min;
            }
            view2.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(i3, BasicMeasure.EXACTLY));
            paddingTop = i7 + view2.getMeasuredHeight();
            i2 = View.combineMeasuredStates(i2, view2.getMeasuredState());
            i6 = i6;
        }
        if (view3 != null && i6 > 0) {
            int i8 = i6;
            view3.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(i5 + i8, mode));
            paddingTop = (paddingTop - i5) + view3.getMeasuredHeight();
            i2 = View.combineMeasuredStates(i2, view3.getMeasuredState());
            i6 -= i8;
        }
        int i9 = 0;
        int i10 = 0;
        while (i10 < childCount) {
            View childAt2 = getChildAt(i10);
            View view4 = view2;
            View view5 = view3;
            if (childAt2.getVisibility() != 8) {
                i9 = Math.max(i9, childAt2.getMeasuredWidth());
            }
            i10++;
            view2 = view4;
            view3 = view5;
        }
        setMeasuredDimension(View.resolveSizeAndState(i9 + getPaddingLeft() + getPaddingRight(), widthMeasureSpec, i2), View.resolveSizeAndState(paddingTop, heightMeasureSpec, 0));
        if (mode2 == 1073741824) {
            return true;
        }
        forceUniformWidth(childCount, heightMeasureSpec);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int paddingTop;
        int i;
        int i2;
        AlertDialogLayout alertDialogLayout = this;
        int paddingLeft = getPaddingLeft();
        int i3 = right - left;
        int paddingRight = i3 - getPaddingRight();
        int paddingRight2 = (i3 - paddingLeft) - getPaddingRight();
        int measuredHeight = getMeasuredHeight();
        int childCount = getChildCount();
        int gravity = getGravity();
        int i4 = gravity & 112;
        int i5 = gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (i4) {
            case 16:
                paddingTop = getPaddingTop() + (((bottom - top) - measuredHeight) / 2);
                break;
            case 80:
                paddingTop = ((getPaddingTop() + bottom) - top) - measuredHeight;
                break;
            default:
                paddingTop = getPaddingTop();
                break;
        }
        Drawable dividerDrawable = getDividerDrawable();
        int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        int i6 = 0;
        while (i6 < childCount) {
            View childAt = alertDialogLayout.getChildAt(i6);
            if (childAt == null || childAt.getVisibility() == 8) {
                i = i6;
            } else {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams();
                int i7 = layoutParams.gravity;
                switch (GravityCompat.getAbsoluteGravity(i7 < 0 ? i5 : i7, ViewCompat.getLayoutDirection(this)) & 7) {
                    case 1:
                        i2 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + layoutParams.leftMargin) - layoutParams.rightMargin;
                        break;
                    case 5:
                        i2 = (paddingRight - measuredWidth) - layoutParams.rightMargin;
                        break;
                    default:
                        i2 = layoutParams.leftMargin + paddingLeft;
                        break;
                }
                if (alertDialogLayout.hasDividerBeforeChildAt(i6)) {
                    paddingTop += intrinsicHeight;
                }
                int i8 = paddingTop + layoutParams.topMargin;
                i = i6;
                setChildFrame(childAt, i2, i8, measuredWidth, measuredHeight2);
                paddingTop = i8 + measuredHeight2 + layoutParams.bottomMargin;
            }
            i6 = i + 1;
            alertDialogLayout = this;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (tryOnMeasure(widthMeasureSpec, heightMeasureSpec)) {
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
