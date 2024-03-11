package kotlin.io;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;

@Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\002\020\016\n\000\n\002\030\002\n\002\b\002\n\002\020(\n\000\b\002\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\017\020\006\032\b\022\004\022\0020\0020\007H\002R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\b"}, d2={"Lkotlin/io/LinesSequence;", "Lkotlin/sequences/Sequence;", "", "reader", "Ljava/io/BufferedReader;", "(Ljava/io/BufferedReader;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class LinesSequence
  implements Sequence<String>
{
  private final BufferedReader reader;
  
  public LinesSequence(BufferedReader paramBufferedReader)
  {
    this.reader = paramBufferedReader;
  }
  
  public Iterator<String> iterator()
  {
    (Iterator)new Iterator()
    {
      private boolean done;
      private String nextValue;
      final LinesSequence this$0;
      
      public boolean hasNext()
      {
        String str = this.nextValue;
        boolean bool = true;
        if ((str == null) && (!this.done))
        {
          str = LinesSequence.access$getReader$p(this.this$0).readLine();
          this.nextValue = str;
          if (str == null) {
            this.done = true;
          }
        }
        if (this.nextValue == null) {
          bool = false;
        }
        return bool;
      }
      
      public String next()
      {
        if (hasNext())
        {
          String str = this.nextValue;
          this.nextValue = null;
          Intrinsics.checkNotNull(str);
          return str;
        }
        throw new NoSuchElementException();
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/LinesSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */