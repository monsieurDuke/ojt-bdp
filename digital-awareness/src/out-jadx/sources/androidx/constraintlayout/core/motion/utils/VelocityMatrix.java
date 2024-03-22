package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class VelocityMatrix {
    private static String TAG = "VelocityMatrix";
    float mDRotate;
    float mDScaleX;
    float mDScaleY;
    float mDTranslateX;
    float mDTranslateY;
    float mRotate;

    public void applyTransform(float locationX, float locationY, int width, int height, float[] mAnchorDpDt) {
        float f = mAnchorDpDt[0];
        float f2 = mAnchorDpDt[1];
        float f3 = f + this.mDTranslateX;
        float f4 = f2 + this.mDTranslateY;
        float f5 = f3 + (this.mDScaleX * (locationX - 0.5f) * 2.0f);
        float f6 = f4 + (this.mDScaleY * (locationY - 0.5f) * 2.0f);
        float radians = (float) Math.toRadians(this.mRotate);
        float radians2 = (float) Math.toRadians(this.mDRotate);
        float sin = f5 + (((float) ((((-width) * r8) * Math.sin(radians)) - ((height * r7) * Math.cos(radians)))) * radians2);
        float cos = f6 + (((float) (((width * r8) * Math.cos(radians)) - ((height * r7) * Math.sin(radians)))) * radians2);
        mAnchorDpDt[0] = sin;
        mAnchorDpDt[1] = cos;
    }

    public void clear() {
        this.mDRotate = 0.0f;
        this.mDTranslateY = 0.0f;
        this.mDTranslateX = 0.0f;
        this.mDScaleY = 0.0f;
        this.mDScaleX = 0.0f;
    }

    public void setRotationVelocity(KeyCycleOscillator osc_r, float position) {
        if (osc_r != null) {
            this.mDRotate = osc_r.getSlope(position);
        }
    }

    public void setRotationVelocity(SplineSet rot, float position) {
        if (rot != null) {
            this.mDRotate = rot.getSlope(position);
            this.mRotate = rot.get(position);
        }
    }

    public void setScaleVelocity(KeyCycleOscillator osc_sx, KeyCycleOscillator osc_sy, float position) {
        if (osc_sx != null) {
            this.mDScaleX = osc_sx.getSlope(position);
        }
        if (osc_sy != null) {
            this.mDScaleY = osc_sy.getSlope(position);
        }
    }

    public void setScaleVelocity(SplineSet scale_x, SplineSet scale_y, float position) {
        if (scale_x != null) {
            this.mDScaleX = scale_x.getSlope(position);
        }
        if (scale_y != null) {
            this.mDScaleY = scale_y.getSlope(position);
        }
    }

    public void setTranslationVelocity(KeyCycleOscillator osc_x, KeyCycleOscillator osc_y, float position) {
        if (osc_x != null) {
            this.mDTranslateX = osc_x.getSlope(position);
        }
        if (osc_y != null) {
            this.mDTranslateY = osc_y.getSlope(position);
        }
    }

    public void setTranslationVelocity(SplineSet trans_x, SplineSet trans_y, float position) {
        if (trans_x != null) {
            this.mDTranslateX = trans_x.getSlope(position);
        }
        if (trans_y != null) {
            this.mDTranslateY = trans_y.getSlope(position);
        }
    }
}
