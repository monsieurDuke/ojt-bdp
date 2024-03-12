package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public abstract class ConstraintHelper
  extends View
{
  protected int mCount;
  protected Helper mHelperWidget;
  protected int[] mIds = new int[32];
  protected HashMap<Integer, String> mMap = new HashMap();
  protected String mReferenceIds;
  protected String mReferenceTags;
  protected boolean mUseViewMeasure = false;
  private View[] mViews = null;
  protected Context myContext;
  
  public ConstraintHelper(Context paramContext)
  {
    super(paramContext);
    this.myContext = paramContext;
    init(null);
  }
  
  public ConstraintHelper(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.myContext = paramContext;
    init(paramAttributeSet);
  }
  
  public ConstraintHelper(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.myContext = paramContext;
    init(paramAttributeSet);
  }
  
  private void addID(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (this.myContext == null) {
        return;
      }
      String str = paramString.trim();
      if ((getParent() instanceof ConstraintLayout)) {
        paramString = (ConstraintLayout)getParent();
      }
      int i = findId(str);
      if (i != 0)
      {
        this.mMap.put(Integer.valueOf(i), str);
        addRscID(i);
      }
      else
      {
        Log.w("ConstraintHelper", "Could not find id of \"" + str + "\"");
      }
      return;
    }
  }
  
  private void addRscID(int paramInt)
  {
    if (paramInt == getId()) {
      return;
    }
    int i = this.mCount;
    int[] arrayOfInt = this.mIds;
    if (i + 1 > arrayOfInt.length) {
      this.mIds = Arrays.copyOf(arrayOfInt, arrayOfInt.length * 2);
    }
    arrayOfInt = this.mIds;
    i = this.mCount;
    arrayOfInt[i] = paramInt;
    this.mCount = (i + 1);
  }
  
  private void addTag(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (this.myContext == null) {
        return;
      }
      String str = paramString.trim();
      paramString = null;
      if ((getParent() instanceof ConstraintLayout)) {
        paramString = (ConstraintLayout)getParent();
      }
      if (paramString == null)
      {
        Log.w("ConstraintHelper", "Parent not a ConstraintLayout");
        return;
      }
      int j = paramString.getChildCount();
      for (int i = 0; i < j; i++)
      {
        View localView = paramString.getChildAt(i);
        ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
        if (((localLayoutParams instanceof ConstraintLayout.LayoutParams)) && (str.equals(((ConstraintLayout.LayoutParams)localLayoutParams).constraintTag))) {
          if (localView.getId() == -1) {
            Log.w("ConstraintHelper", "to use ConstraintTag view " + localView.getClass().getSimpleName() + " must have an ID");
          } else {
            addRscID(localView.getId());
          }
        }
      }
      return;
    }
  }
  
  private int[] convertReferenceString(View paramView, String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    paramView.getContext();
    paramString = new int[arrayOfString.length];
    int j = 0;
    int i = 0;
    while (i < arrayOfString.length)
    {
      int m = findId(arrayOfString[i].trim());
      int k = j;
      if (m != 0)
      {
        paramString[j] = m;
        k = j + 1;
      }
      i++;
      j = k;
    }
    paramView = paramString;
    if (j != arrayOfString.length) {
      paramView = Arrays.copyOf(paramString, j);
    }
    return paramView;
  }
  
  private int findId(ConstraintLayout paramConstraintLayout, String paramString)
  {
    if ((paramString != null) && (paramConstraintLayout != null))
    {
      Resources localResources = this.myContext.getResources();
      if (localResources == null) {
        return 0;
      }
      int j = paramConstraintLayout.getChildCount();
      for (int i = 0; i < j; i++)
      {
        View localView = paramConstraintLayout.getChildAt(i);
        if (localView.getId() != -1)
        {
          Object localObject = null;
          try
          {
            String str = localResources.getResourceEntryName(localView.getId());
            localObject = str;
          }
          catch (Resources.NotFoundException localNotFoundException) {}
          if (paramString.equals(localObject)) {
            return localView.getId();
          }
        }
      }
      return 0;
    }
    return 0;
  }
  
  private int findId(String paramString)
  {
    ConstraintLayout localConstraintLayout = null;
    if ((getParent() instanceof ConstraintLayout)) {
      localConstraintLayout = (ConstraintLayout)getParent();
    }
    int i = 0;
    int j = i;
    if (isInEditMode())
    {
      j = i;
      if (localConstraintLayout != null)
      {
        Object localObject = localConstraintLayout.getDesignInformation(0, paramString);
        j = i;
        if ((localObject instanceof Integer)) {
          j = ((Integer)localObject).intValue();
        }
      }
    }
    i = j;
    if (j == 0)
    {
      i = j;
      if (localConstraintLayout != null) {
        i = findId(localConstraintLayout, paramString);
      }
    }
    j = i;
    if (i == 0) {
      try
      {
        j = R.id.class.getField(paramString).getInt(null);
      }
      catch (Exception localException)
      {
        j = i;
      }
    }
    i = j;
    if (j == 0) {
      i = this.myContext.getResources().getIdentifier(paramString, "id", this.myContext.getPackageName());
    }
    return i;
  }
  
  public void addView(View paramView)
  {
    if (paramView == this) {
      return;
    }
    if (paramView.getId() == -1)
    {
      Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have an id");
      return;
    }
    if (paramView.getParent() == null)
    {
      Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have a parent");
      return;
    }
    this.mReferenceIds = null;
    addRscID(paramView.getId());
    requestLayout();
  }
  
  protected void applyLayoutFeatures()
  {
    ViewParent localViewParent = getParent();
    if ((localViewParent != null) && ((localViewParent instanceof ConstraintLayout))) {
      applyLayoutFeatures((ConstraintLayout)localViewParent);
    }
  }
  
  protected void applyLayoutFeatures(ConstraintLayout paramConstraintLayout)
  {
    int j = getVisibility();
    float f = 0.0F;
    if (Build.VERSION.SDK_INT >= 21) {
      f = getElevation();
    }
    for (int i = 0; i < this.mCount; i++)
    {
      View localView = paramConstraintLayout.getViewById(this.mIds[i]);
      if (localView != null)
      {
        localView.setVisibility(j);
        if ((f > 0.0F) && (Build.VERSION.SDK_INT >= 21)) {
          localView.setTranslationZ(localView.getTranslationZ() + f);
        }
      }
    }
  }
  
  protected void applyLayoutFeaturesInConstraintSet(ConstraintLayout paramConstraintLayout) {}
  
  public boolean containsId(int paramInt)
  {
    boolean bool2 = false;
    int[] arrayOfInt = this.mIds;
    int j = arrayOfInt.length;
    boolean bool1;
    for (int i = 0;; i++)
    {
      bool1 = bool2;
      if (i >= j) {
        break;
      }
      if (arrayOfInt[i] == paramInt)
      {
        bool1 = true;
        break;
      }
    }
    return bool1;
  }
  
  public int[] getReferencedIds()
  {
    return Arrays.copyOf(this.mIds, this.mCount);
  }
  
  protected View[] getViews(ConstraintLayout paramConstraintLayout)
  {
    View[] arrayOfView = this.mViews;
    if ((arrayOfView == null) || (arrayOfView.length != this.mCount)) {
      this.mViews = new View[this.mCount];
    }
    for (int i = 0; i < this.mCount; i++)
    {
      int j = this.mIds[i];
      this.mViews[i] = paramConstraintLayout.getViewById(j);
    }
    return this.mViews;
  }
  
  public int indexFromId(int paramInt)
  {
    int j = -1;
    for (int m : this.mIds)
    {
      j++;
      if (m == paramInt) {
        return j;
      }
    }
    return j;
  }
  
  protected void init(AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramAttributeSet.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramAttributeSet.getIndex(i);
        String str;
        if (k == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids)
        {
          str = paramAttributeSet.getString(k);
          this.mReferenceIds = str;
          setIds(str);
        }
        else if (k == R.styleable.ConstraintLayout_Layout_constraint_referenced_tags)
        {
          str = paramAttributeSet.getString(k);
          this.mReferenceTags = str;
          setReferenceTags(str);
        }
      }
      paramAttributeSet.recycle();
    }
  }
  
  public void loadParameters(ConstraintSet.Constraint paramConstraint, HelperWidget paramHelperWidget, ConstraintLayout.LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray)
  {
    if (paramConstraint.layout.mReferenceIds != null) {
      setReferencedIds(paramConstraint.layout.mReferenceIds);
    } else if (paramConstraint.layout.mReferenceIdString != null) {
      if (paramConstraint.layout.mReferenceIdString.length() > 0) {
        paramConstraint.layout.mReferenceIds = convertReferenceString(this, paramConstraint.layout.mReferenceIdString);
      } else {
        paramConstraint.layout.mReferenceIds = null;
      }
    }
    if (paramHelperWidget != null)
    {
      paramHelperWidget.removeAllIds();
      if (paramConstraint.layout.mReferenceIds != null) {
        for (int i = 0; i < paramConstraint.layout.mReferenceIds.length; i++)
        {
          paramLayoutParams = (ConstraintWidget)paramSparseArray.get(paramConstraint.layout.mReferenceIds[i]);
          if (paramLayoutParams != null) {
            paramHelperWidget.add(paramLayoutParams);
          }
        }
      }
    }
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    String str = this.mReferenceIds;
    if (str != null) {
      setIds(str);
    }
    str = this.mReferenceTags;
    if (str != null) {
      setReferenceTags(str);
    }
  }
  
  public void onDraw(Canvas paramCanvas) {}
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mUseViewMeasure) {
      super.onMeasure(paramInt1, paramInt2);
    } else {
      setMeasuredDimension(0, 0);
    }
  }
  
  public int removeView(View paramView)
  {
    int k = -1;
    int m = paramView.getId();
    if (m == -1) {
      return -1;
    }
    this.mReferenceIds = null;
    int j;
    for (int i = 0;; i++)
    {
      j = k;
      if (i >= this.mCount) {
        break;
      }
      if (this.mIds[i] == m)
      {
        j = i;
        for (;;)
        {
          k = this.mCount;
          if (i >= k - 1) {
            break;
          }
          paramView = this.mIds;
          paramView[i] = paramView[(i + 1)];
          i++;
        }
        this.mIds[(k - 1)] = 0;
        this.mCount = (k - 1);
        break;
      }
    }
    requestLayout();
    return j;
  }
  
  public void resolveRtl(ConstraintWidget paramConstraintWidget, boolean paramBoolean) {}
  
  protected void setIds(String paramString)
  {
    this.mReferenceIds = paramString;
    if (paramString == null) {
      return;
    }
    int i = 0;
    this.mCount = 0;
    for (;;)
    {
      int j = paramString.indexOf(',', i);
      if (j == -1)
      {
        addID(paramString.substring(i));
        return;
      }
      addID(paramString.substring(i, j));
      i = j + 1;
    }
  }
  
  protected void setReferenceTags(String paramString)
  {
    this.mReferenceTags = paramString;
    if (paramString == null) {
      return;
    }
    int i = 0;
    this.mCount = 0;
    for (;;)
    {
      int j = paramString.indexOf(',', i);
      if (j == -1)
      {
        addTag(paramString.substring(i));
        return;
      }
      addTag(paramString.substring(i, j));
      i = j + 1;
    }
  }
  
  public void setReferencedIds(int[] paramArrayOfInt)
  {
    this.mReferenceIds = null;
    this.mCount = 0;
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      addRscID(paramArrayOfInt[i]);
    }
  }
  
  public void setTag(int paramInt, Object paramObject)
  {
    super.setTag(paramInt, paramObject);
    if ((paramObject == null) && (this.mReferenceIds == null)) {
      addRscID(paramInt);
    }
  }
  
  public void updatePostConstraints(ConstraintLayout paramConstraintLayout) {}
  
  public void updatePostLayout(ConstraintLayout paramConstraintLayout) {}
  
  public void updatePostMeasure(ConstraintLayout paramConstraintLayout) {}
  
  public void updatePreDraw(ConstraintLayout paramConstraintLayout) {}
  
  public void updatePreLayout(ConstraintWidgetContainer paramConstraintWidgetContainer, Helper paramHelper, SparseArray<ConstraintWidget> paramSparseArray)
  {
    paramHelper.removeAllIds();
    for (int i = 0; i < this.mCount; i++) {
      paramHelper.add((ConstraintWidget)paramSparseArray.get(this.mIds[i]));
    }
  }
  
  public void updatePreLayout(ConstraintLayout paramConstraintLayout)
  {
    if (isInEditMode()) {
      setIds(this.mReferenceIds);
    }
    Object localObject = this.mHelperWidget;
    if (localObject == null) {
      return;
    }
    ((Helper)localObject).removeAllIds();
    for (int i = 0; i < this.mCount; i++)
    {
      int j = this.mIds[i];
      View localView = paramConstraintLayout.getViewById(j);
      localObject = localView;
      if (localView == null)
      {
        String str = (String)this.mMap.get(Integer.valueOf(j));
        j = findId(paramConstraintLayout, str);
        localObject = localView;
        if (j != 0)
        {
          this.mIds[i] = j;
          this.mMap.put(Integer.valueOf(j), str);
          localObject = paramConstraintLayout.getViewById(j);
        }
      }
      if (localObject != null) {
        this.mHelperWidget.add(paramConstraintLayout.getViewWidget((View)localObject));
      }
    }
    this.mHelperWidget.updateConstraints(paramConstraintLayout.mLayoutWidget);
  }
  
  public void validateParams()
  {
    if (this.mHelperWidget == null) {
      return;
    }
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    if ((localLayoutParams instanceof ConstraintLayout.LayoutParams)) {
      ((ConstraintLayout.LayoutParams)localLayoutParams).widget = ((ConstraintWidget)this.mHelperWidget);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/ConstraintHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */