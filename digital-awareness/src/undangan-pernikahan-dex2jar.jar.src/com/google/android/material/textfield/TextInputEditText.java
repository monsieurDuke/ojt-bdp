package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class TextInputEditText
  extends AppCompatEditText
{
  private final Rect parentRect = new Rect();
  private boolean textInputLayoutFocusedRectEnabled;
  
  public TextInputEditText(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TextInputEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.editTextStyle);
  }
  
  public TextInputEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, 0), paramAttributeSet, paramInt);
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.TextInputEditText, paramInt, R.style.Widget_Design_TextInputEditText, new int[0]);
    setTextInputLayoutFocusedRectEnabled(paramContext.getBoolean(R.styleable.TextInputEditText_textInputLayoutFocusedRectEnabled, false));
    paramContext.recycle();
  }
  
  private String getAccessibilityNodeInfoText(TextInputLayout paramTextInputLayout)
  {
    Object localObject = getText();
    paramTextInputLayout = paramTextInputLayout.getHint();
    boolean bool1 = TextUtils.isEmpty((CharSequence)localObject);
    boolean bool2 = TextUtils.isEmpty(paramTextInputLayout);
    String str = "";
    if ((bool2 ^ true)) {
      paramTextInputLayout = paramTextInputLayout.toString();
    } else {
      paramTextInputLayout = "";
    }
    if ((bool1 ^ true))
    {
      localObject = new StringBuilder().append(localObject);
      if (!TextUtils.isEmpty(paramTextInputLayout)) {
        str = ", " + paramTextInputLayout;
      }
      return str;
    }
    if (!TextUtils.isEmpty(paramTextInputLayout)) {
      return paramTextInputLayout;
    }
    return "";
  }
  
  private CharSequence getHintFromLayout()
  {
    Object localObject = getTextInputLayout();
    if (localObject != null) {
      localObject = ((TextInputLayout)localObject).getHint();
    } else {
      localObject = null;
    }
    return (CharSequence)localObject;
  }
  
  private TextInputLayout getTextInputLayout()
  {
    for (ViewParent localViewParent = getParent(); (localViewParent instanceof View); localViewParent = localViewParent.getParent()) {
      if ((localViewParent instanceof TextInputLayout)) {
        return (TextInputLayout)localViewParent;
      }
    }
    return null;
  }
  
  private boolean shouldUseTextInputLayoutFocusedRect(TextInputLayout paramTextInputLayout)
  {
    boolean bool;
    if ((paramTextInputLayout != null) && (this.textInputLayoutFocusedRectEnabled)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void getFocusedRect(Rect paramRect)
  {
    super.getFocusedRect(paramRect);
    TextInputLayout localTextInputLayout = getTextInputLayout();
    if ((shouldUseTextInputLayoutFocusedRect(localTextInputLayout)) && (paramRect != null))
    {
      localTextInputLayout.getFocusedRect(this.parentRect);
      paramRect.bottom = this.parentRect.bottom;
    }
  }
  
  public boolean getGlobalVisibleRect(Rect paramRect, Point paramPoint)
  {
    TextInputLayout localTextInputLayout = getTextInputLayout();
    boolean bool;
    if (shouldUseTextInputLayoutFocusedRect(localTextInputLayout)) {
      bool = localTextInputLayout.getGlobalVisibleRect(paramRect, paramPoint);
    } else {
      bool = super.getGlobalVisibleRect(paramRect, paramPoint);
    }
    return bool;
  }
  
  public CharSequence getHint()
  {
    TextInputLayout localTextInputLayout = getTextInputLayout();
    if ((localTextInputLayout != null) && (localTextInputLayout.isProvidingHint())) {
      return localTextInputLayout.getHint();
    }
    return super.getHint();
  }
  
  public boolean isTextInputLayoutFocusedRectEnabled()
  {
    return this.textInputLayoutFocusedRectEnabled;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    TextInputLayout localTextInputLayout = getTextInputLayout();
    if ((localTextInputLayout != null) && (localTextInputLayout.isProvidingHint()) && (super.getHint() == null) && (ManufacturerUtils.isMeizuDevice())) {
      setHint("");
    }
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    InputConnection localInputConnection = super.onCreateInputConnection(paramEditorInfo);
    if ((localInputConnection != null) && (paramEditorInfo.hintText == null)) {
      paramEditorInfo.hintText = getHintFromLayout();
    }
    return localInputConnection;
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    TextInputLayout localTextInputLayout = getTextInputLayout();
    if ((Build.VERSION.SDK_INT < 23) && (localTextInputLayout != null)) {
      paramAccessibilityNodeInfo.setText(getAccessibilityNodeInfoText(localTextInputLayout));
    }
  }
  
  public boolean requestRectangleOnScreen(Rect paramRect)
  {
    TextInputLayout localTextInputLayout = getTextInputLayout();
    if ((shouldUseTextInputLayoutFocusedRect(localTextInputLayout)) && (paramRect != null))
    {
      int j = localTextInputLayout.getHeight();
      int i = getHeight();
      this.parentRect.set(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom + (j - i));
      return super.requestRectangleOnScreen(this.parentRect);
    }
    return super.requestRectangleOnScreen(paramRect);
  }
  
  public void setTextInputLayoutFocusedRectEnabled(boolean paramBoolean)
  {
    this.textInputLayoutFocusedRectEnabled = paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/TextInputEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */