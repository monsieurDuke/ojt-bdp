package kotlin.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: 0133.java */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u000b\u001a\u00020\f*\u00020\bH\u0002¢\u0006\u0002\b\r\u001a\u001c\u0010\u000e\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0000\u001a\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0002H\u0000\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0002*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\b*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"isRooted", HttpUrl.FRAGMENT_ENCODE_SET, "Ljava/io/File;", "(Ljava/io/File;)Z", "root", "getRoot", "(Ljava/io/File;)Ljava/io/File;", "rootName", HttpUrl.FRAGMENT_ENCODE_SET, "getRootName", "(Ljava/io/File;)Ljava/lang/String;", "getRootLength", HttpUrl.FRAGMENT_ENCODE_SET, "getRootLength$FilesKt__FilePathComponentsKt", "subPath", "beginIndex", "endIndex", "toComponents", "Lkotlin/io/FilePathComponents;", "kotlin-stdlib"}, k = 5, mv = {1, 7, 1}, xi = 49, xs = "kotlin/io/FilesKt")
/* loaded from: classes.dex */
public class FilesKt__FilePathComponentsKt {
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final File getRoot(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        String rootName = FilesKt.getRootName(file);
        Log5ECF72.a(rootName);
        LogE84000.a(rootName);
        Log229316.a(rootName);
        return new File(rootName);
    }

    private static final int getRootLength$FilesKt__FilePathComponentsKt(String $this$getRootLength) {
        int indexOf$default;
        int indexOf$default2 = StringsKt.indexOf$default((CharSequence) $this$getRootLength, File.separatorChar, 0, false, 4, (Object) null);
        if (indexOf$default2 == 0) {
            if ($this$getRootLength.length() <= 1 || $this$getRootLength.charAt(1) != File.separatorChar || (indexOf$default = StringsKt.indexOf$default((CharSequence) $this$getRootLength, File.separatorChar, 2, false, 4, (Object) null)) < 0) {
                return 1;
            }
            int indexOf$default3 = StringsKt.indexOf$default((CharSequence) $this$getRootLength, File.separatorChar, indexOf$default + 1, false, 4, (Object) null);
            return indexOf$default3 >= 0 ? indexOf$default3 + 1 : $this$getRootLength.length();
        }
        if (indexOf$default2 > 0 && $this$getRootLength.charAt(indexOf$default2 - 1) == ':') {
            return indexOf$default2 + 1;
        }
        if (indexOf$default2 == -1 && StringsKt.endsWith$default((CharSequence) $this$getRootLength, ':', false, 2, (Object) null)) {
            return $this$getRootLength.length();
        }
        return 0;
    }

    public static final String getRootName(File $this$rootName) {
        Intrinsics.checkNotNullParameter($this$rootName, "<this>");
        String path = $this$rootName.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "path");
        String path2 = $this$rootName.getPath();
        Intrinsics.checkNotNullExpressionValue(path2, "path");
        String substring = path.substring(0, getRootLength$FilesKt__FilePathComponentsKt(path2));
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static final boolean isRooted(File $this$isRooted) {
        Intrinsics.checkNotNullParameter($this$isRooted, "<this>");
        String path = $this$isRooted.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "path");
        return getRootLength$FilesKt__FilePathComponentsKt(path) > 0;
    }

    public static final File subPath(File $this$subPath, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$subPath, "<this>");
        return FilesKt.toComponents($this$subPath).subPath(beginIndex, endIndex);
    }

    public static final FilePathComponents toComponents(File $this$toComponents) {
        List list;
        Intrinsics.checkNotNullParameter($this$toComponents, "<this>");
        String path = $this$toComponents.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "path");
        int rootLength$FilesKt__FilePathComponentsKt = getRootLength$FilesKt__FilePathComponentsKt(path);
        String substring = path.substring(0, rootLength$FilesKt__FilePathComponentsKt);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        String substring2 = path.substring(rootLength$FilesKt__FilePathComponentsKt);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
        if (substring2.length() == 0) {
            list = CollectionsKt.emptyList();
        } else {
            List split$default = StringsKt.split$default((CharSequence) substring2, new char[]{File.separatorChar}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(split$default, 10));
            Iterator it = split$default.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            list = arrayList;
        }
        return new FilePathComponents(new File(substring), list);
    }
}