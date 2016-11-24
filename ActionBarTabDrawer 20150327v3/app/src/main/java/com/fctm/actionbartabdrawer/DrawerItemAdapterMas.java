package com.fctm.actionbartabdrawer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


// 側邊欄位的選項設定
public class DrawerItemAdapterMas extends BaseAdapter
{
    private Activity context;
    String[] menumas;
    public DrawerItemAdapterMas(Activity context)
    {
        this.context = context;
    }

    @Override
    public int getCount()
    {
        menumas = context.getResources().getStringArray(R.array.menumas);

        return menumas.length;
    }

    @Override
    public String getItem(int position)
    {
        menumas = context.getResources().getStringArray(R.array.menumas);

        return menumas[position];
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
        ((TextView)convertView.findViewById(R.id.drawerItem)).setText(getItem(position));

        return convertView;
    }
}
