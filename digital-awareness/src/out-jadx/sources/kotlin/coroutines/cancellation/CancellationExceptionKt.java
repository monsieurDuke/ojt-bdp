package kotlin.coroutines.cancellation;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import okhttp3.HttpUrl;

/* compiled from: CancellationException.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a!\u0010\u0000\u001a\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00060\u0001j\u0002`\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b*\u001a\b\u0007\u0010\u0000\"\u00020\u00012\u00020\u0001B\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t¨\u0006\n"}, d2 = {"CancellationException", "Ljava/util/concurrent/CancellationException;", "Lkotlin/coroutines/cancellation/CancellationException;", "message", HttpUrl.FRAGMENT_ENCODE_SET, "cause", HttpUrl.FRAGMENT_ENCODE_SET, "Lkotlin/SinceKotlin;", "version", "1.4", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CancellationExceptionKt {
    private static final CancellationException CancellationException(String message, Throwable cause) {
        CancellationException cancellationException = new CancellationException(message);
        cancellationException.initCause(cause);
        return cancellationException;
    }

    private static final CancellationException CancellationException(Throwable cause) {
        CancellationException cancellationException = new CancellationException(cause != null ? cause.toString() : null);
        cancellationException.initCause(cause);
        return cancellationException;
    }

    public static /* synthetic */ void CancellationException$annotations() {
    }
}
