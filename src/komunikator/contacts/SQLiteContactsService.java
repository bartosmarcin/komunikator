package komunikator.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Rafał Zawadzki
 */
public class SQLiteContactsService extends SQLiteOpenHelper {

    public SQLiteContactsService(Context context) {
        super(context, "Contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO establish limits (length) for pools
        //TODO establish max number of contacts in database
        db.execSQL("create table if not exists contacts (" +
                "id integer primary key autoincrement, " +
                "name text, " +
                "orgnization text, " +
                "email text );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table contacts;");
//        db.execSQL("create table contacts (" +
//                "id integer primary key autoincrement, " +
//                "name text, " +
//                "orgnization text, " +
//                "email text );");
    }

//    public void addContact(Contact contact) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues val = new ContentValues();
//        val.put("name", contact.getName());
//        val.put("organization", contact.getOrganization());
//        val.put("email", contact.getEmail());
//        //TODO add image but don't put it into database
//        try {
//            db.insertOrThrow("contacts", null, val); //TODO catch when not insert
//            db.close();
//        } catch (Exception e) {
//            System.out.println("Can't add new contacts");
//        }
//    }

//    public List<Contact> getContacts() {
//        List<Contact> allContactsList = new LinkedList<Contact>();
//        String[] cols = {"name", "email"};
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor q = db.query("contacts", cols, null, null, null, null, null);
//        while (q.moveToNext()) {
//            Contact con = new Contact();
//            //con.setID(q.getInt(0));
//            con.setName(q.getString(1));
//          //  con.setOrganization(q.getString(2));
//            con.setEmail(q.getString(2));
//            allContactsList.add(con);
//        }
//        db.close();
//        return allContactsList;
//    }

    public void deleteContact(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {"" + id};
        db.delete("contacts", "id=?", args);
    }

    public void deleteContacts(Integer[] id) {
        for (int i = 0; i < id.length; i++) {
            deleteContact(id[i]);
        }
    }

    public List<Contact> searchContacts(String query, String criterium) {
        // criterium: name, email, id organization
        //String[] args = {criterium, query};
        String[] args = new String[]{};
        List<Contact> searchedContactsList = new LinkedList<Contact>();
        String[] cols = {"id", "name", "organization", "email"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor q;
        if (criterium.equals("id")) {
            q = db.query("contacts", cols, "? MATCH '?'", args, null, null, null);
        } else {
            q = db.query("contacts", cols, "name LIKE '%Ja%'", args, null, null, null);
        }
        while (q.moveToNext()) {
            Contact con = new Contact();
//            con.setID(q.getInt(0));
//            con.setName(q.getString(1));
            con.setOrganization(q.getString(2));
//            con.setEmail(q.getString(3));
            searchedContactsList.add(con);
        }
        return searchedContactsList;
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
//        val.put("name", contact.getName());
        val.put("organization", contact.getOrganization());
//        val.put("email", contact.getEmail());
//        String[] args = {"" + contact.getID()};
//        db.update("contacts", val, "id=?", args);
        //TODO update only changed values not all?
    }

}