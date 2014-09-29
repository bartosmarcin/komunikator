package komunikator.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import komunikator.utils.BasicDAO;
import komunikator.utils.ColumnType;
import komunikator.utils.DatabaseObject;
import komunikator.utils.TableDefinition;

/**
 * @author Rafał Zawadzki
 */
public class ContactsDAO extends BasicDAO<Contact> {
    private static final String tableName = "CONTACTS";
    private static final String organizationColumn = "organization";
    private static final String availabilityColumn = "availability";

    public ContactsDAO(Context context) {
        super(context);
    }

    @Override
    protected TableDefinition getTableDefinition() {
        tabDef = new TableDefinition(tableName);
        tabDef.addColumn(organizationColumn, ColumnType.TEXT);
        tabDef.addColumn(availabilityColumn, ColumnType.INTEGER);
        return tabDef;
    }

    @Override
    protected Contact cursorToObject(Cursor cursor) {
        Contact profile = new Contact();
        profile.setId(cursor.getLong(tabDef
                .getIndex(TableDefinition.idColumnName)));
        profile.setOrganization(cursor.getString(tabDef.getIndex(organizationColumn)));
        profile.setAvailability(cursor.getString(tabDef.getIndex(availabilityColumn)));
        return profile;
    }

    @Override
    protected ContentValues ObjectToContentValues(Contact obj) {
        ContentValues values = new ContentValues();
        values.put(organizationColumn, obj.getOrganization());
        values.put(availabilityColumn, obj.getAvailability());
        return values;
    }

}
