package me.labs.corobox.corobox.network;

import java.util.List;
import java.util.Objects;

import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.model.realm.ProfileModel;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @PUT("/address_set_default/{id}/")
    Observable<Response<Object>> setDefaultAddress(@Header("Authorization") String authKey, @Path("id") Integer id);

    // Stuff

    @GET("/stuff/")
    Observable<Response<List<Box>>> getStuff(@Header("Authorization") String authKey);

    // OrderTo

    @PUT("/order_to_put/")
    Observable<Response<Object>> putOrderTo(@Header("Authorization") String authKey, @Body String orderModelTo);

    @GET("/order_to/")
    Observable<Response<List<OrderModelTo>>> getOrderTo(@Header("Authorization") String authKey);

    @GET("/order_from/")
    Observable<Response<List<OrderModelFrom>>> getOrderFrom(@Header("Authorization") String authKey);

    @PUT("/order_from_put/")
    Observable<Response<Object>> putOrderFrom(@Header("Authorization") String authKey, @Body String orderModelFrom);

    @PUT("/order_to_cancel/{uuid}/")
    Observable<Response<Object>> cancelOrderTo(@Header("Authorization") String authKey, @Path("uuid") String uuid);

    @PUT("/order_from_cancel/{uuid}/")
    Observable<Response<Object>> cancelOrderFrom(@Header("Authorization") String authKey, @Path("uuid") String uuid);

    // Profile

    @GET("/profile/")
    Observable<Response<List<ProfileModel>>> getProfile(@Header("Authorization") String authKey);

    @PUT("/profile_upd/{id}/")
    Observable<Response<Object>> putProfile(@Header("Authorization") String authKey, @Path("id") String id, @Body ProfileModel profileModel);
}
