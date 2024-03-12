package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.R.attr;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.widget.ListViewAutoScrollHelper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DropDownListView
  extends ListView
{
  public static final int INVALID_POSITION = -1;
  public static final int NO_POSITION = -1;
  private ViewPropertyAnimatorCompat mClickAnimation;
  private boolean mDrawsInPressedState;
  private boolean mHijackFocus;
  private Field mIsChildViewEnabled;
  private boolean mListSelectionHidden;
  private int mMotionPosition;
  ResolveHoverRunnable mResolveHoverRunnable;
  private ListViewAutoScrollHelper mScrollHelper;
  private int mSelectionBottomPadding = 0;
  private int mSelectionLeftPadding = 0;
  private int mSelectionRightPadding = 0;
  private int mSelectionTopPadding = 0;
  private GateKeeperDrawable mSelector;
  private final Rect mSelectorRect = new Rect();
  
  DropDownListView(Context paramContext, boolean paramBoolean)
  {
    super(paramContext, null, R.attr.dropDownListViewStyle);
    this.mHijackFocus = paramBoolean;
    setCacheColorHint(0);
    try
    {
      paramContext = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
      this.mIsChildViewEnabled = paramContext;
      paramContext.setAccessible(true);
    }
    catch (NoSuchFieldException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  private void clearPressedItem()
  {
    this.mDrawsInPressedState = false;
    setPressed(false);
    drawableStateChanged();
    Object localObject = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
    if (localObject != null) {
      ((View)localObject).setPressed(false);
    }
    localObject = this.mClickAnimation;
    if (localObject != null)
    {
      ((ViewPropertyAnimatorCompat)localObject).cancel();
      this.mClickAnimation = null;
    }
  }
  
  private void clickPressedItem(View paramView, int paramInt)
  {
    performItemClick(paramView, paramInt, getItemIdAtPosition(paramInt));
  }
  
  private void drawSelectorCompat(Canvas paramCanvas)
  {
    if (!this.mSelectorRect.isEmpty())
    {
      Drawable localDrawable = getSelector();
      if (localDrawable != null)
      {
        localDrawable.setBounds(this.mSelectorRect);
        localDrawable.draw(paramCanvas);
      }
    }
  }
  
  private void positionSelectorCompat(int paramInt, View paramView)
  {
    Rect localRect = this.mSelectorRect;
    localRect.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
    localRect.left -= this.mSelectionLeftPadding;
    localRect.top -= this.mSelectionTopPadding;
    localRect.right += this.mSelectionRightPadding;
    localRect.bottom += this.mSelectionBottomPadding;
    try
    {
      boolean bool = this.mIsChildViewEnabled.getBoolean(this);
      if (paramView.isEnabled() != bool)
      {
        paramView = this.mIsChildViewEnabled;
        if (!bool) {
          bool = true;
        } else {
          bool = false;
        }
        paramView.set(this, Boolean.valueOf(bool));
        if (paramInt != -1) {
          refreshDrawableState();
        }
      }
    }
    catch (IllegalAccessException paramView)
    {
      paramView.printStackTrace();
    }
  }
  
  private void positionSelectorLikeFocusCompat(int paramInt, View paramView)
  {
    Drawable localDrawable = getSelector();
    boolean bool = true;
    int i;
    if ((localDrawable != null) && (paramInt != -1)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      localDrawable.setVisible(false, false);
    }
    positionSelectorCompat(paramInt, paramView);
    if (i != 0)
    {
      paramView = this.mSelectorRect;
      float f2 = paramView.exactCenterX();
      float f1 = paramView.exactCenterY();
      if (getVisibility() != 0) {
        bool = false;
      }
      localDrawable.setVisible(bool, false);
      DrawableCompat.setHotspot(localDrawable, f2, f1);
    }
  }
  
  private void positionSelectorLikeTouchCompat(int paramInt, View paramView, float paramFloat1, float paramFloat2)
  {
    positionSelectorLikeFocusCompat(paramInt, paramView);
    paramView = getSelector();
    if ((paramView != null) && (paramInt != -1)) {
      DrawableCompat.setHotspot(paramView, paramFloat1, paramFloat2);
    }
  }
  
  private void setPressedItem(View paramView, int paramInt, float paramFloat1, float paramFloat2)
  {
    this.mDrawsInPressedState = true;
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.drawableHotspotChanged(this, paramFloat1, paramFloat2);
    }
    if (!isPressed()) {
      setPressed(true);
    }
    layoutChildren();
    int i = this.mMotionPosition;
    if (i != -1)
    {
      View localView = getChildAt(i - getFirstVisiblePosition());
      if ((localView != null) && (localView != paramView) && (localView.isPressed())) {
        localView.setPressed(false);
      }
    }
    this.mMotionPosition = paramInt;
    float f2 = paramView.getLeft();
    float f1 = paramView.getTop();
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.drawableHotspotChanged(paramView, paramFloat1 - f2, paramFloat2 - f1);
    }
    if (!paramView.isPressed()) {
      paramView.setPressed(true);
    }
    positionSelectorLikeTouchCompat(paramInt, paramView, paramFloat1, paramFloat2);
    setSelectorEnabled(false);
    refreshDrawableState();
  }
  
  private void setSelectorEnabled(boolean paramBoolean)
  {
    GateKeeperDrawable localGateKeeperDrawable = this.mSelector;
    if (localGateKeeperDrawable != null) {
      localGateKeeperDrawable.setEnabled(paramBoolean);
    }
  }
  
  private boolean touchModeDrawsInPressedStateCompat()
  {
    return this.mDrawsInPressedState;
  }
  
  private void updateSelectorStateCompat()
  {
    Drawable localDrawable = getSelector();
    if ((localDrawable != null) && (touchModeDrawsInPressedStateCompat()) && (isPressed())) {
      localDrawable.setState(getDrawableState());
    }
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    drawSelectorCompat(paramCanvas);
    super.dispatchDraw(paramCanvas);
  }
  
  protected void drawableStateChanged()
  {
    if (this.mResolveHoverRunnable != null) {
      return;
    }
    super.drawableStateChanged();
    setSelectorEnabled(true);
    updateSelectorStateCompat();
  }
  
  public boolean hasFocus()
  {
    boolean bool;
    if ((!this.mHijackFocus) && (!super.hasFocus())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean hasWindowFocus()
  {
    boolean bool;
    if ((!this.mHijackFocus) && (!super.hasWindowFocus())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isFocused()
  {
    boolean bool;
    if ((!this.mHijackFocus) && (!super.isFocused())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isInTouchMode()
  {
    boolean bool;
    if (((this.mHijackFocus) && (this.mListSelectionHidden)) || (super.isInTouchMode())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int lookForSelectablePosition(int paramInt, boolean paramBoolean)
  {
    ListAdapter localListAdapter = getAdapter();
    if ((localListAdapter != null) && (!isInTouchMode()))
    {
      int j = localListAdapter.getCount();
      if (!getAdapter().areAllItemsEnabled())
      {
        int i;
        if (paramBoolean) {
          for (paramInt = Math.max(0, paramInt);; paramInt++)
          {
            i = paramInt;
            if (paramInt >= j) {
              break;
            }
            i = paramInt;
            if (localListAdapter.isEnabled(paramInt)) {
              break;
            }
          }
        }
        for (paramInt = Math.min(paramInt, j - 1);; paramInt--)
        {
          i = paramInt;
          if (paramInt < 0) {
            break;
          }
          i = paramInt;
          if (localListAdapter.isEnabled(paramInt)) {
            break;
          }
        }
        if ((i >= 0) && (i < j)) {
          return i;
        }
        return -1;
      }
      if ((paramInt >= 0) && (paramInt < j)) {
        return paramInt;
      }
      return -1;
    }
    return -1;
  }
  
  public int measureHeightOfChildrenCompat(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int n = getListPaddingTop();
    int k = getListPaddingBottom();
    int i = getDividerHeight();
    Object localObject = getDivider();
    ListAdapter localListAdapter = getAdapter();
    if (localListAdapter == null) {
      return n + k;
    }
    paramInt3 = n + k;
    if ((i <= 0) || (localObject == null)) {
      i = 0;
    }
    paramInt2 = 0;
    localObject = null;
    int i1 = 0;
    int i3 = localListAdapter.getCount();
    int j = 0;
    while (j < i3)
    {
      int i2 = localListAdapter.getItemViewType(j);
      int m = i1;
      if (i2 != i1)
      {
        localObject = null;
        m = i2;
      }
      View localView = localListAdapter.getView(j, (View)localObject, this);
      localObject = localView.getLayoutParams();
      if (localObject == null)
      {
        localObject = generateDefaultLayoutParams();
        localView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      }
      if (((ViewGroup.LayoutParams)localObject).height > 0) {
        i1 = View.MeasureSpec.makeMeasureSpec(((ViewGroup.LayoutParams)localObject).height, 1073741824);
      } else {
        i1 = View.MeasureSpec.makeMeasureSpec(0, 0);
      }
      localView.measure(paramInt1, i1);
      localView.forceLayout();
      i1 = paramInt3;
      if (j > 0) {
        i1 = paramInt3 + i;
      }
      paramInt3 = i1 + localView.getMeasuredHeight();
      if (paramInt3 >= paramInt4)
      {
        if ((paramInt5 >= 0) && (j > paramInt5) && (paramInt2 > 0) && (paramInt3 != paramInt4)) {
          paramInt4 = paramInt2;
        }
        return paramInt4;
      }
      i1 = paramInt2;
      if (paramInt5 >= 0)
      {
        i1 = paramInt2;
        if (j >= paramInt5) {
          i1 = paramInt3;
        }
      }
      j++;
      paramInt2 = i1;
      localObject = localView;
      i1 = m;
    }
    return paramInt3;
  }
  
  protected void onDetachedFromWindow()
  {
    this.mResolveHoverRunnable = null;
    super.onDetachedFromWindow();
  }
  
  public boolean onForwardedEvent(MotionEvent paramMotionEvent, int paramInt)
  {
    boolean bool2 = true;
    boolean bool1 = true;
    int i = 0;
    int j = paramMotionEvent.getActionMasked();
    switch (j)
    {
    default: 
      bool1 = bool2;
      paramInt = i;
      break;
    case 3: 
      bool1 = false;
      paramInt = i;
      break;
    case 1: 
      bool1 = false;
    case 2: 
      int k = paramMotionEvent.findPointerIndex(paramInt);
      if (k < 0)
      {
        bool1 = false;
        paramInt = i;
      }
      else
      {
        paramInt = (int)paramMotionEvent.getX(k);
        int m = (int)paramMotionEvent.getY(k);
        k = pointToPosition(paramInt, m);
        if (k == -1)
        {
          paramInt = 1;
        }
        else
        {
          View localView = getChildAt(k - getFirstVisiblePosition());
          setPressedItem(localView, k, paramInt, m);
          bool2 = true;
          bool1 = bool2;
          paramInt = i;
          if (j == 1)
          {
            clickPressedItem(localView, k);
            paramInt = i;
            bool1 = bool2;
          }
        }
      }
      break;
    }
    if ((!bool1) || (paramInt != 0)) {
      clearPressedItem();
    }
    if (bool1)
    {
      if (this.mScrollHelper == null) {
        this.mScrollHelper = new ListViewAutoScrollHelper(this);
      }
      this.mScrollHelper.setEnabled(true);
      this.mScrollHelper.onTouch(this, paramMotionEvent);
    }
    else
    {
      paramMotionEvent = this.mScrollHelper;
      if (paramMotionEvent != null) {
        paramMotionEvent.setEnabled(false);
      }
    }
    return bool1;
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    if (Build.VERSION.SDK_INT < 26) {
      return super.onHoverEvent(paramMotionEvent);
    }
    int i = paramMotionEvent.getActionMasked();
    if ((i == 10) && (this.mResolveHoverRunnable == null))
    {
      ResolveHoverRunnable localResolveHoverRunnable = new ResolveHoverRunnable();
      this.mResolveHoverRunnable = localResolveHoverRunnable;
      localResolveHoverRunnable.post();
    }
    boolean bool = super.onHoverEvent(paramMotionEvent);
    if ((i != 9) && (i != 7))
    {
      setSelection(-1);
    }
    else
    {
      i = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      if ((i != -1) && (i != getSelectedItemPosition()))
      {
        paramMotionEvent = getChildAt(i - getFirstVisiblePosition());
        if (paramMotionEvent.isEnabled())
        {
          requestFocus();
          if ((Build.VERSION.SDK_INT >= 30) && (Api30Impl.canPositionSelectorForHoveredItem())) {
            Api30Impl.positionSelectorForHoveredItem(this, i, paramMotionEvent);
          } else {
            setSelectionFromTop(i, paramMotionEvent.getTop() - getTop());
          }
        }
        updateSelectorStateCompat();
      }
    }
    return bool;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default: 
      break;
    case 0: 
      this.mMotionPosition = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
    }
    ResolveHoverRunnable localResolveHoverRunnable = this.mResolveHoverRunnable;
    if (localResolveHoverRunnable != null) {
      localResolveHoverRunnable.cancel();
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  void setListSelectionHidden(boolean paramBoolean)
  {
    this.mListSelectionHidden = paramBoolean;
  }
  
  public void setSelector(Drawable paramDrawable)
  {
    if (paramDrawable != null) {
      localObject = new GateKeeperDrawable(paramDrawable);
    } else {
      localObject = null;
    }
    this.mSelector = ((GateKeeperDrawable)localObject);
    super.setSelector((Drawable)localObject);
    Object localObject = new Rect();
    if (paramDrawable != null) {
      paramDrawable.getPadding((Rect)localObject);
    }
    this.mSelectionLeftPadding = ((Rect)localObject).left;
    this.mSelectionTopPadding = ((Rect)localObject).top;
    this.mSelectionRightPadding = ((Rect)localObject).right;
    this.mSelectionBottomPadding = ((Rect)localObject).bottom;
  }
  
  static class Api21Impl
  {
    static void drawableHotspotChanged(View paramView, float paramFloat1, float paramFloat2)
    {
      paramView.drawableHotspotChanged(paramFloat1, paramFloat2);
    }
  }
  
  static class Api30Impl
  {
    private static boolean sHasMethods;
    private static Method sPositionSelector;
    private static Method sSetNextSelectedPositionInt;
    private static Method sSetSelectedPositionInt;
    
    static
    {
      try
      {
        Method localMethod = AbsListView.class.getDeclaredMethod("positionSelector", new Class[] { Integer.TYPE, View.class, Boolean.TYPE, Float.TYPE, Float.TYPE });
        sPositionSelector = localMethod;
        localMethod.setAccessible(true);
        localMethod = AdapterView.class.getDeclaredMethod("setSelectedPositionInt", new Class[] { Integer.TYPE });
        sSetSelectedPositionInt = localMethod;
        localMethod.setAccessible(true);
        localMethod = AdapterView.class.getDeclaredMethod("setNextSelectedPositionInt", new Class[] { Integer.TYPE });
        sSetNextSelectedPositionInt = localMethod;
        localMethod.setAccessible(true);
        sHasMethods = true;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localNoSuchMethodException.printStackTrace();
      }
    }
    
    static boolean canPositionSelectorForHoveredItem()
    {
      return sHasMethods;
    }
    
    static void positionSelectorForHoveredItem(DropDownListView paramDropDownListView, int paramInt, View paramView)
    {
      try
      {
        sPositionSelector.invoke(paramDropDownListView, new Object[] { Integer.valueOf(paramInt), paramView, Boolean.valueOf(false), Integer.valueOf(-1), Integer.valueOf(-1) });
        sSetSelectedPositionInt.invoke(paramDropDownListView, new Object[] { Integer.valueOf(paramInt) });
        sSetNextSelectedPositionInt.invoke(paramDropDownListView, new Object[] { Integer.valueOf(paramInt) });
      }
      catch (InvocationTargetException paramDropDownListView)
      {
        paramDropDownListView.printStackTrace();
      }
      catch (IllegalAccessException paramDropDownListView)
      {
        paramDropDownListView.printStackTrace();
      }
    }
  }
  
  private static class GateKeeperDrawable
    extends DrawableWrapper
  {
    private boolean mEnabled = true;
    
    GateKeeperDrawable(Drawable paramDrawable)
    {
      super();
    }
    
    public void draw(Canvas paramCanvas)
    {
      if (this.mEnabled) {
        super.draw(paramCanvas);
      }
    }
    
    void setEnabled(boolean paramBoolean)
    {
      this.mEnabled = paramBoolean;
    }
    
    public void setHotspot(float paramFloat1, float paramFloat2)
    {
      if (this.mEnabled) {
        super.setHotspot(paramFloat1, paramFloat2);
      }
    }
    
    public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (this.mEnabled) {
        super.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    
    public boolean setState(int[] paramArrayOfInt)
    {
      if (this.mEnabled) {
        return super.setState(paramArrayOfInt);
      }
      return false;
    }
    
    public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
    {
      if (this.mEnabled) {
        return super.setVisible(paramBoolean1, paramBoolean2);
      }
      return false;
    }
  }
  
  private class ResolveHoverRunnable
    implements Runnable
  {
    ResolveHoverRunnable() {}
    
    public void cancel()
    {
      DropDownListView.this.mResolveHoverRunnable = null;
      DropDownListView.this.removeCallbacks(this);
    }
    
    public void post()
    {
      DropDownListView.this.post(this);
    }
    
    public void run()
    {
      DropDownListView.this.mResolveHoverRunnable = null;
      DropDownListView.this.drawableStateChanged();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/DropDownListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */