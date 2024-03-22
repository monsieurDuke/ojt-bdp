package com.google.android.material.color;

/* loaded from: classes.dex */
final class Cam16 {
    private final float astar;
    private final float bstar;
    private final float chroma;
    private final float hue;
    private final float j;
    private final float jstar;
    private final float m;
    private final float q;
    private final float s;
    static final float[][] XYZ_TO_CAM16RGB = {new float[]{0.401288f, 0.650173f, -0.051461f}, new float[]{-0.250268f, 1.204414f, 0.045854f}, new float[]{-0.002079f, 0.048952f, 0.953127f}};
    static final float[][] CAM16RGB_TO_XYZ = {new float[]{1.8620678f, -1.0112547f, 0.14918678f}, new float[]{0.38752654f, 0.62144744f, -0.00897398f}, new float[]{-0.0158415f, -0.03412294f, 1.0499644f}};

    private Cam16(float hue, float chroma, float j, float q, float m, float s, float jstar, float astar, float bstar) {
        this.hue = hue;
        this.chroma = chroma;
        this.j = j;
        this.q = q;
        this.m = m;
        this.s = s;
        this.jstar = jstar;
        this.astar = astar;
        this.bstar = bstar;
    }

    public static Cam16 fromInt(int argb) {
        return fromIntInViewingConditions(argb, ViewingConditions.DEFAULT);
    }

