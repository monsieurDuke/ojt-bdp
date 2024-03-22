package androidx.interpolator.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes.dex */
abstract class LookupTableInterpolator implements Interpolator {
    private final float mStepSize;
    private final float[] mValues;

    /* JADX INFO: Access modifiers changed from: protected */
    public LookupTableInterpolator(float[] values) {
        this.mValues = values;
        this.mStepSize = 1.0f / ((float) (values.length - 1));
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float input) {
        if (input >= 1.0f) {
            return 1.0f;
        }
        if (input <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.mValues;
        int min = Math.min((int) (((float) (fArr.length - 1)) * input), fArr.length - 2);
        float f = this.mStepSize;
        float[] fArr2 = this.mValues;
        float f2 = fArr2[min];
        return f2 + ((fArr2[min + 1] - f2) * ((input - (min * f)) / f));
    }
}
