package com.google.android.material.elevation;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;

/* loaded from: classes.dex */
public class ElevationOverlayProvider {
    private static final float FORMULA_MULTIPLIER = 4.5f;
    private static final float FORMULA_OFFSET = 2.0f;
    private static final int OVERLAY_ACCENT_COLOR_ALPHA = (int) Math.round(5.1000000000000005d);
    private final int colorSurface;
    private final float displayDensity;
    private final int elevationOverlayAccentColor;
    private final int elevationOverlayColor;
    private final boolean elevationOverlayEnabled;

    public ElevationOverlayProvider(Context context) {
        this(MaterialAttributes.resolveBoolean(context, R.attr.elevationOverlayEnabled, false), MaterialColors.getColor(context, R.attr.elevationOverlayColor, 0), MaterialColors.getColor(context, R.attr.elevationOverlayAccentColor, 0), MaterialColors.getColor(context, R.attr.MT_Bin, 0), context.getResources().getDisplayMetrics().density);
    }

    public ElevationOverlayProvider(boolean elevationOverlayEnabled, int elevationOverlayColor, int elevationOverlayAccentColor, int colorSurface, float displayDensity) {
        this.elevationOverlayEnabled = elevationOverlayEnabled;
        this.elevationOverlayColor = elevationOverlayColor;
        this.elevationOverlayAccentColor = elevationOverlayAccentColor;
        this.colorSurface = colorSurface;
        this.displayDensity = displayDensity;
    }

    private boolean isThemeSurfaceColor(int color) {
        return ColorUtils.setAlphaComponent(color, 255) == this.colorSurface;
    }

    public int calculateOverlayAlpha(float elevation) {
        return Math.round(calculateOverlayAlphaFraction(elevation) * 255.0f);
    }

    public float calculateOverlayAlphaFraction(float elevation) {
        if (this.displayDensity <= 0.0f || elevation <= 0.0f) {
            return 0.0f;
        }
        return Math.min(((((float) Math.log1p(elevation / r0)) * FORMULA_MULTIPLIER) + FORMULA_OFFSET) / 100.0f, 1.0f);
    }

    public int compositeOverlay(int backgroundColor, float elevation) {
        int i;
        float calculateOverlayAlphaFraction = calculateOverlayAlphaFraction(elevation);
        int alpha = Color.alpha(backgroundColor);
        int layer = MaterialColors.layer(ColorUtils.setAlphaComponent(backgroundColor, 255), this.elevationOverlayColor, calculateOverlayAlphaFraction);
        if (calculateOverlayAlphaFraction > 0.0f && (i = this.elevationOverlayAccentColor) != 0) {
            layer = MaterialColors.layer(layer, ColorUtils.setAlphaComponent(i, OVERLAY_ACCENT_COLOR_ALPHA));
        }
        return ColorUtils.setAlphaComponent(layer, alpha);
    }

    public int compositeOverlay(int backgroundColor, float elevation, View overlayView) {
        return compositeOverlay(backgroundColor, elevation + getParentAbsoluteElevation(overlayView));
    }

    public int compositeOverlayIfNeeded(int backgroundColor, float elevation) {
        return (this.elevationOverlayEnabled && isThemeSurfaceColor(backgroundColor)) ? compositeOverlay(backgroundColor, elevation) : backgroundColor;
    }

    public int compositeOverlayIfNeeded(int backgroundColor, float elevation, View overlayView) {
        return compositeOverlayIfNeeded(backgroundColor, elevation + getParentAbsoluteElevation(overlayView));
    }

    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float elevation) {
        return compositeOverlayIfNeeded(this.colorSurface, elevation);
    }

    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float elevation, View overlayView) {
        return compositeOverlayWithThemeSurfaceColorIfNeeded(elevation + getParentAbsoluteElevation(overlayView));
    }

    public float getParentAbsoluteElevation(View overlayView) {
        return ViewUtils.getParentAbsoluteElevation(overlayView);
    }

    public int getThemeElevationOverlayColor() {
        return this.elevationOverlayColor;
    }

    public int getThemeSurfaceColor() {
        return this.colorSurface;
    }

    public boolean isThemeElevationOverlayEnabled() {
        return this.elevationOverlayEnabled;
    }
}
