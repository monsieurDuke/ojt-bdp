package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

/* loaded from: classes.dex */
public interface HasDefaultViewModelProviderFactory {
    default CreationExtras getDefaultViewModelCreationExtras() {
        return CreationExtras.Empty.INSTANCE;
    }

    ViewModelProvider.Factory getDefaultViewModelProviderFactory();
}
