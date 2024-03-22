package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class SlideDistanceProvider implements VisibilityAnimatorProvider {
    private static final int DEFAULT_DISTANCE = -1;
    private int slideDistance = -1;
    private int slideEdge;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface GravityFlag {
    }

    public SlideDistanceProvider(int slideEdge) {
        this.slideEdge = slideEdge;
    }

    private static Animator createTranslationAppearAnimator(View sceneRoot, View view, int slideEdge, int slideDistance) {
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        switch (slideEdge) {
            case 3:
                return createTranslationXAnimator(view, slideDistance + translationX, translationX, translationX);
            case 5:
                return createTranslationXAnimator(view, translationX - slideDistance, translationX, translationX);
            case 48:
                return createTranslationYAnimator(view, translationY - slideDistance, translationY, translationY);
            case 80:
                return createTranslationYAnimator(view, slideDistance + translationY, translationY, translationY);
            case GravityCompat.START /* 8388611 */:
                return createTranslationXAnimator(view, isRtl(sceneRoot) ? slideDistance + translationX : translationX - slideDistance, translationX, translationX);
            case GravityCompat.END /* 8388613 */:
                return createTranslationXAnimator(view, isRtl(sceneRoot) ? translationX - slideDistance : slideDistance + translationX, translationX, translationX);
            default:
                throw new IllegalArgumentException("Invalid slide direction: " + slideEdge);
        }
    }

    private static Animator createTranslationDisappearAnimator(View sceneRoot, View view, int slideEdge, int slideDistance) {
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        switch (slideEdge) {
            case 3:
                return createTranslationXAnimator(view, translationX, translationX - slideDistance, translationX);
            case 5:
                return createTranslationXAnimator(view, translationX, slideDistance + translationX, translationX);
            case 48:
                return createTranslationYAnimator(view, translationY, slideDistance + translationY, translationY);
            case 80:
                return createTranslationYAnimator(view, translationY, translationY - slideDistance, translationY);
            case GravityCompat.START /* 8388611 */:
                return createTranslationXAnimator(view, translationX, isRtl(sceneRoot) ? translationX - slideDistance : slideDistance + translationX, translationX);
            case GravityCompat.END /* 8388613 */:
                return createTranslationXAnimator(view, translationX, isRtl(sceneRoot) ? slideDistance + translationX : translationX - slideDistance, translationX);
            default:
                throw new IllegalArgumentException("Invalid slide direction: " + slideEdge);
        }
    }

    private static Animator createTranslationXAnimator(final View view, float startTranslation, float endTranslation, final float originalTranslation) {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_X, startTranslation, endTranslation));
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.platform.SlideDistanceProvider.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                view.setTranslationX(originalTranslation);
            }
        });
        return ofPropertyValuesHolder;
    }

    private static Animator createTranslationYAnimator(final View view, float startTranslation, float endTranslation, final float originalTranslation) {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, startTranslation, endTranslation));
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.platform.SlideDistanceProvider.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                view.setTranslationY(originalTranslation);
            }
        });
        return ofPropertyValuesHolder;
    }

    private int getSlideDistanceOrDefault(Context context) {
        int i = this.slideDistance;
        return i != -1 ? i : context.getResources().getDimensionPixelSize(R.dimen.mtrl_transition_shared_axis_slide_distance);
    }

    private static boolean isRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    public Animator createAppear(ViewGroup sceneRoot, View view) {
        return createTranslationAppearAnimator(sceneRoot, view, this.slideEdge, getSlideDistanceOrDefault(view.getContext()));
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    public Animator createDisappear(ViewGroup sceneRoot, View view) {
        return createTranslationDisappearAnimator(sceneRoot, view, this.slideEdge, getSlideDistanceOrDefault(view.getContext()));
    }

    public int getSlideDistance() {
        return this.slideDistance;
    }

    public int getSlideEdge() {
        return this.slideEdge;
    }

    public void setSlideDistance(int slideDistance) {
        if (slideDistance < 0) {
            throw new IllegalArgumentException("Slide distance must be positive. If attempting to reverse the direction of the slide, use setSlideEdge(int) instead.");
        }
        this.slideDistance = slideDistance;
    }

    public void setSlideEdge(int slideEdge) {
        this.slideEdge = slideEdge;
    }
}
