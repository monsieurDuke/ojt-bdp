package kotlin.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.time.Duration;
import okhttp3.HttpUrl;

/* compiled from: longSaturatedMath.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\"\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\"\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\n\u001a \u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, d2 = {"checkInfiniteSumDefined", HttpUrl.FRAGMENT_ENCODE_SET, "longNs", TypedValues.TransitionType.S_DURATION, "Lkotlin/time/Duration;", "durationNs", "checkInfiniteSumDefined-PjuGub4", "(JJJ)J", "saturatingAdd", "saturatingAdd-pTJri5U", "(JJ)J", "saturatingAddInHalves", "saturatingAddInHalves-pTJri5U", "saturatingDiff", "valueNs", "originNs", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class LongSaturatedMathKt {
    /* renamed from: checkInfiniteSumDefined-PjuGub4, reason: not valid java name */
    private static final long m1486checkInfiniteSumDefinedPjuGub4(long longNs, long duration, long durationNs) {
        if (!Duration.m1391isInfiniteimpl(duration) || (longNs ^ durationNs) >= 0) {
            return longNs;
        }
        throw new IllegalArgumentException("Summing infinities of different signs");
    }

    /* renamed from: saturatingAdd-pTJri5U, reason: not valid java name */
    public static final long m1487saturatingAddpTJri5U(long longNs, long duration) {
        long m1379getInWholeNanosecondsimpl = Duration.m1379getInWholeNanosecondsimpl(duration);
        if (((longNs - 1) | 1) == Long.MAX_VALUE) {
            return m1486checkInfiniteSumDefinedPjuGub4(longNs, duration, m1379getInWholeNanosecondsimpl);
        }
        if ((1 | (m1379getInWholeNanosecondsimpl - 1)) == Long.MAX_VALUE) {
            return m1488saturatingAddInHalvespTJri5U(longNs, duration);
        }
        long j = longNs + m1379getInWholeNanosecondsimpl;
        return ((longNs ^ j) & (m1379getInWholeNanosecondsimpl ^ j)) < 0 ? longNs < 0 ? Long.MIN_VALUE : Long.MAX_VALUE : j;
    }

    /* renamed from: saturatingAddInHalves-pTJri5U, reason: not valid java name */
    private static final long m1488saturatingAddInHalvespTJri5U(long longNs, long duration) {
        long m1362divUwyO8pc = Duration.m1362divUwyO8pc(duration, 2);
        return ((Duration.m1379getInWholeNanosecondsimpl(m1362divUwyO8pc) - 1) | 1) == Long.MAX_VALUE ? (long) (longNs + Duration.m1402toDoubleimpl(duration, DurationUnit.NANOSECONDS)) : m1487saturatingAddpTJri5U(m1487saturatingAddpTJri5U(longNs, m1362divUwyO8pc), m1362divUwyO8pc);
    }

    public static final long saturatingDiff(long valueNs, long originNs) {
        if ((1 | (originNs - 1)) == Long.MAX_VALUE) {
            return Duration.m1411unaryMinusUwyO8pc(DurationKt.toDuration(originNs, DurationUnit.DAYS));
        }
        long j = valueNs - originNs;
        if (((j ^ valueNs) & (~(j ^ originNs))) >= 0) {
            Duration.Companion companion = Duration.INSTANCE;
            return DurationKt.toDuration(j, DurationUnit.NANOSECONDS);
        }
        long j2 = (long) DurationKt.NANOS_IN_MILLIS;
        long j3 = (valueNs / j2) - (originNs / j2);
        long j4 = (valueNs % j2) - (originNs % j2);
        Duration.Companion companion2 = Duration.INSTANCE;
        long duration = DurationKt.toDuration(j3, DurationUnit.MILLISECONDS);
        Duration.Companion companion3 = Duration.INSTANCE;
        return Duration.m1395plusLRDsOJo(duration, DurationKt.toDuration(j4, DurationUnit.NANOSECONDS));
    }
}
