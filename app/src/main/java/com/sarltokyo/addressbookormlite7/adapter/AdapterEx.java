package com.sarltokyo.addressbookormlite7.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sarltokyo.addressbookormlite7.app.R;
import com.sarltokyo.addressbookormlite7.entity.Person;

import java.util.List;

/**
 * Created by osabe on 15/07/22.
 */
public class AdapterEx extends ArrayAdapter<Person> {
    public AdapterEx(Context context, List<Person> persons) {
        super(context, R.layout.layout_main_item, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_main_item, null);
        }
        convertView.setTag(position);
        convertView.findViewById(R.id.nameTv).setTag(position);
        TextView nameTv = (TextView)convertView.findViewById(R.id.nameTv);
        nameTv.setText(person.getName());
        return convertView;
    }
}
