package com.joeyfilm.citybus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class conListViewAdapter extends BaseAdapter {
    public ArrayList<conListViewItem> listViewItemList = new ArrayList<conListViewItem>();
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
            convertView = inflater.inflate(R.layout.conlist, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textView1 = (TextView) convertView.findViewById(R.id.day);
        TextView textView2 = (TextView) convertView.findViewById(R.id.start);
        TextView textView3 = (TextView) convertView.findViewById(R.id.end);
        TextView textView4 = (TextView) convertView.findViewById(R.id.money);
        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        conListViewItem listViewItem = listViewItemList.get(position);

        textView1.setText(listViewItem.getday());
        textView2.setText(listViewItem.getstart());
        textView3.setText(listViewItem.getend());
        textView4.setText(listViewItem.getmoney());
        convertView.setBackgroundColor(Color.parseColor("#AFA6A6"));
        return convertView;
    }

    public void addItem(String text1, String text2,String text3,String text4) {
        conListViewItem item = new conListViewItem();
        item.setstart(text1);
        item.setend(text2);
        item.setday(text3);
        item.setmoney(text4);

        listViewItemList.add(item);
    }

    public void clearItem(){
        listViewItemList.clear();
    }
}
