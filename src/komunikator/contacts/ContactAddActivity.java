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
    HashMap<String,String> formPools;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add_activity);

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
            addToBase();
            Intent intent = getIntent();
            intent.putExtra(String.valueOf(ContactsActivity.ADD_CONTACT_CODE), "contactAdded");
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return false;
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