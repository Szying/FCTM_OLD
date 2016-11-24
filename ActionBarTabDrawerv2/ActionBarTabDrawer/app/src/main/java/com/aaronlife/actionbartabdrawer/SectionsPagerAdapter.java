package com.aaronlife.actionbartabdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// ViewPager內的頁面和其TAB要顯示的標題設定
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
	private String[] titles = new String[] { "SECTION 1", "SECTION 2", "SECTION 3", "SECTION 4", "SECTION 5", "SECTION 6" };

	public SectionsPagerAdapter(FragmentManager fragmentManager)
    {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position)
    {
        switch(position)
        {
        case 0: return new Fragment1();
        case 1: return new Fragment2();
        case 2: return new Fragment3();
        case 3: return new Fragment4();
        case 4: return new Fragment5();
        case 5: return new Fragment6();
        }

        return null;
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public CharSequence getPageTitle(int position)
    {
		return titles[position];
	}
}
