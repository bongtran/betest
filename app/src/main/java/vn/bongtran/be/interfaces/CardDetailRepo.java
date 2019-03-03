package vn.bongtran.be.interfaces;

import com.google.gson.JsonArray;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CardDetailRepo {
    @GET("cards")
    Observable<JsonArray> listCardDetails();
}
