package com.fctm.actionbartabdrawer;


import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LabGPS extends Fragment implements LocationListener {


    /*
    * 自己抓外勞的位置 ,送給主机 ,然後 主机再把經緯度給定位去顯示圖
    *  double lat = location.getLatitude(); // 緯度
    *   double lng = location.getLongitude(); // 經度
    *
    * */
    WebView myWebView;
    TextView info;
    // 首頁位置
    private static final String MAP_URL = "file:///android_asset/www/index.html";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.gps, container, false);
        ImageView getMe = (ImageView)v.findViewById(R.id.getMe);
        getMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 將Google地圖拉回目前座標

                    // 判斷有無位置資訊了
                    if (loc == null) return;

                    // 取得經緯度
                    double lat = loc.getLatitude();  // 緯度
                    double lng = loc.getLongitude(); // 經度

                    // 呼叫JavaScript
                    myWebView.loadUrl("javascript:centerAt(" + lat + ", " + lng + ");");
                }


        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initview();
    }

    public void initview() {


        myWebView = (WebView) getActivity().findViewById(R.id.webview);
        info = (TextView) getActivity().findViewById(R.id.info);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(MAP_URL);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                setupLocationService();
            }
        });
    }

    LocationManager lm = null;
    // 初始化定位服務

    private void setupLocationService() {
// 取得LocationManager
        lm = (LocationManager) (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
// 判斷GPS定位服務或Network定位服務是否有打開
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
// 顯示錯誤訊息
            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
           // dlg.setTitle("警告"); ann
          //  dlg.setMessage("請至少開啟GPS或網路來使用定位服務");
          //  dlg.show();
            return;
        }
// 如果網路定位服務有開啟
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(getActivity(), "NETWORK定位已開啟.", Toast.LENGTH_SHORT).show();
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 要使用的Provider
                    1000, // 定位間格時間（毫秒）
                    0, // 位置變更多遠要觸發listener(公尺)
                    this);
        }
// 如果網路定位服務有開啟
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getActivity(), "GPS定位已開啟.", Toast.LENGTH_SHORT).show();
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 要使用的Provider
                    1000, // 定位間格時間（毫秒）
                    0, // 位置變更多遠要觸發listener(公尺)
                    this);
        }
    }

    Location loc;

    // 座標更新時會觸發的方法
    @Override
    public void onLocationChanged(Location location) {
// 存下最新位置資訊
        loc = location;
// 取得經緯度
        double lat = location.getLatitude(); // 緯度
        double lng = location.getLongitude(); // 經度
// 更新最新資訊到TextView上
        float acc = location.getAccuracy(); // 單位: 公尺
        float bea = location.getBearing(); // 方向
        float spd = location.getSpeed(); // 速度: 小時/公尺
        float time = location.getTime(); // 該資訊的時間(1970年1月1日到現在經過的毫秒, UTC)
        info.setText("Latitude: " + lat + "\n" +
                "Longitude: " + lng + "\n" +
                "Accuracy: " + acc + "\n" +
                "Bearing: " + bea + "\n" +
                "Speed: " + spd + "\n" +
                "Time: " + time);
        myWebView.loadUrl("javascript:centerAt(" + lat + ", " + lng + ");");
        Toast.makeText(getActivity(), "位置更新: " + lat + ", " + lng + "(" +
                location.getProvider() + ")", Toast.LENGTH_LONG).show();
    }

    // 定位服務狀態變更時會觸發的方法
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Toast.makeText(getActivity(), provider + "服務中", Toast.LENGTH_SHORT).show();
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Toast.makeText(getActivity(), provider + "沒有服務", Toast.LENGTH_SHORT).show();
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Toast.makeText(getActivity(), provider + "暫時不可使用", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 定位服務開啟時會觸發的方法
    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity(), provider + "被啟動了.", Toast.LENGTH_LONG).show();
    }

    // 定位服務被關閉時會觸發的方法
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), provider + "被關閉了.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        // 關閉定位, 否則定位服務在離開app後仍然會一直定位
        lm.removeUpdates(this);

        super.onDestroy();
    }



}
