package komunikator.contacts;

import android.app.Activity;
import android.os.Bundle;

import com.example.komunikator.R;

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
    }
}