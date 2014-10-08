package komunikator.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.komunikator.R;

import java.util.ArrayList;
import java.util.HashMap;

import komunikator.profile.Profile;
import komunikator.profile.ProfileDAO;

/**
 * @author Rafał Zawadzki
 */
public class ContactDetailsActivity extends Activity {
    private Long itemID;
    public final static Integer EDIT_CONTACT_CODE = 2;
    public final static String CONTACT_EDITION = "com.komunikator.contacts.ContactDetailsActivity.EDITION";
    public final static String CONTACT_ID_EDITION = "com.komunikator.contacts.ContactDetailsActivity.ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_activity);

        //get back to parent activity (defined in manifest)
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ContactsActivity.EXTRA_MESSAGE);
        itemID = Long.parseLong(message);
        setContactToView();
    }

    private HashMap<String, String> getItemInfo(Long po) {
        HashMap<String, String> info = new HashMap<>();
        ProfileDAO pDAO = new ProfileDAO(this);
        Profile tProf;
        tProf = pDAO.getById(po);
        info.put("first name", tProf.getFirstName());
        info.put("last name", tProf.getLastName());
        info.put("nick name", tProf.getNickName());
        info.put("email", tProf.getEmail());

        ContactsDAO cDAO = new ContactsDAO(this);
        Contact tCon;
        tCon = cDAO.getById(po);
        info.put("organization", tCon.getOrganization());

        return info;
    }

    private void setContactToView() {
        HashMap<String, String> itemInfo;
        itemInfo = getItemInfo(itemID);
        TextView firstLine = (TextView) findViewById(R.id.contact_first_line);
        if (itemInfo.get("first name") == null || itemInfo.get("last name") == null) {
            firstLine.setText(itemInfo.get("nick name"));
        } else {
            firstLine.setText(itemInfo.get("first name") + " " + itemInfo.get("last name"));
        }
        TextView secondLine = (TextView) findViewById(R.id.contact_second_line);
        secondLine.setText("Group: " + itemInfo.get("organization")); //TODO ability to display many groups

        TextView emailLine = (TextView) findViewById(R.id.contact_email);
        emailLine.setText("Email: " + itemInfo.get("email"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_contact_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_contact:
                Intent intent = new Intent(ContactDetailsActivity.this, ContactAddActivity.class);
                intent.putExtra(CONTACT_EDITION, true);
                intent.putExtra(CONTACT_ID_EDITION, itemID);
                startActivityForResult(intent, EDIT_CONTACT_CODE);
                setResult(RESULT_OK, intent); //TODO improvement - set only if change really was
                return true;
            case R.id.delete_contact:
                deleteContact();
                return true;
            default:
                return false;

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //what to do if contact is added (message from different activiti)
        if (requestCode == EDIT_CONTACT_CODE) {
            if (resultCode == RESULT_OK) {
                setContactToView();
            }
        }
    }

    private void deleteContact() {
//        ContactsDAO cDAO = new ContactsDAO(this);
//        cDAO.delete(cDAO.getById(itemID));
//        ProfileDAO pDAO = new ProfileDAO(this);
//        pDAO.delete(pDAO.getById(itemID));
        finish();
    }

}