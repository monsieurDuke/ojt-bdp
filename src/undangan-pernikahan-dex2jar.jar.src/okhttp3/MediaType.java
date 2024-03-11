package okhttp3;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\003\n\002\020\021\n\002\b\004\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\007\030\000 \0302\0020\001:\001\030B-\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003\022\f\020\006\032\b\022\004\022\0020\0030\007¢\006\002\020\bJ\026\020\013\032\004\030\0010\f2\n\b\002\020\r\032\004\030\0010\fH\007J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001H\002J\b\020\021\032\0020\022H\026J\020\020\023\032\004\030\0010\0032\006\020\024\032\0020\003J\r\020\005\032\0020\003H\007¢\006\002\b\025J\b\020\026\032\0020\003H\026J\r\020\004\032\0020\003H\007¢\006\002\b\027R\016\020\002\032\0020\003X\004¢\006\002\n\000R\026\020\006\032\b\022\004\022\0020\0030\007X\004¢\006\004\n\002\020\tR\023\020\005\032\0020\0038\007¢\006\b\n\000\032\004\b\005\020\nR\023\020\004\032\0020\0038\007¢\006\b\n\000\032\004\b\004\020\n¨\006\031"}, d2={"Lokhttp3/MediaType;", "", "mediaType", "", "type", "subtype", "parameterNamesAndValues", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "[Ljava/lang/String;", "()Ljava/lang/String;", "charset", "Ljava/nio/charset/Charset;", "defaultValue", "equals", "", "other", "hashCode", "", "parameter", "name", "-deprecated_subtype", "toString", "-deprecated_type", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class MediaType
{
  public static final Companion Companion = new Companion(null);
  private static final Pattern PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
  private static final String QUOTED = "\"([^\"]*)\"";
  private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
  private static final Pattern TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
  private final String mediaType;
  private final String[] parameterNamesAndValues;
  private final String subtype;
  private final String type;
  
  private MediaType(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString)
  {
    this.mediaType = paramString1;
    this.type = paramString2;
    this.subtype = paramString3;
    this.parameterNamesAndValues = paramArrayOfString;
  }
  
  @JvmStatic
  public static final MediaType get(String paramString)
  {
    return Companion.get(paramString);
  }
  
  @JvmStatic
  public static final MediaType parse(String paramString)
  {
    return Companion.parse(paramString);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="subtype", imports={}))
  public final String -deprecated_subtype()
  {
    return this.subtype;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="type", imports={}))
  public final String -deprecated_type()
  {
    return this.type;
  }
  
  public final Charset charset()
  {
    return charset$default(this, null, 1, null);
  }
  
  public final Charset charset(Charset paramCharset)
  {
    Object localObject = parameter("charset");
    if (localObject != null)
    {
      try
      {
        localObject = Charset.forName((String)localObject);
        paramCharset = (Charset)localObject;
      }
      catch (IllegalArgumentException localIllegalArgumentException) {}
      return paramCharset;
    }
    return paramCharset;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof MediaType)) && (Intrinsics.areEqual(((MediaType)paramObject).mediaType, this.mediaType))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.mediaType.hashCode();
  }
  
  public final String parameter(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "name");
    IntProgression localIntProgression = RangesKt.step((IntProgression)ArraysKt.getIndices(this.parameterNamesAndValues), 2);
    int i = localIntProgression.getFirst();
    int j = localIntProgression.getLast();
    int k = localIntProgression.getStep();
    if (k >= 0 ? i <= j : i >= j) {
      for (;;)
      {
        if (StringsKt.equals(this.parameterNamesAndValues[i], paramString, true)) {
          return this.parameterNamesAndValues[(i + 1)];
        }
        if (i == j) {
          break;
        }
        i += k;
      }
    }
    return null;
  }
  
  public final String subtype()
  {
    return this.subtype;
  }
  
  public String toString()
  {
    return this.mediaType;
  }
  
  public final String type()
  {
    return this.type;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\025\020\n\032\0020\0132\006\020\f\032\0020\007H\007¢\006\002\b\rJ\027\020\016\032\004\030\0010\0132\006\020\f\032\0020\007H\007¢\006\002\b\017J\021\020\020\032\0020\013*\0020\007H\007¢\006\002\b\nJ\023\020\021\032\004\030\0010\013*\0020\007H\007¢\006\002\b\016R\026\020\003\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000R\016\020\006\032\0020\007XT¢\006\002\n\000R\016\020\b\032\0020\007XT¢\006\002\n\000R\026\020\t\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000¨\006\022"}, d2={"Lokhttp3/MediaType$Companion;", "", "()V", "PARAMETER", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "QUOTED", "", "TOKEN", "TYPE_SUBTYPE", "get", "Lokhttp3/MediaType;", "mediaType", "-deprecated_get", "parse", "-deprecated_parse", "toMediaType", "toMediaTypeOrNull", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="mediaType.toMediaType()", imports={"okhttp3.MediaType.Companion.toMediaType"}))
    public final MediaType -deprecated_get(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "mediaType");
      return ((Companion)this).get(paramString);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="mediaType.toMediaTypeOrNull()", imports={"okhttp3.MediaType.Companion.toMediaTypeOrNull"}))
    public final MediaType -deprecated_parse(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "mediaType");
      return ((Companion)this).parse(paramString);
    }
    
    @JvmStatic
    public final MediaType get(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "$this$toMediaType");
      Object localObject1 = MediaType.access$getTYPE_SUBTYPE$cp().matcher((CharSequence)paramString);
      if (((Matcher)localObject1).lookingAt())
      {
        Object localObject3 = ((Matcher)localObject1).group(1);
        Intrinsics.checkNotNullExpressionValue(localObject3, "typeSubtype.group(1)");
        Object localObject2 = Locale.US;
        Intrinsics.checkNotNullExpressionValue(localObject2, "Locale.US");
        if (localObject3 != null)
        {
          localObject2 = ((String)localObject3).toLowerCase((Locale)localObject2);
          Intrinsics.checkNotNullExpressionValue(localObject2, "(this as java.lang.String).toLowerCase(locale)");
          Object localObject4 = ((Matcher)localObject1).group(2);
          Intrinsics.checkNotNullExpressionValue(localObject4, "typeSubtype.group(2)");
          localObject3 = Locale.US;
          Intrinsics.checkNotNullExpressionValue(localObject3, "Locale.US");
          if (localObject4 != null)
          {
            localObject3 = ((String)localObject4).toLowerCase((Locale)localObject3);
            Intrinsics.checkNotNullExpressionValue(localObject3, "(this as java.lang.String).toLowerCase(locale)");
            List localList = (List)new ArrayList();
            localObject4 = MediaType.access$getPARAMETER$cp().matcher((CharSequence)paramString);
            int i = ((Matcher)localObject1).end();
            while (i < paramString.length())
            {
              ((Matcher)localObject4).region(i, paramString.length());
              if (((Matcher)localObject4).lookingAt())
              {
                String str = ((Matcher)localObject4).group(1);
                if (str == null)
                {
                  i = ((Matcher)localObject4).end();
                }
                else
                {
                  localObject1 = ((Matcher)localObject4).group(2);
                  if (localObject1 == null)
                  {
                    localObject1 = ((Matcher)localObject4).group(3);
                  }
                  else if ((StringsKt.startsWith$default((String)localObject1, "'", false, 2, null)) && (StringsKt.endsWith$default((String)localObject1, "'", false, 2, null)) && (((String)localObject1).length() > 2))
                  {
                    localObject1 = ((String)localObject1).substring(1, ((String)localObject1).length() - 1);
                    Intrinsics.checkNotNullExpressionValue(localObject1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                  }
                  ((Collection)localList).add(str);
                  ((Collection)localList).add(localObject1);
                  i = ((Matcher)localObject4).end();
                }
              }
              else
              {
                localObject1 = new StringBuilder().append("Parameter is not formatted correctly: \"");
                localObject2 = paramString.substring(i);
                Intrinsics.checkNotNullExpressionValue(localObject2, "(this as java.lang.String).substring(startIndex)");
                throw ((Throwable)new IllegalArgumentException(((String)localObject2 + "\" for: \"" + paramString + '"').toString()));
              }
            }
            localObject1 = ((Collection)localList).toArray(new String[0]);
            if (localObject1 != null) {
              return new MediaType(paramString, (String)localObject2, (String)localObject3, (String[])localObject1, null);
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
          }
          throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
      }
      throw ((Throwable)new IllegalArgumentException(("No subtype found for: \"" + paramString + '"').toString()));
    }
    
    @JvmStatic
    public final MediaType parse(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "$this$toMediaTypeOrNull");
      try
      {
        paramString = ((Companion)this).get(paramString);
      }
      catch (IllegalArgumentException paramString)
      {
        paramString = null;
      }
      return paramString;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/MediaType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */