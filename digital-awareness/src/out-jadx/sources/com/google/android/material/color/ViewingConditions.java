package com.google.android.material.color;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class ViewingConditions {
    public static final ViewingConditions DEFAULT = make(ColorUtils.whitePointD65(), (float) ((ColorUtils.yFromLstar(50.0f) * 63.66197723675813d) / 100.0d), 50.0f, 2.0f, false);
    private final float aw;
    private final float c;
    private final float fl;
    private final float flRoot;
    private final float n;
    private final float nbb;
    private final float nc;
    private final float ncb;
    private final float[] rgbD;
    private final float z;

    private ViewingConditions(float n, float aw, float nbb, float ncb, float c, float nc, float[] rgbD, float fl, float flRoot, float z) {
        this.n = n;
        this.aw = aw;
        this.nbb = nbb;
        this.ncb = ncb;
        this.c = c;
        this.nc = nc;
        this.rgbD = rgbD;
        this.fl = fl;
        this.flRoot = flRoot;
        this.z = z;
    }

    static ViewingConditions make(float[] whitePoint, float adaptingLuminance, float backgroundLstar, float surround, boolean discountingIlluminant) {
        float[][] fArr = Cam16.XYZ_TO_CAM16RGB;
        float f = (whitePoint[0] * fArr[0][0]) + (whitePoint[1] * fArr[0][1]) + (whitePoint[2] * fArr[0][2]);
        float f2 = (whitePoint[0] * fArr[1][0]) + (whitePoint[1] * fArr[1][1]) + (whitePoint[2] * fArr[1][2]);
        float f3 = (whitePoint[0] * fArr[2][0]) + (whitePoint[1] * fArr[2][1]) + (whitePoint[2] * fArr[2][2]);
        float f4 = (surround / 10.0f) + 0.8f;
        float lerp = ((double) f4) >= 0.9d ? MathUtils.lerp(0.59f, 0.69f, (f4 - 0.9f) * 10.0f) : MathUtils.lerp(0.525f, 0.59f, (f4 - 0.8f) * 10.0f);
        float exp = discountingIlluminant ? 1.0f : (1.0f - (((float) Math.exp(((-adaptingLuminance) - 42.0f) / 92.0f)) * 0.2777778f)) * f4;
        float f5 = ((double) exp) > 1.0d ? 1.0f : ((double) exp) < 0.0d ? 0.0f : exp;
        float[] fArr2 = {(((100.0f / f) * f5) + 1.0f) - f5, (((100.0f / f2) * f5) + 1.0f) - f5, (((100.0f / f3) * f5) + 1.0f) - f5};
        float f6 = 1.0f / ((5.0f * adaptingLuminance) + 1.0f);
        float f7 = f6 * f6 * f6 * f6;
        float f8 = 1.0f - f7;
        float cbrt = (f7 * adaptingLuminance) + (0.1f * f8 * f8 * ((float) Math.cbrt(adaptingLuminance * 5.0d)));
        float yFromLstar = ColorUtils.yFromLstar(backgroundLstar) / whitePoint[1];
        float sqrt = ((float) Math.sqrt(yFromLstar)) + 1.48f;
        float pow = 0.725f / ((float) Math.pow(yFromLstar, 0.2d));
        float[] fArr3 = {(float) Math.pow(((fArr2[0] * cbrt) * f) / 100.0d, 0.42d), (float) Math.pow(((fArr2[1] * cbrt) * f2) / 100.0d, 0.42d), (float) Math.pow(((fArr2[2] * cbrt) * f3) / 100.0d, 0.42d)};
        float[] fArr4 = {(fArr3[0] * 400.0f) / (fArr3[0] + 27.13f), (fArr3[1] * 400.0f) / (fArr3[1] + 27.13f), (fArr3[2] * 400.0f) / (fArr3[2] + 27.13f)};
        return new ViewingConditions(yFromLstar, ((fArr4[0] * 2.0f) + fArr4[1] + (fArr4[2] * 0.05f)) * pow, pow, pow, lerp, f4, fArr2, cbrt, (float) Math.pow(cbrt, 0.25d), sqrt);
    }

    public float getAw() {
        return this.aw;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getC() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getFl() {
        return this.fl;
    }

    public float getFlRoot() {
        return this.flRoot;
    }

    public float getN() {
        return this.n;
    }

    public float getNbb() {
        return this.nbb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getNc() {
        return this.nc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getNcb() {
        return this.ncb;
    }

    public float[] getRgbD() {
        return this.rgbD;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getZ() {
        return this.z;
    }
}
