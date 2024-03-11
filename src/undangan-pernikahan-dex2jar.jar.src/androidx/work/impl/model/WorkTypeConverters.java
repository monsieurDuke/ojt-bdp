package androidx.work.impl.model;

import android.os.Build.VERSION;
import androidx.work.BackoffPolicy;
import androidx.work.NetworkType;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo.State;

public class WorkTypeConverters
{
  public static int backoffPolicyToInt(BackoffPolicy paramBackoffPolicy)
  {
    switch (1.$SwitchMap$androidx$work$BackoffPolicy[paramBackoffPolicy.ordinal()])
    {
    default: 
      throw new IllegalArgumentException("Could not convert " + paramBackoffPolicy + " to int");
    case 2: 
      return 1;
    }
    return 0;
  }
  
  /* Error */
  public static androidx.work.ContentUriTriggers byteArrayToContentUriTriggers(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: new 64	androidx/work/ContentUriTriggers
    //   3: dup
    //   4: invokespecial 65	androidx/work/ContentUriTriggers:<init>	()V
    //   7: astore 6
    //   9: aload_0
    //   10: ifnonnull +6 -> 16
    //   13: aload 6
    //   15: areturn
    //   16: new 67	java/io/ByteArrayInputStream
    //   19: dup
    //   20: aload_0
    //   21: invokespecial 70	java/io/ByteArrayInputStream:<init>	([B)V
    //   24: astore 7
    //   26: aconst_null
    //   27: astore 4
    //   29: aconst_null
    //   30: astore 5
    //   32: aload 5
    //   34: astore_0
    //   35: aload 4
    //   37: astore_2
    //   38: new 72	java/io/ObjectInputStream
    //   41: astore_3
    //   42: aload 5
    //   44: astore_0
    //   45: aload 4
    //   47: astore_2
    //   48: aload_3
    //   49: aload 7
    //   51: invokespecial 75	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   54: aload_3
    //   55: astore_0
    //   56: aload_3
    //   57: astore_2
    //   58: aload_3
    //   59: invokevirtual 78	java/io/ObjectInputStream:readInt	()I
    //   62: istore_1
    //   63: iload_1
    //   64: ifle +29 -> 93
    //   67: aload_3
    //   68: astore_0
    //   69: aload_3
    //   70: astore_2
    //   71: aload 6
    //   73: aload_3
    //   74: invokevirtual 81	java/io/ObjectInputStream:readUTF	()Ljava/lang/String;
    //   77: invokestatic 87	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   80: aload_3
    //   81: invokevirtual 91	java/io/ObjectInputStream:readBoolean	()Z
    //   84: invokevirtual 95	androidx/work/ContentUriTriggers:add	(Landroid/net/Uri;Z)V
    //   87: iinc 1 -1
    //   90: goto -27 -> 63
    //   93: aload_3
    //   94: invokevirtual 98	java/io/ObjectInputStream:close	()V
    //   97: goto +8 -> 105
    //   100: astore_0
    //   101: aload_0
    //   102: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   105: aload 7
    //   107: invokevirtual 102	java/io/ByteArrayInputStream:close	()V
    //   110: goto +46 -> 156
    //   113: astore_0
    //   114: aload_0
    //   115: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   118: goto +38 -> 156
    //   121: astore_2
    //   122: goto +37 -> 159
    //   125: astore_3
    //   126: aload_2
    //   127: astore_0
    //   128: aload_3
    //   129: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   132: aload_2
    //   133: ifnull +15 -> 148
    //   136: aload_2
    //   137: invokevirtual 98	java/io/ObjectInputStream:close	()V
    //   140: goto +8 -> 148
    //   143: astore_0
    //   144: aload_0
    //   145: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   148: aload 7
    //   150: invokevirtual 102	java/io/ByteArrayInputStream:close	()V
    //   153: goto -43 -> 110
    //   156: aload 6
    //   158: areturn
    //   159: aload_0
    //   160: ifnull +15 -> 175
    //   163: aload_0
    //   164: invokevirtual 98	java/io/ObjectInputStream:close	()V
    //   167: goto +8 -> 175
    //   170: astore_0
    //   171: aload_0
    //   172: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   175: aload 7
    //   177: invokevirtual 102	java/io/ByteArrayInputStream:close	()V
    //   180: goto +8 -> 188
    //   183: astore_0
    //   184: aload_0
    //   185: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   188: aload_2
    //   189: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	190	0	paramArrayOfByte	byte[]
    //   62	26	1	i	int
    //   37	34	2	localObject1	Object
    //   121	68	2	localObject2	Object
    //   41	53	3	localObjectInputStream	java.io.ObjectInputStream
    //   125	4	3	localIOException	java.io.IOException
    //   27	19	4	localObject3	Object
    //   30	13	5	localObject4	Object
    //   7	150	6	localContentUriTriggers	androidx.work.ContentUriTriggers
    //   24	152	7	localByteArrayInputStream	java.io.ByteArrayInputStream
    // Exception table:
    //   from	to	target	type
    //   93	97	100	java/io/IOException
    //   105	110	113	java/io/IOException
    //   148	153	113	java/io/IOException
    //   38	42	121	finally
    //   48	54	121	finally
    //   58	63	121	finally
    //   71	87	121	finally
    //   128	132	121	finally
    //   38	42	125	java/io/IOException
    //   48	54	125	java/io/IOException
    //   58	63	125	java/io/IOException
    //   71	87	125	java/io/IOException
    //   136	140	143	java/io/IOException
    //   163	167	170	java/io/IOException
    //   175	180	183	java/io/IOException
  }
  
