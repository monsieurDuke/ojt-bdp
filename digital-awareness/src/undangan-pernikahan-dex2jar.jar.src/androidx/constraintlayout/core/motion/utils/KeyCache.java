package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;
import java.util.HashMap;

public class KeyCache
{
  HashMap<Object, HashMap<String, float[]>> map = new HashMap();
  
  public float getFloatValue(Object paramObject, String paramString, int paramInt)
  {
    if (!this.map.containsKey(paramObject)) {
      return NaN.0F;
    }
    paramObject = (HashMap)this.map.get(paramObject);
    if ((paramObject != null) && (((HashMap)paramObject).containsKey(paramString)))
    {
      paramObject = (float[])((HashMap)paramObject).get(paramString);
      if (paramObject == null) {
        return NaN.0F;
      }
      if (paramObject.length > paramInt) {
        return paramObject[paramInt];
      }
      return NaN.0F;
    }
    return NaN.0F;
  }
  
  public void setFloatValue(Object paramObject, String paramString, int paramInt, float paramFloat)
  {
    Object localObject1;
    Object localObject2;
    if (!this.map.containsKey(paramObject))
    {
      localObject1 = new HashMap();
      localObject2 = new float[paramInt + 1];
      localObject2[paramInt] = paramFloat;
      ((HashMap)localObject1).put(paramString, localObject2);
      this.map.put(paramObject, localObject1);
    }
    else
    {
      localObject2 = (HashMap)this.map.get(paramObject);
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new HashMap();
      }
      if (!((HashMap)localObject1).containsKey(paramString))
      {
        localObject2 = new float[paramInt + 1];
        localObject2[paramInt] = paramFloat;
        ((HashMap)localObject1).put(paramString, localObject2);
        this.map.put(paramObject, localObject1);
      }
      else
      {
        localObject2 = (float[])((HashMap)localObject1).get(paramString);
        paramObject = localObject2;
        if (localObject2 == null) {
          paramObject = new float[0];
        }
        localObject2 = paramObject;
        if (paramObject.length <= paramInt) {
          localObject2 = Arrays.copyOf((float[])paramObject, paramInt + 1);
        }
        localObject2[paramInt] = paramFloat;
        ((HashMap)localObject1).put(paramString, localObject2);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/KeyCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */