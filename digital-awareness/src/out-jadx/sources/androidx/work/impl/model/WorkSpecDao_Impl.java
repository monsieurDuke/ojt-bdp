package androidx.work.impl.model;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class WorkSpecDao_Impl implements WorkSpecDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<WorkSpec> __insertionAdapterOfWorkSpec;
    private final SharedSQLiteStatement __preparedStmtOfDelete;
    private final SharedSQLiteStatement __preparedStmtOfIncrementWorkSpecRunAttemptCount;
    private final SharedSQLiteStatement __preparedStmtOfMarkWorkSpecScheduled;
    private final SharedSQLiteStatement __preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast;
    private final SharedSQLiteStatement __preparedStmtOfResetScheduledState;
    private final SharedSQLiteStatement __preparedStmtOfResetWorkSpecRunAttemptCount;
    private final SharedSQLiteStatement __preparedStmtOfSetOutput;
    private final SharedSQLiteStatement __preparedStmtOfSetPeriodStartTime;

    public WorkSpecDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfWorkSpec = new EntityInsertionAdapter<WorkSpec>(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.1
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, WorkSpec workSpec) {
                if (workSpec.id == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, workSpec.id);
                }
                supportSQLiteStatement.bindLong(2, WorkTypeConverters.stateToInt(workSpec.state));
                if (workSpec.workerClassName == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, workSpec.workerClassName);
                }
                if (workSpec.inputMergerClassName == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, workSpec.inputMergerClassName);
                }
                byte[] byteArrayInternal = Data.toByteArrayInternal(workSpec.input);
                if (byteArrayInternal == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindBlob(5, byteArrayInternal);
                }
                byte[] byteArrayInternal2 = Data.toByteArrayInternal(workSpec.output);
                if (byteArrayInternal2 == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindBlob(6, byteArrayInternal2);
                }
                supportSQLiteStatement.bindLong(7, workSpec.initialDelay);
                supportSQLiteStatement.bindLong(8, workSpec.intervalDuration);
                supportSQLiteStatement.bindLong(9, workSpec.flexDuration);
                supportSQLiteStatement.bindLong(10, workSpec.runAttemptCount);
                supportSQLiteStatement.bindLong(11, WorkTypeConverters.backoffPolicyToInt(workSpec.backoffPolicy));
                supportSQLiteStatement.bindLong(12, workSpec.backoffDelayDuration);
                supportSQLiteStatement.bindLong(13, workSpec.periodStartTime);
                supportSQLiteStatement.bindLong(14, workSpec.minimumRetentionDuration);
                supportSQLiteStatement.bindLong(15, workSpec.scheduleRequestedAt);
                supportSQLiteStatement.bindLong(16, workSpec.expedited ? 1L : 0L);
                supportSQLiteStatement.bindLong(17, WorkTypeConverters.outOfQuotaPolicyToInt(workSpec.outOfQuotaPolicy));
                Constraints constraints = workSpec.constraints;
                if (constraints == null) {
                    supportSQLiteStatement.bindNull(18);
                    supportSQLiteStatement.bindNull(19);
                    supportSQLiteStatement.bindNull(20);
                    supportSQLiteStatement.bindNull(21);
                    supportSQLiteStatement.bindNull(22);
                    supportSQLiteStatement.bindNull(23);
                    supportSQLiteStatement.bindNull(24);
                    supportSQLiteStatement.bindNull(25);
                    return;
                }
                supportSQLiteStatement.bindLong(18, WorkTypeConverters.networkTypeToInt(constraints.getRequiredNetworkType()));
                supportSQLiteStatement.bindLong(19, constraints.requiresCharging() ? 1L : 0L);
                supportSQLiteStatement.bindLong(20, constraints.requiresDeviceIdle() ? 1L : 0L);
                supportSQLiteStatement.bindLong(21, constraints.requiresBatteryNotLow() ? 1L : 0L);
                supportSQLiteStatement.bindLong(22, constraints.requiresStorageNotLow() ? 1L : 0L);
                supportSQLiteStatement.bindLong(23, constraints.getTriggerContentUpdateDelay());
                supportSQLiteStatement.bindLong(24, constraints.getTriggerMaxContentDelay());
                byte[] contentUriTriggersToByteArray = WorkTypeConverters.contentUriTriggersToByteArray(constraints.getContentUriTriggers());
                if (contentUriTriggersToByteArray == null) {
                    supportSQLiteStatement.bindNull(25);
                } else {
                    supportSQLiteStatement.bindBlob(25, contentUriTriggersToByteArray);
                }
            }

            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR IGNORE INTO `WorkSpec` (`id`,`state`,`worker_class_name`,`input_merger_class_name`,`input`,`output`,`initial_delay`,`interval_duration`,`flex_duration`,`run_attempt_count`,`backoff_policy`,`backoff_delay_duration`,`period_start_time`,`minimum_retention_duration`,`schedule_requested_at`,`run_in_foreground`,`out_of_quota_policy`,`required_network_type`,`requires_charging`,`requires_device_idle`,`requires_battery_not_low`,`requires_storage_not_low`,`trigger_content_update_delay`,`trigger_max_content_delay`,`content_uri_triggers`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }
        };
        this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM workspec WHERE id=?";
            }
        };
        this.__preparedStmtOfSetOutput = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET output=? WHERE id=?";
            }
        };
        this.__preparedStmtOfSetPeriodStartTime = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET period_start_time=? WHERE id=?";
            }
        };
        this.__preparedStmtOfIncrementWorkSpecRunAttemptCount = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET run_attempt_count=run_attempt_count+1 WHERE id=?";
            }
        };
        this.__preparedStmtOfResetWorkSpecRunAttemptCount = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET run_attempt_count=0 WHERE id=?";
            }
        };
        this.__preparedStmtOfMarkWorkSpecScheduled = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.7
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET schedule_requested_at=? WHERE id=?";
            }
        };
        this.__preparedStmtOfResetScheduledState = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.8
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET schedule_requested_at=-1 WHERE state NOT IN (2, 3, 5)";
            }
        };
        this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.9
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void __fetchRelationshipWorkProgressAsandroidxWorkData(final ArrayMap<String, ArrayList<Data>> _map) {
        ArrayList<Data> arrayList;
        Set<String> keySet = _map.keySet();
        if (keySet.isEmpty()) {
            return;
        }
        if (_map.size() > 999) {
            ArrayMap<String, ArrayList<Data>> arrayMap = new ArrayMap<>((int) RoomDatabase.MAX_BIND_PARAMETER_CNT);
            int i = 0;
            int i2 = 0;
            int size = _map.size();
            while (i2 < size) {
                arrayMap.put(_map.keyAt(i2), _map.valueAt(i2));
                i2++;
                i++;
                if (i == 999) {
                    __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap);
                    arrayMap = new ArrayMap<>((int) RoomDatabase.MAX_BIND_PARAMETER_CNT);
                    i = 0;
                }
            }
            if (i > 0) {
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap);
                return;
            }
            return;
        }
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT `progress`,`work_spec_id` FROM `WorkProgress` WHERE `work_spec_id` IN (");
        int size2 = keySet.size();
        StringUtil.appendPlaceholders(newStringBuilder, size2);
        newStringBuilder.append(")");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size2 + 0);
        int i3 = 1;
        for (String str : keySet) {
            if (str == null) {
                acquire.bindNull(i3);
            } else {
                acquire.bindString(i3, str);
            }
            i3++;
        }
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndex = CursorUtil.getColumnIndex(query, "work_spec_id");
            if (columnIndex == -1) {
                return;
            }
            while (query.moveToNext()) {
                if (!query.isNull(columnIndex) && (arrayList = _map.get(query.getString(columnIndex))) != null) {
                    arrayList.add(Data.fromByteArray(query.getBlob(0)));
                }
            }
        } finally {
            query.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void __fetchRelationshipWorkTagAsjavaLangString(final ArrayMap<String, ArrayList<String>> _map) {
        ArrayList<String> arrayList;
        Set<String> keySet = _map.keySet();
        if (keySet.isEmpty()) {
            return;
        }
        if (_map.size() > 999) {
            ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>((int) RoomDatabase.MAX_BIND_PARAMETER_CNT);
            int i = 0;
            int i2 = 0;
            int size = _map.size();
            while (i2 < size) {
                arrayMap.put(_map.keyAt(i2), _map.valueAt(i2));
                i2++;
                i++;
                if (i == 999) {
                    __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                    arrayMap = new ArrayMap<>((int) RoomDatabase.MAX_BIND_PARAMETER_CNT);
                    i = 0;
                }
            }
            if (i > 0) {
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                return;
            }
            return;
        }
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT `tag`,`work_spec_id` FROM `WorkTag` WHERE `work_spec_id` IN (");
        int size2 = keySet.size();
        StringUtil.appendPlaceholders(newStringBuilder, size2);
        newStringBuilder.append(")");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size2 + 0);
        int i3 = 1;
        for (String str : keySet) {
            if (str == null) {
                acquire.bindNull(i3);
            } else {
                acquire.bindString(i3, str);
            }
            i3++;
        }
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndex = CursorUtil.getColumnIndex(query, "work_spec_id");
            if (columnIndex == -1) {
                return;
            }
            while (query.moveToNext()) {
                if (!query.isNull(columnIndex) && (arrayList = _map.get(query.getString(columnIndex))) != null) {
                    arrayList.add(query.getString(0));
                }
            }
        } finally {
            query.close();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void delete(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfDelete.acquire();
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDelete.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getAllEligibleWorkSpecsForScheduling(final int maxLimit) {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        boolean z;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 ORDER BY period_start_time LIMIT ?", 1);
        acquire.bindLong(1, maxLimit);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "required_network_type");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "requires_charging");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "requires_device_idle");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "requires_battery_not_low");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "requires_storage_not_low");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "trigger_content_update_delay");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "trigger_max_content_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "content_uri_triggers");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "id");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "worker_class_name");
            try {
                columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "input_merger_class_name");
                try {
                    columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "input");
                    columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "output");
                    roomSQLiteQuery = acquire;
                } catch (Throwable th) {
                    th = th;
                    roomSQLiteQuery = acquire;
                }
            } catch (Throwable th2) {
                th = th2;
                roomSQLiteQuery = acquire;
            }
        } catch (Throwable th3) {
            th = th3;
            roomSQLiteQuery = acquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "initial_delay");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "interval_duration");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "flex_duration");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(query, "backoff_policy");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(query, "backoff_delay_duration");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(query, "period_start_time");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(query, "minimum_retention_duration");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(query, "schedule_requested_at");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(query, "run_in_foreground");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(query, "out_of_quota_policy");
            int i = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                String string = query.getString(columnIndexOrThrow9);
                String string2 = query.getString(columnIndexOrThrow11);
                Constraints constraints = new Constraints();
                int i2 = columnIndexOrThrow;
                int i3 = columnIndexOrThrow11;
                constraints.setRequiredNetworkType(WorkTypeConverters.intToNetworkType(query.getInt(columnIndexOrThrow)));
                constraints.setRequiresCharging(query.getInt(columnIndexOrThrow2) != 0);
                constraints.setRequiresDeviceIdle(query.getInt(columnIndexOrThrow3) != 0);
                constraints.setRequiresBatteryNotLow(query.getInt(columnIndexOrThrow4) != 0);
                constraints.setRequiresStorageNotLow(query.getInt(columnIndexOrThrow5) != 0);
                int i4 = columnIndexOrThrow2;
                int i5 = columnIndexOrThrow3;
                constraints.setTriggerContentUpdateDelay(query.getLong(columnIndexOrThrow6));
                constraints.setTriggerMaxContentDelay(query.getLong(columnIndexOrThrow7));
                constraints.setContentUriTriggers(WorkTypeConverters.byteArrayToContentUriTriggers(query.getBlob(columnIndexOrThrow8)));
                int i6 = columnIndexOrThrow9;
                WorkSpec workSpec = new WorkSpec(string, string2);
                workSpec.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow10));
                workSpec.inputMergerClassName = query.getString(columnIndexOrThrow12);
                int i7 = columnIndexOrThrow12;
                workSpec.input = Data.fromByteArray(query.getBlob(columnIndexOrThrow13));
                int i8 = i;
                workSpec.output = Data.fromByteArray(query.getBlob(i8));
                int i9 = columnIndexOrThrow15;
                int i10 = columnIndexOrThrow13;
                workSpec.initialDelay = query.getLong(i9);
                int i11 = columnIndexOrThrow16;
                workSpec.intervalDuration = query.getLong(i11);
                int i12 = columnIndexOrThrow17;
                workSpec.flexDuration = query.getLong(i12);
                int i13 = columnIndexOrThrow18;
                workSpec.runAttemptCount = query.getInt(i13);
                int i14 = columnIndexOrThrow19;
                workSpec.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(query.getInt(i14));
                int i15 = columnIndexOrThrow20;
                workSpec.backoffDelayDuration = query.getLong(i15);
                int i16 = columnIndexOrThrow21;
                workSpec.periodStartTime = query.getLong(i16);
                int i17 = columnIndexOrThrow22;
                workSpec.minimumRetentionDuration = query.getLong(i17);
                int i18 = columnIndexOrThrow23;
                workSpec.scheduleRequestedAt = query.getLong(i18);
                int i19 = columnIndexOrThrow24;
                if (query.getInt(i19) != 0) {
                    columnIndexOrThrow24 = i19;
                    z = true;
                } else {
                    columnIndexOrThrow24 = i19;
                    z = false;
                }
                workSpec.expedited = z;
                int i20 = columnIndexOrThrow25;
                workSpec.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(query.getInt(i20));
                workSpec.constraints = constraints;
                arrayList.add(workSpec);
                columnIndexOrThrow17 = i12;
                columnIndexOrThrow18 = i13;
                columnIndexOrThrow20 = i15;
                columnIndexOrThrow21 = i16;
                columnIndexOrThrow11 = i3;
                columnIndexOrThrow = i2;
                columnIndexOrThrow2 = i4;
                columnIndexOrThrow3 = i5;
                columnIndexOrThrow9 = i6;
                columnIndexOrThrow12 = i7;
                i = i8;
                columnIndexOrThrow19 = i14;
                columnIndexOrThrow25 = i20;
                columnIndexOrThrow23 = i18;
                columnIndexOrThrow13 = i10;
                columnIndexOrThrow15 = i9;
                columnIndexOrThrow16 = i11;
                columnIndexOrThrow22 = i17;
            }
            query.close();
            roomSQLiteQuery.release();
            return arrayList;
        } catch (Throwable th4) {
            th = th4;
            query.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getAllUnfinishedWork() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5)", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(query.getString(0));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getAllWorkSpecIds() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(query.getString(0));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<String>> getAllWorkSpecIdsLiveData() {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"workspec"}, true, new Callable<List<String>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.10
            @Override // java.util.concurrent.Callable
            public List<String> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor query = DBUtil.query(WorkSpecDao_Impl.this.__db, acquire, false, null);
                    try {
                        ArrayList arrayList = new ArrayList(query.getCount());
                        while (query.moveToNext()) {
                            arrayList.add(query.getString(0));
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        query.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getEligibleWorkForScheduling(final int schedulerLimit) {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        boolean z;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at=-1 ORDER BY period_start_time LIMIT (SELECT MAX(?-COUNT(*), 0) FROM workspec WHERE schedule_requested_at<>-1 AND state NOT IN (2, 3, 5))", 1);
        acquire.bindLong(1, schedulerLimit);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "required_network_type");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "requires_charging");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "requires_device_idle");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "requires_battery_not_low");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "requires_storage_not_low");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "trigger_content_update_delay");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "trigger_max_content_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "content_uri_triggers");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "id");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "worker_class_name");
            try {
                columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "input_merger_class_name");
                try {
                    columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "input");
                    columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "output");
                    roomSQLiteQuery = acquire;
                } catch (Throwable th) {
                    th = th;
                    roomSQLiteQuery = acquire;
                }
            } catch (Throwable th2) {
                th = th2;
                roomSQLiteQuery = acquire;
            }
        } catch (Throwable th3) {
            th = th3;
            roomSQLiteQuery = acquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "initial_delay");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "interval_duration");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "flex_duration");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(query, "backoff_policy");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(query, "backoff_delay_duration");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(query, "period_start_time");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(query, "minimum_retention_duration");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(query, "schedule_requested_at");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(query, "run_in_foreground");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(query, "out_of_quota_policy");
            int i = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                String string = query.getString(columnIndexOrThrow9);
                String string2 = query.getString(columnIndexOrThrow11);
                Constraints constraints = new Constraints();
                int i2 = columnIndexOrThrow;
                int i3 = columnIndexOrThrow11;
                constraints.setRequiredNetworkType(WorkTypeConverters.intToNetworkType(query.getInt(columnIndexOrThrow)));
                constraints.setRequiresCharging(query.getInt(columnIndexOrThrow2) != 0);
                constraints.setRequiresDeviceIdle(query.getInt(columnIndexOrThrow3) != 0);
                constraints.setRequiresBatteryNotLow(query.getInt(columnIndexOrThrow4) != 0);
                constraints.setRequiresStorageNotLow(query.getInt(columnIndexOrThrow5) != 0);
                int i4 = columnIndexOrThrow2;
                int i5 = columnIndexOrThrow3;
                constraints.setTriggerContentUpdateDelay(query.getLong(columnIndexOrThrow6));
                constraints.setTriggerMaxContentDelay(query.getLong(columnIndexOrThrow7));
                constraints.setContentUriTriggers(WorkTypeConverters.byteArrayToContentUriTriggers(query.getBlob(columnIndexOrThrow8)));
                int i6 = columnIndexOrThrow9;
                WorkSpec workSpec = new WorkSpec(string, string2);
                workSpec.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow10));
                workSpec.inputMergerClassName = query.getString(columnIndexOrThrow12);
                int i7 = columnIndexOrThrow12;
                workSpec.input = Data.fromByteArray(query.getBlob(columnIndexOrThrow13));
                int i8 = i;
                workSpec.output = Data.fromByteArray(query.getBlob(i8));
                int i9 = columnIndexOrThrow15;
                int i10 = columnIndexOrThrow13;
                workSpec.initialDelay = query.getLong(i9);
                int i11 = columnIndexOrThrow16;
                workSpec.intervalDuration = query.getLong(i11);
                int i12 = columnIndexOrThrow17;
                workSpec.flexDuration = query.getLong(i12);
                int i13 = columnIndexOrThrow18;
                workSpec.runAttemptCount = query.getInt(i13);
                int i14 = columnIndexOrThrow19;
                workSpec.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(query.getInt(i14));
                int i15 = columnIndexOrThrow20;
                workSpec.backoffDelayDuration = query.getLong(i15);
                int i16 = columnIndexOrThrow21;
                workSpec.periodStartTime = query.getLong(i16);
                int i17 = columnIndexOrThrow22;
                workSpec.minimumRetentionDuration = query.getLong(i17);
                int i18 = columnIndexOrThrow23;
                workSpec.scheduleRequestedAt = query.getLong(i18);
                int i19 = columnIndexOrThrow24;
                if (query.getInt(i19) != 0) {
                    columnIndexOrThrow24 = i19;
                    z = true;
                } else {
                    columnIndexOrThrow24 = i19;
                    z = false;
                }
                workSpec.expedited = z;
                int i20 = columnIndexOrThrow25;
                workSpec.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(query.getInt(i20));
                workSpec.constraints = constraints;
                arrayList.add(workSpec);
                columnIndexOrThrow17 = i12;
                columnIndexOrThrow18 = i13;
                columnIndexOrThrow20 = i15;
                columnIndexOrThrow21 = i16;
                columnIndexOrThrow11 = i3;
                columnIndexOrThrow = i2;
                columnIndexOrThrow2 = i4;
                columnIndexOrThrow3 = i5;
                columnIndexOrThrow9 = i6;
                columnIndexOrThrow12 = i7;
                i = i8;
                columnIndexOrThrow19 = i14;
                columnIndexOrThrow25 = i20;
                columnIndexOrThrow23 = i18;
                columnIndexOrThrow13 = i10;
                columnIndexOrThrow15 = i9;
                columnIndexOrThrow16 = i11;
                columnIndexOrThrow22 = i17;
            }
            query.close();
            roomSQLiteQuery.release();
            return arrayList;
        } catch (Throwable th4) {
            th = th4;
            query.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<Data> getInputsFromPrerequisites(final String id) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT output FROM workspec WHERE id IN (SELECT prerequisite_id FROM dependency WHERE work_spec_id=?)", 1);
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(Data.fromByteArray(query.getBlob(0)));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getRecentlyCompletedWork(final long startingAt) {
        RoomSQLiteQuery roomSQLiteQuery;
        int i;
        boolean z;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE period_start_time >= ? AND state IN (2, 3, 5) ORDER BY period_start_time DESC", 1);
        acquire.bindLong(1, startingAt);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "required_network_type");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "requires_charging");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "requires_device_idle");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "requires_battery_not_low");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "requires_storage_not_low");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "trigger_content_update_delay");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "trigger_max_content_delay");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "content_uri_triggers");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            try {
                int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "worker_class_name");
                try {
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "input_merger_class_name");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "input");
                    int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "output");
                    roomSQLiteQuery = acquire;
                    try {
                        int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "initial_delay");
                        int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "interval_duration");
                        int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "flex_duration");
                        int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                        int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(query, "backoff_policy");
                        int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(query, "backoff_delay_duration");
                        int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(query, "period_start_time");
                        int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(query, "minimum_retention_duration");
                        int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(query, "schedule_requested_at");
                        int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(query, "run_in_foreground");
                        int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(query, "out_of_quota_policy");
                        int i2 = columnIndexOrThrow14;
                        ArrayList arrayList = new ArrayList(query.getCount());
                        while (query.moveToNext()) {
                            String string = query.getString(columnIndexOrThrow9);
                            String string2 = query.getString(columnIndexOrThrow11);
                            Constraints constraints = new Constraints();
                            int i3 = columnIndexOrThrow;
                            int i4 = columnIndexOrThrow11;
                            constraints.setRequiredNetworkType(WorkTypeConverters.intToNetworkType(query.getInt(columnIndexOrThrow)));
                            constraints.setRequiresCharging(query.getInt(columnIndexOrThrow2) != 0);
                            constraints.setRequiresDeviceIdle(query.getInt(columnIndexOrThrow3) != 0);
                            constraints.setRequiresBatteryNotLow(query.getInt(columnIndexOrThrow4) != 0);
                            constraints.setRequiresStorageNotLow(query.getInt(columnIndexOrThrow5) != 0);
                            int i5 = columnIndexOrThrow2;
                            int i6 = columnIndexOrThrow3;
                            constraints.setTriggerContentUpdateDelay(query.getLong(columnIndexOrThrow6));
                            constraints.setTriggerMaxContentDelay(query.getLong(columnIndexOrThrow7));
                            constraints.setContentUriTriggers(WorkTypeConverters.byteArrayToContentUriTriggers(query.getBlob(columnIndexOrThrow8)));
                            int i7 = columnIndexOrThrow8;
                            WorkSpec workSpec = new WorkSpec(string, string2);
                            int i8 = columnIndexOrThrow10;
                            workSpec.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow10));
                            workSpec.inputMergerClassName = query.getString(columnIndexOrThrow12);
                            workSpec.input = Data.fromByteArray(query.getBlob(columnIndexOrThrow13));
                            int i9 = i2;
                            workSpec.output = Data.fromByteArray(query.getBlob(i9));
                            int i10 = columnIndexOrThrow13;
                            int i11 = columnIndexOrThrow15;
                            int i12 = columnIndexOrThrow12;
                            workSpec.initialDelay = query.getLong(i11);
                            int i13 = columnIndexOrThrow16;
                            workSpec.intervalDuration = query.getLong(i13);
                            int i14 = columnIndexOrThrow17;
                            int i15 = columnIndexOrThrow9;
                            workSpec.flexDuration = query.getLong(i14);
                            int i16 = columnIndexOrThrow18;
                            workSpec.runAttemptCount = query.getInt(i16);
                            int i17 = columnIndexOrThrow19;
                            workSpec.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(query.getInt(i17));
                            int i18 = columnIndexOrThrow20;
                            workSpec.backoffDelayDuration = query.getLong(i18);
                            int i19 = columnIndexOrThrow21;
                            workSpec.periodStartTime = query.getLong(i19);
                            int i20 = columnIndexOrThrow22;
                            workSpec.minimumRetentionDuration = query.getLong(i20);
                            int i21 = columnIndexOrThrow23;
                            workSpec.scheduleRequestedAt = query.getLong(i21);
                            int i22 = columnIndexOrThrow24;
                            if (query.getInt(i22) != 0) {
                                i = i18;
                                z = true;
                            } else {
                                i = i18;
                                z = false;
                            }
                            workSpec.expedited = z;
                            int i23 = columnIndexOrThrow25;
                            workSpec.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(query.getInt(i23));
                            workSpec.constraints = constraints;
                            arrayList.add(workSpec);
                            i2 = i9;
                            columnIndexOrThrow16 = i13;
                            columnIndexOrThrow18 = i16;
                            columnIndexOrThrow20 = i;
                            columnIndexOrThrow11 = i4;
                            columnIndexOrThrow = i3;
                            columnIndexOrThrow2 = i5;
                            columnIndexOrThrow3 = i6;
                            columnIndexOrThrow8 = i7;
                            columnIndexOrThrow10 = i8;
                            columnIndexOrThrow13 = i10;
                            columnIndexOrThrow25 = i23;
                            columnIndexOrThrow24 = i22;
                            columnIndexOrThrow21 = i19;
                            columnIndexOrThrow12 = i12;
                            columnIndexOrThrow9 = i15;
                            columnIndexOrThrow15 = i11;
                            columnIndexOrThrow19 = i17;
                            columnIndexOrThrow22 = i20;
                            columnIndexOrThrow17 = i14;
                            columnIndexOrThrow23 = i21;
                        }
                        query.close();
                        roomSQLiteQuery.release();
                        return arrayList;
                    } catch (Throwable th) {
                        th = th;
                        query.close();
                        roomSQLiteQuery.release();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    roomSQLiteQuery = acquire;
                }
            } catch (Throwable th3) {
                th = th3;
                roomSQLiteQuery = acquire;
            }
        } catch (Throwable th4) {
            th = th4;
            roomSQLiteQuery = acquire;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getRunningWork() {
        RoomSQLiteQuery roomSQLiteQuery;
        int i;
        boolean z;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "required_network_type");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "requires_charging");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "requires_device_idle");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "requires_battery_not_low");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "requires_storage_not_low");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "trigger_content_update_delay");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "trigger_max_content_delay");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "content_uri_triggers");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "worker_class_name");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "input_merger_class_name");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "input");
            try {
                int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "output");
                roomSQLiteQuery = acquire;
                try {
                    int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "initial_delay");
                    int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "interval_duration");
                    int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "flex_duration");
                    int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                    int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(query, "backoff_policy");
                    int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(query, "backoff_delay_duration");
                    int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(query, "period_start_time");
                    int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(query, "minimum_retention_duration");
                    int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(query, "schedule_requested_at");
                    int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(query, "run_in_foreground");
                    int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(query, "out_of_quota_policy");
                    int i2 = columnIndexOrThrow14;
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        String string = query.getString(columnIndexOrThrow9);
                        String string2 = query.getString(columnIndexOrThrow11);
                        Constraints constraints = new Constraints();
                        int i3 = columnIndexOrThrow;
                        int i4 = columnIndexOrThrow9;
                        constraints.setRequiredNetworkType(WorkTypeConverters.intToNetworkType(query.getInt(columnIndexOrThrow)));
                        int i5 = columnIndexOrThrow2;
                        constraints.setRequiresCharging(query.getInt(columnIndexOrThrow2) != 0);
                        constraints.setRequiresDeviceIdle(query.getInt(columnIndexOrThrow3) != 0);
                        constraints.setRequiresBatteryNotLow(query.getInt(columnIndexOrThrow4) != 0);
                        constraints.setRequiresStorageNotLow(query.getInt(columnIndexOrThrow5) != 0);
                        int i6 = columnIndexOrThrow3;
                        constraints.setTriggerContentUpdateDelay(query.getLong(columnIndexOrThrow6));
                        constraints.setTriggerMaxContentDelay(query.getLong(columnIndexOrThrow7));
                        constraints.setContentUriTriggers(WorkTypeConverters.byteArrayToContentUriTriggers(query.getBlob(columnIndexOrThrow8)));
                        WorkSpec workSpec = new WorkSpec(string, string2);
                        workSpec.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow10));
                        workSpec.inputMergerClassName = query.getString(columnIndexOrThrow12);
                        int i7 = columnIndexOrThrow13;
                        workSpec.input = Data.fromByteArray(query.getBlob(columnIndexOrThrow13));
                        int i8 = i2;
                        workSpec.output = Data.fromByteArray(query.getBlob(i8));
                        int i9 = columnIndexOrThrow15;
                        int i10 = columnIndexOrThrow12;
                        workSpec.initialDelay = query.getLong(i9);
                        int i11 = columnIndexOrThrow4;
                        int i12 = columnIndexOrThrow16;
                        int i13 = columnIndexOrThrow5;
                        workSpec.intervalDuration = query.getLong(i12);
                        int i14 = columnIndexOrThrow17;
                        workSpec.flexDuration = query.getLong(i14);
                        int i15 = columnIndexOrThrow18;
                        workSpec.runAttemptCount = query.getInt(i15);
                        int i16 = columnIndexOrThrow19;
                        workSpec.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(query.getInt(i16));
                        int i17 = columnIndexOrThrow20;
                        workSpec.backoffDelayDuration = query.getLong(i17);
                        int i18 = columnIndexOrThrow21;
                        workSpec.periodStartTime = query.getLong(i18);
                        int i19 = columnIndexOrThrow22;
                        workSpec.minimumRetentionDuration = query.getLong(i19);
                        int i20 = columnIndexOrThrow23;
                        workSpec.scheduleRequestedAt = query.getLong(i20);
                        int i21 = columnIndexOrThrow24;
                        if (query.getInt(i21) != 0) {
                            i = i17;
                            z = true;
                        } else {
                            i = i17;
                            z = false;
                        }
                        workSpec.expedited = z;
                        int i22 = columnIndexOrThrow25;
                        workSpec.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(query.getInt(i22));
                        workSpec.constraints = constraints;
                        arrayList.add(workSpec);
                        columnIndexOrThrow5 = i13;
                        columnIndexOrThrow18 = i15;
                        columnIndexOrThrow16 = i12;
                        columnIndexOrThrow21 = i18;
                        columnIndexOrThrow20 = i;
                        columnIndexOrThrow9 = i4;
                        columnIndexOrThrow = i3;
                        columnIndexOrThrow25 = i22;
                        columnIndexOrThrow2 = i5;
                        columnIndexOrThrow3 = i6;
                        columnIndexOrThrow13 = i7;
                        i2 = i8;
                        columnIndexOrThrow23 = i20;
                        columnIndexOrThrow24 = i21;
                        columnIndexOrThrow12 = i10;
                        columnIndexOrThrow4 = i11;
                        columnIndexOrThrow15 = i9;
                        columnIndexOrThrow17 = i14;
                        columnIndexOrThrow19 = i16;
                        columnIndexOrThrow22 = i19;
                    }
                    query.close();
                    roomSQLiteQuery.release();
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    query.close();
                    roomSQLiteQuery.release();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                roomSQLiteQuery = acquire;
            }
        } catch (Throwable th3) {
            th = th3;
            roomSQLiteQuery = acquire;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<Long> getScheduleRequestedAtLiveData(final String id) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT schedule_requested_at FROM workspec WHERE id=?", 1);
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"workspec"}, false, new Callable<Long>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.14
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Long call() throws Exception {
                Cursor query = DBUtil.query(WorkSpecDao_Impl.this.__db, acquire, false, null);
                try {
                    return query.moveToFirst() ? query.isNull(0) ? null : Long.valueOf(query.getLong(0)) : null;
                } finally {
                    query.close();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getScheduledWork() {
        RoomSQLiteQuery roomSQLiteQuery;
        int i;
        boolean z;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at<>-1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "required_network_type");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "requires_charging");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "requires_device_idle");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "requires_battery_not_low");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "requires_storage_not_low");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "trigger_content_update_delay");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "trigger_max_content_delay");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "content_uri_triggers");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "worker_class_name");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "input_merger_class_name");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "input");
            try {
                int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "output");
                roomSQLiteQuery = acquire;
                try {
                    int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "initial_delay");
                    int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "interval_duration");
                    int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "flex_duration");
                    int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                    int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(query, "backoff_policy");
                    int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(query, "backoff_delay_duration");
                    int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(query, "period_start_time");
                    int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(query, "minimum_retention_duration");
                    int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(query, "schedule_requested_at");
                    int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(query, "run_in_foreground");
                    int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(query, "out_of_quota_policy");
                    int i2 = columnIndexOrThrow14;
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        String string = query.getString(columnIndexOrThrow9);
                        String string2 = query.getString(columnIndexOrThrow11);
                        Constraints constraints = new Constraints();
                        int i3 = columnIndexOrThrow;
                        int i4 = columnIndexOrThrow9;
                        constraints.setRequiredNetworkType(WorkTypeConverters.intToNetworkType(query.getInt(columnIndexOrThrow)));
                        int i5 = columnIndexOrThrow2;
                        constraints.setRequiresCharging(query.getInt(columnIndexOrThrow2) != 0);
                        constraints.setRequiresDeviceIdle(query.getInt(columnIndexOrThrow3) != 0);
                        constraints.setRequiresBatteryNotLow(query.getInt(columnIndexOrThrow4) != 0);
                        constraints.setRequiresStorageNotLow(query.getInt(columnIndexOrThrow5) != 0);
                        int i6 = columnIndexOrThrow3;
                        constraints.setTriggerContentUpdateDelay(query.getLong(columnIndexOrThrow6));
                        constraints.setTriggerMaxContentDelay(query.getLong(columnIndexOrThrow7));
                        constraints.setContentUriTriggers(WorkTypeConverters.byteArrayToContentUriTriggers(query.getBlob(columnIndexOrThrow8)));
                        WorkSpec workSpec = new WorkSpec(string, string2);
                        workSpec.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow10));
                        workSpec.inputMergerClassName = query.getString(columnIndexOrThrow12);
                        int i7 = columnIndexOrThrow13;
                        workSpec.input = Data.fromByteArray(query.getBlob(columnIndexOrThrow13));
                        int i8 = i2;
                        workSpec.output = Data.fromByteArray(query.getBlob(i8));
                        int i9 = columnIndexOrThrow15;
                        int i10 = columnIndexOrThrow12;
                        workSpec.initialDelay = query.getLong(i9);
                        int i11 = columnIndexOrThrow4;
                        int i12 = columnIndexOrThrow16;
                        int i13 = columnIndexOrThrow5;
                        workSpec.intervalDuration = query.getLong(i12);
                        int i14 = columnIndexOrThrow17;
                        workSpec.flexDuration = query.getLong(i14);
                        int i15 = columnIndexOrThrow18;
                        workSpec.runAttemptCount = query.getInt(i15);
                        int i16 = columnIndexOrThrow19;
                        workSpec.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(query.getInt(i16));
                        int i17 = columnIndexOrThrow20;
                        workSpec.backoffDelayDuration = query.getLong(i17);
                        int i18 = columnIndexOrThrow21;
                        workSpec.periodStartTime = query.getLong(i18);
                        int i19 = columnIndexOrThrow22;
                        workSpec.minimumRetentionDuration = query.getLong(i19);
                        int i20 = columnIndexOrThrow23;
                        workSpec.scheduleRequestedAt = query.getLong(i20);
                        int i21 = columnIndexOrThrow24;
                        if (query.getInt(i21) != 0) {
                            i = i17;
                            z = true;
                        } else {
                            i = i17;
                            z = false;
                        }
                        workSpec.expedited = z;
                        int i22 = columnIndexOrThrow25;
                        workSpec.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(query.getInt(i22));
                        workSpec.constraints = constraints;
                        arrayList.add(workSpec);
                        columnIndexOrThrow5 = i13;
                        columnIndexOrThrow18 = i15;
                        columnIndexOrThrow16 = i12;
                        columnIndexOrThrow21 = i18;
                        columnIndexOrThrow20 = i;
                        columnIndexOrThrow9 = i4;
                        columnIndexOrThrow = i3;
                        columnIndexOrThrow25 = i22;
                        columnIndexOrThrow2 = i5;
                        columnIndexOrThrow3 = i6;
                        columnIndexOrThrow13 = i7;
                        i2 = i8;
                        columnIndexOrThrow23 = i20;
                        columnIndexOrThrow24 = i21;
                        columnIndexOrThrow12 = i10;
                        columnIndexOrThrow4 = i11;
                        columnIndexOrThrow15 = i9;
                        columnIndexOrThrow17 = i14;
                        columnIndexOrThrow19 = i16;
                        columnIndexOrThrow22 = i19;
                    }
                    query.close();
                    roomSQLiteQuery.release();
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    query.close();
                    roomSQLiteQuery.release();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                roomSQLiteQuery = acquire;
            }
        } catch (Throwable th3) {
            th = th3;
            roomSQLiteQuery = acquire;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public WorkInfo.State getState(final String id) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT state FROM workspec WHERE id=?", 1);
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            return query.moveToFirst() ? WorkTypeConverters.intToState(query.getInt(0)) : null;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getUnfinishedWorkWithName(final String name) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(query.getString(0));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getUnfinishedWorkWithTag(final String tag) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, tag);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(query.getString(0));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public WorkSpec getWorkSpec(final String id) {
        RoomSQLiteQuery roomSQLiteQuery;
        WorkSpec workSpec;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE id=?", 1);
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "required_network_type");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "requires_charging");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "requires_device_idle");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "requires_battery_not_low");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "requires_storage_not_low");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "trigger_content_update_delay");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "trigger_max_content_delay");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "content_uri_triggers");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "worker_class_name");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "input_merger_class_name");
            try {
                int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "input");
                try {
                    int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "output");
                    roomSQLiteQuery = acquire;
                    try {
                        int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "initial_delay");
                        int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "interval_duration");
                        int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "flex_duration");
                        int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                        int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(query, "backoff_policy");
                        int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(query, "backoff_delay_duration");
                        int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(query, "period_start_time");
                        int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(query, "minimum_retention_duration");
                        int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(query, "schedule_requested_at");
                        int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(query, "run_in_foreground");
                        int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(query, "out_of_quota_policy");
                        if (query.moveToFirst()) {
                            String string = query.getString(columnIndexOrThrow9);
                            String string2 = query.getString(columnIndexOrThrow11);
                            Constraints constraints = new Constraints();
                            constraints.setRequiredNetworkType(WorkTypeConverters.intToNetworkType(query.getInt(columnIndexOrThrow)));
                            constraints.setRequiresCharging(query.getInt(columnIndexOrThrow2) != 0);
                            constraints.setRequiresDeviceIdle(query.getInt(columnIndexOrThrow3) != 0);
                            constraints.setRequiresBatteryNotLow(query.getInt(columnIndexOrThrow4) != 0);
                            constraints.setRequiresStorageNotLow(query.getInt(columnIndexOrThrow5) != 0);
                            constraints.setTriggerContentUpdateDelay(query.getLong(columnIndexOrThrow6));
                            constraints.setTriggerMaxContentDelay(query.getLong(columnIndexOrThrow7));
                            constraints.setContentUriTriggers(WorkTypeConverters.byteArrayToContentUriTriggers(query.getBlob(columnIndexOrThrow8)));
                            workSpec = new WorkSpec(string, string2);
                            workSpec.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow10));
                            workSpec.inputMergerClassName = query.getString(columnIndexOrThrow12);
                            workSpec.input = Data.fromByteArray(query.getBlob(columnIndexOrThrow13));
                            workSpec.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow14));
                            workSpec.initialDelay = query.getLong(columnIndexOrThrow15);
                            workSpec.intervalDuration = query.getLong(columnIndexOrThrow16);
                            workSpec.flexDuration = query.getLong(columnIndexOrThrow17);
                            workSpec.runAttemptCount = query.getInt(columnIndexOrThrow18);
                            workSpec.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(query.getInt(columnIndexOrThrow19));
                            workSpec.backoffDelayDuration = query.getLong(columnIndexOrThrow20);
                            workSpec.periodStartTime = query.getLong(columnIndexOrThrow21);
                            workSpec.minimumRetentionDuration = query.getLong(columnIndexOrThrow22);
                            workSpec.scheduleRequestedAt = query.getLong(columnIndexOrThrow23);
                            workSpec.expedited = query.getInt(columnIndexOrThrow24) != 0;
                            workSpec.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(query.getInt(columnIndexOrThrow25));
                            workSpec.constraints = constraints;
                        } else {
                            workSpec = null;
                        }
                        query.close();
                        roomSQLiteQuery.release();
                        return workSpec;
                    } catch (Throwable th) {
                        th = th;
                        query.close();
                        roomSQLiteQuery.release();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    roomSQLiteQuery = acquire;
                }
            } catch (Throwable th3) {
                th = th3;
                roomSQLiteQuery = acquire;
            }
        } catch (Throwable th4) {
            th = th4;
            roomSQLiteQuery = acquire;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.IdAndState> getWorkSpecIdAndStatesForName(final String name) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id, state FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                WorkSpec.IdAndState idAndState = new WorkSpec.IdAndState();
                idAndState.id = query.getString(columnIndexOrThrow);
                idAndState.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                arrayList.add(idAndState);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public WorkSpec[] getWorkSpecs(final List<String> ids) {
        RoomSQLiteQuery roomSQLiteQuery;
        int i;
        boolean z;
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT ");
        newStringBuilder.append("*");
        newStringBuilder.append(" FROM workspec WHERE id IN (");
        int size = ids.size();
        StringUtil.appendPlaceholders(newStringBuilder, size);
        newStringBuilder.append(")");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size + 0);
        int i2 = 1;
        for (String str : ids) {
            if (str == null) {
                acquire.bindNull(i2);
            } else {
                acquire.bindString(i2, str);
            }
            i2++;
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "required_network_type");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "requires_charging");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "requires_device_idle");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "requires_battery_not_low");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "requires_storage_not_low");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "trigger_content_update_delay");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "trigger_max_content_delay");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "content_uri_triggers");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "id");
            try {
                int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
                try {
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "worker_class_name");
                    try {
                        int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "input_merger_class_name");
                        try {
                            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "input");
                            try {
                                int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "output");
                                roomSQLiteQuery = acquire;
                                try {
                                    int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "initial_delay");
                                    int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "interval_duration");
                                    int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "flex_duration");
                                    int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                                    int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(query, "backoff_policy");
                                    int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(query, "backoff_delay_duration");
                                    int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(query, "period_start_time");
                                    int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(query, "minimum_retention_duration");
                                    int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(query, "schedule_requested_at");
                                    int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(query, "run_in_foreground");
                                    int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(query, "out_of_quota_policy");
                                    WorkSpec[] workSpecArr = new WorkSpec[query.getCount()];
                                    int i3 = 0;
                                    while (query.moveToNext()) {
                                        String string = query.getString(columnIndexOrThrow9);
                                        String string2 = query.getString(columnIndexOrThrow11);
                                        Constraints constraints = new Constraints();
                                        int i4 = columnIndexOrThrow;
                                        int i5 = columnIndexOrThrow9;
                                        constraints.setRequiredNetworkType(WorkTypeConverters.intToNetworkType(query.getInt(columnIndexOrThrow)));
                                        constraints.setRequiresCharging(query.getInt(columnIndexOrThrow2) != 0);
                                        constraints.setRequiresDeviceIdle(query.getInt(columnIndexOrThrow3) != 0);
                                        constraints.setRequiresBatteryNotLow(query.getInt(columnIndexOrThrow4) != 0);
                                        constraints.setRequiresStorageNotLow(query.getInt(columnIndexOrThrow5) != 0);
                                        int i6 = columnIndexOrThrow2;
                                        int i7 = columnIndexOrThrow3;
                                        constraints.setTriggerContentUpdateDelay(query.getLong(columnIndexOrThrow6));
                                        constraints.setTriggerMaxContentDelay(query.getLong(columnIndexOrThrow7));
                                        constraints.setContentUriTriggers(WorkTypeConverters.byteArrayToContentUriTriggers(query.getBlob(columnIndexOrThrow8)));
                                        int i8 = columnIndexOrThrow11;
                                        int i9 = columnIndexOrThrow8;
                                        WorkSpec workSpec = new WorkSpec(string, string2);
                                        int i10 = columnIndexOrThrow10;
                                        workSpec.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow10));
                                        workSpec.inputMergerClassName = query.getString(columnIndexOrThrow12);
                                        workSpec.input = Data.fromByteArray(query.getBlob(columnIndexOrThrow13));
                                        workSpec.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow14));
                                        int i11 = columnIndexOrThrow15;
                                        int i12 = columnIndexOrThrow12;
                                        workSpec.initialDelay = query.getLong(i11);
                                        int i13 = columnIndexOrThrow16;
                                        int i14 = columnIndexOrThrow13;
                                        workSpec.intervalDuration = query.getLong(i13);
                                        int i15 = columnIndexOrThrow17;
                                        workSpec.flexDuration = query.getLong(i15);
                                        int i16 = columnIndexOrThrow18;
                                        workSpec.runAttemptCount = query.getInt(i16);
                                        int i17 = columnIndexOrThrow19;
                                        workSpec.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(query.getInt(i17));
                                        int i18 = columnIndexOrThrow20;
                                        workSpec.backoffDelayDuration = query.getLong(i18);
                                        int i19 = columnIndexOrThrow21;
                                        workSpec.periodStartTime = query.getLong(i19);
                                        int i20 = columnIndexOrThrow22;
                                        workSpec.minimumRetentionDuration = query.getLong(i20);
                                        int i21 = columnIndexOrThrow23;
                                        workSpec.scheduleRequestedAt = query.getLong(i21);
                                        int i22 = columnIndexOrThrow24;
                                        if (query.getInt(i22) != 0) {
                                            i = i21;
                                            z = true;
                                        } else {
                                            i = i21;
                                            z = false;
                                        }
                                        workSpec.expedited = z;
                                        int i23 = columnIndexOrThrow25;
                                        workSpec.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(query.getInt(i23));
                                        workSpec.constraints = constraints;
                                        workSpecArr[i3] = workSpec;
                                        i3++;
                                        columnIndexOrThrow12 = i12;
                                        columnIndexOrThrow18 = i16;
                                        columnIndexOrThrow15 = i11;
                                        columnIndexOrThrow21 = i19;
                                        columnIndexOrThrow22 = i20;
                                        columnIndexOrThrow23 = i;
                                        columnIndexOrThrow8 = i9;
                                        columnIndexOrThrow9 = i5;
                                        columnIndexOrThrow = i4;
                                        columnIndexOrThrow25 = i23;
                                        columnIndexOrThrow2 = i6;
                                        columnIndexOrThrow3 = i7;
                                        columnIndexOrThrow10 = i10;
                                        columnIndexOrThrow19 = i17;
                                        columnIndexOrThrow24 = i22;
                                        columnIndexOrThrow11 = i8;
                                        columnIndexOrThrow20 = i18;
                                        columnIndexOrThrow13 = i14;
                                        columnIndexOrThrow16 = i13;
                                        columnIndexOrThrow17 = i15;
                                    }
                                    query.close();
                                    roomSQLiteQuery.release();
                                    return workSpecArr;
                                } catch (Throwable th) {
                                    th = th;
                                    query.close();
                                    roomSQLiteQuery.release();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                roomSQLiteQuery = acquire;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            roomSQLiteQuery = acquire;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        roomSQLiteQuery = acquire;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    roomSQLiteQuery = acquire;
                }
            } catch (Throwable th6) {
                th = th6;
                roomSQLiteQuery = acquire;
            }
        } catch (Throwable th7) {
            th = th7;
            roomSQLiteQuery = acquire;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public WorkSpec.WorkInfoPojo getWorkStatusPojoForId(final String id) {
        WorkSpec.WorkInfoPojo workInfoPojo;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id=?", 1);
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor query = DBUtil.query(this.__db, acquire, true, null);
            try {
                int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
                int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "output");
                int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                while (query.moveToNext()) {
                    if (!query.isNull(columnIndexOrThrow)) {
                        String string = query.getString(columnIndexOrThrow);
                        if (arrayMap.get(string) == null) {
                            arrayMap.put(string, new ArrayList<>());
                        }
                    }
                    if (!query.isNull(columnIndexOrThrow)) {
                        String string2 = query.getString(columnIndexOrThrow);
                        if (arrayMap2.get(string2) == null) {
                            arrayMap2.put(string2, new ArrayList<>());
                        }
                    }
                }
                query.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                if (query.moveToFirst()) {
                    ArrayList<String> arrayList = query.isNull(columnIndexOrThrow) ? null : arrayMap.get(query.getString(columnIndexOrThrow));
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    ArrayList<Data> arrayList2 = query.isNull(columnIndexOrThrow) ? null : arrayMap2.get(query.getString(columnIndexOrThrow));
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>();
                    }
                    workInfoPojo = new WorkSpec.WorkInfoPojo();
                    workInfoPojo.id = query.getString(columnIndexOrThrow);
                    workInfoPojo.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                    workInfoPojo.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow3));
                    workInfoPojo.runAttemptCount = query.getInt(columnIndexOrThrow4);
                    workInfoPojo.tags = arrayList;
                    workInfoPojo.progress = arrayList2;
                } else {
                    workInfoPojo = null;
                }
                this.__db.setTransactionSuccessful();
                return workInfoPojo;
            } finally {
                query.close();
                acquire.release();
            }
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForIds(final List<String> ids) {
        StringBuilder sb;
        int i;
        String str;
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (");
        int size = ids.size();
        StringUtil.appendPlaceholders(newStringBuilder, size);
        newStringBuilder.append(")");
        String sb2 = newStringBuilder.toString();
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(sb2, size + 0);
        int i2 = 1;
        for (String str2 : ids) {
            if (str2 == null) {
                acquire.bindNull(i2);
            } else {
                acquire.bindString(i2, str2);
            }
            i2++;
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor query = DBUtil.query(this.__db, acquire, true, null);
            try {
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "output");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                    ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                    ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                    while (query.moveToNext()) {
                        try {
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string = query.getString(columnIndexOrThrow);
                                if (arrayMap.get(string) == null) {
                                    arrayMap.put(string, new ArrayList<>());
                                }
                            }
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string2 = query.getString(columnIndexOrThrow);
                                if (arrayMap2.get(string2) == null) {
                                    arrayMap2.put(string2, new ArrayList<>());
                                }
                            }
                        } catch (Throwable th) {
                            th = th;
                            query.close();
                            acquire.release();
                            throw th;
                        }
                    }
                    query.moveToPosition(-1);
                    __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                    __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        ArrayList<String> arrayList2 = null;
                        if (query.isNull(columnIndexOrThrow)) {
                            sb = newStringBuilder;
                        } else {
                            try {
                                sb = newStringBuilder;
                            } catch (Throwable th2) {
                                th = th2;
                            }
                            try {
                                arrayList2 = arrayMap.get(query.getString(columnIndexOrThrow));
                            } catch (Throwable th3) {
                                th = th3;
                                query.close();
                                acquire.release();
                                throw th;
                            }
                        }
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList<>();
                        }
                        ArrayList<Data> arrayList3 = null;
                        try {
                            if (!query.isNull(columnIndexOrThrow)) {
                                arrayList3 = arrayMap2.get(query.getString(columnIndexOrThrow));
                            }
                            if (arrayList3 == null) {
                                arrayList3 = new ArrayList<>();
                            }
                            WorkSpec.WorkInfoPojo workInfoPojo = new WorkSpec.WorkInfoPojo();
                            int i3 = size;
                            try {
                                i = columnIndexOrThrow;
                                workInfoPojo.id = query.getString(columnIndexOrThrow);
                                str = sb2;
                            } catch (Throwable th4) {
                                th = th4;
                                query.close();
                                acquire.release();
                                throw th;
                            }
                            try {
                                workInfoPojo.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                                workInfoPojo.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow3));
                                workInfoPojo.runAttemptCount = query.getInt(columnIndexOrThrow4);
                                workInfoPojo.tags = arrayList2;
                                workInfoPojo.progress = arrayList3;
                                arrayList.add(workInfoPojo);
                                newStringBuilder = sb;
                                size = i3;
                                sb2 = str;
                                columnIndexOrThrow = i;
                            } catch (Throwable th5) {
                                th = th5;
                                query.close();
                                acquire.release();
                                throw th;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                        }
                    }
                    this.__db.setTransactionSuccessful();
                    query.close();
                    acquire.release();
                    this.__db.endTransaction();
                    return arrayList;
                } catch (Throwable th7) {
                    th = th7;
                    this.__db.endTransaction();
                    throw th;
                }
            } catch (Throwable th8) {
                th = th8;
            }
        } catch (Throwable th9) {
            th = th9;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForName(final String name) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor query = DBUtil.query(this.__db, acquire, true, null);
            try {
                int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
                int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "output");
                int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                while (query.moveToNext()) {
                    if (!query.isNull(columnIndexOrThrow)) {
                        String string = query.getString(columnIndexOrThrow);
                        if (arrayMap.get(string) == null) {
                            arrayMap.put(string, new ArrayList<>());
                        }
                    }
                    if (!query.isNull(columnIndexOrThrow)) {
                        String string2 = query.getString(columnIndexOrThrow);
                        if (arrayMap2.get(string2) == null) {
                            arrayMap2.put(string2, new ArrayList<>());
                        }
                    }
                }
                query.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                ArrayList arrayList = new ArrayList(query.getCount());
                while (query.moveToNext()) {
                    ArrayList<String> arrayList2 = query.isNull(columnIndexOrThrow) ? null : arrayMap.get(query.getString(columnIndexOrThrow));
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>();
                    }
                    ArrayList<Data> arrayList3 = query.isNull(columnIndexOrThrow) ? null : arrayMap2.get(query.getString(columnIndexOrThrow));
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList<>();
                    }
                    WorkSpec.WorkInfoPojo workInfoPojo = new WorkSpec.WorkInfoPojo();
                    workInfoPojo.id = query.getString(columnIndexOrThrow);
                    workInfoPojo.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                    workInfoPojo.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow3));
                    workInfoPojo.runAttemptCount = query.getInt(columnIndexOrThrow4);
                    workInfoPojo.tags = arrayList2;
                    workInfoPojo.progress = arrayList3;
                    arrayList.add(workInfoPojo);
                    columnIndexOrThrow = columnIndexOrThrow;
                }
                this.__db.setTransactionSuccessful();
                return arrayList;
            } finally {
                query.close();
                acquire.release();
            }
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForTag(final String tag) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, tag);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor query = DBUtil.query(this.__db, acquire, true, null);
            try {
                int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
                int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "output");
                int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                while (query.moveToNext()) {
                    if (!query.isNull(columnIndexOrThrow)) {
                        String string = query.getString(columnIndexOrThrow);
                        if (arrayMap.get(string) == null) {
                            arrayMap.put(string, new ArrayList<>());
                        }
                    }
                    if (!query.isNull(columnIndexOrThrow)) {
                        String string2 = query.getString(columnIndexOrThrow);
                        if (arrayMap2.get(string2) == null) {
                            arrayMap2.put(string2, new ArrayList<>());
                        }
                    }
                }
                query.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                ArrayList arrayList = new ArrayList(query.getCount());
                while (query.moveToNext()) {
                    ArrayList<String> arrayList2 = query.isNull(columnIndexOrThrow) ? null : arrayMap.get(query.getString(columnIndexOrThrow));
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>();
                    }
                    ArrayList<Data> arrayList3 = query.isNull(columnIndexOrThrow) ? null : arrayMap2.get(query.getString(columnIndexOrThrow));
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList<>();
                    }
                    WorkSpec.WorkInfoPojo workInfoPojo = new WorkSpec.WorkInfoPojo();
                    workInfoPojo.id = query.getString(columnIndexOrThrow);
                    workInfoPojo.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                    workInfoPojo.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow3));
                    workInfoPojo.runAttemptCount = query.getInt(columnIndexOrThrow4);
                    workInfoPojo.tags = arrayList2;
                    workInfoPojo.progress = arrayList3;
                    arrayList.add(workInfoPojo);
                    columnIndexOrThrow = columnIndexOrThrow;
                }
                this.__db.setTransactionSuccessful();
                return arrayList;
            } finally {
                query.close();
                acquire.release();
            }
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForIds(final List<String> ids) {
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (");
        int size = ids.size();
        StringUtil.appendPlaceholders(newStringBuilder, size);
        newStringBuilder.append(")");
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size + 0);
        int i = 1;
        for (String str : ids) {
            if (str == null) {
                acquire.bindNull(i);
            } else {
                acquire.bindString(i, str);
            }
            i++;
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.11
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public List<WorkSpec.WorkInfoPojo> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor query = DBUtil.query(WorkSpecDao_Impl.this.__db, acquire, true, null);
                    try {
                        int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                        int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
                        int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "output");
                        int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                        ArrayMap arrayMap = new ArrayMap();
                        ArrayMap arrayMap2 = new ArrayMap();
                        while (query.moveToNext()) {
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string = query.getString(columnIndexOrThrow);
                                if (((ArrayList) arrayMap.get(string)) == null) {
                                    arrayMap.put(string, new ArrayList());
                                }
                            }
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string2 = query.getString(columnIndexOrThrow);
                                if (((ArrayList) arrayMap2.get(string2)) == null) {
                                    arrayMap2.put(string2, new ArrayList());
                                }
                            }
                        }
                        query.moveToPosition(-1);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                        ArrayList arrayList = new ArrayList(query.getCount());
                        while (query.moveToNext()) {
                            ArrayList arrayList2 = query.isNull(columnIndexOrThrow) ? null : (ArrayList) arrayMap.get(query.getString(columnIndexOrThrow));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            ArrayList arrayList3 = query.isNull(columnIndexOrThrow) ? null : (ArrayList) arrayMap2.get(query.getString(columnIndexOrThrow));
                            if (arrayList3 == null) {
                                arrayList3 = new ArrayList();
                            }
                            WorkSpec.WorkInfoPojo workInfoPojo = new WorkSpec.WorkInfoPojo();
                            workInfoPojo.id = query.getString(columnIndexOrThrow);
                            workInfoPojo.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                            workInfoPojo.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow3));
                            workInfoPojo.runAttemptCount = query.getInt(columnIndexOrThrow4);
                            workInfoPojo.tags = arrayList2;
                            workInfoPojo.progress = arrayList3;
                            arrayList.add(workInfoPojo);
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        query.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForName(final String name) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, name);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec", "workname"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.13
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public List<WorkSpec.WorkInfoPojo> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor query = DBUtil.query(WorkSpecDao_Impl.this.__db, acquire, true, null);
                    try {
                        int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                        int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
                        int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "output");
                        int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                        ArrayMap arrayMap = new ArrayMap();
                        ArrayMap arrayMap2 = new ArrayMap();
                        while (query.moveToNext()) {
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string = query.getString(columnIndexOrThrow);
                                if (((ArrayList) arrayMap.get(string)) == null) {
                                    arrayMap.put(string, new ArrayList());
                                }
                            }
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string2 = query.getString(columnIndexOrThrow);
                                if (((ArrayList) arrayMap2.get(string2)) == null) {
                                    arrayMap2.put(string2, new ArrayList());
                                }
                            }
                        }
                        query.moveToPosition(-1);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                        ArrayList arrayList = new ArrayList(query.getCount());
                        while (query.moveToNext()) {
                            ArrayList arrayList2 = query.isNull(columnIndexOrThrow) ? null : (ArrayList) arrayMap.get(query.getString(columnIndexOrThrow));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            ArrayList arrayList3 = query.isNull(columnIndexOrThrow) ? null : (ArrayList) arrayMap2.get(query.getString(columnIndexOrThrow));
                            if (arrayList3 == null) {
                                arrayList3 = new ArrayList();
                            }
                            WorkSpec.WorkInfoPojo workInfoPojo = new WorkSpec.WorkInfoPojo();
                            workInfoPojo.id = query.getString(columnIndexOrThrow);
                            workInfoPojo.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                            workInfoPojo.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow3));
                            workInfoPojo.runAttemptCount = query.getInt(columnIndexOrThrow4);
                            workInfoPojo.tags = arrayList2;
                            workInfoPojo.progress = arrayList3;
                            arrayList.add(workInfoPojo);
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        query.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForTag(final String tag) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, tag);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec", "worktag"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.12
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public List<WorkSpec.WorkInfoPojo> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor query = DBUtil.query(WorkSpecDao_Impl.this.__db, acquire, true, null);
                    try {
                        int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                        int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "state");
                        int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "output");
                        int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "run_attempt_count");
                        ArrayMap arrayMap = new ArrayMap();
                        ArrayMap arrayMap2 = new ArrayMap();
                        while (query.moveToNext()) {
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string = query.getString(columnIndexOrThrow);
                                if (((ArrayList) arrayMap.get(string)) == null) {
                                    arrayMap.put(string, new ArrayList());
                                }
                            }
                            if (!query.isNull(columnIndexOrThrow)) {
                                String string2 = query.getString(columnIndexOrThrow);
                                if (((ArrayList) arrayMap2.get(string2)) == null) {
                                    arrayMap2.put(string2, new ArrayList());
                                }
                            }
                        }
                        query.moveToPosition(-1);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                        ArrayList arrayList = new ArrayList(query.getCount());
                        while (query.moveToNext()) {
                            ArrayList arrayList2 = query.isNull(columnIndexOrThrow) ? null : (ArrayList) arrayMap.get(query.getString(columnIndexOrThrow));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            ArrayList arrayList3 = query.isNull(columnIndexOrThrow) ? null : (ArrayList) arrayMap2.get(query.getString(columnIndexOrThrow));
                            if (arrayList3 == null) {
                                arrayList3 = new ArrayList();
                            }
                            WorkSpec.WorkInfoPojo workInfoPojo = new WorkSpec.WorkInfoPojo();
                            workInfoPojo.id = query.getString(columnIndexOrThrow);
                            workInfoPojo.state = WorkTypeConverters.intToState(query.getInt(columnIndexOrThrow2));
                            workInfoPojo.output = Data.fromByteArray(query.getBlob(columnIndexOrThrow3));
                            workInfoPojo.runAttemptCount = query.getInt(columnIndexOrThrow4);
                            workInfoPojo.tags = arrayList2;
                            workInfoPojo.progress = arrayList3;
                            arrayList.add(workInfoPojo);
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        query.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public boolean hasUnfinishedWork() {
        boolean z = false;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT COUNT(*) > 0 FROM workspec WHERE state NOT IN (2, 3, 5) LIMIT 1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            if (!query.moveToFirst()) {
                z = false;
            } else if (query.getInt(0) != 0) {
                z = true;
            }
            return z;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int incrementWorkSpecRunAttemptCount(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.acquire();
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            int executeUpdateDelete = acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void insertWorkSpec(final WorkSpec workSpec) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfWorkSpec.insert((EntityInsertionAdapter<WorkSpec>) workSpec);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int markWorkSpecScheduled(final String id, final long startTime) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfMarkWorkSpecScheduled.acquire();
        acquire.bindLong(1, startTime);
        if (id == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            int executeUpdateDelete = acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfMarkWorkSpecScheduled.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void pruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int resetScheduledState() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfResetScheduledState.acquire();
        this.__db.beginTransaction();
        try {
            int executeUpdateDelete = acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfResetScheduledState.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int resetWorkSpecRunAttemptCount(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfResetWorkSpecRunAttemptCount.acquire();
        if (id == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            int executeUpdateDelete = acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfResetWorkSpecRunAttemptCount.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void setOutput(final String id, final Data output) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfSetOutput.acquire();
        byte[] byteArrayInternal = Data.toByteArrayInternal(output);
        if (byteArrayInternal == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindBlob(1, byteArrayInternal);
        }
        if (id == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetOutput.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void setPeriodStartTime(final String id, final long periodStartTime) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfSetPeriodStartTime.acquire();
        acquire.bindLong(1, periodStartTime);
        if (id == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetPeriodStartTime.release(acquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int setState(final WorkInfo.State state, final String... ids) {
        this.__db.assertNotSuspendingTransaction();
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("UPDATE workspec SET state=");
        newStringBuilder.append("?");
        newStringBuilder.append(" WHERE id IN (");
        StringUtil.appendPlaceholders(newStringBuilder, ids.length);
        newStringBuilder.append(")");
        SupportSQLiteStatement compileStatement = this.__db.compileStatement(newStringBuilder.toString());
        compileStatement.bindLong(1, WorkTypeConverters.stateToInt(state));
        int i = 2;
        for (String str : ids) {
            if (str == null) {
                compileStatement.bindNull(i);
            } else {
                compileStatement.bindString(i, str);
            }
            i++;
        }
        this.__db.beginTransaction();
        try {
            int executeUpdateDelete = compileStatement.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            this.__db.endTransaction();
        }
    }
}
