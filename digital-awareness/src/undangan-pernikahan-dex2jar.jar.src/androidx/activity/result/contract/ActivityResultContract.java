package androidx.activity.result.contract;

import android.content.Context;
import android.content.Intent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000.\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\004\b&\030\000*\004\b\000\020\001*\004\b\001\020\0022\0020\003:\001\023B\005¢\006\002\020\004J\035\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0028\000H&¢\006\002\020\nJ%\020\013\032\n\022\004\022\0028\001\030\0010\f2\006\020\007\032\0020\b2\006\020\t\032\0028\000H\026¢\006\002\020\rJ\037\020\016\032\0028\0012\006\020\017\032\0020\0202\b\020\021\032\004\030\0010\006H&¢\006\002\020\022¨\006\024"}, d2={"Landroidx/activity/result/contract/ActivityResultContract;", "I", "O", "", "()V", "createIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "input", "(Landroid/content/Context;Ljava/lang/Object;)Landroid/content/Intent;", "getSynchronousResult", "Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "(Landroid/content/Context;Ljava/lang/Object;)Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "parseResult", "resultCode", "", "intent", "(ILandroid/content/Intent;)Ljava/lang/Object;", "SynchronousResult", "activity_release"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class ActivityResultContract<I, O>
{
  public abstract Intent createIntent(Context paramContext, I paramI);
  
  public SynchronousResult<O> getSynchronousResult(Context paramContext, I paramI)
  {
    Intrinsics.checkNotNullParameter(paramContext, "context");
    return null;
  }
  
  public abstract O parseResult(int paramInt, Intent paramIntent);
  
  @Metadata(d1={"\000\016\n\002\030\002\n\000\n\002\020\000\n\002\b\006\030\000*\004\b\002\020\0012\0020\002B\r\022\006\020\003\032\0028\002¢\006\002\020\004R\023\020\003\032\0028\002¢\006\n\n\002\020\007\032\004\b\005\020\006¨\006\b"}, d2={"Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "T", "", "value", "(Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "activity_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class SynchronousResult<T>
  {
    private final T value;
    
    public SynchronousResult(T paramT)
    {
      this.value = paramT;
    }
    
    public final T getValue()
    {
      return (T)this.value;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/result/contract/ActivityResultContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */