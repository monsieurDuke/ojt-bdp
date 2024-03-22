package androidx.transition;

import android.graphics.Rect;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class CircularPropagation extends VisibilityPropagation {
    private float mPropagationSpeed = 3.0f;

    private static float distance(float x1, float y1, float x2, float y2) {
        float f = x2 - x1;
        float f2 = y2 - y1;
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    @Override // androidx.transition.TransitionPropagation
    public long getStartDelay(ViewGroup sceneRoot, Transition transition, TransitionValues startValues, TransitionValues endValues) {
        TransitionValues transitionValues;
        int round;
        int i;
        if (startValues == null && endValues == null) {
            return 0L;
        }
        int i2 = 1;
        if (endValues == null || getViewVisibility(startValues) == 0) {
            transitionValues = startValues;
            i2 = -1;
        } else {
            transitionValues = endValues;
        }
        int viewX = getViewX(transitionValues);
        int viewY = getViewY(transitionValues);
        Rect epicenter = transition.getEpicenter();
        if (epicenter != null) {
            i = epicenter.centerX();
            round = epicenter.centerY();
        } else {
            sceneRoot.getLocationOnScreen(new int[2]);
            int round2 = Math.round(r10[0] + (sceneRoot.getWidth() / 2) + sceneRoot.getTranslationX());
            round = Math.round(r10[1] + (sceneRoot.getHeight() / 2) + sceneRoot.getTranslationY());
            i = round2;
        }
        float distance = distance(viewX, viewY, i, round) / distance(0.0f, 0.0f, sceneRoot.getWidth(), sceneRoot.getHeight());
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return Math.round((((float) (i2 * duration)) / this.mPropagationSpeed) * distance);
    }

    public void setPropagationSpeed(float propagationSpeed) {
        if (propagationSpeed == 0.0f) {
            throw new IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.mPropagationSpeed = propagationSpeed;
    }
}
