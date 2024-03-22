package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.animation.ChildrenAlphaProperty;
import com.google.android.material.animation.DrawableAlphaProperty;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.circularreveal.CircularRevealCompat;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes.dex */
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    private float dependencyOriginalTranslationX;
    private float dependencyOriginalTranslationY;
    private final int[] tmpArray;
    private final Rect tmpRect;
    private final RectF tmpRectF1;
    private final RectF tmpRectF2;

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class FabTransformationSpec {
        public Positioning positioning;
        public MotionSpec timings;
    }

    public FabTransformationBehavior() {
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }

    public FabTransformationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }

    private ViewGroup calculateChildContentContainer(View view) {
        View findViewById = view.findViewById(R.id.mtrl_child_content_container);
        return findViewById != null ? toViewGroupOrNull(findViewById) : ((view instanceof TransformationChildLayout) || (view instanceof TransformationChildCard)) ? toViewGroupOrNull(((ViewGroup) view).getChildAt(0)) : toViewGroupOrNull(view);
    }

    private void calculateChildVisibleBoundsAtEndOfExpansion(View child, FabTransformationSpec spec, MotionTiming translationXTiming, MotionTiming translationYTiming, float fromX, float fromY, float toX, float toY, RectF childBounds) {
        float calculateValueOfAnimationAtEndOfExpansion = calculateValueOfAnimationAtEndOfExpansion(spec, translationXTiming, fromX, toX);
        float calculateValueOfAnimationAtEndOfExpansion2 = calculateValueOfAnimationAtEndOfExpansion(spec, translationYTiming, fromY, toY);
        Rect rect = this.tmpRect;
        child.getWindowVisibleDisplayFrame(rect);
        RectF rectF = this.tmpRectF1;
        rectF.set(rect);
        RectF rectF2 = this.tmpRectF2;
        calculateWindowBounds(child, rectF2);
        rectF2.offset(calculateValueOfAnimationAtEndOfExpansion, calculateValueOfAnimationAtEndOfExpansion2);
        rectF2.intersect(rectF);
        childBounds.set(rectF2);
    }

    private void calculateDependencyWindowBounds(View view, RectF rect) {
        calculateWindowBounds(view, rect);
        rect.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
    }

    private Pair<MotionTiming, MotionTiming> calculateMotionTiming(float translationX, float translationY, boolean expanded, FabTransformationSpec spec) {
        MotionTiming timing;
        MotionTiming timing2;
        if (translationX == 0.0f || translationY == 0.0f) {
            timing = spec.timings.getTiming("translationXLinear");
            timing2 = spec.timings.getTiming("translationYLinear");
        } else if ((!expanded || translationY >= 0.0f) && (expanded || translationY <= 0.0f)) {
            timing = spec.timings.getTiming("translationXCurveDownwards");
            timing2 = spec.timings.getTiming("translationYCurveDownwards");
        } else {
            timing = spec.timings.getTiming("translationXCurveUpwards");
            timing2 = spec.timings.getTiming("translationYCurveUpwards");
        }
        return new Pair<>(timing, timing2);
    }

    private float calculateRevealCenterX(View dependency, View child, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, rectF);
        calculateWindowBounds(child, rectF2);
        rectF2.offset(-calculateTranslationX(dependency, child, positioning), 0.0f);
        return rectF.centerX() - rectF2.left;
    }

    private float calculateRevealCenterY(View dependency, View child, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, rectF);
        calculateWindowBounds(child, rectF2);
        rectF2.offset(0.0f, -calculateTranslationY(dependency, child, positioning));
        return rectF.centerY() - rectF2.top;
    }

    private float calculateTranslationX(View dependency, View child, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, rectF);
        calculateWindowBounds(child, rectF2);
        float f = 0.0f;
        switch (positioning.gravity & 7) {
            case 1:
                f = rectF2.centerX() - rectF.centerX();
                break;
            case 3:
                f = rectF2.left - rectF.left;
                break;
            case 5:
                f = rectF2.right - rectF.right;
                break;
        }
        return f + positioning.xAdjustment;
    }

    private float calculateTranslationY(View dependency, View child, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, rectF);
        calculateWindowBounds(child, rectF2);
        float f = 0.0f;
        switch (positioning.gravity & 112) {
            case 16:
                f = rectF2.centerY() - rectF.centerY();
                break;
            case 48:
                f = rectF2.top - rectF.top;
                break;
            case 80:
                f = rectF2.bottom - rectF.bottom;
                break;
        }
        return f + positioning.yAdjustment;
    }

    private float calculateValueOfAnimationAtEndOfExpansion(FabTransformationSpec spec, MotionTiming timing, float from, float to) {
        long delay = timing.getDelay();
        long duration = timing.getDuration();
        MotionTiming timing2 = spec.timings.getTiming("expansion");
        return AnimationUtils.lerp(from, to, timing.getInterpolator().getInterpolation(((float) (((timing2.getDelay() + timing2.getDuration()) + 17) - delay)) / ((float) duration)));
    }

    private void calculateWindowBounds(View view, RectF rect) {
        rect.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        view.getLocationInWindow(this.tmpArray);
        rect.offsetTo(r1[0], r1[1]);
        rect.offset((int) (-view.getTranslationX()), (int) (-view.getTranslationY()));
    }

    private void createChildrenFadeAnimation(View unusedDependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ViewGroup calculateChildContentContainer;
        ObjectAnimator ofFloat;
        if (child instanceof ViewGroup) {
            if (((child instanceof CircularRevealWidget) && CircularRevealHelper.STRATEGY == 0) || (calculateChildContentContainer = calculateChildContentContainer(child)) == null) {
                return;
            }
            if (expanded) {
                if (!currentlyAnimating) {
                    ChildrenAlphaProperty.CHILDREN_ALPHA.set(calculateChildContentContainer, Float.valueOf(0.0f));
                }
                ofFloat = ObjectAnimator.ofFloat(calculateChildContentContainer, ChildrenAlphaProperty.CHILDREN_ALPHA, 1.0f);
            } else {
                ofFloat = ObjectAnimator.ofFloat(calculateChildContentContainer, ChildrenAlphaProperty.CHILDREN_ALPHA, 0.0f);
            }
            spec.timings.getTiming("contentFade").apply(ofFloat);
            list.add(ofFloat);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void createColorAnimation(View dependency, View view, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator ofInt;
        if (view instanceof CircularRevealWidget) {
            CircularRevealWidget circularRevealWidget = (CircularRevealWidget) view;
            int backgroundTint = getBackgroundTint(dependency);
            int i = 16777215 & backgroundTint;
            if (expanded) {
                if (!currentlyAnimating) {
                    circularRevealWidget.setCircularRevealScrimColor(backgroundTint);
                }
                ofInt = ObjectAnimator.ofInt(circularRevealWidget, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, i);
            } else {
                ofInt = ObjectAnimator.ofInt(circularRevealWidget, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, backgroundTint);
            }
            ofInt.setEvaluator(ArgbEvaluatorCompat.getInstance());
            spec.timings.getTiming(TypedValues.Custom.S_COLOR).apply(ofInt);
            list.add(ofInt);
        }
    }

    private void createDependencyTranslationAnimation(View dependency, View child, boolean expanded, FabTransformationSpec spec, List<Animator> list) {
        float calculateTranslationX = calculateTranslationX(dependency, child, spec.positioning);
        float calculateTranslationY = calculateTranslationY(dependency, child, spec.positioning);
        Pair<MotionTiming, MotionTiming> calculateMotionTiming = calculateMotionTiming(calculateTranslationX, calculateTranslationY, expanded, spec);
        MotionTiming motionTiming = (MotionTiming) calculateMotionTiming.first;
        MotionTiming motionTiming2 = (MotionTiming) calculateMotionTiming.second;
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[1];
        fArr[0] = expanded ? calculateTranslationX : this.dependencyOriginalTranslationX;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(dependency, (Property<View, Float>) property, fArr);
        Property property2 = View.TRANSLATION_Y;
        float[] fArr2 = new float[1];
        fArr2[0] = expanded ? calculateTranslationY : this.dependencyOriginalTranslationY;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(dependency, (Property<View, Float>) property2, fArr2);
        motionTiming.apply(ofFloat);
        motionTiming2.apply(ofFloat2);
        list.add(ofFloat);
        list.add(ofFloat2);
    }

    private void createElevationAnimation(View dependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator ofFloat;
        float elevation = ViewCompat.getElevation(child) - ViewCompat.getElevation(dependency);
        if (expanded) {
            if (!currentlyAnimating) {
                child.setTranslationZ(-elevation);
            }
            ofFloat = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_Z, 0.0f);
        } else {
            ofFloat = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_Z, -elevation);
        }
        spec.timings.getTiming("elevation").apply(ofFloat);
        list.add(ofFloat);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void createExpansionAnimation(View dependency, View view, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, float childWidth, float childHeight, List<Animator> list, List<Animator.AnimatorListener> list2) {
        MotionTiming motionTiming;
        CircularRevealWidget circularRevealWidget;
        Animator animator;
        if (view instanceof CircularRevealWidget) {
            final CircularRevealWidget circularRevealWidget2 = (CircularRevealWidget) view;
            float calculateRevealCenterX = calculateRevealCenterX(dependency, view, spec.positioning);
            float calculateRevealCenterY = calculateRevealCenterY(dependency, view, spec.positioning);
            ((FloatingActionButton) dependency).getContentRect(this.tmpRect);
            float width = this.tmpRect.width() / 2.0f;
            MotionTiming timing = spec.timings.getTiming("expansion");
            if (expanded) {
                if (!currentlyAnimating) {
                    circularRevealWidget2.setRevealInfo(new CircularRevealWidget.RevealInfo(calculateRevealCenterX, calculateRevealCenterY, width));
                }
                float f = currentlyAnimating ? circularRevealWidget2.getRevealInfo().radius : width;
                Animator createCircularReveal = CircularRevealCompat.createCircularReveal(circularRevealWidget2, calculateRevealCenterX, calculateRevealCenterY, MathUtils.distanceToFurthestCorner(calculateRevealCenterX, calculateRevealCenterY, 0.0f, 0.0f, childWidth, childHeight));
                createCircularReveal.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        CircularRevealWidget.RevealInfo revealInfo = circularRevealWidget2.getRevealInfo();
                        revealInfo.radius = Float.MAX_VALUE;
                        circularRevealWidget2.setRevealInfo(revealInfo);
                    }
                });
                motionTiming = timing;
                createPreFillRadialExpansion(view, timing.getDelay(), (int) calculateRevealCenterX, (int) calculateRevealCenterY, f, list);
                circularRevealWidget = circularRevealWidget2;
                animator = createCircularReveal;
            } else {
                motionTiming = timing;
                float f2 = circularRevealWidget2.getRevealInfo().radius;
                Animator createCircularReveal2 = CircularRevealCompat.createCircularReveal(circularRevealWidget2, calculateRevealCenterX, calculateRevealCenterY, width);
                createPreFillRadialExpansion(view, motionTiming.getDelay(), (int) calculateRevealCenterX, (int) calculateRevealCenterY, f2, list);
                circularRevealWidget = circularRevealWidget2;
                createPostFillRadialExpansion(view, motionTiming.getDelay(), motionTiming.getDuration(), spec.timings.getTotalDuration(), (int) calculateRevealCenterX, (int) calculateRevealCenterY, width, list);
                animator = createCircularReveal2;
            }
            motionTiming.apply(animator);
            list.add(animator);
            list2.add(CircularRevealCompat.createCircularRevealListener(circularRevealWidget));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void createIconFadeAnimation(View dependency, final View view, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator ofInt;
        if ((view instanceof CircularRevealWidget) && (dependency instanceof ImageView)) {
            final CircularRevealWidget circularRevealWidget = (CircularRevealWidget) view;
            final Drawable drawable = ((ImageView) dependency).getDrawable();
            if (drawable == null) {
                return;
            }
            drawable.mutate();
            if (expanded) {
                if (!currentlyAnimating) {
                    drawable.setAlpha(255);
                }
                ofInt = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 0);
            } else {
                ofInt = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 255);
            }
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.transformation.FabTransformationBehavior.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.invalidate();
                }
            });
            spec.timings.getTiming("iconFade").apply(ofInt);
            list.add(ofInt);
            list2.add(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    circularRevealWidget.setCircularRevealOverlayDrawable(null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                    circularRevealWidget.setCircularRevealOverlayDrawable(drawable);
                }
            });
        }
    }

    private void createPostFillRadialExpansion(View child, long delay, long duration, long totalDuration, int revealCenterX, int revealCenterY, float toRadius, List<Animator> list) {
        if (Build.VERSION.SDK_INT < 21 || delay + duration >= totalDuration) {
            return;
        }
        Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(child, revealCenterX, revealCenterY, toRadius, toRadius);
        createCircularReveal.setStartDelay(delay + duration);
        createCircularReveal.setDuration(totalDuration - (delay + duration));
        list.add(createCircularReveal);
    }

    private void createPreFillRadialExpansion(View child, long delay, int revealCenterX, int revealCenterY, float fromRadius, List<Animator> list) {
        if (Build.VERSION.SDK_INT < 21 || delay <= 0) {
            return;
        }
        Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(child, revealCenterX, revealCenterY, fromRadius, fromRadius);
        createCircularReveal.setStartDelay(0L);
        createCircularReveal.setDuration(delay);
        list.add(createCircularReveal);
    }

    private void createTranslationAnimation(View dependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List<Animator> list, List<Animator.AnimatorListener> list2, RectF childBounds) {
        MotionTiming motionTiming;
        MotionTiming motionTiming2;
        ObjectAnimator ofFloat;
        ObjectAnimator ofFloat2;
        float calculateTranslationX = calculateTranslationX(dependency, child, spec.positioning);
        float calculateTranslationY = calculateTranslationY(dependency, child, spec.positioning);
        Pair<MotionTiming, MotionTiming> calculateMotionTiming = calculateMotionTiming(calculateTranslationX, calculateTranslationY, expanded, spec);
        MotionTiming motionTiming3 = (MotionTiming) calculateMotionTiming.first;
        MotionTiming motionTiming4 = (MotionTiming) calculateMotionTiming.second;
        if (expanded) {
            if (!currentlyAnimating) {
                child.setTranslationX(-calculateTranslationX);
                child.setTranslationY(-calculateTranslationY);
            }
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_X, 0.0f);
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_Y, 0.0f);
            motionTiming = motionTiming4;
            motionTiming2 = motionTiming3;
            calculateChildVisibleBoundsAtEndOfExpansion(child, spec, motionTiming3, motionTiming4, -calculateTranslationX, -calculateTranslationY, 0.0f, 0.0f, childBounds);
            ofFloat = ofFloat3;
            ofFloat2 = ofFloat4;
        } else {
            motionTiming = motionTiming4;
            motionTiming2 = motionTiming3;
            ofFloat = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_X, -calculateTranslationX);
            ofFloat2 = ObjectAnimator.ofFloat(child, (Property<View, Float>) View.TRANSLATION_Y, -calculateTranslationY);
        }
        motionTiming2.apply(ofFloat);
        motionTiming.apply(ofFloat2);
        list.add(ofFloat);
        list.add(ofFloat2);
    }

    private int getBackgroundTint(View view) {
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(view);
        if (backgroundTintList != null) {
            return backgroundTintList.getColorForState(view.getDrawableState(), backgroundTintList.getDefaultColor());
        }
        return 0;
    }

    private ViewGroup toViewGroupOrNull(View view) {
        if (view instanceof ViewGroup) {
            return (ViewGroup) view;
        }
        return null;
    }

    @Override // com.google.android.material.transformation.ExpandableBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (child.getVisibility() == 8) {
            throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        }
        if (!(dependency instanceof FloatingActionButton)) {
            return false;
        }
        int expandedComponentIdHint = ((FloatingActionButton) dependency).getExpandedComponentIdHint();
        return expandedComponentIdHint == 0 || expandedComponentIdHint == child.getId();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams lp) {
        if (lp.dodgeInsetEdges == 0) {
            lp.dodgeInsetEdges = 80;
        }
    }

    @Override // com.google.android.material.transformation.ExpandableTransformationBehavior
    protected AnimatorSet onCreateExpandedStateChangeAnimation(final View dependency, final View child, final boolean expanded, boolean isAnimating) {
        FabTransformationSpec onCreateMotionSpec = onCreateMotionSpec(child.getContext(), expanded);
        if (expanded) {
            this.dependencyOriginalTranslationX = dependency.getTranslationX();
            this.dependencyOriginalTranslationY = dependency.getTranslationY();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (Build.VERSION.SDK_INT >= 21) {
            createElevationAnimation(dependency, child, expanded, isAnimating, onCreateMotionSpec, arrayList, arrayList2);
        }
        RectF rectF = this.tmpRectF1;
        createTranslationAnimation(dependency, child, expanded, isAnimating, onCreateMotionSpec, arrayList, arrayList2, rectF);
        float width = rectF.width();
        float height = rectF.height();
        createDependencyTranslationAnimation(dependency, child, expanded, onCreateMotionSpec, arrayList);
        createIconFadeAnimation(dependency, child, expanded, isAnimating, onCreateMotionSpec, arrayList, arrayList2);
        createExpansionAnimation(dependency, child, expanded, isAnimating, onCreateMotionSpec, width, height, arrayList, arrayList2);
        createColorAnimation(dependency, child, expanded, isAnimating, onCreateMotionSpec, arrayList, arrayList2);
        createChildrenFadeAnimation(dependency, child, expanded, isAnimating, onCreateMotionSpec, arrayList, arrayList2);
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (expanded) {
                    return;
                }
                child.setVisibility(4);
                dependency.setAlpha(1.0f);
                dependency.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (expanded) {
                    child.setVisibility(0);
                    dependency.setAlpha(0.0f);
                    dependency.setVisibility(4);
                }
            }
        });
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            animatorSet.addListener(arrayList2.get(i));
        }
        return animatorSet;
    }

    protected abstract FabTransformationSpec onCreateMotionSpec(Context context, boolean z);
}
