package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ToolbarUtils
{
  private static final Comparator<View> VIEW_TOP_COMPARATOR = new Comparator()
  {
    public int compare(View paramAnonymousView1, View paramAnonymousView2)
    {
      return paramAnonymousView1.getTop() - paramAnonymousView2.getTop();
    }
  };
  
  public static ActionMenuItemView getActionMenuItemView(Toolbar paramToolbar, int paramInt)
  {
    paramToolbar = getActionMenuView(paramToolbar);
    if (paramToolbar != null) {
      for (int i = 0; i < paramToolbar.getChildCount(); i++)
      {
        Object localObject = paramToolbar.getChildAt(i);
        if ((localObject instanceof ActionMenuItemView))
        {
          localObject = (ActionMenuItemView)localObject;
          if (((ActionMenuItemView)localObject).getItemData().getItemId() == paramInt) {
            return (ActionMenuItemView)localObject;
          }
        }
      }
    }
    return null;
  }
  
  public static ActionMenuView getActionMenuView(Toolbar paramToolbar)
  {
    for (int i = 0; i < paramToolbar.getChildCount(); i++)
    {
      View localView = paramToolbar.getChildAt(i);
      if ((localView instanceof ActionMenuView)) {
        return (ActionMenuView)localView;
      }
    }
    return null;
  }
  
  private static ImageView getImageView(Toolbar paramToolbar, Drawable paramDrawable)
  {
    if (paramDrawable == null) {
      return null;
    }
    for (int i = 0; i < paramToolbar.getChildCount(); i++)
    {
      Object localObject = paramToolbar.getChildAt(i);
      if ((localObject instanceof ImageView))
      {
        localObject = (ImageView)localObject;
        Drawable localDrawable = ((ImageView)localObject).getDrawable();
        if ((localDrawable != null) && (localDrawable.getConstantState() != null) && (localDrawable.getConstantState().equals(paramDrawable.getConstantState()))) {
          return (ImageView)localObject;
        }
      }
    }
    return null;
  }
  
  public static ImageView getLogoImageView(Toolbar paramToolbar)
  {
    return getImageView(paramToolbar, paramToolbar.getLogo());
  }
  
  public static ImageButton getNavigationIconButton(Toolbar paramToolbar)
  {
    Drawable localDrawable = paramToolbar.getNavigationIcon();
    if (localDrawable == null) {
      return null;
    }
    for (int i = 0; i < paramToolbar.getChildCount(); i++)
    {
      Object localObject = paramToolbar.getChildAt(i);
      if ((localObject instanceof ImageButton))
      {
        localObject = (ImageButton)localObject;
        if (((ImageButton)localObject).getDrawable() == localDrawable) {
          return (ImageButton)localObject;
        }
      }
    }
    return null;
  }
  
  public static View getSecondaryActionMenuItemView(Toolbar paramToolbar)
  {
    paramToolbar = getActionMenuView(paramToolbar);
    if ((paramToolbar != null) && (paramToolbar.getChildCount() > 1)) {
      return paramToolbar.getChildAt(0);
    }
    return null;
  }
  
  public static TextView getSubtitleTextView(Toolbar paramToolbar)
  {
    paramToolbar = getTextViewsWithText(paramToolbar, paramToolbar.getSubtitle());
    if (paramToolbar.isEmpty()) {
      paramToolbar = null;
    } else {
      paramToolbar = (TextView)Collections.max(paramToolbar, VIEW_TOP_COMPARATOR);
    }
    return paramToolbar;
  }
  
  private static List<TextView> getTextViewsWithText(Toolbar paramToolbar, CharSequence paramCharSequence)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramToolbar.getChildCount(); i++)
    {
      Object localObject = paramToolbar.getChildAt(i);
      if ((localObject instanceof TextView))
      {
        localObject = (TextView)localObject;
        if (TextUtils.equals(((TextView)localObject).getText(), paramCharSequence)) {
          localArrayList.add(localObject);
        }
      }
    }
    return localArrayList;
  }
  
  public static TextView getTitleTextView(Toolbar paramToolbar)
  {
    paramToolbar = getTextViewsWithText(paramToolbar, paramToolbar.getTitle());
    if (paramToolbar.isEmpty()) {
      paramToolbar = null;
    } else {
      paramToolbar = (TextView)Collections.min(paramToolbar, VIEW_TOP_COMPARATOR);
    }
    return paramToolbar;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/ToolbarUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */