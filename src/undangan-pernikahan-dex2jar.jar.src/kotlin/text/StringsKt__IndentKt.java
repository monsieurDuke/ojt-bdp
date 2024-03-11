package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.SequencesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\036\n\000\n\002\030\002\n\002\020\016\n\002\b\003\n\002\020\b\n\002\b\003\n\002\020 \n\002\b\013\032!\020\000\032\016\022\004\022\0020\002\022\004\022\0020\0020\0012\006\020\003\032\0020\002H\002¢\006\002\b\004\032\021\020\005\032\0020\006*\0020\002H\002¢\006\002\b\007\032\024\020\b\032\0020\002*\0020\0022\b\b\002\020\003\032\0020\002\032J\020\t\032\0020\002*\b\022\004\022\0020\0020\n2\006\020\013\032\0020\0062\022\020\f\032\016\022\004\022\0020\002\022\004\022\0020\0020\0012\024\020\r\032\020\022\004\022\0020\002\022\006\022\004\030\0010\0020\001H\b¢\006\002\b\016\032\024\020\017\032\0020\002*\0020\0022\b\b\002\020\020\032\0020\002\032\036\020\021\032\0020\002*\0020\0022\b\b\002\020\020\032\0020\0022\b\b\002\020\022\032\0020\002\032\n\020\023\032\0020\002*\0020\002\032\024\020\024\032\0020\002*\0020\0022\b\b\002\020\022\032\0020\002¨\006\025"}, d2={"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__IndentKt
  extends StringsKt__AppendableKt
{
  private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String paramString)
  {
    int i;
    if (((CharSequence)paramString).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramString = (Function1)getIndentFunction.1.INSTANCE;
    } else {
      paramString = (Function1)new Lambda(paramString)
      {
        final String $indent;
        
        public final String invoke(String paramAnonymousString)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousString, "line");
          return this.$indent + paramAnonymousString;
        }
      };
    }
    return paramString;
  }
  
  private static final int indentWidth$StringsKt__IndentKt(String paramString)
  {
    CharSequence localCharSequence = (CharSequence)paramString;
    int i = 0;
    int j = localCharSequence.length();
    while (i < j)
    {
      if ((CharsKt.isWhitespace(localCharSequence.charAt(i)) ^ true)) {
        break label45;
      }
      i++;
    }
    i = -1;
    label45:
    j = i;
    if (i == -1) {
      j = paramString.length();
    }
    return j;
  }
  
  public static final String prependIndent(String paramString1, String paramString2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "indent");
    paramString1 = SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence((CharSequence)paramString1), (Function1)new Lambda(paramString2)
    {
      final String $indent;
      
      public final String invoke(String paramAnonymousString)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousString, "it");
        if (StringsKt.isBlank((CharSequence)paramAnonymousString))
        {
          if (paramAnonymousString.length() < this.$indent.length()) {
            paramAnonymousString = this.$indent;
          }
        }
        else {
          paramAnonymousString = this.$indent + paramAnonymousString;
        }
        return paramAnonymousString;
      }
    }), (CharSequence)"\n", null, null, 0, null, null, 62, null);
    Log5ECF72.a(paramString1);
    LogE84000.a(paramString1);
    Log229316.a(paramString1);
    return paramString1;
  }
  
  private static final String reindent$StringsKt__IndentKt(List<String> paramList, int paramInt, Function1<? super String, String> paramFunction11, Function1<? super String, String> paramFunction12)
  {
    int k = 0;
    int j = CollectionsKt.getLastIndex(paramList);
    paramList = (Iterable)paramList;
    Collection localCollection = (Collection)new ArrayList();
    int i = 0;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = localIterator.next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      String str1 = (String)paramList;
      if (((i == 0) || (i == j)) && (StringsKt.isBlank((CharSequence)str1)))
      {
        paramList = null;
      }
      else
      {
        paramList = (String)paramFunction12.invoke(str1);
        if (paramList != null)
        {
          String str2 = (String)paramFunction11.invoke(paramList);
          paramList = str2;
          if (str2 != null) {
            break label139;
          }
        }
        paramList = str1;
      }
      label139:
      if (paramList != null) {
        localCollection.add(paramList);
      }
      i++;
    }
    paramList = (List)localCollection;
    paramList = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)paramList, (Appendable)new StringBuilder(paramInt), (CharSequence)"\n", null, null, 0, null, null, 124, null)).toString();
    Intrinsics.checkNotNullExpressionValue(paramList, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
    return paramList;
  }
  
  public static final String replaceIndent(String paramString1, String paramString2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "newIndent");
    Object localObject1 = StringsKt.lines((CharSequence)paramString1);
    Object localObject3 = (Iterable)localObject1;
    Object localObject2 = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject3).iterator();
    while (localIterator.hasNext())
    {
      localObject3 = localIterator.next();
      if ((StringsKt.isBlank((CharSequence)localObject3) ^ true)) {
        ((Collection)localObject2).add(localObject3);
      }
    }
    localObject2 = (List)localObject2;
    localObject3 = (Iterable)localObject2;
    localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
    localObject3 = ((Iterable)localObject3).iterator();
    while (((Iterator)localObject3).hasNext()) {
      ((Collection)localObject2).add(Integer.valueOf(indentWidth$StringsKt__IndentKt((String)((Iterator)localObject3).next())));
    }
    localObject2 = (List)localObject2;
    localObject2 = (Integer)CollectionsKt.minOrNull((Iterable)localObject2);
    int i;
    if (localObject2 != null) {
      i = ((Integer)localObject2).intValue();
    } else {
      i = 0;
    }
    int n = paramString1.length();
    int m = paramString2.length();
    int i1 = ((List)localObject1).size();
    localObject2 = getIndentFunction$StringsKt__IndentKt(paramString2);
    int k = CollectionsKt.getLastIndex((List)localObject1);
    paramString1 = (Iterable)localObject1;
    localObject3 = (Collection)new ArrayList();
    int j = 0;
    localIterator = paramString1.iterator();
    while (localIterator.hasNext())
    {
      paramString1 = localIterator.next();
      if (j < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      paramString2 = (String)paramString1;
      if (((j == 0) || (j == k)) && (StringsKt.isBlank((CharSequence)paramString2)))
      {
        paramString1 = null;
      }
      else
      {
        paramString1 = StringsKt.drop(paramString2, i);
        Log5ECF72.a(paramString1);
        LogE84000.a(paramString1);
        Log229316.a(paramString1);
        if (paramString1 != null)
        {
          localObject1 = (String)((Function1)localObject2).invoke(paramString1);
          paramString1 = (String)localObject1;
          if (localObject1 != null) {}
        }
        else
        {
          paramString1 = paramString2;
        }
      }
      if (paramString1 != null) {
        ((Collection)localObject3).add(paramString1);
      }
      j++;
    }
    paramString1 = (List)localObject3;
    paramString1 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)paramString1, (Appendable)new StringBuilder(n + m * i1), (CharSequence)"\n", null, null, 0, null, null, 124, null)).toString();
    Intrinsics.checkNotNullExpressionValue(paramString1, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
    return paramString1;
  }
  
  public static final String replaceIndentByMargin(String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "newIndent");
    Intrinsics.checkNotNullParameter(paramString3, "marginPrefix");
    if ((StringsKt.isBlank((CharSequence)paramString3) ^ true))
    {
      Object localObject = StringsKt.lines((CharSequence)paramString1);
      int i1 = paramString1.length();
      int k = paramString2.length();
      int m = ((List)localObject).size();
      Function1 localFunction1 = getIndentFunction$StringsKt__IndentKt(paramString2);
      int n = CollectionsKt.getLastIndex((List)localObject);
      paramString1 = (Iterable)localObject;
      paramString2 = (Collection)new ArrayList();
      int j = 0;
      Iterator localIterator = paramString1.iterator();
      while (localIterator.hasNext())
      {
        paramString1 = localIterator.next();
        if (j < 0) {
          CollectionsKt.throwIndexOverflow();
        }
        localObject = (String)paramString1;
        paramString1 = null;
        if (((j != 0) && (j != n)) || (!StringsKt.isBlank((CharSequence)localObject)))
        {
          paramString1 = (CharSequence)localObject;
          int i3 = 0;
          int i = 0;
          int i2 = paramString1.length();
          while (i < i2)
          {
            if ((CharsKt.isWhitespace(paramString1.charAt(i)) ^ true)) {
              break label212;
            }
            i++;
          }
          i = -1;
          label212:
          if (i == -1)
          {
            paramString1 = null;
          }
          else if (StringsKt.startsWith$default((String)localObject, paramString3, i, false, 4, null))
          {
            i2 = paramString3.length();
            Intrinsics.checkNotNull(localObject, "null cannot be cast to non-null type java.lang.String");
            paramString1 = ((String)localObject).substring(i2 + i);
            Intrinsics.checkNotNullExpressionValue(paramString1, "this as java.lang.String).substring(startIndex)");
          }
          else
          {
            paramString1 = null;
          }
          if (paramString1 != null)
          {
            String str = (String)localFunction1.invoke(paramString1);
            paramString1 = str;
            if (str != null) {}
          }
          else
          {
            paramString1 = (String)localObject;
          }
        }
        if (paramString1 != null) {
          paramString2.add(paramString1);
        }
        j++;
      }
      paramString1 = (List)paramString2;
      paramString1 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)paramString1, (Appendable)new StringBuilder(i1 + k * m), (CharSequence)"\n", null, null, 0, null, null, 124, null)).toString();
      Intrinsics.checkNotNullExpressionValue(paramString1, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return paramString1;
    }
    throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
  }
  
  public static final String trimIndent(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = StringsKt.replaceIndent(paramString, "");
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return paramString;
  }
  
  public static final String trimMargin(String paramString1, String paramString2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "marginPrefix");
    paramString1 = StringsKt.replaceIndentByMargin(paramString1, "", paramString2);
    Log5ECF72.a(paramString1);
    LogE84000.a(paramString1);
    Log229316.a(paramString1);
    return paramString1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__IndentKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */