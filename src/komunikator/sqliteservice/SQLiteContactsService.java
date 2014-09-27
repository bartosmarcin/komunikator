package komunikator.sqliteservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import komunikator.Contact;

/**
 * @author Rafał Zawadzki
 */
public class SQLiteContactsService extends SQLiteOpenHelper {

    SQLiteContactsService(Context context) {
        super(context, "Contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacts(" +
                "ID integer primary key autoincrement," +
                "name text," +
                "orgnization text," +
                "email text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("name", contact.getName());
        val.put("organization", contact.getOrganization());
        val.put("email", contact.getEmail());
        try {
            db.insertOrThrow("contacts", null, val); //TODO catch when not insert
        } catch (Exception e) {
            System.out.println("Can't add new contacts");
        }
    }

    public Cursor getContacts() {
        String[] cols = {"ID", "name", "organization", "email"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor q = db.query("contacts", cols, null, null, null, null, null);
        return q;
    }

    public void deleteContact(Integer ID) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {"" + ID};
        db.delete("contacts", "ID=?", args);
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("name", contact.getName());
        val.put("organization", contact.getOrganization());
        val.put("email", contact.getEmail());
        String[] args = {"" + contact.getID()};
        db.update("contacts", val, "ID=?", args);
        //TODO update only changed values not all?
    }

}
