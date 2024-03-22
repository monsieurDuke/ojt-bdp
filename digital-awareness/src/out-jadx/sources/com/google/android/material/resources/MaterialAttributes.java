package com.google.android.material.resources;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import com.google.android.material.R;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 00F9.java */
/* loaded from: classes.dex */
public class MaterialAttributes {
    public static TypedValue resolve(Context context, int attributeResId) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    public static boolean resolveBoolean(Context context, int attributeResId, boolean defaultValue) {
        TypedValue resolve = resolve(context, attributeResId);
        return (resolve == null || resolve.type != 18) ? defaultValue : resolve.data != 0;
    }

    public static boolean resolveBooleanOrThrow(Context context, int attributeResId, String errorMessageComponent) {
        return resolveOrThrow(context, attributeResId, errorMessageComponent) != 0;
    }

    public static int resolveDimension(Context context, int attributeResId, int defaultDimenResId) {
        TypedValue resolve = resolve(context, attributeResId);
        return (resolve == null || resolve.type != 5) ? (int) context.getResources().getDimension(defaultDimenResId) : (int) resolve.getDimension(context.getResources().getDisplayMetrics());
    }

    public static int resolveInteger(Context context, int attributeResId, int defaultValue) {
        TypedValue resolve = resolve(context, attributeResId);
        return (resolve == null || resolve.type != 16) ? defaultValue : resolve.data;
    }

    public static int resolveMinimumAccessibleTouchTarget(Context context) {
        return resolveDimension(context, R.attr.minTouchTargetSize, R.dimen.mtrl_min_touch_target_size);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static int resolveOrThrow(Context context, int i, String str) {
        TypedValue resolve = resolve(context, i);
        if (resolve != null) {
            return resolve.data;
        }
        String format = String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", str, context.getResources().getResourceName(i));
        Log5ECF72.a(format);
        LogE84000.a(format);
        Log229316.a(format);
        throw new IllegalArgumentException(format);
    }

    public static int resolveOrThrow(View componentView, int attributeResId) {
        return resolveOrThrow(componentView.getContext(), attributeResId, componentView.getClass().getCanonicalName());
    }
}
