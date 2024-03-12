package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLNumber;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WidgetFrame
{
  private static final boolean OLD_SYSTEM = true;
  public static float phone_orientation = NaN.0F;
  public float alpha = NaN.0F;
  public int bottom = 0;
  public float interpolatedPos = NaN.0F;
  public int left = 0;
  public final HashMap<String, CustomVariable> mCustom = new HashMap();
  public String name = null;
  public float pivotX = NaN.0F;
  public float pivotY = NaN.0F;
  public int right = 0;
  public float rotationX = NaN.0F;
  public float rotationY = NaN.0F;
  public float rotationZ = NaN.0F;
  public float scaleX = NaN.0F;
  public float scaleY = NaN.0F;
  public int top = 0;
  public float translationX = NaN.0F;
  public float translationY = NaN.0F;
  public float translationZ = NaN.0F;
  public int visibility = 0;
  public ConstraintWidget widget = null;
  
  public WidgetFrame() {}
  
  public WidgetFrame(WidgetFrame paramWidgetFrame)
  {
    this.widget = paramWidgetFrame.widget;
    this.left = paramWidgetFrame.left;
    this.top = paramWidgetFrame.top;
    this.right = paramWidgetFrame.right;
    this.bottom = paramWidgetFrame.bottom;
    updateAttributes(paramWidgetFrame);
  }
  
  public WidgetFrame(ConstraintWidget paramConstraintWidget)
  {
    this.widget = paramConstraintWidget;
  }
  
  private static void add(StringBuilder paramStringBuilder, String paramString, float paramFloat)
  {
    if (Float.isNaN(paramFloat)) {
      return;
    }
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(": ");
    paramStringBuilder.append(paramFloat);
    paramStringBuilder.append(",\n");
  }
  
  private static void add(StringBuilder paramStringBuilder, String paramString, int paramInt)
  {
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(": ");
    paramStringBuilder.append(paramInt);
    paramStringBuilder.append(",\n");
  }
  
  private static float interpolate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    boolean bool2 = Float.isNaN(paramFloat1);
    boolean bool1 = Float.isNaN(paramFloat2);
    if ((bool2) && (bool1)) {
      return NaN.0F;
    }
    if (bool2) {
      paramFloat1 = paramFloat3;
    }
    if (bool1) {
      paramFloat2 = paramFloat3;
    }
    return (paramFloat2 - paramFloat1) * paramFloat4 + paramFloat1;
  }
  
  public static void interpolate(int paramInt1, int paramInt2, WidgetFrame paramWidgetFrame1, WidgetFrame paramWidgetFrame2, WidgetFrame paramWidgetFrame3, Transition paramTransition, float paramFloat)
  {
    int i8 = (int)(paramFloat * 100.0F);
    int i1 = paramWidgetFrame2.left;
    int n = paramWidgetFrame2.top;
    int i5 = paramWidgetFrame3.left;
    int i4 = paramWidgetFrame3.top;
    int i = paramWidgetFrame2.right;
    int i3 = paramWidgetFrame2.left;
    int i2 = paramWidgetFrame2.bottom;
    int m = paramWidgetFrame2.top;
    int j = paramWidgetFrame3.right - paramWidgetFrame3.left;
    int k = paramWidgetFrame3.bottom - paramWidgetFrame3.top;
    float f4 = paramFloat;
    float f1 = paramWidgetFrame2.alpha;
    float f3 = paramWidgetFrame3.alpha;
    if (paramWidgetFrame2.visibility == 8)
    {
      i1 = (int)(i1 - j / 2.0F);
      n = (int)(n - k / 2.0F);
      m = k;
      if (Float.isNaN(f1))
      {
        i = j;
        f1 = 0.0F;
      }
      else
      {
        i = j;
      }
    }
    else
    {
      i -= i3;
      m = i2 - m;
    }
    i2 = paramWidgetFrame3.visibility;
    float f2;
    if (i2 == 8)
    {
      int i6 = (int)(i5 - i / 2.0F);
      int i7 = (int)(i4 - m / 2.0F);
      i5 = i;
      i4 = m;
      i3 = i4;
      k = i6;
      j = i7;
      f2 = f3;
      i2 = i5;
      if (Float.isNaN(f3))
      {
        f2 = 0.0F;
        i3 = i4;
        k = i6;
        j = i7;
        i2 = i5;
      }
    }
    else
    {
      i3 = k;
      i2 = j;
      f2 = f3;
      j = i4;
      k = i5;
    }
    f3 = f1;
    if (Float.isNaN(f1))
    {
      f3 = f1;
      if (!Float.isNaN(f2)) {
        f3 = 1.0F;
      }
    }
    f1 = f2;
    if (!Float.isNaN(f3))
    {
      f1 = f2;
      if (Float.isNaN(f2)) {
        f1 = 1.0F;
      }
    }
    if (paramWidgetFrame2.visibility == 4) {
      f2 = 0.0F;
    } else {
      f2 = f3;
    }
    if (paramWidgetFrame3.visibility == 4) {
      f1 = 0.0F;
    }
    Object localObject2;
    if ((paramWidgetFrame1.widget != null) && (paramTransition.hasPositionKeyframes()))
    {
      localObject2 = paramTransition.findPreviousPosition(paramWidgetFrame1.widget.stringId, i8);
      localObject1 = paramTransition.findNextPosition(paramWidgetFrame1.widget.stringId, i8);
      paramTransition = (Transition)localObject1;
      if (localObject2 == localObject1) {
        paramTransition = null;
      }
      i5 = 100;
      if (localObject2 != null)
      {
        i1 = (int)(((Transition.KeyPosition)localObject2).x * paramInt1);
        f3 = ((Transition.KeyPosition)localObject2).y;
        n = (int)(f3 * paramInt2);
        i4 = ((Transition.KeyPosition)localObject2).frame;
      }
      else
      {
        i4 = 0;
      }
      if (paramTransition != null)
      {
        paramInt1 = (int)(paramTransition.x * paramInt1);
        j = (int)(paramTransition.y * paramInt2);
        paramInt2 = paramTransition.frame;
      }
      else
      {
        paramInt1 = k;
        paramInt2 = i5;
      }
      f4 = (100.0F * paramFloat - i4) / (paramInt2 - i4);
    }
    else
    {
      paramInt1 = k;
    }
    paramWidgetFrame1.widget = paramWidgetFrame2.widget;
    paramInt1 = (int)(i1 + (paramInt1 - i1) * f4);
    paramWidgetFrame1.left = paramInt1;
    paramInt2 = (int)(n + (j - n) * f4);
    paramWidgetFrame1.top = paramInt2;
    i = (int)((1.0F - paramFloat) * i + i2 * paramFloat);
    j = (int)((1.0F - paramFloat) * m + i3 * paramFloat);
    paramWidgetFrame1.right = (paramInt1 + i);
    paramWidgetFrame1.bottom = (paramInt2 + j);
    paramWidgetFrame1.pivotX = interpolate(paramWidgetFrame2.pivotX, paramWidgetFrame3.pivotX, 0.5F, paramFloat);
    paramWidgetFrame1.pivotY = interpolate(paramWidgetFrame2.pivotY, paramWidgetFrame3.pivotY, 0.5F, paramFloat);
    paramWidgetFrame1.rotationX = interpolate(paramWidgetFrame2.rotationX, paramWidgetFrame3.rotationX, 0.0F, paramFloat);
    paramWidgetFrame1.rotationY = interpolate(paramWidgetFrame2.rotationY, paramWidgetFrame3.rotationY, 0.0F, paramFloat);
    paramWidgetFrame1.rotationZ = interpolate(paramWidgetFrame2.rotationZ, paramWidgetFrame3.rotationZ, 0.0F, paramFloat);
    paramWidgetFrame1.scaleX = interpolate(paramWidgetFrame2.scaleX, paramWidgetFrame3.scaleX, 1.0F, paramFloat);
    paramWidgetFrame1.scaleY = interpolate(paramWidgetFrame2.scaleY, paramWidgetFrame3.scaleY, 1.0F, paramFloat);
    paramWidgetFrame1.translationX = interpolate(paramWidgetFrame2.translationX, paramWidgetFrame3.translationX, 0.0F, paramFloat);
    paramWidgetFrame1.translationY = interpolate(paramWidgetFrame2.translationY, paramWidgetFrame3.translationY, 0.0F, paramFloat);
    paramWidgetFrame1.translationZ = interpolate(paramWidgetFrame2.translationZ, paramWidgetFrame3.translationZ, 0.0F, paramFloat);
    paramWidgetFrame1.alpha = interpolate(f2, f1, 1.0F, paramFloat);
    Object localObject1 = paramWidgetFrame3.mCustom.keySet();
    paramWidgetFrame1.mCustom.clear();
    paramTransition = ((Set)localObject1).iterator();
    while (paramTransition.hasNext())
    {
      localObject2 = (String)paramTransition.next();
      if (paramWidgetFrame2.mCustom.containsKey(localObject2))
      {
        CustomVariable localCustomVariable3 = (CustomVariable)paramWidgetFrame2.mCustom.get(localObject2);
        CustomVariable localCustomVariable1 = (CustomVariable)paramWidgetFrame3.mCustom.get(localObject2);
        CustomVariable localCustomVariable2 = new CustomVariable(localCustomVariable3);
        paramWidgetFrame1.mCustom.put(localObject2, localCustomVariable2);
        if (localCustomVariable3.numberOfInterpolatedValues() == 1)
        {
          localCustomVariable2.setValue(Float.valueOf(interpolate(localCustomVariable3.getValueToInterpolate(), localCustomVariable1.getValueToInterpolate(), 0.0F, paramFloat)));
        }
        else
        {
          paramInt1 = localCustomVariable3.numberOfInterpolatedValues();
          float[] arrayOfFloat = new float[paramInt1];
          localObject2 = new float[paramInt1];
          localCustomVariable3.getValuesToInterpolate(arrayOfFloat);
          localCustomVariable1.getValuesToInterpolate((float[])localObject2);
          for (paramInt2 = 0; paramInt2 < paramInt1; paramInt2++)
          {
            arrayOfFloat[paramInt2] = interpolate(arrayOfFloat[paramInt2], localObject2[paramInt2], 0.0F, paramFloat);
            localCustomVariable2.setValue(arrayOfFloat);
          }
        }
      }
    }
  }
  
  private void serializeAnchor(StringBuilder paramStringBuilder, ConstraintAnchor.Type paramType)
  {
    ConstraintAnchor localConstraintAnchor = this.widget.getAnchor(paramType);
    if ((localConstraintAnchor != null) && (localConstraintAnchor.mTarget != null))
    {
      paramStringBuilder.append("Anchor");
      paramStringBuilder.append(paramType.name());
      paramStringBuilder.append(": ['");
      paramType = localConstraintAnchor.mTarget.getOwner().stringId;
      if (paramType == null) {
        paramType = "#PARENT";
      }
      paramStringBuilder.append(paramType);
      paramStringBuilder.append("', '");
      paramStringBuilder.append(localConstraintAnchor.mTarget.getType().name());
      paramStringBuilder.append("', '");
      paramStringBuilder.append(localConstraintAnchor.mMargin);
      paramStringBuilder.append("'],\n");
      return;
    }
  }
  
  public void addCustomColor(String paramString, int paramInt)
  {
    setCustomAttribute(paramString, 902, paramInt);
  }
  
  public void addCustomFloat(String paramString, float paramFloat)
  {
    setCustomAttribute(paramString, 901, paramFloat);
  }
  
  public float centerX()
  {
    int i = this.left;
    return i + (this.right - i) / 2.0F;
  }
  
  public float centerY()
  {
    int i = this.top;
    return i + (this.bottom - i) / 2.0F;
  }
  
  public CustomVariable getCustomAttribute(String paramString)
  {
    return (CustomVariable)this.mCustom.get(paramString);
  }
  
  public Set<String> getCustomAttributeNames()
  {
    return this.mCustom.keySet();
  }
  
  public int getCustomColor(String paramString)
  {
    if (this.mCustom.containsKey(paramString)) {
      return ((CustomVariable)this.mCustom.get(paramString)).getColorValue();
    }
    return 43656;
  }
  
  public float getCustomFloat(String paramString)
  {
    if (this.mCustom.containsKey(paramString)) {
      return ((CustomVariable)this.mCustom.get(paramString)).getFloatValue();
    }
    return NaN.0F;
  }
  
  public String getId()
  {
    ConstraintWidget localConstraintWidget = this.widget;
    if (localConstraintWidget == null) {
      return "unknown";
    }
    return localConstraintWidget.stringId;
  }
  
  public int height()
  {
    return Math.max(0, this.bottom - this.top);
  }
  
  public boolean isDefaultTransform()
  {
    boolean bool;
    if ((Float.isNaN(this.rotationX)) && (Float.isNaN(this.rotationY)) && (Float.isNaN(this.rotationZ)) && (Float.isNaN(this.translationX)) && (Float.isNaN(this.translationY)) && (Float.isNaN(this.translationZ)) && (Float.isNaN(this.scaleX)) && (Float.isNaN(this.scaleY)) && (Float.isNaN(this.alpha))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void logv(String paramString)
  {
    Object localObject = new Throwable().getStackTrace()[1];
    localObject = ".(" + ((StackTraceElement)localObject).getFileName() + ":" + ((StackTraceElement)localObject).getLineNumber() + ") " + ((StackTraceElement)localObject).getMethodName();
    localObject = (String)localObject + " " + hashCode() % 1000;
    if (this.widget != null) {
      localObject = (String)localObject + "/" + this.widget.hashCode() % 1000;
    } else {
      localObject = (String)localObject + "/NULL";
    }
    System.out.println((String)localObject + " " + paramString);
  }
  
  void parseCustom(CLElement paramCLElement)
    throws CLParsingException
  {
    paramCLElement = (CLObject)paramCLElement;
    int j = paramCLElement.size();
    for (int i = 0; i < j; i++)
    {
      CLKey localCLKey = (CLKey)paramCLElement.get(i);
      localCLKey.content();
      CLElement localCLElement = localCLKey.getValue();
      String str = localCLElement.content();
      if (str.matches("#[0-9a-fA-F]+"))
      {
        int k = Integer.parseInt(str.substring(1), 16);
        setCustomAttribute(localCLKey.content(), 902, k);
      }
      else if ((localCLElement instanceof CLNumber))
      {
        setCustomAttribute(localCLKey.content(), 901, localCLElement.getFloat());
      }
      else
      {
        setCustomAttribute(localCLKey.content(), 903, str);
      }
    }
  }
  
  void printCustomAttributes()
  {
    Object localObject1 = new Throwable().getStackTrace()[1];
    localObject1 = ".(" + ((StackTraceElement)localObject1).getFileName() + ":" + ((StackTraceElement)localObject1).getLineNumber() + ") " + ((StackTraceElement)localObject1).getMethodName();
    localObject1 = (String)localObject1 + " " + hashCode() % 1000;
    if (this.widget != null) {
      localObject1 = (String)localObject1 + "/" + this.widget.hashCode() % 1000 + " ";
    } else {
      localObject1 = (String)localObject1 + "/NULL ";
    }
    Object localObject2 = this.mCustom;
    if (localObject2 != null)
    {
      Iterator localIterator = ((HashMap)localObject2).keySet().iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (String)localIterator.next();
        System.out.println((String)localObject1 + ((CustomVariable)this.mCustom.get(localObject2)).toString());
      }
    }
  }
  
  public StringBuilder serialize(StringBuilder paramStringBuilder)
  {
    return serialize(paramStringBuilder, false);
  }
  
  public StringBuilder serialize(StringBuilder paramStringBuilder, boolean paramBoolean)
  {
    paramStringBuilder.append("{\n");
    add(paramStringBuilder, "left", this.left);
    add(paramStringBuilder, "top", this.top);
    add(paramStringBuilder, "right", this.right);
    add(paramStringBuilder, "bottom", this.bottom);
    add(paramStringBuilder, "pivotX", this.pivotX);
    add(paramStringBuilder, "pivotY", this.pivotY);
    add(paramStringBuilder, "rotationX", this.rotationX);
    add(paramStringBuilder, "rotationY", this.rotationY);
    add(paramStringBuilder, "rotationZ", this.rotationZ);
    add(paramStringBuilder, "translationX", this.translationX);
    add(paramStringBuilder, "translationY", this.translationY);
    add(paramStringBuilder, "translationZ", this.translationZ);
    add(paramStringBuilder, "scaleX", this.scaleX);
    add(paramStringBuilder, "scaleY", this.scaleY);
    add(paramStringBuilder, "alpha", this.alpha);
    add(paramStringBuilder, "visibility", this.visibility);
    add(paramStringBuilder, "interpolatedPos", this.interpolatedPos);
    Object localObject;
    if (this.widget != null)
    {
      localObject = ConstraintAnchor.Type.values();
      int j = localObject.length;
      for (int i = 0; i < j; i++) {
        serializeAnchor(paramStringBuilder, localObject[i]);
      }
    }
    if (paramBoolean) {
      add(paramStringBuilder, "phone_orientation", phone_orientation);
    }
    if (paramBoolean) {
      add(paramStringBuilder, "phone_orientation", phone_orientation);
    }
    if (this.mCustom.size() != 0)
    {
      paramStringBuilder.append("custom : {\n");
      localObject = this.mCustom.keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        CustomVariable localCustomVariable = (CustomVariable)this.mCustom.get(str);
        paramStringBuilder.append(str);
        paramStringBuilder.append(": ");
        switch (localCustomVariable.getType())
        {
        default: 
          break;
        case 904: 
          paramStringBuilder.append("'");
          paramStringBuilder.append(localCustomVariable.getBooleanValue());
          paramStringBuilder.append("',\n");
          break;
        case 903: 
          paramStringBuilder.append("'");
          paramStringBuilder.append(localCustomVariable.getStringValue());
          paramStringBuilder.append("',\n");
          break;
        case 902: 
          paramStringBuilder.append("'");
          str = CustomVariable.colorString(localCustomVariable.getIntegerValue());
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          paramStringBuilder.append(str);
          paramStringBuilder.append("',\n");
          break;
        case 901: 
        case 905: 
          paramStringBuilder.append(localCustomVariable.getFloatValue());
          paramStringBuilder.append(",\n");
          break;
        case 900: 
          paramStringBuilder.append(localCustomVariable.getIntegerValue());
          paramStringBuilder.append(",\n");
        }
      }
      paramStringBuilder.append("}\n");
    }
    paramStringBuilder.append("}\n");
    return paramStringBuilder;
  }
  
  public void setCustomAttribute(String paramString, int paramInt, float paramFloat)
  {
    if (this.mCustom.containsKey(paramString)) {
      ((CustomVariable)this.mCustom.get(paramString)).setFloatValue(paramFloat);
    } else {
      this.mCustom.put(paramString, new CustomVariable(paramString, paramInt, paramFloat));
    }
  }
  
  public void setCustomAttribute(String paramString, int paramInt1, int paramInt2)
  {
    if (this.mCustom.containsKey(paramString)) {
      ((CustomVariable)this.mCustom.get(paramString)).setIntValue(paramInt2);
    } else {
      this.mCustom.put(paramString, new CustomVariable(paramString, paramInt1, paramInt2));
    }
  }
  
  public void setCustomAttribute(String paramString1, int paramInt, String paramString2)
  {
    if (this.mCustom.containsKey(paramString1)) {
      ((CustomVariable)this.mCustom.get(paramString1)).setStringValue(paramString2);
    } else {
      this.mCustom.put(paramString1, new CustomVariable(paramString1, paramInt, paramString2));
    }
  }
  
  public void setCustomAttribute(String paramString, int paramInt, boolean paramBoolean)
  {
    if (this.mCustom.containsKey(paramString)) {
      ((CustomVariable)this.mCustom.get(paramString)).setBooleanValue(paramBoolean);
    } else {
      this.mCustom.put(paramString, new CustomVariable(paramString, paramInt, paramBoolean));
    }
  }
  
  public void setCustomValue(CustomAttribute paramCustomAttribute, float[] paramArrayOfFloat) {}
  
  public boolean setValue(String paramString, CLElement paramCLElement)
    throws CLParsingException
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("interpolatedPos"))
      {
        i = 11;
        break label447;
        if (paramString.equals("right"))
        {
          i = 15;
          break label447;
          if (paramString.equals("alpha"))
          {
            i = 10;
            break label447;
            if (paramString.equals("left"))
            {
              i = 14;
              break label447;
              if (paramString.equals("top"))
              {
                i = 13;
                break label447;
                if (paramString.equals("scaleY"))
                {
                  i = 9;
                  break label447;
                  if (paramString.equals("scaleX"))
                  {
                    i = 8;
                    break label447;
                    if (paramString.equals("pivotY"))
                    {
                      i = 1;
                      break label447;
                      if (paramString.equals("pivotX"))
                      {
                        i = 0;
                        break label447;
                        if (paramString.equals("translationZ"))
                        {
                          i = 7;
                          break label447;
                          if (paramString.equals("translationY"))
                          {
                            i = 6;
                            break label447;
                            if (paramString.equals("translationX"))
                            {
                              i = 5;
                              break label447;
                              if (paramString.equals("rotationZ"))
                              {
                                i = 4;
                                break label447;
                                if (paramString.equals("rotationY"))
                                {
                                  i = 3;
                                  break label447;
                                  if (paramString.equals("rotationX"))
                                  {
                                    i = 2;
                                    break label447;
                                    if (paramString.equals("custom"))
                                    {
                                      i = 17;
                                      break label447;
                                      if (paramString.equals("bottom"))
                                      {
                                        i = 16;
                                        break label447;
                                        if (paramString.equals("phone_orientation"))
                                        {
                                          i = 12;
                                          break label447;
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    int i = -1;
    switch (i)
    {
    default: 
      return false;
    case 17: 
      parseCustom(paramCLElement);
      break;
    case 16: 
      this.bottom = paramCLElement.getInt();
      break;
    case 15: 
      this.right = paramCLElement.getInt();
      break;
    case 14: 
      this.left = paramCLElement.getInt();
      break;
    case 13: 
      this.top = paramCLElement.getInt();
      break;
    case 12: 
      phone_orientation = paramCLElement.getFloat();
      break;
    case 11: 
      this.interpolatedPos = paramCLElement.getFloat();
      break;
    case 10: 
      this.alpha = paramCLElement.getFloat();
      break;
    case 9: 
      this.scaleY = paramCLElement.getFloat();
      break;
    case 8: 
      this.scaleX = paramCLElement.getFloat();
      break;
    case 7: 
      this.translationZ = paramCLElement.getFloat();
      break;
    case 6: 
      this.translationY = paramCLElement.getFloat();
      break;
    case 5: 
      this.translationX = paramCLElement.getFloat();
      break;
    case 4: 
      this.rotationZ = paramCLElement.getFloat();
      break;
    case 3: 
      this.rotationY = paramCLElement.getFloat();
      break;
    case 2: 
      this.rotationX = paramCLElement.getFloat();
      break;
    case 1: 
      this.pivotY = paramCLElement.getFloat();
      break;
    case 0: 
      label447:
      this.pivotX = paramCLElement.getFloat();
    }
    return true;
  }
  
  public WidgetFrame update()
  {
    ConstraintWidget localConstraintWidget = this.widget;
    if (localConstraintWidget != null)
    {
      this.left = localConstraintWidget.getLeft();
      this.top = this.widget.getTop();
      this.right = this.widget.getRight();
      this.bottom = this.widget.getBottom();
      updateAttributes(this.widget.frame);
    }
    return this;
  }
  
  public WidgetFrame update(ConstraintWidget paramConstraintWidget)
  {
    if (paramConstraintWidget == null) {
      return this;
    }
    this.widget = paramConstraintWidget;
    update();
    return this;
  }
  
  public void updateAttributes(WidgetFrame paramWidgetFrame)
  {
    this.pivotX = paramWidgetFrame.pivotX;
    this.pivotY = paramWidgetFrame.pivotY;
    this.rotationX = paramWidgetFrame.rotationX;
    this.rotationY = paramWidgetFrame.rotationY;
    this.rotationZ = paramWidgetFrame.rotationZ;
    this.translationX = paramWidgetFrame.translationX;
    this.translationY = paramWidgetFrame.translationY;
    this.translationZ = paramWidgetFrame.translationZ;
    this.scaleX = paramWidgetFrame.scaleX;
    this.scaleY = paramWidgetFrame.scaleY;
    this.alpha = paramWidgetFrame.alpha;
    this.visibility = paramWidgetFrame.visibility;
    this.mCustom.clear();
    if (paramWidgetFrame != null)
    {
      paramWidgetFrame = paramWidgetFrame.mCustom.values().iterator();
      while (paramWidgetFrame.hasNext())
      {
        CustomVariable localCustomVariable = (CustomVariable)paramWidgetFrame.next();
        this.mCustom.put(localCustomVariable.getName(), localCustomVariable.copy());
      }
    }
  }
  
  public int width()
  {
    return Math.max(0, this.right - this.left);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/WidgetFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */