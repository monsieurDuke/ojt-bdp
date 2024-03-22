package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class SpringStopEngine implements StopEngine {
    private static final double UNSET = Double.MAX_VALUE;
    private float mLastTime;
    private double mLastVelocity;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;
    private float mV;
    double mDamping = 0.5d;
    private boolean mInitialized = false;
    private int mBoundaryMode = 0;

    private void compute(double dt) {
        double d = this.mStiffness;
        double d2 = this.mDamping;
        int sqrt = (int) ((9.0d / ((Math.sqrt(this.mStiffness / this.mMass) * dt) * 4.0d)) + 1.0d);
        double d3 = dt / sqrt;
        int i = 0;
        while (i < sqrt) {
            float f = this.mPos;
            double d4 = this.mTargetPos;
            int i2 = sqrt;
            float f2 = this.mV;
            float f3 = this.mMass;
            double d5 = d2;
            double d6 = f2 + ((((((-d) * (f - d4)) - (f2 * d2)) / f3) * d3) / 2.0d);
            double d7 = d;
            double d8 = ((((-((f + ((d3 * d6) / 2.0d)) - d4)) * d) - (d6 * d5)) / f3) * d3;
            double d9 = f2 + (d8 / 2.0d);
            float f4 = (float) (f2 + d8);
            this.mV = f4;
            float f5 = (float) (f + (d9 * d3));
            this.mPos = f5;
            int i3 = this.mBoundaryMode;
            if (i3 > 0) {
                if (f5 < 0.0f && (i3 & 1) == 1) {
                    this.mPos = -f5;
                    this.mV = -f4;
                }
                float f6 = this.mPos;
                if (f6 > 1.0f && (i3 & 2) == 2) {
                    this.mPos = 2.0f - f6;
                    this.mV = -this.mV;
                }
            }
            i++;
            sqrt = i2;
            d2 = d5;
            d = d7;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public String debug(String desc, float time) {
        return null;
    }

    public float getAcceleration() {
        return ((float) (((-this.mStiffness) * (this.mPos - this.mTargetPos)) - (this.mV * this.mDamping))) / this.mMass;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getInterpolation(float time) {
        compute(time - this.mLastTime);
        this.mLastTime = time;
        return this.mPos;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity() {
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity(float t) {
        return this.mV;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public boolean isStopped() {
        double d = this.mPos - this.mTargetPos;
        double d2 = this.mStiffness;
        double d3 = this.mV;
        return Math.sqrt((((d3 * d3) * ((double) this.mMass)) + ((d2 * d) * d)) / d2) <= ((double) this.mStopThreshold);
    }

    void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        System.out.println((".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName() + "() ") + str);
    }

    public void springConfig(float currentPos, float target, float currentVelocity, float mass, float stiffness, float damping, float stopThreshold, int boundaryMode) {
        this.mTargetPos = target;
        this.mDamping = damping;
        this.mInitialized = false;
        this.mPos = currentPos;
        this.mLastVelocity = currentVelocity;
        this.mStiffness = stiffness;
        this.mMass = mass;
        this.mStopThreshold = stopThreshold;
        this.mBoundaryMode = boundaryMode;
        this.mLastTime = 0.0f;
    }
}
