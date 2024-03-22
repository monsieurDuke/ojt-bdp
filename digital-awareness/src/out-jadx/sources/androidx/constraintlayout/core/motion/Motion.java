package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKey;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyTrigger;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.DifferentialInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.core.motion.utils.ViewState;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Motion implements TypedValues {
    static final int BOUNCE = 4;
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int INTERPOLATOR_UNDEFINED = -3;
    static final int LINEAR = 3;
    static final int OVERSHOOT = 5;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    public static final int ROTATION_LEFT = 2;
    public static final int ROTATION_RIGHT = 1;
    private static final int SPLINE_STRING = -1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    String[] attributeTable;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, SplineSet> mAttributesMap;
    String mConstraintTag;
    float mCurrentCenterX;
    float mCurrentCenterY;
    private HashMap<String, KeyCycleOscillator> mCycleMap;
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private MotionKeyTrigger[] mKeyTriggers;
    private CurveFit[] mSpline;
    private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
    MotionWidget mView;
    Rect mTempRect = new Rect();
    private int mCurveFitType = -1;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    float mMotionStagger = Float.NaN;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private int MAX_DIMENSION = 4;
    private float[] mValuesBuff = new float[4];
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    private float[] mVelocity = new float[1];
    private ArrayList<MotionKey> mKeyList = new ArrayList<>();
    private int mPathMotionArc = -1;
    private int mTransformPivotTarget = -1;
    private MotionWidget mTransformPivotView = null;
    private int mQuantizeMotionSteps = -1;
    private float mQuantizeMotionPhase = Float.NaN;
    private DifferentialInterpolator mQuantizeMotionInterpolator = null;
    private boolean mNoMovement = false;

    public Motion(MotionWidget view) {
        setView(view);
    }

    private float getAdjustedPosition(float position, float[] velocity) {
        if (velocity != null) {
            velocity[0] = 1.0f;
        } else {
            float f = this.mStaggerScale;
            if (f != 1.0d) {
                float f2 = this.mStaggerOffset;
                if (position < f2) {
                    position = 0.0f;
                }
                if (position > f2 && position < 1.0d) {
                    position = Math.min((position - f2) * f, 1.0f);
                }
            }
        }
        float f3 = position;
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        float f4 = 0.0f;
        float f5 = Float.NaN;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            MotionPaths next = it.next();
            if (next.mKeyFrameEasing != null) {
                if (next.time < position) {
                    easing = next.mKeyFrameEasing;
                    f4 = next.time;
                } else if (Float.isNaN(f5)) {
                    f5 = next.time;
                }
            }
        }
        if (easing != null) {
            if (Float.isNaN(f5)) {
                f5 = 1.0f;
            }
            float f6 = (position - f4) / (f5 - f4);
            f3 = ((f5 - f4) * ((float) easing.get(f6))) + f4;
            if (velocity != null) {
                velocity[0] = (float) easing.getDiff(f6);
            }
        }
        return f3;
    }

    private static DifferentialInterpolator getInterpolator(int type, String interpolatorString, int id) {
        switch (type) {
            case -1:
                final Easing interpolator = Easing.getInterpolator(interpolatorString);
                return new DifferentialInterpolator() { // from class: androidx.constraintlayout.core.motion.Motion.1
                    float mX;

                    @Override // androidx.constraintlayout.core.motion.utils.DifferentialInterpolator
                    public float getInterpolation(float x) {
                        this.mX = x;
                        return (float) Easing.this.get(x);
                    }

                    @Override // androidx.constraintlayout.core.motion.utils.DifferentialInterpolator
                    public float getVelocity() {
                        return (float) Easing.this.getDiff(this.mX);
                    }
                };
            default:
                return null;
        }
    }

    private float getPreCycleDistance() {
        float f;
        double d;
        int i = 100;
        float[] fArr = new float[2];
        float f2 = 1.0f / ((float) (100 - 1));
        float f3 = 0.0f;
        double d2 = 0.0d;
        double d3 = 0.0d;
        int i2 = 0;
        while (i2 < i) {
            float f4 = i2 * f2;
            double d4 = f4;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            int i3 = i;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f5 = 0.0f;
            Easing easing2 = easing;
            float f6 = Float.NaN;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Iterator<MotionPaths> it2 = it;
                if (next.mKeyFrameEasing != null) {
                    if (next.time < f4) {
                        Easing easing3 = next.mKeyFrameEasing;
                        f5 = next.time;
                        easing2 = easing3;
                    } else if (Float.isNaN(f6)) {
                        f6 = next.time;
                    }
                }
                it = it2;
            }
            if (easing2 != null) {
                if (Float.isNaN(f6)) {
                    f6 = 1.0f;
                }
                f = f6;
                d = ((f6 - f5) * ((float) easing2.get((f4 - f5) / (f6 - f5)))) + f5;
            } else {
                f = f6;
                d = d4;
            }
            this.mSpline[0].getPos(d, this.mInterpolateData);
            int i4 = i2;
            this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
            if (i4 > 0) {
                f3 = (float) (f3 + Math.hypot(d3 - fArr[1], d2 - fArr[0]));
            }
            d2 = fArr[0];
            d3 = fArr[1];
            i2 = i4 + 1;
            i = i3;
        }
        return f3;
    }

    private void insertKey(MotionPaths point) {
        MotionPaths motionPaths = null;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            MotionPaths next = it.next();
            if (point.position == next.position) {
                motionPaths = next;
            }
        }
        if (motionPaths != null) {
            this.mMotionPaths.remove(motionPaths);
        }
        if (Collections.binarySearch(this.mMotionPaths, point) == 0) {
            Utils.loge(TAG, " KeyPath position \"" + point.position + "\" outside of range");
        }
        this.mMotionPaths.add((-r1) - 1, point);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds(this.mView.getX(), this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    public void addKey(MotionKey key) {
        this.mKeyList.add(key);
    }

    void addKeys(ArrayList<MotionKey> arrayList) {
        this.mKeyList.addAll(arrayList);
    }

    void buildBounds(float[] bounds, int pointCount) {
        float f;
        Motion motion = this;
        int i = pointCount;
        float f2 = 1.0f;
        float f3 = 1.0f / ((float) (i - 1));
        HashMap<String, SplineSet> hashMap = motion.mAttributesMap;
        SplineSet splineSet = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = motion.mAttributesMap;
        if (hashMap2 != null) {
            hashMap2.get("translationY");
        }
        HashMap<String, KeyCycleOscillator> hashMap3 = motion.mCycleMap;
        if (hashMap3 != null) {
            hashMap3.get("translationX");
        }
        HashMap<String, KeyCycleOscillator> hashMap4 = motion.mCycleMap;
        if (hashMap4 != null) {
            hashMap4.get("translationY");
        }
        int i2 = 0;
        while (i2 < i) {
            float f4 = i2 * f3;
            float f5 = motion.mStaggerScale;
            if (f5 != f2) {
                float f6 = motion.mStaggerOffset;
                if (f4 < f6) {
                    f4 = 0.0f;
                }
                if (f4 > f6 && f4 < 1.0d) {
                    f4 = Math.min((f4 - f6) * f5, f2);
                }
            }
            double d = f4;
            Easing easing = motion.mStartMotionPath.mKeyFrameEasing;
            float f7 = 0.0f;
            float f8 = Float.NaN;
            Iterator<MotionPaths> it = motion.mMotionPaths.iterator();
            while (it.hasNext()) {
                MotionPaths next = it.next();
                if (next.mKeyFrameEasing != null) {
                    if (next.time < f4) {
                        Easing easing2 = next.mKeyFrameEasing;
                        f7 = next.time;
                        easing = easing2;
                    } else if (Float.isNaN(f8)) {
                        f8 = next.time;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f8)) {
                    f8 = 1.0f;
                }
                f = f3;
                d = ((f8 - f7) * ((float) easing.get((f4 - f7) / (f8 - f7)))) + f7;
            } else {
                f = f3;
            }
            motion.mSpline[0].getPos(d, motion.mInterpolateData);
            CurveFit curveFit = motion.mArcSpline;
            if (curveFit != null) {
                double[] dArr = motion.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            motion.mStartMotionPath.getBounds(motion.mInterpolateVariables, motion.mInterpolateData, bounds, i2 * 2);
            i2++;
            motion = this;
            i = pointCount;
            f3 = f;
            splineSet = splineSet;
            f2 = 1.0f;
        }
    }

    int buildKeyBounds(float[] keyBounds, int[] mode) {
        if (keyBounds == null) {
            return 0;
        }
        int i = 0;
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            while (it.hasNext()) {
                mode[i] = it.next().mMode;
                i++;
            }
            i = 0;
        }
        for (double d : timePoints) {
            this.mSpline[0].getPos(d, this.mInterpolateData);
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, keyBounds, i);
            i += 2;
        }
        return i / 2;
    }

    public int buildKeyFrames(float[] keyFrames, int[] mode, int[] pos) {
        if (keyFrames == null) {
            return 0;
        }
        int i = 0;
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            while (it.hasNext()) {
                mode[i] = it.next().mMode;
                i++;
            }
            i = 0;
        }
        if (pos != null) {
            Iterator<MotionPaths> it2 = this.mMotionPaths.iterator();
            while (it2.hasNext()) {
                pos[i] = (int) (it2.next().position * 100.0f);
                i++;
            }
            i = 0;
        }
        for (int i2 = 0; i2 < timePoints.length; i2++) {
            this.mSpline[0].getPos(timePoints[i2], this.mInterpolateData);
            this.mStartMotionPath.getCenter(timePoints[i2], this.mInterpolateVariables, this.mInterpolateData, keyFrames, i);
            i += 2;
        }
        return i / 2;
    }

    public void buildPath(float[] points, int pointCount) {
        float f;
        double d;
        Motion motion = this;
        float f2 = 1.0f;
        float f3 = 1.0f / ((float) (pointCount - 1));
        HashMap<String, SplineSet> hashMap = motion.mAttributesMap;
        SplineSet splineSet = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = motion.mAttributesMap;
        SplineSet splineSet2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, KeyCycleOscillator> hashMap3 = motion.mCycleMap;
        KeyCycleOscillator keyCycleOscillator = hashMap3 == null ? null : hashMap3.get("translationX");
        HashMap<String, KeyCycleOscillator> hashMap4 = motion.mCycleMap;
        KeyCycleOscillator keyCycleOscillator2 = hashMap4 != null ? hashMap4.get("translationY") : null;
        int i = 0;
        while (i < pointCount) {
            float f4 = i * f3;
            float f5 = motion.mStaggerScale;
            if (f5 != f2) {
                float f6 = motion.mStaggerOffset;
                if (f4 < f6) {
                    f4 = 0.0f;
                }
                f = (f4 <= f6 || ((double) f4) >= 1.0d) ? f4 : Math.min((f4 - f6) * f5, f2);
            } else {
                f = f4;
            }
            double d2 = f;
            Easing easing = motion.mStartMotionPath.mKeyFrameEasing;
            Iterator<MotionPaths> it = motion.mMotionPaths.iterator();
            float f7 = 0.0f;
            Easing easing2 = easing;
            float f8 = Float.NaN;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                if (next.mKeyFrameEasing != null) {
                    if (next.time < f) {
                        easing2 = next.mKeyFrameEasing;
                        f7 = next.time;
                    } else if (Float.isNaN(f8)) {
                        f8 = next.time;
                    }
                }
            }
            if (easing2 != null) {
                if (Float.isNaN(f8)) {
                    f8 = 1.0f;
                }
                d = ((f8 - f7) * ((float) easing2.get((f - f7) / (f8 - f7)))) + f7;
            } else {
                d = d2;
            }
            motion.mSpline[0].getPos(d, motion.mInterpolateData);
            CurveFit curveFit = motion.mArcSpline;
            if (curveFit != null) {
                double[] dArr = motion.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            float f9 = f;
            motion.mStartMotionPath.getCenter(d, motion.mInterpolateVariables, motion.mInterpolateData, points, i * 2);
            if (keyCycleOscillator != null) {
                int i2 = i * 2;
                points[i2] = points[i2] + keyCycleOscillator.get(f9);
            } else if (splineSet != null) {
                int i3 = i * 2;
                points[i3] = points[i3] + splineSet.get(f9);
            }
            if (keyCycleOscillator2 != null) {
                int i4 = (i * 2) + 1;
                points[i4] = points[i4] + keyCycleOscillator2.get(f9);
            } else if (splineSet2 != null) {
                int i5 = (i * 2) + 1;
                points[i5] = points[i5] + splineSet2.get(f9);
            }
            i++;
            f2 = 1.0f;
            motion = this;
        }
    }

    public void buildRect(float p, float[] path, int offset) {
        this.mSpline[0].getPos(getAdjustedPosition(p, null), this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, offset);
    }

    void buildRectangles(float[] path, int pointCount) {
        float f = 1.0f / ((float) (pointCount - 1));
        for (int i = 0; i < pointCount; i++) {
            this.mSpline[0].getPos(getAdjustedPosition(i * f, null), this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, i * 8);
        }
    }

    void endTrigger(boolean start) {
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    int getAttributeValues(String attributeType, float[] points, int pointCount) {
        float f = 1.0f / ((float) (pointCount - 1));
        SplineSet splineSet = this.mAttributesMap.get(attributeType);
        if (splineSet == null) {
            return -1;
        }
        for (int i = 0; i < points.length; i++) {
            points[i] = splineSet.get(i / (points.length - 1));
        }
        return points.length;
    }

    public void getCenter(double p, float[] pos, float[] vel) {
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        int[] iArr = new int[4];
        this.mSpline[0].getPos(p, dArr);
        this.mSpline[0].getSlope(p, dArr2);
        Arrays.fill(vel, 0.0f);
        this.mStartMotionPath.getCenter(p, this.mInterpolateVariables, dArr, pos, dArr2, vel);
    }

    public float getCenterX() {
        return this.mCurrentCenterX;
    }

    public float getCenterY() {
        return this.mCurrentCenterY;
    }

    void getDpDt(float position, float locationX, float locationY, float[] mAnchorDpDt) {
        double[] dArr;
        float adjustedPosition = getAdjustedPosition(position, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr == null) {
            float f = this.mEndMotionPath.x - this.mStartMotionPath.x;
            float f2 = this.mEndMotionPath.y - this.mStartMotionPath.y;
            float f3 = this.mEndMotionPath.width - this.mStartMotionPath.width;
            float f4 = this.mEndMotionPath.height - this.mStartMotionPath.height;
            mAnchorDpDt[0] = ((1.0f - locationX) * f) + ((f + f3) * locationX);
            mAnchorDpDt[1] = ((1.0f - locationY) * f2) + ((f2 + f4) * locationY);
            return;
        }
        curveFitArr[0].getSlope(adjustedPosition, this.mInterpolateVelocity);
        this.mSpline[0].getPos(adjustedPosition, this.mInterpolateData);
        float f5 = this.mVelocity[0];
        int i = 0;
        while (true) {
            dArr = this.mInterpolateVelocity;
            if (i >= dArr.length) {
                break;
            }
            dArr[i] = dArr[i] * f5;
            i++;
        }
        CurveFit curveFit = this.mArcSpline;
        if (curveFit == null) {
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, dArr, this.mInterpolateData);
            return;
        }
        double[] dArr2 = this.mInterpolateData;
        if (dArr2.length > 0) {
            curveFit.getPos(adjustedPosition, dArr2);
            this.mArcSpline.getSlope(adjustedPosition, this.mInterpolateVelocity);
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
        }
    }

    public int getDrawPath() {
        int i = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            i = Math.max(i, it.next().mDrawPath);
        }
        return Math.max(i, this.mEndMotionPath.mDrawPath);
    }

    public float getFinalHeight() {
        return this.mEndMotionPath.height;
    }

    public float getFinalWidth() {
        return this.mEndMotionPath.width;
    }

    public float getFinalX() {
        return this.mEndMotionPath.x;
    }

    public float getFinalY() {
        return this.mEndMotionPath.y;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String name) {
        return 0;
    }

    public MotionPaths getKeyFrame(int i) {
        return this.mMotionPaths.get(i);
    }

    public int getKeyFrameInfo(int type, int[] info) {
        int i = 0;
        int i2 = 0;
        float[] fArr = new float[2];
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey next = it.next();
            if (next.mType == type || type != -1) {
                int i3 = i2;
                info[i2] = 0;
                int i4 = i2 + 1;
                info[i4] = next.mType;
                int i5 = i4 + 1;
                info[i5] = next.mFramePosition;
                float f = next.mFramePosition / 100.0f;
                this.mSpline[0].getPos(f, this.mInterpolateData);
                this.mStartMotionPath.getCenter(f, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
                int i6 = i5 + 1;
                info[i6] = Float.floatToIntBits(fArr[0]);
                int i7 = i6 + 1;
                info[i7] = Float.floatToIntBits(fArr[1]);
                if (next instanceof MotionKeyPosition) {
                    MotionKeyPosition motionKeyPosition = (MotionKeyPosition) next;
                    int i8 = i7 + 1;
                    info[i8] = motionKeyPosition.mPositionType;
                    int i9 = i8 + 1;
                    info[i9] = Float.floatToIntBits(motionKeyPosition.mPercentX);
                    i7 = i9 + 1;
                    info[i7] = Float.floatToIntBits(motionKeyPosition.mPercentY);
                }
                i2 = i7 + 1;
                info[i3] = i2 - i3;
                i++;
            }
        }
        return i;
    }

    float getKeyFrameParameter(int type, float x, float y) {
        float f = this.mEndMotionPath.x - this.mStartMotionPath.x;
        float f2 = this.mEndMotionPath.y - this.mStartMotionPath.y;
        float f3 = this.mStartMotionPath.x + (this.mStartMotionPath.width / 2.0f);
        float f4 = this.mStartMotionPath.y + (this.mStartMotionPath.height / 2.0f);
        float hypot = (float) Math.hypot(f, f2);
        if (hypot < 1.0E-7d) {
            return Float.NaN;
        }
        float f5 = x - f3;
        float f6 = y - f4;
        if (((float) Math.hypot(f5, f6)) == 0.0f) {
            return 0.0f;
        }
        float f7 = (f5 * f) + (f6 * f2);
        switch (type) {
            case 0:
                return f7 / hypot;
            case 1:
                return (float) Math.sqrt((hypot * hypot) - (f7 * f7));
            case 2:
                return f5 / f;
            case 3:
                return f6 / f;
            case 4:
                return f5 / f2;
            case 5:
                return f6 / f2;
            default:
                return 0.0f;
        }
    }

    public int getKeyFramePositions(int[] type, float[] pos) {
        int i = 0;
        int i2 = 0;
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey next = it.next();
            int i3 = i + 1;
            type[i] = next.mFramePosition + (next.mType * 1000);
            float f = next.mFramePosition / 100.0f;
            this.mSpline[0].getPos(f, this.mInterpolateData);
            this.mStartMotionPath.getCenter(f, this.mInterpolateVariables, this.mInterpolateData, pos, i2);
            i2 += 2;
            i = i3;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public double[] getPos(double position) {
        this.mSpline[0].getPos(position, this.mInterpolateData);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos(position, dArr);
            }
        }
        return this.mInterpolateData;
    }

    MotionKeyPosition getPositionKeyframe(int layoutWidth, int layoutHeight, float x, float y) {
        FloatRect floatRect = new FloatRect();
        floatRect.left = this.mStartMotionPath.x;
        floatRect.top = this.mStartMotionPath.y;
        floatRect.right = floatRect.left + this.mStartMotionPath.width;
        floatRect.bottom = floatRect.top + this.mStartMotionPath.height;
        FloatRect floatRect2 = new FloatRect();
        floatRect2.left = this.mEndMotionPath.x;
        floatRect2.top = this.mEndMotionPath.y;
        floatRect2.right = floatRect2.left + this.mEndMotionPath.width;
        floatRect2.bottom = floatRect2.top + this.mEndMotionPath.height;
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey next = it.next();
            if ((next instanceof MotionKeyPosition) && ((MotionKeyPosition) next).intersects(layoutWidth, layoutHeight, floatRect, floatRect2, x, y)) {
                return (MotionKeyPosition) next;
            }
        }
        return null;
    }

    void getPostLayoutDvDp(float position, int width, int height, float locationX, float locationY, float[] mAnchorDpDt) {
        VelocityMatrix velocityMatrix;
        float adjustedPosition = getAdjustedPosition(position, this.mVelocity);
        HashMap<String, SplineSet> hashMap = this.mAttributesMap;
        SplineSet splineSet = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = this.mAttributesMap;
        SplineSet splineSet2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, SplineSet> hashMap3 = this.mAttributesMap;
        SplineSet splineSet3 = hashMap3 == null ? null : hashMap3.get("rotationZ");
        HashMap<String, SplineSet> hashMap4 = this.mAttributesMap;
        SplineSet splineSet4 = hashMap4 == null ? null : hashMap4.get("scaleX");
        HashMap<String, SplineSet> hashMap5 = this.mAttributesMap;
        SplineSet splineSet5 = hashMap5 == null ? null : hashMap5.get("scaleY");
        HashMap<String, KeyCycleOscillator> hashMap6 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator = hashMap6 == null ? null : hashMap6.get("translationX");
        HashMap<String, KeyCycleOscillator> hashMap7 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator2 = hashMap7 == null ? null : hashMap7.get("translationY");
        HashMap<String, KeyCycleOscillator> hashMap8 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator3 = hashMap8 == null ? null : hashMap8.get("rotationZ");
        HashMap<String, KeyCycleOscillator> hashMap9 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator4 = hashMap9 == null ? null : hashMap9.get("scaleX");
        HashMap<String, KeyCycleOscillator> hashMap10 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator5 = hashMap10 != null ? hashMap10.get("scaleY") : null;
        VelocityMatrix velocityMatrix2 = new VelocityMatrix();
        velocityMatrix2.clear();
        velocityMatrix2.setRotationVelocity(splineSet3, adjustedPosition);
        velocityMatrix2.setTranslationVelocity(splineSet, splineSet2, adjustedPosition);
        velocityMatrix2.setScaleVelocity(splineSet4, splineSet5, adjustedPosition);
        velocityMatrix2.setRotationVelocity(keyCycleOscillator3, adjustedPosition);
        velocityMatrix2.setTranslationVelocity(keyCycleOscillator, keyCycleOscillator2, adjustedPosition);
        velocityMatrix2.setScaleVelocity(keyCycleOscillator4, keyCycleOscillator5, adjustedPosition);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos(adjustedPosition, dArr);
                this.mArcSpline.getSlope(adjustedPosition, this.mInterpolateVelocity);
                velocityMatrix = velocityMatrix2;
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            } else {
                velocityMatrix = velocityMatrix2;
            }
            velocityMatrix.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
            return;
        }
        if (this.mSpline == null) {
            float f = this.mEndMotionPath.x - this.mStartMotionPath.x;
            float f2 = this.mEndMotionPath.y - this.mStartMotionPath.y;
            float f3 = this.mEndMotionPath.width - this.mStartMotionPath.width;
            float f4 = f2 + (this.mEndMotionPath.height - this.mStartMotionPath.height);
            mAnchorDpDt[0] = ((1.0f - locationX) * f) + ((f + f3) * locationX);
            mAnchorDpDt[1] = ((1.0f - locationY) * f2) + (f4 * locationY);
            velocityMatrix2.clear();
            velocityMatrix2.setRotationVelocity(splineSet3, adjustedPosition);
            velocityMatrix2.setTranslationVelocity(splineSet, splineSet2, adjustedPosition);
            velocityMatrix2.setScaleVelocity(splineSet4, splineSet5, adjustedPosition);
            velocityMatrix2.setRotationVelocity(keyCycleOscillator3, adjustedPosition);
            velocityMatrix2.setTranslationVelocity(keyCycleOscillator, keyCycleOscillator2, adjustedPosition);
            velocityMatrix2.setScaleVelocity(keyCycleOscillator4, keyCycleOscillator5, adjustedPosition);
            velocityMatrix2.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
            return;
        }
        float adjustedPosition2 = getAdjustedPosition(adjustedPosition, this.mVelocity);
        this.mSpline[0].getSlope(adjustedPosition2, this.mInterpolateVelocity);
        this.mSpline[0].getPos(adjustedPosition2, this.mInterpolateData);
        float f5 = this.mVelocity[0];
        int i = 0;
        while (true) {
            double[] dArr2 = this.mInterpolateVelocity;
            if (i >= dArr2.length) {
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, dArr2, this.mInterpolateData);
                velocityMatrix2.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
                return;
            } else {
                dArr2[i] = dArr2[i] * f5;
                i++;
            }
        }
    }

    public float getStartHeight() {
        return this.mStartMotionPath.height;
    }

    public float getStartWidth() {
        return this.mStartMotionPath.width;
    }

    public float getStartX() {
        return this.mStartMotionPath.x;
    }

    public float getStartY() {
        return this.mStartMotionPath.y;
    }

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public MotionWidget getView() {
        return this.mView;
    }

    public boolean interpolate(MotionWidget child, float global_position, long time, KeyCache keyCache) {
        float f;
        float adjustedPosition = getAdjustedPosition(global_position, null);
        int i = this.mQuantizeMotionSteps;
        if (i != -1) {
            float f2 = 1.0f / i;
            float floor = ((float) Math.floor(adjustedPosition / f2)) * f2;
            float f3 = (adjustedPosition % f2) / f2;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f3 = (this.mQuantizeMotionPhase + f3) % 1.0f;
            }
            DifferentialInterpolator differentialInterpolator = this.mQuantizeMotionInterpolator;
            f = ((differentialInterpolator != null ? differentialInterpolator.getInterpolation(f3) : ((double) f3) > 0.5d ? 1.0f : 0.0f) * f2) + floor;
        } else {
            f = adjustedPosition;
        }
        HashMap<String, SplineSet> hashMap = this.mAttributesMap;
        if (hashMap != null) {
            Iterator<SplineSet> it = hashMap.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(child, f);
            }
        }
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            curveFitArr[0].getPos(f, this.mInterpolateData);
            this.mSpline[0].getSlope(f, this.mInterpolateVelocity);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(f, dArr);
                    this.mArcSpline.getSlope(f, this.mInterpolateVelocity);
                }
            }
            if (!this.mNoMovement) {
                this.mStartMotionPath.setView(f, child, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null);
            }
            if (this.mTransformPivotTarget != -1) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = child.getParent().findViewById(this.mTransformPivotTarget);
                }
                if (this.mTransformPivotView != null) {
                    float top = (r1.getTop() + this.mTransformPivotView.getBottom()) / 2.0f;
                    float left = (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight()) / 2.0f;
                    if (child.getRight() - child.getLeft() > 0 && child.getBottom() - child.getTop() > 0) {
                        child.setPivotX(left - child.getLeft());
                        child.setPivotY(top - child.getTop());
                    }
                }
            }
            int i2 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i2 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i2].getPos(f, this.mValuesBuff);
                this.mStartMotionPath.customAttributes.get(this.mAttributeNames[i2 - 1]).setInterpolatedValue(child, this.mValuesBuff);
                i2++;
            }
            if (this.mStartPoint.mVisibilityMode == 0) {
                if (f <= 0.0f) {
                    child.setVisibility(this.mStartPoint.visibility);
                } else if (f >= 1.0f) {
                    child.setVisibility(this.mEndPoint.visibility);
                } else if (this.mEndPoint.visibility != this.mStartPoint.visibility) {
                    child.setVisibility(4);
                }
            }
            if (this.mKeyTriggers != null) {
                int i3 = 0;
                while (true) {
                    MotionKeyTrigger[] motionKeyTriggerArr = this.mKeyTriggers;
                    if (i3 >= motionKeyTriggerArr.length) {
                        break;
                    }
                    motionKeyTriggerArr[i3].conditionallyFire(f, child);
                    i3++;
                }
            }
        } else {
            float f4 = this.mStartMotionPath.x + ((this.mEndMotionPath.x - this.mStartMotionPath.x) * f);
            float f5 = this.mStartMotionPath.y + ((this.mEndMotionPath.y - this.mStartMotionPath.y) * f);
            int i4 = (int) (f4 + 0.5f);
            int i5 = (int) (f5 + 0.5f);
            int i6 = (int) (f4 + 0.5f + this.mStartMotionPath.width + ((this.mEndMotionPath.width - this.mStartMotionPath.width) * f));
            int i7 = (int) (0.5f + f5 + this.mStartMotionPath.height + ((this.mEndMotionPath.height - this.mStartMotionPath.height) * f));
            int i8 = i6 - i4;
            int i9 = i7 - i5;
            child.layout(i4, i5, i6, i7);
        }
        HashMap<String, KeyCycleOscillator> hashMap2 = this.mCycleMap;
        if (hashMap2 != null) {
            for (KeyCycleOscillator keyCycleOscillator : hashMap2.values()) {
                if (keyCycleOscillator instanceof KeyCycleOscillator.PathRotateSet) {
                    double[] dArr2 = this.mInterpolateVelocity;
                    ((KeyCycleOscillator.PathRotateSet) keyCycleOscillator).setPathRotate(child, f, dArr2[0], dArr2[1]);
                } else {
                    keyCycleOscillator.setProperty(child, f);
                }
            }
        }
        return false;
    }

    String name() {
        return this.mView.getName();
    }

    void positionKeyframe(MotionWidget view, MotionKeyPosition key, float x, float y, String[] attribute, float[] value) {
        FloatRect floatRect = new FloatRect();
        floatRect.left = this.mStartMotionPath.x;
        floatRect.top = this.mStartMotionPath.y;
        floatRect.right = floatRect.left + this.mStartMotionPath.width;
        floatRect.bottom = floatRect.top + this.mStartMotionPath.height;
        FloatRect floatRect2 = new FloatRect();
        floatRect2.left = this.mEndMotionPath.x;
        floatRect2.top = this.mEndMotionPath.y;
        floatRect2.right = floatRect2.left + this.mEndMotionPath.width;
        floatRect2.bottom = floatRect2.top + this.mEndMotionPath.height;
        key.positionAttributes(view, floatRect, floatRect2, x, y, attribute, value);
    }

    void rotate(Rect rect, Rect out, int rotation, int preHeight, int preWidth) {
        switch (rotation) {
            case 1:
                int i = rect.left + rect.right;
                out.left = ((rect.top + rect.bottom) - rect.width()) / 2;
                out.top = preWidth - ((rect.height() + i) / 2);
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 2:
                int i2 = rect.left + rect.right;
                out.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
                out.top = (i2 - rect.height()) / 2;
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 3:
                int i3 = rect.left + rect.right;
                int i4 = rect.top + rect.bottom;
                out.left = ((rect.height() / 2) + rect.top) - (i3 / 2);
                out.top = preWidth - ((rect.height() + i3) / 2);
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 4:
                int i5 = rect.left + rect.right;
                out.left = preHeight - ((rect.width() + (rect.bottom + rect.top)) / 2);
                out.top = (i5 - rect.height()) / 2;
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            default:
                return;
        }
    }

    void setBothStates(MotionWidget v) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        this.mNoMovement = true;
        this.mStartMotionPath.setBounds(v.getX(), v.getY(), v.getWidth(), v.getHeight());
        this.mEndMotionPath.setBounds(v.getX(), v.getY(), v.getWidth(), v.getHeight());
        this.mStartPoint.setState(v);
        this.mEndPoint.setState(v);
    }

    public void setDrawPath(int debugMode) {
        this.mStartMotionPath.mDrawPath = debugMode;
    }

    public void setEnd(MotionWidget mw) {
        this.mEndMotionPath.time = 1.0f;
        this.mEndMotionPath.position = 1.0f;
        readView(this.mEndMotionPath);
        this.mEndMotionPath.setBounds(mw.getLeft(), mw.getTop(), mw.getWidth(), mw.getHeight());
        this.mEndMotionPath.applyParameters(mw);
        this.mEndPoint.setState(mw);
    }

    public void setPathMotionArc(int arc) {
        this.mPathMotionArc = arc;
    }

    public void setStart(MotionWidget mw) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        this.mStartMotionPath.setBounds(mw.getX(), mw.getY(), mw.getWidth(), mw.getHeight());
        this.mStartMotionPath.applyParameters(mw);
        this.mStartPoint.setState(mw);
    }

    public void setStartState(ViewState rect, MotionWidget v, int rotation, int preWidth, int preHeight) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        Rect rect2 = new Rect();
        switch (rotation) {
            case 1:
                int i = rect.left + rect.right;
                rect2.left = ((rect.top + rect.bottom) - rect.width()) / 2;
                rect2.top = preWidth - ((rect.height() + i) / 2);
                rect2.right = rect2.left + rect.width();
                rect2.bottom = rect2.top + rect.height();
                break;
            case 2:
                int i2 = rect.left + rect.right;
                rect2.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
                rect2.top = (i2 - rect.height()) / 2;
                rect2.right = rect2.left + rect.width();
                rect2.bottom = rect2.top + rect.height();
                break;
        }
        this.mStartMotionPath.setBounds(rect2.left, rect2.top, rect2.width(), rect2.height());
        this.mStartPoint.setState(rect2, v, rotation, rect.rotation);
    }

    public void setTransformPivotTarget(int transformPivotTarget) {
        this.mTransformPivotTarget = transformPivotTarget;
        this.mTransformPivotView = null;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int id, float value) {
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int id, int value) {
        switch (id) {
            case 509:
                setPathMotionArc(value);
                return true;
            case TypedValues.TransitionType.TYPE_AUTO_TRANSITION /* 704 */:
                return true;
            default:
                return false;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int id, String value) {
        if (705 == id) {
            System.out.println("TYPE_INTERPOLATOR  " + value);
            this.mQuantizeMotionInterpolator = getInterpolator(-1, value, 0);
        }
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int id, boolean value) {
        return false;
    }

    public void setView(MotionWidget view) {
        this.mView = view;
    }

    public void setup(int parentWidth, int parentHeight, float transitionDuration, long currentTime) {
        char c;
        HashSet hashSet;
        boolean z;
        boolean[] zArr;
        HashSet<String> hashSet2;
        CustomVariable customVariable;
        Iterator<String> it;
        HashSet<String> hashSet3;
        SplineSet makeSpline;
        HashSet<String> hashSet4;
        Integer num;
        HashSet hashSet5;
        ArrayList arrayList;
        SplineSet makeSpline2;
        String str;
        HashSet hashSet6 = new HashSet();
        HashSet<String> hashSet7 = new HashSet<>();
        HashSet<String> hashSet8 = new HashSet<>();
        HashSet<String> hashSet9 = new HashSet<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        ArrayList arrayList2 = null;
        int i = this.mPathMotionArc;
        if (i != -1) {
            this.mStartMotionPath.mPathMotionArc = i;
        }
        this.mStartPoint.different(this.mEndPoint, hashSet8);
        ArrayList<MotionKey> arrayList3 = this.mKeyList;
        if (arrayList3 != null) {
            Iterator<MotionKey> it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                MotionKey next = it2.next();
                if (next instanceof MotionKeyPosition) {
                    MotionKeyPosition motionKeyPosition = (MotionKeyPosition) next;
                    insertKey(new MotionPaths(parentWidth, parentHeight, motionKeyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    if (motionKeyPosition.mCurveFit != -1) {
                        this.mCurveFitType = motionKeyPosition.mCurveFit;
                    }
                } else if (next instanceof MotionKeyCycle) {
                    next.getAttributeNames(hashSet9);
                } else if (next instanceof MotionKeyTimeCycle) {
                    next.getAttributeNames(hashSet7);
                } else if (next instanceof MotionKeyTrigger) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add((MotionKeyTrigger) next);
                } else {
                    next.setInterpolation(hashMap);
                    next.getAttributeNames(hashSet8);
                }
            }
        }
        if (arrayList2 != null) {
            this.mKeyTriggers = (MotionKeyTrigger[]) arrayList2.toArray(new MotionKeyTrigger[0]);
        }
        char c2 = 1;
        if (!hashSet8.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            Iterator<String> it3 = hashSet8.iterator();
            while (it3.hasNext()) {
                String next2 = it3.next();
                if (next2.startsWith("CUSTOM,")) {
                    KeyFrameArray.CustomVar customVar = new KeyFrameArray.CustomVar();
                    String str2 = next2.split(",")[c2];
                    Iterator<MotionKey> it4 = this.mKeyList.iterator();
                    while (it4.hasNext()) {
                        HashSet hashSet10 = hashSet6;
                        MotionKey next3 = it4.next();
                        ArrayList arrayList4 = arrayList2;
                        if (next3.mCustom == null) {
                            arrayList2 = arrayList4;
                            hashSet6 = hashSet10;
                        } else {
                            CustomVariable customVariable2 = next3.mCustom.get(str2);
                            if (customVariable2 != null) {
                                str = str2;
                                customVar.append(next3.mFramePosition, customVariable2);
                            } else {
                                str = str2;
                            }
                            arrayList2 = arrayList4;
                            hashSet6 = hashSet10;
                            str2 = str;
                        }
                    }
                    hashSet5 = hashSet6;
                    arrayList = arrayList2;
                    makeSpline2 = SplineSet.makeCustomSplineSet(next2, customVar);
                } else {
                    hashSet5 = hashSet6;
                    arrayList = arrayList2;
                    makeSpline2 = SplineSet.makeSpline(next2, currentTime);
                }
                if (makeSpline2 == null) {
                    arrayList2 = arrayList;
                    hashSet6 = hashSet5;
                    c2 = 1;
                } else {
                    makeSpline2.setType(next2);
                    this.mAttributesMap.put(next2, makeSpline2);
                    arrayList2 = arrayList;
                    hashSet6 = hashSet5;
                    c2 = 1;
                }
            }
            ArrayList<MotionKey> arrayList5 = this.mKeyList;
            if (arrayList5 != null) {
                Iterator<MotionKey> it5 = arrayList5.iterator();
                while (it5.hasNext()) {
                    MotionKey next4 = it5.next();
                    if (next4 instanceof MotionKeyAttributes) {
                        next4.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str3 : this.mAttributesMap.keySet()) {
                int i2 = 0;
                if (hashMap.containsKey(str3) && (num = hashMap.get(str3)) != null) {
                    i2 = num.intValue();
                }
                SplineSet splineSet = this.mAttributesMap.get(str3);
                if (splineSet != null) {
                    splineSet.setup(i2);
                }
            }
        }
        if (!hashSet7.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            Iterator<String> it6 = hashSet7.iterator();
            while (it6.hasNext()) {
                String next5 = it6.next();
                if (!this.mTimeCycleAttributesMap.containsKey(next5)) {
                    if (next5.startsWith("CUSTOM,")) {
                        KeyFrameArray.CustomVar customVar2 = new KeyFrameArray.CustomVar();
                        String str4 = next5.split(",")[1];
                        Iterator<MotionKey> it7 = this.mKeyList.iterator();
                        while (it7.hasNext()) {
                            MotionKey next6 = it7.next();
                            Iterator<String> it8 = it6;
                            if (next6.mCustom == null) {
                                it6 = it8;
                            } else {
                                CustomVariable customVariable3 = next6.mCustom.get(str4);
                                if (customVariable3 != null) {
                                    hashSet4 = hashSet7;
                                    customVar2.append(next6.mFramePosition, customVariable3);
                                } else {
                                    hashSet4 = hashSet7;
                                }
                                it6 = it8;
                                hashSet7 = hashSet4;
                            }
                        }
                        it = it6;
                        hashSet3 = hashSet7;
                        makeSpline = SplineSet.makeCustomSplineSet(next5, customVar2);
                    } else {
                        it = it6;
                        hashSet3 = hashSet7;
                        makeSpline = SplineSet.makeSpline(next5, currentTime);
                    }
                    if (makeSpline == null) {
                        it6 = it;
                        hashSet7 = hashSet3;
                    } else {
                        makeSpline.setType(next5);
                        it6 = it;
                        hashSet7 = hashSet3;
                    }
                }
            }
            ArrayList<MotionKey> arrayList6 = this.mKeyList;
            if (arrayList6 != null) {
                Iterator<MotionKey> it9 = arrayList6.iterator();
                while (it9.hasNext()) {
                    MotionKey next7 = it9.next();
                    if (next7 instanceof MotionKeyTimeCycle) {
                        ((MotionKeyTimeCycle) next7).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str5 : this.mTimeCycleAttributesMap.keySet()) {
                int i3 = 0;
                if (hashMap.containsKey(str5)) {
                    i3 = hashMap.get(str5).intValue();
                }
                this.mTimeCycleAttributesMap.get(str5).setup(i3);
            }
        }
        MotionPaths[] motionPathsArr = new MotionPaths[this.mMotionPaths.size() + 2];
        int i4 = 1;
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[motionPathsArr.length - 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == MotionKey.UNSET) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it10 = this.mMotionPaths.iterator();
        while (it10.hasNext()) {
            motionPathsArr[i4] = it10.next();
            i4++;
        }
        char c3 = 18;
        HashSet hashSet11 = new HashSet();
        for (String str6 : this.mEndMotionPath.customAttributes.keySet()) {
            if (this.mStartMotionPath.customAttributes.containsKey(str6) && !hashSet8.contains("CUSTOM," + str6)) {
                hashSet11.add(str6);
            }
        }
        String[] strArr = (String[]) hashSet11.toArray(new String[0]);
        this.mAttributeNames = strArr;
        this.mAttributeInterpolatorCount = new int[strArr.length];
        int i5 = 0;
        while (true) {
            String[] strArr2 = this.mAttributeNames;
            if (i5 >= strArr2.length) {
                break;
            }
            String str7 = strArr2[i5];
            this.mAttributeInterpolatorCount[i5] = 0;
            int i6 = 0;
            while (true) {
                if (i6 >= motionPathsArr.length) {
                    break;
                }
                if (motionPathsArr[i6].customAttributes.containsKey(str7) && (customVariable = motionPathsArr[i6].customAttributes.get(str7)) != null) {
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i5] = iArr[i5] + customVariable.numberOfInterpolatedValues();
                    break;
                }
                i6++;
            }
            i5++;
        }
        boolean z2 = motionPathsArr[0].mPathMotionArc != -1;
        boolean[] zArr2 = new boolean[this.mAttributeNames.length + 18];
        for (int i7 = 1; i7 < motionPathsArr.length; i7++) {
            motionPathsArr[i7].different(motionPathsArr[i7 - 1], zArr2, this.mAttributeNames, z2);
        }
        int i8 = 0;
        for (int i9 = 1; i9 < zArr2.length; i9++) {
            if (zArr2[i9]) {
                i8++;
            }
        }
        this.mInterpolateVariables = new int[i8];
        int max = Math.max(2, i8);
        this.mInterpolateData = new double[max];
        this.mInterpolateVelocity = new double[max];
        int i10 = 0;
        for (int i11 = 1; i11 < zArr2.length; i11++) {
            if (zArr2[i11]) {
                this.mInterpolateVariables[i10] = i11;
                i10++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, motionPathsArr.length, this.mInterpolateVariables.length);
        double[] dArr2 = new double[motionPathsArr.length];
        int i12 = 0;
        while (i12 < motionPathsArr.length) {
            motionPathsArr[i12].fillStandard(dArr[i12], this.mInterpolateVariables);
            dArr2[i12] = motionPathsArr[i12].time;
            i12++;
            i10 = i10;
        }
        int i13 = 0;
        while (true) {
            int[] iArr2 = this.mInterpolateVariables;
            if (i13 >= iArr2.length) {
                break;
            }
            int i14 = iArr2[i13];
            if (i14 < MotionPaths.names.length) {
                hashSet2 = hashSet8;
                String str8 = MotionPaths.names[this.mInterpolateVariables[i13]] + " [";
                int i15 = 0;
                while (i15 < motionPathsArr.length) {
                    str8 = str8 + dArr[i15][i13];
                    i15++;
                    hashMap = hashMap;
                    i14 = i14;
                }
            } else {
                hashSet2 = hashSet8;
            }
            i13++;
            hashSet8 = hashSet2;
            hashMap = hashMap;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i16 = 0;
        while (true) {
            String[] strArr3 = this.mAttributeNames;
            if (i16 >= strArr3.length) {
                break;
            }
            int i17 = 0;
            double[][] dArr3 = (double[][]) null;
            double[] dArr4 = null;
            String str9 = strArr3[i16];
            int i18 = 0;
            while (true) {
                c = c3;
                if (i18 < motionPathsArr.length) {
                    if (motionPathsArr[i18].hasCustomData(str9)) {
                        if (dArr3 == null) {
                            dArr4 = new double[motionPathsArr.length];
                            hashSet = hashSet11;
                            z = z2;
                            zArr = zArr2;
                            dArr3 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, motionPathsArr.length, motionPathsArr[i18].getCustomDataCount(str9));
                        } else {
                            hashSet = hashSet11;
                            z = z2;
                            zArr = zArr2;
                        }
                        dArr4[i17] = motionPathsArr[i18].time;
                        motionPathsArr[i18].getCustomData(str9, dArr3[i17], 0);
                        i17++;
                    } else {
                        hashSet = hashSet11;
                        z = z2;
                        zArr = zArr2;
                    }
                    i18++;
                    c3 = c;
                    hashSet11 = hashSet;
                    z2 = z;
                    zArr2 = zArr;
                }
            }
            this.mSpline[i16 + 1] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr4, i17), (double[][]) Arrays.copyOf(dArr3, i17));
            i16++;
            c3 = c;
            hashSet11 = hashSet11;
            z2 = z2;
            zArr2 = zArr2;
        }
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr[0].mPathMotionArc != -1) {
            int length = motionPathsArr.length;
            int[] iArr3 = new int[length];
            double[] dArr5 = new double[length];
            double[][] dArr6 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 2);
            for (int i19 = 0; i19 < length; i19++) {
                iArr3[i19] = motionPathsArr[i19].mPathMotionArc;
                dArr5[i19] = motionPathsArr[i19].time;
                dArr6[i19][0] = motionPathsArr[i19].x;
                dArr6[i19][1] = motionPathsArr[i19].y;
            }
            this.mArcSpline = CurveFit.getArc(iArr3, dArr5, dArr6);
        }
        float f = Float.NaN;
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            Iterator<String> it11 = hashSet9.iterator();
            while (it11.hasNext()) {
                String next8 = it11.next();
                KeyCycleOscillator makeWidgetCycle = KeyCycleOscillator.makeWidgetCycle(next8);
                if (makeWidgetCycle != null) {
                    if (makeWidgetCycle.variesByPath() && Float.isNaN(f)) {
                        f = getPreCycleDistance();
                    }
                    makeWidgetCycle.setType(next8);
                    this.mCycleMap.put(next8, makeWidgetCycle);
                }
            }
            Iterator<MotionKey> it12 = this.mKeyList.iterator();
            while (it12.hasNext()) {
                MotionKey next9 = it12.next();
                if (next9 instanceof MotionKeyCycle) {
                    ((MotionKeyCycle) next9).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<KeyCycleOscillator> it13 = this.mCycleMap.values().iterator();
            while (it13.hasNext()) {
                it13.next().setup(f);
            }
        }
    }

    public void setupRelative(Motion motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.x + " y: " + this.mStartMotionPath.y + " end: x: " + this.mEndMotionPath.x + " y: " + this.mEndMotionPath.y;
    }
}
