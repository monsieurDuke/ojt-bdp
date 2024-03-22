package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes.dex */
public class MonotonicCurveFit extends CurveFit {
    private static final String TAG = "MonotonicCurveFit";
    private boolean mExtrapolate = true;
    double[] mSlopeTemp;
    private double[] mT;
    private double[][] mTangent;
    private double[][] mY;

    public MonotonicCurveFit(double[] time, double[][] y) {
        int length = time.length;
        int length2 = y[0].length;
        this.mSlopeTemp = new double[length2];
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length - 1, length2);
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, length2);
        for (int i = 0; i < length2; i++) {
            for (int i2 = 0; i2 < length - 1; i2++) {
                dArr[i2][i] = (y[i2 + 1][i] - y[i2][i]) / (time[i2 + 1] - time[i2]);
                if (i2 == 0) {
                    dArr2[i2][i] = dArr[i2][i];
                } else {
                    dArr2[i2][i] = (dArr[i2 - 1][i] + dArr[i2][i]) * 0.5d;
                }
            }
            dArr2[length - 1][i] = dArr[length - 2][i];
        }
        for (int i3 = 0; i3 < length - 1; i3++) {
            for (int i4 = 0; i4 < length2; i4++) {
                if (dArr[i3][i4] == 0.0d) {
                    dArr2[i3][i4] = 0.0d;
                    dArr2[i3 + 1][i4] = 0.0d;
                } else {
                    double d = dArr2[i3][i4] / dArr[i3][i4];
                    double d2 = dArr2[i3 + 1][i4] / dArr[i3][i4];
                    double hypot = Math.hypot(d, d2);
                    if (hypot > 9.0d) {
                        double d3 = 3.0d / hypot;
                        dArr2[i3][i4] = d3 * d * dArr[i3][i4];
                        dArr2[i3 + 1][i4] = d3 * d2 * dArr[i3][i4];
                    }
                }
            }
        }
        this.mT = time;
        this.mY = y;
        this.mTangent = dArr2;
    }

    public static MonotonicCurveFit buildWave(String configString) {
        double[] dArr = new double[configString.length() / 2];
        int indexOf = configString.indexOf(40) + 1;
        int indexOf2 = configString.indexOf(44, indexOf);
        int i = 0;
        while (indexOf2 != -1) {
            int i2 = i + 1;
            dArr[i] = Double.parseDouble(configString.substring(indexOf, indexOf2).trim());
            int i3 = indexOf2 + 1;
            indexOf = i3;
            indexOf2 = configString.indexOf(44, i3);
            i = i2;
        }
        dArr[i] = Double.parseDouble(configString.substring(indexOf, configString.indexOf(41, indexOf)).trim());
        return buildWave(Arrays.copyOf(dArr, i + 1));
    }

    private static MonotonicCurveFit buildWave(double[] values) {
        int length = (values.length * 3) - 2;
        int length2 = values.length - 1;
        double d = 1.0d / length2;
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
        double[] dArr2 = new double[length];
        for (int i = 0; i < values.length; i++) {
            double d2 = values[i];
            dArr[i + length2][0] = d2;
            dArr2[i + length2] = i * d;
            if (i > 0) {
                dArr[(length2 * 2) + i][0] = d2 + 1.0d;
                dArr2[(length2 * 2) + i] = (i * d) + 1.0d;
                dArr[i - 1][0] = (d2 - 1.0d) - d;
                dArr2[i - 1] = ((i * d) - 1.0d) - d;
            }
        }
        return new MonotonicCurveFit(dArr2, dArr);
    }

    private static double diff(double h, double x, double y1, double y2, double t1, double t2) {
        double d = x * x;
        return ((((((((((-6.0d) * d) * y2) + ((x * 6.0d) * y2)) + ((d * 6.0d) * y1)) - ((6.0d * x) * y1)) + (((h * 3.0d) * t2) * d)) + (((3.0d * h) * t1) * d)) - (((2.0d * h) * t2) * x)) - (((4.0d * h) * t1) * x)) + (h * t1);
    }

    private static double interpolate(double h, double x, double y1, double y2, double t1, double t2) {
        double d = x * x;
        double d2 = d * x;
        return (((((((((((-2.0d) * d2) * y2) + ((d * 3.0d) * y2)) + ((d2 * 2.0d) * y1)) - ((3.0d * d) * y1)) + y1) + ((h * t2) * d2)) + ((h * t1) * d2)) - ((h * t2) * d)) - (((h * 2.0d) * t1) * d)) + (h * t1 * x);
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getPos(double t, int j) {
        double[] dArr = this.mT;
        int length = dArr.length;
        if (this.mExtrapolate) {
            double d = dArr[0];
            if (t <= d) {
                return this.mY[0][j] + ((t - d) * getSlope(d, j));
            }
            if (t >= dArr[length - 1]) {
                return this.mY[length - 1][j] + ((t - dArr[length - 1]) * getSlope(dArr[length - 1], j));
            }
        } else {
            if (t <= dArr[0]) {
                return this.mY[0][j];
            }
            if (t >= dArr[length - 1]) {
                return this.mY[length - 1][j];
            }
        }
        for (int i = 0; i < length - 1; i++) {
            double[] dArr2 = this.mT;
            double d2 = dArr2[i];
            if (t == d2) {
                return this.mY[i][j];
            }
            if (t < dArr2[i + 1]) {
                double d3 = dArr2[i + 1] - d2;
                double[][] dArr3 = this.mY;
                double d4 = dArr3[i][j];
                double d5 = dArr3[i + 1][j];
                double[][] dArr4 = this.mTangent;
                return interpolate(d3, (t - d2) / d3, d4, d5, dArr4[i][j], dArr4[i + 1][j]);
            }
        }
        return 0.0d;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double t, double[] v) {
        double[] dArr = this.mT;
        int length = dArr.length;
        int length2 = this.mY[0].length;
        if (this.mExtrapolate) {
            double d = dArr[0];
            if (t <= d) {
                getSlope(d, this.mSlopeTemp);
                for (int i = 0; i < length2; i++) {
                    v[i] = this.mY[0][i] + ((t - this.mT[0]) * this.mSlopeTemp[i]);
                }
                return;
            }
            if (t >= dArr[length - 1]) {
                getSlope(dArr[length - 1], this.mSlopeTemp);
                for (int i2 = 0; i2 < length2; i2++) {
                    v[i2] = this.mY[length - 1][i2] + ((t - this.mT[length - 1]) * this.mSlopeTemp[i2]);
                }
                return;
            }
        } else {
            if (t <= dArr[0]) {
                for (int i3 = 0; i3 < length2; i3++) {
                    v[i3] = this.mY[0][i3];
                }
                return;
            }
            if (t >= dArr[length - 1]) {
                for (int i4 = 0; i4 < length2; i4++) {
                    v[i4] = this.mY[length - 1][i4];
                }
                return;
            }
        }
        for (int i5 = 0; i5 < length - 1; i5++) {
            if (t == this.mT[i5]) {
                for (int i6 = 0; i6 < length2; i6++) {
                    v[i6] = this.mY[i5][i6];
                }
            }
            double[] dArr2 = this.mT;
            if (t < dArr2[i5 + 1]) {
                double d2 = dArr2[i5 + 1];
                double d3 = dArr2[i5];
                double d4 = d2 - d3;
                double d5 = (t - d3) / d4;
                for (int i7 = 0; i7 < length2; i7++) {
                    double[][] dArr3 = this.mY;
                    double d6 = dArr3[i5][i7];
                    double d7 = dArr3[i5 + 1][i7];
                    double[][] dArr4 = this.mTangent;
                    v[i7] = interpolate(d4, d5, d6, d7, dArr4[i5][i7], dArr4[i5 + 1][i7]);
                }
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double t, float[] v) {
        double[] dArr = this.mT;
        int length = dArr.length;
        int length2 = this.mY[0].length;
        if (this.mExtrapolate) {
            double d = dArr[0];
            if (t <= d) {
                getSlope(d, this.mSlopeTemp);
                for (int i = 0; i < length2; i++) {
                    v[i] = (float) (this.mY[0][i] + ((t - this.mT[0]) * this.mSlopeTemp[i]));
                }
                return;
            }
            if (t >= dArr[length - 1]) {
                getSlope(dArr[length - 1], this.mSlopeTemp);
                for (int i2 = 0; i2 < length2; i2++) {
                    v[i2] = (float) (this.mY[length - 1][i2] + ((t - this.mT[length - 1]) * this.mSlopeTemp[i2]));
                }
                return;
            }
        } else {
            if (t <= dArr[0]) {
                for (int i3 = 0; i3 < length2; i3++) {
                    v[i3] = (float) this.mY[0][i3];
                }
                return;
            }
            if (t >= dArr[length - 1]) {
                for (int i4 = 0; i4 < length2; i4++) {
                    v[i4] = (float) this.mY[length - 1][i4];
                }
                return;
            }
        }
        for (int i5 = 0; i5 < length - 1; i5++) {
            if (t == this.mT[i5]) {
                for (int i6 = 0; i6 < length2; i6++) {
                    v[i6] = (float) this.mY[i5][i6];
                }
            }
            double[] dArr2 = this.mT;
            if (t < dArr2[i5 + 1]) {
                double d2 = dArr2[i5 + 1];
                double d3 = dArr2[i5];
                double d4 = d2 - d3;
                double d5 = (t - d3) / d4;
                for (int i7 = 0; i7 < length2; i7++) {
                    double[][] dArr3 = this.mY;
                    double d6 = dArr3[i5][i7];
                    double d7 = dArr3[i5 + 1][i7];
                    double[][] dArr4 = this.mTangent;
                    v[i7] = (float) interpolate(d4, d5, d6, d7, dArr4[i5][i7], dArr4[i5 + 1][i7]);
                }
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getSlope(double t, int j) {
        double[] dArr = this.mT;
        int length = dArr.length;
        double d = t < dArr[0] ? dArr[0] : t >= dArr[length + (-1)] ? dArr[length - 1] : t;
        for (int i = 0; i < length - 1; i++) {
            double[] dArr2 = this.mT;
            if (d <= dArr2[i + 1]) {
                double d2 = dArr2[i + 1];
                double d3 = dArr2[i];
                double d4 = d2 - d3;
                double[][] dArr3 = this.mY;
                double d5 = dArr3[i][j];
                double d6 = dArr3[i + 1][j];
                double[][] dArr4 = this.mTangent;
                return diff(d4, (d - d3) / d4, d5, d6, dArr4[i][j], dArr4[i + 1][j]) / d4;
            }
        }
        return 0.0d;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getSlope(double t, double[] v) {
        double[] dArr = this.mT;
        int length = dArr.length;
        int length2 = this.mY[0].length;
        double d = t <= dArr[0] ? dArr[0] : t >= dArr[length + (-1)] ? dArr[length - 1] : t;
        for (int i = 0; i < length - 1; i++) {
            double[] dArr2 = this.mT;
            if (d <= dArr2[i + 1]) {
                double d2 = dArr2[i + 1];
                double d3 = dArr2[i];
                double d4 = d2 - d3;
                double d5 = (d - d3) / d4;
                for (int i2 = 0; i2 < length2; i2++) {
                    double[][] dArr3 = this.mY;
                    double d6 = dArr3[i][i2];
                    double d7 = dArr3[i + 1][i2];
                    double[][] dArr4 = this.mTangent;
                    v[i2] = diff(d4, d5, d6, d7, dArr4[i][i2], dArr4[i + 1][i2]) / d4;
                }
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.mT;
    }
}
