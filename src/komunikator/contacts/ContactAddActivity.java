package komunikator.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
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
public class ContactAddActivity extends Activity {
    HashMap<String, String> formPools;
    Boolean editionMode = false;
    Long editContactID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add_activity);

        Intent intent = getIntent();
        editionMode = intent.getBooleanExtra(ContactDetailsActivity.CONTACT_EDITION, false);
        if (editionMode) {
            establishValues();
        }
        //get back to parent activity (defined in manifest)
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_contact_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_contact) {
            formPools = new HashMap<>();
            EditText nameSurname = (EditText) findViewById(R.id.edit_name_surname);
            formPools.put("names", nameSurname.getText().toString());
            EditText group = (EditText) findViewById(R.id.edit_group);
            formPools.put("group", group.getText().toString());
            EditText nickname = (EditText) findViewById(R.id.edit_nickname);
            formPools.put("nickname", nickname.getText().toString());
            EditText email = (EditText) findViewById(R.id.edit_email);
            formPools.put("email", email.getText().toString());
            Intent intent = getIntent();
            if (editionMode) {
                updateContact();
                setResult(RESULT_OK, intent); // for ContactDetailsActivity
            } else {
                addToBase();
                intent.putExtra(String.valueOf(ContactsActivity.ADD_CONTACT_CODE), "contactAdded");
                setResult(RESULT_OK, intent); // for ContactsActivity
            }
            finish();
            return true;
        }
        return false;
    }

    private void establishValues() {
        Intent intent = getIntent();
        editContactID = intent.getLongExtra(ContactDetailsActivity.CONTACT_ID_EDITION, new Long(-1));
        if (!editContactID.equals(-1)) {
            ContactsDAO cDAO = new ContactsDAO(this);
            Contact tmpC = cDAO.getById(editContactID);

            ProfileDAO pDAO = new ProfileDAO(this);
            Profile tmpP = pDAO.getById(editContactID);

            EditText nameSurname = (EditText) findViewById(R.id.edit_name_surname);
            nameSurname.setText(tmpP.getFirstName() + " " + tmpP.getLastName());

            EditText group = (EditText) findViewById(R.id.edit_group);
            group.setText(tmpC.getOrganization());

            EditText nickname = (EditText) findViewById(R.id.edit_nickname);
            nickname.setText(tmpP.getNickName());

            EditText email = (EditText) findViewById(R.id.edit_email);
            email.setText(tmpP.getEmail());

        }
    }

    private void updateContact() {
        //ContactsDAO cDAO = new ContactsDAO(this);
        //Contact c = cDAO.getById(editContactID);
        //Contact tmpC = cDAO.update(c);

        ProfileDAO pDAO = new ProfileDAO(this);
        Profile tmpP = pDAO.getById(editContactID);
        String[] names = formPools.get("names").split(" ");
        tmpP.setFirstName(names[0]);
        tmpP.setLastName(names[1]);
        pDAO.update(tmpP);
    }

    private void addToBase() {
        Profile tmpProf = new Profile();
        String[] names = formPools.get("names").split(" ");
        tmpProf.setFirstName(names[0]);
        tmpProf.setLastName(names[1]);
        tmpProf.setNickName(formPools.get("nickname"));
        tmpProf.setEmail(formPools.get("email"));

        Contact tmpCont = new Contact();
        tmpCont.setOrganization(formPools.get("group"));

        ContactsDAO con = new ContactsDAO(this);
        con.add(tmpCont);
        ProfileDAO pro = new ProfileDAO(this);
        pro.add(tmpProf);
    }
}