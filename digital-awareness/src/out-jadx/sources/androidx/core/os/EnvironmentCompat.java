package androidx.core.os;

import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 0049.java */
/* loaded from: classes.dex */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    /* compiled from: 0047.java */
    /* loaded from: classes.dex */
    static class Api19Impl {
        private Api19Impl() {
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        static String getStorageState(File file) {
            String storageState = Environment.getStorageState(file);
            Log5ECF72.a(storageState);
            LogE84000.a(storageState);
            Log229316.a(storageState);
            return storageState;
        }
    }

    /* compiled from: 0048.java */
    /* loaded from: classes.dex */
    static class Api21Impl {
        private Api21Impl() {
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        static String getExternalStorageState(File file) {
            String externalStorageState = Environment.getExternalStorageState(file);
            Log5ECF72.a(externalStorageState);
            LogE84000.a(externalStorageState);
            Log229316.a(externalStorageState);
            return externalStorageState;
        }
    }

    private EnvironmentCompat() {
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String getStorageState(File file) {
        if (Build.VERSION.SDK_INT >= 21) {
            String externalStorageState = Api21Impl.getExternalStorageState(file);
            Log5ECF72.a(externalStorageState);
            LogE84000.a(externalStorageState);
            Log229316.a(externalStorageState);
            return externalStorageState;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            String storageState = Api19Impl.getStorageState(file);
            Log5ECF72.a(storageState);
            LogE84000.a(storageState);
            Log229316.a(storageState);
            return storageState;
        }
        try {
            if (!file.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) {
                return MEDIA_UNKNOWN;
            }
            String externalStorageState2 = Environment.getExternalStorageState();
            Log5ECF72.a(externalStorageState2);
            LogE84000.a(externalStorageState2);
            Log229316.a(externalStorageState2);
            return externalStorageState2;
        } catch (IOException e) {
            Log.w(TAG, "Failed to resolve canonical path: " + e);
            return MEDIA_UNKNOWN;
        }
    }
}
