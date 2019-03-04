package vn.bongtran.be.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import vn.bongtran.be.model.CardLiteModel;

public class CardDataSource extends BaseDataSource {
    public CardDataSource(Context context) {
        super(context);
    }


    public boolean insertCard(CardLiteModel card) {
        ContentValues values = new ContentValues();
        try {
            values.put(DatabaseHelper.DATA_COL, new Gson().toJson(card));

            long val = database.insert(DatabaseHelper.TABLE_CARD, null, values);
            return val >= 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public CardLiteModel getCard() {
        CardLiteModel result = null;
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_CARD + " ORDER BY " + DatabaseHelper.ID_COL + " DESC LIMIT 1";
            Log.d(">>> getCard", query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                String data = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_COL));
                result = new Gson().fromJson(data, CardLiteModel.class);
            }
            cursor.close();
        } catch (Exception e) {
        }
        return result;
    }

    public ArrayList<CardLiteModel> getCards() {
        ArrayList<CardLiteModel> cards = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_CARD;
            Log.d(">>> getCard", query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String data = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_COL));
                    CardLiteModel card = new Gson().fromJson(data, CardLiteModel.class);
                    cards.add(card);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
        }

        return cards;
    }
}
