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
 * Created by Red on 2015/2/22.
 */
public class LabExtraRpt extends Fragment {
    ArrayList<MasDbHelper.Vocabulary> all = null;
    MasDbHelper masDB = null;
    ListView list;
    int pic=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.labextralistview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        masDB = new MasDbHelper(getActivity());
        initView();


    }

    public void initView() {

           ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

        mylist.clear();
        list=(ListView)getActivity().findViewById(R.id.listAllmasextra);
        all = masDB.getExtraSch();
        pic=R.drawable.newsch;
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, Object> map = new HashMap();
            map.put("labextrimg",pic);
            map.put("ItemTitleext","行程 :"+all.get(i).sch );
            map.put("ItemTextext2","工作內容 :"+all.get(i).workitem);
            map.put("ItemTextext3", "時間起:"+all.get(i).timebeg);
            map.put("ItemTextext4", "時間止:"+all.get(i).timeend);

            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.labextrarpt_view,
                new String[]{"labextrimg","ItemTitleext", "ItemTextext2", "ItemTextext3"},
                new int[]{R.id.labextrimg,R.id.ItemTitleext, R.id.ItemTextext2,R.id.ItemTextext3});
        list.setAdapter(mSchedule);
    }

    public void updateListView(Activity activity, MasDbHelper masDB)
    {
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

        mylist.clear();
        all = masDB.getExtraSch();
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, Object> map = new HashMap();
            map.put("labextrimg",pic);
            map.put("ItemTitleext","行程 :"+all.get(i).sch );
            map.put("ItemTextext2","工作內容 :"+all.get(i).workitem);
            map.put("ItemTextext3", "時間起:"+all.get(i).timebeg);
            map.put("ItemTextext4", "時間止:"+all.get(i).timeend);

            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.labextrarpt_view,
                new String[]{"labextrimg","ItemTitleext", "ItemTextext2", "ItemTextext3"},
                new int[]{R.id.labextrimg,R.id.ItemTitleext, R.id.ItemTextext2,R.id.ItemTextext3});
        list.setAdapter(mSchedule);
    }
}
