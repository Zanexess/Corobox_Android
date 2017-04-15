package me.labs.corobox.corobox.common;

import android.support.v4.app.Fragment;

import me.labs.corobox.corobox.di.IHasComponent;

public abstract class BaseFragment extends Fragment {
    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>)getActivity()).getComponent());
    }
}
