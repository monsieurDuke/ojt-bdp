package com.google.android.material.timepicker;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: 0103.java */
/* loaded from: classes.dex */
public class TimeModel implements Parcelable {
    public static final Parcelable.Creator<TimeModel> CREATOR = new Parcelable.Creator<TimeModel>() { // from class: com.google.android.material.timepicker.TimeModel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeModel createFromParcel(Parcel in) {
            return new TimeModel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeModel[] newArray(int size) {
            return new TimeModel[size];
        }
    };
    public static final String NUMBER_FORMAT = "%d";
    public static final String ZERO_LEADING_NUMBER_FORMAT = "%02d";
    final int format;
    int hour;
    private final MaxInputValidator hourInputValidator;
    int minute;
    private final MaxInputValidator minuteInputValidator;
    int period;
    int selection;

    public TimeModel() {
        this(0);
    }

    public TimeModel(int format) {
        this(0, 0, 10, format);
    }

    public TimeModel(int hour, int minute, int selection, int format) {
        this.hour = hour;
        this.minute = minute;
        this.selection = selection;
        this.format = format;
        this.period = getPeriod(hour);
        this.minuteInputValidator = new MaxInputValidator(59);
        this.hourInputValidator = new MaxInputValidator(format == 1 ? 24 : 12);
    }

    protected TimeModel(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt(), in.readInt());
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String formatText(Resources resources, CharSequence charSequence) {
        String formatText = formatText(resources, charSequence, ZERO_LEADING_NUMBER_FORMAT);
        Log5ECF72.a(formatText);
        LogE84000.a(formatText);
        Log229316.a(formatText);
        return formatText;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String formatText(Resources resources, CharSequence charSequence, String str) {
        Locale locale = resources.getConfiguration().locale;
        String valueOf = String.valueOf(charSequence);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        Log229316.a(valueOf);
        String format = String.format(locale, str, Integer.valueOf(Integer.parseInt(valueOf)));
        Log5ECF72.a(format);
        LogE84000.a(format);
        Log229316.a(format);
        return format;
    }

    private static int getPeriod(int hourOfDay) {
        return hourOfDay >= 12 ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeModel)) {
            return false;
        }
        TimeModel timeModel = (TimeModel) o;
        return this.hour == timeModel.hour && this.minute == timeModel.minute && this.format == timeModel.format && this.selection == timeModel.selection;
    }

    public int getHourForDisplay() {
        if (this.format == 1) {
            return this.hour % 24;
        }
        int i = this.hour;
        if (i % 12 == 0) {
            return 12;
        }
        return this.period == 1 ? i - 12 : i;
    }

    public MaxInputValidator getHourInputValidator() {
        return this.hourInputValidator;
    }

    public MaxInputValidator getMinuteInputValidator() {
        return this.minuteInputValidator;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.format), Integer.valueOf(this.hour), Integer.valueOf(this.minute), Integer.valueOf(this.selection)});
    }

    public void setHour(int hour) {
        if (this.format == 1) {
            this.hour = hour;
        } else {
            this.hour = (hour % 12) + (this.period == 1 ? 12 : 0);
        }
    }

    public void setHourOfDay(int hour) {
        this.period = getPeriod(hour);
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute % 60;
    }

    public void setPeriod(int period) {
        if (period != this.period) {
            this.period = period;
            int i = this.hour;
            if (i < 12 && period == 1) {
                this.hour = i + 12;
            } else {
                if (i < 12 || period != 0) {
                    return;
                }
                this.hour = i - 12;
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hour);
        dest.writeInt(this.minute);
        dest.writeInt(this.selection);
        dest.writeInt(this.format);
    }
}
