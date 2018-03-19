package com.app.dueday.maya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dueday.maya.R;
import com.app.dueday.maya.module.EventListViewItem;

import java.util.ArrayList;

/**
 * Created by fengxinlin on 3/18/18.
 */

public class EventListAdapter extends ArrayAdapter<EventListViewItem>{

    Context context;
    ArrayList<EventListViewItem> data = null;

    public EventListAdapter(Context context, ArrayList<EventListViewItem> data) {
        super(context, R.layout.list_event_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        LocationHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_event_item, parent, false);

            holder = new LocationHolder();
            holder.imgIcon = (ImageView)convertView.findViewById(R.id.eventImage);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.eventTitle);
            row = convertView;
            convertView.setTag(holder);
        } else {
            holder = (LocationHolder)convertView.getTag();
            row = convertView;
        }

        EventListViewItem event = data.get(position);
        holder.txtTitle.setText(event.title);
        holder.imgIcon.setImageResource(event.image);

        return row;
    }

    static class LocationHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}