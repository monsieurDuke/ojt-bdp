package androidx.work.impl.workers;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import java.util.List;
import java.util.concurrent.TimeUnit;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 00E4.java */
/* loaded from: classes.dex */
public class DiagnosticsWorker extends Worker {
    private static final String TAG;

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    static {
        String tagWithPrefix = Logger.tagWithPrefix("DiagnosticsWrkr");
        Log5ECF72.a(tagWithPrefix);
        LogE84000.a(tagWithPrefix);
        Log229316.a(tagWithPrefix);
        TAG = tagWithPrefix;
    }

    public DiagnosticsWorker(Context context, WorkerParameters parameters) {
        super(context, parameters);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    private static String workSpecRow(WorkSpec workSpec, String name, Integer systemId, String tags) {
        String format = String.format("\n%s\t %s\t %s\t %s\t %s\t %s\t", workSpec.id, workSpec.workerClassName, systemId, workSpec.state.name(), name, tags);
        Log5ECF72.a(format);
        LogE84000.a(format);
        Log229316.a(format);
        return format;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    private static String workSpecRows(WorkNameDao workNameDao, WorkTagDao workTagDao, SystemIdInfoDao systemIdInfoDao, List<WorkSpec> workSpecs) {
        StringBuilder sb = new StringBuilder();
        String format = String.format("\n Id \t Class Name\t %s\t State\t Unique Name\t Tags\t", Build.VERSION.SDK_INT >= 23 ? "Job Id" : "Alarm Id");
        Log5ECF72.a(format);
        LogE84000.a(format);
        Log229316.a(format);
        sb.append(format);
        for (WorkSpec workSpec : workSpecs) {
            Integer num = null;
            SystemIdInfo systemIdInfo = systemIdInfoDao.getSystemIdInfo(workSpec.id);
            if (systemIdInfo != null) {
                num = Integer.valueOf(systemIdInfo.systemId);
            }
            List<String> namesForWorkSpecId = workNameDao.getNamesForWorkSpecId(workSpec.id);
            List<String> tagsForWorkSpecId = workTagDao.getTagsForWorkSpecId(workSpec.id);
            String join = TextUtils.join(",", namesForWorkSpecId);
            Log5ECF72.a(join);
            LogE84000.a(join);
            Log229316.a(join);
            String join2 = TextUtils.join(",", tagsForWorkSpecId);
            Log5ECF72.a(join2);
            LogE84000.a(join2);
            String workSpecRow = workSpecRow(workSpec, join, num, join2);
            Log5ECF72.a(workSpecRow);
            Log229316.a(workSpecRow);
            sb.append(workSpecRow);
        }
        return sb.toString();
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        WorkDatabase workDatabase = WorkManagerImpl.getInstance(getApplicationContext()).getWorkDatabase();
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        WorkNameDao workNameDao = workDatabase.workNameDao();
        WorkTagDao workTagDao = workDatabase.workTagDao();
        SystemIdInfoDao systemIdInfoDao = workDatabase.systemIdInfoDao();
        List<WorkSpec> recentlyCompletedWork = workSpecDao.getRecentlyCompletedWork(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1L));
        List<WorkSpec> runningWork = workSpecDao.getRunningWork();
        List<WorkSpec> allEligibleWorkSpecsForScheduling = workSpecDao.getAllEligibleWorkSpecsForScheduling(200);
        if (recentlyCompletedWork != null && !recentlyCompletedWork.isEmpty()) {
            Logger logger = Logger.get();
            String str = TAG;
            logger.info(str, "Recently completed work:\n\n", new Throwable[0]);
            Logger logger2 = Logger.get();
            String workSpecRows = workSpecRows(workNameDao, workTagDao, systemIdInfoDao, recentlyCompletedWork);
            Log5ECF72.a(workSpecRows);
            LogE84000.a(workSpecRows);
            Log229316.a(workSpecRows);
            logger2.info(str, workSpecRows, new Throwable[0]);
        }
        if (runningWork != null && !runningWork.isEmpty()) {
            Logger logger3 = Logger.get();
            String str2 = TAG;
            logger3.info(str2, "Running work:\n\n", new Throwable[0]);
            Logger logger4 = Logger.get();
            String workSpecRows2 = workSpecRows(workNameDao, workTagDao, systemIdInfoDao, runningWork);
            Log5ECF72.a(workSpecRows2);
            LogE84000.a(workSpecRows2);
            Log229316.a(workSpecRows2);
            logger4.info(str2, workSpecRows2, new Throwable[0]);
        }
        if (allEligibleWorkSpecsForScheduling != null && !allEligibleWorkSpecsForScheduling.isEmpty()) {
            Logger logger5 = Logger.get();
            String str3 = TAG;
            logger5.info(str3, "Enqueued work:\n\n", new Throwable[0]);
            Logger logger6 = Logger.get();
            String workSpecRows3 = workSpecRows(workNameDao, workTagDao, systemIdInfoDao, allEligibleWorkSpecsForScheduling);
            Log5ECF72.a(workSpecRows3);
            LogE84000.a(workSpecRows3);
            Log229316.a(workSpecRows3);
            logger6.info(str3, workSpecRows3, new Throwable[0]);
        }
        return ListenableWorker.Result.success();
    }
}
