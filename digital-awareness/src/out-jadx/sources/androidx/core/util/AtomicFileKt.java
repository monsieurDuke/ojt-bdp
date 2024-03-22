package androidx.core.util;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* compiled from: 005A.java */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\u0016\u0010\u0003\u001a\u00020\u0004*\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a3\u0010\u0007\u001a\u00020\b*\u00020\u00022!\u0010\t\u001a\u001d\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\b0\nH\u0087\bø\u0001\u0000\u001a\u0014\u0010\u000f\u001a\u00020\b*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0001H\u0007\u001a\u001e\u0010\u0011\u001a\u00020\b*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"readBytes", HttpUrl.FRAGMENT_ENCODE_SET, "Landroid/util/AtomicFile;", "readText", HttpUrl.FRAGMENT_ENCODE_SET, "charset", "Ljava/nio/charset/Charset;", "tryWrite", HttpUrl.FRAGMENT_ENCODE_SET, "block", "Lkotlin/Function1;", "Ljava/io/FileOutputStream;", "Lkotlin/ParameterName;", "name", "out", "writeBytes", "array", "writeText", "text", "core-ktx_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AtomicFileKt {
    public static final byte[] readBytes(android.util.AtomicFile $this$readBytes) {
        Intrinsics.checkNotNullParameter($this$readBytes, "<this>");
        byte[] readFully = $this$readBytes.readFully();
        Intrinsics.checkNotNullExpressionValue(readFully, "readFully()");
        return readFully;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String readText(android.util.AtomicFile atomicFile, Charset charset) {
        Intrinsics.checkNotNullParameter(atomicFile, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] readFully = atomicFile.readFully();
        Intrinsics.checkNotNullExpressionValue(readFully, "readFully()");
        String str = new String(readFully, charset);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        return str;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static /* synthetic */ String readText$default(android.util.AtomicFile atomicFile, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        String readText = readText(atomicFile, charset);
        Log5ECF72.a(readText);
        LogE84000.a(readText);
        Log229316.a(readText);
        return readText;
    }

    public static final void tryWrite(android.util.AtomicFile $this$tryWrite, Function1<? super FileOutputStream, Unit> block) {
        Intrinsics.checkNotNullParameter($this$tryWrite, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        FileOutputStream stream = $this$tryWrite.startWrite();
        try {
            Intrinsics.checkNotNullExpressionValue(stream, "stream");
            block.invoke(stream);
            InlineMarker.finallyStart(1);
            $this$tryWrite.finishWrite(stream);
            InlineMarker.finallyEnd(1);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            $this$tryWrite.failWrite(stream);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public static final void writeBytes(android.util.AtomicFile $this$writeBytes, byte[] array) {
        Intrinsics.checkNotNullParameter($this$writeBytes, "<this>");
        Intrinsics.checkNotNullParameter(array, "array");
        FileOutputStream stream = $this$writeBytes.startWrite();
        try {
            Intrinsics.checkNotNullExpressionValue(stream, "stream");
            stream.write(array);
            $this$writeBytes.finishWrite(stream);
        } catch (Throwable th) {
            $this$writeBytes.failWrite(stream);
            throw th;
        }
    }

    public static final void writeText(android.util.AtomicFile $this$writeText, String text, Charset charset) {
        Intrinsics.checkNotNullParameter($this$writeText, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        writeBytes($this$writeText, bytes);
    }

    public static /* synthetic */ void writeText$default(android.util.AtomicFile atomicFile, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        writeText(atomicFile, str, charset);
    }
}
