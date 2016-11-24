package com.fctm.actionbartabdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class LabDBHelper
{
    private DBHelper helper = null;

    public LabDBHelper(Context context)
    {
        // 建立SQLiteOpenHelper來操作資料庫
        // 當最後一個參數版本號變動時, onUpgrade方法會被呼叫, 當資料庫結構在改版時有變動時, 
        // 可以將變動的程式碼寫在onUpgrade裡面
        helper = new DBHelper(context, "Labfile", null, 4);
    }


    // 透過SQLiteOpenHelper來操作資料庫
    private class DBHelper extends SQLiteOpenHelper
    {
        // name參數為資料庫名稱
        public DBHelper(Context context, String name, CursorFactory factory, int version)
        {
            super(context, name, factory, version);
            // TODO Auto-generated constructor stub
        }

        // 當資料庫不存在的時候, Android系統自動建立資料庫時, 這個方法會被觸發
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // 建立要存放資料的資料表格(相當於Excel的sheet)
            // 1. SQL語法不分大小寫
            // 2. 這裡大寫代表的是SQL標準語法, 小寫字是資料表/欄位的命名
            db.execSQL("CREATE TABLE labs(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "blood_glucose TEXT," +
                    "blood_pressure TEXT," +
                    "palpitant TEXT," +
                    "pulse TEXT," +
                    "datetime TEXT," +
                    "status TEXT," +
                    "memberid TEXT)");
        }

        // 當資料庫版本增加的時候會被呼叫（也就是DBHelper建構式最後一個參數）
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try
            {
                // 在這裡先把原本資料讀出來

                // 刪除資料表
                db.execSQL("DROP TABLE IF EXISTS labs");

                // 重新呼叫onCrate來建立新的Table
                onCreate(db);

                // 把舊的資料寫到新的Table
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    // 用來存放每一筆資料的Inner類別
    public static class LabData
    {
        public long id;        // 1ID
        public String blood_glucose;     // 2血糖
        public String blood_pressure; // 3血壓
        public String palpitant; // 4心跳
        public String pulse; // 5脈搏
        public String datetime;   // 6時間
        public String status;
        public String memberid;
    }

    // 增

    public void add(String blood_glucose, String blood_pressure,String palpitant,String pulse ,String datetime,String status,String memberid)
    {
        // 取出資料庫物件, 並且是可以寫入狀態
        // 當APP空間不夠時, 該方法會丟出例外
        SQLiteDatabase db = helper.getWritableDatabase();

        // 定義要新增的資料
        ContentValues values = new ContentValues();
        values.put("blood_glucose", blood_glucose);
        values.put("blood_pressure", blood_pressure);
        values.put("palpitant", palpitant);
        values.put("pulse", pulse);
        values.put("datetime", datetime);
        values.put("status",status);
        values.put("memberid",memberid);


        // 新增一筆資料到資料表(Table)
        db.insert("labs", null, values);

        // 釋放SQLiteDatabase資源
        db.close();

        Log.d("LabDBHelper", "add");
    }

    // 更新單字
    public void update(String datetime, LabData v)
    {
        // 取出資料庫物件, 並且是可以寫入狀態
        // 當APP空間不夠時, 該方法會丟出例外
        SQLiteDatabase db = helper.getWritableDatabase();

        // 定義要新增的資料
        ContentValues values = new ContentValues();
        values.put("blood_glucose", v.blood_glucose);
        values.put("blood_pressure", v.blood_pressure);
        values.put("palpitant", v.palpitant);
        values.put("pulse", v.pulse);
        values.put("datetime", v.datetime);
    //    values.put("status", v.status);
    //    values.put("memberid", v.memberid);
        // 更新資料到資料表(Table)
        db.update("labs", values, "datetime='" + datetime + "'", null);

        // 釋放資源
        db.close();

        Log.d("LabDBHelper", "update");
    }

    public LabData get(String datetime)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        // 準備回傳結果用的物件
        LabData v = new LabData();

        // 執行查詢
        Cursor c = db.query("labs", new String[]{"_id, blood_glucose, blood_pressure,palpitant,pulse, datetime,status,memberid"},
                "datetime='" + datetime + "'", null, null, null, null, null);

        // 如果有查詢結果
        if (c.moveToFirst())
        {
            // 讀取包裝一筆資料的物件
            v.id = c.getLong(c.getColumnIndex("_id"));    // 取出名字欄位資料
            v.blood_glucose = c.getString(c.getColumnIndex("blood_glucose"));    // 取出名字欄位資料
            v.blood_pressure = c.getString(c.getColumnIndex("blood_pressure"));    // 取出名字欄位資料
            v.palpitant = c.getString(c.getColumnIndex("palpitant"));    // 取出名字欄位資料
            v.pulse = c.getString(c.getColumnIndex("pulse"));    // 取出名字欄位資料
            v.datetime = c.getString(c.getColumnIndex("datetime"));     // 取出翻卡片次數資料
            v.status=c.getColumnName(c.getColumnIndex("status"));
            v.memberid=c.getColumnName(c.getColumnIndex("memberid"));
        }

        // 關閉Cursor物件
        c.close();
        db.close();

        // 回傳結果
        return v;
    }

    public int getCount()
    {
        // 取得唯讀模式資料庫
        SQLiteDatabase db = helper.getReadableDatabase();

        // 透過query來查詢資料
        Cursor c = db.query("labs",    // 資料表名字 
                new String[]{"datetime"},  // 要取出的欄位資料
                null,                 // 查詢條件式 
                null,                 // 查詢條件值字串陣列
                null,                 // Group By字串語法 
                null,                 // Having字串法 
                null,                 // Order By字串語法(排序) 
                null);                // Limit字串語法

        int count = c.getCount();

        Log.d("LabDBHelper", "getCount=" + count);

        // 釋放資源
        c.close();
        db.close();

        return count;
    }

    // 取得所有單字
    public ArrayList<LabData> getAllData()
    {

        ArrayList<LabData> result = new ArrayList<LabData>();
        // 取得唯讀模式資料庫
        SQLiteDatabase db = helper.getReadableDatabase();
        // 透過query來查詢資料
        Cursor c = db.query("labs",                           // 資料表名字 
                new String[]{"_id", "blood_glucose", "blood_pressure","palpitant","pulse", "datetime","status","memberid"},  // 要取出的欄位資料
                null,                                        // 查詢條件式 
                null,                                        // 查詢條件值字串陣列
                null,                                        // Group By字串語法 
                null,                                        // Having字串法 
                "datetime",                                  	  // Order By字串語法(排序)
                null);                                       // Limit字串語法

       // Log.d("LabDBHelper", "Query:" + c.getCount());

        while(c.moveToNext())
        {
            LabData d = new LabData();
            d.id = c.getLong(c.getColumnIndex("_id"));    // 取出名字欄位資料
            d.blood_glucose = c.getString(c.getColumnIndex("blood_glucose"));    // 取出名字欄位資料
            d.blood_pressure = c.getString(c.getColumnIndex("blood_pressure"));    // 取出名字欄位資料
            d.palpitant = c.getString(c.getColumnIndex("palpitant"));    // 取出名字欄位資料
            d.pulse = c.getString(c.getColumnIndex("pulse"));    // 取出名字欄位資料
            d.datetime = c.getString(c.getColumnIndex("datetime"));     // 取出翻卡片次數資料
            d.status=c.getColumnName(c.getColumnIndex("status"));
            d.memberid=c.getColumnName(c.getColumnIndex("memberid"));

            result.add(d);

        }

        // 釋放資源
        c.close();
        db.close();

        return result;
    }

    public void delete(String datetime)
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("labs", "datetime='" + datetime + "'", null);
        db.close();
    }

    public boolean isExisting(String datetime)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        // 執行查詢
        Cursor c = db.query("labs", new String[]{"datetime"},
                "datetime='" + datetime + "'", null, null, null, null, null);

        // 如果有查詢結果
        if (c.moveToFirst())
        {
            return true;
        }

        return false;
    }

    // 清除資料庫全部資料
    public void clear()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("labs", null, null);
        db.close();
    }
}
