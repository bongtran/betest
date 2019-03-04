package vn.bongtran.be.data;

import android.content.Context;

import java.util.ArrayList;

import vn.bongtran.be.model.CardLiteModel;

public class DataManager {
    private static DataManager _instance;
    private static Context _context;
    private final static String _lock = "";

    private DataManager() {
    }

    public static DataManager sharedInstance() {
        synchronized (_lock) {
            if (_instance == null)
                _instance = new DataManager();

            return _instance;
        }
    }

    public static void initDataManager(Context applicationContext) {
        _context = applicationContext;
    }

    public boolean insertCard(CardLiteModel card) {
        CardDataSource cardDataSource = new CardDataSource(_context);
        cardDataSource.open();
        boolean result = cardDataSource.insertCard(card);
        cardDataSource.close();
        cardDataSource = null;
        return result;
    }

    public CardLiteModel getCard() {
        CardLiteModel result = null;
        CardDataSource cardDataSource = new CardDataSource(_context);
        cardDataSource.open();
        result = cardDataSource.getCard();
        cardDataSource.close();
        cardDataSource = null;
        return result;
    }

    public ArrayList<CardLiteModel> getCards() {
        ArrayList<CardLiteModel> result = null;
        CardDataSource cardDataSource = new CardDataSource(_context);
        cardDataSource.open();
        result = cardDataSource.getCards();
        cardDataSource.close();
        cardDataSource = null;
        return result;
    }

}
