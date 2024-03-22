package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class ChangeScroll extends Transition {
    private static final String PROPNAME_SCROLL_X = "android:changeScroll:x";
    private static final String PROPNAME_SCROLL_Y = "android:changeScroll:y";
    private static final String[] PROPERTIES = {PROPNAME_SCROLL_X, PROPNAME_SCROLL_Y};

    public ChangeScroll() {
    }

    public ChangeScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_SCROLL_X, Integer.valueOf(transitionValues.view.getScrollX()));
        transitionValues.values.put(PROPNAME_SCROLL_Y, Integer.valueOf(transitionValues.view.getScrollY()));
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        View view = endValues.view;
        int intValue = ((Integer) startValues.values.get(PROPNAME_SCROLL_X)).intValue();
        int intValue2 = ((Integer) endValues.values.get(PROPNAME_SCROLL_X)).intValue();
        int intValue3 = ((Integer) startValues.values.get(PROPNAME_SCROLL_Y)).intValue();
        int intValue4 = ((Integer) endValues.values.get(PROPNAME_SCROLL_Y)).intValue();
        ObjectAnimator objectAnimator = null;
        ObjectAnimator objectAnimator2 = null;
        if (intValue != intValue2) {
            view.setScrollX(intValue);
            objectAnimator = ObjectAnimator.ofInt(view, "scrollX", intValue, intValue2);
        }
        if (intValue3 != intValue4) {
            view.setScrollY(intValue3);
            objectAnimator2 = ObjectAnimator.ofInt(view, "scrollY", intValue3, intValue4);
        }
        return TransitionUtils.mergeAnimators(objectAnimator, objectAnimator2);
    }

    @Override // androidx.transition.Transition
    public String[] getTransitionProperties() {
        return PROPERTIES;
    }
}