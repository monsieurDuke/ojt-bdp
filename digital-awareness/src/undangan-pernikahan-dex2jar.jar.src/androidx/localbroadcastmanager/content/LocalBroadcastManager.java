package androidx.localbroadcastmanager.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class LocalBroadcastManager
{
  private static final boolean DEBUG = false;
  static final int MSG_EXEC_PENDING_BROADCASTS = 1;
  private static final String TAG = "LocalBroadcastManager";
  private static LocalBroadcastManager mInstance;
  private static final Object mLock = new Object();
  private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap();
  private final Context mAppContext;
  private final Handler mHandler;
  private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList();
  private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap();
  
  private LocalBroadcastManager(Context paramContext)
  {
    this.mAppContext = paramContext;
    this.mHandler = new Handler(paramContext.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default: 
          super.handleMessage(paramAnonymousMessage);
          break;
        case 1: 
          LocalBroadcastManager.this.executePendingBroadcasts();
        }
      }
    };
  }
  
  public static LocalBroadcastManager getInstance(Context paramContext)
  {
    synchronized (mLock)
    {
      if (mInstance == null)
      {
        LocalBroadcastManager localLocalBroadcastManager = new androidx/localbroadcastmanager/content/LocalBroadcastManager;
        localLocalBroadcastManager.<init>(paramContext.getApplicationContext());
        mInstance = localLocalBroadcastManager;
      }
      paramContext = mInstance;
      return paramContext;
    }
  }
  
  void executePendingBroadcasts()
  {
    int i;
    BroadcastRecord[] arrayOfBroadcastRecord;
    int k;
    int j;
    ReceiverRecord localReceiverRecord;
    synchronized (this.mReceivers)
    {
      i = this.mPendingBroadcasts.size();
      if (i <= 0) {
        return;
      }
      arrayOfBroadcastRecord = new BroadcastRecord[i];
    }
    throw ((Throwable)localObject1);
  }
  
  public void registerReceiver(BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
  {
    synchronized (this.mReceivers)
    {
      ReceiverRecord localReceiverRecord = new androidx/localbroadcastmanager/content/LocalBroadcastManager$ReceiverRecord;
      localReceiverRecord.<init>(paramIntentFilter, paramBroadcastReceiver);
      Object localObject2 = (ArrayList)this.mReceivers.get(paramBroadcastReceiver);
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new java/util/ArrayList;
        ((ArrayList)localObject1).<init>(1);
        this.mReceivers.put(paramBroadcastReceiver, localObject1);
      }
      ((ArrayList)localObject1).add(localReceiverRecord);
      for (int i = 0; i < paramIntentFilter.countActions(); i++)
      {
        localObject2 = paramIntentFilter.getAction(i);
        localObject1 = (ArrayList)this.mActions.get(localObject2);
        paramBroadcastReceiver = (BroadcastReceiver)localObject1;
        if (localObject1 == null)
        {
          paramBroadcastReceiver = new java/util/ArrayList;
          paramBroadcastReceiver.<init>(1);
          this.mActions.put(localObject2, paramBroadcastReceiver);
        }
        paramBroadcastReceiver.add(localReceiverRecord);
      }
      return;
    }
  }
  
  public boolean sendBroadcast(Intent paramIntent)
  {
    synchronized (this.mReceivers)
    {
      String str1 = paramIntent.getAction();
      Object localObject3 = paramIntent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
      Uri localUri = paramIntent.getData();
      String str2 = paramIntent.getScheme();
      Set localSet = paramIntent.getCategories();
      int i;
      if ((paramIntent.getFlags() & 0x8) != 0) {
        i = 1;
      } else {
        i = 0;
      }
      Object localObject1;
      if (i != 0)
      {
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        Log.v("LocalBroadcastManager", "Resolving type " + (String)localObject3 + " scheme " + str2 + " of intent " + paramIntent);
      }
      ArrayList localArrayList = (ArrayList)this.mActions.get(paramIntent.getAction());
      if (localArrayList != null)
      {
        if (i != 0)
        {
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          Log.v("LocalBroadcastManager", "Action list: " + localArrayList);
        }
        Object localObject2 = null;
        int j = 0;
        while (j < localArrayList.size())
        {
          Object localObject4 = (ReceiverRecord)localArrayList.get(j);
          if (i != 0)
          {
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            Log.v("LocalBroadcastManager", "Matching against filter " + ((ReceiverRecord)localObject4).filter);
          }
          if (((ReceiverRecord)localObject4).broadcasting)
          {
            if (i != 0) {
              Log.v("LocalBroadcastManager", "  Filter's target already added");
            }
          }
          else
          {
            localObject1 = ((ReceiverRecord)localObject4).filter;
            int k = ((IntentFilter)localObject1).match(str1, (String)localObject3, str2, localUri, localSet, "LocalBroadcastManager");
            if (k >= 0)
            {
              if (i != 0)
              {
                localObject1 = new java/lang/StringBuilder;
                ((StringBuilder)localObject1).<init>();
                localObject1 = ((StringBuilder)localObject1).append("  Filter matched!  match=0x");
                String str3 = Integer.toHexString(k);
                Log5ECF72.a(str3);
                LogE84000.a(str3);
                Log229316.a(str3);
                Log.v("LocalBroadcastManager", str3);
              }
              if (localObject2 == null)
              {
                localObject1 = new java/util/ArrayList;
                ((ArrayList)localObject1).<init>();
              }
              else
              {
                localObject1 = localObject2;
              }
              ((ArrayList)localObject1).add(localObject4);
              ((ReceiverRecord)localObject4).broadcasting = true;
              break label495;
            }
            if (i != 0)
            {
              switch (k)
              {
              default: 
                localObject1 = "unknown reason";
                break;
              case -1: 
                localObject1 = "type";
                break;
              case -2: 
                localObject1 = "data";
                break;
              case -3: 
                localObject1 = "action";
                break;
              case -4: 
                localObject1 = "category";
              }
              localObject4 = new java/lang/StringBuilder;
              ((StringBuilder)localObject4).<init>();
              Log.v("LocalBroadcastManager", "  Filter did not match: " + (String)localObject1);
            }
          }
          localObject1 = localObject2;
          label495:
          j++;
          localObject2 = localObject1;
        }
        if (localObject2 != null)
        {
          for (i = 0; i < ((ArrayList)localObject2).size(); i++) {
            ((ReceiverRecord)((ArrayList)localObject2).get(i)).broadcasting = false;
          }
          localObject3 = this.mPendingBroadcasts;
          localObject1 = new androidx/localbroadcastmanager/content/LocalBroadcastManager$BroadcastRecord;
          ((BroadcastRecord)localObject1).<init>(paramIntent, (ArrayList)localObject2);
          ((ArrayList)localObject3).add(localObject1);
          if (!this.mHandler.hasMessages(1)) {
            this.mHandler.sendEmptyMessage(1);
          }
          return true;
        }
      }
      return false;
    }
  }
  
  public void sendBroadcastSync(Intent paramIntent)
  {
    if (sendBroadcast(paramIntent)) {
      executePendingBroadcasts();
    }
  }
  
  public void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    synchronized (this.mReceivers)
    {
      ArrayList localArrayList2 = (ArrayList)this.mReceivers.remove(paramBroadcastReceiver);
      if (localArrayList2 == null) {
        return;
      }
      for (int i = localArrayList2.size() - 1; i >= 0; i--)
      {
        ReceiverRecord localReceiverRecord2 = (ReceiverRecord)localArrayList2.get(i);
        localReceiverRecord2.dead = true;
        for (int j = 0; j < localReceiverRecord2.filter.countActions(); j++)
        {
          String str = localReceiverRecord2.filter.getAction(j);
          ArrayList localArrayList1 = (ArrayList)this.mActions.get(str);
          if (localArrayList1 != null)
          {
            for (int k = localArrayList1.size() - 1; k >= 0; k--)
            {
              ReceiverRecord localReceiverRecord1 = (ReceiverRecord)localArrayList1.get(k);
              if (localReceiverRecord1.receiver == paramBroadcastReceiver)
              {
                localReceiverRecord1.dead = true;
                localArrayList1.remove(k);
              }
            }
            if (localArrayList1.size() <= 0) {
              this.mActions.remove(str);
            }
          }
        }
      }
      return;
    }
  }
  
  private static final class BroadcastRecord
  {
    final Intent intent;
    final ArrayList<LocalBroadcastManager.ReceiverRecord> receivers;
    
    BroadcastRecord(Intent paramIntent, ArrayList<LocalBroadcastManager.ReceiverRecord> paramArrayList)
    {
      this.intent = paramIntent;
      this.receivers = paramArrayList;
    }
  }
  
  private static final class ReceiverRecord
  {
    boolean broadcasting;
    boolean dead;
    final IntentFilter filter;
    final BroadcastReceiver receiver;
    
    ReceiverRecord(IntentFilter paramIntentFilter, BroadcastReceiver paramBroadcastReceiver)
    {
      this.filter = paramIntentFilter;
      this.receiver = paramBroadcastReceiver;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(128);
      localStringBuilder.append("Receiver{");
      localStringBuilder.append(this.receiver);
      localStringBuilder.append(" filter=");
      localStringBuilder.append(this.filter);
      if (this.dead) {
        localStringBuilder.append(" DEAD");
      }
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/localbroadcastmanager/content/LocalBroadcastManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */