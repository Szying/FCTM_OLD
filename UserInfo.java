package com.aaronlife.thecarebank.nurse;

import android.widget.Toast;

/**
 * Created by aaron on 3/29/15.
 */
public class UserInfo implements IHttpCompleted
{
    public static int _id;
    // NUMBER	TEXT	帳號（電話）
    public static String number;

    // NAME	TEXT	姓名
    public static String name;

//        PHOTO	TEXT	大頭照路徑檔名
    public static String photo;

//        TYPE	TEXT	雇主/看護
    public static String type;

//        PASSWORD	TEXT	密碼
    public static String password;

//        EMAIL	TEXT	電子信箱
    public static String email;

//        PHONE	TEXT	連絡電話
    public static String phone;

//        BIRTH	TEXT	生日
    public static String birth;

//        SALARY	INTEGER	希望待遇（日薪）
    public static int salary;

//        EXP_YEAR	INTEGER	年資
    public static int expYear;

//        EXP_DATA	TEXT	經歷資料
    public static String expData;

//        DES	TEXT	自我介紹
    public static String des;

//        REG_DATE	TEXT	註冊日期
    public static String regDate;

//        STATUS	TEXT	帳號狀態
    public static String status;

//        LINK	TEXT	連結的雇主/看護
    public static String link;

//        PATIENT	TEXT	被照顧者
    public static String patient;

//        RESERVED	TEXT	保留
    public static String reserved;

    public static boolean isLogin = false;

    public static void parse(String data)
    {

        String[] datas = data.split("`");

        _id = Integer.parseInt(datas[0]);

        number = datas[1];
        name = datas[2];
        photo = datas[3];
        type = datas[4];
        password = datas[5];
        email = datas[6];
        phone = datas[7];
        birth = datas[8];

        salary = Integer.parseInt(datas[9]);
        expYear = Integer.parseInt(datas[10]);

        expData = datas[11];
        des = datas[12];
        regDate = datas[13];
        status = datas[14];
        link = datas[15];
        patient = datas[16];
        reserved = datas[17];
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
            activity.setTitle("照護銀行 - 看護: " + name);

            activity.doLogin();

            if(!link.equals("NULL"))
            {
                activity.doLink();
            }
        }
    }

    @Override
    public void doError(int resID) {

    }

    public static void reset()
    {
         _id = 0;
          number = null;
          name = null;
          photo = null;
          type = null;
          password = null;
          email = null;
          phone = null;
          birth = null;
          salary = 0;
          expYear = 0;
          expData = null;
          des = null;
          regDate = null;
          status = null;
          link = null;
          patient = null;
          reserved = null;
          isLogin = false;
    }
}
