package androidx.dynamicanimation.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

/* loaded from: classes.dex */
public final class SpringForce implements Force {
    public static final float DAMPING_RATIO_HIGH_BOUNCY = 0.2f;
    public static final float DAMPING_RATIO_LOW_BOUNCY = 0.75f;
    public static final float DAMPING_RATIO_MEDIUM_BOUNCY = 0.5f;
    public static final float DAMPING_RATIO_NO_BOUNCY = 1.0f;
    public static final float STIFFNESS_HIGH = 10000.0f;
    public static final float STIFFNESS_LOW = 200.0f;
    public static final float STIFFNESS_MEDIUM = 1500.0f;
    public static final float STIFFNESS_VERY_LOW = 50.0f;
    private static final double UNSET = Double.MAX_VALUE;
    private static final double VELOCITY_THRESHOLD_MULTIPLIER = 62.5d;
    private double mDampedFreq;
    double mDampingRatio;
    private double mFinalPosition;
    private double mGammaMinus;
    private double mGammaPlus;
    private boolean mInitialized;
    private final DynamicAnimation.MassState mMassState;
    double mNaturalFreq;
    private double mValueThreshold;
    private double mVelocityThreshold;

    public SpringForce() {
        this.mNaturalFreq = Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = Double.MAX_VALUE;
        this.mMassState = new DynamicAnimation.MassState();
    }

    public SpringForce(float finalPosition) {
        this.mNaturalFreq = Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = Double.MAX_VALUE;
        this.mMassState = new DynamicAnimation.MassState();
        this.mFinalPosition = finalPosition;
    }

    private void init() {
        if (this.mInitialized) {
            return;
        }
        if (this.mFinalPosition == Double.MAX_VALUE) {
            throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
        }
        double d = this.mDampingRatio;
        if (d > 1.0d) {
            double d2 = this.mNaturalFreq;
            this.mGammaPlus = ((-d) * d2) + (d2 * Math.sqrt((d * d) - 1.0d));
            double d3 = this.mDampingRatio;
            double d4 = this.mNaturalFreq;
            this.mGammaMinus = ((-d3) * d4) - (d4 * Math.sqrt((d3 * d3) - 1.0d));
        } else if (d >= 0.0d && d < 1.0d) {
            this.mDampedFreq = this.mNaturalFreq * Math.sqrt(1.0d - (d * d));
        }
        this.mInitialized = true;
    }

    @Override // androidx.dynamicanimation.animation.Force
    public float getAcceleration(float lastDisplacement, float lastVelocity) {
        float lastDisplacement2 = lastDisplacement - getFinalPosition();
        double d = this.mNaturalFreq;
        return (float) (((-(d * d)) * lastDisplacement2) - (lastVelocity * ((d * 2.0d) * this.mDampingRatio)));
    }

    public float getDampingRatio() {
        return (float) this.mDampingRatio;
    }

    public float getFinalPosition() {
        return (float) this.mFinalPosition;
    }

    public float getStiffness() {
        double d = this.mNaturalFreq;
        return (float) (d * d);
    }

    @Override // androidx.dynamicanimation.animation.Force
    public boolean isAtEquilibrium(float value, float velocity) {
        return ((double) Math.abs(velocity)) < this.mVelocityThreshold && ((double) Math.abs(value - getFinalPosition())) < this.mValueThreshold;
    }

    public SpringForce setDampingRatio(float dampingRatio) {
        if (dampingRatio < 0.0f) {
            throw new IllegalArgumentException("Damping ratio must be non-negative");
        }
        this.mDampingRatio = dampingRatio;
        this.mInitialized = false;
        return this;
    }

    public SpringForce setFinalPosition(float finalPosition) {
        this.mFinalPosition = finalPosition;
        return this;
    }

    public SpringForce setStiffness(float stiffness) {
        if (stiffness <= 0.0f) {
            throw new IllegalArgumentException("Spring stiffness constant must be positive.");
        }
        this.mNaturalFreq = Math.sqrt(stiffness);
        this.mInitialized = false;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setValueThreshold(double threshold) {
        double abs = Math.abs(threshold);
        this.mValueThreshold = abs;
        this.mVelocityThreshold = abs * VELOCITY_THRESHOLD_MULTIPLIER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DynamicAnimation.MassState updateValues(double lastDisplacement, double lastVelocity, long timeElapsed) {
        double d;
        double d2;
        init();
        double d3 = timeElapsed / 1000.0d;
        double d4 = lastDisplacement - this.mFinalPosition;
        double d5 = this.mDampingRatio;
        if (d5 > 1.0d) {
            double d6 = this.mGammaMinus;
            double d7 = this.mGammaPlus;
            double d8 = d4 - (((d6 * d4) - lastVelocity) / (d6 - d7));
            double d9 = ((d6 * d4) - lastVelocity) / (d6 - d7);
            d = (Math.pow(2.718281828459045d, d6 * d3) * d8) + (Math.pow(2.718281828459045d, this.mGammaPlus * d3) * d9);
            double d10 = this.mGammaMinus;
            double pow = d8 * d10 * Math.pow(2.718281828459045d, d10 * d3);
            double d11 = this.mGammaPlus;
            d2 = pow + (d9 * d11 * Math.pow(2.718281828459045d, d11 * d3));
        } else if (d5 == 1.0d) {
            double d12 = this.mNaturalFreq;
            double d13 = lastVelocity + (d12 * d4);
            double pow2 = Math.pow(2.718281828459045d, (-d12) * d3) * (d4 + (d13 * d3));
            double pow3 = (d4 + (d13 * d3)) * Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d3);
            double d14 = this.mNaturalFreq;
            d = pow2;
            d2 = (pow3 * (-d14)) + (Math.pow(2.718281828459045d, (-d14) * d3) * d13);
        } else {
            double d15 = 1.0d / this.mDampedFreq;
            double d16 = this.mNaturalFreq;
            double d17 = d15 * ((d5 * d16 * d4) + lastVelocity);
            double lastDisplacement2 = Math.pow(2.718281828459045d, (-d5) * d16 * d3) * ((Math.cos(this.mDampedFreq * d3) * d4) + (Math.sin(this.mDampedFreq * d3) * d17));
            double d18 = this.mNaturalFreq;
            double d19 = this.mDampingRatio;
            double d20 = (-d18) * lastDisplacement2 * d19;
            double pow4 = Math.pow(2.718281828459045d, (-d19) * d18 * d3);
            double d21 = this.mDampedFreq;
            double sin = (-d21) * d4 * Math.sin(d21 * d3);
            double d22 = this.mDampedFreq;
            double cos = d20 + (pow4 * (sin + (d22 * d17 * Math.cos(d22 * d3))));
            d = lastDisplacement2;
            d2 = cos;
        }
        this.mMassState.mValue = (float) (this.mFinalPosition + d);
        this.mMassState.mVelocity = (float) d2;
        return this.mMassState;
    }
}
