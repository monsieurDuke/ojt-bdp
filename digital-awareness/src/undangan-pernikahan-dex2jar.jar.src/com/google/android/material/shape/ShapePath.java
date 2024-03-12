package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.google.android.material.shadow.ShadowRenderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapePath
{
  protected static final float ANGLE_LEFT = 180.0F;
  private static final float ANGLE_UP = 270.0F;
  private boolean containsIncompatibleShadowOp;
  @Deprecated
  public float currentShadowAngle;
  @Deprecated
  public float endShadowAngle;
  @Deprecated
  public float endX;
  @Deprecated
  public float endY;
  private final List<PathOperation> operations = new ArrayList();
  private final List<ShadowCompatOperation> shadowCompatOperations = new ArrayList();
  @Deprecated
  public float startX;
  @Deprecated
  public float startY;
  
  public ShapePath()
  {
    reset(0.0F, 0.0F);
  }
  
  public ShapePath(float paramFloat1, float paramFloat2)
  {
    reset(paramFloat1, paramFloat2);
  }
  
  private void addConnectingShadowIfNecessary(float paramFloat)
  {
    if (getCurrentShadowAngle() == paramFloat) {
      return;
    }
    float f = (paramFloat - getCurrentShadowAngle() + 360.0F) % 360.0F;
    if (f > 180.0F) {
      return;
    }
    PathArcOperation localPathArcOperation = new PathArcOperation(getEndX(), getEndY(), getEndX(), getEndY());
    localPathArcOperation.setStartAngle(getCurrentShadowAngle());
    localPathArcOperation.setSweepAngle(f);
    this.shadowCompatOperations.add(new ArcShadowOperation(localPathArcOperation));
    setCurrentShadowAngle(paramFloat);
  }
  
  private void addShadowCompatOperation(ShadowCompatOperation paramShadowCompatOperation, float paramFloat1, float paramFloat2)
  {
    addConnectingShadowIfNecessary(paramFloat1);
    this.shadowCompatOperations.add(paramShadowCompatOperation);
    setCurrentShadowAngle(paramFloat2);
  }
  
  private float getCurrentShadowAngle()
  {
    return this.currentShadowAngle;
  }
  
  private float getEndShadowAngle()
  {
    return this.endShadowAngle;
  }
  
  private void setCurrentShadowAngle(float paramFloat)
  {
    this.currentShadowAngle = paramFloat;
  }
  
  private void setEndShadowAngle(float paramFloat)
  {
    this.endShadowAngle = paramFloat;
  }
  
  private void setEndX(float paramFloat)
  {
    this.endX = paramFloat;
  }
  
  private void setEndY(float paramFloat)
  {
    this.endY = paramFloat;
  }
  
  private void setStartX(float paramFloat)
  {
    this.startX = paramFloat;
  }
  
  private void setStartY(float paramFloat)
  {
    this.startY = paramFloat;
  }
  
  public void addArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    Object localObject = new PathArcOperation(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    ((PathArcOperation)localObject).setStartAngle(paramFloat5);
    ((PathArcOperation)localObject).setSweepAngle(paramFloat6);
    this.operations.add(localObject);
    localObject = new ArcShadowOperation((PathArcOperation)localObject);
    float f2 = paramFloat5 + paramFloat6;
    int i;
    if (paramFloat6 < 0.0F) {
      i = 1;
    } else {
      i = 0;
    }
    float f1;
    if (i != 0) {
      f1 = (paramFloat5 + 180.0F) % 360.0F;
    } else {
      f1 = paramFloat5;
    }
    if (i != 0) {
      f2 = (180.0F + f2) % 360.0F;
    }
    addShadowCompatOperation((ShadowCompatOperation)localObject, f1, f2);
    setEndX((paramFloat1 + paramFloat3) * 0.5F + (paramFloat3 - paramFloat1) / 2.0F * (float)Math.cos(Math.toRadians(paramFloat5 + paramFloat6)));
    setEndY((paramFloat2 + paramFloat4) * 0.5F + (paramFloat4 - paramFloat2) / 2.0F * (float)Math.sin(Math.toRadians(paramFloat5 + paramFloat6)));
  }
  
  public void applyToPath(Matrix paramMatrix, Path paramPath)
  {
    int i = 0;
    int j = this.operations.size();
    while (i < j)
    {
      ((PathOperation)this.operations.get(i)).applyToPath(paramMatrix, paramPath);
      i++;
    }
  }
  
  boolean containsIncompatibleShadowOp()
  {
    return this.containsIncompatibleShadowOp;
  }
  
  ShadowCompatOperation createShadowCompatOperation(final Matrix paramMatrix)
  {
    addConnectingShadowIfNecessary(getEndShadowAngle());
    paramMatrix = new Matrix(paramMatrix);
    new ShadowCompatOperation()
    {
      public void draw(Matrix paramAnonymousMatrix, ShadowRenderer paramAnonymousShadowRenderer, int paramAnonymousInt, Canvas paramAnonymousCanvas)
      {
        paramAnonymousMatrix = this.val$operations.iterator();
        while (paramAnonymousMatrix.hasNext()) {
          ((ShapePath.ShadowCompatOperation)paramAnonymousMatrix.next()).draw(paramMatrix, paramAnonymousShadowRenderer, paramAnonymousInt, paramAnonymousCanvas);
        }
      }
    };
  }
  
  public void cubicToPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    PathCubicOperation localPathCubicOperation = new PathCubicOperation(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    this.operations.add(localPathCubicOperation);
    this.containsIncompatibleShadowOp = true;
    setEndX(paramFloat5);
    setEndY(paramFloat6);
  }
  
  float getEndX()
  {
    return this.endX;
  }
  
  float getEndY()
  {
    return this.endY;
  }
  
  float getStartX()
  {
    return this.startX;
  }
  
  float getStartY()
  {
    return this.startY;
  }
  
  public void lineTo(float paramFloat1, float paramFloat2)
  {
    Object localObject = new PathLineOperation();
    PathLineOperation.access$002((PathLineOperation)localObject, paramFloat1);
    PathLineOperation.access$102((PathLineOperation)localObject, paramFloat2);
    this.operations.add(localObject);
    localObject = new LineShadowOperation((PathLineOperation)localObject, getEndX(), getEndY());
    addShadowCompatOperation((ShadowCompatOperation)localObject, ((LineShadowOperation)localObject).getAngle() + 270.0F, ((LineShadowOperation)localObject).getAngle() + 270.0F);
    setEndX(paramFloat1);
    setEndY(paramFloat2);
  }
  
  public void quadToPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    PathQuadOperation localPathQuadOperation = new PathQuadOperation();
    localPathQuadOperation.setControlX(paramFloat1);
    localPathQuadOperation.setControlY(paramFloat2);
    localPathQuadOperation.setEndX(paramFloat3);
    localPathQuadOperation.setEndY(paramFloat4);
    this.operations.add(localPathQuadOperation);
    this.containsIncompatibleShadowOp = true;
    setEndX(paramFloat3);
    setEndY(paramFloat4);
  }
  
  public void reset(float paramFloat1, float paramFloat2)
  {
    reset(paramFloat1, paramFloat2, 270.0F, 0.0F);
  }
  
  public void reset(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    setStartX(paramFloat1);
    setStartY(paramFloat2);
    setEndX(paramFloat1);
    setEndY(paramFloat2);
    setCurrentShadowAngle(paramFloat3);
    setEndShadowAngle((paramFloat3 + paramFloat4) % 360.0F);
    this.operations.clear();
    this.shadowCompatOperations.clear();
    this.containsIncompatibleShadowOp = false;
  }
  
  static class ArcShadowOperation
    extends ShapePath.ShadowCompatOperation
  {
    private final ShapePath.PathArcOperation operation;
    
    public ArcShadowOperation(ShapePath.PathArcOperation paramPathArcOperation)
    {
      this.operation = paramPathArcOperation;
    }
    
    public void draw(Matrix paramMatrix, ShadowRenderer paramShadowRenderer, int paramInt, Canvas paramCanvas)
    {
      float f1 = ShapePath.PathArcOperation.access$800(this.operation);
      float f2 = ShapePath.PathArcOperation.access$900(this.operation);
      paramShadowRenderer.drawCornerShadow(paramCanvas, paramMatrix, new RectF(ShapePath.PathArcOperation.access$1000(this.operation), ShapePath.PathArcOperation.access$1100(this.operation), ShapePath.PathArcOperation.access$1200(this.operation), ShapePath.PathArcOperation.access$1300(this.operation)), paramInt, f1, f2);
    }
  }
  
  static class LineShadowOperation
    extends ShapePath.ShadowCompatOperation
  {
    private final ShapePath.PathLineOperation operation;
    private final float startX;
    private final float startY;
    
    public LineShadowOperation(ShapePath.PathLineOperation paramPathLineOperation, float paramFloat1, float paramFloat2)
    {
      this.operation = paramPathLineOperation;
      this.startX = paramFloat1;
      this.startY = paramFloat2;
    }
    
    public void draw(Matrix paramMatrix, ShadowRenderer paramShadowRenderer, int paramInt, Canvas paramCanvas)
    {
      float f3 = ShapePath.PathLineOperation.access$100(this.operation);
      float f1 = this.startY;
      float f2 = ShapePath.PathLineOperation.access$000(this.operation);
      float f4 = this.startX;
      RectF localRectF = new RectF(0.0F, 0.0F, (float)Math.hypot(f3 - f1, f2 - f4), 0.0F);
      paramMatrix = new Matrix(paramMatrix);
      paramMatrix.preTranslate(this.startX, this.startY);
      paramMatrix.preRotate(getAngle());
      paramShadowRenderer.drawEdgeShadow(paramCanvas, paramMatrix, localRectF, paramInt);
    }
    
    float getAngle()
    {
      return (float)Math.toDegrees(Math.atan((ShapePath.PathLineOperation.access$100(this.operation) - this.startY) / (ShapePath.PathLineOperation.access$000(this.operation) - this.startX)));
    }
  }
  
  public static class PathArcOperation
    extends ShapePath.PathOperation
  {
    private static final RectF rectF = new RectF();
    @Deprecated
    public float bottom;
    @Deprecated
    public float left;
    @Deprecated
    public float right;
    @Deprecated
    public float startAngle;
    @Deprecated
    public float sweepAngle;
    @Deprecated
    public float top;
    
    public PathArcOperation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      setLeft(paramFloat1);
      setTop(paramFloat2);
      setRight(paramFloat3);
      setBottom(paramFloat4);
    }
    
    private float getBottom()
    {
      return this.bottom;
    }
    
    private float getLeft()
    {
      return this.left;
    }
    
    private float getRight()
    {
      return this.right;
    }
    
    private float getStartAngle()
    {
      return this.startAngle;
    }
    
    private float getSweepAngle()
    {
      return this.sweepAngle;
    }
    
    private float getTop()
    {
      return this.top;
    }
    
    private void setBottom(float paramFloat)
    {
      this.bottom = paramFloat;
    }
    
    private void setLeft(float paramFloat)
    {
      this.left = paramFloat;
    }
    
    private void setRight(float paramFloat)
    {
      this.right = paramFloat;
    }
    
    private void setStartAngle(float paramFloat)
    {
      this.startAngle = paramFloat;
    }
    
    private void setSweepAngle(float paramFloat)
    {
      this.sweepAngle = paramFloat;
    }
    
    private void setTop(float paramFloat)
    {
      this.top = paramFloat;
    }
    
    public void applyToPath(Matrix paramMatrix, Path paramPath)
    {
      Object localObject = this.matrix;
      paramMatrix.invert((Matrix)localObject);
      paramPath.transform((Matrix)localObject);
      localObject = rectF;
      ((RectF)localObject).set(getLeft(), getTop(), getRight(), getBottom());
      paramPath.arcTo((RectF)localObject, getStartAngle(), getSweepAngle(), false);
      paramPath.transform(paramMatrix);
    }
  }
  
  public static class PathCubicOperation
    extends ShapePath.PathOperation
  {
    private float controlX1;
    private float controlX2;
    private float controlY1;
    private float controlY2;
    private float endX;
    private float endY;
    
    public PathCubicOperation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
      setControlX1(paramFloat1);
      setControlY1(paramFloat2);
      setControlX2(paramFloat3);
      setControlY2(paramFloat4);
      setEndX(paramFloat5);
      setEndY(paramFloat6);
    }
    
    private float getControlX1()
    {
      return this.controlX1;
    }
    
    private float getControlX2()
    {
      return this.controlX2;
    }
    
    private float getControlY1()
    {
      return this.controlY1;
    }
    
    private float getControlY2()
    {
      return this.controlY1;
    }
    
    private float getEndX()
    {
      return this.endX;
    }
    
    private float getEndY()
    {
      return this.endY;
    }
    
    private void setControlX1(float paramFloat)
    {
      this.controlX1 = paramFloat;
    }
    
    private void setControlX2(float paramFloat)
    {
      this.controlX2 = paramFloat;
    }
    
    private void setControlY1(float paramFloat)
    {
      this.controlY1 = paramFloat;
    }
    
    private void setControlY2(float paramFloat)
    {
      this.controlY2 = paramFloat;
    }
    
    private void setEndX(float paramFloat)
    {
      this.endX = paramFloat;
    }
    
    private void setEndY(float paramFloat)
    {
      this.endY = paramFloat;
    }
    
    public void applyToPath(Matrix paramMatrix, Path paramPath)
    {
      Matrix localMatrix = this.matrix;
      paramMatrix.invert(localMatrix);
      paramPath.transform(localMatrix);
      paramPath.cubicTo(this.controlX1, this.controlY1, this.controlX2, this.controlY2, this.endX, this.endY);
      paramPath.transform(paramMatrix);
    }
  }
  
  public static class PathLineOperation
    extends ShapePath.PathOperation
  {
    private float x;
    private float y;
    
    public void applyToPath(Matrix paramMatrix, Path paramPath)
    {
      Matrix localMatrix = this.matrix;
      paramMatrix.invert(localMatrix);
      paramPath.transform(localMatrix);
      paramPath.lineTo(this.x, this.y);
      paramPath.transform(paramMatrix);
    }
  }
  
  public static abstract class PathOperation
  {
    protected final Matrix matrix = new Matrix();
    
    public abstract void applyToPath(Matrix paramMatrix, Path paramPath);
  }
  
  public static class PathQuadOperation
    extends ShapePath.PathOperation
  {
    @Deprecated
    public float controlX;
    @Deprecated
    public float controlY;
    @Deprecated
    public float endX;
    @Deprecated
    public float endY;
    
    private float getControlX()
    {
      return this.controlX;
    }
    
    private float getControlY()
    {
      return this.controlY;
    }
    
    private float getEndX()
    {
      return this.endX;
    }
    
    private float getEndY()
    {
      return this.endY;
    }
    
    private void setControlX(float paramFloat)
    {
      this.controlX = paramFloat;
    }
    
    private void setControlY(float paramFloat)
    {
      this.controlY = paramFloat;
    }
    
    private void setEndX(float paramFloat)
    {
      this.endX = paramFloat;
    }
    
    private void setEndY(float paramFloat)
    {
      this.endY = paramFloat;
    }
    
    public void applyToPath(Matrix paramMatrix, Path paramPath)
    {
      Matrix localMatrix = this.matrix;
      paramMatrix.invert(localMatrix);
      paramPath.transform(localMatrix);
      paramPath.quadTo(getControlX(), getControlY(), getEndX(), getEndY());
      paramPath.transform(paramMatrix);
    }
  }
  
  static abstract class ShadowCompatOperation
  {
    static final Matrix IDENTITY_MATRIX = new Matrix();
    
    public abstract void draw(Matrix paramMatrix, ShadowRenderer paramShadowRenderer, int paramInt, Canvas paramCanvas);
    
    public final void draw(ShadowRenderer paramShadowRenderer, int paramInt, Canvas paramCanvas)
    {
      draw(IDENTITY_MATRIX, paramShadowRenderer, paramInt, paramCanvas);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/ShapePath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */