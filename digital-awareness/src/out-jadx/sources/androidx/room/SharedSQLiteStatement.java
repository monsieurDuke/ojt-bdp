package androidx.room;

import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public abstract class SharedSQLiteStatement {
    private final RoomDatabase mDatabase;
    private final AtomicBoolean mLock = new AtomicBoolean(false);
    private volatile SupportSQLiteStatement mStmt;

    public SharedSQLiteStatement(RoomDatabase database) {
        this.mDatabase = database;
    }

    private SupportSQLiteStatement createNewStatement() {
        return this.mDatabase.compileStatement(createQuery());
    }

    private SupportSQLiteStatement getStmt(boolean canUseCached) {
        if (!canUseCached) {
            return createNewStatement();
        }
        if (this.mStmt == null) {
            this.mStmt = createNewStatement();
        }
        return this.mStmt;
    }

    public SupportSQLiteStatement acquire() {
        assertNotMainThread();
        return getStmt(this.mLock.compareAndSet(false, true));
    }

    protected void assertNotMainThread() {
        this.mDatabase.assertNotMainThread();
    }

    protected abstract String createQuery();

    public void release(SupportSQLiteStatement statement) {
        if (statement == this.mStmt) {
            this.mLock.set(false);
        }
    }
}