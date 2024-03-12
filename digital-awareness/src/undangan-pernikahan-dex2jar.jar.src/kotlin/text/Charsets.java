package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\020\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000R\020\020\005\032\0020\0048\006X\004¢\006\002\n\000R\020\020\006\032\0020\0048\006X\004¢\006\002\n\000R\020\020\007\032\0020\0048\006X\004¢\006\002\n\000R\020\020\b\032\0020\0048\006X\004¢\006\002\n\000R\021\020\t\032\0020\0048G¢\006\006\032\004\b\n\020\013R\021\020\f\032\0020\0048G¢\006\006\032\004\b\r\020\013R\021\020\016\032\0020\0048G¢\006\006\032\004\b\017\020\013R\020\020\020\032\0020\0048\006X\004¢\006\002\n\000R\020\020\021\032\004\030\0010\004X\016¢\006\002\n\000R\020\020\022\032\004\030\0010\004X\016¢\006\002\n\000R\020\020\023\032\004\030\0010\004X\016¢\006\002\n\000¨\006\024"}, d2={"Lkotlin/text/Charsets;", "", "()V", "ISO_8859_1", "Ljava/nio/charset/Charset;", "US_ASCII", "UTF_16", "UTF_16BE", "UTF_16LE", "UTF_32", "UTF32", "()Ljava/nio/charset/Charset;", "UTF_32BE", "UTF32_BE", "UTF_32LE", "UTF32_LE", "UTF_8", "utf_32", "utf_32be", "utf_32le", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class Charsets
{
  public static final Charsets INSTANCE = new Charsets();
  public static final Charset ISO_8859_1;
  public static final Charset US_ASCII;
  public static final Charset UTF_16;
  public static final Charset UTF_16BE;
  public static final Charset UTF_16LE;
  public static final Charset UTF_8;
  private static Charset utf_32;
  private static Charset utf_32be;
  private static Charset utf_32le;
  
  static
  {
    Charset localCharset = Charset.forName("UTF-8");
    Intrinsics.checkNotNullExpressionValue(localCharset, "forName(\"UTF-8\")");
    UTF_8 = localCharset;
    localCharset = Charset.forName("UTF-16");
    Intrinsics.checkNotNullExpressionValue(localCharset, "forName(\"UTF-16\")");
    UTF_16 = localCharset;
    localCharset = Charset.forName("UTF-16BE");
    Intrinsics.checkNotNullExpressionValue(localCharset, "forName(\"UTF-16BE\")");
    UTF_16BE = localCharset;
    localCharset = Charset.forName("UTF-16LE");
    Intrinsics.checkNotNullExpressionValue(localCharset, "forName(\"UTF-16LE\")");
    UTF_16LE = localCharset;
    localCharset = Charset.forName("US-ASCII");
    Intrinsics.checkNotNullExpressionValue(localCharset, "forName(\"US-ASCII\")");
    US_ASCII = localCharset;
    localCharset = Charset.forName("ISO-8859-1");
    Intrinsics.checkNotNullExpressionValue(localCharset, "forName(\"ISO-8859-1\")");
    ISO_8859_1 = localCharset;
  }
  
  public final Charset UTF32()
  {
    Charset localCharset = utf_32;
    Object localObject = localCharset;
    if (localCharset == null)
    {
      localObject = (Charsets)this;
      localObject = Charset.forName("UTF-32");
      Intrinsics.checkNotNullExpressionValue(localObject, "forName(\"UTF-32\")");
      utf_32 = (Charset)localObject;
    }
    return (Charset)localObject;
  }
  
  public final Charset UTF32_BE()
  {
    Charset localCharset = utf_32be;
    Object localObject = localCharset;
    if (localCharset == null)
    {
      localObject = (Charsets)this;
      localObject = Charset.forName("UTF-32BE");
      Intrinsics.checkNotNullExpressionValue(localObject, "forName(\"UTF-32BE\")");
      utf_32be = (Charset)localObject;
    }
    return (Charset)localObject;
  }
  
  public final Charset UTF32_LE()
  {
    Charset localCharset = utf_32le;
    Object localObject = localCharset;
    if (localCharset == null)
    {
      localObject = (Charsets)this;
      localObject = Charset.forName("UTF-32LE");
      Intrinsics.checkNotNullExpressionValue(localObject, "forName(\"UTF-32LE\")");
      utf_32le = (Charset)localObject;
    }
    return (Charset)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/Charsets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */