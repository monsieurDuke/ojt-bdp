package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.state.WidgetFrame;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class SplineSet {
    private static final String TAG = "SplineSet";
    private int count;
    protected CurveFit mCurveFit;
    private String mType;
    protected int[] mTimePoints = new int[10];
    protected float[] mValues = new float[10];

    /* loaded from: classes.dex */
    private static class CoreSpline extends SplineSet {
        long start;
        String type;

        public CoreSpline(String str, long currentTime) {
            this.type = str;
            this.start = currentTime;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setProperty(TypedValues widget, float t) {
            widget.setValue(widget.getId(this.type), get(t));
        }
    }

    /* loaded from: classes.dex */
    public static class CustomSet extends SplineSet {
        String mAttributeName;
        KeyFrameArray.CustomArray mConstraintAttributeList;
        float[] mTempValues;

        public CustomSet(String attribute, KeyFrameArray.CustomArray attrList) {
            this.mAttributeName = attribute.split(",")[1];
            this.mConstraintAttributeList = attrList;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setPoint(int position, float value) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
        }

        public void setPoint(int position, CustomAttribute value) {
            this.mConstraintAttributeList.append(position, value);
        }

        public void setProperty(WidgetFrame view, float t) {
            this.mCurveFit.getPos(t, this.mTempValues);
            view.setCustomValue(this.mConstraintAttributeList.valueAt(0), this.mTempValues);
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setup(int curveType) {
            int size = this.mConstraintAttributeList.size();
            int numberOfInterpolatedValues = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            this.mTempValues = new float[numberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, numberOfInterpolatedValues);
            for (int i = 0; i < size; i++) {
                int keyAt = this.mConstraintAttributeList.keyAt(i);
                CustomAttribute valueAt = this.mConstraintAttributeList.valueAt(i);
                dArr[i] = keyAt * 0.01d;
                valueAt.getValuesToInterpolate(this.mTempValues);
                int i2 = 0;
                while (true) {
                    if (i2 < this.mTempValues.length) {
                        dArr2[i][i2] = r8[i2];
                        i2++;
                    }
                }
            }
            this.mCurveFit = CurveFit.get(curveType, dArr, dArr2);
        }
    }

    /* loaded from: classes.dex */
    public static class CustomSpline extends SplineSet {
        String mAttributeName;
        KeyFrameArray.CustomVar mConstraintAttributeList;
        float[] mTempValues;

        public CustomSpline(String attribute, KeyFrameArray.CustomVar attrList) {
            this.mAttributeName = attribute.split(",")[1];
            this.mConstraintAttributeList = attrList;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setPoint(int position, float value) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
        }

        public void setPoint(int position, CustomVariable value) {
            this.mConstraintAttributeList.append(position, value);
        }

        public void setProperty(MotionWidget view, float t) {
            this.mCurveFit.getPos(t, this.mTempValues);
            this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(view, this.mTempValues);
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setProperty(TypedValues widget, float t) {
            setProperty((MotionWidget) widget, t);
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setup(int curveType) {
            int size = this.mConstraintAttributeList.size();
            int numberOfInterpolatedValues = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            this.mTempValues = new float[numberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, numberOfInterpolatedValues);
            for (int i = 0; i < size; i++) {
                int keyAt = this.mConstraintAttributeList.keyAt(i);
                CustomVariable valueAt = this.mConstraintAttributeList.valueAt(i);
                dArr[i] = keyAt * 0.01d;
                valueAt.getValuesToInterpolate(this.mTempValues);
                int i2 = 0;
                while (true) {
                    if (i2 < this.mTempValues.length) {
                        dArr2[i][i2] = r8[i2];
                        i2++;
                    }
                }
            }
            this.mCurveFit = CurveFit.get(curveType, dArr, dArr2);
        }
    }

    /* loaded from: classes.dex */
    private static class Sort {
        private Sort() {
        }

        static void doubleQuickSort(int[] key, float[] value, int low, int hi) {
            int[] iArr = new int[key.length + 10];
            int i = 0 + 1;
            iArr[0] = hi;
            int i2 = i + 1;
            iArr[i] = low;
            while (i2 > 0) {
                int i3 = i2 - 1;
                int low2 = iArr[i3];
                i2 = i3 - 1;
                int hi2 = iArr[i2];
                if (low2 < hi2) {
                    int partition = partition(key, value, low2, hi2);
                    int i4 = i2 + 1;
                    iArr[i2] = partition - 1;
                    int i5 = i4 + 1;
                    iArr[i4] = low2;
                    int i6 = i5 + 1;
                    iArr[i5] = hi2;
                    i2 = i6 + 1;
                    iArr[i6] = partition + 1;
                }
            }
        }

        private static int partition(int[] array, float[] value, int low, int hi) {
            int i = array[hi];
            int i2 = low;
            for (int i3 = low; i3 < hi; i3++) {
                if (array[i3] <= i) {
                    swap(array, value, i2, i3);
                    i2++;
                }
            }
            swap(array, value, i2, hi);
            return i2;
        }

        private static void swap(int[] array, float[] value, int a, int b) {
            int i = array[a];
            array[a] = array[b];
            array[b] = i;
            float f = value[a];
            value[a] = value[b];
            value[b] = f;
        }
    }

    public static SplineSet makeCustomSpline(String str, KeyFrameArray.CustomArray attrList) {
        return new CustomSet(str, attrList);
    }

    public static SplineSet makeCustomSplineSet(String str, KeyFrameArray.CustomVar attrList) {
        return new CustomSpline(str, attrList);
    }

    public static SplineSet makeSpline(String str, long currentTime) {
        return new CoreSpline(str, currentTime);
    }

    public float get(float t) {
        return (float) this.mCurveFit.getPos(t, 0);
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public float getSlope(float t) {
        return (float) this.mCurveFit.getSlope(t, 0);
    }

    public void setPoint(int position, float value) {
        int[] iArr = this.mTimePoints;
        if (iArr.length < this.count + 1) {
            this.mTimePoints = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.mValues;
            this.mValues = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.mTimePoints;
        int i = this.count;
        iArr2[i] = position;
        this.mValues[i] = value;
        this.count = i + 1;
    }

    public void setProperty(TypedValues widget, float t) {
        widget.setValue(TypedValues.AttributesType.getId(this.mType), get(t));
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setup(int curveType) {
        int i;
        int i2 = this.count;
        if (i2 == 0) {
            return;
        }
        Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, i2 - 1);
        int i3 = 1;
        for (int i4 = 1; i4 < this.count; i4++) {
            int[] iArr = this.mTimePoints;
            if (iArr[i4 - 1] != iArr[i4]) {
                i3++;
            }
        }
        double[] dArr = new double[i3];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i3, 1);
        int i5 = 0;
        while (i < this.count) {
            if (i > 0) {
                int[] iArr2 = this.mTimePoints;
                i = iArr2[i] == iArr2[i + (-1)] ? i + 1 : 0;
            }
            dArr[i5] = this.mTimePoints[i] * 0.01d;
            dArr2[i5][0] = this.mValues[i];
            i5++;
        }
        this.mCurveFit = CurveFit.get(curveType, dArr, dArr2);
    }

    public String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i = 0; i < this.count; i++) {
            str = str + "[" + this.mTimePoints[i] + " , " + decimalFormat.format(this.mValues[i]) + "] ";
        }
        return str;
    }
}
