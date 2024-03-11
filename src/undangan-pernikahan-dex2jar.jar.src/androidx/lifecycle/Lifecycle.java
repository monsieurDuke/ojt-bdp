package androidx.lifecycle;

import java.util.concurrent.atomic.AtomicReference;

public abstract class Lifecycle
{
  AtomicReference<Object> mInternalScopeRef = new AtomicReference();
  
  public abstract void addObserver(LifecycleObserver paramLifecycleObserver);
  
  public abstract State getCurrentState();
  
  public abstract void removeObserver(LifecycleObserver paramLifecycleObserver);
  
  public static enum Event
  {
    private static final Event[] $VALUES;
    
    static
    {
      Event localEvent7 = new Event("ON_CREATE", 0);
      ON_CREATE = localEvent7;
      Event localEvent4 = new Event("ON_START", 1);
      ON_START = localEvent4;
      Event localEvent1 = new Event("ON_RESUME", 2);
      ON_RESUME = localEvent1;
      Event localEvent3 = new Event("ON_PAUSE", 3);
      ON_PAUSE = localEvent3;
      Event localEvent5 = new Event("ON_STOP", 4);
      ON_STOP = localEvent5;
      Event localEvent2 = new Event("ON_DESTROY", 5);
      ON_DESTROY = localEvent2;
      Event localEvent6 = new Event("ON_ANY", 6);
      ON_ANY = localEvent6;
      $VALUES = new Event[] { localEvent7, localEvent4, localEvent1, localEvent3, localEvent5, localEvent2, localEvent6 };
    }
    
    private Event() {}
    
    public static Event downFrom(Lifecycle.State paramState)
    {
      switch (Lifecycle.1.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()])
      {
      default: 
        return null;
      case 3: 
        return ON_PAUSE;
      case 2: 
        return ON_STOP;
      }
      return ON_DESTROY;
    }
    
    public static Event downTo(Lifecycle.State paramState)
    {
      switch (Lifecycle.1.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()])
      {
      case 3: 
      default: 
        return null;
      case 4: 
        return ON_DESTROY;
      case 2: 
        return ON_PAUSE;
      }
      return ON_STOP;
    }
    
    public static Event upFrom(Lifecycle.State paramState)
    {
      switch (Lifecycle.1.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()])
      {
      case 3: 
      case 4: 
      default: 
        return null;
      case 5: 
        return ON_CREATE;
      case 2: 
        return ON_RESUME;
      }
      return ON_START;
    }
    
    public static Event upTo(Lifecycle.State paramState)
    {
      switch (Lifecycle.1.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()])
      {
      default: 
        return null;
      case 3: 
        return ON_RESUME;
      case 2: 
        return ON_START;
      }
      return ON_CREATE;
    }
    
    public Lifecycle.State getTargetState()
    {
      switch (Lifecycle.1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[ordinal()])
      {
      default: 
        throw new IllegalArgumentException(this + " has no target state");
      case 6: 
        return Lifecycle.State.DESTROYED;
      case 5: 
        return Lifecycle.State.RESUMED;
      case 3: 
      case 4: 
        return Lifecycle.State.STARTED;
      }
      return Lifecycle.State.CREATED;
    }
  }
  
  public static enum State
  {
    private static final State[] $VALUES;
    
    static
    {
      State localState2 = new State("DESTROYED", 0);
      DESTROYED = localState2;
      State localState5 = new State("INITIALIZED", 1);
      INITIALIZED = localState5;
      State localState3 = new State("CREATED", 2);
      CREATED = localState3;
      State localState1 = new State("STARTED", 3);
      STARTED = localState1;
      State localState4 = new State("RESUMED", 4);
      RESUMED = localState4;
      $VALUES = new State[] { localState2, localState5, localState3, localState1, localState4 };
    }
    
    private State() {}
    
    public boolean isAtLeast(State paramState)
    {
      boolean bool;
      if (compareTo(paramState) >= 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/Lifecycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */