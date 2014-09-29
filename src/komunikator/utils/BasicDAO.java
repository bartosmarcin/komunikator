package komunikator.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BasicDAO<T extends DatabaseObject> {

	private static final String tableName = "PROFILE";
	protected TableDefinition tabDef;
	protected SQLiteOpenHelper dbHelper;
	private SQLiteDatabase db;
	
	public BasicDAO(Context context){
		dbHelper = new DatabaseHelper(context);
		tabDef = getTableDefinition();
	}	
	
	protected abstract TableDefinition getTableDefinition();

	public void createTable(SQLiteDatabase db) {
		db.execSQL(tabDef.getCreateQuery());
	}

	public void dropTable(SQLiteDatabase db) {
		db.execSQL(tabDef.getDropQuery());
	}
	
	public T getById(long id) {
		if (!db.isOpen())
			openWriteableDatabase();
		String query = "select * from " + tableName + " where "
				+ TableDefinition.idColumnName + "=?;";
		String[] args = { String.valueOf(id) };
		Cursor cursor = db.rawQuery(query, args);
		if (cursor.getCount() != 1)
			return null;
		cursor.moveToFirst();
		T Obj = cursorToObject(cursor);
		cursor.close();
		return Obj;
	}

	private void openWriteableDatabase() {
		db = dbHelper.getWritableDatabase();
	}
	
	public T add(T obj) {
		ContentValues values = ObjectToContentValues(obj);
		if (db == null || !db.isOpen() || db.isReadOnly())
			openWriteableDatabase();
		long id = db.insert(tableName, null, values);
		obj.setId(id);
		return obj;
	}
	
	protected abstract T cursorToObject(Cursor cursor);	

	protected abstract ContentValues ObjectToContentValues(T obj);

}
