package com.example.exercise3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MonHocAdapter extends BaseAdapter {

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayoutl() {
        return layout;
    }

    public void setLayoutl(int layoutl) {
        this.layout = layoutl;
    }

    public List<MonHoc> getMonHocList() {
        return monHocList;
    }

    public void setMonHocList(List<MonHoc> monHocList) {
        this.monHocList = monHocList;
    }

    private Context context;
    private int layout;
    private List<MonHoc> monHocList;

    public MonHocAdapter(){}

    public MonHocAdapter(Context context, int layout, List<MonHoc> monhocList)
    {
        this.context = context;
        this.layout = layout;
        this.monHocList = monhocList;
    }

    @Override
    public int getCount(){
        return monHocList == null ? 0 : monHocList.size();
    }

    @Override
    public Object getItem(int position) {
        return (monHocList == null || position < 0 || position >= monHocList.size()) ? null : monHocList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView tv_name;
        TextView tv_desc;
        ImageView iv_pic;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            holder.iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MonHoc monHoc = monHocList.get(i);
        if (monHoc != null) {
            holder.tv_name.setText(monHoc.getName());
            holder.tv_desc.setText(monHoc.getDesc());
            holder.iv_pic.setImageResource(monHoc.getPic());
        }

        return view;
    }


}
