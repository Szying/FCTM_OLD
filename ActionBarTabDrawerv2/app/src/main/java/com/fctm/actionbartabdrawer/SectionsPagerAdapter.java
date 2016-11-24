package com.fctm.actionbartabdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


// ViewPager內的頁面和其TAB要顯示的標題設定
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
	private String[] titles = new String[] { "Laber Add", "Laber Work List", "Laber Train", "Laber Train", "GPS", "Raw","顧主行程","顧主報表" };

	public SectionsPagerAdapter(FragmentManager fragmentManager)
    {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position)
    {
        if(UserInfo.type == 0) {
            switch (position) {
                case 0:
                    return new LabWorkAdd();
                case 1:
                    return new LabHelRpt();
                case 2:
                    return new LabTrain();
                case 3:
                    return new LabLanguTrain();
                case 4:
                    return new MasSchdAdd();
                case 5:
                    return new MasSchRpt();
                case 6:
                    return new LabRaw();
                case 7:
                    return new LabGPS();
            }
        }
        else
        {

        }

        return null;
	}

	@Override
	public int getCount() {
		return 8;
	}

	@Override
	public CharSequence getPageTitle(int position)
    {
		return titles[position];
	}
}
