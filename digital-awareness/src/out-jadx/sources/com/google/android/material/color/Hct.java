package com.google.android.material.color;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class Hct {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DE_MAX_ERROR = 1.0E-9f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private float chroma;
    private float hue;
    private float tone;

    private Hct(float hue, float chroma, float tone) {
        setInternalState(gamutMap(hue, chroma, tone));
    }

    private static Cam16 findCamByJ(float hue, float chroma, float tone) {
        float f = 0.0f;
        float f2 = 100.0f;
        float f3 = 1000.0f;
        float f4 = 1000.0f;
        Cam16 cam16 = null;
        while (Math.abs(f - f2) > LIGHTNESS_SEARCH_ENDPOINT) {
            float f5 = f + ((f2 - f) / 2.0f);
            int i = Cam16.fromJch(f5, chroma, hue).getInt();
            float lstarFromInt = ColorUtils.lstarFromInt(i);
            float abs = Math.abs(tone - lstarFromInt);
            if (abs < 0.2f) {
                Cam16 fromInt = Cam16.fromInt(i);
                float distance = fromInt.distance(Cam16.fromJch(fromInt.getJ(), fromInt.getChroma(), hue));
                if (distance <= 1.0f && distance <= f4) {
                    f3 = abs;
                    f4 = distance;
                    cam16 = fromInt;
                }
            }
            if (f3 == 0.0f && f4 < DE_MAX_ERROR) {
                break;
            }
            if (lstarFromInt < tone) {
                f = f5;
            } else {
                f2 = f5;
            }
        }
        return cam16;
    }

    public static Hct from(float hue, float chroma, float tone) {
        return new Hct(hue, chroma, tone);
    }

    public static Hct fromInt(int argb) {
        Cam16 fromInt = Cam16.fromInt(argb);
        return new Hct(fromInt.getHue(), fromInt.getChroma(), ColorUtils.lstarFromInt(argb));
    }

    private static int gamutMap(float hue, float chroma, float tone) {
        return gamutMapInViewingConditions(hue, chroma, tone, ViewingConditions.DEFAULT);
    }

    static int gamutMapInViewingConditions(float hue, float chroma, float tone, ViewingConditions viewingConditions) {
        if (chroma < 1.0d || Math.round(tone) <= 0.0d || Math.round(tone) >= 100.0d) {
            return ColorUtils.intFromLstar(tone);
        }
        float hue2 = MathUtils.sanitizeDegrees(hue);
        float f = chroma;
        float f2 = chroma;
        float f3 = 0.0f;
        boolean z = true;
        Cam16 cam16 = null;
        while (Math.abs(f3 - f) >= CHROMA_SEARCH_ENDPOINT) {
            Cam16 findCamByJ = findCamByJ(hue2, f2, tone);
            if (!z) {
                if (findCamByJ == null) {
                    f = f2;
                } else {
                    cam16 = findCamByJ;
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
        return cam16 == null ? ColorUtils.intFromLstar(tone) : cam16.viewed(viewingConditions);
    }

    private void setInternalState(int argb) {
        Cam16 fromInt = Cam16.fromInt(argb);
        float lstarFromInt = ColorUtils.lstarFromInt(argb);
        this.hue = fromInt.getHue();
        this.chroma = fromInt.getChroma();
        this.tone = lstarFromInt;
    }

    public float getChroma() {
        return this.chroma;
    }

    public float getHue() {
        return this.hue;
    }

    public float getTone() {
        return this.tone;
    }

    public void setChroma(float newChroma) {
        setInternalState(gamutMap(this.hue, newChroma, this.tone));
    }

    public void setHue(float newHue) {
        setInternalState(gamutMap(MathUtils.sanitizeDegrees(newHue), this.chroma, this.tone));
    }

    public void setTone(float newTone) {
        setInternalState(gamutMap(this.hue, this.chroma, newTone));
    }

    public int toInt() {
        return gamutMap(this.hue, this.chroma, this.tone);
    }
}
