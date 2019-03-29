package com.example.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static android.content.ContentValues.TAG;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ChatRoomDB";
    private static final String DB_TABLE = "ChatMessages";

    private static final String VERSION_NUMBER = "1";

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
    public boolean insertData(String message, boolean isSend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MESSAGE, message);
        if (isSend)
            contentValues.put(COL_ISSENT, 0);
        else
            contentValues.put(COL_ISSENT, 1);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1; //if result = -1 data doesn't insert
    }

    //view data
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        //Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }


    public void printCursor(Cursor c){

        Log.d(TAG, "PrintCursor()  version number:"+ VERSION_NUMBER);

        int numberCols = c.getColumnCount();
        int message = c.getColumnIndex(COL_MESSAGE);
        int messageId = c.getColumnIndex(COL_MESSAGEID);
        int  isSent = c.getColumnIndex(COL_ISSENT);

        Log.d(TAG, "PrintCursor() numberColumns:"+ c.getColumnCount());


        for(int i = 0;i<=numberCols;i++){

            Log.d(TAG, "PrintCursor() nameOfColumns:"+ c.getColumnName(i));

        }

        Log.d(TAG, "PrintCursor() NoRows:"+ c.getCount());


        while(c.moveToNext()) {
            String message1 = c.getString(message);
            String sent = c.getString(isSent);
            long id = c.getLong(messageId);

            Log.d(TAG, "printCursor() row: "+ message1+ " " + sent +" "+ id );

        }
    }
}
