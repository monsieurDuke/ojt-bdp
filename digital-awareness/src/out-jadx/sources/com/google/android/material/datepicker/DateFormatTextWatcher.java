package com.google.android.material.datepicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.google.android.material.R;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* loaded from: classes.dex */
abstract class DateFormatTextWatcher extends TextWatcherAdapter {
    private static final int VALIDATION_DELAY = 1000;
    private final CalendarConstraints constraints;
    private final DateFormat dateFormat;
    private final String outOfRange;
    private final Runnable setErrorCallback;
    private Runnable setRangeErrorCallback;
    private final TextInputLayout textInputLayout;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DateFormatTextWatcher(final String formatHint, DateFormat dateFormat, TextInputLayout textInputLayout, CalendarConstraints constraints) {
        this.dateFormat = dateFormat;
        this.textInputLayout = textInputLayout;
        this.constraints = constraints;
        this.outOfRange = textInputLayout.getContext().getString(R.string.mtrl_picker_out_of_range);
        this.setErrorCallback = new Runnable() { // from class: com.google.android.material.datepicker.DateFormatTextWatcher.1
            /* JADX WARN: Failed to parse debug info
            jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
            	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
             */
            @Override // java.lang.Runnable
            public void run() {
                TextInputLayout textInputLayout2 = DateFormatTextWatcher.this.textInputLayout;
                DateFormat dateFormat2 = DateFormatTextWatcher.this.dateFormat;
                Context context = textInputLayout2.getContext();
                String string = context.getString(R.string.mtrl_picker_invalid_format);
                String format = String.format(context.getString(R.string.mtrl_picker_invalid_format_use), formatHint);
                Log5ECF72.a(format);
                LogE84000.a(format);
                Log229316.a(format);
                String format2 = String.format(context.getString(R.string.mtrl_picker_invalid_format_example), dateFormat2.format(new Date(UtcDates.getTodayCalendar().getTimeInMillis())));
                Log5ECF72.a(format2);
                LogE84000.a(format2);
                Log229316.a(format2);
                textInputLayout2.setError(string + "\n" + format + "\n" + format2);
                DateFormatTextWatcher.this.onInvalidDate();
            }
        };
    }

    private Runnable createRangeErrorCallback(final long milliseconds) {
        return new Runnable() { // from class: com.google.android.material.datepicker.DateFormatTextWatcher.2
            /* JADX WARN: Failed to parse debug info
            jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
            	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
            	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
             */
            @Override // java.lang.Runnable
            public void run() {
                TextInputLayout textInputLayout = DateFormatTextWatcher.this.textInputLayout;
                String str = DateFormatTextWatcher.this.outOfRange;
                Log5ECF72.a(str);
                LogE84000.a(str);
                Log229316.a(str);
                String dateString = DateStrings.getDateString(milliseconds);
                Log5ECF72.a(dateString);
                LogE84000.a(dateString);
                Log229316.a(dateString);
                String format = String.format(str, dateString);
                Log5ECF72.a(format);
                LogE84000.a(format);
                Log229316.a(format);
                textInputLayout.setError(format);
                DateFormatTextWatcher.this.onInvalidDate();
            }
        };
    }

    void onInvalidDate() {
    }

    @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.textInputLayout.removeCallbacks(this.setErrorCallback);
        this.textInputLayout.removeCallbacks(this.setRangeErrorCallback);
        this.textInputLayout.setError(null);
        onValidDate(null);
        if (TextUtils.isEmpty(s)) {
            return;
        }
        try {
            Date parse = this.dateFormat.parse(s.toString());
            this.textInputLayout.setError(null);
            long time = parse.getTime();
            if (this.constraints.getDateValidator().isValid(time) && this.constraints.isWithinBounds(time)) {
                onValidDate(Long.valueOf(parse.getTime()));
                return;
            }
            Runnable createRangeErrorCallback = createRangeErrorCallback(time);
            this.setRangeErrorCallback = createRangeErrorCallback;
            runValidation(this.textInputLayout, createRangeErrorCallback);
        } catch (ParseException e) {
            runValidation(this.textInputLayout, this.setErrorCallback);
        }
    }

    abstract void onValidDate(Long l);

    public void runValidation(View view, Runnable validation) {
        view.postDelayed(validation, 1000L);
    }
}
