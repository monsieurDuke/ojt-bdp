package androidx.core.graphics;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000<\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\020\007\n\002\b\004\n\002\020\013\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\002\032\025\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f\032\025\020\000\032\0020\003*\0020\0032\006\020\002\032\0020\003H\f\032\r\020\004\032\0020\005*\0020\001H\n\032\r\020\004\032\0020\006*\0020\003H\n\032\r\020\007\032\0020\005*\0020\001H\n\032\r\020\007\032\0020\006*\0020\003H\n\032\r\020\b\032\0020\005*\0020\001H\n\032\r\020\b\032\0020\006*\0020\003H\n\032\r\020\t\032\0020\005*\0020\001H\n\032\r\020\t\032\0020\006*\0020\003H\n\032\025\020\n\032\0020\013*\0020\0012\006\020\f\032\0020\rH\n\032\025\020\n\032\0020\013*\0020\0032\006\020\f\032\0020\016H\n\032\025\020\017\032\0020\001*\0020\0012\006\020\020\032\0020\rH\n\032\025\020\017\032\0020\021*\0020\0012\006\020\002\032\0020\001H\n\032\025\020\017\032\0020\001*\0020\0012\006\020\020\032\0020\005H\n\032\025\020\017\032\0020\003*\0020\0032\006\020\020\032\0020\016H\n\032\025\020\017\032\0020\021*\0020\0032\006\020\002\032\0020\003H\n\032\025\020\017\032\0020\003*\0020\0032\006\020\020\032\0020\006H\n\032\025\020\022\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f\032\025\020\022\032\0020\003*\0020\0032\006\020\002\032\0020\003H\f\032\025\020\023\032\0020\001*\0020\0012\006\020\020\032\0020\rH\n\032\025\020\023\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\025\020\023\032\0020\001*\0020\0012\006\020\020\032\0020\005H\n\032\025\020\023\032\0020\003*\0020\0032\006\020\020\032\0020\016H\n\032\025\020\023\032\0020\003*\0020\0032\006\020\002\032\0020\003H\n\032\025\020\023\032\0020\003*\0020\0032\006\020\020\032\0020\006H\n\032\025\020\024\032\0020\001*\0020\0012\006\020\025\032\0020\005H\n\032\025\020\024\032\0020\003*\0020\0032\006\020\025\032\0020\006H\n\032\025\020\024\032\0020\003*\0020\0032\006\020\025\032\0020\005H\n\032\r\020\026\032\0020\001*\0020\003H\b\032\r\020\027\032\0020\003*\0020\001H\b\032\r\020\030\032\0020\021*\0020\001H\b\032\r\020\030\032\0020\021*\0020\003H\b\032\025\020\031\032\0020\003*\0020\0032\006\020\032\032\0020\033H\b\032\025\020\034\032\0020\021*\0020\0012\006\020\002\032\0020\001H\f\032\025\020\034\032\0020\021*\0020\0032\006\020\002\032\0020\003H\f¨\006\035"}, d2={"and", "Landroid/graphics/Rect;", "r", "Landroid/graphics/RectF;", "component1", "", "", "component2", "component3", "component4", "contains", "", "p", "Landroid/graphics/Point;", "Landroid/graphics/PointF;", "minus", "xy", "Landroid/graphics/Region;", "or", "plus", "times", "factor", "toRect", "toRectF", "toRegion", "transform", "m", "Landroid/graphics/Matrix;", "xor", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class RectKt
{
  public static final Rect and(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkNotNullParameter(paramRect1, "<this>");
    Intrinsics.checkNotNullParameter(paramRect2, "r");
    paramRect1 = new Rect(paramRect1);
    paramRect1.intersect(paramRect2);
    return paramRect1;
  }
  
  public static final RectF and(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkNotNullParameter(paramRectF1, "<this>");
    Intrinsics.checkNotNullParameter(paramRectF2, "r");
    paramRectF1 = new RectF(paramRectF1);
    paramRectF1.intersect(paramRectF2);
    return paramRectF1;
  }
  
  public static final float component1(RectF paramRectF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    return paramRectF.left;
  }
  
  public static final int component1(Rect paramRect)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    return paramRect.left;
  }
  
  public static final float component2(RectF paramRectF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    return paramRectF.top;
  }
  
  public static final int component2(Rect paramRect)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    return paramRect.top;
  }
  
  public static final float component3(RectF paramRectF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    return paramRectF.right;
  }
  
  public static final int component3(Rect paramRect)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    return paramRect.right;
  }
  
  public static final float component4(RectF paramRectF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    return paramRectF.bottom;
  }
  
  public static final int component4(Rect paramRect)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    return paramRect.bottom;
  }
  
  public static final boolean contains(Rect paramRect, Point paramPoint)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    Intrinsics.checkNotNullParameter(paramPoint, "p");
    return paramRect.contains(paramPoint.x, paramPoint.y);
  }
  
  public static final boolean contains(RectF paramRectF, PointF paramPointF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    Intrinsics.checkNotNullParameter(paramPointF, "p");
    return paramRectF.contains(paramPointF.x, paramPointF.y);
  }
  
  public static final Rect minus(Rect paramRect, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    paramRect = new Rect(paramRect);
    paramRect.offset(-paramInt, -paramInt);
    return paramRect;
  }
  
  public static final Rect minus(Rect paramRect, Point paramPoint)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    Intrinsics.checkNotNullParameter(paramPoint, "xy");
    paramRect = new Rect(paramRect);
    paramRect.offset(-paramPoint.x, -paramPoint.y);
    return paramRect;
  }
  
  public static final RectF minus(RectF paramRectF, float paramFloat)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    paramRectF = new RectF(paramRectF);
    paramRectF.offset(-paramFloat, -paramFloat);
    return paramRectF;
  }
  
  public static final RectF minus(RectF paramRectF, PointF paramPointF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    Intrinsics.checkNotNullParameter(paramPointF, "xy");
    paramRectF = new RectF(paramRectF);
    paramRectF.offset(-paramPointF.x, -paramPointF.y);
    return paramRectF;
  }
  
  public static final Region minus(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkNotNullParameter(paramRect1, "<this>");
    Intrinsics.checkNotNullParameter(paramRect2, "r");
    paramRect1 = new Region(paramRect1);
    paramRect1.op(paramRect2, Region.Op.DIFFERENCE);
    return paramRect1;
  }
  
  public static final Region minus(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkNotNullParameter(paramRectF1, "<this>");
    Intrinsics.checkNotNullParameter(paramRectF2, "r");
    Rect localRect = new Rect();
    paramRectF1.roundOut(localRect);
    paramRectF1 = new Region(localRect);
    localRect = new Rect();
    paramRectF2.roundOut(localRect);
    paramRectF1.op(localRect, Region.Op.DIFFERENCE);
    return paramRectF1;
  }
  
  public static final Rect or(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkNotNullParameter(paramRect1, "<this>");
    Intrinsics.checkNotNullParameter(paramRect2, "r");
    paramRect1 = new Rect(paramRect1);
    paramRect1.union(paramRect2);
    return paramRect1;
  }
  
  public static final RectF or(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkNotNullParameter(paramRectF1, "<this>");
    Intrinsics.checkNotNullParameter(paramRectF2, "r");
    paramRectF1 = new RectF(paramRectF1);
    paramRectF1.union(paramRectF2);
    return paramRectF1;
  }
  
  public static final Rect plus(Rect paramRect, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    paramRect = new Rect(paramRect);
    paramRect.offset(paramInt, paramInt);
    return paramRect;
  }
  
  public static final Rect plus(Rect paramRect, Point paramPoint)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    Intrinsics.checkNotNullParameter(paramPoint, "xy");
    paramRect = new Rect(paramRect);
    paramRect.offset(paramPoint.x, paramPoint.y);
    return paramRect;
  }
  
  public static final Rect plus(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkNotNullParameter(paramRect1, "<this>");
    Intrinsics.checkNotNullParameter(paramRect2, "r");
    paramRect1 = new Rect(paramRect1);
    paramRect1.union(paramRect2);
    return paramRect1;
  }
  
  public static final RectF plus(RectF paramRectF, float paramFloat)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    paramRectF = new RectF(paramRectF);
    paramRectF.offset(paramFloat, paramFloat);
    return paramRectF;
  }
  
  public static final RectF plus(RectF paramRectF, PointF paramPointF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    Intrinsics.checkNotNullParameter(paramPointF, "xy");
    paramRectF = new RectF(paramRectF);
    paramRectF.offset(paramPointF.x, paramPointF.y);
    return paramRectF;
  }
  
  public static final RectF plus(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkNotNullParameter(paramRectF1, "<this>");
    Intrinsics.checkNotNullParameter(paramRectF2, "r");
    paramRectF1 = new RectF(paramRectF1);
    paramRectF1.union(paramRectF2);
    return paramRectF1;
  }
  
  public static final Rect times(Rect paramRect, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    paramRect = new Rect(paramRect);
    paramRect.top *= paramInt;
    paramRect.left *= paramInt;
    paramRect.right *= paramInt;
    paramRect.bottom *= paramInt;
    return paramRect;
  }
  
  public static final RectF times(RectF paramRectF, float paramFloat)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    paramRectF = new RectF(paramRectF);
    paramRectF.top *= paramFloat;
    paramRectF.left *= paramFloat;
    paramRectF.right *= paramFloat;
    paramRectF.bottom *= paramFloat;
    return paramRectF;
  }
  
  public static final RectF times(RectF paramRectF, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    float f = paramInt;
    paramRectF = new RectF(paramRectF);
    paramRectF.top *= f;
    paramRectF.left *= f;
    paramRectF.right *= f;
    paramRectF.bottom *= f;
    return paramRectF;
  }
  
  public static final Rect toRect(RectF paramRectF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    Rect localRect = new Rect();
    paramRectF.roundOut(localRect);
    return localRect;
  }
  
  public static final RectF toRectF(Rect paramRect)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    return new RectF(paramRect);
  }
  
  public static final Region toRegion(Rect paramRect)
  {
    Intrinsics.checkNotNullParameter(paramRect, "<this>");
    return new Region(paramRect);
  }
  
  public static final Region toRegion(RectF paramRectF)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    Rect localRect = new Rect();
    paramRectF.roundOut(localRect);
    return new Region(localRect);
  }
  
  public static final RectF transform(RectF paramRectF, Matrix paramMatrix)
  {
    Intrinsics.checkNotNullParameter(paramRectF, "<this>");
    Intrinsics.checkNotNullParameter(paramMatrix, "m");
    paramMatrix.mapRect(paramRectF);
    return paramRectF;
  }
  
  public static final Region xor(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkNotNullParameter(paramRect1, "<this>");
    Intrinsics.checkNotNullParameter(paramRect2, "r");
    paramRect1 = new Region(paramRect1);
    paramRect1.op(paramRect2, Region.Op.XOR);
    return paramRect1;
  }
  
  public static final Region xor(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkNotNullParameter(paramRectF1, "<this>");
    Intrinsics.checkNotNullParameter(paramRectF2, "r");
    Rect localRect = new Rect();
    paramRectF1.roundOut(localRect);
    paramRectF1 = new Region(localRect);
    localRect = new Rect();
    paramRectF2.roundOut(localRect);
    paramRectF1.op(localRect, Region.Op.XOR);
    return paramRectF1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/RectKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */