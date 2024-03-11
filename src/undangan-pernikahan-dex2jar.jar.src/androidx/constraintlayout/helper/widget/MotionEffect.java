package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.KeyAttributes;
import androidx.constraintlayout.motion.widget.KeyPosition;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R.styleable;
import java.util.HashMap;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MotionEffect
  extends MotionHelper
{
  public static final int AUTO = -1;
  public static final int EAST = 2;
  public static final int NORTH = 0;
  public static final int SOUTH = 1;
  public static final String TAG = "FadeMove";
  private static final int UNSET = -1;
  public static final int WEST = 3;
  private int fadeMove = -1;
  private float motionEffectAlpha = 0.1F;
  private int motionEffectEnd = 50;
  private int motionEffectStart = 49;
  private boolean motionEffectStrictMove = true;
  private int motionEffectTranslationX = 0;
  private int motionEffectTranslationY = 0;
  private int viewTransitionId = -1;
  
  public MotionEffect(Context paramContext)
  {
    super(paramContext);
  }
  
  public MotionEffect(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public MotionEffect(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MotionEffect);
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.MotionEffect_motionEffect_start)
        {
          k = paramContext.getInt(k, this.motionEffectStart);
          this.motionEffectStart = k;
          this.motionEffectStart = Math.max(Math.min(k, 99), 0);
        }
        else if (k == R.styleable.MotionEffect_motionEffect_end)
        {
          k = paramContext.getInt(k, this.motionEffectEnd);
          this.motionEffectEnd = k;
          this.motionEffectEnd = Math.max(Math.min(k, 99), 0);
        }
        else if (k == R.styleable.MotionEffect_motionEffect_translationX)
        {
          this.motionEffectTranslationX = paramContext.getDimensionPixelOffset(k, this.motionEffectTranslationX);
        }
        else if (k == R.styleable.MotionEffect_motionEffect_translationY)
        {
          this.motionEffectTranslationY = paramContext.getDimensionPixelOffset(k, this.motionEffectTranslationY);
        }
        else if (k == R.styleable.MotionEffect_motionEffect_alpha)
        {
          this.motionEffectAlpha = paramContext.getFloat(k, this.motionEffectAlpha);
        }
        else if (k == R.styleable.MotionEffect_motionEffect_move)
        {
          this.fadeMove = paramContext.getInt(k, this.fadeMove);
        }
        else if (k == R.styleable.MotionEffect_motionEffect_strict)
        {
          this.motionEffectStrictMove = paramContext.getBoolean(k, this.motionEffectStrictMove);
        }
        else if (k == R.styleable.MotionEffect_motionEffect_viewTransition)
        {
          this.viewTransitionId = paramContext.getResourceId(k, this.viewTransitionId);
        }
      }
      i = this.motionEffectStart;
      j = this.motionEffectEnd;
      if (i == j) {
        if (i > 0) {
          this.motionEffectStart = (i - 1);
        } else {
          this.motionEffectEnd = (j + 1);
        }
      }
      paramContext.recycle();
    }
  }
  
  public boolean isDecorator()
  {
    return true;
  }
  
  public void onPreSetup(MotionLayout paramMotionLayout, HashMap<View, MotionController> paramHashMap)
  {
    View[] arrayOfView = getViews((ConstraintLayout)getParent());
    if (arrayOfView == null)
    {
      paramMotionLayout = new StringBuilder();
      paramHashMap = Debug.getLoc();
      Log5ECF72.a(paramHashMap);
      LogE84000.a(paramHashMap);
      Log229316.a(paramHashMap);
      Log.v("FadeMove", paramHashMap + " views = null");
      return;
    }
    KeyAttributes localKeyAttributes6 = new KeyAttributes();
    KeyAttributes localKeyAttributes5 = new KeyAttributes();
    localKeyAttributes6.setValue("alpha", Float.valueOf(this.motionEffectAlpha));
    localKeyAttributes5.setValue("alpha", Float.valueOf(this.motionEffectAlpha));
    localKeyAttributes6.setFramePosition(this.motionEffectStart);
    localKeyAttributes5.setFramePosition(this.motionEffectEnd);
    KeyPosition localKeyPosition1 = new KeyPosition();
    localKeyPosition1.setFramePosition(this.motionEffectStart);
    localKeyPosition1.setType(0);
    localKeyPosition1.setValue("percentX", Integer.valueOf(0));
    localKeyPosition1.setValue("percentY", Integer.valueOf(0));
    KeyPosition localKeyPosition2 = new KeyPosition();
    localKeyPosition2.setFramePosition(this.motionEffectEnd);
    localKeyPosition2.setType(0);
    localKeyPosition2.setValue("percentX", Integer.valueOf(1));
    localKeyPosition2.setValue("percentY", Integer.valueOf(1));
    KeyAttributes localKeyAttributes2 = null;
    KeyAttributes localKeyAttributes1 = null;
    if (this.motionEffectTranslationX > 0)
    {
      localKeyAttributes2 = new KeyAttributes();
      localKeyAttributes1 = new KeyAttributes();
      localKeyAttributes2.setValue("translationX", Integer.valueOf(this.motionEffectTranslationX));
      localKeyAttributes2.setFramePosition(this.motionEffectEnd);
      localKeyAttributes1.setValue("translationX", Integer.valueOf(0));
      localKeyAttributes1.setFramePosition(this.motionEffectEnd - 1);
    }
    KeyAttributes localKeyAttributes4 = null;
    KeyAttributes localKeyAttributes3 = null;
    if (this.motionEffectTranslationY > 0)
    {
      localKeyAttributes4 = new KeyAttributes();
      localKeyAttributes3 = new KeyAttributes();
      localKeyAttributes4.setValue("translationY", Integer.valueOf(this.motionEffectTranslationY));
      localKeyAttributes4.setFramePosition(this.motionEffectEnd);
      localKeyAttributes3.setValue("translationY", Integer.valueOf(0));
      localKeyAttributes3.setFramePosition(this.motionEffectEnd - 1);
    }
    int k = this.fadeMove;
    Object localObject;
    int i;
    float f1;
    float f2;
    int m;
    if (this.fadeMove == -1)
    {
      localObject = new int[4];
      for (i = 0; i < arrayOfView.length; i++)
      {
        MotionController localMotionController = (MotionController)paramHashMap.get(arrayOfView[i]);
        if (localMotionController != null)
        {
          f1 = localMotionController.getFinalX() - localMotionController.getStartX();
          f2 = localMotionController.getFinalY() - localMotionController.getStartY();
          if (f2 < 0.0F) {
            localObject[1] += 1;
          }
          if (f2 > 0.0F) {
            localObject[0] += 1;
          }
          if (f1 > 0.0F) {
            localObject[3] += 1;
          }
          if (f1 < 0.0F) {
            localObject[2] += 1;
          }
        }
      }
      m = localObject[0];
      j = 0;
      i = 1;
      for (;;)
      {
        k = j;
        if (i >= 4) {
          break;
        }
        k = m;
        if (m < localObject[i])
        {
          k = localObject[i];
          j = i;
        }
        i++;
        m = k;
      }
    }
    for (int j = 0; j < arrayOfView.length; j++)
    {
      localObject = (MotionController)paramHashMap.get(arrayOfView[j]);
      if (localObject != null)
      {
        f1 = ((MotionController)localObject).getFinalX() - ((MotionController)localObject).getStartX();
        f2 = ((MotionController)localObject).getFinalY() - ((MotionController)localObject).getStartY();
        m = 1;
        if (k == 0)
        {
          i = m;
          if (f2 > 0.0F) {
            if (this.motionEffectStrictMove)
            {
              i = m;
              if (f1 != 0.0F) {}
            }
            else
            {
              i = 0;
            }
          }
        }
        else if (k == 1)
        {
          i = m;
          if (f2 < 0.0F) {
            if (this.motionEffectStrictMove)
            {
              i = m;
              if (f1 != 0.0F) {}
            }
            else
            {
              i = 0;
            }
          }
        }
        else if (k == 2)
        {
          i = m;
          if (f1 < 0.0F) {
            if (this.motionEffectStrictMove)
            {
              i = m;
              if (f2 != 0.0F) {}
            }
            else
            {
              i = 0;
            }
          }
        }
        else
        {
          i = m;
          if (k == 3)
          {
            i = m;
            if (f1 > 0.0F) {
              if (this.motionEffectStrictMove)
              {
                i = m;
                if (f2 != 0.0F) {}
              }
              else
              {
                i = 0;
              }
            }
          }
        }
        if (i != 0)
        {
          i = this.viewTransitionId;
          if (i == -1)
          {
            ((MotionController)localObject).addKey(localKeyAttributes6);
            ((MotionController)localObject).addKey(localKeyAttributes5);
            ((MotionController)localObject).addKey(localKeyPosition1);
            ((MotionController)localObject).addKey(localKeyPosition2);
            if (this.motionEffectTranslationX > 0)
            {
              ((MotionController)localObject).addKey(localKeyAttributes2);
              ((MotionController)localObject).addKey(localKeyAttributes1);
            }
            if (this.motionEffectTranslationY > 0)
            {
              ((MotionController)localObject).addKey(localKeyAttributes4);
              ((MotionController)localObject).addKey(localKeyAttributes3);
            }
          }
          else
          {
            paramMotionLayout.applyViewTransition(i, (MotionController)localObject);
          }
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/helper/widget/MotionEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */