package kotlin.sequences;

import java.util.Enumeration;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032\037\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\b¨\006\004"}, d2={"asSequence", "Lkotlin/sequences/Sequence;", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/sequences/SequencesKt")
class SequencesKt__SequencesJVMKt
  extends SequencesKt__SequenceBuilderKt
{
  private static final <T> Sequence<T> asSequence(Enumeration<T> paramEnumeration)
  {
    Intrinsics.checkNotNullParameter(paramEnumeration, "<this>");
    return SequencesKt.asSequence(CollectionsKt.iterator(paramEnumeration));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/SequencesKt__SequencesJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */