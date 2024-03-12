package kotlin.collections;

import kotlin.Metadata;

@Metadata(d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\006\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004j\002\b\005j\002\b\006¨\006\007"}, d2={"Lkotlin/collections/State;", "", "(Ljava/lang/String;I)V", "Ready", "NotReady", "Done", "Failed", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
 enum State
{
  private static final State[] $VALUES = $values();
  
  static
  {
    NotReady = new State("NotReady", 1);
    Done = new State("Done", 2);
    Failed = new State("Failed", 3);
  }
  
  private State() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/State.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */