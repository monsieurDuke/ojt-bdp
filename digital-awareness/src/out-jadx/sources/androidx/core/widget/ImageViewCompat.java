package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class ImageViewCompat {

    /* loaded from: classes.dex */
    static class Api21Impl {
        private Api21Impl() {
        }

        static ColorStateList getImageTintList(ImageView imageView) {
            return imageView.getImageTintList();
        }

        static PorterDuff.Mode getImageTintMode(ImageView imageView) {
            return imageView.getImageTintMode();
        }

        static void setImageTintList(ImageView imageView, ColorStateList tint) {
            imageView.setImageTintList(tint);
        }

        static void setImageTintMode(ImageView imageView, PorterDuff.Mode tintMode) {
            imageView.setImageTintMode(tintMode);
        }
    }

    private ImageViewCompat() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ColorStateList getImageTintList(ImageView imageView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getImageTintList(imageView);
        }
        if (imageView instanceof TintableImageSourceView) {
            return ((TintableImageSourceView) imageView).getSupportImageTintList();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PorterDuff.Mode getImageTintMode(ImageView imageView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getImageTintMode(imageView);
        }
        if (imageView instanceof TintableImageSourceView) {
            return ((TintableImageSourceView) imageView).getSupportImageTintMode();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setImageTintList(ImageView imageView, ColorStateList tintList) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT < 21) {
            if (imageView instanceof TintableImageSourceView) {
                ((TintableImageSourceView) imageView).setSupportImageTintList(tintList);
                return;
            }
            return;
        }
        Api21Impl.setImageTintList(imageView, tintList);
        if (Build.VERSION.SDK_INT != 21 || (drawable = imageView.getDrawable()) == null || Api21Impl.getImageTintList(imageView) == null) {
            return;
        }
        if (drawable.isStateful()) {
            drawable.setState(imageView.getDrawableState());
        }
        imageView.setImageDrawable(drawable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setImageTintMode(ImageView imageView, PorterDuff.Mode mode) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT < 21) {
            if (imageView instanceof TintableImageSourceView) {
                ((TintableImageSourceView) imageView).setSupportImageTintMode(mode);
                return;
            }
            return;
        }
        Api21Impl.setImageTintMode(imageView, mode);
        if (Build.VERSION.SDK_INT != 21 || (drawable = imageView.getDrawable()) == null || Api21Impl.getImageTintList(imageView) == null) {
            return;
        }
        if (drawable.isStateful()) {
            drawable.setState(imageView.getDrawableState());
        }
        imageView.setImageDrawable(drawable);
    }
}
