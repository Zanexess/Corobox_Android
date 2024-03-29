package me.labs.corobox.corobox.di.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.compat.BuildConfig;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import me.labs.corobox.corobox.common.serializers.AddressSerializer;
import me.labs.corobox.corobox.common.serializers.CategoryNumberModelSerializer;
import me.labs.corobox.corobox.common.serializers.OrderModelToSerializer;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetworkModule {

    private String mBaseUrl;

    public NetworkModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        try {
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.AddressModelRealmProxy"), new AddressSerializer());
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.OrderModelToRealmProxy"), new OrderModelToSerializer());
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.CategoryNumberModelRealmProxy"), new CategoryNumberModelSerializer());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.BUILD_TYPE.contentEquals("release")) {
//            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        } else {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}