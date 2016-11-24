package com.fctm.actionbartabdrawer;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener,
        LabAddDialogFrag.OnUiUpdate,MasAddDialogFrag.OnUiUpdateMas
{

    //Liszy
    public static int userType;
    // Fragment容器，用來滑動切換畫面
    ViewPager viewPager;

    // Fragment橋接器
    SectionsPagerAdapterMas sectionsPagerAdapterMas;
    SectionsPagerAdapterLab sectionsPagerAdapterLab;

    //Ann
    String memberid="001";
    MasDbHelper masDB = null;
    LabDBHelper LabDB = null;
    int DataAnysMode_MOD_SCHDUL=0;  //0:Schdul , 1:Health, GPS
    // 定義Callback介面
    public interface OnUiUpdateMas extends IHttpCompleted {
        public void notifyUpdateMas();
    }

    public String encode(String value) throws Exception{
        return URLEncoder.encode(value, "utf-8");
    }

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


            //Ann
        MasAddDialogFrag.OnUiUpdateMas callback;
       // callback = (OnUiUpdateMas) ateMasMainActivity.OnUiUpd;
        masDB = new MasDbHelper(this);
        LabDB = new LabDBHelper(this);
        // 初始化
        actionBar = getActionBar();

            // 設定ActionBar是導覽列的模式
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            // 將位置留給導覽列
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setHomeButtonEnabled(true);  // 讓ActionBar的左邊圖示可以點擊，點擊時onOptionsItemSelected()會收到ID=android.R.id.home的MenuItem。
            actionBar.setDisplayShowHomeEnabled(true); // 顯示左邊的圖示，setHomeButtonEnabled()必須搭配此方法設為true才能生效。

            // 設定ActionBar背景顏色
        ColorDrawable drawable = new ColorDrawable(0xffff0000);
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

        // 重新設定側邊欄Layout寬度，讓它出現時站滿螢幕１／3的空間
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams)listView.getLayoutParams();
        lp.width = dm.widthPixels /3 ;
        listView.setLayoutParams(lp);

        // 初始化側邊欄ListView選項
        if(userType==0)  {
            listView.setAdapter(new com.fctm.actionbartabdrawer.DrawerItemAdapterLab(this));
        }else{
            listView.setAdapter(new com.fctm.actionbartabdrawer.DrawerItemAdapterMas(this));
        }



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



        // login

        AlertDialog.Builder altDlgBldr = new AlertDialog.Builder(MainActivity.this);
        altDlgBldr.setTitle( getResources().getString(R.string.accoutmang));
        altDlgBldr.setMessage(getResources().getString(R.string.loginlevel));
        altDlgBldr.setIcon(android.R.drawable.ic_dialog_info);
        altDlgBldr.setCancelable(false);

            AlertDialog.Builder builder = altDlgBldr.setPositiveButton(getResources().getString(R.string.mas),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userType = UserInfo.type0;
                            sectionPagerAdapterMas();
                        }
                    });
            altDlgBldr.setNegativeButton(getResources().getString(R.string.lab),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userType = UserInfo.type1;
                        sectionPagerAdapterLab();

                    }
                });

        altDlgBldr.show();

    }

    public void sectionPagerAdapterMas() {

        String[] menumas = getResources().getStringArray(R.array.menumas);
        // 初始化ViewPager，將所有要透過滑動切換畫面的Fragment放到ViewPager內
       sectionsPagerAdapterMas = new SectionsPagerAdapterMas(getSupportFragmentManager(),menumas);
 //       sectionsPagerAdapterMas = new SectionsPagerAdapterMas(getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapterMas);

        // ViewPager滑動時，改編目前應該被選中的TAB
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 滑動畫面時，TAB選項位置也要跟著相對改變
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // 增加Tab到ActionBar上面
        for (int i = 0; i < sectionsPagerAdapterMas.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(sectionsPagerAdapterMas.getPageTitle(i))
                    .setTabListener(this));
        }
    }
    public void sectionPagerAdapterLab() {
         String[] menulab = getResources().getStringArray(R.array.menulab);
        // 初始化ViewPager，將所有要透過滑動切換畫面的Fragment放到ViewPager內
        sectionsPagerAdapterLab = new SectionsPagerAdapterLab(getSupportFragmentManager(),menulab);
    //    sectionsPagerAdapterLab = new SectionsPagerAdapterLab(getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapterLab);

        // ViewPager滑動時，改編目前應該被選中的TAB
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 滑動畫面時，TAB選項位置也要跟著相對改變
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // 增加Tab到ActionBar上面
        for (int i = 0; i < sectionsPagerAdapterLab.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(sectionsPagerAdapterLab.getPageTitle(i))
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
        else if(id == R.id.muschadd)
        {    //Ann 新增

            //更新Schdul
          //  DataAnysMode_MOD_SCHDUL=0;
            HttpAction.query("schdul", "001", "NEW", MainActivity.this);
            Toast.makeText(this, "同步資料開始...會員帳號： "+memberid, Toast.LENGTH_SHORT).show();

            //Health
           // DataAnysMode_MOD_SCHDUL=1;
           // HttpAction.query("health", "001", "NEW", this);
           // Toast.makeText(this, "Get Health "+memberid, Toast.LENGTH_SHORT).show();



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

    // 透過Activity取得第二個Fragment並呼叫該方法
    @Override
    public void notifyUpdate()
    {
        //顧主檢視表單
        if(userType==0) {
            LabHelRpt frag = (LabHelRpt) sectionsPagerAdapterLab.getItem(1);
            frag.updateListView(this, new LabDBHelper(this));
        }
        else{

        }
    }

    public void notifyUpdateMas()
    {
        //userType=0 顧主
        //顧主輸入主表單

        if(userType==0) {
            MasSchRpt frag1 = (MasSchRpt) sectionsPagerAdapterMas.getItem(0);
            frag1.updateListView(this, new MasDbHelper(this));
        }
        else {
            //外傭檢視新增工作表單
            LabNormRpt fraglabextrarpt = (LabNormRpt) sectionsPagerAdapterLab.getItem(1);
            fraglabextrarpt.updateListView(this, new MasDbHelper(this));

            //外傭檢視每日固定工作表單
            LabExtraRpt fraglabnormlrpt = (LabExtraRpt) sectionsPagerAdapterLab.getItem(2);
            fraglabnormlrpt.updateListView(this, new MasDbHelper(this));
        }


    }


    @Override
    public void doNotify(String result) {
        // 從server 抓資料成功時

        try {
           String decodReturn= URLDecoder.decode(result, "UTF-8");
            Toast.makeText(MainActivity.this, "回傳資料\n" + decodReturn, Toast.LENGTH_LONG).show();
            String[] getResult = result.split("`");
           // Toast.makeText(MainActivity.this, ""+getResult.length, Toast.LENGTH_LONG).show();
            if (getResult.length>=6) {
                masDB.add(getResult[1], getResult[2], getResult[3], getResult[4], getResult[5], getResult[6]);
                Toast.makeText(MainActivity.this, ""+"資料同步完成!!!", Toast.LENGTH_LONG).show();
               // if (callback != null) callback.notifyUpdateMas();

            }

        } catch (UnsupportedEncodingException e) {
            throw new Error(e.getMessage(), e);
        }



        //  if (DataAnysMode_MOD_SCHDUL==0 && result !=null ) {

            //schdule 寫回資料庫


            //    Toast.makeText(this, "資料同步完成！！！\n" + result, Toast.LENGTH_LONG).show();

        //
        }
       // else if(DataAnysMode_MOD_SCHDUL==1){
            //health 寫回資料庫
          //  String[] getResult = result.split("#");
         //   Toast.makeText(this, "HealthdoNotify\n" + getResult, Toast.LENGTH_LONG).show();
         //   masDB.add(getResult[0], getResult[1], getResult[2], getResult[3], getResult[4], getResult[5]);

       // }else if(DataAnysMode_MOD_SCHDUL==2){
           // GPS 寫回資料庫
          //  String[] getResult = result.split("#");
          //  Toast.makeText(this, "GPSdoNotify\n" + getResult, Toast.LENGTH_LONG).show();
          //  masDB.add(getResult[0], getResult[1], getResult[2], getResult[3], getResult[4], getResult[5]);

       // }else{

       // }
    //}

    @Override
    public void doError(int resID) {
        Toast.makeText(this,"資料更新失敗"+resID,Toast.LENGTH_LONG).show();
    }




}
