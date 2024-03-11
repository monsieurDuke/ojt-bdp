package okhttp3.internal.publicsuffix;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.IDN;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.internal.Util;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\030\002\n\000\n\002\020 \n\002\020\016\n\002\b\004\n\002\020\002\n\002\b\005\030\000 \0252\0020\001:\001\025B\005¢\006\002\020\002J\034\020\n\032\b\022\004\022\0020\f0\0132\f\020\r\032\b\022\004\022\0020\f0\013H\002J\020\020\016\032\004\030\0010\f2\006\020\017\032\0020\fJ\b\020\020\032\0020\021H\002J\b\020\022\032\0020\021H\002J\026\020\023\032\0020\0212\006\020\007\032\0020\0062\006\020\005\032\0020\006J\026\020\024\032\b\022\004\022\0020\f0\0132\006\020\017\032\0020\fH\002R\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\005\032\0020\006X.¢\006\002\n\000R\016\020\007\032\0020\006X.¢\006\002\n\000R\016\020\b\032\0020\tX\004¢\006\002\n\000¨\006\026"}, d2={"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "", "()V", "listRead", "Ljava/util/concurrent/atomic/AtomicBoolean;", "publicSuffixExceptionListBytes", "", "publicSuffixListBytes", "readCompleteLatch", "Ljava/util/concurrent/CountDownLatch;", "findMatchingRule", "", "", "domainLabels", "getEffectiveTldPlusOne", "domain", "readTheList", "", "readTheListUninterruptibly", "setListBytes", "splitDomain", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class PublicSuffixDatabase
{
  public static final Companion Companion = new Companion(null);
  private static final char EXCEPTION_MARKER = '!';
  private static final List<String> PREVAILING_RULE = CollectionsKt.listOf("*");
  public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
  private static final byte[] WILDCARD_LABEL = { (byte)42 };
  private static final PublicSuffixDatabase instance = new PublicSuffixDatabase();
  private final AtomicBoolean listRead = new AtomicBoolean(false);
  private byte[] publicSuffixExceptionListBytes;
  private byte[] publicSuffixListBytes;
  private final CountDownLatch readCompleteLatch = new CountDownLatch(1);
  
  private final List<String> findMatchingRule(List<String> paramList)
  {
    if ((!this.listRead.get()) && (this.listRead.compareAndSet(false, true))) {
      readTheListUninterruptibly();
    } else {
      try
      {
        this.readCompleteLatch.await();
      }
      catch (InterruptedException localInterruptedException)
      {
        Thread.currentThread().interrupt();
      }
    }
    int i;
    if (((PublicSuffixDatabase)this).publicSuffixListBytes != null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      int j = paramList.size();
      Object localObject1 = new byte[j][];
      i = 0;
      while (i < j)
      {
        localObject3 = (String)paramList.get(i);
        localObject2 = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(localObject2, "UTF_8");
        if (localObject3 != null)
        {
          localObject2 = ((String)localObject3).getBytes((Charset)localObject2);
          Intrinsics.checkNotNullExpressionValue(localObject2, "(this as java.lang.String).getBytes(charset)");
          localObject1[i] = localObject2;
          i++;
        }
        else
        {
          throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
      }
      byte[][] arrayOfByte = (byte[][])localObject1;
      localObject1 = (String)null;
      j = arrayOfByte.length;
      for (i = 0;; i++)
      {
        paramList = (List<String>)localObject1;
        if (i >= j) {
          break;
        }
        localObject2 = Companion;
        paramList = this.publicSuffixListBytes;
        if (paramList == null) {
          Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
        }
        paramList = Companion.access$binarySearch((Companion)localObject2, paramList, arrayOfByte, i);
        Log5ECF72.a(paramList);
        LogE84000.a(paramList);
        Log229316.a(paramList);
        if (paramList != null) {
          break;
        }
      }
      Object localObject2 = (String)null;
      localObject1 = localObject2;
      Object localObject4;
      if (((Object[])arrayOfByte).length > 1)
      {
        localObject3 = (byte[][])((Object[])arrayOfByte).clone();
        j = ((Object[])localObject3).length;
        for (i = 0;; i++)
        {
          localObject1 = localObject2;
          if (i >= j - 1) {
            break;
          }
          localObject3[i] = WILDCARD_LABEL;
          localObject1 = Companion;
          localObject4 = this.publicSuffixListBytes;
          if (localObject4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
          }
          localObject1 = Companion.access$binarySearch((Companion)localObject1, (byte[])localObject4, (byte[][])localObject3, i);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          if (localObject1 != null) {
            break;
          }
        }
      }
      Object localObject3 = (String)null;
      localObject2 = localObject3;
      if (localObject1 != null)
      {
        j = ((Object[])arrayOfByte).length;
        for (i = 0;; i++)
        {
          localObject2 = localObject3;
          if (i >= j - 1) {
            break;
          }
          localObject4 = Companion;
          localObject2 = this.publicSuffixExceptionListBytes;
          if (localObject2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("publicSuffixExceptionListBytes");
          }
          localObject2 = Companion.access$binarySearch((Companion)localObject4, (byte[])localObject2, arrayOfByte, i);
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          if (localObject2 != null) {
            break;
          }
        }
      }
      if (localObject2 != null) {
        return StringsKt.split$default((CharSequence)('!' + (String)localObject2), new char[] { '.' }, false, 0, 6, null);
      }
      if ((paramList == null) && (localObject1 == null)) {
        return PREVAILING_RULE;
      }
      if (paramList != null)
      {
        paramList = StringsKt.split$default((CharSequence)paramList, new char[] { '.' }, false, 0, 6, null);
        if (paramList != null) {}
      }
      else
      {
        paramList = CollectionsKt.emptyList();
      }
      if (localObject1 != null)
      {
        localObject1 = StringsKt.split$default((CharSequence)localObject1, new char[] { '.' }, false, 0, 6, null);
        if (localObject1 != null) {}
      }
      else
      {
        localObject1 = CollectionsKt.emptyList();
      }
      if (paramList.size() <= ((List)localObject1).size()) {
        paramList = (List<String>)localObject1;
      }
      return paramList;
    }
    throw ((Throwable)new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.".toString()));
  }
  
  private final void readTheList()
    throws IOException
  {
    Object localObject1 = PublicSuffixDatabase.class.getResourceAsStream("publicsuffixes.gz");
    if (localObject1 != null)
    {
      localObject1 = (Closeable)Okio.buffer((Source)new GzipSource(Okio.source((InputStream)localObject1)));
      Object localObject2 = (Throwable)null;
      try
      {
        Object localObject3 = (BufferedSource)localObject1;
        localObject2 = ((BufferedSource)localObject3).readByteArray(((BufferedSource)localObject3).readInt());
        byte[] arrayOfByte = ((BufferedSource)localObject3).readByteArray(((BufferedSource)localObject3).readInt());
        localObject3 = Unit.INSTANCE;
        CloseableKt.closeFinally((Closeable)localObject1, null);
        try
        {
          Intrinsics.checkNotNull(localObject2);
          this.publicSuffixListBytes = ((byte[])localObject2);
          Intrinsics.checkNotNull(arrayOfByte);
          this.publicSuffixExceptionListBytes = arrayOfByte;
          localObject1 = Unit.INSTANCE;
          this.readCompleteLatch.countDown();
          return;
        }
        finally {}
        return;
      }
      finally
      {
        try
        {
          throw localThrowable;
        }
        finally
        {
          CloseableKt.closeFinally(localCloseable, localThrowable);
        }
      }
    }
  }
  
  /* Error */
  private final void readTheListUninterruptibly()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: invokespecial 286	okhttp3/internal/publicsuffix/PublicSuffixDatabase:readTheList	()V
    //   6: iload_1
    //   7: ifeq +9 -> 16
    //   10: invokestatic 127	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   13: invokevirtual 130	java/lang/Thread:interrupt	()V
    //   16: return
    //   17: astore_2
    //   18: goto +42 -> 60
    //   21: astore_2
    //   22: getstatic 291	okhttp3/internal/platform/Platform:Companion	Lokhttp3/internal/platform/Platform$Companion;
    //   25: invokevirtual 296	okhttp3/internal/platform/Platform$Companion:get	()Lokhttp3/internal/platform/Platform;
    //   28: ldc_w 298
    //   31: iconst_5
    //   32: aload_2
    //   33: checkcast 226	java/lang/Throwable
    //   36: invokevirtual 302	okhttp3/internal/platform/Platform:log	(Ljava/lang/String;ILjava/lang/Throwable;)V
    //   39: iload_1
    //   40: ifeq +9 -> 49
    //   43: invokestatic 127	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   46: invokevirtual 130	java/lang/Thread:interrupt	()V
    //   49: return
    //   50: astore_2
    //   51: invokestatic 305	java/lang/Thread:interrupted	()Z
    //   54: pop
    //   55: iconst_1
    //   56: istore_1
    //   57: goto -55 -> 2
    //   60: iload_1
    //   61: ifeq +9 -> 70
    //   64: invokestatic 127	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   67: invokevirtual 130	java/lang/Thread:interrupt	()V
    //   70: aload_2
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	PublicSuffixDatabase
    //   1	60	1	i	int
    //   17	1	2	localObject	Object
    //   21	12	2	localIOException	IOException
    //   50	21	2	localInterruptedIOException	java.io.InterruptedIOException
    // Exception table:
    //   from	to	target	type
    //   2	6	17	finally
    //   22	39	17	finally
    //   51	55	17	finally
    //   2	6	21	java/io/IOException
    //   2	6	50	java/io/InterruptedIOException
  }
  
  private final List<String> splitDomain(String paramString)
  {
    paramString = StringsKt.split$default((CharSequence)paramString, new char[] { '.' }, false, 0, 6, null);
    if (Intrinsics.areEqual((String)CollectionsKt.last(paramString), "")) {
      return CollectionsKt.dropLast(paramString, 1);
    }
    return paramString;
  }
  
  public final String getEffectiveTldPlusOne(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "domain");
    Object localObject = IDN.toUnicode(paramString);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    Intrinsics.checkNotNullExpressionValue(localObject, "unicodeDomain");
    localObject = splitDomain((String)localObject);
    List localList = findMatchingRule((List)localObject);
    if ((((List)localObject).size() == localList.size()) && (((String)localList.get(0)).charAt(0) != '!')) {
      return null;
    }
    int i;
    if (((String)localList.get(0)).charAt(0) == '!') {
      i = ((List)localObject).size() - localList.size();
    } else {
      i = ((List)localObject).size() - (localList.size() + 1);
    }
    paramString = SequencesKt.joinToString$default(SequencesKt.drop(CollectionsKt.asSequence((Iterable)splitDomain(paramString)), i), (CharSequence)".", null, null, 0, null, null, 62, null);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return paramString;
  }
  
  public final void setListBytes(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte1, "publicSuffixListBytes");
    Intrinsics.checkNotNullParameter(paramArrayOfByte2, "publicSuffixExceptionListBytes");
    this.publicSuffixListBytes = paramArrayOfByte1;
    this.publicSuffixExceptionListBytes = paramArrayOfByte2;
    this.listRead.set(true);
    this.readCompleteLatch.countDown();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\f\n\000\n\002\020 \n\002\020\016\n\002\b\002\n\002\020\022\n\000\n\002\030\002\n\002\b\003\n\002\020\021\n\000\n\002\020\b\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\006\020\r\032\0020\fJ)\020\016\032\004\030\0010\007*\0020\n2\f\020\017\032\b\022\004\022\0020\n0\0202\006\020\021\032\0020\022H\002¢\006\002\020\023R\016\020\003\032\0020\004XT¢\006\002\n\000R\024\020\005\032\b\022\004\022\0020\0070\006X\004¢\006\002\n\000R\016\020\b\032\0020\007XT¢\006\002\n\000R\016\020\t\032\0020\nX\004¢\006\002\n\000R\016\020\013\032\0020\fX\004¢\006\002\n\000¨\006\024"}, d2={"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase$Companion;", "", "()V", "EXCEPTION_MARKER", "", "PREVAILING_RULE", "", "", "PUBLIC_SUFFIX_RESOURCE", "WILDCARD_LABEL", "", "instance", "Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "get", "binarySearch", "labels", "", "labelIndex", "", "([B[[BI)Ljava/lang/String;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final String binarySearch(byte[] paramArrayOfByte, byte[][] paramArrayOfByte1, int paramInt)
    {
      int i = 0;
      int n = paramArrayOfByte.length;
      String str = (String)null;
      if (i < n)
      {
        for (int j = (i + n) / 2; (j > -1) && (paramArrayOfByte[j] != (byte)10); j--) {}
        int i4 = j + 1;
        for (int i1 = 1; paramArrayOfByte[(i4 + i1)] != (byte)10; i1++) {}
        int i5 = i4 + i1 - i4;
        int i2 = paramInt;
        int k = 0;
        j = 0;
        int m = 0;
        label326:
        label365:
        label377:
        for (;;)
        {
          int i3;
          if (m != 0)
          {
            i3 = 46;
            m = 0;
          }
          else
          {
            i3 = Util.and(paramArrayOfByte1[i2][k], 255);
          }
          i3 -= Util.and(paramArrayOfByte[(i4 + j)], 255);
          if (i3 == 0)
          {
            j++;
            k++;
            if (j != i5)
            {
              if (paramArrayOfByte1[i2].length != k) {
                break label377;
              }
              if (i2 != ((Object[])paramArrayOfByte1).length - 1) {
                break label365;
              }
            }
          }
          if (i3 < 0)
          {
            j = i4 - 1;
          }
          else if (i3 > 0)
          {
            i = i4 + i1 + 1;
            j = n;
          }
          else
          {
            m = i5 - j;
            k = paramArrayOfByte1[i2].length - k;
            j = i2 + 1;
            i2 = ((Object[])paramArrayOfByte1).length;
            while (j < i2)
            {
              k += paramArrayOfByte1[j].length;
              j++;
            }
            if (k < m)
            {
              j = i4 - 1;
            }
            else
            {
              if (k <= m) {
                break label326;
              }
              i = i4 + i1 + 1;
              j = n;
            }
          }
          n = j;
          break;
          paramArrayOfByte1 = StandardCharsets.UTF_8;
          Intrinsics.checkNotNullExpressionValue(paramArrayOfByte1, "UTF_8");
          paramArrayOfByte = new String(paramArrayOfByte, i4, i5, paramArrayOfByte1);
          Log5ECF72.a(paramArrayOfByte);
          LogE84000.a(paramArrayOfByte);
          Log229316.a(paramArrayOfByte);
          return paramArrayOfByte;
          i2++;
          m = 1;
          k = -1;
        }
      }
      paramArrayOfByte = str;
      return paramArrayOfByte;
    }
    
    public final PublicSuffixDatabase get()
    {
      return PublicSuffixDatabase.access$getInstance$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/publicsuffix/PublicSuffixDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */