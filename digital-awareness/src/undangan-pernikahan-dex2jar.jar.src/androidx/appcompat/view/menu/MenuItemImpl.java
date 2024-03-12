package androidx.appcompat.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.LinearLayout;
import androidx.appcompat.R.string;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.view.ActionProvider.VisibilityListener;

public final class MenuItemImpl
  implements SupportMenuItem
{
  private static final int CHECKABLE = 1;
  private static final int CHECKED = 2;
  private static final int ENABLED = 16;
  private static final int EXCLUSIVE = 4;
  private static final int HIDDEN = 8;
  private static final int IS_ACTION = 32;
  static final int NO_ICON = 0;
  private static final int SHOW_AS_ACTION_MASK = 3;
  private static final String TAG = "MenuItemImpl";
  private androidx.core.view.ActionProvider mActionProvider;
  private View mActionView;
  private final int mCategoryOrder;
  private MenuItem.OnMenuItemClickListener mClickListener;
  private CharSequence mContentDescription;
  private int mFlags = 16;
  private final int mGroup;
  private boolean mHasIconTint = false;
  private boolean mHasIconTintMode = false;
  private Drawable mIconDrawable;
  private int mIconResId = 0;
  private ColorStateList mIconTintList = null;
  private PorterDuff.Mode mIconTintMode = null;
  private final int mId;
  private Intent mIntent;
  private boolean mIsActionViewExpanded = false;
  private Runnable mItemCallback;
  MenuBuilder mMenu;
  private ContextMenu.ContextMenuInfo mMenuInfo;
  private boolean mNeedToApplyIconTint = false;
  private MenuItem.OnActionExpandListener mOnActionExpandListener;
  private final int mOrdering;
  private char mShortcutAlphabeticChar;
  private int mShortcutAlphabeticModifiers = 4096;
  private char mShortcutNumericChar;
  private int mShortcutNumericModifiers = 4096;
  private int mShowAsAction = 0;
  private SubMenuBuilder mSubMenu;
  private CharSequence mTitle;
  private CharSequence mTitleCondensed;
  private CharSequence mTooltipText;
  
  MenuItemImpl(MenuBuilder paramMenuBuilder, int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence, int paramInt5)
  {
    this.mMenu = paramMenuBuilder;
    this.mId = paramInt2;
    this.mGroup = paramInt1;
    this.mCategoryOrder = paramInt3;
    this.mOrdering = paramInt4;
    this.mTitle = paramCharSequence;
    this.mShowAsAction = paramInt5;
  }
  
  private static void appendModifier(StringBuilder paramStringBuilder, int paramInt1, int paramInt2, String paramString)
  {
    if ((paramInt1 & paramInt2) == paramInt2) {
      paramStringBuilder.append(paramString);
    }
  }
  
  private Drawable applyIconTintIfNecessary(Drawable paramDrawable)
  {
    Drawable localDrawable = paramDrawable;
    if (paramDrawable != null)
    {
      localDrawable = paramDrawable;
      if (this.mNeedToApplyIconTint) {
        if (!this.mHasIconTint)
        {
          localDrawable = paramDrawable;
          if (!this.mHasIconTintMode) {}
        }
        else
        {
          localDrawable = DrawableCompat.wrap(paramDrawable).mutate();
          if (this.mHasIconTint) {
            DrawableCompat.setTintList(localDrawable, this.mIconTintList);
          }
          if (this.mHasIconTintMode) {
            DrawableCompat.setTintMode(localDrawable, this.mIconTintMode);
          }
          this.mNeedToApplyIconTint = false;
        }
      }
    }
    return localDrawable;
  }
  
  public void actionFormatChanged()
  {
    this.mMenu.onItemActionRequestChanged(this);
  }
  
  public boolean collapseActionView()
  {
    if ((this.mShowAsAction & 0x8) == 0) {
      return false;
    }
    if (this.mActionView == null) {
      return true;
    }
    MenuItem.OnActionExpandListener localOnActionExpandListener = this.mOnActionExpandListener;
    if ((localOnActionExpandListener != null) && (!localOnActionExpandListener.onMenuItemActionCollapse(this))) {
      return false;
    }
    return this.mMenu.collapseItemActionView(this);
  }
  
  public boolean expandActionView()
  {
    if (!hasCollapsibleActionView()) {
      return false;
    }
    MenuItem.OnActionExpandListener localOnActionExpandListener = this.mOnActionExpandListener;
    if ((localOnActionExpandListener != null) && (!localOnActionExpandListener.onMenuItemActionExpand(this))) {
      return false;
    }
    return this.mMenu.expandItemActionView(this);
  }
  
  public android.view.ActionProvider getActionProvider()
  {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
  }
  
  public View getActionView()
  {
    Object localObject = this.mActionView;
    if (localObject != null) {
      return (View)localObject;
    }
    localObject = this.mActionProvider;
    if (localObject != null)
    {
      localObject = ((androidx.core.view.ActionProvider)localObject).onCreateActionView(this);
      this.mActionView = ((View)localObject);
      return (View)localObject;
    }
    return null;
  }
  
  public int getAlphabeticModifiers()
  {
    return this.mShortcutAlphabeticModifiers;
  }
  
  public char getAlphabeticShortcut()
  {
    return this.mShortcutAlphabeticChar;
  }
  
  Runnable getCallback()
  {
    return this.mItemCallback;
  }
  
  public CharSequence getContentDescription()
  {
    return this.mContentDescription;
  }
  
  public int getGroupId()
  {
    return this.mGroup;
  }
  
  public Drawable getIcon()
  {
    Drawable localDrawable = this.mIconDrawable;
    if (localDrawable != null) {
      return applyIconTintIfNecessary(localDrawable);
    }
    if (this.mIconResId != 0)
    {
      localDrawable = AppCompatResources.getDrawable(this.mMenu.getContext(), this.mIconResId);
      this.mIconResId = 0;
      this.mIconDrawable = localDrawable;
      return applyIconTintIfNecessary(localDrawable);
    }
    return null;
  }
  
  public ColorStateList getIconTintList()
  {
    return this.mIconTintList;
  }
  
  public PorterDuff.Mode getIconTintMode()
  {
    return this.mIconTintMode;
  }
  
  public Intent getIntent()
  {
    return this.mIntent;
  }
  
  @ViewDebug.CapturedViewProperty
  public int getItemId()
  {
    return this.mId;
  }
  
  public ContextMenu.ContextMenuInfo getMenuInfo()
  {
    return this.mMenuInfo;
  }
  
  public int getNumericModifiers()
  {
    return this.mShortcutNumericModifiers;
  }
  
  public char getNumericShortcut()
  {
    return this.mShortcutNumericChar;
  }
  
  public int getOrder()
  {
    return this.mCategoryOrder;
  }
  
  public int getOrdering()
  {
    return this.mOrdering;
  }
  
  char getShortcut()
  {
    char c;
    if (this.mMenu.isQwertyMode()) {
      c = this.mShortcutAlphabeticChar;
    } else {
      c = this.mShortcutNumericChar;
    }
    return c;
  }
  
  String getShortcutLabel()
  {
    char c = getShortcut();
    if (c == 0) {
      return "";
    }
    Resources localResources = this.mMenu.getContext().getResources();
    StringBuilder localStringBuilder = new StringBuilder();
    if (ViewConfiguration.get(this.mMenu.getContext()).hasPermanentMenuKey()) {
      localStringBuilder.append(localResources.getString(R.string.abc_prepend_shortcut_label));
    }
    int i;
    if (this.mMenu.isQwertyMode()) {
      i = this.mShortcutAlphabeticModifiers;
    } else {
      i = this.mShortcutNumericModifiers;
    }
    appendModifier(localStringBuilder, i, 65536, localResources.getString(R.string.abc_menu_meta_shortcut_label));
    appendModifier(localStringBuilder, i, 4096, localResources.getString(R.string.abc_menu_ctrl_shortcut_label));
    appendModifier(localStringBuilder, i, 2, localResources.getString(R.string.abc_menu_alt_shortcut_label));
    appendModifier(localStringBuilder, i, 1, localResources.getString(R.string.abc_menu_shift_shortcut_label));
    appendModifier(localStringBuilder, i, 4, localResources.getString(R.string.abc_menu_sym_shortcut_label));
    appendModifier(localStringBuilder, i, 8, localResources.getString(R.string.abc_menu_function_shortcut_label));
    switch (c)
    {
    default: 
      localStringBuilder.append(c);
      break;
    case ' ': 
      localStringBuilder.append(localResources.getString(R.string.abc_menu_space_shortcut_label));
      break;
    case '\n': 
      localStringBuilder.append(localResources.getString(R.string.abc_menu_enter_shortcut_label));
      break;
    case '\b': 
      localStringBuilder.append(localResources.getString(R.string.abc_menu_delete_shortcut_label));
    }
    return localStringBuilder.toString();
  }
  
  public SubMenu getSubMenu()
  {
    return this.mSubMenu;
  }
  
  public androidx.core.view.ActionProvider getSupportActionProvider()
  {
    return this.mActionProvider;
  }
  
  @ViewDebug.CapturedViewProperty
  public CharSequence getTitle()
  {
    return this.mTitle;
  }
  
  public CharSequence getTitleCondensed()
  {
    CharSequence localCharSequence = this.mTitleCondensed;
    if (localCharSequence == null) {
      localCharSequence = this.mTitle;
    }
    if ((Build.VERSION.SDK_INT < 18) && (localCharSequence != null) && (!(localCharSequence instanceof String))) {
      return localCharSequence.toString();
    }
    return localCharSequence;
  }
  
  CharSequence getTitleForItemView(MenuView.ItemView paramItemView)
  {
    if ((paramItemView != null) && (paramItemView.prefersCondensedTitle())) {
      paramItemView = getTitleCondensed();
    } else {
      paramItemView = getTitle();
    }
    return paramItemView;
  }
  
  public CharSequence getTooltipText()
  {
    return this.mTooltipText;
  }
  
  public boolean hasCollapsibleActionView()
  {
    int i = this.mShowAsAction;
    boolean bool = false;
    if ((i & 0x8) != 0)
    {
      if (this.mActionView == null)
      {
        androidx.core.view.ActionProvider localActionProvider = this.mActionProvider;
        if (localActionProvider != null) {
          this.mActionView = localActionProvider.onCreateActionView(this);
        }
      }
      if (this.mActionView != null) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public boolean hasSubMenu()
  {
    boolean bool;
    if (this.mSubMenu != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean invoke()
  {
    Object localObject = this.mClickListener;
    if ((localObject != null) && (((MenuItem.OnMenuItemClickListener)localObject).onMenuItemClick(this))) {
      return true;
    }
    localObject = this.mMenu;
    if (((MenuBuilder)localObject).dispatchMenuItemSelected((MenuBuilder)localObject, this)) {
      return true;
    }
    localObject = this.mItemCallback;
    if (localObject != null)
    {
      ((Runnable)localObject).run();
      return true;
    }
    if (this.mIntent != null) {
      try
      {
        this.mMenu.getContext().startActivity(this.mIntent);
        return true;
      }
      catch (ActivityNotFoundException localActivityNotFoundException)
      {
        Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", localActivityNotFoundException);
      }
    }
    androidx.core.view.ActionProvider localActionProvider = this.mActionProvider;
    return (localActionProvider != null) && (localActionProvider.onPerformDefaultAction());
  }
  
  public boolean isActionButton()
  {
    boolean bool;
    if ((this.mFlags & 0x20) == 32) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isActionViewExpanded()
  {
    return this.mIsActionViewExpanded;
  }
  
  public boolean isCheckable()
  {
    int i = this.mFlags;
    boolean bool = true;
    if ((i & 0x1) != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isChecked()
  {
    boolean bool;
    if ((this.mFlags & 0x2) == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isEnabled()
  {
    boolean bool;
    if ((this.mFlags & 0x10) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isExclusiveCheckable()
  {
    boolean bool;
    if ((this.mFlags & 0x4) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isVisible()
  {
    androidx.core.view.ActionProvider localActionProvider = this.mActionProvider;
    boolean bool1 = true;
    boolean bool2 = true;
    if ((localActionProvider != null) && (localActionProvider.overridesItemVisibility()))
    {
      if (((this.mFlags & 0x8) == 0) && (this.mActionProvider.isVisible())) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    if ((this.mFlags & 0x8) != 0) {
      bool1 = false;
    }
    return bool1;
  }
  
  public boolean requestsActionButton()
  {
    int i = this.mShowAsAction;
    boolean bool = true;
    if ((i & 0x1) != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean requiresActionButton()
  {
    boolean bool;
    if ((this.mShowAsAction & 0x2) == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean requiresOverflow()
  {
    boolean bool;
    if ((!requiresActionButton()) && (!requestsActionButton())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public MenuItem setActionProvider(android.view.ActionProvider paramActionProvider)
  {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
  }
  
  public SupportMenuItem setActionView(int paramInt)
  {
    Context localContext = this.mMenu.getContext();
    setActionView(LayoutInflater.from(localContext).inflate(paramInt, new LinearLayout(localContext), false));
    return this;
  }
  
  public SupportMenuItem setActionView(View paramView)
  {
    this.mActionView = paramView;
    this.mActionProvider = null;
    if ((paramView != null) && (paramView.getId() == -1))
    {
      int i = this.mId;
      if (i > 0) {
        paramView.setId(i);
      }
    }
    this.mMenu.onItemActionRequestChanged(this);
    return this;
  }
  
  public void setActionViewExpanded(boolean paramBoolean)
  {
    this.mIsActionViewExpanded = paramBoolean;
    this.mMenu.onItemsChanged(false);
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar)
  {
    if (this.mShortcutAlphabeticChar == paramChar) {
      return this;
    }
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar);
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar, int paramInt)
  {
    if ((this.mShortcutAlphabeticChar == paramChar) && (this.mShortcutAlphabeticModifiers == paramInt)) {
      return this;
    }
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar);
    this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(paramInt);
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setCallback(Runnable paramRunnable)
  {
    this.mItemCallback = paramRunnable;
    return this;
  }
  
  public MenuItem setCheckable(boolean paramBoolean)
  {
    int i = this.mFlags;
    int j = this.mFlags & 0xFFFFFFFE | paramBoolean;
    this.mFlags = j;
    if (i != j) {
      this.mMenu.onItemsChanged(false);
    }
    return this;
  }
  
  public MenuItem setChecked(boolean paramBoolean)
  {
    if ((this.mFlags & 0x4) != 0) {
      this.mMenu.setExclusiveItemChecked(this);
    } else {
      setCheckedInt(paramBoolean);
    }
    return this;
  }
  
  void setCheckedInt(boolean paramBoolean)
  {
    int j = this.mFlags;
    int k = this.mFlags;
    if (paramBoolean) {
      i = 2;
    } else {
      i = 0;
    }
    int i = k & 0xFFFFFFFD | i;
    this.mFlags = i;
    if (j != i) {
      this.mMenu.onItemsChanged(false);
    }
  }
  
  public SupportMenuItem setContentDescription(CharSequence paramCharSequence)
  {
    this.mContentDescription = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setEnabled(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.mFlags |= 0x10;
    } else {
      this.mFlags &= 0xFFFFFFEF;
    }
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public void setExclusiveCheckable(boolean paramBoolean)
  {
    int j = this.mFlags;
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    this.mFlags = (j & 0xFFFFFFFB | i);
  }
  
  public MenuItem setIcon(int paramInt)
  {
    this.mIconDrawable = null;
    this.mIconResId = paramInt;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setIcon(Drawable paramDrawable)
  {
    this.mIconResId = 0;
    this.mIconDrawable = paramDrawable;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setIconTintList(ColorStateList paramColorStateList)
  {
    this.mIconTintList = paramColorStateList;
    this.mHasIconTint = true;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setIconTintMode(PorterDuff.Mode paramMode)
  {
    this.mIconTintMode = paramMode;
    this.mHasIconTintMode = true;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setIntent(Intent paramIntent)
  {
    this.mIntent = paramIntent;
    return this;
  }
  
  public void setIsActionButton(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.mFlags |= 0x20;
    } else {
      this.mFlags &= 0xFFFFFFDF;
    }
  }
  
  void setMenuInfo(ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    this.mMenuInfo = paramContextMenuInfo;
  }
  
  public MenuItem setNumericShortcut(char paramChar)
  {
    if (this.mShortcutNumericChar == paramChar) {
      return this;
    }
    this.mShortcutNumericChar = paramChar;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setNumericShortcut(char paramChar, int paramInt)
  {
    if ((this.mShortcutNumericChar == paramChar) && (this.mShortcutNumericModifiers == paramInt)) {
      return this;
    }
    this.mShortcutNumericChar = paramChar;
    this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(paramInt);
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener)
  {
    this.mOnActionExpandListener = paramOnActionExpandListener;
    return this;
  }
  
  public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mClickListener = paramOnMenuItemClickListener;
    return this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2)
  {
    this.mShortcutNumericChar = paramChar1;
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar2);
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2, int paramInt1, int paramInt2)
  {
    this.mShortcutNumericChar = paramChar1;
    this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(paramInt1);
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar2);
    this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(paramInt2);
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public void setShowAsAction(int paramInt)
  {
    switch (paramInt & 0x3)
    {
    default: 
      throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
    }
    this.mShowAsAction = paramInt;
    this.mMenu.onItemActionRequestChanged(this);
  }
  
  public SupportMenuItem setShowAsActionFlags(int paramInt)
  {
    setShowAsAction(paramInt);
    return this;
  }
  
  public void setSubMenu(SubMenuBuilder paramSubMenuBuilder)
  {
    this.mSubMenu = paramSubMenuBuilder;
    paramSubMenuBuilder.setHeaderTitle(getTitle());
  }
  
  public SupportMenuItem setSupportActionProvider(androidx.core.view.ActionProvider paramActionProvider)
  {
    androidx.core.view.ActionProvider localActionProvider = this.mActionProvider;
    if (localActionProvider != null) {
      localActionProvider.reset();
    }
    this.mActionView = null;
    this.mActionProvider = paramActionProvider;
    this.mMenu.onItemsChanged(true);
    paramActionProvider = this.mActionProvider;
    if (paramActionProvider != null) {
      paramActionProvider.setVisibilityListener(new ActionProvider.VisibilityListener()
      {
        public void onActionProviderVisibilityChanged(boolean paramAnonymousBoolean)
        {
          MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this);
        }
      });
    }
    return this;
  }
  
  public MenuItem setTitle(int paramInt)
  {
    return setTitle(this.mMenu.getContext().getString(paramInt));
  }
  
  public MenuItem setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    SubMenuBuilder localSubMenuBuilder = this.mSubMenu;
    if (localSubMenuBuilder != null) {
      localSubMenuBuilder.setHeaderTitle(paramCharSequence);
    }
    return this;
  }
  
  public MenuItem setTitleCondensed(CharSequence paramCharSequence)
  {
    this.mTitleCondensed = paramCharSequence;
    if (paramCharSequence == null) {
      paramCharSequence = this.mTitle;
    }
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public SupportMenuItem setTooltipText(CharSequence paramCharSequence)
  {
    this.mTooltipText = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setVisible(boolean paramBoolean)
  {
    if (setVisibleInt(paramBoolean)) {
      this.mMenu.onItemVisibleChanged(this);
    }
    return this;
  }
  
  boolean setVisibleInt(boolean paramBoolean)
  {
    int j = this.mFlags;
    int k = this.mFlags;
    boolean bool = false;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 8;
    }
    int i = k & 0xFFFFFFF7 | i;
    this.mFlags = i;
    paramBoolean = bool;
    if (j != i) {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public boolean shouldShowIcon()
  {
    return this.mMenu.getOptionalIconsVisible();
  }
  
  boolean shouldShowShortcut()
  {
    boolean bool;
    if ((this.mMenu.isShortcutsVisible()) && (getShortcut() != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean showsTextAsAction()
  {
    boolean bool;
    if ((this.mShowAsAction & 0x4) == 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    Object localObject = this.mTitle;
    if (localObject != null) {
      localObject = localObject.toString();
    } else {
      localObject = null;
    }
    return (String)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/view/menu/MenuItemImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */