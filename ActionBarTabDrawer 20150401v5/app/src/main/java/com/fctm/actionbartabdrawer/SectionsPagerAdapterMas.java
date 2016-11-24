package com.fctm.actionbartabdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// ViewPager內的頁面和其TAB要顯示的標題設定
public class SectionsPagerAdapterMas extends FragmentPagerAdapter {
    private String[] titles1 = new String[]{"Schdule", "Health RPT", "Recorder","GPS", "Raw","Member"};
//   private String[] titles1 = new String[]{"Schdule", "Health RPT", "Recorder","GPS", "Raw","Member"};


    private static String[] mMenu;





    public SectionsPagerAdapterMas(FragmentManager fragmentManager) {

        super(fragmentManager);
    }


    public SectionsPagerAdapterMas(FragmentManager fm,String[] mstring){
        super(fm);
        mMenu=mstring;
        m = new MemberInfo();
        ms = new MasSchRpt();
    }

    MemberInfo m;
    MasSchRpt ms;


    @Override

    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                return ms; //顧主額外排程
            case 1:
                return new LabHelRpt();    //外傭健康記錄表
            case 2:
                return new MasRecorder();  //顧主聽錄音檔
            case 3:
                return new LabGPS();  //GPS
            case 5:
                return m;  //會員記錄
            case 4:
            return new LabRaw();      //外傭法規
        }

        return null;
    }

    @Override

//        取得TAB的數目
    public int getCount() {

        return mMenu.length;
    };

    //取得TAB的標題名字
    @Override
    public CharSequence getPageTitle(int position) {

        return mMenu[position];

    };


}
