package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.internal.Util;
import okio.ByteString;
import okio.ByteString.Companion;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\021\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\032\n\002\020\013\n\002\b\003\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\037\032\0020\0052\006\020 \032\0020\0132\006\020!\032\0020\013J\025\020\"\032\0020\0052\006\020 \032\0020\013H\000¢\006\002\b#J.\020$\032\0020\0052\006\020%\032\0020&2\006\020'\032\0020\0132\006\020(\032\0020\0132\006\020 \032\0020\0132\006\020!\032\0020\013R\026\020\003\032\b\022\004\022\0020\0050\004X\004¢\006\004\n\002\020\006R\020\020\007\032\0020\b8\006X\004¢\006\002\n\000R\030\020\t\032\n\022\006\022\004\030\0010\0050\004X\004¢\006\004\n\002\020\006R\016\020\n\032\0020\013XT¢\006\002\n\000R\016\020\f\032\0020\013XT¢\006\002\n\000R\016\020\r\032\0020\013XT¢\006\002\n\000R\016\020\016\032\0020\013XT¢\006\002\n\000R\016\020\017\032\0020\013XT¢\006\002\n\000R\016\020\020\032\0020\013XT¢\006\002\n\000R\016\020\021\032\0020\013XT¢\006\002\n\000R\016\020\022\032\0020\013XT¢\006\002\n\000R\026\020\023\032\b\022\004\022\0020\0050\004X\004¢\006\004\n\002\020\006R\016\020\024\032\0020\013XT¢\006\002\n\000R\016\020\025\032\0020\013XT¢\006\002\n\000R\016\020\026\032\0020\013XT¢\006\002\n\000R\016\020\027\032\0020\013XT¢\006\002\n\000R\016\020\030\032\0020\013XT¢\006\002\n\000R\016\020\031\032\0020\013XT¢\006\002\n\000R\016\020\032\032\0020\013XT¢\006\002\n\000R\016\020\033\032\0020\013XT¢\006\002\n\000R\016\020\034\032\0020\013XT¢\006\002\n\000R\016\020\035\032\0020\013XT¢\006\002\n\000R\016\020\036\032\0020\013XT¢\006\002\n\000¨\006)"}, d2={"Lokhttp3/internal/http2/Http2;", "", "()V", "BINARY", "", "", "[Ljava/lang/String;", "CONNECTION_PREFACE", "Lokio/ByteString;", "FLAGS", "FLAG_ACK", "", "FLAG_COMPRESSED", "FLAG_END_HEADERS", "FLAG_END_PUSH_PROMISE", "FLAG_END_STREAM", "FLAG_NONE", "FLAG_PADDED", "FLAG_PRIORITY", "FRAME_NAMES", "INITIAL_MAX_FRAME_SIZE", "TYPE_CONTINUATION", "TYPE_DATA", "TYPE_GOAWAY", "TYPE_HEADERS", "TYPE_PING", "TYPE_PRIORITY", "TYPE_PUSH_PROMISE", "TYPE_RST_STREAM", "TYPE_SETTINGS", "TYPE_WINDOW_UPDATE", "formatFlags", "type", "flags", "formattedType", "formattedType$okhttp", "frameLog", "inbound", "", "streamId", "length", "okhttp"}, k=1, mv={1, 4, 0})
public final class Http2
{
  private static final String[] BINARY;
  public static final ByteString CONNECTION_PREFACE;
  private static final String[] FLAGS;
  public static final int FLAG_ACK = 1;
  public static final int FLAG_COMPRESSED = 32;
  public static final int FLAG_END_HEADERS = 4;
  public static final int FLAG_END_PUSH_PROMISE = 4;
  public static final int FLAG_END_STREAM = 1;
  public static final int FLAG_NONE = 0;
  public static final int FLAG_PADDED = 8;
  public static final int FLAG_PRIORITY = 32;
  private static final String[] FRAME_NAMES;
  public static final int INITIAL_MAX_FRAME_SIZE = 16384;
  public static final Http2 INSTANCE = new Http2();
  public static final int TYPE_CONTINUATION = 9;
  public static final int TYPE_DATA = 0;
  public static final int TYPE_GOAWAY = 7;
  public static final int TYPE_HEADERS = 1;
  public static final int TYPE_PING = 6;
  public static final int TYPE_PRIORITY = 2;
  public static final int TYPE_PUSH_PROMISE = 5;
  public static final int TYPE_RST_STREAM = 3;
  public static final int TYPE_SETTINGS = 4;
  public static final int TYPE_WINDOW_UPDATE = 8;
  
