package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateSet
{
  private static final boolean DEBUG = false;
  public static final String TAG = "ConstraintLayoutStates";
  private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
  private ConstraintsChangedListener mConstraintsChangedListener = null;
  int mCurrentConstraintNumber = -1;
  int mCurrentStateId = -1;
  ConstraintSet mDefaultConstraintSet;
  int mDefaultState = -1;
  private SparseArray<State> mStateList = new SparseArray();
  
  public StateSet(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    load(paramContext, paramXmlPullParser);
  }
  
  private void load(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    Object localObject1 = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.StateSet);
    int j = ((TypedArray)localObject1).getIndexCount();
    for (int i = 0; i < j; i++)
    {
      int k = ((TypedArray)localObject1).getIndex(i);
      if (k == R.styleable.StateSet_defaultState) {
        this.mDefaultState = ((TypedArray)localObject1).getResourceId(k, this.mDefaultState);
      }
    }
    ((TypedArray)localObject1).recycle();
    localObject1 = null;
    try
    {
      for (i = paramXmlPullParser.getEventType();; i = paramXmlPullParser.next())
      {
        j = 1;
        if (i == 1) {
          break;
        }
        switch (i)
        {
        case 1: 
        default: 
          break;
        case 3: 
          if ("StateSet".equals(paramXmlPullParser.getName())) {
            return;
          }
          break;
        case 2: 
          Object localObject2 = paramXmlPullParser.getName();
          switch (((String)localObject2).hashCode())
          {
          }
          for (;;)
          {
            break;
            if (((String)localObject2).equals("Variant"))
            {
              i = 3;
              break label262;
              if (((String)localObject2).equals("StateSet"))
              {
                i = j;
                break label262;
                if (((String)localObject2).equals("LayoutDescription"))
                {
                  i = 0;
                  break label262;
                  if (((String)localObject2).equals("State"))
                  {
                    i = 2;
                    break label262;
                  }
                }
              }
            }
          }
          i = -1;
          switch (i)
          {
          default: 
            localObject2 = localObject1;
            break;
          case 3: 
            Variant localVariant = new androidx/constraintlayout/widget/StateSet$Variant;
            localVariant.<init>(paramContext, paramXmlPullParser);
            localObject2 = localObject1;
            if (localObject1 != null)
            {
              ((State)localObject1).add(localVariant);
              localObject2 = localObject1;
            }
            break;
          case 2: 
            localObject2 = new androidx/constraintlayout/widget/StateSet$State;
            ((State)localObject2).<init>(paramContext, paramXmlPullParser);
            this.mStateList.put(((State)localObject2).mId, localObject2);
            break;
          case 1: 
            localObject2 = localObject1;
            break;
          case 0: 
            localObject2 = localObject1;
          }
          localObject1 = localObject2;
          break;
        case 0: 
          label262:
          paramXmlPullParser.getName();
        }
      }
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (XmlPullParserException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public int convertToConstraintSet(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    State localState = (State)this.mStateList.get(paramInt2);
    if (localState == null) {
      return paramInt2;
    }
    if ((paramFloat1 != -1.0F) && (paramFloat2 != -1.0F))
    {
      localObject = null;
      Iterator localIterator = localState.mVariants.iterator();
      while (localIterator.hasNext())
      {
        Variant localVariant = (Variant)localIterator.next();
        if (localVariant.match(paramFloat1, paramFloat2))
        {
          if (paramInt1 == localVariant.mConstraintID) {
            return paramInt1;
          }
          localObject = localVariant;
        }
      }
      if (localObject != null) {
        return ((Variant)localObject).mConstraintID;
      }
      return localState.mConstraintID;
    }
    if (localState.mConstraintID == paramInt1) {
      return paramInt1;
    }
    Object localObject = localState.mVariants.iterator();
    while (((Iterator)localObject).hasNext()) {
      if (paramInt1 == ((Variant)((Iterator)localObject).next()).mConstraintID) {
        return paramInt1;
      }
    }
    return localState.mConstraintID;
  }
  
  public boolean needsToChange(int paramInt, float paramFloat1, float paramFloat2)
  {
    int i = this.mCurrentStateId;
    if (i != paramInt) {
      return true;
    }
    if (paramInt == -1) {
      localObject = this.mStateList.valueAt(0);
    } else {
      localObject = this.mStateList.get(i);
    }
    Object localObject = (State)localObject;
    if ((this.mCurrentConstraintNumber != -1) && (((Variant)((State)localObject).mVariants.get(this.mCurrentConstraintNumber)).match(paramFloat1, paramFloat2))) {
      return false;
    }
    return this.mCurrentConstraintNumber != ((State)localObject).findMatch(paramFloat1, paramFloat2);
  }
  
  public void setOnConstraintsChanged(ConstraintsChangedListener paramConstraintsChangedListener)
  {
    this.mConstraintsChangedListener = paramConstraintsChangedListener;
  }
  
  public int stateGetConstraintID(int paramInt1, int paramInt2, int paramInt3)
  {
    return updateConstraints(-1, paramInt1, paramInt2, paramInt3);
  }
  
  public int updateConstraints(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    if (paramInt1 == paramInt2)
    {
      if (paramInt2 == -1) {
        localState = (State)this.mStateList.valueAt(0);
      } else {
        localState = (State)this.mStateList.get(this.mCurrentStateId);
      }
      if (localState == null) {
        return -1;
      }
      if ((this.mCurrentConstraintNumber != -1) && (((Variant)localState.mVariants.get(paramInt1)).match(paramFloat1, paramFloat2))) {
        return paramInt1;
      }
      paramInt2 = localState.findMatch(paramFloat1, paramFloat2);
      if (paramInt1 == paramInt2) {
        return paramInt1;
      }
      if (paramInt2 == -1) {
        paramInt1 = localState.mConstraintID;
      } else {
        paramInt1 = ((Variant)localState.mVariants.get(paramInt2)).mConstraintID;
      }
      return paramInt1;
    }
    State localState = (State)this.mStateList.get(paramInt2);
    if (localState == null) {
      return -1;
    }
    paramInt1 = localState.findMatch(paramFloat1, paramFloat2);
    if (paramInt1 == -1) {
      paramInt1 = localState.mConstraintID;
    } else {
      paramInt1 = ((Variant)localState.mVariants.get(paramInt1)).mConstraintID;
    }
    return paramInt1;
  }
  
  static class State
  {
    int mConstraintID = -1;
    int mId;
    boolean mIsLayout = false;
    ArrayList<StateSet.Variant> mVariants = new ArrayList();
    
    public State(Context paramContext, XmlPullParser paramXmlPullParser)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.State);
      int j = localTypedArray.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = localTypedArray.getIndex(i);
        if (k == R.styleable.State_android_id)
        {
          this.mId = localTypedArray.getResourceId(k, this.mId);
        }
        else if (k == R.styleable.State_constraints)
        {
          this.mConstraintID = localTypedArray.getResourceId(k, this.mConstraintID);
          paramXmlPullParser = paramContext.getResources().getResourceTypeName(this.mConstraintID);
          paramContext.getResources().getResourceName(this.mConstraintID);
          if ("layout".equals(paramXmlPullParser)) {
            this.mIsLayout = true;
          }
        }
      }
      localTypedArray.recycle();
    }
    
    void add(StateSet.Variant paramVariant)
    {
      this.mVariants.add(paramVariant);
    }
    
    public int findMatch(float paramFloat1, float paramFloat2)
    {
      for (int i = 0; i < this.mVariants.size(); i++) {
        if (((StateSet.Variant)this.mVariants.get(i)).match(paramFloat1, paramFloat2)) {
          return i;
        }
      }
      return -1;
    }
  }
  
  static class Variant
  {
    int mConstraintID = -1;
    int mId;
    boolean mIsLayout = false;
    float mMaxHeight = NaN.0F;
    float mMaxWidth = NaN.0F;
    float mMinHeight = NaN.0F;
    float mMinWidth = NaN.0F;
    
    public Variant(Context paramContext, XmlPullParser paramXmlPullParser)
    {
      paramXmlPullParser = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.Variant);
      int j = paramXmlPullParser.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramXmlPullParser.getIndex(i);
        if (k == R.styleable.Variant_constraints)
        {
          this.mConstraintID = paramXmlPullParser.getResourceId(k, this.mConstraintID);
          String str = paramContext.getResources().getResourceTypeName(this.mConstraintID);
          paramContext.getResources().getResourceName(this.mConstraintID);
          if ("layout".equals(str)) {
            this.mIsLayout = true;
          }
        }
        else if (k == R.styleable.Variant_region_heightLessThan)
        {
          this.mMaxHeight = paramXmlPullParser.getDimension(k, this.mMaxHeight);
        }
        else if (k == R.styleable.Variant_region_heightMoreThan)
        {
          this.mMinHeight = paramXmlPullParser.getDimension(k, this.mMinHeight);
        }
        else if (k == R.styleable.Variant_region_widthLessThan)
        {
          this.mMaxWidth = paramXmlPullParser.getDimension(k, this.mMaxWidth);
        }
        else if (k == R.styleable.Variant_region_widthMoreThan)
        {
          this.mMinWidth = paramXmlPullParser.getDimension(k, this.mMinWidth);
        }
        else
        {
          Log.v("ConstraintLayoutStates", "Unknown tag");
        }
      }
      paramXmlPullParser.recycle();
    }
    
    boolean match(float paramFloat1, float paramFloat2)
    {
      if ((!Float.isNaN(this.mMinWidth)) && (paramFloat1 < this.mMinWidth)) {
        return false;
      }
      if ((!Float.isNaN(this.mMinHeight)) && (paramFloat2 < this.mMinHeight)) {
        return false;
      }
      if ((!Float.isNaN(this.mMaxWidth)) && (paramFloat1 > this.mMaxWidth)) {
        return false;
      }
      return (Float.isNaN(this.mMaxHeight)) || (paramFloat2 <= this.mMaxHeight);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/StateSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */