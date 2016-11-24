package com.fctm.actionbartabdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
    ListView list;
    int masschpic = 0;
    SimpleAdapter mSchedule;
    ArrayList<HashMap<String, Object>> mylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.maslistview, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        ListView list = (ListView) getActivity().findViewById(R.id.listAllmas);
        mylist.clear();
        masDB = new MasDbHelper(getActivity());
        //all = masDB.getAllData();
       // all.add(sss);
        //list.setAdapter(mSchedule);
        mSchedule.notifyDataSetChanged();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        masDB = new MasDbHelper(getActivity());

        //新增外傭行程記錄
        ImageButton btnadd = (ImageButton) getActivity().findViewById(R.id.btnaddmas1);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new MasAddDialogFrag();                // 傳遞標題列文字給對話盒
                dialog.show(getFragmentManager(), "MasAddDialogFrag");
            }
        });

        //Report
        list = (ListView) getActivity().findViewById(R.id.listAllmas);


        mylist = new ArrayList<HashMap<String, Object>>();

        mylist.clear();
        all = masDB.getAllData();
        masschpic = R.drawable.sch;
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, Object> map = new HashMap();
            map.put("masschimg", masschpic);
            map.put("ItemTitle2", "行程 :" + all.get(i).sch);
            map.put("ItemText2", "工作內容 :" + all.get(i).workitem);
            map.put("ItemText3", "時間起:" + all.get(i).timebeg);
            map.put("ItemText4", "時間止:" + all.get(i).timeend);

            mylist.add(map);
        }
         mSchedule = new SimpleAdapter(
                getActivity(),
                mylist,
                R.layout.maslistitemview,
                //          new String[]{"ItemTitle2", "ItemText2", "ItemText3", "ItemText4"},
                //          new int[]{R.id.ItemTitle2, R.id.ItemText2, R.id.ItemText3, R.id.ItemText4});
                new String[]{"masschimg", "ItemTitle2", "ItemText2", "ItemText3", "ItemText4"},
                new int[]{R.id.masschimg, R.id.ItemTitle2, R.id.ItemText2, R.id.ItemText3, R.id.ItemText4});

        mSchedule.notifyDataSetChanged();
        ((ListView) getActivity().findViewById(R.id.listAllmas)).setAdapter(mSchedule);

        updateListView(getActivity(), masDB);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String editline = all.get(position).id + "#" + all.get(position).sch + "#" + all.get(position).timebeg + "#" +
                        all.get(position).timeend + "#" + all.get(position).workitem;
                DialogFragment dialog = new MasAddDialogFrag();
                Bundle args = new Bundle();
                args.putString("ARG_SECTION_NUMBER", editline);
                dialog.setArguments(args);// 傳遞標題列文字給對話盒
                dialog.show(getFragmentManager(), "MasAddDialogFrag");


            }
        });

    }

    public void Add(String[] getResult)
    {
        ListView list = (ListView) getActivity().findViewById(R.id.listAllmas);
        mylist.clear();
        masDB = new MasDbHelper(getActivity());
        all = masDB.getAllData();
        // all.add(sss);
        list.setAdapter(mSchedule);
        //mSchedule.notifyDataSetChanged();
    }

    public void updateListView(Activity activity, MasDbHelper masDB) {
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

        mylist.clear();
        all = masDB.getAllData();
        masschpic = R.drawable.sch;
        for (int i = 0; i < all.size(); i++) {
            HashMap<String, Object> map = new HashMap();
            map.put("masschimg", masschpic);
            map.put("ItemTitle2", "行程 :" + all.get(i).sch);
            map.put("ItemText2", "工作內容 :" + all.get(i).workitem);
            map.put("ItemText3", "時間起:" + all.get(i).timebeg);
            map.put("ItemText4", "時間止:" + all.get(i).timeend);
            mylist.add(map);
        }

         mSchedule = new SimpleAdapter(
                activity,
                mylist,
                R.layout.maslistitemview,
                new String[]{"masschimg", "ItemTitle2", "ItemText2", "ItemText3", "ItemText4"},
                new int[]{R.id.masschimg, R.id.ItemTitle2, R.id.ItemText2, R.id.ItemText3, R.id.ItemText4});
        //    new String[]{ "ItemTitle2", "ItemText2", "ItemText3", "ItemText4"},
        //    new int[]{ R.id.ItemTitle2, R.id.ItemText2, R.id.ItemText3, R.id.ItemText4});

        ((ListView) activity.findViewById(R.id.listAllmas)).setAdapter(mSchedule);
        mSchedule.notifyDataSetChanged();

    }



}
