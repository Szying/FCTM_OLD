package com.fctm.actionbartabdrawer;

import android.accounts.Account;
import android.widget.Toast;

/**
 * Created by aaron on 3/29/15.
 */

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

public class UserInfo implements IHttpCompleted
{
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

    public static boolean isLogin = false;

    public static void parse(String data)
    {

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

    private MainActivity activity;
    public UserInfo(MainActivity activity)
    {
        this.activity = activity;
    }


    @Override
    public void doNotify(String result)
    {
        if(result.indexOf("false") == 0)
        {
            Toast.makeText(activity, "取得使用者資料錯誤", Toast.LENGTH_LONG).show();

        }
        else
        {
            parse(result);
            activity.setTitle("外傭管理" + number);

        }
    }

    @Override
    public void doError(int resID) {

    }

    public static void reset()
    {
        _id = 0;
        number = null;
        password = null;
        email = null;
        phone = null;
        Lab_Account = null;
        Lab_Password = null;
        regDate = null;
        status = null;
        reserved = null;
        isLogin = false;
    }
}
