package com.fctm.actionbartabdrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fctm.actionbartabdrawer.R;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by aaron on 3/18/15.
 */
public class MemberInfo extends Fragment implements IHttpCompleted {
    View view;
    public static int _id;

    // NUMBER	TEXT	帳號（電話）
    public static String number;

    // PASSWORD	TEXT	密碼
    public static String password;

    // EMAIL	TEXT	電子信箱
    public static String email;

    // PHONE	TEXT	連絡電話
    public static String phone;

    // BIRTH	TEXT	外傭帳號
    public static String Lab_Account;

    // EXP_DATA	TEXT	外傭密碼
    public static String Lab_Password;

    // REG_DATE	TEXT	註冊日期
    public static String regDate;

    // STATUS	TEXT	帳號狀態
    public static String status;

    // RESERVED	TEXT	保留
    public static String reserved;

    public static void parse(String data) {

        String[] datas = data.split("`");

        _id = Integer.parseInt(datas[0]);
        number = datas[1];
        password = datas[2];
        email = datas[3];
        phone = datas[4];
        Lab_Account = datas[5];
        Lab_Password = datas[6];
        regDate = datas[7];
        status = datas[8];
        reserved = datas[9];
    }


    public void onCreateDialog(Bundle savedInstanceState) {
        // 定義對話盒物件建構器
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 取得介面實體化物件
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.userinfo, null);

        final TextView txtAccount = (TextView) view.findViewById(R.id.reg_account);
        final TextView txtPassword = (TextView) view.findViewById(R.id.reg_password);
        final TextView txtEmail = (TextView) view.findViewById(R.id.reg_email);
        final TextView txtPhone = (TextView) view.findViewById(R.id.reg_phone);
        final TextView txtLabAccount = (TextView) view.findViewById(R.id.reg_labAccount);
        final TextView txtLabPassword = (TextView) view.findViewById(R.id.reg_labPassword);
        final TextView txtStatus = (TextView) view.findViewById(R.id.reg_status);
//        外傭管理資料欄位
//        NUMBER	TEXT	帳號
//        PASSWORD	TEXT	密碼
//        EMAIL   TEXT    Email
//        PHONE   TEXT     行動電話
//        LAB_NUMBER TEXT  外傭帳號
//        LAB_PASSWORD TEXT 外傭密碼
//        REG_DATE     TEXT 註冊日期
//        STATUS      TEXT  帳號狀態
//        RESERVED     TEXT 保留

        String s = DialogLogin.txtUsername.toString();
        // 將註冊資料傳送給Server

        HttpAction.query("member", s, "", MemberInfo.this);
        txtAccount.setText(number);
        txtPassword.setText(password);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtLabAccount.setText(Lab_Account);
        txtLabPassword.setText(Lab_Password);
        txtStatus.setText(regDate);

        try {
            HttpAction.query("member", s, "", MemberInfo.this);
        } catch (Exception e) {
            MemberInfo.this.doError(R.string.query_err);
        }

    }

    @Override
    public void doNotify(String result) {

    }

    @Override
    public void doError(int resID) {

    }
//    @Override
//    public void doNotify(String result)
//    {
//        if(result.indexOf("false.") == 0)
//        {
//            UserInfo.isLogin = false;
//            TextView regStatus = (TextView)view.findViewById(R.id.reg_status);
//            regStatus.setText(result.replace("false.", ""));
//        }
//        else if(result.indexOf("true") == 0)
//        {
//            UserInfo.isLogin = true;
//            Toast.makeText(getActivity(), "註冊成功", Toast.LENGTH_SHORT).show();
//
//            // 取得該看護資料
//            //     TextView txtUsername = (TextView)view.findViewById(R.id.reg_account);
//            //    HttpAction.query("member", txtUsername.getText().toString(), "", new UserInfo((MainActivity)getActivity()));
////            DialogLogin dlg = new DialogLogin();
////            closeDialog(getDialog(), true);
////            dlg.show(getFragmentManager(), "DialogLogin");
////            dismiss();
//
//
//        }
//        else
//        {
//            UserInfo.isLogin = false;
//            TextView regStatus = (TextView)view.findViewById(R.id.reg_status);
//            regStatus.setText(result);
//        }
//    }
//
//    @Override
//    public void doError(int resID)
//    {
//        TextView loginStatus = (TextView)view.findViewById(R.id.reg_status);
//        loginStatus.setText(getText(resID));
//    }
//
//    private void closeDialog(DialogInterface dlg, boolean bClose)
//    {
//        try
//        {
//            Field field = dlg.getClass().getSuperclass().getDeclaredField("mShowing");
//            field.setAccessible(true);
//            field.set(dlg, bClose);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

}
