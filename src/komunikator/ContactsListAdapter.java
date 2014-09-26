package komunikator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.komunikator.R;

/**
 * @author Rafał Zawadzki
 */
public class ContactsListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ContactsListAdapter(Context context, String[] values) {
        super(context, R.layout.contacts_list_item, values);
        this.context = context;
        this.values = values;
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
        if (values[position].equals("Siwek") || values[position].equals("Siwek2")) {
            StatusImageView.setImageResource(R.drawable.ic_action_stop);
        } else {
            StatusImageView.setImageResource(R.drawable.ic_action_flash_on);
        }
        imageView.setImageResource(R.drawable.ic_action_person);
        textView.setText(values[position]);
        textView2.setText("organizacja/grupa");

        return rowView;
    }
}
