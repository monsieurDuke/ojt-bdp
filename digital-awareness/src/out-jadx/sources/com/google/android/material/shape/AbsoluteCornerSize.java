package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class AbsoluteCornerSize implements CornerSize {
    private final float size;

    public AbsoluteCornerSize(float size) {
        this.size = size;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof AbsoluteCornerSize) && this.size == ((AbsoluteCornerSize) o).size;
    }

    public float getCornerSize() {
        return this.size;
    }

    @Override // com.google.android.material.shape.CornerSize
    public float getCornerSize(RectF bounds) {
        return this.size;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.size)});
    }
}
