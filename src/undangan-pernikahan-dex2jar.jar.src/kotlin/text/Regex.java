package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000f\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\"\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\013\n\000\n\002\020\r\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\005\n\002\020\000\n\002\b\003\030\000 02\0060\001j\002`\002:\00201B\017\b\026\022\006\020\003\032\0020\004¢\006\002\020\005B\027\b\026\022\006\020\003\032\0020\004\022\006\020\006\032\0020\007¢\006\002\020\bB\035\b\026\022\006\020\003\032\0020\004\022\f\020\t\032\b\022\004\022\0020\0070\n¢\006\002\020\013B\017\b\001\022\006\020\f\032\0020\r¢\006\002\020\016J\016\020\024\032\0020\0252\006\020\026\032\0020\027J\032\020\030\032\004\030\0010\0312\006\020\026\032\0020\0272\b\b\002\020\032\032\0020\033J\036\020\034\032\b\022\004\022\0020\0310\0352\006\020\026\032\0020\0272\b\b\002\020\032\032\0020\033J\032\020\036\032\004\030\0010\0312\006\020\026\032\0020\0272\006\020\037\032\0020\033H\007J\020\020 \032\004\030\0010\0312\006\020\026\032\0020\027J\021\020!\032\0020\0252\006\020\026\032\0020\027H\004J\030\020\"\032\0020\0252\006\020\026\032\0020\0272\006\020\037\032\0020\033H\007J\"\020#\032\0020\0042\006\020\026\032\0020\0272\022\020$\032\016\022\004\022\0020\031\022\004\022\0020\0270%J\026\020#\032\0020\0042\006\020\026\032\0020\0272\006\020&\032\0020\004J\026\020'\032\0020\0042\006\020\026\032\0020\0272\006\020&\032\0020\004J\036\020(\032\b\022\004\022\0020\0040)2\006\020\026\032\0020\0272\b\b\002\020*\032\0020\033J \020+\032\b\022\004\022\0020\0040\0352\006\020\026\032\0020\0272\b\b\002\020*\032\0020\033H\007J\006\020,\032\0020\rJ\b\020-\032\0020\004H\026J\b\020.\032\0020/H\002R\026\020\017\032\n\022\004\022\0020\007\030\0010\nX\016¢\006\002\n\000R\016\020\f\032\0020\rX\004¢\006\002\n\000R\027\020\t\032\b\022\004\022\0020\0070\n8F¢\006\006\032\004\b\020\020\021R\021\020\003\032\0020\0048F¢\006\006\032\004\b\022\020\023¨\0062"}, d2={"Lkotlin/text/Regex;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "(Ljava/lang/String;)V", "option", "Lkotlin/text/RegexOption;", "(Ljava/lang/String;Lkotlin/text/RegexOption;)V", "options", "", "(Ljava/lang/String;Ljava/util/Set;)V", "nativePattern", "Ljava/util/regex/Pattern;", "(Ljava/util/regex/Pattern;)V", "_options", "getOptions", "()Ljava/util/Set;", "getPattern", "()Ljava/lang/String;", "containsMatchIn", "", "input", "", "find", "Lkotlin/text/MatchResult;", "startIndex", "", "findAll", "Lkotlin/sequences/Sequence;", "matchAt", "index", "matchEntire", "matches", "matchesAt", "replace", "transform", "Lkotlin/Function1;", "replacement", "replaceFirst", "split", "", "limit", "splitToSequence", "toPattern", "toString", "writeReplace", "", "Companion", "Serialized", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class Regex
  implements Serializable
{
  public static final Companion Companion = new Companion(null);
  private Set<? extends RegexOption> _options;
  private final Pattern nativePattern;
  
  public Regex(String paramString)
  {
    this(paramString);
  }
  
  public Regex(String paramString, Set<? extends RegexOption> paramSet)
  {
    this(paramString);
  }
  
  public Regex(String paramString, RegexOption paramRegexOption)
  {
    this(paramString);
  }
  
  public Regex(Pattern paramPattern)
  {
    this.nativePattern = paramPattern;
  }
  
  private final Object writeReplace()
  {
    String str = this.nativePattern.pattern();
    Intrinsics.checkNotNullExpressionValue(str, "nativePattern.pattern()");
    return new Serialized(str, this.nativePattern.flags());
  }
  
  public final boolean containsMatchIn(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    return this.nativePattern.matcher(paramCharSequence).find();
  }
  
  public final MatchResult find(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    Matcher localMatcher = this.nativePattern.matcher(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(localMatcher, "nativePattern.matcher(input)");
    return RegexKt.access$findNext(localMatcher, paramInt, paramCharSequence);
  }
  
  public final Sequence<MatchResult> findAll(final CharSequence paramCharSequence, final int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    if ((paramInt >= 0) && (paramInt <= paramCharSequence.length())) {
      SequencesKt.generateSequence((Function0)new Lambda(paramCharSequence)
      {
        final Regex this$0;
        
        public final MatchResult invoke()
        {
          return this.this$0.find(paramCharSequence, paramInt);
        }
      }, (Function1)findAll.2.INSTANCE);
    }
    throw new IndexOutOfBoundsException("Start index out of bounds: " + paramInt + ", input length: " + paramCharSequence.length());
  }
  
  public final Set<RegexOption> getOptions()
  {
    Set localSet = this._options;
    Object localObject = localSet;
    if (localSet == null)
    {
      int i = this.nativePattern.flags();
      localObject = EnumSet.allOf(RegexOption.class);
      Intrinsics.checkNotNullExpressionValue(localObject, "");
      CollectionsKt.retainAll((Iterable)localObject, (Function1)new Lambda(i)
      {
        final int $value;
        
        public final Boolean invoke(RegexOption paramAnonymousRegexOption)
        {
          boolean bool;
          if ((this.$value & ((FlagEnum)paramAnonymousRegexOption).getMask()) == ((FlagEnum)paramAnonymousRegexOption).getValue()) {
            bool = true;
          } else {
            bool = false;
          }
          return Boolean.valueOf(bool);
        }
      });
      localObject = Collections.unmodifiableSet((Set)localObject);
      Intrinsics.checkNotNullExpressionValue(localObject, "unmodifiableSet(EnumSet.…mask == it.value }\n    })");
      this._options = ((Set)localObject);
    }
    return (Set<RegexOption>)localObject;
  }
  
  public final String getPattern()
  {
    String str = this.nativePattern.pattern();
    Intrinsics.checkNotNullExpressionValue(str, "nativePattern.pattern()");
    return str;
  }
  
  public final MatchResult matchAt(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    Matcher localMatcher = this.nativePattern.matcher(paramCharSequence).useAnchoringBounds(false).useTransparentBounds(true).region(paramInt, paramCharSequence.length());
    if (localMatcher.lookingAt())
    {
      Intrinsics.checkNotNullExpressionValue(localMatcher, "this");
      paramCharSequence = new MatcherMatchResult(localMatcher, paramCharSequence);
    }
    else
    {
      paramCharSequence = null;
    }
    return (MatchResult)paramCharSequence;
  }
  
  public final MatchResult matchEntire(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    Matcher localMatcher = this.nativePattern.matcher(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(localMatcher, "nativePattern.matcher(input)");
    return RegexKt.access$matchEntire(localMatcher, paramCharSequence);
  }
  
  public final boolean matches(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    return this.nativePattern.matcher(paramCharSequence).matches();
  }
  
  public final boolean matchesAt(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    return this.nativePattern.matcher(paramCharSequence).useAnchoringBounds(false).useTransparentBounds(true).region(paramInt, paramCharSequence.length()).lookingAt();
  }
  
  public final String replace(CharSequence paramCharSequence, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    Intrinsics.checkNotNullParameter(paramString, "replacement");
    paramCharSequence = this.nativePattern.matcher(paramCharSequence).replaceAll(paramString);
    Intrinsics.checkNotNullExpressionValue(paramCharSequence, "nativePattern.matcher(in…).replaceAll(replacement)");
    return paramCharSequence;
  }
  
  public final String replace(CharSequence paramCharSequence, Function1<? super MatchResult, ? extends CharSequence> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Object localObject = find$default(this, paramCharSequence, 0, 2, null);
    if (localObject == null) {
      return paramCharSequence.toString();
    }
    int i = 0;
    int k = paramCharSequence.length();
    StringBuilder localStringBuilder = new StringBuilder(k);
    int j;
    MatchResult localMatchResult;
    do
    {
      localStringBuilder.append(paramCharSequence, i, ((MatchResult)localObject).getRange().getStart().intValue());
      localStringBuilder.append((CharSequence)paramFunction1.invoke(localObject));
      j = ((MatchResult)localObject).getRange().getEndInclusive().intValue() + 1;
      localMatchResult = ((MatchResult)localObject).next();
      if (j >= k) {
        break;
      }
      localObject = localMatchResult;
      i = j;
    } while (localMatchResult != null);
    if (j < k) {
      localStringBuilder.append(paramCharSequence, j, k);
    }
    paramCharSequence = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramCharSequence, "sb.toString()");
    return paramCharSequence;
  }
  
  public final String replaceFirst(CharSequence paramCharSequence, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    Intrinsics.checkNotNullParameter(paramString, "replacement");
    paramCharSequence = this.nativePattern.matcher(paramCharSequence).replaceFirst(paramString);
    Intrinsics.checkNotNullExpressionValue(paramCharSequence, "nativePattern.matcher(in…replaceFirst(replacement)");
    return paramCharSequence;
  }
  
  public final List<String> split(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    StringsKt.requireNonNegativeLimit(paramInt);
    Matcher localMatcher = this.nativePattern.matcher(paramCharSequence);
    if ((paramInt != 1) && (localMatcher.find()))
    {
      int i = 10;
      if (paramInt > 0) {
        i = RangesKt.coerceAtMost(paramInt, 10);
      }
      ArrayList localArrayList = new ArrayList(i);
      i = 0;
      int j = paramInt - 1;
      paramInt = i;
      do
      {
        localArrayList.add(paramCharSequence.subSequence(paramInt, localMatcher.start()).toString());
        i = localMatcher.end();
        if ((j >= 0) && (localArrayList.size() == j)) {
          break;
        }
        paramInt = i;
      } while (localMatcher.find());
      localArrayList.add(paramCharSequence.subSequence(i, paramCharSequence.length()).toString());
      return (List)localArrayList;
    }
    return CollectionsKt.listOf(paramCharSequence.toString());
  }
  
  public final Sequence<String> splitToSequence(final CharSequence paramCharSequence, final int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "input");
    StringsKt.requireNonNegativeLimit(paramInt);
    SequencesKt.sequence((Function2)new RestrictedSuspendLambda(paramCharSequence, paramInt)
    {
      int I$0;
      private Object L$0;
      Object L$1;
      int label;
      final Regex this$0;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.this$0, paramCharSequence, paramInt, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super String> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i;
        Object localObject4;
        int j;
        Object localObject5;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 3: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 2: 
          localObject1 = this;
          i = ((1)localObject1).I$0;
          localObject3 = (Matcher)((1)localObject1).L$1;
          localObject2 = (SequenceScope)((1)localObject1).L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject1;
          localObject1 = localObject2;
          break;
        case 1: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = this;
          localObject2 = (SequenceScope)((1)paramAnonymousObject).L$0;
          localObject4 = Regex.access$getNativePattern$p(((1)paramAnonymousObject).this$0).matcher(paramCharSequence);
          if ((paramInt == 1) || (!((Matcher)localObject4).find())) {
            break label357;
          }
          i = 0;
          j = 0;
          localObject5 = paramAnonymousObject;
        }
        int k;
        do
        {
          String str = paramCharSequence.subSequence(j, ((Matcher)localObject4).start()).toString();
          Continuation localContinuation = (Continuation)localObject5;
          ((1)localObject5).L$0 = localObject2;
          ((1)localObject5).L$1 = localObject4;
          ((1)localObject5).I$0 = i;
          ((1)localObject5).label = 2;
          paramAnonymousObject = localObject5;
          localObject3 = localObject4;
          localObject1 = localObject2;
          if (((SequenceScope)localObject2).yield(str, localContinuation) == localObject6) {
            return localObject6;
          }
          k = ((Matcher)localObject3).end();
          i++;
          if (i == paramInt - 1) {
            break;
          }
          localObject5 = paramAnonymousObject;
          localObject4 = localObject3;
          localObject2 = localObject1;
          j = k;
        } while (((Matcher)localObject3).find());
        Object localObject2 = paramCharSequence;
        Object localObject3 = ((CharSequence)localObject2).subSequence(k, ((CharSequence)localObject2).length()).toString();
        localObject2 = (Continuation)paramAnonymousObject;
        ((1)paramAnonymousObject).L$0 = null;
        ((1)paramAnonymousObject).L$1 = null;
        ((1)paramAnonymousObject).label = 3;
        if (((SequenceScope)localObject1).yield(localObject3, (Continuation)localObject2) == localObject6) {
          return localObject6;
        }
        return Unit.INSTANCE;
        label357:
        localObject3 = paramCharSequence.toString();
        Object localObject1 = (Continuation)paramAnonymousObject;
        ((1)paramAnonymousObject).label = 1;
        if (((SequenceScope)localObject2).yield(localObject3, (Continuation)localObject1) == localObject6) {
          return localObject6;
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public final Pattern toPattern()
  {
    return this.nativePattern;
  }
  
  public String toString()
  {
    String str = this.nativePattern.toString();
    Intrinsics.checkNotNullExpressionValue(str, "nativePattern.toString()");
    return str;
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\016\n\002\b\003\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\004H\002J\016\020\006\032\0020\0072\006\020\b\032\0020\007J\016\020\t\032\0020\0072\006\020\b\032\0020\007J\016\020\n\032\0020\0132\006\020\b\032\0020\007¨\006\f"}, d2={"Lkotlin/text/Regex$Companion;", "", "()V", "ensureUnicodeCase", "", "flags", "escape", "", "literal", "escapeReplacement", "fromLiteral", "Lkotlin/text/Regex;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    private final int ensureUnicodeCase(int paramInt)
    {
      if ((paramInt & 0x2) != 0) {
        paramInt |= 0x40;
      }
      return paramInt;
    }
    
    public final String escape(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "literal");
      paramString = Pattern.quote(paramString);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      Intrinsics.checkNotNullExpressionValue(paramString, "quote(literal)");
      return paramString;
    }
    
    public final String escapeReplacement(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "literal");
      paramString = Matcher.quoteReplacement(paramString);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      Intrinsics.checkNotNullExpressionValue(paramString, "quoteReplacement(literal)");
      return paramString;
    }
    
    public final Regex fromLiteral(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "literal");
      return new Regex(paramString, RegexOption.LITERAL);
    }
  }
  
  @Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\b\n\002\b\006\n\002\020\000\n\002\b\002\b\002\030\000 \0162\0060\001j\002`\002:\001\016B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006¢\006\002\020\007J\b\020\f\032\0020\rH\002R\021\020\005\032\0020\006¢\006\b\n\000\032\004\b\b\020\tR\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\n\020\013¨\006\017"}, d2={"Lkotlin/text/Regex$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "flags", "", "(Ljava/lang/String;I)V", "getFlags", "()I", "getPattern", "()Ljava/lang/String;", "readResolve", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Serialized
    implements Serializable
  {
    public static final Companion Companion = new Companion(null);
    private static final long serialVersionUID = 0L;
    private final int flags;
    private final String pattern;
    
    public Serialized(String paramString, int paramInt)
    {
      this.pattern = paramString;
      this.flags = paramInt;
    }
    
    private final Object readResolve()
    {
      Pattern localPattern = Pattern.compile(this.pattern, this.flags);
      Intrinsics.checkNotNullExpressionValue(localPattern, "compile(pattern, flags)");
      return new Regex(localPattern);
    }
    
    public final int getFlags()
    {
      return this.flags;
    }
    
    public final String getPattern()
    {
      return this.pattern;
    }
    
    @Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lkotlin/text/Regex$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
    public static final class Companion {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/Regex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */