package androidx.work;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class Data
{
  public static final Data EMPTY = new Builder().build();
  public static final int MAX_DATA_BYTES = 10240;
  private static final String TAG;
  Map<String, Object> mValues;
  
  static
  {
    String str = Logger.tagWithPrefix("Data");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  Data() {}
  
  public Data(Data paramData)
  {
    this.mValues = new HashMap(paramData.mValues);
  }
  
  public Data(Map<String, ?> paramMap)
  {
    this.mValues = new HashMap(paramMap);
  }
  
  public static Boolean[] convertPrimitiveBooleanArray(boolean[] paramArrayOfBoolean)
  {
    Boolean[] arrayOfBoolean = new Boolean[paramArrayOfBoolean.length];
    for (int i = 0; i < paramArrayOfBoolean.length; i++) {
      arrayOfBoolean[i] = Boolean.valueOf(paramArrayOfBoolean[i]);
    }
    return arrayOfBoolean;
  }
  
  public static Byte[] convertPrimitiveByteArray(byte[] paramArrayOfByte)
  {
    Byte[] arrayOfByte = new Byte[paramArrayOfByte.length];
    for (int i = 0; i < paramArrayOfByte.length; i++) {
      arrayOfByte[i] = Byte.valueOf(paramArrayOfByte[i]);
    }
    return arrayOfByte;
  }
  
  public static Double[] convertPrimitiveDoubleArray(double[] paramArrayOfDouble)
  {
    Double[] arrayOfDouble = new Double[paramArrayOfDouble.length];
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      arrayOfDouble[i] = Double.valueOf(paramArrayOfDouble[i]);
    }
    return arrayOfDouble;
  }
  
  public static Float[] convertPrimitiveFloatArray(float[] paramArrayOfFloat)
  {
    Float[] arrayOfFloat = new Float[paramArrayOfFloat.length];
    for (int i = 0; i < paramArrayOfFloat.length; i++) {
      arrayOfFloat[i] = Float.valueOf(paramArrayOfFloat[i]);
    }
    return arrayOfFloat;
  }
  
  public static Integer[] convertPrimitiveIntArray(int[] paramArrayOfInt)
  {
    Integer[] arrayOfInteger = new Integer[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      arrayOfInteger[i] = Integer.valueOf(paramArrayOfInt[i]);
    }
    return arrayOfInteger;
  }
  
  public static Long[] convertPrimitiveLongArray(long[] paramArrayOfLong)
  {
    Long[] arrayOfLong = new Long[paramArrayOfLong.length];
    for (int i = 0; i < paramArrayOfLong.length; i++) {
      arrayOfLong[i] = Long.valueOf(paramArrayOfLong[i]);
    }
    return arrayOfLong;
  }
  
  public static byte[] convertToPrimitiveArray(Byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    for (int i = 0; i < paramArrayOfByte.length; i++) {
      arrayOfByte[i] = paramArrayOfByte[i].byteValue();
    }
    return arrayOfByte;
  }
  
  public static double[] convertToPrimitiveArray(Double[] paramArrayOfDouble)
  {
    double[] arrayOfDouble = new double[paramArrayOfDouble.length];
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      arrayOfDouble[i] = paramArrayOfDouble[i].doubleValue();
    }
    return arrayOfDouble;
  }
  
  public static float[] convertToPrimitiveArray(Float[] paramArrayOfFloat)
  {
    float[] arrayOfFloat = new float[paramArrayOfFloat.length];
    for (int i = 0; i < paramArrayOfFloat.length; i++) {
      arrayOfFloat[i] = paramArrayOfFloat[i].floatValue();
    }
    return arrayOfFloat;
  }
  
  public static int[] convertToPrimitiveArray(Integer[] paramArrayOfInteger)
  {
    int[] arrayOfInt = new int[paramArrayOfInteger.length];
    for (int i = 0; i < paramArrayOfInteger.length; i++) {
      arrayOfInt[i] = paramArrayOfInteger[i].intValue();
    }
    return arrayOfInt;
  }
  
  public static long[] convertToPrimitiveArray(Long[] paramArrayOfLong)
  {
    long[] arrayOfLong = new long[paramArrayOfLong.length];
    for (int i = 0; i < paramArrayOfLong.length; i++) {
      arrayOfLong[i] = paramArrayOfLong[i].longValue();
    }
    return arrayOfLong;
  }
  
  public static boolean[] convertToPrimitiveArray(Boolean[] paramArrayOfBoolean)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean.length];
    for (int i = 0; i < paramArrayOfBoolean.length; i++) {
      arrayOfBoolean[i] = paramArrayOfBoolean[i].booleanValue();
    }
    return arrayOfBoolean;
  }
  
  /* Error */
  public static Data fromByteArray(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: arraylength
    //   2: sipush 10240
    //   5: if_icmpgt +255 -> 260
    //   8: new 55	java/util/HashMap
    //   11: dup
    //   12: invokespecial 143	java/util/HashMap:<init>	()V
    //   15: astore 9
    //   17: new 145	java/io/ByteArrayInputStream
    //   20: dup
    //   21: aload_0
    //   22: invokespecial 148	java/io/ByteArrayInputStream:<init>	([B)V
    //   25: astore 8
    //   27: aconst_null
    //   28: astore_0
    //   29: aconst_null
    //   30: astore 5
    //   32: aconst_null
    //   33: astore 7
    //   35: aload 7
    //   37: astore_2
    //   38: aload_0
    //   39: astore 4
    //   41: aload 5
    //   43: astore_3
    //   44: new 150	java/io/ObjectInputStream
    //   47: astore 6
    //   49: aload 7
    //   51: astore_2
    //   52: aload_0
    //   53: astore 4
    //   55: aload 5
    //   57: astore_3
    //   58: aload 6
    //   60: aload 8
    //   62: invokespecial 153	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   65: aload 6
    //   67: astore_0
    //   68: aload_0
    //   69: astore_2
    //   70: aload_0
    //   71: astore 4
    //   73: aload_0
    //   74: astore_3
    //   75: aload_0
    //   76: invokevirtual 156	java/io/ObjectInputStream:readInt	()I
    //   79: istore_1
    //   80: iload_1
    //   81: ifle +32 -> 113
    //   84: aload_0
    //   85: astore_2
    //   86: aload_0
    //   87: astore 4
    //   89: aload_0
    //   90: astore_3
    //   91: aload 9
    //   93: aload_0
    //   94: invokevirtual 160	java/io/ObjectInputStream:readUTF	()Ljava/lang/String;
    //   97: aload_0
    //   98: invokevirtual 164	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   101: invokeinterface 170 3 0
    //   106: pop
    //   107: iinc 1 -1
    //   110: goto -30 -> 80
    //   113: aload_0
    //   114: invokevirtual 173	java/io/ObjectInputStream:close	()V
    //   117: goto +14 -> 131
    //   120: astore_0
    //   121: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   124: ldc -81
    //   126: aload_0
    //   127: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   130: pop
    //   131: aload 8
    //   133: invokevirtual 182	java/io/ByteArrayInputStream:close	()V
    //   136: goto +71 -> 207
    //   139: astore_0
    //   140: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   143: ldc -81
    //   145: aload_0
    //   146: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   149: pop
    //   150: goto +57 -> 207
    //   153: astore_0
    //   154: goto +63 -> 217
    //   157: astore_0
    //   158: aload 4
    //   160: astore_3
    //   161: goto +4 -> 165
    //   164: astore_0
    //   165: aload_3
    //   166: astore_2
    //   167: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   170: ldc -81
    //   172: aload_0
    //   173: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   176: pop
    //   177: aload_3
    //   178: ifnull +21 -> 199
    //   181: aload_3
    //   182: invokevirtual 173	java/io/ObjectInputStream:close	()V
    //   185: goto +14 -> 199
    //   188: astore_0
    //   189: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   192: ldc -81
    //   194: aload_0
    //   195: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   198: pop
    //   199: aload 8
    //   201: invokevirtual 182	java/io/ByteArrayInputStream:close	()V
    //   204: goto -68 -> 136
    //   207: new 2	androidx/work/Data
    //   210: dup
    //   211: aload 9
    //   213: invokespecial 183	androidx/work/Data:<init>	(Ljava/util/Map;)V
    //   216: areturn
    //   217: aload_2
    //   218: ifnull +21 -> 239
    //   221: aload_2
    //   222: invokevirtual 173	java/io/ObjectInputStream:close	()V
    //   225: goto +14 -> 239
    //   228: astore_2
    //   229: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   232: ldc -81
    //   234: aload_2
    //   235: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   238: pop
    //   239: aload 8
    //   241: invokevirtual 182	java/io/ByteArrayInputStream:close	()V
    //   244: goto +14 -> 258
    //   247: astore_2
    //   248: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   251: ldc -81
    //   253: aload_2
    //   254: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   257: pop
    //   258: aload_0
    //   259: athrow
    //   260: new 185	java/lang/IllegalStateException
    //   263: dup
    //   264: ldc -69
    //   266: invokespecial 190	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   269: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	270	0	paramArrayOfByte	byte[]
    //   79	29	1	i	int
    //   37	185	2	localObject1	Object
    //   228	7	2	localIOException1	java.io.IOException
    //   247	7	2	localIOException2	java.io.IOException
    //   43	139	3	localObject2	Object
    //   39	120	4	arrayOfByte	byte[]
    //   30	26	5	localObject3	Object
    //   47	19	6	localObjectInputStream	java.io.ObjectInputStream
    //   33	17	7	localObject4	Object
    //   25	215	8	localByteArrayInputStream	java.io.ByteArrayInputStream
    //   15	197	9	localHashMap	HashMap
    // Exception table:
    //   from	to	target	type
    //   113	117	120	java/io/IOException
    //   131	136	139	java/io/IOException
    //   199	204	139	java/io/IOException
    //   44	49	153	finally
    //   58	65	153	finally
    //   75	80	153	finally
    //   91	107	153	finally
    //   167	177	153	finally
    //   44	49	157	java/lang/ClassNotFoundException
    //   58	65	157	java/lang/ClassNotFoundException
    //   75	80	157	java/lang/ClassNotFoundException
    //   91	107	157	java/lang/ClassNotFoundException
    //   44	49	164	java/io/IOException
    //   58	65	164	java/io/IOException
    //   75	80	164	java/io/IOException
    //   91	107	164	java/io/IOException
    //   181	185	188	java/io/IOException
    //   221	225	228	java/io/IOException
    //   239	244	247	java/io/IOException
  }
  
  /* Error */
  public static byte[] toByteArrayInternal(Data paramData)
  {
    // Byte code:
    //   0: new 194	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 195	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore 6
    //   9: aconst_null
    //   10: astore 5
    //   12: aconst_null
    //   13: astore 4
    //   15: aload 4
    //   17: astore_1
    //   18: aload 5
    //   20: astore_2
    //   21: new 197	java/io/ObjectOutputStream
    //   24: astore_3
    //   25: aload 4
    //   27: astore_1
    //   28: aload 5
    //   30: astore_2
    //   31: aload_3
    //   32: aload 6
    //   34: invokespecial 200	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   37: aload_3
    //   38: astore_1
    //   39: aload_3
    //   40: astore_2
    //   41: aload_3
    //   42: aload_0
    //   43: invokevirtual 203	androidx/work/Data:size	()I
    //   46: invokevirtual 207	java/io/ObjectOutputStream:writeInt	(I)V
    //   49: aload_3
    //   50: astore_1
    //   51: aload_3
    //   52: astore_2
    //   53: aload_0
    //   54: getfield 57	androidx/work/Data:mValues	Ljava/util/Map;
    //   57: invokeinterface 211 1 0
    //   62: invokeinterface 217 1 0
    //   67: astore_0
    //   68: aload_3
    //   69: astore_1
    //   70: aload_3
    //   71: astore_2
    //   72: aload_0
    //   73: invokeinterface 222 1 0
    //   78: ifeq +54 -> 132
    //   81: aload_3
    //   82: astore_1
    //   83: aload_3
    //   84: astore_2
    //   85: aload_0
    //   86: invokeinterface 225 1 0
    //   91: checkcast 227	java/util/Map$Entry
    //   94: astore 4
    //   96: aload_3
    //   97: astore_1
    //   98: aload_3
    //   99: astore_2
    //   100: aload_3
    //   101: aload 4
    //   103: invokeinterface 230 1 0
    //   108: checkcast 232	java/lang/String
    //   111: invokevirtual 235	java/io/ObjectOutputStream:writeUTF	(Ljava/lang/String;)V
    //   114: aload_3
    //   115: astore_1
    //   116: aload_3
    //   117: astore_2
    //   118: aload_3
    //   119: aload 4
    //   121: invokeinterface 238 1 0
    //   126: invokevirtual 241	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
    //   129: goto -61 -> 68
    //   132: aload_3
    //   133: invokevirtual 242	java/io/ObjectOutputStream:close	()V
    //   136: goto +14 -> 150
    //   139: astore_0
    //   140: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   143: ldc -12
    //   145: aload_0
    //   146: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   149: pop
    //   150: aload 6
    //   152: invokevirtual 245	java/io/ByteArrayOutputStream:close	()V
    //   155: goto +14 -> 169
    //   158: astore_0
    //   159: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   162: ldc -12
    //   164: aload_0
    //   165: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   168: pop
    //   169: aload 6
    //   171: invokevirtual 246	java/io/ByteArrayOutputStream:size	()I
    //   174: sipush 10240
    //   177: if_icmpgt +9 -> 186
    //   180: aload 6
    //   182: invokevirtual 250	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   185: areturn
    //   186: new 185	java/lang/IllegalStateException
    //   189: dup
    //   190: ldc -69
    //   192: invokespecial 190	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   195: athrow
    //   196: astore_0
    //   197: goto +67 -> 264
    //   200: astore_0
    //   201: aload_2
    //   202: astore_1
    //   203: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   206: ldc -12
    //   208: aload_0
    //   209: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   212: pop
    //   213: aload_2
    //   214: astore_1
    //   215: aload 6
    //   217: invokevirtual 250	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   220: astore_0
    //   221: aload_2
    //   222: ifnull +21 -> 243
    //   225: aload_2
    //   226: invokevirtual 242	java/io/ObjectOutputStream:close	()V
    //   229: goto +14 -> 243
    //   232: astore_1
    //   233: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   236: ldc -12
    //   238: aload_1
    //   239: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   242: pop
    //   243: aload 6
    //   245: invokevirtual 245	java/io/ByteArrayOutputStream:close	()V
    //   248: goto +14 -> 262
    //   251: astore_1
    //   252: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   255: ldc -12
    //   257: aload_1
    //   258: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   261: pop
    //   262: aload_0
    //   263: areturn
    //   264: aload_1
    //   265: ifnull +21 -> 286
    //   268: aload_1
    //   269: invokevirtual 242	java/io/ObjectOutputStream:close	()V
    //   272: goto +14 -> 286
    //   275: astore_1
    //   276: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   279: ldc -12
    //   281: aload_1
    //   282: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   285: pop
    //   286: aload 6
    //   288: invokevirtual 245	java/io/ByteArrayOutputStream:close	()V
    //   291: goto +14 -> 305
    //   294: astore_1
    //   295: getstatic 41	androidx/work/Data:TAG	Ljava/lang/String;
    //   298: ldc -12
    //   300: aload_1
    //   301: invokestatic 181	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   304: pop
    //   305: aload_0
    //   306: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	307	0	paramData	Data
    //   17	198	1	localObject1	Object
    //   232	7	1	localIOException1	java.io.IOException
    //   251	18	1	localIOException2	java.io.IOException
    //   275	7	1	localIOException3	java.io.IOException
    //   294	7	1	localIOException4	java.io.IOException
    //   20	206	2	localObject2	Object
    //   24	109	3	localObjectOutputStream	java.io.ObjectOutputStream
    //   13	107	4	localEntry	Map.Entry
    //   10	19	5	localObject3	Object
    //   7	280	6	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    // Exception table:
    //   from	to	target	type
    //   132	136	139	java/io/IOException
    //   150	155	158	java/io/IOException
    //   21	25	196	finally
    //   31	37	196	finally
    //   41	49	196	finally
    //   53	68	196	finally
    //   72	81	196	finally
    //   85	96	196	finally
    //   100	114	196	finally
    //   118	129	196	finally
    //   203	213	196	finally
    //   215	221	196	finally
    //   21	25	200	java/io/IOException
    //   31	37	200	java/io/IOException
    //   41	49	200	java/io/IOException
    //   53	68	200	java/io/IOException
    //   72	81	200	java/io/IOException
    //   85	96	200	java/io/IOException
    //   100	114	200	java/io/IOException
    //   118	129	200	java/io/IOException
    //   225	229	232	java/io/IOException
    //   243	248	251	java/io/IOException
    //   268	272	275	java/io/IOException
    //   286	291	294	java/io/IOException
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (Data)paramObject;
      Object localObject1 = this.mValues.keySet();
      if (!localObject1.equals(((Data)paramObject).mValues.keySet())) {
        return false;
      }
      Iterator localIterator = ((Set)localObject1).iterator();
      while (localIterator.hasNext())
      {
        Object localObject2 = (String)localIterator.next();
        localObject1 = this.mValues.get(localObject2);
        localObject2 = ((Data)paramObject).mValues.get(localObject2);
        boolean bool;
        if ((localObject1 != null) && (localObject2 != null))
        {
          if (((localObject1 instanceof Object[])) && ((localObject2 instanceof Object[]))) {
            bool = Arrays.deepEquals((Object[])localObject1, (Object[])localObject2);
          } else {
            bool = localObject1.equals(localObject2);
          }
        }
        else if (localObject1 == localObject2) {
          bool = true;
        } else {
          bool = false;
        }
        if (!bool) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Boolean)) {
      return ((Boolean)paramString).booleanValue();
    }
    return paramBoolean;
  }
  
  public boolean[] getBooleanArray(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Boolean[])) {
      return convertToPrimitiveArray((Boolean[])paramString);
    }
    return null;
  }
  
  public byte getByte(String paramString, byte paramByte)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Byte)) {
      return ((Byte)paramString).byteValue();
    }
    return paramByte;
  }
  
  public byte[] getByteArray(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Byte[])) {
      return convertToPrimitiveArray((Byte[])paramString);
    }
    return null;
  }
  
  public double getDouble(String paramString, double paramDouble)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Double)) {
      return ((Double)paramString).doubleValue();
    }
    return paramDouble;
  }
  
  public double[] getDoubleArray(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Double[])) {
      return convertToPrimitiveArray((Double[])paramString);
    }
    return null;
  }
  
  public float getFloat(String paramString, float paramFloat)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Float)) {
      return ((Float)paramString).floatValue();
    }
    return paramFloat;
  }
  
  public float[] getFloatArray(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Float[])) {
      return convertToPrimitiveArray((Float[])paramString);
    }
    return null;
  }
  
  public int getInt(String paramString, int paramInt)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Integer)) {
      return ((Integer)paramString).intValue();
    }
    return paramInt;
  }
  
  public int[] getIntArray(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Integer[])) {
      return convertToPrimitiveArray((Integer[])paramString);
    }
    return null;
  }
  
  public Map<String, Object> getKeyValueMap()
  {
    return Collections.unmodifiableMap(this.mValues);
  }
  
  public long getLong(String paramString, long paramLong)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Long)) {
      return ((Long)paramString).longValue();
    }
    return paramLong;
  }
  
  public long[] getLongArray(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof Long[])) {
      return convertToPrimitiveArray((Long[])paramString);
    }
    return null;
  }
  
  public String getString(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof String)) {
      return (String)paramString;
    }
    return null;
  }
  
  public String[] getStringArray(String paramString)
  {
    paramString = this.mValues.get(paramString);
    if ((paramString instanceof String[])) {
      return (String[])paramString;
    }
    return null;
  }
  
  public <T> boolean hasKeyWithValueOfType(String paramString, Class<T> paramClass)
  {
    paramString = this.mValues.get(paramString);
    boolean bool;
    if ((paramString != null) && (paramClass.isAssignableFrom(paramString.getClass()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.mValues.hashCode() * 31;
  }
  
  public int size()
  {
    return this.mValues.size();
  }
  
  public byte[] toByteArray()
  {
    return toByteArrayInternal(this);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Data {");
    if (!this.mValues.isEmpty())
    {
      Iterator localIterator = this.mValues.keySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (String)localIterator.next();
        localStringBuilder.append((String)localObject).append(" : ");
        localObject = this.mValues.get(localObject);
        if ((localObject instanceof Object[]))
        {
          localObject = Arrays.toString((Object[])localObject);
          Log5ECF72.a(localObject);
          LogE84000.a(localObject);
          Log229316.a(localObject);
          localStringBuilder.append((String)localObject);
        }
        else
        {
          localStringBuilder.append(localObject);
        }
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  public static final class Builder
  {
    private Map<String, Object> mValues = new HashMap();
    
    public Data build()
    {
      Data localData = new Data(this.mValues);
      Data.toByteArrayInternal(localData);
      return localData;
    }
    
    public Builder put(String paramString, Object paramObject)
    {
      if (paramObject == null)
      {
        this.mValues.put(paramString, null);
      }
      else
      {
        Class localClass = paramObject.getClass();
        if ((localClass != Boolean.class) && (localClass != Byte.class) && (localClass != Integer.class) && (localClass != Long.class) && (localClass != Float.class) && (localClass != Double.class) && (localClass != String.class) && (localClass != Boolean[].class) && (localClass != Byte[].class) && (localClass != Integer[].class) && (localClass != Long[].class) && (localClass != Float[].class) && (localClass != Double[].class) && (localClass != String[].class))
        {
          if (localClass == boolean[].class)
          {
            this.mValues.put(paramString, Data.convertPrimitiveBooleanArray((boolean[])paramObject));
          }
          else if (localClass == byte[].class)
          {
            this.mValues.put(paramString, Data.convertPrimitiveByteArray((byte[])paramObject));
          }
          else if (localClass == int[].class)
          {
            this.mValues.put(paramString, Data.convertPrimitiveIntArray((int[])paramObject));
          }
          else if (localClass == long[].class)
          {
            this.mValues.put(paramString, Data.convertPrimitiveLongArray((long[])paramObject));
          }
          else if (localClass == float[].class)
          {
            this.mValues.put(paramString, Data.convertPrimitiveFloatArray((float[])paramObject));
          }
          else if (localClass == double[].class)
          {
            this.mValues.put(paramString, Data.convertPrimitiveDoubleArray((double[])paramObject));
          }
          else
          {
            paramString = String.format("Key %s has invalid type %s", new Object[] { paramString, localClass });
            Log5ECF72.a(paramString);
            LogE84000.a(paramString);
            Log229316.a(paramString);
            throw new IllegalArgumentException(paramString);
          }
        }
        else {
          this.mValues.put(paramString, paramObject);
        }
      }
      return this;
    }
    
    public Builder putAll(Data paramData)
    {
      putAll(paramData.mValues);
      return this;
    }
    
    public Builder putAll(Map<String, Object> paramMap)
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        put((String)localEntry.getKey(), localEntry.getValue());
      }
      return this;
    }
    
    public Builder putBoolean(String paramString, boolean paramBoolean)
    {
      this.mValues.put(paramString, Boolean.valueOf(paramBoolean));
      return this;
    }
    
    public Builder putBooleanArray(String paramString, boolean[] paramArrayOfBoolean)
    {
      this.mValues.put(paramString, Data.convertPrimitiveBooleanArray(paramArrayOfBoolean));
      return this;
    }
    
    public Builder putByte(String paramString, byte paramByte)
    {
      this.mValues.put(paramString, Byte.valueOf(paramByte));
      return this;
    }
    
    public Builder putByteArray(String paramString, byte[] paramArrayOfByte)
    {
      this.mValues.put(paramString, Data.convertPrimitiveByteArray(paramArrayOfByte));
      return this;
    }
    
    public Builder putDouble(String paramString, double paramDouble)
    {
      this.mValues.put(paramString, Double.valueOf(paramDouble));
      return this;
    }
    
    public Builder putDoubleArray(String paramString, double[] paramArrayOfDouble)
    {
      this.mValues.put(paramString, Data.convertPrimitiveDoubleArray(paramArrayOfDouble));
      return this;
    }
    
    public Builder putFloat(String paramString, float paramFloat)
    {
      this.mValues.put(paramString, Float.valueOf(paramFloat));
      return this;
    }
    
    public Builder putFloatArray(String paramString, float[] paramArrayOfFloat)
    {
      this.mValues.put(paramString, Data.convertPrimitiveFloatArray(paramArrayOfFloat));
      return this;
    }
    
    public Builder putInt(String paramString, int paramInt)
    {
      this.mValues.put(paramString, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder putIntArray(String paramString, int[] paramArrayOfInt)
    {
      this.mValues.put(paramString, Data.convertPrimitiveIntArray(paramArrayOfInt));
      return this;
    }
    
    public Builder putLong(String paramString, long paramLong)
    {
      this.mValues.put(paramString, Long.valueOf(paramLong));
      return this;
    }
    
    public Builder putLongArray(String paramString, long[] paramArrayOfLong)
    {
      this.mValues.put(paramString, Data.convertPrimitiveLongArray(paramArrayOfLong));
      return this;
    }
    
    public Builder putString(String paramString1, String paramString2)
    {
      this.mValues.put(paramString1, paramString2);
      return this;
    }
    
    public Builder putStringArray(String paramString, String[] paramArrayOfString)
    {
      this.mValues.put(paramString, paramArrayOfString);
      return this;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/Data.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */