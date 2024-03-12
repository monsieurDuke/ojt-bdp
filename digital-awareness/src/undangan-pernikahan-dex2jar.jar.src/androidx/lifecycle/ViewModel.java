package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class ViewModel
{
  private final Map<String, Object> mBagOfTags = new HashMap();
  private volatile boolean mCleared;
  private final Set<Closeable> mCloseables;
  
  public ViewModel()
  {
    this.mCloseables = new LinkedHashSet();
    this.mCleared = false;
  }
  
  public ViewModel(Closeable... paramVarArgs)
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    this.mCloseables = localLinkedHashSet;
    this.mCleared = false;
    localLinkedHashSet.addAll(Arrays.asList(paramVarArgs));
  }
  
  private static void closeWithRuntimeException(Object paramObject)
  {
    if ((paramObject instanceof Closeable)) {
      try
      {
        ((Closeable)paramObject).close();
      }
      catch (IOException paramObject)
      {
        throw new RuntimeException((Throwable)paramObject);
      }
    }
  }
  
  public void addCloseable(Closeable paramCloseable)
  {
    Set localSet = this.mCloseables;
    if (localSet != null) {
      try
      {
        this.mCloseables.add(paramCloseable);
      }
      finally {}
    }
  }
  
  final void clear()
  {
    this.mCleared = true;
    Object localObject1 = this.mBagOfTags;
    if (localObject1 != null) {
      try
      {
        Iterator localIterator1 = this.mBagOfTags.values().iterator();
        while (localIterator1.hasNext()) {
          closeWithRuntimeException(localIterator1.next());
        }
      }
      finally {}
    }
    localObject1 = this.mCloseables;
    if (localObject1 != null) {
      try
      {
        Iterator localIterator2 = this.mCloseables.iterator();
        while (localIterator2.hasNext()) {
          closeWithRuntimeException((Closeable)localIterator2.next());
        }
      }
      finally {}
    }
    onCleared();
  }
  
  <T> T getTag(String paramString)
  {
    Map localMap = this.mBagOfTags;
    if (localMap == null) {
      return null;
    }
    try
    {
      paramString = this.mBagOfTags.get(paramString);
      return paramString;
    }
    finally {}
  }
  
  protected void onCleared() {}
  
  <T> T setTagIfAbsent(String paramString, T paramT)
  {
    synchronized (this.mBagOfTags)
    {
      Object localObject = this.mBagOfTags.get(paramString);
      if (localObject == null) {
        this.mBagOfTags.put(paramString, paramT);
      }
      if (localObject == null) {
        paramString = paramT;
      } else {
        paramString = (String)localObject;
      }
      if (this.mCleared) {
        closeWithRuntimeException(paramString);
      }
      return paramString;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ViewModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */