package me.labs.corobox.corobox.di.modules;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.network.ApiInterface;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    public ApiInterface provideApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

}
