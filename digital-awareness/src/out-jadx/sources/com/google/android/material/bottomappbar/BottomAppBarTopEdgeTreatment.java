package com.google.android.material.bottomappbar;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

/* loaded from: classes.dex */
public class BottomAppBarTopEdgeTreatment extends EdgeTreatment implements Cloneable {
    private static final int ANGLE_LEFT = 180;
    private static final int ANGLE_UP = 270;
    private static final int ARC_HALF = 180;
    private static final int ARC_QUARTER = 90;
    private static final float ROUNDED_CORNER_FAB_OFFSET = 1.75f;
    private float cradleVerticalOffset;
    private float fabCornerSize = -1.0f;
    private float fabDiameter;
    private float fabMargin;
    private float horizontalOffset;
    private float roundedCornerRadius;

    public BottomAppBarTopEdgeTreatment(float fabMargin, float roundedCornerRadius, float cradleVerticalOffset) {
        this.fabMargin = fabMargin;
        this.roundedCornerRadius = roundedCornerRadius;
        setCradleVerticalOffset(cradleVerticalOffset);
        this.horizontalOffset = 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getCradleVerticalOffset() {
        return this.cradleVerticalOffset;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float length, float center, float interpolation, ShapePath shapePath) {
        float f;
        float f2;
        float f3 = this.fabDiameter;
        if (f3 == 0.0f) {
            shapePath.lineTo(length, 0.0f);
            return;
        }
        float f4 = ((this.fabMargin * 2.0f) + f3) / 2.0f;
        float f5 = interpolation * this.roundedCornerRadius;
        float f6 = center + this.horizontalOffset;
        float f7 = (this.cradleVerticalOffset * interpolation) + ((1.0f - interpolation) * f4);
        if (f7 / f4 >= 1.0f) {
            shapePath.lineTo(length, 0.0f);
            return;
        }
        float f8 = this.fabCornerSize;
        float f9 = f8 * interpolation;
        boolean z = f8 == -1.0f || Math.abs((f8 * 2.0f) - f3) < 0.1f;
        if (z) {
            f = 0.0f;
            f2 = f7;
        } else {
            f = 1.75f;
            f2 = 0.0f;
        }
        float f10 = f4 + f5;
        float f11 = f2 + f5;
        float sqrt = (float) Math.sqrt((f10 * f10) - (f11 * f11));
        float f12 = f6 - sqrt;
        float f13 = f6 + sqrt;
        float degrees = (float) Math.toDegrees(Math.atan(sqrt / f11));
        float f14 = (90.0f - degrees) + f;
        shapePath.lineTo(f12, 0.0f);
        shapePath.addArc(f12 - f5, 0.0f, f12 + f5, f5 * 2.0f, 270.0f, degrees);
        if (z) {
            shapePath.addArc(f6 - f4, (-f4) - f2, f6 + f4, f4 - f2, 180.0f - f14, (f14 * 2.0f) - 180.0f);
        } else {
            float f15 = this.fabMargin;
            shapePath.addArc(f6 - f4, -(f9 + f15), (f6 - f4) + f15 + (f9 * 2.0f), f15 + f9, 180.0f - f14, ((f14 * 2.0f) - 180.0f) / 2.0f);
            float f16 = this.fabMargin;
            shapePath.lineTo((f6 + f4) - (f9 + (f16 / 2.0f)), f9 + f16);
            float f17 = this.fabMargin;
            shapePath.addArc((f6 + f4) - ((f9 * 2.0f) + f17), -(f9 + f17), f6 + f4, f17 + f9, 90.0f, f14 - 90.0f);
        }
        shapePath.addArc(f13 - f5, 0.0f, f13 + f5, f5 * 2.0f, 270.0f - degrees, degrees);
        shapePath.lineTo(length, 0.0f);
    }

    public float getFabCornerRadius() {
        return this.fabCornerSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getFabCradleMargin() {
        return this.fabMargin;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getFabCradleRoundedCornerRadius() {
        return this.roundedCornerRadius;
    }

    public float getFabDiameter() {
        return this.fabDiameter;
    }

    public float getHorizontalOffset() {
        return this.horizontalOffset;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCradleVerticalOffset(float cradleVerticalOffset) {
        if (cradleVerticalOffset < 0.0f) {
            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        }
        this.cradleVerticalOffset = cradleVerticalOffset;
    }

    public void setFabCornerSize(float size) {
        this.fabCornerSize = size;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setFabCradleMargin(float fabMargin) {
        this.fabMargin = fabMargin;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setFabCradleRoundedCornerRadius(float roundedCornerRadius) {
        this.roundedCornerRadius = roundedCornerRadius;
    }

    public void setFabDiameter(float fabDiameter) {
        this.fabDiameter = fabDiameter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setHorizontalOffset(float horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }
}
