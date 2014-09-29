package komunikator.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BasicDAO<T extends DatabaseObject> {

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
		if (db==null || !db.isOpen())
			openWriteableDatabase();
		Cursor cursor = db.query(
				tabDef.getTableName(),
				null, 
				TableDefinition.idColumnName+"=?", 
				new String[]{ String.valueOf(id) },
				null,
				null,
				null);		
		if (cursor.getCount() != 1)
			return null;
		cursor.moveToFirst();
		T Obj = cursorToObject(cursor);
		cursor.close();
		return Obj;
	}
	
	public List<T> listAll(){		
		return list(null, null, null);
	}
	
	private List<T> list(String whereQuery, String[] whereArgs, String orderBy){
		if (db == null || !db.isOpen())
			openReadableDatabase();
		Cursor cursor = db.query(tabDef.getTableName(),
				null, 
				whereQuery, 
				whereArgs, 
				null, 
				null,
				orderBy);
		return cursorToList(cursor);
	}
	
	public List<T> searchString(Object val, String... columnNames){
		if(columnNames.length < 1)
			return null;
		String query = buildSearchQuery(columnNames);
		String[] params = new String[columnNames.length];
		for(int i=0; i<params.length; i++)
			params[i] = "%"+val+"%";
		return list(query, 
				params,
				null);
	}
	
	private String buildSearchQuery(String... columns){
		if(columns.length < 1)
			return null;
		StringBuilder query = new StringBuilder(columns[0] + " LIKE ? ");
		for(int i=1; i< columns.length; i++)
			query.append("OR "+columns[i]+" LIKE ? ");	
		return query.toString();
	}
	
	private List<T> cursorToList(Cursor cursor){
		cursor.moveToFirst();
		List<T> results = new ArrayList<T>();
		while(!cursor.isAfterLast()){
			results.add(cursorToObject(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return results;	
	}	
	
	private void openWriteableDatabase() {
		db = dbHelper.getWritableDatabase();
	}

	private void openReadableDatabase() {
		db = dbHelper.getWritableDatabase();
	}
	
	public T add(T obj) {
		ContentValues values = ObjectToContentValues(obj);
		if (db == null || !db.isOpen() || db.isReadOnly())
			openWriteableDatabase();
		long id = db.insert(tabDef.getTableName(), null, values);
		obj.setId(id);
		return obj;
	}
	
	protected abstract T cursorToObject(Cursor cursor);	

	protected abstract ContentValues ObjectToContentValues(T obj);

}
