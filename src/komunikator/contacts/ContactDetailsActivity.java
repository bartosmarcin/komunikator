package komunikator.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_activity);

        //get back to parent activity (defined in manifest)
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ContactsActivity.EXTRA_MESSAGE);
        HashMap<String, String> itemInfo;
        itemInfo = getItemInfo(Long.parseLong(message));
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

    private HashMap<String, String> getItemInfo(Long po) {
        HashMap<String, String> info = new HashMap<>();
        ProfileDAO pDAO = new ProfileDAO(this);
        Profile tProf;
        tProf = pDAO.getById(po);
        info.put("first name", tProf.getFirstName()); //0
        info.put("last name", tProf.getLastName());
        info.put("nick name", tProf.getNickName());
        info.put("email", tProf.getEmail());

        ContactsDAO cDAO = new ContactsDAO(this);
        Contact tCon;
        tCon = cDAO.getById(po);
        info.put("organization", tCon.getOrganization());

        return info;
    }

}