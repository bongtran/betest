package vn.bongtran.be;

import android.app.Application;
import android.content.Context;

import vn.bongtran.be.data.DataManager;

public class App extends Application {
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        DataManager.initDataManager(appContext);
    }

    public static Context getAppContext(){
        return appContext;
    }
}
