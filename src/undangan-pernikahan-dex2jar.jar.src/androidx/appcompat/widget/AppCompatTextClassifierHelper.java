package androidx.appcompat.widget;

import android.content.Context;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.core.util.Preconditions;

final class AppCompatTextClassifierHelper
{
  private TextClassifier mTextClassifier;
  private TextView mTextView;
  
  AppCompatTextClassifierHelper(TextView paramTextView)
  {
    this.mTextView = ((TextView)Preconditions.checkNotNull(paramTextView));
  }
  
  public TextClassifier getTextClassifier()
  {
    TextClassifier localTextClassifier = this.mTextClassifier;
    if (localTextClassifier == null) {
      return Api26Impl.getTextClassifier(this.mTextView);
    }
    return localTextClassifier;
  }
  
  public void setTextClassifier(TextClassifier paramTextClassifier)
  {
    this.mTextClassifier = paramTextClassifier;
  }
  
  private static final class Api26Impl
  {
    static TextClassifier getTextClassifier(TextView paramTextView)
    {
      paramTextView = (TextClassificationManager)paramTextView.getContext().getSystemService(TextClassificationManager.class);
      if (paramTextView != null) {
        return paramTextView.getTextClassifier();
      }
      return TextClassifier.NO_OP;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatTextClassifierHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */