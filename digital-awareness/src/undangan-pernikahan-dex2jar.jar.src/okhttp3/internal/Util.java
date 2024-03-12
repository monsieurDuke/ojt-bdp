package okhttp3.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.EventListener.Factory;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.Headers.Companion;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Companion;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.RequestBody.Companion;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ResponseBody.Companion;
import okhttp3.internal.http2.Header;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ByteString.Companion;
import okio.Options;
import okio.Options.Companion;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\000¸\002\n\000\n\002\020\022\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\005\n\002\020\021\n\002\020\000\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020!\n\002\b\003\n\002\020\005\n\000\n\002\020\n\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\f\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020\034\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\t\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\006\n\002\020$\n\002\b\b\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\032 \020\023\032\0020\0242\006\020\025\032\0020\0212\006\020\026\032\0020\0272\b\020\030\032\004\030\0010\031\032\036\020\032\032\0020\0332\006\020\034\032\0020\0272\006\020\035\032\0020\0272\006\020\036\032\0020\027\032'\020\037\032\0020\0212\006\020\037\032\0020\0212\022\020 \032\n\022\006\b\001\022\0020\"0!\"\0020\"¢\006\002\020#\032\032\020$\032\0020\0332\f\020%\032\b\022\004\022\0020\0330&H\bø\001\000\032-\020'\032\b\022\004\022\002H)0(\"\004\b\000\020)2\022\020*\032\n\022\006\b\001\022\002H)0!\"\002H)H\007¢\006\002\020+\0321\020,\032\004\030\001H)\"\004\b\000\020)2\006\020-\032\0020\"2\f\020.\032\b\022\004\022\002H)0/2\006\0200\032\0020\021¢\006\002\0201\032\026\0202\032\002032\006\020\025\032\0020\0212\006\0204\032\0020\017\032\"\0205\032\0020\0332\006\020\025\032\0020\0212\f\020%\032\b\022\004\022\0020\0330&H\bø\001\000\032%\0206\032\0020\033\"\004\b\000\0207*\b\022\004\022\002H7082\006\0209\032\002H7H\000¢\006\002\020:\032\025\020;\032\0020\024*\0020<2\006\020=\032\0020\024H\004\032\025\020;\032\0020\027*\0020\0242\006\020=\032\0020\027H\004\032\025\020;\032\0020\024*\0020>2\006\020=\032\0020\024H\004\032\n\020?\032\0020@*\0020A\032\r\020B\032\0020\033*\0020\"H\b\032\r\020C\032\0020\033*\0020\"H\b\032\n\020D\032\0020\017*\0020\021\032\022\020E\032\0020\017*\0020F2\006\020G\032\0020F\032\n\020H\032\0020\033*\0020I\032\n\020H\032\0020\033*\0020J\032\n\020H\032\0020\033*\0020K\032#\020L\032\b\022\004\022\0020\0210!*\b\022\004\022\0020\0210!2\006\020M\032\0020\021¢\006\002\020N\032&\020O\032\0020\024*\0020\0212\006\020P\032\0020Q2\b\b\002\020R\032\0020\0242\b\b\002\020S\032\0020\024\032&\020O\032\0020\024*\0020\0212\006\020T\032\0020\0212\b\b\002\020R\032\0020\0242\b\b\002\020S\032\0020\024\032\032\020U\032\0020\017*\0020V2\006\020W\032\0020\0242\006\020X\032\0020\031\032;\020Y\032\b\022\004\022\002H)0(\"\004\b\000\020)*\b\022\004\022\002H)0Z2\027\020[\032\023\022\004\022\002H)\022\004\022\0020\0170\\¢\006\002\b]H\bø\001\000\0325\020^\032\0020\017*\b\022\004\022\0020\0210!2\016\020G\032\n\022\004\022\0020\021\030\0010!2\016\020_\032\n\022\006\b\000\022\0020\0210`¢\006\002\020a\032\n\020b\032\0020\027*\0020c\032+\020d\032\0020\024*\b\022\004\022\0020\0210!2\006\020M\032\0020\0212\f\020_\032\b\022\004\022\0020\0210`¢\006\002\020e\032\n\020f\032\0020\024*\0020\021\032\036\020g\032\0020\024*\0020\0212\b\b\002\020R\032\0020\0242\b\b\002\020S\032\0020\024\032\036\020h\032\0020\024*\0020\0212\b\b\002\020R\032\0020\0242\b\b\002\020S\032\0020\024\032\024\020i\032\0020\024*\0020\0212\b\b\002\020R\032\0020\024\0329\020j\032\b\022\004\022\0020\0210!*\b\022\004\022\0020\0210!2\f\020G\032\b\022\004\022\0020\0210!2\016\020_\032\n\022\006\b\000\022\0020\0210`¢\006\002\020k\032\022\020l\032\0020\017*\0020m2\006\020n\032\0020o\032\022\020p\032\0020\017*\0020K2\006\020q\032\0020r\032\r\020s\032\0020\033*\0020\"H\b\032\r\020t\032\0020\033*\0020\"H\b\032\n\020u\032\0020\024*\0020Q\032\n\020v\032\0020\021*\0020K\032\022\020w\032\0020x*\0020r2\006\020y\032\0020x\032\n\020z\032\0020\024*\0020r\032\022\020{\032\0020\024*\0020|2\006\020}\032\0020<\032\032\020{\032\0020\017*\0020V2\006\020\026\032\0020\0242\006\020X\032\0020\031\032\020\020~\032\b\022\004\022\00200(*\0020\003\032\021\020\001\032\0020\003*\b\022\004\022\00200(\032\013\020\001\032\0020\021*\0020\024\032\013\020\001\032\0020\021*\0020\027\032\026\020\001\032\0020\021*\0020F2\t\b\002\020\001\032\0020\017\032\035\020\001\032\b\022\004\022\002H)0(\"\004\b\000\020)*\b\022\004\022\002H)0(\0327\020\001\032\021\022\005\022\003H\001\022\005\022\003H\0010\001\"\005\b\000\020\001\"\005\b\001\020\001*\021\022\005\022\003H\001\022\005\022\003H\0010\001\032\024\020\001\032\0020\027*\0020\0212\007\020\001\032\0020\027\032\026\020\001\032\0020\024*\004\030\0010\0212\007\020\001\032\0020\024\032\037\020\001\032\0020\021*\0020\0212\b\b\002\020R\032\0020\0242\b\b\002\020S\032\0020\024\032\016\020\001\032\0020\033*\0020\"H\b\032'\020\001\032\0030\001*\b0\001j\003`\0012\023\020\001\032\016\022\n\022\b0\001j\003`\0010(\032\025\020\001\032\0020\033*\0030\0012\007\020\001\032\0020\024\"\020\020\000\032\0020\0018\006X\004¢\006\002\n\000\"\020\020\002\032\0020\0038\006X\004¢\006\002\n\000\"\020\020\004\032\0020\0058\006X\004¢\006\002\n\000\"\020\020\006\032\0020\0078\006X\004¢\006\002\n\000\"\016\020\b\032\0020\tX\004¢\006\002\n\000\"\020\020\n\032\0020\0138\006X\004¢\006\002\n\000\"\016\020\f\032\0020\rX\004¢\006\002\n\000\"\020\020\016\032\0020\0178\000X\004¢\006\002\n\000\"\020\020\020\032\0020\0218\000X\004¢\006\002\n\000\"\016\020\022\032\0020\021XT¢\006\002\n\000\002\007\n\005\b20\001¨\006\001"}, d2={"EMPTY_BYTE_ARRAY", "", "EMPTY_HEADERS", "Lokhttp3/Headers;", "EMPTY_REQUEST", "Lokhttp3/RequestBody;", "EMPTY_RESPONSE", "Lokhttp3/ResponseBody;", "UNICODE_BOMS", "Lokio/Options;", "UTC", "Ljava/util/TimeZone;", "VERIFY_AS_IP_ADDRESS", "Lkotlin/text/Regex;", "assertionsEnabled", "", "okHttpName", "", "userAgent", "checkDuration", "", "name", "duration", "", "unit", "Ljava/util/concurrent/TimeUnit;", "checkOffsetAndCount", "", "arrayLength", "offset", "count", "format", "args", "", "", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "ignoreIoExceptions", "block", "Lkotlin/Function0;", "immutableListOf", "", "T", "elements", "([Ljava/lang/Object;)Ljava/util/List;", "readFieldOrNull", "instance", "fieldType", "Ljava/lang/Class;", "fieldName", "(Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "daemon", "threadName", "addIfAbsent", "E", "", "element", "(Ljava/util/List;Ljava/lang/Object;)V", "and", "", "mask", "", "asFactory", "Lokhttp3/EventListener$Factory;", "Lokhttp3/EventListener;", "assertThreadDoesntHoldLock", "assertThreadHoldsLock", "canParseAsIpAddress", "canReuseConnectionFor", "Lokhttp3/HttpUrl;", "other", "closeQuietly", "Ljava/io/Closeable;", "Ljava/net/ServerSocket;", "Ljava/net/Socket;", "concat", "value", "([Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "delimiterOffset", "delimiter", "", "startIndex", "endIndex", "delimiters", "discard", "Lokio/Source;", "timeout", "timeUnit", "filterList", "", "predicate", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "hasIntersection", "comparator", "Ljava/util/Comparator;", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)Z", "headersContentLength", "Lokhttp3/Response;", "indexOf", "([Ljava/lang/String;Ljava/lang/String;Ljava/util/Comparator;)I", "indexOfControlOrNonAscii", "indexOfFirstNonAsciiWhitespace", "indexOfLastNonAsciiWhitespace", "indexOfNonWhitespace", "intersect", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)[Ljava/lang/String;", "isCivilized", "Lokhttp3/internal/io/FileSystem;", "file", "Ljava/io/File;", "isHealthy", "source", "Lokio/BufferedSource;", "notify", "notifyAll", "parseHexDigit", "peerName", "readBomAsCharset", "Ljava/nio/charset/Charset;", "default", "readMedium", "skipAll", "Lokio/Buffer;", "b", "toHeaderList", "Lokhttp3/internal/http2/Header;", "toHeaders", "toHexString", "toHostHeader", "includeDefaultPort", "toImmutableList", "toImmutableMap", "", "K", "V", "toLongOrDefault", "defaultValue", "toNonNegativeInt", "trimSubstring", "wait", "withSuppressed", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "suppressed", "writeMedium", "Lokio/BufferedSink;", "medium", "okhttp"}, k=2, mv={1, 4, 0})
public final class Util
{
  public static final byte[] EMPTY_BYTE_ARRAY;
  public static final Headers EMPTY_HEADERS;
  public static final RequestBody EMPTY_REQUEST;
  public static final ResponseBody EMPTY_RESPONSE;
  private static final Options UNICODE_BOMS;
  public static final TimeZone UTC;
  private static final Regex VERIFY_AS_IP_ADDRESS;
  public static final boolean assertionsEnabled;
  public static final String okHttpName;
  public static final String userAgent = "okhttp/4.9.0";
  
  static
  {
    Object localObject = new byte[0];
    EMPTY_BYTE_ARRAY = (byte[])localObject;
    EMPTY_HEADERS = Headers.Companion.of(new String[0]);
    EMPTY_RESPONSE = ResponseBody.Companion.create$default(ResponseBody.Companion, (byte[])localObject, null, 1, null);
    EMPTY_REQUEST = RequestBody.Companion.create$default(RequestBody.Companion, (byte[])localObject, null, 0, 0, 7, null);
    UNICODE_BOMS = Options.Companion.of(new ByteString[] { ByteString.Companion.decodeHex("efbbbf"), ByteString.Companion.decodeHex("feff"), ByteString.Companion.decodeHex("fffe"), ByteString.Companion.decodeHex("0000ffff"), ByteString.Companion.decodeHex("ffff0000") });
    localObject = TimeZone.getTimeZone("GMT");
    Intrinsics.checkNotNull(localObject);
    UTC = (TimeZone)localObject;
    VERIFY_AS_IP_ADDRESS = new Regex("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    assertionsEnabled = false;
    localObject = OkHttpClient.class.getName();
    Intrinsics.checkNotNullExpressionValue(localObject, "OkHttpClient::class.java.name");
    localObject = StringsKt.removePrefix((String)localObject, (CharSequence)"okhttp3.");
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    localObject = StringsKt.removeSuffix((String)localObject, (CharSequence)"Client");
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    okHttpName = (String)localObject;
  }
  
  public static final <E> void addIfAbsent(List<E> paramList, E paramE)
  {
    Intrinsics.checkNotNullParameter(paramList, "$this$addIfAbsent");
    if (!paramList.contains(paramE)) {
      paramList.add(paramE);
    }
  }
  
  public static final int and(byte paramByte, int paramInt)
  {
    return paramByte & paramInt;
  }
  
  public static final int and(short paramShort, int paramInt)
  {
    return paramShort & paramInt;
  }
  
  public static final long and(int paramInt, long paramLong)
  {
    return paramInt & paramLong;
  }
  
  public static final EventListener.Factory asFactory(EventListener paramEventListener)
  {
    Intrinsics.checkNotNullParameter(paramEventListener, "$this$asFactory");
    (EventListener.Factory)new EventListener.Factory()
    {
      final EventListener $this_asFactory;
      
      public final EventListener create(Call paramAnonymousCall)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousCall, "it");
        return this.$this_asFactory;
      }
    };
  }
  
  public static final void assertThreadDoesntHoldLock(Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramObject, "$this$assertThreadDoesntHoldLock");
    if ((assertionsEnabled) && (Thread.holdsLock(paramObject)))
    {
      StringBuilder localStringBuilder = new StringBuilder().append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(localThread.getName() + " MUST NOT hold lock on " + paramObject));
    }
  }
  
  public static final void assertThreadHoldsLock(Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramObject, "$this$assertThreadHoldsLock");
    if ((assertionsEnabled) && (!Thread.holdsLock(paramObject)))
    {
      StringBuilder localStringBuilder = new StringBuilder().append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkNotNullExpressionValue(localThread, "Thread.currentThread()");
      throw ((Throwable)new AssertionError(localThread.getName() + " MUST hold lock on " + paramObject));
    }
  }
  
  public static final boolean canParseAsIpAddress(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$canParseAsIpAddress");
    return VERIFY_AS_IP_ADDRESS.matches((CharSequence)paramString);
  }
  
  public static final boolean canReuseConnectionFor(HttpUrl paramHttpUrl1, HttpUrl paramHttpUrl2)
  {
    Intrinsics.checkNotNullParameter(paramHttpUrl1, "$this$canReuseConnectionFor");
    Intrinsics.checkNotNullParameter(paramHttpUrl2, "other");
    boolean bool;
    if ((Intrinsics.areEqual(paramHttpUrl1.host(), paramHttpUrl2.host())) && (paramHttpUrl1.port() == paramHttpUrl2.port()) && (Intrinsics.areEqual(paramHttpUrl1.scheme(), paramHttpUrl2.scheme()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final int checkDuration(String paramString, long paramLong, TimeUnit paramTimeUnit)
  {
    Intrinsics.checkNotNullParameter(paramString, "name");
    int j = 1;
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramTimeUnit != null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        long l = paramTimeUnit.toMillis(paramLong);
        if (l <= Integer.MAX_VALUE) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          i = j;
          if (l == 0L) {
            if (paramLong <= 0L) {
              i = j;
            } else {
              i = 0;
            }
          }
          if (i != 0) {
            return (int)l;
          }
          throw ((Throwable)new IllegalArgumentException((paramString + " too small.").toString()));
        }
        throw ((Throwable)new IllegalArgumentException((paramString + " too large.").toString()));
      }
      throw ((Throwable)new IllegalStateException("unit == null".toString()));
    }
    throw ((Throwable)new IllegalStateException((paramString + " < 0").toString()));
  }
  
  public static final void checkOffsetAndCount(long paramLong1, long paramLong2, long paramLong3)
  {
    if (((paramLong2 | paramLong3) >= 0L) && (paramLong2 <= paramLong1) && (paramLong1 - paramLong2 >= paramLong3)) {
      return;
    }
    throw ((Throwable)new ArrayIndexOutOfBoundsException());
  }
  
  public static final void closeQuietly(Closeable paramCloseable)
  {
    Intrinsics.checkNotNullParameter(paramCloseable, "$this$closeQuietly");
    try
    {
      try
      {
        paramCloseable.close();
      }
      catch (Exception paramCloseable) {}
      return;
    }
    catch (RuntimeException paramCloseable)
    {
      throw ((Throwable)paramCloseable);
    }
  }
  
  public static final void closeQuietly(ServerSocket paramServerSocket)
  {
    Intrinsics.checkNotNullParameter(paramServerSocket, "$this$closeQuietly");
    try
    {
      try
      {
        paramServerSocket.close();
      }
      catch (Exception paramServerSocket) {}
      return;
    }
    catch (RuntimeException paramServerSocket)
    {
      throw ((Throwable)paramServerSocket);
    }
  }
  
  public static final void closeQuietly(Socket paramSocket)
  {
    Intrinsics.checkNotNullParameter(paramSocket, "$this$closeQuietly");
    try
    {
      try
      {
        paramSocket.close();
      }
      catch (Exception paramSocket) {}
      return;
    }
    catch (RuntimeException paramSocket)
    {
      throw ((Throwable)paramSocket);
    }
    catch (AssertionError paramSocket)
    {
      throw ((Throwable)paramSocket);
    }
  }
  
  public static final String[] concat(String[] paramArrayOfString, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfString, "$this$concat");
    Intrinsics.checkNotNullParameter(paramString, "value");
    paramArrayOfString = Arrays.copyOf(paramArrayOfString, paramArrayOfString.length + 1);
    Intrinsics.checkNotNullExpressionValue(paramArrayOfString, "java.util.Arrays.copyOf(this, newSize)");
    paramArrayOfString = (String[])paramArrayOfString;
    paramArrayOfString[kotlin.collections.ArraysKt.getLastIndex(paramArrayOfString)] = paramString;
    if (paramArrayOfString != null) {
      return paramArrayOfString;
    }
    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
  }
  
  public static final int delimiterOffset(String paramString, char paramChar, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$delimiterOffset");
    while (paramInt1 < paramInt2)
    {
      if (paramString.charAt(paramInt1) == paramChar) {
        return paramInt1;
      }
      paramInt1++;
    }
    return paramInt2;
  }
  
  public static final int delimiterOffset(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "$this$delimiterOffset");
    Intrinsics.checkNotNullParameter(paramString2, "delimiters");
    while (paramInt1 < paramInt2)
    {
      if (StringsKt.contains$default((CharSequence)paramString2, paramString1.charAt(paramInt1), false, 2, null)) {
        return paramInt1;
      }
      paramInt1++;
    }
    return paramInt2;
  }
  
  public static final boolean discard(Source paramSource, int paramInt, TimeUnit paramTimeUnit)
  {
    Intrinsics.checkNotNullParameter(paramSource, "$this$discard");
    Intrinsics.checkNotNullParameter(paramTimeUnit, "timeUnit");
    boolean bool;
    try
    {
      bool = skipAll(paramSource, paramInt, paramTimeUnit);
    }
    catch (IOException paramSource)
    {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> List<T> filterList(Iterable<? extends T> paramIterable, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "$this$filterList");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Object localObject1 = CollectionsKt.emptyList();
    Iterator localIterator = paramIterable.iterator();
    for (paramIterable = (Iterable<? extends T>)localObject1; localIterator.hasNext(); paramIterable = (Iterable<? extends T>)localObject1)
    {
      Object localObject2 = localIterator.next();
      localObject1 = paramIterable;
      if (((Boolean)paramFunction1.invoke(localObject2)).booleanValue())
      {
        localObject1 = paramIterable;
        if (paramIterable.isEmpty()) {
          localObject1 = (List)new ArrayList();
        }
        if (localObject1 != null) {
          TypeIntrinsics.asMutableList(localObject1).add(localObject2);
        } else {
          throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
        }
      }
    }
    return paramIterable;
  }
  
  public static final String format(String paramString, Object... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramString, "format");
    Intrinsics.checkNotNullParameter(paramVarArgs, "args");
    Object localObject = StringCompanionObject.INSTANCE;
    localObject = Locale.US;
    paramVarArgs = Arrays.copyOf(paramVarArgs, paramVarArgs.length);
    paramString = String.format((Locale)localObject, paramString, Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "java.lang.String.format(locale, format, *args)");
    return paramString;
  }
  
  public static final boolean hasIntersection(String[] paramArrayOfString1, String[] paramArrayOfString2, Comparator<? super String> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfString1, "$this$hasIntersection");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int i;
    if (paramArrayOfString1.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i == 0) && (paramArrayOfString2 != null))
    {
      if (paramArrayOfString2.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0)
      {
        int k = paramArrayOfString1.length;
        for (i = 0; i < k; i++)
        {
          String str = paramArrayOfString1[i];
          int m = paramArrayOfString2.length;
          for (int j = 0; j < m; j++) {
            if (paramComparator.compare(str, paramArrayOfString2[j]) == 0) {
              return true;
            }
          }
        }
        return false;
      }
    }
    return false;
  }
  
  public static final long headersContentLength(Response paramResponse)
  {
    Intrinsics.checkNotNullParameter(paramResponse, "$this$headersContentLength");
    paramResponse = paramResponse.headers().get("Content-Length");
    long l = -1L;
    if (paramResponse != null) {
      l = toLongOrDefault(paramResponse, -1L);
    }
    return l;
  }
  
  public static final void ignoreIoExceptions(Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    try
    {
      paramFunction0.invoke();
    }
    catch (IOException paramFunction0) {}
  }
  
  @SafeVarargs
  public static final <T> List<T> immutableListOf(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    paramVarArgs = (Object[])paramVarArgs.clone();
    paramVarArgs = Collections.unmodifiableList(CollectionsKt.listOf(Arrays.copyOf(paramVarArgs, paramVarArgs.length)));
    Intrinsics.checkNotNullExpressionValue(paramVarArgs, "Collections.unmodifiable…istOf(*elements.clone()))");
    return paramVarArgs;
  }
  
  public static final int indexOf(String[] paramArrayOfString, String paramString, Comparator<String> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfString, "$this$indexOf");
    Intrinsics.checkNotNullParameter(paramString, "value");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int k = paramArrayOfString.length;
    for (int i = 0; i < k; i++)
    {
      int j;
      if (paramComparator.compare(paramArrayOfString[i], paramString) == 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0) {
        return i;
      }
    }
    i = -1;
    return i;
  }
  
  public static final int indexOfControlOrNonAscii(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$indexOfControlOrNonAscii");
    int j = paramString.length();
    int i = 0;
    while (i < j)
    {
      int k = paramString.charAt(i);
      if ((Intrinsics.compare(k, 31) > 0) && (Intrinsics.compare(k, 127) < 0)) {
        i++;
      } else {
        return i;
      }
    }
    return -1;
  }
  
  public static final int indexOfFirstNonAsciiWhitespace(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$indexOfFirstNonAsciiWhitespace");
    while (paramInt1 < paramInt2)
    {
      switch (paramString.charAt(paramInt1))
      {
      default: 
        return paramInt1;
      }
      paramInt1++;
    }
    return paramInt2;
  }
  
  public static final int indexOfLastNonAsciiWhitespace(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$indexOfLastNonAsciiWhitespace");
    paramInt2--;
    if (paramInt2 >= paramInt1) {
      for (;;)
      {
        switch (paramString.charAt(paramInt2))
        {
        default: 
          return paramInt2 + 1;
        }
        if (paramInt2 == paramInt1) {
          break;
        }
        paramInt2--;
      }
    }
    return paramInt1;
  }
  
  public static final int indexOfNonWhitespace(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$indexOfNonWhitespace");
    int i = paramString.length();
    while (paramInt < i)
    {
      int j = paramString.charAt(paramInt);
      if ((j != 32) && (j != 9)) {
        return paramInt;
      }
      paramInt++;
    }
    return paramString.length();
  }
  
  public static final String[] intersect(String[] paramArrayOfString1, String[] paramArrayOfString2, Comparator<? super String> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfString1, "$this$intersect");
    Intrinsics.checkNotNullParameter(paramArrayOfString2, "other");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    List localList = (List)new ArrayList();
    int k = paramArrayOfString1.length;
    for (int i = 0; i < k; i++)
    {
      String str = paramArrayOfString1[i];
      int m = paramArrayOfString2.length;
      for (int j = 0; j < m; j++) {
        if (paramComparator.compare(str, paramArrayOfString2[j]) == 0)
        {
          localList.add(str);
          break;
        }
      }
    }
    paramArrayOfString1 = ((Collection)localList).toArray(new String[0]);
    if (paramArrayOfString1 != null) {
      return (String[])paramArrayOfString1;
    }
    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
  }
  
  /* Error */
  public static final boolean isCivilized(okhttp3.internal.io.FileSystem paramFileSystem, java.io.File paramFile)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w 679
    //   4: invokestatic 316	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_1
    //   8: ldc_w 680
    //   11: invokestatic 316	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   14: aload_0
    //   15: aload_1
    //   16: invokeinterface 686 2 0
    //   21: checkcast 443	java/io/Closeable
    //   24: astore_2
    //   25: aconst_null
    //   26: checkcast 375	java/lang/Throwable
    //   29: astore_3
    //   30: aload_2
    //   31: checkcast 688	okio/Sink
    //   34: astore_3
    //   35: aload_0
    //   36: aload_1
    //   37: invokeinterface 692 2 0
    //   42: aload_2
    //   43: aconst_null
    //   44: invokestatic 698	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   47: iconst_1
    //   48: ireturn
    //   49: astore_3
    //   50: getstatic 703	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   53: astore_3
    //   54: aload_2
    //   55: aconst_null
    //   56: invokestatic 698	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   59: aload_0
    //   60: aload_1
    //   61: invokeinterface 692 2 0
    //   66: iconst_0
    //   67: ireturn
    //   68: astore_0
    //   69: aload_0
    //   70: athrow
    //   71: astore_1
    //   72: aload_2
    //   73: aload_0
    //   74: invokestatic 698	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   77: aload_1
    //   78: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	paramFileSystem	okhttp3.internal.io.FileSystem
    //   0	79	1	paramFile	java.io.File
    //   24	49	2	localCloseable	Closeable
    //   29	6	3	localObject	Object
    //   49	1	3	localIOException	IOException
    //   53	1	3	localUnit	Unit
    // Exception table:
    //   from	to	target	type
    //   35	42	49	java/io/IOException
    //   30	35	68	finally
    //   35	42	68	finally
    //   50	54	68	finally
    //   69	71	71	finally
  }
  
  /* Error */
  public static final boolean isHealthy(Socket paramSocket, BufferedSource paramBufferedSource)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w 708
    //   4: invokestatic 316	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_1
    //   8: ldc_w 709
    //   11: invokestatic 316	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   14: iconst_1
    //   15: istore_3
    //   16: aload_0
    //   17: invokevirtual 712	java/net/Socket:getSoTimeout	()I
    //   20: istore_2
    //   21: aload_0
    //   22: iconst_1
    //   23: invokevirtual 716	java/net/Socket:setSoTimeout	(I)V
    //   26: aload_1
    //   27: invokeinterface 721 1 0
    //   32: istore 4
    //   34: aload_0
    //   35: iload_2
    //   36: invokevirtual 716	java/net/Socket:setSoTimeout	(I)V
    //   39: iload 4
    //   41: iconst_1
    //   42: ixor
    //   43: istore_3
    //   44: goto +18 -> 62
    //   47: astore_1
    //   48: aload_0
    //   49: iload_2
    //   50: invokevirtual 716	java/net/Socket:setSoTimeout	(I)V
    //   53: aload_1
    //   54: athrow
    //   55: astore_0
    //   56: iconst_0
    //   57: istore_3
    //   58: goto +4 -> 62
    //   61: astore_0
    //   62: iload_3
    //   63: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	paramSocket	Socket
    //   0	64	1	paramBufferedSource	BufferedSource
    //   20	30	2	i	int
    //   15	48	3	bool1	boolean
    //   32	11	4	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   21	34	47	finally
    //   16	21	55	java/io/IOException
    //   34	39	55	java/io/IOException
    //   48	55	55	java/io/IOException
    //   16	21	61	java/net/SocketTimeoutException
    //   34	39	61	java/net/SocketTimeoutException
    //   48	55	61	java/net/SocketTimeoutException
  }
  
  public static final void notify(Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramObject, "$this$notify");
    paramObject.notify();
  }
  
  public static final void notifyAll(Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramObject, "$this$notifyAll");
    paramObject.notifyAll();
  }
  
  public static final int parseHexDigit(char paramChar)
  {
    if (('0' <= paramChar) && ('9' >= paramChar)) {
      paramChar -= 48;
    } else if (('a' <= paramChar) && ('f' >= paramChar)) {
      paramChar = paramChar - 'a' + 10;
    } else if (('A' <= paramChar) && ('F' >= paramChar)) {
      paramChar = paramChar - 'A' + 10;
    } else {
      paramChar = '￿';
    }
    return paramChar;
  }
  
  public static final String peerName(Socket paramSocket)
  {
    Intrinsics.checkNotNullParameter(paramSocket, "$this$peerName");
    paramSocket = paramSocket.getRemoteSocketAddress();
    if ((paramSocket instanceof InetSocketAddress))
    {
      paramSocket = ((InetSocketAddress)paramSocket).getHostName();
      Intrinsics.checkNotNullExpressionValue(paramSocket, "address.hostName");
    }
    else
    {
      paramSocket = paramSocket.toString();
    }
    return paramSocket;
  }
  
  public static final Charset readBomAsCharset(BufferedSource paramBufferedSource, Charset paramCharset)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBufferedSource, "$this$readBomAsCharset");
    Intrinsics.checkNotNullParameter(paramCharset, "default");
    switch (paramBufferedSource.select(UNICODE_BOMS))
    {
    default: 
      throw ((Throwable)new AssertionError());
    case 4: 
      paramBufferedSource = Charsets.INSTANCE.UTF32_LE();
      break;
    case 3: 
      paramBufferedSource = Charsets.INSTANCE.UTF32_BE();
      break;
    case 2: 
      paramBufferedSource = StandardCharsets.UTF_16LE;
      Intrinsics.checkNotNullExpressionValue(paramBufferedSource, "UTF_16LE");
      break;
    case 1: 
      paramBufferedSource = StandardCharsets.UTF_16BE;
      Intrinsics.checkNotNullExpressionValue(paramBufferedSource, "UTF_16BE");
      break;
    case 0: 
      paramBufferedSource = StandardCharsets.UTF_8;
      Intrinsics.checkNotNullExpressionValue(paramBufferedSource, "UTF_8");
      break;
    case -1: 
      paramBufferedSource = paramCharset;
    }
    return paramBufferedSource;
  }
  
  public static final <T> T readFieldOrNull(Object paramObject, Class<T> paramClass, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramObject, "instance");
    Intrinsics.checkNotNullParameter(paramClass, "fieldType");
    Intrinsics.checkNotNullParameter(paramString, "fieldName");
    Class localClass = paramObject.getClass();
    for (;;)
    {
      boolean bool = Intrinsics.areEqual(localClass, Object.class);
      Object localObject1 = null;
      if (!(bool ^ true)) {
        break;
      }
      try
      {
        Object localObject2 = localClass.getDeclaredField(paramString);
        Intrinsics.checkNotNullExpressionValue(localObject2, "field");
        ((Field)localObject2).setAccessible(true);
        localObject2 = ((Field)localObject2).get(paramObject);
        if (!paramClass.isInstance(localObject2))
        {
          paramObject = localObject1;
        }
        else
        {
          localObject1 = paramClass.cast(localObject2);
          paramObject = localObject1;
        }
        return (T)paramObject;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        localClass = localClass.getSuperclass();
        Intrinsics.checkNotNullExpressionValue(localClass, "c.superclass");
      }
    }
    if ((true ^ Intrinsics.areEqual(paramString, "delegate")))
    {
      paramObject = readFieldOrNull(paramObject, Object.class, "delegate");
      if (paramObject != null) {
        return (T)readFieldOrNull(paramObject, paramClass, paramString);
      }
    }
    return null;
  }
  
  public static final int readMedium(BufferedSource paramBufferedSource)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBufferedSource, "$this$readMedium");
    return and(paramBufferedSource.readByte(), 255) << 16 | and(paramBufferedSource.readByte(), 255) << 8 | and(paramBufferedSource.readByte(), 255);
  }
  
  public static final int skipAll(Buffer paramBuffer, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramBuffer, "$this$skipAll");
    int i = 0;
    while ((!paramBuffer.exhausted()) && (paramBuffer.getByte(0L) == paramByte))
    {
      i++;
      paramBuffer.readByte();
    }
    return i;
  }
  
  /* Error */
  public static final boolean skipAll(Source paramSource, int paramInt, TimeUnit paramTimeUnit)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w 834
    //   4: invokestatic 316	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_2
    //   8: ldc_w 507
    //   11: invokestatic 316	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   14: invokestatic 850	java/lang/System:nanoTime	()J
    //   17: lstore 6
    //   19: aload_0
    //   20: invokeinterface 855 1 0
    //   25: invokevirtual 860	okio/Timeout:hasDeadline	()Z
    //   28: ifeq +20 -> 48
    //   31: aload_0
    //   32: invokeinterface 855 1 0
    //   37: invokevirtual 863	okio/Timeout:deadlineNanoTime	()J
    //   40: lload 6
    //   42: lsub
    //   43: lstore 4
    //   45: goto +8 -> 53
    //   48: ldc2_w 864
    //   51: lstore 4
    //   53: aload_0
    //   54: invokeinterface 855 1 0
    //   59: lload 4
    //   61: aload_2
    //   62: iload_1
    //   63: i2l
    //   64: invokevirtual 868	java/util/concurrent/TimeUnit:toNanos	(J)J
    //   67: invokestatic 874	java/lang/Math:min	(JJ)J
    //   70: lload 6
    //   72: ladd
    //   73: invokevirtual 877	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
    //   76: pop
    //   77: new 836	okio/Buffer
    //   80: astore_2
    //   81: aload_2
    //   82: invokespecial 878	okio/Buffer:<init>	()V
    //   85: aload_0
    //   86: aload_2
    //   87: ldc2_w 879
    //   90: invokeinterface 884 4 0
    //   95: ldc2_w 606
    //   98: lcmp
    //   99: ifeq +10 -> 109
    //   102: aload_2
    //   103: invokevirtual 887	okio/Buffer:clear	()V
    //   106: goto -21 -> 85
    //   109: iconst_1
    //   110: istore_3
    //   111: lload 4
    //   113: ldc2_w 864
    //   116: lcmp
    //   117: ifne +16 -> 133
    //   120: aload_0
    //   121: invokeinterface 855 1 0
    //   126: invokevirtual 890	okio/Timeout:clearDeadline	()Lokio/Timeout;
    //   129: pop
    //   130: goto +18 -> 148
    //   133: aload_0
    //   134: invokeinterface 855 1 0
    //   139: lload 6
    //   141: lload 4
    //   143: ladd
    //   144: invokevirtual 877	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
    //   147: pop
    //   148: goto +83 -> 231
    //   151: astore_2
    //   152: lload 4
    //   154: ldc2_w 864
    //   157: lcmp
    //   158: ifne +16 -> 174
    //   161: aload_0
    //   162: invokeinterface 855 1 0
    //   167: invokevirtual 890	okio/Timeout:clearDeadline	()Lokio/Timeout;
    //   170: pop
    //   171: goto +18 -> 189
    //   174: aload_0
    //   175: invokeinterface 855 1 0
    //   180: lload 6
    //   182: lload 4
    //   184: ladd
    //   185: invokevirtual 877	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
    //   188: pop
    //   189: aload_2
    //   190: athrow
    //   191: astore_2
    //   192: lload 4
    //   194: ldc2_w 864
    //   197: lcmp
    //   198: ifne +16 -> 214
    //   201: aload_0
    //   202: invokeinterface 855 1 0
    //   207: invokevirtual 890	okio/Timeout:clearDeadline	()Lokio/Timeout;
    //   210: pop
    //   211: goto +18 -> 229
    //   214: aload_0
    //   215: invokeinterface 855 1 0
    //   220: lload 6
    //   222: lload 4
    //   224: ladd
    //   225: invokevirtual 877	okio/Timeout:deadlineNanoTime	(J)Lokio/Timeout;
    //   228: pop
    //   229: iconst_0
    //   230: istore_3
    //   231: iload_3
    //   232: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	233	0	paramSource	Source
    //   0	233	1	paramInt	int
    //   0	233	2	paramTimeUnit	TimeUnit
    //   110	122	3	bool	boolean
    //   43	180	4	l1	long
    //   17	204	6	l2	long
    // Exception table:
    //   from	to	target	type
    //   77	85	151	finally
    //   85	106	151	finally
    //   77	85	191	java/io/InterruptedIOException
    //   85	106	191	java/io/InterruptedIOException
  }
  
  public static final ThreadFactory threadFactory(String paramString, final boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString, "name");
    (ThreadFactory)new ThreadFactory()
    {
      final String $name;
      
      public final Thread newThread(Runnable paramAnonymousRunnable)
      {
        paramAnonymousRunnable = new Thread(paramAnonymousRunnable, this.$name);
        paramAnonymousRunnable.setDaemon(paramBoolean);
        return paramAnonymousRunnable;
      }
    };
  }
  
  public static final void threadName(String paramString, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramString, "name");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    Thread localThread = Thread.currentThread();
    Intrinsics.checkNotNullExpressionValue(localThread, "currentThread");
    String str = localThread.getName();
    localThread.setName(paramString);
    try
    {
      paramFunction0.invoke();
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      localThread.setName(str);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final List<Header> toHeaderList(Headers paramHeaders)
  {
    Intrinsics.checkNotNullParameter(paramHeaders, "$this$toHeaderList");
    Object localObject = (Iterable)RangesKt.until(0, paramHeaders.size());
    Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      int i = ((IntIterator)localObject).nextInt();
      localCollection.add(new Header(paramHeaders.name(i), paramHeaders.value(i)));
    }
    paramHeaders = (List)localCollection;
    return paramHeaders;
  }
  
  public static final Headers toHeaders(List<Header> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "$this$toHeaders");
    Headers.Builder localBuilder = new Headers.Builder();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Header)localIterator.next();
      paramList = ((Header)localObject).component1();
      localObject = ((Header)localObject).component2();
      localBuilder.addLenient$okhttp(paramList.utf8(), ((ByteString)localObject).utf8());
    }
    return localBuilder.build();
  }
  
  public static final String toHexString(int paramInt)
  {
    String str = Integer.toHexString(paramInt);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNullExpressionValue(str, "Integer.toHexString(this)");
    return str;
  }
  
  public static final String toHexString(long paramLong)
  {
    String str = Long.toHexString(paramLong);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNullExpressionValue(str, "java.lang.Long.toHexString(this)");
    return str;
  }
  
  public static final String toHostHeader(HttpUrl paramHttpUrl, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramHttpUrl, "$this$toHostHeader");
    if (StringsKt.contains$default((CharSequence)paramHttpUrl.host(), (CharSequence)":", false, 2, null)) {
      str = '[' + paramHttpUrl.host() + ']';
    } else {
      str = paramHttpUrl.host();
    }
    if ((!paramBoolean) && (paramHttpUrl.port() == HttpUrl.Companion.defaultPort(paramHttpUrl.scheme()))) {
      return str;
    }
    String str = str + ':' + paramHttpUrl.port();
    return str;
  }
  
  public static final <T> List<T> toImmutableList(List<? extends T> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "$this$toImmutableList");
    paramList = Collections.unmodifiableList(CollectionsKt.toMutableList((Collection)paramList));
    Intrinsics.checkNotNullExpressionValue(paramList, "Collections.unmodifiableList(toMutableList())");
    return paramList;
  }
  
  public static final <K, V> Map<K, V> toImmutableMap(Map<K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "$this$toImmutableMap");
    if (paramMap.isEmpty())
    {
      paramMap = MapsKt.emptyMap();
    }
    else
    {
      paramMap = Collections.unmodifiableMap((Map)new LinkedHashMap(paramMap));
      Intrinsics.checkNotNullExpressionValue(paramMap, "Collections.unmodifiableMap(LinkedHashMap(this))");
    }
    return paramMap;
  }
  
  public static final long toLongOrDefault(String paramString, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$toLongOrDefault");
    try
    {
      long l = Long.parseLong(paramString);
      paramLong = l;
    }
    catch (NumberFormatException paramString) {}
    return paramLong;
  }
  
  public static final int toNonNegativeInt(String paramString, int paramInt)
  {
    if (paramString != null) {
      try
      {
        long l = Long.parseLong(paramString);
        paramInt = Integer.MAX_VALUE;
        if (l <= Integer.MAX_VALUE) {
          if (l < 0L) {
            paramInt = 0;
          } else {
            paramInt = (int)l;
          }
        }
        return paramInt;
      }
      catch (NumberFormatException paramString)
      {
        return paramInt;
      }
    }
    return paramInt;
  }
  
  public static final String trimSubstring(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$trimSubstring");
    paramInt1 = indexOfFirstNonAsciiWhitespace(paramString, paramInt1, paramInt2);
    paramString = paramString.substring(paramInt1, indexOfLastNonAsciiWhitespace(paramString, paramInt1, paramInt2));
    Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.Strin…ing(startIndex, endIndex)");
    return paramString;
  }
  
  public static final void wait(Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramObject, "$this$wait");
    paramObject.wait();
  }
  
  public static final Throwable withSuppressed(Exception paramException, List<? extends Exception> paramList)
  {
    Intrinsics.checkNotNullParameter(paramException, "$this$withSuppressed");
    Intrinsics.checkNotNullParameter(paramList, "suppressed");
    if (paramList.size() > 1) {
      System.out.println(paramList);
    }
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Exception localException = (Exception)paramList.next();
      ExceptionsKt.addSuppressed((Throwable)paramException, (Throwable)localException);
    }
    return (Throwable)paramException;
  }
  
  public static final void writeMedium(BufferedSink paramBufferedSink, int paramInt)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramBufferedSink, "$this$writeMedium");
    paramBufferedSink.writeByte(paramInt >>> 16 & 0xFF);
    paramBufferedSink.writeByte(paramInt >>> 8 & 0xFF);
    paramBufferedSink.writeByte(paramInt & 0xFF);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */