package komunikator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komunikator.R;

/**
 * @author Rafał Zawadzki
 */
public class ContactsActivity extends Activity {
    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.contacts);

            // get list items from strings.xml
            drawerListViewItems = getResources().getStringArray(R.array.items);

            // get ListView defined in contacts.xml
            drawerListView = (ListView) findViewById(R.id.left_drawer);

            // Set the adapter for the list view
            drawerListView.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.drawer_listview_item, drawerListViewItems));

//            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(ContactsActivity.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(drawerListView);
        }
    }
}
