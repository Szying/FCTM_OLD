package com.fctm.actionbartabdrawer;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    }

    public void initview() {

        //新增外傭記錄
        ImageButton btnadd = (ImageButton) getActivity().findViewById(R.id.btnAdd1ab);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(),"xxx",Toast.LENGTH_SHORT).show();
                DialogFragment dialog = new LabAddDialogFrag();                // 傳遞標題列文字給對話盒
                dialog.show(getFragmentManager(), "Laber add");

            }
        });


        ListView list = (ListView) getActivity().findViewById(R.id.listAll);
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

        mylist.clear();
        all = LabDB.getAllData();
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, String> map = new HashMap();
            map.put("ItemTitle","血糖:"+all.get(i).blood_glucose + " 血壓:"+all.get(i).pulse+" 心跳: "+all.get(i).palpitant);
            map.put( "ItemText", "時間:"+all.get(i).datetime+" 脈搏:"+all.get(i).blood_pressure);
            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.list_view,
                new String[]{"ItemTitle", "ItemText"},
                new int[]{R.id.ItemTitle, R.id.ItemText});

        list.setAdapter(mSchedule);
    }

        public void updateListView(Activity activity, LabDBHelper LabDB)
        {
            ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

            mylist.clear();

            all = LabDB.getAllData();
            for (int i = 0; i < all.size(); i++) {
                HashMap<String, String> map = new HashMap();
                map.put("ItemTitle","血糖:"+all.get(i).blood_glucose + " 血壓:"+all.get(i).pulse+"心跳: "+all.get(i).palpitant);
                map.put("ItemText2", "時間:"+all.get(i).datetime+" 脈搏:"+all.get(i).blood_pressure);
                mylist.add(map);
            }


            SimpleAdapter mSchedule = new SimpleAdapter(
                    activity,
                    mylist,
                    R.layout.list_view,
                    new String[]{"ItemTitle", "ItemText"},
                    new int[]{R.id.ItemTitle, R.id.ItemText});

            ((ListView)getActivity().findViewById(R.id.listAll)).setAdapter(mSchedule);


        }
    }
