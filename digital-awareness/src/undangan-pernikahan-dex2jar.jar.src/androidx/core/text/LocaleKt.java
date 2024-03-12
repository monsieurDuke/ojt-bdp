package androidx.core.text;

import android.text.TextUtils;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\016\n\000\n\002\020\b\n\002\030\002\n\002\b\003\"\026\020\000\032\0020\001*\0020\0028Ç\002¢\006\006\032\004\b\003\020\004¨\006\005"}, d2={"layoutDirection", "", "Ljava/util/Locale;", "getLayoutDirection", "(Ljava/util/Locale;)I", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class LocaleKt
{
  public static final int getLayoutDirection(Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramLocale, "<this>");
    return TextUtils.getLayoutDirectionFromLocale(paramLocale);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/LocaleKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */