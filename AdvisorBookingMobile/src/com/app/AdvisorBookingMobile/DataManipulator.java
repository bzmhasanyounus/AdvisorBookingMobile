package com.app.AdvisorBookingMobile;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.List;

public class DataManipulator {
	private static final  String DATABASE_NAME = "AdvisorBookingMobile.db";
	private static final int DATABASE_VERSION = 1;
	static final String TABLE_NAME = "newtable";
	private static Context context;
	static SQLiteDatabase db;

	private SQLiteStatement insertStmt;
	
    private static final String INSERT = "insert into "
		+ TABLE_NAME + " (First_Name,Last_Name,student_number,courses,telephone_number,email_Id,address) values (?,?,?,?,?,?,?)";

	public DataManipulator(Context context) {
		DataManipulator.context = context;
		OpenHelper openHelper = new OpenHelper(DataManipulator.context);
		DataManipulator.db = openHelper.getWritableDatabase();
		this.insertStmt = DataManipulator.db.compileStatement(INSERT);

	}
	public long insert(String First_name,String Last_name,String student_number,String courses,String telephone_number,String email_Id,String address) {
		this.insertStmt.bindString(1, First_name);
		this.insertStmt.bindString(1, Last_name);
		this.insertStmt.bindString(2, student_number);
		this.insertStmt.bindString(2, courses);
		this.insertStmt.bindString(2, telephone_number);
		this.insertStmt.bindString(3, email_Id);
		this.insertStmt.bindString(4, address);
		return this.insertStmt.executeInsert();
	}

	public void deleteAll() {
		db.delete(TABLE_NAME, null, null);
	}

	public List<String[]> selectAll()
	{

		List<String[]> list = new ArrayList<String[]>();
		Cursor cursor = db.query(TABLE_NAME, new String[] {"First_name","Last_name","student_number","courses","telephone_number","email_Id","address" },
				null, null, null, null, null); 

		int x=0;
		if (cursor.moveToFirst()) {
			do {
				String[] b1=new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)};

				list.add(b1);

				x=x+1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		} 
		cursor.close();

		return list;
	}


	public void delete(int rowId) {
		db.delete(TABLE_NAME, null, null); 
	}



	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (student_number TEXT PRIMARY KEY, First_name TEXT, Last_name TEXT,courses TEXT, telephone_number TEXT, email_Id TEXT, address TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}



}
