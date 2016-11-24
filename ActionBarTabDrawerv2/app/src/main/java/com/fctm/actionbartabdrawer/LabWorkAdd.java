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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Red on 2015/3/7.
 */
public class LabWorkAdd extends Fragment {

    LabDBHelper labDB = null;

    EditText nowtxt,blood_glucose,blood_pressure,pulse,palpitant;


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
        callback = (OnUiUpdate)activity;
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


        ImageButton btnadd = (ImageButton) getActivity().findViewById(R.id.btnAdd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                labDB.add(blood_glucose.getText().toString(), blood_pressure.getText().toString(), palpitant.getText().toString(), pulse.getText().toString(), nowtxt.getText().toString());
                Toast.makeText(getActivity(), "新增完成!! 時間: " + nowtxt.getText() + blood_pressure.getText().toString() + palpitant.getText().toString() + pulse.getText().toString(), Toast.LENGTH_SHORT).show();
                // 在這裡呼叫Callback方法
                if (callback != null) callback.notifyUpdate();
            }
        });


    }










    // end Ann
}