  /* Error */
  public static byte[] contentUriTriggersToByteArray(androidx.work.ContentUriTriggers paramContentUriTriggers)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 107	androidx/work/ContentUriTriggers:size	()I
    //   4: ifne +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: new 109	java/io/ByteArrayOutputStream
    //   12: dup
    //   13: invokespecial 110	java/io/ByteArrayOutputStream:<init>	()V
    //   16: astore 6
    //   18: aconst_null
    //   19: astore 4
    //   21: aconst_null
    //   22: astore_3
    //   23: aload_3
    //   24: astore_1
    //   25: aload 4
    //   27: astore_2
    //   28: new 112	java/io/ObjectOutputStream
    //   31: astore 5
    //   33: aload_3
    //   34: astore_1
    //   35: aload 4
    //   37: astore_2
    //   38: aload 5
    //   40: aload 6
    //   42: invokespecial 115	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   45: aload 5
    //   47: astore_3
    //   48: aload_3
    //   49: astore_1
    //   50: aload_3
    //   51: astore_2
    //   52: aload_3
    //   53: aload_0
    //   54: invokevirtual 107	androidx/work/ContentUriTriggers:size	()I
    //   57: invokevirtual 119	java/io/ObjectOutputStream:writeInt	(I)V
    //   60: aload_3
    //   61: astore_1
    //   62: aload_3
    //   63: astore_2
    //   64: aload_0
    //   65: invokevirtual 123	androidx/work/ContentUriTriggers:getTriggers	()Ljava/util/Set;
    //   68: invokeinterface 129 1 0
    //   73: astore 4
    //   75: aload_3
    //   76: astore_1
    //   77: aload_3
    //   78: astore_2
    //   79: aload 4
    //   81: invokeinterface 134 1 0
    //   86: ifeq +48 -> 134
    //   89: aload_3
    //   90: astore_1
    //   91: aload_3
    //   92: astore_2
    //   93: aload 4
    //   95: invokeinterface 138 1 0
    //   100: checkcast 140	androidx/work/ContentUriTriggers$Trigger
    //   103: astore_0
    //   104: aload_3
    //   105: astore_1
    //   106: aload_3
    //   107: astore_2
    //   108: aload_3
    //   109: aload_0
    //   110: invokevirtual 144	androidx/work/ContentUriTriggers$Trigger:getUri	()Landroid/net/Uri;
    //   113: invokevirtual 145	android/net/Uri:toString	()Ljava/lang/String;
    //   116: invokevirtual 148	java/io/ObjectOutputStream:writeUTF	(Ljava/lang/String;)V
    //   119: aload_3
    //   120: astore_1
    //   121: aload_3
    //   122: astore_2
    //   123: aload_3
    //   124: aload_0
    //   125: invokevirtual 151	androidx/work/ContentUriTriggers$Trigger:shouldTriggerForDescendants	()Z
    //   128: invokevirtual 155	java/io/ObjectOutputStream:writeBoolean	(Z)V
    //   131: goto -56 -> 75
    //   134: aload_3
    //   135: invokevirtual 156	java/io/ObjectOutputStream:close	()V
    //   138: goto +8 -> 146
    //   141: astore_0
    //   142: aload_0
    //   143: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   146: aload 6
    //   148: invokevirtual 157	java/io/ByteArrayOutputStream:close	()V
    //   151: goto +46 -> 197
    //   154: astore_0
    //   155: aload_0
    //   156: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   159: goto +38 -> 197
    //   162: astore_0
    //   163: goto +40 -> 203
    //   166: astore_0
    //   167: aload_2
    //   168: astore_1
    //   169: aload_0
    //   170: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   173: aload_2
    //   174: ifnull +15 -> 189
    //   177: aload_2
    //   178: invokevirtual 156	java/io/ObjectOutputStream:close	()V
    //   181: goto +8 -> 189
    //   184: astore_0
    //   185: aload_0
    //   186: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   189: aload 6
    //   191: invokevirtual 157	java/io/ByteArrayOutputStream:close	()V
    //   194: goto -43 -> 151
    //   197: aload 6
    //   199: invokevirtual 161	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   202: areturn
    //   203: aload_1
    //   204: ifnull +15 -> 219
    //   207: aload_1
    //   208: invokevirtual 156	java/io/ObjectOutputStream:close	()V
    //   211: goto +8 -> 219
    //   214: astore_1
    //   215: aload_1
    //   216: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   219: aload 6
    //   221: invokevirtual 157	java/io/ByteArrayOutputStream:close	()V
    //   224: goto +8 -> 232
    //   227: astore_1
    //   228: aload_1
    //   229: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   232: aload_0
    //   233: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	234	0	paramContentUriTriggers	androidx.work.ContentUriTriggers
    //   24	184	1	localObject1	Object
    //   214	2	1	localIOException1	java.io.IOException
    //   227	2	1	localIOException2	java.io.IOException
    //   27	151	2	localObject2	Object
    //   22	113	3	localObject3	Object
    //   19	75	4	localIterator	java.util.Iterator
    //   31	15	5	localObjectOutputStream	java.io.ObjectOutputStream
    //   16	204	6	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    // Exception table:
    //   from	to	target	type
    //   134	138	141	java/io/IOException
    //   146	151	154	java/io/IOException
    //   189	194	154	java/io/IOException
    //   28	33	162	finally
    //   38	45	162	finally
    //   52	60	162	finally
    //   64	75	162	finally
    //   79	89	162	finally
    //   93	104	162	finally
    //   108	119	162	finally
    //   123	131	162	finally
    //   169	173	162	finally
    //   28	33	166	java/io/IOException
    //   38	45	166	java/io/IOException
    //   52	60	166	java/io/IOException
    //   64	75	166	java/io/IOException
    //   79	89	166	java/io/IOException
    //   93	104	166	java/io/IOException
    //   108	119	166	java/io/IOException
    //   123	131	166	java/io/IOException
    //   177	181	184	java/io/IOException
    //   207	211	214	java/io/IOException
    //   219	224	227	java/io/IOException
  }
  
  public static BackoffPolicy intToBackoffPolicy(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Could not convert " + paramInt + " to BackoffPolicy");
    case 1: 
      return BackoffPolicy.LINEAR;
    }
    return BackoffPolicy.EXPONENTIAL;
  }
  
  public static NetworkType intToNetworkType(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      if ((Build.VERSION.SDK_INT >= 30) && (paramInt == 5)) {
        return NetworkType.TEMPORARILY_UNMETERED;
      }
      break;
    case 4: 
      return NetworkType.METERED;
    case 3: 
      return NetworkType.NOT_ROAMING;
    case 2: 
      return NetworkType.UNMETERED;
    case 1: 
      return NetworkType.CONNECTED;
    case 0: 
      return NetworkType.NOT_REQUIRED;
    }
    throw new IllegalArgumentException("Could not convert " + paramInt + " to NetworkType");
  }
  
  public static OutOfQuotaPolicy intToOutOfQuotaPolicy(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Could not convert " + paramInt + " to OutOfQuotaPolicy");
    case 1: 
      return OutOfQuotaPolicy.DROP_WORK_REQUEST;
    }
    return OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST;
  }
  
  public static WorkInfo.State intToState(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Could not convert " + paramInt + " to State");
    case 5: 
      return WorkInfo.State.CANCELLED;
    case 4: 
      return WorkInfo.State.BLOCKED;
    case 3: 
      return WorkInfo.State.FAILED;
    case 2: 
      return WorkInfo.State.SUCCEEDED;
    case 1: 
      return WorkInfo.State.RUNNING;
    }
    return WorkInfo.State.ENQUEUED;
  }
  
  public static int networkTypeToInt(NetworkType paramNetworkType)
  {
    switch (1.$SwitchMap$androidx$work$NetworkType[paramNetworkType.ordinal()])
    {
    default: 
      if ((Build.VERSION.SDK_INT >= 30) && (paramNetworkType == NetworkType.TEMPORARILY_UNMETERED)) {
        return 5;
      }
      break;
    case 5: 
      return 4;
    case 4: 
      return 3;
    case 3: 
      return 2;
    case 2: 
      return 1;
    case 1: 
      return 0;
    }
    throw new IllegalArgumentException("Could not convert " + paramNetworkType + " to int");
  }
  
  public static int outOfQuotaPolicyToInt(OutOfQuotaPolicy paramOutOfQuotaPolicy)
  {
    switch (1.$SwitchMap$androidx$work$OutOfQuotaPolicy[paramOutOfQuotaPolicy.ordinal()])
    {
    default: 
      throw new IllegalArgumentException("Could not convert " + paramOutOfQuotaPolicy + " to int");
    case 2: 
      return 1;
    }
    return 0;
  }
  
  public static int stateToInt(WorkInfo.State paramState)
  {
    switch (1.$SwitchMap$androidx$work$WorkInfo$State[paramState.ordinal()])
    {
    default: 
      throw new IllegalArgumentException("Could not convert " + paramState + " to int");
    case 6: 
      return 5;
    case 5: 
      return 4;
    case 4: 
      return 3;
    case 3: 
      return 2;
    case 2: 
      return 1;
    }
    return 0;
  }
  
  public static abstract interface BackoffPolicyIds
  {
    public static final int EXPONENTIAL = 0;
    public static final int LINEAR = 1;
  }
  
  public static abstract interface NetworkTypeIds
  {
    public static final int CONNECTED = 1;
    public static final int METERED = 4;
    public static final int NOT_REQUIRED = 0;
    public static final int NOT_ROAMING = 3;
    public static final int TEMPORARILY_UNMETERED = 5;
    public static final int UNMETERED = 2;
  }
  
  public static abstract interface OutOfPolicyIds
  {
    public static final int DROP_WORK_REQUEST = 1;
    public static final int RUN_AS_NON_EXPEDITED_WORK_REQUEST = 0;
  }
  
  public static abstract interface StateIds
  {
    public static final int BLOCKED = 4;
    public static final int CANCELLED = 5;
    public static final String COMPLETED_STATES = "(2, 3, 5)";
    public static final int ENQUEUED = 0;
    public static final int FAILED = 3;
    public static final int RUNNING = 1;
    public static final int SUCCEEDED = 2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/WorkTypeConverters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */