package me.labs.corobox.corobox.view.main_screen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import me.labs.corobox.corobox.BuildConfig;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.activities.DaggerIMainActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.di.modules.activities.MainActivityModule;

public class MainActivityView extends BaseActivity implements IMainActivityView {

    private IMainActivityComponent mainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .iCoroboxAppComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
