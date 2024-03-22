package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class SidePropagation extends VisibilityPropagation {
    private float mPropagationSpeed = 3.0f;
    private int mSide = 80;

    private int distance(View sceneRoot, int viewX, int viewY, int epicenterX, int epicenterY, int left, int top, int right, int bottom) {
        int i;
        int i2 = this.mSide;
        if (i2 == 8388611) {
            i = ViewCompat.getLayoutDirection(sceneRoot) == 1 ? 5 : 3;
        } else if (i2 == 8388613) {
            i = ViewCompat.getLayoutDirection(sceneRoot) == 1 ? 3 : 5;
        } else {
            i = this.mSide;
        }
        switch (i) {
            case 3:
                return (right - viewX) + Math.abs(epicenterY - viewY);
            case 5:
                return (viewX - left) + Math.abs(epicenterY - viewY);
            case 48:
                return (bottom - viewY) + Math.abs(epicenterX - viewX);
            case 80:
                return (viewY - top) + Math.abs(epicenterX - viewX);
            default:
                return 0;
        }
    }

    private int getMaxDistance(ViewGroup sceneRoot) {
        switch (this.mSide) {
            case 3:
            case 5:
            case GravityCompat.START /* 8388611 */:
            case GravityCompat.END /* 8388613 */:
                return sceneRoot.getWidth();
            default:
                return sceneRoot.getHeight();
        }
    }

    @Override // androidx.transition.TransitionPropagation
    public long getStartDelay(ViewGroup sceneRoot, Transition transition, TransitionValues startValues, TransitionValues endValues) {
        int i;
        TransitionValues transitionValues;
        int i2;
        int i3;
        if (startValues == null && endValues == null) {
            return 0L;
        }
        Rect epicenter = transition.getEpicenter();
        if (endValues == null || getViewVisibility(startValues) == 0) {
            i = -1;
            transitionValues = startValues;
        } else {
            i = 1;
            transitionValues = endValues;
        }
        int viewX = getViewX(transitionValues);
        int viewY = getViewY(transitionValues);
        int[] iArr = new int[2];
        sceneRoot.getLocationOnScreen(iArr);
        int round = iArr[0] + Math.round(sceneRoot.getTranslationX());
        int round2 = iArr[1] + Math.round(sceneRoot.getTranslationY());
        int width = round + sceneRoot.getWidth();
        int height = round2 + sceneRoot.getHeight();
        if (epicenter != null) {
            i3 = epicenter.centerX();
            i2 = epicenter.centerY();
        } else {
            i2 = (round2 + height) / 2;
            i3 = (round + width) / 2;
        }
        float distance = distance(sceneRoot, viewX, viewY, i3, i2, round, round2, width, height) / getMaxDistance(sceneRoot);
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return Math.round((((float) (i * duration)) / this.mPropagationSpeed) * distance);
    }

    public void setPropagationSpeed(float propagationSpeed) {
        if (propagationSpeed == 0.0f) {
            throw new IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.mPropagationSpeed = propagationSpeed;
    }

    public void setSide(int side) {
        this.mSide = side;
    }
}
