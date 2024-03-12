package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import com.google.android.material.R.styleable;

public class ShapeAppearanceModel
{
  public static final CornerSize PILL = new RelativeCornerSize(0.5F);
  EdgeTreatment bottomEdge;
  CornerTreatment bottomLeftCorner;
  CornerSize bottomLeftCornerSize;
  CornerTreatment bottomRightCorner;
  CornerSize bottomRightCornerSize;
  EdgeTreatment leftEdge;
  EdgeTreatment rightEdge;
  EdgeTreatment topEdge;
  CornerTreatment topLeftCorner;
  CornerSize topLeftCornerSize;
  CornerTreatment topRightCorner;
  CornerSize topRightCornerSize;
  
  public ShapeAppearanceModel()
  {
    this.topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    this.topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    this.bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    this.bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    this.topLeftCornerSize = new AbsoluteCornerSize(0.0F);
    this.topRightCornerSize = new AbsoluteCornerSize(0.0F);
    this.bottomRightCornerSize = new AbsoluteCornerSize(0.0F);
    this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0F);
    this.topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    this.rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    this.bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    this.leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
  }
  
  private ShapeAppearanceModel(Builder paramBuilder)
  {
    this.topLeftCorner = paramBuilder.topLeftCorner;
    this.topRightCorner = paramBuilder.topRightCorner;
    this.bottomRightCorner = paramBuilder.bottomRightCorner;
    this.bottomLeftCorner = paramBuilder.bottomLeftCorner;
    this.topLeftCornerSize = paramBuilder.topLeftCornerSize;
    this.topRightCornerSize = paramBuilder.topRightCornerSize;
    this.bottomRightCornerSize = paramBuilder.bottomRightCornerSize;
    this.bottomLeftCornerSize = paramBuilder.bottomLeftCornerSize;
    this.topEdge = paramBuilder.topEdge;
    this.rightEdge = paramBuilder.rightEdge;
    this.bottomEdge = paramBuilder.bottomEdge;
    this.leftEdge = paramBuilder.leftEdge;
  }
  
  public static Builder builder()
  {
    return new Builder();
  }
  
  public static Builder builder(Context paramContext, int paramInt1, int paramInt2)
  {
    return builder(paramContext, paramInt1, paramInt2, 0);
  }
  
  private static Builder builder(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    return builder(paramContext, paramInt1, paramInt2, new AbsoluteCornerSize(paramInt3));
  }
  
  private static Builder builder(Context paramContext, int paramInt1, int paramInt2, CornerSize paramCornerSize)
  {
    Object localObject = paramContext;
    int i = paramInt1;
    if (paramInt2 != 0)
    {
      localObject = new ContextThemeWrapper(paramContext, paramInt1);
      i = paramInt2;
    }
    paramContext = ((Context)localObject).obtainStyledAttributes(i, R.styleable.ShapeAppearance);
    try
    {
      int j = paramContext.getInt(R.styleable.ShapeAppearance_cornerFamily, 0);
      paramInt1 = paramContext.getInt(R.styleable.ShapeAppearance_cornerFamilyTopLeft, j);
      i = paramContext.getInt(R.styleable.ShapeAppearance_cornerFamilyTopRight, j);
      paramInt2 = paramContext.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomRight, j);
      j = paramContext.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomLeft, j);
      CornerSize localCornerSize2 = getCornerSize(paramContext, R.styleable.ShapeAppearance_cornerSize, paramCornerSize);
      paramCornerSize = getCornerSize(paramContext, R.styleable.ShapeAppearance_cornerSizeTopLeft, localCornerSize2);
      CornerSize localCornerSize1 = getCornerSize(paramContext, R.styleable.ShapeAppearance_cornerSizeTopRight, localCornerSize2);
      localObject = getCornerSize(paramContext, R.styleable.ShapeAppearance_cornerSizeBottomRight, localCornerSize2);
      localCornerSize2 = getCornerSize(paramContext, R.styleable.ShapeAppearance_cornerSizeBottomLeft, localCornerSize2);
      Builder localBuilder = new com/google/android/material/shape/ShapeAppearanceModel$Builder;
      localBuilder.<init>();
      paramCornerSize = localBuilder.setTopLeftCorner(paramInt1, paramCornerSize).setTopRightCorner(i, localCornerSize1).setBottomRightCorner(paramInt2, (CornerSize)localObject).setBottomLeftCorner(j, localCornerSize2);
      return paramCornerSize;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static Builder builder(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    return builder(paramContext, paramAttributeSet, paramInt1, paramInt2, 0);
  }
  
  public static Builder builder(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, int paramInt3)
  {
    return builder(paramContext, paramAttributeSet, paramInt1, paramInt2, new AbsoluteCornerSize(paramInt3));
  }
  
  public static Builder builder(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, CornerSize paramCornerSize)
  {
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MaterialShape, paramInt1, paramInt2);
    paramInt2 = paramAttributeSet.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0);
    paramInt1 = paramAttributeSet.getResourceId(R.styleable.MaterialShape_shapeAppearanceOverlay, 0);
    paramAttributeSet.recycle();
    return builder(paramContext, paramInt2, paramInt1, paramCornerSize);
  }
  
  private static CornerSize getCornerSize(TypedArray paramTypedArray, int paramInt, CornerSize paramCornerSize)
  {
    TypedValue localTypedValue = paramTypedArray.peekValue(paramInt);
    if (localTypedValue == null) {
      return paramCornerSize;
    }
    if (localTypedValue.type == 5) {
      return new AbsoluteCornerSize(TypedValue.complexToDimensionPixelSize(localTypedValue.data, paramTypedArray.getResources().getDisplayMetrics()));
    }
    if (localTypedValue.type == 6) {
      return new RelativeCornerSize(localTypedValue.getFraction(1.0F, 1.0F));
    }
    return paramCornerSize;
  }
  
  public EdgeTreatment getBottomEdge()
  {
    return this.bottomEdge;
  }
  
  public CornerTreatment getBottomLeftCorner()
  {
    return this.bottomLeftCorner;
  }
  
  public CornerSize getBottomLeftCornerSize()
  {
    return this.bottomLeftCornerSize;
  }
  
  public CornerTreatment getBottomRightCorner()
  {
    return this.bottomRightCorner;
  }
  
  public CornerSize getBottomRightCornerSize()
  {
    return this.bottomRightCornerSize;
  }
  
  public EdgeTreatment getLeftEdge()
  {
    return this.leftEdge;
  }
  
  public EdgeTreatment getRightEdge()
  {
    return this.rightEdge;
  }
  
  public EdgeTreatment getTopEdge()
  {
    return this.topEdge;
  }
  
  public CornerTreatment getTopLeftCorner()
  {
    return this.topLeftCorner;
  }
  
  public CornerSize getTopLeftCornerSize()
  {
    return this.topLeftCornerSize;
  }
  
  public CornerTreatment getTopRightCorner()
  {
    return this.topRightCorner;
  }
  
  public CornerSize getTopRightCornerSize()
  {
    return this.topRightCornerSize;
  }
  
  public boolean isRoundRect(RectF paramRectF)
  {
    boolean bool2 = this.leftEdge.getClass().equals(EdgeTreatment.class);
    boolean bool1 = true;
    int i;
    if ((bool2) && (this.rightEdge.getClass().equals(EdgeTreatment.class)) && (this.topEdge.getClass().equals(EdgeTreatment.class)) && (this.bottomEdge.getClass().equals(EdgeTreatment.class))) {
      i = 1;
    } else {
      i = 0;
    }
    float f = this.topLeftCornerSize.getCornerSize(paramRectF);
    int j;
    if ((this.topRightCornerSize.getCornerSize(paramRectF) == f) && (this.bottomLeftCornerSize.getCornerSize(paramRectF) == f) && (this.bottomRightCornerSize.getCornerSize(paramRectF) == f)) {
      j = 1;
    } else {
      j = 0;
    }
    int k;
    if (((this.topRightCorner instanceof RoundedCornerTreatment)) && ((this.topLeftCorner instanceof RoundedCornerTreatment)) && ((this.bottomRightCorner instanceof RoundedCornerTreatment)) && ((this.bottomLeftCorner instanceof RoundedCornerTreatment))) {
      k = 1;
    } else {
      k = 0;
    }
    if ((i == 0) || (j == 0) || (k == 0)) {
      bool1 = false;
    }
    return bool1;
  }
  
  public Builder toBuilder()
  {
    return new Builder(this);
  }
  
  public ShapeAppearanceModel withCornerSize(float paramFloat)
  {
    return toBuilder().setAllCornerSizes(paramFloat).build();
  }
  
  public ShapeAppearanceModel withCornerSize(CornerSize paramCornerSize)
  {
    return toBuilder().setAllCornerSizes(paramCornerSize).build();
  }
  
  public ShapeAppearanceModel withTransformedCornerSizes(CornerSizeUnaryOperator paramCornerSizeUnaryOperator)
  {
    return toBuilder().setTopLeftCornerSize(paramCornerSizeUnaryOperator.apply(getTopLeftCornerSize())).setTopRightCornerSize(paramCornerSizeUnaryOperator.apply(getTopRightCornerSize())).setBottomLeftCornerSize(paramCornerSizeUnaryOperator.apply(getBottomLeftCornerSize())).setBottomRightCornerSize(paramCornerSizeUnaryOperator.apply(getBottomRightCornerSize())).build();
  }
  
  public static final class Builder
  {
    private EdgeTreatment bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    private CornerTreatment bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    private CornerSize bottomLeftCornerSize = new AbsoluteCornerSize(0.0F);
    private CornerTreatment bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    private CornerSize bottomRightCornerSize = new AbsoluteCornerSize(0.0F);
    private EdgeTreatment leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    private EdgeTreatment rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    private EdgeTreatment topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    private CornerTreatment topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    private CornerSize topLeftCornerSize = new AbsoluteCornerSize(0.0F);
    private CornerTreatment topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
    private CornerSize topRightCornerSize = new AbsoluteCornerSize(0.0F);
    
    public Builder() {}
    
    public Builder(ShapeAppearanceModel paramShapeAppearanceModel)
    {
      this.topLeftCorner = paramShapeAppearanceModel.topLeftCorner;
      this.topRightCorner = paramShapeAppearanceModel.topRightCorner;
      this.bottomRightCorner = paramShapeAppearanceModel.bottomRightCorner;
      this.bottomLeftCorner = paramShapeAppearanceModel.bottomLeftCorner;
      this.topLeftCornerSize = paramShapeAppearanceModel.topLeftCornerSize;
      this.topRightCornerSize = paramShapeAppearanceModel.topRightCornerSize;
      this.bottomRightCornerSize = paramShapeAppearanceModel.bottomRightCornerSize;
      this.bottomLeftCornerSize = paramShapeAppearanceModel.bottomLeftCornerSize;
      this.topEdge = paramShapeAppearanceModel.topEdge;
      this.rightEdge = paramShapeAppearanceModel.rightEdge;
      this.bottomEdge = paramShapeAppearanceModel.bottomEdge;
      this.leftEdge = paramShapeAppearanceModel.leftEdge;
    }
    
    private static float compatCornerTreatmentSize(CornerTreatment paramCornerTreatment)
    {
      if ((paramCornerTreatment instanceof RoundedCornerTreatment)) {
        return ((RoundedCornerTreatment)paramCornerTreatment).radius;
      }
      if ((paramCornerTreatment instanceof CutCornerTreatment)) {
        return ((CutCornerTreatment)paramCornerTreatment).size;
      }
      return -1.0F;
    }
    
    public ShapeAppearanceModel build()
    {
      return new ShapeAppearanceModel(this, null);
    }
    
    public Builder setAllCornerSizes(float paramFloat)
    {
      return setTopLeftCornerSize(paramFloat).setTopRightCornerSize(paramFloat).setBottomRightCornerSize(paramFloat).setBottomLeftCornerSize(paramFloat);
    }
    
    public Builder setAllCornerSizes(CornerSize paramCornerSize)
    {
      return setTopLeftCornerSize(paramCornerSize).setTopRightCornerSize(paramCornerSize).setBottomRightCornerSize(paramCornerSize).setBottomLeftCornerSize(paramCornerSize);
    }
    
    public Builder setAllCorners(int paramInt, float paramFloat)
    {
      return setAllCorners(MaterialShapeUtils.createCornerTreatment(paramInt)).setAllCornerSizes(paramFloat);
    }
    
    public Builder setAllCorners(CornerTreatment paramCornerTreatment)
    {
      return setTopLeftCorner(paramCornerTreatment).setTopRightCorner(paramCornerTreatment).setBottomRightCorner(paramCornerTreatment).setBottomLeftCorner(paramCornerTreatment);
    }
    
    public Builder setAllEdges(EdgeTreatment paramEdgeTreatment)
    {
      return setLeftEdge(paramEdgeTreatment).setTopEdge(paramEdgeTreatment).setRightEdge(paramEdgeTreatment).setBottomEdge(paramEdgeTreatment);
    }
    
    public Builder setBottomEdge(EdgeTreatment paramEdgeTreatment)
    {
      this.bottomEdge = paramEdgeTreatment;
      return this;
    }
    
    public Builder setBottomLeftCorner(int paramInt, float paramFloat)
    {
      return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setBottomLeftCornerSize(paramFloat);
    }
    
    public Builder setBottomLeftCorner(int paramInt, CornerSize paramCornerSize)
    {
      return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setBottomLeftCornerSize(paramCornerSize);
    }
    
    public Builder setBottomLeftCorner(CornerTreatment paramCornerTreatment)
    {
      this.bottomLeftCorner = paramCornerTreatment;
      float f = compatCornerTreatmentSize(paramCornerTreatment);
      if (f != -1.0F) {
        setBottomLeftCornerSize(f);
      }
      return this;
    }
    
    public Builder setBottomLeftCornerSize(float paramFloat)
    {
      this.bottomLeftCornerSize = new AbsoluteCornerSize(paramFloat);
      return this;
    }
    
    public Builder setBottomLeftCornerSize(CornerSize paramCornerSize)
    {
      this.bottomLeftCornerSize = paramCornerSize;
      return this;
    }
    
    public Builder setBottomRightCorner(int paramInt, float paramFloat)
    {
      return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setBottomRightCornerSize(paramFloat);
    }
    
    public Builder setBottomRightCorner(int paramInt, CornerSize paramCornerSize)
    {
      return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setBottomRightCornerSize(paramCornerSize);
    }
    
    public Builder setBottomRightCorner(CornerTreatment paramCornerTreatment)
    {
      this.bottomRightCorner = paramCornerTreatment;
      float f = compatCornerTreatmentSize(paramCornerTreatment);
      if (f != -1.0F) {
        setBottomRightCornerSize(f);
      }
      return this;
    }
    
    public Builder setBottomRightCornerSize(float paramFloat)
    {
      this.bottomRightCornerSize = new AbsoluteCornerSize(paramFloat);
      return this;
    }
    
    public Builder setBottomRightCornerSize(CornerSize paramCornerSize)
    {
      this.bottomRightCornerSize = paramCornerSize;
      return this;
    }
    
    public Builder setLeftEdge(EdgeTreatment paramEdgeTreatment)
    {
      this.leftEdge = paramEdgeTreatment;
      return this;
    }
    
    public Builder setRightEdge(EdgeTreatment paramEdgeTreatment)
    {
      this.rightEdge = paramEdgeTreatment;
      return this;
    }
    
    public Builder setTopEdge(EdgeTreatment paramEdgeTreatment)
    {
      this.topEdge = paramEdgeTreatment;
      return this;
    }
    
    public Builder setTopLeftCorner(int paramInt, float paramFloat)
    {
      return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setTopLeftCornerSize(paramFloat);
    }
    
    public Builder setTopLeftCorner(int paramInt, CornerSize paramCornerSize)
    {
      return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setTopLeftCornerSize(paramCornerSize);
    }
    
    public Builder setTopLeftCorner(CornerTreatment paramCornerTreatment)
    {
      this.topLeftCorner = paramCornerTreatment;
      float f = compatCornerTreatmentSize(paramCornerTreatment);
      if (f != -1.0F) {
        setTopLeftCornerSize(f);
      }
      return this;
    }
    
    public Builder setTopLeftCornerSize(float paramFloat)
    {
      this.topLeftCornerSize = new AbsoluteCornerSize(paramFloat);
      return this;
    }
    
    public Builder setTopLeftCornerSize(CornerSize paramCornerSize)
    {
      this.topLeftCornerSize = paramCornerSize;
      return this;
    }
    
    public Builder setTopRightCorner(int paramInt, float paramFloat)
    {
      return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setTopRightCornerSize(paramFloat);
    }
    
    public Builder setTopRightCorner(int paramInt, CornerSize paramCornerSize)
    {
      return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(paramInt)).setTopRightCornerSize(paramCornerSize);
    }
    
    public Builder setTopRightCorner(CornerTreatment paramCornerTreatment)
    {
      this.topRightCorner = paramCornerTreatment;
      float f = compatCornerTreatmentSize(paramCornerTreatment);
      if (f != -1.0F) {
        setTopRightCornerSize(f);
      }
      return this;
    }
    
    public Builder setTopRightCornerSize(float paramFloat)
    {
      this.topRightCornerSize = new AbsoluteCornerSize(paramFloat);
      return this;
    }
    
    public Builder setTopRightCornerSize(CornerSize paramCornerSize)
    {
      this.topRightCornerSize = paramCornerSize;
      return this;
    }
  }
  
  public static abstract interface CornerSizeUnaryOperator
  {
    public abstract CornerSize apply(CornerSize paramCornerSize);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/ShapeAppearanceModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */