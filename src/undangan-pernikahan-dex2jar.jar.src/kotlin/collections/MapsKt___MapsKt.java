package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(d1={"\000\001\n\000\n\002\020\013\n\002\b\002\n\002\020$\n\000\n\002\030\002\n\002\020&\n\002\b\002\n\002\020\034\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\000\n\002\b\004\n\002\020 \n\002\b\003\n\002\020\037\n\002\b\004\n\002\020\002\n\002\b\006\n\002\020\017\n\002\b\005\n\002\020\006\n\002\020\007\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\024\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\032J\020\000\032\0020\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020\005\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020\0010\006H\bø\001\000\032$\020\b\032\0020\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004\032J\020\b\032\0020\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020\005\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020\0010\006H\bø\001\000\0329\020\t\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0070\n\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004H\b\0326\020\013\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0070\f\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004\032'\020\r\032\0020\016\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004H\b\032J\020\r\032\0020\016\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020\005\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020\0010\006H\bø\001\000\032[\020\017\032\002H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\b\b\002\020\020*\0020\021*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042 \020\022\032\034\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\006\022\004\030\001H\0200\006H\bø\001\000¢\006\002\020\023\032]\020\024\032\004\030\001H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\b\b\002\020\020*\0020\021*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042 \020\022\032\034\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\006\022\004\030\001H\0200\006H\bø\001\000¢\006\002\020\023\032\\\020\025\032\b\022\004\022\002H\0200\026\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042$\020\022\032 \022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\n\022\b\022\004\022\002H\0200\n0\006H\bø\001\000\032a\020\025\032\b\022\004\022\002H\0200\026\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042$\020\022\032 \022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\n\022\b\022\004\022\002H\0200\f0\006H\bø\001\000¢\006\002\b\027\032u\020\030\032\002H\031\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020\"\020\b\003\020\031*\n\022\006\b\000\022\002H\0200\032*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\006\020\033\032\002H\0312$\020\022\032 \022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\n\022\b\022\004\022\002H\0200\n0\006H\bø\001\000¢\006\002\020\034\032w\020\030\032\002H\031\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020\"\020\b\003\020\031*\n\022\006\b\000\022\002H\0200\032*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\006\020\033\032\002H\0312$\020\022\032 \022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\n\022\b\022\004\022\002H\0200\f0\006H\bø\001\000¢\006\004\b\035\020\034\032J\020\036\032\0020\037\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020 \032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020\0370\006H\bø\001\000\032V\020!\032\b\022\004\022\002H\0200\026\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020\022\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000\032\\\020\"\032\b\022\004\022\002H\0200\026\"\004\b\000\020\002\"\004\b\001\020\003\"\b\b\002\020\020*\0020\021*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042 \020\022\032\034\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\006\022\004\030\001H\0200\006H\bø\001\000\032u\020#\032\002H\031\"\004\b\000\020\002\"\004\b\001\020\003\"\b\b\002\020\020*\0020\021\"\020\b\003\020\031*\n\022\006\b\000\022\002H\0200\032*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\006\020\033\032\002H\0312 \020\022\032\034\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\006\022\004\030\001H\0200\006H\bø\001\000¢\006\002\020\034\032o\020$\032\002H\031\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020\"\020\b\003\020\031*\n\022\006\b\000\022\002H\0200\032*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\006\020\033\032\002H\0312\036\020\022\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\020\034\032k\020%\032\016\022\004\022\002H\002\022\004\022\002H\0030\007\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\b(\032h\020)\032\020\022\004\022\002H\002\022\004\022\002H\003\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000\032_\020*\032\002H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\020+\032J\020*\032\0020,\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020,0\006H\bø\001\000\032J\020*\032\0020-\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020-0\006H\bø\001\000\032a\020.\032\004\030\001H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\020+\032Q\020.\032\004\030\0010,\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020,0\006H\bø\001\000¢\006\002\020/\032Q\020.\032\004\030\0010-\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020-0\006H\bø\001\000¢\006\002\0200\032q\0201\032\002H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\032\0202\032\026\022\006\b\000\022\002H\02003j\n\022\006\b\000\022\002H\020`42\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\0205\032s\0206\032\004\030\001H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\032\0202\032\026\022\006\b\000\022\002H\02003j\n\022\006\b\000\022\002H\020`42\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\0205\032l\0207\032\016\022\004\022\002H\002\022\004\022\002H\0030\007\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\00422\0202\032.\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\00703j\026\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\007`4H\b¢\006\002\b8\032i\0209\032\020\022\004\022\002H\002\022\004\022\002H\003\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\00422\0202\032.\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\00703j\026\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\007`4H\b\032k\020:\032\016\022\004\022\002H\002\022\004\022\002H\0030\007\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\b;\032h\020<\032\020\022\004\022\002H\002\022\004\022\002H\003\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000\032_\020=\032\002H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\020+\032J\020=\032\0020,\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020,0\006H\bø\001\000\032J\020=\032\0020-\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020-0\006H\bø\001\000\032a\020>\032\004\030\001H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\016\b\002\020\020*\b\022\004\022\002H\0200&*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\020+\032Q\020>\032\004\030\0010,\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020,0\006H\bø\001\000¢\006\002\020/\032Q\020>\032\004\030\0010-\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020-0\006H\bø\001\000¢\006\002\0200\032q\020?\032\002H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\032\0202\032\026\022\006\b\000\022\002H\02003j\n\022\006\b\000\022\002H\020`42\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\0205\032s\020@\032\004\030\001H\020\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020\020*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\032\0202\032\026\022\006\b\000\022\002H\02003j\n\022\006\b\000\022\002H\020`42\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\002H\0200\006H\bø\001\000¢\006\002\0205\032l\020A\032\016\022\004\022\002H\002\022\004\022\002H\0030\007\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\00422\0202\032.\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\00703j\026\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\007`4H\b¢\006\002\bB\032i\020C\032\020\022\004\022\002H\002\022\004\022\002H\003\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\00422\0202\032.\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\00703j\026\022\022\b\000\022\016\022\004\022\002H\002\022\004\022\002H\0030\007`4H\b\032$\020D\032\0020\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004\032J\020D\032\0020\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0042\036\020\005\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020\0010\006H\bø\001\000\032Y\020E\032\002HF\"\004\b\000\020\002\"\004\b\001\020\003\"\026\b\002\020F*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004*\002HF2\036\020 \032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020\0370\006H\bø\001\000¢\006\002\020G\032n\020H\032\002HF\"\004\b\000\020\002\"\004\b\001\020\003\"\026\b\002\020F*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004*\002HF23\020 \032/\022\023\022\0210\016¢\006\f\bJ\022\b\bK\022\004\b\b(L\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\007\022\004\022\0020\0370IH\bø\001\000¢\006\002\020M\0326\020N\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030O0\026\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\004\002\007\n\005\b20\001¨\006P"}, d2={"all", "", "K", "V", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "count", "", "firstNotNullOf", "R", "", "transform", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "firstNotNullOfOrNull", "flatMap", "", "flatMapSequence", "flatMapTo", "C", "", "destination", "(Ljava/util/Map;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "flatMapSequenceTo", "forEach", "", "action", "map", "mapNotNull", "mapNotNullTo", "mapTo", "maxBy", "", "selector", "maxByOrThrow", "maxByOrNull", "maxOf", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Comparable;", "", "", "maxOfOrNull", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Double;", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Float;", "maxOfWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/Map;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "maxOfWithOrNull", "maxWith", "maxWithOrThrow", "maxWithOrNull", "minBy", "minByOrThrow", "minByOrNull", "minOf", "minOfOrNull", "minOfWith", "minOfWithOrNull", "minWith", "minWithOrThrow", "minWithOrNull", "none", "onEach", "M", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "onEachIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "index", "(Ljava/util/Map;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "toList", "Lkotlin/Pair;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/MapsKt")
class MapsKt___MapsKt
  extends MapsKt___MapsJvmKt
{
  public static final <K, V> boolean all(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    if (paramMap.isEmpty()) {
      return true;
    }
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      if (!((Boolean)paramFunction1.invoke((Map.Entry)paramMap.next())).booleanValue()) {
        return false;
      }
    }
    return true;
  }
  
  public static final <K, V> boolean any(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.isEmpty() ^ true;
  }
  
  public static final <K, V> boolean any(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    if (paramMap.isEmpty()) {
      return false;
    }
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      if (((Boolean)paramFunction1.invoke((Map.Entry)paramMap.next())).booleanValue()) {
        return true;
      }
    }
    return false;
  }
  
  private static final <K, V> Iterable<Map.Entry<K, V>> asIterable(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return (Iterable)paramMap.entrySet();
  }
  
  public static final <K, V> Sequence<Map.Entry<K, V>> asSequence(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return CollectionsKt.asSequence((Iterable)paramMap.entrySet());
  }
  
  private static final <K, V> int count(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.size();
  }
  
  public static final <K, V> int count(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    if (paramMap.isEmpty()) {
      return 0;
    }
    int i = 0;
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      if (((Boolean)paramFunction1.invoke((Map.Entry)paramMap.next())).booleanValue()) {
        i++;
      }
    }
    return i;
  }
  
  private static final <K, V, R> R firstNotNullOf(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = paramFunction1.invoke((Map.Entry)localIterator.next());
      paramMap = (Map<? extends K, ? extends V>)localObject;
      if (localObject != null) {
        break label60;
      }
    }
    paramMap = null;
    label60:
    if (paramMap != null) {
      return paramMap;
    }
    throw new NoSuchElementException("No element of the map was transformed to a non-null value.");
  }
  
  private static final <K, V, R> R firstNotNullOfOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Object localObject = paramFunction1.invoke((Map.Entry)paramMap.next());
      if (localObject != null) {
        return (R)localObject;
      }
    }
    return null;
  }
  
  public static final <K, V, R> List<R> flatMap(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Collection localCollection = (Collection)new ArrayList();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      CollectionsKt.addAll(localCollection, (Iterable)paramFunction1.invoke((Map.Entry)paramMap.next()));
    }
    return (List)localCollection;
  }
  
  public static final <K, V, R> List<R> flatMapSequence(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Sequence<? extends R>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Collection localCollection = (Collection)new ArrayList();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      CollectionsKt.addAll(localCollection, (Sequence)paramFunction1.invoke((Map.Entry)paramMap.next()));
    }
    return (List)localCollection;
  }
  
  public static final <K, V, R, C extends Collection<? super R>> C flatMapSequenceTo(Map<? extends K, ? extends V> paramMap, C paramC, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Sequence<? extends R>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      CollectionsKt.addAll(paramC, (Sequence)paramFunction1.invoke((Map.Entry)paramMap.next()));
    }
    return paramC;
  }
  
  public static final <K, V, R, C extends Collection<? super R>> C flatMapTo(Map<? extends K, ? extends V> paramMap, C paramC, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      CollectionsKt.addAll(paramC, (Iterable)paramFunction1.invoke((Map.Entry)paramMap.next()));
    }
    return paramC;
  }
  
  public static final <K, V> void forEach(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      paramFunction1.invoke((Map.Entry)paramMap.next());
    }
  }
  
  public static final <K, V, R> List<R> map(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Collection localCollection = (Collection)new ArrayList(paramMap.size());
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      localCollection.add(paramFunction1.invoke((Map.Entry)paramMap.next()));
    }
    return (List)localCollection;
  }
  
  public static final <K, V, R> List<R> mapNotNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Collection localCollection = (Collection)new ArrayList();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Object localObject = paramFunction1.invoke((Map.Entry)paramMap.next());
      if (localObject != null) {
        localCollection.add(localObject);
      }
    }
    return (List)localCollection;
  }
  
  public static final <K, V, R, C extends Collection<? super R>> C mapNotNullTo(Map<? extends K, ? extends V> paramMap, C paramC, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Object localObject = paramFunction1.invoke((Map.Entry)paramMap.next());
      if (localObject != null) {
        paramC.add(localObject);
      }
    }
    return paramC;
  }
  
  public static final <K, V, R, C extends Collection<? super R>> C mapTo(Map<? extends K, ? extends V> paramMap, C paramC, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      paramC.add(paramFunction1.invoke((Map.Entry)paramMap.next()));
    }
    return paramC;
  }
  
  private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> maxByOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (!localIterator.hasNext())
    {
      paramMap = null;
    }
    else
    {
      paramMap = localIterator.next();
      if (localIterator.hasNext())
      {
        Object localObject2 = (Comparable)paramFunction1.invoke(paramMap);
        Map<? extends K, ? extends V> localMap = paramMap;
        do
        {
          Object localObject3 = localIterator.next();
          Comparable localComparable = (Comparable)paramFunction1.invoke(localObject3);
          paramMap = localMap;
          Object localObject1 = localObject2;
          if (((Comparable)localObject2).compareTo(localComparable) < 0)
          {
            paramMap = (Map<? extends K, ? extends V>)localObject3;
            localObject1 = localComparable;
          }
          localMap = paramMap;
          localObject2 = localObject1;
        } while (localIterator.hasNext());
      }
    }
    return (Map.Entry)paramMap;
  }
  
  private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> maxByOrThrow(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (localIterator.hasNext())
    {
      Object localObject3 = localIterator.next();
      if (!localIterator.hasNext())
      {
        paramMap = (Map<? extends K, ? extends V>)localObject3;
      }
      else
      {
        Object localObject2 = (Comparable)paramFunction1.invoke(localObject3);
        do
        {
          Object localObject4 = localIterator.next();
          Comparable localComparable = (Comparable)paramFunction1.invoke(localObject4);
          paramMap = (Map<? extends K, ? extends V>)localObject3;
          Object localObject1 = localObject2;
          if (((Comparable)localObject2).compareTo(localComparable) < 0)
          {
            paramMap = (Map<? extends K, ? extends V>)localObject4;
            localObject1 = localComparable;
          }
          localObject3 = paramMap;
          localObject2 = localObject1;
        } while (localIterator.hasNext());
      }
      return (Map.Entry)paramMap;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V> double maxOf(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (paramMap.hasNext())
    {
      for (double d = ((Number)paramFunction1.invoke(paramMap.next())).doubleValue(); paramMap.hasNext(); d = Math.max(d, ((Number)paramFunction1.invoke(paramMap.next())).doubleValue())) {}
      return d;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V> float maxOf(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (paramMap.hasNext())
    {
      for (float f = ((Number)paramFunction1.invoke(paramMap.next())).floatValue(); paramMap.hasNext(); f = Math.max(f, ((Number)paramFunction1.invoke(paramMap.next())).floatValue())) {}
      return f;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V, R extends Comparable<? super R>> R maxOf(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (localIterator.hasNext())
    {
      paramMap = (Comparable)paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Comparable localComparable = (Comparable)paramFunction1.invoke(localIterator.next());
        if (paramMap.compareTo(localComparable) < 0) {
          paramMap = localComparable;
        }
      }
      return paramMap;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V, R extends Comparable<? super R>> R maxOfOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (!localIterator.hasNext())
    {
      paramMap = null;
    }
    else
    {
      paramMap = (Comparable)paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Comparable localComparable = (Comparable)paramFunction1.invoke(localIterator.next());
        if (paramMap.compareTo(localComparable) < 0) {
          paramMap = localComparable;
        }
      }
    }
    return paramMap;
  }
  
  private static final <K, V> Double maxOfOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (!paramMap.hasNext())
    {
      paramMap = null;
    }
    else
    {
      for (double d = ((Number)paramFunction1.invoke(paramMap.next())).doubleValue(); paramMap.hasNext(); d = Math.max(d, ((Number)paramFunction1.invoke(paramMap.next())).doubleValue())) {}
      paramMap = Double.valueOf(d);
    }
    return paramMap;
  }
  
  private static final <K, V> Float maxOfOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (!paramMap.hasNext())
    {
      paramMap = null;
    }
    else
    {
      for (float f = ((Number)paramFunction1.invoke(paramMap.next())).floatValue(); paramMap.hasNext(); f = Math.max(f, ((Number)paramFunction1.invoke(paramMap.next())).floatValue())) {}
      paramMap = Float.valueOf(f);
    }
    return paramMap;
  }
  
  private static final <K, V, R> R maxOfWith(Map<? extends K, ? extends V> paramMap, Comparator<? super R> paramComparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (localIterator.hasNext())
    {
      paramMap = paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Object localObject = paramFunction1.invoke(localIterator.next());
        if (paramComparator.compare(paramMap, localObject) < 0) {
          paramMap = (Map<? extends K, ? extends V>)localObject;
        }
      }
      return paramMap;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V, R> R maxOfWithOrNull(Map<? extends K, ? extends V> paramMap, Comparator<? super R> paramComparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (!localIterator.hasNext())
    {
      paramMap = null;
    }
    else
    {
      paramMap = paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Object localObject = paramFunction1.invoke(localIterator.next());
        if (paramComparator.compare(paramMap, localObject) < 0) {
          paramMap = (Map<? extends K, ? extends V>)localObject;
        }
      }
    }
    return paramMap;
  }
  
  private static final <K, V> Map.Entry<K, V> maxWithOrNull(Map<? extends K, ? extends V> paramMap, Comparator<? super Map.Entry<? extends K, ? extends V>> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (Map.Entry)CollectionsKt.maxWithOrNull((Iterable)paramMap.entrySet(), paramComparator);
  }
  
  private static final <K, V> Map.Entry<K, V> maxWithOrThrow(Map<? extends K, ? extends V> paramMap, Comparator<? super Map.Entry<? extends K, ? extends V>> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (Map.Entry)CollectionsKt.maxWithOrThrow((Iterable)paramMap.entrySet(), paramComparator);
  }
  
  private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> minByOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (!localIterator.hasNext())
    {
      paramMap = null;
    }
    else
    {
      paramMap = localIterator.next();
      if (localIterator.hasNext())
      {
        Object localObject2 = (Comparable)paramFunction1.invoke(paramMap);
        Map<? extends K, ? extends V> localMap = paramMap;
        do
        {
          Object localObject3 = localIterator.next();
          Comparable localComparable = (Comparable)paramFunction1.invoke(localObject3);
          paramMap = localMap;
          Object localObject1 = localObject2;
          if (((Comparable)localObject2).compareTo(localComparable) > 0)
          {
            paramMap = (Map<? extends K, ? extends V>)localObject3;
            localObject1 = localComparable;
          }
          localMap = paramMap;
          localObject2 = localObject1;
        } while (localIterator.hasNext());
      }
    }
    return (Map.Entry)paramMap;
  }
  
  private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> minByOrThrow(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (localIterator.hasNext())
    {
      Object localObject3 = localIterator.next();
      if (!localIterator.hasNext())
      {
        paramMap = (Map<? extends K, ? extends V>)localObject3;
      }
      else
      {
        Object localObject1 = (Comparable)paramFunction1.invoke(localObject3);
        do
        {
          Object localObject4 = localIterator.next();
          Comparable localComparable = (Comparable)paramFunction1.invoke(localObject4);
          paramMap = (Map<? extends K, ? extends V>)localObject3;
          Object localObject2 = localObject1;
          if (((Comparable)localObject1).compareTo(localComparable) > 0)
          {
            paramMap = (Map<? extends K, ? extends V>)localObject4;
            localObject2 = localComparable;
          }
          localObject3 = paramMap;
          localObject1 = localObject2;
        } while (localIterator.hasNext());
      }
      return (Map.Entry)paramMap;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V> double minOf(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (paramMap.hasNext())
    {
      for (double d = ((Number)paramFunction1.invoke(paramMap.next())).doubleValue(); paramMap.hasNext(); d = Math.min(d, ((Number)paramFunction1.invoke(paramMap.next())).doubleValue())) {}
      return d;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V> float minOf(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (paramMap.hasNext())
    {
      for (float f = ((Number)paramFunction1.invoke(paramMap.next())).floatValue(); paramMap.hasNext(); f = Math.min(f, ((Number)paramFunction1.invoke(paramMap.next())).floatValue())) {}
      return f;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V, R extends Comparable<? super R>> R minOf(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (localIterator.hasNext())
    {
      paramMap = (Comparable)paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Comparable localComparable = (Comparable)paramFunction1.invoke(localIterator.next());
        if (paramMap.compareTo(localComparable) > 0) {
          paramMap = localComparable;
        }
      }
      return paramMap;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V, R extends Comparable<? super R>> R minOfOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (!localIterator.hasNext())
    {
      paramMap = null;
    }
    else
    {
      paramMap = (Comparable)paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Comparable localComparable = (Comparable)paramFunction1.invoke(localIterator.next());
        if (paramMap.compareTo(localComparable) > 0) {
          paramMap = localComparable;
        }
      }
    }
    return paramMap;
  }
  
  private static final <K, V> Double minOfOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (!paramMap.hasNext())
    {
      paramMap = null;
    }
    else
    {
      for (double d = ((Number)paramFunction1.invoke(paramMap.next())).doubleValue(); paramMap.hasNext(); d = Math.min(d, ((Number)paramFunction1.invoke(paramMap.next())).doubleValue())) {}
      paramMap = Double.valueOf(d);
    }
    return paramMap;
  }
  
  private static final <K, V> Float minOfOrNull(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    if (!paramMap.hasNext())
    {
      paramMap = null;
    }
    else
    {
      for (float f = ((Number)paramFunction1.invoke(paramMap.next())).floatValue(); paramMap.hasNext(); f = Math.min(f, ((Number)paramFunction1.invoke(paramMap.next())).floatValue())) {}
      paramMap = Float.valueOf(f);
    }
    return paramMap;
  }
  
  private static final <K, V, R> R minOfWith(Map<? extends K, ? extends V> paramMap, Comparator<? super R> paramComparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (localIterator.hasNext())
    {
      paramMap = paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Object localObject = paramFunction1.invoke(localIterator.next());
        if (paramComparator.compare(paramMap, localObject) > 0) {
          paramMap = (Map<? extends K, ? extends V>)localObject;
        }
      }
      return paramMap;
    }
    throw new NoSuchElementException();
  }
  
  private static final <K, V, R> R minOfWithOrNull(Map<? extends K, ? extends V> paramMap, Comparator<? super R> paramComparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    if (!localIterator.hasNext())
    {
      paramMap = null;
    }
    else
    {
      paramMap = paramFunction1.invoke(localIterator.next());
      while (localIterator.hasNext())
      {
        Object localObject = paramFunction1.invoke(localIterator.next());
        if (paramComparator.compare(paramMap, localObject) > 0) {
          paramMap = (Map<? extends K, ? extends V>)localObject;
        }
      }
    }
    return paramMap;
  }
  
  private static final <K, V> Map.Entry<K, V> minWithOrNull(Map<? extends K, ? extends V> paramMap, Comparator<? super Map.Entry<? extends K, ? extends V>> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (Map.Entry)CollectionsKt.minWithOrNull((Iterable)paramMap.entrySet(), paramComparator);
  }
  
  private static final <K, V> Map.Entry<K, V> minWithOrThrow(Map<? extends K, ? extends V> paramMap, Comparator<? super Map.Entry<? extends K, ? extends V>> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    return (Map.Entry)CollectionsKt.minWithOrThrow((Iterable)paramMap.entrySet(), paramComparator);
  }
  
  public static final <K, V> boolean none(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.isEmpty();
  }
  
  public static final <K, V> boolean none(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    if (paramMap.isEmpty()) {
      return true;
    }
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      if (((Boolean)paramFunction1.invoke((Map.Entry)paramMap.next())).booleanValue()) {
        return false;
      }
    }
    return true;
  }
  
  public static final <K, V, M extends Map<? extends K, ? extends V>> M onEach(M paramM, Function1<? super Map.Entry<? extends K, ? extends V>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramM, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    Iterator localIterator = paramM.entrySet().iterator();
    while (localIterator.hasNext()) {
      paramFunction1.invoke((Map.Entry)localIterator.next());
    }
    return paramM;
  }
  
  public static final <K, V, M extends Map<? extends K, ? extends V>> M onEachIndexed(M paramM, Function2<? super Integer, ? super Map.Entry<? extends K, ? extends V>, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramM, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    Object localObject = (Iterable)paramM.entrySet();
    int i = 0;
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      paramFunction2.invoke(Integer.valueOf(i), localObject);
      i++;
    }
    return paramM;
  }
  
  public static final <K, V> List<Pair<K, V>> toList(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    if (paramMap.size() == 0) {
      return CollectionsKt.emptyList();
    }
    Iterator localIterator = paramMap.entrySet().iterator();
    if (!localIterator.hasNext()) {
      return CollectionsKt.emptyList();
    }
    Map.Entry localEntry = (Map.Entry)localIterator.next();
    if (!localIterator.hasNext()) {
      return CollectionsKt.listOf(new Pair(localEntry.getKey(), localEntry.getValue()));
    }
    paramMap = new ArrayList(paramMap.size());
    paramMap.add(new Pair(localEntry.getKey(), localEntry.getValue()));
    for (;;)
    {
      localEntry = (Map.Entry)localIterator.next();
      paramMap.add(new Pair(localEntry.getKey(), localEntry.getValue()));
      if (!localIterator.hasNext()) {
        return (List)paramMap;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/MapsKt___MapsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */