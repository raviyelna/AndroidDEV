package com.example.exercise3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<MonHoc> {

    private final LayoutInflater inflater;
    private final int resource;

    public GridViewAdapter(Context context, int resource, List<MonHoc> items) {
        super(context, resource, items);
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
    }

    private static class ViewHolder {
        ImageView ivPic;
        TextView tvName;
        TextView tvDesc;

        ViewHolder(View view) {
            ivPic = view.findViewById(R.id.iv_pic_grid);
            tvName = view.findViewById(R.id.tv_name_grid);
            tvDesc = view.findViewById(R.id.tv_desc_grid);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        MonHoc item = getItem(position);
        if (item != null) {
            holder.tvName.setText(item.getName());
            holder.tvDesc.setText(item.getDesc());
            holder.ivPic.setImageResource(item.getPic());
        } else {
            holder.tvName.setText("");
            holder.tvDesc.setText("");
            holder.ivPic.setImageResource(R.drawable.activity_transparent);
        }

        return view;
    }
}