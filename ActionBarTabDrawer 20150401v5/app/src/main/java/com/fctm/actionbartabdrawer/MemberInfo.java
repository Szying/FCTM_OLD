package com.fctm.actionbartabdrawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

        if(GetInfo.result == null || GetInfo.result.length() < 5) return;

        String[] datas = data.split("`");

        if(datas.length < 10) return;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.userinfo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        parse(GetInfo.result);


        final TextView txtAccount = (TextView)getActivity().findViewById(R.id.reg_account);
        final TextView txtPassword = (TextView) getActivity().findViewById(R.id.reg_password);
        final TextView txtEmail = (TextView) getActivity().findViewById(R.id.reg_email);
        final TextView txtPhone = (TextView)getActivity().findViewById(R.id.reg_phone);
        final TextView txtLabAccount = (TextView) getActivity().findViewById(R.id.reg_labAccount);
        final TextView txtLabPassword = (TextView) getActivity().findViewById(R.id.reg_labPassword);
        final TextView txtStatus = (TextView) getActivity().findViewById(R.id.reg_status);

        txtAccount.setText(number);
        txtPassword.setText(password);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtLabAccount.setText(Lab_Account);
        txtLabPassword.setText(Lab_Password);
        txtStatus.setText(regDate);

    }




    @Override
    public void doNotify(String result) {
    //  Toast.makeText(getActivity(),"MemberInfo -result:"+result,Toast.LENGTH_LONG).show();
        TextView txtStatus = (TextView) getActivity().findViewById(R.id.reg_status);
        txtStatus.setText(result);
    }

    @Override
    public void doError(int resID) {
        Toast.makeText(getActivity(),"doNotify-member"+resID,Toast.LENGTH_LONG).show();
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