  static
  {
    CONNECTION_PREFACE = ByteString.Companion.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    FRAME_NAMES = new String[] { "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION" };
    FLAGS = new String[64];
    Object localObject1 = new String['Ā'];
    int k = 0;
    for (int i = 0; i < 256; i++)
    {
      localObject2 = Integer.toBinaryString(i);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      Intrinsics.checkNotNullExpressionValue(localObject2, "Integer.toBinaryString(it)");
      localObject2 = Util.format("%8s", new Object[] { localObject2 });
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject2 = StringsKt.replace$default((String)localObject2, ' ', '0', false, 4, null);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject1[i] = localObject2;
    }
    BINARY = (String[])localObject1;
    Object localObject2 = FLAGS;
    localObject2[0] = "";
    localObject2[1] = "END_STREAM";
    localObject1 = new int[1];
    localObject1[0] = 1;
    localObject2[8] = "PADDED";
    int j = localObject1.length;
    Object localObject3;
    for (i = 0; i < j; i++)
    {
      m = localObject1[i];
      localObject2 = FLAGS;
      localObject3 = Intrinsics.stringPlus(localObject2[m], "|PADDED");
      Log5ECF72.a(localObject3);
      LogE84000.a(localObject3);
      Log229316.a(localObject3);
      localObject2[(m | 0x8)] = localObject3;
    }
    localObject2 = FLAGS;
    localObject2[4] = "END_HEADERS";
    localObject2[32] = "PRIORITY";
    localObject2[36] = "END_HEADERS|PRIORITY";
    localObject2 = new int[3];
    Object tmp343_341 = localObject2;
    tmp343_341[0] = 4;
    Object tmp347_343 = tmp343_341;
    tmp347_343[1] = 32;
    Object tmp352_347 = tmp347_343;
    tmp352_347[2] = 36;
    tmp352_347;
    int m = localObject2.length;
    for (i = 0; i < m; i++)
    {
      int i1 = localObject2[i];
      int n = localObject1.length;
      for (j = 0; j < n; j++)
      {
        int i2 = localObject1[j];
        localObject3 = FLAGS;
        localObject3[(i2 | i1)] = (localObject3[i2] + "|" + localObject3[i1]);
        localObject3[(i2 | i1 | 0x8)] = (localObject3[i2] + "|" + localObject3[i1] + "|PADDED");
      }
    }
    j = FLAGS.length;
    for (i = k; i < j; i++)
    {
      localObject1 = FLAGS;
      if (localObject1[i] == null) {
        localObject1[i] = BINARY[i];
      }
    }
  }
  
  public final String formatFlags(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      return "";
    }
    switch (paramInt1)
    {
    case 5: 
    default: 
      localObject = FLAGS;
      if (paramInt2 < localObject.length)
      {
        localObject = localObject[paramInt2];
        Intrinsics.checkNotNull(localObject);
      }
      break;
    case 4: 
    case 6: 
      if (paramInt2 == 1) {
        localObject = "ACK";
      } else {
        localObject = BINARY[paramInt2];
      }
      return (String)localObject;
    case 2: 
    case 3: 
    case 7: 
    case 8: 
      return BINARY[paramInt2];
    }
    Object localObject = BINARY[paramInt2];
    if ((paramInt1 == 5) && ((paramInt2 & 0x4) != 0))
    {
      localObject = StringsKt.replace$default((String)localObject, "HEADERS", "PUSH_PROMISE", false, 4, null);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
    }
    else if ((paramInt1 == 0) && ((paramInt2 & 0x20) != 0))
    {
      localObject = StringsKt.replace$default((String)localObject, "PRIORITY", "COMPRESSED", false, 4, null);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
    }
    return (String)localObject;
  }
  
  public final String formattedType$okhttp(int paramInt)
  {
    Object localObject = FRAME_NAMES;
    if (paramInt < localObject.length)
    {
      localObject = localObject[paramInt];
    }
    else
    {
      localObject = Util.format("0x%02x", new Object[] { Integer.valueOf(paramInt) });
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
    }
    return (String)localObject;
  }
  
  public final String frameLog(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    String str2 = formattedType$okhttp(paramInt3);
    String str3 = formatFlags(paramInt3, paramInt4);
    if (paramBoolean) {
      str1 = "<<";
    } else {
      str1 = ">>";
    }
    String str1 = Util.format("%s 0x%08x %5d %-13s %s", new Object[] { str1, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), str2, str3 });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    return str1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http2/Http2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */