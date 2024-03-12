package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.Op;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build.VERSION;

public class ShapeAppearancePathProvider
{
  private final Path boundsPath = new Path();
  private final Path cornerPath = new Path();
  private final ShapePath[] cornerPaths = new ShapePath[4];
  private final Matrix[] cornerTransforms = new Matrix[4];
  private boolean edgeIntersectionCheckEnabled = true;
  private final Path edgePath = new Path();
  private final Matrix[] edgeTransforms = new Matrix[4];
  private final Path overlappedEdgePath = new Path();
  private final PointF pointF = new PointF();
  private final float[] scratch = new float[2];
  private final float[] scratch2 = new float[2];
  private final ShapePath shapePath = new ShapePath();
  
  public ShapeAppearancePathProvider()
  {
    for (int i = 0; i < 4; i++)
    {
      this.cornerPaths[i] = new ShapePath();
      this.cornerTransforms[i] = new Matrix();
      this.edgeTransforms[i] = new Matrix();
    }
  }
  
  private float angleOfEdge(int paramInt)
  {
    return (paramInt + 1) * 90;
  }
  
  private void appendCornerPath(ShapeAppearancePathSpec paramShapeAppearancePathSpec, int paramInt)
  {
    this.scratch[0] = this.cornerPaths[paramInt].getStartX();
    this.scratch[1] = this.cornerPaths[paramInt].getStartY();
    this.cornerTransforms[paramInt].mapPoints(this.scratch);
    Object localObject1;
    Object localObject2;
    if (paramInt == 0)
    {
      localObject1 = paramShapeAppearancePathSpec.path;
      localObject2 = this.scratch;
      ((Path)localObject1).moveTo(localObject2[0], localObject2[1]);
    }
    else
    {
      localObject2 = paramShapeAppearancePathSpec.path;
      localObject1 = this.scratch;
      ((Path)localObject2).lineTo(localObject1[0], localObject1[1]);
    }
    this.cornerPaths[paramInt].applyToPath(this.cornerTransforms[paramInt], paramShapeAppearancePathSpec.path);
    if (paramShapeAppearancePathSpec.pathListener != null) {
      paramShapeAppearancePathSpec.pathListener.onCornerPathCreated(this.cornerPaths[paramInt], this.cornerTransforms[paramInt], paramInt);
    }
  }
  
  private void appendEdgePath(ShapeAppearancePathSpec paramShapeAppearancePathSpec, int paramInt)
  {
    int i = (paramInt + 1) % 4;
    this.scratch[0] = this.cornerPaths[paramInt].getEndX();
    this.scratch[1] = this.cornerPaths[paramInt].getEndY();
    this.cornerTransforms[paramInt].mapPoints(this.scratch);
    this.scratch2[0] = this.cornerPaths[i].getStartX();
    this.scratch2[1] = this.cornerPaths[i].getStartY();
    this.cornerTransforms[i].mapPoints(this.scratch2);
    Object localObject1 = this.scratch;
    float f1 = localObject1[0];
    Object localObject2 = this.scratch2;
    f1 = Math.max((float)Math.hypot(f1 - localObject2[0], localObject1[1] - localObject2[1]) - 0.001F, 0.0F);
    float f2 = getEdgeCenterForIndex(paramShapeAppearancePathSpec.bounds, paramInt);
    this.shapePath.reset(0.0F, 0.0F);
    localObject1 = getEdgeTreatmentForIndex(paramInt, paramShapeAppearancePathSpec.shapeAppearanceModel);
    ((EdgeTreatment)localObject1).getEdgePath(f1, f2, paramShapeAppearancePathSpec.interpolation, this.shapePath);
    this.edgePath.reset();
    this.shapePath.applyToPath(this.edgeTransforms[paramInt], this.edgePath);
    if ((this.edgeIntersectionCheckEnabled) && (Build.VERSION.SDK_INT >= 19) && ((((EdgeTreatment)localObject1).forceIntersection()) || (pathOverlapsCorner(this.edgePath, paramInt)) || (pathOverlapsCorner(this.edgePath, i))))
    {
      localObject1 = this.edgePath;
      ((Path)localObject1).op((Path)localObject1, this.boundsPath, Path.Op.DIFFERENCE);
      this.scratch[0] = this.shapePath.getStartX();
      this.scratch[1] = this.shapePath.getStartY();
      this.edgeTransforms[paramInt].mapPoints(this.scratch);
      localObject2 = this.overlappedEdgePath;
      localObject1 = this.scratch;
      ((Path)localObject2).moveTo(localObject1[0], localObject1[1]);
      this.shapePath.applyToPath(this.edgeTransforms[paramInt], this.overlappedEdgePath);
    }
    else
    {
      this.shapePath.applyToPath(this.edgeTransforms[paramInt], paramShapeAppearancePathSpec.path);
    }
    if (paramShapeAppearancePathSpec.pathListener != null) {
      paramShapeAppearancePathSpec.pathListener.onEdgePathCreated(this.shapePath, this.edgeTransforms[paramInt], paramInt);
    }
  }
  
