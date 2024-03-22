package androidx.core.net;

import android.net.Uri;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.text.Typography;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 0046.java */
/* loaded from: classes.dex */
public final class MailTo {
    private static final String BCC = "bcc";
    private static final String BODY = "body";
    private static final String CC = "cc";
    private static final String MAILTO = "mailto";
    public static final String MAILTO_SCHEME = "mailto:";
    private static final String SUBJECT = "subject";
    private static final String TO = "to";
    private HashMap<String, String> mHeaders = new HashMap<>();

    private MailTo() {
    }

    public static boolean isMailTo(Uri uri) {
        return uri != null && MAILTO.equals(uri.getScheme());
    }

    public static boolean isMailTo(String uri) {
        return uri != null && uri.startsWith(MAILTO_SCHEME);
    }

    public static MailTo parse(Uri uri) throws ParseException {
        return parse(uri.toString());
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static MailTo parse(String str) throws ParseException {
        String decode;
        String substring;
        String str2;
        Preconditions.checkNotNull(str);
        if (!isMailTo(str)) {
            throw new ParseException("Not a mailto scheme");
        }
        int indexOf = str.indexOf(35);
        if (indexOf != -1) {
            str = str.substring(0, indexOf);
        }
        int indexOf2 = str.indexOf(63);
        if (indexOf2 == -1) {
            decode = Uri.decode(str.substring(MAILTO_SCHEME.length()));
            Log5ECF72.a(decode);
            LogE84000.a(decode);
            Log229316.a(decode);
            substring = null;
        } else {
            decode = Uri.decode(str.substring(MAILTO_SCHEME.length(), indexOf2));
            Log5ECF72.a(decode);
            LogE84000.a(decode);
            Log229316.a(decode);
            substring = str.substring(indexOf2 + 1);
        }
        MailTo mailTo = new MailTo();
        if (substring != null) {
            for (String str3 : substring.split("&")) {
                String[] split = str3.split("=", 2);
                if (split.length != 0) {
                    String decode2 = Uri.decode(split[0]);
                    Log5ECF72.a(decode2);
                    LogE84000.a(decode2);
                    Log229316.a(decode2);
                    String lowerCase = decode2.toLowerCase(Locale.ROOT);
                    if (split.length > 1) {
                        str2 = Uri.decode(split[1]);
                        Log5ECF72.a(str2);
                        LogE84000.a(str2);
                        Log229316.a(str2);
                    } else {
                        str2 = null;
                    }
                    mailTo.mHeaders.put(lowerCase, str2);
                }
            }
        }
        String to = mailTo.getTo();
        if (to != null) {
            decode = decode + ", " + to;
        }
        mailTo.mHeaders.put("to", decode);
        return mailTo;
    }

    public String getBcc() {
        return this.mHeaders.get(BCC);
    }

    public String getBody() {
        return this.mHeaders.get(BODY);
    }

    public String getCc() {
        return this.mHeaders.get(CC);
    }

    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public String getSubject() {
        return this.mHeaders.get(SUBJECT);
    }

    public String getTo() {
        return this.mHeaders.get("to");
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(MAILTO_SCHEME);
        sb.append('?');
        for (Map.Entry<String, String> entry : this.mHeaders.entrySet()) {
            String encode = Uri.encode(entry.getKey());
            Log5ECF72.a(encode);
            LogE84000.a(encode);
            Log229316.a(encode);
            sb.append(encode);
            sb.append('=');
            String encode2 = Uri.encode(entry.getValue());
            Log5ECF72.a(encode2);
            LogE84000.a(encode2);
            Log229316.a(encode2);
            sb.append(encode2);
            sb.append(Typography.amp);
        }
        return sb.toString();
    }
}
