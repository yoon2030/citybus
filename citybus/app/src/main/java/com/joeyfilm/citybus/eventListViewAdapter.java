package com.joeyfilm.citybus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class eventListViewAdapter extends BaseAdapter {
    public ArrayList<faListViewItem> listViewItemList = new ArrayList<faListViewItem>();
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.eventlistitem, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textView1 = (TextView) convertView.findViewById(R.id.day);
        TextView textView2 = (TextView) convertView.findViewById(R.id.text);
        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        faListViewItem listViewItem = listViewItemList.get(position);

        textView1.setText(listViewItem.getstart());
        textView2.setText(listViewItem.getend());
        convertView.setBackgroundColor(Color.parseColor("#5ABAC3"));
        return convertView;
    }

    public void addItem(String text1, String text2) {
        faListViewItem item = new faListViewItem();
        item.setstart(text1);
        item.setend(text2);

        listViewItemList.add(item);
    }

    public void clearItem(){
        listViewItemList.clear();
    }
}
