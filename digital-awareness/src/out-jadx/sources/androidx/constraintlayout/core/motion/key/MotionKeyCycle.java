package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MotionKeyCycle extends MotionKey {
    public static final int KEY_TYPE = 4;
    static final String NAME = "KeyCycle";
    public static final int SHAPE_BOUNCE = 6;
    public static final int SHAPE_COS_WAVE = 5;
    public static final int SHAPE_REVERSE_SAW_WAVE = 4;
    public static final int SHAPE_SAW_WAVE = 3;
    public static final int SHAPE_SIN_WAVE = 0;
    public static final int SHAPE_SQUARE_WAVE = 1;
    public static final int SHAPE_TRIANGLE_WAVE = 2;
    private static final String TAG = "KeyCycle";
    public static final String WAVE_OFFSET = "waveOffset";
    public static final String WAVE_PERIOD = "wavePeriod";
    public static final String WAVE_PHASE = "wavePhase";
    public static final String WAVE_SHAPE = "waveShape";
    private String mTransitionEasing = null;
    private int mCurveFit = 0;
    private int mWaveShape = -1;
    private String mCustomWaveShape = null;
    private float mWavePeriod = Float.NaN;
    private float mWaveOffset = 0.0f;
    private float mWavePhase = 0.0f;
    private float mProgress = Float.NaN;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;

    public MotionKeyCycle() {
        this.mType = 4;
        this.mCustom = new HashMap<>();
    }

    public void addCycleValues(HashMap<String, KeyCycleOscillator> hashMap) {
        KeyCycleOscillator keyCycleOscillator;
        KeyCycleOscillator keyCycleOscillator2;
        for (String str : hashMap.keySet()) {
            if (str.startsWith("CUSTOM")) {
                CustomVariable customVariable = this.mCustom.get(str.substring("CUSTOM".length() + 1));
                if (customVariable != null && customVariable.getType() == 901 && (keyCycleOscillator = hashMap.get(str)) != null) {
                    keyCycleOscillator.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, -1, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, customVariable.getValueToInterpolate(), customVariable);
                }
            } else {
                float value = getValue(str);
                if (!Float.isNaN(value) && (keyCycleOscillator2 = hashMap.get(str)) != null) {
                    keyCycleOscillator2.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, -1, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, value);
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /* renamed from: clone */
    public MotionKey mo6clone() {
        return null;
    }

    public void dump() {
        System.out.println("MotionKeyCycle{mWaveShape=" + this.mWaveShape + ", mWavePeriod=" + this.mWavePeriod + ", mWaveOffset=" + this.mWaveOffset + ", mWavePhase=" + this.mWavePhase + ", mRotation=" + this.mRotation + '}');
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.mAlpha)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            hashSet.add("rotationZ");
        }
        if (!Float.isNaN(this.mRotationX)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashSet.add("pathRotate");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            hashSet.add("translationZ");
        }
        if (this.mCustom.size() > 0) {
            Iterator<String> it = this.mCustom.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + it.next());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String name) {
        char c;
        switch (name.hashCode()) {
            case -1581616630:
                if (name.equals(TypedValues.CycleType.S_CUSTOM_WAVE_SHAPE)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1310311125:
                if (name.equals("easing")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1249320806:
                if (name.equals("rotationX")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1249320805:
                if (name.equals("rotationY")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1249320804:
                if (name.equals("rotationZ")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1225497657:
                if (name.equals("translationX")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1225497656:
                if (name.equals("translationY")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1225497655:
                if (name.equals("translationZ")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1019779949:
                if (name.equals(TypedValues.CycleType.S_WAVE_OFFSET)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1001078227:
                if (name.equals("progress")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -991726143:
                if (name.equals(TypedValues.CycleType.S_WAVE_PERIOD)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -987906986:
                if (name.equals("pivotX")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -987906985:
                if (name.equals("pivotY")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -908189618:
                if (name.equals("scaleX")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -908189617:
                if (name.equals("scaleY")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 92909918:
                if (name.equals("alpha")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 106629499:
                if (name.equals(TypedValues.CycleType.S_WAVE_PHASE)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 579057826:
                if (name.equals("curveFit")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 803192288:
                if (name.equals("pathRotate")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1532805160:
                if (name.equals("waveShape")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 1941332754:
                if (name.equals("visibility")) {
                    c = 1;
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
                return TypedValues.CycleType.TYPE_CURVE_FIT;
            case 1:
                return TypedValues.CycleType.TYPE_VISIBILITY;
            case 2:
                return TypedValues.CycleType.TYPE_ALPHA;
            case 3:
                return 304;
            case 4:
                return 305;
            case 5:
                return 306;
            case 6:
                return 308;
            case 7:
                return 309;
            case '\b':
                return 310;
            case '\t':
                return 311;
            case '\n':
                return 312;
            case 11:
                return 313;
            case '\f':
                return 314;
            case '\r':
                return 315;
            case 14:
                return TypedValues.CycleType.TYPE_PATH_ROTATE;
            case 15:
                return TypedValues.CycleType.TYPE_EASING;
            case 16:
                return TypedValues.CycleType.TYPE_WAVE_PERIOD;
            case 17:
                return 421;
            case 18:
                return TypedValues.CycleType.TYPE_WAVE_PHASE;
            case 19:
                return TypedValues.CycleType.TYPE_WAVE_OFFSET;
            case 20:
                return TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE;
            default:
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public float getValue(String key) {
        char c;
        switch (key.hashCode()) {
            case -1249320806:
                if (key.equals("rotationX")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1249320805:
                if (key.equals("rotationY")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1249320804:
                if (key.equals("rotationZ")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1225497657:
                if (key.equals("translationX")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1225497656:
                if (key.equals("translationY")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1225497655:
                if (key.equals("translationZ")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1019779949:
                if (key.equals(TypedValues.CycleType.S_WAVE_OFFSET)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1001078227:
                if (key.equals("progress")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -908189618:
                if (key.equals("scaleX")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -908189617:
                if (key.equals("scaleY")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -4379043:
                if (key.equals("elevation")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 92909918:
                if (key.equals("alpha")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 106629499:
                if (key.equals(TypedValues.CycleType.S_WAVE_PHASE)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 803192288:
                if (key.equals("pathRotate")) {
                    c = 5;
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
                return this.mAlpha;
            case 1:
                return this.mElevation;
            case 2:
                return this.mRotation;
            case 3:
                return this.mRotationX;
            case 4:
                return this.mRotationY;
            case 5:
                return this.mTransitionPathRotate;
            case 6:
                return this.mScaleX;
            case 7:
                return this.mScaleY;
            case '\b':
                return this.mTranslationX;
            case '\t':
                return this.mTranslationY;
            case '\n':
                return this.mTranslationZ;
            case 11:
                return this.mWaveOffset;
            case '\f':
                return this.mWavePhase;
            case '\r':
                return this.mProgress;
            default:
                return Float.NaN;
        }
    }

    public void printAttributes() {
        HashSet<String> hashSet = new HashSet<>();
        getAttributeNames(hashSet);
        Utils.log(" ------------- " + this.mFramePosition + " -------------");
        Utils.log("MotionKeyCycle{Shape=" + this.mWaveShape + ", Period=" + this.mWavePeriod + ", Offset=" + this.mWaveOffset + ", Phase=" + this.mWavePhase + '}');
        String[] strArr = (String[]) hashSet.toArray(new String[0]);
        for (int i = 0; i < strArr.length; i++) {
            TypedValues.AttributesType.getId(strArr[i]);
            Utils.log(strArr[i] + ":" + getValue(strArr[i]));
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, float value) {
        switch (type) {
            case 304:
                this.mTranslationX = value;
                return true;
            case 305:
                this.mTranslationY = value;
                return true;
            case 306:
                this.mTranslationZ = value;
                return true;
            case 307:
                this.mElevation = value;
                return true;
            case 308:
                this.mRotationX = value;
                return true;
            case 309:
                this.mRotationY = value;
                return true;
            case 310:
                this.mRotation = value;
                return true;
            case 311:
                this.mScaleX = value;
                return true;
            case 312:
                this.mScaleY = value;
                return true;
            case 315:
                this.mProgress = value;
                return true;
            case TypedValues.CycleType.TYPE_ALPHA /* 403 */:
                this.mAlpha = value;
                return true;
            case TypedValues.CycleType.TYPE_PATH_ROTATE /* 416 */:
                this.mTransitionPathRotate = value;
                return true;
            case TypedValues.CycleType.TYPE_WAVE_PERIOD /* 423 */:
                this.mWavePeriod = value;
                return true;
            case TypedValues.CycleType.TYPE_WAVE_OFFSET /* 424 */:
                this.mWaveOffset = value;
                return true;
            case TypedValues.CycleType.TYPE_WAVE_PHASE /* 425 */:
                this.mWavePhase = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, int value) {
        switch (type) {
            case TypedValues.CycleType.TYPE_CURVE_FIT /* 401 */:
                this.mCurveFit = value;
                return true;
            case 421:
                this.mWaveShape = value;
                return true;
            default:
                if (setValue(type, value)) {
                    return true;
                }
                return super.setValue(type, value);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, String value) {
        switch (type) {
            case TypedValues.CycleType.TYPE_EASING /* 420 */:
                this.mTransitionEasing = value;
                return true;
            case 421:
            default:
                return super.setValue(type, value);
            case TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE /* 422 */:
                this.mCustomWaveShape = value;
                return true;
        }
    }
}