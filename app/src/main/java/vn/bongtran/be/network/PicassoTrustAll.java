package vn.bongtran.be.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class PicassoTrustAll {
//    private static String LOG_TAG = PicassoTrustAll.class.getSimpleName();
//    private static boolean hasCustomPicassoSingletonInstanceSet;
//
//    public static Picasso with(Context context) {
//
//        if (hasCustomPicassoSingletonInstanceSet)
//            return Picasso.with(context);
//
//        try {
//            Picasso.setSingletonInstance(null);
//        } catch (IllegalStateException e) {
//            Log.w(LOG_TAG, "-> Default singleton instance already present" +
//                    " so CustomPicasso singleton cannot be set. Use CustomPicasso.getNewInstance() now.");
//            return Picasso.with(context);
//        }
//
//        Picasso picasso = new Picasso.Builder(context).
//                downloader(new OkHttp3Downloader(context))
//                .build();
//
//        Picasso.setSingletonInstance(picasso);
//        Log.w(LOG_TAG, "-> CustomPicasso singleton set to Picasso singleton." +
//                " In case if you need Picasso singleton in future then use Picasso.Builder()");
//        hasCustomPicassoSingletonInstanceSet = true;
//
//        return picasso;
//    }
//
//    public static Picasso getNewInstance(Context context) {
//
//        Log.w(LOG_TAG, "-> Do not forget to call customPicasso.shutdown()" +
//                " to avoid memory leak");
//
//        return new Picasso.Builder(context).
//                downloader(new OkHttp3Downloader(context))
//                .build();
//    }
private static Picasso mInstance = null;

    private PicassoTrustAll(Context context) {
        OkHttpClient client = new OkHttpClient();
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        } };
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            client.setSslSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mInstance = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(client))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e("PICASSO", exception.getMessage());
                    }
                }).build();

    }

    public static Picasso getInstance(Context context) {
        if (mInstance == null) {
            new PicassoTrustAll(context);
        }
        return mInstance;
    }
}
