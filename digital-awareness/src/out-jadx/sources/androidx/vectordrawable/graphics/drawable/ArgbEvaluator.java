package androidx.vectordrawable.graphics.drawable;

import android.animation.TypeEvaluator;

/* loaded from: classes.dex */
public class ArgbEvaluator implements TypeEvaluator {
    private static final ArgbEvaluator sInstance = new ArgbEvaluator();

    public static ArgbEvaluator getInstance() {
        return sInstance;
    }

    @Override // android.animation.TypeEvaluator
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int intValue = ((Integer) startValue).intValue();
        float f = ((intValue >> 24) & 255) / 255.0f;
        int intValue2 = ((Integer) endValue).intValue();
        float pow = (float) Math.pow(((intValue >> 16) & 255) / 255.0f, 2.2d);
        float pow2 = (float) Math.pow(((intValue >> 8) & 255) / 255.0f, 2.2d);
        float pow3 = (float) Math.pow((intValue & 255) / 255.0f, 2.2d);
        float pow4 = (float) Math.pow(((intValue2 >> 16) & 255) / 255.0f, 2.2d);
        float pow5 = (float) Math.pow(((intValue2 >> 8) & 255) / 255.0f, 2.2d);
        float pow6 = (float) Math.pow((intValue2 & 255) / 255.0f, 2.2d);
        return Integer.valueOf((Math.round(((((((intValue2 >> 24) & 255) / 255.0f) - f) * fraction) + f) * 255.0f) << 24) | (Math.round(((float) Math.pow(((pow4 - pow) * fraction) + pow, 0.45454545454545453d)) * 255.0f) << 16) | (Math.round(((float) Math.pow(((pow5 - pow2) * fraction) + pow2, 0.45454545454545453d)) * 255.0f) << 8) | Math.round(((float) Math.pow(((pow6 - pow3) * fraction) + pow3, 0.45454545454545453d)) * 255.0f));
    }
}
