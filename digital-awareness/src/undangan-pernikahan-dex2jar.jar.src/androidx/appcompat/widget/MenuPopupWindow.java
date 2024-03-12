package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import java.lang.reflect.Method;

public class MenuPopupWindow
  extends ListPopupWindow
  implements MenuItemHoverListener
{
  private static final String TAG = "MenuPopupWindow";
  private static Method sSetTouchModalMethod;
  private MenuItemHoverListener mHoverListener;
  
  static
  {
    try
    {
      if (Build.VERSION.SDK_INT <= 28) {
        sSetTouchModalMethod = PopupWindow.class.getDeclaredMethod("setTouchModal", new Class[] { Boolean.TYPE });
      }
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
    }
  }
  
  public MenuPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  DropDownListView createDropDownListView(Context paramContext, boolean paramBoolean)
  {
    paramContext = new MenuDropDownListView(paramContext, paramBoolean);
    paramContext.setHoverListener(this);
    return paramContext;
  }
  
  public void onItemHoverEnter(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    MenuItemHoverListener localMenuItemHoverListener = this.mHoverListener;
    if (localMenuItemHoverListener != null) {
      localMenuItemHoverListener.onItemHoverEnter(paramMenuBuilder, paramMenuItem);
    }
  }
  
  public void onItemHoverExit(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    MenuItemHoverListener localMenuItemHoverListener = this.mHoverListener;
    if (localMenuItemHoverListener != null) {
      localMenuItemHoverListener.onItemHoverExit(paramMenuBuilder, paramMenuItem);
    }
  }
  
  public void setEnterTransition(Object paramObject)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      Api23Impl.setEnterTransition(this.mPopup, (Transition)paramObject);
    }
  }
  
  public void setExitTransition(Object paramObject)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      Api23Impl.setExitTransition(this.mPopup, (Transition)paramObject);
    }
  }
  
  public void setHoverListener(MenuItemHoverListener paramMenuItemHoverListener)
  {
    this.mHoverListener = paramMenuItemHoverListener;
  }
  
  public void setTouchModal(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT <= 28)
    {
      Method localMethod = sSetTouchModalMethod;
      if (localMethod != null) {
        try
        {
          localMethod.invoke(this.mPopup, new Object[] { Boolean.valueOf(paramBoolean) });
        }
        catch (Exception localException)
        {
          Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
        }
      }
    }
    else
    {
      Api29Impl.setTouchModal(this.mPopup, paramBoolean);
    }
  }
  
  static class Api23Impl
  {
    static void setEnterTransition(PopupWindow paramPopupWindow, Transition paramTransition)
    {
      paramPopupWindow.setEnterTransition(paramTransition);
    }
    
    static void setExitTransition(PopupWindow paramPopupWindow, Transition paramTransition)
    {
      paramPopupWindow.setExitTransition(paramTransition);
    }
  }
  
  static class Api29Impl
  {
    static void setTouchModal(PopupWindow paramPopupWindow, boolean paramBoolean)
    {
      paramPopupWindow.setTouchModal(paramBoolean);
    }
  }
  
  public static class MenuDropDownListView
    extends DropDownListView
  {
    final int mAdvanceKey;
    private MenuItemHoverListener mHoverListener;
    private MenuItem mHoveredMenuItem;
    final int mRetreatKey;
    
    public MenuDropDownListView(Context paramContext, boolean paramBoolean)
    {
      super(paramBoolean);
      paramContext = paramContext.getResources().getConfiguration();
      if ((Build.VERSION.SDK_INT >= 17) && (1 == Api17Impl.getLayoutDirection(paramContext)))
      {
        this.mAdvanceKey = 21;
        this.mRetreatKey = 22;
      }
      else
      {
        this.mAdvanceKey = 22;
        this.mRetreatKey = 21;
      }
    }
    
    public void clearSelection()
    {
      setSelection(-1);
    }
    
    public boolean onHoverEvent(MotionEvent paramMotionEvent)
    {
      if (this.mHoverListener != null)
      {
        Object localObject1 = getAdapter();
        int i;
        Object localObject2;
        if ((localObject1 instanceof HeaderViewListAdapter))
        {
          localObject1 = (HeaderViewListAdapter)localObject1;
          i = ((HeaderViewListAdapter)localObject1).getHeadersCount();
          localObject2 = (MenuAdapter)((HeaderViewListAdapter)localObject1).getWrappedAdapter();
        }
        else
        {
          i = 0;
          localObject2 = (MenuAdapter)localObject1;
        }
        MenuItem localMenuItem = null;
        localObject1 = localMenuItem;
        if (paramMotionEvent.getAction() != 10)
        {
          int j = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
          localObject1 = localMenuItem;
          if (j != -1)
          {
            i = j - i;
            localObject1 = localMenuItem;
            if (i >= 0)
            {
              localObject1 = localMenuItem;
              if (i < ((MenuAdapter)localObject2).getCount()) {
                localObject1 = ((MenuAdapter)localObject2).getItem(i);
              }
            }
          }
        }
        localMenuItem = this.mHoveredMenuItem;
        if (localMenuItem != localObject1)
        {
          localObject2 = ((MenuAdapter)localObject2).getAdapterMenu();
          if (localMenuItem != null) {
            this.mHoverListener.onItemHoverExit((MenuBuilder)localObject2, localMenuItem);
          }
          this.mHoveredMenuItem = ((MenuItem)localObject1);
          if (localObject1 != null) {
            this.mHoverListener.onItemHoverEnter((MenuBuilder)localObject2, (MenuItem)localObject1);
          }
        }
      }
      return super.onHoverEvent(paramMotionEvent);
    }
    
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
      ListMenuItemView localListMenuItemView = (ListMenuItemView)getSelectedView();
      if ((localListMenuItemView != null) && (paramInt == this.mAdvanceKey))
      {
        if ((localListMenuItemView.isEnabled()) && (localListMenuItemView.getItemData().hasSubMenu())) {
          performItemClick(localListMenuItemView, getSelectedItemPosition(), getSelectedItemId());
        }
        return true;
      }
      if ((localListMenuItemView != null) && (paramInt == this.mRetreatKey))
      {
        setSelection(-1);
        paramKeyEvent = getAdapter();
        if ((paramKeyEvent instanceof HeaderViewListAdapter)) {
          paramKeyEvent = (MenuAdapter)((HeaderViewListAdapter)paramKeyEvent).getWrappedAdapter();
        } else {
          paramKeyEvent = (MenuAdapter)paramKeyEvent;
        }
        paramKeyEvent.getAdapterMenu().close(false);
        return true;
      }
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    
    public void setHoverListener(MenuItemHoverListener paramMenuItemHoverListener)
    {
      this.mHoverListener = paramMenuItemHoverListener;
    }
    
    static class Api17Impl
    {
      static int getLayoutDirection(Configuration paramConfiguration)
      {
        return paramConfiguration.getLayoutDirection();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/MenuPopupWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */