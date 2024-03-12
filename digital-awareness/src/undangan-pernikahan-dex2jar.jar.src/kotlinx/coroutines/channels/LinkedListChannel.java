package kotlinx.coroutines.channels;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000F\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\000\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\b\020\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B'\022 \020\003\032\034\022\004\022\0028\000\022\004\022\0020\005\030\0010\004j\n\022\004\022\0028\000\030\001`\006¢\006\002\020\007J\025\020\016\032\0020\0172\006\020\020\032\0028\000H\024¢\006\002\020\021J!\020\022\032\0020\0172\006\020\020\032\0028\0002\n\020\023\032\006\022\002\b\0030\024H\024¢\006\002\020\025J/\020\026\032\0020\0052\f\020\027\032\b\022\004\022\0020\0310\0302\n\020\032\032\006\022\002\b\0030\033H\024ø\001\000ø\001\001¢\006\004\b\034\020\035R\024\020\b\032\0020\t8DX\004¢\006\006\032\004\b\b\020\nR\024\020\013\032\0020\t8DX\004¢\006\006\032\004\b\013\020\nR\024\020\f\032\0020\t8DX\004¢\006\006\032\004\b\f\020\nR\024\020\r\032\0020\t8DX\004¢\006\006\032\004\b\r\020\n\002\013\n\002\b\031\n\005\b¡\0360\001¨\006\036"}, d2={"Lkotlinx/coroutines/channels/LinkedListChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class LinkedListChannel<E>
  extends AbstractChannel<E>
{
  public LinkedListChannel(Function1<? super E, Unit> paramFunction1)
  {
    super(paramFunction1);
  }
  
  protected final boolean isBufferAlwaysEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferAlwaysFull()
  {
    return false;
  }
  
  protected final boolean isBufferEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferFull()
  {
    return false;
  }
  
  protected Object offerInternal(E paramE)
  {
    Object localObject;
    do
    {
      localObject = super.offerInternal(paramE);
      if (localObject == AbstractChannelKt.OFFER_SUCCESS) {
        return AbstractChannelKt.OFFER_SUCCESS;
      }
      if (localObject != AbstractChannelKt.OFFER_FAILED) {
        break;
      }
      localObject = sendBuffered(paramE);
      if (localObject == null) {
        return AbstractChannelKt.OFFER_SUCCESS;
      }
    } while (!(localObject instanceof Closed));
    return localObject;
    if ((localObject instanceof Closed)) {
      return localObject;
    }
    paramE = Intrinsics.stringPlus("Invalid offerInternal result ", localObject);
    Log5ECF72.a(paramE);
    LogE84000.a(paramE);
    Log229316.a(paramE);
    throw new IllegalStateException(paramE.toString());
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    Object localObject1;
    do
    {
      if (getHasReceiveOrClosed())
      {
        localObject1 = super.offerSelectInternal(paramE, paramSelectInstance);
      }
      else
      {
        Object localObject2 = paramSelectInstance.performAtomicTrySelect((AtomicDesc)describeSendBuffered(paramE));
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = AbstractChannelKt.OFFER_SUCCESS;
        }
      }
      if (localObject1 == SelectKt.getALREADY_SELECTED()) {
        return SelectKt.getALREADY_SELECTED();
      }
      if (localObject1 == AbstractChannelKt.OFFER_SUCCESS) {
        return AbstractChannelKt.OFFER_SUCCESS;
      }
    } while ((localObject1 == AbstractChannelKt.OFFER_FAILED) || (localObject1 == AtomicKt.RETRY_ATOMIC));
    if ((localObject1 instanceof Closed)) {
      return localObject1;
    }
    paramE = Intrinsics.stringPlus("Invalid result ", localObject1);
    Log5ECF72.a(paramE);
    LogE84000.a(paramE);
    Log229316.a(paramE);
    throw new IllegalStateException(paramE.toString());
  }
  
  protected void onCancelIdempotentList-w-w6eGU(Object paramObject, Closed<?> paramClosed)
  {
    Object localObject1 = null;
    Send localSend = null;
    Object localObject2 = null;
    ArrayList localArrayList = null;
    if (paramObject == null)
    {
      paramObject = localObject2;
    }
    else
    {
      boolean bool = paramObject instanceof ArrayList;
      localObject2 = null;
      if (!bool)
      {
        paramObject = (Send)paramObject;
        if ((paramObject instanceof AbstractSendChannel.SendBuffered))
        {
          paramClosed = this.onUndeliveredElement;
          if (paramClosed == null) {
            paramObject = localObject2;
          } else {
            paramObject = OnUndeliveredElementKt.callUndeliveredElementCatchingException(paramClosed, ((AbstractSendChannel.SendBuffered)paramObject).element, (UndeliveredElementException)null);
          }
        }
        else
        {
          ((Send)paramObject).resumeSendClosed(paramClosed);
          paramObject = localArrayList;
        }
      }
      else
      {
        if (paramObject == null) {
          break label201;
        }
        localArrayList = (ArrayList)paramObject;
        int i = localArrayList.size() - 1;
        paramObject = localSend;
        if (i >= 0)
        {
          int j;
          do
          {
            j = i - 1;
            localSend = (Send)localArrayList.get(i);
            if ((localSend instanceof AbstractSendChannel.SendBuffered))
            {
              paramObject = this.onUndeliveredElement;
              if (paramObject == null) {
                paramObject = null;
              } else {
                paramObject = OnUndeliveredElementKt.callUndeliveredElementCatchingException((Function1)paramObject, ((AbstractSendChannel.SendBuffered)localSend).element, (UndeliveredElementException)localObject1);
              }
            }
            else
            {
              localSend.resumeSendClosed(paramClosed);
              paramObject = localObject1;
            }
            localObject1 = paramObject;
            i = j;
          } while (j >= 0);
        }
      }
    }
    if (paramObject == null) {
      return;
    }
    throw ((Throwable)paramObject);
    label201:
    throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/LinkedListChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */