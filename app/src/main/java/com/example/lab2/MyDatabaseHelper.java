package com.example.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ChatRoomDB";
    private static final String DB_TABLE = "ChatMessages";

  //  private static final String VERSION_NUMBER = "1";

    //columns
    private static final String COL_MESSAGE = "Message";
    private static final String COL_ISSENT = "IsSent";
    private static final String COL_MESSAGEID = "MessageID";

    //query
    private static final String CREATE_TABLE = "CREATE TABLE "+DB_TABLE+" ("+COL_MESSAGEID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_MESSAGE+" TEXT, "+COL_ISSENT+" BIT);";

    public MyDatabaseHelper(Context context) {
        super(context,DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(sqLiteDatabase);
    }

    //insert data
    public long insertData(String message, boolean isSend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MESSAGE, message);
        if (isSend)
            contentValues.put(COL_ISSENT, 0);
        else
            contentValues.put(COL_ISSENT, 1);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result;
    }


    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        Log.d("Database Version Number", Integer.toString(db.getVersion()));
        printCursor(cursor);
        return cursor;
    }


    public void printCursor(Cursor cursor) {

        Log.d("Column Count", Integer.toString(cursor.getColumnCount()));
        Log.d("Column Names", Arrays.toString(cursor.getColumnNames()));
        Log.d("Row Count", Integer.toString(cursor.getCount()));
        Log.d("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
    }

    public int deleteEntry(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String where="MessageID=?";
        int numberOFEntriesDeleted= db.delete(DB_TABLE, where, new String[]{Integer.toString(id)});
        return numberOFEntriesDeleted;
    }
}