  private void getCoordinatesOfCorner(int paramInt, RectF paramRectF, PointF paramPointF)
  {
    switch (paramInt)
    {
    default: 
      paramPointF.set(paramRectF.right, paramRectF.top);
      break;
    case 3: 
      paramPointF.set(paramRectF.left, paramRectF.top);
      break;
    case 2: 
      paramPointF.set(paramRectF.left, paramRectF.bottom);
      break;
    case 1: 
      paramPointF.set(paramRectF.right, paramRectF.bottom);
    }
  }
  
  private CornerSize getCornerSizeForIndex(int paramInt, ShapeAppearanceModel paramShapeAppearanceModel)
  {
    switch (paramInt)
    {
    default: 
      return paramShapeAppearanceModel.getTopRightCornerSize();
    case 3: 
      return paramShapeAppearanceModel.getTopLeftCornerSize();
    case 2: 
      return paramShapeAppearanceModel.getBottomLeftCornerSize();
    }
    return paramShapeAppearanceModel.getBottomRightCornerSize();
  }
  
  private CornerTreatment getCornerTreatmentForIndex(int paramInt, ShapeAppearanceModel paramShapeAppearanceModel)
  {
    switch (paramInt)
    {
    default: 
      return paramShapeAppearanceModel.getTopRightCorner();
    case 3: 
      return paramShapeAppearanceModel.getTopLeftCorner();
    case 2: 
      return paramShapeAppearanceModel.getBottomLeftCorner();
    }
    return paramShapeAppearanceModel.getBottomRightCorner();
  }
  
  private float getEdgeCenterForIndex(RectF paramRectF, int paramInt)
  {
    this.scratch[0] = this.cornerPaths[paramInt].endX;
    this.scratch[1] = this.cornerPaths[paramInt].endY;
    this.cornerTransforms[paramInt].mapPoints(this.scratch);
    switch (paramInt)
    {
    case 2: 
    default: 
      return Math.abs(paramRectF.centerY() - this.scratch[1]);
    }
    return Math.abs(paramRectF.centerX() - this.scratch[0]);
  }
  
  private EdgeTreatment getEdgeTreatmentForIndex(int paramInt, ShapeAppearanceModel paramShapeAppearanceModel)
  {
    switch (paramInt)
    {
    default: 
      return paramShapeAppearanceModel.getRightEdge();
    case 3: 
      return paramShapeAppearanceModel.getTopEdge();
    case 2: 
      return paramShapeAppearanceModel.getLeftEdge();
    }
    return paramShapeAppearanceModel.getBottomEdge();
  }
  
  public static ShapeAppearancePathProvider getInstance()
  {
    return Lazy.INSTANCE;
  }
  
