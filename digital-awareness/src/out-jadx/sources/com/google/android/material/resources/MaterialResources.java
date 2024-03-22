package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R;

/* loaded from: classes.dex */
public class MaterialResources {
    private static final float FONT_SCALE_1_3 = 1.3f;
    private static final float FONT_SCALE_2_0 = 2.0f;

    private MaterialResources() {
    }

    public static ColorStateList getColorStateList(Context context, TypedArray attributes, int index) {
        int color;
        int resourceId;
        ColorStateList colorStateList;
        return (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0 || (colorStateList = AppCompatResources.getColorStateList(context, resourceId)) == null) ? (Build.VERSION.SDK_INT > 15 || (color = attributes.getColor(index, -1)) == -1) ? attributes.getColorStateList(index) : ColorStateList.valueOf(color) : colorStateList;
    }

    public static ColorStateList getColorStateList(Context context, TintTypedArray attributes, int index) {
        int color;
        int resourceId;
        ColorStateList colorStateList;
        return (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0 || (colorStateList = AppCompatResources.getColorStateList(context, resourceId)) == null) ? (Build.VERSION.SDK_INT > 15 || (color = attributes.getColor(index, -1)) == -1) ? attributes.getColorStateList(index) : ColorStateList.valueOf(color) : colorStateList;
    }

    private static int getComplexUnit(TypedValue tv) {
        return Build.VERSION.SDK_INT >= 22 ? tv.getComplexUnit() : (tv.data >> 0) & 15;
    }

    public static int getDimensionPixelSize(Context context, TypedArray attributes, int index, int defaultValue) {
        TypedValue typedValue = new TypedValue();
        if (!attributes.getValue(index, typedValue) || typedValue.type != 2) {
            return attributes.getDimensionPixelSize(index, defaultValue);
        }
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{typedValue.data});
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, defaultValue);
        obtainStyledAttributes.recycle();
        return dimensionPixelSize;
    }

    public static Drawable getDrawable(Context context, TypedArray attributes, int index) {
        int resourceId;
        Drawable drawable;
        return (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0 || (drawable = AppCompatResources.getDrawable(context, resourceId)) == null) ? attributes.getDrawable(index) : drawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getIndexWithValue(TypedArray attributes, int a, int b) {
        return attributes.hasValue(a) ? a : b;
    }

    public static TextAppearance getTextAppearance(Context context, TypedArray attributes, int index) {
        int resourceId;
        if (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0) {
            return null;
        }
        return new TextAppearance(context, resourceId);
    }

    public static int getUnscaledTextSize(Context context, int textAppearance, int defValue) {
        if (textAppearance == 0) {
            return defValue;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(textAppearance, R.styleable.TextAppearance);
        TypedValue typedValue = new TypedValue();
        boolean value = obtainStyledAttributes.getValue(R.styleable.TextAppearance_android_textSize, typedValue);
        obtainStyledAttributes.recycle();
        return !value ? defValue : getComplexUnit(typedValue) == 2 ? Math.round(TypedValue.complexToFloat(typedValue.data) * context.getResources().getDisplayMetrics().density) : TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
    }

    public static boolean isFontScaleAtLeast1_3(Context context) {
        return context.getResources().getConfiguration().fontScale >= FONT_SCALE_1_3;
    }

    public static boolean isFontScaleAtLeast2_0(Context context) {
        return context.getResources().getConfiguration().fontScale >= FONT_SCALE_2_0;
    }
}
