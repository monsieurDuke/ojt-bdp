package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 00CF.java */
/* loaded from: classes.dex */
public class StorageNotLowTracker extends BroadcastReceiverConstraintTracker<Boolean> {
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
        String tagWithPrefix = Logger.tagWithPrefix("StorageNotLowTracker");
        Log5ECF72.a(tagWithPrefix);
        LogE84000.a(tagWithPrefix);
        Log229316.a(tagWithPrefix);
        TAG = tagWithPrefix;
    }

    public StorageNotLowTracker(Context context, TaskExecutor taskExecutor) {
        super(context, taskExecutor);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
    
        if (r4.equals("android.intent.action.DEVICE_STORAGE_LOW") != false) goto L17;
     */
    @Override // androidx.work.impl.constraints.trackers.ConstraintTracker
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Boolean getInitialState() {
        /*
            r8 = this;
            android.content.Context r0 = r8.mAppContext
            android.content.IntentFilter r1 = r8.getIntentFilter()
            r2 = 0
            android.content.Intent r0 = r0.registerReceiver(r2, r1)
            r1 = 1
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r1)
            if (r0 == 0) goto L45
            java.lang.String r4 = r0.getAction()
            if (r4 != 0) goto L19
            goto L45
        L19:
            java.lang.String r4 = r0.getAction()
            r5 = -1
            int r6 = r4.hashCode()
            r7 = 0
            switch(r6) {
                case -1181163412: goto L31;
                case -730838620: goto L27;
                default: goto L26;
            }
        L26:
            goto L3a
        L27:
            java.lang.String r1 = "android.intent.action.DEVICE_STORAGE_OK"
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L26
            r1 = r7
            goto L3b
        L31:
            java.lang.String r6 = "android.intent.action.DEVICE_STORAGE_LOW"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L26
            goto L3b
        L3a:
            r1 = r5
        L3b:
            switch(r1) {
                case 0: goto L44;
                case 1: goto L3f;
                default: goto L3e;
            }
        L3e:
            return r2
        L3f:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r7)
            return r1
        L44:
            return r3
        L45:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.constraints.trackers.StorageNotLowTracker.getInitialState():java.lang.Boolean");
    }

    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
        intentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
        return intentFilter;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    public void onBroadcastReceive(Context context, Intent intent) {
        if (intent.getAction() == null) {
            return;
        }
        Logger logger = Logger.get();
        String str = TAG;
        String format = String.format("Received %s", intent.getAction());
        Log5ECF72.a(format);
        LogE84000.a(format);
        Log229316.a(format);
        logger.debug(str, format, new Throwable[0]);
        String action = intent.getAction();
        char c = 65535;
        switch (action.hashCode()) {
            case -1181163412:
                if (action.equals("android.intent.action.DEVICE_STORAGE_LOW")) {
                    c = 1;
                    break;
                }
                break;
            case -730838620:
                if (action.equals("android.intent.action.DEVICE_STORAGE_OK")) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                setState(true);
                return;
            case 1:
                setState(false);
                return;
            default:
                return;
        }
    }
}
