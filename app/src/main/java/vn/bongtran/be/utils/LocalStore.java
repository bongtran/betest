package vn.bongtran.be.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.bongtran.be.App;
import vn.bongtran.be.model.CardLiteModel;

public class LocalStore {
    private static LocalStore instance;
    SharedPreferences pref;

    private LocalStore() {
        pref = App.getAppContext().getSharedPreferences("MyPref", 0);
    }

    public static synchronized LocalStore getInstance() {
        if (instance == null) {
            instance = new LocalStore();
        }
        return instance;
    }

    public CardLiteModel getCardLite() {
       CardLiteModel result = null;
        try {
            String m = getStringValue(Statics.CARD_EXTRA_NAME);
            if (!m.isEmpty()) {
                Type listType = new TypeToken<List<CardLiteModel>>() {
                }.getType();
                ArrayList<CardLiteModel> arrayList = new Gson().fromJson(m, listType);
                if(arrayList != null && arrayList.size() > 0){
                    result = arrayList.get(arrayList.size() -1);
                }
            }

        } catch (Exception e) {

        }
        return result;
    }

    public ArrayList<CardLiteModel> getCardLites() {
        ArrayList<CardLiteModel> result = new ArrayList<CardLiteModel>();
        try {
            String m = getStringValue(Statics.CARD_EXTRA_NAME);
            if (!m.isEmpty()) {
                Type listType = new TypeToken<List<CardLiteModel>>() {
                }.getType();
                result = new Gson().fromJson(m, listType);
            }

        } catch (Exception e) {

        }
        return result;
    }

    public void saveCard(CardLiteModel card) {
        ArrayList<CardLiteModel> cards = getCardLites();
        cards.add(card);

        putString(Statics.CARD_EXTRA_NAME, new Gson().toJson(cards));
    }

    public int getLocalId(){
        int result = getIntValue(Statics.CARD_ID_LOCAL);
        if(result == 0)
            result = 10151;

        return result + 1;
    }

    public void putLocalId(int id){
        putInt(Statics.CARD_ID_LOCAL, id);
    }

    public boolean justAddCard(){
        return getBooleanValue(Statics.CARD_ADDED);
    }

    public void putJustAddCard(boolean value){
        putBoolean(Statics.CARD_ADDED, value);
    }

    private String getStringValue(String key){
        return pref.getString(key, "");
    }

    private void putString(String key, String value){
        pref.edit().putString(key, value).apply();
    }

    private boolean getBooleanValue(String key){
        return pref.getBoolean(key, false);
    }

    private void putBoolean(String key, boolean value){
        pref.edit().putBoolean(key, value).apply();
    }

    private int getIntValue(String key){
        return pref.getInt(key, 0);
    }

    private void putInt(String key, int value){
        pref.edit().putInt(key, value).apply();
    }
}
