package okhttp3.internal.ws;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.Headers;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\000\n\002\020\b\n\002\b\025\n\002\020\016\n\002\b\002\b\b\030\000 \0342\0020\001:\001\034BE\022\b\b\002\020\002\032\0020\003\022\n\b\002\020\004\032\004\030\0010\005\022\b\b\002\020\006\032\0020\003\022\n\b\002\020\007\032\004\030\0010\005\022\b\b\002\020\b\032\0020\003\022\b\b\002\020\t\032\0020\003¢\006\002\020\nJ\t\020\f\032\0020\003HÆ\003J\020\020\r\032\004\030\0010\005HÆ\003¢\006\002\020\016J\t\020\017\032\0020\003HÆ\003J\020\020\020\032\004\030\0010\005HÆ\003¢\006\002\020\016J\t\020\021\032\0020\003HÆ\003J\t\020\022\032\0020\003HÆ\003JN\020\023\032\0020\0002\b\b\002\020\002\032\0020\0032\n\b\002\020\004\032\004\030\0010\0052\b\b\002\020\006\032\0020\0032\n\b\002\020\007\032\004\030\0010\0052\b\b\002\020\b\032\0020\0032\b\b\002\020\t\032\0020\003HÆ\001¢\006\002\020\024J\023\020\025\032\0020\0032\b\020\026\032\004\030\0010\001HÖ\003J\t\020\027\032\0020\005HÖ\001J\016\020\030\032\0020\0032\006\020\031\032\0020\003J\t\020\032\032\0020\033HÖ\001R\024\020\004\032\004\030\0010\0058\006X\004¢\006\004\n\002\020\013R\020\020\006\032\0020\0038\006X\004¢\006\002\n\000R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000R\024\020\007\032\004\030\0010\0058\006X\004¢\006\004\n\002\020\013R\020\020\b\032\0020\0038\006X\004¢\006\002\n\000R\020\020\t\032\0020\0038\006X\004¢\006\002\n\000¨\006\035"}, d2={"Lokhttp3/internal/ws/WebSocketExtensions;", "", "perMessageDeflate", "", "clientMaxWindowBits", "", "clientNoContextTakeover", "serverMaxWindowBits", "serverNoContextTakeover", "unknownValues", "(ZLjava/lang/Integer;ZLjava/lang/Integer;ZZ)V", "Ljava/lang/Integer;", "component1", "component2", "()Ljava/lang/Integer;", "component3", "component4", "component5", "component6", "copy", "(ZLjava/lang/Integer;ZLjava/lang/Integer;ZZ)Lokhttp3/internal/ws/WebSocketExtensions;", "equals", "other", "hashCode", "noContextTakeover", "clientOriginated", "toString", "", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class WebSocketExtensions
{
  public static final Companion Companion = new Companion(null);
  private static final String HEADER_WEB_SOCKET_EXTENSION = "Sec-WebSocket-Extensions";
  public final Integer clientMaxWindowBits;
  public final boolean clientNoContextTakeover;
  public final boolean perMessageDeflate;
  public final Integer serverMaxWindowBits;
  public final boolean serverNoContextTakeover;
  public final boolean unknownValues;
  
  public WebSocketExtensions()
  {
    this(false, null, false, null, false, false, 63, null);
  }
  
  public WebSocketExtensions(boolean paramBoolean1, Integer paramInteger1, boolean paramBoolean2, Integer paramInteger2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.perMessageDeflate = paramBoolean1;
    this.clientMaxWindowBits = paramInteger1;
    this.clientNoContextTakeover = paramBoolean2;
    this.serverMaxWindowBits = paramInteger2;
    this.serverNoContextTakeover = paramBoolean3;
    this.unknownValues = paramBoolean4;
  }
  
  public final boolean component1()
  {
    return this.perMessageDeflate;
  }
  
  public final Integer component2()
  {
    return this.clientMaxWindowBits;
  }
  
  public final boolean component3()
  {
    return this.clientNoContextTakeover;
  }
  
  public final Integer component4()
  {
    return this.serverMaxWindowBits;
  }
  
  public final boolean component5()
  {
    return this.serverNoContextTakeover;
  }
  
  public final boolean component6()
  {
    return this.unknownValues;
  }
  
  public final WebSocketExtensions copy(boolean paramBoolean1, Integer paramInteger1, boolean paramBoolean2, Integer paramInteger2, boolean paramBoolean3, boolean paramBoolean4)
  {
    return new WebSocketExtensions(paramBoolean1, paramInteger1, paramBoolean2, paramInteger2, paramBoolean3, paramBoolean4);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof WebSocketExtensions))
      {
        paramObject = (WebSocketExtensions)paramObject;
        if ((this.perMessageDeflate == ((WebSocketExtensions)paramObject).perMessageDeflate) && (Intrinsics.areEqual(this.clientMaxWindowBits, ((WebSocketExtensions)paramObject).clientMaxWindowBits)) && (this.clientNoContextTakeover == ((WebSocketExtensions)paramObject).clientNoContextTakeover) && (Intrinsics.areEqual(this.serverMaxWindowBits, ((WebSocketExtensions)paramObject).serverMaxWindowBits)) && (this.serverNoContextTakeover == ((WebSocketExtensions)paramObject).serverNoContextTakeover) && (this.unknownValues == ((WebSocketExtensions)paramObject).unknownValues)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode()
  {
    boolean bool2 = this.perMessageDeflate;
    int i1 = 1;
    boolean bool1 = bool2;
    if (bool2) {
      bool1 = true;
    }
    Integer localInteger = this.clientMaxWindowBits;
    int m = 0;
    int i;
    if (localInteger != null) {
      i = localInteger.hashCode();
    } else {
      i = 0;
    }
    int n = this.clientNoContextTakeover;
    int j = n;
    int k;
    if (n != 0) {
      k = 1;
    }
    localInteger = this.serverMaxWindowBits;
    if (localInteger != null) {
      m = localInteger.hashCode();
    }
    int i2 = this.serverNoContextTakeover;
    n = i2;
    if (i2 != 0) {
      n = 1;
    }
    i2 = this.unknownValues;
    if (i2 == 0) {
      i1 = i2;
    }
    return ((((bool1 * true + i) * 31 + k) * 31 + m) * 31 + n) * 31 + i1;
  }
  
  public final boolean noContextTakeover(boolean paramBoolean)
  {
    if (paramBoolean) {
      paramBoolean = this.clientNoContextTakeover;
    } else {
      paramBoolean = this.serverNoContextTakeover;
    }
    return paramBoolean;
  }
  
  public String toString()
  {
    return "WebSocketExtensions(perMessageDeflate=" + this.perMessageDeflate + ", clientMaxWindowBits=" + this.clientMaxWindowBits + ", clientNoContextTakeover=" + this.clientNoContextTakeover + ", serverMaxWindowBits=" + this.serverMaxWindowBits + ", serverNoContextTakeover=" + this.serverNoContextTakeover + ", unknownValues=" + this.unknownValues + ")";
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\005\032\0020\0062\006\020\007\032\0020\bR\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\t"}, d2={"Lokhttp3/internal/ws/WebSocketExtensions$Companion;", "", "()V", "HEADER_WEB_SOCKET_EXTENSION", "", "parse", "Lokhttp3/internal/ws/WebSocketExtensions;", "responseHeaders", "Lokhttp3/Headers;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final WebSocketExtensions parse(Headers paramHeaders)
      throws IOException
    {
      Intrinsics.checkNotNullParameter(paramHeaders, "responseHeaders");
      boolean bool5 = false;
      Object localObject1 = (Integer)null;
      boolean bool2 = false;
      Object localObject2 = (Integer)null;
      boolean bool3 = false;
      boolean bool4 = false;
      int k = paramHeaders.size();
      int j = 0;
      while (j < k)
      {
        Object localObject3 = paramHeaders.name(j);
        boolean bool1 = true;
        boolean bool9;
        Object localObject4;
        boolean bool7;
        boolean bool8;
        boolean bool6;
        if (!StringsKt.equals((String)localObject3, "Sec-WebSocket-Extensions", true))
        {
          bool9 = bool5;
          localObject4 = localObject1;
          bool7 = bool2;
          localObject3 = localObject2;
          bool8 = bool3;
          bool6 = bool4;
        }
        else
        {
          String str1 = paramHeaders.value(j);
          int i = 0;
          for (;;)
          {
            bool9 = bool5;
            localObject4 = localObject1;
            bool7 = bool2;
            localObject3 = localObject2;
            bool8 = bool3;
            bool6 = bool4;
            if (i >= str1.length()) {
              break;
            }
            int m = Util.delimiterOffset$default(str1, ',', i, 0, 4, null);
            int n = Util.delimiterOffset(str1, ';', i, m);
            localObject4 = Util.trimSubstring(str1, i, n);
            Log5ECF72.a(localObject4);
            LogE84000.a(localObject4);
            Log229316.a(localObject4);
            i = n + 1;
            if (StringsKt.equals((String)localObject4, "permessage-deflate", bool1))
            {
              if (bool5) {
                bool4 = true;
              }
              bool6 = true;
              bool5 = bool1;
              bool1 = bool4;
              bool4 = bool2;
              localObject3 = localObject1;
              bool2 = bool6;
              while (i < m)
              {
                n = Util.delimiterOffset(str1, ';', i, m);
                int i1 = Util.delimiterOffset(str1, '=', i, n);
                String str2 = Util.trimSubstring(str1, i, i1);
                Log5ECF72.a(str2);
                LogE84000.a(str2);
                Log229316.a(str2);
                if (i1 < n)
                {
                  localObject1 = Util.trimSubstring(str1, i1 + 1, n);
                  Log5ECF72.a(localObject1);
                  LogE84000.a(localObject1);
                  Log229316.a(localObject1);
                  localObject1 = StringsKt.removeSurrounding((String)localObject1, (CharSequence)"\"");
                  Log5ECF72.a(localObject1);
                  LogE84000.a(localObject1);
                  Log229316.a(localObject1);
                }
                else
                {
                  localObject1 = null;
                }
                i = n + 1;
                if (StringsKt.equals(str2, "client_max_window_bits", true))
                {
                  if (localObject3 != null) {
                    bool1 = true;
                  }
                  if (localObject1 != null) {
                    localObject1 = StringsKt.toIntOrNull((String)localObject1);
                  } else {
                    localObject1 = null;
                  }
                  if (localObject1 == null)
                  {
                    bool1 = true;
                    localObject3 = localObject1;
                  }
                  else
                  {
                    localObject3 = localObject1;
                  }
                }
                else if (StringsKt.equals(str2, "client_no_context_takeover", true))
                {
                  if (bool4) {
                    bool1 = true;
                  }
                  if (localObject1 != null) {
                    bool1 = true;
                  }
                  bool4 = true;
                }
                else if (StringsKt.equals(str2, "server_max_window_bits", true))
                {
                  if (localObject2 != null) {
                    bool1 = true;
                  }
                  if (localObject1 != null) {
                    localObject1 = StringsKt.toIntOrNull((String)localObject1);
                  } else {
                    localObject1 = null;
                  }
                  if (localObject1 == null)
                  {
                    bool1 = true;
                    localObject2 = localObject1;
                  }
                  else
                  {
                    localObject2 = localObject1;
                  }
                }
                else if (StringsKt.equals(str2, "server_no_context_takeover", true))
                {
                  if (bool3) {
                    bool1 = true;
                  }
                  if (localObject1 != null) {
                    bool1 = true;
                  }
                  bool3 = true;
                }
                else
                {
                  bool1 = true;
                }
                bool5 = true;
              }
              bool6 = bool1;
              bool1 = bool5;
              bool5 = bool2;
              localObject1 = localObject3;
              bool2 = bool4;
              bool4 = bool6;
            }
            else
            {
              bool4 = true;
            }
          }
        }
        j++;
        bool5 = bool9;
        localObject1 = localObject4;
        bool2 = bool7;
        localObject2 = localObject3;
        bool3 = bool8;
        bool4 = bool6;
      }
      return new WebSocketExtensions(bool5, (Integer)localObject1, bool2, (Integer)localObject2, bool3, bool4);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/ws/WebSocketExtensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */