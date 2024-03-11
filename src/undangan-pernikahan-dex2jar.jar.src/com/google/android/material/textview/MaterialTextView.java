package com.google.android.material.textview;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.R.attr;
import com.google.android.material.R.styleable;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialTextView
  extends AppCompatTextView
{
  public MaterialTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842884);
  }
  
  public MaterialTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public MaterialTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt1, paramInt2), paramAttributeSet, paramInt1);
    Context localContext = getContext();
    if (canApplyTextAppearanceLineHeight(localContext))
    {
      paramContext = localContext.getTheme();
      if (!viewAttrsHasLineHeight(localContext, paramContext, paramAttributeSet, paramInt1, paramInt2))
      {
        paramInt1 = findViewAppearanceResourceId(paramContext, paramAttributeSet, paramInt1, paramInt2);
        if (paramInt1 != -1) {
          applyLineHeightFromViewAppearance(paramContext, paramInt1);
        }
      }
    }
  }
  
  private void applyLineHeightFromViewAppearance(Resources.Theme paramTheme, int paramInt)
  {
    paramTheme = paramTheme.obtainStyledAttributes(paramInt, R.styleable.MaterialTextAppearance);
    paramInt = readFirstAvailableDimension(getContext(), paramTheme, new int[] { R.styleable.MaterialTextAppearance_android_lineHeight, R.styleable.MaterialTextAppearance_lineHeight });
    paramTheme.recycle();
    if (paramInt >= 0) {
      setLineHeight(paramInt);
    }
  }
  
  private static boolean canApplyTextAppearanceLineHeight(Context paramContext)
  {
    return MaterialAttributes.resolveBoolean(paramContext, R.attr.textAppearanceLineHeightEnabled, true);
  }
  
  private static int findViewAppearanceResourceId(Resources.Theme paramTheme, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramTheme = paramTheme.obtainStyledAttributes(paramAttributeSet, R.styleable.MaterialTextView, paramInt1, paramInt2);
    paramInt1 = paramTheme.getResourceId(R.styleable.MaterialTextView_android_textAppearance, -1);
    paramTheme.recycle();
    return paramInt1;
  }
  
  private static int readFirstAvailableDimension(Context paramContext, TypedArray paramTypedArray, int... paramVarArgs)
  {
    int j = -1;
    for (int i = 0; (i < paramVarArgs.length) && (j < 0); i++) {
      j = MaterialResources.getDimensionPixelSize(paramContext, paramTypedArray, paramVarArgs[i], -1);
    }
    return j;
  }
  
  private static boolean viewAttrsHasLineHeight(Context paramContext, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramTheme = paramTheme.obtainStyledAttributes(paramAttributeSet, R.styleable.MaterialTextView, paramInt1, paramInt2);
    paramInt1 = R.styleable.MaterialTextView_android_lineHeight;
    boolean bool = false;
    paramInt1 = readFirstAvailableDimension(paramContext, paramTheme, new int[] { paramInt1, R.styleable.MaterialTextView_lineHeight });
    paramTheme.recycle();
    if (paramInt1 != -1) {
      bool = true;
    }
    return bool;
  }
  
  public void setTextAppearance(Context paramContext, int paramInt)
  {
    super.setTextAppearance(paramContext, paramInt);
    if (canApplyTextAppearanceLineHeight(paramContext)) {
      applyLineHeightFromViewAppearance(paramContext.getTheme(), paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textview/MaterialTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */