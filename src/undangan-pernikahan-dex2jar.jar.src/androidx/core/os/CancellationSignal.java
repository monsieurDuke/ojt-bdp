package androidx.core.os;

import android.os.Build.VERSION;

public final class CancellationSignal
{
  private boolean mCancelInProgress;
  private Object mCancellationSignalObj;
  private boolean mIsCanceled;
  private OnCancelListener mOnCancelListener;
  
  private void waitForCancelFinishedLocked()
  {
    for (;;)
    {
      if (this.mCancelInProgress) {
        try
        {
          wait();
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;) {}
        }
      }
    }
  }
  
  /* Error */
  public void cancel()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 33	androidx/core/os/CancellationSignal:mIsCanceled	Z
    //   6: ifeq +6 -> 12
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield 33	androidx/core/os/CancellationSignal:mIsCanceled	Z
    //   17: aload_0
    //   18: iconst_1
    //   19: putfield 27	androidx/core/os/CancellationSignal:mCancelInProgress	Z
    //   22: aload_0
    //   23: getfield 35	androidx/core/os/CancellationSignal:mOnCancelListener	Landroidx/core/os/CancellationSignal$OnCancelListener;
    //   26: astore_2
    //   27: aload_0
    //   28: getfield 37	androidx/core/os/CancellationSignal:mCancellationSignalObj	Ljava/lang/Object;
    //   31: astore_1
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_2
    //   35: ifnull +16 -> 51
    //   38: aload_2
    //   39: invokeinterface 40 1 0
    //   44: goto +7 -> 51
    //   47: astore_1
    //   48: goto +22 -> 70
    //   51: aload_1
    //   52: ifnull +38 -> 90
    //   55: getstatic 46	android/os/Build$VERSION:SDK_INT	I
    //   58: bipush 16
    //   60: if_icmplt +30 -> 90
    //   63: aload_1
    //   64: invokestatic 49	androidx/core/os/CancellationSignal$Api16Impl:cancel	(Ljava/lang/Object;)V
    //   67: goto +23 -> 90
    //   70: aload_0
    //   71: monitorenter
    //   72: aload_0
    //   73: iconst_0
    //   74: putfield 27	androidx/core/os/CancellationSignal:mCancelInProgress	Z
    //   77: aload_0
    //   78: invokevirtual 52	java/lang/Object:notifyAll	()V
    //   81: aload_0
    //   82: monitorexit
    //   83: aload_1
    //   84: athrow
    //   85: astore_1
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_1
    //   89: athrow
    //   90: aload_0
    //   91: monitorenter
    //   92: aload_0
    //   93: iconst_0
    //   94: putfield 27	androidx/core/os/CancellationSignal:mCancelInProgress	Z
    //   97: aload_0
    //   98: invokevirtual 52	java/lang/Object:notifyAll	()V
    //   101: aload_0
    //   102: monitorexit
    //   103: return
    //   104: astore_1
    //   105: aload_0
    //   106: monitorexit
    //   107: aload_1
    //   108: athrow
    //   109: astore_1
    //   110: aload_0
    //   111: monitorexit
    //   112: aload_1
    //   113: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	114	0	this	CancellationSignal
    //   31	1	1	localObject1	Object
    //   47	37	1	localObject2	Object
    //   85	4	1	localObject3	Object
    //   104	4	1	localObject4	Object
    //   109	4	1	localObject5	Object
    //   26	13	2	localOnCancelListener	OnCancelListener
    // Exception table:
    //   from	to	target	type
    //   38	44	47	finally
    //   55	67	47	finally
    //   72	83	85	finally
    //   86	88	85	finally
    //   92	103	104	finally
    //   105	107	104	finally
    //   2	11	109	finally
    //   12	34	109	finally
    //   110	112	109	finally
  }
  
  public Object getCancellationSignalObject()
  {
    if (Build.VERSION.SDK_INT < 16) {
      return null;
    }
    try
    {
      if (this.mCancellationSignalObj == null)
      {
        localObject1 = Api16Impl.createCancellationSignal();
        this.mCancellationSignalObj = localObject1;
        if (this.mIsCanceled) {
          Api16Impl.cancel(localObject1);
        }
      }
      Object localObject1 = this.mCancellationSignalObj;
      return localObject1;
    }
    finally {}
  }
  
  public boolean isCanceled()
  {
    try
    {
      boolean bool = this.mIsCanceled;
      return bool;
    }
    finally {}
  }
  
  public void setOnCancelListener(OnCancelListener paramOnCancelListener)
  {
    try
    {
      waitForCancelFinishedLocked();
      if (this.mOnCancelListener == paramOnCancelListener) {
        return;
      }
      this.mOnCancelListener = paramOnCancelListener;
      if ((this.mIsCanceled) && (paramOnCancelListener != null))
      {
        paramOnCancelListener.onCancel();
        return;
      }
      return;
    }
    finally {}
  }
  
  public void throwIfCanceled()
  {
    if (!isCanceled()) {
      return;
    }
    throw new OperationCanceledException();
  }
  
  static class Api16Impl
  {
    static void cancel(Object paramObject)
    {
      ((android.os.CancellationSignal)paramObject).cancel();
    }
    
    static android.os.CancellationSignal createCancellationSignal()
    {
      return new android.os.CancellationSignal();
    }
  }
  
  public static abstract interface OnCancelListener
  {
    public abstract void onCancel();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/CancellationSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */