package komunikator.profile;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import komunikator.utils.BasicDAO;
import komunikator.utils.ColumnType;
import komunikator.utils.TableDefinition;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

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
    
    public void saveProfileImage(Bitmap bitmap, long profileId){
    	String filename = getProfileFilename(profileId);
    	try {
    		FileOutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
	    	bitmap.compress(CompressFormat.PNG, 40, out);
	    	out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
    }  
    
    public Bitmap getProfileImage(Profile profile){
    	return getProfileImage(profile.getId());
    }
    
    public Bitmap getProfileImage(long profileId){
    	String filename = getProfileFilename(profileId);
    	File path = context.getFileStreamPath(filename);
    	return BitmapFactory.decodeFile(path.toString());
    }
    
    private String getProfileFilename(long profileId){
    	return String.valueOf(profileId);
    }
}
