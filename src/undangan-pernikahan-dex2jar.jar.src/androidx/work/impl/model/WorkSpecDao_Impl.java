package androidx.work.impl.model;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.WorkInfo.State;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public final class WorkSpecDao_Impl
  implements WorkSpecDao
{
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
  
  public WorkSpecDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfWorkSpec = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, WorkSpec paramAnonymousWorkSpec)
      {
        if (paramAnonymousWorkSpec.id == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousWorkSpec.id);
        }
        paramAnonymousSupportSQLiteStatement.bindLong(2, WorkTypeConverters.stateToInt(paramAnonymousWorkSpec.state));
        if (paramAnonymousWorkSpec.workerClassName == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(3);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(3, paramAnonymousWorkSpec.workerClassName);
        }
        if (paramAnonymousWorkSpec.inputMergerClassName == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(4);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(4, paramAnonymousWorkSpec.inputMergerClassName);
        }
        byte[] arrayOfByte = Data.toByteArrayInternal(paramAnonymousWorkSpec.input);
        if (arrayOfByte == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(5);
        } else {
          paramAnonymousSupportSQLiteStatement.bindBlob(5, arrayOfByte);
        }
        arrayOfByte = Data.toByteArrayInternal(paramAnonymousWorkSpec.output);
        if (arrayOfByte == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(6);
        } else {
          paramAnonymousSupportSQLiteStatement.bindBlob(6, arrayOfByte);
        }
        paramAnonymousSupportSQLiteStatement.bindLong(7, paramAnonymousWorkSpec.initialDelay);
        paramAnonymousSupportSQLiteStatement.bindLong(8, paramAnonymousWorkSpec.intervalDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(9, paramAnonymousWorkSpec.flexDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(10, paramAnonymousWorkSpec.runAttemptCount);
        paramAnonymousSupportSQLiteStatement.bindLong(11, WorkTypeConverters.backoffPolicyToInt(paramAnonymousWorkSpec.backoffPolicy));
        paramAnonymousSupportSQLiteStatement.bindLong(12, paramAnonymousWorkSpec.backoffDelayDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(13, paramAnonymousWorkSpec.periodStartTime);
        paramAnonymousSupportSQLiteStatement.bindLong(14, paramAnonymousWorkSpec.minimumRetentionDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(15, paramAnonymousWorkSpec.scheduleRequestedAt);
        paramAnonymousSupportSQLiteStatement.bindLong(16, paramAnonymousWorkSpec.expedited);
        paramAnonymousSupportSQLiteStatement.bindLong(17, WorkTypeConverters.outOfQuotaPolicyToInt(paramAnonymousWorkSpec.outOfQuotaPolicy));
        paramAnonymousWorkSpec = paramAnonymousWorkSpec.constraints;
        if (paramAnonymousWorkSpec != null)
        {
          paramAnonymousSupportSQLiteStatement.bindLong(18, WorkTypeConverters.networkTypeToInt(paramAnonymousWorkSpec.getRequiredNetworkType()));
          paramAnonymousSupportSQLiteStatement.bindLong(19, paramAnonymousWorkSpec.requiresCharging());
          paramAnonymousSupportSQLiteStatement.bindLong(20, paramAnonymousWorkSpec.requiresDeviceIdle());
          paramAnonymousSupportSQLiteStatement.bindLong(21, paramAnonymousWorkSpec.requiresBatteryNotLow());
          paramAnonymousSupportSQLiteStatement.bindLong(22, paramAnonymousWorkSpec.requiresStorageNotLow());
          paramAnonymousSupportSQLiteStatement.bindLong(23, paramAnonymousWorkSpec.getTriggerContentUpdateDelay());
          paramAnonymousSupportSQLiteStatement.bindLong(24, paramAnonymousWorkSpec.getTriggerMaxContentDelay());
          paramAnonymousWorkSpec = WorkTypeConverters.contentUriTriggersToByteArray(paramAnonymousWorkSpec.getContentUriTriggers());
          if (paramAnonymousWorkSpec == null) {
            paramAnonymousSupportSQLiteStatement.bindNull(25);
          } else {
            paramAnonymousSupportSQLiteStatement.bindBlob(25, paramAnonymousWorkSpec);
          }
        }
        else
        {
          paramAnonymousSupportSQLiteStatement.bindNull(18);
          paramAnonymousSupportSQLiteStatement.bindNull(19);
          paramAnonymousSupportSQLiteStatement.bindNull(20);
          paramAnonymousSupportSQLiteStatement.bindNull(21);
          paramAnonymousSupportSQLiteStatement.bindNull(22);
          paramAnonymousSupportSQLiteStatement.bindNull(23);
          paramAnonymousSupportSQLiteStatement.bindNull(24);
          paramAnonymousSupportSQLiteStatement.bindNull(25);
        }
      }
      
      public String createQuery()
      {
        return "INSERT OR IGNORE INTO `WorkSpec` (`id`,`state`,`worker_class_name`,`input_merger_class_name`,`input`,`output`,`initial_delay`,`interval_duration`,`flex_duration`,`run_attempt_count`,`backoff_policy`,`backoff_delay_duration`,`period_start_time`,`minimum_retention_duration`,`schedule_requested_at`,`run_in_foreground`,`out_of_quota_policy`,`required_network_type`,`requires_charging`,`requires_device_idle`,`requires_battery_not_low`,`requires_storage_not_low`,`trigger_content_update_delay`,`trigger_max_content_delay`,`content_uri_triggers`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "DELETE FROM workspec WHERE id=?";
      }
    };
    this.__preparedStmtOfSetOutput = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET output=? WHERE id=?";
      }
    };
    this.__preparedStmtOfSetPeriodStartTime = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET period_start_time=? WHERE id=?";
      }
    };
    this.__preparedStmtOfIncrementWorkSpecRunAttemptCount = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET run_attempt_count=run_attempt_count+1 WHERE id=?";
      }
    };
    this.__preparedStmtOfResetWorkSpecRunAttemptCount = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET run_attempt_count=0 WHERE id=?";
      }
    };
    this.__preparedStmtOfMarkWorkSpecScheduled = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET schedule_requested_at=? WHERE id=?";
      }
    };
    this.__preparedStmtOfResetScheduledState = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET schedule_requested_at=-1 WHERE state NOT IN (2, 3, 5)";
      }
    };
    this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
      }
    };
  }
  
  private void __fetchRelationshipWorkProgressAsandroidxWorkData(ArrayMap<String, ArrayList<Data>> paramArrayMap)
  {
    Object localObject2 = paramArrayMap.keySet();
    if (((Set)localObject2).isEmpty()) {
      return;
    }
    if (paramArrayMap.size() > 999)
    {
      localObject1 = new ArrayMap(999);
      i = 0;
      int j = 0;
      int n = paramArrayMap.size();
      while (j < n)
      {
        ((ArrayMap)localObject1).put((String)paramArrayMap.keyAt(j), (ArrayList)paramArrayMap.valueAt(j));
        int k = j + 1;
        int m = i + 1;
        i = m;
        j = k;
        if (m == 999)
        {
          __fetchRelationshipWorkProgressAsandroidxWorkData((ArrayMap)localObject1);
          localObject1 = new ArrayMap(999);
          i = 0;
          j = k;
        }
      }
      if (i > 0) {
        __fetchRelationshipWorkProgressAsandroidxWorkData((ArrayMap)localObject1);
      }
      return;
    }
    Object localObject1 = StringUtil.newStringBuilder();
    ((StringBuilder)localObject1).append("SELECT `progress`,`work_spec_id` FROM `WorkProgress` WHERE `work_spec_id` IN (");
    int i = ((Set)localObject2).size();
    StringUtil.appendPlaceholders((StringBuilder)localObject1, i);
    ((StringBuilder)localObject1).append(")");
    localObject1 = RoomSQLiteQuery.acquire(((StringBuilder)localObject1).toString(), i + 0);
    i = 1;
    localObject2 = ((Set)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      String str = (String)((Iterator)localObject2).next();
      if (str == null) {
        ((RoomSQLiteQuery)localObject1).bindNull(i);
      } else {
        ((RoomSQLiteQuery)localObject1).bindString(i, str);
      }
      i++;
    }
    localObject1 = DBUtil.query(this.__db, (SupportSQLiteQuery)localObject1, false, null);
    try
    {
      i = CursorUtil.getColumnIndex((Cursor)localObject1, "work_spec_id");
      if (i == -1) {
        return;
      }
      while (((Cursor)localObject1).moveToNext()) {
        if (!((Cursor)localObject1).isNull(i))
        {
          localObject2 = (ArrayList)paramArrayMap.get(((Cursor)localObject1).getString(i));
          if (localObject2 != null) {
            ((ArrayList)localObject2).add(Data.fromByteArray(((Cursor)localObject1).getBlob(0)));
          }
        }
      }
      return;
    }
    finally
    {
      ((Cursor)localObject1).close();
    }
  }
  
  private void __fetchRelationshipWorkTagAsjavaLangString(ArrayMap<String, ArrayList<String>> paramArrayMap)
  {
    Object localObject2 = paramArrayMap.keySet();
    if (((Set)localObject2).isEmpty()) {
      return;
    }
    if (paramArrayMap.size() > 999)
    {
      localObject1 = new ArrayMap(999);
      i = 0;
      int j = 0;
      int n = paramArrayMap.size();
      while (j < n)
      {
        ((ArrayMap)localObject1).put((String)paramArrayMap.keyAt(j), (ArrayList)paramArrayMap.valueAt(j));
        int k = j + 1;
        int m = i + 1;
        i = m;
        j = k;
        if (m == 999)
        {
          __fetchRelationshipWorkTagAsjavaLangString((ArrayMap)localObject1);
          localObject1 = new ArrayMap(999);
          i = 0;
          j = k;
        }
      }
      if (i > 0) {
        __fetchRelationshipWorkTagAsjavaLangString((ArrayMap)localObject1);
      }
      return;
    }
    Object localObject1 = StringUtil.newStringBuilder();
    ((StringBuilder)localObject1).append("SELECT `tag`,`work_spec_id` FROM `WorkTag` WHERE `work_spec_id` IN (");
    int i = ((Set)localObject2).size();
    StringUtil.appendPlaceholders((StringBuilder)localObject1, i);
    ((StringBuilder)localObject1).append(")");
    localObject1 = RoomSQLiteQuery.acquire(((StringBuilder)localObject1).toString(), i + 0);
    i = 1;
    localObject2 = ((Set)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      String str = (String)((Iterator)localObject2).next();
      if (str == null) {
        ((RoomSQLiteQuery)localObject1).bindNull(i);
      } else {
        ((RoomSQLiteQuery)localObject1).bindString(i, str);
      }
      i++;
    }
    localObject1 = DBUtil.query(this.__db, (SupportSQLiteQuery)localObject1, false, null);
    try
    {
      i = CursorUtil.getColumnIndex((Cursor)localObject1, "work_spec_id");
      if (i == -1) {
        return;
      }
      while (((Cursor)localObject1).moveToNext()) {
        if (!((Cursor)localObject1).isNull(i))
        {
          localObject2 = (ArrayList)paramArrayMap.get(((Cursor)localObject1).getString(i));
          if (localObject2 != null) {
            ((ArrayList)localObject2).add(((Cursor)localObject1).getString(0));
          }
        }
      }
      return;
    }
    finally
    {
      ((Cursor)localObject1).close();
    }
  }
  
  public void delete(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfDelete.acquire();
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindString(1, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfDelete.release(localSupportSQLiteStatement);
    }
  }
  
  /* Error */
  public List<WorkSpec> getAllEligibleWorkSpecsForScheduling(int paramInt)
  {
    // Byte code:
    //   0: ldc_w 272
    //   3: iconst_1
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 29
    //   9: aload 29
    //   11: iconst_1
    //   12: iload_1
    //   13: i2l
    //   14: invokevirtual 276	androidx/room/RoomSQLiteQuery:bindLong	(IJ)V
    //   17: aload_0
    //   18: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   21: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   24: aload_0
    //   25: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   28: aload 29
    //   30: iconst_0
    //   31: aconst_null
    //   32: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   35: astore 28
    //   37: aload 28
    //   39: ldc_w 278
    //   42: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   45: istore 17
    //   47: aload 28
    //   49: ldc_w 283
    //   52: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   55: istore 13
    //   57: aload 28
    //   59: ldc_w 285
    //   62: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   65: istore 9
    //   67: aload 28
    //   69: ldc_w 287
    //   72: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   75: istore 22
    //   77: aload 28
    //   79: ldc_w 289
    //   82: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   85: istore 21
    //   87: aload 28
    //   89: ldc_w 291
    //   92: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   95: istore 20
    //   97: aload 28
    //   99: ldc_w 293
    //   102: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   105: istore 23
    //   107: aload 28
    //   109: ldc_w 295
    //   112: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   115: istore 25
    //   117: aload 28
    //   119: ldc_w 297
    //   122: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   125: istore 14
    //   127: aload 28
    //   129: ldc_w 299
    //   132: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   135: istore 24
    //   137: aload 28
    //   139: ldc_w 301
    //   142: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   145: istore 19
    //   147: aload 28
    //   149: ldc_w 303
    //   152: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   155: istore 11
    //   157: aload 28
    //   159: ldc_w 305
    //   162: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   165: istore 8
    //   167: aload 28
    //   169: ldc_w 307
    //   172: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   175: istore 4
    //   177: aload 28
    //   179: ldc_w 309
    //   182: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   185: istore 7
    //   187: aload 28
    //   189: ldc_w 311
    //   192: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   195: istore 6
    //   197: aload 28
    //   199: ldc_w 313
    //   202: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   205: istore 16
    //   207: aload 28
    //   209: ldc_w 315
    //   212: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   215: istore 15
    //   217: aload 28
    //   219: ldc_w 317
    //   222: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   225: istore 5
    //   227: aload 28
    //   229: ldc_w 319
    //   232: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   235: istore 12
    //   237: aload 28
    //   239: ldc_w 321
    //   242: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   245: istore 10
    //   247: aload 28
    //   249: ldc_w 323
    //   252: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   255: istore_1
    //   256: aload 28
    //   258: ldc_w 325
    //   261: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   264: istore_3
    //   265: aload 28
    //   267: ldc_w 327
    //   270: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   273: istore 18
    //   275: aload 28
    //   277: ldc_w 329
    //   280: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   283: istore_2
    //   284: new 118	java/util/ArrayList
    //   287: astore 33
    //   289: aload 33
    //   291: aload 28
    //   293: invokeinterface 332 1 0
    //   298: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   301: aload 28
    //   303: invokeinterface 197 1 0
    //   308: ifeq +479 -> 787
    //   311: aload 28
    //   313: iload 14
    //   315: invokeinterface 205 2 0
    //   320: astore 31
    //   322: aload 28
    //   324: iload 19
    //   326: invokeinterface 205 2 0
    //   331: astore 27
    //   333: new 335	androidx/work/Constraints
    //   336: astore 32
    //   338: aload 32
    //   340: invokespecial 336	androidx/work/Constraints:<init>	()V
    //   343: aload 32
    //   345: aload 28
    //   347: iload 17
    //   349: invokeinterface 340 2 0
    //   354: invokestatic 346	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   357: invokevirtual 350	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   360: aload 28
    //   362: iload 13
    //   364: invokeinterface 340 2 0
    //   369: ifeq +9 -> 378
    //   372: iconst_1
    //   373: istore 26
    //   375: goto +6 -> 381
    //   378: iconst_0
    //   379: istore 26
    //   381: aload 32
    //   383: iload 26
    //   385: invokevirtual 354	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   388: aload 28
    //   390: iload 9
    //   392: invokeinterface 340 2 0
    //   397: ifeq +9 -> 406
    //   400: iconst_1
    //   401: istore 26
    //   403: goto +6 -> 409
    //   406: iconst_0
    //   407: istore 26
    //   409: aload 32
    //   411: iload 26
    //   413: invokevirtual 357	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   416: aload 28
    //   418: iload 22
    //   420: invokeinterface 340 2 0
    //   425: ifeq +9 -> 434
    //   428: iconst_1
    //   429: istore 26
    //   431: goto +6 -> 437
    //   434: iconst_0
    //   435: istore 26
    //   437: aload 32
    //   439: iload 26
    //   441: invokevirtual 360	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   444: aload 28
    //   446: iload 21
    //   448: invokeinterface 340 2 0
    //   453: ifeq +9 -> 462
    //   456: iconst_1
    //   457: istore 26
    //   459: goto +6 -> 465
    //   462: iconst_0
    //   463: istore 26
    //   465: aload 32
    //   467: iload 26
    //   469: invokevirtual 363	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   472: aload 32
    //   474: aload 28
    //   476: iload 20
    //   478: invokeinterface 367 2 0
    //   483: invokevirtual 371	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   486: aload 32
    //   488: aload 28
    //   490: iload 23
    //   492: invokeinterface 367 2 0
    //   497: invokevirtual 374	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   500: aload 32
    //   502: aload 28
    //   504: iload 25
    //   506: invokeinterface 213 2 0
    //   511: invokestatic 378	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   514: invokevirtual 382	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   517: new 384	androidx/work/impl/model/WorkSpec
    //   520: astore 30
    //   522: aload 30
    //   524: aload 31
    //   526: aload 27
    //   528: invokespecial 387	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   531: aload 30
    //   533: aload 28
    //   535: iload 24
    //   537: invokeinterface 340 2 0
    //   542: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   545: putfield 394	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   548: aload 30
    //   550: aload 28
    //   552: iload 11
    //   554: invokeinterface 205 2 0
    //   559: putfield 398	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   562: aload 30
    //   564: aload 28
    //   566: iload 8
    //   568: invokeinterface 213 2 0
    //   573: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   576: putfield 401	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   579: aload 30
    //   581: aload 28
    //   583: iload 4
    //   585: invokeinterface 213 2 0
    //   590: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   593: putfield 403	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   596: aload 30
    //   598: aload 28
    //   600: iload 7
    //   602: invokeinterface 367 2 0
    //   607: putfield 407	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   610: aload 30
    //   612: aload 28
    //   614: iload 6
    //   616: invokeinterface 367 2 0
    //   621: putfield 410	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   624: aload 30
    //   626: aload 28
    //   628: iload 16
    //   630: invokeinterface 367 2 0
    //   635: putfield 413	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   638: aload 30
    //   640: aload 28
    //   642: iload 15
    //   644: invokeinterface 340 2 0
    //   649: putfield 417	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   652: aload 30
    //   654: aload 28
    //   656: iload 5
    //   658: invokeinterface 340 2 0
    //   663: invokestatic 421	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   666: putfield 425	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   669: aload 30
    //   671: aload 28
    //   673: iload 12
    //   675: invokeinterface 367 2 0
    //   680: putfield 428	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   683: aload 30
    //   685: aload 28
    //   687: iload 10
    //   689: invokeinterface 367 2 0
    //   694: putfield 431	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   697: aload 30
    //   699: aload 28
    //   701: iload_1
    //   702: invokeinterface 367 2 0
    //   707: putfield 434	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   710: aload 30
    //   712: aload 28
    //   714: iload_3
    //   715: invokeinterface 367 2 0
    //   720: putfield 437	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   723: aload 28
    //   725: iload 18
    //   727: invokeinterface 340 2 0
    //   732: ifeq +9 -> 741
    //   735: iconst_1
    //   736: istore 26
    //   738: goto +6 -> 744
    //   741: iconst_0
    //   742: istore 26
    //   744: aload 30
    //   746: iload 26
    //   748: putfield 441	androidx/work/impl/model/WorkSpec:expedited	Z
    //   751: aload 30
    //   753: aload 28
    //   755: iload_2
    //   756: invokeinterface 340 2 0
    //   761: invokestatic 445	androidx/work/impl/model/WorkTypeConverters:intToOutOfQuotaPolicy	(I)Landroidx/work/OutOfQuotaPolicy;
    //   764: putfield 449	androidx/work/impl/model/WorkSpec:outOfQuotaPolicy	Landroidx/work/OutOfQuotaPolicy;
    //   767: aload 30
    //   769: aload 32
    //   771: putfield 453	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   774: aload 33
    //   776: aload 30
    //   778: invokeinterface 456 2 0
    //   783: pop
    //   784: goto -483 -> 301
    //   787: aload 28
    //   789: invokeinterface 194 1 0
    //   794: aload 29
    //   796: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   799: aload 33
    //   801: areturn
    //   802: astore 27
    //   804: goto +15 -> 819
    //   807: astore 27
    //   809: goto +10 -> 819
    //   812: astore 27
    //   814: goto +5 -> 819
    //   817: astore 27
    //   819: aload 28
    //   821: invokeinterface 194 1 0
    //   826: aload 29
    //   828: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   831: aload 27
    //   833: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	834	0	this	WorkSpecDao_Impl
    //   0	834	1	paramInt	int
    //   283	473	2	i	int
    //   264	451	3	j	int
    //   175	409	4	k	int
    //   225	432	5	m	int
    //   195	420	6	n	int
    //   185	416	7	i1	int
    //   165	402	8	i2	int
    //   65	326	9	i3	int
    //   245	443	10	i4	int
    //   155	398	11	i5	int
    //   235	439	12	i6	int
    //   55	308	13	i7	int
    //   125	189	14	i8	int
    //   215	428	15	i9	int
    //   205	424	16	i10	int
    //   45	303	17	i11	int
    //   273	453	18	i12	int
    //   145	180	19	i13	int
    //   95	382	20	i14	int
    //   85	362	21	i15	int
    //   75	344	22	i16	int
    //   105	386	23	i17	int
    //   135	401	24	i18	int
    //   115	390	25	i19	int
    //   373	374	26	bool	boolean
    //   331	196	27	str1	String
    //   802	1	27	localObject1	Object
    //   807	1	27	localObject2	Object
    //   812	1	27	localObject3	Object
    //   817	15	27	localObject4	Object
    //   35	785	28	localCursor	Cursor
    //   7	820	29	localRoomSQLiteQuery	RoomSQLiteQuery
    //   520	257	30	localWorkSpec	WorkSpec
    //   320	205	31	str2	String
    //   336	434	32	localConstraints	Constraints
    //   287	513	33	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   177	289	802	finally
    //   289	301	802	finally
    //   301	372	802	finally
    //   381	400	802	finally
    //   409	428	802	finally
    //   437	456	802	finally
    //   465	735	802	finally
    //   744	784	802	finally
    //   157	177	807	finally
    //   147	157	812	finally
    //   37	147	817	finally
  }
  
  public List<String> getAllUnfinishedWork()
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5)", 0);
    this.__db.assertNotSuspendingTransaction();
    Cursor localCursor = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(localCursor.getCount());
      while (localCursor.moveToNext()) {
        localArrayList.add(localCursor.getString(0));
      }
      return localArrayList;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public List<String> getAllWorkSpecIds()
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
    this.__db.assertNotSuspendingTransaction();
    Cursor localCursor = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(localCursor.getCount());
      while (localCursor.moveToNext()) {
        localArrayList.add(localCursor.getString(0));
      }
      return localArrayList;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public LiveData<List<String>> getAllWorkSpecIdsLiveData()
  {
    final Object localObject = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
    InvalidationTracker localInvalidationTracker = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      /* Error */
      public List<String> call()
        throws Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   4: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   7: invokevirtual 44	androidx/room/RoomDatabase:beginTransaction	()V
        //   10: aload_0
        //   11: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   14: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   17: aload_0
        //   18: getfield 22	androidx/work/impl/model/WorkSpecDao_Impl$10:val$_statement	Landroidx/room/RoomSQLiteQuery;
        //   21: iconst_0
        //   22: aconst_null
        //   23: invokestatic 50	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
        //   26: astore_1
        //   27: new 52	java/util/ArrayList
        //   30: astore_2
        //   31: aload_2
        //   32: aload_1
        //   33: invokeinterface 58 1 0
        //   38: invokespecial 61	java/util/ArrayList:<init>	(I)V
        //   41: aload_1
        //   42: invokeinterface 65 1 0
        //   47: ifeq +20 -> 67
        //   50: aload_2
        //   51: aload_1
        //   52: iconst_0
        //   53: invokeinterface 69 2 0
        //   58: invokeinterface 75 2 0
        //   63: pop
        //   64: goto -23 -> 41
        //   67: aload_0
        //   68: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   71: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   74: invokevirtual 78	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   77: aload_1
        //   78: invokeinterface 81 1 0
        //   83: aload_0
        //   84: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   87: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   90: invokevirtual 84	androidx/room/RoomDatabase:endTransaction	()V
        //   93: aload_2
        //   94: areturn
        //   95: astore_2
        //   96: aload_1
        //   97: invokeinterface 81 1 0
        //   102: aload_2
        //   103: athrow
        //   104: astore_1
        //   105: aload_0
        //   106: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   109: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   112: invokevirtual 84	androidx/room/RoomDatabase:endTransaction	()V
        //   115: aload_1
        //   116: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	117	0	this	10
        //   26	71	1	localCursor	Cursor
        //   104	12	1	localObject1	Object
        //   30	64	2	localArrayList	ArrayList
        //   95	8	2	localObject2	Object
        // Exception table:
        //   from	to	target	type
        //   27	41	95	finally
        //   41	64	95	finally
        //   67	77	95	finally
        //   10	27	104	finally
        //   77	83	104	finally
        //   96	104	104	finally
      }
      
      protected void finalize()
      {
        localObject.release();
      }
    };
    return localInvalidationTracker.createLiveData(new String[] { "workspec" }, true, (Callable)localObject);
  }
  
  /* Error */
  public List<WorkSpec> getEligibleWorkForScheduling(int paramInt)
  {
    // Byte code:
    //   0: ldc_w 487
    //   3: iconst_1
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 29
    //   9: aload 29
    //   11: iconst_1
    //   12: iload_1
    //   13: i2l
    //   14: invokevirtual 276	androidx/room/RoomSQLiteQuery:bindLong	(IJ)V
    //   17: aload_0
    //   18: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   21: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   24: aload_0
    //   25: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   28: aload 29
    //   30: iconst_0
    //   31: aconst_null
    //   32: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   35: astore 28
    //   37: aload 28
    //   39: ldc_w 278
    //   42: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   45: istore 17
    //   47: aload 28
    //   49: ldc_w 283
    //   52: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   55: istore 12
    //   57: aload 28
    //   59: ldc_w 285
    //   62: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   65: istore 9
    //   67: aload 28
    //   69: ldc_w 287
    //   72: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   75: istore 21
    //   77: aload 28
    //   79: ldc_w 289
    //   82: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   85: istore 25
    //   87: aload 28
    //   89: ldc_w 291
    //   92: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   95: istore 24
    //   97: aload 28
    //   99: ldc_w 293
    //   102: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   105: istore 22
    //   107: aload 28
    //   109: ldc_w 295
    //   112: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   115: istore 23
    //   117: aload 28
    //   119: ldc_w 297
    //   122: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   125: istore 13
    //   127: aload 28
    //   129: ldc_w 299
    //   132: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   135: istore 20
    //   137: aload 28
    //   139: ldc_w 301
    //   142: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   145: istore 19
    //   147: aload 28
    //   149: ldc_w 303
    //   152: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   155: istore 11
    //   157: aload 28
    //   159: ldc_w 305
    //   162: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   165: istore 8
    //   167: aload 28
    //   169: ldc_w 307
    //   172: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   175: istore 4
    //   177: aload 28
    //   179: ldc_w 309
    //   182: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   185: istore 7
    //   187: aload 28
    //   189: ldc_w 311
    //   192: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   195: istore 5
    //   197: aload 28
    //   199: ldc_w 313
    //   202: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   205: istore 18
    //   207: aload 28
    //   209: ldc_w 315
    //   212: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   215: istore 15
    //   217: aload 28
    //   219: ldc_w 317
    //   222: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   225: istore 6
    //   227: aload 28
    //   229: ldc_w 319
    //   232: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   235: istore 14
    //   237: aload 28
    //   239: ldc_w 321
    //   242: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   245: istore 10
    //   247: aload 28
    //   249: ldc_w 323
    //   252: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   255: istore_1
    //   256: aload 28
    //   258: ldc_w 325
    //   261: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   264: istore_3
    //   265: aload 28
    //   267: ldc_w 327
    //   270: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   273: istore 16
    //   275: aload 28
    //   277: ldc_w 329
    //   280: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   283: istore_2
    //   284: new 118	java/util/ArrayList
    //   287: astore 33
    //   289: aload 33
    //   291: aload 28
    //   293: invokeinterface 332 1 0
    //   298: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   301: aload 28
    //   303: invokeinterface 197 1 0
    //   308: ifeq +479 -> 787
    //   311: aload 28
    //   313: iload 13
    //   315: invokeinterface 205 2 0
    //   320: astore 31
    //   322: aload 28
    //   324: iload 19
    //   326: invokeinterface 205 2 0
    //   331: astore 30
    //   333: new 335	androidx/work/Constraints
    //   336: astore 32
    //   338: aload 32
    //   340: invokespecial 336	androidx/work/Constraints:<init>	()V
    //   343: aload 32
    //   345: aload 28
    //   347: iload 17
    //   349: invokeinterface 340 2 0
    //   354: invokestatic 346	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   357: invokevirtual 350	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   360: aload 28
    //   362: iload 12
    //   364: invokeinterface 340 2 0
    //   369: ifeq +9 -> 378
    //   372: iconst_1
    //   373: istore 26
    //   375: goto +6 -> 381
    //   378: iconst_0
    //   379: istore 26
    //   381: aload 32
    //   383: iload 26
    //   385: invokevirtual 354	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   388: aload 28
    //   390: iload 9
    //   392: invokeinterface 340 2 0
    //   397: ifeq +9 -> 406
    //   400: iconst_1
    //   401: istore 26
    //   403: goto +6 -> 409
    //   406: iconst_0
    //   407: istore 26
    //   409: aload 32
    //   411: iload 26
    //   413: invokevirtual 357	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   416: aload 28
    //   418: iload 21
    //   420: invokeinterface 340 2 0
    //   425: ifeq +9 -> 434
    //   428: iconst_1
    //   429: istore 26
    //   431: goto +6 -> 437
    //   434: iconst_0
    //   435: istore 26
    //   437: aload 32
    //   439: iload 26
    //   441: invokevirtual 360	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   444: aload 28
    //   446: iload 25
    //   448: invokeinterface 340 2 0
    //   453: ifeq +9 -> 462
    //   456: iconst_1
    //   457: istore 26
    //   459: goto +6 -> 465
    //   462: iconst_0
    //   463: istore 26
    //   465: aload 32
    //   467: iload 26
    //   469: invokevirtual 363	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   472: aload 32
    //   474: aload 28
    //   476: iload 24
    //   478: invokeinterface 367 2 0
    //   483: invokevirtual 371	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   486: aload 32
    //   488: aload 28
    //   490: iload 22
    //   492: invokeinterface 367 2 0
    //   497: invokevirtual 374	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   500: aload 32
    //   502: aload 28
    //   504: iload 23
    //   506: invokeinterface 213 2 0
    //   511: invokestatic 378	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   514: invokevirtual 382	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   517: new 384	androidx/work/impl/model/WorkSpec
    //   520: astore 27
    //   522: aload 27
    //   524: aload 31
    //   526: aload 30
    //   528: invokespecial 387	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   531: aload 27
    //   533: aload 28
    //   535: iload 20
    //   537: invokeinterface 340 2 0
    //   542: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   545: putfield 394	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   548: aload 27
    //   550: aload 28
    //   552: iload 11
    //   554: invokeinterface 205 2 0
    //   559: putfield 398	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   562: aload 27
    //   564: aload 28
    //   566: iload 8
    //   568: invokeinterface 213 2 0
    //   573: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   576: putfield 401	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   579: aload 27
    //   581: aload 28
    //   583: iload 4
    //   585: invokeinterface 213 2 0
    //   590: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   593: putfield 403	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   596: aload 27
    //   598: aload 28
    //   600: iload 7
    //   602: invokeinterface 367 2 0
    //   607: putfield 407	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   610: aload 27
    //   612: aload 28
    //   614: iload 5
    //   616: invokeinterface 367 2 0
    //   621: putfield 410	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   624: aload 27
    //   626: aload 28
    //   628: iload 18
    //   630: invokeinterface 367 2 0
    //   635: putfield 413	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   638: aload 27
    //   640: aload 28
    //   642: iload 15
    //   644: invokeinterface 340 2 0
    //   649: putfield 417	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   652: aload 27
    //   654: aload 28
    //   656: iload 6
    //   658: invokeinterface 340 2 0
    //   663: invokestatic 421	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   666: putfield 425	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   669: aload 27
    //   671: aload 28
    //   673: iload 14
    //   675: invokeinterface 367 2 0
    //   680: putfield 428	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   683: aload 27
    //   685: aload 28
    //   687: iload 10
    //   689: invokeinterface 367 2 0
    //   694: putfield 431	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   697: aload 27
    //   699: aload 28
    //   701: iload_1
    //   702: invokeinterface 367 2 0
    //   707: putfield 434	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   710: aload 27
    //   712: aload 28
    //   714: iload_3
    //   715: invokeinterface 367 2 0
    //   720: putfield 437	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   723: aload 28
    //   725: iload 16
    //   727: invokeinterface 340 2 0
    //   732: ifeq +9 -> 741
    //   735: iconst_1
    //   736: istore 26
    //   738: goto +6 -> 744
    //   741: iconst_0
    //   742: istore 26
    //   744: aload 27
    //   746: iload 26
    //   748: putfield 441	androidx/work/impl/model/WorkSpec:expedited	Z
    //   751: aload 27
    //   753: aload 28
    //   755: iload_2
    //   756: invokeinterface 340 2 0
    //   761: invokestatic 445	androidx/work/impl/model/WorkTypeConverters:intToOutOfQuotaPolicy	(I)Landroidx/work/OutOfQuotaPolicy;
    //   764: putfield 449	androidx/work/impl/model/WorkSpec:outOfQuotaPolicy	Landroidx/work/OutOfQuotaPolicy;
    //   767: aload 27
    //   769: aload 32
    //   771: putfield 453	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   774: aload 33
    //   776: aload 27
    //   778: invokeinterface 456 2 0
    //   783: pop
    //   784: goto -483 -> 301
    //   787: aload 28
    //   789: invokeinterface 194 1 0
    //   794: aload 29
    //   796: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   799: aload 33
    //   801: areturn
    //   802: astore 27
    //   804: goto +15 -> 819
    //   807: astore 27
    //   809: goto +10 -> 819
    //   812: astore 27
    //   814: goto +5 -> 819
    //   817: astore 27
    //   819: aload 28
    //   821: invokeinterface 194 1 0
    //   826: aload 29
    //   828: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   831: aload 27
    //   833: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	834	0	this	WorkSpecDao_Impl
    //   0	834	1	paramInt	int
    //   283	473	2	i	int
    //   264	451	3	j	int
    //   175	409	4	k	int
    //   195	420	5	m	int
    //   225	432	6	n	int
    //   185	416	7	i1	int
    //   165	402	8	i2	int
    //   65	326	9	i3	int
    //   245	443	10	i4	int
    //   155	398	11	i5	int
    //   55	308	12	i6	int
    //   125	189	13	i7	int
    //   235	439	14	i8	int
    //   215	428	15	i9	int
    //   273	453	16	i10	int
    //   45	303	17	i11	int
    //   205	424	18	i12	int
    //   145	180	19	i13	int
    //   135	401	20	i14	int
    //   75	344	21	i15	int
    //   105	386	22	i16	int
    //   115	390	23	i17	int
    //   95	382	24	i18	int
    //   85	362	25	i19	int
    //   373	374	26	bool	boolean
    //   520	257	27	localWorkSpec	WorkSpec
    //   802	1	27	localObject1	Object
    //   807	1	27	localObject2	Object
    //   812	1	27	localObject3	Object
    //   817	15	27	localObject4	Object
    //   35	785	28	localCursor	Cursor
    //   7	820	29	localRoomSQLiteQuery	RoomSQLiteQuery
    //   331	196	30	str1	String
    //   320	205	31	str2	String
    //   336	434	32	localConstraints	Constraints
    //   287	513	33	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   177	289	802	finally
    //   289	301	802	finally
    //   301	372	802	finally
    //   381	400	802	finally
    //   409	428	802	finally
    //   437	456	802	finally
    //   465	735	802	finally
    //   744	784	802	finally
    //   157	177	807	finally
    //   147	157	812	finally
    //   37	147	817	finally
  }
  
  public List<Data> getInputsFromPrerequisites(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT output FROM workspec WHERE id IN (SELECT prerequisite_id FROM dependency WHERE work_spec_id=?)", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    paramString = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramString.getCount());
      while (paramString.moveToNext()) {
        localArrayList.add(Data.fromByteArray(paramString.getBlob(0)));
      }
      return localArrayList;
    }
    finally
    {
      paramString.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  /* Error */
  public List<WorkSpec> getRecentlyCompletedWork(long paramLong)
  {
    // Byte code:
    //   0: ldc_w 497
    //   3: iconst_1
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 30
    //   9: aload 30
    //   11: iconst_1
    //   12: lload_1
    //   13: invokevirtual 276	androidx/room/RoomSQLiteQuery:bindLong	(IJ)V
    //   16: aload_0
    //   17: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   20: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   23: aload_0
    //   24: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   27: aload 30
    //   29: iconst_0
    //   30: aconst_null
    //   31: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   34: astore 31
    //   36: aload 31
    //   38: ldc_w 278
    //   41: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   44: istore 23
    //   46: aload 31
    //   48: ldc_w 283
    //   51: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   54: istore 17
    //   56: aload 31
    //   58: ldc_w 285
    //   61: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   64: istore 14
    //   66: aload 31
    //   68: ldc_w 287
    //   71: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   74: istore 26
    //   76: aload 31
    //   78: ldc_w 289
    //   81: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   84: istore 27
    //   86: aload 31
    //   88: ldc_w 291
    //   91: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   94: istore 25
    //   96: aload 31
    //   98: ldc_w 293
    //   101: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   104: istore 24
    //   106: aload 31
    //   108: ldc_w 295
    //   111: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   114: istore 16
    //   116: aload 31
    //   118: ldc_w 297
    //   121: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   124: istore 11
    //   126: aload 31
    //   128: ldc_w 299
    //   131: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   134: istore 19
    //   136: aload 31
    //   138: ldc_w 301
    //   141: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   144: istore 20
    //   146: aload 31
    //   148: ldc_w 303
    //   151: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   154: istore 12
    //   156: aload 31
    //   158: ldc_w 305
    //   161: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   164: istore 13
    //   166: aload 31
    //   168: ldc_w 307
    //   171: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   174: istore 22
    //   176: aload 31
    //   178: ldc_w 309
    //   181: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   184: istore 10
    //   186: aload 31
    //   188: ldc_w 311
    //   191: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   194: istore 21
    //   196: aload 31
    //   198: ldc_w 313
    //   201: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   204: istore 6
    //   206: aload 31
    //   208: ldc_w 315
    //   211: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   214: istore 18
    //   216: aload 31
    //   218: ldc_w 317
    //   221: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   224: istore 5
    //   226: aload 31
    //   228: ldc_w 319
    //   231: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   234: istore 15
    //   236: aload 31
    //   238: ldc_w 321
    //   241: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   244: istore 9
    //   246: aload 31
    //   248: ldc_w 323
    //   251: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   254: istore 4
    //   256: aload 31
    //   258: ldc_w 325
    //   261: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   264: istore_3
    //   265: aload 31
    //   267: ldc_w 327
    //   270: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   273: istore 8
    //   275: aload 31
    //   277: ldc_w 329
    //   280: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   283: istore 7
    //   285: new 118	java/util/ArrayList
    //   288: astore 34
    //   290: aload 34
    //   292: aload 31
    //   294: invokeinterface 332 1 0
    //   299: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   302: aload 31
    //   304: invokeinterface 197 1 0
    //   309: ifeq +481 -> 790
    //   312: aload 31
    //   314: iload 11
    //   316: invokeinterface 205 2 0
    //   321: astore 33
    //   323: aload 31
    //   325: iload 20
    //   327: invokeinterface 205 2 0
    //   332: astore 29
    //   334: new 335	androidx/work/Constraints
    //   337: astore 35
    //   339: aload 35
    //   341: invokespecial 336	androidx/work/Constraints:<init>	()V
    //   344: aload 35
    //   346: aload 31
    //   348: iload 23
    //   350: invokeinterface 340 2 0
    //   355: invokestatic 346	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   358: invokevirtual 350	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   361: aload 31
    //   363: iload 17
    //   365: invokeinterface 340 2 0
    //   370: ifeq +9 -> 379
    //   373: iconst_1
    //   374: istore 28
    //   376: goto +6 -> 382
    //   379: iconst_0
    //   380: istore 28
    //   382: aload 35
    //   384: iload 28
    //   386: invokevirtual 354	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   389: aload 31
    //   391: iload 14
    //   393: invokeinterface 340 2 0
    //   398: ifeq +9 -> 407
    //   401: iconst_1
    //   402: istore 28
    //   404: goto +6 -> 410
    //   407: iconst_0
    //   408: istore 28
    //   410: aload 35
    //   412: iload 28
    //   414: invokevirtual 357	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   417: aload 31
    //   419: iload 26
    //   421: invokeinterface 340 2 0
    //   426: ifeq +9 -> 435
    //   429: iconst_1
    //   430: istore 28
    //   432: goto +6 -> 438
    //   435: iconst_0
    //   436: istore 28
    //   438: aload 35
    //   440: iload 28
    //   442: invokevirtual 360	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   445: aload 31
    //   447: iload 27
    //   449: invokeinterface 340 2 0
    //   454: ifeq +9 -> 463
    //   457: iconst_1
    //   458: istore 28
    //   460: goto +6 -> 466
    //   463: iconst_0
    //   464: istore 28
    //   466: aload 35
    //   468: iload 28
    //   470: invokevirtual 363	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   473: aload 35
    //   475: aload 31
    //   477: iload 25
    //   479: invokeinterface 367 2 0
    //   484: invokevirtual 371	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   487: aload 35
    //   489: aload 31
    //   491: iload 24
    //   493: invokeinterface 367 2 0
    //   498: invokevirtual 374	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   501: aload 35
    //   503: aload 31
    //   505: iload 16
    //   507: invokeinterface 213 2 0
    //   512: invokestatic 378	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   515: invokevirtual 382	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   518: new 384	androidx/work/impl/model/WorkSpec
    //   521: astore 32
    //   523: aload 32
    //   525: aload 33
    //   527: aload 29
    //   529: invokespecial 387	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   532: aload 32
    //   534: aload 31
    //   536: iload 19
    //   538: invokeinterface 340 2 0
    //   543: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   546: putfield 394	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   549: aload 32
    //   551: aload 31
    //   553: iload 12
    //   555: invokeinterface 205 2 0
    //   560: putfield 398	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   563: aload 32
    //   565: aload 31
    //   567: iload 13
    //   569: invokeinterface 213 2 0
    //   574: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   577: putfield 401	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   580: aload 32
    //   582: aload 31
    //   584: iload 22
    //   586: invokeinterface 213 2 0
    //   591: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   594: putfield 403	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   597: aload 32
    //   599: aload 31
    //   601: iload 10
    //   603: invokeinterface 367 2 0
    //   608: putfield 407	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   611: aload 32
    //   613: aload 31
    //   615: iload 21
    //   617: invokeinterface 367 2 0
    //   622: putfield 410	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   625: aload 32
    //   627: aload 31
    //   629: iload 6
    //   631: invokeinterface 367 2 0
    //   636: putfield 413	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   639: aload 32
    //   641: aload 31
    //   643: iload 18
    //   645: invokeinterface 340 2 0
    //   650: putfield 417	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   653: aload 32
    //   655: aload 31
    //   657: iload 5
    //   659: invokeinterface 340 2 0
    //   664: invokestatic 421	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   667: putfield 425	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   670: aload 32
    //   672: aload 31
    //   674: iload 15
    //   676: invokeinterface 367 2 0
    //   681: putfield 428	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   684: aload 32
    //   686: aload 31
    //   688: iload 9
    //   690: invokeinterface 367 2 0
    //   695: putfield 431	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   698: aload 32
    //   700: aload 31
    //   702: iload 4
    //   704: invokeinterface 367 2 0
    //   709: putfield 434	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   712: aload 32
    //   714: aload 31
    //   716: iload_3
    //   717: invokeinterface 367 2 0
    //   722: putfield 437	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   725: aload 31
    //   727: iload 8
    //   729: invokeinterface 340 2 0
    //   734: ifeq +9 -> 743
    //   737: iconst_1
    //   738: istore 28
    //   740: goto +6 -> 746
    //   743: iconst_0
    //   744: istore 28
    //   746: aload 32
    //   748: iload 28
    //   750: putfield 441	androidx/work/impl/model/WorkSpec:expedited	Z
    //   753: aload 32
    //   755: aload 31
    //   757: iload 7
    //   759: invokeinterface 340 2 0
    //   764: invokestatic 445	androidx/work/impl/model/WorkTypeConverters:intToOutOfQuotaPolicy	(I)Landroidx/work/OutOfQuotaPolicy;
    //   767: putfield 449	androidx/work/impl/model/WorkSpec:outOfQuotaPolicy	Landroidx/work/OutOfQuotaPolicy;
    //   770: aload 32
    //   772: aload 35
    //   774: putfield 453	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   777: aload 34
    //   779: aload 32
    //   781: invokeinterface 456 2 0
    //   786: pop
    //   787: goto -485 -> 302
    //   790: aload 31
    //   792: invokeinterface 194 1 0
    //   797: aload 30
    //   799: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   802: aload 34
    //   804: areturn
    //   805: astore 29
    //   807: goto +15 -> 822
    //   810: astore 29
    //   812: goto +10 -> 822
    //   815: astore 29
    //   817: goto +5 -> 822
    //   820: astore 29
    //   822: aload 31
    //   824: invokeinterface 194 1 0
    //   829: aload 30
    //   831: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   834: aload 29
    //   836: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	837	0	this	WorkSpecDao_Impl
    //   0	837	1	paramLong	long
    //   264	453	3	i	int
    //   254	449	4	j	int
    //   224	434	5	k	int
    //   204	426	6	m	int
    //   283	475	7	n	int
    //   273	455	8	i1	int
    //   244	445	9	i2	int
    //   184	418	10	i3	int
    //   124	191	11	i4	int
    //   154	400	12	i5	int
    //   164	404	13	i6	int
    //   64	328	14	i7	int
    //   234	441	15	i8	int
    //   114	392	16	i9	int
    //   54	310	17	i10	int
    //   214	430	18	i11	int
    //   134	403	19	i12	int
    //   144	182	20	i13	int
    //   194	422	21	i14	int
    //   174	411	22	i15	int
    //   44	305	23	i16	int
    //   104	388	24	i17	int
    //   94	384	25	i18	int
    //   74	346	26	i19	int
    //   84	364	27	i20	int
    //   374	375	28	bool	boolean
    //   332	196	29	str1	String
    //   805	1	29	localObject1	Object
    //   810	1	29	localObject2	Object
    //   815	1	29	localObject3	Object
    //   820	15	29	localObject4	Object
    //   7	823	30	localRoomSQLiteQuery	RoomSQLiteQuery
    //   34	789	31	localCursor	Cursor
    //   521	259	32	localWorkSpec	WorkSpec
    //   321	205	33	str2	String
    //   288	515	34	localArrayList	ArrayList
    //   337	436	35	localConstraints	Constraints
    // Exception table:
    //   from	to	target	type
    //   176	290	805	finally
    //   290	302	805	finally
    //   302	373	805	finally
    //   382	401	805	finally
    //   410	429	805	finally
    //   438	457	805	finally
    //   466	737	805	finally
    //   746	787	805	finally
    //   146	176	810	finally
    //   136	146	815	finally
    //   36	136	820	finally
  }
  
  /* Error */
  public List<WorkSpec> getRunningWork()
  {
    // Byte code:
    //   0: ldc_w 501
    //   3: iconst_0
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 29
    //   9: aload_0
    //   10: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   13: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   16: aload_0
    //   17: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   20: aload 29
    //   22: iconst_0
    //   23: aconst_null
    //   24: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   27: astore 28
    //   29: aload 28
    //   31: ldc_w 278
    //   34: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   37: istore 20
    //   39: aload 28
    //   41: ldc_w 283
    //   44: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   47: istore 14
    //   49: aload 28
    //   51: ldc_w 285
    //   54: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   57: istore 13
    //   59: aload 28
    //   61: ldc_w 287
    //   64: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   67: istore 9
    //   69: aload 28
    //   71: ldc_w 289
    //   74: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   77: istore 19
    //   79: aload 28
    //   81: ldc_w 291
    //   84: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   87: istore 25
    //   89: aload 28
    //   91: ldc_w 293
    //   94: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   97: istore 22
    //   99: aload 28
    //   101: ldc_w 295
    //   104: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   107: istore 21
    //   109: aload 28
    //   111: ldc_w 297
    //   114: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   117: istore 17
    //   119: aload 28
    //   121: ldc_w 299
    //   124: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   127: istore 23
    //   129: aload 28
    //   131: ldc_w 301
    //   134: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   137: istore 24
    //   139: aload 28
    //   141: ldc_w 303
    //   144: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   147: istore 12
    //   149: aload 28
    //   151: ldc_w 305
    //   154: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   157: istore 16
    //   159: aload 28
    //   161: ldc_w 307
    //   164: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   167: istore_3
    //   168: aload 28
    //   170: ldc_w 309
    //   173: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   176: istore 7
    //   178: aload 28
    //   180: ldc_w 311
    //   183: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   186: istore 18
    //   188: aload 28
    //   190: ldc_w 313
    //   193: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   196: istore 5
    //   198: aload 28
    //   200: ldc_w 315
    //   203: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   206: istore 15
    //   208: aload 28
    //   210: ldc_w 317
    //   213: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   216: istore_2
    //   217: aload 28
    //   219: ldc_w 319
    //   222: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   225: istore 11
    //   227: aload 28
    //   229: ldc_w 321
    //   232: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   235: istore 10
    //   237: aload 28
    //   239: ldc_w 323
    //   242: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   245: istore_1
    //   246: aload 28
    //   248: ldc_w 325
    //   251: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   254: istore 6
    //   256: aload 28
    //   258: ldc_w 327
    //   261: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   264: istore 4
    //   266: aload 28
    //   268: ldc_w 329
    //   271: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   274: istore 8
    //   276: new 118	java/util/ArrayList
    //   279: astore 33
    //   281: aload 33
    //   283: aload 28
    //   285: invokeinterface 332 1 0
    //   290: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   293: aload 28
    //   295: invokeinterface 197 1 0
    //   300: ifeq +479 -> 779
    //   303: aload 28
    //   305: iload 17
    //   307: invokeinterface 205 2 0
    //   312: astore 27
    //   314: aload 28
    //   316: iload 24
    //   318: invokeinterface 205 2 0
    //   323: astore 32
    //   325: new 335	androidx/work/Constraints
    //   328: astore 30
    //   330: aload 30
    //   332: invokespecial 336	androidx/work/Constraints:<init>	()V
    //   335: aload 30
    //   337: aload 28
    //   339: iload 20
    //   341: invokeinterface 340 2 0
    //   346: invokestatic 346	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   349: invokevirtual 350	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   352: aload 28
    //   354: iload 14
    //   356: invokeinterface 340 2 0
    //   361: ifeq +9 -> 370
    //   364: iconst_1
    //   365: istore 26
    //   367: goto +6 -> 373
    //   370: iconst_0
    //   371: istore 26
    //   373: aload 30
    //   375: iload 26
    //   377: invokevirtual 354	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   380: aload 28
    //   382: iload 13
    //   384: invokeinterface 340 2 0
    //   389: ifeq +9 -> 398
    //   392: iconst_1
    //   393: istore 26
    //   395: goto +6 -> 401
    //   398: iconst_0
    //   399: istore 26
    //   401: aload 30
    //   403: iload 26
    //   405: invokevirtual 357	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   408: aload 28
    //   410: iload 9
    //   412: invokeinterface 340 2 0
    //   417: ifeq +9 -> 426
    //   420: iconst_1
    //   421: istore 26
    //   423: goto +6 -> 429
    //   426: iconst_0
    //   427: istore 26
    //   429: aload 30
    //   431: iload 26
    //   433: invokevirtual 360	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   436: aload 28
    //   438: iload 19
    //   440: invokeinterface 340 2 0
    //   445: ifeq +9 -> 454
    //   448: iconst_1
    //   449: istore 26
    //   451: goto +6 -> 457
    //   454: iconst_0
    //   455: istore 26
    //   457: aload 30
    //   459: iload 26
    //   461: invokevirtual 363	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   464: aload 30
    //   466: aload 28
    //   468: iload 25
    //   470: invokeinterface 367 2 0
    //   475: invokevirtual 371	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   478: aload 30
    //   480: aload 28
    //   482: iload 22
    //   484: invokeinterface 367 2 0
    //   489: invokevirtual 374	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   492: aload 30
    //   494: aload 28
    //   496: iload 21
    //   498: invokeinterface 213 2 0
    //   503: invokestatic 378	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   506: invokevirtual 382	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   509: new 384	androidx/work/impl/model/WorkSpec
    //   512: astore 31
    //   514: aload 31
    //   516: aload 27
    //   518: aload 32
    //   520: invokespecial 387	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   523: aload 31
    //   525: aload 28
    //   527: iload 23
    //   529: invokeinterface 340 2 0
    //   534: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   537: putfield 394	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   540: aload 31
    //   542: aload 28
    //   544: iload 12
    //   546: invokeinterface 205 2 0
    //   551: putfield 398	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   554: aload 31
    //   556: aload 28
    //   558: iload 16
    //   560: invokeinterface 213 2 0
    //   565: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   568: putfield 401	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   571: aload 31
    //   573: aload 28
    //   575: iload_3
    //   576: invokeinterface 213 2 0
    //   581: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   584: putfield 403	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   587: aload 31
    //   589: aload 28
    //   591: iload 7
    //   593: invokeinterface 367 2 0
    //   598: putfield 407	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   601: aload 31
    //   603: aload 28
    //   605: iload 18
    //   607: invokeinterface 367 2 0
    //   612: putfield 410	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   615: aload 31
    //   617: aload 28
    //   619: iload 5
    //   621: invokeinterface 367 2 0
    //   626: putfield 413	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   629: aload 31
    //   631: aload 28
    //   633: iload 15
    //   635: invokeinterface 340 2 0
    //   640: putfield 417	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   643: aload 31
    //   645: aload 28
    //   647: iload_2
    //   648: invokeinterface 340 2 0
    //   653: invokestatic 421	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   656: putfield 425	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   659: aload 31
    //   661: aload 28
    //   663: iload 11
    //   665: invokeinterface 367 2 0
    //   670: putfield 428	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   673: aload 31
    //   675: aload 28
    //   677: iload 10
    //   679: invokeinterface 367 2 0
    //   684: putfield 431	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   687: aload 31
    //   689: aload 28
    //   691: iload_1
    //   692: invokeinterface 367 2 0
    //   697: putfield 434	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   700: aload 31
    //   702: aload 28
    //   704: iload 6
    //   706: invokeinterface 367 2 0
    //   711: putfield 437	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   714: aload 28
    //   716: iload 4
    //   718: invokeinterface 340 2 0
    //   723: ifeq +9 -> 732
    //   726: iconst_1
    //   727: istore 26
    //   729: goto +6 -> 735
    //   732: iconst_0
    //   733: istore 26
    //   735: aload 31
    //   737: iload 26
    //   739: putfield 441	androidx/work/impl/model/WorkSpec:expedited	Z
    //   742: aload 31
    //   744: aload 28
    //   746: iload 8
    //   748: invokeinterface 340 2 0
    //   753: invokestatic 445	androidx/work/impl/model/WorkTypeConverters:intToOutOfQuotaPolicy	(I)Landroidx/work/OutOfQuotaPolicy;
    //   756: putfield 449	androidx/work/impl/model/WorkSpec:outOfQuotaPolicy	Landroidx/work/OutOfQuotaPolicy;
    //   759: aload 31
    //   761: aload 30
    //   763: putfield 453	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   766: aload 33
    //   768: aload 31
    //   770: invokeinterface 456 2 0
    //   775: pop
    //   776: goto -483 -> 293
    //   779: aload 28
    //   781: invokeinterface 194 1 0
    //   786: aload 29
    //   788: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   791: aload 33
    //   793: areturn
    //   794: astore 27
    //   796: goto +10 -> 806
    //   799: astore 27
    //   801: goto +5 -> 806
    //   804: astore 27
    //   806: aload 28
    //   808: invokeinterface 194 1 0
    //   813: aload 29
    //   815: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   818: aload 27
    //   820: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	821	0	this	WorkSpecDao_Impl
    //   245	447	1	i	int
    //   216	432	2	j	int
    //   167	409	3	k	int
    //   264	453	4	m	int
    //   196	424	5	n	int
    //   254	451	6	i1	int
    //   176	416	7	i2	int
    //   274	473	8	i3	int
    //   67	344	9	i4	int
    //   235	443	10	i5	int
    //   225	439	11	i6	int
    //   147	398	12	i7	int
    //   57	326	13	i8	int
    //   47	308	14	i9	int
    //   206	428	15	i10	int
    //   157	402	16	i11	int
    //   117	189	17	i12	int
    //   186	420	18	i13	int
    //   77	362	19	i14	int
    //   37	303	20	i15	int
    //   107	390	21	i16	int
    //   97	386	22	i17	int
    //   127	401	23	i18	int
    //   137	180	24	i19	int
    //   87	382	25	i20	int
    //   365	373	26	bool	boolean
    //   312	205	27	str1	String
    //   794	1	27	localObject1	Object
    //   799	1	27	localObject2	Object
    //   804	15	27	localObject3	Object
    //   27	780	28	localCursor	Cursor
    //   7	807	29	localRoomSQLiteQuery	RoomSQLiteQuery
    //   328	434	30	localConstraints	Constraints
    //   512	257	31	localWorkSpec	WorkSpec
    //   323	196	32	str2	String
    //   279	513	33	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   168	281	794	finally
    //   281	293	794	finally
    //   293	364	794	finally
    //   373	392	794	finally
    //   401	420	794	finally
    //   429	448	794	finally
    //   457	726	794	finally
    //   735	776	794	finally
    //   159	168	799	finally
    //   29	159	804	finally
  }
  
  public LiveData<Long> getScheduleRequestedAtLiveData(String paramString)
  {
    final Object localObject = RoomSQLiteQuery.acquire("SELECT schedule_requested_at FROM workspec WHERE id=?", 1);
    if (paramString == null) {
      ((RoomSQLiteQuery)localObject).bindNull(1);
    } else {
      ((RoomSQLiteQuery)localObject).bindString(1, paramString);
    }
    paramString = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      public Long call()
        throws Exception
      {
        Cursor localCursor = DBUtil.query(WorkSpecDao_Impl.this.__db, localObject, false, null);
        try
        {
          Long localLong;
          if (localCursor.moveToFirst())
          {
            if (localCursor.isNull(0)) {
              localLong = null;
            } else {
              localLong = Long.valueOf(localCursor.getLong(0));
            }
          }
          else {
            localLong = null;
          }
          return localLong;
        }
        finally
        {
          localCursor.close();
        }
      }
      
      protected void finalize()
      {
        localObject.release();
      }
    };
    return paramString.createLiveData(new String[] { "workspec" }, false, (Callable)localObject);
  }
  
  /* Error */
  public List<WorkSpec> getScheduledWork()
  {
    // Byte code:
    //   0: ldc_w 510
    //   3: iconst_0
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 28
    //   9: aload_0
    //   10: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   13: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   16: aload_0
    //   17: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   20: aload 28
    //   22: iconst_0
    //   23: aconst_null
    //   24: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   27: astore 29
    //   29: aload 29
    //   31: ldc_w 278
    //   34: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   37: istore 19
    //   39: aload 29
    //   41: ldc_w 283
    //   44: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   47: istore 15
    //   49: aload 29
    //   51: ldc_w 285
    //   54: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   57: istore 12
    //   59: aload 29
    //   61: ldc_w 287
    //   64: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   67: istore 9
    //   69: aload 29
    //   71: ldc_w 289
    //   74: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   77: istore 20
    //   79: aload 29
    //   81: ldc_w 291
    //   84: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   87: istore 21
    //   89: aload 29
    //   91: ldc_w 293
    //   94: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   97: istore 25
    //   99: aload 29
    //   101: ldc_w 295
    //   104: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   107: istore 24
    //   109: aload 29
    //   111: ldc_w 297
    //   114: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   117: istore 16
    //   119: aload 29
    //   121: ldc_w 299
    //   124: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   127: istore 22
    //   129: aload 29
    //   131: ldc_w 301
    //   134: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   137: istore 23
    //   139: aload 29
    //   141: ldc_w 303
    //   144: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   147: istore 13
    //   149: aload 29
    //   151: ldc_w 305
    //   154: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   157: istore 17
    //   159: aload 29
    //   161: ldc_w 307
    //   164: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   167: istore_3
    //   168: aload 29
    //   170: ldc_w 309
    //   173: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   176: istore 7
    //   178: aload 29
    //   180: ldc_w 311
    //   183: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   186: istore 18
    //   188: aload 29
    //   190: ldc_w 313
    //   193: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   196: istore 6
    //   198: aload 29
    //   200: ldc_w 315
    //   203: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   206: istore 14
    //   208: aload 29
    //   210: ldc_w 317
    //   213: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   216: istore_2
    //   217: aload 29
    //   219: ldc_w 319
    //   222: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   225: istore 10
    //   227: aload 29
    //   229: ldc_w 321
    //   232: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   235: istore 11
    //   237: aload 29
    //   239: ldc_w 323
    //   242: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   245: istore_1
    //   246: aload 29
    //   248: ldc_w 325
    //   251: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   254: istore 5
    //   256: aload 29
    //   258: ldc_w 327
    //   261: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   264: istore 4
    //   266: aload 29
    //   268: ldc_w 329
    //   271: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   274: istore 8
    //   276: new 118	java/util/ArrayList
    //   279: astore 32
    //   281: aload 32
    //   283: aload 29
    //   285: invokeinterface 332 1 0
    //   290: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   293: aload 29
    //   295: invokeinterface 197 1 0
    //   300: ifeq +479 -> 779
    //   303: aload 29
    //   305: iload 16
    //   307: invokeinterface 205 2 0
    //   312: astore 31
    //   314: aload 29
    //   316: iload 23
    //   318: invokeinterface 205 2 0
    //   323: astore 27
    //   325: new 335	androidx/work/Constraints
    //   328: astore 33
    //   330: aload 33
    //   332: invokespecial 336	androidx/work/Constraints:<init>	()V
    //   335: aload 33
    //   337: aload 29
    //   339: iload 19
    //   341: invokeinterface 340 2 0
    //   346: invokestatic 346	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   349: invokevirtual 350	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   352: aload 29
    //   354: iload 15
    //   356: invokeinterface 340 2 0
    //   361: ifeq +9 -> 370
    //   364: iconst_1
    //   365: istore 26
    //   367: goto +6 -> 373
    //   370: iconst_0
    //   371: istore 26
    //   373: aload 33
    //   375: iload 26
    //   377: invokevirtual 354	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   380: aload 29
    //   382: iload 12
    //   384: invokeinterface 340 2 0
    //   389: ifeq +9 -> 398
    //   392: iconst_1
    //   393: istore 26
    //   395: goto +6 -> 401
    //   398: iconst_0
    //   399: istore 26
    //   401: aload 33
    //   403: iload 26
    //   405: invokevirtual 357	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   408: aload 29
    //   410: iload 9
    //   412: invokeinterface 340 2 0
    //   417: ifeq +9 -> 426
    //   420: iconst_1
    //   421: istore 26
    //   423: goto +6 -> 429
    //   426: iconst_0
    //   427: istore 26
    //   429: aload 33
    //   431: iload 26
    //   433: invokevirtual 360	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   436: aload 29
    //   438: iload 20
    //   440: invokeinterface 340 2 0
    //   445: ifeq +9 -> 454
    //   448: iconst_1
    //   449: istore 26
    //   451: goto +6 -> 457
    //   454: iconst_0
    //   455: istore 26
    //   457: aload 33
    //   459: iload 26
    //   461: invokevirtual 363	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   464: aload 33
    //   466: aload 29
    //   468: iload 21
    //   470: invokeinterface 367 2 0
    //   475: invokevirtual 371	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   478: aload 33
    //   480: aload 29
    //   482: iload 25
    //   484: invokeinterface 367 2 0
    //   489: invokevirtual 374	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   492: aload 33
    //   494: aload 29
    //   496: iload 24
    //   498: invokeinterface 213 2 0
    //   503: invokestatic 378	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   506: invokevirtual 382	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   509: new 384	androidx/work/impl/model/WorkSpec
    //   512: astore 30
    //   514: aload 30
    //   516: aload 31
    //   518: aload 27
    //   520: invokespecial 387	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   523: aload 30
    //   525: aload 29
    //   527: iload 22
    //   529: invokeinterface 340 2 0
    //   534: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   537: putfield 394	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   540: aload 30
    //   542: aload 29
    //   544: iload 13
    //   546: invokeinterface 205 2 0
    //   551: putfield 398	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   554: aload 30
    //   556: aload 29
    //   558: iload 17
    //   560: invokeinterface 213 2 0
    //   565: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   568: putfield 401	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   571: aload 30
    //   573: aload 29
    //   575: iload_3
    //   576: invokeinterface 213 2 0
    //   581: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   584: putfield 403	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   587: aload 30
    //   589: aload 29
    //   591: iload 7
    //   593: invokeinterface 367 2 0
    //   598: putfield 407	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   601: aload 30
    //   603: aload 29
    //   605: iload 18
    //   607: invokeinterface 367 2 0
    //   612: putfield 410	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   615: aload 30
    //   617: aload 29
    //   619: iload 6
    //   621: invokeinterface 367 2 0
    //   626: putfield 413	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   629: aload 30
    //   631: aload 29
    //   633: iload 14
    //   635: invokeinterface 340 2 0
    //   640: putfield 417	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   643: aload 30
    //   645: aload 29
    //   647: iload_2
    //   648: invokeinterface 340 2 0
    //   653: invokestatic 421	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   656: putfield 425	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   659: aload 30
    //   661: aload 29
    //   663: iload 10
    //   665: invokeinterface 367 2 0
    //   670: putfield 428	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   673: aload 30
    //   675: aload 29
    //   677: iload 11
    //   679: invokeinterface 367 2 0
    //   684: putfield 431	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   687: aload 30
    //   689: aload 29
    //   691: iload_1
    //   692: invokeinterface 367 2 0
    //   697: putfield 434	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   700: aload 30
    //   702: aload 29
    //   704: iload 5
    //   706: invokeinterface 367 2 0
    //   711: putfield 437	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   714: aload 29
    //   716: iload 4
    //   718: invokeinterface 340 2 0
    //   723: ifeq +9 -> 732
    //   726: iconst_1
    //   727: istore 26
    //   729: goto +6 -> 735
    //   732: iconst_0
    //   733: istore 26
    //   735: aload 30
    //   737: iload 26
    //   739: putfield 441	androidx/work/impl/model/WorkSpec:expedited	Z
    //   742: aload 30
    //   744: aload 29
    //   746: iload 8
    //   748: invokeinterface 340 2 0
    //   753: invokestatic 445	androidx/work/impl/model/WorkTypeConverters:intToOutOfQuotaPolicy	(I)Landroidx/work/OutOfQuotaPolicy;
    //   756: putfield 449	androidx/work/impl/model/WorkSpec:outOfQuotaPolicy	Landroidx/work/OutOfQuotaPolicy;
    //   759: aload 30
    //   761: aload 33
    //   763: putfield 453	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   766: aload 32
    //   768: aload 30
    //   770: invokeinterface 456 2 0
    //   775: pop
    //   776: goto -483 -> 293
    //   779: aload 29
    //   781: invokeinterface 194 1 0
    //   786: aload 28
    //   788: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   791: aload 32
    //   793: areturn
    //   794: astore 27
    //   796: goto +10 -> 806
    //   799: astore 27
    //   801: goto +5 -> 806
    //   804: astore 27
    //   806: aload 29
    //   808: invokeinterface 194 1 0
    //   813: aload 28
    //   815: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   818: aload 27
    //   820: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	821	0	this	WorkSpecDao_Impl
    //   245	447	1	i	int
    //   216	432	2	j	int
    //   167	409	3	k	int
    //   264	453	4	m	int
    //   254	451	5	n	int
    //   196	424	6	i1	int
    //   176	416	7	i2	int
    //   274	473	8	i3	int
    //   67	344	9	i4	int
    //   225	439	10	i5	int
    //   235	443	11	i6	int
    //   57	326	12	i7	int
    //   147	398	13	i8	int
    //   206	428	14	i9	int
    //   47	308	15	i10	int
    //   117	189	16	i11	int
    //   157	402	17	i12	int
    //   186	420	18	i13	int
    //   37	303	19	i14	int
    //   77	362	20	i15	int
    //   87	382	21	i16	int
    //   127	401	22	i17	int
    //   137	180	23	i18	int
    //   107	390	24	i19	int
    //   97	386	25	i20	int
    //   365	373	26	bool	boolean
    //   323	196	27	str1	String
    //   794	1	27	localObject1	Object
    //   799	1	27	localObject2	Object
    //   804	15	27	localObject3	Object
    //   7	807	28	localRoomSQLiteQuery	RoomSQLiteQuery
    //   27	780	29	localCursor	Cursor
    //   512	257	30	localWorkSpec	WorkSpec
    //   312	205	31	str2	String
    //   279	513	32	localArrayList	ArrayList
    //   328	434	33	localConstraints	Constraints
    // Exception table:
    //   from	to	target	type
    //   168	281	794	finally
    //   281	293	794	finally
    //   293	364	794	finally
    //   373	392	794	finally
    //   401	420	794	finally
    //   429	448	794	finally
    //   457	726	794	finally
    //   735	776	794	finally
    //   159	168	799	finally
    //   29	159	804	finally
  }
  
  public WorkInfo.State getState(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT state FROM workspec WHERE id=?", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    Cursor localCursor = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      if (localCursor.moveToFirst()) {
        paramString = WorkTypeConverters.intToState(localCursor.getInt(0));
      } else {
        paramString = null;
      }
      return paramString;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public List<String> getUnfinishedWorkWithName(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    paramString = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramString.getCount());
      while (paramString.moveToNext()) {
        localArrayList.add(paramString.getString(0));
      }
      return localArrayList;
    }
    finally
    {
      paramString.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public List<String> getUnfinishedWorkWithTag(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    paramString = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramString.getCount());
      while (paramString.moveToNext()) {
        localArrayList.add(paramString.getString(0));
      }
      return localArrayList;
    }
    finally
    {
      paramString.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  /* Error */
  public WorkSpec getWorkSpec(String paramString)
  {
    // Byte code:
    //   0: ldc_w 528
    //   3: iconst_1
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 28
    //   9: aload_1
    //   10: ifnonnull +12 -> 22
    //   13: aload 28
    //   15: iconst_1
    //   16: invokevirtual 171	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   19: goto +10 -> 29
    //   22: aload 28
    //   24: iconst_1
    //   25: aload_1
    //   26: invokevirtual 175	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   29: aload_0
    //   30: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   33: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   36: aload_0
    //   37: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   40: aload 28
    //   42: iconst_0
    //   43: aconst_null
    //   44: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   47: astore 29
    //   49: aload 29
    //   51: ldc_w 278
    //   54: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   57: istore 13
    //   59: aload 29
    //   61: ldc_w 283
    //   64: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   67: istore_2
    //   68: aload 29
    //   70: ldc_w 285
    //   73: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   76: istore 12
    //   78: aload 29
    //   80: ldc_w 287
    //   83: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   86: istore_3
    //   87: aload 29
    //   89: ldc_w 289
    //   92: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   95: istore 10
    //   97: aload 29
    //   99: ldc_w 291
    //   102: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   105: istore 14
    //   107: aload 29
    //   109: ldc_w 293
    //   112: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   115: istore 11
    //   117: aload 29
    //   119: ldc_w 295
    //   122: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   125: istore 7
    //   127: aload 29
    //   129: ldc_w 297
    //   132: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   135: istore 9
    //   137: aload 29
    //   139: ldc_w 299
    //   142: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   145: istore 5
    //   147: aload 29
    //   149: ldc_w 301
    //   152: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   155: istore 24
    //   157: aload 29
    //   159: ldc_w 303
    //   162: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   165: istore 22
    //   167: aload 29
    //   169: ldc_w 305
    //   172: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   175: istore 15
    //   177: aload 29
    //   179: ldc_w 307
    //   182: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   185: istore 16
    //   187: aload 29
    //   189: ldc_w 309
    //   192: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   195: istore 26
    //   197: aload 29
    //   199: ldc_w 311
    //   202: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   205: istore 8
    //   207: aload 29
    //   209: ldc_w 313
    //   212: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   215: istore 19
    //   217: aload 29
    //   219: ldc_w 315
    //   222: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   225: istore 17
    //   227: aload 29
    //   229: ldc_w 317
    //   232: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   235: istore 6
    //   237: aload 29
    //   239: ldc_w 319
    //   242: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   245: istore 23
    //   247: aload 29
    //   249: ldc_w 321
    //   252: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   255: istore 25
    //   257: aload 29
    //   259: ldc_w 323
    //   262: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   265: istore 18
    //   267: aload 29
    //   269: ldc_w 325
    //   272: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   275: istore 4
    //   277: aload 29
    //   279: ldc_w 327
    //   282: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   285: istore 21
    //   287: aload 29
    //   289: ldc_w 329
    //   292: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   295: istore 20
    //   297: aload 29
    //   299: invokeinterface 517 1 0
    //   304: ifeq +452 -> 756
    //   307: aload 29
    //   309: iload 9
    //   311: invokeinterface 205 2 0
    //   316: astore 31
    //   318: aload 29
    //   320: iload 24
    //   322: invokeinterface 205 2 0
    //   327: astore 30
    //   329: new 335	androidx/work/Constraints
    //   332: astore 32
    //   334: aload 32
    //   336: invokespecial 336	androidx/work/Constraints:<init>	()V
    //   339: aload 32
    //   341: aload 29
    //   343: iload 13
    //   345: invokeinterface 340 2 0
    //   350: invokestatic 346	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   353: invokevirtual 350	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   356: aload 29
    //   358: iload_2
    //   359: invokeinterface 340 2 0
    //   364: ifeq +9 -> 373
    //   367: iconst_1
    //   368: istore 27
    //   370: goto +6 -> 376
    //   373: iconst_0
    //   374: istore 27
    //   376: aload 32
    //   378: iload 27
    //   380: invokevirtual 354	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   383: aload 29
    //   385: iload 12
    //   387: invokeinterface 340 2 0
    //   392: ifeq +9 -> 401
    //   395: iconst_1
    //   396: istore 27
    //   398: goto +6 -> 404
    //   401: iconst_0
    //   402: istore 27
    //   404: aload 32
    //   406: iload 27
    //   408: invokevirtual 357	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   411: aload 29
    //   413: iload_3
    //   414: invokeinterface 340 2 0
    //   419: ifeq +9 -> 428
    //   422: iconst_1
    //   423: istore 27
    //   425: goto +6 -> 431
    //   428: iconst_0
    //   429: istore 27
    //   431: aload 32
    //   433: iload 27
    //   435: invokevirtual 360	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   438: aload 29
    //   440: iload 10
    //   442: invokeinterface 340 2 0
    //   447: ifeq +9 -> 456
    //   450: iconst_1
    //   451: istore 27
    //   453: goto +6 -> 459
    //   456: iconst_0
    //   457: istore 27
    //   459: aload 32
    //   461: iload 27
    //   463: invokevirtual 363	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   466: aload 32
    //   468: aload 29
    //   470: iload 14
    //   472: invokeinterface 367 2 0
    //   477: invokevirtual 371	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   480: aload 32
    //   482: aload 29
    //   484: iload 11
    //   486: invokeinterface 367 2 0
    //   491: invokevirtual 374	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   494: aload 32
    //   496: aload 29
    //   498: iload 7
    //   500: invokeinterface 213 2 0
    //   505: invokestatic 378	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   508: invokevirtual 382	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   511: new 384	androidx/work/impl/model/WorkSpec
    //   514: astore_1
    //   515: aload_1
    //   516: aload 31
    //   518: aload 30
    //   520: invokespecial 387	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   523: aload_1
    //   524: aload 29
    //   526: iload 5
    //   528: invokeinterface 340 2 0
    //   533: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   536: putfield 394	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   539: aload_1
    //   540: aload 29
    //   542: iload 22
    //   544: invokeinterface 205 2 0
    //   549: putfield 398	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   552: aload_1
    //   553: aload 29
    //   555: iload 15
    //   557: invokeinterface 213 2 0
    //   562: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   565: putfield 401	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   568: aload_1
    //   569: aload 29
    //   571: iload 16
    //   573: invokeinterface 213 2 0
    //   578: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   581: putfield 403	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   584: aload_1
    //   585: aload 29
    //   587: iload 26
    //   589: invokeinterface 367 2 0
    //   594: putfield 407	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   597: aload_1
    //   598: aload 29
    //   600: iload 8
    //   602: invokeinterface 367 2 0
    //   607: putfield 410	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   610: aload_1
    //   611: aload 29
    //   613: iload 19
    //   615: invokeinterface 367 2 0
    //   620: putfield 413	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   623: aload_1
    //   624: aload 29
    //   626: iload 17
    //   628: invokeinterface 340 2 0
    //   633: putfield 417	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   636: aload_1
    //   637: aload 29
    //   639: iload 6
    //   641: invokeinterface 340 2 0
    //   646: invokestatic 421	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   649: putfield 425	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   652: aload_1
    //   653: aload 29
    //   655: iload 23
    //   657: invokeinterface 367 2 0
    //   662: putfield 428	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   665: aload_1
    //   666: aload 29
    //   668: iload 25
    //   670: invokeinterface 367 2 0
    //   675: putfield 431	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   678: aload_1
    //   679: aload 29
    //   681: iload 18
    //   683: invokeinterface 367 2 0
    //   688: putfield 434	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   691: aload_1
    //   692: aload 29
    //   694: iload 4
    //   696: invokeinterface 367 2 0
    //   701: putfield 437	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   704: aload 29
    //   706: iload 21
    //   708: invokeinterface 340 2 0
    //   713: ifeq +9 -> 722
    //   716: iconst_1
    //   717: istore 27
    //   719: goto +6 -> 725
    //   722: iconst_0
    //   723: istore 27
    //   725: aload_1
    //   726: iload 27
    //   728: putfield 441	androidx/work/impl/model/WorkSpec:expedited	Z
    //   731: aload_1
    //   732: aload 29
    //   734: iload 20
    //   736: invokeinterface 340 2 0
    //   741: invokestatic 445	androidx/work/impl/model/WorkTypeConverters:intToOutOfQuotaPolicy	(I)Landroidx/work/OutOfQuotaPolicy;
    //   744: putfield 449	androidx/work/impl/model/WorkSpec:outOfQuotaPolicy	Landroidx/work/OutOfQuotaPolicy;
    //   747: aload_1
    //   748: aload 32
    //   750: putfield 453	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   753: goto +5 -> 758
    //   756: aconst_null
    //   757: astore_1
    //   758: aload 29
    //   760: invokeinterface 194 1 0
    //   765: aload 28
    //   767: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   770: aload_1
    //   771: areturn
    //   772: astore_1
    //   773: goto +12 -> 785
    //   776: astore_1
    //   777: goto +8 -> 785
    //   780: astore_1
    //   781: goto +4 -> 785
    //   784: astore_1
    //   785: aload 29
    //   787: invokeinterface 194 1 0
    //   792: aload 28
    //   794: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   797: aload_1
    //   798: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	799	0	this	WorkSpecDao_Impl
    //   0	799	1	paramString	String
    //   67	292	2	i	int
    //   86	328	3	j	int
    //   275	420	4	k	int
    //   145	382	5	m	int
    //   235	405	6	n	int
    //   125	374	7	i1	int
    //   205	396	8	i2	int
    //   135	175	9	i3	int
    //   95	346	10	i4	int
    //   115	370	11	i5	int
    //   76	310	12	i6	int
    //   57	287	13	i7	int
    //   105	366	14	i8	int
    //   175	381	15	i9	int
    //   185	387	16	i10	int
    //   225	402	17	i11	int
    //   265	417	18	i12	int
    //   215	399	19	i13	int
    //   295	440	20	i14	int
    //   285	422	21	i15	int
    //   165	378	22	i16	int
    //   245	411	23	i17	int
    //   155	166	24	i18	int
    //   255	414	25	i19	int
    //   195	393	26	i20	int
    //   368	359	27	bool	boolean
    //   7	786	28	localRoomSQLiteQuery	RoomSQLiteQuery
    //   47	739	29	localCursor	Cursor
    //   327	192	30	str1	String
    //   316	201	31	str2	String
    //   332	417	32	localConstraints	Constraints
    // Exception table:
    //   from	to	target	type
    //   187	367	772	finally
    //   376	395	772	finally
    //   404	422	772	finally
    //   431	450	772	finally
    //   459	716	772	finally
    //   725	753	772	finally
    //   177	187	776	finally
    //   167	177	780	finally
    //   49	167	784	finally
  }
  
  public List<WorkSpec.IdAndState> getWorkSpecIdAndStatesForName(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id, state FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    paramString = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      int i = CursorUtil.getColumnIndexOrThrow(paramString, "id");
      int j = CursorUtil.getColumnIndexOrThrow(paramString, "state");
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramString.getCount());
      while (paramString.moveToNext())
      {
        WorkSpec.IdAndState localIdAndState = new androidx/work/impl/model/WorkSpec$IdAndState;
        localIdAndState.<init>();
        localIdAndState.id = paramString.getString(i);
        localIdAndState.state = WorkTypeConverters.intToState(paramString.getInt(j));
        localArrayList.add(localIdAndState);
      }
      return localArrayList;
    }
    finally
    {
      paramString.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  /* Error */
  public WorkSpec[] getWorkSpecs(List<String> paramList)
  {
    // Byte code:
    //   0: invokestatic 130	androidx/room/util/StringUtil:newStringBuilder	()Ljava/lang/StringBuilder;
    //   3: astore 29
    //   5: aload 29
    //   7: ldc_w 543
    //   10: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   13: pop
    //   14: aload 29
    //   16: ldc_w 545
    //   19: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: pop
    //   23: aload 29
    //   25: ldc_w 547
    //   28: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: pop
    //   32: aload_1
    //   33: invokeinterface 548 1 0
    //   38: istore_2
    //   39: aload 29
    //   41: iload_2
    //   42: invokestatic 143	androidx/room/util/StringUtil:appendPlaceholders	(Ljava/lang/StringBuilder;I)V
    //   45: aload 29
    //   47: ldc -111
    //   49: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload 29
    //   55: invokevirtual 149	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: iload_2
    //   59: iconst_0
    //   60: iadd
    //   61: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   64: astore 29
    //   66: aload_1
    //   67: invokeinterface 549 1 0
    //   72: astore 30
    //   74: iconst_1
    //   75: istore_2
    //   76: aload 30
    //   78: invokeinterface 164 1 0
    //   83: ifeq +40 -> 123
    //   86: aload 30
    //   88: invokeinterface 168 1 0
    //   93: checkcast 113	java/lang/String
    //   96: astore_1
    //   97: aload_1
    //   98: ifnonnull +12 -> 110
    //   101: aload 29
    //   103: iload_2
    //   104: invokevirtual 171	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   107: goto +10 -> 117
    //   110: aload 29
    //   112: iload_2
    //   113: aload_1
    //   114: invokevirtual 175	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   117: iinc 2 1
    //   120: goto -44 -> 76
    //   123: aload_0
    //   124: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   127: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   130: aload_0
    //   131: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   134: aload 29
    //   136: iconst_0
    //   137: aconst_null
    //   138: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   141: astore 30
    //   143: aload 30
    //   145: ldc_w 278
    //   148: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   151: istore 20
    //   153: aload 30
    //   155: ldc_w 283
    //   158: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   161: istore 11
    //   163: aload 30
    //   165: ldc_w 285
    //   168: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   171: istore 8
    //   173: aload 30
    //   175: ldc_w 287
    //   178: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   181: istore 26
    //   183: aload 30
    //   185: ldc_w 289
    //   188: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   191: istore 27
    //   193: aload 30
    //   195: ldc_w 291
    //   198: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   201: istore 24
    //   203: aload 30
    //   205: ldc_w 293
    //   208: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   211: istore 25
    //   213: aload 30
    //   215: ldc_w 295
    //   218: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   221: istore 18
    //   223: aload 30
    //   225: ldc_w 297
    //   228: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   231: istore 22
    //   233: aload 30
    //   235: ldc_w 299
    //   238: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   241: istore 15
    //   243: aload 30
    //   245: ldc_w 301
    //   248: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   251: istore 14
    //   253: aload 30
    //   255: ldc_w 303
    //   258: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   261: istore 21
    //   263: aload 30
    //   265: ldc_w 305
    //   268: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   271: istore 10
    //   273: aload 30
    //   275: ldc_w 307
    //   278: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   281: istore 23
    //   283: aload 30
    //   285: ldc_w 309
    //   288: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   291: istore 16
    //   293: aload 30
    //   295: ldc_w 311
    //   298: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   301: istore 5
    //   303: aload 30
    //   305: ldc_w 313
    //   308: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   311: istore_3
    //   312: aload 30
    //   314: ldc_w 315
    //   317: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   320: istore 17
    //   322: aload 30
    //   324: ldc_w 317
    //   327: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   330: istore 6
    //   332: aload 30
    //   334: ldc_w 319
    //   337: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   340: istore 4
    //   342: aload 30
    //   344: ldc_w 321
    //   347: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   350: istore 13
    //   352: aload 30
    //   354: ldc_w 323
    //   357: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   360: istore 12
    //   362: aload 30
    //   364: ldc_w 325
    //   367: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   370: istore 9
    //   372: aload 30
    //   374: ldc_w 327
    //   377: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   380: istore_2
    //   381: aload 30
    //   383: ldc_w 329
    //   386: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   389: istore 7
    //   391: aload 30
    //   393: invokeinterface 332 1 0
    //   398: anewarray 384	androidx/work/impl/model/WorkSpec
    //   401: astore 34
    //   403: iconst_0
    //   404: istore 19
    //   406: aload 30
    //   408: invokeinterface 197 1 0
    //   413: ifeq +478 -> 891
    //   416: aload 30
    //   418: iload 22
    //   420: invokeinterface 205 2 0
    //   425: astore 33
    //   427: aload 30
    //   429: iload 14
    //   431: invokeinterface 205 2 0
    //   436: astore_1
    //   437: new 335	androidx/work/Constraints
    //   440: astore 32
    //   442: aload 32
    //   444: invokespecial 336	androidx/work/Constraints:<init>	()V
    //   447: aload 32
    //   449: aload 30
    //   451: iload 20
    //   453: invokeinterface 340 2 0
    //   458: invokestatic 346	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   461: invokevirtual 350	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   464: aload 30
    //   466: iload 11
    //   468: invokeinterface 340 2 0
    //   473: ifeq +9 -> 482
    //   476: iconst_1
    //   477: istore 28
    //   479: goto +6 -> 485
    //   482: iconst_0
    //   483: istore 28
    //   485: aload 32
    //   487: iload 28
    //   489: invokevirtual 354	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   492: aload 30
    //   494: iload 8
    //   496: invokeinterface 340 2 0
    //   501: ifeq +9 -> 510
    //   504: iconst_1
    //   505: istore 28
    //   507: goto +6 -> 513
    //   510: iconst_0
    //   511: istore 28
    //   513: aload 32
    //   515: iload 28
    //   517: invokevirtual 357	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   520: aload 30
    //   522: iload 26
    //   524: invokeinterface 340 2 0
    //   529: ifeq +9 -> 538
    //   532: iconst_1
    //   533: istore 28
    //   535: goto +6 -> 541
    //   538: iconst_0
    //   539: istore 28
    //   541: aload 32
    //   543: iload 28
    //   545: invokevirtual 360	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   548: aload 30
    //   550: iload 27
    //   552: invokeinterface 340 2 0
    //   557: ifeq +9 -> 566
    //   560: iconst_1
    //   561: istore 28
    //   563: goto +6 -> 569
    //   566: iconst_0
    //   567: istore 28
    //   569: aload 32
    //   571: iload 28
    //   573: invokevirtual 363	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   576: aload 32
    //   578: aload 30
    //   580: iload 24
    //   582: invokeinterface 367 2 0
    //   587: invokevirtual 371	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   590: aload 32
    //   592: aload 30
    //   594: iload 25
    //   596: invokeinterface 367 2 0
    //   601: invokevirtual 374	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   604: aload 32
    //   606: aload 30
    //   608: iload 18
    //   610: invokeinterface 213 2 0
    //   615: invokestatic 378	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   618: invokevirtual 382	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   621: new 384	androidx/work/impl/model/WorkSpec
    //   624: astore 31
    //   626: aload 31
    //   628: aload 33
    //   630: aload_1
    //   631: invokespecial 387	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   634: aload 31
    //   636: aload 30
    //   638: iload 15
    //   640: invokeinterface 340 2 0
    //   645: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   648: putfield 394	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   651: aload 31
    //   653: aload 30
    //   655: iload 21
    //   657: invokeinterface 205 2 0
    //   662: putfield 398	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   665: aload 31
    //   667: aload 30
    //   669: iload 10
    //   671: invokeinterface 213 2 0
    //   676: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   679: putfield 401	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   682: aload 31
    //   684: aload 30
    //   686: iload 23
    //   688: invokeinterface 213 2 0
    //   693: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   696: putfield 403	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   699: aload 31
    //   701: aload 30
    //   703: iload 16
    //   705: invokeinterface 367 2 0
    //   710: putfield 407	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   713: aload 31
    //   715: aload 30
    //   717: iload 5
    //   719: invokeinterface 367 2 0
    //   724: putfield 410	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   727: aload 31
    //   729: aload 30
    //   731: iload_3
    //   732: invokeinterface 367 2 0
    //   737: putfield 413	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   740: aload 31
    //   742: aload 30
    //   744: iload 17
    //   746: invokeinterface 340 2 0
    //   751: putfield 417	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   754: aload 31
    //   756: aload 30
    //   758: iload 6
    //   760: invokeinterface 340 2 0
    //   765: invokestatic 421	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   768: putfield 425	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   771: aload 31
    //   773: aload 30
    //   775: iload 4
    //   777: invokeinterface 367 2 0
    //   782: putfield 428	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   785: aload 31
    //   787: aload 30
    //   789: iload 13
    //   791: invokeinterface 367 2 0
    //   796: putfield 431	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   799: aload 31
    //   801: aload 30
    //   803: iload 12
    //   805: invokeinterface 367 2 0
    //   810: putfield 434	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   813: aload 31
    //   815: aload 30
    //   817: iload 9
    //   819: invokeinterface 367 2 0
    //   824: putfield 437	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   827: aload 30
    //   829: iload_2
    //   830: invokeinterface 340 2 0
    //   835: ifeq +9 -> 844
    //   838: iconst_1
    //   839: istore 28
    //   841: goto +6 -> 847
    //   844: iconst_0
    //   845: istore 28
    //   847: aload 31
    //   849: iload 28
    //   851: putfield 441	androidx/work/impl/model/WorkSpec:expedited	Z
    //   854: aload 31
    //   856: aload 30
    //   858: iload 7
    //   860: invokeinterface 340 2 0
    //   865: invokestatic 445	androidx/work/impl/model/WorkTypeConverters:intToOutOfQuotaPolicy	(I)Landroidx/work/OutOfQuotaPolicy;
    //   868: putfield 449	androidx/work/impl/model/WorkSpec:outOfQuotaPolicy	Landroidx/work/OutOfQuotaPolicy;
    //   871: aload 31
    //   873: aload 32
    //   875: putfield 453	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   878: aload 34
    //   880: iload 19
    //   882: aload 31
    //   884: aastore
    //   885: iinc 19 1
    //   888: goto -482 -> 406
    //   891: aload 30
    //   893: invokeinterface 194 1 0
    //   898: aload 29
    //   900: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   903: aload 34
    //   905: areturn
    //   906: astore_1
    //   907: goto +24 -> 931
    //   910: astore_1
    //   911: goto +20 -> 931
    //   914: astore_1
    //   915: goto +16 -> 931
    //   918: astore_1
    //   919: goto +12 -> 931
    //   922: astore_1
    //   923: goto +8 -> 931
    //   926: astore_1
    //   927: goto +4 -> 931
    //   930: astore_1
    //   931: aload 30
    //   933: invokeinterface 194 1 0
    //   938: aload 29
    //   940: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   943: aload_1
    //   944: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	945	0	this	WorkSpecDao_Impl
    //   0	945	1	paramList	List<String>
    //   38	792	2	i	int
    //   311	421	3	j	int
    //   340	436	4	k	int
    //   301	417	5	m	int
    //   330	429	6	n	int
    //   389	470	7	i1	int
    //   171	324	8	i2	int
    //   370	448	9	i3	int
    //   271	399	10	i4	int
    //   161	306	11	i5	int
    //   360	444	12	i6	int
    //   350	440	13	i7	int
    //   251	179	14	i8	int
    //   241	398	15	i9	int
    //   291	413	16	i10	int
    //   320	425	17	i11	int
    //   221	388	18	i12	int
    //   404	482	19	i13	int
    //   151	301	20	i14	int
    //   261	395	21	i15	int
    //   231	188	22	i16	int
    //   281	406	23	i17	int
    //   201	380	24	i18	int
    //   211	384	25	i19	int
    //   181	342	26	i20	int
    //   191	360	27	i21	int
    //   477	373	28	bool	boolean
    //   3	936	29	localObject1	Object
    //   72	860	30	localObject2	Object
    //   624	259	31	localWorkSpec	WorkSpec
    //   440	434	32	localConstraints	Constraints
    //   425	204	33	str	String
    //   401	503	34	arrayOfWorkSpec	WorkSpec[]
    // Exception table:
    //   from	to	target	type
    //   283	403	906	finally
    //   406	476	906	finally
    //   485	504	906	finally
    //   513	532	906	finally
    //   541	560	906	finally
    //   569	838	906	finally
    //   847	878	906	finally
    //   273	283	910	finally
    //   263	273	914	finally
    //   253	263	918	finally
    //   243	253	922	finally
    //   233	243	926	finally
    //   143	233	930	finally
  }
  
  /* Error */
  public WorkSpec.WorkInfoPojo getWorkStatusPojoForId(String paramString)
  {
    // Byte code:
    //   0: ldc_w 553
    //   3: iconst_1
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 8
    //   9: aload_1
    //   10: ifnonnull +12 -> 22
    //   13: aload 8
    //   15: iconst_1
    //   16: invokevirtual 171	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   19: goto +10 -> 29
    //   22: aload 8
    //   24: iconst_1
    //   25: aload_1
    //   26: invokevirtual 175	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   29: aload_0
    //   30: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   33: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   36: aload_0
    //   37: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   40: invokevirtual 254	androidx/room/RoomDatabase:beginTransaction	()V
    //   43: aload_0
    //   44: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   47: aload 8
    //   49: iconst_1
    //   50: aconst_null
    //   51: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   54: astore 9
    //   56: aload 9
    //   58: ldc_w 297
    //   61: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   64: istore 5
    //   66: aload 9
    //   68: ldc_w 299
    //   71: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   74: istore_3
    //   75: aload 9
    //   77: ldc_w 307
    //   80: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   83: istore_2
    //   84: aload 9
    //   86: ldc_w 315
    //   89: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   92: istore 4
    //   94: new 90	androidx/collection/ArrayMap
    //   97: astore 6
    //   99: aload 6
    //   101: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   104: new 90	androidx/collection/ArrayMap
    //   107: astore 7
    //   109: aload 7
    //   111: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   114: aload 9
    //   116: invokeinterface 197 1 0
    //   121: ifeq +112 -> 233
    //   124: aload 9
    //   126: iload 5
    //   128: invokeinterface 201 2 0
    //   133: ifne +44 -> 177
    //   136: aload 9
    //   138: iload 5
    //   140: invokeinterface 205 2 0
    //   145: astore 10
    //   147: aload 6
    //   149: aload 10
    //   151: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   154: checkcast 118	java/util/ArrayList
    //   157: ifnonnull +20 -> 177
    //   160: new 118	java/util/ArrayList
    //   163: astore_1
    //   164: aload_1
    //   165: invokespecial 555	java/util/ArrayList:<init>	()V
    //   168: aload 6
    //   170: aload 10
    //   172: aload_1
    //   173: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   176: pop
    //   177: aload 9
    //   179: iload 5
    //   181: invokeinterface 201 2 0
    //   186: ifne -72 -> 114
    //   189: aload 9
    //   191: iload 5
    //   193: invokeinterface 205 2 0
    //   198: astore_1
    //   199: aload 7
    //   201: aload_1
    //   202: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   205: checkcast 118	java/util/ArrayList
    //   208: ifnonnull +22 -> 230
    //   211: new 118	java/util/ArrayList
    //   214: astore 10
    //   216: aload 10
    //   218: invokespecial 555	java/util/ArrayList:<init>	()V
    //   221: aload 7
    //   223: aload_1
    //   224: aload 10
    //   226: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   229: pop
    //   230: goto -116 -> 114
    //   233: aload 9
    //   235: iconst_m1
    //   236: invokeinterface 558 2 0
    //   241: pop
    //   242: aload_0
    //   243: aload 6
    //   245: invokespecial 228	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   248: aload_0
    //   249: aload 7
    //   251: invokespecial 124	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   254: aload 9
    //   256: invokeinterface 517 1 0
    //   261: ifeq +180 -> 441
    //   264: aconst_null
    //   265: astore_1
    //   266: aload 9
    //   268: iload 5
    //   270: invokeinterface 201 2 0
    //   275: ifne +21 -> 296
    //   278: aload 6
    //   280: aload 9
    //   282: iload 5
    //   284: invokeinterface 205 2 0
    //   289: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   292: checkcast 118	java/util/ArrayList
    //   295: astore_1
    //   296: aload_1
    //   297: astore 6
    //   299: aload_1
    //   300: ifnonnull +13 -> 313
    //   303: new 118	java/util/ArrayList
    //   306: astore 6
    //   308: aload 6
    //   310: invokespecial 555	java/util/ArrayList:<init>	()V
    //   313: aconst_null
    //   314: astore_1
    //   315: aload 9
    //   317: iload 5
    //   319: invokeinterface 201 2 0
    //   324: ifne +21 -> 345
    //   327: aload 7
    //   329: aload 9
    //   331: iload 5
    //   333: invokeinterface 205 2 0
    //   338: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   341: checkcast 118	java/util/ArrayList
    //   344: astore_1
    //   345: aload_1
    //   346: astore 7
    //   348: aload_1
    //   349: ifnonnull +13 -> 362
    //   352: new 118	java/util/ArrayList
    //   355: astore 7
    //   357: aload 7
    //   359: invokespecial 555	java/util/ArrayList:<init>	()V
    //   362: new 560	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   365: astore_1
    //   366: aload_1
    //   367: invokespecial 561	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   370: aload_1
    //   371: aload 9
    //   373: iload 5
    //   375: invokeinterface 205 2 0
    //   380: putfield 562	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   383: aload_1
    //   384: aload 9
    //   386: iload_3
    //   387: invokeinterface 340 2 0
    //   392: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   395: putfield 563	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   398: aload_1
    //   399: aload 9
    //   401: iload_2
    //   402: invokeinterface 213 2 0
    //   407: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   410: putfield 564	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   413: aload_1
    //   414: aload 9
    //   416: iload 4
    //   418: invokeinterface 340 2 0
    //   423: putfield 565	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   426: aload_1
    //   427: aload 6
    //   429: putfield 569	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   432: aload_1
    //   433: aload 7
    //   435: putfield 572	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   438: goto +5 -> 443
    //   441: aconst_null
    //   442: astore_1
    //   443: aload_0
    //   444: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   447: invokevirtual 260	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   450: aload 9
    //   452: invokeinterface 194 1 0
    //   457: aload 8
    //   459: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   462: aload_0
    //   463: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   466: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   469: aload_1
    //   470: areturn
    //   471: astore_1
    //   472: aload 9
    //   474: invokeinterface 194 1 0
    //   479: aload 8
    //   481: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   484: aload_1
    //   485: athrow
    //   486: astore_1
    //   487: aload_0
    //   488: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   491: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   494: aload_1
    //   495: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	496	0	this	WorkSpecDao_Impl
    //   0	496	1	paramString	String
    //   83	319	2	i	int
    //   74	313	3	j	int
    //   92	325	4	k	int
    //   64	310	5	m	int
    //   97	331	6	localObject1	Object
    //   107	327	7	localObject2	Object
    //   7	473	8	localRoomSQLiteQuery	RoomSQLiteQuery
    //   54	419	9	localCursor	Cursor
    //   145	80	10	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   56	114	471	finally
    //   114	177	471	finally
    //   177	230	471	finally
    //   233	264	471	finally
    //   266	296	471	finally
    //   303	313	471	finally
    //   315	345	471	finally
    //   352	362	471	finally
    //   362	438	471	finally
    //   443	450	471	finally
    //   43	56	486	finally
    //   450	462	486	finally
    //   472	484	486	finally
    //   484	486	486	finally
  }
  
  /* Error */
  public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForIds(List<String> paramList)
  {
    // Byte code:
    //   0: invokestatic 130	androidx/room/util/StringUtil:newStringBuilder	()Ljava/lang/StringBuilder;
    //   3: astore 9
    //   5: aload 9
    //   7: ldc_w 577
    //   10: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   13: pop
    //   14: aload_1
    //   15: invokeinterface 548 1 0
    //   20: istore_2
    //   21: aload 9
    //   23: iload_2
    //   24: invokestatic 143	androidx/room/util/StringUtil:appendPlaceholders	(Ljava/lang/StringBuilder;I)V
    //   27: aload 9
    //   29: ldc -111
    //   31: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: pop
    //   35: aload 9
    //   37: invokevirtual 149	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: astore 10
    //   42: aload 10
    //   44: iload_2
    //   45: iconst_0
    //   46: iadd
    //   47: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   50: astore 13
    //   52: aload_1
    //   53: invokeinterface 549 1 0
    //   58: astore 11
    //   60: iconst_1
    //   61: istore_3
    //   62: aload 11
    //   64: invokeinterface 164 1 0
    //   69: ifeq +40 -> 109
    //   72: aload 11
    //   74: invokeinterface 168 1 0
    //   79: checkcast 113	java/lang/String
    //   82: astore_1
    //   83: aload_1
    //   84: ifnonnull +12 -> 96
    //   87: aload 13
    //   89: iload_3
    //   90: invokevirtual 171	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   93: goto +10 -> 103
    //   96: aload 13
    //   98: iload_3
    //   99: aload_1
    //   100: invokevirtual 175	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   103: iinc 3 1
    //   106: goto -44 -> 62
    //   109: aload_0
    //   110: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   113: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   116: aload_0
    //   117: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   120: invokevirtual 254	androidx/room/RoomDatabase:beginTransaction	()V
    //   123: aload_0
    //   124: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   127: aload 13
    //   129: iconst_1
    //   130: aconst_null
    //   131: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   134: astore 14
    //   136: aload 9
    //   138: astore 11
    //   140: iload_2
    //   141: istore 4
    //   143: aload 10
    //   145: astore 11
    //   147: aload 14
    //   149: ldc_w 297
    //   152: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   155: istore_3
    //   156: aload 9
    //   158: astore 11
    //   160: iload_2
    //   161: istore 4
    //   163: aload 10
    //   165: astore 11
    //   167: aload 14
    //   169: ldc_w 299
    //   172: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   175: istore 6
    //   177: aload 9
    //   179: astore 11
    //   181: iload_2
    //   182: istore 4
    //   184: aload 10
    //   186: astore 11
    //   188: aload 14
    //   190: ldc_w 307
    //   193: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   196: istore 7
    //   198: aload 9
    //   200: astore 11
    //   202: iload_2
    //   203: istore 4
    //   205: aload 10
    //   207: astore 11
    //   209: aload 14
    //   211: ldc_w 315
    //   214: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   217: istore 5
    //   219: aload 9
    //   221: astore 11
    //   223: iload_2
    //   224: istore 4
    //   226: aload 10
    //   228: astore 11
    //   230: new 90	androidx/collection/ArrayMap
    //   233: astore 16
    //   235: aload 9
    //   237: astore 11
    //   239: iload_2
    //   240: istore 4
    //   242: aload 10
    //   244: astore 11
    //   246: aload 16
    //   248: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   251: aload 9
    //   253: astore 11
    //   255: iload_2
    //   256: istore 4
    //   258: aload 10
    //   260: astore 11
    //   262: new 90	androidx/collection/ArrayMap
    //   265: astore 15
    //   267: aload 9
    //   269: astore 11
    //   271: iload_2
    //   272: istore 4
    //   274: aload 10
    //   276: astore 11
    //   278: aload 15
    //   280: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   283: aload 9
    //   285: astore 11
    //   287: iload_2
    //   288: istore 4
    //   290: aload 10
    //   292: astore 11
    //   294: aload 14
    //   296: invokeinterface 197 1 0
    //   301: istore 8
    //   303: iload 8
    //   305: ifeq +112 -> 417
    //   308: aload 14
    //   310: iload_3
    //   311: invokeinterface 201 2 0
    //   316: ifne +43 -> 359
    //   319: aload 14
    //   321: iload_3
    //   322: invokeinterface 205 2 0
    //   327: astore 11
    //   329: aload 16
    //   331: aload 11
    //   333: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   336: checkcast 118	java/util/ArrayList
    //   339: ifnonnull +20 -> 359
    //   342: new 118	java/util/ArrayList
    //   345: astore_1
    //   346: aload_1
    //   347: invokespecial 555	java/util/ArrayList:<init>	()V
    //   350: aload 16
    //   352: aload 11
    //   354: aload_1
    //   355: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   358: pop
    //   359: aload 14
    //   361: iload_3
    //   362: invokeinterface 201 2 0
    //   367: ifne -84 -> 283
    //   370: aload 14
    //   372: iload_3
    //   373: invokeinterface 205 2 0
    //   378: astore_1
    //   379: aload 15
    //   381: aload_1
    //   382: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   385: checkcast 118	java/util/ArrayList
    //   388: ifnonnull +22 -> 410
    //   391: new 118	java/util/ArrayList
    //   394: astore 11
    //   396: aload 11
    //   398: invokespecial 555	java/util/ArrayList:<init>	()V
    //   401: aload 15
    //   403: aload_1
    //   404: aload 11
    //   406: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   409: pop
    //   410: goto -127 -> 283
    //   413: astore_1
    //   414: goto +404 -> 818
    //   417: aload 9
    //   419: astore 11
    //   421: iload_2
    //   422: istore 4
    //   424: aload 10
    //   426: astore 11
    //   428: aload 14
    //   430: iconst_m1
    //   431: invokeinterface 558 2 0
    //   436: pop
    //   437: aload 9
    //   439: astore 11
    //   441: iload_2
    //   442: istore 4
    //   444: aload 10
    //   446: astore 11
    //   448: aload_0
    //   449: aload 16
    //   451: invokespecial 228	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   454: aload 9
    //   456: astore 11
    //   458: iload_2
    //   459: istore 4
    //   461: aload 10
    //   463: astore 11
    //   465: aload_0
    //   466: aload 15
    //   468: invokespecial 124	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   471: aload 9
    //   473: astore 11
    //   475: iload_2
    //   476: istore 4
    //   478: aload 10
    //   480: astore 11
    //   482: new 118	java/util/ArrayList
    //   485: astore 17
    //   487: aload 9
    //   489: astore 11
    //   491: iload_2
    //   492: istore 4
    //   494: aload 10
    //   496: astore 11
    //   498: aload 17
    //   500: aload 14
    //   502: invokeinterface 332 1 0
    //   507: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   510: aload 10
    //   512: astore_1
    //   513: aload 9
    //   515: astore 11
    //   517: iload_2
    //   518: istore 4
    //   520: aload_1
    //   521: astore 11
    //   523: aload 14
    //   525: invokeinterface 197 1 0
    //   530: ifeq +254 -> 784
    //   533: aconst_null
    //   534: astore 10
    //   536: aload 9
    //   538: astore 11
    //   540: iload_2
    //   541: istore 4
    //   543: aload_1
    //   544: astore 11
    //   546: aload 14
    //   548: iload_3
    //   549: invokeinterface 201 2 0
    //   554: istore 8
    //   556: iload 8
    //   558: ifne +32 -> 590
    //   561: aload 14
    //   563: iload_3
    //   564: invokeinterface 205 2 0
    //   569: astore 10
    //   571: aload 16
    //   573: aload 10
    //   575: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   578: checkcast 118	java/util/ArrayList
    //   581: astore 10
    //   583: goto +7 -> 590
    //   586: astore_1
    //   587: goto +231 -> 818
    //   590: aload 10
    //   592: astore 11
    //   594: aload 10
    //   596: ifnonnull +20 -> 616
    //   599: new 118	java/util/ArrayList
    //   602: astore 11
    //   604: aload 11
    //   606: invokespecial 555	java/util/ArrayList:<init>	()V
    //   609: goto +7 -> 616
    //   612: astore_1
    //   613: goto +205 -> 818
    //   616: aconst_null
    //   617: astore 10
    //   619: aload 14
    //   621: iload_3
    //   622: invokeinterface 201 2 0
    //   627: istore 8
    //   629: iload 8
    //   631: ifne +24 -> 655
    //   634: aload 15
    //   636: aload 14
    //   638: iload_3
    //   639: invokeinterface 205 2 0
    //   644: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   647: checkcast 118	java/util/ArrayList
    //   650: astore 10
    //   652: goto +3 -> 655
    //   655: aload 10
    //   657: astore 12
    //   659: aload 10
    //   661: ifnonnull +13 -> 674
    //   664: new 118	java/util/ArrayList
    //   667: astore 12
    //   669: aload 12
    //   671: invokespecial 555	java/util/ArrayList:<init>	()V
    //   674: new 560	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   677: astore 10
    //   679: aload 10
    //   681: invokespecial 561	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   684: aload 10
    //   686: aload 14
    //   688: iload_3
    //   689: invokeinterface 205 2 0
    //   694: putfield 562	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   697: aload 14
    //   699: iload 6
    //   701: invokeinterface 340 2 0
    //   706: istore 4
    //   708: aload 10
    //   710: iload 4
    //   712: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   715: putfield 563	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   718: aload 10
    //   720: aload 14
    //   722: iload 7
    //   724: invokeinterface 213 2 0
    //   729: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   732: putfield 564	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   735: aload 10
    //   737: aload 14
    //   739: iload 5
    //   741: invokeinterface 340 2 0
    //   746: putfield 565	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   749: aload 10
    //   751: aload 11
    //   753: putfield 569	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   756: aload 10
    //   758: aload 12
    //   760: putfield 572	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   763: aload 17
    //   765: aload 10
    //   767: invokeinterface 456 2 0
    //   772: pop
    //   773: goto -260 -> 513
    //   776: astore_1
    //   777: goto +41 -> 818
    //   780: astore_1
    //   781: goto +37 -> 818
    //   784: aload_0
    //   785: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   788: invokevirtual 260	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   791: aload 14
    //   793: invokeinterface 194 1 0
    //   798: aload 13
    //   800: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   803: aload_0
    //   804: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   807: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   810: aload 17
    //   812: areturn
    //   813: astore_1
    //   814: goto +4 -> 818
    //   817: astore_1
    //   818: aload 14
    //   820: invokeinterface 194 1 0
    //   825: aload 13
    //   827: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   830: aload_1
    //   831: athrow
    //   832: astore_1
    //   833: goto +4 -> 837
    //   836: astore_1
    //   837: aload_0
    //   838: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   841: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   844: aload_1
    //   845: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	846	0	this	WorkSpecDao_Impl
    //   0	846	1	paramList	List<String>
    //   20	521	2	i	int
    //   61	628	3	j	int
    //   141	570	4	k	int
    //   217	523	5	m	int
    //   175	525	6	n	int
    //   196	527	7	i1	int
    //   301	329	8	bool	boolean
    //   3	534	9	localStringBuilder	StringBuilder
    //   40	726	10	localObject1	Object
    //   58	694	11	localObject2	Object
    //   657	102	12	localObject3	Object
    //   50	776	13	localRoomSQLiteQuery	RoomSQLiteQuery
    //   134	685	14	localCursor	Cursor
    //   265	370	15	localArrayMap1	ArrayMap
    //   233	339	16	localArrayMap2	ArrayMap
    //   485	326	17	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   308	359	413	finally
    //   359	410	413	finally
    //   561	571	586	finally
    //   571	583	612	finally
    //   599	609	612	finally
    //   634	652	612	finally
    //   664	674	612	finally
    //   684	708	776	finally
    //   619	629	780	finally
    //   674	684	780	finally
    //   708	773	813	finally
    //   784	791	813	finally
    //   147	156	817	finally
    //   167	177	817	finally
    //   188	198	817	finally
    //   209	219	817	finally
    //   230	235	817	finally
    //   246	251	817	finally
    //   262	267	817	finally
    //   278	283	817	finally
    //   294	303	817	finally
    //   428	437	817	finally
    //   448	454	817	finally
    //   465	471	817	finally
    //   482	487	817	finally
    //   498	510	817	finally
    //   523	533	817	finally
    //   546	556	817	finally
    //   791	803	832	finally
    //   818	830	832	finally
    //   830	832	832	finally
    //   123	136	836	finally
  }
  
  /* Error */
  public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForName(String paramString)
  {
    // Byte code:
    //   0: ldc_w 581
    //   3: iconst_1
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 8
    //   9: aload_1
    //   10: ifnonnull +12 -> 22
    //   13: aload 8
    //   15: iconst_1
    //   16: invokevirtual 171	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   19: goto +10 -> 29
    //   22: aload 8
    //   24: iconst_1
    //   25: aload_1
    //   26: invokevirtual 175	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   29: aload_0
    //   30: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   33: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   36: aload_0
    //   37: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   40: invokevirtual 254	androidx/room/RoomDatabase:beginTransaction	()V
    //   43: aload_0
    //   44: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   47: aload 8
    //   49: iconst_1
    //   50: aconst_null
    //   51: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   54: astore 9
    //   56: aload 9
    //   58: ldc_w 297
    //   61: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   64: istore_2
    //   65: aload 9
    //   67: ldc_w 299
    //   70: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   73: istore 4
    //   75: aload 9
    //   77: ldc_w 307
    //   80: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   83: istore 5
    //   85: aload 9
    //   87: ldc_w 315
    //   90: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   93: istore_3
    //   94: new 90	androidx/collection/ArrayMap
    //   97: astore 10
    //   99: aload 10
    //   101: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   104: new 90	androidx/collection/ArrayMap
    //   107: astore 11
    //   109: aload 11
    //   111: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   114: aload 9
    //   116: invokeinterface 197 1 0
    //   121: ifeq +108 -> 229
    //   124: aload 9
    //   126: iload_2
    //   127: invokeinterface 201 2 0
    //   132: ifne +43 -> 175
    //   135: aload 9
    //   137: iload_2
    //   138: invokeinterface 205 2 0
    //   143: astore 6
    //   145: aload 10
    //   147: aload 6
    //   149: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   152: checkcast 118	java/util/ArrayList
    //   155: ifnonnull +20 -> 175
    //   158: new 118	java/util/ArrayList
    //   161: astore_1
    //   162: aload_1
    //   163: invokespecial 555	java/util/ArrayList:<init>	()V
    //   166: aload 10
    //   168: aload 6
    //   170: aload_1
    //   171: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   174: pop
    //   175: aload 9
    //   177: iload_2
    //   178: invokeinterface 201 2 0
    //   183: ifne -69 -> 114
    //   186: aload 9
    //   188: iload_2
    //   189: invokeinterface 205 2 0
    //   194: astore_1
    //   195: aload 11
    //   197: aload_1
    //   198: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   201: checkcast 118	java/util/ArrayList
    //   204: ifnonnull +22 -> 226
    //   207: new 118	java/util/ArrayList
    //   210: astore 6
    //   212: aload 6
    //   214: invokespecial 555	java/util/ArrayList:<init>	()V
    //   217: aload 11
    //   219: aload_1
    //   220: aload 6
    //   222: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   225: pop
    //   226: goto -112 -> 114
    //   229: aload 9
    //   231: iconst_m1
    //   232: invokeinterface 558 2 0
    //   237: pop
    //   238: aload_0
    //   239: aload 10
    //   241: invokespecial 228	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   244: aload_0
    //   245: aload 11
    //   247: invokespecial 124	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   250: new 118	java/util/ArrayList
    //   253: astore 12
    //   255: aload 12
    //   257: aload 9
    //   259: invokeinterface 332 1 0
    //   264: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   267: aload 9
    //   269: invokeinterface 197 1 0
    //   274: ifeq +185 -> 459
    //   277: aconst_null
    //   278: astore_1
    //   279: aload 9
    //   281: iload_2
    //   282: invokeinterface 201 2 0
    //   287: ifne +20 -> 307
    //   290: aload 10
    //   292: aload 9
    //   294: iload_2
    //   295: invokeinterface 205 2 0
    //   300: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   303: checkcast 118	java/util/ArrayList
    //   306: astore_1
    //   307: aload_1
    //   308: astore 6
    //   310: aload_1
    //   311: ifnonnull +13 -> 324
    //   314: new 118	java/util/ArrayList
    //   317: astore 6
    //   319: aload 6
    //   321: invokespecial 555	java/util/ArrayList:<init>	()V
    //   324: aconst_null
    //   325: astore_1
    //   326: aload 9
    //   328: iload_2
    //   329: invokeinterface 201 2 0
    //   334: ifne +20 -> 354
    //   337: aload 11
    //   339: aload 9
    //   341: iload_2
    //   342: invokeinterface 205 2 0
    //   347: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   350: checkcast 118	java/util/ArrayList
    //   353: astore_1
    //   354: aload_1
    //   355: astore 7
    //   357: aload_1
    //   358: ifnonnull +13 -> 371
    //   361: new 118	java/util/ArrayList
    //   364: astore 7
    //   366: aload 7
    //   368: invokespecial 555	java/util/ArrayList:<init>	()V
    //   371: new 560	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   374: astore_1
    //   375: aload_1
    //   376: invokespecial 561	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   379: aload_1
    //   380: aload 9
    //   382: iload_2
    //   383: invokeinterface 205 2 0
    //   388: putfield 562	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   391: aload_1
    //   392: aload 9
    //   394: iload 4
    //   396: invokeinterface 340 2 0
    //   401: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   404: putfield 563	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   407: aload_1
    //   408: aload 9
    //   410: iload 5
    //   412: invokeinterface 213 2 0
    //   417: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   420: putfield 564	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   423: aload_1
    //   424: aload 9
    //   426: iload_3
    //   427: invokeinterface 340 2 0
    //   432: putfield 565	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   435: aload_1
    //   436: aload 6
    //   438: putfield 569	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   441: aload_1
    //   442: aload 7
    //   444: putfield 572	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   447: aload 12
    //   449: aload_1
    //   450: invokeinterface 456 2 0
    //   455: pop
    //   456: goto -189 -> 267
    //   459: aload_0
    //   460: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   463: invokevirtual 260	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   466: aload 9
    //   468: invokeinterface 194 1 0
    //   473: aload 8
    //   475: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   478: aload_0
    //   479: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   482: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   485: aload 12
    //   487: areturn
    //   488: astore_1
    //   489: aload 9
    //   491: invokeinterface 194 1 0
    //   496: aload 8
    //   498: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   501: aload_1
    //   502: athrow
    //   503: astore_1
    //   504: aload_0
    //   505: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   508: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   511: aload_1
    //   512: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	513	0	this	WorkSpecDao_Impl
    //   0	513	1	paramString	String
    //   64	319	2	i	int
    //   93	334	3	j	int
    //   73	322	4	k	int
    //   83	328	5	m	int
    //   143	294	6	localObject1	Object
    //   355	88	7	localObject2	Object
    //   7	490	8	localRoomSQLiteQuery	RoomSQLiteQuery
    //   54	436	9	localCursor	Cursor
    //   97	194	10	localArrayMap1	ArrayMap
    //   107	231	11	localArrayMap2	ArrayMap
    //   253	233	12	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   56	114	488	finally
    //   114	175	488	finally
    //   175	226	488	finally
    //   229	267	488	finally
    //   267	277	488	finally
    //   279	307	488	finally
    //   314	324	488	finally
    //   326	354	488	finally
    //   361	371	488	finally
    //   371	456	488	finally
    //   459	466	488	finally
    //   43	56	503	finally
    //   466	478	503	finally
    //   489	501	503	finally
    //   501	503	503	finally
  }
  
  /* Error */
  public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForTag(String paramString)
  {
    // Byte code:
    //   0: ldc_w 584
    //   3: iconst_1
    //   4: invokestatic 155	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore 8
    //   9: aload_1
    //   10: ifnonnull +12 -> 22
    //   13: aload 8
    //   15: iconst_1
    //   16: invokevirtual 171	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   19: goto +10 -> 29
    //   22: aload 8
    //   24: iconst_1
    //   25: aload_1
    //   26: invokevirtual 175	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   29: aload_0
    //   30: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   33: invokevirtual 242	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   36: aload_0
    //   37: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   40: invokevirtual 254	androidx/room/RoomDatabase:beginTransaction	()V
    //   43: aload_0
    //   44: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   47: aload 8
    //   49: iconst_1
    //   50: aconst_null
    //   51: invokestatic 181	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   54: astore 9
    //   56: aload 9
    //   58: ldc_w 297
    //   61: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   64: istore_2
    //   65: aload 9
    //   67: ldc_w 299
    //   70: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   73: istore_3
    //   74: aload 9
    //   76: ldc_w 307
    //   79: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   82: istore 5
    //   84: aload 9
    //   86: ldc_w 315
    //   89: invokestatic 281	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   92: istore 4
    //   94: new 90	androidx/collection/ArrayMap
    //   97: astore 11
    //   99: aload 11
    //   101: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   104: new 90	androidx/collection/ArrayMap
    //   107: astore 10
    //   109: aload 10
    //   111: invokespecial 554	androidx/collection/ArrayMap:<init>	()V
    //   114: aload 9
    //   116: invokeinterface 197 1 0
    //   121: ifeq +108 -> 229
    //   124: aload 9
    //   126: iload_2
    //   127: invokeinterface 201 2 0
    //   132: ifne +43 -> 175
    //   135: aload 9
    //   137: iload_2
    //   138: invokeinterface 205 2 0
    //   143: astore 6
    //   145: aload 11
    //   147: aload 6
    //   149: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   152: checkcast 118	java/util/ArrayList
    //   155: ifnonnull +20 -> 175
    //   158: new 118	java/util/ArrayList
    //   161: astore_1
    //   162: aload_1
    //   163: invokespecial 555	java/util/ArrayList:<init>	()V
    //   166: aload 11
    //   168: aload 6
    //   170: aload_1
    //   171: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   174: pop
    //   175: aload 9
    //   177: iload_2
    //   178: invokeinterface 201 2 0
    //   183: ifne -69 -> 114
    //   186: aload 9
    //   188: iload_2
    //   189: invokeinterface 205 2 0
    //   194: astore 6
    //   196: aload 10
    //   198: aload 6
    //   200: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   203: checkcast 118	java/util/ArrayList
    //   206: ifnonnull +20 -> 226
    //   209: new 118	java/util/ArrayList
    //   212: astore_1
    //   213: aload_1
    //   214: invokespecial 555	java/util/ArrayList:<init>	()V
    //   217: aload 10
    //   219: aload 6
    //   221: aload_1
    //   222: invokevirtual 122	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   225: pop
    //   226: goto -112 -> 114
    //   229: aload 9
    //   231: iconst_m1
    //   232: invokeinterface 558 2 0
    //   237: pop
    //   238: aload_0
    //   239: aload 11
    //   241: invokespecial 228	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   244: aload_0
    //   245: aload 10
    //   247: invokespecial 124	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   250: new 118	java/util/ArrayList
    //   253: astore 12
    //   255: aload 12
    //   257: aload 9
    //   259: invokeinterface 332 1 0
    //   264: invokespecial 333	java/util/ArrayList:<init>	(I)V
    //   267: aload 9
    //   269: invokeinterface 197 1 0
    //   274: ifeq +185 -> 459
    //   277: aconst_null
    //   278: astore_1
    //   279: aload 9
    //   281: iload_2
    //   282: invokeinterface 201 2 0
    //   287: ifne +20 -> 307
    //   290: aload 11
    //   292: aload 9
    //   294: iload_2
    //   295: invokeinterface 205 2 0
    //   300: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   303: checkcast 118	java/util/ArrayList
    //   306: astore_1
    //   307: aload_1
    //   308: astore 6
    //   310: aload_1
    //   311: ifnonnull +13 -> 324
    //   314: new 118	java/util/ArrayList
    //   317: astore 6
    //   319: aload 6
    //   321: invokespecial 555	java/util/ArrayList:<init>	()V
    //   324: aconst_null
    //   325: astore_1
    //   326: aload 9
    //   328: iload_2
    //   329: invokeinterface 201 2 0
    //   334: ifne +20 -> 354
    //   337: aload 10
    //   339: aload 9
    //   341: iload_2
    //   342: invokeinterface 205 2 0
    //   347: invokevirtual 209	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   350: checkcast 118	java/util/ArrayList
    //   353: astore_1
    //   354: aload_1
    //   355: astore 7
    //   357: aload_1
    //   358: ifnonnull +13 -> 371
    //   361: new 118	java/util/ArrayList
    //   364: astore 7
    //   366: aload 7
    //   368: invokespecial 555	java/util/ArrayList:<init>	()V
    //   371: new 560	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   374: astore_1
    //   375: aload_1
    //   376: invokespecial 561	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   379: aload_1
    //   380: aload 9
    //   382: iload_2
    //   383: invokeinterface 205 2 0
    //   388: putfield 562	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   391: aload_1
    //   392: aload 9
    //   394: iload_3
    //   395: invokeinterface 340 2 0
    //   400: invokestatic 391	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   403: putfield 563	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   406: aload_1
    //   407: aload 9
    //   409: iload 5
    //   411: invokeinterface 213 2 0
    //   416: invokestatic 219	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   419: putfield 564	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   422: aload_1
    //   423: aload 9
    //   425: iload 4
    //   427: invokeinterface 340 2 0
    //   432: putfield 565	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   435: aload_1
    //   436: aload 6
    //   438: putfield 569	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   441: aload_1
    //   442: aload 7
    //   444: putfield 572	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   447: aload 12
    //   449: aload_1
    //   450: invokeinterface 456 2 0
    //   455: pop
    //   456: goto -189 -> 267
    //   459: aload_0
    //   460: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   463: invokevirtual 260	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   466: aload 9
    //   468: invokeinterface 194 1 0
    //   473: aload 8
    //   475: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   478: aload_0
    //   479: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   482: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   485: aload 12
    //   487: areturn
    //   488: astore_1
    //   489: aload 9
    //   491: invokeinterface 194 1 0
    //   496: aload 8
    //   498: invokevirtual 458	androidx/room/RoomSQLiteQuery:release	()V
    //   501: aload_1
    //   502: athrow
    //   503: astore_1
    //   504: aload_0
    //   505: getfield 55	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   508: invokevirtual 263	androidx/room/RoomDatabase:endTransaction	()V
    //   511: aload_1
    //   512: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	513	0	this	WorkSpecDao_Impl
    //   0	513	1	paramString	String
    //   64	319	2	i	int
    //   73	322	3	j	int
    //   92	334	4	k	int
    //   82	328	5	m	int
    //   143	294	6	localObject1	Object
    //   355	88	7	localObject2	Object
    //   7	490	8	localRoomSQLiteQuery	RoomSQLiteQuery
    //   54	436	9	localCursor	Cursor
    //   107	231	10	localArrayMap1	ArrayMap
    //   97	194	11	localArrayMap2	ArrayMap
    //   253	233	12	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   56	114	488	finally
    //   114	175	488	finally
    //   175	226	488	finally
    //   229	267	488	finally
    //   267	277	488	finally
    //   279	307	488	finally
    //   314	324	488	finally
    //   326	354	488	finally
    //   361	371	488	finally
    //   371	456	488	finally
    //   459	466	488	finally
    //   43	56	503	finally
    //   466	478	503	finally
    //   489	501	503	finally
    //   501	503	503	finally
  }
  
  public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForIds(List<String> paramList)
  {
    final Object localObject = StringUtil.newStringBuilder();
    ((StringBuilder)localObject).append("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (");
    int i = paramList.size();
    StringUtil.appendPlaceholders((StringBuilder)localObject, i);
    ((StringBuilder)localObject).append(")");
    localObject = RoomSQLiteQuery.acquire(((StringBuilder)localObject).toString(), i + 0);
    i = 1;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (String)localIterator.next();
      if (paramList == null) {
        ((RoomSQLiteQuery)localObject).bindNull(i);
      } else {
        ((RoomSQLiteQuery)localObject).bindString(i, paramList);
      }
      i++;
    }
    paramList = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      /* Error */
      public List<WorkSpec.WorkInfoPojo> call()
        throws Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   4: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   7: invokevirtual 44	androidx/room/RoomDatabase:beginTransaction	()V
        //   10: aload_0
        //   11: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   14: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   17: aload_0
        //   18: getfield 22	androidx/work/impl/model/WorkSpecDao_Impl$11:val$_statement	Landroidx/room/RoomSQLiteQuery;
        //   21: iconst_1
        //   22: aconst_null
        //   23: invokestatic 50	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
        //   26: astore 8
        //   28: aload 8
        //   30: ldc 52
        //   32: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   35: istore_3
        //   36: aload 8
        //   38: ldc 60
        //   40: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   43: istore 4
        //   45: aload 8
        //   47: ldc 62
        //   49: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   52: istore_2
        //   53: aload 8
        //   55: ldc 64
        //   57: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   60: istore_1
        //   61: new 66	androidx/collection/ArrayMap
        //   64: astore 9
        //   66: aload 9
        //   68: invokespecial 67	androidx/collection/ArrayMap:<init>	()V
        //   71: new 66	androidx/collection/ArrayMap
        //   74: astore 10
        //   76: aload 10
        //   78: invokespecial 67	androidx/collection/ArrayMap:<init>	()V
        //   81: aload 8
        //   83: invokeinterface 73 1 0
        //   88: ifeq +114 -> 202
        //   91: aload 8
        //   93: iload_3
        //   94: invokeinterface 77 2 0
        //   99: ifne +46 -> 145
        //   102: aload 8
        //   104: iload_3
        //   105: invokeinterface 81 2 0
        //   110: astore 5
        //   112: aload 9
        //   114: aload 5
        //   116: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   119: checkcast 87	java/util/ArrayList
        //   122: ifnonnull +23 -> 145
        //   125: new 87	java/util/ArrayList
        //   128: astore 6
        //   130: aload 6
        //   132: invokespecial 88	java/util/ArrayList:<init>	()V
        //   135: aload 9
        //   137: aload 5
        //   139: aload 6
        //   141: invokevirtual 92	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   144: pop
        //   145: aload 8
        //   147: iload_3
        //   148: invokeinterface 77 2 0
        //   153: ifne -72 -> 81
        //   156: aload 8
        //   158: iload_3
        //   159: invokeinterface 81 2 0
        //   164: astore 6
        //   166: aload 10
        //   168: aload 6
        //   170: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   173: checkcast 87	java/util/ArrayList
        //   176: ifnonnull +23 -> 199
        //   179: new 87	java/util/ArrayList
        //   182: astore 5
        //   184: aload 5
        //   186: invokespecial 88	java/util/ArrayList:<init>	()V
        //   189: aload 10
        //   191: aload 6
        //   193: aload 5
        //   195: invokevirtual 92	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   198: pop
        //   199: goto -118 -> 81
        //   202: aload 8
        //   204: iconst_m1
        //   205: invokeinterface 95 2 0
        //   210: pop
        //   211: aload_0
        //   212: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   215: aload 9
        //   217: invokestatic 99	androidx/work/impl/model/WorkSpecDao_Impl:access$100	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   220: aload_0
        //   221: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   224: aload 10
        //   226: invokestatic 102	androidx/work/impl/model/WorkSpecDao_Impl:access$200	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   229: new 87	java/util/ArrayList
        //   232: astore 11
        //   234: aload 11
        //   236: aload 8
        //   238: invokeinterface 106 1 0
        //   243: invokespecial 109	java/util/ArrayList:<init>	(I)V
        //   246: aload 8
        //   248: invokeinterface 73 1 0
        //   253: ifeq +201 -> 454
        //   256: aconst_null
        //   257: astore 5
        //   259: aload 8
        //   261: iload_3
        //   262: invokeinterface 77 2 0
        //   267: ifne +21 -> 288
        //   270: aload 9
        //   272: aload 8
        //   274: iload_3
        //   275: invokeinterface 81 2 0
        //   280: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   283: checkcast 87	java/util/ArrayList
        //   286: astore 5
        //   288: aload 5
        //   290: astore 6
        //   292: aload 5
        //   294: ifnonnull +13 -> 307
        //   297: new 87	java/util/ArrayList
        //   300: astore 6
        //   302: aload 6
        //   304: invokespecial 88	java/util/ArrayList:<init>	()V
        //   307: aconst_null
        //   308: astore 5
        //   310: aload 8
        //   312: iload_3
        //   313: invokeinterface 77 2 0
        //   318: ifne +21 -> 339
        //   321: aload 10
        //   323: aload 8
        //   325: iload_3
        //   326: invokeinterface 81 2 0
        //   331: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   334: checkcast 87	java/util/ArrayList
        //   337: astore 5
        //   339: aload 5
        //   341: astore 7
        //   343: aload 5
        //   345: ifnonnull +13 -> 358
        //   348: new 87	java/util/ArrayList
        //   351: astore 7
        //   353: aload 7
        //   355: invokespecial 88	java/util/ArrayList:<init>	()V
        //   358: new 111	androidx/work/impl/model/WorkSpec$WorkInfoPojo
        //   361: astore 5
        //   363: aload 5
        //   365: invokespecial 112	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
        //   368: aload 5
        //   370: aload 8
        //   372: iload_3
        //   373: invokeinterface 81 2 0
        //   378: putfield 115	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
        //   381: aload 5
        //   383: aload 8
        //   385: iload 4
        //   387: invokeinterface 119 2 0
        //   392: invokestatic 125	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
        //   395: putfield 128	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
        //   398: aload 5
        //   400: aload 8
        //   402: iload_2
        //   403: invokeinterface 132 2 0
        //   408: invokestatic 138	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
        //   411: putfield 141	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
        //   414: aload 5
        //   416: aload 8
        //   418: iload_1
        //   419: invokeinterface 119 2 0
        //   424: putfield 145	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
        //   427: aload 5
        //   429: aload 6
        //   431: putfield 149	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
        //   434: aload 5
        //   436: aload 7
        //   438: putfield 152	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
        //   441: aload 11
        //   443: aload 5
        //   445: invokeinterface 158 2 0
        //   450: pop
        //   451: goto -205 -> 246
        //   454: aload_0
        //   455: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   458: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   461: invokevirtual 161	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   464: aload 8
        //   466: invokeinterface 164 1 0
        //   471: aload_0
        //   472: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   475: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   478: invokevirtual 167	androidx/room/RoomDatabase:endTransaction	()V
        //   481: aload 11
        //   483: areturn
        //   484: astore 5
        //   486: aload 8
        //   488: invokeinterface 164 1 0
        //   493: aload 5
        //   495: athrow
        //   496: astore 5
        //   498: aload_0
        //   499: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   502: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   505: invokevirtual 167	androidx/room/RoomDatabase:endTransaction	()V
        //   508: aload 5
        //   510: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	511	0	this	11
        //   60	359	1	i	int
        //   52	351	2	j	int
        //   35	338	3	k	int
        //   43	343	4	m	int
        //   110	334	5	localObject1	Object
        //   484	10	5	localObject2	Object
        //   496	13	5	localObject3	Object
        //   128	302	6	localObject4	Object
        //   341	96	7	localObject5	Object
        //   26	461	8	localCursor	Cursor
        //   64	207	9	localArrayMap1	ArrayMap
        //   74	248	10	localArrayMap2	ArrayMap
        //   232	250	11	localArrayList	ArrayList
        // Exception table:
        //   from	to	target	type
        //   28	81	484	finally
        //   81	145	484	finally
        //   145	199	484	finally
        //   202	246	484	finally
        //   246	256	484	finally
        //   259	288	484	finally
        //   297	307	484	finally
        //   310	339	484	finally
        //   348	358	484	finally
        //   358	451	484	finally
        //   454	464	484	finally
        //   10	28	496	finally
        //   464	471	496	finally
        //   486	496	496	finally
      }
      
      protected void finalize()
      {
        localObject.release();
      }
    };
    return paramList.createLiveData(new String[] { "WorkTag", "WorkProgress", "workspec" }, true, (Callable)localObject);
  }
  
  public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForName(String paramString)
  {
    final Object localObject = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
    if (paramString == null) {
      ((RoomSQLiteQuery)localObject).bindNull(1);
    } else {
      ((RoomSQLiteQuery)localObject).bindString(1, paramString);
    }
    paramString = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      /* Error */
      public List<WorkSpec.WorkInfoPojo> call()
        throws Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$13:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   4: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   7: invokevirtual 44	androidx/room/RoomDatabase:beginTransaction	()V
        //   10: aload_0
        //   11: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$13:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   14: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   17: aload_0
        //   18: getfield 22	androidx/work/impl/model/WorkSpecDao_Impl$13:val$_statement	Landroidx/room/RoomSQLiteQuery;
        //   21: iconst_1
        //   22: aconst_null
        //   23: invokestatic 50	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
        //   26: astore 8
        //   28: aload 8
        //   30: ldc 52
        //   32: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   35: istore 4
        //   37: aload 8
        //   39: ldc 60
        //   41: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   44: istore_3
        //   45: aload 8
        //   47: ldc 62
        //   49: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   52: istore_2
        //   53: aload 8
        //   55: ldc 64
        //   57: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   60: istore_1
        //   61: new 66	androidx/collection/ArrayMap
        //   64: astore 10
        //   66: aload 10
        //   68: invokespecial 67	androidx/collection/ArrayMap:<init>	()V
        //   71: new 66	androidx/collection/ArrayMap
        //   74: astore 9
        //   76: aload 9
        //   78: invokespecial 67	androidx/collection/ArrayMap:<init>	()V
        //   81: aload 8
        //   83: invokeinterface 73 1 0
        //   88: ifeq +118 -> 206
        //   91: aload 8
        //   93: iload 4
        //   95: invokeinterface 77 2 0
        //   100: ifne +47 -> 147
        //   103: aload 8
        //   105: iload 4
        //   107: invokeinterface 81 2 0
        //   112: astore 6
        //   114: aload 10
        //   116: aload 6
        //   118: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   121: checkcast 87	java/util/ArrayList
        //   124: ifnonnull +23 -> 147
        //   127: new 87	java/util/ArrayList
        //   130: astore 5
        //   132: aload 5
        //   134: invokespecial 88	java/util/ArrayList:<init>	()V
        //   137: aload 10
        //   139: aload 6
        //   141: aload 5
        //   143: invokevirtual 92	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   146: pop
        //   147: aload 8
        //   149: iload 4
        //   151: invokeinterface 77 2 0
        //   156: ifne -75 -> 81
        //   159: aload 8
        //   161: iload 4
        //   163: invokeinterface 81 2 0
        //   168: astore 6
        //   170: aload 9
        //   172: aload 6
        //   174: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   177: checkcast 87	java/util/ArrayList
        //   180: ifnonnull +23 -> 203
        //   183: new 87	java/util/ArrayList
        //   186: astore 5
        //   188: aload 5
        //   190: invokespecial 88	java/util/ArrayList:<init>	()V
        //   193: aload 9
        //   195: aload 6
        //   197: aload 5
        //   199: invokevirtual 92	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   202: pop
        //   203: goto -122 -> 81
        //   206: aload 8
        //   208: iconst_m1
        //   209: invokeinterface 95 2 0
        //   214: pop
        //   215: aload_0
        //   216: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$13:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   219: aload 10
        //   221: invokestatic 99	androidx/work/impl/model/WorkSpecDao_Impl:access$100	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   224: aload_0
        //   225: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$13:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   228: aload 9
        //   230: invokestatic 102	androidx/work/impl/model/WorkSpecDao_Impl:access$200	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   233: new 87	java/util/ArrayList
        //   236: astore 11
        //   238: aload 11
        //   240: aload 8
        //   242: invokeinterface 106 1 0
        //   247: invokespecial 109	java/util/ArrayList:<init>	(I)V
        //   250: aload 8
        //   252: invokeinterface 73 1 0
        //   257: ifeq +205 -> 462
        //   260: aconst_null
        //   261: astore 5
        //   263: aload 8
        //   265: iload 4
        //   267: invokeinterface 77 2 0
        //   272: ifne +22 -> 294
        //   275: aload 10
        //   277: aload 8
        //   279: iload 4
        //   281: invokeinterface 81 2 0
        //   286: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   289: checkcast 87	java/util/ArrayList
        //   292: astore 5
        //   294: aload 5
        //   296: astore 6
        //   298: aload 5
        //   300: ifnonnull +13 -> 313
        //   303: new 87	java/util/ArrayList
        //   306: astore 6
        //   308: aload 6
        //   310: invokespecial 88	java/util/ArrayList:<init>	()V
        //   313: aconst_null
        //   314: astore 5
        //   316: aload 8
        //   318: iload 4
        //   320: invokeinterface 77 2 0
        //   325: ifne +22 -> 347
        //   328: aload 9
        //   330: aload 8
        //   332: iload 4
        //   334: invokeinterface 81 2 0
        //   339: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   342: checkcast 87	java/util/ArrayList
        //   345: astore 5
        //   347: aload 5
        //   349: astore 7
        //   351: aload 5
        //   353: ifnonnull +13 -> 366
        //   356: new 87	java/util/ArrayList
        //   359: astore 7
        //   361: aload 7
        //   363: invokespecial 88	java/util/ArrayList:<init>	()V
        //   366: new 111	androidx/work/impl/model/WorkSpec$WorkInfoPojo
        //   369: astore 5
        //   371: aload 5
        //   373: invokespecial 112	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
        //   376: aload 5
        //   378: aload 8
        //   380: iload 4
        //   382: invokeinterface 81 2 0
        //   387: putfield 115	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
        //   390: aload 5
        //   392: aload 8
        //   394: iload_3
        //   395: invokeinterface 119 2 0
        //   400: invokestatic 125	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
        //   403: putfield 128	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
        //   406: aload 5
        //   408: aload 8
        //   410: iload_2
        //   411: invokeinterface 132 2 0
        //   416: invokestatic 138	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
        //   419: putfield 141	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
        //   422: aload 5
        //   424: aload 8
        //   426: iload_1
        //   427: invokeinterface 119 2 0
        //   432: putfield 145	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
        //   435: aload 5
        //   437: aload 6
        //   439: putfield 149	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
        //   442: aload 5
        //   444: aload 7
        //   446: putfield 152	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
        //   449: aload 11
        //   451: aload 5
        //   453: invokeinterface 158 2 0
        //   458: pop
        //   459: goto -209 -> 250
        //   462: aload_0
        //   463: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$13:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   466: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   469: invokevirtual 161	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   472: aload 8
        //   474: invokeinterface 164 1 0
        //   479: aload_0
        //   480: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$13:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   483: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   486: invokevirtual 167	androidx/room/RoomDatabase:endTransaction	()V
        //   489: aload 11
        //   491: areturn
        //   492: astore 5
        //   494: aload 8
        //   496: invokeinterface 164 1 0
        //   501: aload 5
        //   503: athrow
        //   504: astore 5
        //   506: aload_0
        //   507: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$13:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   510: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   513: invokevirtual 167	androidx/room/RoomDatabase:endTransaction	()V
        //   516: aload 5
        //   518: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	519	0	this	13
        //   60	367	1	i	int
        //   52	359	2	j	int
        //   44	351	3	k	int
        //   35	346	4	m	int
        //   130	322	5	localObject1	Object
        //   492	10	5	localObject2	Object
        //   504	13	5	localObject3	Object
        //   112	326	6	localObject4	Object
        //   349	96	7	localObject5	Object
        //   26	469	8	localCursor	Cursor
        //   74	255	9	localArrayMap1	ArrayMap
        //   64	212	10	localArrayMap2	ArrayMap
        //   236	254	11	localArrayList	ArrayList
        // Exception table:
        //   from	to	target	type
        //   28	81	492	finally
        //   81	147	492	finally
        //   147	203	492	finally
        //   206	250	492	finally
        //   250	260	492	finally
        //   263	294	492	finally
        //   303	313	492	finally
        //   316	347	492	finally
        //   356	366	492	finally
        //   366	459	492	finally
        //   462	472	492	finally
        //   10	28	504	finally
        //   472	479	504	finally
        //   494	504	504	finally
      }
      
      protected void finalize()
      {
        localObject.release();
      }
    };
    return paramString.createLiveData(new String[] { "WorkTag", "WorkProgress", "workspec", "workname" }, true, (Callable)localObject);
  }
  
  public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForTag(String paramString)
  {
    final Object localObject = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
    if (paramString == null) {
      ((RoomSQLiteQuery)localObject).bindNull(1);
    } else {
      ((RoomSQLiteQuery)localObject).bindString(1, paramString);
    }
    paramString = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      /* Error */
      public List<WorkSpec.WorkInfoPojo> call()
        throws Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   4: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   7: invokevirtual 44	androidx/room/RoomDatabase:beginTransaction	()V
        //   10: aload_0
        //   11: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   14: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   17: aload_0
        //   18: getfield 22	androidx/work/impl/model/WorkSpecDao_Impl$12:val$_statement	Landroidx/room/RoomSQLiteQuery;
        //   21: iconst_1
        //   22: aconst_null
        //   23: invokestatic 50	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
        //   26: astore 8
        //   28: aload 8
        //   30: ldc 52
        //   32: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   35: istore_2
        //   36: aload 8
        //   38: ldc 60
        //   40: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   43: istore 4
        //   45: aload 8
        //   47: ldc 62
        //   49: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   52: istore_3
        //   53: aload 8
        //   55: ldc 64
        //   57: invokestatic 58	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   60: istore_1
        //   61: new 66	androidx/collection/ArrayMap
        //   64: astore 10
        //   66: aload 10
        //   68: invokespecial 67	androidx/collection/ArrayMap:<init>	()V
        //   71: new 66	androidx/collection/ArrayMap
        //   74: astore 9
        //   76: aload 9
        //   78: invokespecial 67	androidx/collection/ArrayMap:<init>	()V
        //   81: aload 8
        //   83: invokeinterface 73 1 0
        //   88: ifeq +114 -> 202
        //   91: aload 8
        //   93: iload_2
        //   94: invokeinterface 77 2 0
        //   99: ifne +46 -> 145
        //   102: aload 8
        //   104: iload_2
        //   105: invokeinterface 81 2 0
        //   110: astore 6
        //   112: aload 10
        //   114: aload 6
        //   116: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   119: checkcast 87	java/util/ArrayList
        //   122: ifnonnull +23 -> 145
        //   125: new 87	java/util/ArrayList
        //   128: astore 5
        //   130: aload 5
        //   132: invokespecial 88	java/util/ArrayList:<init>	()V
        //   135: aload 10
        //   137: aload 6
        //   139: aload 5
        //   141: invokevirtual 92	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   144: pop
        //   145: aload 8
        //   147: iload_2
        //   148: invokeinterface 77 2 0
        //   153: ifne -72 -> 81
        //   156: aload 8
        //   158: iload_2
        //   159: invokeinterface 81 2 0
        //   164: astore 5
        //   166: aload 9
        //   168: aload 5
        //   170: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   173: checkcast 87	java/util/ArrayList
        //   176: ifnonnull +23 -> 199
        //   179: new 87	java/util/ArrayList
        //   182: astore 6
        //   184: aload 6
        //   186: invokespecial 88	java/util/ArrayList:<init>	()V
        //   189: aload 9
        //   191: aload 5
        //   193: aload 6
        //   195: invokevirtual 92	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   198: pop
        //   199: goto -118 -> 81
        //   202: aload 8
        //   204: iconst_m1
        //   205: invokeinterface 95 2 0
        //   210: pop
        //   211: aload_0
        //   212: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   215: aload 10
        //   217: invokestatic 99	androidx/work/impl/model/WorkSpecDao_Impl:access$100	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   220: aload_0
        //   221: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   224: aload 9
        //   226: invokestatic 102	androidx/work/impl/model/WorkSpecDao_Impl:access$200	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   229: new 87	java/util/ArrayList
        //   232: astore 11
        //   234: aload 11
        //   236: aload 8
        //   238: invokeinterface 106 1 0
        //   243: invokespecial 109	java/util/ArrayList:<init>	(I)V
        //   246: aload 8
        //   248: invokeinterface 73 1 0
        //   253: ifeq +201 -> 454
        //   256: aconst_null
        //   257: astore 5
        //   259: aload 8
        //   261: iload_2
        //   262: invokeinterface 77 2 0
        //   267: ifne +21 -> 288
        //   270: aload 10
        //   272: aload 8
        //   274: iload_2
        //   275: invokeinterface 81 2 0
        //   280: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   283: checkcast 87	java/util/ArrayList
        //   286: astore 5
        //   288: aload 5
        //   290: astore 6
        //   292: aload 5
        //   294: ifnonnull +13 -> 307
        //   297: new 87	java/util/ArrayList
        //   300: astore 6
        //   302: aload 6
        //   304: invokespecial 88	java/util/ArrayList:<init>	()V
        //   307: aconst_null
        //   308: astore 5
        //   310: aload 8
        //   312: iload_2
        //   313: invokeinterface 77 2 0
        //   318: ifne +21 -> 339
        //   321: aload 9
        //   323: aload 8
        //   325: iload_2
        //   326: invokeinterface 81 2 0
        //   331: invokevirtual 85	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   334: checkcast 87	java/util/ArrayList
        //   337: astore 5
        //   339: aload 5
        //   341: astore 7
        //   343: aload 5
        //   345: ifnonnull +13 -> 358
        //   348: new 87	java/util/ArrayList
        //   351: astore 7
        //   353: aload 7
        //   355: invokespecial 88	java/util/ArrayList:<init>	()V
        //   358: new 111	androidx/work/impl/model/WorkSpec$WorkInfoPojo
        //   361: astore 5
        //   363: aload 5
        //   365: invokespecial 112	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
        //   368: aload 5
        //   370: aload 8
        //   372: iload_2
        //   373: invokeinterface 81 2 0
        //   378: putfield 115	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
        //   381: aload 5
        //   383: aload 8
        //   385: iload 4
        //   387: invokeinterface 119 2 0
        //   392: invokestatic 125	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
        //   395: putfield 128	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
        //   398: aload 5
        //   400: aload 8
        //   402: iload_3
        //   403: invokeinterface 132 2 0
        //   408: invokestatic 138	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
        //   411: putfield 141	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
        //   414: aload 5
        //   416: aload 8
        //   418: iload_1
        //   419: invokeinterface 119 2 0
        //   424: putfield 145	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
        //   427: aload 5
        //   429: aload 6
        //   431: putfield 149	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
        //   434: aload 5
        //   436: aload 7
        //   438: putfield 152	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
        //   441: aload 11
        //   443: aload 5
        //   445: invokeinterface 158 2 0
        //   450: pop
        //   451: goto -205 -> 246
        //   454: aload_0
        //   455: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   458: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   461: invokevirtual 161	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   464: aload 8
        //   466: invokeinterface 164 1 0
        //   471: aload_0
        //   472: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   475: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   478: invokevirtual 167	androidx/room/RoomDatabase:endTransaction	()V
        //   481: aload 11
        //   483: areturn
        //   484: astore 5
        //   486: aload 8
        //   488: invokeinterface 164 1 0
        //   493: aload 5
        //   495: athrow
        //   496: astore 5
        //   498: aload_0
        //   499: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   502: invokestatic 39	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   505: invokevirtual 167	androidx/room/RoomDatabase:endTransaction	()V
        //   508: aload 5
        //   510: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	511	0	this	12
        //   60	359	1	i	int
        //   35	338	2	j	int
        //   52	351	3	k	int
        //   43	343	4	m	int
        //   128	316	5	localObject1	Object
        //   484	10	5	localObject2	Object
        //   496	13	5	localObject3	Object
        //   110	320	6	localObject4	Object
        //   341	96	7	localObject5	Object
        //   26	461	8	localCursor	Cursor
        //   74	248	9	localArrayMap1	ArrayMap
        //   64	207	10	localArrayMap2	ArrayMap
        //   232	250	11	localArrayList	ArrayList
        // Exception table:
        //   from	to	target	type
        //   28	81	484	finally
        //   81	145	484	finally
        //   145	199	484	finally
        //   202	246	484	finally
        //   246	256	484	finally
        //   259	288	484	finally
        //   297	307	484	finally
        //   310	339	484	finally
        //   348	358	484	finally
        //   358	451	484	finally
        //   454	464	484	finally
        //   10	28	496	finally
        //   464	471	496	finally
        //   486	496	496	finally
      }
      
      protected void finalize()
      {
        localObject.release();
      }
    };
    return paramString.createLiveData(new String[] { "WorkTag", "WorkProgress", "workspec", "worktag" }, true, (Callable)localObject);
  }
  
  public boolean hasUnfinishedWork()
  {
    boolean bool = false;
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT COUNT(*) > 0 FROM workspec WHERE state NOT IN (2, 3, 5) LIMIT 1", 0);
    this.__db.assertNotSuspendingTransaction();
    Cursor localCursor = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      if (localCursor.moveToFirst())
      {
        int i = localCursor.getInt(0);
        if (i != 0) {
          bool = true;
        }
      }
      else
      {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public int incrementWorkSpecRunAttemptCount(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.acquire();
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindString(1, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.release(localSupportSQLiteStatement);
    }
  }
  
  public void insertWorkSpec(WorkSpec paramWorkSpec)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfWorkSpec.insert(paramWorkSpec);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
  
  public int markWorkSpecScheduled(String paramString, long paramLong)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfMarkWorkSpecScheduled.acquire();
    localSupportSQLiteStatement.bindLong(1, paramLong);
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(2);
    } else {
      localSupportSQLiteStatement.bindString(2, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfMarkWorkSpecScheduled.release(localSupportSQLiteStatement);
    }
  }
  
  public void pruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast()
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.acquire();
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.release(localSupportSQLiteStatement);
    }
  }
  
  public int resetScheduledState()
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfResetScheduledState.acquire();
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfResetScheduledState.release(localSupportSQLiteStatement);
    }
  }
  
  public int resetWorkSpecRunAttemptCount(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfResetWorkSpecRunAttemptCount.acquire();
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindString(1, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfResetWorkSpecRunAttemptCount.release(localSupportSQLiteStatement);
    }
  }
  
  public void setOutput(String paramString, Data paramData)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetOutput.acquire();
    paramData = Data.toByteArrayInternal(paramData);
    if (paramData == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindBlob(1, paramData);
    }
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(2);
    } else {
      localSupportSQLiteStatement.bindString(2, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfSetOutput.release(localSupportSQLiteStatement);
    }
  }
  
  public void setPeriodStartTime(String paramString, long paramLong)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetPeriodStartTime.acquire();
    localSupportSQLiteStatement.bindLong(1, paramLong);
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(2);
    } else {
      localSupportSQLiteStatement.bindString(2, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfSetPeriodStartTime.release(localSupportSQLiteStatement);
    }
  }
  
  public int setState(WorkInfo.State paramState, String... paramVarArgs)
  {
    this.__db.assertNotSuspendingTransaction();
    Object localObject = StringUtil.newStringBuilder();
    ((StringBuilder)localObject).append("UPDATE workspec SET state=");
    ((StringBuilder)localObject).append("?");
    ((StringBuilder)localObject).append(" WHERE id IN (");
    StringUtil.appendPlaceholders((StringBuilder)localObject, paramVarArgs.length);
    ((StringBuilder)localObject).append(")");
    localObject = ((StringBuilder)localObject).toString();
    localObject = this.__db.compileStatement((String)localObject);
    ((SupportSQLiteStatement)localObject).bindLong(1, WorkTypeConverters.stateToInt(paramState));
    int j = 2;
    int k = paramVarArgs.length;
    for (int i = 0; i < k; i++)
    {
      paramState = paramVarArgs[i];
      if (paramState == null) {
        ((SupportSQLiteStatement)localObject).bindNull(j);
      } else {
        ((SupportSQLiteStatement)localObject).bindString(j, paramState);
      }
      j++;
    }
    this.__db.beginTransaction();
    try
    {
      i = ((SupportSQLiteStatement)localObject).executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/WorkSpecDao_Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */