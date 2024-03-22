package androidx.core.content.res;

import androidx.core.graphics.ColorUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class CamColor {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;
    private final float mJ;
    private final float mJstar;
    private final float mM;
    private final float mQ;
    private final float mS;

    CamColor(float hue, float chroma, float j, float q, float m, float s, float jStar, float aStar, float bStar) {
        this.mHue = hue;
        this.mChroma = chroma;
        this.mJ = j;
        this.mQ = q;
        this.mM = m;
        this.mS = s;
        this.mJstar = jStar;
        this.mAstar = aStar;
        this.mBstar = bStar;
    }

    private static CamColor findCamByJ(float hue, float chroma, float lstar) {
        float f = 0.0f;
        float f2 = 100.0f;
        float f3 = 1000.0f;
        float f4 = 1000.0f;
        CamColor camColor = null;
        while (Math.abs(f - f2) > LIGHTNESS_SEARCH_ENDPOINT) {
            float f5 = f + ((f2 - f) / 2.0f);
            int viewedInSrgb = fromJch(f5, chroma, hue).viewedInSrgb();
            float lStarFromInt = CamUtils.lStarFromInt(viewedInSrgb);
            float abs = Math.abs(lstar - lStarFromInt);
            if (abs < 0.2f) {
                CamColor fromColor = fromColor(viewedInSrgb);
                float distance = fromColor.distance(fromJch(fromColor.getJ(), fromColor.getChroma(), hue));
                if (distance <= 1.0f) {
                    f3 = abs;
                    f4 = distance;
                    camColor = fromColor;
                }
            }
            if (f3 == 0.0f && f4 == 0.0f) {
                break;
            }
            if (lStarFromInt < lstar) {
                f = f5;
            } else {
                f2 = f5;
            }
        }
        return camColor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CamColor fromColor(int color) {
        return fromColorInViewingConditions(color, ViewingConditions.DEFAULT);
    }

    static CamColor fromColorInViewingConditions(int color, ViewingConditions viewingConditions) {
        float[] xyzFromInt = CamUtils.xyzFromInt(color);
        float[][] fArr = CamUtils.XYZ_TO_CAM16RGB;
        float f = (xyzFromInt[0] * fArr[0][0]) + (xyzFromInt[1] * fArr[0][1]) + (xyzFromInt[2] * fArr[0][2]);
        float f2 = (xyzFromInt[0] * fArr[1][0]) + (xyzFromInt[1] * fArr[1][1]) + (xyzFromInt[2] * fArr[1][2]);
        float f3 = (xyzFromInt[0] * fArr[2][0]) + (xyzFromInt[1] * fArr[2][1]) + (xyzFromInt[2] * fArr[2][2]);
        float f4 = viewingConditions.getRgbD()[0] * f;
        float f5 = viewingConditions.getRgbD()[1] * f2;
        float f6 = viewingConditions.getRgbD()[2] * f3;
        float pow = (float) Math.pow((viewingConditions.getFl() * Math.abs(f4)) / 100.0d, 0.42d);
        float pow2 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f5)) / 100.0d, 0.42d);
        float pow3 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f6)) / 100.0d, 0.42d);
        float signum = ((Math.signum(f4) * 400.0f) * pow) / (pow + 27.13f);
        float signum2 = ((Math.signum(f5) * 400.0f) * pow2) / (pow2 + 27.13f);
        float signum3 = ((Math.signum(f6) * 400.0f) * pow3) / (27.13f + pow3);
        float f7 = ((float) ((signum + signum2) - (signum3 * 2.0d))) / 9.0f;
        float f8 = (((signum * 20.0f) + (signum2 * 20.0f)) + (21.0f * signum3)) / 20.0f;
        float f9 = (((40.0f * signum) + (signum2 * 20.0f)) + signum3) / 20.0f;
        float atan2 = (((float) Math.atan2(f7, ((float) (((signum * 11.0d) + (signum2 * (-12.0d))) + signum3)) / 11.0f)) * 180.0f) / 3.1415927f;
        float f10 = atan2 < 0.0f ? atan2 + 360.0f : atan2 >= 360.0f ? atan2 - 360.0f : atan2;
        float f11 = (f10 * 3.1415927f) / 180.0f;
        float pow4 = ((float) Math.pow((viewingConditions.getNbb() * f9) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ())) * 100.0f;
        float c = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(pow4 / 100.0f)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float sqrt = ((float) Math.sqrt(pow4 / 100.0d)) * ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d)) * ((float) Math.pow((((float) Math.sqrt((r0 * r0) + (f7 * f7))) * (((3846.1538f * (((float) (Math.cos((((((double) f10) < 20.14d ? f10 + 360.0f : f10) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f)) * viewingConditions.getNc()) * viewingConditions.getNcb())) / (0.305f + f8), 0.9d));
        float flRoot = viewingConditions.getFlRoot() * sqrt;
        float log = ((float) Math.log((0.0228f * flRoot) + 1.0f)) * 43.85965f;
        return new CamColor(f10, sqrt, pow4, c, flRoot, ((float) Math.sqrt((viewingConditions.getC() * r1) / (viewingConditions.getAw() + 4.0f))) * 50.0f, (1.7f * pow4) / ((0.007f * pow4) + 1.0f), ((float) Math.cos(f11)) * log, ((float) Math.sin(f11)) * log);
    }

    private static CamColor fromJch(float j, float c, float h) {
        return fromJchInFrame(j, c, h, ViewingConditions.DEFAULT);
    }

    private static CamColor fromJchInFrame(float j, float c, float h, ViewingConditions viewingConditions) {
        float c2 = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(j / 100.0d)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float flRoot = c * viewingConditions.getFlRoot();
        float sqrt = ((float) Math.sqrt((viewingConditions.getC() * (c / ((float) Math.sqrt(j / 100.0d)))) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f = (3.1415927f * h) / 180.0f;
        float f2 = (1.7f * j) / ((0.007f * j) + 1.0f);
        float log = ((float) Math.log((flRoot * 0.0228d) + 1.0d)) * 43.85965f;
        return new CamColor(h, c, j, c2, flRoot, sqrt, f2, log * ((float) Math.cos(f)), log * ((float) Math.sin(f)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int toColor(float hue, float chroma, float lStar) {
        return toColor(hue, chroma, lStar, ViewingConditions.DEFAULT);
    }

    static int toColor(float hue, float chroma, float lstar, ViewingConditions viewingConditions) {
        if (chroma < 1.0d || Math.round(lstar) <= 0.0d || Math.round(lstar) >= 100.0d) {
            return CamUtils.intFromLStar(lstar);
        }
        float hue2 = hue >= 0.0f ? Math.min(360.0f, hue) : 0.0f;
        float f = chroma;
        float f2 = chroma;
        float f3 = 0.0f;
        boolean z = true;
        CamColor camColor = null;
        while (Math.abs(f3 - f) >= CHROMA_SEARCH_ENDPOINT) {
            CamColor findCamByJ = findCamByJ(hue2, f2, lstar);
            if (!z) {
                if (findCamByJ == null) {
                    f = f2;
                } else {
                    camColor = findCamByJ;
                    f3 = f2;
                }
                f2 = f3 + ((f - f3) / 2.0f);
            } else {
                if (findCamByJ != null) {
                    return findCamByJ.viewed(viewingConditions);
                }
                z = false;
                f2 = f3 + ((f - f3) / 2.0f);
            }
        }
        return camColor == null ? CamUtils.intFromLStar(lstar) : camColor.viewed(viewingConditions);
    }

    float distance(CamColor other) {
        float jStar = getJStar() - other.getJStar();
        float aStar = getAStar() - other.getAStar();
        float bStar = getBStar() - other.getBStar();
        return (float) (Math.pow(Math.sqrt((jStar * jStar) + (aStar * aStar) + (bStar * bStar)), 0.63d) * 1.41d);
    }

    float getAStar() {
        return this.mAstar;
    }

    float getBStar() {
        return this.mBstar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getChroma() {
        return this.mChroma;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getHue() {
        return this.mHue;
    }

    float getJ() {
        return this.mJ;
    }

    float getJStar() {
        return this.mJstar;
    }

    float getM() {
        return this.mM;
    }

    float getQ() {
        return this.mQ;
    }

    float getS() {
        return this.mS;
    }

    int viewed(ViewingConditions viewingConditions) {
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
        float[][] fArr = CamUtils.CAM16RGB_TO_XYZ;
        return ColorUtils.XYZToColor((fArr[0][0] * f4) + (fArr[0][1] * f5) + (fArr[0][2] * f6), (fArr[1][0] * f4) + (fArr[1][1] * f5) + (fArr[1][2] * f6), (fArr[2][0] * f4) + (fArr[2][1] * f5) + (fArr[2][2] * f6));
    }

    int viewedInSrgb() {
        return viewed(ViewingConditions.DEFAULT);
    }
}
