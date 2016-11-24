package com.aaronlife.thecarebank.nurse;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aaron on 3/26/15.
 */
public class AsyncHttpConnection extends AsyncTask<Void, Void, String>
{
    IHttpCompleted callback;
    String action;
    String type;
    String data1;
    String data2;

    public AsyncHttpConnection(IHttpCompleted callback, String action, String type, String data1, String data2)
    {
        this.callback = callback;
        this.action = action;
        this.type = type;
        this.data1 = data1;
        this.data2 = data2;
    }

    @Override
    protected void onPostExecute(String s)
    {
        callback.doNotify(s);
        super.onPostExecute(s);
    }


    @Override
    protected String doInBackground(Void... params)
    {
        String result = "";
        try
        {
            URL url = new URL("http://52.11.214.88:8080/6/core?action=" + action +
                    "&type=" + type +
                    "&data1=" + data1 +
                    "&data2=" + data2);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            while ((line = in.readLine()) != null)
            {
                result = line; // 最後一行才是我們要的
            }
        }
        catch(Exception e)
        {
            result = e.getMessage();
        }

        return result;
    }
}
