package vn.bongtran.be.interfaces;

import com.google.gson.JsonArray;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CardRepo {
    @GET("cards/{paging}")
    Observable<JsonArray> listCardDetails(@Path("paging") int paging);
}

