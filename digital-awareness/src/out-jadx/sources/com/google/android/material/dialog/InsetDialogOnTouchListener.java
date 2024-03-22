package com.google.android.material.dialog;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/* loaded from: classes.dex */
public class InsetDialogOnTouchListener implements View.OnTouchListener {
    private final Dialog dialog;
    private final int leftInset;
    private final int prePieSlop;
    private final int topInset;

    public InsetDialogOnTouchListener(Dialog dialog, Rect insets) {
        this.dialog = dialog;
        this.leftInset = insets.left;
        this.topInset = insets.top;
        this.prePieSlop = ViewConfiguration.get(dialog.getContext()).getScaledWindowTouchSlop();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent event) {
        View findViewById = view.findViewById(16908290);
        int left = this.leftInset + findViewById.getLeft();
        int width = findViewById.getWidth() + left;
        if (new RectF(left, this.topInset + findViewById.getTop(), width, findViewById.getHeight() + r3).contains(event.getX(), event.getY())) {
            return false;
        }
        MotionEvent obtain = MotionEvent.obtain(event);
        if (event.getAction() == 1) {
            obtain.setAction(4);
        }
        if (Build.VERSION.SDK_INT < 28) {
            obtain.setAction(0);
            int i = this.prePieSlop;
            obtain.setLocation((-i) - 1, (-i) - 1);
        }
        view.performClick();
        return this.dialog.onTouchEvent(obtain);
    }
}
