package mt;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: 01B2.java */
/* loaded from: classes.dex */
public class Log229316 extends Thread {
    private static final SimpleDateFormat TIME_FORMAT1 = new SimpleDateFormat("HH:mm:ss.SSS");
    private static final SimpleDateFormat TIME_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final LinkedBlockingQueue QUEUE = new LinkedBlockingQueue();
    private static final ThreadLocal PARAMETER_BUFFER = new ThreadLocal();

    static {
        Log229316 log229316 = new Log229316();
        log229316.setDaemon(true);
        log229316.start();
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void a(char c) {
        String valueOf = String.valueOf(c);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        z(valueOf);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void a(double d) {
        String valueOf = String.valueOf(d);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        z(valueOf);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void a(float f) {
        String valueOf = String.valueOf(f);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        z(valueOf);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void a(int i) {
        String valueOf = String.valueOf(i);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        z(valueOf);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void a(long j) {
        String valueOf = String.valueOf(j);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        z(valueOf);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void a(Object obj) {
        String y = y(obj);
        Log5ECF72.a(y);
        LogE84000.a(y);
        z(y);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void a(boolean z) {
        String valueOf = String.valueOf(z);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        z(valueOf);
    }

    public static void b() {
        ThreadLocal threadLocal = PARAMETER_BUFFER;
        z(((StringBuilder) threadLocal.get()).toString());
        threadLocal.remove();
    }

    public static void b1(char c) {
        x("Parameter1: " + c);
    }

    public static void b1(double d) {
        x("Parameter1: " + d);
    }

    public static void b1(float f) {
        x("Parameter1: " + f);
    }

    public static void b1(int i) {
        x("Parameter1: " + i);
    }

    public static void b1(long j) {
        x("Parameter1: " + j);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void b1(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter1: ");
        String y = y(obj);
        Log5ECF72.a(y);
        LogE84000.a(y);
        sb.append(y);
        x(sb.toString());
    }

    public static void b1(boolean z) {
        x("Parameter1: " + z);
    }

    public static void b2(char c) {
        x("Parameter2: " + c);
    }

    public static void b2(double d) {
        x("Parameter2: " + d);
    }

    public static void b2(float f) {
        x("Parameter2: " + f);
    }

    public static void b2(int i) {
        x("Parameter2: " + i);
    }

    public static void b2(long j) {
        x("Parameter2: " + j);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void b2(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter2: ");
        String y = y(obj);
        Log5ECF72.a(y);
        LogE84000.a(y);
        sb.append(y);
        x(sb.toString());
    }

    public static void b2(boolean z) {
        x("Parameter2: " + z);
    }

    public static void b3(char c) {
        x("Parameter3: " + c);
    }

    public static void b3(double d) {
        x("Parameter3: " + d);
    }

    public static void b3(float f) {
        x("Parameter3: " + f);
    }

    public static void b3(int i) {
        x("Parameter3: " + i);
    }

    public static void b3(long j) {
        x("Parameter3: " + j);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void b3(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter3: ");
        String y = y(obj);
        Log5ECF72.a(y);
        LogE84000.a(y);
        sb.append(y);
        x(sb.toString());
    }

    public static void b3(boolean z) {
        x("Parameter3: " + z);
    }

    public static void b4(char c) {
        x("Parameter4: " + c);
    }

    public static void b4(double d) {
        x("Parameter4: " + d);
    }

    public static void b4(float f) {
        x("Parameter4: " + f);
    }

    public static void b4(int i) {
        x("Parameter4: " + i);
    }

    public static void b4(long j) {
        x("Parameter4: " + j);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void b4(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter4: ");
        String y = y(obj);
        Log5ECF72.a(y);
        LogE84000.a(y);
        sb.append(y);
        x(sb.toString());
    }

    public static void b4(boolean z) {
        x("Parameter4: " + z);
    }

    public static void b5(char c) {
        x("Parameter5: " + c);
    }

    public static void b5(double d) {
        x("Parameter5: " + d);
    }

    public static void b5(float f) {
        x("Parameter5: " + f);
    }

    public static void b5(int i) {
        x("Parameter5: " + i);
    }

    public static void b5(long j) {
        x("Parameter5: " + j);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void b5(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter5: ");
        String y = y(obj);
        Log5ECF72.a(y);
        LogE84000.a(y);
        sb.append(y);
        x(sb.toString());
    }

    public static void b5(boolean z) {
        x("Parameter5: " + z);
    }

    public static void b6(char c) {
        x("Parameter6: " + c);
    }

    public static void b6(double d) {
        x("Parameter6: " + d);
    }

    public static void b6(float f) {
        x("Parameter6: " + f);
    }

    public static void b6(int i) {
        x("Parameter6: " + i);
    }

    public static void b6(long j) {
        x("Parameter6: " + j);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void b6(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter6: ");
        String y = y(obj);
        Log5ECF72.a(y);
        LogE84000.a(y);
        sb.append(y);
        x(sb.toString());
    }

    public static void b6(boolean z) {
        x("Parameter6: " + z);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static void printStackTrace() {
        String stackTraceString = Log.getStackTraceString(new Exception("InjectedLog.printStackTrace"));
        Log5ECF72.a(stackTraceString);
        LogE84000.a(stackTraceString);
        z(stackTraceString);
    }

    private static void x(String str) {
        ThreadLocal threadLocal = PARAMETER_BUFFER;
        StringBuilder sb = (StringBuilder) threadLocal.get();
        if (sb == null) {
            sb = new StringBuilder();
            threadLocal.set(sb);
        }
        if (sb.length() > 0) {
            sb.append('\n');
        }
        sb.append(str);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    private static String y(Object obj) {
        if (obj == null) {
            return "null";
        }
        Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            return obj.toString();
        }
        if (cls == byte[].class) {
            String arrays = Arrays.toString((byte[]) obj);
            Log5ECF72.a(arrays);
            LogE84000.a(arrays);
            return arrays;
        }
        if (cls == short[].class) {
            String arrays2 = Arrays.toString((short[]) obj);
            Log5ECF72.a(arrays2);
            LogE84000.a(arrays2);
            return arrays2;
        }
        if (cls == int[].class) {
            String arrays3 = Arrays.toString((int[]) obj);
            Log5ECF72.a(arrays3);
            LogE84000.a(arrays3);
            return arrays3;
        }
        if (cls == long[].class) {
            String arrays4 = Arrays.toString((long[]) obj);
            Log5ECF72.a(arrays4);
            LogE84000.a(arrays4);
            return arrays4;
        }
        if (cls == char[].class) {
            String arrays5 = Arrays.toString((char[]) obj);
            Log5ECF72.a(arrays5);
            LogE84000.a(arrays5);
            return arrays5;
        }
        if (cls == float[].class) {
            String arrays6 = Arrays.toString((float[]) obj);
            Log5ECF72.a(arrays6);
            LogE84000.a(arrays6);
            return arrays6;
        }
        if (cls == double[].class) {
            String arrays7 = Arrays.toString((double[]) obj);
            Log5ECF72.a(arrays7);
            LogE84000.a(arrays7);
            return arrays7;
        }
        if (cls == boolean[].class) {
            String arrays8 = Arrays.toString((boolean[]) obj);
            Log5ECF72.a(arrays8);
            LogE84000.a(arrays8);
            return arrays8;
        }
        String deepToString = Arrays.deepToString((Object[]) obj);
        Log5ECF72.a(deepToString);
        LogE84000.a(deepToString);
        return deepToString;
    }

    private static void z(String str) {
        String replace = "[TIME][CLASS][METHOD][LOCATION][RESULT]\n[LOCATION][METHOD][CLASS][LOCATION]\n".contains("[TIME]") ? "[TIME][CLASS][METHOD][LOCATION][RESULT]\n[LOCATION][METHOD][CLASS][LOCATION]\n".replace("[TIME]", TIME_FORMAT1.format(Long.valueOf(System.currentTimeMillis()))) : "[TIME][CLASS][METHOD][LOCATION][RESULT]\n[LOCATION][METHOD][CLASS][LOCATION]\n";
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        String fileName = stackTraceElement.getFileName();
        if (fileName == null) {
            fileName = "Unknown Source";
        }
        int lineNumber = stackTraceElement.getLineNumber();
        if (lineNumber >= 0) {
            fileName = fileName + ":" + lineNumber;
        }
        QUEUE.offer(replace.replace("[RESULT]", str).replace("[CLASS]", stackTraceElement.getClassName()).replace("[METHOD]", stackTraceElement.getMethodName()).replace("[LOCATION]", fileName));
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        FileOutputStream fileOutputStream = null;
        IOException iOException = null;
        try {
            File file = new File("[SDCARD]/MT2/logs/[PACKAGE]-[TIME].log".replace("[SDCARD]", Environment.getExternalStorageDirectory().getPath()).replace("[PACKAGE]", "com.google.cuap").replace("[TIME]", TIME_FORMAT2.format(Long.valueOf(System.currentTimeMillis()))).replace('\\', '/').replace("//", "/"));
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            fileOutputStream = new FileOutputStream(file, true);
        } catch (IOException e) {
            e.printStackTrace();
            iOException = e;
        }
        if (fileOutputStream == null) {
            try {
                File file2 = new File("/data/data/com.google.cuap/logs");
                File file3 = new File(file2, TIME_FORMAT2.format(Long.valueOf(System.currentTimeMillis())) + ".log");
                file2.mkdirs();
                fileOutputStream = new FileOutputStream(file3);
            } catch (IOException e2) {
                e2.printStackTrace();
                throw new RuntimeException(iOException);
            }
        }
        try {
            Charset defaultCharset = Charset.defaultCharset();
            while (true) {
                LinkedBlockingQueue linkedBlockingQueue = QUEUE;
                fileOutputStream.write(((String) linkedBlockingQueue.take()).getBytes(defaultCharset));
                if (linkedBlockingQueue.isEmpty()) {
                    fileOutputStream.flush();
                }
            }
        } catch (Exception e3) {
            throw new RuntimeException(e3);
        }
    }
}
