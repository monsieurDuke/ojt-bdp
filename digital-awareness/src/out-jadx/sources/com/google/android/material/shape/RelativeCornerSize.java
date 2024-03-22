package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class RelativeCornerSize implements CornerSize {
    private final float percent;

    public RelativeCornerSize(float percent) {
        this.percent = percent;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof RelativeCornerSize) && this.percent == ((RelativeCornerSize) o).percent;
    }

    @Override // com.google.android.material.shape.CornerSize
    public float getCornerSize(RectF bounds) {
        return this.percent * bounds.height();
    }

    public float getRelativePercent() {
        return this.percent;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.percent)});
    }
}
