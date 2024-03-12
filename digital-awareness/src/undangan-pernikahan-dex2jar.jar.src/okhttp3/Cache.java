package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.DiskLruCache.Editor;
import okhttp3.internal.cache.DiskLruCache.Snapshot;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.http.StatusLine.Companion;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ByteString.Companion;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\000r\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\b\n\000\n\002\020\013\n\002\b\f\n\002\020\002\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\006\n\002\020)\n\002\020\016\n\002\b\005\030\000 C2\0020\0012\0020\002:\004BCDEB\027\b\026\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006¢\006\002\020\007B\037\b\000\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006\022\006\020\b\032\0020\t¢\006\002\020\nJ\026\020\037\032\0020 2\f\020!\032\b\030\0010\"R\0020\fH\002J\b\020#\032\0020 H\026J\006\020$\032\0020 J\r\020\003\032\0020\004H\007¢\006\002\b%J\006\020&\032\0020 J\b\020'\032\0020 H\026J\027\020(\032\004\030\0010)2\006\020*\032\0020+H\000¢\006\002\b,J\006\020\020\032\0020\021J\006\020-\032\0020 J\006\020\005\032\0020\006J\006\020\025\032\0020\021J\027\020.\032\004\030\0010/2\006\0200\032\0020)H\000¢\006\002\b1J\025\0202\032\0020 2\006\020*\032\0020+H\000¢\006\002\b3J\006\020\026\032\0020\021J\006\0204\032\0020\006J\r\0205\032\0020 H\000¢\006\002\b6J\025\0207\032\0020 2\006\0208\032\00209H\000¢\006\002\b:J\035\020;\032\0020 2\006\020<\032\0020)2\006\020=\032\0020)H\000¢\006\002\b>J\f\020?\032\b\022\004\022\0020A0@J\006\020\027\032\0020\021J\006\020\034\032\0020\021R\024\020\013\032\0020\fX\004¢\006\b\n\000\032\004\b\r\020\016R\021\020\003\032\0020\0048G¢\006\006\032\004\b\003\020\017R\016\020\020\032\0020\021X\016¢\006\002\n\000R\021\020\022\032\0020\0238F¢\006\006\032\004\b\022\020\024R\016\020\025\032\0020\021X\016¢\006\002\n\000R\016\020\026\032\0020\021X\016¢\006\002\n\000R\032\020\027\032\0020\021X\016¢\006\016\n\000\032\004\b\030\020\031\"\004\b\032\020\033R\032\020\034\032\0020\021X\016¢\006\016\n\000\032\004\b\035\020\031\"\004\b\036\020\033¨\006F"}, d2={"Lokhttp3/Cache;", "Ljava/io/Closeable;", "Ljava/io/Flushable;", "directory", "Ljava/io/File;", "maxSize", "", "(Ljava/io/File;J)V", "fileSystem", "Lokhttp3/internal/io/FileSystem;", "(Ljava/io/File;JLokhttp3/internal/io/FileSystem;)V", "cache", "Lokhttp3/internal/cache/DiskLruCache;", "getCache$okhttp", "()Lokhttp3/internal/cache/DiskLruCache;", "()Ljava/io/File;", "hitCount", "", "isClosed", "", "()Z", "networkCount", "requestCount", "writeAbortCount", "getWriteAbortCount$okhttp", "()I", "setWriteAbortCount$okhttp", "(I)V", "writeSuccessCount", "getWriteSuccessCount$okhttp", "setWriteSuccessCount$okhttp", "abortQuietly", "", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "close", "delete", "-deprecated_directory", "evictAll", "flush", "get", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "get$okhttp", "initialize", "put", "Lokhttp3/internal/cache/CacheRequest;", "response", "put$okhttp", "remove", "remove$okhttp", "size", "trackConditionalCacheHit", "trackConditionalCacheHit$okhttp", "trackResponse", "cacheStrategy", "Lokhttp3/internal/cache/CacheStrategy;", "trackResponse$okhttp", "update", "cached", "network", "update$okhttp", "urls", "", "", "CacheResponseBody", "Companion", "Entry", "RealCacheRequest", "okhttp"}, k=1, mv={1, 4, 0})
public final class Cache
  implements Closeable, Flushable
{
  public static final Companion Companion = new Companion(null);
  private static final int ENTRY_BODY = 1;
  private static final int ENTRY_COUNT = 2;
  private static final int ENTRY_METADATA = 0;
  private static final int VERSION = 201105;
  private final DiskLruCache cache;
  private int hitCount;
  private int networkCount;
  private int requestCount;
  private int writeAbortCount;
  private int writeSuccessCount;
  
  public Cache(File paramFile, long paramLong)
  {
    this(paramFile, paramLong, FileSystem.SYSTEM);
  }
  
  public Cache(File paramFile, long paramLong, FileSystem paramFileSystem)
  {
    this.cache = new DiskLruCache(paramFileSystem, paramFile, 201105, 2, paramLong, TaskRunner.INSTANCE);
  }
  
  private final void abortQuietly(DiskLruCache.Editor paramEditor)
  {
    if (paramEditor != null) {
      try
      {
        paramEditor.abort();
      }
      catch (IOException paramEditor) {}
    }
  }
  
  @JvmStatic
  public static final String key(HttpUrl paramHttpUrl)
  {
    return Companion.key(paramHttpUrl);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="directory", imports={}))
  public final File -deprecated_directory()
  {
    return this.cache.getDirectory();
  }
  
  public void close()
    throws IOException
  {
    this.cache.close();
  }
  
  public final void delete()
    throws IOException
  {
    this.cache.delete();
  }
  
  public final File directory()
  {
    return this.cache.getDirectory();
  }
  
  public final void evictAll()
    throws IOException
  {
    this.cache.evictAll();
  }
  
  public void flush()
    throws IOException
  {
    this.cache.flush();
  }
  
  public final Response get$okhttp(Request paramRequest)
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    Object localObject = Companion.key(paramRequest.url());
    try
    {
      localObject = this.cache.get((String)localObject);
      if (localObject != null) {
        try
        {
          Entry localEntry = new Entry(((DiskLruCache.Snapshot)localObject).getSource(0));
          localObject = localEntry.response((DiskLruCache.Snapshot)localObject);
          if (!localEntry.matches(paramRequest, (Response)localObject))
          {
            paramRequest = ((Response)localObject).body();
            if (paramRequest != null) {
              Util.closeQuietly((Closeable)paramRequest);
            }
            return null;
          }
          return (Response)localObject;
        }
        catch (IOException paramRequest)
        {
          Util.closeQuietly((Closeable)localObject);
          return null;
        }
      }
      return null;
    }
    catch (IOException paramRequest) {}
    return null;
  }
  
  public final DiskLruCache getCache$okhttp()
  {
    return this.cache;
  }
  
  public final int getWriteAbortCount$okhttp()
  {
    return this.writeAbortCount;
  }
  
  public final int getWriteSuccessCount$okhttp()
  {
    return this.writeSuccessCount;
  }
  
  public final int hitCount()
  {
    try
    {
      int i = this.hitCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void initialize()
    throws IOException
  {
    this.cache.initialize();
  }
  
  public final boolean isClosed()
  {
    return this.cache.isClosed();
  }
  
  public final long maxSize()
  {
    return this.cache.getMaxSize();
  }
  
  public final int networkCount()
  {
    try
    {
      int i = this.networkCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final CacheRequest put$okhttp(Response paramResponse)
  {
    Intrinsics.checkNotNullParameter(paramResponse, "response");
    Object localObject1 = paramResponse.request().method();
    if (HttpMethod.INSTANCE.invalidatesCache(paramResponse.request().method()))
    {
      try
      {
        remove$okhttp(paramResponse.request());
      }
      catch (IOException paramResponse) {}
      return null;
    }
    if ((Intrinsics.areEqual(localObject1, "GET") ^ true)) {
      return null;
    }
    Companion localCompanion = Companion;
    if (localCompanion.hasVaryAll(paramResponse)) {
      return null;
    }
    Object localObject2 = new Entry(paramResponse);
    localObject1 = (DiskLruCache.Editor)null;
    try
    {
      paramResponse = DiskLruCache.edit$default(this.cache, localCompanion.key(paramResponse.request().url()), 0L, 2, null);
      if (paramResponse != null)
      {
        localObject1 = paramResponse;
        ((Entry)localObject2).writeTo(paramResponse);
        localObject1 = paramResponse;
        localObject2 = new okhttp3/Cache$RealCacheRequest;
        localObject1 = paramResponse;
        ((RealCacheRequest)localObject2).<init>(this, paramResponse);
        localObject1 = paramResponse;
        paramResponse = (CacheRequest)localObject2;
        return paramResponse;
      }
      return null;
    }
    catch (IOException paramResponse)
    {
      abortQuietly((DiskLruCache.Editor)localObject1);
    }
    return null;
  }
  
  public final void remove$okhttp(Request paramRequest)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramRequest, "request");
    this.cache.remove(Companion.key(paramRequest.url()));
  }
  
  public final int requestCount()
  {
    try
    {
      int i = this.requestCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void setWriteAbortCount$okhttp(int paramInt)
  {
    this.writeAbortCount = paramInt;
  }
  
  public final void setWriteSuccessCount$okhttp(int paramInt)
  {
    this.writeSuccessCount = paramInt;
  }
  
  public final long size()
    throws IOException
  {
    return this.cache.size();
  }
  
  public final void trackConditionalCacheHit$okhttp()
  {
    try
    {
      this.hitCount += 1;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void trackResponse$okhttp(CacheStrategy paramCacheStrategy)
  {
    try
    {
      Intrinsics.checkNotNullParameter(paramCacheStrategy, "cacheStrategy");
      this.requestCount += 1;
      if (paramCacheStrategy.getNetworkRequest() != null) {
        this.networkCount += 1;
      } else if (paramCacheStrategy.getCacheResponse() != null) {
        this.hitCount += 1;
      }
      return;
    }
    finally {}
  }
  
  public final void update$okhttp(Response paramResponse1, Response paramResponse2)
  {
    Intrinsics.checkNotNullParameter(paramResponse1, "cached");
    Intrinsics.checkNotNullParameter(paramResponse2, "network");
    Entry localEntry = new Entry(paramResponse2);
    paramResponse1 = paramResponse1.body();
    if (paramResponse1 != null)
    {
      paramResponse2 = ((CacheResponseBody)paramResponse1).getSnapshot();
      paramResponse1 = (DiskLruCache.Editor)null;
      try
      {
        paramResponse2 = paramResponse2.edit();
        if (paramResponse2 != null)
        {
          paramResponse1 = paramResponse2;
          localEntry.writeTo(paramResponse2);
          paramResponse1 = paramResponse2;
          paramResponse2.commit();
        }
        else
        {
          return;
        }
      }
      catch (IOException paramResponse2)
      {
        abortQuietly(paramResponse1);
      }
      return;
    }
    throw new NullPointerException("null cannot be cast to non-null type okhttp3.Cache.CacheResponseBody");
  }
  
  public final Iterator<String> urls()
    throws IOException
  {
    (Iterator)new Iterator()
    {
      private boolean canRemove;
      private final Iterator<DiskLruCache.Snapshot> delegate;
      private String nextUrl;
      final Cache this$0;
      
      public boolean hasNext()
      {
        if (this.nextUrl != null) {
          return true;
        }
        this.canRemove = false;
        for (;;)
        {
          if (this.delegate.hasNext()) {
            try
            {
              Closeable localCloseable = (Closeable)this.delegate.next();
              Throwable localThrowable1 = (Throwable)null;
              try
              {
                this.nextUrl = Okio.buffer(((DiskLruCache.Snapshot)localCloseable).getSource(0)).readUtf8LineStrict();
                CloseableKt.closeFinally(localCloseable, null);
                return true;
              }
              finally
              {
                try
                {
                  throw localThrowable2;
                }
                finally
                {
                  CloseableKt.closeFinally(localCloseable, localThrowable2);
                }
              }
            }
            catch (IOException localIOException) {}
          }
        }
        return false;
      }
      
      public String next()
      {
        if (hasNext())
        {
          String str1 = this.nextUrl;
          Intrinsics.checkNotNull(str1);
          String str2 = (String)null;
          this.nextUrl = null;
          this.canRemove = true;
          return str1;
        }
        throw ((Throwable)new NoSuchElementException());
      }
      
      public void remove()
      {
        if (this.canRemove)
        {
          this.delegate.remove();
          return;
        }
        throw ((Throwable)new IllegalStateException("remove() before next()".toString()));
      }
    };
  }
  
  public final int writeAbortCount()
  {
    try
    {
      int i = this.writeAbortCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int writeSuccessCount()
  {
    try
    {
      int i = this.writeSuccessCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\t\n\002\030\002\n\002\b\002\b\002\030\0002\0020\001B%\022\n\020\002\032\0060\003R\0020\004\022\b\020\005\032\004\030\0010\006\022\b\020\007\032\004\030\0010\006¢\006\002\020\bJ\b\020\007\032\0020\rH\026J\n\020\005\032\004\030\0010\016H\026J\b\020\017\032\0020\nH\026R\016\020\t\032\0020\nX\004¢\006\002\n\000R\020\020\007\032\004\030\0010\006X\004¢\006\002\n\000R\020\020\005\032\004\030\0010\006X\004¢\006\002\n\000R\025\020\002\032\0060\003R\0020\004¢\006\b\n\000\032\004\b\013\020\f¨\006\020"}, d2={"Lokhttp3/Cache$CacheResponseBody;", "Lokhttp3/ResponseBody;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "contentType", "", "contentLength", "(Lokhttp3/internal/cache/DiskLruCache$Snapshot;Ljava/lang/String;Ljava/lang/String;)V", "bodySource", "Lokio/BufferedSource;", "getSnapshot", "()Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "", "Lokhttp3/MediaType;", "source", "okhttp"}, k=1, mv={1, 4, 0})
  private static final class CacheResponseBody
    extends ResponseBody
  {
    private final BufferedSource bodySource;
    private final String contentLength;
    private final String contentType;
    private final DiskLruCache.Snapshot snapshot;
    
    public CacheResponseBody(final DiskLruCache.Snapshot paramSnapshot, String paramString1, String paramString2)
    {
      this.snapshot = paramSnapshot;
      this.contentType = paramString1;
      this.contentLength = paramString2;
      paramSnapshot = paramSnapshot.getSource(1);
      this.bodySource = Okio.buffer((Source)new ForwardingSource(paramSnapshot)
      {
        final Cache.CacheResponseBody this$0;
        
        public void close()
          throws IOException
        {
          this.this$0.getSnapshot().close();
          super.close();
        }
      });
    }
    
    public long contentLength()
    {
      String str = this.contentLength;
      long l = -1L;
      if (str != null) {
        l = Util.toLongOrDefault(str, -1L);
      }
      return l;
    }
    
    public MediaType contentType()
    {
      Object localObject = this.contentType;
      if (localObject != null) {
        localObject = MediaType.Companion.parse((String)localObject);
      } else {
        localObject = null;
      }
      return (MediaType)localObject;
    }
    
    public final DiskLruCache.Snapshot getSnapshot()
    {
      return this.snapshot;
    }
    
    public BufferedSource source()
    {
      return this.bodySource;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000N\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020\016\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\"\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\007J\025\020\f\032\0020\0042\006\020\r\032\0020\016H\000¢\006\002\b\017J\030\020\020\032\0020\0212\006\020\022\032\0020\0212\006\020\023\032\0020\021H\002J\036\020\024\032\0020\0252\006\020\026\032\0020\0272\006\020\030\032\0020\0212\006\020\031\032\0020\032J\n\020\033\032\0020\025*\0020\027J\022\020\034\032\b\022\004\022\0020\t0\035*\0020\021H\002J\n\020\020\032\0020\021*\0020\027R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000¨\006\036"}, d2={"Lokhttp3/Cache$Companion;", "", "()V", "ENTRY_BODY", "", "ENTRY_COUNT", "ENTRY_METADATA", "VERSION", "key", "", "url", "Lokhttp3/HttpUrl;", "readInt", "source", "Lokio/BufferedSource;", "readInt$okhttp", "varyHeaders", "Lokhttp3/Headers;", "requestHeaders", "responseHeaders", "varyMatches", "", "cachedResponse", "Lokhttp3/Response;", "cachedRequest", "newRequest", "Lokhttp3/Request;", "hasVaryAll", "varyFields", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final Set<String> varyFields(Headers paramHeaders)
    {
      Object localObject1 = (Set)null;
      int j = paramHeaders.size();
      label160:
      for (int i = 0; i < j; i++) {
        if (StringsKt.equals("Vary", paramHeaders.name(i), true))
        {
          Object localObject3 = paramHeaders.value(i);
          Object localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = (Set)new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
          }
          localObject3 = StringsKt.split$default((CharSequence)localObject3, new char[] { ',' }, false, 0, 6, null).iterator();
          for (;;)
          {
            localObject1 = localObject2;
            if (!((Iterator)localObject3).hasNext()) {
              break label160;
            }
            localObject1 = (String)((Iterator)localObject3).next();
            if (localObject1 == null) {
              break;
            }
            ((Set)localObject2).add(StringsKt.trim((CharSequence)localObject1).toString());
          }
          throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
        }
      }
      if (localObject1 != null) {
        paramHeaders = (Headers)localObject1;
      } else {
        paramHeaders = SetsKt.emptySet();
      }
      return paramHeaders;
    }
    
    private final Headers varyHeaders(Headers paramHeaders1, Headers paramHeaders2)
    {
      Set localSet = ((Companion)this).varyFields(paramHeaders2);
      if (localSet.isEmpty()) {
        return Util.EMPTY_HEADERS;
      }
      Headers.Builder localBuilder = new Headers.Builder();
      int i = 0;
      int j = paramHeaders1.size();
      while (i < j)
      {
        paramHeaders2 = paramHeaders1.name(i);
        if (localSet.contains(paramHeaders2)) {
          localBuilder.add(paramHeaders2, paramHeaders1.value(i));
        }
        i++;
      }
      return localBuilder.build();
    }
    
    public final boolean hasVaryAll(Response paramResponse)
    {
      Intrinsics.checkNotNullParameter(paramResponse, "$this$hasVaryAll");
      return ((Companion)this).varyFields(paramResponse.headers()).contains("*");
    }
    
    @JvmStatic
    public final String key(HttpUrl paramHttpUrl)
    {
      Intrinsics.checkNotNullParameter(paramHttpUrl, "url");
      return ByteString.Companion.encodeUtf8(paramHttpUrl.toString()).md5().hex();
    }
    
    public final int readInt$okhttp(BufferedSource paramBufferedSource)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramBufferedSource, "source");
      try
      {
        long l = paramBufferedSource.readDecimalLong();
        String str = paramBufferedSource.readUtf8LineStrict();
        if ((l >= 0L) && (l <= Integer.MAX_VALUE))
        {
          int i;
          if (((CharSequence)str).length() > 0) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0) {
            return (int)l;
          }
        }
        paramBufferedSource = new java/io/IOException;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        paramBufferedSource.<init>("expected an int but was \"" + l + str + '"');
        throw ((Throwable)paramBufferedSource);
      }
      catch (NumberFormatException paramBufferedSource)
      {
        throw ((Throwable)new IOException(paramBufferedSource.getMessage()));
      }
    }
    
    public final Headers varyHeaders(Response paramResponse)
    {
      Intrinsics.checkNotNullParameter(paramResponse, "$this$varyHeaders");
      Object localObject = paramResponse.networkResponse();
      Intrinsics.checkNotNull(localObject);
      localObject = ((Response)localObject).request().headers();
      paramResponse = paramResponse.headers();
      return ((Companion)this).varyHeaders((Headers)localObject, paramResponse);
    }
    
    public final boolean varyMatches(Response paramResponse, Headers paramHeaders, Request paramRequest)
    {
      Intrinsics.checkNotNullParameter(paramResponse, "cachedResponse");
      Intrinsics.checkNotNullParameter(paramHeaders, "cachedRequest");
      Intrinsics.checkNotNullParameter(paramRequest, "newRequest");
      paramResponse = (Iterable)((Companion)this).varyFields(paramResponse.headers());
      boolean bool2 = paramResponse instanceof Collection;
      boolean bool1 = true;
      if ((!bool2) || (!((Collection)paramResponse).isEmpty()))
      {
        Iterator localIterator = paramResponse.iterator();
        while (localIterator.hasNext())
        {
          paramResponse = (String)localIterator.next();
          if ((Intrinsics.areEqual(paramHeaders.values(paramResponse), paramRequest.headers(paramResponse)) ^ true)) {
            bool1 = false;
          }
        }
      }
      return bool1;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\001\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\b\002\030\000 .2\0020\001:\001.B\017\b\026\022\006\020\002\032\0020\003¢\006\002\020\004B\017\b\026\022\006\020\005\032\0020\006¢\006\002\020\007J\026\020\033\032\0020\r2\006\020\034\032\0020\0352\006\020\005\032\0020\006J\026\020\036\032\b\022\004\022\0020 0\0372\006\020!\032\0020\"H\002J\022\020\005\032\0020\0062\n\020#\032\0060$R\0020%J\036\020&\032\0020'2\006\020(\032\0020)2\f\020*\032\b\022\004\022\0020 0\037H\002J\022\020+\032\0020'2\n\020,\032\0060-R\0020%R\016\020\b\032\0020\tX\004¢\006\002\n\000R\020\020\n\032\004\030\0010\013X\004¢\006\002\n\000R\024\020\f\032\0020\r8BX\004¢\006\006\032\004\b\f\020\016R\016\020\017\032\0020\020X\004¢\006\002\n\000R\016\020\021\032\0020\022X\004¢\006\002\n\000R\016\020\023\032\0020\024X\004¢\006\002\n\000R\016\020\025\032\0020\020X\004¢\006\002\n\000R\016\020\026\032\0020\027X\004¢\006\002\n\000R\016\020\030\032\0020\024X\004¢\006\002\n\000R\016\020\031\032\0020\020X\004¢\006\002\n\000R\016\020\032\032\0020\027X\004¢\006\002\n\000¨\006/"}, d2={"Lokhttp3/Cache$Entry;", "", "rawSource", "Lokio/Source;", "(Lokio/Source;)V", "response", "Lokhttp3/Response;", "(Lokhttp3/Response;)V", "code", "", "handshake", "Lokhttp3/Handshake;", "isHttps", "", "()Z", "message", "", "protocol", "Lokhttp3/Protocol;", "receivedResponseMillis", "", "requestMethod", "responseHeaders", "Lokhttp3/Headers;", "sentRequestMillis", "url", "varyHeaders", "matches", "request", "Lokhttp3/Request;", "readCertificateList", "", "Ljava/security/cert/Certificate;", "source", "Lokio/BufferedSource;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "writeCertList", "", "sink", "Lokio/BufferedSink;", "certificates", "writeTo", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
  private static final class Entry
  {
    public static final Companion Companion = new Companion(null);
    private static final String RECEIVED_MILLIS = Platform.Companion.get().getPrefix() + "-Received-Millis";
    private static final String SENT_MILLIS = Platform.Companion.get().getPrefix() + "-Sent-Millis";
    private final int code;
    private final Handshake handshake;
    private final String message;
    private final Protocol protocol;
    private final long receivedResponseMillis;
    private final String requestMethod;
    private final Headers responseHeaders;
    private final long sentRequestMillis;
    private final String url;
    private final Headers varyHeaders;
    
    public Entry(Response paramResponse)
    {
      this.url = paramResponse.request().url().toString();
      this.varyHeaders = Cache.Companion.varyHeaders(paramResponse);
      this.requestMethod = paramResponse.request().method();
      this.protocol = paramResponse.protocol();
      this.code = paramResponse.code();
      this.message = paramResponse.message();
      this.responseHeaders = paramResponse.headers();
      this.handshake = paramResponse.handshake();
      this.sentRequestMillis = paramResponse.sentRequestAtMillis();
      this.receivedResponseMillis = paramResponse.receivedResponseAtMillis();
    }
    
    public Entry(Source paramSource)
      throws IOException
    {
      try
      {
        Object localObject1 = Okio.buffer(paramSource);
        this.url = ((BufferedSource)localObject1).readUtf8LineStrict();
        this.requestMethod = ((BufferedSource)localObject1).readUtf8LineStrict();
        Object localObject3 = new okhttp3/Headers$Builder;
        ((Headers.Builder)localObject3).<init>();
        int k = Cache.Companion.readInt$okhttp((BufferedSource)localObject1);
        int j;
        for (int i = 0;; i++)
        {
          j = 1;
          if (i >= k) {
            break;
          }
          ((Headers.Builder)localObject3).addLenient$okhttp(((BufferedSource)localObject1).readUtf8LineStrict());
        }
        this.varyHeaders = ((Headers.Builder)localObject3).build();
        localObject3 = StatusLine.Companion.parse(((BufferedSource)localObject1).readUtf8LineStrict());
        this.protocol = ((StatusLine)localObject3).protocol;
        this.code = ((StatusLine)localObject3).code;
        this.message = ((StatusLine)localObject3).message;
        localObject3 = new okhttp3/Headers$Builder;
        ((Headers.Builder)localObject3).<init>();
        k = Cache.Companion.readInt$okhttp((BufferedSource)localObject1);
        for (i = 0; i < k; i++) {
          ((Headers.Builder)localObject3).addLenient$okhttp(((BufferedSource)localObject1).readUtf8LineStrict());
        }
        String str2 = SENT_MILLIS;
        Object localObject5 = ((Headers.Builder)localObject3).get(str2);
        Object localObject4 = RECEIVED_MILLIS;
        String str1 = ((Headers.Builder)localObject3).get((String)localObject4);
        ((Headers.Builder)localObject3).removeAll(str2);
        ((Headers.Builder)localObject3).removeAll((String)localObject4);
        long l2 = 0L;
        if (localObject5 != null) {
          l1 = Long.parseLong((String)localObject5);
        } else {
          l1 = 0L;
        }
        this.sentRequestMillis = l1;
        long l1 = l2;
        if (str1 != null) {
          l1 = Long.parseLong(str1);
        }
        this.receivedResponseMillis = l1;
        this.responseHeaders = ((Headers.Builder)localObject3).build();
        if (isHttps())
        {
          localObject3 = ((BufferedSource)localObject1).readUtf8LineStrict();
          if (((CharSequence)localObject3).length() > 0) {
            i = j;
          } else {
            i = 0;
          }
          if (i == 0)
          {
            localObject3 = ((BufferedSource)localObject1).readUtf8LineStrict();
            localObject5 = CipherSuite.Companion.forJavaName((String)localObject3);
            localObject3 = readCertificateList((BufferedSource)localObject1);
            localObject4 = readCertificateList((BufferedSource)localObject1);
            if (!((BufferedSource)localObject1).exhausted()) {
              localObject1 = TlsVersion.Companion.forJavaName(((BufferedSource)localObject1).readUtf8LineStrict());
            } else {
              localObject1 = TlsVersion.SSL_3_0;
            }
            this.handshake = Handshake.Companion.get((TlsVersion)localObject1, (CipherSuite)localObject5, (List)localObject3, (List)localObject4);
          }
          else
          {
            localObject4 = new java/io/IOException;
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            ((IOException)localObject4).<init>("expected \"\" but was \"" + (String)localObject3 + '"');
            throw ((Throwable)localObject4);
          }
        }
        else
        {
          localObject1 = (Handshake)null;
          this.handshake = null;
        }
        return;
      }
      finally
      {
        paramSource.close();
      }
    }
    
    private final boolean isHttps()
    {
      return StringsKt.startsWith$default(this.url, "https://", false, 2, null);
    }
    
    private final List<Certificate> readCertificateList(BufferedSource paramBufferedSource)
      throws IOException
    {
      int j = Cache.Companion.readInt$okhttp(paramBufferedSource);
      if (j == -1) {
        return CollectionsKt.emptyList();
      }
      try
      {
        CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
        ArrayList localArrayList = new java/util/ArrayList;
        localArrayList.<init>(j);
        for (int i = 0; i < j; i++)
        {
          Object localObject = paramBufferedSource.readUtf8LineStrict();
          Buffer localBuffer = new okio/Buffer;
          localBuffer.<init>();
          localObject = ByteString.Companion.decodeBase64((String)localObject);
          Intrinsics.checkNotNull(localObject);
          localBuffer.write((ByteString)localObject);
          localArrayList.add(localCertificateFactory.generateCertificate(localBuffer.inputStream()));
        }
        paramBufferedSource = (List)localArrayList;
        return paramBufferedSource;
      }
      catch (CertificateException paramBufferedSource)
      {
        throw ((Throwable)new IOException(paramBufferedSource.getMessage()));
      }
    }
    
    private final void writeCertList(BufferedSink paramBufferedSink, List<? extends Certificate> paramList)
      throws IOException
    {
      try
      {
        paramBufferedSink.writeDecimalLong(paramList.size()).writeByte(10);
        int i = 0;
        int j = paramList.size();
        while (i < j)
        {
          byte[] arrayOfByte = ((Certificate)paramList.get(i)).getEncoded();
          ByteString.Companion localCompanion = ByteString.Companion;
          Intrinsics.checkNotNullExpressionValue(arrayOfByte, "bytes");
          paramBufferedSink.writeUtf8(ByteString.Companion.of$default(localCompanion, arrayOfByte, 0, 0, 3, null).base64()).writeByte(10);
          i++;
        }
        return;
      }
      catch (CertificateEncodingException paramBufferedSink)
      {
        throw ((Throwable)new IOException(paramBufferedSink.getMessage()));
      }
    }
    
    public final boolean matches(Request paramRequest, Response paramResponse)
    {
      Intrinsics.checkNotNullParameter(paramRequest, "request");
      Intrinsics.checkNotNullParameter(paramResponse, "response");
      boolean bool;
      if ((Intrinsics.areEqual(this.url, paramRequest.url().toString())) && (Intrinsics.areEqual(this.requestMethod, paramRequest.method())) && (Cache.Companion.varyMatches(paramResponse, this.varyHeaders, paramRequest))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final Response response(DiskLruCache.Snapshot paramSnapshot)
    {
      Intrinsics.checkNotNullParameter(paramSnapshot, "snapshot");
      String str2 = this.responseHeaders.get("Content-Type");
      String str1 = this.responseHeaders.get("Content-Length");
      Request localRequest = new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build();
      return new Response.Builder().request(localRequest).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body((ResponseBody)new Cache.CacheResponseBody(paramSnapshot, str2, str1)).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
    }
    
    public final void writeTo(DiskLruCache.Editor paramEditor)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramEditor, "editor");
      int j = 0;
      paramEditor = (Closeable)Okio.buffer(paramEditor.newSink(0));
      Object localObject1 = (Throwable)null;
      try
      {
        localObject1 = (BufferedSink)paramEditor;
        ((BufferedSink)localObject1).writeUtf8(this.url).writeByte(10);
        ((BufferedSink)localObject1).writeUtf8(this.requestMethod).writeByte(10);
        ((BufferedSink)localObject1).writeDecimalLong(this.varyHeaders.size()).writeByte(10);
        int k = this.varyHeaders.size();
        for (int i = 0; i < k; i++) {
          ((BufferedSink)localObject1).writeUtf8(this.varyHeaders.name(i)).writeUtf8(": ").writeUtf8(this.varyHeaders.value(i)).writeByte(10);
        }
        Object localObject2 = new okhttp3/internal/http/StatusLine;
        ((StatusLine)localObject2).<init>(this.protocol, this.code, this.message);
        ((BufferedSink)localObject1).writeUtf8(((StatusLine)localObject2).toString()).writeByte(10);
        ((BufferedSink)localObject1).writeDecimalLong(this.responseHeaders.size() + 2).writeByte(10);
        k = this.responseHeaders.size();
        for (i = j; i < k; i++) {
          ((BufferedSink)localObject1).writeUtf8(this.responseHeaders.name(i)).writeUtf8(": ").writeUtf8(this.responseHeaders.value(i)).writeByte(10);
        }
        ((BufferedSink)localObject1).writeUtf8(SENT_MILLIS).writeUtf8(": ").writeDecimalLong(this.sentRequestMillis).writeByte(10);
        ((BufferedSink)localObject1).writeUtf8(RECEIVED_MILLIS).writeUtf8(": ").writeDecimalLong(this.receivedResponseMillis).writeByte(10);
        if (isHttps())
        {
          ((BufferedSink)localObject1).writeByte(10);
          localObject2 = this.handshake;
          Intrinsics.checkNotNull(localObject2);
          ((BufferedSink)localObject1).writeUtf8(((Handshake)localObject2).cipherSuite().javaName()).writeByte(10);
          writeCertList((BufferedSink)localObject1, this.handshake.peerCertificates());
          writeCertList((BufferedSink)localObject1, this.handshake.localCertificates());
          ((BufferedSink)localObject1).writeUtf8(this.handshake.tlsVersion().javaName()).writeByte(10);
        }
        localObject1 = Unit.INSTANCE;
        CloseableKt.closeFinally(paramEditor, null);
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
          CloseableKt.closeFinally(paramEditor, localThrowable);
        }
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\005\032\0020\004X\004¢\006\002\n\000¨\006\006"}, d2={"Lokhttp3/Cache$Entry$Companion;", "", "()V", "RECEIVED_MILLIS", "", "SENT_MILLIS", "okhttp"}, k=1, mv={1, 4, 0})
    public static final class Companion {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\002\n\000\b\004\030\0002\0020\001B\021\022\n\020\002\032\0060\003R\0020\004¢\006\002\020\005J\b\020\017\032\0020\020H\026J\b\020\006\032\0020\007H\026R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\b\032\0020\007X\004¢\006\002\n\000R\032\020\t\032\0020\nX\016¢\006\016\n\000\032\004\b\013\020\f\"\004\b\r\020\016R\022\020\002\032\0060\003R\0020\004X\004¢\006\002\n\000¨\006\021"}, d2={"Lokhttp3/Cache$RealCacheRequest;", "Lokhttp3/internal/cache/CacheRequest;", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "(Lokhttp3/Cache;Lokhttp3/internal/cache/DiskLruCache$Editor;)V", "body", "Lokio/Sink;", "cacheOut", "done", "", "getDone", "()Z", "setDone", "(Z)V", "abort", "", "okhttp"}, k=1, mv={1, 4, 0})
  private final class RealCacheRequest
    implements CacheRequest
  {
    private final Sink body;
    private final Sink cacheOut;
    private boolean done;
    private final DiskLruCache.Editor editor;
    
    public RealCacheRequest()
    {
      this.editor = ((DiskLruCache.Editor)localObject);
      this$1 = ((DiskLruCache.Editor)localObject).newSink(1);
      this.cacheOut = Cache.this;
      this.body = ((Sink)new ForwardingSink(Cache.this)
      {
        final Cache.RealCacheRequest this$0;
        
        public void close()
          throws IOException
        {
          synchronized (this.this$0.this$0)
          {
            boolean bool = this.this$0.getDone();
            if (bool) {
              return;
            }
            this.this$0.setDone(true);
            Cache localCache2 = this.this$0.this$0;
            localCache2.setWriteSuccessCount$okhttp(localCache2.getWriteSuccessCount$okhttp() + 1);
            super.close();
            Cache.RealCacheRequest.access$getEditor$p(this.this$0).commit();
            return;
          }
        }
      });
    }
    
    public void abort()
    {
      synchronized (Cache.this)
      {
        boolean bool = this.done;
        if (bool) {
          return;
        }
        this.done = true;
        Cache localCache2 = Cache.this;
        localCache2.setWriteAbortCount$okhttp(localCache2.getWriteAbortCount$okhttp() + 1);
        Util.closeQuietly((Closeable)this.cacheOut);
        try
        {
          this.editor.abort();
        }
        catch (IOException localIOException) {}
        return;
      }
    }
    
    public Sink body()
    {
      return this.body;
    }
    
    public final boolean getDone()
    {
      return this.done;
    }
    
    public final void setDone(boolean paramBoolean)
    {
      this.done = paramBoolean;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */