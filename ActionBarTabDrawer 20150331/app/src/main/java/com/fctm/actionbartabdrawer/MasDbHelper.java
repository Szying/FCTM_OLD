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

public class MasDbHelper {
    private DBHelper helper = null;

    public MasDbHelper(Context context) {
        // 建立SQLiteOpenHelper來操作資料庫
        // 當最後一個參數版本號變動時, onUpgrade方法會被呼叫, 當資料庫結構在改版時有變動時,
        // 可以將變動的程式碼寫在onUpgrade裡面
        helper = new DBHelper(context, "Masfile", null, 4);
    }


    // 透過SQLiteOpenHelper來操作資料庫
    private class DBHelper extends SQLiteOpenHelper {
        // name參數為資料庫名稱
        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
            // TODO Auto-generated constructor stub
        }

        // 當資料庫不存在的時候, Android系統自動建立資料庫時, 這個方法會被觸發
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 建立要存放資料的資料表格(相當於Excel的sheet)
            // 1. SQL語法不分大小寫
            // 2. 這裡大寫代表的是SQL標準語法, 小寫字是資料表/欄位的命名
            db.execSQL("CREATE TABLE mas(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "sch TEXT," +
                    "timebeg TEXT," +
                    "timeend Text," +
                    "workitem Text," +
                    "status Text," +
                    "memberid TEXT)");
        }

        // 當資料庫版本增加的時候會被呼叫（也就是DBHelper建構式最後一個參數）
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                // 在這裡先把原本資料讀出來

                // 刪除資料表
                db.execSQL("DROP TABLE IF EXISTS mas");

                // 重新呼叫onCrate來建立新的Table
                onCreate(db);

                // 把舊的資料寫到新的Table
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 用來存放每一筆資料的Inner類別
    public static class Vocabulary {
        public long id;        // ID
        public String sch;     // 生字
        public String timebeg; // 內容
        public String timeend;   // 加入的時間
        public String workitem;
        public String status;
        public String memberid;

    }

    // 新增一筆新的成績
    public void add(String sch, String timebeg, String timeend, String workitem, String status, String memberid) {
        // 取出資料庫物件, 並且是可以寫入狀態
        // 當APP空間不夠時, 該方法會丟出例外
        SQLiteDatabase db = helper.getWritableDatabase();

        // 定義要新增的資料
        ContentValues values = new ContentValues();
        values.put("sch", sch);
        values.put("timebeg", timebeg);
        values.put("timeend", timeend);
        values.put("workitem", workitem);
        values.put("status", status);
        values.put("memberid", memberid);
        // 新增一筆資料到資料表(Table)
        db.insert("mas", null, values);

        // 釋放SQLiteDatabase資源
        db.close();


    }

    // 更新單字
    public void update(int positon, Vocabulary v) {
        // 取出資料庫物件, 並且是可以寫入狀態
        // 當APP空間不夠時, 該方法會丟出例外
        SQLiteDatabase db = helper.getWritableDatabase();

        // 定義要新增的資料
        ContentValues values = new ContentValues();
        values.put("sch", v.sch);
        values.put("timebeg", v.timebeg);
        values.put("timeend", v.timeend);
        values.put("workitem", v.workitem);
        values.put("status", v.status);
        values.put("memberid", v.memberid);
        // 更新資料到資料表(Table)
        db.update("mas", values, "_id=" + positon, null);

        // 釋放資源
        db.close();


    }

    public Vocabulary get(String sch) {
        SQLiteDatabase db = helper.getReadableDatabase();

        // 準備回傳結果用的物件
        Vocabulary v = new Vocabulary();

        // 執行查詢
        Cursor c = db.query("mas", new String[]{"_id, sch, timebeg, timeend", "workitem","status",",memberid"},
                "sch='" + sch + "'", null, null, null, null, null);

        // 如果有查詢結果
        if (c.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            v.id = c.getLong(c.getColumnIndex("_id"));    // 取出名字欄位資料
            v.sch = c.getString(c.getColumnIndex("sch"));    // 取出名字欄位資料
            v.timebeg = c.getString(c.getColumnIndex("timebeg"));    // 取出名字欄位資料
            v.timeend = c.getString(c.getColumnIndex("timeend"));     // 取出翻卡片次數資料
            v.workitem = c.getString(c.getColumnIndex("workitem"));
            v.status = c.getString(c.getColumnIndex("status"));
            v.memberid = c.getString(c.getColumnIndex("memberid"));

        }

        // 關閉Cursor物件
        c.close();
        db.close();

        // 回傳結果
        return v;
    }

    public int getCount() {
        // 取得唯讀模式資料庫
        SQLiteDatabase db = helper.getReadableDatabase();

        // 透過query來查詢資料
        Cursor c = db.query("mas",    // 資料表名字
                new String[]{"sch"},  // 要取出的欄位資料
                null,                 // 查詢條件式
                null,                 // 查詢條件值字串陣列
                null,                 // Group By字串語法
                null,                 // Having字串法
                null,                 // Order By字串語法(排序)
                null);                // Limit字串語法

        int count = c.getCount();

        // 釋放資源
        c.close();
        db.close();

        return count;
    }

    // 取得所有單字
    public ArrayList<Vocabulary> getAllData() {
        ArrayList<Vocabulary> result = new ArrayList<Vocabulary>();
        // 取得唯讀模式資料庫
        SQLiteDatabase db = helper.getReadableDatabase();
        // 透過query來查詢資料
       // String sql;
       //  sql = "SELECT * FROM mas";
       //  Cursor c = db.rawQuery(sql, null);

        Cursor c = db.query("mas",                           // 資料表名字
                new String[]{"_id", "sch", "timebeg", "timeend", "workitem","status","memberid"},  // 要取出的欄位資料
                null,                                        // 查詢條件式
                null,                                        // 查詢條件值字串陣列
                null,                                        // Group By字串語法
                null,                                        // Having字串法
                "timebeg",                                      // Order By字串語法(排序)
                null);                                       // Limit字串語法

        Log.d("ANNLOG", "getAllData :" + c.getCount());
        while (c.moveToNext()) {
            Vocabulary d = new Vocabulary();
            d.id = c.getLong(c.getColumnIndex("_id"));
            d.sch = c.getString(c.getColumnIndex("sch"));
            d.timebeg = c.getString(c.getColumnIndex("timebeg"));
            d.timeend = c.getString(c.getColumnIndex("timeend"));
            d.workitem = c.getString(c.getColumnIndex("workitem"));
          //  d.status = c.getString(c.getColumnIndex("status"));
          //  d.memberid = c.getString(c.getColumnIndex("memberid"));
            result.add(d);
        }

        // 釋放資源
        c.close();
        db.close();

        return result;
    }

    public void delete(int position) {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("mas", "_id=" + position + "", null);
        db.close();
    }

    public boolean isExisting(String sch) {
        SQLiteDatabase db = helper.getReadableDatabase();

        // 執行查詢
        Cursor c = db.query("mas", new String[]{"_id"},
                "_id=" + sch + "", null, null, null, null, null);

        // 如果有查詢結果
        if (c.moveToFirst()) {
            return true;
        }

        return false;
    }

    // 清除資料庫全部資料
    public void clear() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("mas", null, null);
        db.close();
    }


    // 取得外傭的額外行程Cursors
    public ArrayList<Vocabulary> getExtraSch() {
        ArrayList<Vocabulary> result = new ArrayList<Vocabulary>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql;
        sql = "SELECT * FROM mas WHERE sch  <> '固定排程' ";
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            Vocabulary d = new Vocabulary();
            d.id = c.getLong(c.getColumnIndex("_id"));
            d.sch = c.getString(c.getColumnIndex("sch"));
            d.workitem = c.getString(c.getColumnIndex("workitem"));
            d.timebeg = c.getString(c.getColumnIndex("timebeg"));
            d.timeend = c.getString(c.getColumnIndex("timeend"));
        //    d.status = c.getString(c.getColumnIndex("status"));
        //    d.memberid = c.getString(c.getColumnIndex("memberid"));
            result.add(d);
        }
        c.close();
        db.close();
        return result;
    }

    // 外傭的每日固定行程
    public ArrayList<Vocabulary> getSch() {
        ArrayList<Vocabulary> result = new ArrayList<Vocabulary>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql;
        //sql = "SELECT * FROM mas WHERE sch ='EveryDay Work' ORDER BY timebeg";
        sql = "SELECT * FROM mas WHERE sch ='固定排程'  ORDER BY timebeg ";
        Cursor c = db.rawQuery(sql, null);
        Log.d("ANNLOG", "getSch :" + c.getCount());
        while (c.moveToNext()) {
            Vocabulary d = new Vocabulary();
            d.id = c.getLong(c.getColumnIndex("_id"));
            d.sch = c.getString(c.getColumnIndex("sch"));
            d.workitem = c.getString(c.getColumnIndex("workitem"));
            d.timebeg = c.getString(c.getColumnIndex("timebeg"));
            d.timeend = c.getString(c.getColumnIndex("timeend"));
        //    d.status = c.getString(c.getColumnIndex("status"));
        //    d.memberid = c.getString(c.getColumnIndex("memberid"));
            result.add(d);
        }
        c.close();
        db.close();
        return result;
    }

}