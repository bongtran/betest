package vn.bongtran.be.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

class BaseDataSource {
    private Context _context;
    SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    BaseDataSource(Context context)
    {
        _context = context;
        dbHelper = new DatabaseHelper(_context);
    }



    void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    void close()
    {
        dbHelper.close();
    }
}
