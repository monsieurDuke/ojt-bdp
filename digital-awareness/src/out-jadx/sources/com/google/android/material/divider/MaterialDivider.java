package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.shape.MaterialShapeDrawable;

/* loaded from: classes.dex */
public class MaterialDivider extends View {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider;
    private int color;
    private final MaterialShapeDrawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private int thickness;

    public MaterialDivider(Context context) {
        this(context, null);
    }

    public MaterialDivider(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialDividerStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public MaterialDivider(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            int r4 = com.google.android.material.divider.MaterialDivider.DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r8, r9, r10, r4)
            r7.<init>(r0, r9, r10)
            android.content.Context r8 = r7.getContext()
            com.google.android.material.shape.MaterialShapeDrawable r0 = new com.google.android.material.shape.MaterialShapeDrawable
            r0.<init>()
            r7.dividerDrawable = r0
            int[] r2 = com.google.android.material.R.styleable.MaterialDivider
            r6 = 0
            int[] r5 = new int[r6]
            r0 = r8
            r1 = r9
            r3 = r10
            android.content.res.TypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r1 = com.google.android.material.R.styleable.MaterialDivider_dividerThickness
            android.content.res.Resources r2 = r7.getResources()
            int r3 = com.google.android.material.R.dimen.material_divider_thickness
            int r2 = r2.getDimensionPixelSize(r3)
            int r1 = r0.getDimensionPixelSize(r1, r2)
            r7.thickness = r1
            int r1 = com.google.android.material.R.styleable.MaterialDivider_dividerInsetStart
            int r1 = r0.getDimensionPixelOffset(r1, r6)
            r7.insetStart = r1
            int r1 = com.google.android.material.R.styleable.MaterialDivider_dividerInsetEnd
            int r1 = r0.getDimensionPixelOffset(r1, r6)
            r7.insetEnd = r1
            int r1 = com.google.android.material.R.styleable.MaterialDivider_dividerColor
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList(r8, r0, r1)
            int r1 = r1.getDefaultColor()
            r7.setDividerColor(r1)
            r0.recycle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.divider.MaterialDivider.<init>(android.content.Context, android.util.AttributeSet, int):void");
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

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean z = ViewCompat.getLayoutDirection(this) == 1;
        this.dividerDrawable.setBounds(z ? this.insetEnd : this.insetStart, 0, getWidth() - (z ? this.insetStart : this.insetEnd), getBottom() - getTop());
        this.dividerDrawable.draw(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        if (mode == Integer.MIN_VALUE || mode == 0) {
            int i = this.thickness;
            if (i > 0 && measuredHeight != i) {
                measuredHeight = this.thickness;
            }
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
    }

    public void setDividerColor(int color) {
        if (this.color != color) {
            this.color = color;
            this.dividerDrawable.setFillColor(ColorStateList.valueOf(color));
            invalidate();
        }
    }

    public void setDividerColorResource(int colorId) {
        setDividerColor(ContextCompat.getColor(getContext(), colorId));
    }

    public void setDividerInsetEnd(int insetEnd) {
        this.insetEnd = insetEnd;
    }

    public void setDividerInsetEndResource(int insetEndId) {
        setDividerInsetEnd(getContext().getResources().getDimensionPixelOffset(insetEndId));
    }

    public void setDividerInsetStart(int insetStart) {
        this.insetStart = insetStart;
    }

    public void setDividerInsetStartResource(int insetStartId) {
        setDividerInsetStart(getContext().getResources().getDimensionPixelOffset(insetStartId));
    }

    public void setDividerThickness(int thickness) {
        if (this.thickness != thickness) {
            this.thickness = thickness;
            requestLayout();
        }
    }

    public void setDividerThicknessResource(int thicknessId) {
        setDividerThickness(getContext().getResources().getDimensionPixelSize(thicknessId));
    }
}