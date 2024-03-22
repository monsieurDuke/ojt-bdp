package androidx.core.content.res;

/* loaded from: classes.dex */
final class ViewingConditions {
    static final ViewingConditions DEFAULT = make(CamUtils.WHITE_POINT_D65, (float) ((CamUtils.yFromLStar(50.0f) * 63.66197723675813d) / 100.0d), 50.0f, 2.0f, false);
    private final float mAw;
    private final float mC;
    private final float mFl;
    private final float mFlRoot;
    private final float mN;
    private final float mNbb;
    private final float mNc;
    private final float mNcb;
    private final float[] mRgbD;
    private final float mZ;

    private ViewingConditions(float n, float aw, float nbb, float ncb, float c, float nc, float[] rgbD, float fl, float fLRoot, float z) {
        this.mN = n;
        this.mAw = aw;
        this.mNbb = nbb;
        this.mNcb = ncb;
        this.mC = c;
        this.mNc = nc;
        this.mRgbD = rgbD;
        this.mFl = fl;
        this.mFlRoot = fLRoot;
        this.mZ = z;
    }

    static ViewingConditions make(float[] whitepoint, float adaptingLuminance, float backgroundLstar, float surround, boolean discountingIlluminant) {
        float[][] fArr = CamUtils.XYZ_TO_CAM16RGB;
        float f = (whitepoint[0] * fArr[0][0]) + (whitepoint[1] * fArr[0][1]) + (whitepoint[2] * fArr[0][2]);
        float f2 = (whitepoint[0] * fArr[1][0]) + (whitepoint[1] * fArr[1][1]) + (whitepoint[2] * fArr[1][2]);
        float f3 = (whitepoint[0] * fArr[2][0]) + (whitepoint[1] * fArr[2][1]) + (whitepoint[2] * fArr[2][2]);
        float f4 = (surround / 10.0f) + 0.8f;
        float lerp = ((double) f4) >= 0.9d ? CamUtils.lerp(0.59f, 0.69f, (f4 - 0.9f) * 10.0f) : CamUtils.lerp(0.525f, 0.59f, (f4 - 0.8f) * 10.0f);
        float exp = discountingIlluminant ? 1.0f : (1.0f - (((float) Math.exp(((-adaptingLuminance) - 42.0f) / 92.0f)) * 0.2777778f)) * f4;
        float f5 = ((double) exp) > 1.0d ? 1.0f : ((double) exp) < 0.0d ? 0.0f : exp;
        float[] fArr2 = {(((100.0f / f) * f5) + 1.0f) - f5, (((100.0f / f2) * f5) + 1.0f) - f5, (((100.0f / f3) * f5) + 1.0f) - f5};
        float f6 = 1.0f / ((5.0f * adaptingLuminance) + 1.0f);
        float f7 = f6 * f6 * f6 * f6;
        float f8 = 1.0f - f7;
        float cbrt = (f7 * adaptingLuminance) + (0.1f * f8 * f8 * ((float) Math.cbrt(adaptingLuminance * 5.0d)));
        float yFromLStar = CamUtils.yFromLStar(backgroundLstar) / whitepoint[1];
        float sqrt = ((float) Math.sqrt(yFromLStar)) + 1.48f;
        float pow = 0.725f / ((float) Math.pow(yFromLStar, 0.2d));
        float[] fArr3 = {(float) Math.pow(((fArr2[0] * cbrt) * f) / 100.0d, 0.42d), (float) Math.pow(((fArr2[1] * cbrt) * f2) / 100.0d, 0.42d), (float) Math.pow(((fArr2[2] * cbrt) * f3) / 100.0d, 0.42d)};
        float[] fArr4 = {(fArr3[0] * 400.0f) / (fArr3[0] + 27.13f), (fArr3[1] * 400.0f) / (fArr3[1] + 27.13f), (fArr3[2] * 400.0f) / (fArr3[2] + 27.13f)};
        return new ViewingConditions(yFromLStar, ((fArr4[0] * 2.0f) + fArr4[1] + (fArr4[2] * 0.05f)) * pow, pow, pow, lerp, f4, fArr2, cbrt, (float) Math.pow(cbrt, 0.25d), sqrt);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getAw() {
        return this.mAw;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getC() {
        return this.mC;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getFl() {
        return this.mFl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getFlRoot() {
        return this.mFlRoot;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getN() {
        return this.mN;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getNbb() {
        return this.mNbb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getNc() {
        return this.mNc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getNcb() {
        return this.mNcb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float[] getRgbD() {
        return this.mRgbD;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getZ() {
        return this.mZ;
    }
}
