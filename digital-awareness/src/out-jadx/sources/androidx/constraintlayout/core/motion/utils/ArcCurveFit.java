package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class ArcCurveFit extends CurveFit {
    public static final int ARC_START_FLIP = 3;
    public static final int ARC_START_HORIZONTAL = 2;
    public static final int ARC_START_LINEAR = 0;
    public static final int ARC_START_VERTICAL = 1;
    private static final int START_HORIZONTAL = 2;
    private static final int START_LINEAR = 3;
    private static final int START_VERTICAL = 1;
    Arc[] mArcs;
    private boolean mExtrapolate = true;
    private final double[] mTime;

    /* loaded from: classes.dex */
    private static class Arc {
        private static final double EPSILON = 0.001d;
        private static final String TAG = "Arc";
        private static double[] ourPercent = new double[91];
        boolean linear;
        double mArcDistance;
        double mArcVelocity;
        double mEllipseA;
        double mEllipseB;
        double mEllipseCenterX;
        double mEllipseCenterY;
        double[] mLut;
        double mOneOverDeltaTime;
        double mTime1;
        double mTime2;
        double mTmpCosAngle;
        double mTmpSinAngle;
        boolean mVertical;
        double mX1;
        double mX2;
        double mY1;
        double mY2;

        Arc(int mode, double t1, double t2, double x1, double y1, double x2, double y2) {
            double d;
            double d2;
            double d3;
            double d4;
            double d5;
            this.linear = false;
            this.mVertical = mode == 1;
            this.mTime1 = t1;
            this.mTime2 = t2;
            this.mOneOverDeltaTime = 1.0d / (t2 - t1);
            if (3 == mode) {
                this.linear = true;
            }
            double d6 = x2 - x1;
            double d7 = y2 - y1;
            if (this.linear || Math.abs(d6) < EPSILON) {
                d = d6;
                d2 = d7;
                d3 = x2;
                d4 = y1;
                d5 = x1;
            } else {
                if (Math.abs(d7) >= EPSILON) {
                    this.mLut = new double[101];
                    boolean z = this.mVertical;
                    this.mEllipseA = (z ? -1 : 1) * d6;
                    this.mEllipseB = (z ? 1 : -1) * d7;
                    this.mEllipseCenterX = z ? x2 : x1;
                    this.mEllipseCenterY = z ? y1 : y2;
                    buildTable(x1, y1, x2, y2);
                    this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
                    return;
                }
                d = d6;
                d2 = d7;
                d3 = x2;
                d4 = y1;
                d5 = x1;
            }
            this.linear = true;
            this.mX1 = d5;
            this.mX2 = d3;
            this.mY1 = d4;
            this.mY2 = y2;
            double d8 = d2;
            double d9 = d;
            double hypot = Math.hypot(d8, d9);
            this.mArcDistance = hypot;
            this.mArcVelocity = hypot * this.mOneOverDeltaTime;
            double d10 = this.mTime2;
            double d11 = this.mTime1;
            this.mEllipseCenterX = d9 / (d10 - d11);
            this.mEllipseCenterY = d8 / (d10 - d11);
        }

        private void buildTable(double x1, double y1, double x2, double y2) {
            double d;
            double d2;
            double d3 = x2 - x1;
            double d4 = y1 - y2;
            double d5 = 0.0d;
            double d6 = 0.0d;
            double d7 = 0.0d;
            int i = 0;
            while (true) {
                if (i >= ourPercent.length) {
                    break;
                }
                double d8 = d7;
                double radians = Math.toRadians((i * 90.0d) / ((double) (r12.length - 1)));
                double sin = d3 * Math.sin(radians);
                double cos = d4 * Math.cos(radians);
                if (i > 0) {
                    d = d3;
                    d2 = d4;
                    double hypot = Math.hypot(sin - d5, cos - d6) + d8;
                    ourPercent[i] = hypot;
                    d8 = hypot;
                } else {
                    d = d3;
                    d2 = d4;
                }
                d5 = sin;
                d6 = cos;
                i++;
                d7 = d8;
                d3 = d;
                d4 = d2;
            }
            this.mArcDistance = d7;
            int i2 = 0;
            while (true) {
                double[] dArr = ourPercent;
                if (i2 >= dArr.length) {
                    break;
                }
                dArr[i2] = dArr[i2] / d7;
                i2++;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= this.mLut.length) {
                    return;
                }
                double length = i3 / ((double) (r2.length - 1));
                int binarySearch = Arrays.binarySearch(ourPercent, length);
                if (binarySearch >= 0) {
                    this.mLut[i3] = binarySearch / ((double) (ourPercent.length - 1));
                } else if (binarySearch == -1) {
                    this.mLut[i3] = 0.0d;
                } else {
                    int i4 = (-binarySearch) - 2;
                    double[] dArr2 = ourPercent;
                    double d9 = dArr2[i4];
                    this.mLut[i3] = (i4 + ((length - d9) / (dArr2[(-binarySearch) - 1] - d9))) / ((double) (dArr2.length - 1));
                }
                i3++;
            }
        }

        double getDX() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, (-this.mEllipseB) * this.mTmpSinAngle);
            return this.mVertical ? (-d) * hypot : d * hypot;
        }

        double getDY() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double d2 = (-this.mEllipseB) * this.mTmpSinAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, d2);
            return this.mVertical ? (-d2) * hypot : d2 * hypot;
        }

        public double getLinearDX(double t) {
            return this.mEllipseCenterX;
        }

        public double getLinearDY(double t) {
            return this.mEllipseCenterY;
        }

        public double getLinearX(double t) {
            double d = (t - this.mTime1) * this.mOneOverDeltaTime;
            double t2 = this.mX1;
            return t2 + ((this.mX2 - t2) * d);
        }

        public double getLinearY(double t) {
            double d = (t - this.mTime1) * this.mOneOverDeltaTime;
            double t2 = this.mY1;
            return t2 + ((this.mY2 - t2) * d);
        }

        double getX() {
            return this.mEllipseCenterX + (this.mEllipseA * this.mTmpSinAngle);
        }

        double getY() {
            return this.mEllipseCenterY + (this.mEllipseB * this.mTmpCosAngle);
        }

        double lookup(double v) {
            if (v <= 0.0d) {
                return 0.0d;
            }
            if (v >= 1.0d) {
                return 1.0d;
            }
            double[] dArr = this.mLut;
            double length = ((double) (dArr.length - 1)) * v;
            int i = (int) length;
            double d = dArr[i];
            return d + ((dArr[i + 1] - d) * (length - ((int) length)));
        }

        void setPoint(double time) {
            double lookup = lookup((this.mVertical ? this.mTime2 - time : time - this.mTime1) * this.mOneOverDeltaTime) * 1.5707963267948966d;
            this.mTmpSinAngle = Math.sin(lookup);
            this.mTmpCosAngle = Math.cos(lookup);
        }
    }

    public ArcCurveFit(int[] arcModes, double[] time, double[][] y) {
        this.mTime = time;
        this.mArcs = new Arc[time.length - 1];
        int i = 1;
        int i2 = 1;
        int i3 = 0;
        while (true) {
            Arc[] arcArr = this.mArcs;
            if (i3 >= arcArr.length) {
                return;
            }
            switch (arcModes[i3]) {
                case 0:
                    i = 3;
                    break;
                case 1:
                    i = 1;
                    i2 = 1;
                    break;
                case 2:
                    i = 2;
                    i2 = 2;
                    break;
                case 3:
                    i = i2 != 1 ? 1 : 2;
                    i2 = i;
                    break;
            }
            arcArr[i3] = new Arc(i, time[i3], time[i3 + 1], y[i3][0], y[i3][1], y[i3 + 1][0], y[i3 + 1][1]);
            i3++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getPos(double t, int j) {
        if (this.mExtrapolate) {
            if (t < this.mArcs[0].mTime1) {
                double d = this.mArcs[0].mTime1;
                double d2 = t - this.mArcs[0].mTime1;
                if (this.mArcs[0].linear) {
                    return j == 0 ? this.mArcs[0].getLinearX(d) + (this.mArcs[0].getLinearDX(d) * d2) : this.mArcs[0].getLinearY(d) + (this.mArcs[0].getLinearDY(d) * d2);
                }
                this.mArcs[0].setPoint(d);
                return j == 0 ? this.mArcs[0].getX() + (this.mArcs[0].getDX() * d2) : this.mArcs[0].getY() + (this.mArcs[0].getDY() * d2);
            }
            if (t > this.mArcs[r0.length - 1].mTime2) {
                double d3 = this.mArcs[r0.length - 1].mTime2;
                double d4 = t - d3;
                Arc[] arcArr = this.mArcs;
                int length = arcArr.length - 1;
                return j == 0 ? arcArr[length].getLinearX(d3) + (this.mArcs[length].getLinearDX(d3) * d4) : arcArr[length].getLinearY(d3) + (this.mArcs[length].getLinearDY(d3) * d4);
            }
        } else if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        } else {
            if (t > this.mArcs[r0.length - 1].mTime2) {
                t = this.mArcs[r0.length - 1].mTime2;
            }
        }
        int i = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return Double.NaN;
            }
            if (t <= arcArr2[i].mTime2) {
                if (this.mArcs[i].linear) {
                    return j == 0 ? this.mArcs[i].getLinearX(t) : this.mArcs[i].getLinearY(t);
                }
                this.mArcs[i].setPoint(t);
                return j == 0 ? this.mArcs[i].getX() : this.mArcs[i].getY();
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double t, double[] v) {
        if (!this.mExtrapolate) {
            if (t < this.mArcs[0].mTime1) {
                t = this.mArcs[0].mTime1;
            }
            Arc[] arcArr = this.mArcs;
            if (t > arcArr[arcArr.length - 1].mTime2) {
                Arc[] arcArr2 = this.mArcs;
                t = arcArr2[arcArr2.length - 1].mTime2;
            }
        } else {
            if (t < this.mArcs[0].mTime1) {
                double d = this.mArcs[0].mTime1;
                double d2 = t - this.mArcs[0].mTime1;
                if (this.mArcs[0].linear) {
                    v[0] = this.mArcs[0].getLinearX(d) + (this.mArcs[0].getLinearDX(d) * d2);
                    v[1] = this.mArcs[0].getLinearY(d) + (this.mArcs[0].getLinearDY(d) * d2);
                    return;
                } else {
                    this.mArcs[0].setPoint(d);
                    v[0] = this.mArcs[0].getX() + (this.mArcs[0].getDX() * d2);
                    v[1] = this.mArcs[0].getY() + (this.mArcs[0].getDY() * d2);
                    return;
                }
            }
            Arc[] arcArr3 = this.mArcs;
            if (t > arcArr3[arcArr3.length - 1].mTime2) {
                Arc[] arcArr4 = this.mArcs;
                double d3 = arcArr4[arcArr4.length - 1].mTime2;
                double d4 = t - d3;
                Arc[] arcArr5 = this.mArcs;
                int length = arcArr5.length - 1;
                if (arcArr5[length].linear) {
                    v[0] = this.mArcs[length].getLinearX(d3) + (this.mArcs[length].getLinearDX(d3) * d4);
                    v[1] = this.mArcs[length].getLinearY(d3) + (this.mArcs[length].getLinearDY(d3) * d4);
                    return;
                } else {
                    this.mArcs[length].setPoint(t);
                    v[0] = this.mArcs[length].getX() + (this.mArcs[length].getDX() * d4);
                    v[1] = this.mArcs[length].getY() + (this.mArcs[length].getDY() * d4);
                    return;
                }
            }
        }
        int i = 0;
        while (true) {
            Arc[] arcArr6 = this.mArcs;
            if (i >= arcArr6.length) {
                return;
            }
            if (t <= arcArr6[i].mTime2) {
                if (this.mArcs[i].linear) {
                    v[0] = this.mArcs[i].getLinearX(t);
                    v[1] = this.mArcs[i].getLinearY(t);
                    return;
                } else {
                    this.mArcs[i].setPoint(t);
                    v[0] = this.mArcs[i].getX();
                    v[1] = this.mArcs[i].getY();
                    return;
                }
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double t, float[] v) {
        if (this.mExtrapolate) {
            if (t < this.mArcs[0].mTime1) {
                double d = this.mArcs[0].mTime1;
                double d2 = t - this.mArcs[0].mTime1;
                if (this.mArcs[0].linear) {
                    v[0] = (float) (this.mArcs[0].getLinearX(d) + (this.mArcs[0].getLinearDX(d) * d2));
                    v[1] = (float) (this.mArcs[0].getLinearY(d) + (this.mArcs[0].getLinearDY(d) * d2));
                    return;
                } else {
                    this.mArcs[0].setPoint(d);
                    v[0] = (float) (this.mArcs[0].getX() + (this.mArcs[0].getDX() * d2));
                    v[1] = (float) (this.mArcs[0].getY() + (this.mArcs[0].getDY() * d2));
                    return;
                }
            }
            Arc[] arcArr = this.mArcs;
            if (t > arcArr[arcArr.length - 1].mTime2) {
                Arc[] arcArr2 = this.mArcs;
                double d3 = arcArr2[arcArr2.length - 1].mTime2;
                double d4 = t - d3;
                Arc[] arcArr3 = this.mArcs;
                int length = arcArr3.length - 1;
                if (arcArr3[length].linear) {
                    v[0] = (float) (this.mArcs[length].getLinearX(d3) + (this.mArcs[length].getLinearDX(d3) * d4));
                    v[1] = (float) (this.mArcs[length].getLinearY(d3) + (this.mArcs[length].getLinearDY(d3) * d4));
                    return;
                } else {
                    this.mArcs[length].setPoint(t);
                    v[0] = (float) this.mArcs[length].getX();
                    v[1] = (float) this.mArcs[length].getY();
                    return;
                }
            }
        } else if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        } else {
            Arc[] arcArr4 = this.mArcs;
            if (t > arcArr4[arcArr4.length - 1].mTime2) {
                Arc[] arcArr5 = this.mArcs;
                t = arcArr5[arcArr5.length - 1].mTime2;
            }
        }
        int i = 0;
        while (true) {
            Arc[] arcArr6 = this.mArcs;
            if (i >= arcArr6.length) {
                return;
            }
            if (t <= arcArr6[i].mTime2) {
                if (this.mArcs[i].linear) {
                    v[0] = (float) this.mArcs[i].getLinearX(t);
                    v[1] = (float) this.mArcs[i].getLinearY(t);
                    return;
                } else {
                    this.mArcs[i].setPoint(t);
                    v[0] = (float) this.mArcs[i].getX();
                    v[1] = (float) this.mArcs[i].getY();
                    return;
                }
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getSlope(double t, int j) {
        if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        }
        if (t > this.mArcs[r0.length - 1].mTime2) {
            t = this.mArcs[r0.length - 1].mTime2;
        }
        int i = 0;
        while (true) {
            Arc[] arcArr = this.mArcs;
            if (i >= arcArr.length) {
                return Double.NaN;
            }
            if (t <= arcArr[i].mTime2) {
                if (this.mArcs[i].linear) {
                    return j == 0 ? this.mArcs[i].getLinearDX(t) : this.mArcs[i].getLinearDY(t);
                }
                this.mArcs[i].setPoint(t);
                return j == 0 ? this.mArcs[i].getDX() : this.mArcs[i].getDY();
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getSlope(double t, double[] v) {
        if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        } else {
            Arc[] arcArr = this.mArcs;
            if (t > arcArr[arcArr.length - 1].mTime2) {
                Arc[] arcArr2 = this.mArcs;
                t = arcArr2[arcArr2.length - 1].mTime2;
            }
        }
        int i = 0;
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i >= arcArr3.length) {
                return;
            }
            if (t <= arcArr3[i].mTime2) {
                if (this.mArcs[i].linear) {
                    v[0] = this.mArcs[i].getLinearDX(t);
                    v[1] = this.mArcs[i].getLinearDY(t);
                    return;
                } else {
                    this.mArcs[i].setPoint(t);
                    v[0] = this.mArcs[i].getDX();
                    v[1] = this.mArcs[i].getDY();
                    return;
                }
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.mTime;
    }
}
