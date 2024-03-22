package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class StopLogicEngine implements StopEngine {
    private static final float EPSILON = 1.0E-5f;
    private boolean mBackwards = false;
    private boolean mDone = false;
    private float mLastPosition;
    private int mNumberOfStages;
    private float mStage1Duration;
    private float mStage1EndPosition;
    private float mStage1Velocity;
    private float mStage2Duration;
    private float mStage2EndPosition;
    private float mStage2Velocity;
    private float mStage3Duration;
    private float mStage3EndPosition;
    private float mStage3Velocity;
    private float mStartPosition;
    private String mType;

    private float calcY(float time) {
        this.mDone = false;
        float f = this.mStage1Duration;
        if (time <= f) {
            float f2 = this.mStage1Velocity;
            return (f2 * time) + ((((this.mStage2Velocity - f2) * time) * time) / (f * 2.0f));
        }
        int i = this.mNumberOfStages;
        if (i == 1) {
            return this.mStage1EndPosition;
        }
        float time2 = time - f;
        float f3 = this.mStage2Duration;
        if (time2 < f3) {
            float f4 = this.mStage1EndPosition;
            float f5 = this.mStage2Velocity;
            return f4 + (f5 * time2) + ((((this.mStage3Velocity - f5) * time2) * time2) / (f3 * 2.0f));
        }
        if (i == 2) {
            return this.mStage2EndPosition;
        }
        float time3 = time2 - f3;
        float f6 = this.mStage3Duration;
        if (time3 > f6) {
            this.mDone = true;
            return this.mStage3EndPosition;
        }
        float f7 = this.mStage2EndPosition;
        float f8 = this.mStage3Velocity;
        return (f7 + (f8 * time3)) - (((f8 * time3) * time3) / (f6 * 2.0f));
    }

    private void setup(float velocity, float distance, float maxAcceleration, float maxVelocity, float maxTime) {
        this.mDone = false;
        float f = velocity == 0.0f ? 1.0E-4f : velocity;
        this.mStage1Velocity = f;
        float f2 = f / maxAcceleration;
        float f3 = (f2 * f) / 2.0f;
        if (f < 0.0f) {
            float sqrt = (float) Math.sqrt(maxAcceleration * (distance - ((((-f) / maxAcceleration) * f) / 2.0f)));
            if (sqrt < maxVelocity) {
                this.mType = "backward accelerate, decelerate";
                this.mNumberOfStages = 2;
                this.mStage1Velocity = f;
                this.mStage2Velocity = sqrt;
                this.mStage3Velocity = 0.0f;
                float f4 = (sqrt - f) / maxAcceleration;
                this.mStage1Duration = f4;
                this.mStage2Duration = sqrt / maxAcceleration;
                this.mStage1EndPosition = ((f + sqrt) * f4) / 2.0f;
                this.mStage2EndPosition = distance;
                this.mStage3EndPosition = distance;
                return;
            }
            this.mType = "backward accelerate cruse decelerate";
            this.mNumberOfStages = 3;
            this.mStage1Velocity = f;
            this.mStage2Velocity = maxVelocity;
            this.mStage3Velocity = maxVelocity;
            float f5 = (maxVelocity - f) / maxAcceleration;
            this.mStage1Duration = f5;
            float f6 = maxVelocity / maxAcceleration;
            this.mStage3Duration = f6;
            float f7 = ((f + maxVelocity) * f5) / 2.0f;
            float f8 = (maxVelocity * f6) / 2.0f;
            this.mStage2Duration = ((distance - f7) - f8) / maxVelocity;
            this.mStage1EndPosition = f7;
            this.mStage2EndPosition = distance - f8;
            this.mStage3EndPosition = distance;
            return;
        }
        if (f3 >= distance) {
            this.mType = "hard stop";
            this.mNumberOfStages = 1;
            this.mStage1Velocity = f;
            this.mStage2Velocity = 0.0f;
            this.mStage1EndPosition = distance;
            this.mStage1Duration = (2.0f * distance) / f;
            return;
        }
        float f9 = distance - f3;
        float f10 = f9 / f;
        if (f10 + f2 < maxTime) {
            this.mType = "cruse decelerate";
            this.mNumberOfStages = 2;
            this.mStage1Velocity = f;
            this.mStage2Velocity = f;
            this.mStage3Velocity = 0.0f;
            this.mStage1EndPosition = f9;
            this.mStage2EndPosition = distance;
            this.mStage1Duration = f10;
            this.mStage2Duration = f / maxAcceleration;
            return;
        }
        float sqrt2 = (float) Math.sqrt((maxAcceleration * distance) + ((f * f) / 2.0f));
        this.mStage1Duration = (sqrt2 - f) / maxAcceleration;
        this.mStage2Duration = sqrt2 / maxAcceleration;
        if (sqrt2 < maxVelocity) {
            this.mType = "accelerate decelerate";
            this.mNumberOfStages = 2;
            this.mStage1Velocity = f;
            this.mStage2Velocity = sqrt2;
            this.mStage3Velocity = 0.0f;
            float f11 = (sqrt2 - f) / maxAcceleration;
            this.mStage1Duration = f11;
            this.mStage2Duration = sqrt2 / maxAcceleration;
            this.mStage1EndPosition = ((f + sqrt2) * f11) / 2.0f;
            this.mStage2EndPosition = distance;
            return;
        }
        this.mType = "accelerate cruse decelerate";
        this.mNumberOfStages = 3;
        this.mStage1Velocity = f;
        this.mStage2Velocity = maxVelocity;
        this.mStage3Velocity = maxVelocity;
        float f12 = (maxVelocity - f) / maxAcceleration;
        this.mStage1Duration = f12;
        float f13 = maxVelocity / maxAcceleration;
        this.mStage3Duration = f13;
        float f14 = ((f + maxVelocity) * f12) / 2.0f;
        float f15 = (maxVelocity * f13) / 2.0f;
        this.mStage2Duration = ((distance - f14) - f15) / maxVelocity;
        this.mStage1EndPosition = f14;
        this.mStage2EndPosition = distance - f15;
        this.mStage3EndPosition = distance;
    }

    public void config(float currentPos, float destination, float currentVelocity, float maxTime, float maxAcceleration, float maxVelocity) {
        this.mDone = false;
        this.mStartPosition = currentPos;
        boolean z = currentPos > destination;
        this.mBackwards = z;
        if (z) {
            setup(-currentVelocity, currentPos - destination, maxAcceleration, maxVelocity, maxTime);
        } else {
            setup(currentVelocity, destination - currentPos, maxAcceleration, maxVelocity, maxTime);
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public String debug(String desc, float time) {
        String str = ((desc + " ===== " + this.mType + "\n") + desc + (this.mBackwards ? "backwards" : "forward ") + " time = " + time + "  stages " + this.mNumberOfStages + "\n") + desc + " dur " + this.mStage1Duration + " vel " + this.mStage1Velocity + " pos " + this.mStage1EndPosition + "\n";
        if (this.mNumberOfStages > 1) {
            str = str + desc + " dur " + this.mStage2Duration + " vel " + this.mStage2Velocity + " pos " + this.mStage2EndPosition + "\n";
        }
        if (this.mNumberOfStages > 2) {
            str = str + desc + " dur " + this.mStage3Duration + " vel " + this.mStage3Velocity + " pos " + this.mStage3EndPosition + "\n";
        }
        float f = this.mStage1Duration;
        if (time <= f) {
            return str + desc + "stage 0\n";
        }
        int i = this.mNumberOfStages;
        if (i == 1) {
            return str + desc + "end stage 0\n";
        }
        float time2 = time - f;
        float f2 = this.mStage2Duration;
        return time2 < f2 ? str + desc + " stage 1\n" : i == 2 ? str + desc + "end stage 1\n" : time2 - f2 < this.mStage3Duration ? str + desc + " stage 2\n" : str + desc + " end stage 2\n";
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getInterpolation(float v) {
        float calcY = calcY(v);
        this.mLastPosition = v;
        return this.mBackwards ? this.mStartPosition - calcY : this.mStartPosition + calcY;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity() {
        return this.mBackwards ? -getVelocity(this.mLastPosition) : getVelocity(this.mLastPosition);
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity(float x) {
        float f = this.mStage1Duration;
        if (x <= f) {
            float f2 = this.mStage1Velocity;
            return f2 + (((this.mStage2Velocity - f2) * x) / f);
        }
        int i = this.mNumberOfStages;
        if (i == 1) {
            return 0.0f;
        }
        float x2 = x - f;
        float f3 = this.mStage2Duration;
        if (x2 < f3) {
            float f4 = this.mStage2Velocity;
            return f4 + (((this.mStage3Velocity - f4) * x2) / f3);
        }
        if (i == 2) {
            return this.mStage2EndPosition;
        }
        float x3 = x2 - f3;
        float f5 = this.mStage3Duration;
        if (x3 >= f5) {
            return this.mStage3EndPosition;
        }
        float f6 = this.mStage3Velocity;
        return f6 - ((f6 * x3) / f5);
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public boolean isStopped() {
        return getVelocity() < EPSILON && Math.abs(this.mStage3EndPosition - this.mLastPosition) < EPSILON;
    }
}
