package vn.bongtran.be.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import vn.bongtran.be.interfaces.OnGetCardDetailListCallBack;
import vn.bongtran.be.interfaces.OnGetCardListCallBack;
import vn.bongtran.be.model.CardDetailModel;
import vn.bongtran.be.model.CardModel;
import vn.bongtran.be.model.ErrorModel;
import vn.bongtran.be.utils.Statics;

public class HttpRequest {
    private static String TAG = ">>>HttpRequest";
    private static HttpRequest mInstance;
    private OkHttpClient client = new OkHttpClient();

    public static HttpRequest getInstance() {
        if (null == mInstance) {
            mInstance = new HttpRequest();
        }
        return mInstance;
    }

    public void getCardList(final OnGetCardDetailListCallBack onGetCardListCallBack, int page) {
        String url = Statics.URL_CARD_API;

        Map<String, String> params = new HashMap<>();
        Log.d(">>> getCardList", new Gson().toJson(params));
        WebAPIManager webServiceManager = new WebAPIManager();
        webServiceManager.doGetRequest(client, url, new WebAPIManager.RequestListener<Response>() {
            @Override
            public void onSuccess(Response response) {
                ArrayList<CardDetailModel> cardModelList = new ArrayList<>();
                try {
                    Type listType = new TypeToken<List<CardDetailModel>>() {
                    }.getType();
                    cardModelList = new Gson().fromJson(response.body().toString(), listType);
                    onGetCardListCallBack.onGetCardListSuccess(cardModelList);
                } catch (Exception e) {
                    onGetCardListCallBack.onGetCardListFail(new ErrorModel());
                }
            }

            @Override
            public void onFailure(ErrorModel error) {
                onGetCardListCallBack.onGetCardListFail(error);
            }
        });
    }

    public void getCardListPaging(final OnGetCardListCallBack onGetCardListCallBack, int page) {
        String url = Statics.URL_CARD_API_PAGING + page;
        Log.d(">>> getCardList", url);
        WebAPIManager webServiceManager = new WebAPIManager();
        webServiceManager.doGetRequest(client, url, new WebAPIManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                ArrayList<CardModel> cardModelList = new ArrayList<>();
                try {
                    Type listType = new TypeToken<ArrayList<CardModel>>() {
                    }.getType();


//                    String responseString = response.body().toString();
//                    responseString = responseString.substring(1);
//                    responseString = responseString.substring(0, responseString.length() - 2);
//                    Log.d(TAG, responseString);
                    cardModelList = new Gson().fromJson(response, listType);
                    onGetCardListCallBack.onGetCardListSuccess(cardModelList);
                } catch (Exception e) {
                    Log.d(">>> getCardList", e.getMessage());
                    onGetCardListCallBack.onGetCardListFail(new ErrorModel());
                }
            }

            @Override
            public void onFailure(ErrorModel error) {
                onGetCardListCallBack.onGetCardListFail(error);
            }
        });
    }
}
