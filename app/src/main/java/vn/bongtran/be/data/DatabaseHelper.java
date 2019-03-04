package vn.bongtran.be.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vn.bongtran.be.utils.Statics;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_CARD = "Card";
    public static final String ID_COL = "id";
    public static final String DATA_COL = "data";

    static final String CREATE_CARD_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CARD + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + DATA_COL + " TEXT)";


    static final String dbName = "dbcard";

    public DatabaseHelper(Context context) {
        super(context, dbName, null, Statics.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_CARD_TABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        try {
            // Drop table if exists
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), e.getMessage());
        }
        onCreate(sqLiteDatabase);
    }
}
