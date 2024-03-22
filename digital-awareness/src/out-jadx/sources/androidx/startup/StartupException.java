package androidx.startup;

/* loaded from: classes.dex */
public final class StartupException extends RuntimeException {
    public StartupException(String message) {
        super(message);
    }

    public StartupException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public StartupException(Throwable throwable) {
        super(throwable);
    }
}
