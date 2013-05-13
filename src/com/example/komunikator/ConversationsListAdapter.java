package com.example.komunikator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConversationsListAdapter extends BaseAdapter{

	Context c;
	
	public ConversationsListAdapter(Context context){
		c=context;
	}
	
	@Override
	public int getCount() {
		return ConversationsList.getSize();
	}

	@Override
	public Object getItem(int arg0) {
		return ConversationsList.getAt(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	     View v = convertView;
         if (v == null)
         {
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
         }
 
           ImageView image = (ImageView) v.findViewById(R.id.icon);
           TextView fromView = (TextView)v.findViewById(R.id.From);
 
           Conversation conv = ConversationsList.getAt(position);
           image.setImageResource(R.drawable.ic_launcher);
           fromView.setText( conv.getRecipient() );                                      
                        
        return v;
	}
	
	

}
