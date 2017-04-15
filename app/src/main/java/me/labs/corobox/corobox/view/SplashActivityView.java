package me.labs.corobox.corobox.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;

import me.labs.corobox.corobox.MainActivity;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;

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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {

    }
}