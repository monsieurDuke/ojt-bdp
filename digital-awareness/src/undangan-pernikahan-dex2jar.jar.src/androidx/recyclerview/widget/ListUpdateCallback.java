package androidx.recyclerview.widget;

public abstract interface ListUpdateCallback
{
  public abstract void onChanged(int paramInt1, int paramInt2, Object paramObject);
  
  public abstract void onInserted(int paramInt1, int paramInt2);
  
  public abstract void onMoved(int paramInt1, int paramInt2);
  
  public abstract void onRemoved(int paramInt1, int paramInt2);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/ListUpdateCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */