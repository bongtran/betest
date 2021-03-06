package vn.bongtran.be.network;

import android.util.Log;
import java.io.IOException;
import java.lang.annotation.Target;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.bongtran.be.model.ErrorModel;

class WebAPIManager<T> {
    String TAG = ">>>WebAPIManager";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    void doPostRequest(OkHttpClient client, int requestMethod, final String url, final String bodyParam, final RequestListener<Response> listener) {
        RequestBody body = RequestBody.create(JSON, bodyParam);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                call.cancel();
                listener.onFailure(new ErrorModel());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onSuccess(response);
            }
        });
    }

    void doGetRequest(OkHttpClient client, String url, final RequestListener<String> listener){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        listener.onFailure(new ErrorModel());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        listener.onSuccess(response.body().string());
                    }
                });
    }

    public interface RequestListener<T> {
        void onSuccess(T response);

        void onFailure(ErrorModel error);
    }
}