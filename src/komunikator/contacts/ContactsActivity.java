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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komunikator.R;

import java.util.ArrayList;
import java.util.List;

import komunikator.utils.DatabaseHelper;

/**
 * @author Rafał Zawadzki
 */
public class ContactsActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private List<Contact> searchContactList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        drawerListViewItems = getResources().getStringArray(R.array.items);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
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

        final ListView contactsList = (ListView) findViewById(R.id.listView);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsActivity.this, ContactDetailsActivity.class);
                //TODO define constant in main activity
                intent.putExtra("ContactDetail", position);
                startActivity(intent);
            }
        });

        // Create a progress bar to display while the list loads
        //todo nie sprawdzałem czy się pojawia bo nie mam listy, która się tak dlugo wczytuje, sleepem się później pobawię ;)
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,
                AbsListView.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        contactsList.setEmptyView(progressBar);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("QUERY:" + query); //TODO delete
            //  searchContactList = baseManager.searchContacts(query, "name");
        }

        DatabaseHelper baseManager = new DatabaseHelper(this);
        Contact tmpCont = new Contact();
        tmpCont.setOrganization("PW");
        tmpCont.setAvailability("1");
        ContactsDAO con = new ContactsDAO(this);
        con.add(tmpCont);
        List<Contact> listToShow;
        listToShow = con.listAll();

        String[] org = new String[1];
        for (Contact n : listToShow) {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add(n.getOrganization());
            tmp.toArray(org);
        }
        String[] second = new String[1];
        ContactsListAdapter adapter = new ContactsListAdapter(this, org, second);
        contactsList.setAdapter(adapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_action_bar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(ContactsActivity.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(drawerListView);
        }
    }

}