package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ElasticTabIndicatorInterpolator extends TabIndicatorInterpolator {
    private static float accInterp(float fraction) {
        return (float) (1.0d - Math.cos((fraction * 3.141592653589793d) / 2.0d));
    }

    private static float decInterp(float fraction) {
        return (float) Math.sin((fraction * 3.141592653589793d) / 2.0d);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.TabIndicatorInterpolator
    public void updateIndicatorForOffset(TabLayout tabLayout, View startTitle, View endTitle, float offset, Drawable indicator) {
        float decInterp;
        float accInterp;
        RectF calculateIndicatorWidthForTab = calculateIndicatorWidthForTab(tabLayout, startTitle);
        RectF calculateIndicatorWidthForTab2 = calculateIndicatorWidthForTab(tabLayout, endTitle);
        if (calculateIndicatorWidthForTab.left < calculateIndicatorWidthForTab2.left) {
            decInterp = accInterp(offset);
            accInterp = decInterp(offset);
        } else {
            decInterp = decInterp(offset);
            accInterp = accInterp(offset);
        }
        indicator.setBounds(AnimationUtils.lerp((int) calculateIndicatorWidthForTab.left, (int) calculateIndicatorWidthForTab2.left, decInterp), indicator.getBounds().top, AnimationUtils.lerp((int) calculateIndicatorWidthForTab.right, (int) calculateIndicatorWidthForTab2.right, accInterp), indicator.getBounds().bottom);
    }
}
