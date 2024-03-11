package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class AccessibilityWindowInfoCompat
{
  public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
  public static final int TYPE_APPLICATION = 1;
  public static final int TYPE_INPUT_METHOD = 2;
  public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
  public static final int TYPE_SYSTEM = 3;
  private static final int UNDEFINED = -1;
  private final Object mInfo;
  
  private AccessibilityWindowInfoCompat(Object paramObject)
  {
    this.mInfo = paramObject;
  }
  
  public static AccessibilityWindowInfoCompat obtain()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return wrapNonNullInstance(Api21Impl.obtain());
    }
    return null;
  }
  
  public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat paramAccessibilityWindowInfoCompat)
  {
    int i = Build.VERSION.SDK_INT;
    Object localObject = null;
    if (i >= 21)
    {
      if (paramAccessibilityWindowInfoCompat == null) {
        paramAccessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat)localObject;
      } else {
        paramAccessibilityWindowInfoCompat = wrapNonNullInstance(Api21Impl.obtain((AccessibilityWindowInfo)paramAccessibilityWindowInfoCompat.mInfo));
      }
      return paramAccessibilityWindowInfoCompat;
    }
    return null;
  }
  
  private static String typeToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "<UNKNOWN>";
    case 4: 
      return "TYPE_ACCESSIBILITY_OVERLAY";
    case 3: 
      return "TYPE_SYSTEM";
    case 2: 
      return "TYPE_INPUT_METHOD";
    }
    return "TYPE_APPLICATION";
  }
  
  static AccessibilityWindowInfoCompat wrapNonNullInstance(Object paramObject)
  {
    if (paramObject != null) {
      return new AccessibilityWindowInfoCompat(paramObject);
    }
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof AccessibilityWindowInfoCompat)) {
      return false;
    }
    paramObject = (AccessibilityWindowInfoCompat)paramObject;
    Object localObject = this.mInfo;
    if (localObject == null)
    {
      if (((AccessibilityWindowInfoCompat)paramObject).mInfo != null) {
        bool = false;
      }
      return bool;
    }
    return localObject.equals(((AccessibilityWindowInfoCompat)paramObject).mInfo);
  }
  
  public AccessibilityNodeInfoCompat getAnchor()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return AccessibilityNodeInfoCompat.wrapNonNullInstance(Api24Impl.getAnchor((AccessibilityWindowInfo)this.mInfo));
    }
    return null;
  }
  
  public void getBoundsInScreen(Rect paramRect)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.getBoundsInScreen((AccessibilityWindowInfo)this.mInfo, paramRect);
    }
  }
  
  public AccessibilityWindowInfoCompat getChild(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return wrapNonNullInstance(Api21Impl.getChild((AccessibilityWindowInfo)this.mInfo, paramInt));
    }
    return null;
  }
  
  public int getChildCount()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getChildCount((AccessibilityWindowInfo)this.mInfo);
    }
    return 0;
  }
  
  public int getId()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getId((AccessibilityWindowInfo)this.mInfo);
    }
    return -1;
  }
  
  public int getLayer()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getLayer((AccessibilityWindowInfo)this.mInfo);
    }
    return -1;
  }
  
  public AccessibilityWindowInfoCompat getParent()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return wrapNonNullInstance(Api21Impl.getParent((AccessibilityWindowInfo)this.mInfo));
    }
    return null;
  }
  
  public AccessibilityNodeInfoCompat getRoot()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return AccessibilityNodeInfoCompat.wrapNonNullInstance(Api21Impl.getRoot((AccessibilityWindowInfo)this.mInfo));
    }
    return null;
  }
  
  public CharSequence getTitle()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return Api24Impl.getTitle((AccessibilityWindowInfo)this.mInfo);
    }
    return null;
  }
  
  public int getType()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getType((AccessibilityWindowInfo)this.mInfo);
    }
    return -1;
  }
  
  public int hashCode()
  {
    Object localObject = this.mInfo;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    return i;
  }
  
  public boolean isAccessibilityFocused()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.isAccessibilityFocused((AccessibilityWindowInfo)this.mInfo);
    }
    return true;
  }
  
  public boolean isActive()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.isActive((AccessibilityWindowInfo)this.mInfo);
    }
    return true;
  }
  
  public boolean isFocused()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.isFocused((AccessibilityWindowInfo)this.mInfo);
    }
    return true;
  }
  
  public void recycle()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.recycle((AccessibilityWindowInfo)this.mInfo);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    Object localObject = new Rect();
    getBoundsInScreen((Rect)localObject);
    localStringBuilder1.append("AccessibilityWindowInfo[");
    localStringBuilder1.append("id=").append(getId());
    StringBuilder localStringBuilder2 = localStringBuilder1.append(", type=");
    String str = typeToString(getType());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder2.append(str);
    localStringBuilder1.append(", layer=").append(getLayer());
    localStringBuilder1.append(", bounds=").append(localObject);
    localStringBuilder1.append(", focused=").append(isFocused());
    localStringBuilder1.append(", active=").append(isActive());
    localStringBuilder2 = localStringBuilder1.append(", hasParent=");
    localObject = getParent();
    boolean bool2 = true;
    boolean bool1;
    if (localObject != null) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    localStringBuilder2.append(bool1);
    localStringBuilder2 = localStringBuilder1.append(", hasChildren=");
    if (getChildCount() > 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    localStringBuilder2.append(bool1);
    localStringBuilder1.append(']');
    return localStringBuilder1.toString();
  }
  
  private static class Api21Impl
  {
    static void getBoundsInScreen(AccessibilityWindowInfo paramAccessibilityWindowInfo, Rect paramRect)
    {
      paramAccessibilityWindowInfo.getBoundsInScreen(paramRect);
    }
    
    static AccessibilityWindowInfo getChild(AccessibilityWindowInfo paramAccessibilityWindowInfo, int paramInt)
    {
      return paramAccessibilityWindowInfo.getChild(paramInt);
    }
    
    static int getChildCount(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getChildCount();
    }
    
    static int getId(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getId();
    }
    
    static int getLayer(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getLayer();
    }
    
    static AccessibilityWindowInfo getParent(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getParent();
    }
    
    static AccessibilityNodeInfo getRoot(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getRoot();
    }
    
    static int getType(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getType();
    }
    
    static boolean isAccessibilityFocused(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.isAccessibilityFocused();
    }
    
    static boolean isActive(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.isActive();
    }
    
    static boolean isFocused(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.isFocused();
    }
    
    static AccessibilityWindowInfo obtain()
    {
      return AccessibilityWindowInfo.obtain();
    }
    
    static AccessibilityWindowInfo obtain(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return AccessibilityWindowInfo.obtain(paramAccessibilityWindowInfo);
    }
    
    static void recycle(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      paramAccessibilityWindowInfo.recycle();
    }
  }
  
  private static class Api24Impl
  {
    static AccessibilityNodeInfo getAnchor(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getAnchor();
    }
    
    static CharSequence getTitle(AccessibilityWindowInfo paramAccessibilityWindowInfo)
    {
      return paramAccessibilityWindowInfo.getTitle();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/accessibility/AccessibilityWindowInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */