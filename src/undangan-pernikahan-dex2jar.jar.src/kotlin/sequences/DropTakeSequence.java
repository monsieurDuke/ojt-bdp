package kotlin.sequences;

import kotlin.Metadata;

@Metadata(d1={"\000\026\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\b`\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002J\026\020\003\032\b\022\004\022\0028\0000\0022\006\020\004\032\0020\005H&J\026\020\006\032\b\022\004\022\0028\0000\0022\006\020\004\032\0020\005H&¨\006\007"}, d2={"Lkotlin/sequences/DropTakeSequence;", "T", "Lkotlin/sequences/Sequence;", "drop", "n", "", "take", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface DropTakeSequence<T>
  extends Sequence<T>
{
  public abstract Sequence<T> drop(int paramInt);
  
  public abstract Sequence<T> take(int paramInt);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/DropTakeSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */