package me.labs.corobox.corobox.common;

import android.app.Service;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;

public abstract class BaseService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        setupComponent(CoroboxApp.get(this).getAppComponent());
    }

    protected abstract void setupComponent(ICoroboxAppComponent appComponent);
}
