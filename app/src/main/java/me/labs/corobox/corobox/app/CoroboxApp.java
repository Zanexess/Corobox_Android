package me.labs.corobox.corobox.app;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.di.components.DaggerIApiComponent;
import me.labs.corobox.corobox.di.components.DaggerICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.DaggerINetworkComponent;
import me.labs.corobox.corobox.di.components.IApiComponent;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.INetworkComponent;
import me.labs.corobox.corobox.di.modules.CoroboxAppModule;
import me.labs.corobox.corobox.di.modules.NetworkModule;

public class CoroboxApp extends Application {

    private ICoroboxAppComponent appComponent;
    private INetworkComponent networkComponent;
    private IApiComponent apiComponent;
    public static FragmentType  type = FragmentType.NEW_BOX;

    public static CoroboxApp get(Context context) {
        return (CoroboxApp) context.getApplicationContext();
    }

    public void  buildGraphAndInject() {
        appComponent = DaggerICoroboxAppComponent.builder()
                .coroboxAppModule(new CoroboxAppModule(this))
                .build();
        appComponent.inject(this);


        networkComponent = DaggerINetworkComponent.builder()
                .networkModule(new NetworkModule("http://185.143.172.79:8000/"))
                .coroboxAppModule(new CoroboxAppModule(this))
                .build();

        apiComponent = DaggerIApiComponent.builder().iNetworkComponent(networkComponent)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        buildGraphAndInject();
        Realm.init(getApplicationContext());


        try {
            Picasso picasso = new Picasso.Builder(getApplicationContext()).downloader(new OkHttp3Downloader(getApplicationContext(), 10 * 1024 * 1024)).build();
            Picasso.setSingletonInstance(picasso);
        } catch (Exception e) {

        }
    }

    public ICoroboxAppComponent getAppComponent() {
        return appComponent;
    }

    public INetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    public IApiComponent getApiComponent() {
        return apiComponent;
    }

//    public static final String AUTH_KEY = "Token 79036a9576257e07ebc9ffa05c0280afeeedd6bf";
    public static final String AUTH_KEY = "Token 2f40d34dc5dbb5366cb02005ec1f1a00beaa8c89";
//    public static final String AUTH_KEY = "Token dcb2824c5802b4a3fa88481eca85854ca3316c23";

}
