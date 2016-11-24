package com.aaronlife.actionbartabdrawer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

// 側邊欄位的選項設定
public class DrawerItemAdapter extends BaseAdapter
{
    private Activity context;

    public DrawerItemAdapter(Activity context)
    {
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return 6;
    }

    @Override
    public String getItem(int position)
    {
        return "Item" + position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = context.getLayoutInflater().inflate(R.layout.item_list, parent, false);
        }

        // 根據選項位置設定選項文字
        ((TextView)convertView.findViewById(R.id.drawerItem)).setText("Select Fragment " + (position + 1));

        return convertView;
    }
}
