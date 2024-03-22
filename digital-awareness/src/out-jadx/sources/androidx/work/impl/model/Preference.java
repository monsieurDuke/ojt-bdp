package androidx.work.impl.model;

/* loaded from: classes.dex */
public class Preference {
    public String mKey;
    public Long mValue;

    public Preference(String key, long value) {
        this.mKey = key;
        this.mValue = Long.valueOf(value);
    }

    public Preference(String key, boolean value) {
        this(key, value ? 1L : 0L);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preference)) {
            return false;
        }
        Preference preference = (Preference) o;
        if (!this.mKey.equals(preference.mKey)) {
            return false;
        }
        Long l = this.mValue;
        return l != null ? l.equals(preference.mValue) : preference.mValue == null;
    }

    public int hashCode() {
        int hashCode = this.mKey.hashCode() * 31;
        Long l = this.mValue;
        return hashCode + (l != null ? l.hashCode() : 0);
    }
}
