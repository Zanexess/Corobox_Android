package me.labs.corobox.corobox.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(CoroboxApp.get(this).getAppComponent());
    }

    protected abstract void setupComponent(ICoroboxAppComponent appComponent);


}