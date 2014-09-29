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
public class ContactsListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] names;
    private final String[] organization;

    public ContactsListAdapter(Context context, String[] names, String[] organization) {
        super(context, R.layout.contacts_list_item, names);
        this.context = context;
        this.names = names;
        this.organization = organization;
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
        if (names[position].equals("Siwek") || names[position].equals("Siwek2")) {
            StatusImageView.setImageResource(R.drawable.ic_action_stop);
        } else {
            StatusImageView.setImageResource(R.drawable.ic_action_flash_on);
        }
        imageView.setImageResource(R.drawable.ic_action_person);
        textView.setText(names[position]);
        textView2.setText(organization[position]);

        return rowView;
    }
}
