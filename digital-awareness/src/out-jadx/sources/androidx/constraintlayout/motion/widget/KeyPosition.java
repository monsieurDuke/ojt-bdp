package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* loaded from: classes.dex */
public class KeyPosition extends KeyPositionBase {
    public static final String DRAWPATH = "drawPath";
    static final int KEY_TYPE = 2;
    static final String NAME = "KeyPosition";
    public static final String PERCENT_HEIGHT = "percentHeight";
    public static final String PERCENT_WIDTH = "percentWidth";
    public static final String PERCENT_X = "percentX";
    public static final String PERCENT_Y = "percentY";
    public static final String SIZE_PERCENT = "sizePercent";
    private static final String TAG = "KeyPosition";
    public static final String TRANSITION_EASING = "transitionEasing";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    String mTransitionEasing = null;
    int mPathMotionArc = UNSET;
    int mDrawPath = 0;
    float mPercentWidth = Float.NaN;
    float mPercentHeight = Float.NaN;
    float mPercentX = Float.NaN;
    float mPercentY = Float.NaN;
    float mAltPercentX = Float.NaN;
    float mAltPercentY = Float.NaN;
    int mPositionType = 0;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;

    /* compiled from: 001C.java */
    /* loaded from: classes.dex */
    private static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        private Loader() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        public static void read(KeyPosition c, TypedArray a) {
            int indexCount = a.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = a.getIndex(i);
                switch (mAttrMap.get(index)) {
                    case 1:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            c.mTargetId = a.getResourceId(index, c.mTargetId);
                            if (c.mTargetId == -1) {
                                c.mTargetString = a.getString(index);
                                break;
                            } else {
                                break;
                            }
                        } else if (a.peekValue(index).type == 3) {
                            c.mTargetString = a.getString(index);
                            break;
                        } else {
                            c.mTargetId = a.getResourceId(index, c.mTargetId);
                            break;
                        }
                    case 2:
                        c.mFramePosition = a.getInt(index, c.mFramePosition);
                        break;
                    case 3:
                        if (a.peekValue(index).type == 3) {
                            c.mTransitionEasing = a.getString(index);
                            break;
                        } else {
                            c.mTransitionEasing = Easing.NAMED_EASING[a.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        c.mCurveFit = a.getInteger(index, c.mCurveFit);
                        break;
                    case 5:
                        c.mDrawPath = a.getInt(index, c.mDrawPath);
                        break;
                    case 6:
                        c.mPercentX = a.getFloat(index, c.mPercentX);
                        break;
                    case 7:
                        c.mPercentY = a.getFloat(index, c.mPercentY);
                        break;
                    case 8:
                        float f = a.getFloat(index, c.mPercentHeight);
                        c.mPercentWidth = f;
                        c.mPercentHeight = f;
                        break;
                    case 9:
                        c.mPositionType = a.getInt(index, c.mPositionType);
                        break;
                    case 10:
                        c.mPathMotionArc = a.getInt(index, c.mPathMotionArc);
                        break;
                    case 11:
                        c.mPercentWidth = a.getFloat(index, c.mPercentWidth);
                        break;
                    case 12:
                        c.mPercentHeight = a.getFloat(index, c.mPercentHeight);
                        break;
                    default:
                        StringBuilder append = new StringBuilder().append("unused attribute 0x");
                        String hexString = Integer.toHexString(index);
                        Log5ECF72.a(hexString);
                        LogE84000.a(hexString);
                        Log229316.a(hexString);
                        Log.e(TypedValues.PositionType.NAME, append.append(hexString).append("   ").append(mAttrMap.get(index)).toString());
                        break;
                }
            }
            if (c.mFramePosition == -1) {
                Log.e(TypedValues.PositionType.NAME, "no frame position");
            }
        }
    }

    public KeyPosition() {
        this.mType = 2;
    }

    private void calcCartesianPosition(float start_x, float start_y, float end_x, float end_y) {
        float f = end_x - start_x;
        float f2 = end_y - start_y;
        float f3 = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float f4 = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float f5 = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        this.mCalculatedPositionX = (int) ((f * f3) + start_x + (f2 * (Float.isNaN(this.mAltPercentX) ? 0.0f : this.mAltPercentX)));
        this.mCalculatedPositionY = (int) ((f * f4) + start_y + (f2 * f5));
    }

    private void calcPathPosition(float start_x, float start_y, float end_x, float end_y) {
        float f = end_x - start_x;
        float f2 = end_y - start_y;
        float f3 = this.mPercentX;
        float f4 = this.mPercentY;
        this.mCalculatedPositionX = (f * f3) + start_x + ((-f2) * f4);
        this.mCalculatedPositionY = (f3 * f2) + start_y + (f4 * f);
    }

    private void calcScreenPosition(int layoutWidth, int layoutHeight) {
        float f = this.mPercentX;
        this.mCalculatedPositionX = ((layoutWidth - 0) * f) + (0 / 2);
        this.mCalculatedPositionY = ((layoutHeight - 0) * f) + (0 / 2);
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, ViewSpline> splines) {
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    void calcPosition(int layoutWidth, int layoutHeight, float start_x, float start_y, float end_x, float end_y) {
        switch (this.mPositionType) {
            case 1:
                calcPathPosition(start_x, start_y, end_x, end_y);
                return;
            case 2:
                calcScreenPosition(layoutWidth, layoutHeight);
                return;
            default:
                calcCartesianPosition(start_x, start_y, end_x, end_y);
                return;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public Key mo8clone() {
        return new KeyPosition().copy(this);
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public Key copy(Key src) {
        super.copy(src);
        KeyPosition keyPosition = (KeyPosition) src;
        this.mTransitionEasing = keyPosition.mTransitionEasing;
        this.mPathMotionArc = keyPosition.mPathMotionArc;
        this.mDrawPath = keyPosition.mDrawPath;
        this.mPercentWidth = keyPosition.mPercentWidth;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = keyPosition.mPercentX;
        this.mPercentY = keyPosition.mPercentY;
        this.mAltPercentX = keyPosition.mAltPercentX;
        this.mAltPercentY = keyPosition.mAltPercentY;
        this.mCalculatedPositionX = keyPosition.mCalculatedPositionX;
        this.mCalculatedPositionY = keyPosition.mCalculatedPositionY;
        return this;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    float getPositionX() {
        return this.mCalculatedPositionX;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    float getPositionY() {
        return this.mCalculatedPositionY;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public boolean intersects(int layoutWidth, int layoutHeight, RectF start, RectF end, float x, float y) {
        calcPosition(layoutWidth, layoutHeight, start.centerX(), start.centerY(), end.centerX(), end.centerY());
        return Math.abs(x - this.mCalculatedPositionX) < 20.0f && Math.abs(y - this.mCalculatedPositionY) < 20.0f;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attrs) {
        Loader.read(this, context.obtainStyledAttributes(attrs, R.styleable.KeyPosition));
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public void positionAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        switch (this.mPositionType) {
            case 1:
                positionPathAttributes(start, end, x, y, attribute, value);
                return;
            case 2:
                positionScreenAttributes(view, start, end, x, y, attribute, value);
                return;
            default:
                positionCartAttributes(start, end, x, y, attribute, value);
                return;
        }
    }

    void positionCartAttributes(RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float centerX = start.centerX();
        float centerY = start.centerY();
        float centerX2 = end.centerX() - centerX;
        float centerY2 = end.centerY() - centerY;
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            value[0] = (x - centerX) / centerX2;
            attribute[1] = "percentY";
            value[1] = (y - centerY) / centerY2;
            return;
        }
        if ("percentX".equals(attribute[0])) {
            value[0] = (x - centerX) / centerX2;
            value[1] = (y - centerY) / centerY2;
        } else {
            value[1] = (x - centerX) / centerX2;
            value[0] = (y - centerY) / centerY2;
        }
    }

    void positionPathAttributes(RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float centerX = start.centerX();
        float centerY = start.centerY();
        float centerX2 = end.centerX() - centerX;
        float centerY2 = end.centerY() - centerY;
        float hypot = (float) Math.hypot(centerX2, centerY2);
        if (hypot < 1.0E-4d) {
            System.out.println("distance ~ 0");
            value[0] = 0.0f;
            value[1] = 0.0f;
            return;
        }
        float f = centerX2 / hypot;
        float f2 = centerY2 / hypot;
        float f3 = (((y - centerY) * f) - ((x - centerX) * f2)) / hypot;
        float f4 = (((x - centerX) * f) + ((y - centerY) * f2)) / hypot;
        if (attribute[0] != null) {
            if ("percentX".equals(attribute[0])) {
                value[0] = f4;
                value[1] = f3;
                return;
            }
            return;
        }
        attribute[0] = "percentX";
        attribute[1] = "percentY";
        value[0] = f4;
        value[1] = f3;
    }

    void positionScreenAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float centerX = start.centerX();
        float centerY = start.centerY();
        float centerX2 = end.centerX() - centerX;
        float centerY2 = end.centerY() - centerY;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            value[0] = x / width;
            attribute[1] = "percentY";
            value[1] = y / height;
            return;
        }
        if ("percentX".equals(attribute[0])) {
            value[0] = x / width;
            value[1] = y / height;
        } else {
            value[1] = x / width;
            value[0] = y / height;
        }
    }

    public void setType(int type) {
        this.mPositionType = type;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String tag, Object value) {
        char c;
        switch (tag.hashCode()) {
            case -1812823328:
                if (tag.equals("transitionEasing")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1127236479:
                if (tag.equals("percentWidth")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1017587252:
                if (tag.equals("percentHeight")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -827014263:
                if (tag.equals("drawPath")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -200259324:
                if (tag.equals("sizePercent")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 428090547:
                if (tag.equals("percentX")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 428090548:
                if (tag.equals("percentY")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTransitionEasing = value.toString();
                return;
            case 1:
                this.mDrawPath = toInt(value);
                return;
            case 2:
                this.mPercentWidth = toFloat(value);
                return;
            case 3:
                this.mPercentHeight = toFloat(value);
                return;
            case 4:
                float f = toFloat(value);
                this.mPercentWidth = f;
                this.mPercentHeight = f;
                return;
            case 5:
                this.mPercentX = toFloat(value);
                return;
            case 6:
                this.mPercentY = toFloat(value);
                return;
            default:
                return;
        }
    }
}