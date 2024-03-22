package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class MotionPaths implements Comparable<MotionPaths> {
    static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    static final int PERPENDICULAR = 1;
    static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    LinkedHashMap<String, ConstraintAttribute> attributes;
    float height;
    int mAnimateCircleAngleTo;
    int mAnimateRelativeTo;
    int mDrawPath;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mPathRotate;
    float mProgress;
    float mRelativeAngle;
    MotionController mRelativeToController;
    double[] mTempDelta;
    double[] mTempValue;
    float position;
    float time;
    float width;
    float x;
    float y;

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    public MotionPaths(int parentWidth, int parentHeight, KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (startTimePoint.mAnimateRelativeTo != Key.UNSET) {
            initPolar(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
            return;
        }
        switch (c.mPositionType) {
            case 1:
                initPath(c, startTimePoint, endTimePoint);
                return;
            case 2:
                initScreen(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
                return;
            default:
                initCartesian(c, startTimePoint, endTimePoint);
                return;
        }
    }

    private boolean diff(float a, float b) {
        return (Float.isNaN(a) || Float.isNaN(b)) ? Float.isNaN(a) != Float.isNaN(b) : Math.abs(a - b) > 1.0E-6f;
    }

    private static final float xRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return (((x - cx) * cos) - ((y - cy) * sin)) + cx;
    }

    private static final float yRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return ((x - cx) * sin) + ((y - cy) * cos) + cy;
    }

    public void applyParameters(ConstraintSet.Constraint c) {
        this.mKeyFrameEasing = Easing.getInterpolator(c.motion.mTransitionEasing);
        this.mPathMotionArc = c.motion.mPathMotionArc;
        this.mAnimateRelativeTo = c.motion.mAnimateRelativeTo;
        this.mPathRotate = c.motion.mPathRotate;
        this.mDrawPath = c.motion.mDrawPath;
        this.mAnimateCircleAngleTo = c.motion.mAnimateCircleAngleTo;
        this.mProgress = c.propertySet.mProgress;
        this.mRelativeAngle = c.layout.circleAngle;
        for (String str : c.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = c.mCustomConstraints.get(str);
            if (constraintAttribute != null && constraintAttribute.isContinuous()) {
                this.attributes.put(str, constraintAttribute);
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionPaths o) {
        return Float.compare(this.position, o.position);
    }

    public void configureRelativeTo(MotionController toOrbit) {
        toOrbit.getPos(this.mProgress);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void different(MotionPaths points, boolean[] mask, String[] custom, boolean arcMode) {
        boolean diff = diff(this.x, points.x);
        boolean diff2 = diff(this.y, points.y);
        int i = 0 + 1;
        mask[0] = mask[0] | diff(this.position, points.position);
        int i2 = i + 1;
        mask[i] = mask[i] | diff | diff2 | arcMode;
        int i3 = i2 + 1;
        mask[i2] = mask[i2] | diff | diff2 | arcMode;
        int i4 = i3 + 1;
        mask[i3] = mask[i3] | diff(this.width, points.width);
        int i5 = i4 + 1;
        mask[i4] = mask[i4] | diff(this.height, points.height);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void fillStandard(double[] data, int[] toUse) {
        float[] fArr = {this.position, this.x, this.y, this.width, this.height, this.mPathRotate};
        int i = 0;
        for (int i2 = 0; i2 < toUse.length; i2++) {
            if (toUse[i2] < fArr.length) {
                data[i] = fArr[toUse[i2]];
                i++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getBounds(int[] toUse, double[] data, float[] point, int offset) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.width;
        float f4 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f5 = (float) data[i];
            switch (toUse[i]) {
                case 3:
                    f3 = f5;
                    break;
                case 4:
                    f4 = f5;
                    break;
            }
        }
        point[offset] = f3;
        point[offset + 1] = f4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        float f2 = this.x;
        float f3 = this.y;
        float f4 = this.width;
        float f5 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f6 = (float) data[i];
            switch (toUse[i]) {
                case 1:
                    f2 = f6;
                    break;
                case 2:
                    f3 = f6;
                    break;
                case 3:
                    f4 = f6;
                    break;
                case 4:
                    f5 = f6;
                    break;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            motionController.getCenter(p, fArr, new float[2]);
            float f7 = fArr[0];
            float f8 = fArr[1];
            float f9 = f2;
            float f10 = f3;
            float sin = (float) ((f7 + (f9 * Math.sin(f10))) - (f4 / 2.0f));
            f = 2.0f;
            f3 = (float) ((f8 - (f9 * Math.cos(f10))) - (f5 / 2.0f));
            f2 = sin;
        } else {
            f = 2.0f;
        }
        point[offset] = (f4 / f) + f2 + 0.0f;
        point[offset + 1] = (f5 / f) + f3 + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, double[] vdata, float[] velocity) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.width;
        float f4 = this.height;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (int i = 0; i < toUse.length; i++) {
            float f9 = (float) data[i];
            float f10 = (float) vdata[i];
            switch (toUse[i]) {
                case 1:
                    f = f9;
                    f5 = f10;
                    break;
                case 2:
                    f2 = f9;
                    f6 = f10;
                    break;
                case 3:
                    f3 = f9;
                    f7 = f10;
                    break;
                case 4:
                    f4 = f9;
                    f8 = f10;
                    break;
            }
        }
        float f11 = (f7 / 2.0f) + f5;
        float f12 = (f8 / 2.0f) + f6;
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(p, fArr, fArr2);
            float f13 = fArr[0];
            float f14 = fArr[1];
            float f15 = f;
            float f16 = f2;
            float f17 = f5;
            float f18 = f6;
            float f19 = fArr2[0];
            float f20 = fArr2[1];
            float sin = (float) ((f13 + (f15 * Math.sin(f16))) - (f3 / 2.0f));
            float cos = (float) ((f14 - (f15 * Math.cos(f16))) - (f4 / 2.0f));
            float sin2 = (float) (f19 + (f17 * Math.sin(f16)) + (Math.cos(f16) * f18));
            f12 = (float) ((f20 - (f17 * Math.cos(f16))) + (Math.sin(f16) * f18));
            f2 = cos;
            f11 = sin2;
            f = sin;
        }
        point[0] = (f3 / 2.0f) + f + 0.0f;
        point[1] = (f4 / 2.0f) + f2 + 0.0f;
        velocity[0] = f11;
        velocity[1] = f12;
    }

    void getCenterVelocity(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        float f2 = this.x;
        float f3 = this.y;
        float f4 = this.width;
        float f5 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f6 = (float) data[i];
            switch (toUse[i]) {
                case 1:
                    f2 = f6;
                    break;
                case 2:
                    f3 = f6;
                    break;
                case 3:
                    f4 = f6;
                    break;
                case 4:
                    f5 = f6;
                    break;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            motionController.getCenter(p, fArr, new float[2]);
            float f7 = fArr[0];
            float f8 = fArr[1];
            float f9 = f2;
            float f10 = f3;
            float sin = (float) ((f7 + (f9 * Math.sin(f10))) - (f4 / 2.0f));
            f = 2.0f;
            f3 = (float) ((f8 - (f9 * Math.cos(f10))) - (f5 / 2.0f));
            f2 = sin;
        } else {
            f = 2.0f;
        }
        point[offset] = (f4 / f) + f2 + 0.0f;
        point[offset + 1] = (f5 / f) + f3 + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getCustomData(String name, double[] value, int offset) {
        ConstraintAttribute constraintAttribute = this.attributes.get(name);
        if (constraintAttribute == null) {
            return 0;
        }
        if (constraintAttribute.numberOfInterpolatedValues() == 1) {
            value[offset] = constraintAttribute.getValueToInterpolate();
            return 1;
        }
        int numberOfInterpolatedValues = constraintAttribute.numberOfInterpolatedValues();
        constraintAttribute.getValuesToInterpolate(new float[numberOfInterpolatedValues]);
        int i = 0;
        while (i < numberOfInterpolatedValues) {
            value[offset] = r2[i];
            i++;
            offset++;
        }
        return numberOfInterpolatedValues;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getCustomDataCount(String name) {
        ConstraintAttribute constraintAttribute = this.attributes.get(name);
        if (constraintAttribute == null) {
            return 0;
        }
        return constraintAttribute.numberOfInterpolatedValues();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getRect(int[] toUse, double[] data, float[] path, int offset) {
        float f;
        float f2 = this.x;
        float f3 = this.y;
        float f4 = this.width;
        float f5 = this.height;
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        while (true) {
            boolean z3 = z;
            if (i >= toUse.length) {
                MotionController motionController = this.mRelativeToController;
                if (motionController != null) {
                    float centerX = motionController.getCenterX();
                    float centerY = this.mRelativeToController.getCenterY();
                    float f6 = f2;
                    f = 0.0f;
                    float sin = (float) ((centerX + (f6 * Math.sin(f3))) - (f4 / 2.0f));
                    f3 = (float) ((centerY - (f6 * Math.cos(f3))) - (f5 / 2.0f));
                    f2 = sin;
                } else {
                    f = 0.0f;
                }
                float f7 = f2;
                float f8 = f3;
                float f9 = f2 + f4;
                float f10 = f8;
                float f11 = f9;
                float f12 = f3 + f5;
                float f13 = f7;
                float f14 = f12;
                float f15 = f7 + (f4 / 2.0f);
                float f16 = f8 + (f5 / 2.0f);
                if (!Float.isNaN(Float.NaN)) {
                    f15 = f7 + ((f9 - f7) * Float.NaN);
                }
                if (!Float.isNaN(Float.NaN)) {
                    f16 = f8 + ((f12 - f8) * Float.NaN);
                }
                if (1.0f != 1.0f) {
                    float f17 = (f7 + f9) / 2.0f;
                    f7 = ((f7 - f17) * 1.0f) + f17;
                    f9 = ((f9 - f17) * 1.0f) + f17;
                    f11 = ((f11 - f17) * 1.0f) + f17;
                    f13 = ((f13 - f17) * 1.0f) + f17;
                }
                if (1.0f != 1.0f) {
                    float f18 = (f8 + f12) / 2.0f;
                    f8 = ((f8 - f18) * 1.0f) + f18;
                    f10 = ((f10 - f18) * 1.0f) + f18;
                    f12 = ((f12 - f18) * 1.0f) + f18;
                    f14 = ((f14 - f18) * 1.0f) + f18;
                }
                if (f != 0.0f) {
                    float f19 = f;
                    float sin2 = (float) Math.sin(Math.toRadians(f19));
                    float cos = (float) Math.cos(Math.toRadians(f19));
                    float f20 = f15;
                    float f21 = f16;
                    float f22 = f7;
                    float f23 = f8;
                    float xRotate = xRotate(sin2, cos, f20, f21, f22, f23);
                    float yRotate = yRotate(sin2, cos, f20, f21, f22, f23);
                    float f24 = f9;
                    float f25 = f10;
                    float xRotate2 = xRotate(sin2, cos, f20, f21, f24, f25);
                    float yRotate2 = yRotate(sin2, cos, f20, f21, f24, f25);
                    float f26 = f11;
                    float f27 = f12;
                    float xRotate3 = xRotate(sin2, cos, f20, f21, f26, f27);
                    float yRotate3 = yRotate(sin2, cos, f20, f21, f26, f27);
                    float f28 = f13;
                    float f29 = f14;
                    f7 = xRotate;
                    f8 = yRotate;
                    f9 = xRotate2;
                    f10 = yRotate2;
                    f11 = xRotate3;
                    f12 = yRotate3;
                    f13 = xRotate(sin2, cos, f20, f21, f28, f29);
                    f14 = yRotate(sin2, cos, f20, f21, f28, f29);
                }
                int i2 = offset + 1;
                path[offset] = f7 + 0.0f;
                int i3 = i2 + 1;
                path[i2] = f8 + 0.0f;
                int i4 = i3 + 1;
                path[i3] = f9 + 0.0f;
                int i5 = i4 + 1;
                path[i4] = f10 + 0.0f;
                int i6 = i5 + 1;
                path[i5] = f11 + 0.0f;
                int i7 = i6 + 1;
                path[i6] = f12 + 0.0f;
                int i8 = i7 + 1;
                path[i7] = f13 + 0.0f;
                int i9 = i8 + 1;
                path[i8] = f14 + 0.0f;
                return;
            }
            boolean z4 = z2;
            float f30 = (float) data[i];
            switch (toUse[i]) {
                case 1:
                    f2 = f30;
                    break;
                case 2:
                    f3 = f30;
                    break;
                case 3:
                    f4 = f30;
                    break;
                case 4:
                    f5 = f30;
                    break;
            }
            i++;
            z = z3;
            z2 = z4;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasCustomData(String name) {
        return this.attributes.containsKey(name);
    }

    void initCartesian(KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f = c.mFramePosition / 100.0f;
        this.time = f;
        this.mDrawPath = c.mDrawPath;
        float f2 = Float.isNaN(c.mPercentWidth) ? f : c.mPercentWidth;
        float f3 = Float.isNaN(c.mPercentHeight) ? f : c.mPercentHeight;
        float f4 = endTimePoint.width;
        float f5 = startTimePoint.width;
        float f6 = f4 - f5;
        float f7 = endTimePoint.height;
        float f8 = startTimePoint.height;
        float f9 = f7 - f8;
        this.position = this.time;
        float f10 = startTimePoint.x;
        float f11 = startTimePoint.y;
        float f12 = endTimePoint.x + (f4 / 2.0f);
        float f13 = endTimePoint.y + (f7 / 2.0f);
        float f14 = f12 - (f10 + (f5 / 2.0f));
        float f15 = f13 - (f11 + (f8 / 2.0f));
        this.x = (int) ((f10 + (f14 * f)) - ((f6 * f2) / 2.0f));
        this.y = (int) ((f11 + (f15 * f)) - ((f9 * f3) / 2.0f));
        this.width = (int) (f5 + (f6 * f2));
        this.height = (int) (f8 + (f9 * f3));
        float f16 = Float.isNaN(c.mPercentX) ? f : c.mPercentX;
        float f17 = Float.isNaN(c.mAltPercentY) ? 0.0f : c.mAltPercentY;
        float f18 = Float.isNaN(c.mPercentY) ? f : c.mPercentY;
        float f19 = Float.isNaN(c.mAltPercentX) ? 0.0f : c.mAltPercentX;
        this.mMode = 0;
        this.x = (int) (((startTimePoint.x + (f14 * f16)) + (f15 * f19)) - ((f6 * f2) / 2.0f));
        this.y = (int) (((startTimePoint.y + (f14 * f17)) + (f15 * f18)) - ((f9 * f3) / 2.0f));
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    void initPath(KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f = c.mFramePosition / 100.0f;
        this.time = f;
        this.mDrawPath = c.mDrawPath;
        float f2 = Float.isNaN(c.mPercentWidth) ? f : c.mPercentWidth;
        float f3 = Float.isNaN(c.mPercentHeight) ? f : c.mPercentHeight;
        float f4 = endTimePoint.width - startTimePoint.width;
        float f5 = endTimePoint.height - startTimePoint.height;
        this.position = this.time;
        float f6 = Float.isNaN(c.mPercentX) ? f : c.mPercentX;
        float f7 = startTimePoint.x;
        float f8 = startTimePoint.width;
        float f9 = startTimePoint.y;
        float f10 = startTimePoint.height;
        float f11 = (endTimePoint.x + (endTimePoint.width / 2.0f)) - ((f8 / 2.0f) + f7);
        float f12 = (endTimePoint.y + (endTimePoint.height / 2.0f)) - (f9 + (f10 / 2.0f));
        this.x = (int) ((f7 + (f11 * f6)) - ((f4 * f2) / 2.0f));
        this.y = (int) ((f9 + (f12 * f6)) - ((f5 * f3) / 2.0f));
        this.width = (int) (f8 + (f4 * f2));
        this.height = (int) (f10 + (f5 * f3));
        float f13 = Float.isNaN(c.mPercentY) ? 0.0f : c.mPercentY;
        float f14 = f11 * f13;
        this.mMode = 1;
        float f15 = (int) ((startTimePoint.x + (f11 * f6)) - ((f4 * f2) / 2.0f));
        this.x = f15;
        float f16 = (int) ((startTimePoint.y + (f12 * f6)) - ((f5 * f3) / 2.0f));
        this.y = f16;
        this.x = f15 + ((-f12) * f13);
        this.y = f16 + f14;
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    void initPolar(int parentWidth, int parentHeight, KeyPosition c, MotionPaths s, MotionPaths e) {
        float min;
        float f;
        float f2 = c.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = c.mDrawPath;
        this.mMode = c.mPositionType;
        float f3 = Float.isNaN(c.mPercentWidth) ? f2 : c.mPercentWidth;
        float f4 = Float.isNaN(c.mPercentHeight) ? f2 : c.mPercentHeight;
        float f5 = e.width;
        float f6 = s.width;
        float f7 = e.height;
        float f8 = s.height;
        this.position = this.time;
        this.width = (int) (f6 + ((f5 - f6) * f3));
        this.height = (int) (f8 + ((f7 - f8) * f4));
        float f9 = 1.0f - f2;
        switch (c.mPositionType) {
            case 1:
                float f10 = Float.isNaN(c.mPercentX) ? f2 : c.mPercentX;
                float f11 = e.x;
                float f12 = s.x;
                this.x = (f10 * (f11 - f12)) + f12;
                float f13 = Float.isNaN(c.mPercentY) ? f2 : c.mPercentY;
                float f14 = e.y;
                float f15 = s.y;
                this.y = (f13 * (f14 - f15)) + f15;
                break;
            case 2:
                if (Float.isNaN(c.mPercentX)) {
                    float f16 = e.x;
                    float f17 = s.x;
                    min = ((f16 - f17) * f2) + f17;
                } else {
                    min = c.mPercentX * Math.min(f4, f3);
                }
                this.x = min;
                if (Float.isNaN(c.mPercentY)) {
                    float f18 = e.y;
                    float f19 = s.y;
                    f = ((f18 - f19) * f2) + f19;
                } else {
                    f = c.mPercentY;
                }
                this.y = f;
                break;
            default:
                float f20 = Float.isNaN(c.mPercentX) ? f2 : c.mPercentX;
                float f21 = e.x;
                float f22 = s.x;
                this.x = (f20 * (f21 - f22)) + f22;
                float f23 = Float.isNaN(c.mPercentY) ? f2 : c.mPercentY;
                float f24 = e.y;
                float f25 = s.y;
                this.y = (f23 * (f24 - f25)) + f25;
                break;
        }
        this.mAnimateRelativeTo = s.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    void initScreen(int parentWidth, int parentHeight, KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f = c.mFramePosition / 100.0f;
        this.time = f;
        this.mDrawPath = c.mDrawPath;
        float f2 = Float.isNaN(c.mPercentWidth) ? f : c.mPercentWidth;
        float f3 = Float.isNaN(c.mPercentHeight) ? f : c.mPercentHeight;
        float f4 = endTimePoint.width;
        float f5 = f4 - startTimePoint.width;
        float f6 = endTimePoint.height;
        float f7 = f6 - startTimePoint.height;
        this.position = this.time;
        float f8 = startTimePoint.x;
        float f9 = startTimePoint.y;
        float f10 = endTimePoint.x + (f4 / 2.0f);
        float f11 = endTimePoint.y + (f6 / 2.0f);
        this.x = (int) ((f8 + ((f10 - (f8 + (r9 / 2.0f))) * f)) - ((f5 * f2) / 2.0f));
        this.y = (int) ((f9 + ((f11 - (f9 + (r12 / 2.0f))) * f)) - ((f7 * f3) / 2.0f));
        this.width = (int) (r9 + (f5 * f2));
        this.height = (int) (r12 + (f7 * f3));
        this.mMode = 2;
        if (!Float.isNaN(c.mPercentX)) {
            this.x = (int) (c.mPercentX * ((int) (parentWidth - this.width)));
        }
        if (!Float.isNaN(c.mPercentY)) {
            this.y = (int) (c.mPercentY * ((int) (parentHeight - this.height)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBounds(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDpDt(float locationX, float locationY, float[] mAnchorDpDt, int[] toUse, double[] deltaData, double[] data) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (int i = 0; i < toUse.length; i++) {
            float f5 = (float) deltaData[i];
            switch (toUse[i]) {
                case 1:
                    f = f5;
                    break;
                case 2:
                    f2 = f5;
                    break;
                case 3:
                    f3 = f5;
                    break;
                case 4:
                    f4 = f5;
                    break;
            }
        }
        float f6 = f - ((0.0f * f3) / 2.0f);
        float f7 = f2 - ((0.0f * f4) / 2.0f);
        mAnchorDpDt[0] = ((1.0f - locationX) * f6) + ((f6 + ((0.0f + 1.0f) * f3)) * locationX) + 0.0f;
        mAnchorDpDt[1] = ((1.0f - locationY) * f7) + ((f7 + ((0.0f + 1.0f) * f4)) * locationY) + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setView(float position, View view, int[] toUse, double[] data, double[] slope, double[] cycle, boolean mForceMeasure) {
        float f;
        float f2;
        boolean z;
        boolean z2;
        float f3;
        float f4;
        float f5;
        float f6;
        View view2 = view;
        float f7 = this.x;
        float f8 = this.y;
        float f9 = this.width;
        float f10 = this.height;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        float f14 = 0.0f;
        float f15 = 0.0f;
        float f16 = Float.NaN;
        if (toUse.length != 0) {
            f = f7;
            if (this.mTempValue.length <= toUse[toUse.length - 1]) {
                int i = toUse[toUse.length - 1] + 1;
                this.mTempValue = new double[i];
                this.mTempDelta = new double[i];
            }
        } else {
            f = f7;
        }
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i2 = 0; i2 < toUse.length; i2++) {
            this.mTempValue[toUse[i2]] = data[i2];
            this.mTempDelta[toUse[i2]] = slope[i2];
        }
        int i3 = 0;
        float f17 = f8;
        float f18 = f9;
        while (true) {
            double[] dArr = this.mTempValue;
            if (i3 >= dArr.length) {
                float f19 = f14;
                MotionController motionController = this.mRelativeToController;
                if (motionController != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    motionController.getCenter(position, fArr, fArr2);
                    float f20 = fArr[0];
                    float f21 = fArr[1];
                    float f22 = f;
                    float f23 = f17;
                    float f24 = f11;
                    float f25 = f12;
                    float f26 = fArr2[0];
                    float f27 = fArr2[1];
                    float f28 = f16;
                    float sin = (float) ((f20 + (f22 * Math.sin(f23))) - (f18 / 2.0f));
                    float cos = (float) ((f21 - (f22 * Math.cos(f23))) - (f10 / 2.0f));
                    f3 = f10;
                    f2 = f18;
                    float sin2 = (float) (f26 + (f24 * Math.sin(f23)) + (f22 * Math.cos(f23) * f25));
                    float cos2 = (float) ((f27 - (f24 * Math.cos(f23))) + (f22 * Math.sin(f23) * f25));
                    f = sin;
                    if (slope.length >= 2) {
                        z = false;
                        slope[0] = sin2;
                        z2 = true;
                        slope[1] = cos2;
                    } else {
                        z2 = true;
                        z = false;
                    }
                    if (Float.isNaN(f28)) {
                        view2 = view;
                    } else {
                        view2 = view;
                        view2.setRotation((float) (f28 + Math.toDegrees(Math.atan2(cos2, sin2))));
                    }
                    f4 = cos;
                } else {
                    float f29 = f17;
                    f2 = f18;
                    float f30 = f11;
                    float f31 = f12;
                    z = false;
                    z2 = true;
                    f3 = f10;
                    float f32 = f13;
                    if (!Float.isNaN(f16)) {
                        view2.setRotation((float) (0.0f + f16 + Math.toDegrees(Math.atan2(f31 + (f19 / 2.0f), f30 + (f32 / 2.0f)))));
                    }
                    f4 = f29;
                }
                if (view2 instanceof FloatLayout) {
                    ((FloatLayout) view2).layout(f, f4, f + f2, f4 + f3);
                    return;
                }
                int i4 = (int) (f + 0.5f);
                int i5 = (int) (f4 + 0.5f);
                int i6 = (int) (f + 0.5f + f2);
                int i7 = (int) (0.5f + f4 + f3);
                int i8 = i6 - i4;
                int i9 = i7 - i5;
                if (i8 == view.getMeasuredWidth() && i9 == view.getMeasuredHeight()) {
                    z2 = z;
                }
                if (z2 || mForceMeasure) {
                    view2.measure(View.MeasureSpec.makeMeasureSpec(i8, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i9, BasicMeasure.EXACTLY));
                }
                view2.layout(i4, i5, i6, i7);
                return;
            }
            if (Double.isNaN(dArr[i3])) {
                if (cycle == null) {
                    f5 = f14;
                    f6 = f15;
                } else if (cycle[i3] == 0.0d) {
                    f5 = f14;
                    f6 = f15;
                }
                f14 = f5;
                f15 = f6;
                i3++;
            }
            double d = cycle != null ? cycle[i3] : 0.0d;
            if (!Double.isNaN(this.mTempValue[i3])) {
                d = this.mTempValue[i3] + d;
            }
            float f33 = (float) d;
            f5 = f14;
            f6 = f15;
            f14 = (float) this.mTempDelta[i3];
            switch (i3) {
                case 0:
                    f15 = f33;
                    f14 = f5;
                    break;
                case 1:
                    f11 = f14;
                    f = f33;
                    f14 = f5;
                    f15 = f6;
                    break;
                case 2:
                    f17 = f33;
                    f12 = f14;
                    f14 = f5;
                    f15 = f6;
                    break;
                case 3:
                    f18 = f33;
                    f13 = f14;
                    f14 = f5;
                    f15 = f6;
                    break;
                case 4:
                    f10 = f33;
                    f15 = f6;
                    break;
                case 5:
                    f16 = f33;
                    f14 = f5;
                    f15 = f6;
                    break;
                default:
                    f14 = f5;
                    f15 = f6;
                    break;
            }
            i3++;
        }
    }

    public void setupRelative(MotionController mc, MotionPaths relative) {
        double d = ((this.x + (this.width / 2.0f)) - relative.x) - (relative.width / 2.0f);
        double d2 = ((this.y + (this.height / 2.0f)) - relative.y) - (relative.height / 2.0f);
        this.mRelativeToController = mc;
        this.x = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.y = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }
}
