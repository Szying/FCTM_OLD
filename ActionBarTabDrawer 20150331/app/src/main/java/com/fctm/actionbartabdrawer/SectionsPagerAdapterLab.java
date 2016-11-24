package com.fctm.actionbartabdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// ViewPager內的頁面和其TAB要顯示的標題設定
public class SectionsPagerAdapterLab extends FragmentPagerAdapter {
    public static final String[] titles2 = new String[]{"Health Add", "Daily Sch", "Extra Sch", "TrainVideo", "Language"};
    private static  String[] mMenulab;

    public SectionsPagerAdapterLab(FragmentManager fm) {
        super(fm);
    }

    public SectionsPagerAdapterLab(FragmentManager fm,String[] mstring){
        super(fm);
        mMenulab=mstring;
    }



    @Override

    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                return new LabWorkAdd();   //外傭新增健康記錄
            case 1:
                return new LabNormRpt();    //顧主每日排程
            case 2:
                return new LabExtraRpt(); //顧主額外排程

            case 3:
                return new LabTrain();    //外傭教育

            case 4:
                return new LabLanguTrain();      //印語教學


        }
        return null;
    }

    @Override

//        取得TAB的數目
    public int getCount() {

        return mMenulab.length;
    }



    //取得TAB的標題名字
    @Override
    public CharSequence getPageTitle(int position) {
   //     titles2=_context.getResources().getStringArray(R.array.menulab);
        return mMenulab[position];
    }
}
