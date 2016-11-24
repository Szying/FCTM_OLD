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
 * Created by Red on 2015/3/12.
 */
public class LabHelRpt extends Fragment {
    LabDBHelper LabDB = null;
    ArrayList<LabDBHelper.LabData> all = null;
     int mPics=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lablistview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LabDB = new LabDBHelper(getActivity());
        initview();
        updateListView(getActivity(),LabDB);
    }

    public void initview() {

        //新增外傭記錄



        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

        mylist.clear();
        all = LabDB.getAllData();

        mPics=R.drawable.doc1;
        for (int i = 0; i < all.size(); i++) {
            HashMap<String ,Object> map = new HashMap();
            map.put("labimg1",mPics);
            map.put("ItemTitle", "血糖:" + all.get(i).blood_glucose + "   血壓:" + all.get(i).pulse );
            map.put("ItemText2", "心跳: " + all.get(i).palpitant + "   脈搏:" + all.get(i).blood_pressure);
            map.put("ItemText3", "時間:" + all.get(i).datetime);

            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.labhealitemview,
                new String[]{"labimg1","ItemTitle","ItemText2", "ItemText3"},
                new int[]{R.id.labimg1,R.id.ItemTitlehealth, R.id.ItemTexthealth,R.id.ItemTexthealth2});

        ((ListView)getActivity().findViewById(R.id.listAll)).setAdapter(mSchedule);
        updateListView(getActivity(),LabDB);
    }

        //更新外傭輸入的健康報表
        public void updateListView(Activity activity, LabDBHelper LabDB)
        {
            ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

            mylist.clear();

            all = LabDB.getAllData();
            for (int i = 0; i < all.size(); i++) {
                HashMap<String, Object> map = new HashMap();
                map.put("labimg1",mPics);
                map.put("ItemTitle","血糖:"+all.get(i).blood_glucose + "   血壓:"+all.get(i).pulse);
                map.put("ItemText2", "心跳:" + all.get(i).palpitant + "   脈搏:" + all.get(i).blood_pressure);
                map.put("ItemText3", "時間:"+all.get(i).datetime);
                mylist.add(map);
            }


            SimpleAdapter mSchedule = new SimpleAdapter(
                    activity,
                    mylist,
                    R.layout.labhealitemview,
                    new String[]{"labimg1","ItemTitle","ItemText2", "ItemText3"},
                    new int[]{R.id.labimg1,R.id.ItemTitlehealth, R.id.ItemTexthealth,R.id.ItemTexthealth2});

            ((ListView)activity.findViewById(R.id.listAll)).setAdapter(mSchedule);


        }
    }
