package com.example.komunikator;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConversationAdapter extends BaseAdapter {
   
    private ArrayList<Message> _data;
    Context _c;
    
    ConversationAdapter (ArrayList<Message> data, Context c){
        _data = data;
        _c = c;
    }
   
    public int getCount() {
        // TODO Auto-generated method stub
        return _data.size();
    }
    
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return _data.get(position);
    }
 
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
         View v = convertView;
         if (v == null)
         {
            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
         }
 
           ImageView image = (ImageView) v.findViewById(R.id.icon);
           TextView fromView = (TextView)v.findViewById(R.id.From);
           TextView descView = (TextView)v.findViewById(R.id.description);
           TextView timeView = (TextView)v.findViewById(R.id.time);
 
           Message msg = _data.get(position);
           image.setImageResource(R.drawable.ic_launcher);
           fromView.setText(msg.getSenderName());
           descView.setText(msg.getMessageContent());
           timeView.setText((CharSequence) msg.getDateSent());                             
                        
        return v;
}
}
