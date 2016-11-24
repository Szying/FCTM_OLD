package com.aaronlife.actionbartabdrawer;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
    // Fragment容器，用來滑動切換畫面
    ViewPager viewPager;

    // Fragment橋接器
    SectionsPagerAdapter sectionsPagerAdapter;

    // 側邊選單的ListView物件
    private ListView listView;

    // ActionBar物件
    private ActionBar actionBar;

    // 原生側邊選單
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化
        actionBar = getActionBar();

        // 設定ActionBar背景顏色
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(android.R.color.holo_green_light));
        actionBar.setBackgroundDrawable(drawable);

        // 開啟ActionBar標籤（Tab）
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // 開啟ActionBar的Home按鈕
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // 關聯元件
        viewPager = (ViewPager)findViewById(R.id.pager);
        listView = (ListView)findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        // 取得螢幕解析度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 重新設定側邊欄Layout寬度，讓它出現時站滿螢幕三分之二的空間
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams)listView.getLayoutParams();
        lp.width = dm.widthPixels / 3 * 2;
        listView.setLayoutParams(lp);

        // 初始化側邊欄ListView選項
        listView.setAdapter(new DrawerItemAdapter(this));

        // 側邊選單的選項被點擊後傾聽器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // 關閉側邊選單
                drawerLayout.closeDrawers();

                // 將TAB設定為選擇的選項項目
                actionBar.setSelectedNavigationItem(position);

                // 將畫面切為選項項目的畫面
                viewPager.setCurrentItem(position);
            }
        });

        // 初始化ViewPager，將所有要透過滑動切換畫面的Fragment放到ViewPager內
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        // ViewPager滑動時，改編目前應該被選中的TAB
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                // 滑動畫面時，TAB選項位置也要跟著相對改變
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // 增加Tab到ActionBar上面
        for (int i = 0; i < sectionsPagerAdapter.getCount(); i++)
        {
            actionBar.addTab(actionBar.newTab()
                     .setText(sectionsPagerAdapter.getPageTitle(i))
                     .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        // ActionBar的Home按鈕被點擊了
        if(id == android.R.id.home)
        {
            if(drawerLayout.isDrawerOpen(listView))
            {
                drawerLayout.closeDrawers();
            }
            else
            {
                drawerLayout.openDrawer(listView);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
    {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
