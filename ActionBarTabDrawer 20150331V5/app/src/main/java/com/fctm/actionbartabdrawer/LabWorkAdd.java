package com.fctm.actionbartabdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Red on 2015/3/7.
 */
public class LabWorkAdd extends Fragment implements IHttpCompleted{

    LabDBHelper labDB = null;

    EditText nowtxt,blood_glucose,blood_pressure,pulse,palpitant;
    String memberid="001";
    @Override
    public void doNotify(String result)
    {
        Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
        if(result.indexOf("false.") == 0)
        {
            //  UserInfo.isLogin = false;
            //  TextView regStatus = (TextView)view.findViewById(R.id.reg_status);
            //  regStatus.setText(result.replace("false.", ""));
        }
        else if(result.indexOf("true") == 0)
        {
            //  UserInfo.isLogin = true;
            //  Toast.makeText(getActivity(), "註冊成功", Toast.LENGTH_SHORT).show();
            //  ((MainActivity)getActivity()).doLogin();
           // closeDialog(getDialog(), true);
           // dismiss();
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
    public interface OnUiUpdate
    {
        public void notifyUpdate();
    }

    OnUiUpdate callback;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // 設定Callback
      //*  callback = (OnUiUpdate)activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.labhealthadd, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        labDB = new LabDBHelper(getActivity());
        //預設當天日期
        nowtxt = (EditText) getActivity().findViewById(R.id.nowtxt);
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        nowtxt.setText(s.format(d));
        //血糖
        blood_glucose = (EditText) getActivity().findViewById(R.id.blood_glucose);
        //脈搏
        pulse = (EditText) getActivity().findViewById(R.id.pulse);
        //心跳
        palpitant = (EditText) getActivity().findViewById(R.id.palpitant);
        //血壓
        blood_pressure = (EditText) getActivity().findViewById(R.id.blood_pressure);


        ImageButton btnadd = (ImageButton) getActivity().findViewById(R.id.btnAddlab);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                labDB.add(blood_glucose.getText().toString(), blood_pressure.getText().toString(), palpitant.getText().toString(), pulse.getText().toString(), nowtxt.getText().toString(),"NEW",memberid);
                Toast.makeText(getActivity(), "新增完成!! ", Toast.LENGTH_SHORT).show();




                //寫至外部資料庫
                String url1=blood_glucose.getText().toString()+"`"+ blood_pressure.getText().toString()+"`"+palpitant.getText().toString()+"`";
                url1=url1+  pulse.getText().toString()+"`"+ nowtxt.getText().toString()+"`"+"NEW"+"`"+memberid;
                Toast.makeText(getActivity(),url1,Toast.LENGTH_LONG).show();
                try{
                    HttpAction.insert("health", encode(url1),LabWorkAdd.this);

                }catch (Exception e){

                }
                blood_glucose.setText("");
                blood_pressure.setText("");
                palpitant.setText("");
                pulse.setText("");

                //
                if (callback != null) callback.notifyUpdate();

            }
        });



        ((ImageButton)getActivity().findViewById(R.id.btnEditlab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //沒空做
               // labDB.update(blood_glucose.getText().toString(), blood_pressure.getText().toString(), palpitant.getText().toString(), pulse.getText().toString(), nowtxt.getText().toString());
                Toast.makeText(getActivity(), "修改完成!! ", Toast.LENGTH_SHORT).show();
                // 在這裡呼叫Callback方法
                //if (callback != null) callback.notifyUpdate();

            }
        });

        ((ImageButton)getActivity().findViewById(R.id.btnDeletelab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "刪除完成!! ", Toast.LENGTH_SHORT).show();

            }



        });


    }



    public String encode(String value) throws Exception{
        return URLEncoder.encode(value, "utf-8");
    }






    // end Ann
}
