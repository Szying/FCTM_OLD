package com.aaronlife.thecarebank.nurse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronlife.actionbartabdrawer.R;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by aaron on 3/18/15.
 */
public class DialogRegister extends DialogFragment implements IHttpCompleted
{
    View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 定義對話盒物件建構器
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 取得介面實體化物件
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_register, null);

        final TextView txtAccount = (TextView)view.findViewById(R.id.reg_account);
        final TextView txtName = (TextView)view.findViewById(R.id.reg_name);
        final TextView txtPassword = (TextView)view.findViewById(R.id.reg_password);
        final TextView txtEmail = (TextView)view.findViewById(R.id.reg_email);
        final TextView txtSalary = (TextView)view.findViewById(R.id.reg_salary);
        final TextView txtExpYear = (TextView)view.findViewById(R.id.reg_exp_year);
        final TextView txtSelf = (TextView)view.findViewById(R.id.reg_selfinstrduction);

//        NUMBER	TEXT	帳號（電話）
//        NAME	TEXT	姓名
//        PHOTO	TEXT	大頭照路徑檔名
//        TYPE	TEXT	雇主/看護
//        PASSWORD	TEXT	密碼
//        EMAIL	TEXT	電子信箱
//        PHONE	TEXT	連絡電話
//        BIRTH	TEXT	生日
//        SALARY	INTEGER	希望待遇（日薪）
//        EXP_YEAR	INTEGER	年資
//        EXP_DATA	TEXT	經歷資料
//        DES	TEXT	自我介紹
//        REG_DATE	TEXT	註冊日期
//        STATUS	TEXT	帳號狀態
//        LINK	TEXT	連結的雇主/看護
//        PATIENT	TEXT	被照顧者
//        RESERVED	TEXT	保留
        builder.setPositiveButton(R.string.register, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //定義好時間字串的格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);

                // 取得現在時間
                String datetime = sdf.format(Calendar.getInstance().getTime());

                String data = txtAccount.getText().toString() + "`" +
                              txtName.getText() + "`" +
                              "/photo.jpg`" +
                              "nurse`" +
                              txtPassword.getText() + "`" +
                              txtEmail.getText() + "`" +
                              txtAccount.getText() + "`" +
                              "1990-01-01`" +
                              txtSalary.getText() + "`" +
                              txtExpYear.getText() + "`" +
                              "台大醫院、振興醫院`" +
                              txtSelf.getText() + "`" +
                              datetime + "`" +
                              "normal`" +
                              "NULL`" +
                              "NULL`" +
                              "NULL";

                // 將註冊資料傳送給Server
                try
                {
                    // 必須將字串轉成URL編碼，否則空白字元或中文會變成亂碼
                    data = URLEncoder.encode(data, "UTF-8");
                }
                catch (UnsupportedEncodingException e) {}
                HttpAction.insert("member", data, DialogRegister.this);

                closeDialog(dialog, false);
            }
        });

        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                closeDialog(dialog, true);
            }
        });

        // 設定對話盒介面
        builder.setView(view);
        builder.setCancelable(false);

        return builder.create(); // 產生對話盒實體並回傳
    }

    @Override
    public void doNotify(String result)
    {
        if(result.indexOf("false.") == 0)
        {
            UserInfo.isLogin = false;
            TextView regStatus = (TextView)view.findViewById(R.id.reg_status);
            regStatus.setText(result.replace("false.", ""));
        }
        else if(result.indexOf("true") == 0)
        {
            UserInfo.isLogin = true;
            Toast.makeText(getActivity(), "註冊成功", Toast.LENGTH_SHORT).show();

            // 取得該看護資料
            TextView txtUsername = (TextView)view.findViewById(R.id.reg_account);
            HttpAction.query("member", txtUsername.getText().toString(), "", new UserInfo((MainActivity)getActivity()));

            closeDialog(getDialog(), true);
            dismiss();
        }
        else
        {
            UserInfo.isLogin = false;
            TextView regStatus = (TextView)view.findViewById(R.id.reg_status);
            regStatus.setText(result);
        }
    }

    @Override
    public void doError(int resID)
    {
        TextView loginStatus = (TextView)view.findViewById(R.id.reg_status);
        loginStatus.setText(getText(resID));
    }

    private void closeDialog(DialogInterface dlg, boolean bClose)
    {
        try
        {
            Field field = dlg.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dlg, bClose);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
