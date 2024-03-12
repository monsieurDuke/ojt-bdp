package androidx.appcompat.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.R.styleable;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SupportMenuInflater
  extends MenuInflater
{
  static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
  static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
  static final String LOG_TAG = "SupportMenuInflater";
  static final int NO_ID = 0;
  private static final String XML_GROUP = "group";
  private static final String XML_ITEM = "item";
  private static final String XML_MENU = "menu";
  final Object[] mActionProviderConstructorArguments;
  final Object[] mActionViewConstructorArguments;
  Context mContext;
  private Object mRealOwner;
  
  static
  {
    Class[] arrayOfClass = new Class[1];
    arrayOfClass[0] = Context.class;
    ACTION_VIEW_CONSTRUCTOR_SIGNATURE = arrayOfClass;
    ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = arrayOfClass;
  }
  
  public SupportMenuInflater(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext;
    this.mActionViewConstructorArguments = arrayOfObject;
    this.mActionProviderConstructorArguments = arrayOfObject;
  }
  
  private Object findRealOwner(Object paramObject)
  {
    if ((paramObject instanceof Activity)) {
      return paramObject;
    }
    if ((paramObject instanceof ContextWrapper)) {
      return findRealOwner(((ContextWrapper)paramObject).getBaseContext());
    }
    return paramObject;
  }
  
  private void parseMenu(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Menu paramMenu)
    throws XmlPullParserException, IOException
  {
    MenuState localMenuState = new MenuState(paramMenu);
    int j = paramXmlPullParser.getEventType();
    int k = 0;
    Object localObject = null;
    int i;
    do
    {
      if (j == 2)
      {
        paramMenu = paramXmlPullParser.getName();
        if (paramMenu.equals("menu"))
        {
          i = paramXmlPullParser.next();
          break;
        }
        throw new RuntimeException("Expecting menu, got " + paramMenu);
      }
      i = paramXmlPullParser.next();
      j = i;
    } while (i != 1);
    j = 0;
    int n = i;
    while (j == 0)
    {
      int m;
      switch (n)
      {
      default: 
        i = k;
        paramMenu = (Menu)localObject;
        m = j;
        break;
      case 3: 
        String str = paramXmlPullParser.getName();
        if ((k != 0) && (str.equals(localObject)))
        {
          i = 0;
          paramMenu = null;
          m = j;
        }
        else if (str.equals("group"))
        {
          localMenuState.resetGroup();
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
        }
        else if (str.equals("item"))
        {
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
          if (!localMenuState.hasAddedItem()) {
            if ((localMenuState.itemActionProvider != null) && (localMenuState.itemActionProvider.hasSubMenu()))
            {
              localMenuState.addSubMenuItem();
              i = k;
              paramMenu = (Menu)localObject;
              m = j;
            }
            else
            {
              localMenuState.addItem();
              i = k;
              paramMenu = (Menu)localObject;
              m = j;
            }
          }
        }
        else
        {
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
          if (str.equals("menu"))
          {
            m = 1;
            i = k;
            paramMenu = (Menu)localObject;
          }
        }
        break;
      case 2: 
        if (k != 0)
        {
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
        }
        else
        {
          paramMenu = paramXmlPullParser.getName();
          if (paramMenu.equals("group"))
          {
            localMenuState.readGroup(paramAttributeSet);
            i = k;
            paramMenu = (Menu)localObject;
            m = j;
          }
          else if (paramMenu.equals("item"))
          {
            localMenuState.readItem(paramAttributeSet);
            i = k;
            paramMenu = (Menu)localObject;
            m = j;
          }
          else if (paramMenu.equals("menu"))
          {
            parseMenu(paramXmlPullParser, paramAttributeSet, localMenuState.addSubMenuItem());
            i = k;
            paramMenu = (Menu)localObject;
            m = j;
          }
          else
          {
            i = 1;
            m = j;
          }
        }
        break;
      case 1: 
        throw new RuntimeException("Unexpected end of document");
      }
      n = paramXmlPullParser.next();
      k = i;
      localObject = paramMenu;
      j = m;
    }
  }
  
  Object getRealOwner()
  {
    if (this.mRealOwner == null) {
      this.mRealOwner = findRealOwner(this.mContext);
    }
    return this.mRealOwner;
  }
  
  /* Error */
  public void inflate(int paramInt, Menu paramMenu)
  {
    // Byte code:
    //   0: aload_2
    //   1: instanceof 161
    //   4: ifne +10 -> 14
    //   7: aload_0
    //   8: iload_1
    //   9: aload_2
    //   10: invokespecial 163	android/view/MenuInflater:inflate	(ILandroid/view/Menu;)V
    //   13: return
    //   14: aconst_null
    //   15: astore 4
    //   17: aconst_null
    //   18: astore 5
    //   20: aconst_null
    //   21: astore_3
    //   22: aload_0
    //   23: getfield 54	androidx/appcompat/view/SupportMenuInflater:mContext	Landroid/content/Context;
    //   26: invokevirtual 167	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   29: iload_1
    //   30: invokevirtual 173	android/content/res/Resources:getLayout	(I)Landroid/content/res/XmlResourceParser;
    //   33: astore 6
    //   35: aload 6
    //   37: astore_3
    //   38: aload 6
    //   40: astore 4
    //   42: aload 6
    //   44: astore 5
    //   46: aload_0
    //   47: aload 6
    //   49: aload 6
    //   51: invokestatic 179	android/util/Xml:asAttributeSet	(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;
    //   54: aload_2
    //   55: invokespecial 150	androidx/appcompat/view/SupportMenuInflater:parseMenu	(Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/view/Menu;)V
    //   58: aload 6
    //   60: ifnull +10 -> 70
    //   63: aload 6
    //   65: invokeinterface 184 1 0
    //   70: return
    //   71: astore_2
    //   72: goto +55 -> 127
    //   75: astore_2
    //   76: aload 4
    //   78: astore_3
    //   79: new 186	android/view/InflateException
    //   82: astore 5
    //   84: aload 4
    //   86: astore_3
    //   87: aload 5
    //   89: ldc -68
    //   91: aload_2
    //   92: invokespecial 191	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: aload 4
    //   97: astore_3
    //   98: aload 5
    //   100: athrow
    //   101: astore_2
    //   102: aload 5
    //   104: astore_3
    //   105: new 186	android/view/InflateException
    //   108: astore 4
    //   110: aload 5
    //   112: astore_3
    //   113: aload 4
    //   115: ldc -68
    //   117: aload_2
    //   118: invokespecial 191	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   121: aload 5
    //   123: astore_3
    //   124: aload 4
    //   126: athrow
    //   127: aload_3
    //   128: ifnull +9 -> 137
    //   131: aload_3
    //   132: invokeinterface 184 1 0
    //   137: aload_2
    //   138: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	139	0	this	SupportMenuInflater
    //   0	139	1	paramInt	int
    //   0	139	2	paramMenu	Menu
    //   21	111	3	localObject1	Object
    //   15	110	4	localObject2	Object
    //   18	104	5	localObject3	Object
    //   33	31	6	localXmlResourceParser	android.content.res.XmlResourceParser
    // Exception table:
    //   from	to	target	type
    //   22	35	71	finally
    //   46	58	71	finally
    //   79	84	71	finally
    //   87	95	71	finally
    //   98	101	71	finally
    //   105	110	71	finally
    //   113	121	71	finally
    //   124	127	71	finally
    //   22	35	75	java/io/IOException
    //   46	58	75	java/io/IOException
    //   22	35	101	org/xmlpull/v1/XmlPullParserException
    //   46	58	101	org/xmlpull/v1/XmlPullParserException
  }
  
  private static class InflatedOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener
  {
    private static final Class<?>[] PARAM_TYPES = { MenuItem.class };
    private Method mMethod;
    private Object mRealOwner;
    
    public InflatedOnMenuItemClickListener(Object paramObject, String paramString)
    {
      this.mRealOwner = paramObject;
      Class localClass = paramObject.getClass();
      try
      {
        this.mMethod = localClass.getMethod(paramString, PARAM_TYPES);
        return;
      }
      catch (Exception paramObject)
      {
        paramString = new InflateException("Couldn't resolve menu item onClick handler " + paramString + " in class " + localClass.getName());
        paramString.initCause((Throwable)paramObject);
        throw paramString;
      }
    }
    
    public boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      try
      {
        if (this.mMethod.getReturnType() == Boolean.TYPE) {
          return ((Boolean)this.mMethod.invoke(this.mRealOwner, new Object[] { paramMenuItem })).booleanValue();
        }
        this.mMethod.invoke(this.mRealOwner, new Object[] { paramMenuItem });
        return true;
      }
      catch (Exception paramMenuItem)
      {
        throw new RuntimeException(paramMenuItem);
      }
    }
  }
  
  private class MenuState
  {
    private static final int defaultGroupId = 0;
    private static final int defaultItemCategory = 0;
    private static final int defaultItemCheckable = 0;
    private static final boolean defaultItemChecked = false;
    private static final boolean defaultItemEnabled = true;
    private static final int defaultItemId = 0;
    private static final int defaultItemOrder = 0;
    private static final boolean defaultItemVisible = true;
    private int groupCategory;
    private int groupCheckable;
    private boolean groupEnabled;
    private int groupId;
    private int groupOrder;
    private boolean groupVisible;
    ActionProvider itemActionProvider;
    private String itemActionProviderClassName;
    private String itemActionViewClassName;
    private int itemActionViewLayout;
    private boolean itemAdded;
    private int itemAlphabeticModifiers;
    private char itemAlphabeticShortcut;
    private int itemCategoryOrder;
    private int itemCheckable;
    private boolean itemChecked;
    private CharSequence itemContentDescription;
    private boolean itemEnabled;
    private int itemIconResId;
    private ColorStateList itemIconTintList = null;
    private PorterDuff.Mode itemIconTintMode = null;
    private int itemId;
    private String itemListenerMethodName;
    private int itemNumericModifiers;
    private char itemNumericShortcut;
    private int itemShowAsAction;
    private CharSequence itemTitle;
    private CharSequence itemTitleCondensed;
    private CharSequence itemTooltipText;
    private boolean itemVisible;
    private Menu menu;
    
    public MenuState(Menu paramMenu)
    {
      this.menu = paramMenu;
      resetGroup();
    }
    
    private char getShortcut(String paramString)
    {
      if (paramString == null) {
        return '\000';
      }
      return paramString.charAt(0);
    }
    
    private <T> T newInstance(String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
    {
      try
      {
        paramArrayOfClass = Class.forName(paramString, false, SupportMenuInflater.this.mContext.getClassLoader()).getConstructor(paramArrayOfClass);
        paramArrayOfClass.setAccessible(true);
        paramArrayOfClass = paramArrayOfClass.newInstance(paramArrayOfObject);
        return paramArrayOfClass;
      }
      catch (Exception paramArrayOfClass)
      {
        Log.w("SupportMenuInflater", "Cannot instantiate class: " + paramString, paramArrayOfClass);
      }
      return null;
    }
    
    private void setItem(MenuItem paramMenuItem)
    {
      Object localObject = paramMenuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
      boolean bool;
      if (this.itemCheckable >= 1) {
        bool = true;
      } else {
        bool = false;
      }
      ((MenuItem)localObject).setCheckable(bool).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
      int i = this.itemShowAsAction;
      if (i >= 0) {
        paramMenuItem.setShowAsAction(i);
      }
      if (this.itemListenerMethodName != null) {
        if (!SupportMenuInflater.this.mContext.isRestricted()) {
          paramMenuItem.setOnMenuItemClickListener(new SupportMenuInflater.InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
        } else {
          throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
        }
      }
      if (this.itemCheckable >= 2) {
        if ((paramMenuItem instanceof MenuItemImpl)) {
          ((MenuItemImpl)paramMenuItem).setExclusiveCheckable(true);
        } else if ((paramMenuItem instanceof MenuItemWrapperICS)) {
          ((MenuItemWrapperICS)paramMenuItem).setExclusiveCheckable(true);
        }
      }
      i = 0;
      localObject = this.itemActionViewClassName;
      if (localObject != null)
      {
        paramMenuItem.setActionView((View)newInstance((String)localObject, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
        i = 1;
      }
      int j = this.itemActionViewLayout;
      if (j > 0) {
        if (i == 0) {
          paramMenuItem.setActionView(j);
        } else {
          Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
        }
      }
      localObject = this.itemActionProvider;
      if (localObject != null) {
        MenuItemCompat.setActionProvider(paramMenuItem, (ActionProvider)localObject);
      }
      MenuItemCompat.setContentDescription(paramMenuItem, this.itemContentDescription);
      MenuItemCompat.setTooltipText(paramMenuItem, this.itemTooltipText);
      MenuItemCompat.setAlphabeticShortcut(paramMenuItem, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers);
      MenuItemCompat.setNumericShortcut(paramMenuItem, this.itemNumericShortcut, this.itemNumericModifiers);
      localObject = this.itemIconTintMode;
      if (localObject != null) {
        MenuItemCompat.setIconTintMode(paramMenuItem, (PorterDuff.Mode)localObject);
      }
      localObject = this.itemIconTintList;
      if (localObject != null) {
        MenuItemCompat.setIconTintList(paramMenuItem, (ColorStateList)localObject);
      }
    }
    
    public void addItem()
    {
      this.itemAdded = true;
      setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
    }
    
    public SubMenu addSubMenuItem()
    {
      this.itemAdded = true;
      SubMenu localSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
      setItem(localSubMenu.getItem());
      return localSubMenu;
    }
    
    public boolean hasAddedItem()
    {
      return this.itemAdded;
    }
    
    public void readGroup(AttributeSet paramAttributeSet)
    {
      paramAttributeSet = SupportMenuInflater.this.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MenuGroup);
      this.groupId = paramAttributeSet.getResourceId(R.styleable.MenuGroup_android_id, 0);
      this.groupCategory = paramAttributeSet.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
      this.groupOrder = paramAttributeSet.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
      this.groupCheckable = paramAttributeSet.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
      this.groupVisible = paramAttributeSet.getBoolean(R.styleable.MenuGroup_android_visible, true);
      this.groupEnabled = paramAttributeSet.getBoolean(R.styleable.MenuGroup_android_enabled, true);
      paramAttributeSet.recycle();
    }
    
    public void readItem(AttributeSet paramAttributeSet)
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(SupportMenuInflater.this.mContext, paramAttributeSet, R.styleable.MenuItem);
      this.itemId = localTintTypedArray.getResourceId(R.styleable.MenuItem_android_id, 0);
      this.itemCategoryOrder = (0xFFFF0000 & localTintTypedArray.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory) | 0xFFFF & localTintTypedArray.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder));
      this.itemTitle = localTintTypedArray.getText(R.styleable.MenuItem_android_title);
      this.itemTitleCondensed = localTintTypedArray.getText(R.styleable.MenuItem_android_titleCondensed);
      this.itemIconResId = localTintTypedArray.getResourceId(R.styleable.MenuItem_android_icon, 0);
      this.itemAlphabeticShortcut = getShortcut(localTintTypedArray.getString(R.styleable.MenuItem_android_alphabeticShortcut));
      this.itemAlphabeticModifiers = localTintTypedArray.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
      this.itemNumericShortcut = getShortcut(localTintTypedArray.getString(R.styleable.MenuItem_android_numericShortcut));
      this.itemNumericModifiers = localTintTypedArray.getInt(R.styleable.MenuItem_numericModifiers, 4096);
      if (localTintTypedArray.hasValue(R.styleable.MenuItem_android_checkable)) {
        this.itemCheckable = localTintTypedArray.getBoolean(R.styleable.MenuItem_android_checkable, false);
      } else {
        this.itemCheckable = this.groupCheckable;
      }
      this.itemChecked = localTintTypedArray.getBoolean(R.styleable.MenuItem_android_checked, false);
      this.itemVisible = localTintTypedArray.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible);
      this.itemEnabled = localTintTypedArray.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled);
      this.itemShowAsAction = localTintTypedArray.getInt(R.styleable.MenuItem_showAsAction, -1);
      this.itemListenerMethodName = localTintTypedArray.getString(R.styleable.MenuItem_android_onClick);
      this.itemActionViewLayout = localTintTypedArray.getResourceId(R.styleable.MenuItem_actionLayout, 0);
      this.itemActionViewClassName = localTintTypedArray.getString(R.styleable.MenuItem_actionViewClass);
      paramAttributeSet = localTintTypedArray.getString(R.styleable.MenuItem_actionProviderClass);
      this.itemActionProviderClassName = paramAttributeSet;
      int i;
      if (paramAttributeSet != null) {
        i = 1;
      } else {
        i = 0;
      }
      if ((i != 0) && (this.itemActionViewLayout == 0) && (this.itemActionViewClassName == null))
      {
        this.itemActionProvider = ((ActionProvider)newInstance(paramAttributeSet, SupportMenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments));
      }
      else
      {
        if (i != 0) {
          Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
        }
        this.itemActionProvider = null;
      }
      this.itemContentDescription = localTintTypedArray.getText(R.styleable.MenuItem_contentDescription);
      this.itemTooltipText = localTintTypedArray.getText(R.styleable.MenuItem_tooltipText);
      if (localTintTypedArray.hasValue(R.styleable.MenuItem_iconTintMode)) {
        this.itemIconTintMode = DrawableUtils.parseTintMode(localTintTypedArray.getInt(R.styleable.MenuItem_iconTintMode, -1), this.itemIconTintMode);
      } else {
        this.itemIconTintMode = null;
      }
      if (localTintTypedArray.hasValue(R.styleable.MenuItem_iconTint)) {
        this.itemIconTintList = localTintTypedArray.getColorStateList(R.styleable.MenuItem_iconTint);
      } else {
        this.itemIconTintList = null;
      }
      localTintTypedArray.recycle();
      this.itemAdded = false;
    }
    
    public void resetGroup()
    {
      this.groupId = 0;
      this.groupCategory = 0;
      this.groupOrder = 0;
      this.groupCheckable = 0;
      this.groupVisible = true;
      this.groupEnabled = true;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/view/SupportMenuInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */