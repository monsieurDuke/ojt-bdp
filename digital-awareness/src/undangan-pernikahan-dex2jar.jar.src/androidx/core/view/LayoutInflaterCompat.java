package androidx.core.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

public final class LayoutInflaterCompat
{
  private static final String TAG = "LayoutInflaterCompatHC";
  private static boolean sCheckedField;
  private static Field sLayoutInflaterFactory2Field;
  
  private static void forceSetFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    if (!sCheckedField)
    {
      try
      {
        Field localField1 = LayoutInflater.class.getDeclaredField("mFactory2");
        sLayoutInflaterFactory2Field = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("LayoutInflaterCompatHC", "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", localNoSuchFieldException);
      }
      sCheckedField = true;
    }
    Field localField2 = sLayoutInflaterFactory2Field;
    if (localField2 != null) {
      try
      {
        localField2.set(paramLayoutInflater, paramFactory2);
      }
      catch (IllegalAccessException paramFactory2)
      {
        Log.e("LayoutInflaterCompatHC", "forceSetFactory2 could not set the Factory2 on LayoutInflater " + paramLayoutInflater + "; inflation may have unexpected results.", paramFactory2);
      }
    }
  }
  
  @Deprecated
  public static LayoutInflaterFactory getFactory(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = paramLayoutInflater.getFactory();
    if ((paramLayoutInflater instanceof Factory2Wrapper)) {
      return ((Factory2Wrapper)paramLayoutInflater).mDelegateFactory;
    }
    return null;
  }
  
  @Deprecated
  public static void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramLayoutInflater.setFactory2(new Factory2Wrapper(paramLayoutInflaterFactory));
    }
    else
    {
      paramLayoutInflaterFactory = new Factory2Wrapper(paramLayoutInflaterFactory);
      paramLayoutInflater.setFactory2(paramLayoutInflaterFactory);
      LayoutInflater.Factory localFactory = paramLayoutInflater.getFactory();
      if ((localFactory instanceof LayoutInflater.Factory2)) {
        forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)localFactory);
      } else {
        forceSetFactory2(paramLayoutInflater, paramLayoutInflaterFactory);
      }
    }
  }
  
  public static void setFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    paramLayoutInflater.setFactory2(paramFactory2);
    if (Build.VERSION.SDK_INT < 21)
    {
      LayoutInflater.Factory localFactory = paramLayoutInflater.getFactory();
      if ((localFactory instanceof LayoutInflater.Factory2)) {
        forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)localFactory);
      } else {
        forceSetFactory2(paramLayoutInflater, paramFactory2);
      }
    }
  }
  
  static class Factory2Wrapper
    implements LayoutInflater.Factory2
  {
    final LayoutInflaterFactory mDelegateFactory;
    
    Factory2Wrapper(LayoutInflaterFactory paramLayoutInflaterFactory)
    {
      this.mDelegateFactory = paramLayoutInflaterFactory;
    }
    
    public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
    }
    
    public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(null, paramString, paramContext, paramAttributeSet);
    }
    
    public String toString()
    {
      return getClass().getName() + "{" + this.mDelegateFactory + "}";
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/LayoutInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */