package komunikator.utils;

import komunikator.profile.ProfileDAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final static String dbName = "komunikator_database.db";
	private Context context;
	private final static int initDbVersion = 1;
	
	public DatabaseHelper(Context context){
		super(context, dbName, null, initDbVersion);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		new ProfileDAO(context).createTable(db);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		new ProfileDAO(context).dropTable(db);
		onCreate(db);
	}
	
	

}
