package com.finance.sakuid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/
public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_saku_id";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "tbl_kelola";

    private static final String ID_COL = "id";
    private static final String KETERANGAN_COL = "keterangan";
    private static final String STATUS_COL = "status";
    private static final String JUMLAH_COL = "jumlah";
    private static final String TANGGAL_COL = "tanggal";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KETERANGAN_COL + " TEXT,"
                + STATUS_COL + " TEXT,"
                + JUMLAH_COL + " TEXT,"
                + TANGGAL_COL + " DATE)";

        db.execSQL(query);
    }

    public void addNewData(String keterangan, String status, String jumlah, String tanggal) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KETERANGAN_COL, keterangan);
        values.put(STATUS_COL, status);
        values.put(JUMLAH_COL, jumlah);
        values.put(TANGGAL_COL, tanggal);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public Cursor getData(String status){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_kelola WHERE status = ?",new String[]{status});
        return cursor;
    }

    public int sumData() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;

        // Query untuk menghitung total pendapatan
        String queryPendapatan = "SELECT SUM(jumlah) FROM " + TABLE_NAME + " WHERE status = 'pendapatan'";
        Cursor cursorPendapatan = db.rawQuery(queryPendapatan, null);
        if (cursorPendapatan.moveToFirst()) {
            total += cursorPendapatan.getInt(0);
        }
        cursorPendapatan.close();

        // Query untuk menghitung total pengeluaran
        String queryPengeluaran = "SELECT SUM(jumlah) FROM " + TABLE_NAME + " WHERE status = 'pengeluaran'";
        Cursor cursorPengeluaran = db.rawQuery(queryPengeluaran, null);
        if (cursorPengeluaran.moveToFirst()) {
            total -= cursorPengeluaran.getInt(0);
        }
        cursorPengeluaran.close();

        return total;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
