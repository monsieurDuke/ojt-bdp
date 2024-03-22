package androidx.transition;

import android.animation.TypeEvaluator;

/* loaded from: classes.dex */
class FloatArrayEvaluator implements TypeEvaluator<float[]> {
    private float[] mArray;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FloatArrayEvaluator(float[] reuseArray) {
        this.mArray = reuseArray;
    }

    @Override // android.animation.TypeEvaluator
    public float[] evaluate(float fraction, float[] startValue, float[] endValue) {
        float[] fArr = this.mArray;
        if (fArr == null) {
            fArr = new float[startValue.length];
        }
        for (int i = 0; i < fArr.length; i++) {
            float f = startValue[i];
            fArr[i] = ((endValue[i] - f) * fraction) + f;
        }
        return fArr;
    }
}
