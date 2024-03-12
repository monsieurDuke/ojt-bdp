package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TuplesKt;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SlidingWindowKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.random.Random;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000ö\001\n\000\n\002\020\013\n\002\020\r\n\000\n\002\030\002\n\002\020\f\n\002\b\002\n\002\020\034\n\000\n\002\030\002\n\000\n\002\020$\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020%\n\002\b\b\n\002\020 \n\002\020\016\n\000\n\002\020\b\n\002\b\017\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\n\n\002\020\000\n\002\b\b\n\002\020\037\n\002\b\n\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\006\n\002\020!\n\000\n\002\030\002\n\002\b\017\n\002\020\017\n\002\b\005\n\002\020\006\n\002\020\007\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\034\n\002\030\002\n\002\b\031\n\002\030\002\n\002\b\005\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\t\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\"\n\002\b\005\n\002\030\002\n\002\b\006\032$\020\000\032\0020\001*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032\n\020\006\032\0020\001*\0020\002\032$\020\006\032\0020\001*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032\020\020\007\032\b\022\004\022\0020\0050\b*\0020\002\032\020\020\t\032\b\022\004\022\0020\0050\n*\0020\002\032H\020\013\032\016\022\004\022\002H\r\022\004\022\002H\0160\f\"\004\b\000\020\r\"\004\b\001\020\016*\0020\0022\036\020\017\032\032\022\004\022\0020\005\022\020\022\016\022\004\022\002H\r\022\004\022\002H\0160\0200\004H\bø\001\000\0326\020\021\032\016\022\004\022\002H\r\022\004\022\0020\0050\f\"\004\b\000\020\r*\0020\0022\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\004H\bø\001\000\032P\020\021\032\016\022\004\022\002H\r\022\004\022\002H\0160\f\"\004\b\000\020\r\"\004\b\001\020\016*\0020\0022\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\0042\022\020\023\032\016\022\004\022\0020\005\022\004\022\002H\0160\004H\bø\001\000\032Q\020\024\032\002H\025\"\004\b\000\020\r\"\030\b\001\020\025*\022\022\006\b\000\022\002H\r\022\006\b\000\022\0020\0050\026*\0020\0022\006\020\027\032\002H\0252\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\004H\bø\001\000¢\006\002\020\030\032k\020\024\032\002H\025\"\004\b\000\020\r\"\004\b\001\020\016\"\030\b\002\020\025*\022\022\006\b\000\022\002H\r\022\006\b\000\022\002H\0160\026*\0020\0022\006\020\027\032\002H\0252\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\0042\022\020\023\032\016\022\004\022\0020\005\022\004\022\002H\0160\004H\bø\001\000¢\006\002\020\031\032c\020\032\032\002H\025\"\004\b\000\020\r\"\004\b\001\020\016\"\030\b\002\020\025*\022\022\006\b\000\022\002H\r\022\006\b\000\022\002H\0160\026*\0020\0022\006\020\027\032\002H\0252\036\020\017\032\032\022\004\022\0020\005\022\020\022\016\022\004\022\002H\r\022\004\022\002H\0160\0200\004H\bø\001\000¢\006\002\020\030\0326\020\033\032\016\022\004\022\0020\005\022\004\022\002H\0160\f\"\004\b\000\020\016*\0020\0022\022\020\034\032\016\022\004\022\0020\005\022\004\022\002H\0160\004H\bø\001\000\032Q\020\035\032\002H\025\"\004\b\000\020\016\"\030\b\001\020\025*\022\022\006\b\000\022\0020\005\022\006\b\000\022\002H\0160\026*\0020\0022\006\020\027\032\002H\0252\022\020\034\032\016\022\004\022\0020\005\022\004\022\002H\0160\004H\bø\001\000¢\006\002\020\030\032\032\020\036\032\b\022\004\022\0020 0\037*\0020\0022\006\020!\032\0020\"H\007\0324\020\036\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\006\020!\032\0020\"2\022\020\017\032\016\022\004\022\0020\002\022\004\022\002H#0\004H\007\032\032\020$\032\b\022\004\022\0020 0\n*\0020\0022\006\020!\032\0020\"H\007\0324\020$\032\b\022\004\022\002H#0\n\"\004\b\000\020#*\0020\0022\006\020!\032\0020\"2\022\020\017\032\016\022\004\022\0020\002\022\004\022\002H#0\004H\007\032\r\020%\032\0020\"*\0020\002H\b\032$\020%\032\0020\"*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032\022\020&\032\0020\002*\0020\0022\006\020'\032\0020\"\032\022\020&\032\0020 *\0020 2\006\020'\032\0020\"\032\022\020(\032\0020\002*\0020\0022\006\020'\032\0020\"\032\022\020(\032\0020 *\0020 2\006\020'\032\0020\"\032$\020)\032\0020\002*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032$\020)\032\0020 *\0020 2\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032$\020*\032\0020\002*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032$\020*\032\0020 *\0020 2\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032,\020+\032\0020\005*\0020\0022\006\020,\032\0020\"2\022\020-\032\016\022\004\022\0020\"\022\004\022\0020\0050\004H\bø\001\000\032\034\020.\032\004\030\0010\005*\0020\0022\006\020,\032\0020\"H\b¢\006\002\020/\032$\0200\032\0020\002*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032$\0200\032\0020 *\0020 2\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\0329\0201\032\0020\002*\0020\0022'\020\003\032#\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\004\022\0020\00102H\bø\001\000\0329\0201\032\0020 *\0020 2'\020\003\032#\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\004\022\0020\00102H\bø\001\000\032T\0205\032\002H6\"\f\b\000\0206*\00607j\002`8*\0020\0022\006\020\027\032\002H62'\020\003\032#\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\004\022\0020\00102H\bø\001\000¢\006\002\0209\032$\020:\032\0020\002*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032$\020:\032\0020 *\0020 2\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032?\020;\032\002H6\"\f\b\000\0206*\00607j\002`8*\0020\0022\006\020\027\032\002H62\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000¢\006\002\020<\032?\020=\032\002H6\"\f\b\000\0206*\00607j\002`8*\0020\0022\006\020\027\032\002H62\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000¢\006\002\020<\032+\020>\032\004\030\0010\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000¢\006\002\020?\032+\020@\032\004\030\0010\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000¢\006\002\020?\032\n\020A\032\0020\005*\0020\002\032$\020A\032\0020\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\0325\020B\032\002H#\"\b\b\000\020#*\0020C*\0020\0022\024\020\017\032\020\022\004\022\0020\005\022\006\022\004\030\001H#0\004H\bø\001\000¢\006\002\020D\0327\020E\032\004\030\001H#\"\b\b\000\020#*\0020C*\0020\0022\024\020\017\032\020\022\004\022\0020\005\022\006\022\004\030\001H#0\004H\bø\001\000¢\006\002\020D\032\021\020F\032\004\030\0010\005*\0020\002¢\006\002\020G\032+\020F\032\004\030\0010\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000¢\006\002\020?\0326\020H\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\030\020\017\032\024\022\004\022\0020\005\022\n\022\b\022\004\022\002H#0\b0\004H\bø\001\000\032P\020I\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022-\020\017\032)\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\n\022\b\022\004\022\002H#0\b02H\bø\001\000¢\006\002\bJ\032f\020K\032\002H6\"\004\b\000\020#\"\020\b\001\0206*\n\022\006\b\000\022\002H#0L*\0020\0022\006\020\027\032\002H62-\020\017\032)\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\n\022\b\022\004\022\002H#0\b02H\bø\001\000¢\006\004\bM\020N\032O\020O\032\002H6\"\004\b\000\020#\"\020\b\001\0206*\n\022\006\b\000\022\002H#0L*\0020\0022\006\020\027\032\002H62\030\020\017\032\024\022\004\022\0020\005\022\n\022\b\022\004\022\002H#0\b0\004H\bø\001\000¢\006\002\020P\032L\020Q\032\002H#\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2'\020S\032#\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\002H#02H\bø\001\000¢\006\002\020U\032a\020V\032\002H#\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\002H#0WH\bø\001\000¢\006\002\020X\032L\020Y\032\002H#\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2'\020S\032#\022\004\022\0020\005\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\002H#02H\bø\001\000¢\006\002\020U\032a\020Z\032\002H#\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\002H#0WH\bø\001\000¢\006\002\020X\032$\020[\032\0020\\*\0020\0022\022\020]\032\016\022\004\022\0020\005\022\004\022\0020\\0\004H\bø\001\000\0329\020^\032\0020\\*\0020\0022'\020]\032#\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\004\022\0020\\02H\bø\001\000\032,\020_\032\0020\005*\0020\0022\006\020,\032\0020\"2\022\020-\032\016\022\004\022\0020\"\022\004\022\0020\0050\004H\bø\001\000\032\031\020`\032\004\030\0010\005*\0020\0022\006\020,\032\0020\"¢\006\002\020/\032<\020a\032\024\022\004\022\002H\r\022\n\022\b\022\004\022\0020\0050\0370\f\"\004\b\000\020\r*\0020\0022\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\004H\bø\001\000\032V\020a\032\024\022\004\022\002H\r\022\n\022\b\022\004\022\002H\0160\0370\f\"\004\b\000\020\r\"\004\b\001\020\016*\0020\0022\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\0042\022\020\023\032\016\022\004\022\0020\005\022\004\022\002H\0160\004H\bø\001\000\032U\020b\032\002H\025\"\004\b\000\020\r\"\034\b\001\020\025*\026\022\006\b\000\022\002H\r\022\n\022\b\022\004\022\0020\0050c0\026*\0020\0022\006\020\027\032\002H\0252\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\004H\bø\001\000¢\006\002\020\030\032o\020b\032\002H\025\"\004\b\000\020\r\"\004\b\001\020\016\"\034\b\002\020\025*\026\022\006\b\000\022\002H\r\022\n\022\b\022\004\022\002H\0160c0\026*\0020\0022\006\020\027\032\002H\0252\022\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\0042\022\020\023\032\016\022\004\022\0020\005\022\004\022\002H\0160\004H\bø\001\000¢\006\002\020\031\0328\020d\032\016\022\004\022\0020\005\022\004\022\002H\r0e\"\004\b\000\020\r*\0020\0022\024\b\004\020\022\032\016\022\004\022\0020\005\022\004\022\002H\r0\004H\bø\001\000\032$\020f\032\0020\"*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032$\020g\032\0020\"*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032\n\020h\032\0020\005*\0020\002\032$\020h\032\0020\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032\021\020i\032\004\030\0010\005*\0020\002¢\006\002\020G\032+\020i\032\004\030\0010\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000¢\006\002\020?\0320\020j\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\022\020\017\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000\032E\020k\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022'\020\017\032#\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\004\022\002H#02H\bø\001\000\032K\020l\032\b\022\004\022\002H#0\037\"\b\b\000\020#*\0020C*\0020\0022)\020\017\032%\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\006\022\004\030\001H#02H\bø\001\000\032d\020m\032\002H6\"\b\b\000\020#*\0020C\"\020\b\001\0206*\n\022\006\b\000\022\002H#0L*\0020\0022\006\020\027\032\002H62)\020\017\032%\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\006\022\004\030\001H#02H\bø\001\000¢\006\002\020N\032^\020n\032\002H6\"\004\b\000\020#\"\020\b\001\0206*\n\022\006\b\000\022\002H#0L*\0020\0022\006\020\027\032\002H62'\020\017\032#\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\004\022\002H#02H\bø\001\000¢\006\002\020N\0326\020o\032\b\022\004\022\002H#0\037\"\b\b\000\020#*\0020C*\0020\0022\024\020\017\032\020\022\004\022\0020\005\022\006\022\004\030\001H#0\004H\bø\001\000\032O\020p\032\002H6\"\b\b\000\020#*\0020C\"\020\b\001\0206*\n\022\006\b\000\022\002H#0L*\0020\0022\006\020\027\032\002H62\024\020\017\032\020\022\004\022\0020\005\022\006\022\004\030\001H#0\004H\bø\001\000¢\006\002\020P\032I\020q\032\002H6\"\004\b\000\020#\"\020\b\001\0206*\n\022\006\b\000\022\002H#0L*\0020\0022\006\020\027\032\002H62\022\020\017\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\020P\032\021\020r\032\0020\005*\0020\002H\007¢\006\002\bs\0329\020t\032\0020\005\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\bw\032;\020x\032\004\030\0010\005\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\020?\0329\020y\032\002H#\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\020z\032$\020y\032\0020{*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020{0\004H\bø\001\000\032$\020y\032\0020|*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020|0\004H\bø\001\000\032;\020}\032\004\030\001H#\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\020z\032+\020}\032\004\030\0010{*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020{0\004H\bø\001\000¢\006\002\020~\032+\020}\032\004\030\0010|*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020|0\004H\bø\001\000¢\006\002\020\032P\020\001\032\002H#\"\004\b\000\020#*\0020\0022\035\020\001\032\030\022\006\b\000\022\002H#0\001j\013\022\006\b\000\022\002H#`\0012\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\003\020\001\032R\020\001\032\004\030\001H#\"\004\b\000\020#*\0020\0022\035\020\001\032\030\022\006\b\000\022\002H#0\001j\013\022\006\b\000\022\002H#`\0012\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\003\020\001\032\024\020\001\032\004\030\0010\005*\0020\002H\007¢\006\002\020G\0322\020\001\032\0020\005*\0020\0022\035\020\001\032\030\022\006\b\000\022\0020\0050\001j\013\022\006\b\000\022\0020\005`\001H\007¢\006\003\b\001\0324\020\001\032\004\030\0010\005*\0020\0022\035\020\001\032\030\022\006\b\000\022\0020\0050\001j\013\022\006\b\000\022\0020\005`\001H\007¢\006\003\020\001\032\023\020\001\032\0020\005*\0020\002H\007¢\006\003\b\001\032;\020\001\032\0020\005\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\003\b\001\032<\020\001\032\004\030\0010\005\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\020?\032:\020\001\032\002H#\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\020z\032%\020\001\032\0020{*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020{0\004H\bø\001\000\032%\020\001\032\0020|*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020|0\004H\bø\001\000\032<\020\001\032\004\030\001H#\"\016\b\000\020#*\b\022\004\022\002H#0u*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\002\020z\032,\020\001\032\004\030\0010{*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020{0\004H\bø\001\000¢\006\002\020~\032,\020\001\032\004\030\0010|*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020|0\004H\bø\001\000¢\006\002\020\032P\020\001\032\002H#\"\004\b\000\020#*\0020\0022\035\020\001\032\030\022\006\b\000\022\002H#0\001j\013\022\006\b\000\022\002H#`\0012\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\003\020\001\032R\020\001\032\004\030\001H#\"\004\b\000\020#*\0020\0022\035\020\001\032\030\022\006\b\000\022\002H#0\001j\013\022\006\b\000\022\002H#`\0012\022\020v\032\016\022\004\022\0020\005\022\004\022\002H#0\004H\bø\001\000¢\006\003\020\001\032\024\020\001\032\004\030\0010\005*\0020\002H\007¢\006\002\020G\0322\020\001\032\0020\005*\0020\0022\035\020\001\032\030\022\006\b\000\022\0020\0050\001j\013\022\006\b\000\022\0020\005`\001H\007¢\006\003\b\001\0324\020\001\032\004\030\0010\005*\0020\0022\035\020\001\032\030\022\006\b\000\022\0020\0050\001j\013\022\006\b\000\022\0020\005`\001H\007¢\006\003\020\001\032\013\020\001\032\0020\001*\0020\002\032%\020\001\032\0020\001*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\0328\020\001\032\003H\001\"\t\b\000\020\001*\0020\002*\003H\0012\022\020]\032\016\022\004\022\0020\005\022\004\022\0020\\0\004H\bø\001\000¢\006\003\020\001\032M\020\001\032\003H\001\"\t\b\000\020\001*\0020\002*\003H\0012'\020]\032#\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\004\022\0020\\02H\bø\001\000¢\006\003\020\001\0321\020\001\032\016\022\004\022\0020\002\022\004\022\0020\0020\020*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\0321\020\001\032\016\022\004\022\0020 \022\004\022\0020 0\020*\0020 2\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032\016\020\001\032\0020\005*\0020\002H\b\032\027\020\001\032\0020\005*\0020\0022\b\020\001\032\0030 \001H\007\032\025\020¡\001\032\004\030\0010\005*\0020\002H\b¢\006\002\020G\032\037\020¡\001\032\004\030\0010\005*\0020\0022\b\020\001\032\0030 \001H\007¢\006\003\020¢\001\032:\020£\001\032\0020\005*\0020\0022'\020S\032#\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\0020\00502H\bø\001\000\032O\020¤\001\032\0020\005*\0020\0022<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\0020\0050WH\bø\001\000\032W\020¥\001\032\004\030\0010\005*\0020\0022<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\0020\0050WH\bø\001\000¢\006\003\020¦\001\032B\020§\001\032\004\030\0010\005*\0020\0022'\020S\032#\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\0020\00502H\bø\001\000¢\006\003\020¨\001\032:\020©\001\032\0020\005*\0020\0022'\020S\032#\022\004\022\0020\005\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\00502H\bø\001\000\032O\020ª\001\032\0020\005*\0020\0022<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\0050WH\bø\001\000\032W\020«\001\032\004\030\0010\005*\0020\0022<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\004\022\0020\005\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\0050WH\bø\001\000¢\006\003\020¦\001\032B\020¬\001\032\004\030\0010\005*\0020\0022'\020S\032#\022\004\022\0020\005\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\00502H\bø\001\000¢\006\003\020¨\001\032\013\020­\001\032\0020\002*\0020\002\032\016\020­\001\032\0020 *\0020 H\b\032T\020®\001\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2'\020S\032#\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\002H#02H\bø\001\000¢\006\003\020¯\001\032i\020°\001\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\002H#0WH\bø\001\000¢\006\003\020±\001\032@\020²\001\032\b\022\004\022\0020\0050\037*\0020\0022'\020S\032#\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\0020\00502H\bø\001\000\032U\020³\001\032\b\022\004\022\0020\0050\037*\0020\0022<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\023\022\0210\005¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\0020\0050WH\bø\001\000\032T\020´\001\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2'\020S\032#\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\002H#02H\bø\001\000¢\006\003\020¯\001\032i\020µ\001\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\006\020R\032\002H#2<\020S\0328\022\023\022\0210\"¢\006\f\b3\022\b\b4\022\004\b\b(,\022\023\022\021H#¢\006\f\b3\022\b\b4\022\004\b\b(T\022\004\022\0020\005\022\004\022\002H#0WH\bø\001\000¢\006\003\020±\001\032\013\020¶\001\032\0020\005*\0020\002\032%\020¶\001\032\0020\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032\022\020·\001\032\004\030\0010\005*\0020\002¢\006\002\020G\032,\020·\001\032\004\030\0010\005*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000¢\006\002\020?\032\032\020¸\001\032\0020\002*\0020\0022\r\020¹\001\032\b\022\004\022\0020\"0\b\032\025\020¸\001\032\0020\002*\0020\0022\b\020¹\001\032\0030º\001\032\035\020¸\001\032\0020 *\0020 2\r\020¹\001\032\b\022\004\022\0020\"0\bH\b\032\025\020¸\001\032\0020 *\0020 2\b\020¹\001\032\0030º\001\032%\020»\001\032\0020\"*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020\"0\004H\bø\001\000\032%\020¼\001\032\0020{*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020{0\004H\bø\001\000\032+\020½\001\032\0020{*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020{0\004H\bø\001\000¢\006\003\b¾\001\032+\020½\001\032\0020\"*\0020\0022\022\020v\032\016\022\004\022\0020\005\022\004\022\0020\"0\004H\bø\001\000¢\006\003\b¿\001\032-\020½\001\032\0030À\001*\0020\0022\023\020v\032\017\022\004\022\0020\005\022\005\022\0030À\0010\004H\bø\001\000¢\006\003\bÁ\001\0323\020½\001\032\0030Â\001*\0020\0022\023\020v\032\017\022\004\022\0020\005\022\005\022\0030Â\0010\004H\bø\001\000ø\001\001¢\006\006\bÃ\001\020Ä\001\0323\020½\001\032\0030Å\001*\0020\0022\023\020v\032\017\022\004\022\0020\005\022\005\022\0030Å\0010\004H\bø\001\000ø\001\001¢\006\006\bÆ\001\020Ç\001\032\023\020È\001\032\0020\002*\0020\0022\006\020'\032\0020\"\032\023\020È\001\032\0020 *\0020 2\006\020'\032\0020\"\032\023\020É\001\032\0020\002*\0020\0022\006\020'\032\0020\"\032\023\020É\001\032\0020 *\0020 2\006\020'\032\0020\"\032%\020Ê\001\032\0020\002*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032%\020Ê\001\032\0020 *\0020 2\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032%\020Ë\001\032\0020\002*\0020\0022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032%\020Ë\001\032\0020 *\0020 2\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0010\004H\bø\001\000\032+\020Ì\001\032\002H6\"\020\b\000\0206*\n\022\006\b\000\022\0020\0050L*\0020\0022\006\020\027\032\002H6¢\006\003\020Í\001\032\035\020Î\001\032\024\022\004\022\0020\0050Ï\001j\t\022\004\022\0020\005`Ð\001*\0020\002\032\021\020Ñ\001\032\b\022\004\022\0020\0050\037*\0020\002\032\021\020Ò\001\032\b\022\004\022\0020\0050c*\0020\002\032\022\020Ó\001\032\t\022\004\022\0020\0050Ô\001*\0020\002\0321\020Õ\001\032\b\022\004\022\0020 0\037*\0020\0022\006\020!\032\0020\"2\t\b\002\020Ö\001\032\0020\"2\t\b\002\020×\001\032\0020\001H\007\032K\020Õ\001\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\0022\006\020!\032\0020\"2\t\b\002\020Ö\001\032\0020\"2\t\b\002\020×\001\032\0020\0012\022\020\017\032\016\022\004\022\0020\002\022\004\022\002H#0\004H\007\0321\020Ø\001\032\b\022\004\022\0020 0\n*\0020\0022\006\020!\032\0020\"2\t\b\002\020Ö\001\032\0020\"2\t\b\002\020×\001\032\0020\001H\007\032K\020Ø\001\032\b\022\004\022\002H#0\n\"\004\b\000\020#*\0020\0022\006\020!\032\0020\"2\t\b\002\020Ö\001\032\0020\"2\t\b\002\020×\001\032\0020\0012\022\020\017\032\016\022\004\022\0020\002\022\004\022\002H#0\004H\007\032\030\020Ù\001\032\017\022\013\022\t\022\004\022\0020\0050Ú\0010\b*\0020\002\032)\020Û\001\032\024\022\020\022\016\022\004\022\0020\005\022\004\022\0020\0050\0200\037*\0020\0022\007\020Ü\001\032\0020\002H\004\032`\020Û\001\032\b\022\004\022\002H\0160\037\"\004\b\000\020\016*\0020\0022\007\020Ü\001\032\0020\00228\020\017\0324\022\024\022\0220\005¢\006\r\b3\022\t\b4\022\005\b\b(Ý\001\022\024\022\0220\005¢\006\r\b3\022\t\b4\022\005\b\b(Þ\001\022\004\022\002H\01602H\bø\001\000\032\037\020ß\001\032\024\022\020\022\016\022\004\022\0020\005\022\004\022\0020\0050\0200\037*\0020\002H\007\032W\020ß\001\032\b\022\004\022\002H#0\037\"\004\b\000\020#*\0020\00228\020\017\0324\022\024\022\0220\005¢\006\r\b3\022\t\b4\022\005\b\b(Ý\001\022\024\022\0220\005¢\006\r\b3\022\t\b4\022\005\b\b(Þ\001\022\004\022\002H#02H\bø\001\000\002\013\n\005\b20\001\n\002\b\031¨\006à\001"}, d2={"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAtOrElse", "index", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstNotNullOf", "", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "firstNotNullOfOrNull", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapIndexed", "flatMapIndexedIterable", "flatMapIndexedTo", "", "flatMapIndexedIterableTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "flatMapTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "mapIndexedNotNullTo", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxOrThrow", "maxBy", "", "selector", "maxByOrThrow", "maxByOrNull", "maxOf", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Comparable;", "", "", "maxOfOrNull", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Double;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Float;", "maxOfWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "maxOfWithOrNull", "maxOrNull", "maxWith", "maxWithOrThrow", "maxWithOrNull", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minOrThrow", "minBy", "minByOrThrow", "minByOrNull", "minOf", "minOfOrNull", "minOfWith", "minOfWithOrNull", "minOrNull", "minWith", "minWithOrThrow", "minWithOrNull", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "onEachIndexed", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function2;)Ljava/lang/CharSequence;", "partition", "random", "Lkotlin/random/Random;", "randomOrNull", "(Ljava/lang/CharSequence;Lkotlin/random/Random;)Ljava/lang/Character;", "reduce", "reduceIndexed", "reduceIndexedOrNull", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function3;)Ljava/lang/Character;", "reduceOrNull", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function2;)Ljava/lang/Character;", "reduceRight", "reduceRightIndexed", "reduceRightIndexedOrNull", "reduceRightOrNull", "reversed", "runningFold", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "runningFoldIndexed", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/util/List;", "runningReduce", "runningReduceIndexed", "scan", "scanIndexed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "sumOf", "sumOfDouble", "sumOfInt", "", "sumOfLong", "Lkotlin/UInt;", "sumOfUInt", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)I", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)J", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt___StringsKt
  extends StringsKt___StringsJvmKt
{
  public static final boolean all(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return false;
      }
    }
    return true;
  }
  
  public static final boolean any(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    return i ^ 0x1;
  }
  
  public static final boolean any(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      if (((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return true;
      }
    }
    return false;
  }
  
  public static final Iterable<Character> asIterable(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if ((paramCharSequence instanceof String))
    {
      int i;
      if (paramCharSequence.length() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return (Iterable)CollectionsKt.emptyList();
      }
    }
    (Iterable)new Iterable()
    {
      final CharSequence $this_asIterable$inlined;
      
      public Iterator<Character> iterator()
      {
        return (Iterator)StringsKt.iterator(this.$this_asIterable$inlined);
      }
    };
  }
  
  public static final Sequence<Character> asSequence(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if ((paramCharSequence instanceof String))
    {
      int i;
      if (paramCharSequence.length() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return SequencesKt.emptySequence();
      }
    }
    (Sequence)new Sequence()
    {
      final CharSequence $this_asSequence$inlined;
      
      public Iterator<Character> iterator()
      {
        return (Iterator)StringsKt.iterator(this.$this_asSequence$inlined);
      }
    };
  }
  
  public static final <K, V> Map<K, V> associate(CharSequence paramCharSequence, Function1<? super Character, ? extends Pair<? extends K, ? extends V>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Map localMap = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(paramCharSequence.length()), 16));
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      Pair localPair = (Pair)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)));
      localMap.put(localPair.getFirst(), localPair.getSecond());
    }
    return localMap;
  }
  
  public static final <K> Map<K, Character> associateBy(CharSequence paramCharSequence, Function1<? super Character, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    Map localMap = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(paramCharSequence.length()), 16));
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      localMap.put(paramFunction1.invoke(Character.valueOf(c)), Character.valueOf(c));
    }
    return localMap;
  }
  
  public static final <K, V> Map<K, V> associateBy(CharSequence paramCharSequence, Function1<? super Character, ? extends K> paramFunction1, Function1<? super Character, ? extends V> paramFunction11)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    Intrinsics.checkNotNullParameter(paramFunction11, "valueTransform");
    Map localMap = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(paramCharSequence.length()), 16));
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      localMap.put(paramFunction1.invoke(Character.valueOf(c)), paramFunction11.invoke(Character.valueOf(c)));
    }
    return localMap;
  }
  
  public static final <K, M extends Map<? super K, ? super Character>> M associateByTo(CharSequence paramCharSequence, M paramM, Function1<? super Character, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      paramM.put(paramFunction1.invoke(Character.valueOf(c)), Character.valueOf(c));
    }
    return paramM;
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M associateByTo(CharSequence paramCharSequence, M paramM, Function1<? super Character, ? extends K> paramFunction1, Function1<? super Character, ? extends V> paramFunction11)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    Intrinsics.checkNotNullParameter(paramFunction11, "valueTransform");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      paramM.put(paramFunction1.invoke(Character.valueOf(c)), paramFunction11.invoke(Character.valueOf(c)));
    }
    return paramM;
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M associateTo(CharSequence paramCharSequence, M paramM, Function1<? super Character, ? extends Pair<? extends K, ? extends V>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      Pair localPair = (Pair)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)));
      paramM.put(localPair.getFirst(), localPair.getSecond());
    }
    return paramM;
  }
  
  public static final <V> Map<Character, V> associateWith(CharSequence paramCharSequence, Function1<? super Character, ? extends V> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "valueSelector");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(RangesKt.coerceAtMost(paramCharSequence.length(), 128)), 16));
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      ((Map)localLinkedHashMap).put(Character.valueOf(c), paramFunction1.invoke(Character.valueOf(c)));
    }
    return (Map)localLinkedHashMap;
  }
  
  public static final <V, M extends Map<? super Character, ? super V>> M associateWithTo(CharSequence paramCharSequence, M paramM, Function1<? super Character, ? extends V> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "valueSelector");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      paramM.put(Character.valueOf(c), paramFunction1.invoke(Character.valueOf(c)));
    }
    return paramM;
  }
  
  public static final List<String> chunked(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.windowed(paramCharSequence, paramInt, paramInt, true);
  }
  
  public static final <R> List<R> chunked(CharSequence paramCharSequence, int paramInt, Function1<? super CharSequence, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    return StringsKt.windowed(paramCharSequence, paramInt, paramInt, true, paramFunction1);
  }
  
  public static final Sequence<String> chunkedSequence(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.chunkedSequence(paramCharSequence, paramInt, (Function1)chunkedSequence.1.INSTANCE);
  }
  
  public static final <R> Sequence<R> chunkedSequence(CharSequence paramCharSequence, int paramInt, Function1<? super CharSequence, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    return StringsKt.windowedSequence(paramCharSequence, paramInt, paramInt, true, paramFunction1);
  }
  
  private static final int count(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return paramCharSequence.length();
  }
  
  public static final int count(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      int k = j;
      if (((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        k = j + 1;
      }
      i++;
      j = k;
    }
    return j;
  }
  
  public static final CharSequence drop(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramCharSequence.subSequence(RangesKt.coerceAtMost(paramInt, paramCharSequence.length()), paramCharSequence.length());
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final String drop(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramString = paramString.substring(RangesKt.coerceAtMost(paramInt, paramString.length()));
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
      return paramString;
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final CharSequence dropLast(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return StringsKt.take(paramCharSequence, RangesKt.coerceAtLeast(paramCharSequence.length() - paramInt, 0));
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final String dropLast(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramString = StringsKt.take(paramString, RangesKt.coerceAtLeast(paramString.length() - paramInt, 0));
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      return paramString;
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final CharSequence dropLastWhile(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = StringsKt.getLastIndex(paramCharSequence); -1 < i; i--) {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return paramCharSequence.subSequence(0, i + 1);
      }
    }
    return (CharSequence)"";
  }
  
  public static final String dropLastWhile(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = StringsKt.getLastIndex((CharSequence)paramString); -1 < i; i--) {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(i)))).booleanValue())
      {
        paramString = paramString.substring(0, i + 1);
        Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
        return paramString;
      }
    }
    return "";
  }
  
  public static final CharSequence dropWhile(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
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
  
  public static final String dropWhile(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = 0;
    int j = paramString.length();
    while (i < j)
    {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(i)))).booleanValue())
      {
        paramString = paramString.substring(i);
        Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
        return paramString;
      }
      i++;
    }
    return "";
  }
  
  private static final char elementAtOrElse(CharSequence paramCharSequence, int paramInt, Function1<? super Integer, Character> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "defaultValue");
    char c;
    if ((paramInt >= 0) && (paramInt <= StringsKt.getLastIndex(paramCharSequence))) {
      c = paramCharSequence.charAt(paramInt);
    } else {
      c = ((Character)paramFunction1.invoke(Integer.valueOf(paramInt))).charValue();
    }
    return c;
  }
  
  private static final Character elementAtOrNull(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.getOrNull(paramCharSequence, paramInt);
  }
  
  public static final CharSequence filter(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Appendable localAppendable = (Appendable)new StringBuilder();
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        localAppendable.append(c);
      }
      i++;
    }
    return (CharSequence)localAppendable;
  }
  
  public static final String filter(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    CharSequence localCharSequence = (CharSequence)paramString;
    paramString = (Appendable)new StringBuilder();
    int i = 0;
    int j = localCharSequence.length();
    while (i < j)
    {
      char c = localCharSequence.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        paramString.append(c);
      }
      i++;
    }
    paramString = ((StringBuilder)paramString).toString();
    Intrinsics.checkNotNullExpressionValue(paramString, "filterTo(StringBuilder(), predicate).toString()");
    return paramString;
  }
  
  public static final CharSequence filterIndexed(CharSequence paramCharSequence, Function2<? super Integer, ? super Character, Boolean> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "predicate");
    Appendable localAppendable = (Appendable)new StringBuilder();
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(c))).booleanValue()) {
        localAppendable.append(c);
      }
      i++;
      j++;
    }
    return (CharSequence)localAppendable;
  }
  
  public static final String filterIndexed(String paramString, Function2<? super Integer, ? super Character, Boolean> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "predicate");
    paramString = (CharSequence)paramString;
    Appendable localAppendable = (Appendable)new StringBuilder();
    int j = 0;
    int i = 0;
    while (i < paramString.length())
    {
      char c = paramString.charAt(i);
      if (((Boolean)paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(c))).booleanValue()) {
        localAppendable.append(c);
      }
      i++;
      j++;
    }
    paramString = ((StringBuilder)localAppendable).toString();
    Intrinsics.checkNotNullExpressionValue(paramString, "filterIndexedTo(StringBu…(), predicate).toString()");
    return paramString;
  }
  
  public static final <C extends Appendable> C filterIndexedTo(CharSequence paramCharSequence, C paramC, Function2<? super Integer, ? super Character, Boolean> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction2, "predicate");
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(c))).booleanValue()) {
        paramC.append(c);
      }
      i++;
      j++;
    }
    return paramC;
  }
  
  public static final CharSequence filterNot(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Appendable localAppendable = (Appendable)new StringBuilder();
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        localAppendable.append(c);
      }
    }
    return (CharSequence)localAppendable;
  }
  
  public static final String filterNot(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    paramString = (CharSequence)paramString;
    Appendable localAppendable = (Appendable)new StringBuilder();
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        localAppendable.append(c);
      }
    }
    paramString = ((StringBuilder)localAppendable).toString();
    Intrinsics.checkNotNullExpressionValue(paramString, "filterNotTo(StringBuilder(), predicate).toString()");
    return paramString;
  }
  
  public static final <C extends Appendable> C filterNotTo(CharSequence paramCharSequence, C paramC, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        paramC.append(c);
      }
    }
    return paramC;
  }
  
  public static final <C extends Appendable> C filterTo(CharSequence paramCharSequence, C paramC, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        paramC.append(c);
      }
      i++;
    }
    return paramC;
  }
  
  private static final Character find(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        return Character.valueOf(c);
      }
    }
    paramCharSequence = null;
    return paramCharSequence;
  }
  
  private static final Character findLast(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
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
        char c = paramCharSequence.charAt(i);
        if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue())
        {
          paramCharSequence = Character.valueOf(c);
          break;
        }
        i = j;
      } while (j >= 0);
    }
    paramCharSequence = null;
    return paramCharSequence;
  }
  
  public static final char first(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0) {
      return paramCharSequence.charAt(0);
    }
    throw new NoSuchElementException("Char sequence is empty.");
  }
  
  public static final char first(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        return c;
      }
    }
    throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
  }
  
  private static final <R> R firstNotNullOf(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      Object localObject2 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)));
      localObject1 = localObject2;
      if (localObject2 != null) {
        break label59;
      }
    }
    Object localObject1 = null;
    label59:
    if (localObject1 != null) {
      return (R)localObject1;
    }
    throw new NoSuchElementException("No element of the char sequence was transformed to a non-null value.");
  }
  
  private static final <R> R firstNotNullOfOrNull(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      Object localObject = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)));
      if (localObject != null) {
        return (R)localObject;
      }
    }
    return null;
  }
  
  public static final Character firstOrNull(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramCharSequence = null;
    } else {
      paramCharSequence = Character.valueOf(paramCharSequence.charAt(0));
    }
    return paramCharSequence;
  }
  
  public static final Character firstOrNull(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        return Character.valueOf(c);
      }
    }
    return null;
  }
  
  public static final <R> List<R> flatMap(CharSequence paramCharSequence, Function1<? super Character, ? extends Iterable<? extends R>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Collection localCollection = (Collection)new ArrayList();
    for (int i = 0; i < paramCharSequence.length(); i++) {
      CollectionsKt.addAll(localCollection, (Iterable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i))));
    }
    return (List)localCollection;
  }
  
  private static final <R> List<R> flatMapIndexedIterable(CharSequence paramCharSequence, Function2<? super Integer, ? super Character, ? extends Iterable<? extends R>> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    Collection localCollection = (Collection)new ArrayList();
    int j = 0;
    for (int i = 0; j < paramCharSequence.length(); i++)
    {
      CollectionsKt.addAll(localCollection, (Iterable)paramFunction2.invoke(Integer.valueOf(i), Character.valueOf(paramCharSequence.charAt(j))));
      j++;
    }
    return (List)localCollection;
  }
  
  private static final <R, C extends Collection<? super R>> C flatMapIndexedIterableTo(CharSequence paramCharSequence, C paramC, Function2<? super Integer, ? super Character, ? extends Iterable<? extends R>> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      CollectionsKt.addAll(paramC, (Iterable)paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(paramCharSequence.charAt(i))));
      i++;
      j++;
    }
    return paramC;
  }
  
  public static final <R, C extends Collection<? super R>> C flatMapTo(CharSequence paramCharSequence, C paramC, Function1<? super Character, ? extends Iterable<? extends R>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      CollectionsKt.addAll(paramC, (Iterable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i))));
    }
    return paramC;
  }
  
  public static final <R> R fold(CharSequence paramCharSequence, R paramR, Function2<? super R, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      paramR = paramFunction2.invoke(paramR, Character.valueOf(paramCharSequence.charAt(i)));
    }
    return paramR;
  }
  
  public static final <R> R foldIndexed(CharSequence paramCharSequence, R paramR, Function3<? super Integer, ? super R, ? super Character, ? extends R> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      paramR = paramFunction3.invoke(Integer.valueOf(j), paramR, Character.valueOf(paramCharSequence.charAt(i)));
      i++;
      j++;
    }
    return paramR;
  }
  
  public static final <R> R foldRight(CharSequence paramCharSequence, R paramR, Function2<? super Character, ? super R, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    for (int i = StringsKt.getLastIndex(paramCharSequence); i >= 0; i--) {
      paramR = paramFunction2.invoke(Character.valueOf(paramCharSequence.charAt(i)), paramR);
    }
    return paramR;
  }
  
  public static final <R> R foldRightIndexed(CharSequence paramCharSequence, R paramR, Function3<? super Integer, ? super Character, ? super R, ? extends R> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    for (int i = StringsKt.getLastIndex(paramCharSequence); i >= 0; i--) {
      paramR = paramFunction3.invoke(Integer.valueOf(i), Character.valueOf(paramCharSequence.charAt(i)), paramR);
    }
    return paramR;
  }
  
  public static final void forEach(CharSequence paramCharSequence, Function1<? super Character, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)));
    }
  }
  
  public static final void forEachIndexed(CharSequence paramCharSequence, Function2<? super Integer, ? super Character, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(paramCharSequence.charAt(i)));
      i++;
      j++;
    }
  }
  
  private static final char getOrElse(CharSequence paramCharSequence, int paramInt, Function1<? super Integer, Character> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "defaultValue");
    char c;
    if ((paramInt >= 0) && (paramInt <= StringsKt.getLastIndex(paramCharSequence))) {
      c = paramCharSequence.charAt(paramInt);
    } else {
      c = ((Character)paramFunction1.invoke(Integer.valueOf(paramInt))).charValue();
    }
    return c;
  }
  
  public static final Character getOrNull(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if ((paramInt >= 0) && (paramInt <= StringsKt.getLastIndex(paramCharSequence))) {
      paramCharSequence = Character.valueOf(paramCharSequence.charAt(paramInt));
    } else {
      paramCharSequence = null;
    }
    return paramCharSequence;
  }
  
  public static final <K> Map<K, List<Character>> groupBy(CharSequence paramCharSequence, Function1<? super Character, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    Map localMap = (Map)new LinkedHashMap();
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      Object localObject2 = paramFunction1.invoke(Character.valueOf(c));
      Object localObject1 = localMap.get(localObject2);
      if (localObject1 == null)
      {
        localObject1 = (List)new ArrayList();
        localMap.put(localObject2, localObject1);
      }
      ((List)localObject1).add(Character.valueOf(c));
    }
    return localMap;
  }
  
  public static final <K, V> Map<K, List<V>> groupBy(CharSequence paramCharSequence, Function1<? super Character, ? extends K> paramFunction1, Function1<? super Character, ? extends V> paramFunction11)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    Intrinsics.checkNotNullParameter(paramFunction11, "valueTransform");
    Map localMap = (Map)new LinkedHashMap();
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      Object localObject2 = paramFunction1.invoke(Character.valueOf(c));
      Object localObject1 = localMap.get(localObject2);
      if (localObject1 == null)
      {
        localObject1 = (List)new ArrayList();
        localMap.put(localObject2, localObject1);
      }
      ((List)localObject1).add(paramFunction11.invoke(Character.valueOf(c)));
    }
    return localMap;
  }
  
  public static final <K, M extends Map<? super K, List<Character>>> M groupByTo(CharSequence paramCharSequence, M paramM, Function1<? super Character, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      Object localObject2 = paramFunction1.invoke(Character.valueOf(c));
      Object localObject1 = paramM.get(localObject2);
      if (localObject1 == null)
      {
        localObject1 = (List)new ArrayList();
        paramM.put(localObject2, localObject1);
      }
      ((List)localObject1).add(Character.valueOf(c));
    }
    return paramM;
  }
  
  public static final <K, V, M extends Map<? super K, List<V>>> M groupByTo(CharSequence paramCharSequence, M paramM, Function1<? super Character, ? extends K> paramFunction1, Function1<? super Character, ? extends V> paramFunction11)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    Intrinsics.checkNotNullParameter(paramFunction11, "valueTransform");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      Object localObject2 = paramFunction1.invoke(Character.valueOf(c));
      Object localObject1 = paramM.get(localObject2);
      if (localObject1 == null)
      {
        localObject1 = (List)new ArrayList();
        paramM.put(localObject2, localObject1);
      }
      ((List)localObject1).add(paramFunction11.invoke(Character.valueOf(c)));
    }
    return paramM;
  }
  
  public static final <K> Grouping<Character, K> groupingBy(CharSequence paramCharSequence, final Function1<? super Character, ? extends K> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "keySelector");
    (Grouping)new Grouping()
    {
      final CharSequence $this_groupingBy;
      
      public K keyOf(char paramAnonymousChar)
      {
        return (K)paramFunction1.invoke(Character.valueOf(paramAnonymousChar));
      }
      
      public Iterator<Character> sourceIterator()
      {
        return (Iterator)StringsKt.iterator(this.$this_groupingBy);
      }
    };
  }
  
  public static final int indexOfFirst(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      if (((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return i;
      }
      i++;
    }
    return -1;
  }
  
  public static final int indexOfLast(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
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
        if (((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
          return i;
        }
        i = j;
      } while (j >= 0);
    }
    return -1;
  }
  
  public static final char last(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0) {
      return paramCharSequence.charAt(StringsKt.getLastIndex(paramCharSequence));
    }
    throw new NoSuchElementException("Char sequence is empty.");
  }
  
  public static final char last(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = paramCharSequence.length() - 1;
    if (i >= 0) {
      do
      {
        int j = i;
        i = j - 1;
        char c = paramCharSequence.charAt(j);
        if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
          return c;
        }
      } while (i >= 0);
    }
    throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
  }
  
  public static final Character lastOrNull(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramCharSequence = null;
    } else {
      paramCharSequence = Character.valueOf(paramCharSequence.charAt(paramCharSequence.length() - 1));
    }
    return paramCharSequence;
  }
  
  public static final Character lastOrNull(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
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
        char c = paramCharSequence.charAt(i);
        if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
          return Character.valueOf(c);
        }
        i = j;
      } while (j >= 0);
    }
    return null;
  }
  
  public static final <R> List<R> map(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Collection localCollection = (Collection)new ArrayList(paramCharSequence.length());
    for (int i = 0; i < paramCharSequence.length(); i++) {
      localCollection.add(paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i))));
    }
    return (List)localCollection;
  }
  
  public static final <R> List<R> mapIndexed(CharSequence paramCharSequence, Function2<? super Integer, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    Collection localCollection = (Collection)new ArrayList(paramCharSequence.length());
    int i = 0;
    int j = 0;
    while (j < paramCharSequence.length())
    {
      localCollection.add(paramFunction2.invoke(Integer.valueOf(i), Character.valueOf(paramCharSequence.charAt(j))));
      j++;
      i++;
    }
    return (List)localCollection;
  }
  
  public static final <R> List<R> mapIndexedNotNull(CharSequence paramCharSequence, Function2<? super Integer, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    Collection localCollection = (Collection)new ArrayList();
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      Object localObject = paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(paramCharSequence.charAt(i)));
      if (localObject != null) {
        localCollection.add(localObject);
      }
      i++;
      j++;
    }
    return (List)localCollection;
  }
  
  public static final <R, C extends Collection<? super R>> C mapIndexedNotNullTo(CharSequence paramCharSequence, C paramC, Function2<? super Integer, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      Object localObject = paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(paramCharSequence.charAt(i)));
      if (localObject != null) {
        paramC.add(localObject);
      }
      i++;
      j++;
    }
    return paramC;
  }
  
  public static final <R, C extends Collection<? super R>> C mapIndexedTo(CharSequence paramCharSequence, C paramC, Function2<? super Integer, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    int i = 0;
    int j = 0;
    while (j < paramCharSequence.length())
    {
      paramC.add(paramFunction2.invoke(Integer.valueOf(i), Character.valueOf(paramCharSequence.charAt(j))));
      j++;
      i++;
    }
    return paramC;
  }
  
  public static final <R> List<R> mapNotNull(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Collection localCollection = (Collection)new ArrayList();
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      Object localObject = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)));
      if (localObject != null) {
        localCollection.add(localObject);
      }
    }
    return (List)localCollection;
  }
  
  public static final <R, C extends Collection<? super R>> C mapNotNullTo(CharSequence paramCharSequence, C paramC, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      Object localObject = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)));
      if (localObject != null) {
        paramC.add(localObject);
      }
    }
    return paramC;
  }
  
  public static final <R, C extends Collection<? super R>> C mapTo(CharSequence paramCharSequence, C paramC, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      paramC.add(paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i))));
    }
    return paramC;
  }
  
  public static final <R extends Comparable<? super R>> Character maxByOrNull(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c1 = paramCharSequence.charAt(0);
    int i = StringsKt.getLastIndex(paramCharSequence);
    if (i == 0) {
      return Character.valueOf(c1);
    }
    Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(c1));
    IntIterator localIntIterator = new IntRange(1, i).iterator();
    while (localIntIterator.hasNext())
    {
      char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
      Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(c2));
      if (((Comparable)localObject).compareTo(localComparable) < 0)
      {
        c1 = c2;
        localObject = localComparable;
      }
    }
    return Character.valueOf(c1);
  }
  
  public static final <R extends Comparable<? super R>> char maxByOrThrow(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c1 = paramCharSequence.charAt(0);
      i = StringsKt.getLastIndex(paramCharSequence);
      if (i == 0) {
        return c1;
      }
      Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(c1));
      IntIterator localIntIterator = new IntRange(1, i).iterator();
      while (localIntIterator.hasNext())
      {
        char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
        Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(c2));
        if (((Comparable)localObject).compareTo(localComparable) < 0)
        {
          c1 = c2;
          localObject = localComparable;
        }
      }
      return c1;
    }
    throw new NoSuchElementException();
  }
  
  private static final double maxOf(CharSequence paramCharSequence, Function1<? super Character, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      double d = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).doubleValue();
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext()) {
        d = Math.max(d, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).doubleValue());
      }
      return d;
    }
    throw new NoSuchElementException();
  }
  
  private static final float maxOf(CharSequence paramCharSequence, Function1<? super Character, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      float f = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).floatValue();
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext()) {
        f = Math.max(f, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).floatValue());
      }
      return f;
    }
    throw new NoSuchElementException();
  }
  
  private static final <R extends Comparable<? super R>> R maxOf(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
        if (((Comparable)localObject).compareTo(localComparable) < 0) {
          localObject = localComparable;
        }
      }
      return (R)localObject;
    }
    throw new NoSuchElementException();
  }
  
  private static final <R extends Comparable<? super R>> R maxOfOrNull(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
      if (((Comparable)localObject).compareTo(localComparable) < 0) {
        localObject = localComparable;
      }
    }
    return (R)localObject;
  }
  
  private static final Double maxOfOrNull(CharSequence paramCharSequence, Function1<? super Character, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    double d = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).doubleValue();
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext()) {
      d = Math.max(d, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).doubleValue());
    }
    return Double.valueOf(d);
  }
  
  private static final Float maxOfOrNull(CharSequence paramCharSequence, Function1<? super Character, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    float f = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).floatValue();
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext()) {
      f = Math.max(f, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).floatValue());
    }
    return Float.valueOf(f);
  }
  
  private static final <R> R maxOfWith(CharSequence paramCharSequence, Comparator<? super R> paramComparator, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      Object localObject1 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        Object localObject2 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
        if (paramComparator.compare(localObject1, localObject2) < 0) {
          localObject1 = localObject2;
        }
      }
      return (R)localObject1;
    }
    throw new NoSuchElementException();
  }
  
  private static final <R> R maxOfWithOrNull(CharSequence paramCharSequence, Comparator<? super R> paramComparator, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    Object localObject1 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      Object localObject2 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
      if (paramComparator.compare(localObject1, localObject2) < 0) {
        localObject1 = localObject2;
      }
    }
    return (R)localObject1;
  }
  
  public static final Character maxOrNull(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c1 = paramCharSequence.charAt(0);
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
      if (Intrinsics.compare(c1, c2) < 0) {
        c1 = c2;
      }
    }
    return Character.valueOf(c1);
  }
  
  public static final char maxOrThrow(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c1 = paramCharSequence.charAt(0);
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
        if (Intrinsics.compare(c1, c2) < 0) {
          c1 = c2;
        }
      }
      return c1;
    }
    throw new NoSuchElementException();
  }
  
  public static final Character maxWithOrNull(CharSequence paramCharSequence, Comparator<? super Character> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c1 = paramCharSequence.charAt(0);
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
      if (paramComparator.compare(Character.valueOf(c1), Character.valueOf(c2)) < 0) {
        c1 = c2;
      }
    }
    return Character.valueOf(c1);
  }
  
  public static final char maxWithOrThrow(CharSequence paramCharSequence, Comparator<? super Character> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c1 = paramCharSequence.charAt(0);
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
        if (paramComparator.compare(Character.valueOf(c1), Character.valueOf(c2)) < 0) {
          c1 = c2;
        }
      }
      return c1;
    }
    throw new NoSuchElementException();
  }
  
  public static final <R extends Comparable<? super R>> Character minByOrNull(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c1 = paramCharSequence.charAt(0);
    int i = StringsKt.getLastIndex(paramCharSequence);
    if (i == 0) {
      return Character.valueOf(c1);
    }
    Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(c1));
    IntIterator localIntIterator = new IntRange(1, i).iterator();
    while (localIntIterator.hasNext())
    {
      char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
      Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(c2));
      if (((Comparable)localObject).compareTo(localComparable) > 0)
      {
        c1 = c2;
        localObject = localComparable;
      }
    }
    return Character.valueOf(c1);
  }
  
  public static final <R extends Comparable<? super R>> char minByOrThrow(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c1 = paramCharSequence.charAt(0);
      i = StringsKt.getLastIndex(paramCharSequence);
      if (i == 0) {
        return c1;
      }
      Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(c1));
      IntIterator localIntIterator = new IntRange(1, i).iterator();
      while (localIntIterator.hasNext())
      {
        char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
        Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(c2));
        if (((Comparable)localObject).compareTo(localComparable) > 0)
        {
          c1 = c2;
          localObject = localComparable;
        }
      }
      return c1;
    }
    throw new NoSuchElementException();
  }
  
  private static final double minOf(CharSequence paramCharSequence, Function1<? super Character, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      double d = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).doubleValue();
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext()) {
        d = Math.min(d, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).doubleValue());
      }
      return d;
    }
    throw new NoSuchElementException();
  }
  
  private static final float minOf(CharSequence paramCharSequence, Function1<? super Character, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      float f = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).floatValue();
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext()) {
        f = Math.min(f, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).floatValue());
      }
      return f;
    }
    throw new NoSuchElementException();
  }
  
  private static final <R extends Comparable<? super R>> R minOf(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
        if (((Comparable)localObject).compareTo(localComparable) > 0) {
          localObject = localComparable;
        }
      }
      return (R)localObject;
    }
    throw new NoSuchElementException();
  }
  
  private static final <R extends Comparable<? super R>> R minOfOrNull(CharSequence paramCharSequence, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    Object localObject = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      Comparable localComparable = (Comparable)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
      if (((Comparable)localObject).compareTo(localComparable) > 0) {
        localObject = localComparable;
      }
    }
    return (R)localObject;
  }
  
  private static final Double minOfOrNull(CharSequence paramCharSequence, Function1<? super Character, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    double d = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).doubleValue();
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext()) {
      d = Math.min(d, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).doubleValue());
    }
    return Double.valueOf(d);
  }
  
  private static final Float minOfOrNull(CharSequence paramCharSequence, Function1<? super Character, Float> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    float f = ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)))).floatValue();
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext()) {
      f = Math.min(f, ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).floatValue());
    }
    return Float.valueOf(f);
  }
  
  private static final <R> R minOfWith(CharSequence paramCharSequence, Comparator<? super R> paramComparator, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      Object localObject1 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        Object localObject2 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
        if (paramComparator.compare(localObject1, localObject2) > 0) {
          localObject1 = localObject2;
        }
      }
      return (R)localObject1;
    }
    throw new NoSuchElementException();
  }
  
  private static final <R> R minOfWithOrNull(CharSequence paramCharSequence, Comparator<? super R> paramComparator, Function1<? super Character, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    Object localObject1 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(0)));
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      Object localObject2 = paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())));
      if (paramComparator.compare(localObject1, localObject2) > 0) {
        localObject1 = localObject2;
      }
    }
    return (R)localObject1;
  }
  
  public static final Character minOrNull(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c1 = paramCharSequence.charAt(0);
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
      if (Intrinsics.compare(c1, c2) > 0) {
        c1 = c2;
      }
    }
    return Character.valueOf(c1);
  }
  
  public static final char minOrThrow(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c1 = paramCharSequence.charAt(0);
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
        if (Intrinsics.compare(c1, c2) > 0) {
          c1 = c2;
        }
      }
      return c1;
    }
    throw new NoSuchElementException();
  }
  
  public static final Character minWithOrNull(CharSequence paramCharSequence, Comparator<? super Character> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c1 = paramCharSequence.charAt(0);
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
      if (paramComparator.compare(Character.valueOf(c1), Character.valueOf(c2)) > 0) {
        c1 = c2;
      }
    }
    return Character.valueOf(c1);
  }
  
  public static final char minWithOrThrow(CharSequence paramCharSequence, Comparator<? super Character> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c1 = paramCharSequence.charAt(0);
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        char c2 = paramCharSequence.charAt(localIntIterator.nextInt());
        if (paramComparator.compare(Character.valueOf(c1), Character.valueOf(c2)) > 0) {
          c1 = c2;
        }
      }
      return c1;
    }
    throw new NoSuchElementException();
  }
  
  public static final boolean none(CharSequence paramCharSequence)
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
  
  public static final boolean none(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      if (((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return false;
      }
    }
    return true;
  }
  
  public static final <S extends CharSequence> S onEach(S paramS, Function1<? super Character, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramS, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    for (int i = 0; i < paramS.length(); i++) {
      paramFunction1.invoke(Character.valueOf(paramS.charAt(i)));
    }
    return paramS;
  }
  
  public static final <S extends CharSequence> S onEachIndexed(S paramS, Function2<? super Integer, ? super Character, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramS, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    int j = 0;
    int i = 0;
    while (i < paramS.length())
    {
      paramFunction2.invoke(Integer.valueOf(j), Character.valueOf(paramS.charAt(i)));
      i++;
      j++;
    }
    return paramS;
  }
  
  public static final Pair<CharSequence, CharSequence> partition(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    StringBuilder localStringBuilder2 = new StringBuilder();
    StringBuilder localStringBuilder1 = new StringBuilder();
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        localStringBuilder2.append(c);
      } else {
        localStringBuilder1.append(c);
      }
    }
    return new Pair(localStringBuilder2, localStringBuilder1);
  }
  
  public static final Pair<String, String> partition(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    StringBuilder localStringBuilder2 = new StringBuilder();
    StringBuilder localStringBuilder1 = new StringBuilder();
    int j = paramString.length();
    for (int i = 0; i < j; i++)
    {
      char c = paramString.charAt(i);
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        localStringBuilder2.append(c);
      } else {
        localStringBuilder1.append(c);
      }
    }
    paramString = localStringBuilder2.toString();
    Intrinsics.checkNotNullExpressionValue(paramString, "first.toString()");
    paramFunction1 = localStringBuilder1.toString();
    Intrinsics.checkNotNullExpressionValue(paramFunction1, "second.toString()");
    return new Pair(paramString, paramFunction1);
  }
  
  private static final char random(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.random(paramCharSequence, (Random)Random.Default);
  }
  
  public static final char random(CharSequence paramCharSequence, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0) {
      return paramCharSequence.charAt(paramRandom.nextInt(paramCharSequence.length()));
    }
    throw new NoSuchElementException("Char sequence is empty.");
  }
  
  private static final Character randomOrNull(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.randomOrNull(paramCharSequence, (Random)Random.Default);
  }
  
  public static final Character randomOrNull(CharSequence paramCharSequence, Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    return Character.valueOf(paramCharSequence.charAt(paramRandom.nextInt(paramCharSequence.length())));
  }
  
  public static final char reduce(CharSequence paramCharSequence, Function2<? super Character, ? super Character, Character> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c = paramCharSequence.charAt(0);
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext()) {
        c = ((Character)paramFunction2.invoke(Character.valueOf(c), Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).charValue();
      }
      return c;
    }
    throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
  }
  
  public static final char reduceIndexed(CharSequence paramCharSequence, Function3<? super Integer, ? super Character, ? super Character, Character> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      char c = paramCharSequence.charAt(0);
      IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
      while (localIntIterator.hasNext())
      {
        i = localIntIterator.nextInt();
        c = ((Character)paramFunction3.invoke(Integer.valueOf(i), Character.valueOf(c), Character.valueOf(paramCharSequence.charAt(i)))).charValue();
      }
      return c;
    }
    throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
  }
  
  public static final Character reduceIndexedOrNull(CharSequence paramCharSequence, Function3<? super Integer, ? super Character, ? super Character, Character> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c = paramCharSequence.charAt(0);
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext())
    {
      i = localIntIterator.nextInt();
      c = ((Character)paramFunction3.invoke(Integer.valueOf(i), Character.valueOf(c), Character.valueOf(paramCharSequence.charAt(i)))).charValue();
    }
    return Character.valueOf(c);
  }
  
  public static final Character reduceOrNull(CharSequence paramCharSequence, Function2<? super Character, ? super Character, Character> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return null;
    }
    char c = paramCharSequence.charAt(0);
    IntIterator localIntIterator = new IntRange(1, StringsKt.getLastIndex(paramCharSequence)).iterator();
    while (localIntIterator.hasNext()) {
      c = ((Character)paramFunction2.invoke(Character.valueOf(c), Character.valueOf(paramCharSequence.charAt(localIntIterator.nextInt())))).charValue();
    }
    return Character.valueOf(c);
  }
  
  public static final char reduceRight(CharSequence paramCharSequence, Function2<? super Character, ? super Character, Character> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    int j = StringsKt.getLastIndex(paramCharSequence);
    if (j >= 0)
    {
      int i = j - 1;
      char c = paramCharSequence.charAt(j);
      while (i >= 0)
      {
        c = ((Character)paramFunction2.invoke(Character.valueOf(paramCharSequence.charAt(i)), Character.valueOf(c))).charValue();
        i--;
      }
      return c;
    }
    throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
  }
  
  public static final char reduceRightIndexed(CharSequence paramCharSequence, Function3<? super Integer, ? super Character, ? super Character, Character> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    int j = StringsKt.getLastIndex(paramCharSequence);
    if (j >= 0)
    {
      int i = j - 1;
      char c = paramCharSequence.charAt(j);
      while (i >= 0)
      {
        c = ((Character)paramFunction3.invoke(Integer.valueOf(i), Character.valueOf(paramCharSequence.charAt(i)), Character.valueOf(c))).charValue();
        i--;
      }
      return c;
    }
    throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
  }
  
  public static final Character reduceRightIndexedOrNull(CharSequence paramCharSequence, Function3<? super Integer, ? super Character, ? super Character, Character> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    int j = StringsKt.getLastIndex(paramCharSequence);
    if (j < 0) {
      return null;
    }
    int i = j - 1;
    char c = paramCharSequence.charAt(j);
    while (i >= 0)
    {
      c = ((Character)paramFunction3.invoke(Integer.valueOf(i), Character.valueOf(paramCharSequence.charAt(i)), Character.valueOf(c))).charValue();
      i--;
    }
    return Character.valueOf(c);
  }
  
  public static final Character reduceRightOrNull(CharSequence paramCharSequence, Function2<? super Character, ? super Character, Character> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    int j = StringsKt.getLastIndex(paramCharSequence);
    if (j < 0) {
      return null;
    }
    int i = j - 1;
    char c = paramCharSequence.charAt(j);
    while (i >= 0)
    {
      c = ((Character)paramFunction2.invoke(Character.valueOf(paramCharSequence.charAt(i)), Character.valueOf(c))).charValue();
      i--;
    }
    return Character.valueOf(c);
  }
  
  public static final CharSequence reversed(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    paramCharSequence = new StringBuilder(paramCharSequence).reverse();
    Intrinsics.checkNotNullExpressionValue(paramCharSequence, "StringBuilder(this).reverse()");
    return (CharSequence)paramCharSequence;
  }
  
  private static final String reversed(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return StringsKt.reversed((CharSequence)paramString).toString();
  }
  
  public static final <R> List<R> runningFold(CharSequence paramCharSequence, R paramR, Function2<? super R, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    int i = paramCharSequence.length();
    int j = 0;
    if (i == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return CollectionsKt.listOf(paramR);
    }
    ArrayList localArrayList = new ArrayList(paramCharSequence.length() + 1);
    localArrayList.add(paramR);
    for (i = j; i < paramCharSequence.length(); i++)
    {
      paramR = paramFunction2.invoke(paramR, Character.valueOf(paramCharSequence.charAt(i)));
      localArrayList.add(paramR);
    }
    return (List)localArrayList;
  }
  
  public static final <R> List<R> runningFoldIndexed(CharSequence paramCharSequence, R paramR, Function3<? super Integer, ? super R, ? super Character, ? extends R> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return CollectionsKt.listOf(paramR);
    }
    ArrayList localArrayList = new ArrayList(paramCharSequence.length() + 1);
    localArrayList.add(paramR);
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      paramR = paramFunction3.invoke(Integer.valueOf(i), paramR, Character.valueOf(paramCharSequence.charAt(i)));
      localArrayList.add(paramR);
      i++;
    }
    return (List)localArrayList;
  }
  
  public static final List<Character> runningReduce(CharSequence paramCharSequence, Function2<? super Character, ? super Character, Character> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return CollectionsKt.emptyList();
    }
    char c = paramCharSequence.charAt(0);
    ArrayList localArrayList = new ArrayList(paramCharSequence.length());
    localArrayList.add(Character.valueOf(c));
    int i = 1;
    int j = paramCharSequence.length();
    while (i < j)
    {
      c = ((Character)paramFunction2.invoke(Character.valueOf(c), Character.valueOf(paramCharSequence.charAt(i)))).charValue();
      localArrayList.add(Character.valueOf(c));
      i++;
    }
    return (List)localArrayList;
  }
  
  public static final List<Character> runningReduceIndexed(CharSequence paramCharSequence, Function3<? super Integer, ? super Character, ? super Character, Character> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return CollectionsKt.emptyList();
    }
    char c = paramCharSequence.charAt(0);
    ArrayList localArrayList = new ArrayList(paramCharSequence.length());
    localArrayList.add(Character.valueOf(c));
    int i = 1;
    int j = paramCharSequence.length();
    while (i < j)
    {
      c = ((Character)paramFunction3.invoke(Integer.valueOf(i), Character.valueOf(c), Character.valueOf(paramCharSequence.charAt(i)))).charValue();
      localArrayList.add(Character.valueOf(c));
      i++;
    }
    return (List)localArrayList;
  }
  
  public static final <R> List<R> scan(CharSequence paramCharSequence, R paramR, Function2<? super R, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "operation");
    int i = paramCharSequence.length();
    int j = 0;
    if (i == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramCharSequence = CollectionsKt.listOf(paramR);
    }
    else
    {
      ArrayList localArrayList = new ArrayList(paramCharSequence.length() + 1);
      localArrayList.add(paramR);
      for (i = j; i < paramCharSequence.length(); i++)
      {
        paramR = paramFunction2.invoke(paramR, Character.valueOf(paramCharSequence.charAt(i)));
        localArrayList.add(paramR);
      }
      paramCharSequence = (List)localArrayList;
    }
    return paramCharSequence;
  }
  
  public static final <R> List<R> scanIndexed(CharSequence paramCharSequence, R paramR, Function3<? super Integer, ? super R, ? super Character, ? extends R> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction3, "operation");
    int i;
    if (paramCharSequence.length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramCharSequence = CollectionsKt.listOf(paramR);
    }
    else
    {
      ArrayList localArrayList = new ArrayList(paramCharSequence.length() + 1);
      localArrayList.add(paramR);
      i = 0;
      int j = paramCharSequence.length();
      while (i < j)
      {
        paramR = paramFunction3.invoke(Integer.valueOf(i), paramR, Character.valueOf(paramCharSequence.charAt(i)));
        localArrayList.add(paramR);
        i++;
      }
      paramCharSequence = (List)localArrayList;
    }
    return paramCharSequence;
  }
  
  public static final char single(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    switch (paramCharSequence.length())
    {
    default: 
      throw new IllegalArgumentException("Char sequence has more than one element.");
    case 1: 
      return paramCharSequence.charAt(0);
    }
    throw new NoSuchElementException("Char sequence is empty.");
  }
  
  public static final char single(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Character localCharacter = null;
    int k = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      char c = paramCharSequence.charAt(i);
      int j = k;
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue()) {
        if (k == 0)
        {
          localCharacter = Character.valueOf(c);
          j = 1;
        }
        else
        {
          throw new IllegalArgumentException("Char sequence contains more than one matching element.");
        }
      }
      i++;
      k = j;
    }
    if (k != 0)
    {
      Intrinsics.checkNotNull(localCharacter, "null cannot be cast to non-null type kotlin.Char");
      return localCharacter.charValue();
    }
    throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
  }
  
  public static final Character singleOrNull(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    if (paramCharSequence.length() == 1) {
      paramCharSequence = Character.valueOf(paramCharSequence.charAt(0));
    } else {
      paramCharSequence = null;
    }
    return paramCharSequence;
  }
  
  public static final Character singleOrNull(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Character localCharacter = null;
    int j = 0;
    int i = 0;
    while (i < paramCharSequence.length())
    {
      char c = paramCharSequence.charAt(i);
      int k = j;
      if (((Boolean)paramFunction1.invoke(Character.valueOf(c))).booleanValue())
      {
        if (j != 0) {
          return null;
        }
        localCharacter = Character.valueOf(c);
        k = 1;
      }
      i++;
      j = k;
    }
    if (j == 0) {
      return null;
    }
    return localCharacter;
  }
  
  public static final CharSequence slice(CharSequence paramCharSequence, Iterable<Integer> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "indices");
    int i = CollectionsKt.collectionSizeOrDefault(paramIterable, 10);
    if (i == 0) {
      return (CharSequence)"";
    }
    StringBuilder localStringBuilder = new StringBuilder(i);
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      localStringBuilder.append(paramCharSequence.charAt(((Number)paramIterable.next()).intValue()));
    }
    return (CharSequence)localStringBuilder;
  }
  
  public static final CharSequence slice(CharSequence paramCharSequence, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "indices");
    if (paramIntRange.isEmpty()) {
      return (CharSequence)"";
    }
    return StringsKt.subSequence(paramCharSequence, paramIntRange);
  }
  
  private static final String slice(String paramString, Iterable<Integer> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "indices");
    return StringsKt.slice((CharSequence)paramString, paramIterable).toString();
  }
  
  public static final String slice(String paramString, IntRange paramIntRange)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "indices");
    if (paramIntRange.isEmpty()) {
      return "";
    }
    paramString = StringsKt.substring(paramString, paramIntRange);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return paramString;
  }
  
  @Deprecated(message="Use sumOf instead.", replaceWith=@ReplaceWith(expression="this.sumOf(selector)", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  public static final int sumBy(CharSequence paramCharSequence, Function1<? super Character, Integer> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int j = 0;
    for (int i = 0; i < paramCharSequence.length(); i++) {
      j += ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).intValue();
    }
    return j;
  }
  
  @Deprecated(message="Use sumOf instead.", replaceWith=@ReplaceWith(expression="this.sumOf(selector)", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  public static final double sumByDouble(CharSequence paramCharSequence, Function1<? super Character, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    double d = 0.0D;
    for (int i = 0; i < paramCharSequence.length(); i++) {
      d += ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).doubleValue();
    }
    return d;
  }
  
  private static final double sumOfDouble(CharSequence paramCharSequence, Function1<? super Character, Double> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    double d = 0.0D;
    for (int i = 0; i < paramCharSequence.length(); i++) {
      d += ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).doubleValue();
    }
    return d;
  }
  
  private static final int sumOfInt(CharSequence paramCharSequence, Function1<? super Character, Integer> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int j = 0;
    for (int i = 0; i < paramCharSequence.length(); i++) {
      j += ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).intValue();
    }
    return j;
  }
  
  private static final long sumOfLong(CharSequence paramCharSequence, Function1<? super Character, Long> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    long l = 0L;
    for (int i = 0; i < paramCharSequence.length(); i++) {
      l += ((Number)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).longValue();
    }
    return l;
  }
  
  private static final int sumOfUInt(CharSequence paramCharSequence, Function1<? super Character, UInt> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    int j = 0;
    int i = UInt.constructor-impl(0);
    while (j < paramCharSequence.length())
    {
      i = UInt.constructor-impl(((UInt)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(j)))).unbox-impl() + i);
      j++;
    }
    return i;
  }
  
  private static final long sumOfULong(CharSequence paramCharSequence, Function1<? super Character, ULong> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "selector");
    long l = ULong.constructor-impl(0L);
    for (int i = 0; i < paramCharSequence.length(); i++) {
      l = ULong.constructor-impl(((ULong)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).unbox-impl() + l);
    }
    return l;
  }
  
  public static final CharSequence take(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramCharSequence.subSequence(0, RangesKt.coerceAtMost(paramInt, paramCharSequence.length()));
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final String take(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramString = paramString.substring(0, RangesKt.coerceAtMost(paramInt, paramString.length()));
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
      return paramString;
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final CharSequence takeLast(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      i = paramCharSequence.length();
      return paramCharSequence.subSequence(i - RangesKt.coerceAtMost(paramInt, i), i);
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final String takeLast(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      i = paramString.length();
      paramString = paramString.substring(i - RangesKt.coerceAtMost(paramInt, i));
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
      return paramString;
    }
    throw new IllegalArgumentException(("Requested character count " + paramInt + " is less than zero.").toString());
  }
  
  public static final CharSequence takeLastWhile(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = StringsKt.getLastIndex(paramCharSequence); -1 < i; i--) {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return paramCharSequence.subSequence(i + 1, paramCharSequence.length());
      }
    }
    return paramCharSequence.subSequence(0, paramCharSequence.length());
  }
  
  public static final String takeLastWhile(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    for (int i = StringsKt.getLastIndex((CharSequence)paramString); -1 < i; i--) {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(i)))).booleanValue())
      {
        paramString = paramString.substring(i + 1);
        Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
        return paramString;
      }
    }
    return paramString;
  }
  
  public static final CharSequence takeWhile(CharSequence paramCharSequence, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = 0;
    int j = paramCharSequence.length();
    while (i < j)
    {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramCharSequence.charAt(i)))).booleanValue()) {
        return paramCharSequence.subSequence(0, i);
      }
      i++;
    }
    return paramCharSequence.subSequence(0, paramCharSequence.length());
  }
  
  public static final String takeWhile(String paramString, Function1<? super Character, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    int i = 0;
    int j = paramString.length();
    while (i < j)
    {
      if (!((Boolean)paramFunction1.invoke(Character.valueOf(paramString.charAt(i)))).booleanValue())
      {
        paramString = paramString.substring(0, i);
        Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
        return paramString;
      }
      i++;
    }
    return paramString;
  }
  
  public static final <C extends Collection<? super Character>> C toCollection(CharSequence paramCharSequence, C paramC)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramC, "destination");
    for (int i = 0; i < paramCharSequence.length(); i++) {
      paramC.add(Character.valueOf(paramCharSequence.charAt(i)));
    }
    return paramC;
  }
  
  public static final HashSet<Character> toHashSet(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return (HashSet)StringsKt.toCollection(paramCharSequence, (Collection)new HashSet(MapsKt.mapCapacity(RangesKt.coerceAtMost(paramCharSequence.length(), 128))));
  }
  
  public static final List<Character> toList(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    switch (paramCharSequence.length())
    {
    default: 
      paramCharSequence = StringsKt.toMutableList(paramCharSequence);
      break;
    case 1: 
      paramCharSequence = CollectionsKt.listOf(Character.valueOf(paramCharSequence.charAt(0)));
      break;
    case 0: 
      paramCharSequence = CollectionsKt.emptyList();
    }
    return paramCharSequence;
  }
  
  public static final List<Character> toMutableList(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return (List)StringsKt.toCollection(paramCharSequence, (Collection)new ArrayList(paramCharSequence.length()));
  }
  
  public static final Set<Character> toSet(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    switch (paramCharSequence.length())
    {
    default: 
      paramCharSequence = (Set)StringsKt.toCollection(paramCharSequence, (Collection)new LinkedHashSet(MapsKt.mapCapacity(RangesKt.coerceAtMost(paramCharSequence.length(), 128))));
      break;
    case 1: 
      paramCharSequence = SetsKt.setOf(Character.valueOf(paramCharSequence.charAt(0)));
      break;
    case 0: 
      paramCharSequence = SetsKt.emptySet();
    }
    return paramCharSequence;
  }
  
  public static final List<String> windowed(CharSequence paramCharSequence, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.windowed(paramCharSequence, paramInt1, paramInt2, paramBoolean, (Function1)windowed.1.INSTANCE);
  }
  
  public static final <R> List<R> windowed(CharSequence paramCharSequence, int paramInt1, int paramInt2, boolean paramBoolean, Function1<? super CharSequence, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    SlidingWindowKt.checkWindowSizeStep(paramInt1, paramInt2);
    int k = paramCharSequence.length();
    int j = k / paramInt2;
    if (k % paramInt2 == 0) {
      i = 0;
    } else {
      i = 1;
    }
    ArrayList localArrayList = new ArrayList(j + i);
    int i = 0;
    for (;;)
    {
      if ((i >= 0) && (i < k)) {
        j = 1;
      } else {
        j = 0;
      }
      if (j == 0) {
        break;
      }
      j = i + paramInt1;
      if ((j >= 0) && (j <= k)) {
        break label124;
      }
      if (!paramBoolean) {
        break;
      }
      j = k;
      label124:
      localArrayList.add(paramFunction1.invoke(paramCharSequence.subSequence(i, j)));
      i += paramInt2;
    }
    return (List)localArrayList;
  }
  
  public static final Sequence<String> windowedSequence(CharSequence paramCharSequence, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    return StringsKt.windowedSequence(paramCharSequence, paramInt1, paramInt2, paramBoolean, (Function1)windowedSequence.1.INSTANCE);
  }
  
  public static final <R> Sequence<R> windowedSequence(final CharSequence paramCharSequence, int paramInt1, int paramInt2, boolean paramBoolean, final Function1<? super CharSequence, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    SlidingWindowKt.checkWindowSizeStep(paramInt1, paramInt2);
    IntRange localIntRange;
    if (paramBoolean) {
      localIntRange = StringsKt.getIndices(paramCharSequence);
    } else {
      localIntRange = RangesKt.until(0, paramCharSequence.length() - paramInt1 + 1);
    }
    SequencesKt.map(CollectionsKt.asSequence((Iterable)RangesKt.step((IntProgression)localIntRange, paramInt2)), (Function1)new Lambda(paramInt1)
    {
      final int $size;
      
      public final R invoke(int paramAnonymousInt)
      {
        int i = this.$size + paramAnonymousInt;
        if ((i >= 0) && (i <= paramCharSequence.length())) {
          break label40;
        }
        i = paramCharSequence.length();
        label40:
        return (R)paramFunction1.invoke(paramCharSequence.subSequence(paramAnonymousInt, i));
      }
    });
  }
  
  public static final Iterable<IndexedValue<Character>> withIndex(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    (Iterable)new IndexingIterable((Function0)new Lambda(paramCharSequence)
    {
      final CharSequence $this_withIndex;
      
      public final Iterator<Character> invoke()
      {
        return (Iterator)StringsKt.iterator(this.$this_withIndex);
      }
    });
  }
  
  public static final List<Pair<Character, Character>> zip(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "other");
    int j = Math.min(paramCharSequence1.length(), paramCharSequence2.length());
    ArrayList localArrayList = new ArrayList(j);
    for (int i = 0; i < j; i++) {
      localArrayList.add(TuplesKt.to(Character.valueOf(paramCharSequence1.charAt(i)), Character.valueOf(paramCharSequence2.charAt(i))));
    }
    return (List)localArrayList;
  }
  
  public static final <V> List<V> zip(CharSequence paramCharSequence1, CharSequence paramCharSequence2, Function2<? super Character, ? super Character, ? extends V> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "other");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    int j = Math.min(paramCharSequence1.length(), paramCharSequence2.length());
    ArrayList localArrayList = new ArrayList(j);
    for (int i = 0; i < j; i++) {
      localArrayList.add(paramFunction2.invoke(Character.valueOf(paramCharSequence1.charAt(i)), Character.valueOf(paramCharSequence2.charAt(i))));
    }
    return (List)localArrayList;
  }
  
  public static final List<Pair<Character, Character>> zipWithNext(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int j = paramCharSequence.length() - 1;
    if (j < 1)
    {
      paramCharSequence = CollectionsKt.emptyList();
    }
    else
    {
      ArrayList localArrayList = new ArrayList(j);
      for (int i = 0; i < j; i++) {
        localArrayList.add(TuplesKt.to(Character.valueOf(paramCharSequence.charAt(i)), Character.valueOf(paramCharSequence.charAt(i + 1))));
      }
      paramCharSequence = (List)localArrayList;
    }
    return paramCharSequence;
  }
  
  public static final <R> List<R> zipWithNext(CharSequence paramCharSequence, Function2<? super Character, ? super Character, ? extends R> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    int j = paramCharSequence.length() - 1;
    if (j < 1) {
      return CollectionsKt.emptyList();
    }
    ArrayList localArrayList = new ArrayList(j);
    for (int i = 0; i < j; i++) {
      localArrayList.add(paramFunction2.invoke(Character.valueOf(paramCharSequence.charAt(i)), Character.valueOf(paramCharSequence.charAt(i + 1))));
    }
    return (List)localArrayList;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt___StringsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */