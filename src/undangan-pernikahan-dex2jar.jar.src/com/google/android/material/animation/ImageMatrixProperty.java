package com.google.android.material.animation;

import android.graphics.Matrix;
import android.util.Property;
import android.widget.ImageView;

public class ImageMatrixProperty
  extends Property<ImageView, Matrix>
{
  private final Matrix matrix = new Matrix();
  
  public ImageMatrixProperty()
  {
    super(Matrix.class, "imageMatrixProperty");
  }
  
  public Matrix get(ImageView paramImageView)
  {
    this.matrix.set(paramImageView.getImageMatrix());
    return this.matrix;
  }
  
  public void set(ImageView paramImageView, Matrix paramMatrix)
  {
    paramImageView.setImageMatrix(paramMatrix);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/animation/ImageMatrixProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */