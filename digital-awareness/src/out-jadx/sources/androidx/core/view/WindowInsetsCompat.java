package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowInsetsCompat {
    public static final WindowInsetsCompat CONSUMED;
    private static final String TAG = "WindowInsetsCompat";
    private final Impl mImpl;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Api21ReflectionHolder {
        private static Field sContentInsets;
        private static boolean sReflectionSucceeded;
        private static Field sStableInsets;
        private static Field sViewAttachInfoField;

        static {
            try {
                Field declaredField = View.class.getDeclaredField("mAttachInfo");
                sViewAttachInfoField = declaredField;
                declaredField.setAccessible(true);
                Class<?> cls = Class.forName("android.view.View$AttachInfo");
                Field declaredField2 = cls.getDeclaredField("mStableInsets");
                sStableInsets = declaredField2;
                declaredField2.setAccessible(true);
                Field declaredField3 = cls.getDeclaredField("mContentInsets");
                sContentInsets = declaredField3;
                declaredField3.setAccessible(true);
                sReflectionSucceeded = true;
            } catch (ReflectiveOperationException e) {
                Log.w(WindowInsetsCompat.TAG, "Failed to get visible insets from AttachInfo " + e.getMessage(), e);
            }
        }

        private Api21ReflectionHolder() {
        }

        public static WindowInsetsCompat getRootWindowInsets(View v) {
            if (!sReflectionSucceeded || !v.isAttachedToWindow()) {
                return null;
            }
            try {
                Object obj = sViewAttachInfoField.get(v.getRootView());
                if (obj != null) {
                    Rect rect = (Rect) sStableInsets.get(obj);
                    Rect rect2 = (Rect) sContentInsets.get(obj);
                    if (rect != null && rect2 != null) {
                        WindowInsetsCompat build = new Builder().setStableInsets(Insets.of(rect)).setSystemWindowInsets(Insets.of(rect2)).build();
                        build.setRootWindowInsets(build);
                        build.copyRootViewBounds(v.getRootView());
                        return build;
                    }
                }
            } catch (IllegalAccessException e) {
                Log.w(WindowInsetsCompat.TAG, "Failed to get insets from AttachInfo. " + e.getMessage(), e);
            }
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final BuilderImpl mImpl;

        public Builder() {
            if (Build.VERSION.SDK_INT >= 30) {
                this.mImpl = new BuilderImpl30();
                return;
            }
            if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29();
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20();
            } else {
                this.mImpl = new BuilderImpl();
            }
        }

        public Builder(WindowInsetsCompat insets) {
            if (Build.VERSION.SDK_INT >= 30) {
                this.mImpl = new BuilderImpl30(insets);
                return;
            }
            if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29(insets);
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20(insets);
            } else {
                this.mImpl = new BuilderImpl(insets);
            }
        }

        public WindowInsetsCompat build() {
            return this.mImpl.build();
        }

        public Builder setDisplayCutout(DisplayCutoutCompat displayCutout) {
            this.mImpl.setDisplayCutout(displayCutout);
            return this;
        }

        public Builder setInsets(int typeMask, Insets insets) {
            this.mImpl.setInsets(typeMask, insets);
            return this;
        }

        public Builder setInsetsIgnoringVisibility(int typeMask, Insets insets) {
            this.mImpl.setInsetsIgnoringVisibility(typeMask, insets);
            return this;
        }

        @Deprecated
        public Builder setMandatorySystemGestureInsets(Insets insets) {
            this.mImpl.setMandatorySystemGestureInsets(insets);
            return this;
        }

        @Deprecated
        public Builder setStableInsets(Insets insets) {
            this.mImpl.setStableInsets(insets);
            return this;
        }

        @Deprecated
        public Builder setSystemGestureInsets(Insets insets) {
            this.mImpl.setSystemGestureInsets(insets);
            return this;
        }

        @Deprecated
        public Builder setSystemWindowInsets(Insets insets) {
            this.mImpl.setSystemWindowInsets(insets);
            return this;
        }

        @Deprecated
        public Builder setTappableElementInsets(Insets insets) {
            this.mImpl.setTappableElementInsets(insets);
            return this;
        }

        public Builder setVisible(int typeMask, boolean visible) {
            this.mImpl.setVisible(typeMask, visible);
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class BuilderImpl {
        private final WindowInsetsCompat mInsets;
        Insets[] mInsetsTypeMask;

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        BuilderImpl() {
            this(new WindowInsetsCompat((WindowInsetsCompat) null));
        }

        BuilderImpl(WindowInsetsCompat insets) {
            this.mInsets = insets;
        }

        protected final void applyInsetTypes() {
            Insets[] insetsArr = this.mInsetsTypeMask;
            if (insetsArr != null) {
                Insets insets = insetsArr[Type.indexOf(1)];
                Insets insets2 = this.mInsetsTypeMask[Type.indexOf(2)];
                if (insets2 == null) {
                    insets2 = this.mInsets.getInsets(2);
                }
                if (insets == null) {
                    insets = this.mInsets.getInsets(1);
                }
                setSystemWindowInsets(Insets.max(insets, insets2));
                Insets insets3 = this.mInsetsTypeMask[Type.indexOf(16)];
                if (insets3 != null) {
                    setSystemGestureInsets(insets3);
                }
                Insets insets4 = this.mInsetsTypeMask[Type.indexOf(32)];
                if (insets4 != null) {
                    setMandatorySystemGestureInsets(insets4);
                }
                Insets insets5 = this.mInsetsTypeMask[Type.indexOf(64)];
                if (insets5 != null) {
                    setTappableElementInsets(insets5);
                }
            }
        }

        WindowInsetsCompat build() {
            applyInsetTypes();
            return this.mInsets;
        }

        void setDisplayCutout(DisplayCutoutCompat displayCutout) {
        }

        void setInsets(int typeMask, Insets insets) {
            if (this.mInsetsTypeMask == null) {
                this.mInsetsTypeMask = new Insets[9];
            }
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0) {
                    this.mInsetsTypeMask[Type.indexOf(i)] = insets;
                }
            }
        }

        void setInsetsIgnoringVisibility(int typeMask, Insets insets) {
            if (typeMask == 8) {
                throw new IllegalArgumentException("Ignoring visibility inset not available for IME");
            }
        }

        void setMandatorySystemGestureInsets(Insets insets) {
        }

        void setStableInsets(Insets insets) {
        }

        void setSystemGestureInsets(Insets insets) {
        }

        void setSystemWindowInsets(Insets insets) {
        }

        void setTappableElementInsets(Insets insets) {
        }

        void setVisible(int typeMask, boolean visible) {
        }
    }

    /* loaded from: classes.dex */
    private static class BuilderImpl20 extends BuilderImpl {
        private static Constructor<WindowInsets> sConstructor;
        private static Field sConsumedField;
        private WindowInsets mPlatformInsets;
        private Insets mStableInsets;
        private static boolean sConsumedFieldFetched = false;
        private static boolean sConstructorFetched = false;

        BuilderImpl20() {
            this.mPlatformInsets = createWindowInsetsInstance();
        }

        BuilderImpl20(WindowInsetsCompat insets) {
            super(insets);
            this.mPlatformInsets = insets.toWindowInsets();
        }

        private static WindowInsets createWindowInsetsInstance() {
            if (!sConsumedFieldFetched) {
                try {
                    sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
                } catch (ReflectiveOperationException e) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets.CONSUMED field", e);
                }
                sConsumedFieldFetched = true;
            }
            Field field = sConsumedField;
            if (field != null) {
                try {
                    WindowInsets windowInsets = (WindowInsets) field.get(null);
                    if (windowInsets != null) {
                        return new WindowInsets(windowInsets);
                    }
                } catch (ReflectiveOperationException e2) {
                    Log.i(WindowInsetsCompat.TAG, "Could not get value from WindowInsets.CONSUMED field", e2);
                }
            }
            if (!sConstructorFetched) {
                try {
                    sConstructor = WindowInsets.class.getConstructor(Rect.class);
                } catch (ReflectiveOperationException e3) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets(Rect) constructor", e3);
                }
                sConstructorFetched = true;
            }
            Constructor<WindowInsets> constructor = sConstructor;
            if (constructor != null) {
                try {
                    return constructor.newInstance(new Rect());
                } catch (ReflectiveOperationException e4) {
                    Log.i(WindowInsetsCompat.TAG, "Could not invoke WindowInsets(Rect) constructor", e4);
                }
            }
            return null;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        WindowInsetsCompat build() {
            applyInsetTypes();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets);
            windowInsetsCompat.setOverriddenInsets(this.mInsetsTypeMask);
            windowInsetsCompat.setStableInsets(this.mStableInsets);
            return windowInsetsCompat;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setStableInsets(Insets insets) {
            this.mStableInsets = insets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setSystemWindowInsets(Insets insets) {
            WindowInsets windowInsets = this.mPlatformInsets;
            if (windowInsets != null) {
                this.mPlatformInsets = windowInsets.replaceSystemWindowInsets(insets.left, insets.top, insets.right, insets.bottom);
            }
        }
    }

    /* loaded from: classes.dex */
    private static class BuilderImpl29 extends BuilderImpl {
        final WindowInsets.Builder mPlatBuilder;

        BuilderImpl29() {
            this.mPlatBuilder = new WindowInsets.Builder();
        }

        BuilderImpl29(WindowInsetsCompat insets) {
            super(insets);
            WindowInsets windowInsets = insets.toWindowInsets();
            this.mPlatBuilder = windowInsets != null ? new WindowInsets.Builder(windowInsets) : new WindowInsets.Builder();
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        WindowInsetsCompat build() {
            applyInsetTypes();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build());
            windowInsetsCompat.setOverriddenInsets(this.mInsetsTypeMask);
            return windowInsetsCompat;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setDisplayCutout(DisplayCutoutCompat displayCutout) {
            this.mPlatBuilder.setDisplayCutout(displayCutout != null ? displayCutout.unwrap() : null);
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setMandatorySystemGestureInsets(Insets insets) {
            this.mPlatBuilder.setMandatorySystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setStableInsets(Insets insets) {
            this.mPlatBuilder.setStableInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setSystemGestureInsets(Insets insets) {
            this.mPlatBuilder.setSystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setSystemWindowInsets(Insets insets) {
            this.mPlatBuilder.setSystemWindowInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setTappableElementInsets(Insets insets) {
            this.mPlatBuilder.setTappableElementInsets(insets.toPlatformInsets());
        }
    }

    /* loaded from: classes.dex */
    private static class BuilderImpl30 extends BuilderImpl29 {
        BuilderImpl30() {
        }

        BuilderImpl30(WindowInsetsCompat insets) {
            super(insets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setInsets(int typeMask, Insets insets) {
            this.mPlatBuilder.setInsets(TypeImpl30.toPlatformType(typeMask), insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setInsetsIgnoringVisibility(int typeMask, Insets insets) {
            this.mPlatBuilder.setInsetsIgnoringVisibility(TypeImpl30.toPlatformType(typeMask), insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setVisible(int typeMask, boolean visible) {
            this.mPlatBuilder.setVisible(TypeImpl30.toPlatformType(typeMask), visible);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Impl {
        static final WindowInsetsCompat CONSUMED = new Builder().build().consumeDisplayCutout().consumeStableInsets().consumeSystemWindowInsets();
        final WindowInsetsCompat mHost;

        Impl(WindowInsetsCompat host) {
            this.mHost = host;
        }

        WindowInsetsCompat consumeDisplayCutout() {
            return this.mHost;
        }

        WindowInsetsCompat consumeStableInsets() {
            return this.mHost;
        }

        WindowInsetsCompat consumeSystemWindowInsets() {
            return this.mHost;
        }

        void copyRootViewBounds(View rootView) {
        }

        void copyWindowDataInto(WindowInsetsCompat other) {
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Impl)) {
                return false;
            }
            Impl impl = (Impl) o;
            return isRound() == impl.isRound() && isConsumed() == impl.isConsumed() && ObjectsCompat.equals(getSystemWindowInsets(), impl.getSystemWindowInsets()) && ObjectsCompat.equals(getStableInsets(), impl.getStableInsets()) && ObjectsCompat.equals(getDisplayCutout(), impl.getDisplayCutout());
        }

        DisplayCutoutCompat getDisplayCutout() {
            return null;
        }

        Insets getInsets(int typeMask) {
            return Insets.NONE;
        }

        Insets getInsetsIgnoringVisibility(int typeMask) {
            if ((typeMask & 8) == 0) {
                return Insets.NONE;
            }
            throw new IllegalArgumentException("Unable to query the maximum insets for IME");
        }

        Insets getMandatorySystemGestureInsets() {
            return getSystemWindowInsets();
        }

        Insets getStableInsets() {
            return Insets.NONE;
        }

        Insets getSystemGestureInsets() {
            return getSystemWindowInsets();
        }

        Insets getSystemWindowInsets() {
            return Insets.NONE;
        }

        Insets getTappableElementInsets() {
            return getSystemWindowInsets();
        }

        public int hashCode() {
            return ObjectsCompat.hash(Boolean.valueOf(isRound()), Boolean.valueOf(isConsumed()), getSystemWindowInsets(), getStableInsets(), getDisplayCutout());
        }

        WindowInsetsCompat inset(int left, int top, int right, int bottom) {
            return CONSUMED;
        }

        boolean isConsumed() {
            return false;
        }

        boolean isRound() {
            return false;
        }

        boolean isVisible(int typeMask) {
            return true;
        }

        public void setOverriddenInsets(Insets[] insetsTypeMask) {
        }

        void setRootViewData(Insets visibleInsets) {
        }

        void setRootWindowInsets(WindowInsetsCompat rootWindowInsets) {
        }

        public void setStableInsets(Insets stableInsets) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Impl20 extends Impl {
        private static Class<?> sAttachInfoClass;
        private static Field sAttachInfoField;
        private static Method sGetViewRootImplMethod;
        private static Field sVisibleInsetsField;
        private static boolean sVisibleRectReflectionFetched = false;
        private Insets[] mOverriddenInsets;
        final WindowInsets mPlatformInsets;
        Insets mRootViewVisibleInsets;
        private WindowInsetsCompat mRootWindowInsets;
        private Insets mSystemWindowInsets;

        Impl20(WindowInsetsCompat host, WindowInsets insets) {
            super(host);
            this.mSystemWindowInsets = null;
            this.mPlatformInsets = insets;
        }

        Impl20(WindowInsetsCompat host, Impl20 other) {
            this(host, new WindowInsets(other.mPlatformInsets));
        }

        private Insets getInsets(int typeMask, boolean ignoreVisibility) {
            Insets insets = Insets.NONE;
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0) {
                    insets = Insets.max(insets, getInsetsForType(i, ignoreVisibility));
                }
            }
            return insets;
        }

        private Insets getRootStableInsets() {
            WindowInsetsCompat windowInsetsCompat = this.mRootWindowInsets;
            return windowInsetsCompat != null ? windowInsetsCompat.getStableInsets() : Insets.NONE;
        }

        private Insets getVisibleInsets(View rootView) {
            if (Build.VERSION.SDK_INT >= 30) {
                throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
            }
            if (!sVisibleRectReflectionFetched) {
                loadReflectionField();
            }
            Method method = sGetViewRootImplMethod;
            if (method == null || sAttachInfoClass == null || sVisibleInsetsField == null) {
                return null;
            }
            try {
                Object invoke = method.invoke(rootView, new Object[0]);
                if (invoke == null) {
                    Log.w(WindowInsetsCompat.TAG, "Failed to get visible insets. getViewRootImpl() returned null from the provided view. This means that the view is either not attached or the method has been overridden", new NullPointerException());
                    return null;
                }
                Rect rect = (Rect) sVisibleInsetsField.get(sAttachInfoField.get(invoke));
                if (rect != null) {
                    return Insets.of(rect);
                }
                return null;
            } catch (ReflectiveOperationException e) {
                Log.e(WindowInsetsCompat.TAG, "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
                return null;
            }
        }

        private static void loadReflectionField() {
            try {
                sGetViewRootImplMethod = View.class.getDeclaredMethod("getViewRootImpl", new Class[0]);
                Class<?> cls = Class.forName("android.view.View$AttachInfo");
                sAttachInfoClass = cls;
                sVisibleInsetsField = cls.getDeclaredField("mVisibleInsets");
                sAttachInfoField = Class.forName("android.view.ViewRootImpl").getDeclaredField("mAttachInfo");
                sVisibleInsetsField.setAccessible(true);
                sAttachInfoField.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                Log.e(WindowInsetsCompat.TAG, "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
            }
            sVisibleRectReflectionFetched = true;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void copyRootViewBounds(View rootView) {
            Insets visibleInsets = getVisibleInsets(rootView);
            if (visibleInsets == null) {
                visibleInsets = Insets.NONE;
            }
            setRootViewData(visibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void copyWindowDataInto(WindowInsetsCompat other) {
            other.setRootWindowInsets(this.mRootWindowInsets);
            other.setRootViewData(this.mRootViewVisibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object o) {
            if (super.equals(o)) {
                return Objects.equals(this.mRootViewVisibleInsets, ((Impl20) o).mRootViewVisibleInsets);
            }
            return false;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public Insets getInsets(int typeMask) {
            return getInsets(typeMask, false);
        }

        protected Insets getInsetsForType(int type, boolean ignoreVisibility) {
            Insets stableInsets;
            switch (type) {
                case 1:
                    return ignoreVisibility ? Insets.of(0, Math.max(getRootStableInsets().top, getSystemWindowInsets().top), 0, 0) : Insets.of(0, getSystemWindowInsets().top, 0, 0);
                case 2:
                    if (ignoreVisibility) {
                        Insets rootStableInsets = getRootStableInsets();
                        Insets stableInsets2 = getStableInsets();
                        return Insets.of(Math.max(rootStableInsets.left, stableInsets2.left), 0, Math.max(rootStableInsets.right, stableInsets2.right), Math.max(rootStableInsets.bottom, stableInsets2.bottom));
                    }
                    Insets systemWindowInsets = getSystemWindowInsets();
                    WindowInsetsCompat windowInsetsCompat = this.mRootWindowInsets;
                    stableInsets = windowInsetsCompat != null ? windowInsetsCompat.getStableInsets() : null;
                    int i = systemWindowInsets.bottom;
                    if (stableInsets != null) {
                        i = Math.min(i, stableInsets.bottom);
                    }
                    return Insets.of(systemWindowInsets.left, 0, systemWindowInsets.right, i);
                case 8:
                    Insets[] insetsArr = this.mOverriddenInsets;
                    stableInsets = insetsArr != null ? insetsArr[Type.indexOf(8)] : null;
                    if (stableInsets != null) {
                        return stableInsets;
                    }
                    Insets systemWindowInsets2 = getSystemWindowInsets();
                    Insets rootStableInsets2 = getRootStableInsets();
                    if (systemWindowInsets2.bottom > rootStableInsets2.bottom) {
                        return Insets.of(0, 0, 0, systemWindowInsets2.bottom);
                    }
                    Insets insets = this.mRootViewVisibleInsets;
                    return (insets == null || insets.equals(Insets.NONE) || this.mRootViewVisibleInsets.bottom <= rootStableInsets2.bottom) ? Insets.NONE : Insets.of(0, 0, 0, this.mRootViewVisibleInsets.bottom);
                case 16:
                    return getSystemGestureInsets();
                case 32:
                    return getMandatorySystemGestureInsets();
                case 64:
                    return getTappableElementInsets();
                case 128:
                    WindowInsetsCompat windowInsetsCompat2 = this.mRootWindowInsets;
                    DisplayCutoutCompat displayCutout = windowInsetsCompat2 != null ? windowInsetsCompat2.getDisplayCutout() : getDisplayCutout();
                    return displayCutout != null ? Insets.of(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetTop(), displayCutout.getSafeInsetRight(), displayCutout.getSafeInsetBottom()) : Insets.NONE;
                default:
                    return Insets.NONE;
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public Insets getInsetsIgnoringVisibility(int typeMask) {
            return getInsets(typeMask, true);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        final Insets getSystemWindowInsets() {
            if (this.mSystemWindowInsets == null) {
                this.mSystemWindowInsets = Insets.of(this.mPlatformInsets.getSystemWindowInsetLeft(), this.mPlatformInsets.getSystemWindowInsetTop(), this.mPlatformInsets.getSystemWindowInsetRight(), this.mPlatformInsets.getSystemWindowInsetBottom());
            }
            return this.mSystemWindowInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        WindowInsetsCompat inset(int left, int top, int right, int bottom) {
            Builder builder = new Builder(WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets));
            builder.setSystemWindowInsets(WindowInsetsCompat.insetInsets(getSystemWindowInsets(), left, top, right, bottom));
            builder.setStableInsets(WindowInsetsCompat.insetInsets(getStableInsets(), left, top, right, bottom));
            return builder.build();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        boolean isRound() {
            return this.mPlatformInsets.isRound();
        }

        protected boolean isTypeVisible(int type) {
            switch (type) {
                case 1:
                case 2:
                case 8:
                case 128:
                    return true ^ getInsetsForType(type, false).equals(Insets.NONE);
                case 4:
                    return false;
                default:
                    return true;
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        boolean isVisible(int typeMask) {
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0 && !isTypeVisible(i)) {
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setOverriddenInsets(Insets[] insetsTypeMask) {
            this.mOverriddenInsets = insetsTypeMask;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void setRootViewData(Insets visibleInsets) {
            this.mRootViewVisibleInsets = visibleInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void setRootWindowInsets(WindowInsetsCompat rootWindowInsets) {
            this.mRootWindowInsets = rootWindowInsets;
        }
    }

    /* loaded from: classes.dex */
    private static class Impl21 extends Impl20 {
        private Insets mStableInsets;

        Impl21(WindowInsetsCompat host, WindowInsets insets) {
            super(host, insets);
            this.mStableInsets = null;
        }

        Impl21(WindowInsetsCompat host, Impl21 other) {
            super(host, other);
            this.mStableInsets = null;
            this.mStableInsets = other.mStableInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        WindowInsetsCompat consumeStableInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeStableInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        WindowInsetsCompat consumeSystemWindowInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeSystemWindowInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        final Insets getStableInsets() {
            if (this.mStableInsets == null) {
                this.mStableInsets = Insets.of(this.mPlatformInsets.getStableInsetLeft(), this.mPlatformInsets.getStableInsetTop(), this.mPlatformInsets.getStableInsetRight(), this.mPlatformInsets.getStableInsetBottom());
            }
            return this.mStableInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        boolean isConsumed() {
            return this.mPlatformInsets.isConsumed();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setStableInsets(Insets stableInsets) {
            this.mStableInsets = stableInsets;
        }
    }

    /* loaded from: classes.dex */
    private static class Impl28 extends Impl21 {
        Impl28(WindowInsetsCompat host, WindowInsets insets) {
            super(host, insets);
        }

        Impl28(WindowInsetsCompat host, Impl28 other) {
            super(host, other);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        WindowInsetsCompat consumeDisplayCutout() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Impl28)) {
                return false;
            }
            Impl28 impl28 = (Impl28) o;
            return Objects.equals(this.mPlatformInsets, impl28.mPlatformInsets) && Objects.equals(this.mRootViewVisibleInsets, impl28.mRootViewVisibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        DisplayCutoutCompat getDisplayCutout() {
            return DisplayCutoutCompat.wrap(this.mPlatformInsets.getDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public int hashCode() {
            return this.mPlatformInsets.hashCode();
        }
    }

    /* loaded from: classes.dex */
    private static class Impl29 extends Impl28 {
        private Insets mMandatorySystemGestureInsets;
        private Insets mSystemGestureInsets;
        private Insets mTappableElementInsets;

        Impl29(WindowInsetsCompat host, WindowInsets insets) {
            super(host, insets);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }

        Impl29(WindowInsetsCompat host, Impl29 other) {
            super(host, other);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        Insets getMandatorySystemGestureInsets() {
            if (this.mMandatorySystemGestureInsets == null) {
                this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getMandatorySystemGestureInsets());
            }
            return this.mMandatorySystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        Insets getSystemGestureInsets() {
            if (this.mSystemGestureInsets == null) {
                this.mSystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getSystemGestureInsets());
            }
            return this.mSystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        Insets getTappableElementInsets() {
            if (this.mTappableElementInsets == null) {
                this.mTappableElementInsets = Insets.toCompatInsets(this.mPlatformInsets.getTappableElementInsets());
            }
            return this.mTappableElementInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        WindowInsetsCompat inset(int left, int top, int right, int bottom) {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.inset(left, top, right, bottom));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl21, androidx.core.view.WindowInsetsCompat.Impl
        public void setStableInsets(Insets stableInsets) {
        }
    }

    /* loaded from: classes.dex */
    private static class Impl30 extends Impl29 {
        static final WindowInsetsCompat CONSUMED = WindowInsetsCompat.toWindowInsetsCompat(WindowInsets.CONSUMED);

        Impl30(WindowInsetsCompat host, WindowInsets insets) {
            super(host, insets);
        }

        Impl30(WindowInsetsCompat host, Impl30 other) {
            super(host, other);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        final void copyRootViewBounds(View rootView) {
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public Insets getInsets(int typeMask) {
            return Insets.toCompatInsets(this.mPlatformInsets.getInsets(TypeImpl30.toPlatformType(typeMask)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public Insets getInsetsIgnoringVisibility(int typeMask) {
            return Insets.toCompatInsets(this.mPlatformInsets.getInsetsIgnoringVisibility(TypeImpl30.toPlatformType(typeMask)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public boolean isVisible(int typeMask) {
            return this.mPlatformInsets.isVisible(TypeImpl30.toPlatformType(typeMask));
        }
    }

    /* loaded from: classes.dex */
    public static final class Type {
        static final int CAPTION_BAR = 4;
        static final int DISPLAY_CUTOUT = 128;
        static final int FIRST = 1;
        static final int IME = 8;
        static final int LAST = 256;
        static final int MANDATORY_SYSTEM_GESTURES = 32;
        static final int NAVIGATION_BARS = 2;
        static final int SIZE = 9;
        static final int STATUS_BARS = 1;
        static final int SYSTEM_GESTURES = 16;
        static final int TAPPABLE_ELEMENT = 64;
        static final int WINDOW_DECOR = 256;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface InsetsType {
        }

        private Type() {
        }

        static int all() {
            return -1;
        }

        public static int captionBar() {
            return 4;
        }

        public static int displayCutout() {
            return 128;
        }

        public static int ime() {
            return 8;
        }

        static int indexOf(int type) {
            switch (type) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 4:
                    return 2;
                case 8:
                    return 3;
                case 16:
                    return 4;
                case 32:
                    return 5;
                case 64:
                    return 6;
                case 128:
                    return 7;
                case 256:
                    return 8;
                default:
                    throw new IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + type);
            }
        }

        public static int mandatorySystemGestures() {
            return 32;
        }

        public static int navigationBars() {
            return 2;
        }

        public static int statusBars() {
            return 1;
        }

        public static int systemBars() {
            return 7;
        }

        public static int systemGestures() {
            return 16;
        }

        public static int tappableElement() {
            return 64;
        }
    }

    /* loaded from: classes.dex */
    private static final class TypeImpl30 {
        private TypeImpl30() {
        }

        static int toPlatformType(int typeMask) {
            int i = 0;
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((typeMask & i2) != 0) {
                    switch (i2) {
                        case 1:
                            i |= WindowInsets.Type.statusBars();
                            break;
                        case 2:
                            i |= WindowInsets.Type.navigationBars();
                            break;
                        case 4:
                            i |= WindowInsets.Type.captionBar();
                            break;
                        case 8:
                            i |= WindowInsets.Type.ime();
                            break;
                        case 16:
                            i |= WindowInsets.Type.systemGestures();
                            break;
                        case 32:
                            i |= WindowInsets.Type.mandatorySystemGestures();
                            break;
                        case 64:
                            i |= WindowInsets.Type.tappableElement();
                            break;
                        case 128:
                            i |= WindowInsets.Type.displayCutout();
                            break;
                    }
                }
            }
            return i;
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 30) {
            CONSUMED = Impl30.CONSUMED;
        } else {
            CONSUMED = Impl.CONSUMED;
        }
    }

    private WindowInsetsCompat(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(this, insets);
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            this.mImpl = new Impl29(this, insets);
            return;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = new Impl28(this, insets);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new Impl21(this, insets);
        } else if (Build.VERSION.SDK_INT >= 20) {
            this.mImpl = new Impl20(this, insets);
        } else {
            this.mImpl = new Impl(this);
        }
    }

    public WindowInsetsCompat(WindowInsetsCompat src) {
        if (src == null) {
            this.mImpl = new Impl(this);
            return;
        }
        Impl impl = src.mImpl;
        if (Build.VERSION.SDK_INT >= 30 && (impl instanceof Impl30)) {
            this.mImpl = new Impl30(this, (Impl30) impl);
        } else if (Build.VERSION.SDK_INT >= 29 && (impl instanceof Impl29)) {
            this.mImpl = new Impl29(this, (Impl29) impl);
        } else if (Build.VERSION.SDK_INT >= 28 && (impl instanceof Impl28)) {
            this.mImpl = new Impl28(this, (Impl28) impl);
        } else if (Build.VERSION.SDK_INT >= 21 && (impl instanceof Impl21)) {
            this.mImpl = new Impl21(this, (Impl21) impl);
        } else if (Build.VERSION.SDK_INT < 20 || !(impl instanceof Impl20)) {
            this.mImpl = new Impl(this);
        } else {
            this.mImpl = new Impl20(this, (Impl20) impl);
        }
        impl.copyWindowDataInto(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Insets insetInsets(Insets insets, int left, int top, int right, int bottom) {
        int max = Math.max(0, insets.left - left);
        int max2 = Math.max(0, insets.top - top);
        int max3 = Math.max(0, insets.right - right);
        int max4 = Math.max(0, insets.bottom - bottom);
        return (max == left && max2 == top && max3 == right && max4 == bottom) ? insets : Insets.of(max, max2, max3, max4);
    }

    public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets insets) {
        return toWindowInsetsCompat(insets, null);
    }

    public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets insets, View view) {
        WindowInsetsCompat windowInsetsCompat = new WindowInsetsCompat((WindowInsets) Preconditions.checkNotNull(insets));
        if (view != null && ViewCompat.isAttachedToWindow(view)) {
            windowInsetsCompat.setRootWindowInsets(ViewCompat.getRootWindowInsets(view));
            windowInsetsCompat.copyRootViewBounds(view.getRootView());
        }
        return windowInsetsCompat;
    }

    @Deprecated
    public WindowInsetsCompat consumeDisplayCutout() {
        return this.mImpl.consumeDisplayCutout();
    }

    @Deprecated
    public WindowInsetsCompat consumeStableInsets() {
        return this.mImpl.consumeStableInsets();
    }

    @Deprecated
    public WindowInsetsCompat consumeSystemWindowInsets() {
        return this.mImpl.consumeSystemWindowInsets();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void copyRootViewBounds(View rootView) {
        this.mImpl.copyRootViewBounds(rootView);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof WindowInsetsCompat) {
            return ObjectsCompat.equals(this.mImpl, ((WindowInsetsCompat) o).mImpl);
        }
        return false;
    }

    public DisplayCutoutCompat getDisplayCutout() {
        return this.mImpl.getDisplayCutout();
    }

    public Insets getInsets(int typeMask) {
        return this.mImpl.getInsets(typeMask);
    }

    public Insets getInsetsIgnoringVisibility(int typeMask) {
        return this.mImpl.getInsetsIgnoringVisibility(typeMask);
    }

    @Deprecated
    public Insets getMandatorySystemGestureInsets() {
        return this.mImpl.getMandatorySystemGestureInsets();
    }

    @Deprecated
    public int getStableInsetBottom() {
        return this.mImpl.getStableInsets().bottom;
    }

    @Deprecated
    public int getStableInsetLeft() {
        return this.mImpl.getStableInsets().left;
    }

    @Deprecated
    public int getStableInsetRight() {
        return this.mImpl.getStableInsets().right;
    }

    @Deprecated
    public int getStableInsetTop() {
        return this.mImpl.getStableInsets().top;
    }

    @Deprecated
    public Insets getStableInsets() {
        return this.mImpl.getStableInsets();
    }

    @Deprecated
    public Insets getSystemGestureInsets() {
        return this.mImpl.getSystemGestureInsets();
    }

    @Deprecated
    public int getSystemWindowInsetBottom() {
        return this.mImpl.getSystemWindowInsets().bottom;
    }

    @Deprecated
    public int getSystemWindowInsetLeft() {
        return this.mImpl.getSystemWindowInsets().left;
    }

    @Deprecated
    public int getSystemWindowInsetRight() {
        return this.mImpl.getSystemWindowInsets().right;
    }

    @Deprecated
    public int getSystemWindowInsetTop() {
        return this.mImpl.getSystemWindowInsets().top;
    }

    @Deprecated
    public Insets getSystemWindowInsets() {
        return this.mImpl.getSystemWindowInsets();
    }

    @Deprecated
    public Insets getTappableElementInsets() {
        return this.mImpl.getTappableElementInsets();
    }

    public boolean hasInsets() {
        return (getInsets(Type.all()).equals(Insets.NONE) && getInsetsIgnoringVisibility(Type.all() ^ Type.ime()).equals(Insets.NONE) && getDisplayCutout() == null) ? false : true;
    }

    @Deprecated
    public boolean hasStableInsets() {
        return !this.mImpl.getStableInsets().equals(Insets.NONE);
    }

    @Deprecated
    public boolean hasSystemWindowInsets() {
        return !this.mImpl.getSystemWindowInsets().equals(Insets.NONE);
    }

    public int hashCode() {
        Impl impl = this.mImpl;
        if (impl == null) {
            return 0;
        }
        return impl.hashCode();
    }

    public WindowInsetsCompat inset(int left, int top, int right, int bottom) {
        return this.mImpl.inset(left, top, right, bottom);
    }

    public WindowInsetsCompat inset(Insets insets) {
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    public boolean isConsumed() {
        return this.mImpl.isConsumed();
    }

    public boolean isRound() {
        return this.mImpl.isRound();
    }

    public boolean isVisible(int typeMask) {
        return this.mImpl.isVisible(typeMask);
    }

    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(int left, int top, int right, int bottom) {
        return new Builder(this).setSystemWindowInsets(Insets.of(left, top, right, bottom)).build();
    }

    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(Rect systemWindowInsets) {
        return new Builder(this).setSystemWindowInsets(Insets.of(systemWindowInsets)).build();
    }

    void setOverriddenInsets(Insets[] insetsTypeMask) {
        this.mImpl.setOverriddenInsets(insetsTypeMask);
    }

    void setRootViewData(Insets visibleInsets) {
        this.mImpl.setRootViewData(visibleInsets);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRootWindowInsets(WindowInsetsCompat rootWindowInsets) {
        this.mImpl.setRootWindowInsets(rootWindowInsets);
    }

    void setStableInsets(Insets stableInsets) {
        this.mImpl.setStableInsets(stableInsets);
    }

    public WindowInsets toWindowInsets() {
        Impl impl = this.mImpl;
        if (impl instanceof Impl20) {
            return ((Impl20) impl).mPlatformInsets;
        }
        return null;
    }
}