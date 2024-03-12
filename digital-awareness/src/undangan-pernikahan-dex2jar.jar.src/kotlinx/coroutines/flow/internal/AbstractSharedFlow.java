package kotlinx.coroutines.flow.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

@Metadata(d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\004\n\002\020\021\n\002\b\006\n\002\030\002\n\002\b\t\n\002\020\002\n\000\n\002\030\002\n\002\b\004\b \030\000*\f\b\000\020\001*\006\022\002\b\0030\0022\0060\003j\002`\004B\005¢\006\002\020\005J\r\020\030\032\0028\000H\004¢\006\002\020\031J\r\020\032\032\0028\000H$¢\006\002\020\031J\035\020\033\032\n\022\006\022\004\030\0018\0000\0162\006\020\034\032\0020\tH$¢\006\002\020\035J\035\020\036\032\0020\0372\022\020 \032\016\022\004\022\0028\000\022\004\022\0020\0370!H\bJ\025\020\"\032\0020\0372\006\020#\032\0028\000H\004¢\006\002\020$R\020\020\006\032\004\030\0010\007X\016¢\006\002\n\000R\036\020\n\032\0020\t2\006\020\b\032\0020\t@BX\016¢\006\b\n\000\032\004\b\013\020\fR\016\020\r\032\0020\tX\016¢\006\002\n\000R:\020\017\032\f\022\006\022\004\030\0018\000\030\0010\0162\020\020\b\032\f\022\006\022\004\030\0018\000\030\0010\016@BX\016¢\006\020\n\002\020\023\022\004\b\020\020\005\032\004\b\021\020\022R\027\020\024\032\b\022\004\022\0020\t0\0258F¢\006\006\032\004\b\026\020\027¨\006%"}, d2={"Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "S", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "()V", "_subscriptionCount", "Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "<set-?>", "", "nCollectors", "getNCollectors", "()I", "nextIndex", "", "slots", "getSlots$annotations", "getSlots", "()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "allocateSlot", "()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "forEachSlotLocked", "", "block", "Lkotlin/Function1;", "freeSlot", "slot", "(Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AbstractSharedFlow<S extends AbstractSharedFlowSlot<?>>
{
  private SubscriptionCountStateFlow _subscriptionCount;
  private int nCollectors;
  private int nextIndex;
  private S[] slots;
  
  protected final S allocateSlot()
  {
    try
    {
      Object localObject1 = getSlots();
      if (localObject1 == null)
      {
        localObject1 = createSlotArray(2);
        this.slots = ((AbstractSharedFlowSlot[])localObject1);
      }
      else if (getNCollectors() >= localObject1.length)
      {
        localObject1 = Arrays.copyOf((Object[])localObject1, localObject1.length * 2);
        Intrinsics.checkNotNullExpressionValue(localObject1, "copyOf(this, newSize)");
        this.slots = ((AbstractSharedFlowSlot[])localObject1);
        localObject1 = (AbstractSharedFlowSlot[])localObject1;
      }
      int j = this.nextIndex;
      Object localObject3;
      int i;
      do
      {
        Object localObject4 = localObject1[j];
        localObject3 = localObject4;
        if (localObject4 == null)
        {
          localObject3 = createSlot();
          localObject1[j] = localObject3;
        }
        j++;
        i = j;
        if (j >= localObject1.length) {
          i = 0;
        }
        j = i;
      } while (!((AbstractSharedFlowSlot)localObject3).allocateLocked(this));
      this.nextIndex = i;
      this.nCollectors = (getNCollectors() + 1);
      localObject1 = this._subscriptionCount;
      if (localObject1 != null) {
        ((SubscriptionCountStateFlow)localObject1).increment(1);
      }
      return (S)localObject3;
    }
    finally {}
  }
  
  protected abstract S createSlot();
  
  protected abstract S[] createSlotArray(int paramInt);
  
  protected final void forEachSlotLocked(Function1<? super S, Unit> paramFunction1)
  {
    if (access$getNCollectors(this) == 0) {
      return;
    }
    AbstractSharedFlowSlot[] arrayOfAbstractSharedFlowSlot = access$getSlots(this);
    if (arrayOfAbstractSharedFlowSlot != null)
    {
      int i = 0;
      int j = arrayOfAbstractSharedFlowSlot.length;
      while (i < j)
      {
        AbstractSharedFlowSlot localAbstractSharedFlowSlot = arrayOfAbstractSharedFlowSlot[i];
        i++;
        if (localAbstractSharedFlowSlot != null) {
          paramFunction1.invoke(localAbstractSharedFlowSlot);
        }
      }
    }
  }
  
  protected final void freeSlot(S paramS)
  {
    try
    {
      this.nCollectors = (getNCollectors() - 1);
      SubscriptionCountStateFlow localSubscriptionCountStateFlow = this._subscriptionCount;
      int j = getNCollectors();
      int i = 0;
      if (j == 0) {
        this.nextIndex = 0;
      }
      paramS = paramS.freeLocked(this);
      j = paramS.length;
      while (i < j)
      {
        Object localObject = paramS[i];
        i++;
        if (localObject != null)
        {
          Result.Companion localCompanion = Result.Companion;
          ((Continuation)localObject).resumeWith(Result.constructor-impl(Unit.INSTANCE));
        }
      }
      if (localSubscriptionCountStateFlow != null) {
        localSubscriptionCountStateFlow.increment(-1);
      }
      return;
    }
    finally {}
  }
  
  protected final int getNCollectors()
  {
    return this.nCollectors;
  }
  
  protected final S[] getSlots()
  {
    return this.slots;
  }
  
  public final StateFlow<Integer> getSubscriptionCount()
  {
    try
    {
      SubscriptionCountStateFlow localSubscriptionCountStateFlow2 = this._subscriptionCount;
      SubscriptionCountStateFlow localSubscriptionCountStateFlow1 = localSubscriptionCountStateFlow2;
      if (localSubscriptionCountStateFlow2 == null)
      {
        localSubscriptionCountStateFlow1 = new kotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;
        localSubscriptionCountStateFlow1.<init>(getNCollectors());
        this._subscriptionCount = localSubscriptionCountStateFlow1;
      }
      return (StateFlow)localSubscriptionCountStateFlow1;
    }
    finally {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/AbstractSharedFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */