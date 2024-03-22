package com.google.android.material.datepicker;

import android.os.Build;
import android.text.format.DateUtils;
import androidx.core.util.Pair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: 00ED.java */
/* loaded from: classes.dex */
public class DateStrings {
    private DateStrings() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Pair<String, String> getDateRangeString(Long start, Long end) {
        return getDateRangeString(start, end, null);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    static Pair<String, String> getDateRangeString(Long l, Long l2, SimpleDateFormat simpleDateFormat) {
        if (l == null && l2 == null) {
            return Pair.create(null, null);
        }
        if (l == null) {
            String dateString = getDateString(l2.longValue(), simpleDateFormat);
            Log5ECF72.a(dateString);
            LogE84000.a(dateString);
            Log229316.a(dateString);
            return Pair.create(null, dateString);
        }
        if (l2 == null) {
            String dateString2 = getDateString(l.longValue(), simpleDateFormat);
            Log5ECF72.a(dateString2);
            LogE84000.a(dateString2);
            Log229316.a(dateString2);
            return Pair.create(dateString2, null);
        }
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendar = UtcDates.getUtcCalendar();
        utcCalendar.setTimeInMillis(l.longValue());
        Calendar utcCalendar2 = UtcDates.getUtcCalendar();
        utcCalendar2.setTimeInMillis(l2.longValue());
        if (simpleDateFormat != null) {
            return Pair.create(simpleDateFormat.format(new Date(l.longValue())), simpleDateFormat.format(new Date(l2.longValue())));
        }
        if (utcCalendar.get(1) != utcCalendar2.get(1)) {
            String yearMonthDay = getYearMonthDay(l.longValue(), Locale.getDefault());
            Log5ECF72.a(yearMonthDay);
            LogE84000.a(yearMonthDay);
            Log229316.a(yearMonthDay);
            String yearMonthDay2 = getYearMonthDay(l2.longValue(), Locale.getDefault());
            Log5ECF72.a(yearMonthDay2);
            LogE84000.a(yearMonthDay2);
            Log229316.a(yearMonthDay2);
            return Pair.create(yearMonthDay, yearMonthDay2);
        }
        if (utcCalendar.get(1) == todayCalendar.get(1)) {
            String monthDay = getMonthDay(l.longValue(), Locale.getDefault());
            Log5ECF72.a(monthDay);
            LogE84000.a(monthDay);
            Log229316.a(monthDay);
            String monthDay2 = getMonthDay(l2.longValue(), Locale.getDefault());
            Log5ECF72.a(monthDay2);
            LogE84000.a(monthDay2);
            Log229316.a(monthDay2);
            return Pair.create(monthDay, monthDay2);
        }
        String monthDay3 = getMonthDay(l.longValue(), Locale.getDefault());
        Log5ECF72.a(monthDay3);
        LogE84000.a(monthDay3);
        Log229316.a(monthDay3);
        String yearMonthDay3 = getYearMonthDay(l2.longValue(), Locale.getDefault());
        Log5ECF72.a(yearMonthDay3);
        LogE84000.a(yearMonthDay3);
        Log229316.a(yearMonthDay3);
        return Pair.create(monthDay3, yearMonthDay3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String getDateString(long j) {
        String dateString = getDateString(j, null);
        Log5ECF72.a(dateString);
        LogE84000.a(dateString);
        Log229316.a(dateString);
        return dateString;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    static String getDateString(long j, SimpleDateFormat simpleDateFormat) {
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendar = UtcDates.getUtcCalendar();
        utcCalendar.setTimeInMillis(j);
        if (simpleDateFormat != null) {
            return simpleDateFormat.format(new Date(j));
        }
        if (todayCalendar.get(1) == utcCalendar.get(1)) {
            String monthDay = getMonthDay(j);
            Log5ECF72.a(monthDay);
            LogE84000.a(monthDay);
            Log229316.a(monthDay);
            return monthDay;
        }
        String yearMonthDay = getYearMonthDay(j);
        Log5ECF72.a(yearMonthDay);
        LogE84000.a(yearMonthDay);
        Log229316.a(yearMonthDay);
        return yearMonthDay;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    static String getMonthDay(long j) {
        String monthDay = getMonthDay(j, Locale.getDefault());
        Log5ECF72.a(monthDay);
        LogE84000.a(monthDay);
        Log229316.a(monthDay);
        return monthDay;
    }

    static String getMonthDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getAbbrMonthDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getMediumNoYear(locale).format(new Date(timeInMillis));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String getMonthDayOfWeekDay(long j) {
        String monthDayOfWeekDay = getMonthDayOfWeekDay(j, Locale.getDefault());
        Log5ECF72.a(monthDayOfWeekDay);
        LogE84000.a(monthDayOfWeekDay);
        Log229316.a(monthDayOfWeekDay);
        return monthDayOfWeekDay;
    }

    static String getMonthDayOfWeekDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getAbbrMonthWeekdayDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getFullFormat(locale).format(new Date(timeInMillis));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String getYearMonth(long j) {
        String formatDateTime = DateUtils.formatDateTime(null, j, 8228);
        Log5ECF72.a(formatDateTime);
        LogE84000.a(formatDateTime);
        Log229316.a(formatDateTime);
        return formatDateTime;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String getYearMonthDay(long j) {
        String yearMonthDay = getYearMonthDay(j, Locale.getDefault());
        Log5ECF72.a(yearMonthDay);
        LogE84000.a(yearMonthDay);
        Log229316.a(yearMonthDay);
        return yearMonthDay;
    }

    static String getYearMonthDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getYearAbbrMonthDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getMediumFormat(locale).format(new Date(timeInMillis));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static String getYearMonthDayOfWeekDay(long j) {
        String yearMonthDayOfWeekDay = getYearMonthDayOfWeekDay(j, Locale.getDefault());
        Log5ECF72.a(yearMonthDayOfWeekDay);
        LogE84000.a(yearMonthDayOfWeekDay);
        Log229316.a(yearMonthDayOfWeekDay);
        return yearMonthDayOfWeekDay;
    }

    static String getYearMonthDayOfWeekDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getYearAbbrMonthWeekdayDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getFullFormat(locale).format(new Date(timeInMillis));
    }
}
