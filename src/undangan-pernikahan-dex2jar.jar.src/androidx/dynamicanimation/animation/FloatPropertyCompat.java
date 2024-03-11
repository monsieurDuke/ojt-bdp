package androidx.dynamicanimation.animation;

import android.util.FloatProperty;

public abstract class FloatPropertyCompat<T>
{
  final String mPropertyName;
  
  public FloatPropertyCompat(String paramString)
  {
    this.mPropertyName = paramString;
  }
  
  public static <T> FloatPropertyCompat<T> createFloatPropertyCompat(final FloatProperty<T> paramFloatProperty)
  {
    new FloatPropertyCompat(paramFloatProperty.getName())
    {
      public float getValue(T paramAnonymousT)
      {
        return ((Float)paramFloatProperty.get(paramAnonymousT)).floatValue();
      }
      
      public void setValue(T paramAnonymousT, float paramAnonymousFloat)
      {
        paramFloatProperty.setValue(paramAnonymousT, paramAnonymousFloat);
      }
    };
  }
  
  public abstract float getValue(T paramT);
  
  public abstract void setValue(T paramT, float paramFloat);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/dynamicanimation/animation/FloatPropertyCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */