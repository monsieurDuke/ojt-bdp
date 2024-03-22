package com.google.android.material.animation;

import android.animation.TypeEvaluator;

/* loaded from: classes.dex */
public class ArgbEvaluatorCompat implements TypeEvaluator<Integer> {
    private static final ArgbEvaluatorCompat instance = new ArgbEvaluatorCompat();

    public static ArgbEvaluatorCompat getInstance() {
        return instance;
    }

    @Override // android.animation.TypeEvaluator
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int intValue = startValue.intValue();
        float f = ((intValue >> 24) & 255) / 255.0f;
        int intValue2 = endValue.intValue();
        float pow = (float) Math.pow(((intValue >> 16) & 255) / 255.0f, 2.2d);
        float pow2 = (float) Math.pow(((intValue >> 8) & 255) / 255.0f, 2.2d);
        float pow3 = (float) Math.pow((intValue & 255) / 255.0f, 2.2d);
        float pow4 = (float) Math.pow(((intValue2 >> 16) & 255) / 255.0f, 2.2d);
        float pow5 = (float) Math.pow(((intValue2 >> 8) & 255) / 255.0f, 2.2d);
        float pow6 = (float) Math.pow((intValue2 & 255) / 255.0f, 2.2d);
        return Integer.valueOf((Math.round(((((((intValue2 >> 24) & 255) / 255.0f) - f) * fraction) + f) * 255.0f) << 24) | (Math.round(((float) Math.pow(((pow4 - pow) * fraction) + pow, 0.45454545454545453d)) * 255.0f) << 16) | (Math.round(((float) Math.pow(((pow5 - pow2) * fraction) + pow2, 0.45454545454545453d)) * 255.0f) << 8) | Math.round(((float) Math.pow(((pow6 - pow3) * fraction) + pow3, 0.45454545454545453d)) * 255.0f));
    }
}
