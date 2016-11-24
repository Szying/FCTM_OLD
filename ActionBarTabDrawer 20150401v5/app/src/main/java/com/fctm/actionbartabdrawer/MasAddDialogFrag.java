package com.fctm.actionbartabdrawer;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Red on 2015/3/7.
 */
public class MasAddDialogFrag extends DialogFragment implements IHttpCompleted {
    OnUiUpdateMas callback;
    EditText show2, show3, everyday;
    MasDbHelper masDB = null;
    Calendar c = Calendar.getInstance();
    EditText tmespin;
    ArrayList<MasDbHelper.Vocabulary> allMatch = null;
    ArrayList<MasDbHelper.Vocabulary> all = null;
    String[] spiltData;
    String editdata = "";
    ArrayList<HashMap<String, String>> mylist;
    View view = null;
    static int ControlMode = 0; //新增模式
    String memberid=MemberInfo.number;
    String[] allworks;
    Spinner spanworkitem;


    public String encode(String value) throws Exception{
        return URLEncoder.encode(value, "utf-8");
    }


    @Override
    public void doNotify(String result)
    {
      //  Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
        if(result.indexOf("false.") == 0)
        {
            //  UserInfo.isLogin = false;
            // TextView regStatus = (TextView)view.findViewById(R.id.reg_status);
            //  regStatus.setText(result.replace("false.", ""));
        }
        else if(result.indexOf("true") == 0)
        {

              Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

          //  closeDialog(getDialog(), true);
         //   dismiss();
        }
        else
        {
            //   UserInfo.isLogin = false;
            //   TextView regStatus = (TextView)view.findViewById(R.id.reg_status);
            //   regStatus.setText(result);
        }
    }
    @Override
    public void doError(int resID)
    {
     //   TextView loginStatus = (TextView)view.findViewById(R.id.reg_status);
     //   loginStatus.setText(getText(resID));
    }


    // 定義Callback介面
    public interface OnUiUpdateMas extends IHttpCompleted {
        public void notifyUpdateMas();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 設定Callback
        callback = (OnUiUpdateMas) activity;
        allworks = getResources().getStringArray(R.array.onschdule);

    }


    public static MasAddDialogFrag newInstance(int title) {
        MasAddDialogFrag frag = new MasAddDialogFrag();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.labmagtitle));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.masschadd, null);

        initView(view);


        builder.setView(view);
        builder.setNegativeButton(R.string.Cancel, null);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            //修改模式才需作
            //Toast.makeText(getActivity(),getArguments().getString("ARG_SECTION_NUMBER"),Toast.LENGTH_SHORT).show();

                editdata = getArguments().getString("ARG_SECTION_NUMBER");
                ControlMode = 1;
                setMasValue();

        }
    }


    //修改模式 要給的預設值
    public void setMasValue() {
        if(editdata !="") {
            spiltData = editdata.split("#");
            if(spiltData.length==5) {
                everyday.setText(spiltData[1]);//日期
                tmespin.setText(spiltData[4]);  //工作內容　
                //放回spinner值
                String[] arraysch=getResources().getStringArray(R.array.onschdule);
                for (int i = 0; i < arraysch.length; i++) {
                    if (spiltData[4].equals(arraysch[i])) {
                        spanworkitem.setSelection(i);
                        break;
                    }
                }
                show2.setText(spiltData[2]);   //時間起
                show3.setText(spiltData[3]);  //時間止
            }
        }
    }


    public void initView(final View view) {
        show2 = (EditText) view.findViewById(R.id.mas_heathendtime);
        show3 = (EditText) view.findViewById(R.id.masendtime2);
        masDB = new MasDbHelper(getActivity());



        //預設日期值
        everyday = (EditText) view.findViewById(R.id.everyday);
        everyday.setText(R.string.onschdule);

        // 建立ArrayList
        mylist = new ArrayList<HashMap<String, String>>();
        //spinner 工作選項
        spanworkitem = (Spinner) view.findViewById(R.id.spanworkitem2);

        //spinner Relation
        tmespin = (EditText) view.findViewById(R.id.tmespin2);
        spanworkitem.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tmespin.setText(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(view.getContext(), getResources().getString(R.string.selecterror), Toast.LENGTH_LONG).show();
            }
        });

        //日期值
        ((EditText) view.findViewById(R.id.everyday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(view.getContext(),
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

        //時間起
        ((EditText) view.findViewById(R.id.mas_heathendtime)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                show2.setText(hourOfDay + "時" + minute + "分");
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), 0, true).show();

            }
        });


        //時間值止
        ((EditText) view.findViewById(R.id.masendtime2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.HOUR_OF_DAY, 1);
                new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                show3.setText(hourOfDay + "時" + minute + "分");
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), 0, true).show();

            }
        });

//        //回首頁
//        ImageButton imghome = (ImageButton) view.findViewById(R.id.btnhomemas);
//        imghome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        //新增
        ImageButton imgbtn = (ImageButton) view.findViewById(R.id.btnaddmas);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (everyday.getText().toString() == "" | show2.getText().toString() == "" || show3.getText().toString() == "" || tmespin.getText().toString() == "") {
                    Toast.makeText(view.getContext(), getResources().getString(R.string.fillfields), Toast.LENGTH_SHORT).show();
                } else {
                    masDB.add(everyday.getText().toString(), show2.getText().toString(), show3.getText().toString(), tmespin.getText().toString(),"NEW",MemberInfo.number);
                    Toast.makeText(view.getContext(),getResources().getString(R.string.addsuccess), Toast.LENGTH_SHORT).show();

                    //寫至外部 server
                    String url1=everyday.getText().toString()+"`";
                    url1=url1+show2.getText().toString()+"`"+show3.getText().toString()+"`"+tmespin.getText().toString()+"`"+"NEW"+"`"+MemberInfo.number;
                    try{
                       // Toast.makeText(view.getContext(),url1 , Toast.LENGTH_SHORT).show();
                        HttpAction.insert("schdul",encode(url1),MasAddDialogFrag.this,"schedul");
                    }catch (Exception e){

                    }


                    if (callback != null) callback.notifyUpdateMas();
                    dismiss();
                }
            }
        });



//        //delete
        ((ImageButton) view.findViewById(R.id.btndeletemas)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getActivity(), spiltData[0] + " " + ControlMode, Toast.LENGTH_SHORT).show();
                if (spiltData[0] != "") {
                    masDB.delete(Integer.parseInt(spiltData[0]));
                    Toast.makeText(getActivity(),getResources().getString(R.string.deletesuccess),Toast.LENGTH_LONG).show();
                    if(callback != null) callback.notifyUpdateMas();

                    dismiss();
                }

            }
        });

        //修改
        ((ImageButton) view.findViewById(R.id.btneditmas5))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(),"spiltData[0]: "+spiltData[0],Toast.LENGTH_SHORT).show();
                if (ControlMode == 1 ) {
                    MasDbHelper.Vocabulary v1 = new MasDbHelper.Vocabulary();
                    v1.workitem = tmespin.getText().toString();
                    v1.sch = everyday.getText().toString();
                    v1.timebeg =  show2.getText().toString();
                    v1.timeend = show3.getText().toString();
                    masDB.update(Integer.parseInt(spiltData[0]), v1);
                    Toast.makeText(getActivity(),getResources().getString(R.string.editsuccess),Toast.LENGTH_LONG).show();
                    if (callback != null) callback.notifyUpdateMas();

                    dismiss();
                }

            }
        });

    }

    public void closeDialog(DialogInterface dialog, boolean bClose) {
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, bClose);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}