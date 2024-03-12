package kotlin.streams.jdk8;

import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;
import java.util.PrimitiveIterator.OfInt;
import java.util.PrimitiveIterator.OfLong;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(d1={"\000.\n\000\n\002\030\002\n\002\020\006\n\002\030\002\n\002\020\b\n\002\030\002\n\002\020\t\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\000\032\022\020\000\032\b\022\004\022\0020\0020\001*\0020\003H\007\032\022\020\000\032\b\022\004\022\0020\0040\001*\0020\005H\007\032\022\020\000\032\b\022\004\022\0020\0060\001*\0020\007H\007\032\036\020\000\032\b\022\004\022\002H\b0\001\"\004\b\000\020\b*\b\022\004\022\002H\b0\tH\007\032\036\020\n\032\b\022\004\022\002H\b0\t\"\004\b\000\020\b*\b\022\004\022\002H\b0\001H\007\032\022\020\013\032\b\022\004\022\0020\0020\f*\0020\003H\007\032\022\020\013\032\b\022\004\022\0020\0040\f*\0020\005H\007\032\022\020\013\032\b\022\004\022\0020\0060\f*\0020\007H\007\032\036\020\013\032\b\022\004\022\002H\b0\f\"\004\b\000\020\b*\b\022\004\022\002H\b0\tH\007¨\006\r"}, d2={"asSequence", "Lkotlin/sequences/Sequence;", "", "Ljava/util/stream/DoubleStream;", "", "Ljava/util/stream/IntStream;", "", "Ljava/util/stream/LongStream;", "T", "Ljava/util/stream/Stream;", "asStream", "toList", "", "kotlin-stdlib-jdk8"}, k=2, mv={1, 6, 0}, pn="kotlin.streams", xi=48)
public final class StreamsKt
{
  public static final Sequence<Double> asSequence(DoubleStream paramDoubleStream)
  {
    Intrinsics.checkNotNullParameter(paramDoubleStream, "<this>");
    (Sequence)new Sequence()
    {
      final DoubleStream $this_asSequence$inlined;
      
      public Iterator<Double> iterator()
      {
        PrimitiveIterator.OfDouble localOfDouble = this.$this_asSequence$inlined.iterator();
        Intrinsics.checkNotNullExpressionValue(localOfDouble, "iterator()");
        return (Iterator)localOfDouble;
      }
    };
  }
  
  public static final Sequence<Integer> asSequence(IntStream paramIntStream)
  {
    Intrinsics.checkNotNullParameter(paramIntStream, "<this>");
    (Sequence)new Sequence()
    {
      final IntStream $this_asSequence$inlined;
      
      public Iterator<Integer> iterator()
      {
        PrimitiveIterator.OfInt localOfInt = this.$this_asSequence$inlined.iterator();
        Intrinsics.checkNotNullExpressionValue(localOfInt, "iterator()");
        return (Iterator)localOfInt;
      }
    };
  }
  
  public static final Sequence<Long> asSequence(LongStream paramLongStream)
  {
    Intrinsics.checkNotNullParameter(paramLongStream, "<this>");
    (Sequence)new Sequence()
    {
      final LongStream $this_asSequence$inlined;
      
      public Iterator<Long> iterator()
      {
        PrimitiveIterator.OfLong localOfLong = this.$this_asSequence$inlined.iterator();
        Intrinsics.checkNotNullExpressionValue(localOfLong, "iterator()");
        return (Iterator)localOfLong;
      }
    };
  }
  
  public static final <T> Sequence<T> asSequence(Stream<T> paramStream)
  {
    Intrinsics.checkNotNullParameter(paramStream, "<this>");
    (Sequence)new Sequence()
    {
      final Stream $this_asSequence$inlined;
      
      public Iterator<T> iterator()
      {
        Iterator localIterator = this.$this_asSequence$inlined.iterator();
        Intrinsics.checkNotNullExpressionValue(localIterator, "iterator()");
        return localIterator;
      }
    };
  }
  
  public static final <T> Stream<T> asStream(Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    paramSequence = StreamSupport.stream(new StreamsKt..ExternalSyntheticLambda0(paramSequence), 16, false);
    Intrinsics.checkNotNullExpressionValue(paramSequence, "stream({ Spliterators.sp…literator.ORDERED, false)");
    return paramSequence;
  }
  
  private static final Spliterator asStream$lambda-4(Sequence paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "$this_asStream");
    return Spliterators.spliteratorUnknownSize(paramSequence.iterator(), 16);
  }
  
  public static final List<Double> toList(DoubleStream paramDoubleStream)
  {
    Intrinsics.checkNotNullParameter(paramDoubleStream, "<this>");
    paramDoubleStream = paramDoubleStream.toArray();
    Intrinsics.checkNotNullExpressionValue(paramDoubleStream, "toArray()");
    return ArraysKt.asList(paramDoubleStream);
  }
  
  public static final List<Integer> toList(IntStream paramIntStream)
  {
    Intrinsics.checkNotNullParameter(paramIntStream, "<this>");
    paramIntStream = paramIntStream.toArray();
    Intrinsics.checkNotNullExpressionValue(paramIntStream, "toArray()");
    return ArraysKt.asList(paramIntStream);
  }
  
  public static final List<Long> toList(LongStream paramLongStream)
  {
    Intrinsics.checkNotNullParameter(paramLongStream, "<this>");
    paramLongStream = paramLongStream.toArray();
    Intrinsics.checkNotNullExpressionValue(paramLongStream, "toArray()");
    return ArraysKt.asList(paramLongStream);
  }
  
  public static final <T> List<T> toList(Stream<T> paramStream)
  {
    Intrinsics.checkNotNullParameter(paramStream, "<this>");
    paramStream = paramStream.collect(Collectors.toList());
    Intrinsics.checkNotNullExpressionValue(paramStream, "collect(Collectors.toList<T>())");
    return (List)paramStream;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/streams/jdk8/StreamsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */