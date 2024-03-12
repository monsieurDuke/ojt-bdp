package androidx.appcompat.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R.attr;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.TintableBackgroundView;
import androidx.core.view.ViewCompat;

public class AppCompatSpinner
  extends Spinner
  implements TintableBackgroundView
{
  private static final int[] ATTRS_ANDROID_SPINNERMODE = { 16843505 };
  private static final int MAX_ITEMS_MEASURED = 15;
  private static final int MODE_DIALOG = 0;
  private static final int MODE_DROPDOWN = 1;
  private static final int MODE_THEME = -1;
  private static final String TAG = "AppCompatSpinner";
  private final AppCompatBackgroundHelper mBackgroundTintHelper;
  int mDropDownWidth;
  private ForwardingListener mForwardingListener;
  private SpinnerPopup mPopup;
  private final Context mPopupContext;
  private final boolean mPopupSet;
  private SpinnerAdapter mTempAdapter;
  final Rect mTempRect;
  
  public AppCompatSpinner(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatSpinner(Context paramContext, int paramInt)
  {
    this(paramContext, null, R.attr.spinnerStyle, paramInt);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.spinnerStyle);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, -1);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this(paramContext, paramAttributeSet, paramInt1, paramInt2, null);
  }
  
  /* Error */
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, final Resources.Theme paramTheme)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: iload_3
    //   4: invokespecial 101	android/widget/Spinner:<init>	(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   7: aload_0
    //   8: new 103	android/graphics/Rect
    //   11: dup
    //   12: invokespecial 105	android/graphics/Rect:<init>	()V
    //   15: putfield 107	androidx/appcompat/widget/AppCompatSpinner:mTempRect	Landroid/graphics/Rect;
    //   18: aload_0
    //   19: aload_0
    //   20: invokevirtual 111	androidx/appcompat/widget/AppCompatSpinner:getContext	()Landroid/content/Context;
    //   23: invokestatic 117	androidx/appcompat/widget/ThemeUtils:checkAppCompatTheme	(Landroid/view/View;Landroid/content/Context;)V
    //   26: aload_1
    //   27: aload_2
    //   28: getstatic 122	androidx/appcompat/R$styleable:Spinner	[I
    //   31: iload_3
    //   32: iconst_0
    //   33: invokestatic 128	androidx/appcompat/widget/TintTypedArray:obtainStyledAttributes	(Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroidx/appcompat/widget/TintTypedArray;
    //   36: astore 10
    //   38: aload_0
    //   39: new 130	androidx/appcompat/widget/AppCompatBackgroundHelper
    //   42: dup
    //   43: aload_0
    //   44: invokespecial 133	androidx/appcompat/widget/AppCompatBackgroundHelper:<init>	(Landroid/view/View;)V
    //   47: putfield 135	androidx/appcompat/widget/AppCompatSpinner:mBackgroundTintHelper	Landroidx/appcompat/widget/AppCompatBackgroundHelper;
    //   50: aload 5
    //   52: ifnull +20 -> 72
    //   55: aload_0
    //   56: new 137	androidx/appcompat/view/ContextThemeWrapper
    //   59: dup
    //   60: aload_1
    //   61: aload 5
    //   63: invokespecial 140	androidx/appcompat/view/ContextThemeWrapper:<init>	(Landroid/content/Context;Landroid/content/res/Resources$Theme;)V
    //   66: putfield 142	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   69: goto +41 -> 110
    //   72: aload 10
    //   74: getstatic 145	androidx/appcompat/R$styleable:Spinner_popupTheme	I
    //   77: iconst_0
    //   78: invokevirtual 149	androidx/appcompat/widget/TintTypedArray:getResourceId	(II)I
    //   81: istore 6
    //   83: iload 6
    //   85: ifeq +20 -> 105
    //   88: aload_0
    //   89: new 137	androidx/appcompat/view/ContextThemeWrapper
    //   92: dup
    //   93: aload_1
    //   94: iload 6
    //   96: invokespecial 151	androidx/appcompat/view/ContextThemeWrapper:<init>	(Landroid/content/Context;I)V
    //   99: putfield 142	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   102: goto +8 -> 110
    //   105: aload_0
    //   106: aload_1
    //   107: putfield 142	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   110: iload 4
    //   112: istore 7
    //   114: iload 4
    //   116: iconst_m1
    //   117: if_icmpne +132 -> 249
    //   120: aconst_null
    //   121: astore 5
    //   123: aconst_null
    //   124: astore 8
    //   126: aload_1
    //   127: aload_2
    //   128: getstatic 77	androidx/appcompat/widget/AppCompatSpinner:ATTRS_ANDROID_SPINNERMODE	[I
    //   131: iload_3
    //   132: iconst_0
    //   133: invokevirtual 156	android/content/Context:obtainStyledAttributes	(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;
    //   136: astore 9
    //   138: iload 4
    //   140: istore 6
    //   142: aload 9
    //   144: astore 8
    //   146: aload 9
    //   148: astore 5
    //   150: aload 9
    //   152: iconst_0
    //   153: invokevirtual 162	android/content/res/TypedArray:hasValue	(I)Z
    //   156: ifeq +20 -> 176
    //   159: aload 9
    //   161: astore 8
    //   163: aload 9
    //   165: astore 5
    //   167: aload 9
    //   169: iconst_0
    //   170: iconst_0
    //   171: invokevirtual 165	android/content/res/TypedArray:getInt	(II)I
    //   174: istore 6
    //   176: iload 6
    //   178: istore 7
    //   180: aload 9
    //   182: ifnull +67 -> 249
    //   185: iload 6
    //   187: istore 4
    //   189: aload 9
    //   191: astore 5
    //   193: aload 5
    //   195: invokevirtual 168	android/content/res/TypedArray:recycle	()V
    //   198: iload 4
    //   200: istore 7
    //   202: goto +47 -> 249
    //   205: astore_1
    //   206: goto +31 -> 237
    //   209: astore 9
    //   211: aload 5
    //   213: astore 8
    //   215: ldc 57
    //   217: ldc -86
    //   219: aload 9
    //   221: invokestatic 176	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   224: pop
    //   225: iload 4
    //   227: istore 7
    //   229: aload 5
    //   231: ifnull +18 -> 249
    //   234: goto -41 -> 193
    //   237: aload 8
    //   239: ifnull +8 -> 247
    //   242: aload 8
    //   244: invokevirtual 168	android/content/res/TypedArray:recycle	()V
    //   247: aload_1
    //   248: athrow
    //   249: iload 7
    //   251: tableswitch	default:+21->272, 0:+124->375, 1:+24->275
    //   272: goto +134 -> 406
    //   275: new 27	androidx/appcompat/widget/AppCompatSpinner$DropdownPopup
    //   278: dup
    //   279: aload_0
    //   280: aload_0
    //   281: getfield 142	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   284: aload_2
    //   285: iload_3
    //   286: invokespecial 179	androidx/appcompat/widget/AppCompatSpinner$DropdownPopup:<init>	(Landroidx/appcompat/widget/AppCompatSpinner;Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   289: astore 5
    //   291: aload_0
    //   292: getfield 142	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   295: aload_2
    //   296: getstatic 122	androidx/appcompat/R$styleable:Spinner	[I
    //   299: iload_3
    //   300: iconst_0
    //   301: invokestatic 128	androidx/appcompat/widget/TintTypedArray:obtainStyledAttributes	(Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroidx/appcompat/widget/TintTypedArray;
    //   304: astore 8
    //   306: aload_0
    //   307: aload 8
    //   309: getstatic 182	androidx/appcompat/R$styleable:Spinner_android_dropDownWidth	I
    //   312: bipush -2
    //   314: invokevirtual 185	androidx/appcompat/widget/TintTypedArray:getLayoutDimension	(II)I
    //   317: putfield 187	androidx/appcompat/widget/AppCompatSpinner:mDropDownWidth	I
    //   320: aload 5
    //   322: aload 8
    //   324: getstatic 190	androidx/appcompat/R$styleable:Spinner_android_popupBackground	I
    //   327: invokevirtual 194	androidx/appcompat/widget/TintTypedArray:getDrawable	(I)Landroid/graphics/drawable/Drawable;
    //   330: invokevirtual 198	androidx/appcompat/widget/AppCompatSpinner$DropdownPopup:setBackgroundDrawable	(Landroid/graphics/drawable/Drawable;)V
    //   333: aload 5
    //   335: aload 10
    //   337: getstatic 201	androidx/appcompat/R$styleable:Spinner_android_prompt	I
    //   340: invokevirtual 205	androidx/appcompat/widget/TintTypedArray:getString	(I)Ljava/lang/String;
    //   343: invokevirtual 209	androidx/appcompat/widget/AppCompatSpinner$DropdownPopup:setPromptText	(Ljava/lang/CharSequence;)V
    //   346: aload 8
    //   348: invokevirtual 210	androidx/appcompat/widget/TintTypedArray:recycle	()V
    //   351: aload_0
    //   352: aload 5
    //   354: putfield 212	androidx/appcompat/widget/AppCompatSpinner:mPopup	Landroidx/appcompat/widget/AppCompatSpinner$SpinnerPopup;
    //   357: aload_0
    //   358: new 8	androidx/appcompat/widget/AppCompatSpinner$1
    //   361: dup
    //   362: aload_0
    //   363: aload_0
    //   364: aload 5
    //   366: invokespecial 215	androidx/appcompat/widget/AppCompatSpinner$1:<init>	(Landroidx/appcompat/widget/AppCompatSpinner;Landroid/view/View;Landroidx/appcompat/widget/AppCompatSpinner$DropdownPopup;)V
    //   369: putfield 217	androidx/appcompat/widget/AppCompatSpinner:mForwardingListener	Landroidx/appcompat/widget/ForwardingListener;
    //   372: goto +34 -> 406
    //   375: new 21	androidx/appcompat/widget/AppCompatSpinner$DialogPopup
    //   378: dup
    //   379: aload_0
    //   380: invokespecial 220	androidx/appcompat/widget/AppCompatSpinner$DialogPopup:<init>	(Landroidx/appcompat/widget/AppCompatSpinner;)V
    //   383: astore 5
    //   385: aload_0
    //   386: aload 5
    //   388: putfield 212	androidx/appcompat/widget/AppCompatSpinner:mPopup	Landroidx/appcompat/widget/AppCompatSpinner$SpinnerPopup;
    //   391: aload 5
    //   393: aload 10
    //   395: getstatic 201	androidx/appcompat/R$styleable:Spinner_android_prompt	I
    //   398: invokevirtual 205	androidx/appcompat/widget/TintTypedArray:getString	(I)Ljava/lang/String;
    //   401: invokeinterface 221 2 0
    //   406: aload 10
    //   408: getstatic 224	androidx/appcompat/R$styleable:Spinner_android_entries	I
    //   411: invokevirtual 228	androidx/appcompat/widget/TintTypedArray:getTextArray	(I)[Ljava/lang/CharSequence;
    //   414: astore 5
    //   416: aload 5
    //   418: ifnull +28 -> 446
    //   421: new 230	android/widget/ArrayAdapter
    //   424: dup
    //   425: aload_1
    //   426: ldc -25
    //   428: aload 5
    //   430: invokespecial 234	android/widget/ArrayAdapter:<init>	(Landroid/content/Context;I[Ljava/lang/Object;)V
    //   433: astore_1
    //   434: aload_1
    //   435: getstatic 239	androidx/appcompat/R$layout:support_simple_spinner_dropdown_item	I
    //   438: invokevirtual 243	android/widget/ArrayAdapter:setDropDownViewResource	(I)V
    //   441: aload_0
    //   442: aload_1
    //   443: invokevirtual 247	androidx/appcompat/widget/AppCompatSpinner:setAdapter	(Landroid/widget/SpinnerAdapter;)V
    //   446: aload 10
    //   448: invokevirtual 210	androidx/appcompat/widget/TintTypedArray:recycle	()V
    //   451: aload_0
    //   452: iconst_1
    //   453: putfield 249	androidx/appcompat/widget/AppCompatSpinner:mPopupSet	Z
    //   456: aload_0
    //   457: getfield 251	androidx/appcompat/widget/AppCompatSpinner:mTempAdapter	Landroid/widget/SpinnerAdapter;
    //   460: astore_1
    //   461: aload_1
    //   462: ifnull +13 -> 475
    //   465: aload_0
    //   466: aload_1
    //   467: invokevirtual 247	androidx/appcompat/widget/AppCompatSpinner:setAdapter	(Landroid/widget/SpinnerAdapter;)V
    //   470: aload_0
    //   471: aconst_null
    //   472: putfield 251	androidx/appcompat/widget/AppCompatSpinner:mTempAdapter	Landroid/widget/SpinnerAdapter;
    //   475: aload_0
    //   476: getfield 135	androidx/appcompat/widget/AppCompatSpinner:mBackgroundTintHelper	Landroidx/appcompat/widget/AppCompatBackgroundHelper;
    //   479: aload_2
    //   480: iload_3
    //   481: invokevirtual 255	androidx/appcompat/widget/AppCompatBackgroundHelper:loadFromAttributes	(Landroid/util/AttributeSet;I)V
    //   484: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	485	0	this	AppCompatSpinner
    //   0	485	1	paramContext	Context
    //   0	485	2	paramAttributeSet	AttributeSet
    //   0	485	3	paramInt1	int
    //   0	485	4	paramInt2	int
    //   0	485	5	paramTheme	Resources.Theme
    //   81	105	6	i	int
    //   112	138	7	j	int
    //   124	223	8	localObject	Object
    //   136	54	9	localTypedArray	android.content.res.TypedArray
    //   209	11	9	localException	Exception
    //   36	411	10	localTintTypedArray	TintTypedArray
    // Exception table:
    //   from	to	target	type
    //   126	138	205	finally
    //   150	159	205	finally
    //   167	176	205	finally
    //   215	225	205	finally
    //   126	138	209	java/lang/Exception
    //   150	159	209	java/lang/Exception
    //   167	176	209	java/lang/Exception
  }
  
  int compatMeasureContentWidth(SpinnerAdapter paramSpinnerAdapter, Drawable paramDrawable)
  {
    if (paramSpinnerAdapter == null) {
      return 0;
    }
    int i = 0;
    View localView = null;
    int k = 0;
    int i1 = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
    int i2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
    int j = Math.max(0, getSelectedItemPosition());
    int i3 = Math.min(paramSpinnerAdapter.getCount(), j + 15);
    j = Math.max(0, j - (15 - (i3 - j)));
    while (j < i3)
    {
      int n = paramSpinnerAdapter.getItemViewType(j);
      int m = k;
      if (n != k)
      {
        m = n;
        localView = null;
      }
      localView = paramSpinnerAdapter.getView(j, localView, this);
      if (localView.getLayoutParams() == null) {
        localView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      }
      localView.measure(i1, i2);
      i = Math.max(i, localView.getMeasuredWidth());
      j++;
      k = m;
    }
    j = i;
    if (paramDrawable != null)
    {
      paramDrawable.getPadding(this.mTempRect);
      j = i + (this.mTempRect.left + this.mTempRect.right);
    }
    return j;
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.applySupportBackgroundTint();
    }
  }
  
  public int getDropDownHorizontalOffset()
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null) {
      return localSpinnerPopup.getHorizontalOffset();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownHorizontalOffset();
    }
    return 0;
  }
  
  public int getDropDownVerticalOffset()
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null) {
      return localSpinnerPopup.getVerticalOffset();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownVerticalOffset();
    }
    return 0;
  }
  
  public int getDropDownWidth()
  {
    if (this.mPopup != null) {
      return this.mDropDownWidth;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownWidth();
    }
    return 0;
  }
  
  final SpinnerPopup getInternalPopup()
  {
    return this.mPopup;
  }
  
  public Drawable getPopupBackground()
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null) {
      return localSpinnerPopup.getBackground();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getPopupBackground();
    }
    return null;
  }
  
  public Context getPopupContext()
  {
    return this.mPopupContext;
  }
  
  public CharSequence getPrompt()
  {
    Object localObject = this.mPopup;
    if (localObject != null) {
      localObject = ((SpinnerPopup)localObject).getHintText();
    } else {
      localObject = super.getPrompt();
    }
    return (CharSequence)localObject;
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((AppCompatBackgroundHelper)localObject).getSupportBackgroundTintList();
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((AppCompatBackgroundHelper)localObject).getSupportBackgroundTintMode();
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if ((localSpinnerPopup != null) && (localSpinnerPopup.isShowing())) {
      this.mPopup.dismiss();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mPopup != null) && (View.MeasureSpec.getMode(paramInt1) == Integer.MIN_VALUE))
    {
      paramInt2 = getMeasuredWidth();
      setMeasuredDimension(Math.min(Math.max(paramInt2, compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(paramInt1)), getMeasuredHeight());
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    if (paramParcelable.mShowDropdown)
    {
      paramParcelable = getViewTreeObserver();
      if (paramParcelable != null) {
        paramParcelable.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            if (!AppCompatSpinner.this.getInternalPopup().isShowing()) {
              AppCompatSpinner.this.showPopup();
            }
            ViewTreeObserver localViewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
            if (localViewTreeObserver != null) {
              if (Build.VERSION.SDK_INT >= 16) {
                AppCompatSpinner.Api16Impl.removeOnGlobalLayoutListener(localViewTreeObserver, this);
              } else {
                localViewTreeObserver.removeGlobalOnLayoutListener(this);
              }
            }
          }
        });
      }
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    SpinnerPopup localSpinnerPopup = this.mPopup;
    boolean bool;
    if ((localSpinnerPopup != null) && (localSpinnerPopup.isShowing())) {
      bool = true;
    } else {
      bool = false;
    }
    localSavedState.mShowDropdown = bool;
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ForwardingListener localForwardingListener = this.mForwardingListener;
    if ((localForwardingListener != null) && (localForwardingListener.onTouch(this, paramMotionEvent))) {
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean performClick()
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null)
    {
      if (!localSpinnerPopup.isShowing()) {
        showPopup();
      }
      return true;
    }
    return super.performClick();
  }
  
  public void setAdapter(SpinnerAdapter paramSpinnerAdapter)
  {
    if (!this.mPopupSet)
    {
      this.mTempAdapter = paramSpinnerAdapter;
      return;
    }
    super.setAdapter(paramSpinnerAdapter);
    if (this.mPopup != null)
    {
      Context localContext2 = this.mPopupContext;
      Context localContext1 = localContext2;
      if (localContext2 == null) {
        localContext1 = getContext();
      }
      this.mPopup.setAdapter(new DropDownAdapter(paramSpinnerAdapter, localContext1.getTheme()));
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.onSetBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.onSetBackgroundResource(paramInt);
    }
  }
  
  public void setDropDownHorizontalOffset(int paramInt)
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null)
    {
      localSpinnerPopup.setHorizontalOriginalOffset(paramInt);
      this.mPopup.setHorizontalOffset(paramInt);
    }
    else if (Build.VERSION.SDK_INT >= 16)
    {
      super.setDropDownHorizontalOffset(paramInt);
    }
  }
  
  public void setDropDownVerticalOffset(int paramInt)
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null) {
      localSpinnerPopup.setVerticalOffset(paramInt);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownVerticalOffset(paramInt);
    }
  }
  
  public void setDropDownWidth(int paramInt)
  {
    if (this.mPopup != null) {
      this.mDropDownWidth = paramInt;
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownWidth(paramInt);
    }
  }
  
  public void setPopupBackgroundDrawable(Drawable paramDrawable)
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null) {
      localSpinnerPopup.setBackgroundDrawable(paramDrawable);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setPopupBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setPopupBackgroundResource(int paramInt)
  {
    setPopupBackgroundDrawable(AppCompatResources.getDrawable(getPopupContext(), paramInt));
  }
  
  public void setPrompt(CharSequence paramCharSequence)
  {
    SpinnerPopup localSpinnerPopup = this.mPopup;
    if (localSpinnerPopup != null) {
      localSpinnerPopup.setPromptText(paramCharSequence);
    } else {
      super.setPrompt(paramCharSequence);
    }
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.setSupportBackgroundTintList(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.setSupportBackgroundTintMode(paramMode);
    }
  }
  
  void showPopup()
  {
    if (Build.VERSION.SDK_INT >= 17) {
      this.mPopup.show(Api17Impl.getTextDirection(this), Api17Impl.getTextAlignment(this));
    } else {
      this.mPopup.show(-1, -1);
    }
  }
  
  private static final class Api16Impl
  {
    static void removeOnGlobalLayoutListener(ViewTreeObserver paramViewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
    {
      paramViewTreeObserver.removeOnGlobalLayoutListener(paramOnGlobalLayoutListener);
    }
  }
  
  private static final class Api17Impl
  {
    static int getTextAlignment(View paramView)
    {
      return paramView.getTextAlignment();
    }
    
    static int getTextDirection(View paramView)
    {
      return paramView.getTextDirection();
    }
    
    static void setTextAlignment(View paramView, int paramInt)
    {
      paramView.setTextAlignment(paramInt);
    }
    
    static void setTextDirection(View paramView, int paramInt)
    {
      paramView.setTextDirection(paramInt);
    }
  }
  
  private static final class Api23Impl
  {
    static void setDropDownViewTheme(android.widget.ThemedSpinnerAdapter paramThemedSpinnerAdapter, Resources.Theme paramTheme)
    {
      if (paramThemedSpinnerAdapter.getDropDownViewTheme() != paramTheme) {
        paramThemedSpinnerAdapter.setDropDownViewTheme(paramTheme);
      }
    }
  }
  
  class DialogPopup
    implements AppCompatSpinner.SpinnerPopup, DialogInterface.OnClickListener
  {
    private ListAdapter mListAdapter;
    AlertDialog mPopup;
    private CharSequence mPrompt;
    
    DialogPopup() {}
    
    public void dismiss()
    {
      AlertDialog localAlertDialog = this.mPopup;
      if (localAlertDialog != null)
      {
        localAlertDialog.dismiss();
        this.mPopup = null;
      }
    }
    
    public Drawable getBackground()
    {
      return null;
    }
    
    public CharSequence getHintText()
    {
      return this.mPrompt;
    }
    
    public int getHorizontalOffset()
    {
      return 0;
    }
    
    public int getHorizontalOriginalOffset()
    {
      return 0;
    }
    
    public int getVerticalOffset()
    {
      return 0;
    }
    
    public boolean isShowing()
    {
      AlertDialog localAlertDialog = this.mPopup;
      boolean bool;
      if (localAlertDialog != null) {
        bool = localAlertDialog.isShowing();
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      AppCompatSpinner.this.setSelection(paramInt);
      if (AppCompatSpinner.this.getOnItemClickListener() != null) {
        AppCompatSpinner.this.performItemClick(null, paramInt, this.mListAdapter.getItemId(paramInt));
      }
      dismiss();
    }
    
    public void setAdapter(ListAdapter paramListAdapter)
    {
      this.mListAdapter = paramListAdapter;
    }
    
    public void setBackgroundDrawable(Drawable paramDrawable)
    {
      Log.e("AppCompatSpinner", "Cannot set popup background for MODE_DIALOG, ignoring");
    }
    
    public void setHorizontalOffset(int paramInt)
    {
      Log.e("AppCompatSpinner", "Cannot set horizontal offset for MODE_DIALOG, ignoring");
    }
    
    public void setHorizontalOriginalOffset(int paramInt)
    {
      Log.e("AppCompatSpinner", "Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
    }
    
    public void setPromptText(CharSequence paramCharSequence)
    {
      this.mPrompt = paramCharSequence;
    }
    
    public void setVerticalOffset(int paramInt)
    {
      Log.e("AppCompatSpinner", "Cannot set vertical offset for MODE_DIALOG, ignoring");
    }
    
    public void show(int paramInt1, int paramInt2)
    {
      if (this.mListAdapter == null) {
        return;
      }
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(AppCompatSpinner.this.getPopupContext());
      Object localObject = this.mPrompt;
      if (localObject != null) {
        localBuilder.setTitle((CharSequence)localObject);
      }
      localObject = localBuilder.setSingleChoiceItems(this.mListAdapter, AppCompatSpinner.this.getSelectedItemPosition(), this).create();
      this.mPopup = ((AlertDialog)localObject);
      localObject = ((AlertDialog)localObject).getListView();
      if (Build.VERSION.SDK_INT >= 17)
      {
        AppCompatSpinner.Api17Impl.setTextDirection((View)localObject, paramInt1);
        AppCompatSpinner.Api17Impl.setTextAlignment((View)localObject, paramInt2);
      }
      this.mPopup.show();
    }
  }
  
  private static class DropDownAdapter
    implements ListAdapter, SpinnerAdapter
  {
    private SpinnerAdapter mAdapter;
    private ListAdapter mListAdapter;
    
    public DropDownAdapter(SpinnerAdapter paramSpinnerAdapter, Resources.Theme paramTheme)
    {
      this.mAdapter = paramSpinnerAdapter;
      if ((paramSpinnerAdapter instanceof ListAdapter)) {
        this.mListAdapter = ((ListAdapter)paramSpinnerAdapter);
      }
      if (paramTheme != null) {
        if ((Build.VERSION.SDK_INT >= 23) && ((paramSpinnerAdapter instanceof android.widget.ThemedSpinnerAdapter)))
        {
          AppCompatSpinner.Api23Impl.setDropDownViewTheme((android.widget.ThemedSpinnerAdapter)paramSpinnerAdapter, paramTheme);
        }
        else if ((paramSpinnerAdapter instanceof ThemedSpinnerAdapter))
        {
          paramSpinnerAdapter = (ThemedSpinnerAdapter)paramSpinnerAdapter;
          if (paramSpinnerAdapter.getDropDownViewTheme() == null) {
            paramSpinnerAdapter.setDropDownViewTheme(paramTheme);
          }
        }
      }
    }
    
    public boolean areAllItemsEnabled()
    {
      ListAdapter localListAdapter = this.mListAdapter;
      if (localListAdapter != null) {
        return localListAdapter.areAllItemsEnabled();
      }
      return true;
    }
    
    public int getCount()
    {
      SpinnerAdapter localSpinnerAdapter = this.mAdapter;
      int i;
      if (localSpinnerAdapter == null) {
        i = 0;
      } else {
        i = localSpinnerAdapter.getCount();
      }
      return i;
    }
    
    public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      SpinnerAdapter localSpinnerAdapter = this.mAdapter;
      if (localSpinnerAdapter == null) {
        paramView = null;
      } else {
        paramView = localSpinnerAdapter.getDropDownView(paramInt, paramView, paramViewGroup);
      }
      return paramView;
    }
    
    public Object getItem(int paramInt)
    {
      Object localObject = this.mAdapter;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = ((SpinnerAdapter)localObject).getItem(paramInt);
      }
      return localObject;
    }
    
    public long getItemId(int paramInt)
    {
      SpinnerAdapter localSpinnerAdapter = this.mAdapter;
      long l;
      if (localSpinnerAdapter == null) {
        l = -1L;
      } else {
        l = localSpinnerAdapter.getItemId(paramInt);
      }
      return l;
    }
    
    public int getItemViewType(int paramInt)
    {
      return 0;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      return getDropDownView(paramInt, paramView, paramViewGroup);
    }
    
    public int getViewTypeCount()
    {
      return 1;
    }
    
    public boolean hasStableIds()
    {
      SpinnerAdapter localSpinnerAdapter = this.mAdapter;
      boolean bool;
      if ((localSpinnerAdapter != null) && (localSpinnerAdapter.hasStableIds())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isEmpty()
    {
      boolean bool;
      if (getCount() == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isEnabled(int paramInt)
    {
      ListAdapter localListAdapter = this.mListAdapter;
      if (localListAdapter != null) {
        return localListAdapter.isEnabled(paramInt);
      }
      return true;
    }
    
    public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      SpinnerAdapter localSpinnerAdapter = this.mAdapter;
      if (localSpinnerAdapter != null) {
        localSpinnerAdapter.registerDataSetObserver(paramDataSetObserver);
      }
    }
    
    public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      SpinnerAdapter localSpinnerAdapter = this.mAdapter;
      if (localSpinnerAdapter != null) {
        localSpinnerAdapter.unregisterDataSetObserver(paramDataSetObserver);
      }
    }
  }
  
  class DropdownPopup
    extends ListPopupWindow
    implements AppCompatSpinner.SpinnerPopup
  {
    ListAdapter mAdapter;
    private CharSequence mHintText;
    private int mOriginalHorizontalOffset;
    private final Rect mVisibleRect = new Rect();
    
    public DropdownPopup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
      setAnchorView(AppCompatSpinner.this);
      setModal(true);
      setPromptPosition(0);
      setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          AppCompatSpinner.this.setSelection(paramAnonymousInt);
          if (AppCompatSpinner.this.getOnItemClickListener() != null) {
            AppCompatSpinner.this.performItemClick(paramAnonymousView, paramAnonymousInt, AppCompatSpinner.DropdownPopup.this.mAdapter.getItemId(paramAnonymousInt));
          }
          AppCompatSpinner.DropdownPopup.this.dismiss();
        }
      });
    }
    
    void computeContentWidth()
    {
      Object localObject = getBackground();
      int i = 0;
      if (localObject != null)
      {
        ((Drawable)localObject).getPadding(AppCompatSpinner.this.mTempRect);
        if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
          i = AppCompatSpinner.this.mTempRect.right;
        } else {
          i = -AppCompatSpinner.this.mTempRect.left;
        }
      }
      else
      {
        localObject = AppCompatSpinner.this.mTempRect;
        AppCompatSpinner.this.mTempRect.right = 0;
        ((Rect)localObject).left = 0;
      }
      int i2 = AppCompatSpinner.this.getPaddingLeft();
      int i1 = AppCompatSpinner.this.getPaddingRight();
      int n = AppCompatSpinner.this.getWidth();
      if (AppCompatSpinner.this.mDropDownWidth == -2)
      {
        int m = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, getBackground());
        int k = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left - AppCompatSpinner.this.mTempRect.right;
        int j = m;
        if (m > k) {
          j = k;
        }
        setContentWidth(Math.max(j, n - i2 - i1));
      }
      else if (AppCompatSpinner.this.mDropDownWidth == -1)
      {
        setContentWidth(n - i2 - i1);
      }
      else
      {
        setContentWidth(AppCompatSpinner.this.mDropDownWidth);
      }
      if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
        i += n - i1 - getWidth() - getHorizontalOriginalOffset();
      } else {
        i += getHorizontalOriginalOffset() + i2;
      }
      setHorizontalOffset(i);
    }
    
    public CharSequence getHintText()
    {
      return this.mHintText;
    }
    
    public int getHorizontalOriginalOffset()
    {
      return this.mOriginalHorizontalOffset;
    }
    
    boolean isVisibleToUser(View paramView)
    {
      boolean bool;
      if ((ViewCompat.isAttachedToWindow(paramView)) && (paramView.getGlobalVisibleRect(this.mVisibleRect))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void setAdapter(ListAdapter paramListAdapter)
    {
      super.setAdapter(paramListAdapter);
      this.mAdapter = paramListAdapter;
    }
    
    public void setHorizontalOriginalOffset(int paramInt)
    {
      this.mOriginalHorizontalOffset = paramInt;
    }
    
    public void setPromptText(CharSequence paramCharSequence)
    {
      this.mHintText = paramCharSequence;
    }
    
    public void show(int paramInt1, int paramInt2)
    {
      boolean bool = isShowing();
      computeContentWidth();
      setInputMethodMode(2);
      super.show();
      Object localObject = getListView();
      ((ListView)localObject).setChoiceMode(1);
      if (Build.VERSION.SDK_INT >= 17)
      {
        AppCompatSpinner.Api17Impl.setTextDirection((View)localObject, paramInt1);
        AppCompatSpinner.Api17Impl.setTextAlignment((View)localObject, paramInt2);
      }
      setSelection(AppCompatSpinner.this.getSelectedItemPosition());
      if (bool) {
        return;
      }
      localObject = AppCompatSpinner.this.getViewTreeObserver();
      if (localObject != null)
      {
        final ViewTreeObserver.OnGlobalLayoutListener local2 = new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            AppCompatSpinner.DropdownPopup localDropdownPopup = AppCompatSpinner.DropdownPopup.this;
            if (!localDropdownPopup.isVisibleToUser(localDropdownPopup.this$0))
            {
              AppCompatSpinner.DropdownPopup.this.dismiss();
            }
            else
            {
              AppCompatSpinner.DropdownPopup.this.computeContentWidth();
              AppCompatSpinner.DropdownPopup.this.show();
            }
          }
        };
        ((ViewTreeObserver)localObject).addOnGlobalLayoutListener(local2);
        setOnDismissListener(new PopupWindow.OnDismissListener()
        {
          public void onDismiss()
          {
            ViewTreeObserver localViewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
            if (localViewTreeObserver != null) {
              localViewTreeObserver.removeGlobalOnLayoutListener(local2);
            }
          }
        });
      }
    }
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public AppCompatSpinner.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new AppCompatSpinner.SavedState(paramAnonymousParcel);
      }
      
      public AppCompatSpinner.SavedState[] newArray(int paramAnonymousInt)
      {
        return new AppCompatSpinner.SavedState[paramAnonymousInt];
      }
    };
    boolean mShowDropdown;
    
    SavedState(Parcel paramParcel)
    {
      super();
      boolean bool;
      if (paramParcel.readByte() != 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.mShowDropdown = bool;
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeByte((byte)this.mShowDropdown);
    }
  }
  
  static abstract interface SpinnerPopup
  {
    public abstract void dismiss();
    
    public abstract Drawable getBackground();
    
    public abstract CharSequence getHintText();
    
    public abstract int getHorizontalOffset();
    
    public abstract int getHorizontalOriginalOffset();
    
    public abstract int getVerticalOffset();
    
    public abstract boolean isShowing();
    
    public abstract void setAdapter(ListAdapter paramListAdapter);
    
    public abstract void setBackgroundDrawable(Drawable paramDrawable);
    
    public abstract void setHorizontalOffset(int paramInt);
    
    public abstract void setHorizontalOriginalOffset(int paramInt);
    
    public abstract void setPromptText(CharSequence paramCharSequence);
    
    public abstract void setVerticalOffset(int paramInt);
    
    public abstract void show(int paramInt1, int paramInt2);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatSpinner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */