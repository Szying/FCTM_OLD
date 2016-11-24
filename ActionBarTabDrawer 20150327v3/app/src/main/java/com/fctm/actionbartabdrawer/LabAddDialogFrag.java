package com.fctm.actionbartabdrawer;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Red on 2015/3/19.
 */
public class LabAddDialogFrag extends DialogFragment {
    OnUiUpdate callback;
    LabDBHelper labDB = null;
    EditText nowtxt,blood_glucose,blood_pressure,pulse,palpitant;
    View view = null;

    // 定義Callback介面
    public interface OnUiUpdate {
        public void notifyUpdate();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // 設定Callback
        callback = (OnUiUpdate)activity;
    }


    public static LabAddDialogFrag newInstance(int title) {
        LabAddDialogFrag frag = new LabAddDialogFrag();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New Lab Health");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.labhealthadd, null);
        initView(view);
        builder.setView(view);
        return builder.create();
    }


    public void initView(View view) {
        labDB = new LabDBHelper(getActivity());
        //預設當時的系統時間
        nowtxt = (EditText)view.findViewById(R.id.nowtxt);
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        nowtxt.setText(s.format(d));

        //血糖
        blood_glucose = (EditText)view.findViewById(R.id.blood_glucose);
        //脈搏
        pulse = (EditText)view.findViewById(R.id.pulse);
        //心跳
        palpitant= (EditText)view.findViewById(R.id.palpitant);
        //血壓
        blood_pressure= (EditText)view.findViewById(R.id.blood_pressure);


        ImageButton btnadd=(ImageButton)view.findViewById(R.id.btnAddlab);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                labDB.add(blood_glucose.getText().toString(),blood_pressure.getText().toString(), palpitant.getText().toString(),pulse.getText().toString(),nowtxt.getText().toString());
                Toast.makeText(getActivity(), getResources().getString(R.string.addsuccess), Toast.LENGTH_SHORT).show();
                // 在這裡呼叫Callback方法
                if(callback != null) callback.notifyUpdate();
                dismiss();
            }
        });

        ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btnDeletelab);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 震動物件
                Vibrator vVi = (Vibrator)getActivity().getSystemService(Service.VIBRATOR_SERVICE);

                // 震動 0.5 秒
                vVi.vibrate( 500 );

                // 震動 0.2 秒, 0.2 秒, 1 秒 (重複次數為不重複)
                vVi.vibrate(new long[]{200, 200, 1000}, -1);
            }
        });

        ImageButton btnlabedit=(ImageButton)getActivity().findViewById(R.id.btnEditlab);
        btnlabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //沒空做
                Toast.makeText(getActivity(),getResources().getString(R.string.editsuccess),Toast.LENGTH_SHORT).show();
                dismiss();
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


