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
		String query = "select * from " + tabDef.getTableName() + " where "
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
	
	public List<T> listAll(){
		if (db == null || !db.isOpen())
			openReadableDatabase();
		String query = "Select * from "+tabDef.getTableName();
		Cursor cursor = db.rawQuery(query, null);
		return cursorToList(cursor);
	}
	
	public List<T> listLike(Object val, String... columnNames){
		StringBuilder query = new StringBuilder("Select * form "+tabDef.getTableName());
		if(columnNames.length < 1)
			return null;
		String[] likes = new String[columnNames.length];
		query.append(columnNames[0]+" LIKE  %?% ");
		for(int i=1; i<columnNames.length-1; i++){
			query.append(" or " + columnNames[i]+ " ? ");
			likes[i]=val.toString();
		}
		String lol = query.toString();
		return null;
	}
	
	private List<T> cursorToList(Cursor cursor){
		cursor.moveToFirst();
		List<T> results = new ArrayList<T>();
		while(!cursor.isAfterLast()){
			results.add(cursorToObject(cursor));
			cursor.moveToNext();
		}
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