    static Cam16 fromIntInViewingConditions(int argb, ViewingConditions viewingConditions) {
        float linearized = ColorUtils.linearized(((16711680 & argb) >> 16) / 255.0f) * 100.0f;
        float linearized2 = ColorUtils.linearized(((65280 & argb) >> 8) / 255.0f) * 100.0f;
        float linearized3 = ColorUtils.linearized((argb & 255) / 255.0f) * 100.0f;
        float f = (0.41233894f * linearized) + (0.35762063f * linearized2) + (0.18051042f * linearized3);
        float f2 = (0.2126f * linearized) + (0.7152f * linearized2) + (0.0722f * linearized3);
        float f3 = (0.01932141f * linearized) + (0.11916382f * linearized2) + (0.9503448f * linearized3);
        float[][] fArr = XYZ_TO_CAM16RGB;
        float f4 = (fArr[0][0] * f) + (fArr[0][1] * f2) + (fArr[0][2] * f3);
        float f5 = (fArr[1][0] * f) + (fArr[1][1] * f2) + (fArr[1][2] * f3);
        float f6 = (fArr[2][0] * f) + (fArr[2][1] * f2) + (fArr[2][2] * f3);
        float f7 = viewingConditions.getRgbD()[0] * f4;
        float f8 = viewingConditions.getRgbD()[1] * f5;
        float f9 = viewingConditions.getRgbD()[2] * f6;
        float pow = (float) Math.pow((viewingConditions.getFl() * Math.abs(f7)) / 100.0d, 0.42d);
        float pow2 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f8)) / 100.0d, 0.42d);
        float pow3 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f9)) / 100.0d, 0.42d);
        float signum = ((Math.signum(f7) * 400.0f) * pow) / (pow + 27.13f);
        float signum2 = ((Math.signum(f8) * 400.0f) * pow2) / (pow2 + 27.13f);
        float signum3 = ((Math.signum(f9) * 400.0f) * pow3) / (27.13f + pow3);
        float f10 = ((float) ((signum + signum2) - (signum3 * 2.0d))) / 9.0f;
        float f11 = (((signum * 20.0f) + (signum2 * 20.0f)) + (21.0f * signum3)) / 20.0f;
        float f12 = (((40.0f * signum) + (signum2 * 20.0f)) + signum3) / 20.0f;
        float atan2 = (((float) Math.atan2(f10, ((float) (((signum * 11.0d) + (signum2 * (-12.0d))) + signum3)) / 11.0f)) * 180.0f) / 3.1415927f;
        float f13 = atan2 < 0.0f ? atan2 + 360.0f : atan2 >= 360.0f ? atan2 - 360.0f : atan2;
        float f14 = (f13 * 3.1415927f) / 180.0f;
        float pow4 = ((float) Math.pow((viewingConditions.getNbb() * f12) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ())) * 100.0f;
        float c = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(pow4 / 100.0f)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float sqrt = ((float) Math.sqrt(pow4 / 100.0d)) * ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d)) * ((float) Math.pow((((float) Math.hypot(r0, f10)) * (((3846.1538f * (((float) (Math.cos(Math.toRadians(((double) f13) < 20.14d ? f13 + 360.0f : f13) + 2.0d) + 3.8d)) * 0.25f)) * viewingConditions.getNc()) * viewingConditions.getNcb())) / (0.305f + f11), 0.9d));
        float flRoot = viewingConditions.getFlRoot() * sqrt;
        float log1p = ((float) Math.log1p(flRoot * 0.0228f)) * 43.85965f;
        return new Cam16(f13, sqrt, pow4, c, flRoot, ((float) Math.sqrt((viewingConditions.getC() * r0) / (viewingConditions.getAw() + 4.0f))) * 50.0f, (1.7f * pow4) / ((0.007f * pow4) + 1.0f), ((float) Math.cos(f14)) * log1p, ((float) Math.sin(f14)) * log1p);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Cam16 fromJch(float j, float c, float h) {
        return fromJchInViewingConditions(j, c, h, ViewingConditions.DEFAULT);
    }

    private static Cam16 fromJchInViewingConditions(float j, float c, float h, ViewingConditions viewingConditions) {
        float c2 = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(j / 100.0d)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float flRoot = c * viewingConditions.getFlRoot();
        float sqrt = ((float) Math.sqrt((viewingConditions.getC() * (c / ((float) Math.sqrt(j / 100.0d)))) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f = (3.1415927f * h) / 180.0f;
        float f2 = (1.7f * j) / ((0.007f * j) + 1.0f);
        float log1p = ((float) Math.log1p(flRoot * 0.0228d)) * 43.85965f;
        return new Cam16(h, c, j, c2, flRoot, sqrt, f2, log1p * ((float) Math.cos(f)), log1p * ((float) Math.sin(f)));
    }

    public static Cam16 fromUcs(float jstar, float astar, float bstar) {
        return fromUcsInViewingConditions(jstar, astar, bstar, ViewingConditions.DEFAULT);
    }

    public static Cam16 fromUcsInViewingConditions(float jstar, float astar, float bstar, ViewingConditions viewingConditions) {
        double expm1 = (Math.expm1(Math.hypot(astar, bstar) * 0.02280000038444996d) / 0.02280000038444996d) / viewingConditions.getFlRoot();
        double atan2 = Math.atan2(bstar, astar) * 57.29577951308232d;
        if (atan2 < 0.0d) {
            atan2 += 360.0d;
        }
        return fromJchInViewingConditions(jstar / (1.0f - ((jstar - 100.0f) * 0.007f)), (float) expm1, (float) atan2, viewingConditions);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float distance(Cam16 other) {
        float jStar = getJStar() - other.getJStar();
        float aStar = getAStar() - other.getAStar();
        float bStar = getBStar() - other.getBStar();
        return (float) (Math.pow(Math.sqrt((jStar * jStar) + (aStar * aStar) + (bStar * bStar)), 0.63d) * 1.41d);
    }

    public float getAStar() {
        return this.astar;
    }

    public float getBStar() {
        return this.bstar;
    }

    public float getChroma() {
        return this.chroma;
    }

    public float getHue() {
        return this.hue;
    }

    public int getInt() {
        return viewed(ViewingConditions.DEFAULT);
    }

    public float getJ() {
        return this.j;
    }

    public float getJStar() {
        return this.jstar;
    }

    public float getM() {
        return this.m;
    }

    public float getQ() {
        return this.q;
    }

    public float getS() {
        return this.s;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int viewed(ViewingConditions viewingConditions) {
        float pow = (float) Math.pow(((((double) getChroma()) == 0.0d || ((double) getJ()) == 0.0d) ? 0.0f : getChroma() / ((float) Math.sqrt(getJ() / 100.0d))) / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d), 1.1111111111111112d);
        float hue = (getHue() * 3.1415927f) / 180.0f;
        float cos = ((float) (Math.cos(hue + 2.0d) + 3.8d)) * 0.25f;
        float aw = viewingConditions.getAw() * ((float) Math.pow(getJ() / 100.0d, (1.0d / viewingConditions.getC()) / viewingConditions.getZ()));
        float nc = 3846.1538f * cos * viewingConditions.getNc() * viewingConditions.getNcb();
        float nbb = aw / viewingConditions.getNbb();
        float sin = (float) Math.sin(hue);
        float cos2 = (float) Math.cos(hue);
        float f = (((0.305f + nbb) * 23.0f) * pow) / (((23.0f * nc) + ((11.0f * pow) * cos2)) + ((108.0f * pow) * sin));
        float f2 = f * cos2;
        float f3 = f * sin;
        float signum = Math.signum((((nbb * 460.0f) + (451.0f * f2)) + (288.0f * f3)) / 1403.0f) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(r15) * 27.13d) / (400.0d - Math.abs(r15))), 2.380952380952381d));
        float signum2 = Math.signum((((nbb * 460.0f) - (891.0f * f2)) - (261.0f * f3)) / 1403.0f) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(r17) * 27.13d) / (400.0d - Math.abs(r17))), 2.380952380952381d));
        float signum3 = Math.signum((((460.0f * nbb) - (220.0f * f2)) - (6300.0f * f3)) / 1403.0f) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(r14) * 27.13d) / (400.0d - Math.abs(r14))), 2.380952380952381d));
        float f4 = signum / viewingConditions.getRgbD()[0];
        float f5 = signum2 / viewingConditions.getRgbD()[1];
        float f6 = signum3 / viewingConditions.getRgbD()[2];
        float[][] fArr = CAM16RGB_TO_XYZ;
        return ColorUtils.intFromXyzComponents((fArr[0][0] * f4) + (fArr[0][1] * f5) + (fArr[0][2] * f6), (fArr[1][0] * f4) + (fArr[1][1] * f5) + (fArr[1][2] * f6), (fArr[2][0] * f4) + (fArr[2][1] * f5) + (fArr[2][2] * f6));
    }
}
