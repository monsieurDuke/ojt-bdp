package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import okhttp3.HttpUrl;

/* compiled from: CoroutineContextImpl.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0000H\u0002J\u0013\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00100\u0013H\u0016¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0096\u0002¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", HttpUrl.FRAGMENT_ENCODE_SET, "containsAll", "context", "equals", "other", HttpUrl.FRAGMENT_ENCODE_SET, "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", HttpUrl.FRAGMENT_ENCODE_SET, "minusKey", "size", "toString", HttpUrl.FRAGMENT_ENCODE_SET, "writeReplace", "Serialized", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CombinedContext implements CoroutineContext, Serializable {
    private final CoroutineContext.Element element;
    private final CoroutineContext left;

    /* compiled from: CoroutineContextImpl.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", HttpUrl.FRAGMENT_ENCODE_SET, "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", HttpUrl.FRAGMENT_ENCODE_SET, "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private static final class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final CoroutineContext[] elements;

        public Serialized(CoroutineContext[] elements) {
            Intrinsics.checkNotNullParameter(elements, "elements");
            this.elements = elements;
        }

        private final Object readResolve() {
            CoroutineContext[] coroutineContextArr = this.elements;
            CoroutineContext coroutineContext = EmptyCoroutineContext.INSTANCE;
            for (CoroutineContext coroutineContext2 : coroutineContextArr) {
                coroutineContext = coroutineContext.plus(coroutineContext2);
            }
            return coroutineContext;
        }

        public final CoroutineContext[] getElements() {
            return this.elements;
        }
    }

    public CombinedContext(CoroutineContext left, CoroutineContext.Element element) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(element, "element");
        this.left = left;
        this.element = element;
    }

    private final boolean contains(CoroutineContext.Element element) {
        return Intrinsics.areEqual(get(element.getKey()), element);
    }

    private final boolean containsAll(CombinedContext context) {
        CombinedContext combinedContext = context;
        while (contains(combinedContext.element)) {
            CoroutineContext coroutineContext = combinedContext.left;
            if (!(coroutineContext instanceof CombinedContext)) {
                Intrinsics.checkNotNull(coroutineContext, "null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
                return contains((CoroutineContext.Element) coroutineContext);
            }
            combinedContext = (CombinedContext) coroutineContext;
        }
        return false;
    }

    private final int size() {
        CombinedContext combinedContext = this;
        int i = 2;
        while (true) {
            CoroutineContext coroutineContext = combinedContext.left;
            CombinedContext combinedContext2 = coroutineContext instanceof CombinedContext ? (CombinedContext) coroutineContext : null;
            if (combinedContext2 == null) {
                return i;
            }
            combinedContext = combinedContext2;
            i++;
        }
    }

    private final Object writeReplace() {
        int size = size();
        final CoroutineContext[] coroutineContextArr = new CoroutineContext[size];
        final Ref.IntRef intRef = new Ref.IntRef();
        fold(Unit.INSTANCE, new Function2<Unit, CoroutineContext.Element, Unit>() { // from class: kotlin.coroutines.CombinedContext$writeReplace$1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Unit unit, CoroutineContext.Element element) {
                invoke2(unit, element);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Unit unit, CoroutineContext.Element element) {
                Intrinsics.checkNotNullParameter(unit, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(element, "element");
                CoroutineContext[] coroutineContextArr2 = coroutineContextArr;
                int i = intRef.element;
                intRef.element = i + 1;
                coroutineContextArr2[i] = element;
            }
        });
        if (intRef.element == size) {
            return new Serialized(coroutineContextArr);
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public boolean equals(Object other) {
        return this == other || ((other instanceof CombinedContext) && ((CombinedContext) other).size() == size() && ((CombinedContext) other).containsAll(this));
    }

    @Override // kotlin.coroutines.CoroutineContext
    public <R> R fold(R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
        Intrinsics.checkNotNullParameter(operation, "operation");
        return operation.invoke((Object) this.left.fold(initial, operation), this.element);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        CombinedContext combinedContext = this;
        while (true) {
            E e = (E) combinedContext.element.get(key);
            if (e != null) {
                return e;
            }
            CoroutineContext coroutineContext = combinedContext.left;
            if (!(coroutineContext instanceof CombinedContext)) {
                return (E) coroutineContext.get(key);
            }
            combinedContext = (CombinedContext) coroutineContext;
        }
    }

    public int hashCode() {
        return this.left.hashCode() + this.element.hashCode();
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (this.element.get(key) != null) {
            return this.left;
        }
        CoroutineContext minusKey = this.left.minusKey(key);
        return minusKey == this.left ? this : minusKey == EmptyCoroutineContext.INSTANCE ? this.element : new CombinedContext(minusKey, this.element);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext plus(CoroutineContext context) {
        return CoroutineContext.DefaultImpls.plus(this, context);
    }

    public String toString() {
        return '[' + ((String) fold(HttpUrl.FRAGMENT_ENCODE_SET, new Function2<String, CoroutineContext.Element, String>() { // from class: kotlin.coroutines.CombinedContext$toString$1
            @Override // kotlin.jvm.functions.Function2
            public final String invoke(String acc, CoroutineContext.Element element) {
                Intrinsics.checkNotNullParameter(acc, "acc");
                Intrinsics.checkNotNullParameter(element, "element");
                return acc.length() == 0 ? element.toString() : acc + ", " + element;
            }
        })) + ']';
    }
}