  private boolean pathOverlapsCorner(Path paramPath, int paramInt)
  {
    this.cornerPath.reset();
    this.cornerPaths[paramInt].applyToPath(this.cornerTransforms[paramInt], this.cornerPath);
    RectF localRectF = new RectF();
    boolean bool2 = true;
    paramPath.computeBounds(localRectF, true);
    this.cornerPath.computeBounds(localRectF, true);
    paramPath.op(this.cornerPath, Path.Op.INTERSECT);
    paramPath.computeBounds(localRectF, true);
    boolean bool1 = bool2;
    if (localRectF.isEmpty()) {
      if ((localRectF.width() > 1.0F) && (localRectF.height() > 1.0F)) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  private void setCornerPathAndTransform(ShapeAppearancePathSpec paramShapeAppearancePathSpec, int paramInt)
  {
    CornerSize localCornerSize = getCornerSizeForIndex(paramInt, paramShapeAppearancePathSpec.shapeAppearanceModel);
    getCornerTreatmentForIndex(paramInt, paramShapeAppearancePathSpec.shapeAppearanceModel).getCornerPath(this.cornerPaths[paramInt], 90.0F, paramShapeAppearancePathSpec.interpolation, paramShapeAppearancePathSpec.bounds, localCornerSize);
    float f = angleOfEdge(paramInt);
    this.cornerTransforms[paramInt].reset();
    getCoordinatesOfCorner(paramInt, paramShapeAppearancePathSpec.bounds, this.pointF);
    this.cornerTransforms[paramInt].setTranslate(this.pointF.x, this.pointF.y);
    this.cornerTransforms[paramInt].preRotate(f);
  }
  
  private void setEdgePathAndTransform(int paramInt)
  {
    this.scratch[0] = this.cornerPaths[paramInt].getEndX();
    this.scratch[1] = this.cornerPaths[paramInt].getEndY();
    this.cornerTransforms[paramInt].mapPoints(this.scratch);
    float f = angleOfEdge(paramInt);
    this.edgeTransforms[paramInt].reset();
    Matrix localMatrix = this.edgeTransforms[paramInt];
    float[] arrayOfFloat = this.scratch;
    localMatrix.setTranslate(arrayOfFloat[0], arrayOfFloat[1]);
    this.edgeTransforms[paramInt].preRotate(f);
  }
  
  public void calculatePath(ShapeAppearanceModel paramShapeAppearanceModel, float paramFloat, RectF paramRectF, Path paramPath)
  {
    calculatePath(paramShapeAppearanceModel, paramFloat, paramRectF, null, paramPath);
  }
  
  public void calculatePath(ShapeAppearanceModel paramShapeAppearanceModel, float paramFloat, RectF paramRectF, PathListener paramPathListener, Path paramPath)
  {
    paramPath.rewind();
    this.overlappedEdgePath.rewind();
    this.boundsPath.rewind();
    this.boundsPath.addRect(paramRectF, Path.Direction.CW);
    paramShapeAppearanceModel = new ShapeAppearancePathSpec(paramShapeAppearanceModel, paramFloat, paramRectF, paramPathListener, paramPath);
    for (int i = 0; i < 4; i++)
    {
      setCornerPathAndTransform(paramShapeAppearanceModel, i);
      setEdgePathAndTransform(i);
    }
    for (i = 0; i < 4; i++)
    {
      appendCornerPath(paramShapeAppearanceModel, i);
      appendEdgePath(paramShapeAppearanceModel, i);
    }
    paramPath.close();
    this.overlappedEdgePath.close();
    if ((Build.VERSION.SDK_INT >= 19) && (!this.overlappedEdgePath.isEmpty())) {
      paramPath.op(this.overlappedEdgePath, Path.Op.UNION);
    }
  }
  
  void setEdgeIntersectionCheckEnable(boolean paramBoolean)
  {
    this.edgeIntersectionCheckEnabled = paramBoolean;
  }
  
  private static class Lazy
  {
    static final ShapeAppearancePathProvider INSTANCE = new ShapeAppearancePathProvider();
  }
  
  public static abstract interface PathListener
  {
    public abstract void onCornerPathCreated(ShapePath paramShapePath, Matrix paramMatrix, int paramInt);
    
    public abstract void onEdgePathCreated(ShapePath paramShapePath, Matrix paramMatrix, int paramInt);
  }
  
  static final class ShapeAppearancePathSpec
  {
    public final RectF bounds;
    public final float interpolation;
    public final Path path;
    public final ShapeAppearancePathProvider.PathListener pathListener;
    public final ShapeAppearanceModel shapeAppearanceModel;
    
    ShapeAppearancePathSpec(ShapeAppearanceModel paramShapeAppearanceModel, float paramFloat, RectF paramRectF, ShapeAppearancePathProvider.PathListener paramPathListener, Path paramPath)
    {
      this.pathListener = paramPathListener;
      this.shapeAppearanceModel = paramShapeAppearanceModel;
      this.interpolation = paramFloat;
      this.bounds = paramRectF;
      this.path = paramPath;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/ShapeAppearancePathProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */