package com.google.android.material.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

/* loaded from: classes.dex */
public class MaterialDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int color;
    private Drawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private boolean lastItemDecorated;
    private int orientation;
    private final Rect tempRect;
    private int thickness;

    public MaterialDividerItemDecoration(Context context, int orientation) {
        this(context, null, orientation);
    }

    public MaterialDividerItemDecoration(Context context, AttributeSet attrs, int orientation) {
        this(context, attrs, R.attr.materialDividerStyle, orientation);
    }

    public MaterialDividerItemDecoration(Context context, AttributeSet attrs, int defStyleAttr, int orientation) {
        this.tempRect = new Rect();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.MaterialDivider, defStyleAttr, DEF_STYLE_RES, new int[0]);
        this.color = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.MaterialDivider_dividerColor).getDefaultColor();
        this.thickness = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialDivider_dividerThickness, context.getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));
        this.insetStart = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0);
        this.insetEnd = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0);
        this.lastItemDecorated = obtainStyledAttributes.getBoolean(R.styleable.MaterialDivider_lastItemDecorated, true);
        obtainStyledAttributes.recycle();
        this.dividerDrawable = new ShapeDrawable();
        setDividerColor(this.color);
        setOrientation(orientation);
    }

    private void drawForHorizontalOrientation(Canvas canvas, RecyclerView parent) {
        int i;
        int height;
        canvas.save();
        if (parent.getClipToPadding()) {
            i = parent.getPaddingTop();
            height = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), i, parent.getWidth() - parent.getPaddingRight(), height);
        } else {
            i = 0;
            height = parent.getHeight();
        }
        int i2 = i + this.insetStart;
        int i3 = height - this.insetEnd;
        int childCount = parent.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = parent.getChildAt(i4);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(childAt, this.tempRect);
            int round = this.tempRect.right + Math.round(childAt.getTranslationX());
            this.dividerDrawable.setBounds((round - this.dividerDrawable.getIntrinsicWidth()) - this.thickness, i2, round, i3);
            this.dividerDrawable.draw(canvas);
        }
        canvas.restore();
    }

    private void drawForVerticalOrientation(Canvas canvas, RecyclerView parent) {
        int i;
        int width;
        canvas.save();
        if (parent.getClipToPadding()) {
            i = parent.getPaddingLeft();
            width = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(i, parent.getPaddingTop(), width, parent.getHeight() - parent.getPaddingBottom());
        } else {
            i = 0;
            width = parent.getWidth();
        }
        boolean z = ViewCompat.getLayoutDirection(parent) == 1;
        int i2 = i + (z ? this.insetEnd : this.insetStart);
        int i3 = width - (z ? this.insetStart : this.insetEnd);
        int childCount = parent.getChildCount();
        int i4 = this.lastItemDecorated ? childCount : childCount - 1;
        for (int i5 = 0; i5 < i4; i5++) {
            View childAt = parent.getChildAt(i5);
            parent.getDecoratedBoundsWithMargins(childAt, this.tempRect);
            int round = this.tempRect.bottom + Math.round(childAt.getTranslationY());
            this.dividerDrawable.setBounds(i2, (round - this.dividerDrawable.getIntrinsicHeight()) - this.thickness, i3, round);
            this.dividerDrawable.draw(canvas);
        }
        canvas.restore();
    }

    public int getDividerColor() {
        return this.color;
    }

    public int getDividerInsetEnd() {
        return this.insetEnd;
    }

    public int getDividerInsetStart() {
        return this.insetStart;
    }

    public int getDividerThickness() {
        return this.thickness;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, 0);
        if (this.orientation == 1) {
            outRect.bottom = this.dividerDrawable.getIntrinsicHeight() + this.thickness;
        } else {
            outRect.right = this.dividerDrawable.getIntrinsicWidth() + this.thickness;
        }
    }

    public int getOrientation() {
        return this.orientation;
    }

    public boolean isLastItemDecorated() {
        return this.lastItemDecorated;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (this.orientation == 1) {
            drawForVerticalOrientation(canvas, parent);
        } else {
            drawForHorizontalOrientation(canvas, parent);
        }
    }

    public void setDividerColor(int color) {
        this.color = color;
        Drawable wrap = DrawableCompat.wrap(this.dividerDrawable);
        this.dividerDrawable = wrap;
        DrawableCompat.setTint(wrap, color);
    }

    public void setDividerColorResource(Context context, int colorId) {
        setDividerColor(ContextCompat.getColor(context, colorId));
    }

    public void setDividerInsetEnd(int insetEnd) {
        this.insetEnd = insetEnd;
    }

    public void setDividerInsetEndResource(Context context, int insetEndId) {
        setDividerInsetEnd(context.getResources().getDimensionPixelOffset(insetEndId));
    }

    public void setDividerInsetStart(int insetStart) {
        this.insetStart = insetStart;
    }

    public void setDividerInsetStartResource(Context context, int insetStartId) {
        setDividerInsetStart(context.getResources().getDimensionPixelOffset(insetStartId));
    }

    public void setDividerThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setDividerThicknessResource(Context context, int thicknessId) {
        setDividerThickness(context.getResources().getDimensionPixelSize(thicknessId));
    }

    public void setLastItemDecorated(boolean lastItemDecorated) {
        this.lastItemDecorated = lastItemDecorated;
    }

    public void setOrientation(int orientation) {
        if (orientation != 0 && orientation != 1) {
            throw new IllegalArgumentException("Invalid orientation: " + orientation + ". It should be either HORIZONTAL or VERTICAL");
        }
        this.orientation = orientation;
    }
}
