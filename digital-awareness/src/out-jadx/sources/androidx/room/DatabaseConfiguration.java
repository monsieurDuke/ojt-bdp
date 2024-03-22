package androidx.room;

import android.content.Context;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class DatabaseConfiguration {
    public final boolean allowDestructiveMigrationOnDowngrade;
    public final boolean allowMainThreadQueries;
    public final List<RoomDatabase.Callback> callbacks;
    public final Context context;
    public final String copyFromAssetPath;
    public final File copyFromFile;
    public final RoomDatabase.JournalMode journalMode;
    private final Set<Integer> mMigrationNotRequiredFrom;
    public final RoomDatabase.MigrationContainer migrationContainer;
    public final boolean multiInstanceInvalidation;
    public final String name;
    public final Executor queryExecutor;
    public final boolean requireMigration;
    public final SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory;
    public final Executor transactionExecutor;

    @Deprecated
    public DatabaseConfiguration(Context context, String name, SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory, RoomDatabase.MigrationContainer migrationContainer, List<RoomDatabase.Callback> list, boolean allowMainThreadQueries, RoomDatabase.JournalMode journalMode, Executor queryExecutor, Executor transactionExecutor, boolean multiInstanceInvalidation, boolean requireMigration, boolean allowDestructiveMigrationOnDowngrade, Set<Integer> set) {
        this(context, name, sqliteOpenHelperFactory, migrationContainer, list, allowMainThreadQueries, journalMode, queryExecutor, transactionExecutor, multiInstanceInvalidation, requireMigration, allowDestructiveMigrationOnDowngrade, set, null, null);
    }

    public DatabaseConfiguration(Context context, String name, SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory, RoomDatabase.MigrationContainer migrationContainer, List<RoomDatabase.Callback> list, boolean allowMainThreadQueries, RoomDatabase.JournalMode journalMode, Executor queryExecutor, Executor transactionExecutor, boolean multiInstanceInvalidation, boolean requireMigration, boolean allowDestructiveMigrationOnDowngrade, Set<Integer> set, String copyFromAssetPath, File copyFromFile) {
        this.sqliteOpenHelperFactory = sqliteOpenHelperFactory;
        this.context = context;
        this.name = name;
        this.migrationContainer = migrationContainer;
        this.callbacks = list;
        this.allowMainThreadQueries = allowMainThreadQueries;
        this.journalMode = journalMode;
        this.queryExecutor = queryExecutor;
        this.transactionExecutor = transactionExecutor;
        this.multiInstanceInvalidation = multiInstanceInvalidation;
        this.requireMigration = requireMigration;
        this.allowDestructiveMigrationOnDowngrade = allowDestructiveMigrationOnDowngrade;
        this.mMigrationNotRequiredFrom = set;
        this.copyFromAssetPath = copyFromAssetPath;
        this.copyFromFile = copyFromFile;
    }

    @Deprecated
    public DatabaseConfiguration(Context context, String name, SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory, RoomDatabase.MigrationContainer migrationContainer, List<RoomDatabase.Callback> list, boolean allowMainThreadQueries, RoomDatabase.JournalMode journalMode, Executor queryExecutor, boolean requireMigration, Set<Integer> set) {
        this(context, name, sqliteOpenHelperFactory, migrationContainer, list, allowMainThreadQueries, journalMode, queryExecutor, queryExecutor, false, requireMigration, false, set, null, null);
    }

    public boolean isMigrationRequired(int fromVersion, int toVersion) {
        Set<Integer> set;
        if ((fromVersion > toVersion) && this.allowDestructiveMigrationOnDowngrade) {
            return false;
        }
        return this.requireMigration && ((set = this.mMigrationNotRequiredFrom) == null || !set.contains(Integer.valueOf(fromVersion)));
    }

    @Deprecated
    public boolean isMigrationRequiredFrom(int version) {
        return isMigrationRequired(version, version + 1);
    }
}
