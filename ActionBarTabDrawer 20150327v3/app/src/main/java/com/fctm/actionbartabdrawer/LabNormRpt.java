package com.fctm.actionbartabdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Red on 2015/3/21.
 */
public class LabNormRpt extends Fragment {
    MasDbHelper masDB = null;
    ArrayList<MasDbHelper.Vocabulary> all = null;
    ListView listview;
    int pic=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.labnormrptview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        masDB = new MasDbHelper(getActivity());
        listview = (ListView) getActivity().findViewById(R.id.lablistallmasnor);
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

        mylist.clear();
        all = masDB.getSch();
        pic=R.drawable.schedule;
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, Object> map = new HashMap();
            map.put("labnorimg",pic);
            map.put("labItemTitlenor", "工作內容:" + all.get(i).workitem);
            map.put("labItemTextnor2", "時間起:" + all.get(i).timebeg);
            map.put("labItemTextnor3", "時間止:" + all.get(i).timeend);

            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.labnorrptallview,
                new String[]{"labnorimg","labItemTitlenor", "labItemTextnor2", "labItemTextnor3"},
                new int[]{R.id.labnorimg,R.id.labItemTitlenor, R.id.labItemTextnor2, R.id.labItemTextnor3});
        listview.setAdapter(mSchedule);
    }

    public void updateListView(Activity activity, MasDbHelper masDB) {

        listview = (ListView) activity.findViewById(R.id.lablistallmasnor);
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

        mylist.clear();
        all = masDB.getSch();
        pic=R.drawable.schedule;
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, Object> map = new HashMap();
            map.put("labnorimg",pic);
            map.put("labItemTitlenor", "工作內容:" + all.get(i).workitem);
            map.put("labItemTextnor2", "時間起:" + all.get(i).timebeg);
            map.put("labItemTextnor3", "時間止:" + all.get(i).timeend);
            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.labnorrptallview,
                new String[]{"labnorimg","labItemTitlenor", "labItemTextnor2", "labItemTextnor3"},
                new int[]{R.id.labnorimg,R.id.labItemTitlenor, R.id.labItemTextnor2, R.id.labItemTextnor3});
        listview.setAdapter(mSchedule);

    }
}





