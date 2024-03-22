package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 0020.java */
/* loaded from: classes.dex */
public class MotionController {
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
    private HashMap<String, ViewSpline> mAttributesMap;
    String mConstraintTag;
    float mCurrentCenterX;
    float mCurrentCenterY;
    private HashMap<String, ViewOscillator> mCycleMap;
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private KeyTrigger[] mKeyTriggers;
    private CurveFit[] mSpline;
    private HashMap<String, ViewTimeCycle> mTimeCycleAttributesMap;
    View mView;
    Rect mTempRect = new Rect();
    boolean mForceMeasure = false;
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
    private ArrayList<Key> mKeyList = new ArrayList<>();
    private int mPathMotionArc = Key.UNSET;
    private int mTransformPivotTarget = Key.UNSET;
    private View mTransformPivotView = null;
    private int mQuantizeMotionSteps = Key.UNSET;
    private float mQuantizeMotionPhase = Float.NaN;
    private Interpolator mQuantizeMotionInterpolator = null;
    private boolean mNoMovement = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MotionController(View view) {
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

    private static Interpolator getInterpolator(Context context, int type, String interpolatorString, int id) {
        switch (type) {
            case -2:
                return AnimationUtils.loadInterpolator(context, id);
            case -1:
                final Easing interpolator = Easing.getInterpolator(interpolatorString);
                return new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionController.1
                    @Override // android.animation.TimeInterpolator
                    public float getInterpolation(float v) {
                        return (float) Easing.this.get(v);
                    }
                };
            case 0:
                return new AccelerateDecelerateInterpolator();
            case 1:
                return new AccelerateInterpolator();
            case 2:
                return new DecelerateInterpolator();
            case 3:
                return null;
            case 4:
                return new BounceInterpolator();
            case 5:
                return new OvershootInterpolator();
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
        if (Collections.binarySearch(this.mMotionPaths, point) == 0) {
            Log.e(TAG, " KeyPath position \"" + point.position + "\" outside of range");
        }
        this.mMotionPaths.add((-r0) - 1, point);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    public void addKey(Key key) {
        this.mKeyList.add(key);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addKeys(ArrayList<Key> list) {
        this.mKeyList.addAll(list);
    }

    void buildBounds(float[] bounds, int pointCount) {
        float f;
        MotionController motionController = this;
        int i = pointCount;
        float f2 = 1.0f;
        float f3 = 1.0f / ((float) (i - 1));
        HashMap<String, ViewSpline> hashMap = motionController.mAttributesMap;
        ViewSpline viewSpline = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, ViewSpline> hashMap2 = motionController.mAttributesMap;
        if (hashMap2 != null) {
            hashMap2.get("translationY");
        }
        HashMap<String, ViewOscillator> hashMap3 = motionController.mCycleMap;
        if (hashMap3 != null) {
            hashMap3.get("translationX");
        }
        HashMap<String, ViewOscillator> hashMap4 = motionController.mCycleMap;
        if (hashMap4 != null) {
            hashMap4.get("translationY");
        }
        int i2 = 0;
        while (i2 < i) {
            float f4 = i2 * f3;
            float f5 = motionController.mStaggerScale;
            if (f5 != f2) {
                float f6 = motionController.mStaggerOffset;
                if (f4 < f6) {
                    f4 = 0.0f;
                }
                if (f4 > f6 && f4 < 1.0d) {
                    f4 = Math.min((f4 - f6) * f5, f2);
                }
            }
            double d = f4;
            Easing easing = motionController.mStartMotionPath.mKeyFrameEasing;
            float f7 = 0.0f;
            float f8 = Float.NaN;
            Iterator<MotionPaths> it = motionController.mMotionPaths.iterator();
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
            motionController.mSpline[0].getPos(d, motionController.mInterpolateData);
            CurveFit curveFit = motionController.mArcSpline;
            if (curveFit != null) {
                double[] dArr = motionController.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            motionController.mStartMotionPath.getBounds(motionController.mInterpolateVariables, motionController.mInterpolateData, bounds, i2 * 2);
            i2++;
            motionController = this;
            i = pointCount;
            f3 = f;
            viewSpline = viewSpline;
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public int buildKeyFrames(float[] keyFrames, int[] mode) {
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
        for (int i2 = 0; i2 < timePoints.length; i2++) {
            this.mSpline[0].getPos(timePoints[i2], this.mInterpolateData);
            this.mStartMotionPath.getCenter(timePoints[i2], this.mInterpolateVariables, this.mInterpolateData, keyFrames, i);
            i += 2;
        }
        return i / 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void buildPath(float[] points, int pointCount) {
        float f;
        double d;
        MotionController motionController = this;
        float f2 = 1.0f;
        float f3 = 1.0f / ((float) (pointCount - 1));
        HashMap<String, ViewSpline> hashMap = motionController.mAttributesMap;
        ViewSpline viewSpline = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, ViewSpline> hashMap2 = motionController.mAttributesMap;
        ViewSpline viewSpline2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, ViewOscillator> hashMap3 = motionController.mCycleMap;
        ViewOscillator viewOscillator = hashMap3 == null ? null : hashMap3.get("translationX");
        HashMap<String, ViewOscillator> hashMap4 = motionController.mCycleMap;
        ViewOscillator viewOscillator2 = hashMap4 != null ? hashMap4.get("translationY") : null;
        int i = 0;
        while (i < pointCount) {
            float f4 = i * f3;
            float f5 = motionController.mStaggerScale;
            if (f5 != f2) {
                float f6 = motionController.mStaggerOffset;
                if (f4 < f6) {
                    f4 = 0.0f;
                }
                f = (f4 <= f6 || ((double) f4) >= 1.0d) ? f4 : Math.min((f4 - f6) * f5, f2);
            } else {
                f = f4;
            }
            double d2 = f;
            Easing easing = motionController.mStartMotionPath.mKeyFrameEasing;
            Iterator<MotionPaths> it = motionController.mMotionPaths.iterator();
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
            motionController.mSpline[0].getPos(d, motionController.mInterpolateData);
            CurveFit curveFit = motionController.mArcSpline;
            if (curveFit != null) {
                double[] dArr = motionController.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            float f9 = f;
            motionController.mStartMotionPath.getCenter(d, motionController.mInterpolateVariables, motionController.mInterpolateData, points, i * 2);
            if (viewOscillator != null) {
                int i2 = i * 2;
                points[i2] = points[i2] + viewOscillator.get(f9);
            } else if (viewSpline != null) {
                int i3 = i * 2;
                points[i3] = points[i3] + viewSpline.get(f9);
            }
            if (viewOscillator2 != null) {
                int i4 = (i * 2) + 1;
                points[i4] = points[i4] + viewOscillator2.get(f9);
            } else if (viewSpline2 != null) {
                int i5 = (i * 2) + 1;
                points[i5] = points[i5] + viewSpline2.get(f9);
            }
            i++;
            f2 = 1.0f;
            motionController = this;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void buildRect(float p, float[] path, int offset) {
        this.mSpline[0].getPos(getAdjustedPosition(p, null), this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, offset);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void buildRectangles(float[] path, int pointCount) {
        float f = 1.0f / ((float) (pointCount - 1));
        for (int i = 0; i < pointCount; i++) {
            this.mSpline[0].getPos(getAdjustedPosition(i * f, null), this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, i * 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public void endTrigger(boolean start) {
        String name = Debug.getName(this.mView);
        Log5ECF72.a(name);
        LogE84000.a(name);
        Log229316.a(name);
        if (!"button".equals(name) || this.mKeyTriggers == null) {
            return;
        }
        int i = 0;
        while (true) {
            KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
            if (i >= keyTriggerArr.length) {
                return;
            }
            keyTriggerArr[i].conditionallyFire(start ? -100.0f : 100.0f, this.mView);
            i++;
        }
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAttributeValues(String attributeType, float[] points, int pointCount) {
        float f = 1.0f / ((float) (pointCount - 1));
        ViewSpline viewSpline = this.mAttributesMap.get(attributeType);
        if (viewSpline == null) {
            return -1;
        }
        for (int i = 0; i < points.length; i++) {
            points[i] = viewSpline.get(i / (points.length - 1));
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getDpDt(float position, float locationX, float locationY, float[] mAnchorDpDt) {
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public MotionPaths getKeyFrame(int i) {
        return this.mMotionPaths.get(i);
    }

    public int getKeyFrameInfo(int type, int[] info) {
        int i = 0;
        int i2 = 0;
        float[] fArr = new float[2];
        Iterator<Key> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            Key next = it.next();
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
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    int i8 = i7 + 1;
                    info[i8] = keyPosition.mPositionType;
                    int i9 = i8 + 1;
                    info[i9] = Float.floatToIntBits(keyPosition.mPercentX);
                    i7 = i9 + 1;
                    info[i7] = Float.floatToIntBits(keyPosition.mPercentY);
                }
                i2 = i7 + 1;
                info[i3] = i2 - i3;
                i++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getKeyFrameParameter(int type, float x, float y) {
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
        Iterator<Key> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            Key next = it.next();
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public KeyPositionBase getPositionKeyframe(int layoutWidth, int layoutHeight, float x, float y) {
        RectF rectF = new RectF();
        rectF.left = this.mStartMotionPath.x;
        rectF.top = this.mStartMotionPath.y;
        rectF.right = rectF.left + this.mStartMotionPath.width;
        rectF.bottom = rectF.top + this.mStartMotionPath.height;
        RectF rectF2 = new RectF();
        rectF2.left = this.mEndMotionPath.x;
        rectF2.top = this.mEndMotionPath.y;
        rectF2.right = rectF2.left + this.mEndMotionPath.width;
        rectF2.bottom = rectF2.top + this.mEndMotionPath.height;
        Iterator<Key> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            Key next = it.next();
            if ((next instanceof KeyPositionBase) && ((KeyPositionBase) next).intersects(layoutWidth, layoutHeight, rectF, rectF2, x, y)) {
                return (KeyPositionBase) next;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getPostLayoutDvDp(float position, int width, int height, float locationX, float locationY, float[] mAnchorDpDt) {
        VelocityMatrix velocityMatrix;
        float adjustedPosition = getAdjustedPosition(position, this.mVelocity);
        HashMap<String, ViewSpline> hashMap = this.mAttributesMap;
        ViewSpline viewSpline = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, ViewSpline> hashMap2 = this.mAttributesMap;
        ViewSpline viewSpline2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, ViewSpline> hashMap3 = this.mAttributesMap;
        ViewSpline viewSpline3 = hashMap3 == null ? null : hashMap3.get(Key.ROTATION);
        HashMap<String, ViewSpline> hashMap4 = this.mAttributesMap;
        ViewSpline viewSpline4 = hashMap4 == null ? null : hashMap4.get("scaleX");
        HashMap<String, ViewSpline> hashMap5 = this.mAttributesMap;
        ViewSpline viewSpline5 = hashMap5 == null ? null : hashMap5.get("scaleY");
        HashMap<String, ViewOscillator> hashMap6 = this.mCycleMap;
        ViewOscillator viewOscillator = hashMap6 == null ? null : hashMap6.get("translationX");
        HashMap<String, ViewOscillator> hashMap7 = this.mCycleMap;
        ViewOscillator viewOscillator2 = hashMap7 == null ? null : hashMap7.get("translationY");
        HashMap<String, ViewOscillator> hashMap8 = this.mCycleMap;
        ViewOscillator viewOscillator3 = hashMap8 == null ? null : hashMap8.get(Key.ROTATION);
        HashMap<String, ViewOscillator> hashMap9 = this.mCycleMap;
        ViewOscillator viewOscillator4 = hashMap9 == null ? null : hashMap9.get("scaleX");
        HashMap<String, ViewOscillator> hashMap10 = this.mCycleMap;
        ViewOscillator viewOscillator5 = hashMap10 != null ? hashMap10.get("scaleY") : null;
        VelocityMatrix velocityMatrix2 = new VelocityMatrix();
        velocityMatrix2.clear();
        velocityMatrix2.setRotationVelocity(viewSpline3, adjustedPosition);
        velocityMatrix2.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
        velocityMatrix2.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
        velocityMatrix2.setRotationVelocity(viewOscillator3, adjustedPosition);
        velocityMatrix2.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
        velocityMatrix2.setScaleVelocity(viewOscillator4, viewOscillator5, adjustedPosition);
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
            velocityMatrix2.setRotationVelocity(viewSpline3, adjustedPosition);
            velocityMatrix2.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
            velocityMatrix2.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
            velocityMatrix2.setRotationVelocity(viewOscillator3, adjustedPosition);
            velocityMatrix2.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
            velocityMatrix2.setScaleVelocity(viewOscillator4, viewOscillator5, adjustedPosition);
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

    public View getView() {
        return this.mView;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean interpolate(View child, float global_position, long time, KeyCache keyCache) {
        float f;
        boolean z;
        ViewTimeCycle.PathRotate pathRotate;
        char c;
        float adjustedPosition = getAdjustedPosition(global_position, null);
        if (this.mQuantizeMotionSteps != Key.UNSET) {
            float f2 = 1.0f / this.mQuantizeMotionSteps;
            float floor = ((float) Math.floor(adjustedPosition / f2)) * f2;
            float f3 = (adjustedPosition % f2) / f2;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f3 = (this.mQuantizeMotionPhase + f3) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            f = ((interpolator != null ? interpolator.getInterpolation(f3) : ((double) f3) > 0.5d ? 1.0f : 0.0f) * f2) + floor;
        } else {
            f = adjustedPosition;
        }
        HashMap<String, ViewSpline> hashMap = this.mAttributesMap;
        if (hashMap != null) {
            Iterator<ViewSpline> it = hashMap.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(child, f);
            }
        }
        HashMap<String, ViewTimeCycle> hashMap2 = this.mTimeCycleAttributesMap;
        if (hashMap2 != null) {
            boolean z2 = false;
            ViewTimeCycle.PathRotate pathRotate2 = null;
            for (ViewTimeCycle viewTimeCycle : hashMap2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate2 = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    z2 |= viewTimeCycle.setProperty(child, f, time, keyCache);
                }
            }
            z = z2;
            pathRotate = pathRotate2;
        } else {
            z = false;
            pathRotate = null;
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
                this.mStartMotionPath.setView(f, child, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null, this.mForceMeasure);
                this.mForceMeasure = false;
            }
            if (this.mTransformPivotTarget != Key.UNSET) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = ((View) child.getParent()).findViewById(this.mTransformPivotTarget);
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
            HashMap<String, ViewSpline> hashMap3 = this.mAttributesMap;
            if (hashMap3 != null) {
                for (ViewSpline viewSpline : hashMap3.values()) {
                    if (viewSpline instanceof ViewSpline.PathRotate) {
                        double[] dArr2 = this.mInterpolateVelocity;
                        if (dArr2.length > 1) {
                            ((ViewSpline.PathRotate) viewSpline).setPathRotate(child, f, dArr2[0], dArr2[1]);
                        }
                    }
                }
            }
            if (pathRotate != null) {
                double[] dArr3 = this.mInterpolateVelocity;
                c = 1;
                z |= pathRotate.setPathRotate(child, keyCache, f, time, dArr3[0], dArr3[1]);
            } else {
                c = 1;
            }
            int i = 1;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i].getPos(f, this.mValuesBuff);
                CustomSupport.setInterpolatedValue(this.mStartMotionPath.attributes.get(this.mAttributeNames[i - 1]), child, this.mValuesBuff);
                i++;
            }
            if (this.mStartPoint.mVisibilityMode == 0) {
                if (f <= 0.0f) {
                    child.setVisibility(this.mStartPoint.visibility);
                } else if (f >= 1.0f) {
                    child.setVisibility(this.mEndPoint.visibility);
                } else if (this.mEndPoint.visibility != this.mStartPoint.visibility) {
                    child.setVisibility(0);
                }
            }
            if (this.mKeyTriggers != null) {
                int i2 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
                    if (i2 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i2].conditionallyFire(f, child);
                    i2++;
                }
            }
        } else {
            c = 1;
            float f4 = this.mStartMotionPath.x + ((this.mEndMotionPath.x - this.mStartMotionPath.x) * f);
            float f5 = this.mStartMotionPath.y + ((this.mEndMotionPath.y - this.mStartMotionPath.y) * f);
            int i3 = (int) (f4 + 0.5f);
            int i4 = (int) (f5 + 0.5f);
            int i5 = (int) (f4 + 0.5f + this.mStartMotionPath.width + ((this.mEndMotionPath.width - this.mStartMotionPath.width) * f));
            int i6 = (int) (0.5f + f5 + this.mStartMotionPath.height + ((this.mEndMotionPath.height - this.mStartMotionPath.height) * f));
            int i7 = i5 - i3;
            int i8 = i6 - i4;
            if (this.mEndMotionPath.width != this.mStartMotionPath.width || this.mEndMotionPath.height != this.mStartMotionPath.height || this.mForceMeasure) {
                child.measure(View.MeasureSpec.makeMeasureSpec(i7, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i8, BasicMeasure.EXACTLY));
                this.mForceMeasure = false;
            }
            child.layout(i3, i4, i5, i6);
        }
        HashMap<String, ViewOscillator> hashMap4 = this.mCycleMap;
        if (hashMap4 != null) {
            for (ViewOscillator viewOscillator : hashMap4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr4 = this.mInterpolateVelocity;
                    ((ViewOscillator.PathRotateSet) viewOscillator).setPathRotate(child, f, dArr4[0], dArr4[c]);
                } else {
                    viewOscillator.setProperty(child, f);
                }
            }
        }
        return z;
    }

    String name() {
        return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void positionKeyframe(View view, KeyPositionBase key, float x, float y, String[] attribute, float[] value) {
        RectF rectF = new RectF();
        rectF.left = this.mStartMotionPath.x;
        rectF.top = this.mStartMotionPath.y;
        rectF.right = rectF.left + this.mStartMotionPath.width;
        rectF.bottom = rectF.top + this.mStartMotionPath.height;
        RectF rectF2 = new RectF();
        rectF2.left = this.mEndMotionPath.x;
        rectF2.top = this.mEndMotionPath.y;
        rectF2.right = rectF2.left + this.mEndMotionPath.width;
        rectF2.bottom = rectF2.top + this.mEndMotionPath.height;
        key.positionAttributes(view, rectF, rectF2, x, y, attribute, value);
    }

    public void remeasure() {
        this.mForceMeasure = true;
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBothStates(View v) {
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

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEndState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        int i = constraintSet.mRotate;
        if (i != 0) {
            rotate(cw, this.mTempRect, i, parentWidth, parentHeight);
            cw = this.mTempRect;
        }
        this.mEndMotionPath.time = 1.0f;
        this.mEndMotionPath.position = 1.0f;
        readView(this.mEndMotionPath);
        this.mEndMotionPath.setBounds(cw.left, cw.top, cw.width(), cw.height());
        this.mEndMotionPath.applyParameters(constraintSet.getParameters(this.mId));
        this.mEndPoint.setState(cw, constraintSet, i, this.mId);
    }

    public void setPathMotionArc(int arc) {
        this.mPathMotionArc = arc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStartCurrentState(View v) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        this.mStartMotionPath.setBounds(v.getX(), v.getY(), v.getWidth(), v.getHeight());
        this.mStartPoint.setState(v);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStartState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        int i = constraintSet.mRotate;
        if (i != 0) {
            rotate(cw, this.mTempRect, i, parentWidth, parentHeight);
        }
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        readView(this.mStartMotionPath);
        this.mStartMotionPath.setBounds(cw.left, cw.top, cw.width(), cw.height());
        ConstraintSet.Constraint parameters = constraintSet.getParameters(this.mId);
        this.mStartMotionPath.applyParameters(parameters);
        this.mMotionStagger = parameters.motion.mMotionStagger;
        this.mStartPoint.setState(cw, constraintSet, i, this.mId);
        this.mTransformPivotTarget = parameters.transform.transformPivotTarget;
        this.mQuantizeMotionSteps = parameters.motion.mQuantizeMotionSteps;
        this.mQuantizeMotionPhase = parameters.motion.mQuantizeMotionPhase;
        this.mQuantizeMotionInterpolator = getInterpolator(this.mView.getContext(), parameters.motion.mQuantizeInterpolatorType, parameters.motion.mQuantizeInterpolatorString, parameters.motion.mQuantizeInterpolatorID);
    }

    public void setStartState(ViewState rect, View v, int rotation, int preWidth, int preHeight) {
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

    public void setView(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            this.mConstraintTag = ((ConstraintLayout.LayoutParams) layoutParams).getConstraintTag();
        }
    }

    public void setup(int parentWidth, int parentHeight, float transitionDuration, long currentTime) {
        boolean[] zArr;
        HashSet<String> hashSet;
        ConstraintAttribute constraintAttribute;
        Iterator<String> it;
        ViewTimeCycle makeSpline;
        Iterator<String> it2;
        Integer num;
        ViewSpline makeSpline2;
        HashSet hashSet2;
        HashSet hashSet3 = new HashSet();
        HashSet<String> hashSet4 = new HashSet<>();
        HashSet<String> hashSet5 = new HashSet<>();
        HashSet<String> hashSet6 = new HashSet<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        ArrayList arrayList = null;
        if (this.mPathMotionArc != Key.UNSET) {
            this.mStartMotionPath.mPathMotionArc = this.mPathMotionArc;
        }
        this.mStartPoint.different(this.mEndPoint, hashSet5);
        ArrayList<Key> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            Iterator<Key> it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                Key next = it3.next();
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    hashSet2 = hashSet3;
                    insertKey(new MotionPaths(parentWidth, parentHeight, keyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    if (keyPosition.mCurveFit != Key.UNSET) {
                        this.mCurveFitType = keyPosition.mCurveFit;
                    }
                } else {
                    hashSet2 = hashSet3;
                    if (next instanceof KeyCycle) {
                        next.getAttributeNames(hashSet6);
                    } else if (next instanceof KeyTimeCycle) {
                        next.getAttributeNames(hashSet4);
                    } else if (next instanceof KeyTrigger) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add((KeyTrigger) next);
                    } else {
                        next.setInterpolation(hashMap);
                        next.getAttributeNames(hashSet5);
                    }
                }
                hashSet3 = hashSet2;
            }
        }
        if (arrayList != null) {
            this.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        char c = 1;
        if (!hashSet5.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            Iterator<String> it4 = hashSet5.iterator();
            while (it4.hasNext()) {
                String next2 = it4.next();
                if (next2.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    String str = next2.split(",")[c];
                    Iterator<Key> it5 = this.mKeyList.iterator();
                    while (it5.hasNext()) {
                        Key next3 = it5.next();
                        if (next3.mCustomConstraints != null) {
                            ConstraintAttribute constraintAttribute2 = next3.mCustomConstraints.get(str);
                            if (constraintAttribute2 != null) {
                                sparseArray.append(next3.mFramePosition, constraintAttribute2);
                            }
                        }
                    }
                    makeSpline2 = ViewSpline.makeCustomSpline(next2, (SparseArray<ConstraintAttribute>) sparseArray);
                } else {
                    makeSpline2 = ViewSpline.makeSpline(next2);
                }
                if (makeSpline2 == null) {
                    c = 1;
                } else {
                    makeSpline2.setType(next2);
                    this.mAttributesMap.put(next2, makeSpline2);
                    c = 1;
                }
            }
            ArrayList<Key> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                Iterator<Key> it6 = arrayList3.iterator();
                while (it6.hasNext()) {
                    Key next4 = it6.next();
                    if (next4 instanceof KeyAttributes) {
                        next4.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str2 : this.mAttributesMap.keySet()) {
                int i = 0;
                if (hashMap.containsKey(str2) && (num = hashMap.get(str2)) != null) {
                    i = num.intValue();
                }
                ViewSpline viewSpline = this.mAttributesMap.get(str2);
                if (viewSpline != null) {
                    viewSpline.setup(i);
                }
            }
        }
        if (!hashSet4.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            Iterator<String> it7 = hashSet4.iterator();
            while (it7.hasNext()) {
                String next5 = it7.next();
                if (!this.mTimeCycleAttributesMap.containsKey(next5)) {
                    if (next5.startsWith("CUSTOM,")) {
                        SparseArray sparseArray2 = new SparseArray();
                        String str3 = next5.split(",")[1];
                        Iterator<Key> it8 = this.mKeyList.iterator();
                        while (it8.hasNext()) {
                            Key next6 = it8.next();
                            if (next6.mCustomConstraints != null) {
                                ConstraintAttribute constraintAttribute3 = next6.mCustomConstraints.get(str3);
                                if (constraintAttribute3 != null) {
                                    it2 = it7;
                                    sparseArray2.append(next6.mFramePosition, constraintAttribute3);
                                } else {
                                    it2 = it7;
                                }
                                it7 = it2;
                            }
                        }
                        it = it7;
                        makeSpline = ViewTimeCycle.makeCustomSpline(next5, sparseArray2);
                    } else {
                        it = it7;
                        makeSpline = ViewTimeCycle.makeSpline(next5, currentTime);
                    }
                    if (makeSpline == null) {
                        it7 = it;
                    } else {
                        makeSpline.setType(next5);
                        this.mTimeCycleAttributesMap.put(next5, makeSpline);
                        it7 = it;
                    }
                }
            }
            ArrayList<Key> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                Iterator<Key> it9 = arrayList4.iterator();
                while (it9.hasNext()) {
                    Key next7 = it9.next();
                    if (next7 instanceof KeyTimeCycle) {
                        ((KeyTimeCycle) next7).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str4 : this.mTimeCycleAttributesMap.keySet()) {
                int i2 = 0;
                if (hashMap.containsKey(str4)) {
                    i2 = hashMap.get(str4).intValue();
                }
                this.mTimeCycleAttributesMap.get(str4).setup(i2);
            }
        }
        MotionPaths[] motionPathsArr = new MotionPaths[this.mMotionPaths.size() + 2];
        int i3 = 1;
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[motionPathsArr.length - 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it10 = this.mMotionPaths.iterator();
        while (it10.hasNext()) {
            motionPathsArr[i3] = it10.next();
            i3++;
        }
        char c2 = 18;
        HashSet hashSet7 = new HashSet();
        for (String str5 : this.mEndMotionPath.attributes.keySet()) {
            if (this.mStartMotionPath.attributes.containsKey(str5) && !hashSet5.contains("CUSTOM," + str5)) {
                hashSet7.add(str5);
            }
        }
        String[] strArr = (String[]) hashSet7.toArray(new String[0]);
        this.mAttributeNames = strArr;
        this.mAttributeInterpolatorCount = new int[strArr.length];
        int i4 = 0;
        while (true) {
            String[] strArr2 = this.mAttributeNames;
            if (i4 >= strArr2.length) {
                break;
            }
            String str6 = strArr2[i4];
            this.mAttributeInterpolatorCount[i4] = 0;
            int i5 = 0;
            while (true) {
                if (i5 >= motionPathsArr.length) {
                    hashSet = hashSet4;
                    break;
                }
                if (motionPathsArr[i5].attributes.containsKey(str6) && (constraintAttribute = motionPathsArr[i5].attributes.get(str6)) != null) {
                    hashSet = hashSet4;
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i4] = iArr[i4] + constraintAttribute.numberOfInterpolatedValues();
                    break;
                }
                i5++;
                hashSet4 = hashSet4;
            }
            i4++;
            hashSet4 = hashSet;
        }
        boolean z = motionPathsArr[0].mPathMotionArc != Key.UNSET;
        boolean[] zArr2 = new boolean[this.mAttributeNames.length + 18];
        int i6 = 1;
        while (i6 < motionPathsArr.length) {
            motionPathsArr[i6].different(motionPathsArr[i6 - 1], zArr2, this.mAttributeNames, z);
            i6++;
            hashSet5 = hashSet5;
        }
        int i7 = 0;
        for (int i8 = 1; i8 < zArr2.length; i8++) {
            if (zArr2[i8]) {
                i7++;
            }
        }
        this.mInterpolateVariables = new int[i7];
        int max = Math.max(2, i7);
        this.mInterpolateData = new double[max];
        this.mInterpolateVelocity = new double[max];
        int i9 = 0;
        for (int i10 = 1; i10 < zArr2.length; i10++) {
            if (zArr2[i10]) {
                this.mInterpolateVariables[i9] = i10;
                i9++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, motionPathsArr.length, this.mInterpolateVariables.length);
        double[] dArr2 = new double[motionPathsArr.length];
        int i11 = 0;
        while (i11 < motionPathsArr.length) {
            motionPathsArr[i11].fillStandard(dArr[i11], this.mInterpolateVariables);
            dArr2[i11] = motionPathsArr[i11].time;
            i11++;
            arrayList = arrayList;
            i9 = i9;
            hashMap = hashMap;
        }
        int i12 = 0;
        while (true) {
            int[] iArr2 = this.mInterpolateVariables;
            if (i12 >= iArr2.length) {
                break;
            }
            if (iArr2[i12] < MotionPaths.names.length) {
                String str7 = MotionPaths.names[this.mInterpolateVariables[i12]] + " [";
                int i13 = 0;
                while (i13 < motionPathsArr.length) {
                    str7 = str7 + dArr[i13][i12];
                    i13++;
                    c2 = c2;
                    max = max;
                }
            }
            i12++;
            c2 = c2;
            max = max;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i14 = 0;
        while (true) {
            String[] strArr3 = this.mAttributeNames;
            if (i14 >= strArr3.length) {
                break;
            }
            int i15 = 0;
            double[][] dArr3 = null;
            double[] dArr4 = null;
            String str8 = strArr3[i14];
            int i16 = 0;
            while (true) {
                zArr = zArr2;
                if (i16 < motionPathsArr.length) {
                    if (motionPathsArr[i16].hasCustomData(str8)) {
                        if (dArr3 == null) {
                            double[] dArr5 = new double[motionPathsArr.length];
                            dArr3 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, motionPathsArr.length, motionPathsArr[i16].getCustomDataCount(str8));
                            dArr4 = dArr5;
                        }
                        dArr4[i15] = motionPathsArr[i16].time;
                        motionPathsArr[i16].getCustomData(str8, dArr3[i15], 0);
                        i15++;
                    }
                    i16++;
                    zArr2 = zArr;
                }
            }
            this.mSpline[i14 + 1] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr4, i15), (double[][]) Arrays.copyOf(dArr3, i15));
            i14++;
            zArr2 = zArr;
        }
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr[0].mPathMotionArc != Key.UNSET) {
            int length = motionPathsArr.length;
            int[] iArr3 = new int[length];
            double[] dArr6 = new double[length];
            double[][] dArr7 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 2);
            for (int i17 = 0; i17 < length; i17++) {
                iArr3[i17] = motionPathsArr[i17].mPathMotionArc;
                dArr6[i17] = motionPathsArr[i17].time;
                dArr7[i17][0] = motionPathsArr[i17].x;
                dArr7[i17][1] = motionPathsArr[i17].y;
            }
            this.mArcSpline = CurveFit.getArc(iArr3, dArr6, dArr7);
        }
        float f = Float.NaN;
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            Iterator<String> it11 = hashSet6.iterator();
            while (it11.hasNext()) {
                String next8 = it11.next();
                ViewOscillator makeSpline3 = ViewOscillator.makeSpline(next8);
                if (makeSpline3 != null) {
                    if (makeSpline3.variesByPath() && Float.isNaN(f)) {
                        f = getPreCycleDistance();
                    }
                    makeSpline3.setType(next8);
                    this.mCycleMap.put(next8, makeSpline3);
                }
            }
            Iterator<Key> it12 = this.mKeyList.iterator();
            while (it12.hasNext()) {
                Key next9 = it12.next();
                if (next9 instanceof KeyCycle) {
                    ((KeyCycle) next9).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<ViewOscillator> it13 = this.mCycleMap.values().iterator();
            while (it13.hasNext()) {
                it13.next().setup(f);
            }
        }
    }

    public void setupRelative(MotionController motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.x + " y: " + this.mStartMotionPath.y + " end: x: " + this.mEndMotionPath.x + " y: " + this.mEndMotionPath.y;
    }
}
