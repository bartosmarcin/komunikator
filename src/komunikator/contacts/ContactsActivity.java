package komunikator.contacts;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.method.CharacterPickerDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komunikator.R;

import java.util.List;

import komunikator.profile.Profile;
import komunikator.profile.ProfileDAO;

/**
 * @author Rafał Zawadzki
 */
public class ContactsActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final Integer ADD_CONTACT_CODE = 1;
    public static final String EXTRA_MESSAGE = "com.komunikator.ContactsActivity.POSITION_MESSAGE";
    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ContactsListAdapter adapter;
    private ListView contactsList;
    private List<Profile> pSearched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        drawerListViewItems = getResources().getStringArray(R.array.items);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_listview_item, drawerListViewItems));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_launcher,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        contactsList = (ListView) findViewById(R.id.listView);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsActivity.this, ContactDetailsActivity.class);
                Object ContactSQLiteID = contactsList.getItemAtPosition(position);
                intent.putExtra(EXTRA_MESSAGE, ContactSQLiteID.toString());
                startActivityForResult(intent, ContactDetailsActivity.EDIT_CONTACT_CODE);
            }
        });

//        Profile tmpProf = new Profile();
//        tmpProf.setFirstName("Marcin");
//        tmpProf.setLastName("Bartos");
//
//        Profile tmpProf1 = new Profile();
//        tmpProf1.setFirstName("Laskowski");
//        tmpProf1.setLastName("Marcin");
//
//        Profile tmpProf2 = new Profile();
//        tmpProf2.setFirstName("Michal");
//        tmpProf2.setLastName("Siwek");
//        tmpProf2.setEmail("siwiusz@op.pl");
//
//        Contact tmpCont = new Contact();
//        tmpCont.setOrganization("PW");
//        tmpCont.setAvailability("1");
//
//        Contact tmpCont1 = new Contact();
//        tmpCont1.setOrganization("PW");
//        tmpCont1.setAvailability("1");
//
//        Contact tmpCont2 = new Contact();
//        tmpCont2.setOrganization("PW");
//        tmpCont2.setAvailability("1");

//        ContactsDAO con = new ContactsDAO(this);
//        con.add(tmpCont);
//        con.add(tmpCont1);
//        con.add(tmpCont2);
//
//        ProfileDAO pro = new ProfileDAO(this);
//        pro.add(tmpProf);
//        pro.add(tmpProf1);
//        pro.add(tmpProf2);

        Intent intent = getIntent();
        if (!handleSearchIntent(intent)) {
            listAllContacts();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //what to do if contact is added (message from different activiti)
        if (requestCode == ADD_CONTACT_CODE || requestCode == ContactDetailsActivity.EDIT_CONTACT_CODE) {
            if (resultCode == RESULT_OK) {
                listAllContacts();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleSearchIntent(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private Boolean handleSearchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            ProfileDAO pDAO = new ProfileDAO(this);
            pSearched = pDAO.searchString(query, "first_name", "last_name");
            String[] first_line = new String[pSearched.size()];
            Long[] ids = new Long[pSearched.size()];

            for (int i = 0; i < pSearched.size(); i++) {
                first_line[i] = pSearched.get(i).getFirstName() + " " + pSearched.get(i).getLastName();
                ids[i] = pSearched.get(i).getId();
            }
            ContactsDAO cDAO = new ContactsDAO(this);
            String[] second_line = new String[pSearched.size()];
            for (int i = 0; i < pSearched.size(); i++) {
                Long sID = pSearched.get(i).getId();
                String org = cDAO.getById(sID).getOrganization();
                second_line[i] = org;
            }
            adapter = new ContactsListAdapter(this, ids, first_line, second_line);
            contactsList.setAdapter(adapter);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_compose:
                return true;
            case R.id.action_add_contact_list:
                Intent intent = new Intent(ContactsActivity.this, ContactAddActivity.class);
                startActivityForResult(intent, ADD_CONTACT_CODE);
                return true;
            case R.id.action_delete_contacts_list:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_action_bar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setIconified(true);
                searchView.clearFocus();
                searchView.setQuery(query, false);
                Intent intent = getIntent();
                if (!handleSearchIntent(intent)) {
                    listAllContacts();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    listAllContacts();
                    return true;
                }
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void listAllContacts() {
        List<Profile> ProflistToShow;
        ProfileDAO pro = new ProfileDAO(this);
        ProflistToShow = pro.listAll();
        String[] proList = new String[ProflistToShow.size()];
        for (int i = 0; i < ProflistToShow.size(); i++) {
            proList[i] = ProflistToShow.get(i).getFirstName() + " " + ProflistToShow.get(i).getLastName();
        }

        List<Contact> listToShow;
        ContactsDAO con = new ContactsDAO(this);
        listToShow = con.listAll();
        String[] org = new String[listToShow.size()]; //TODO add status of availability to adapter
        Long[] ids = new Long[listToShow.size()];
        for (int i = 0; i < listToShow.size(); i++) {
            org[i] = listToShow.get(i).getOrganization();
            ids[i] = listToShow.get(i).getId();
        }
        adapter = new ContactsListAdapter(this, ids, proList, org);
        contactsList.setAdapter(adapter);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(ContactsActivity.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(drawerListView);
        }
    }

}