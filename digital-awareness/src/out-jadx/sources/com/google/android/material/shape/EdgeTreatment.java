package com.google.android.material.shape;

/* loaded from: classes.dex */
public class EdgeTreatment {
    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean forceIntersection() {
        return false;
    }

    public void getEdgePath(float length, float center, float interpolation, ShapePath shapePath) {
        shapePath.lineTo(length, 0.0f);
    }

    @Deprecated
    public void getEdgePath(float length, float interpolation, ShapePath shapePath) {
        getEdgePath(length, length / 2.0f, interpolation, shapePath);
    }
}
