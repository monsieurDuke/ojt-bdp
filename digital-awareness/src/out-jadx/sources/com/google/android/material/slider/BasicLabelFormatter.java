package com.google.android.material.slider;

import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 00FC.java */
/* loaded from: classes.dex */
public final class BasicLabelFormatter implements LabelFormatter {
    private static final int BILLION = 1000000000;
    private static final int MILLION = 1000000;
    private static final int THOUSAND = 1000;
    private static final long TRILLION = 1000000000000L;

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // com.google.android.material.slider.LabelFormatter
    public String getFormattedValue(float f) {
        if (f >= 1.0E12f) {
            String format = String.format(Locale.US, "%.1fT", Float.valueOf(f / 1.0E12f));
            Log5ECF72.a(format);
            LogE84000.a(format);
            Log229316.a(format);
            return format;
        }
        if (f >= 1.0E9f) {
            String format2 = String.format(Locale.US, "%.1fB", Float.valueOf(f / 1.0E9f));
            Log5ECF72.a(format2);
            LogE84000.a(format2);
            Log229316.a(format2);
            return format2;
        }
        if (f >= 1000000.0f) {
            String format3 = String.format(Locale.US, "%.1fM", Float.valueOf(f / 1000000.0f));
            Log5ECF72.a(format3);
            LogE84000.a(format3);
            Log229316.a(format3);
            return format3;
        }
        if (f >= 1000.0f) {
            String format4 = String.format(Locale.US, "%.1fK", Float.valueOf(f / 1000.0f));
            Log5ECF72.a(format4);
            LogE84000.a(format4);
            Log229316.a(format4);
            return format4;
        }
        String format5 = String.format(Locale.US, "%.0f", Float.valueOf(f));
        Log5ECF72.a(format5);
        LogE84000.a(format5);
        Log229316.a(format5);
        return format5;
    }
}
