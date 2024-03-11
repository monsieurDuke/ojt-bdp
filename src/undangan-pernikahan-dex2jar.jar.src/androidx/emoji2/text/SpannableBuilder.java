package androidx.emoji2.text;

import android.os.Build.VERSION;
import android.text.Editable;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import androidx.core.util.Preconditions;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class SpannableBuilder
  extends SpannableStringBuilder
{
  private final Class<?> mWatcherClass;
  private final List<WatcherWrapper> mWatchers = new ArrayList();
  
  SpannableBuilder(Class<?> paramClass)
  {
    Preconditions.checkNotNull(paramClass, "watcherClass cannot be null");
    this.mWatcherClass = paramClass;
  }
  
  SpannableBuilder(Class<?> paramClass, CharSequence paramCharSequence)
  {
    super(paramCharSequence);
    Preconditions.checkNotNull(paramClass, "watcherClass cannot be null");
    this.mWatcherClass = paramClass;
  }
  
  SpannableBuilder(Class<?> paramClass, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    super(paramCharSequence, paramInt1, paramInt2);
    Preconditions.checkNotNull(paramClass, "watcherClass cannot be null");
    this.mWatcherClass = paramClass;
  }
  
  private void blockWatchers()
  {
    for (int i = 0; i < this.mWatchers.size(); i++) {
      ((WatcherWrapper)this.mWatchers.get(i)).blockCalls();
    }
  }
  
  public static SpannableBuilder create(Class<?> paramClass, CharSequence paramCharSequence)
  {
    return new SpannableBuilder(paramClass, paramCharSequence);
  }
  
  private void fireWatchers()
  {
    for (int i = 0; i < this.mWatchers.size(); i++) {
      ((WatcherWrapper)this.mWatchers.get(i)).onTextChanged(this, 0, length(), length());
    }
  }
  
  private WatcherWrapper getWatcherFor(Object paramObject)
  {
    for (int i = 0; i < this.mWatchers.size(); i++)
    {
      WatcherWrapper localWatcherWrapper = (WatcherWrapper)this.mWatchers.get(i);
      if (localWatcherWrapper.mObject == paramObject) {
        return localWatcherWrapper;
      }
    }
    return null;
  }
  
  private boolean isWatcher(Class<?> paramClass)
  {
    boolean bool;
    if (this.mWatcherClass == paramClass) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isWatcher(Object paramObject)
  {
    boolean bool;
    if ((paramObject != null) && (isWatcher(paramObject.getClass()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void unblockwatchers()
  {
    for (int i = 0; i < this.mWatchers.size(); i++) {
      ((WatcherWrapper)this.mWatchers.get(i)).unblockCalls();
    }
  }
  
  public SpannableStringBuilder append(char paramChar)
  {
    super.append(paramChar);
    return this;
  }
  
  public SpannableStringBuilder append(CharSequence paramCharSequence)
  {
    super.append(paramCharSequence);
    return this;
  }
  
  public SpannableStringBuilder append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    super.append(paramCharSequence, paramInt1, paramInt2);
    return this;
  }
  
  public SpannableStringBuilder append(CharSequence paramCharSequence, Object paramObject, int paramInt)
  {
    super.append(paramCharSequence, paramObject, paramInt);
    return this;
  }
  
  public void beginBatchEdit()
  {
    blockWatchers();
  }
  
  public SpannableStringBuilder delete(int paramInt1, int paramInt2)
  {
    super.delete(paramInt1, paramInt2);
    return this;
  }
  
  public void endBatchEdit()
  {
    unblockwatchers();
    fireWatchers();
  }
  
  public int getSpanEnd(Object paramObject)
  {
    Object localObject = paramObject;
    if (isWatcher(paramObject))
    {
      WatcherWrapper localWatcherWrapper = getWatcherFor(paramObject);
      localObject = paramObject;
      if (localWatcherWrapper != null) {
        localObject = localWatcherWrapper;
      }
    }
    return super.getSpanEnd(localObject);
  }
  
  public int getSpanFlags(Object paramObject)
  {
    Object localObject = paramObject;
    if (isWatcher(paramObject))
    {
      WatcherWrapper localWatcherWrapper = getWatcherFor(paramObject);
      localObject = paramObject;
      if (localWatcherWrapper != null) {
        localObject = localWatcherWrapper;
      }
    }
    return super.getSpanFlags(localObject);
  }
  
  public int getSpanStart(Object paramObject)
  {
    Object localObject = paramObject;
    if (isWatcher(paramObject))
    {
      WatcherWrapper localWatcherWrapper = getWatcherFor(paramObject);
      localObject = paramObject;
      if (localWatcherWrapper != null) {
        localObject = localWatcherWrapper;
      }
    }
    return super.getSpanStart(localObject);
  }
  
  public <T> T[] getSpans(int paramInt1, int paramInt2, Class<T> paramClass)
  {
    if (isWatcher(paramClass))
    {
      WatcherWrapper[] arrayOfWatcherWrapper = (WatcherWrapper[])super.getSpans(paramInt1, paramInt2, WatcherWrapper.class);
      paramClass = (Object[])Array.newInstance(paramClass, arrayOfWatcherWrapper.length);
      for (paramInt1 = 0; paramInt1 < arrayOfWatcherWrapper.length; paramInt1++) {
        paramClass[paramInt1] = arrayOfWatcherWrapper[paramInt1].mObject;
      }
      return paramClass;
    }
    return super.getSpans(paramInt1, paramInt2, paramClass);
  }
  
  public SpannableStringBuilder insert(int paramInt, CharSequence paramCharSequence)
  {
    super.insert(paramInt, paramCharSequence);
    return this;
  }
  
  public SpannableStringBuilder insert(int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3)
  {
    super.insert(paramInt1, paramCharSequence, paramInt2, paramInt3);
    return this;
  }
  
  public int nextSpanTransition(int paramInt1, int paramInt2, Class paramClass)
  {
    Class localClass;
    if (paramClass != null)
    {
      localClass = paramClass;
      if (!isWatcher(paramClass)) {}
    }
    else
    {
      localClass = WatcherWrapper.class;
    }
    return super.nextSpanTransition(paramInt1, paramInt2, localClass);
  }
  
  public void removeSpan(Object paramObject)
  {
    WatcherWrapper localWatcherWrapper2;
    if (isWatcher(paramObject))
    {
      WatcherWrapper localWatcherWrapper1 = getWatcherFor(paramObject);
      localWatcherWrapper2 = localWatcherWrapper1;
      if (localWatcherWrapper1 != null)
      {
        paramObject = localWatcherWrapper1;
        localWatcherWrapper2 = localWatcherWrapper1;
      }
    }
    else
    {
      localWatcherWrapper2 = null;
    }
    super.removeSpan(paramObject);
    if (localWatcherWrapper2 != null) {
      this.mWatchers.remove(localWatcherWrapper2);
    }
  }
  
  public SpannableStringBuilder replace(int paramInt1, int paramInt2, CharSequence paramCharSequence)
  {
    blockWatchers();
    super.replace(paramInt1, paramInt2, paramCharSequence);
    unblockwatchers();
    return this;
  }
  
  public SpannableStringBuilder replace(int paramInt1, int paramInt2, CharSequence paramCharSequence, int paramInt3, int paramInt4)
  {
    blockWatchers();
    super.replace(paramInt1, paramInt2, paramCharSequence, paramInt3, paramInt4);
    unblockwatchers();
    return this;
  }
  
  public void setSpan(Object paramObject, int paramInt1, int paramInt2, int paramInt3)
  {
    Object localObject = paramObject;
    if (isWatcher(paramObject))
    {
      localObject = new WatcherWrapper(paramObject);
      this.mWatchers.add(localObject);
    }
    super.setSpan(localObject, paramInt1, paramInt2, paramInt3);
  }
  
  public CharSequence subSequence(int paramInt1, int paramInt2)
  {
    return new SpannableBuilder(this.mWatcherClass, this, paramInt1, paramInt2);
  }
  
  private static class WatcherWrapper
    implements TextWatcher, SpanWatcher
  {
    private final AtomicInteger mBlockCalls = new AtomicInteger(0);
    final Object mObject;
    
    WatcherWrapper(Object paramObject)
    {
      this.mObject = paramObject;
    }
    
    private boolean isEmojiSpan(Object paramObject)
    {
      return paramObject instanceof EmojiSpan;
    }
    
    public void afterTextChanged(Editable paramEditable)
    {
      ((TextWatcher)this.mObject).afterTextChanged(paramEditable);
    }
    
    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      ((TextWatcher)this.mObject).beforeTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    }
    
    final void blockCalls()
    {
      this.mBlockCalls.incrementAndGet();
    }
    
    public void onSpanAdded(Spannable paramSpannable, Object paramObject, int paramInt1, int paramInt2)
    {
      if ((this.mBlockCalls.get() > 0) && (isEmojiSpan(paramObject))) {
        return;
      }
      ((SpanWatcher)this.mObject).onSpanAdded(paramSpannable, paramObject, paramInt1, paramInt2);
    }
    
    public void onSpanChanged(Spannable paramSpannable, Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if ((this.mBlockCalls.get() > 0) && (isEmojiSpan(paramObject))) {
        return;
      }
      int j = paramInt1;
      int k = paramInt3;
      if (Build.VERSION.SDK_INT < 28)
      {
        int i = paramInt1;
        if (paramInt1 > paramInt2) {
          i = 0;
        }
        j = i;
        k = paramInt3;
        if (paramInt3 > paramInt4)
        {
          k = 0;
          j = i;
        }
      }
      ((SpanWatcher)this.mObject).onSpanChanged(paramSpannable, paramObject, j, paramInt2, k, paramInt4);
    }
    
    public void onSpanRemoved(Spannable paramSpannable, Object paramObject, int paramInt1, int paramInt2)
    {
      if ((this.mBlockCalls.get() > 0) && (isEmojiSpan(paramObject))) {
        return;
      }
      ((SpanWatcher)this.mObject).onSpanRemoved(paramSpannable, paramObject, paramInt1, paramInt2);
    }
    
    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      ((TextWatcher)this.mObject).onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    }
    
    final void unblockCalls()
    {
      this.mBlockCalls.decrementAndGet();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/SpannableBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */