package komunikator.contacts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.komunikator.R;

import java.util.List;

/**
 * @author Rafał Zawadzki
 */
public class ContactsListAdapter extends ArrayAdapter<Long> {
    private final Context context;
    private String[] names;
    private String[] organization;
    private Long[] ids;

    public ContactsListAdapter(Context context, Long[] ids, String[] names, String[] organization) {
        super(context, R.layout.contacts_list_item, ids);
        this.context = context;
        this.names = names;
        this.organization = organization;
        this.ids = ids;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contacts_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.contacts_icon);
        ImageView StatusImageView = (ImageView) rowView.findViewById(R.id.contacts_status_icon);

        //TODO status onlline or offline icon
        if (names[position].equals("Michal Siwek")) {
            StatusImageView.setImageResource(R.drawable.ic_action_stop);
        } else {
            StatusImageView.setImageResource(R.drawable.ic_action_flash_on);
        }
        imageView.setImageResource(R.drawable.ic_action_person);
        textView.setText(names[position]);
        textView2.setText(organization[position]);

        return rowView;
    }

    public String[] getOrganization() {
        return organization;
    }

    public void setOrganization(String[] organization) {
        this.organization = organization;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
}
