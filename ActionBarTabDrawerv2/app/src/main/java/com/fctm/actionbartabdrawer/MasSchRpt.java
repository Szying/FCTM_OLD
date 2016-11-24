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
public class MasSchRpt extends Fragment {
    MasDbHelper masDB = null;
    ArrayList<MasDbHelper.Vocabulary> all = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.maslistview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        masDB = new MasDbHelper(getActivity());

        /*
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
        */


        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

        mylist.clear();
        all = masDB.getAllData();
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, String> map = new HashMap();
            map.put("ItemTitle2","行程 :"+all.get(i).sch );
            map.put("ItemText2","工作內容 :"+all.get(i).workitem);
            map.put("ItemText3", "時間起:"+all.get(i).timebeg+"~時間止:"+all.get(i).timeend);
            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.maslist_view,
                new String[]{"ItemTitle2", "ItemText2", "ItemText3"},
                new int[]{R.id.ItemTitle2, R.id.ItemText2,R.id.ItemText3});
        ((ListView)getActivity().findViewById(R.id.listAllmas)).setAdapter(mSchedule);
    }

        public void updateListView(Activity activity, LabDBHelper LabDB)
        {
            ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

            mylist.clear();

            all = masDB.getAllData();
            for (int i = 0; i < all.size(); i++) {
                HashMap<String, String> map = new HashMap();
                map.put("ItemTitle2",all.get(i).sch );
                map.put("ItemText2"," 工作內容:"+all.get(i).workitem);
                map.put("ItemText3", "時間起:"+all.get(i).timebeg+"~時間止:"+all.get(i).timeend);
                mylist.add(map);
            }


            SimpleAdapter mSchedule = new SimpleAdapter(
                    activity,
                    mylist,
                    R.layout.maslist_view,
                    new String[]{"ItemTitle2", "ItemText2","ItemText3"},
                    new int[]{R.id.ItemTitle2, R.id.ItemText2,R.id.ItemText3});

            ((ListView)getActivity().findViewById(R.id.listAllmas)).setAdapter(mSchedule);


        }
    }
