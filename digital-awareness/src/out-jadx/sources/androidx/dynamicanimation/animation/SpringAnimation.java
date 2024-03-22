package androidx.dynamicanimation.animation;

import android.os.Looper;
import android.util.AndroidRuntimeException;
import androidx.dynamicanimation.animation.DynamicAnimation;

/* loaded from: classes.dex */
public final class SpringAnimation extends DynamicAnimation<SpringAnimation> {
    private static final float UNSET = Float.MAX_VALUE;
    private boolean mEndRequested;
    private float mPendingPosition;
    private SpringForce mSpring;

    public SpringAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public <K> SpringAnimation(K k, FloatPropertyCompat<K> floatPropertyCompat) {
        super(k, floatPropertyCompat);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public <K> SpringAnimation(K k, FloatPropertyCompat<K> floatPropertyCompat, float finalPosition) {
        super(k, floatPropertyCompat);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new SpringForce(finalPosition);
    }

    private void sanityCheck() {
        SpringForce springForce = this.mSpring;
        if (springForce == null) {
            throw new UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
        }
        double finalPosition = springForce.getFinalPosition();
        if (finalPosition > this.mMaxValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
        }
        if (finalPosition < this.mMinValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
        }
    }

    public void animateToFinalPosition(float finalPosition) {
        if (isRunning()) {
            this.mPendingPosition = finalPosition;
            return;
        }
        if (this.mSpring == null) {
            this.mSpring = new SpringForce(finalPosition);
        }
        this.mSpring.setFinalPosition(finalPosition);
        start();
    }

    public boolean canSkipToEnd() {
        return this.mSpring.mDampingRatio > 0.0d;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    float getAcceleration(float value, float velocity) {
        return this.mSpring.getAcceleration(value, velocity);
    }

    public SpringForce getSpring() {
        return this.mSpring;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    boolean isAtEquilibrium(float value, float velocity) {
        return this.mSpring.isAtEquilibrium(value, velocity);
    }

    public SpringAnimation setSpring(SpringForce force) {
        this.mSpring = force;
        return this;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    void setValueThreshold(float threshold) {
    }

    public void skipToEnd() {
        if (!canSkipToEnd()) {
            throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        }
        if (this.mRunning) {
            this.mEndRequested = true;
        }
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public void start() {
        sanityCheck();
        this.mSpring.setValueThreshold(getValueThreshold());
        super.start();
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    boolean updateValueAndVelocity(long deltaT) {
        if (this.mEndRequested) {
            float f = this.mPendingPosition;
            if (f != Float.MAX_VALUE) {
                this.mSpring.setFinalPosition(f);
                this.mPendingPosition = Float.MAX_VALUE;
            }
            this.mValue = this.mSpring.getFinalPosition();
            this.mVelocity = 0.0f;
            this.mEndRequested = false;
            return true;
        }
        if (this.mPendingPosition != Float.MAX_VALUE) {
            this.mSpring.getFinalPosition();
            DynamicAnimation.MassState updateValues = this.mSpring.updateValues(this.mValue, this.mVelocity, deltaT / 2);
            this.mSpring.setFinalPosition(this.mPendingPosition);
            this.mPendingPosition = Float.MAX_VALUE;
            DynamicAnimation.MassState updateValues2 = this.mSpring.updateValues(updateValues.mValue, updateValues.mVelocity, deltaT / 2);
            this.mValue = updateValues2.mValue;
            this.mVelocity = updateValues2.mVelocity;
        } else {
            DynamicAnimation.MassState updateValues3 = this.mSpring.updateValues(this.mValue, this.mVelocity, deltaT);
            this.mValue = updateValues3.mValue;
            this.mVelocity = updateValues3.mVelocity;
        }
        this.mValue = Math.max(this.mValue, this.mMinValue);
        this.mValue = Math.min(this.mValue, this.mMaxValue);
        if (!isAtEquilibrium(this.mValue, this.mVelocity)) {
            return false;
        }
        this.mValue = this.mSpring.getFinalPosition();
        this.mVelocity = 0.0f;
        return true;
    }
}
