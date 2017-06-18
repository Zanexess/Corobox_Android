package me.labs.corobox.corobox.network;

import java.util.List;

import me.labs.corobox.corobox.model.realm.Category;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

public interface ApiInterface {

    @GET("/categories/")
    Observable<Response<List<Category>>> getCategories(@Header("Authorization") String header);

}
