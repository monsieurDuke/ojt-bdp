package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewParent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

/* loaded from: classes.dex */
public final class MarginPageTransformer implements ViewPager2.PageTransformer {
    private final int mMarginPx;

    public MarginPageTransformer(int marginPx) {
        Preconditions.checkArgumentNonnegative(marginPx, "Margin must be non-negative");
        this.mMarginPx = marginPx;
    }

    private ViewPager2 requireViewPager(View page) {
        ViewParent parent = page.getParent();
        ViewParent parent2 = parent.getParent();
        if ((parent instanceof RecyclerView) && (parent2 instanceof ViewPager2)) {
            return (ViewPager2) parent2;
        }
        throw new IllegalStateException("Expected the page view to be managed by a ViewPager2 instance.");
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View page, float position) {
        ViewPager2 requireViewPager = requireViewPager(page);
        float f = this.mMarginPx * position;
        if (requireViewPager.getOrientation() == 0) {
            page.setTranslationX(requireViewPager.isRtl() ? -f : f);
        } else {
            page.setTranslationY(f);
        }
    }
}