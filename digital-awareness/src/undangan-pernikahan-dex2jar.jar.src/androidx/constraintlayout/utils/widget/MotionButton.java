package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.R.styleable;

public class MotionButton
  extends AppCompatButton
{
  private Path mPath;
  RectF mRect;
  private float mRound = NaN.0F;
  private float mRoundPercent = 0.0F;
  ViewOutlineProvider mViewOutlineProvider;
  
  public MotionButton(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }
  
  public MotionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public MotionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    setPadding(0, 0, 0, 0);
    if (paramAttributeSet != null)
    {
      paramContext = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ImageFilterView);
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.ImageFilterView_round)
        {
          if (Build.VERSION.SDK_INT >= 21) {
            setRound(paramContext.getDimension(k, 0.0F));
          }
        }
        else if ((k == R.styleable.ImageFilterView_roundPercent) && (Build.VERSION.SDK_INT >= 21)) {
          setRoundPercent(paramContext.getFloat(k, 0.0F));
        }
      }
      paramContext.recycle();
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    int j = 0;
    int i = j;
    if (Build.VERSION.SDK_INT < 21)
    {
      i = j;
      if (this.mRound != 0.0F)
      {
        i = j;
        if (this.mPath != null)
        {
          i = 1;
          paramCanvas.save();
          paramCanvas.clipPath(this.mPath);
        }
      }
    }
    super.draw(paramCanvas);
    if (i != 0) {
      paramCanvas.restore();
    }
  }
  
  public float getRound()
  {
    return this.mRound;
  }
  
  public float getRoundPercent()
  {
    return this.mRoundPercent;
  }
  
  public void setRound(float paramFloat)
  {
    if (Float.isNaN(paramFloat))
    {
      this.mRound = paramFloat;
      paramFloat = this.mRoundPercent;
      this.mRoundPercent = -1.0F;
      setRoundPercent(paramFloat);
      return;
    }
    int i;
    if (this.mRound != paramFloat) {
      i = 1;
    } else {
      i = 0;
    }
    this.mRound = paramFloat;
    if (paramFloat != 0.0F)
    {
      if (this.mPath == null) {
        this.mPath = new Path();
      }
      if (this.mRect == null) {
        this.mRect = new RectF();
      }
      if (Build.VERSION.SDK_INT >= 21)
      {
        if (this.mViewOutlineProvider == null)
        {
          localObject = new ViewOutlineProvider()
          {
            public void getOutline(View paramAnonymousView, Outline paramAnonymousOutline)
            {
              paramAnonymousOutline.setRoundRect(0, 0, MotionButton.this.getWidth(), MotionButton.this.getHeight(), MotionButton.this.mRound);
            }
          };
          this.mViewOutlineProvider = ((ViewOutlineProvider)localObject);
          setOutlineProvider((ViewOutlineProvider)localObject);
        }
        setClipToOutline(true);
      }
      int j = getWidth();
      int k = getHeight();
      this.mRect.set(0.0F, 0.0F, j, k);
      this.mPath.reset();
      Path localPath = this.mPath;
      Object localObject = this.mRect;
      paramFloat = this.mRound;
      localPath.addRoundRect((RectF)localObject, paramFloat, paramFloat, Path.Direction.CW);
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      setClipToOutline(false);
    }
    if ((i != 0) && (Build.VERSION.SDK_INT >= 21)) {
      invalidateOutline();
    }
  }
  
  public void setRoundPercent(float paramFloat)
  {
    int i;
    if (this.mRoundPercent != paramFloat) {
      i = 1;
    } else {
      i = 0;
    }
    this.mRoundPercent = paramFloat;
    if (paramFloat != 0.0F)
    {
      if (this.mPath == null) {
        this.mPath = new Path();
      }
      if (this.mRect == null) {
        this.mRect = new RectF();
      }
      if (Build.VERSION.SDK_INT >= 21)
      {
        if (this.mViewOutlineProvider == null)
        {
          ViewOutlineProvider local1 = new ViewOutlineProvider()
          {
            public void getOutline(View paramAnonymousView, Outline paramAnonymousOutline)
            {
              int j = MotionButton.this.getWidth();
              int i = MotionButton.this.getHeight();
              paramAnonymousOutline.setRoundRect(0, 0, j, i, Math.min(j, i) * MotionButton.this.mRoundPercent / 2.0F);
            }
          };
          this.mViewOutlineProvider = local1;
          setOutlineProvider(local1);
        }
        setClipToOutline(true);
      }
      int j = getWidth();
      int k = getHeight();
      paramFloat = Math.min(j, k) * this.mRoundPercent / 2.0F;
      this.mRect.set(0.0F, 0.0F, j, k);
      this.mPath.reset();
      this.mPath.addRoundRect(this.mRect, paramFloat, paramFloat, Path.Direction.CW);
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      setClipToOutline(false);
    }
    if ((i != 0) && (Build.VERSION.SDK_INT >= 21)) {
      invalidateOutline();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/utils/widget/MotionButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */