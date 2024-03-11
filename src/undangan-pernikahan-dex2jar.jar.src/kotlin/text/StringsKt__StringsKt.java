package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\001\n\000\n\002\030\002\n\002\020\r\n\002\b\003\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\016\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\f\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020\036\n\002\b\n\n\002\030\002\n\002\b\b\n\002\020\031\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\002\b\b\n\002\020\021\n\002\b\016\n\002\030\002\n\002\030\002\n\002\b!\032\020\020\t\032\0020\n2\006\020\013\032\0020\006H\000\032\034\020\f\032\0020\r*\0020\0022\006\020\016\032\0020\0022\b\b\002\020\017\032\0020\020\032\034\020\021\032\0020\r*\0020\0022\006\020\016\032\0020\0022\b\b\002\020\017\032\0020\020\032\037\020\022\032\0020\020*\0020\0022\006\020\023\032\0020\0242\b\b\002\020\017\032\0020\020H\002\032\037\020\022\032\0020\020*\0020\0022\006\020\016\032\0020\0022\b\b\002\020\017\032\0020\020H\002\032\025\020\022\032\0020\020*\0020\0022\006\020\025\032\0020\026H\n\032\030\020\027\032\0020\020*\004\030\0010\0022\b\020\016\032\004\030\0010\002H\000\032\030\020\030\032\0020\020*\004\030\0010\0022\b\020\016\032\004\030\0010\002H\000\032\034\020\031\032\0020\020*\0020\0022\006\020\023\032\0020\0242\b\b\002\020\017\032\0020\020\032\034\020\031\032\0020\020*\0020\0022\006\020\032\032\0020\0022\b\b\002\020\017\032\0020\020\032:\020\033\032\020\022\004\022\0020\006\022\004\022\0020\r\030\0010\034*\0020\0022\f\020\035\032\b\022\004\022\0020\r0\0362\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032E\020\033\032\020\022\004\022\0020\006\022\004\022\0020\r\030\0010\034*\0020\0022\f\020\035\032\b\022\004\022\0020\r0\0362\006\020\037\032\0020\0062\006\020\017\032\0020\0202\006\020 \032\0020\020H\002¢\006\002\b!\032:\020\"\032\020\022\004\022\0020\006\022\004\022\0020\r\030\0010\034*\0020\0022\f\020\035\032\b\022\004\022\0020\r0\0362\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032\022\020#\032\0020\020*\0020\0022\006\020$\032\0020\006\0327\020%\032\002H&\"\f\b\000\020'*\0020\002*\002H&\"\004\b\001\020&*\002H'2\f\020(\032\b\022\004\022\002H&0)H\bø\001\000¢\006\002\020*\0327\020+\032\002H&\"\f\b\000\020'*\0020\002*\002H&\"\004\b\001\020&*\002H'2\f\020(\032\b\022\004\022\002H&0)H\bø\001\000¢\006\002\020*\032&\020,\032\0020\006*\0020\0022\006\020\023\032\0020\0242\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032;\020,\032\0020\006*\0020\0022\006\020\016\032\0020\0022\006\020\037\032\0020\0062\006\020-\032\0020\0062\006\020\017\032\0020\0202\b\b\002\020 \032\0020\020H\002¢\006\002\b.\032&\020,\032\0020\006*\0020\0022\006\020/\032\0020\r2\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032&\0200\032\0020\006*\0020\0022\006\0201\032\002022\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032,\0200\032\0020\006*\0020\0022\f\020\035\032\b\022\004\022\0020\r0\0362\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032\r\0203\032\0020\020*\0020\002H\b\032\r\0204\032\0020\020*\0020\002H\b\032\r\0205\032\0020\020*\0020\002H\b\032 \0206\032\0020\020*\004\030\0010\002H\b\002\016\n\f\b\000\022\002\030\001\032\004\b\003\020\000\032 \0207\032\0020\020*\004\030\0010\002H\b\002\016\n\f\b\000\022\002\030\001\032\004\b\003\020\000\032\r\0208\032\00209*\0020\002H\002\032&\020:\032\0020\006*\0020\0022\006\020\023\032\0020\0242\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032&\020:\032\0020\006*\0020\0022\006\020/\032\0020\r2\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032&\020;\032\0020\006*\0020\0022\006\0201\032\002022\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032,\020;\032\0020\006*\0020\0022\f\020\035\032\b\022\004\022\0020\r0\0362\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032\020\020<\032\b\022\004\022\0020\r0=*\0020\002\032\020\020>\032\b\022\004\022\0020\r0?*\0020\002\032\025\020@\032\0020\020*\0020\0022\006\020\025\032\0020\026H\f\032\017\020A\032\0020\r*\004\030\0010\rH\b\032\034\020B\032\0020\002*\0020\0022\006\020C\032\0020\0062\b\b\002\020D\032\0020\024\032\034\020B\032\0020\r*\0020\r2\006\020C\032\0020\0062\b\b\002\020D\032\0020\024\032\034\020E\032\0020\002*\0020\0022\006\020C\032\0020\0062\b\b\002\020D\032\0020\024\032\034\020E\032\0020\r*\0020\r2\006\020C\032\0020\0062\b\b\002\020D\032\0020\024\032G\020F\032\b\022\004\022\0020\0010=*\0020\0022\016\020G\032\n\022\006\b\001\022\0020\r0H2\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\0202\b\b\002\020\013\032\0020\006H\002¢\006\004\bI\020J\032=\020F\032\b\022\004\022\0020\0010=*\0020\0022\006\020G\032\002022\b\b\002\020\037\032\0020\0062\b\b\002\020\017\032\0020\0202\b\b\002\020\013\032\0020\006H\002¢\006\002\bI\0324\020K\032\0020\020*\0020\0022\006\020L\032\0020\0062\006\020\016\032\0020\0022\006\020M\032\0020\0062\006\020C\032\0020\0062\006\020\017\032\0020\020H\000\032\022\020N\032\0020\002*\0020\0022\006\020O\032\0020\002\032\022\020N\032\0020\r*\0020\r2\006\020O\032\0020\002\032\032\020P\032\0020\002*\0020\0022\006\020\037\032\0020\0062\006\020-\032\0020\006\032\022\020P\032\0020\002*\0020\0022\006\020Q\032\0020\001\032\035\020P\032\0020\r*\0020\r2\006\020\037\032\0020\0062\006\020-\032\0020\006H\b\032\025\020P\032\0020\r*\0020\r2\006\020Q\032\0020\001H\b\032\022\020R\032\0020\002*\0020\0022\006\020\032\032\0020\002\032\022\020R\032\0020\r*\0020\r2\006\020\032\032\0020\002\032\022\020S\032\0020\002*\0020\0022\006\020T\032\0020\002\032\032\020S\032\0020\002*\0020\0022\006\020O\032\0020\0022\006\020\032\032\0020\002\032\022\020S\032\0020\r*\0020\r2\006\020T\032\0020\002\032\032\020S\032\0020\r*\0020\r2\006\020O\032\0020\0022\006\020\032\032\0020\002\032.\020U\032\0020\r*\0020\0022\006\020\025\032\0020\0262\024\b\b\020V\032\016\022\004\022\0020X\022\004\022\0020\0020WH\bø\001\000\032\035\020U\032\0020\r*\0020\0022\006\020\025\032\0020\0262\006\020Y\032\0020\rH\b\032$\020Z\032\0020\r*\0020\r2\006\020T\032\0020\0242\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032$\020Z\032\0020\r*\0020\r2\006\020T\032\0020\r2\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032$\020\\\032\0020\r*\0020\r2\006\020T\032\0020\0242\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032$\020\\\032\0020\r*\0020\r2\006\020T\032\0020\r2\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032$\020]\032\0020\r*\0020\r2\006\020T\032\0020\0242\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032$\020]\032\0020\r*\0020\r2\006\020T\032\0020\r2\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032$\020^\032\0020\r*\0020\r2\006\020T\032\0020\0242\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032$\020^\032\0020\r*\0020\r2\006\020T\032\0020\r2\006\020Y\032\0020\r2\b\b\002\020[\032\0020\r\032\035\020_\032\0020\r*\0020\0022\006\020\025\032\0020\0262\006\020Y\032\0020\rH\b\032)\020`\032\0020\r*\0020\r2\022\020V\032\016\022\004\022\0020\024\022\004\022\0020\0240WH\bø\001\000¢\006\002\ba\032)\020`\032\0020\r*\0020\r2\022\020V\032\016\022\004\022\0020\024\022\004\022\0020\0020WH\bø\001\000¢\006\002\bb\032\"\020c\032\0020\002*\0020\0022\006\020\037\032\0020\0062\006\020-\032\0020\0062\006\020Y\032\0020\002\032\032\020c\032\0020\002*\0020\0022\006\020Q\032\0020\0012\006\020Y\032\0020\002\032%\020c\032\0020\r*\0020\r2\006\020\037\032\0020\0062\006\020-\032\0020\0062\006\020Y\032\0020\002H\b\032\035\020c\032\0020\r*\0020\r2\006\020Q\032\0020\0012\006\020Y\032\0020\002H\b\032=\020d\032\b\022\004\022\0020\r0?*\0020\0022\022\020G\032\n\022\006\b\001\022\0020\r0H\"\0020\r2\b\b\002\020\017\032\0020\0202\b\b\002\020\013\032\0020\006¢\006\002\020e\0320\020d\032\b\022\004\022\0020\r0?*\0020\0022\n\020G\032\00202\"\0020\0242\b\b\002\020\017\032\0020\0202\b\b\002\020\013\032\0020\006\032/\020d\032\b\022\004\022\0020\r0?*\0020\0022\006\020T\032\0020\r2\006\020\017\032\0020\0202\006\020\013\032\0020\006H\002¢\006\002\bf\032%\020d\032\b\022\004\022\0020\r0?*\0020\0022\006\020\025\032\0020\0262\b\b\002\020\013\032\0020\006H\b\032=\020g\032\b\022\004\022\0020\r0=*\0020\0022\022\020G\032\n\022\006\b\001\022\0020\r0H\"\0020\r2\b\b\002\020\017\032\0020\0202\b\b\002\020\013\032\0020\006¢\006\002\020h\0320\020g\032\b\022\004\022\0020\r0=*\0020\0022\n\020G\032\00202\"\0020\0242\b\b\002\020\017\032\0020\0202\b\b\002\020\013\032\0020\006\032%\020g\032\b\022\004\022\0020\r0=*\0020\0022\006\020\025\032\0020\0262\b\b\002\020\013\032\0020\006H\b\032\034\020i\032\0020\020*\0020\0022\006\020\023\032\0020\0242\b\b\002\020\017\032\0020\020\032\034\020i\032\0020\020*\0020\0022\006\020O\032\0020\0022\b\b\002\020\017\032\0020\020\032$\020i\032\0020\020*\0020\0022\006\020O\032\0020\0022\006\020\037\032\0020\0062\b\b\002\020\017\032\0020\020\032\022\020j\032\0020\002*\0020\0022\006\020Q\032\0020\001\032\035\020j\032\0020\002*\0020\r2\006\020k\032\0020\0062\006\020l\032\0020\006H\b\032\037\020m\032\0020\r*\0020\0022\006\020\037\032\0020\0062\b\b\002\020-\032\0020\006H\b\032\022\020m\032\0020\r*\0020\0022\006\020Q\032\0020\001\032\022\020m\032\0020\r*\0020\r2\006\020Q\032\0020\001\032\034\020n\032\0020\r*\0020\r2\006\020T\032\0020\0242\b\b\002\020[\032\0020\r\032\034\020n\032\0020\r*\0020\r2\006\020T\032\0020\r2\b\b\002\020[\032\0020\r\032\034\020o\032\0020\r*\0020\r2\006\020T\032\0020\0242\b\b\002\020[\032\0020\r\032\034\020o\032\0020\r*\0020\r2\006\020T\032\0020\r2\b\b\002\020[\032\0020\r\032\034\020p\032\0020\r*\0020\r2\006\020T\032\0020\0242\b\b\002\020[\032\0020\r\032\034\020p\032\0020\r*\0020\r2\006\020T\032\0020\r2\b\b\002\020[\032\0020\r\032\034\020q\032\0020\r*\0020\r2\006\020T\032\0020\0242\b\b\002\020[\032\0020\r\032\034\020q\032\0020\r*\0020\r2\006\020T\032\0020\r2\b\b\002\020[\032\0020\r\032\f\020r\032\0020\020*\0020\rH\007\032\023\020s\032\004\030\0010\020*\0020\rH\007¢\006\002\020t\032\n\020u\032\0020\002*\0020\002\032$\020u\032\0020\002*\0020\0022\022\020v\032\016\022\004\022\0020\024\022\004\022\0020\0200WH\bø\001\000\032\026\020u\032\0020\002*\0020\0022\n\0201\032\00202\"\0020\024\032\r\020u\032\0020\r*\0020\rH\b\032$\020u\032\0020\r*\0020\r2\022\020v\032\016\022\004\022\0020\024\022\004\022\0020\0200WH\bø\001\000\032\026\020u\032\0020\r*\0020\r2\n\0201\032\00202\"\0020\024\032\n\020w\032\0020\002*\0020\002\032$\020w\032\0020\002*\0020\0022\022\020v\032\016\022\004\022\0020\024\022\004\022\0020\0200WH\bø\001\000\032\026\020w\032\0020\002*\0020\0022\n\0201\032\00202\"\0020\024\032\r\020w\032\0020\r*\0020\rH\b\032$\020w\032\0020\r*\0020\r2\022\020v\032\016\022\004\022\0020\024\022\004\022\0020\0200WH\bø\001\000\032\026\020w\032\0020\r*\0020\r2\n\0201\032\00202\"\0020\024\032\n\020x\032\0020\002*\0020\002\032$\020x\032\0020\002*\0020\0022\022\020v\032\016\022\004\022\0020\024\022\004\022\0020\0200WH\bø\001\000\032\026\020x\032\0020\002*\0020\0022\n\0201\032\00202\"\0020\024\032\r\020x\032\0020\r*\0020\rH\b\032$\020x\032\0020\r*\0020\r2\022\020v\032\016\022\004\022\0020\024\022\004\022\0020\0200WH\bø\001\000\032\026\020x\032\0020\r*\0020\r2\n\0201\032\00202\"\0020\024\"\025\020\000\032\0020\001*\0020\0028F¢\006\006\032\004\b\003\020\004\"\025\020\005\032\0020\006*\0020\0028F¢\006\006\032\004\b\007\020\b\002\007\n\005\b20\001¨\006y"}, d2={"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "requireNonNegativeLimit", "", "limit", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "contentEqualsIgnoreCaseImpl", "contentEqualsImpl", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", "range", "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceFirstChar", "replaceFirstCharWithChar", "replaceFirstCharWithCharSequence", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "toBooleanStrict", "toBooleanStrictOrNull", "(Ljava/lang/String;)Ljava/lang/Boolean;", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__StringsKt
  extends StringsKt__StringsJVMKt
{
  public static final String commonPrefixWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "other");
    int j = Math.min(paramCharSequence1.length(), paramCharSequence2.length());
    for (int i = 0; (i < j) && (CharsKt.equals(paramCharSequence1.charAt(i), paramCharSequence2.charAt(i), paramBoolean)); i++) {}
    if (!StringsKt.hasSurrogatePairAt(paramCharSequence1, i - 1))
    {
      j = i;
      if (!StringsKt.hasSurrogatePairAt(paramCharSequence2, i - 1)) {}
    }
    else
    {
      j = i - 1;
    }
    return paramCharSequence1.subSequence(0, j).toString();
  }
  
  public static final String commonSuffixWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "other");
    int k = paramCharSequence1.length();
    int m = paramCharSequence2.length();
    int j = Math.min(k, m);
    for (int i = 0; (i < j) && (CharsKt.equals(paramCharSequence1.charAt(k - i - 1), paramCharSequence2.charAt(m - i - 1), paramBoolean)); i++) {}
    if (!StringsKt.hasSurrogatePairAt(paramCharSequence1, k - i - 1))
    {
      j = i;
      if (!StringsKt.hasSurrogatePairAt(paramCharSequence2, m - i - 1)) {}
    }
    else
    {
      j = i - 1;
    }
    return paramCharSequence1.subSequence(k - j, k).toString();
  }
  
  public static final boolean contains(CharSequence paramCharSequence, char paramChar, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if (StringsKt.indexOf$default(paramCharSequence, paramChar, 0, paramBoolean, 2, null) >= 0) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
  
  public static final boolean contains(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "other");
    boolean bool2 = paramCharSequence2 instanceof String;
    boolean bool1 = true;
    if (bool2)
    {
      if (StringsKt.indexOf$default(paramCharSequence1, (String)paramCharSequence2, 0, paramBoolean, 2, null) >= 0) {
        paramBoolean = bool1;
      } else {
        paramBoolean = false;
      }
    }
    else if (indexOf$StringsKt__StringsKt$default(paramCharSequence1, paramCharSequence2, 0, paramCharSequence1.length(), paramBoolean, false, 16, null) >= 0) {
      paramBoolean = bool1;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
  
  private static final boolean contains(CharSequence paramCharSequence, Regex paramRegex)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRegex, "regex");
    return paramRegex.containsMatchIn(paramCharSequence);
  }
  
  public static final boolean contentEqualsIgnoreCaseImpl(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    if (((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
      return StringsKt.equals((String)paramCharSequence1, (String)paramCharSequence2, true);
    }
    if (paramCharSequence1 == paramCharSequence2) {
      return true;
    }
    if ((paramCharSequence1 != null) && (paramCharSequence2 != null) && (paramCharSequence1.length() == paramCharSequence2.length()))
    {
      int i = 0;
      int j = paramCharSequence1.length();
      while (i < j)
      {
        if (!CharsKt.equals(paramCharSequence1.charAt(i), paramCharSequence2.charAt(i), true)) {
          return false;
        }
        i++;
      }
      return true;
    }
    return false;
  }
  
  public static final boolean contentEqualsImpl(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    if (((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
      return Intrinsics.areEqual(paramCharSequence1, paramCharSequence2);
    }
    if (paramCharSequence1 == paramCharSequence2) {
      return true;
    }
    if ((paramCharSequence1 != null) && (paramCharSequence2 != null) && (paramCharSequence1.length() == paramCharSequence2.length()))
    {
      int i = 0;
      int j = paramCharSequence1.length();
      while (i < j)
      {
        if (paramCharSequence1.charAt(i) != paramCharSequence2.charAt(i)) {
          return false;
        }
        i++;
      }
      return true;
    }
    return false;
  }
  
  public static final boolean endsWith(CharSequence paramCharSequence, char paramChar, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if ((paramCharSequence.length() > 0) && (CharsKt.equals(paramCharSequence.charAt(StringsKt.getLastIndex(paramCharSequence)), paramChar, paramBoolean))) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
  
  public static final boolean endsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "suffix");
    if ((!paramBoolean) && ((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
      return StringsKt.endsWith$default((String)paramCharSequence1, (String)paramCharSequence2, false, 2, null);
    }
    return StringsKt.regionMatchesImpl(paramCharSequence1, paramCharSequence1.length() - paramCharSequence2.length(), paramCharSequence2, 0, paramCharSequence2.length(), paramBoolean);
  }
  
  public static final Pair<Integer, String> findAnyOf(CharSequence paramCharSequence, Collection<String> paramCollection, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramCollection, "strings");
    return findAnyOf$StringsKt__StringsKt(paramCharSequence, paramCollection, paramInt, paramBoolean, false);
  }
  
  private static final Pair<Integer, String> findAnyOf$StringsKt__StringsKt(CharSequence paramCharSequence, Collection<String> paramCollection, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject = null;
    if ((!paramBoolean1) && (paramCollection.size() == 1))
    {
      paramCollection = (String)CollectionsKt.single((Iterable)paramCollection);
      if (!paramBoolean2) {
        paramInt = StringsKt.indexOf$default(paramCharSequence, paramCollection, paramInt, false, 4, null);
      } else {
        paramInt = StringsKt.lastIndexOf$default(paramCharSequence, paramCollection, paramInt, false, 4, null);
      }
      if (paramInt < 0) {
        paramCharSequence = (CharSequence)localObject;
      } else {
        paramCharSequence = TuplesKt.to(Integer.valueOf(paramInt), paramCollection);
      }
      return paramCharSequence;
    }
    if (!paramBoolean2) {
      localObject = (IntProgression)new IntRange(RangesKt.coerceAtLeast(paramInt, 0), paramCharSequence.length());
    } else {
      localObject = RangesKt.downTo(RangesKt.coerceAtMost(paramInt, StringsKt.getLastIndex(paramCharSequence)), 0);
    }
    int j;
    int i;
    Iterator localIterator;
    String str;
    if ((paramCharSequence instanceof String))
    {
      paramInt = ((IntProgression)localObject).getFirst();
      j = ((IntProgression)localObject).getLast();
      i = ((IntProgression)localObject).getStep();
      if (((i > 0) && (paramInt <= j)) || ((i < 0) && (j <= paramInt))) {
        for (;;)
        {
          localIterator = ((Iterable)paramCollection).iterator();
          while (localIterator.hasNext())
          {
            localObject = localIterator.next();
            str = (String)localObject;
            if (StringsKt.regionMatches(str, 0, (String)paramCharSequence, paramInt, str.length(), paramBoolean1)) {
              break label234;
            }
          }
          localObject = null;
          label234:
          localObject = (String)localObject;
          if (localObject != null) {
            return TuplesKt.to(Integer.valueOf(paramInt), localObject);
          }
          if (paramInt == j) {
            break;
          }
          paramInt += i;
        }
      }
    }
    else
    {
      paramInt = ((IntProgression)localObject).getFirst();
      i = ((IntProgression)localObject).getLast();
      j = ((IntProgression)localObject).getStep();
      if (((j > 0) && (paramInt <= i)) || ((j < 0) && (i <= paramInt))) {
        for (;;)
        {
          localIterator = ((Iterable)paramCollection).iterator();
          while (localIterator.hasNext())
          {
            localObject = localIterator.next();
            str = (String)localObject;
            if (StringsKt.regionMatchesImpl((CharSequence)str, 0, paramCharSequence, paramInt, str.length(), paramBoolean1)) {
              break label375;
            }
          }
          localObject = null;
          label375:
          localObject = (String)localObject;
          if (localObject != null) {
            return TuplesKt.to(Integer.valueOf(paramInt), localObject);
          }
          if (paramInt == i) {
            break;
          }
          paramInt += j;
        }
      }
    }
    return null;
  }
  
  public static final Pair<Integer, String> findLastAnyOf(CharSequence paramCharSequence, Collection<String> paramCollection, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramCollection, "strings");
    return findAnyOf$StringsKt__StringsKt(paramCharSequence, paramCollection, paramInt, paramBoolean, true);
  }
  
  public static final IntRange getIndices(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return new IntRange(0, paramCharSequence.length() - 1);
  }
  
  public static final int getLastIndex(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return paramCharSequence.length() - 1;
  }
  
  public static final boolean hasSurrogatePairAt(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i = paramCharSequence.length();
    boolean bool = false;
    if ((new IntRange(0, i - 2).contains(paramInt)) && (Character.isHighSurrogate(paramCharSequence.charAt(paramInt))) && (Character.isLowSurrogate(paramCharSequence.charAt(paramInt + 1)))) {
      bool = true;
    }
    return bool;
  }
  
  private static final <C extends CharSequence,  extends R, R> R ifBlank(C paramC, Function0<? extends R> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    if (StringsKt.isBlank(paramC)) {
      paramC = paramFunction0.invoke();
    }
    return paramC;
  }
  
  private static final <C extends CharSequence,  extends R, R> R ifEmpty(C paramC, Function0<? extends R> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    int i;
    if (paramC.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramC = paramFunction0.invoke();
    }
    return paramC;
  }
  
  public static final int indexOf(CharSequence paramCharSequence, char paramChar, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if ((!paramBoolean) && ((paramCharSequence instanceof String))) {
      paramInt = ((String)paramCharSequence).indexOf(paramChar, paramInt);
    } else {
      paramInt = StringsKt.indexOfAny(paramCharSequence, new char[] { paramChar }, paramInt, paramBoolean);
    }
    return paramInt;
  }
  
  public static final int indexOf(CharSequence paramCharSequence, String paramString, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "string");
    if ((!paramBoolean) && ((paramCharSequence instanceof String))) {
      paramInt = ((String)paramCharSequence).indexOf(paramString, paramInt);
    } else {
      paramInt = indexOf$StringsKt__StringsKt$default(paramCharSequence, (CharSequence)paramString, paramInt, paramCharSequence.length(), paramBoolean, false, 16, null);
    }
    return paramInt;
  }
  
  private static final int indexOf$StringsKt__StringsKt(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    IntProgression localIntProgression;
    if (!paramBoolean2) {
      localIntProgression = (IntProgression)new IntRange(RangesKt.coerceAtLeast(paramInt1, 0), RangesKt.coerceAtMost(paramInt2, paramCharSequence1.length()));
    } else {
      localIntProgression = RangesKt.downTo(RangesKt.coerceAtMost(paramInt1, StringsKt.getLastIndex(paramCharSequence1)), RangesKt.coerceAtLeast(paramInt2, 0));
    }
    int i;
    int j;
    if (((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String)))
    {
      paramInt2 = localIntProgression.getFirst();
      i = localIntProgression.getLast();
      j = localIntProgression.getStep();
      if (j > 0)
      {
        paramInt1 = paramInt2;
        if (paramInt2 <= i) {}
      }
      else
      {
        if ((j >= 0) || (i > paramInt2)) {
          break label233;
        }
        paramInt1 = paramInt2;
      }
      for (;;)
      {
        if (StringsKt.regionMatches((String)paramCharSequence2, 0, (String)paramCharSequence1, paramInt1, paramCharSequence2.length(), paramBoolean1)) {
          return paramInt1;
        }
        if (paramInt1 == i) {
          break;
        }
        paramInt1 += j;
      }
    }
    else
    {
      paramInt2 = localIntProgression.getFirst();
      j = localIntProgression.getLast();
      i = localIntProgression.getStep();
      if (i > 0)
      {
        paramInt1 = paramInt2;
        if (paramInt2 <= j) {}
      }
      else
      {
        if ((i >= 0) || (j > paramInt2)) {
          break label233;
        }
        paramInt1 = paramInt2;
      }
      for (;;)
      {
        if (StringsKt.regionMatchesImpl(paramCharSequence2, 0, paramCharSequence1, paramInt1, paramCharSequence2.length(), paramBoolean1)) {
          return paramInt1;
        }
        if (paramInt1 == j) {
          break;
        }
        paramInt1 += i;
      }
    }
    label233:
    return -1;
  }
  
  public static final int indexOfAny(CharSequence paramCharSequence, Collection<String> paramCollection, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramCollection, "strings");
    paramCharSequence = findAnyOf$StringsKt__StringsKt(paramCharSequence, paramCollection, paramInt, paramBoolean, false);
    if (paramCharSequence != null) {
      paramInt = ((Number)paramCharSequence.getFirst()).intValue();
    } else {
      paramInt = -1;
    }
    return paramInt;
  }
  
  public static final int indexOfAny(CharSequence paramCharSequence, char[] paramArrayOfChar, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "chars");
    int i;
    if ((!paramBoolean) && (paramArrayOfChar.length == 1) && ((paramCharSequence instanceof String)))
    {
      i = ArraysKt.single(paramArrayOfChar);
      return ((String)paramCharSequence).indexOf(i, paramInt);
    }
    IntIterator localIntIterator = new IntRange(RangesKt.coerceAtLeast(paramInt, 0), StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      i = localIntIterator.nextInt();
      char c = paramCharSequence.charAt(i);
      int j = paramArrayOfChar.length;
      for (paramInt = 0; paramInt < j; paramInt++) {
        if (CharsKt.equals(paramArrayOfChar[paramInt], c, paramBoolean))
        {
          paramInt = 1;
          break label130;
        }
      }
      paramInt = 0;
      label130:
      if (paramInt != 0) {
        return i;
      }
    }
    return -1;
  }
  
  private static final boolean isEmpty(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    boolean bool;
    if (paramCharSequence.length() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean isNotBlank(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.isBlank(paramCharSequence) ^ true;
  }
  
  private static final boolean isNotEmpty(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    boolean bool;
    if (paramCharSequence.length() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean isNullOrBlank(CharSequence paramCharSequence)
  {
    boolean bool;
    if ((paramCharSequence != null) && (!StringsKt.isBlank(paramCharSequence))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static final boolean isNullOrEmpty(CharSequence paramCharSequence)
  {
    boolean bool;
    if ((paramCharSequence != null) && (paramCharSequence.length() != 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final CharIterator iterator(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    (CharIterator)new CharIterator()
    {
      final CharSequence $this_iterator;
      private int index;
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < this.$this_iterator.length()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public char nextChar()
      {
        CharSequence localCharSequence = this.$this_iterator;
        int i = this.index;
        this.index = (i + 1);
        return localCharSequence.charAt(i);
      }
    };
  }
  
  public static final int lastIndexOf(CharSequence paramCharSequence, char paramChar, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if ((!paramBoolean) && ((paramCharSequence instanceof String))) {
      paramInt = ((String)paramCharSequence).lastIndexOf(paramChar, paramInt);
    } else {
      paramInt = StringsKt.lastIndexOfAny(paramCharSequence, new char[] { paramChar }, paramInt, paramBoolean);
    }
    return paramInt;
  }
  
  public static final int lastIndexOf(CharSequence paramCharSequence, String paramString, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "string");
    if ((!paramBoolean) && ((paramCharSequence instanceof String))) {
      paramInt = ((String)paramCharSequence).lastIndexOf(paramString, paramInt);
    } else {
      paramInt = indexOf$StringsKt__StringsKt(paramCharSequence, (CharSequence)paramString, paramInt, 0, paramBoolean, true);
    }
    return paramInt;
  }
  
  public static final int lastIndexOfAny(CharSequence paramCharSequence, Collection<String> paramCollection, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramCollection, "strings");
    paramCharSequence = findAnyOf$StringsKt__StringsKt(paramCharSequence, paramCollection, paramInt, paramBoolean, true);
    if (paramCharSequence != null) {
      paramInt = ((Number)paramCharSequence.getFirst()).intValue();
    } else {
      paramInt = -1;
    }
    return paramInt;
  }
  
  public static final int lastIndexOfAny(CharSequence paramCharSequence, char[] paramArrayOfChar, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "chars");
    int i;
    if ((!paramBoolean) && (paramArrayOfChar.length == 1) && ((paramCharSequence instanceof String)))
    {
      i = ArraysKt.single(paramArrayOfChar);
      return ((String)paramCharSequence).lastIndexOf(i, paramInt);
    }
    for (paramInt = RangesKt.coerceAtMost(paramInt, StringsKt.getLastIndex(paramCharSequence)); -1 < paramInt; paramInt--)
    {
      char c = paramCharSequence.charAt(paramInt);
      int k = paramArrayOfChar.length;
      int j = 0;
      for (i = 0; i < k; i++) {
        if (CharsKt.equals(paramArrayOfChar[i], c, paramBoolean))
        {
          i = 1;
          break label116;
        }
      }
      i = j;
      label116:
      if (i != 0) {
        return paramInt;
      }
    }
    return -1;
  }
  
  public static final Sequence<String> lineSequence(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.splitToSequence$default(paramCharSequence, new String[] { "\r\n", "\n", "\r" }, false, 0, 6, null);
  }
  
  public static final List<String> lines(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return SequencesKt.toList(StringsKt.lineSequence(paramCharSequence));
  }
  
  private static final boolean matches(CharSequence paramCharSequence, Regex paramRegex)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRegex, "regex");
    return paramRegex.matches(paramCharSequence);
  }
  
  private static final String orEmpty(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    return paramString;
  }
  
  public static final CharSequence padEnd(CharSequence paramCharSequence, int paramInt, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if (paramInt >= 0)
    {
      if (paramInt <= paramCharSequence.length()) {
        return paramCharSequence.subSequence(0, paramCharSequence.length());
      }
      StringBuilder localStringBuilder = new StringBuilder(paramInt);
      localStringBuilder.append(paramCharSequence);
      paramCharSequence = new IntRange(1, paramInt - paramCharSequence.length()).iterator();
      while (paramCharSequence.hasNext())
      {
        paramCharSequence.nextInt();
        localStringBuilder.append(paramChar);
      }
      return (CharSequence)localStringBuilder;
    }
    throw new IllegalArgumentException("Desired length " + paramInt + " is less than zero.");
  }
  
  public static final String padEnd(String paramString, int paramInt, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.padEnd((CharSequence)paramString, paramInt, paramChar).toString();
  }
  
  public static final CharSequence padStart(CharSequence paramCharSequence, int paramInt, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if (paramInt >= 0)
    {
      if (paramInt <= paramCharSequence.length()) {
        return paramCharSequence.subSequence(0, paramCharSequence.length());
      }
      StringBuilder localStringBuilder = new StringBuilder(paramInt);
      IntIterator localIntIterator = new IntRange(1, paramInt - paramCharSequence.length()).iterator();
      while (localIntIterator.hasNext())
      {
        localIntIterator.nextInt();
        localStringBuilder.append(paramChar);
      }
      localStringBuilder.append(paramCharSequence);
      return (CharSequence)localStringBuilder;
    }
    throw new IllegalArgumentException("Desired length " + paramInt + " is less than zero.");
  }
  
  public static final String padStart(String paramString, int paramInt, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.padStart((CharSequence)paramString, paramInt, paramChar).toString();
  }
  
  private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence paramCharSequence, char[] paramArrayOfChar, int paramInt1, final boolean paramBoolean, int paramInt2)
  {
    StringsKt.requireNonNegativeLimit(paramInt2);
    (Sequence)new DelimitedRangesSequence(paramCharSequence, paramInt1, paramInt2, (Function2)new Lambda(paramArrayOfChar)
    {
      final char[] $delimiters;
      
      public final Pair<Integer, Integer> invoke(CharSequence paramAnonymousCharSequence, int paramAnonymousInt)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousCharSequence, "$this$$receiver");
        paramAnonymousInt = StringsKt.indexOfAny(paramAnonymousCharSequence, this.$delimiters, paramAnonymousInt, paramBoolean);
        if (paramAnonymousInt < 0) {
          paramAnonymousCharSequence = null;
        } else {
          paramAnonymousCharSequence = TuplesKt.to(Integer.valueOf(paramAnonymousInt), Integer.valueOf(1));
        }
        return paramAnonymousCharSequence;
      }
    });
  }
  
  private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence paramCharSequence, String[] paramArrayOfString, int paramInt1, final boolean paramBoolean, int paramInt2)
  {
    StringsKt.requireNonNegativeLimit(paramInt2);
    (Sequence)new DelimitedRangesSequence(paramCharSequence, paramInt1, paramInt2, (Function2)new Lambda(ArraysKt.asList(paramArrayOfString))
    {
      final List<String> $delimitersList;
      
      public final Pair<Integer, Integer> invoke(CharSequence paramAnonymousCharSequence, int paramAnonymousInt)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousCharSequence, "$this$$receiver");
        paramAnonymousCharSequence = StringsKt__StringsKt.access$findAnyOf(paramAnonymousCharSequence, (Collection)this.$delimitersList, paramAnonymousInt, paramBoolean, false);
        if (paramAnonymousCharSequence != null) {
          paramAnonymousCharSequence = TuplesKt.to(paramAnonymousCharSequence.getFirst(), Integer.valueOf(((String)paramAnonymousCharSequence.getSecond()).length()));
        } else {
          paramAnonymousCharSequence = null;
        }
        return paramAnonymousCharSequence;
      }
    });
  }
  
  public static final boolean regionMatchesImpl(CharSequence paramCharSequence1, int paramInt1, CharSequence paramCharSequence2, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "other");
    if ((paramInt2 >= 0) && (paramInt1 >= 0) && (paramInt1 <= paramCharSequence1.length() - paramInt3) && (paramInt2 <= paramCharSequence2.length() - paramInt3))
    {
      for (int i = 0; i < paramInt3; i++) {
        if (!CharsKt.equals(paramCharSequence1.charAt(paramInt1 + i), paramCharSequence2.charAt(paramInt2 + i), paramBoolean)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public static final CharSequence removePrefix(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "prefix");
    if (StringsKt.startsWith$default(paramCharSequence1, paramCharSequence2, false, 2, null)) {
      return paramCharSequence1.subSequence(paramCharSequence2.length(), paramCharSequence1.length());
    }
    return paramCharSequence1.subSequence(0, paramCharSequence1.length());
  }
  
  public static final String removePrefix(String paramString, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "prefix");
    if (StringsKt.startsWith$default((CharSequence)paramString, paramCharSequence, false, 2, null))
    {
      paramString = paramString.substring(paramCharSequence.length());
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
      return paramString;
    }
    return paramString;
  }
  
  public static final CharSequence removeRange(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if (paramInt2 >= paramInt1)
    {
      if (paramInt2 == paramInt1) {
        return paramCharSequence.subSequence(0, paramCharSequence.length());
      }
      StringBuilder localStringBuilder = new StringBuilder(paramCharSequence.length() - (paramInt2 - paramInt1));
      Intrinsics.checkNotNullExpressionValue(localStringBuilder.append(paramCharSequence, 0, paramInt1), "this.append(value, startIndex, endIndex)");
      Intrinsics.checkNotNullExpressionValue(localStringBuilder.append(paramCharSequence, paramInt2, paramCharSequence.length()), "this.append(value, startIndex, endIndex)");
      return (CharSequence)localStringBuilder;
    }
    throw new IndexOutOfBoundsException("End index (" + paramInt2 + ") is less than start index (" + paramInt1 + ").");
  }
  
  public static final CharSequence removeRange(CharSequence paramCharSequence, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    return StringsKt.removeRange(paramCharSequence, paramIntRange.getStart().intValue(), paramIntRange.getEndInclusive().intValue() + 1);
  }
  
  private static final String removeRange(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.removeRange((CharSequence)paramString, paramInt1, paramInt2).toString();
  }
  
  private static final String removeRange(String paramString, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    return StringsKt.removeRange((CharSequence)paramString, paramIntRange).toString();
  }
  
  public static final CharSequence removeSuffix(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "suffix");
    if (StringsKt.endsWith$default(paramCharSequence1, paramCharSequence2, false, 2, null)) {
      return paramCharSequence1.subSequence(0, paramCharSequence1.length() - paramCharSequence2.length());
    }
    return paramCharSequence1.subSequence(0, paramCharSequence1.length());
  }
  
  public static final String removeSuffix(String paramString, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "suffix");
    if (StringsKt.endsWith$default((CharSequence)paramString, paramCharSequence, false, 2, null))
    {
      paramString = paramString.substring(0, paramString.length() - paramCharSequence.length());
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
      return paramString;
    }
    return paramString;
  }
  
  public static final CharSequence removeSurrounding(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "delimiter");
    return StringsKt.removeSurrounding(paramCharSequence1, paramCharSequence2, paramCharSequence2);
  }
  
  public static final CharSequence removeSurrounding(CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "prefix");
    Intrinsics.checkNotNullParameter(paramCharSequence3, "suffix");
    if ((paramCharSequence1.length() >= paramCharSequence2.length() + paramCharSequence3.length()) && (StringsKt.startsWith$default(paramCharSequence1, paramCharSequence2, false, 2, null)) && (StringsKt.endsWith$default(paramCharSequence1, paramCharSequence3, false, 2, null))) {
      return paramCharSequence1.subSequence(paramCharSequence2.length(), paramCharSequence1.length() - paramCharSequence3.length());
    }
    return paramCharSequence1.subSequence(0, paramCharSequence1.length());
  }
  
  public static final String removeSurrounding(String paramString, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "delimiter");
    paramString = StringsKt.removeSurrounding(paramString, paramCharSequence, paramCharSequence);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return paramString;
  }
  
  public static final String removeSurrounding(String paramString, CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence1, "prefix");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "suffix");
    if ((paramString.length() >= paramCharSequence1.length() + paramCharSequence2.length()) && (StringsKt.startsWith$default((CharSequence)paramString, paramCharSequence1, false, 2, null)) && (StringsKt.endsWith$default((CharSequence)paramString, paramCharSequence2, false, 2, null)))
    {
      paramString = paramString.substring(paramCharSequence1.length(), paramString.length() - paramCharSequence2.length());
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
      return paramString;
    }
    return paramString;
  }
  
  private static final String replace(CharSequence paramCharSequence, Regex paramRegex, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRegex, "regex");
    Intrinsics.checkNotNullParameter(paramString, "replacement");
    return paramRegex.replace(paramCharSequence, paramString);
  }
  
  private static final String replace(CharSequence paramCharSequence, Regex paramRegex, Function1<? super MatchResult, ? extends CharSequence> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRegex, "regex");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    return paramRegex.replace(paramCharSequence, paramFunction1);
  }
  
  public static final String replaceAfter(String paramString1, char paramChar, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "replacement");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int j = StringsKt.indexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (j == -1)
    {
      paramString1 = paramString3;
    }
    else
    {
      int i = paramString1.length();
      paramString1 = StringsKt.replaceRange((CharSequence)paramString1, j + 1, i, (CharSequence)paramString2).toString();
    }
    return paramString1;
  }
  
  public static final String replaceAfter(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "replacement");
    Intrinsics.checkNotNullParameter(paramString4, "missingDelimiterValue");
    int i = StringsKt.indexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (i != -1)
    {
      int j = paramString2.length();
      int k = paramString1.length();
      paramString4 = StringsKt.replaceRange((CharSequence)paramString1, j + i, k, (CharSequence)paramString3).toString();
    }
    return paramString4;
  }
  
  public static final String replaceAfterLast(String paramString1, char paramChar, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "replacement");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int j = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (j == -1)
    {
      paramString1 = paramString3;
    }
    else
    {
      int i = paramString1.length();
      paramString1 = StringsKt.replaceRange((CharSequence)paramString1, j + 1, i, (CharSequence)paramString2).toString();
    }
    return paramString1;
  }
  
  public static final String replaceAfterLast(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "replacement");
    Intrinsics.checkNotNullParameter(paramString4, "missingDelimiterValue");
    int j = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (j == -1)
    {
      paramString1 = paramString4;
    }
    else
    {
      int i = paramString2.length();
      int k = paramString1.length();
      paramString1 = StringsKt.replaceRange((CharSequence)paramString1, i + j, k, (CharSequence)paramString3).toString();
    }
    return paramString1;
  }
  
  public static final String replaceBefore(String paramString1, char paramChar, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "replacement");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int i = StringsKt.indexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (i == -1) {
      paramString1 = paramString3;
    } else {
      paramString1 = StringsKt.replaceRange((CharSequence)paramString1, 0, i, (CharSequence)paramString2).toString();
    }
    return paramString1;
  }
  
  public static final String replaceBefore(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "replacement");
    Intrinsics.checkNotNullParameter(paramString4, "missingDelimiterValue");
    int i = StringsKt.indexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (i == -1) {
      paramString1 = paramString4;
    } else {
      paramString1 = StringsKt.replaceRange((CharSequence)paramString1, 0, i, (CharSequence)paramString3).toString();
    }
    return paramString1;
  }
  
  public static final String replaceBeforeLast(String paramString1, char paramChar, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "replacement");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int i = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (i != -1) {
      paramString3 = StringsKt.replaceRange((CharSequence)paramString1, 0, i, (CharSequence)paramString2).toString();
    }
    return paramString3;
  }
  
  public static final String replaceBeforeLast(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "replacement");
    Intrinsics.checkNotNullParameter(paramString4, "missingDelimiterValue");
    int i = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (i == -1) {
      paramString1 = paramString4;
    } else {
      paramString1 = StringsKt.replaceRange((CharSequence)paramString1, 0, i, (CharSequence)paramString3).toString();
    }
    return paramString1;
  }
  
  private static final String replaceFirst(CharSequence paramCharSequence, Regex paramRegex, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRegex, "regex");
    Intrinsics.checkNotNullParameter(paramString, "replacement");
    return paramRegex.replaceFirst(paramCharSequence, paramString);
  }
  
  private static final String replaceFirstCharWithChar(String paramString, Function1<? super Character, Character> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    int i;
    if (((CharSequence)paramString).length() > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      char c = ((Character)paramFunction1.invoke(Character.valueOf(paramString.charAt(0)))).charValue();
      paramString = paramString.substring(1);
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
      paramString = c + paramString;
    }
    return paramString;
  }
  
  private static final String replaceFirstCharWithCharSequence(String paramString, Function1<? super Character, ? extends CharSequence> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    int i;
    if (((CharSequence)paramString).length() > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramFunction1 = new StringBuilder().append(paramFunction1.invoke(Character.valueOf(paramString.charAt(0))));
      paramString = paramString.substring(1);
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
      paramString = paramString;
    }
    return paramString;
  }
  
  public static final CharSequence replaceRange(CharSequence paramCharSequence1, int paramInt1, int paramInt2, CharSequence paramCharSequence2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "replacement");
    if (paramInt2 >= paramInt1)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Intrinsics.checkNotNullExpressionValue(localStringBuilder.append(paramCharSequence1, 0, paramInt1), "this.append(value, startIndex, endIndex)");
      localStringBuilder.append(paramCharSequence2);
      Intrinsics.checkNotNullExpressionValue(localStringBuilder.append(paramCharSequence1, paramInt2, paramCharSequence1.length()), "this.append(value, startIndex, endIndex)");
      return (CharSequence)localStringBuilder;
    }
    throw new IndexOutOfBoundsException("End index (" + paramInt2 + ") is less than start index (" + paramInt1 + ").");
  }
  
  public static final CharSequence replaceRange(CharSequence paramCharSequence1, IntRange paramIntRange, CharSequence paramCharSequence2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "replacement");
    return StringsKt.replaceRange(paramCharSequence1, paramIntRange.getStart().intValue(), paramIntRange.getEndInclusive().intValue() + 1, paramCharSequence2);
  }
  
  private static final String replaceRange(String paramString, int paramInt1, int paramInt2, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "replacement");
    return StringsKt.replaceRange((CharSequence)paramString, paramInt1, paramInt2, paramCharSequence).toString();
  }
  
  private static final String replaceRange(String paramString, IntRange paramIntRange, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    Intrinsics.checkNotNullParameter(paramCharSequence, "replacement");
    return StringsKt.replaceRange((CharSequence)paramString, paramIntRange, paramCharSequence).toString();
  }
  
  public static final void requireNonNegativeLimit(int paramInt)
  {
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return;
    }
    throw new IllegalArgumentException(("Limit must be non-negative, but was " + paramInt).toString());
  }
  
  private static final List<String> split(CharSequence paramCharSequence, Regex paramRegex, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRegex, "regex");
    return paramRegex.split(paramCharSequence, paramInt);
  }
  
  public static final List<String> split(CharSequence paramCharSequence, char[] paramArrayOfChar, boolean paramBoolean, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "delimiters");
    if (paramArrayOfChar.length == 1)
    {
      paramArrayOfChar = String.valueOf(paramArrayOfChar[0]);
      Log5ECF72.a(paramArrayOfChar);
      LogE84000.a(paramArrayOfChar);
      Log229316.a(paramArrayOfChar);
      return split$StringsKt__StringsKt(paramCharSequence, paramArrayOfChar, paramBoolean, paramInt);
    }
    Object localObject = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(paramCharSequence, paramArrayOfChar, 0, paramBoolean, paramInt, 2, null));
    paramArrayOfChar = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = StringsKt.substring(paramCharSequence, (IntRange)((Iterator)localObject).next());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      paramArrayOfChar.add(str);
    }
    paramCharSequence = (List)paramArrayOfChar;
    return paramCharSequence;
  }
  
  public static final List<String> split(CharSequence paramCharSequence, String[] paramArrayOfString, boolean paramBoolean, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfString, "delimiters");
    int j = paramArrayOfString.length;
    int i = 1;
    if (j == 1)
    {
      localObject = paramArrayOfString[0];
      if (((CharSequence)localObject).length() != 0) {
        i = 0;
      }
      if (i == 0) {
        return split$StringsKt__StringsKt(paramCharSequence, (String)localObject, paramBoolean, paramInt);
      }
    }
    Object localObject = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(paramCharSequence, paramArrayOfString, 0, paramBoolean, paramInt, 2, null));
    paramArrayOfString = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = StringsKt.substring(paramCharSequence, (IntRange)((Iterator)localObject).next());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      paramArrayOfString.add(str);
    }
    paramCharSequence = (List)paramArrayOfString;
    return paramCharSequence;
  }
  
  private static final List<String> split$StringsKt__StringsKt(CharSequence paramCharSequence, String paramString, boolean paramBoolean, int paramInt)
  {
    StringsKt.requireNonNegativeLimit(paramInt);
    int k = 0;
    int m = StringsKt.indexOf(paramCharSequence, paramString, 0, paramBoolean);
    if (m != -1)
    {
      int i = 1;
      if (paramInt != 1)
      {
        if (paramInt <= 0) {
          i = 0;
        }
        int j = 10;
        if (i != 0) {
          j = RangesKt.coerceAtMost(paramInt, 10);
        }
        ArrayList localArrayList = new ArrayList(j);
        j = m;
        int n;
        do
        {
          localArrayList.add(paramCharSequence.subSequence(k, j).toString());
          m = j + paramString.length();
          if ((i != 0) && (localArrayList.size() == paramInt - 1)) {
            break;
          }
          n = StringsKt.indexOf(paramCharSequence, paramString, m, paramBoolean);
          k = m;
          j = n;
        } while (n != -1);
        localArrayList.add(paramCharSequence.subSequence(m, paramCharSequence.length()).toString());
        return (List)localArrayList;
      }
    }
    return CollectionsKt.listOf(paramCharSequence.toString());
  }
  
  private static final Sequence<String> splitToSequence(CharSequence paramCharSequence, Regex paramRegex, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRegex, "regex");
    return paramRegex.splitToSequence(paramCharSequence, paramInt);
  }
  
  public static final Sequence<String> splitToSequence(CharSequence paramCharSequence, char[] paramArrayOfChar, boolean paramBoolean, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "delimiters");
    SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default(paramCharSequence, paramArrayOfChar, 0, paramBoolean, paramInt, 2, null), (Function1)new Lambda(paramCharSequence)
    {
      final CharSequence $this_splitToSequence;
      
      public final String invoke(IntRange paramAnonymousIntRange)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousIntRange, "it");
        paramAnonymousIntRange = StringsKt.substring(this.$this_splitToSequence, paramAnonymousIntRange);
        Log5ECF72.a(paramAnonymousIntRange);
        LogE84000.a(paramAnonymousIntRange);
        Log229316.a(paramAnonymousIntRange);
        return paramAnonymousIntRange;
      }
    });
  }
  
  public static final Sequence<String> splitToSequence(CharSequence paramCharSequence, String[] paramArrayOfString, boolean paramBoolean, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfString, "delimiters");
    SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default(paramCharSequence, paramArrayOfString, 0, paramBoolean, paramInt, 2, null), (Function1)new Lambda(paramCharSequence)
    {
      final CharSequence $this_splitToSequence;
      
      public final String invoke(IntRange paramAnonymousIntRange)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousIntRange, "it");
        paramAnonymousIntRange = StringsKt.substring(this.$this_splitToSequence, paramAnonymousIntRange);
        Log5ECF72.a(paramAnonymousIntRange);
        LogE84000.a(paramAnonymousIntRange);
        Log229316.a(paramAnonymousIntRange);
        return paramAnonymousIntRange;
      }
    });
  }
  
  public static final boolean startsWith(CharSequence paramCharSequence, char paramChar, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i = paramCharSequence.length();
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (i > 0)
    {
      bool1 = bool2;
      if (CharsKt.equals(paramCharSequence.charAt(0), paramChar, paramBoolean)) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static final boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "prefix");
    if ((!paramBoolean) && ((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
      return StringsKt.startsWith$default((String)paramCharSequence1, (String)paramCharSequence2, paramInt, false, 4, null);
    }
    return StringsKt.regionMatchesImpl(paramCharSequence1, paramInt, paramCharSequence2, 0, paramCharSequence2.length(), paramBoolean);
  }
  
  public static final boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "prefix");
    if ((!paramBoolean) && ((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
      return StringsKt.startsWith$default((String)paramCharSequence1, (String)paramCharSequence2, false, 2, null);
    }
    return StringsKt.regionMatchesImpl(paramCharSequence1, 0, paramCharSequence2, 0, paramCharSequence2.length(), paramBoolean);
  }
  
  public static final CharSequence subSequence(CharSequence paramCharSequence, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    return paramCharSequence.subSequence(paramIntRange.getStart().intValue(), paramIntRange.getEndInclusive().intValue() + 1);
  }
  
  @Deprecated(message="Use parameters named startIndex and endIndex.", replaceWith=@ReplaceWith(expression="subSequence(startIndex = start, endIndex = end)", imports={}))
  private static final CharSequence subSequence(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return paramString.subSequence(paramInt1, paramInt2);
  }
  
  private static final String substring(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return paramCharSequence.subSequence(paramInt1, paramInt2).toString();
  }
  
  public static final String substring(CharSequence paramCharSequence, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    return paramCharSequence.subSequence(paramIntRange.getStart().intValue(), paramIntRange.getEndInclusive().intValue() + 1).toString();
  }
  
  public static final String substring(String paramString, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    paramString = paramString.substring(paramIntRange.getStart().intValue(), paramIntRange.getEndInclusive().intValue() + 1);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
    return paramString;
  }
  
  public static final String substringAfter(String paramString1, char paramChar, String paramString2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "missingDelimiterValue");
    int i = StringsKt.indexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (i != -1)
    {
      paramString2 = paramString1.substring(i + 1, paramString1.length());
      Intrinsics.checkNotNullExpressionValue(paramString2, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString2;
  }
  
  public static final String substringAfter(String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int i = StringsKt.indexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (i != -1)
    {
      paramString3 = paramString1.substring(paramString2.length() + i, paramString1.length());
      Intrinsics.checkNotNullExpressionValue(paramString3, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString3;
  }
  
  public static final String substringAfterLast(String paramString1, char paramChar, String paramString2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "missingDelimiterValue");
    int i = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (i == -1)
    {
      paramString1 = paramString2;
    }
    else
    {
      paramString1 = paramString1.substring(i + 1, paramString1.length());
      Intrinsics.checkNotNullExpressionValue(paramString1, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString1;
  }
  
  public static final String substringAfterLast(String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int i = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (i != -1)
    {
      paramString3 = paramString1.substring(paramString2.length() + i, paramString1.length());
      Intrinsics.checkNotNullExpressionValue(paramString3, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString3;
  }
  
  public static final String substringBefore(String paramString1, char paramChar, String paramString2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "missingDelimiterValue");
    int i = StringsKt.indexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (i != -1)
    {
      paramString2 = paramString1.substring(0, i);
      Intrinsics.checkNotNullExpressionValue(paramString2, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString2;
  }
  
  public static final String substringBefore(String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int i = StringsKt.indexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (i != -1)
    {
      paramString3 = paramString1.substring(0, i);
      Intrinsics.checkNotNullExpressionValue(paramString3, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString3;
  }
  
  public static final String substringBeforeLast(String paramString1, char paramChar, String paramString2)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "missingDelimiterValue");
    int i = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramChar, 0, false, 6, null);
    if (i != -1)
    {
      paramString2 = paramString1.substring(0, i);
      Intrinsics.checkNotNullExpressionValue(paramString2, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString2;
  }
  
  public static final String substringBeforeLast(String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "delimiter");
    Intrinsics.checkNotNullParameter(paramString3, "missingDelimiterValue");
    int i = StringsKt.lastIndexOf$default((CharSequence)paramString1, paramString2, 0, false, 6, null);
    if (i != -1)
    {
      paramString3 = paramString1.substring(0, i);
      Intrinsics.checkNotNullExpressionValue(paramString3, "this as java.lang.String…ing(startIndex, endIndex)");
    }
    return paramString3;
  }
  
  public static final boolean toBooleanStrict(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    boolean bool;
    if (Intrinsics.areEqual(paramString, "true"))
    {
      bool = true;
    }
    else
    {
      if (!Intrinsics.areEqual(paramString, "false")) {
        break label35;
      }
      bool = false;
    }
    return bool;
    label35:
    throw new IllegalArgumentException("The string doesn't represent a boolean value: " + paramString);
  }
  
  public static final Boolean toBooleanStrictOrNull(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    if (Intrinsics.areEqual(paramString, "true")) {
      paramString = Boolean.valueOf(true);
    } else if (Intrinsics.areEqual(paramString, "false")) {
      paramString = Boolean.valueOf(false);
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public static final CharSequence trim(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int j = 0;
    int i = paramCharSequence.length() - 1;
    int k = 0;
    while (j <= i)
    {
      int m;
      if (k == 0) {
        m = j;
      } else {
        m = i;
      }
      boolean bool = CharsKt.isWhitespace(paramCharSequence.charAt(m));
      if (k == 0)
      {
        if (!bool) {
          k = 1;
        } else {
          j++;
        }
      }
      else
      {
        if (!bool) {
          break;
        }
        i--;
      }
    }
    return paramCharSequence.subSequence(j, i + 1);
  }
  
  public static final CharSequence trim(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int j = 0;
    int i = paramCharSequence.length() - 1;
    int k = 0;
    while (j <= i)
    {
      int m;
      if (k == 0) {
        m = j;
      } else {
        m = i;
      }
      boolean bool = ((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(m)))).booleanValue();
      if (k == 0)
      {
        if (!bool) {
          k = 1;
        } else {
          j++;
        }
      }
      else
      {
        if (!bool) {
          break;
        }
        i--;
      }
    }
    return paramCharSequence.subSequence(j, i + 1);
  }
  
  public static final CharSequence trim(CharSequence paramCharSequence, char... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "chars");
    int j = 0;
    int i = paramCharSequence.length() - 1;
    int k = 0;
    while (j <= i)
    {
      int m;
      if (k == 0) {
        m = j;
      } else {
        m = i;
      }
      boolean bool = ArraysKt.contains(paramVarArgs, paramCharSequence.charAt(m));
      if (k == 0)
      {
        if (!bool) {
          k = 1;
        } else {
          j++;
        }
      }
      else
      {
        if (!bool) {
          break;
        }
        i--;
      }
    }
    return paramCharSequence.subSequence(j, i + 1);
  }
  
  private static final String trim(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.trim((CharSequence)paramString).toString();
  }
  
  public static final String trim(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    paramString = (CharSequence)paramString;
    int j = 0;
    int i = paramString.length() - 1;
    int k = 0;
    while (j <= i)
    {
      int m;
      if (k == 0) {
        m = j;
      } else {
        m = i;
      }
      boolean bool = ((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(m)))).booleanValue();
      if (k == 0)
      {
        if (!bool) {
          k = 1;
        } else {
          j++;
        }
      }
      else
      {
        if (!bool) {
          break;
        }
        i--;
      }
    }
    return paramString.subSequence(j, i + 1).toString();
  }
  
  public static final String trim(String paramString, char... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "chars");
    paramString = (CharSequence)paramString;
    int j = 0;
    int i = paramString.length() - 1;
    int k = 0;
    while (j <= i)
    {
      int m;
      if (k == 0) {
        m = j;
      } else {
        m = i;
      }
      boolean bool = ArraysKt.contains(paramVarArgs, paramString.charAt(m));
      if (k == 0)
      {
        if (!bool) {
          k = 1;
        } else {
          j++;
        }
      }
      else
      {
        if (!bool) {
          break;
        }
        i--;
      }
    }
    return paramString.subSequence(j, i + 1).toString();
  }
  
  public static final CharSequence trimEnd(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i = paramCharSequence.length() - 1;
    if (i >= 0)
    {
      int j;
      do
      {
        j = i - 1;
        if (!CharsKt.isWhitespace(paramCharSequence.charAt(i)))
        {
          paramCharSequence = paramCharSequence.subSequence(0, i + 1);
          break;
        }
        i = j;
      } while (j >= 0);
    }
    paramCharSequence = (CharSequence)"";
    return paramCharSequence;
  }
  
  public static final CharSequence trimEnd(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = paramCharSequence.length() - 1;
    if (i >= 0)
    {
      int j;
      do
      {
        j = i - 1;
        if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
          return paramCharSequence.subSequence(0, i + 1);
        }
        i = j;
      } while (j >= 0);
    }
    return (CharSequence)"";
  }
  
  public static final CharSequence trimEnd(CharSequence paramCharSequence, char... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "chars");
    int i = paramCharSequence.length() - 1;
    if (i >= 0)
    {
      int j;
      do
      {
        j = i - 1;
        if (!ArraysKt.contains(paramVarArgs, paramCharSequence.charAt(i)))
        {
          paramCharSequence = paramCharSequence.subSequence(0, i + 1);
          break;
        }
        i = j;
      } while (j >= 0);
    }
    paramCharSequence = (CharSequence)"";
    return paramCharSequence;
  }
  
  private static final String trimEnd(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.trimEnd((CharSequence)paramString).toString();
  }
  
  public static final String trimEnd(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    paramString = (CharSequence)paramString;
    int i = paramString.length() - 1;
    if (i >= 0)
    {
      int j;
      do
      {
        j = i - 1;
        if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(i)))).booleanValue())
        {
          paramString = paramString.subSequence(0, i + 1);
          break;
        }
        i = j;
      } while (j >= 0);
    }
    paramString = (CharSequence)"";
    return paramString.toString();
  }
  
  public static final String trimEnd(String paramString, char... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "chars");
    paramString = (CharSequence)paramString;
    int i = paramString.length() - 1;
    if (i >= 0)
    {
      int j;
      do
      {
        j = i - 1;
        if (!ArraysKt.contains(paramVarArgs, paramString.charAt(i)))
        {
          paramString = paramString.subSequence(0, i + 1);
          break;
        }
        i = j;
      } while (j >= 0);
    }
    paramString = (CharSequence)"";
    return paramString.toString();
  }
  
  public static final CharSequence trimStart(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      if (!CharsKt.isWhitespace(paramCharSequence.charAt(i))) {
        return paramCharSequence.subSequence(i, paramCharSequence.length());
      }
      i++;
    }
    paramCharSequence = (CharSequence)"";
    return paramCharSequence;
  }
  
  public static final CharSequence trimStart(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return paramCharSequence.subSequence(i, paramCharSequence.length());
      }
      i++;
    }
    return (CharSequence)"";
  }
  
  public static final CharSequence trimStart(CharSequence paramCharSequence, char... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "chars");
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      if (!ArraysKt.contains(paramVarArgs, paramCharSequence.charAt(i))) {
        return paramCharSequence.subSequence(i, paramCharSequence.length());
      }
      i++;
    }
    paramCharSequence = (CharSequence)"";
    return paramCharSequence;
  }
  
  private static final String trimStart(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.trimStart((CharSequence)paramString).toString();
  }
  
  public static final String trimStart(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    paramString = (CharSequence)paramString;
    int i = 0;
    int j = paramString.length();
    while (i < j)
    {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(i)))).booleanValue())
      {
        paramString = paramString.subSequence(i, paramString.length());
        break label87;
      }
      i++;
    }
    paramString = (CharSequence)"";
    label87:
    return paramString.toString();
  }
  
  public static final String trimStart(String paramString, char... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "chars");
    paramString = (CharSequence)paramString;
    int i = 0;
    int j = paramString.length();
    while (i < j)
    {
      if (!ArraysKt.contains(paramVarArgs, paramString.charAt(i)))
      {
        paramString = paramString.subSequence(i, paramString.length());
        break label76;
      }
      i++;
    }
    paramString = (CharSequence)"";
    label76:
    return paramString.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__StringsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */