package me.labs.corobox.corobox.di.components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import me.labs.corobox.corobox.di.modules.CoroboxAppModule;
import me.labs.corobox.corobox.di.modules.NetworkModule;
import me.labs.corobox.corobox.di.scope.NetworkScope;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@NetworkScope
@Singleton
@Component(
        modules = {
                CoroboxAppModule.class,
                NetworkModule.class
        }
)
public interface INetworkComponent {
    Retrofit retrofit();
    OkHttpClient okHttpClient();
    SharedPreferences sharedPreferences();
}