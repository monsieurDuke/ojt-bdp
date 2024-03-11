package com.google.android.material.elevation;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.color.MaterialColors;

public enum SurfaceColors
{
  private static final SurfaceColors[] $VALUES;
  private final int elevationResId;
  
  static
  {
    SurfaceColors localSurfaceColors2 = new SurfaceColors("SURFACE_0", 0, R.dimen.m3_sys_elevation_level0);
    SURFACE_0 = localSurfaceColors2;
    SurfaceColors localSurfaceColors3 = new SurfaceColors("SURFACE_1", 1, R.dimen.m3_sys_elevation_level1);
    SURFACE_1 = localSurfaceColors3;
    SurfaceColors localSurfaceColors4 = new SurfaceColors("SURFACE_2", 2, R.dimen.m3_sys_elevation_level2);
    SURFACE_2 = localSurfaceColors4;
    SurfaceColors localSurfaceColors1 = new SurfaceColors("SURFACE_3", 3, R.dimen.m3_sys_elevation_level3);
    SURFACE_3 = localSurfaceColors1;
    SurfaceColors localSurfaceColors5 = new SurfaceColors("SURFACE_4", 4, R.dimen.m3_sys_elevation_level4);
    SURFACE_4 = localSurfaceColors5;
    SurfaceColors localSurfaceColors6 = new SurfaceColors("SURFACE_5", 5, R.dimen.m3_sys_elevation_level5);
    SURFACE_5 = localSurfaceColors6;
    $VALUES = new SurfaceColors[] { localSurfaceColors2, localSurfaceColors3, localSurfaceColors4, localSurfaceColors1, localSurfaceColors5, localSurfaceColors6 };
  }
  
  private SurfaceColors(int paramInt)
  {
    this.elevationResId = paramInt;
  }
  
  public static int getColorForElevation(Context paramContext, float paramFloat)
  {
    return new ElevationOverlayProvider(paramContext).compositeOverlay(MaterialColors.getColor(paramContext, R.attr.colorSurface, 0), paramFloat);
  }
  
  public int getColor(Context paramContext)
  {
    return getColorForElevation(paramContext, paramContext.getResources().getDimension(this.elevationResId));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/elevation/SurfaceColors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */