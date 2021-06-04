package com.vientamthuong.eatsimple.admin.danhMuc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vientamthuong.eatsimple.R;

import java.util.List;

public class SelectAdapter extends ArrayAdapter<String> {

    private List<String> types;
    private Context context;
    private int resource;


    public SelectAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.types = objects;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        TextView mainTextView = convertView.findViewById(R.id.mainTextView);
        mainTextView.setText(types.get(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_view_holder_item_select, parent, false);
        }
        TextView mainTextView = convertView.findViewById(R.id.mainTextView);
        mainTextView.setText(types.get(position));
        return convertView;
    }
}
