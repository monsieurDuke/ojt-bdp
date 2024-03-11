package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintLayoutStates
{
  private static final boolean DEBUG = false;
  public static final String TAG = "ConstraintLayoutStates";
  private final ConstraintLayout mConstraintLayout;
  private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
  private ConstraintsChangedListener mConstraintsChangedListener = null;
  int mCurrentConstraintNumber = -1;
  int mCurrentStateId = -1;
  ConstraintSet mDefaultConstraintSet;
  private SparseArray<State> mStateList = new SparseArray();
  
  ConstraintLayoutStates(Context paramContext, ConstraintLayout paramConstraintLayout, int paramInt)
  {
    this.mConstraintLayout = paramConstraintLayout;
    load(paramContext, paramInt);
  }
  
  private void load(Context paramContext, int paramInt)
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt);
    Object localObject1 = null;
    try
    {
      for (paramInt = localXmlResourceParser.getEventType();; paramInt = localXmlResourceParser.next())
      {
        int i = 1;
        if (paramInt == 1) {
          break;
        }
        switch (paramInt)
        {
        case 1: 
        default: 
          break;
        case 3: 
          break;
        case 2: 
          Object localObject2 = localXmlResourceParser.getName();
          switch (((String)localObject2).hashCode())
          {
          }
          for (;;)
          {
            break;
            if (((String)localObject2).equals("Variant"))
            {
              paramInt = 3;
              break label212;
              if (((String)localObject2).equals("layoutDescription"))
              {
                paramInt = 0;
                break label212;
                if (((String)localObject2).equals("StateSet"))
                {
                  paramInt = i;
                  break label212;
                  if (((String)localObject2).equals("State"))
                  {
                    paramInt = 2;
                    break label212;
                    if (((String)localObject2).equals("ConstraintSet"))
                    {
                      paramInt = 4;
                      break label212;
                    }
                  }
                }
              }
            }
          }
          paramInt = -1;
          switch (paramInt)
          {
          default: 
            localObject2 = localObject1;
            break;
          case 4: 
            parseConstraintSet(paramContext, localXmlResourceParser);
            localObject2 = localObject1;
            break;
          case 3: 
            Variant localVariant = new androidx/constraintlayout/widget/ConstraintLayoutStates$Variant;
            localVariant.<init>(paramContext, localXmlResourceParser);
            localObject2 = localObject1;
            if (localObject1 != null)
            {
              ((State)localObject1).add(localVariant);
              localObject2 = localObject1;
            }
            break;
          case 2: 
            localObject2 = new androidx/constraintlayout/widget/ConstraintLayoutStates$State;
            ((State)localObject2).<init>(paramContext, localXmlResourceParser);
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
          label212:
          localXmlResourceParser.getName();
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
  
  private void parseConstraintSet(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    ConstraintSet localConstraintSet = new ConstraintSet();
    int j = paramXmlPullParser.getAttributeCount();
    for (int i = 0; i < j; i++)
    {
      String str2 = paramXmlPullParser.getAttributeName(i);
      String str1 = paramXmlPullParser.getAttributeValue(i);
      if ((str2 != null) && (str1 != null) && ("id".equals(str2)))
      {
        i = -1;
        if (str1.contains("/"))
        {
          str2 = str1.substring(str1.indexOf('/') + 1);
          i = paramContext.getResources().getIdentifier(str2, "id", paramContext.getPackageName());
        }
        j = i;
        if (i == -1) {
          if (str1.length() > 1)
          {
            j = Integer.parseInt(str1.substring(1));
          }
          else
          {
            Log.e("ConstraintLayoutStates", "error in parsing id");
            j = i;
          }
        }
        localConstraintSet.load(paramContext, paramXmlPullParser);
        this.mConstraintSetMap.put(j, localConstraintSet);
        break;
      }
    }
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
  
  public void updateConstraints(int paramInt, float paramFloat1, float paramFloat2)
  {
    int i = this.mCurrentStateId;
    Object localObject1;
    Object localObject2;
    if (i == paramInt)
    {
      if (paramInt == -1) {
        localObject1 = (State)this.mStateList.valueAt(0);
      } else {
        localObject1 = (State)this.mStateList.get(i);
      }
      if ((this.mCurrentConstraintNumber != -1) && (((Variant)((State)localObject1).mVariants.get(this.mCurrentConstraintNumber)).match(paramFloat1, paramFloat2))) {
        return;
      }
      i = ((State)localObject1).findMatch(paramFloat1, paramFloat2);
      if (this.mCurrentConstraintNumber == i) {
        return;
      }
      if (i == -1) {
        localObject2 = this.mDefaultConstraintSet;
      } else {
        localObject2 = ((Variant)((State)localObject1).mVariants.get(i)).mConstraintSet;
      }
      if (i == -1) {
        paramInt = ((State)localObject1).mConstraintID;
      } else {
        paramInt = ((Variant)((State)localObject1).mVariants.get(i)).mConstraintID;
      }
      if (localObject2 == null) {
        return;
      }
      this.mCurrentConstraintNumber = i;
      localObject1 = this.mConstraintsChangedListener;
      if (localObject1 != null) {
        ((ConstraintsChangedListener)localObject1).preLayoutChange(-1, paramInt);
      }
      ((ConstraintSet)localObject2).applyTo(this.mConstraintLayout);
      localObject1 = this.mConstraintsChangedListener;
      if (localObject1 != null) {
        ((ConstraintsChangedListener)localObject1).postLayoutChange(-1, paramInt);
      }
    }
    else
    {
      this.mCurrentStateId = paramInt;
      localObject2 = (State)this.mStateList.get(paramInt);
      int j = ((State)localObject2).findMatch(paramFloat1, paramFloat2);
      if (j == -1) {
        localObject1 = ((State)localObject2).mConstraintSet;
      } else {
        localObject1 = ((Variant)((State)localObject2).mVariants.get(j)).mConstraintSet;
      }
      if (j == -1) {
        i = ((State)localObject2).mConstraintID;
      } else {
        i = ((Variant)((State)localObject2).mVariants.get(j)).mConstraintID;
      }
      if (localObject1 == null)
      {
        Log.v("ConstraintLayoutStates", "NO Constraint set found ! id=" + paramInt + ", dim =" + paramFloat1 + ", " + paramFloat2);
        return;
      }
      this.mCurrentConstraintNumber = j;
      localObject2 = this.mConstraintsChangedListener;
      if (localObject2 != null) {
        ((ConstraintsChangedListener)localObject2).preLayoutChange(paramInt, i);
      }
      ((ConstraintSet)localObject1).applyTo(this.mConstraintLayout);
      localObject1 = this.mConstraintsChangedListener;
      if (localObject1 != null) {
        ((ConstraintsChangedListener)localObject1).postLayoutChange(paramInt, i);
      }
    }
  }
  
  static class State
  {
    int mConstraintID = -1;
    ConstraintSet mConstraintSet;
    int mId;
    ArrayList<ConstraintLayoutStates.Variant> mVariants = new ArrayList();
    
    public State(Context paramContext, XmlPullParser paramXmlPullParser)
    {
      paramXmlPullParser = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.State);
      int j = paramXmlPullParser.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramXmlPullParser.getIndex(i);
        if (k == R.styleable.State_android_id)
        {
          this.mId = paramXmlPullParser.getResourceId(k, this.mId);
        }
        else if (k == R.styleable.State_constraints)
        {
          this.mConstraintID = paramXmlPullParser.getResourceId(k, this.mConstraintID);
          Object localObject = paramContext.getResources().getResourceTypeName(this.mConstraintID);
          paramContext.getResources().getResourceName(this.mConstraintID);
          if ("layout".equals(localObject))
          {
            localObject = new ConstraintSet();
            this.mConstraintSet = ((ConstraintSet)localObject);
            ((ConstraintSet)localObject).clone(paramContext, this.mConstraintID);
          }
        }
      }
      paramXmlPullParser.recycle();
    }
    
    void add(ConstraintLayoutStates.Variant paramVariant)
    {
      this.mVariants.add(paramVariant);
    }
    
    public int findMatch(float paramFloat1, float paramFloat2)
    {
      for (int i = 0; i < this.mVariants.size(); i++) {
        if (((ConstraintLayoutStates.Variant)this.mVariants.get(i)).match(paramFloat1, paramFloat2)) {
          return i;
        }
      }
      return -1;
    }
  }
  
  static class Variant
  {
    int mConstraintID = -1;
    ConstraintSet mConstraintSet;
    int mId;
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
          Object localObject = paramContext.getResources().getResourceTypeName(this.mConstraintID);
          paramContext.getResources().getResourceName(this.mConstraintID);
          if ("layout".equals(localObject))
          {
            localObject = new ConstraintSet();
            this.mConstraintSet = ((ConstraintSet)localObject);
            ((ConstraintSet)localObject).clone(paramContext, this.mConstraintID);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/ConstraintLayoutStates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */