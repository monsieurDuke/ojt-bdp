package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityChooserModel
  extends DataSetObservable
{
  static final String ATTRIBUTE_ACTIVITY = "activity";
  static final String ATTRIBUTE_TIME = "time";
  static final String ATTRIBUTE_WEIGHT = "weight";
  static final boolean DEBUG = false;
  private static final int DEFAULT_ACTIVITY_INFLATION = 5;
  private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0F;
  public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
  public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
  private static final String HISTORY_FILE_EXTENSION = ".xml";
  private static final int INVALID_INDEX = -1;
  static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
  static final String TAG_HISTORICAL_RECORD = "historical-record";
  static final String TAG_HISTORICAL_RECORDS = "historical-records";
  private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
  private static final Object sRegistryLock = new Object();
  private final List<ActivityResolveInfo> mActivities = new ArrayList();
  private OnChooseActivityListener mActivityChoserModelPolicy;
  private ActivitySorter mActivitySorter = new DefaultSorter();
  boolean mCanReadHistoricalData = true;
  final Context mContext;
  private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
  private boolean mHistoricalRecordsChanged = true;
  final String mHistoryFileName;
  private int mHistoryMaxSize = 50;
  private final Object mInstanceLock = new Object();
  private Intent mIntent;
  private boolean mReadShareHistoryCalled = false;
  private boolean mReloadActivities = false;
  
  private ActivityChooserModel(Context paramContext, String paramString)
  {
    this.mContext = paramContext.getApplicationContext();
    if ((!TextUtils.isEmpty(paramString)) && (!paramString.endsWith(".xml"))) {
      this.mHistoryFileName = (paramString + ".xml");
    } else {
      this.mHistoryFileName = paramString;
    }
  }
  
  private boolean addHistoricalRecord(HistoricalRecord paramHistoricalRecord)
  {
    boolean bool = this.mHistoricalRecords.add(paramHistoricalRecord);
    if (bool)
    {
      this.mHistoricalRecordsChanged = true;
      pruneExcessiveHistoricalRecordsIfNeeded();
      persistHistoricalDataIfNeeded();
      sortActivitiesIfNeeded();
      notifyChanged();
    }
    return bool;
  }
  
  private void ensureConsistentState()
  {
    boolean bool2 = loadActivitiesIfNeeded();
    boolean bool1 = readHistoricalDataIfNeeded();
    pruneExcessiveHistoricalRecordsIfNeeded();
    if ((bool2 | bool1))
    {
      sortActivitiesIfNeeded();
      notifyChanged();
    }
  }
  
  public static ActivityChooserModel get(Context paramContext, String paramString)
  {
    synchronized (sRegistryLock)
    {
      Map localMap = sDataModelRegistry;
      ActivityChooserModel localActivityChooserModel2 = (ActivityChooserModel)localMap.get(paramString);
      ActivityChooserModel localActivityChooserModel1 = localActivityChooserModel2;
      if (localActivityChooserModel2 == null)
      {
        localActivityChooserModel1 = new androidx/appcompat/widget/ActivityChooserModel;
        localActivityChooserModel1.<init>(paramContext, paramString);
        localMap.put(paramString, localActivityChooserModel1);
      }
      return localActivityChooserModel1;
    }
  }
  
  private boolean loadActivitiesIfNeeded()
  {
    if ((this.mReloadActivities) && (this.mIntent != null))
    {
      this.mReloadActivities = false;
      this.mActivities.clear();
      List localList = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
      int j = localList.size();
      for (int i = 0; i < j; i++)
      {
        ResolveInfo localResolveInfo = (ResolveInfo)localList.get(i);
        this.mActivities.add(new ActivityResolveInfo(localResolveInfo));
      }
      return true;
    }
    return false;
  }
  
  private void persistHistoricalDataIfNeeded()
  {
    if (this.mReadShareHistoryCalled)
    {
      if (!this.mHistoricalRecordsChanged) {
        return;
      }
      this.mHistoricalRecordsChanged = false;
      if (!TextUtils.isEmpty(this.mHistoryFileName)) {
        new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[] { new ArrayList(this.mHistoricalRecords), this.mHistoryFileName });
      }
      return;
    }
    throw new IllegalStateException("No preceding call to #readHistoricalData");
  }
  
  private void pruneExcessiveHistoricalRecordsIfNeeded()
  {
    int j = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
    if (j <= 0) {
      return;
    }
    this.mHistoricalRecordsChanged = true;
    for (int i = 0; i < j; i++) {
      HistoricalRecord localHistoricalRecord = (HistoricalRecord)this.mHistoricalRecords.remove(0);
    }
  }
  
  private boolean readHistoricalDataIfNeeded()
  {
    if ((this.mCanReadHistoricalData) && (this.mHistoricalRecordsChanged) && (!TextUtils.isEmpty(this.mHistoryFileName)))
    {
      this.mCanReadHistoricalData = false;
      this.mReadShareHistoryCalled = true;
      readHistoricalDataImpl();
      return true;
    }
    return false;
  }
  
  /* Error */
  private void readHistoricalDataImpl()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 141	androidx/appcompat/widget/ActivityChooserModel:mContext	Landroid/content/Context;
    //   4: aload_0
    //   5: getfield 165	androidx/appcompat/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
    //   8: invokevirtual 272	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   11: astore 5
    //   13: invokestatic 278	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   16: astore 9
    //   18: aload 9
    //   20: aload 5
    //   22: ldc_w 280
    //   25: invokeinterface 286 3 0
    //   30: iconst_0
    //   31: istore_2
    //   32: iload_2
    //   33: iconst_1
    //   34: if_icmpeq +19 -> 53
    //   37: iload_2
    //   38: iconst_2
    //   39: if_icmpeq +14 -> 53
    //   42: aload 9
    //   44: invokeinterface 289 1 0
    //   49: istore_2
    //   50: goto -18 -> 32
    //   53: ldc 61
    //   55: aload 9
    //   57: invokeinterface 292 1 0
    //   62: invokevirtual 295	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   65: ifeq +153 -> 218
    //   68: aload_0
    //   69: getfield 120	androidx/appcompat/widget/ActivityChooserModel:mHistoricalRecords	Ljava/util/List;
    //   72: astore 8
    //   74: aload 8
    //   76: invokeinterface 211 1 0
    //   81: aload 9
    //   83: invokeinterface 289 1 0
    //   88: istore_2
    //   89: iload_2
    //   90: iconst_1
    //   91: if_icmpne +16 -> 107
    //   94: aload 5
    //   96: ifnull +260 -> 356
    //   99: aload 5
    //   101: invokevirtual 300	java/io/FileInputStream:close	()V
    //   104: goto +244 -> 348
    //   107: iload_2
    //   108: iconst_3
    //   109: if_icmpeq -28 -> 81
    //   112: iload_2
    //   113: iconst_4
    //   114: if_icmpne +6 -> 120
    //   117: goto -36 -> 81
    //   120: ldc 58
    //   122: aload 9
    //   124: invokeinterface 292 1 0
    //   129: invokevirtual 295	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   132: ifeq +70 -> 202
    //   135: aload 9
    //   137: aconst_null
    //   138: ldc 29
    //   140: invokeinterface 304 3 0
    //   145: astore 6
    //   147: aload 9
    //   149: aconst_null
    //   150: ldc 32
    //   152: invokeinterface 304 3 0
    //   157: invokestatic 310	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   160: lstore_3
    //   161: aload 9
    //   163: aconst_null
    //   164: ldc 35
    //   166: invokeinterface 304 3 0
    //   171: invokestatic 316	java/lang/Float:parseFloat	(Ljava/lang/String;)F
    //   174: fstore_1
    //   175: new 18	androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord
    //   178: astore 7
    //   180: aload 7
    //   182: aload 6
    //   184: lload_3
    //   185: fload_1
    //   186: invokespecial 319	androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord:<init>	(Ljava/lang/String;JF)V
    //   189: aload 8
    //   191: aload 7
    //   193: invokeinterface 173 2 0
    //   198: pop
    //   199: goto -118 -> 81
    //   202: new 266	org/xmlpull/v1/XmlPullParserException
    //   205: astore 6
    //   207: aload 6
    //   209: ldc_w 321
    //   212: invokespecial 322	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   215: aload 6
    //   217: athrow
    //   218: new 266	org/xmlpull/v1/XmlPullParserException
    //   221: astore 6
    //   223: aload 6
    //   225: ldc_w 324
    //   228: invokespecial 322	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   231: aload 6
    //   233: athrow
    //   234: astore 6
    //   236: goto +121 -> 357
    //   239: astore 8
    //   241: getstatic 96	androidx/appcompat/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
    //   244: astore 6
    //   246: new 155	java/lang/StringBuilder
    //   249: astore 7
    //   251: aload 7
    //   253: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   256: aload 6
    //   258: aload 7
    //   260: ldc_w 326
    //   263: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   266: aload_0
    //   267: getfield 165	androidx/appcompat/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
    //   270: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   273: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   276: aload 8
    //   278: invokestatic 332	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   281: pop
    //   282: aload 5
    //   284: ifnull +72 -> 356
    //   287: aload 5
    //   289: invokevirtual 300	java/io/FileInputStream:close	()V
    //   292: goto +56 -> 348
    //   295: astore 7
    //   297: getstatic 96	androidx/appcompat/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
    //   300: astore 8
    //   302: new 155	java/lang/StringBuilder
    //   305: astore 6
    //   307: aload 6
    //   309: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   312: aload 8
    //   314: aload 6
    //   316: ldc_w 326
    //   319: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: aload_0
    //   323: getfield 165	androidx/appcompat/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
    //   326: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   329: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   332: aload 7
    //   334: invokestatic 332	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   337: pop
    //   338: aload 5
    //   340: ifnull +16 -> 356
    //   343: aload 5
    //   345: invokevirtual 300	java/io/FileInputStream:close	()V
    //   348: goto +8 -> 356
    //   351: astore 5
    //   353: goto -5 -> 348
    //   356: return
    //   357: aload 5
    //   359: ifnull +13 -> 372
    //   362: aload 5
    //   364: invokevirtual 300	java/io/FileInputStream:close	()V
    //   367: goto +5 -> 372
    //   370: astore 5
    //   372: aload 6
    //   374: athrow
    //   375: astore 5
    //   377: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	378	0	this	ActivityChooserModel
    //   174	12	1	f	float
    //   31	84	2	i	int
    //   160	25	3	l	long
    //   11	333	5	localFileInputStream	java.io.FileInputStream
    //   351	12	5	localIOException1	java.io.IOException
    //   370	1	5	localIOException2	java.io.IOException
    //   375	1	5	localFileNotFoundException	java.io.FileNotFoundException
    //   145	87	6	localObject1	Object
    //   234	1	6	localObject2	Object
    //   244	129	6	localObject3	Object
    //   178	81	7	localObject4	Object
    //   295	38	7	localXmlPullParserException	org.xmlpull.v1.XmlPullParserException
    //   72	118	8	localList	List
    //   239	38	8	localIOException3	java.io.IOException
    //   300	13	8	str	String
    //   16	146	9	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    // Exception table:
    //   from	to	target	type
    //   13	30	234	finally
    //   42	50	234	finally
    //   53	81	234	finally
    //   81	89	234	finally
    //   120	199	234	finally
    //   202	218	234	finally
    //   218	234	234	finally
    //   241	282	234	finally
    //   297	338	234	finally
    //   13	30	239	java/io/IOException
    //   42	50	239	java/io/IOException
    //   53	81	239	java/io/IOException
    //   81	89	239	java/io/IOException
    //   120	199	239	java/io/IOException
    //   202	218	239	java/io/IOException
    //   218	234	239	java/io/IOException
    //   13	30	295	org/xmlpull/v1/XmlPullParserException
    //   42	50	295	org/xmlpull/v1/XmlPullParserException
    //   53	81	295	org/xmlpull/v1/XmlPullParserException
    //   81	89	295	org/xmlpull/v1/XmlPullParserException
    //   120	199	295	org/xmlpull/v1/XmlPullParserException
    //   202	218	295	org/xmlpull/v1/XmlPullParserException
    //   218	234	295	org/xmlpull/v1/XmlPullParserException
    //   99	104	351	java/io/IOException
    //   287	292	351	java/io/IOException
    //   343	348	351	java/io/IOException
    //   362	367	370	java/io/IOException
    //   0	13	375	java/io/FileNotFoundException
  }
  
  private boolean sortActivitiesIfNeeded()
  {
    if ((this.mActivitySorter != null) && (this.mIntent != null) && (!this.mActivities.isEmpty()) && (!this.mHistoricalRecords.isEmpty()))
    {
      this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
      return true;
    }
    return false;
  }
  
  public Intent chooseActivity(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mIntent == null) {
        return null;
      }
      ensureConsistentState();
      Object localObject3 = (ActivityResolveInfo)this.mActivities.get(paramInt);
      ComponentName localComponentName = new android/content/ComponentName;
      localComponentName.<init>(((ActivityResolveInfo)localObject3).resolveInfo.activityInfo.packageName, ((ActivityResolveInfo)localObject3).resolveInfo.activityInfo.name);
      localObject3 = new android/content/Intent;
      ((Intent)localObject3).<init>(this.mIntent);
      ((Intent)localObject3).setComponent(localComponentName);
      if (this.mActivityChoserModelPolicy != null)
      {
        localObject4 = new android/content/Intent;
        ((Intent)localObject4).<init>((Intent)localObject3);
        if (this.mActivityChoserModelPolicy.onChooseActivity(this, (Intent)localObject4)) {
          return null;
        }
      }
      Object localObject4 = new androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord;
      ((HistoricalRecord)localObject4).<init>(localComponentName, System.currentTimeMillis(), 1.0F);
      addHistoricalRecord((HistoricalRecord)localObject4);
      return (Intent)localObject3;
    }
  }
  
  public ResolveInfo getActivity(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.mActivities.get(paramInt)).resolveInfo;
      return localResolveInfo;
    }
  }
  
  public int getActivityCount()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      int i = this.mActivities.size();
      return i;
    }
  }
  
  public int getActivityIndex(ResolveInfo paramResolveInfo)
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      List localList = this.mActivities;
      int j = localList.size();
      for (int i = 0; i < j; i++) {
        if (((ActivityResolveInfo)localList.get(i)).resolveInfo == paramResolveInfo) {
          return i;
        }
      }
      return -1;
    }
  }
  
  public ResolveInfo getDefaultActivity()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      if (!this.mActivities.isEmpty())
      {
        ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.mActivities.get(0)).resolveInfo;
        return localResolveInfo;
      }
      return null;
    }
  }
  
  public int getHistoryMaxSize()
  {
    synchronized (this.mInstanceLock)
    {
      int i = this.mHistoryMaxSize;
      return i;
    }
  }
  
  public int getHistorySize()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      int i = this.mHistoricalRecords.size();
      return i;
    }
  }
  
  public Intent getIntent()
  {
    synchronized (this.mInstanceLock)
    {
      Intent localIntent = this.mIntent;
      return localIntent;
    }
  }
  
  public void setActivitySorter(ActivitySorter paramActivitySorter)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mActivitySorter == paramActivitySorter) {
        return;
      }
      this.mActivitySorter = paramActivitySorter;
      if (sortActivitiesIfNeeded()) {
        notifyChanged();
      }
      return;
    }
  }
  
  public void setDefaultActivity(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      Object localObject2 = (ActivityResolveInfo)this.mActivities.get(paramInt);
      Object localObject4 = (ActivityResolveInfo)this.mActivities.get(0);
      float f;
      if (localObject4 != null) {
        f = ((ActivityResolveInfo)localObject4).weight - ((ActivityResolveInfo)localObject2).weight + 5.0F;
      } else {
        f = 1.0F;
      }
      localObject4 = new android/content/ComponentName;
      ((ComponentName)localObject4).<init>(((ActivityResolveInfo)localObject2).resolveInfo.activityInfo.packageName, ((ActivityResolveInfo)localObject2).resolveInfo.activityInfo.name);
      localObject2 = new androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord;
      ((HistoricalRecord)localObject2).<init>((ComponentName)localObject4, System.currentTimeMillis(), f);
      addHistoricalRecord((HistoricalRecord)localObject2);
      return;
    }
  }
  
  public void setHistoryMaxSize(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mHistoryMaxSize == paramInt) {
        return;
      }
      this.mHistoryMaxSize = paramInt;
      pruneExcessiveHistoricalRecordsIfNeeded();
      if (sortActivitiesIfNeeded()) {
        notifyChanged();
      }
      return;
    }
  }
  
  public void setIntent(Intent paramIntent)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mIntent == paramIntent) {
        return;
      }
      this.mIntent = paramIntent;
      this.mReloadActivities = true;
      ensureConsistentState();
      return;
    }
  }
  
  public void setOnChooseActivityListener(OnChooseActivityListener paramOnChooseActivityListener)
  {
    synchronized (this.mInstanceLock)
    {
      this.mActivityChoserModelPolicy = paramOnChooseActivityListener;
      return;
    }
  }
  
  public static abstract interface ActivityChooserModelClient
  {
    public abstract void setActivityChooserModel(ActivityChooserModel paramActivityChooserModel);
  }
  
  public static final class ActivityResolveInfo
    implements Comparable<ActivityResolveInfo>
  {
    public final ResolveInfo resolveInfo;
    public float weight;
    
    public ActivityResolveInfo(ResolveInfo paramResolveInfo)
    {
      this.resolveInfo = paramResolveInfo;
    }
    
    public int compareTo(ActivityResolveInfo paramActivityResolveInfo)
    {
      return Float.floatToIntBits(paramActivityResolveInfo.weight) - Float.floatToIntBits(this.weight);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (ActivityResolveInfo)paramObject;
      return Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo)paramObject).weight);
    }
    
    public int hashCode()
    {
      return Float.floatToIntBits(this.weight) + 31;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("resolveInfo:").append(this.resolveInfo.toString());
      localStringBuilder.append("; weight:").append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static abstract interface ActivitySorter
  {
    public abstract void sort(Intent paramIntent, List<ActivityChooserModel.ActivityResolveInfo> paramList, List<ActivityChooserModel.HistoricalRecord> paramList1);
  }
  
  private static final class DefaultSorter
    implements ActivityChooserModel.ActivitySorter
  {
    private static final float WEIGHT_DECAY_COEFFICIENT = 0.95F;
    private final Map<ComponentName, ActivityChooserModel.ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();
    
    public void sort(Intent paramIntent, List<ActivityChooserModel.ActivityResolveInfo> paramList, List<ActivityChooserModel.HistoricalRecord> paramList1)
    {
      paramIntent = this.mPackageNameToActivityMap;
      paramIntent.clear();
      int j = paramList.size();
      Object localObject;
      for (int i = 0; i < j; i++)
      {
        localObject = (ActivityChooserModel.ActivityResolveInfo)paramList.get(i);
        ((ActivityChooserModel.ActivityResolveInfo)localObject).weight = 0.0F;
        paramIntent.put(new ComponentName(((ActivityChooserModel.ActivityResolveInfo)localObject).resolveInfo.activityInfo.packageName, ((ActivityChooserModel.ActivityResolveInfo)localObject).resolveInfo.activityInfo.name), localObject);
      }
      i = paramList1.size();
      float f2 = 1.0F;
      i--;
      while (i >= 0)
      {
        localObject = (ActivityChooserModel.HistoricalRecord)paramList1.get(i);
        ActivityChooserModel.ActivityResolveInfo localActivityResolveInfo = (ActivityChooserModel.ActivityResolveInfo)paramIntent.get(((ActivityChooserModel.HistoricalRecord)localObject).activity);
        float f1 = f2;
        if (localActivityResolveInfo != null)
        {
          localActivityResolveInfo.weight += ((ActivityChooserModel.HistoricalRecord)localObject).weight * f2;
          f1 = f2 * 0.95F;
        }
        i--;
        f2 = f1;
      }
      Collections.sort(paramList);
    }
  }
  
  public static final class HistoricalRecord
  {
    public final ComponentName activity;
    public final long time;
    public final float weight;
    
    public HistoricalRecord(ComponentName paramComponentName, long paramLong, float paramFloat)
    {
      this.activity = paramComponentName;
      this.time = paramLong;
      this.weight = paramFloat;
    }
    
    public HistoricalRecord(String paramString, long paramLong, float paramFloat)
    {
      this(ComponentName.unflattenFromString(paramString), paramLong, paramFloat);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      HistoricalRecord localHistoricalRecord = (HistoricalRecord)paramObject;
      paramObject = this.activity;
      if (paramObject == null)
      {
        if (localHistoricalRecord.activity != null) {
          return false;
        }
      }
      else if (!((ComponentName)paramObject).equals(localHistoricalRecord.activity)) {
        return false;
      }
      if (this.time != localHistoricalRecord.time) {
        return false;
      }
      return Float.floatToIntBits(this.weight) == Float.floatToIntBits(localHistoricalRecord.weight);
    }
    
    public int hashCode()
    {
      ComponentName localComponentName = this.activity;
      int i;
      if (localComponentName == null) {
        i = 0;
      } else {
        i = localComponentName.hashCode();
      }
      long l = this.time;
      return ((1 * 31 + i) * 31 + (int)(l ^ l >>> 32)) * 31 + Float.floatToIntBits(this.weight);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("; activity:").append(this.activity);
      localStringBuilder.append("; time:").append(this.time);
      localStringBuilder.append("; weight:").append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static abstract interface OnChooseActivityListener
  {
    public abstract boolean onChooseActivity(ActivityChooserModel paramActivityChooserModel, Intent paramIntent);
  }
  
  private final class PersistHistoryAsyncTask
    extends AsyncTask<Object, Void, Void>
  {
    PersistHistoryAsyncTask() {}
    
    /* Error */
    public Void doInBackground(Object... paramVarArgs)
    {
      // Byte code:
      //   0: aload_1
      //   1: iconst_0
      //   2: aaload
      //   3: checkcast 33	java/util/List
      //   6: astore 4
      //   8: aload_1
      //   9: iconst_1
      //   10: aaload
      //   11: checkcast 35	java/lang/String
      //   14: astore_1
      //   15: aload_0
      //   16: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   19: getfield 39	androidx/appcompat/widget/ActivityChooserModel:mContext	Landroid/content/Context;
      //   22: aload_1
      //   23: iconst_0
      //   24: invokevirtual 45	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
      //   27: astore 6
      //   29: invokestatic 51	android/util/Xml:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
      //   32: astore 7
      //   34: aload 4
      //   36: astore 5
      //   38: aload 4
      //   40: astore 5
      //   42: aload 4
      //   44: astore 5
      //   46: aload 4
      //   48: astore 5
      //   50: aload 7
      //   52: aload 6
      //   54: aconst_null
      //   55: invokeinterface 57 3 0
      //   60: aload 4
      //   62: astore 5
      //   64: aload 4
      //   66: astore 5
      //   68: aload 4
      //   70: astore 5
      //   72: aload 4
      //   74: astore 5
      //   76: aload 7
      //   78: ldc 59
      //   80: iconst_1
      //   81: invokestatic 65	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   84: invokeinterface 69 3 0
      //   89: aload 4
      //   91: astore 5
      //   93: aload 4
      //   95: astore 5
      //   97: aload 4
      //   99: astore 5
      //   101: aload 4
      //   103: astore 5
      //   105: aload 7
      //   107: aconst_null
      //   108: ldc 71
      //   110: invokeinterface 75 3 0
      //   115: pop
      //   116: aload 4
      //   118: astore 5
      //   120: aload 4
      //   122: astore 5
      //   124: aload 4
      //   126: astore 5
      //   128: aload 4
      //   130: astore 5
      //   132: aload 4
      //   134: invokeinterface 79 1 0
      //   139: istore_3
      //   140: iconst_0
      //   141: istore_2
      //   142: aload 4
      //   144: astore_1
      //   145: iload_2
      //   146: iload_3
      //   147: if_icmpge +174 -> 321
      //   150: aload_1
      //   151: astore 5
      //   153: aload_1
      //   154: astore 5
      //   156: aload_1
      //   157: astore 5
      //   159: aload_1
      //   160: astore 5
      //   162: aload_1
      //   163: iconst_0
      //   164: invokeinterface 83 2 0
      //   169: checkcast 85	androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord
      //   172: astore 4
      //   174: aload_1
      //   175: astore 5
      //   177: aload_1
      //   178: astore 5
      //   180: aload_1
      //   181: astore 5
      //   183: aload_1
      //   184: astore 5
      //   186: aload 7
      //   188: aconst_null
      //   189: ldc 87
      //   191: invokeinterface 75 3 0
      //   196: pop
      //   197: aload_1
      //   198: astore 5
      //   200: aload_1
      //   201: astore 5
      //   203: aload_1
      //   204: astore 5
      //   206: aload_1
      //   207: astore 5
      //   209: aload 7
      //   211: aconst_null
      //   212: ldc 89
      //   214: aload 4
      //   216: getfield 92	androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord:activity	Landroid/content/ComponentName;
      //   219: invokevirtual 98	android/content/ComponentName:flattenToString	()Ljava/lang/String;
      //   222: invokeinterface 102 4 0
      //   227: pop
      //   228: aload 4
      //   230: getfield 106	androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord:time	J
      //   233: invokestatic 109	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   236: astore 5
      //   238: aload 5
      //   240: invokestatic 115	mt/Log5ECF72:a	(Ljava/lang/Object;)V
      //   243: aload 5
      //   245: invokestatic 118	mt/LogE84000:a	(Ljava/lang/Object;)V
      //   248: aload 5
      //   250: invokestatic 121	mt/Log229316:a	(Ljava/lang/Object;)V
      //   253: aload 7
      //   255: aconst_null
      //   256: ldc 122
      //   258: aload 5
      //   260: invokeinterface 102 4 0
      //   265: pop
      //   266: aload 4
      //   268: getfield 126	androidx/appcompat/widget/ActivityChooserModel$HistoricalRecord:weight	F
      //   271: invokestatic 129	java/lang/String:valueOf	(F)Ljava/lang/String;
      //   274: astore 4
      //   276: aload 4
      //   278: invokestatic 115	mt/Log5ECF72:a	(Ljava/lang/Object;)V
      //   281: aload 4
      //   283: invokestatic 118	mt/LogE84000:a	(Ljava/lang/Object;)V
      //   286: aload 4
      //   288: invokestatic 121	mt/Log229316:a	(Ljava/lang/Object;)V
      //   291: aload 7
      //   293: aconst_null
      //   294: ldc -126
      //   296: aload 4
      //   298: invokeinterface 102 4 0
      //   303: pop
      //   304: aload 7
      //   306: aconst_null
      //   307: ldc 87
      //   309: invokeinterface 133 3 0
      //   314: pop
      //   315: iinc 2 1
      //   318: goto -173 -> 145
      //   321: aload 7
      //   323: aconst_null
      //   324: ldc 71
      //   326: invokeinterface 133 3 0
      //   331: pop
      //   332: aload 7
      //   334: invokeinterface 136 1 0
      //   339: aload_0
      //   340: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   343: iconst_1
      //   344: putfield 140	androidx/appcompat/widget/ActivityChooserModel:mCanReadHistoricalData	Z
      //   347: aload 6
      //   349: ifnull +223 -> 572
      //   352: aload 6
      //   354: invokevirtual 145	java/io/FileOutputStream:close	()V
      //   357: goto +208 -> 565
      //   360: astore_1
      //   361: goto +16 -> 377
      //   364: astore_1
      //   365: goto +76 -> 441
      //   368: astore_1
      //   369: goto +136 -> 505
      //   372: astore_1
      //   373: goto +202 -> 575
      //   376: astore_1
      //   377: getstatic 149	androidx/appcompat/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
      //   380: astore 5
      //   382: new 151	java/lang/StringBuilder
      //   385: astore 4
      //   387: aload 4
      //   389: invokespecial 152	java/lang/StringBuilder:<init>	()V
      //   392: aload 5
      //   394: aload 4
      //   396: ldc -102
      //   398: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   401: aload_0
      //   402: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   405: getfield 161	androidx/appcompat/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
      //   408: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   411: invokevirtual 164	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   414: aload_1
      //   415: invokestatic 170	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   418: pop
      //   419: aload_0
      //   420: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   423: iconst_1
      //   424: putfield 140	androidx/appcompat/widget/ActivityChooserModel:mCanReadHistoricalData	Z
      //   427: aload 6
      //   429: ifnull +143 -> 572
      //   432: aload 6
      //   434: invokevirtual 145	java/io/FileOutputStream:close	()V
      //   437: goto +128 -> 565
      //   440: astore_1
      //   441: getstatic 149	androidx/appcompat/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
      //   444: astore 5
      //   446: new 151	java/lang/StringBuilder
      //   449: astore 4
      //   451: aload 4
      //   453: invokespecial 152	java/lang/StringBuilder:<init>	()V
      //   456: aload 5
      //   458: aload 4
      //   460: ldc -102
      //   462: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   465: aload_0
      //   466: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   469: getfield 161	androidx/appcompat/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
      //   472: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   475: invokevirtual 164	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   478: aload_1
      //   479: invokestatic 170	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   482: pop
      //   483: aload_0
      //   484: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   487: iconst_1
      //   488: putfield 140	androidx/appcompat/widget/ActivityChooserModel:mCanReadHistoricalData	Z
      //   491: aload 6
      //   493: ifnull +79 -> 572
      //   496: aload 6
      //   498: invokevirtual 145	java/io/FileOutputStream:close	()V
      //   501: goto +64 -> 565
      //   504: astore_1
      //   505: getstatic 149	androidx/appcompat/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
      //   508: astore 4
      //   510: new 151	java/lang/StringBuilder
      //   513: astore 5
      //   515: aload 5
      //   517: invokespecial 152	java/lang/StringBuilder:<init>	()V
      //   520: aload 4
      //   522: aload 5
      //   524: ldc -102
      //   526: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   529: aload_0
      //   530: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   533: getfield 161	androidx/appcompat/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
      //   536: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   539: invokevirtual 164	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   542: aload_1
      //   543: invokestatic 170	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   546: pop
      //   547: aload_0
      //   548: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   551: iconst_1
      //   552: putfield 140	androidx/appcompat/widget/ActivityChooserModel:mCanReadHistoricalData	Z
      //   555: aload 6
      //   557: ifnull +15 -> 572
      //   560: aload 6
      //   562: invokevirtual 145	java/io/FileOutputStream:close	()V
      //   565: goto +7 -> 572
      //   568: astore_1
      //   569: goto -4 -> 565
      //   572: aconst_null
      //   573: areturn
      //   574: astore_1
      //   575: aload_0
      //   576: getfield 14	androidx/appcompat/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroidx/appcompat/widget/ActivityChooserModel;
      //   579: iconst_1
      //   580: putfield 140	androidx/appcompat/widget/ActivityChooserModel:mCanReadHistoricalData	Z
      //   583: aload 6
      //   585: ifnull +13 -> 598
      //   588: aload 6
      //   590: invokevirtual 145	java/io/FileOutputStream:close	()V
      //   593: goto +5 -> 598
      //   596: astore 4
      //   598: aload_1
      //   599: athrow
      //   600: astore 4
      //   602: getstatic 149	androidx/appcompat/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
      //   605: new 151	java/lang/StringBuilder
      //   608: dup
      //   609: invokespecial 152	java/lang/StringBuilder:<init>	()V
      //   612: ldc -102
      //   614: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   617: aload_1
      //   618: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   621: invokevirtual 164	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   624: aload 4
      //   626: invokestatic 170	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   629: pop
      //   630: aconst_null
      //   631: areturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	632	0	this	PersistHistoryAsyncTask
      //   0	632	1	paramVarArgs	Object[]
      //   141	175	2	i	int
      //   139	9	3	j	int
      //   6	515	4	localObject1	Object
      //   596	1	4	localIOException	java.io.IOException
      //   600	25	4	localFileNotFoundException	java.io.FileNotFoundException
      //   36	487	5	localObject2	Object
      //   27	562	6	localFileOutputStream	java.io.FileOutputStream
      //   32	301	7	localXmlSerializer	org.xmlpull.v1.XmlSerializer
      // Exception table:
      //   from	to	target	type
      //   228	238	360	java/io/IOException
      //   253	276	360	java/io/IOException
      //   291	315	360	java/io/IOException
      //   321	339	360	java/io/IOException
      //   228	238	364	java/lang/IllegalStateException
      //   253	276	364	java/lang/IllegalStateException
      //   291	315	364	java/lang/IllegalStateException
      //   321	339	364	java/lang/IllegalStateException
      //   228	238	368	java/lang/IllegalArgumentException
      //   253	276	368	java/lang/IllegalArgumentException
      //   291	315	368	java/lang/IllegalArgumentException
      //   321	339	368	java/lang/IllegalArgumentException
      //   50	60	372	finally
      //   76	89	372	finally
      //   105	116	372	finally
      //   132	140	372	finally
      //   162	174	372	finally
      //   186	197	372	finally
      //   209	228	372	finally
      //   50	60	376	java/io/IOException
      //   76	89	376	java/io/IOException
      //   105	116	376	java/io/IOException
      //   132	140	376	java/io/IOException
      //   162	174	376	java/io/IOException
      //   186	197	376	java/io/IOException
      //   209	228	376	java/io/IOException
      //   50	60	440	java/lang/IllegalStateException
      //   76	89	440	java/lang/IllegalStateException
      //   105	116	440	java/lang/IllegalStateException
      //   132	140	440	java/lang/IllegalStateException
      //   162	174	440	java/lang/IllegalStateException
      //   186	197	440	java/lang/IllegalStateException
      //   209	228	440	java/lang/IllegalStateException
      //   50	60	504	java/lang/IllegalArgumentException
      //   76	89	504	java/lang/IllegalArgumentException
      //   105	116	504	java/lang/IllegalArgumentException
      //   132	140	504	java/lang/IllegalArgumentException
      //   162	174	504	java/lang/IllegalArgumentException
      //   186	197	504	java/lang/IllegalArgumentException
      //   209	228	504	java/lang/IllegalArgumentException
      //   352	357	568	java/io/IOException
      //   432	437	568	java/io/IOException
      //   496	501	568	java/io/IOException
      //   560	565	568	java/io/IOException
      //   228	238	574	finally
      //   253	276	574	finally
      //   291	315	574	finally
      //   321	339	574	finally
      //   377	419	574	finally
      //   441	483	574	finally
      //   505	547	574	finally
      //   588	593	596	java/io/IOException
      //   15	29	600	java/io/FileNotFoundException
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/ActivityChooserModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */