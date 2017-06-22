package me.labs.corobox.corobox.network;

import java.util.List;

import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.AddressFragmentPresenter;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("/categories/")
    Observable<Response<List<Category>>> getCategories(@Header("Authorization") String authKey);

    // Address

    @GET("/address/")
    Observable<Response<List<AddressModel>>> getAddressList(@Header("Authorization") String authKey);

    @PUT("/address_put/")
    Observable<Response<Object>> putAddress(@Header("Authorization") String authKey, @Body AddressModel address);

    @DELETE("/address_del/{id}/")
    Observable<Response<Object>> deleteAddress(@Header("Authorization") String authKey, @Path("id") Integer id);

    // Stuff

    @GET("/stuff/")
    Observable<Response<List<Box>>> getStuff(@Header("Authorization") String authKey);

}
