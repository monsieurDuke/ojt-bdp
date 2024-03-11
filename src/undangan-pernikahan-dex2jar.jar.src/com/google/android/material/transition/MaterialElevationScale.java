package com.google.android.material.transition;

public final class MaterialElevationScale
  extends MaterialVisibility<ScaleProvider>
{
  private static final float DEFAULT_SCALE = 0.85F;
  private final boolean growing;
  
  public MaterialElevationScale(boolean paramBoolean)
  {
    super(createPrimaryAnimatorProvider(paramBoolean), createSecondaryAnimatorProvider());
    this.growing = paramBoolean;
  }
  
  private static ScaleProvider createPrimaryAnimatorProvider(boolean paramBoolean)
  {
    ScaleProvider localScaleProvider = new ScaleProvider(paramBoolean);
    localScaleProvider.setOutgoingEndScale(0.85F);
    localScaleProvider.setIncomingStartScale(0.85F);
    return localScaleProvider;
  }
  
  private static VisibilityAnimatorProvider createSecondaryAnimatorProvider()
  {
    return new FadeProvider();
  }
  
  public boolean isGrowing()
  {
    return this.growing;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/MaterialElevationScale.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */