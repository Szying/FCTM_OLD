package com.fctm.actionbartabdrawer;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class MasSchdAdd extends Fragment {

    EditText show1, show2, show3, mastmespin, everyday;
    MasDbHelper masDB = null;
    Calendar c = Calendar.getInstance();
    EditText tmespin;
    ArrayList<MasDbHelper.Vocabulary> allMatch = null;
    ArrayList<MasDbHelper.Vocabulary> all = null;
    Spinner home_spnList;
    EditText home_editSearch;
    TextView home_txtContent;
    ArrayList<HashMap<String, String>> mylist;
    Calendar cal1=null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.masschadd, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        masDB = new MasDbHelper(getActivity());

        //預設日期值
        everyday = (EditText) getActivity().findViewById(R.id.everyday);
        everyday.setText(R.string.onschdule);
        home_spnList = (Spinner) getActivity().findViewById(R.id.spnList);
        // 建立ArrayList
        mylist = new ArrayList<HashMap<String, String>>();
        //spinner 工作選項
        Spinner spanworkitem = (Spinner) getActivity().findViewById(R.id.spanworkitem2);
        String[] allworks = {"起床", "量脈搏", "量血壓", "按摩、拉筋", "穿衣服", "準備早餐", "刷牙、洗臉", "餵藥", "洗衣服", "協助走路", "泡腳", "曬太陽", "屋外打掃", "準備午餐", "整理餐桌、洗碗", "中午休息", "澆花", "收衣服", "倒垃圾", "掃地、拖地"};
        ArrayList allworksarry = new ArrayList<String>();
        for (int i = 0; i < allworks.length; i++)
            allworksarry.add(allworks[i]);
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, allworksarry);
        adapter.setDropDownViewResource(R.layout.spinercont);
        spanworkitem.setAdapter(adapter);

        tmespin = (EditText) getActivity().findViewById(R.id.tmespin2);
        spanworkitem.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tmespin.setText(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //日期值
        ((EditText) getActivity().findViewById(R.id.everyday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                everyday.setText(year + "年" + (monthOfYear + 1)
                                        + "月" + dayOfMonth + "日");
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        //時間值起
        ((EditText) getActivity().findViewById(R.id.mas_heathendtime)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                show2 = (EditText) getActivity().findViewById(R.id.mas_heathendtime);
                                show2.setText(hourOfDay + "時" + minute + "分");
                            }
                        }, c.get(Calendar.HOUR),0, true).show();

            }
        });



        //時間值止
        ((EditText) getActivity().findViewById(R.id.masendtime2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                show3 = (EditText) getActivity().findViewById(R.id.masendtime2);
                                show3.setText(hourOfDay + "時" + minute + "分");
                            }
                        },  c.get(Calendar.HOUR),0, true).show();

            }
        });

        //新增
        ImageButton imgbtn = (ImageButton) getActivity().findViewById(R.id.btnaddmas);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masDB.add(everyday.getText().toString(), show2.getText().toString(), show3.getText().toString(), tmespin.getText().toString());
                Toast.makeText(getActivity(), "顧主新增行程完成 " + everyday.getText().toString() + show2.getText().toString() + show3.getText().toString() + tmespin.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });


        //delete
        ((ImageButton) getActivity().findViewById(R.id.btndeletemas)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), show1.getText().toString() + show2.getText().toString() + show3.getText().toString() + mastmespin.getText().toString(), Toast.LENGTH_SHORT).show();
                masDB.delete(everyday.getText().toString());

            }
        });

        //spinner 選項
        Spinner spinlist=(Spinner)getActivity().findViewById(R.id.spnList);
       all=masDB.getSch();
        for(int i=0;i<all.size();i++){
            HashMap<String,String> map=new HashMap();
            map.put(all.get(i).id+"",all.get(i).timebeg+"~"+all.get(i).timeend+all.get(i).workitem);
            mylist.add(map);
        }
        final ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, mylist);
        adapter.setDropDownViewResource(R.layout.spinercont);
        spinlist.setAdapter(adapter1);

        //修改
        ((ImageButton) getActivity().findViewById(R.id.btneditmas)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), show1.getText().toString() + show2.getText().toString() + show3.getText().toString() + mastmespin.getText().toString(), Toast.LENGTH_SHORT).show();
                //masDB.update(everyday.getText().toString());

            }
        });

        // 建立Spinner傾聽器
         Spinner.OnItemSelectedListener spnListener = new Spinner.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                    String voc = parent.getSelectedItem().toString();
                    String content = masDB.get(voc).sch;
                    home_txtContent.setText(content);
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            };



    } //end Activity
}





