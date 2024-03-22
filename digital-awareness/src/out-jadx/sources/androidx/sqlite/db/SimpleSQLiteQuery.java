package androidx.sqlite.db;

/* loaded from: classes.dex */
public final class SimpleSQLiteQuery implements SupportSQLiteQuery {
    private final Object[] mBindArgs;
    private final String mQuery;

    public SimpleSQLiteQuery(String query) {
        this(query, null);
    }

    public SimpleSQLiteQuery(String query, Object[] bindArgs) {
        this.mQuery = query;
        this.mBindArgs = bindArgs;
    }

    private static void bind(SupportSQLiteProgram statement, int index, Object arg) {
        if (arg == null) {
            statement.bindNull(index);
            return;
        }
        if (arg instanceof byte[]) {
            statement.bindBlob(index, (byte[]) arg);
            return;
        }
        if (arg instanceof Float) {
            statement.bindDouble(index, ((Float) arg).floatValue());
            return;
        }
        if (arg instanceof Double) {
            statement.bindDouble(index, ((Double) arg).doubleValue());
            return;
        }
        if (arg instanceof Long) {
            statement.bindLong(index, ((Long) arg).longValue());
            return;
        }
        if (arg instanceof Integer) {
            statement.bindLong(index, ((Integer) arg).intValue());
            return;
        }
        if (arg instanceof Short) {
            statement.bindLong(index, ((Short) arg).shortValue());
            return;
        }
        if (arg instanceof Byte) {
            statement.bindLong(index, ((Byte) arg).byteValue());
        } else if (arg instanceof String) {
            statement.bindString(index, (String) arg);
        } else {
            if (!(arg instanceof Boolean)) {
                throw new IllegalArgumentException("Cannot bind " + arg + " at index " + index + " Supported types: null, byte[], float, double, long, int, short, byte, string");
            }
            statement.bindLong(index, ((Boolean) arg).booleanValue() ? 1L : 0L);
        }
    }

    public static void bind(SupportSQLiteProgram statement, Object[] bindArgs) {
        if (bindArgs == null) {
            return;
        }
        int length = bindArgs.length;
        for (int i = 0; i < length; i++) {
            bind(statement, i + 1, bindArgs[i]);
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public void bindTo(SupportSQLiteProgram statement) {
        bind(statement, this.mBindArgs);
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public int getArgCount() {
        Object[] objArr = this.mBindArgs;
        if (objArr == null) {
            return 0;
        }
        return objArr.length;
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public String getSql() {
        return this.mQuery;
    }
}
