package kotlin.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;

@Metadata(d1={"\000~\n\000\n\002\020$\n\002\b\003\n\002\020\b\n\000\n\002\030\002\n\002\020%\n\002\020\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020&\n\002\b\003\n\002\020\013\n\002\030\002\n\002\b\023\n\002\030\002\n\002\b\n\n\002\020(\n\002\020)\n\002\020'\n\002\b\n\n\002\020\034\n\002\030\002\n\002\b\027\032`\020\000\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\0032\006\020\004\032\0020\0052%\b\001\020\006\032\037\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\b\022\004\022\0020\t0\007¢\006\002\b\nH\bø\001\000\002\n\n\b\b\001\022\002\020\002 \001\032X\020\000\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\0032%\b\001\020\006\032\037\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\b\022\004\022\0020\t0\007¢\006\002\b\nH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\032\036\020\013\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003\0321\020\f\032\036\022\004\022\002H\002\022\004\022\002H\0030\rj\016\022\004\022\002H\002\022\004\022\002H\003`\016\"\004\b\000\020\002\"\004\b\001\020\003H\b\032_\020\f\032\036\022\004\022\002H\002\022\004\022\002H\0030\rj\016\022\004\022\002H\002\022\004\022\002H\003`\016\"\004\b\000\020\002\"\004\b\001\020\0032*\020\017\032\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020\"\016\022\004\022\002H\002\022\004\022\002H\0030\021¢\006\002\020\022\0321\020\023\032\036\022\004\022\002H\002\022\004\022\002H\0030\024j\016\022\004\022\002H\002\022\004\022\002H\003`\025\"\004\b\000\020\002\"\004\b\001\020\003H\b\032_\020\023\032\036\022\004\022\002H\002\022\004\022\002H\0030\024j\016\022\004\022\002H\002\022\004\022\002H\003`\025\"\004\b\000\020\002\"\004\b\001\020\0032*\020\017\032\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020\"\016\022\004\022\002H\002\022\004\022\002H\0030\021¢\006\002\020\026\032!\020\027\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003H\b\032O\020\027\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\0032*\020\017\032\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020\"\016\022\004\022\002H\002\022\004\022\002H\0030\021¢\006\002\020\030\032!\020\031\032\016\022\004\022\002H\002\022\004\022\002H\0030\b\"\004\b\000\020\002\"\004\b\001\020\003H\b\032O\020\031\032\016\022\004\022\002H\002\022\004\022\002H\0030\b\"\004\b\000\020\002\"\004\b\001\020\0032*\020\017\032\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020\"\016\022\004\022\002H\002\022\004\022\002H\0030\021¢\006\002\020\030\032*\020\032\032\002H\002\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\033H\n¢\006\002\020\034\032*\020\035\032\002H\003\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\033H\n¢\006\002\020\034\0329\020\036\032\0020\037\"\t\b\000\020\002¢\006\002\b \"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020!\032\002H\002H\n¢\006\002\020\"\0321\020#\032\0020\037\"\t\b\000\020\002¢\006\002\b *\016\022\006\b\001\022\002H\002\022\002\b\0030\0012\006\020!\032\002H\002H\b¢\006\002\020\"\0327\020$\032\0020\037\"\004\b\000\020\002\"\t\b\001\020\003¢\006\002\b *\016\022\004\022\002H\002\022\004\022\002H\0030\0012\006\020%\032\002H\003H\b¢\006\002\020\"\032V\020&\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\0020\0370\007H\bø\001\000\032J\020(\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\022\020'\032\016\022\004\022\002H\002\022\004\022\0020\0370\007H\bø\001\000\032V\020)\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\0020\0370\007H\bø\001\000\032q\020*\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\030\b\002\020+*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020,\032\002H+2\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\0020\0370\007H\bø\001\000¢\006\002\020-\032q\020.\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\030\b\002\020+*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020,\032\002H+2\036\020'\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\0020\0370\007H\bø\001\000¢\006\002\020-\032J\020/\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\022\020'\032\016\022\004\022\002H\003\022\004\022\0020\0370\007H\bø\001\000\032;\0200\032\004\030\001H\003\"\t\b\000\020\002¢\006\002\b \"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020!\032\002H\002H\n¢\006\002\0201\032C\0202\032\002H\003\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\0012\006\020!\032\002H\0022\f\0203\032\b\022\004\022\002H\00304H\bø\001\000¢\006\002\0205\032C\0206\032\002H\003\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\0012\006\020!\032\002H\0022\f\0203\032\b\022\004\022\002H\00304H\bø\001\000¢\006\002\0205\032C\0207\032\002H\003\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\b2\006\020!\032\002H\0022\f\0203\032\b\022\004\022\002H\00304H\bø\001\000¢\006\002\0205\0321\0208\032\002H\003\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\0012\006\020!\032\002H\002H\007¢\006\002\0201\032?\0209\032\002H:\"\024\b\000\020+*\n\022\002\b\003\022\002\b\0030\001*\002H:\"\004\b\001\020:*\002H+2\f\0203\032\b\022\004\022\002H:04H\bø\001\000¢\006\002\020;\032'\020<\032\0020\037\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\001H\b\032:\020=\032\0020\037\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\001\022\002H\002\022\004\022\002H\003\030\0010\001H\b\002\016\n\f\b\000\022\002\030\001\032\004\b\003\020\000\0329\020>\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0330?\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\001H\n\032<\020>\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030A0@\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\bH\n¢\006\002\bB\032\\\020C\032\016\022\004\022\002H:\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020:*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\036\020D\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\002H:0\007H\bø\001\000\032w\020E\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020:\"\030\b\003\020+*\022\022\006\b\000\022\002H:\022\006\b\000\022\002H\0030\b*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020,\032\002H+2\036\020D\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\002H:0\007H\bø\001\000¢\006\002\020-\032\\\020F\032\016\022\004\022\002H\002\022\004\022\002H:0\001\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020:*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\036\020D\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\002H:0\007H\bø\001\000\032w\020G\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\004\b\002\020:\"\030\b\003\020+*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H:0\b*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020,\032\002H+2\036\020D\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\033\022\004\022\002H:0\007H\bø\001\000¢\006\002\020-\032@\020H\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020!\032\002H\002H\002¢\006\002\020I\032H\020H\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\016\020J\032\n\022\006\b\001\022\002H\0020\020H\002¢\006\002\020K\032A\020H\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\f\020J\032\b\022\004\022\002H\0020LH\002\032A\020H\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\f\020J\032\b\022\004\022\002H\0020MH\002\0322\020N\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\b2\006\020!\032\002H\002H\n¢\006\002\020O\032:\020N\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\b2\016\020J\032\n\022\006\b\001\022\002H\0020\020H\n¢\006\002\020P\0323\020N\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\b2\f\020J\032\b\022\004\022\002H\0020LH\n\0323\020N\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\b2\f\020J\032\b\022\004\022\002H\0020MH\n\0320\020Q\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\001H\000\0323\020R\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\004\022\002H\002\022\004\022\002H\003\030\0010\001H\b\032T\020S\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\032\020\017\032\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020H\002¢\006\002\020T\032G\020S\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\022\020U\032\016\022\004\022\002H\002\022\004\022\002H\0030\021H\002\032M\020S\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\030\020\017\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210LH\002\032I\020S\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\024\020V\032\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\001H\002\032M\020S\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\030\020\017\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210MH\002\032J\020W\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\032\020\017\032\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020H\n¢\006\002\020X\032=\020W\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\022\020U\032\016\022\004\022\002H\002\022\004\022\002H\0030\021H\n\032C\020W\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\030\020\017\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210LH\n\032=\020W\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\022\020V\032\016\022\004\022\002H\002\022\004\022\002H\0030\001H\n\032C\020W\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\030\020\017\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210MH\n\032G\020Y\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\032\020\017\032\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020¢\006\002\020X\032@\020Y\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\030\020\017\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210L\032@\020Y\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b2\030\020\017\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210M\032;\020Z\032\004\030\001H\003\"\t\b\000\020\002¢\006\002\b \"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\b2\006\020!\032\002H\002H\b¢\006\002\0201\032:\020[\032\0020\t\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\b2\006\020!\032\002H\0022\006\020%\032\002H\003H\n¢\006\002\020\\\032;\020]\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\020¢\006\002\020\030\032Q\020]\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\030\b\002\020+*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b*\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210\0202\006\020,\032\002H+¢\006\002\020^\0324\020]\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210L\032O\020]\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\030\b\002\020+*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b*\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210L2\006\020,\032\002H+¢\006\002\020_\0322\020]\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\001H\007\032M\020]\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\030\b\002\020+*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\0012\006\020,\032\002H+H\007¢\006\002\020`\0324\020]\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\004\b\000\020\002\"\004\b\001\020\003*\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210M\032O\020]\032\002H+\"\004\b\000\020\002\"\004\b\001\020\003\"\030\b\002\020+*\022\022\006\b\000\022\002H\002\022\006\b\000\022\002H\0030\b*\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0030\0210M2\006\020,\032\002H+¢\006\002\020a\0322\020b\032\016\022\004\022\002H\002\022\004\022\002H\0030\b\"\004\b\000\020\002\"\004\b\001\020\003*\020\022\006\b\001\022\002H\002\022\004\022\002H\0030\001H\007\0321\020c\032\016\022\004\022\002H\002\022\004\022\002H\0030\021\"\004\b\000\020\002\"\004\b\001\020\003*\016\022\004\022\002H\002\022\004\022\002H\0030\033H\b\002\007\n\005\b20\001¨\006d"}, d2={"buildMap", "", "K", "V", "capacity", "", "builderAction", "Lkotlin/Function1;", "", "", "Lkotlin/ExtensionFunctionType;", "emptyMap", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", "value", "filter", "predicate", "filterKeys", "filterNot", "filterNotTo", "M", "destination", "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", "get", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", "set", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/MapsKt")
class MapsKt__MapsKt
  extends MapsKt__MapsJVMKt
{
  private static final <K, V> Map<K, V> buildMap(int paramInt, Function1<? super Map<K, V>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Map localMap = MapsKt.createMapBuilder(paramInt);
    paramFunction1.invoke(localMap);
    return MapsKt.build(localMap);
  }
  
  private static final <K, V> Map<K, V> buildMap(Function1<? super Map<K, V>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Map localMap = MapsKt.createMapBuilder();
    paramFunction1.invoke(localMap);
    return MapsKt.build(localMap);
  }
  
  private static final <K, V> K component1(Map.Entry<? extends K, ? extends V> paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "<this>");
    return (K)paramEntry.getKey();
  }
  
  private static final <K, V> V component2(Map.Entry<? extends K, ? extends V> paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "<this>");
    return (V)paramEntry.getValue();
  }
  
  private static final <K, V> boolean contains(Map<? extends K, ? extends V> paramMap, K paramK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.containsKey(paramK);
  }
  
  private static final <K> boolean containsKey(Map<? extends K, ?> paramMap, K paramK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.containsKey(paramK);
  }
  
  private static final <K, V> boolean containsValue(Map<K, ? extends V> paramMap, V paramV)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.containsValue(paramV);
  }
  
  public static final <K, V> Map<K, V> emptyMap()
  {
    EmptyMap localEmptyMap = EmptyMap.INSTANCE;
    Intrinsics.checkNotNull(localEmptyMap, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.emptyMap, V of kotlin.collections.MapsKt__MapsKt.emptyMap>");
    return (Map)localEmptyMap;
  }
  
  public static final <K, V> Map<K, V> filter(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Map localMap = (Map)new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (((Boolean)paramFunction1.invoke(localEntry)).booleanValue()) {
        localMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return localMap;
  }
  
  public static final <K, V> Map<K, V> filterKeys(Map<? extends K, ? extends V> paramMap, Function1<? super K, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (((Boolean)paramFunction1.invoke(localEntry.getKey())).booleanValue()) {
        localLinkedHashMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return (Map)localLinkedHashMap;
  }
  
  public static final <K, V> Map<K, V> filterNot(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Map localMap = (Map)new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (!((Boolean)paramFunction1.invoke(localEntry)).booleanValue()) {
        localMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return localMap;
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M filterNotTo(Map<? extends K, ? extends V> paramMap, M paramM, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      paramMap = (Map.Entry)localIterator.next();
      if (!((Boolean)paramFunction1.invoke(paramMap)).booleanValue()) {
        paramM.put(paramMap.getKey(), paramMap.getValue());
      }
    }
    return paramM;
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M filterTo(Map<? extends K, ? extends V> paramMap, M paramM, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (((Boolean)paramFunction1.invoke(localEntry)).booleanValue()) {
        paramM.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return paramM;
  }
  
  public static final <K, V> Map<K, V> filterValues(Map<? extends K, ? extends V> paramMap, Function1<? super V, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (((Boolean)paramFunction1.invoke(localEntry.getValue())).booleanValue()) {
        localLinkedHashMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return (Map)localLinkedHashMap;
  }
  
  private static final <K, V> V get(Map<? extends K, ? extends V> paramMap, K paramK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return (V)paramMap.get(paramK);
  }
  
  private static final <K, V> V getOrElse(Map<K, ? extends V> paramMap, K paramK, Function0<? extends V> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    paramK = paramMap.get(paramK);
    paramMap = paramK;
    if (paramK == null) {
      paramMap = paramFunction0.invoke();
    }
    return paramMap;
  }
  
  public static final <K, V> V getOrElseNullable(Map<K, ? extends V> paramMap, K paramK, Function0<? extends V> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    Object localObject = paramMap.get(paramK);
    if ((localObject == null) && (!paramMap.containsKey(paramK))) {
      return (V)paramFunction0.invoke();
    }
    return (V)localObject;
  }
  
  public static final <K, V> V getOrPut(Map<K, V> paramMap, K paramK, Function0<? extends V> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    Object localObject = paramMap.get(paramK);
    if (localObject == null)
    {
      paramFunction0 = paramFunction0.invoke();
      paramMap.put(paramK, paramFunction0);
      paramMap = paramFunction0;
    }
    else
    {
      paramMap = (Map<K, V>)localObject;
    }
    return paramMap;
  }
  
  public static final <K, V> V getValue(Map<K, ? extends V> paramMap, K paramK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return (V)MapsKt.getOrImplicitDefaultNullable(paramMap, paramK);
  }
  
  private static final <K, V> HashMap<K, V> hashMapOf()
  {
    return new HashMap();
  }
  
  public static final <K, V> HashMap<K, V> hashMapOf(Pair<? extends K, ? extends V>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    HashMap localHashMap = new HashMap(MapsKt.mapCapacity(paramVarArgs.length));
    MapsKt.putAll((Map)localHashMap, paramVarArgs);
    return localHashMap;
  }
  
  private static final <M extends Map<?, ?>,  extends R, R> R ifEmpty(M paramM, Function0<? extends R> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    if (paramM.isEmpty()) {
      paramM = paramFunction0.invoke();
    }
    return paramM;
  }
  
  private static final <K, V> boolean isNotEmpty(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.isEmpty() ^ true;
  }
  
  private static final <K, V> boolean isNullOrEmpty(Map<? extends K, ? extends V> paramMap)
  {
    boolean bool;
    if ((paramMap != null) && (!paramMap.isEmpty())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static final <K, V> Iterator<Map.Entry<K, V>> iterator(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.entrySet().iterator();
  }
  
  private static final <K, V> LinkedHashMap<K, V> linkedMapOf()
  {
    return new LinkedHashMap();
  }
  
  public static final <K, V> LinkedHashMap<K, V> linkedMapOf(Pair<? extends K, ? extends V>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    return (LinkedHashMap)MapsKt.toMap(paramVarArgs, (Map)new LinkedHashMap(MapsKt.mapCapacity(paramVarArgs.length)));
  }
  
  public static final <K, V, R> Map<R, V> mapKeys(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Map localMap = (Map)new LinkedHashMap(MapsKt.mapCapacity(paramMap.size()));
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    while (paramMap.hasNext())
    {
      Object localObject = paramMap.next();
      localMap.put(paramFunction1.invoke(localObject), ((Map.Entry)localObject).getValue());
    }
    return localMap;
  }
  
  public static final <K, V, R, M extends Map<? super R, ? super V>> M mapKeysTo(Map<? extends K, ? extends V> paramMap, M paramM, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    while (localIterator.hasNext())
    {
      paramMap = localIterator.next();
      paramM.put(paramFunction1.invoke(paramMap), ((Map.Entry)paramMap).getValue());
    }
    return paramM;
  }
  
  private static final <K, V> Map<K, V> mapOf()
  {
    return MapsKt.emptyMap();
  }
  
  public static final <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    if (paramVarArgs.length > 0) {
      paramVarArgs = MapsKt.toMap(paramVarArgs, (Map)new LinkedHashMap(MapsKt.mapCapacity(paramVarArgs.length)));
    } else {
      paramVarArgs = MapsKt.emptyMap();
    }
    return paramVarArgs;
  }
  
  public static final <K, V, R> Map<K, R> mapValues(Map<? extends K, ? extends V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Map localMap = (Map)new LinkedHashMap(MapsKt.mapCapacity(paramMap.size()));
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    while (localIterator.hasNext())
    {
      paramMap = localIterator.next();
      localMap.put(((Map.Entry)paramMap).getKey(), paramFunction1.invoke(paramMap));
    }
    return localMap;
  }
  
  public static final <K, V, R, M extends Map<? super K, ? super R>> M mapValuesTo(Map<? extends K, ? extends V> paramMap, M paramM, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    Intrinsics.checkNotNullParameter(paramFunction1, "transform");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    while (localIterator.hasNext())
    {
      paramMap = localIterator.next();
      paramM.put(((Map.Entry)paramMap).getKey(), paramFunction1.invoke(paramMap));
    }
    return paramM;
  }
  
  public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> paramMap, Iterable<? extends K> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "keys");
    paramMap = MapsKt.toMutableMap(paramMap);
    CollectionsKt.removeAll((Collection)paramMap.keySet(), paramIterable);
    return MapsKt.optimizeReadOnlyMap(paramMap);
  }
  
  public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> paramMap, K paramK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    paramMap = MapsKt.toMutableMap(paramMap);
    paramMap.remove(paramK);
    return MapsKt.optimizeReadOnlyMap(paramMap);
  }
  
  public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> paramMap, Sequence<? extends K> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "keys");
    paramMap = MapsKt.toMutableMap(paramMap);
    CollectionsKt.removeAll((Collection)paramMap.keySet(), paramSequence);
    return MapsKt.optimizeReadOnlyMap(paramMap);
  }
  
  public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> paramMap, K[] paramArrayOfK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfK, "keys");
    paramMap = MapsKt.toMutableMap(paramMap);
    CollectionsKt.removeAll((Collection)paramMap.keySet(), paramArrayOfK);
    return MapsKt.optimizeReadOnlyMap(paramMap);
  }
  
  private static final <K, V> void minusAssign(Map<K, V> paramMap, Iterable<? extends K> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "keys");
    CollectionsKt.removeAll((Collection)paramMap.keySet(), paramIterable);
  }
  
  private static final <K, V> void minusAssign(Map<K, V> paramMap, K paramK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    paramMap.remove(paramK);
  }
  
  private static final <K, V> void minusAssign(Map<K, V> paramMap, Sequence<? extends K> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "keys");
    CollectionsKt.removeAll((Collection)paramMap.keySet(), paramSequence);
  }
  
  private static final <K, V> void minusAssign(Map<K, V> paramMap, K[] paramArrayOfK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfK, "keys");
    CollectionsKt.removeAll((Collection)paramMap.keySet(), paramArrayOfK);
  }
  
  private static final <K, V> Iterator<Map.Entry<K, V>> mutableIterator(Map<K, V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return paramMap.entrySet().iterator();
  }
  
  private static final <K, V> Map<K, V> mutableMapOf()
  {
    return (Map)new LinkedHashMap();
  }
  
  public static final <K, V> Map<K, V> mutableMapOf(Pair<? extends K, ? extends V>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(paramVarArgs.length));
    MapsKt.putAll((Map)localLinkedHashMap, paramVarArgs);
    return (Map)localLinkedHashMap;
  }
  
  public static final <K, V> Map<K, V> optimizeReadOnlyMap(Map<K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    switch (paramMap.size())
    {
    default: 
      break;
    case 1: 
      paramMap = MapsKt.toSingletonMap(paramMap);
      break;
    case 0: 
      paramMap = MapsKt.emptyMap();
    }
    return paramMap;
  }
  
  private static final <K, V> Map<K, V> orEmpty(Map<K, ? extends V> paramMap)
  {
    if (paramMap == null) {
      paramMap = MapsKt.emptyMap();
    }
    return paramMap;
  }
  
  public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> paramMap, Iterable<? extends Pair<? extends K, ? extends V>> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "pairs");
    if (paramMap.isEmpty())
    {
      paramMap = MapsKt.toMap(paramIterable);
    }
    else
    {
      paramMap = new LinkedHashMap(paramMap);
      MapsKt.putAll((Map)paramMap, paramIterable);
      paramMap = (Map)paramMap;
    }
    return paramMap;
  }
  
  public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> paramMap1, Map<? extends K, ? extends V> paramMap2)
  {
    Intrinsics.checkNotNullParameter(paramMap1, "<this>");
    Intrinsics.checkNotNullParameter(paramMap2, "map");
    paramMap1 = new LinkedHashMap(paramMap1);
    paramMap1.putAll(paramMap2);
    return (Map)paramMap1;
  }
  
  public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> paramMap, Pair<? extends K, ? extends V> paramPair)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramPair, "pair");
    if (paramMap.isEmpty())
    {
      paramMap = MapsKt.mapOf(paramPair);
    }
    else
    {
      paramMap = new LinkedHashMap(paramMap);
      paramMap.put(paramPair.getFirst(), paramPair.getSecond());
      paramMap = (Map)paramMap;
    }
    return paramMap;
  }
  
  public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> paramMap, Sequence<? extends Pair<? extends K, ? extends V>> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "pairs");
    paramMap = new LinkedHashMap(paramMap);
    MapsKt.putAll((Map)paramMap, paramSequence);
    return MapsKt.optimizeReadOnlyMap((Map)paramMap);
  }
  
  public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> paramMap, Pair<? extends K, ? extends V>[] paramArrayOfPair)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfPair, "pairs");
    if (paramMap.isEmpty())
    {
      paramMap = MapsKt.toMap(paramArrayOfPair);
    }
    else
    {
      paramMap = new LinkedHashMap(paramMap);
      MapsKt.putAll((Map)paramMap, paramArrayOfPair);
      paramMap = (Map)paramMap;
    }
    return paramMap;
  }
  
  private static final <K, V> void plusAssign(Map<? super K, ? super V> paramMap, Iterable<? extends Pair<? extends K, ? extends V>> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "pairs");
    MapsKt.putAll(paramMap, paramIterable);
  }
  
  private static final <K, V> void plusAssign(Map<? super K, ? super V> paramMap, Map<K, ? extends V> paramMap1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramMap1, "map");
    paramMap.putAll(paramMap1);
  }
  
  private static final <K, V> void plusAssign(Map<? super K, ? super V> paramMap, Pair<? extends K, ? extends V> paramPair)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramPair, "pair");
    paramMap.put(paramPair.getFirst(), paramPair.getSecond());
  }
  
  private static final <K, V> void plusAssign(Map<? super K, ? super V> paramMap, Sequence<? extends Pair<? extends K, ? extends V>> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "pairs");
    MapsKt.putAll(paramMap, paramSequence);
  }
  
  private static final <K, V> void plusAssign(Map<? super K, ? super V> paramMap, Pair<? extends K, ? extends V>[] paramArrayOfPair)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfPair, "pairs");
    MapsKt.putAll(paramMap, paramArrayOfPair);
  }
  
  public static final <K, V> void putAll(Map<? super K, ? super V> paramMap, Iterable<? extends Pair<? extends K, ? extends V>> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramIterable, "pairs");
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      Pair localPair = (Pair)paramIterable.next();
      paramMap.put(localPair.component1(), localPair.component2());
    }
  }
  
  public static final <K, V> void putAll(Map<? super K, ? super V> paramMap, Sequence<? extends Pair<? extends K, ? extends V>> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramSequence, "pairs");
    Iterator localIterator = paramSequence.iterator();
    while (localIterator.hasNext())
    {
      paramSequence = (Pair)localIterator.next();
      paramMap.put(paramSequence.component1(), paramSequence.component2());
    }
  }
  
  public static final <K, V> void putAll(Map<? super K, ? super V> paramMap, Pair<? extends K, ? extends V>[] paramArrayOfPair)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfPair, "pairs");
    int j = paramArrayOfPair.length;
    for (int i = 0; i < j; i++)
    {
      Pair<? extends K, ? extends V> localPair = paramArrayOfPair[i];
      paramMap.put(localPair.component1(), localPair.component2());
    }
  }
  
  private static final <K, V> V remove(Map<? extends K, V> paramMap, K paramK)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return (V)TypeIntrinsics.asMutableMap(paramMap).remove(paramK);
  }
  
  private static final <K, V> void set(Map<K, V> paramMap, K paramK, V paramV)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    paramMap.put(paramK, paramV);
  }
  
  public static final <K, V> Map<K, V> toMap(Iterable<? extends Pair<? extends K, ? extends V>> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    if ((paramIterable instanceof Collection))
    {
      switch (((Collection)paramIterable).size())
      {
      default: 
        paramIterable = MapsKt.toMap(paramIterable, (Map)new LinkedHashMap(MapsKt.mapCapacity(((Collection)paramIterable).size())));
        break;
      case 1: 
        if ((paramIterable instanceof List)) {
          paramIterable = ((List)paramIterable).get(0);
        } else {
          paramIterable = paramIterable.iterator().next();
        }
        paramIterable = MapsKt.mapOf((Pair)paramIterable);
        break;
      case 0: 
        paramIterable = MapsKt.emptyMap();
      }
      return paramIterable;
    }
    return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(paramIterable, (Map)new LinkedHashMap()));
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Iterable<? extends Pair<? extends K, ? extends V>> paramIterable, M paramM)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    MapsKt.putAll(paramM, paramIterable);
    return paramM;
  }
  
  public static final <K, V> Map<K, V> toMap(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    switch (paramMap.size())
    {
    default: 
      paramMap = MapsKt.toMutableMap(paramMap);
      break;
    case 1: 
      paramMap = MapsKt.toSingletonMap(paramMap);
      break;
    case 0: 
      paramMap = MapsKt.emptyMap();
    }
    return paramMap;
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Map<? extends K, ? extends V> paramMap, M paramM)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    paramM.putAll(paramMap);
    return paramM;
  }
  
  public static final <K, V> Map<K, V> toMap(Sequence<? extends Pair<? extends K, ? extends V>> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(paramSequence, (Map)new LinkedHashMap()));
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Sequence<? extends Pair<? extends K, ? extends V>> paramSequence, M paramM)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    MapsKt.putAll(paramM, paramSequence);
    return paramM;
  }
  
  public static final <K, V> Map<K, V> toMap(Pair<? extends K, ? extends V>[] paramArrayOfPair)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfPair, "<this>");
    switch (paramArrayOfPair.length)
    {
    default: 
      paramArrayOfPair = MapsKt.toMap(paramArrayOfPair, (Map)new LinkedHashMap(MapsKt.mapCapacity(paramArrayOfPair.length)));
      break;
    case 1: 
      paramArrayOfPair = MapsKt.mapOf(paramArrayOfPair[0]);
      break;
    case 0: 
      paramArrayOfPair = MapsKt.emptyMap();
    }
    return paramArrayOfPair;
  }
  
  public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Pair<? extends K, ? extends V>[] paramArrayOfPair, M paramM)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfPair, "<this>");
    Intrinsics.checkNotNullParameter(paramM, "destination");
    MapsKt.putAll(paramM, paramArrayOfPair);
    return paramM;
  }
  
  public static final <K, V> Map<K, V> toMutableMap(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return (Map)new LinkedHashMap(paramMap);
  }
  
  private static final <K, V> Pair<K, V> toPair(Map.Entry<? extends K, ? extends V> paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "<this>");
    return new Pair(paramEntry.getKey(), paramEntry.getValue());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/MapsKt__MapsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */