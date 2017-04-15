package me.labs.corobox.corobox.view.splash_screen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;

public class SplashActivityView extends BaseActivity implements ISplashActivityView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, MainActivityView.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {

    }
}