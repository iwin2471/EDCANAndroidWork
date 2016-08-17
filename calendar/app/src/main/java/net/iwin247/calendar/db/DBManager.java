package net.iwin247.calendar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

	public DBManager(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table SUMMARY("
				+ " CONTENT_TEXT TEXT DEFAULT 'asdf', "
				+ " SUMMARY_DATE TEXT,"
				+ " START_TIME TEXT,"
				+ " END_TIME TEXT,"
				+ ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void insert(String Summary, String Date, String Stime, String Etime) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into summary values('" + Summary + "', '" + Date + "', '" + Stime + "', '" + Etime +"');");
		db.close();
	}

	public void update(String _query) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(_query);
		db.close();
	}

	public void delete(String _query) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(_query);
		db.close();
	}

	public String getData() {
		SQLiteDatabase db = getReadableDatabase();
		String str = "";

		Cursor cursor = db.rawQuery("select * from summary", null);
		while(cursor.moveToNext()) {
			str += cursor.getString(0) + "," + cursor.getInt(1) +","+cursor.getInt(2)+","+cursor.getInt(3)+"\n";
		}

		return str;
	}
}