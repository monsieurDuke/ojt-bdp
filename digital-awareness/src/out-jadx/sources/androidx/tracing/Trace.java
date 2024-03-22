package androidx.tracing;

import android.os.Build;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class Trace {
    static final String TAG = "Trace";
    private static Method sAsyncTraceBeginMethod;
    private static Method sAsyncTraceEndMethod;
    private static Method sIsTagEnabledMethod;
    private static Method sTraceCounterMethod;
    private static long sTraceTagApp;

    private Trace() {
    }

    public static void beginAsyncSection(String methodName, int cookie) {
        try {
            if (sAsyncTraceBeginMethod == null) {
                TraceApi29Impl.beginAsyncSection(methodName, cookie);
                return;
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
        beginAsyncSectionFallback(methodName, cookie);
    }

    private static void beginAsyncSectionFallback(String methodName, int cookie) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sAsyncTraceBeginMethod == null) {
                    sAsyncTraceBeginMethod = android.os.Trace.class.getMethod("asyncTraceBegin", Long.TYPE, String.class, Integer.TYPE);
                }
                sAsyncTraceBeginMethod.invoke(null, Long.valueOf(sTraceTagApp), methodName, Integer.valueOf(cookie));
            } catch (Exception e) {
                handleException("asyncTraceBegin", e);
            }
        }
    }

    public static void beginSection(String label) {
        if (Build.VERSION.SDK_INT >= 18) {
            TraceApi18Impl.beginSection(label);
        }
    }

    public static void endAsyncSection(String methodName, int cookie) {
        try {
            if (sAsyncTraceEndMethod == null) {
                TraceApi29Impl.endAsyncSection(methodName, cookie);
                return;
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
        endAsyncSectionFallback(methodName, cookie);
    }

    private static void endAsyncSectionFallback(String methodName, int cookie) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sAsyncTraceEndMethod == null) {
                    sAsyncTraceEndMethod = android.os.Trace.class.getMethod("asyncTraceEnd", Long.TYPE, String.class, Integer.TYPE);
                }
                sAsyncTraceEndMethod.invoke(null, Long.valueOf(sTraceTagApp), methodName, Integer.valueOf(cookie));
            } catch (Exception e) {
                handleException("asyncTraceEnd", e);
            }
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= 18) {
            TraceApi18Impl.endSection();
        }
    }

    private static void handleException(String methodName, Exception exception) {
        if (!(exception instanceof InvocationTargetException)) {
            Log.v(TAG, "Unable to call " + methodName + " via reflection", exception);
            return;
        }
        Throwable cause = exception.getCause();
        if (!(cause instanceof RuntimeException)) {
            throw new RuntimeException(cause);
        }
        throw ((RuntimeException) cause);
    }

    public static boolean isEnabled() {
        try {
            if (sIsTagEnabledMethod == null) {
                return android.os.Trace.isEnabled();
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
        return isEnabledFallback();
    }

    private static boolean isEnabledFallback() {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sIsTagEnabledMethod == null) {
                    sTraceTagApp = android.os.Trace.class.getField("TRACE_TAG_APP").getLong(null);
                    sIsTagEnabledMethod = android.os.Trace.class.getMethod("isTagEnabled", Long.TYPE);
                }
                return ((Boolean) sIsTagEnabledMethod.invoke(null, Long.valueOf(sTraceTagApp))).booleanValue();
            } catch (Exception e) {
                handleException("isTagEnabled", e);
            }
        }
        return false;
    }

    public static void setCounter(String counterName, int counterValue) {
        try {
            if (sTraceCounterMethod == null) {
                TraceApi29Impl.setCounter(counterName, counterValue);
                return;
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
        setCounterFallback(counterName, counterValue);
    }

    private static void setCounterFallback(String counterName, int counterValue) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sTraceCounterMethod == null) {
                    sTraceCounterMethod = android.os.Trace.class.getMethod("traceCounter", Long.TYPE, String.class, Integer.TYPE);
                }
                sTraceCounterMethod.invoke(null, Long.valueOf(sTraceTagApp), counterName, Integer.valueOf(counterValue));
            } catch (Exception e) {
                handleException("traceCounter", e);
            }
        }
    }
}
