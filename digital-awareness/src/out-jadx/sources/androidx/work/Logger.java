package androidx.work;

import android.util.Log;

/* loaded from: classes.dex */
public abstract class Logger {
    private static final int MAX_TAG_LENGTH = 23;
    private static Logger sLogger;
    private static final String TAG_PREFIX = "WM-";
    private static final int MAX_PREFIXED_TAG_LENGTH = 23 - TAG_PREFIX.length();

    /* loaded from: classes.dex */
    public static class LogcatLogger extends Logger {
        private int mLoggingLevel;

        public LogcatLogger(int loggingLevel) {
            super(loggingLevel);
            this.mLoggingLevel = loggingLevel;
        }

        @Override // androidx.work.Logger
        public void debug(String tag, String message, Throwable... throwables) {
            if (this.mLoggingLevel <= 3) {
                if (throwables == null || throwables.length < 1) {
                    Log.d(tag, message);
                } else {
                    Log.d(tag, message, throwables[0]);
                }
            }
        }

        @Override // androidx.work.Logger
        public void error(String tag, String message, Throwable... throwables) {
            if (this.mLoggingLevel <= 6) {
                if (throwables == null || throwables.length < 1) {
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, throwables[0]);
                }
            }
        }

        @Override // androidx.work.Logger
        public void info(String tag, String message, Throwable... throwables) {
            if (this.mLoggingLevel <= 4) {
                if (throwables == null || throwables.length < 1) {
                    Log.i(tag, message);
                } else {
                    Log.i(tag, message, throwables[0]);
                }
            }
        }

        @Override // androidx.work.Logger
        public void verbose(String tag, String message, Throwable... throwables) {
            if (this.mLoggingLevel <= 2) {
                if (throwables == null || throwables.length < 1) {
                    Log.v(tag, message);
                } else {
                    Log.v(tag, message, throwables[0]);
                }
            }
        }

        @Override // androidx.work.Logger
        public void warning(String tag, String message, Throwable... throwables) {
            if (this.mLoggingLevel <= 5) {
                if (throwables == null || throwables.length < 1) {
                    Log.w(tag, message);
                } else {
                    Log.w(tag, message, throwables[0]);
                }
            }
        }
    }

    public Logger(int loggingLevel) {
    }

    public static synchronized Logger get() {
        Logger logger;
        synchronized (Logger.class) {
            if (sLogger == null) {
                sLogger = new LogcatLogger(3);
            }
            logger = sLogger;
        }
        return logger;
    }

    public static synchronized void setLogger(Logger logger) {
        synchronized (Logger.class) {
            sLogger = logger;
        }
    }

    public static String tagWithPrefix(String tag) {
        int length = tag.length();
        StringBuilder sb = new StringBuilder(23);
        sb.append(TAG_PREFIX);
        int i = MAX_PREFIXED_TAG_LENGTH;
        if (length >= i) {
            sb.append(tag.substring(0, i));
        } else {
            sb.append(tag);
        }
        return sb.toString();
    }

    public abstract void debug(String tag, String message, Throwable... throwables);

    public abstract void error(String tag, String message, Throwable... throwables);

    public abstract void info(String tag, String message, Throwable... throwables);

    public abstract void verbose(String tag, String message, Throwable... throwables);

    public abstract void warning(String tag, String message, Throwable... throwables);
}
