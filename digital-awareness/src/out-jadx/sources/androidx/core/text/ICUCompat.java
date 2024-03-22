package androidx.core.text;

import android.icu.util.ULocale;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 0056.java */
/* loaded from: classes.dex */
public final class ICUCompat {
    private static final String TAG = "ICUCompat";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    /* loaded from: classes.dex */
    static class Api21Impl {
        private Api21Impl() {
        }

        static String getScript(Locale locale) {
            return locale.getScript();
        }
    }

    /* loaded from: classes.dex */
    static class Api24Impl {
        private Api24Impl() {
        }

        static ULocale addLikelySubtags(Object loc) {
            return ULocale.addLikelySubtags((ULocale) loc);
        }

        static ULocale forLocale(Locale loc) {
            return ULocale.forLocale(loc);
        }

        static String getScript(Object uLocale) {
            return ((ULocale) uLocale).getScript();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT < 24) {
                try {
                    sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", Locale.class);
                    return;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return;
        }
        try {
            Class<?> cls = Class.forName("libcore.icu.ICU");
            sGetScriptMethod = cls.getMethod("getScript", String.class);
            sAddLikelySubtagsMethod = cls.getMethod("addLikelySubtags", String.class);
        } catch (Exception e2) {
            sGetScriptMethod = null;
            sAddLikelySubtagsMethod = null;
            Log.w(TAG, e2);
        }
    }

    private ICUCompat() {
    }

    private static String addLikelySubtagsBelowApi21(Locale locale) {
        String locale2 = locale.toString();
        try {
            Method method = sAddLikelySubtagsMethod;
            if (method != null) {
                return (String) method.invoke(null, locale2);
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2);
        }
        return locale2;
    }

    private static String getScriptBelowApi21(String localeStr) {
        try {
            Method method = sGetScriptMethod;
            if (method != null) {
                return (String) method.invoke(null, localeStr);
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2);
        }
        return null;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String maximizeAndGetScript(Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            String script = Api24Impl.getScript(Api24Impl.addLikelySubtags(Api24Impl.forLocale(locale)));
            Log5ECF72.a(script);
            LogE84000.a(script);
            Log229316.a(script);
            return script;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                String script2 = Api21Impl.getScript((Locale) sAddLikelySubtagsMethod.invoke(null, locale));
                Log5ECF72.a(script2);
                LogE84000.a(script2);
                Log229316.a(script2);
                return script2;
            } catch (IllegalAccessException e) {
                Log.w(TAG, e);
                String script3 = Api21Impl.getScript(locale);
                Log5ECF72.a(script3);
                LogE84000.a(script3);
                Log229316.a(script3);
                return script3;
            } catch (InvocationTargetException e2) {
                Log.w(TAG, e2);
                String script32 = Api21Impl.getScript(locale);
                Log5ECF72.a(script32);
                LogE84000.a(script32);
                Log229316.a(script32);
                return script32;
            }
        }
        String addLikelySubtagsBelowApi21 = addLikelySubtagsBelowApi21(locale);
        Log5ECF72.a(addLikelySubtagsBelowApi21);
        LogE84000.a(addLikelySubtagsBelowApi21);
        Log229316.a(addLikelySubtagsBelowApi21);
        if (addLikelySubtagsBelowApi21 == null) {
            return null;
        }
        String scriptBelowApi21 = getScriptBelowApi21(addLikelySubtagsBelowApi21);
        Log5ECF72.a(scriptBelowApi21);
        LogE84000.a(scriptBelowApi21);
        Log229316.a(scriptBelowApi21);
        return scriptBelowApi21;
    }
}
