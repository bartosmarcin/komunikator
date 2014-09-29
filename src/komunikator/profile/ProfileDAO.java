package komunikator.profile;


import komunikator.utils.BasicDAO;
import komunikator.utils.ColumnType;
import komunikator.utils.TableDefinition;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ProfileDAO extends BasicDAO<Profile> {
	private static final String tableName = "PROFILE";
	private static final String firstNameColumn = "first_name";
	private static final String lastNameColumn = "last_name";
	private static final String nickNameColumn = "nick_name";
	private static final String emailColumn = "email";
	
	public ProfileDAO(Context c){
		super(c);
	}

	public TableDefinition getTableDefinition() {
		tabDef = new TableDefinition(tableName);
		tabDef.addColumn(firstNameColumn, ColumnType.TEXT);
		tabDef.addColumn(lastNameColumn, ColumnType.TEXT);
		tabDef.addColumn(nickNameColumn, ColumnType.TEXT);
		tabDef.addColumn(emailColumn, ColumnType.TEXT);
		return tabDef;
	}

	@Override
	protected Profile cursorToObject(Cursor cursor) {
		Profile profile = new Profile();
		profile.setId(cursor.getLong(tabDef
				.getIndex(TableDefinition.idColumnName)));
		profile.setFirstName(cursor.getString(tabDef.getIndex(firstNameColumn)));
		profile.setLastName(cursor.getString(tabDef.getIndex(lastNameColumn)));
		profile.setNickName(cursor.getString(tabDef.getIndex(nickNameColumn)));
		profile.setEmail(cursor.getString(tabDef.getIndex(emailColumn)));
		return profile;
	}

	@Override
	protected ContentValues ObjectToContentValues(Profile prof) {
		ContentValues values = new ContentValues();
		values.put(firstNameColumn, prof.getFirstName());
		values.put(lastNameColumn, prof.getLastName());
		values.put(nickNameColumn, prof.getNickName());
		values.put(emailColumn, prof.getEmail());
		return values;
	}

}
