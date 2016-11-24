package com.fctm.actionbartabdrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class InterNetConnect extends Fragment implements IHttpCompleted {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.interconnect, container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showView();






    }

    public String encode(String value) throws Exception {
        return URLEncoder.encode(value, "utf-8");
    }

    public void showView() {

        TextView txtView = (TextView) getActivity().findViewById(R.id.txtView);

        demoSaveFile(txtView);
        demoReadFile(txtView);
        demoInsertDB(txtView);
        demoUpdateDB(txtView);
        demoReadDB(txtView);
        demoDeleteDB(txtView);
        login(txtView);
        logout(txtView);

    }

    void demoSaveFile(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "saveFile", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask

        try {


            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {
        }
    }

    void demoReadFile(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "readFile", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask

        try {
            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {
        }
    }

    void demoInsertDB(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "insertDB", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask

        try {
            t.get();  // 一直等到AsyncTask執行完畢再繼續


        } catch (Exception e) {
        }
    }

    void demoCreateTable(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "updateDB", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask
        try {


            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {


        }

    }

    void demoUpdateDB(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "updateDB", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask

        try {
            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {
        }
    }

    void demoReadDB(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "readDB", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask

        try {
            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {
        }
    }

    void demoDeleteDB(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "deleteDB", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask

        try {
            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {
        }
    }

    void login(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "login", "uch04", "6666ss--");
        t.execute(); // 啟動AsyncTask

        try {
            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {
        }
    }

    void logout(TextView txtView) {
        MyAsyncTask t = new MyAsyncTask(txtView, "logout", "", "-1");
        t.execute(); // 啟動AsyncTask

        try {
            t.get();  // 一直等到AsyncTask執行完畢再繼續
        } catch (Exception e) {
        }
    }

    @Override
    public void doNotify(String result) {


        Toast.makeText(getActivity(),"InterNetdoNotify\n"+result,Toast.LENGTH_LONG).show();


    }

    @Override
    public void doError(int resID) {
        Toast.makeText(getActivity(),"InterNetdoError"+resID,Toast.LENGTH_LONG).show();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        TextView txtView;
        String action;
        String extraString;
        String extraInt;

        public MyAsyncTask(TextView txtView, String action, String extraString, String extraInt) {
            this.txtView = txtView;
            this.action = action;
            this.extraString = extraString;
            this.extraInt = extraString;
        }

        @Override
        protected void onPostExecute(String s) {
            txtView.setText(txtView.getText() + "\n" + action + ": " + s);
            super.onPostExecute(s);
        }


        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            try {
                URL url = new URL("http://52.11.214.88:8080/4/schedul?action=" + action +
                        "&extraString=" + extraString +
                        "&extraString=" + extraString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                String line;
                while ((line = in.readLine()) != null) {
                    result = line; // 最後一行則是我們要的
                }
            } catch (Exception e) {
                result = e.getMessage();
            }

            return result;
        }
    }


}
