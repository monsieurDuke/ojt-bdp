package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class TextInputEditText extends AppCompatEditText {
    private final Rect parentRect;
    private boolean textInputLayoutFocusedRectEnabled;

    public TextInputEditText(Context context) {
        this(context, null);
    }

    public TextInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public TextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, 0), attrs, defStyleAttr);
        this.parentRect = new Rect();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.TextInputEditText, defStyleAttr, R.style.Widget_Design_TextInputEditText, new int[0]);
        setTextInputLayoutFocusedRectEnabled(obtainStyledAttributes.getBoolean(R.styleable.TextInputEditText_textInputLayoutFocusedRectEnabled, false));
        obtainStyledAttributes.recycle();
    }

    private String getAccessibilityNodeInfoText(TextInputLayout layout) {
        Editable text = getText();
        CharSequence hint = layout.getHint();
        boolean z = !TextUtils.isEmpty(text);
        boolean z2 = !TextUtils.isEmpty(hint);
        String str = HttpUrl.FRAGMENT_ENCODE_SET;
        String obj = z2 ? hint.toString() : HttpUrl.FRAGMENT_ENCODE_SET;
        if (!z) {
            return !TextUtils.isEmpty(obj) ? obj : HttpUrl.FRAGMENT_ENCODE_SET;
        }
        StringBuilder append = new StringBuilder().append((Object) text);
        if (!TextUtils.isEmpty(obj)) {
            str = ", " + obj;
        }
        return append.append(str).toString();
    }

    private CharSequence getHintFromLayout() {
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout != null) {
            return textInputLayout.getHint();
        }
        return null;
    }

    private TextInputLayout getTextInputLayout() {
        for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    private boolean shouldUseTextInputLayoutFocusedRect(TextInputLayout textInputLayout) {
        return textInputLayout != null && this.textInputLayoutFocusedRectEnabled;
    }

    @Override // android.widget.TextView, android.view.View
    public void getFocusedRect(Rect r) {
        super.getFocusedRect(r);
        TextInputLayout textInputLayout = getTextInputLayout();
        if (!shouldUseTextInputLayoutFocusedRect(textInputLayout) || r == null) {
            return;
        }
        textInputLayout.getFocusedRect(this.parentRect);
        r.bottom = this.parentRect.bottom;
    }

    @Override // android.view.View
    public boolean getGlobalVisibleRect(Rect r, Point globalOffset) {
        TextInputLayout textInputLayout = getTextInputLayout();
        return shouldUseTextInputLayoutFocusedRect(textInputLayout) ? textInputLayout.getGlobalVisibleRect(r, globalOffset) : super.getGlobalVisibleRect(r, globalOffset);
    }

    @Override // android.widget.TextView
    public CharSequence getHint() {
        TextInputLayout textInputLayout = getTextInputLayout();
        return (textInputLayout == null || !textInputLayout.isProvidingHint()) ? super.getHint() : textInputLayout.getHint();
    }

    public boolean isTextInputLayoutFocusedRectEnabled() {
        return this.textInputLayoutFocusedRectEnabled;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout != null && textInputLayout.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
            setHint(HttpUrl.FRAGMENT_ENCODE_SET);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(outAttrs);
        if (onCreateInputConnection != null && outAttrs.hintText == null) {
            outAttrs.hintText = getHintFromLayout();
        }
        return onCreateInputConnection;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        TextInputLayout textInputLayout = getTextInputLayout();
        if (Build.VERSION.SDK_INT >= 23 || textInputLayout == null) {
            return;
        }
        info.setText(getAccessibilityNodeInfoText(textInputLayout));
    }

    @Override // android.view.View
    public boolean requestRectangleOnScreen(Rect rectangle) {
        TextInputLayout textInputLayout = getTextInputLayout();
        if (!shouldUseTextInputLayoutFocusedRect(textInputLayout) || rectangle == null) {
            return super.requestRectangleOnScreen(rectangle);
        }
        this.parentRect.set(rectangle.left, rectangle.top, rectangle.right, rectangle.bottom + (textInputLayout.getHeight() - getHeight()));
        return super.requestRectangleOnScreen(this.parentRect);
    }

    public void setTextInputLayoutFocusedRectEnabled(boolean textInputLayoutFocusedRectEnabled) {
        this.textInputLayoutFocusedRectEnabled = textInputLayoutFocusedRectEnabled;
    }
}