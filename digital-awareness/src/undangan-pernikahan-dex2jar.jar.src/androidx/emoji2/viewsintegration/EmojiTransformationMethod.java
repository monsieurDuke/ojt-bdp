package androidx.emoji2.viewsintegration;

import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import androidx.emoji2.text.EmojiCompat;

class EmojiTransformationMethod
  implements TransformationMethod
{
  private final TransformationMethod mTransformationMethod;
  
  EmojiTransformationMethod(TransformationMethod paramTransformationMethod)
  {
    this.mTransformationMethod = paramTransformationMethod;
  }
  
  public TransformationMethod getOriginalTransformationMethod()
  {
    return this.mTransformationMethod;
  }
  
  public CharSequence getTransformation(CharSequence paramCharSequence, View paramView)
  {
    if (paramView.isInEditMode()) {
      return paramCharSequence;
    }
    TransformationMethod localTransformationMethod = this.mTransformationMethod;
    CharSequence localCharSequence = paramCharSequence;
    if (localTransformationMethod != null) {
      localCharSequence = localTransformationMethod.getTransformation(paramCharSequence, paramView);
    }
    if (localCharSequence != null) {
      switch (EmojiCompat.get().getLoadState())
      {
      default: 
        break;
      case 1: 
        return EmojiCompat.get().process(localCharSequence);
      }
    }
    return localCharSequence;
  }
  
  public void onFocusChanged(View paramView, CharSequence paramCharSequence, boolean paramBoolean, int paramInt, Rect paramRect)
  {
    TransformationMethod localTransformationMethod = this.mTransformationMethod;
    if (localTransformationMethod != null) {
      localTransformationMethod.onFocusChanged(paramView, paramCharSequence, paramBoolean, paramInt, paramRect);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/viewsintegration/EmojiTransformationMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */