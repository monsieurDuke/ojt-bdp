package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.R.styleable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppCompatViewInflater
{
  private static final String LOG_TAG = "AppCompatViewInflater";
  private static final int[] sAccessibilityHeading;
  private static final int[] sAccessibilityPaneTitle;
  private static final String[] sClassPrefixList = { "android.widget.", "android.view.", "android.webkit." };
  private static final SimpleArrayMap<String, Constructor<? extends View>> sConstructorMap = new SimpleArrayMap();
  private static final Class<?>[] sConstructorSignature = { Context.class, AttributeSet.class };
  private static final int[] sOnClickAttrs = { 16843375 };
  private static final int[] sScreenReaderFocusable;
  private final Object[] mConstructorArgs = new Object[2];
  
  static
  {
    sAccessibilityHeading = new int[] { 16844160 };
    sAccessibilityPaneTitle = new int[] { 16844156 };
    sScreenReaderFocusable = new int[] { 16844148 };
  }
  
  private void backportAccessibilityAttributes(Context paramContext, View paramView, AttributeSet paramAttributeSet)
  {
    if ((Build.VERSION.SDK_INT >= 19) && (Build.VERSION.SDK_INT <= 28))
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, sAccessibilityHeading);
      if (localTypedArray.hasValue(0)) {
        ViewCompat.setAccessibilityHeading(paramView, localTypedArray.getBoolean(0, false));
      }
      localTypedArray.recycle();
      localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, sAccessibilityPaneTitle);
      if (localTypedArray.hasValue(0)) {
        ViewCompat.setAccessibilityPaneTitle(paramView, localTypedArray.getString(0));
      }
      localTypedArray.recycle();
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, sScreenReaderFocusable);
      if (paramContext.hasValue(0)) {
        ViewCompat.setScreenReaderFocusable(paramView, paramContext.getBoolean(0, false));
      }
      paramContext.recycle();
      return;
    }
  }
  
  private void checkOnClickListener(View paramView, AttributeSet paramAttributeSet)
  {
    Object localObject = paramView.getContext();
    if (((localObject instanceof ContextWrapper)) && ((Build.VERSION.SDK_INT < 15) || (ViewCompat.hasOnClickListeners(paramView))))
    {
      localObject = ((Context)localObject).obtainStyledAttributes(paramAttributeSet, sOnClickAttrs);
      paramAttributeSet = ((TypedArray)localObject).getString(0);
      if (paramAttributeSet != null) {
        paramView.setOnClickListener(new DeclaredOnClickListener(paramView, paramAttributeSet));
      }
      ((TypedArray)localObject).recycle();
      return;
    }
  }
  
  private View createViewByPrefix(Context paramContext, String paramString1, String paramString2)
    throws ClassNotFoundException, InflateException
  {
    SimpleArrayMap localSimpleArrayMap = sConstructorMap;
    Constructor localConstructor = (Constructor)localSimpleArrayMap.get(paramString1);
    Object localObject = localConstructor;
    if ((localConstructor != null) || (paramString2 != null)) {}
    try
    {
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      paramString2 = paramString2 + paramString1;
      break label58;
      paramString2 = paramString1;
      label58:
      localObject = Class.forName(paramString2, false, paramContext.getClassLoader()).asSubclass(View.class).getConstructor(sConstructorSignature);
      localSimpleArrayMap.put(paramString1, localObject);
      ((Constructor)localObject).setAccessible(true);
      paramContext = (View)((Constructor)localObject).newInstance(this.mConstructorArgs);
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  /* Error */
  private View createViewFromTag(Context paramContext, String paramString, AttributeSet paramAttributeSet)
  {
    // Byte code:
    //   0: aload_2
    //   1: astore 5
    //   3: aload_2
    //   4: ldc -66
    //   6: invokevirtual 194	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   9: ifeq +14 -> 23
    //   12: aload_3
    //   13: aconst_null
    //   14: ldc -60
    //   16: invokeinterface 200 3 0
    //   21: astore 5
    //   23: aload_0
    //   24: getfield 69	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   27: astore_2
    //   28: aload_2
    //   29: iconst_0
    //   30: aload_1
    //   31: aastore
    //   32: aload_2
    //   33: iconst_1
    //   34: aload_3
    //   35: aastore
    //   36: iconst_m1
    //   37: aload 5
    //   39: bipush 46
    //   41: invokevirtual 204	java/lang/String:indexOf	(I)I
    //   44: if_icmpne +69 -> 113
    //   47: iconst_0
    //   48: istore 4
    //   50: getstatic 58	androidx/appcompat/app/AppCompatViewInflater:sClassPrefixList	[Ljava/lang/String;
    //   53: astore_2
    //   54: iload 4
    //   56: aload_2
    //   57: arraylength
    //   58: if_icmpge +40 -> 98
    //   61: aload_0
    //   62: aload_1
    //   63: aload 5
    //   65: aload_2
    //   66: iload 4
    //   68: aaload
    //   69: invokespecial 206	androidx/appcompat/app/AppCompatViewInflater:createViewByPrefix	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;
    //   72: astore_2
    //   73: aload_2
    //   74: ifnull +18 -> 92
    //   77: aload_0
    //   78: getfield 69	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   81: astore_1
    //   82: aload_1
    //   83: iconst_0
    //   84: aconst_null
    //   85: aastore
    //   86: aload_1
    //   87: iconst_1
    //   88: aconst_null
    //   89: aastore
    //   90: aload_2
    //   91: areturn
    //   92: iinc 4 1
    //   95: goto -45 -> 50
    //   98: aload_0
    //   99: getfield 69	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   102: astore_1
    //   103: aload_1
    //   104: iconst_0
    //   105: aconst_null
    //   106: aastore
    //   107: aload_1
    //   108: iconst_1
    //   109: aconst_null
    //   110: aastore
    //   111: aconst_null
    //   112: areturn
    //   113: aload_0
    //   114: aload_1
    //   115: aload 5
    //   117: aconst_null
    //   118: invokespecial 206	androidx/appcompat/app/AppCompatViewInflater:createViewByPrefix	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;
    //   121: astore_1
    //   122: aload_0
    //   123: getfield 69	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   126: astore_2
    //   127: aload_2
    //   128: iconst_0
    //   129: aconst_null
    //   130: aastore
    //   131: aload_2
    //   132: iconst_1
    //   133: aconst_null
    //   134: aastore
    //   135: aload_1
    //   136: areturn
    //   137: astore_2
    //   138: aload_0
    //   139: getfield 69	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   142: astore_1
    //   143: aload_1
    //   144: iconst_0
    //   145: aconst_null
    //   146: aastore
    //   147: aload_1
    //   148: iconst_1
    //   149: aconst_null
    //   150: aastore
    //   151: aload_2
    //   152: athrow
    //   153: astore_1
    //   154: aload_0
    //   155: getfield 69	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   158: astore_1
    //   159: aload_1
    //   160: iconst_0
    //   161: aconst_null
    //   162: aastore
    //   163: aload_1
    //   164: iconst_1
    //   165: aconst_null
    //   166: aastore
    //   167: aconst_null
    //   168: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	169	0	this	AppCompatViewInflater
    //   0	169	1	paramContext	Context
    //   0	169	2	paramString	String
    //   0	169	3	paramAttributeSet	AttributeSet
    //   48	45	4	i	int
    //   1	115	5	str	String
    // Exception table:
    //   from	to	target	type
    //   23	28	137	finally
    //   36	47	137	finally
    //   50	73	137	finally
    //   113	122	137	finally
    //   23	28	153	java/lang/Exception
    //   36	47	153	java/lang/Exception
    //   50	73	153	java/lang/Exception
    //   113	122	153	java/lang/Exception
  }
  
  private static Context themifyContext(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.View, 0, 0);
    int i = 0;
    if (paramBoolean1) {
      i = paramAttributeSet.getResourceId(R.styleable.View_android_theme, 0);
    }
    int j = i;
    if (paramBoolean2)
    {
      j = i;
      if (i == 0)
      {
        i = paramAttributeSet.getResourceId(R.styleable.View_theme, 0);
        j = i;
        if (i != 0)
        {
          Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
          j = i;
        }
      }
    }
    paramAttributeSet.recycle();
    paramAttributeSet = paramContext;
    if (j != 0) {
      if ((paramContext instanceof ContextThemeWrapper))
      {
        paramAttributeSet = paramContext;
        if (((ContextThemeWrapper)paramContext).getThemeResId() == j) {}
      }
      else
      {
        paramAttributeSet = new ContextThemeWrapper(paramContext, j);
      }
    }
    return paramAttributeSet;
  }
  
  private void verifyNotNull(View paramView, String paramString)
  {
    if (paramView != null) {
      return;
    }
    throw new IllegalStateException(getClass().getName() + " asked to inflate view for <" + paramString + ">, but returned null");
  }
  
  protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatAutoCompleteTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatButton createButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatCheckBox createCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatCheckBox(paramContext, paramAttributeSet);
  }
  
  protected AppCompatCheckedTextView createCheckedTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatCheckedTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatEditText createEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatEditText(paramContext, paramAttributeSet);
  }
  
  protected AppCompatImageButton createImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatImageButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatImageView createImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatImageView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatMultiAutoCompleteTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatRadioButton createRadioButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatRadioButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatRatingBar createRatingBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatRatingBar(paramContext, paramAttributeSet);
  }
  
  protected AppCompatSeekBar createSeekBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatSeekBar(paramContext, paramAttributeSet);
  }
  
  protected AppCompatSpinner createSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatSpinner(paramContext, paramAttributeSet);
  }
  
  protected AppCompatTextView createTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatToggleButton createToggleButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatToggleButton(paramContext, paramAttributeSet);
  }
  
  protected View createView(Context paramContext, String paramString, AttributeSet paramAttributeSet)
  {
    return null;
  }
  
  final View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    Object localObject = paramContext;
    if (paramBoolean1)
    {
      localObject = paramContext;
      if (paramView != null) {
        localObject = paramView.getContext();
      }
    }
    if (!paramBoolean2)
    {
      paramView = (View)localObject;
      if (!paramBoolean3) {}
    }
    else
    {
      paramView = themifyContext((Context)localObject, paramAttributeSet, paramBoolean2, paramBoolean3);
    }
    localObject = paramView;
    if (paramBoolean4) {
      localObject = TintContextWrapper.wrap(paramView);
    }
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("Button"))
      {
        i = 2;
        break;
        if (paramString.equals("EditText"))
        {
          i = 3;
          break;
          if (paramString.equals("CheckBox"))
          {
            i = 6;
            break;
            if (paramString.equals("AutoCompleteTextView"))
            {
              i = 9;
              break;
              if (paramString.equals("ImageView"))
              {
                i = 1;
                break;
                if (paramString.equals("ToggleButton"))
                {
                  i = 13;
                  break;
                  if (paramString.equals("RadioButton"))
                  {
                    i = 7;
                    break;
                    if (paramString.equals("Spinner"))
                    {
                      i = 4;
                      break;
                      if (paramString.equals("SeekBar"))
                      {
                        i = 12;
                        break;
                        if (paramString.equals("ImageButton"))
                        {
                          i = 5;
                          break;
                          if (paramString.equals("TextView"))
                          {
                            i = 0;
                            break;
                            if (paramString.equals("MultiAutoCompleteTextView"))
                            {
                              i = 10;
                              break;
                              if (paramString.equals("CheckedTextView"))
                              {
                                i = 8;
                                break;
                                if (paramString.equals("RatingBar")) {
                                  i = 11;
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    switch (i)
    {
    default: 
      paramView = createView((Context)localObject, paramString, paramAttributeSet);
      break;
    case 13: 
      paramView = createToggleButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 12: 
      paramView = createSeekBar((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 11: 
      paramView = createRatingBar((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 10: 
      paramView = createMultiAutoCompleteTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 9: 
      paramView = createAutoCompleteTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 8: 
      paramView = createCheckedTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 7: 
      paramView = createRadioButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 6: 
      paramView = createCheckBox((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 5: 
      paramView = createImageButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 4: 
      paramView = createSpinner((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 3: 
      paramView = createEditText((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 2: 
      paramView = createButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 1: 
      paramView = createImageView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 0: 
      paramView = createTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
    }
    View localView = paramView;
    if (paramView == null)
    {
      localView = paramView;
      if (paramContext != localObject) {
        localView = createViewFromTag((Context)localObject, paramString, paramAttributeSet);
      }
    }
    if (localView != null)
    {
      checkOnClickListener(localView, paramAttributeSet);
      backportAccessibilityAttributes((Context)localObject, localView, paramAttributeSet);
    }
    return localView;
  }
  
  private static class DeclaredOnClickListener
    implements View.OnClickListener
  {
    private final View mHostView;
    private final String mMethodName;
    private Context mResolvedContext;
    private Method mResolvedMethod;
    
    public DeclaredOnClickListener(View paramView, String paramString)
    {
      this.mHostView = paramView;
      this.mMethodName = paramString;
    }
    
    private void resolveMethod(Context paramContext)
    {
      while (paramContext != null)
      {
        try
        {
          if (!paramContext.isRestricted())
          {
            Method localMethod = paramContext.getClass().getMethod(this.mMethodName, new Class[] { View.class });
            if (localMethod != null)
            {
              this.mResolvedMethod = localMethod;
              this.mResolvedContext = paramContext;
              return;
            }
          }
        }
        catch (NoSuchMethodException localNoSuchMethodException) {}
        if ((paramContext instanceof ContextWrapper)) {
          paramContext = ((ContextWrapper)paramContext).getBaseContext();
        } else {
          paramContext = null;
        }
      }
      int i = this.mHostView.getId();
      if (i == -1) {
        paramContext = "";
      } else {
        paramContext = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(i) + "'";
      }
      throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + paramContext);
    }
    
    public void onClick(View paramView)
    {
      if (this.mResolvedMethod == null) {
        resolveMethod(this.mHostView.getContext());
      }
      try
      {
        this.mResolvedMethod.invoke(this.mResolvedContext, new Object[] { paramView });
        return;
      }
      catch (InvocationTargetException paramView)
      {
        throw new IllegalStateException("Could not execute method for android:onClick", paramView);
      }
      catch (IllegalAccessException paramView)
      {
        throw new IllegalStateException("Could not execute non-public method for android:onClick", paramView);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/app/AppCompatViewInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */