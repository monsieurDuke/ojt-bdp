package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.tabs.TabLayout;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class TabIndicatorInterpolator {
    private static final int MIN_INDICATOR_WIDTH = 24;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static RectF calculateIndicatorWidthForTab(TabLayout tabLayout, View tab) {
        return tab == null ? new RectF() : (tabLayout.isTabIndicatorFullWidth() || !(tab instanceof TabLayout.TabView)) ? new RectF(tab.getLeft(), tab.getTop(), tab.getRight(), tab.getBottom()) : calculateTabViewContentBounds((TabLayout.TabView) tab, 24);
    }

    static RectF calculateTabViewContentBounds(TabLayout.TabView tabView, int minWidth) {
        int contentWidth = tabView.getContentWidth();
        int contentHeight = tabView.getContentHeight();
        int dpToPx = (int) ViewUtils.dpToPx(tabView.getContext(), minWidth);
        if (contentWidth < dpToPx) {
            contentWidth = dpToPx;
        }
        int left = (tabView.getLeft() + tabView.getRight()) / 2;
        int top = (tabView.getTop() + tabView.getBottom()) / 2;
        return new RectF(left - (contentWidth / 2), top - (contentHeight / 2), (contentWidth / 2) + left, (left / 2) + top);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIndicatorBoundsForTab(TabLayout tabLayout, View tab, Drawable indicator) {
        RectF calculateIndicatorWidthForTab = calculateIndicatorWidthForTab(tabLayout, tab);
        indicator.setBounds((int) calculateIndicatorWidthForTab.left, indicator.getBounds().top, (int) calculateIndicatorWidthForTab.right, indicator.getBounds().bottom);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateIndicatorForOffset(TabLayout tabLayout, View startTitle, View endTitle, float offset, Drawable indicator) {
        RectF calculateIndicatorWidthForTab = calculateIndicatorWidthForTab(tabLayout, startTitle);
        RectF calculateIndicatorWidthForTab2 = calculateIndicatorWidthForTab(tabLayout, endTitle);
        indicator.setBounds(AnimationUtils.lerp((int) calculateIndicatorWidthForTab.left, (int) calculateIndicatorWidthForTab2.left, offset), indicator.getBounds().top, AnimationUtils.lerp((int) calculateIndicatorWidthForTab.right, (int) calculateIndicatorWidthForTab2.right, offset), indicator.getBounds().bottom);
    }
}
