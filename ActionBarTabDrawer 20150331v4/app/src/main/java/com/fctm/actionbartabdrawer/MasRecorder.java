package com.fctm.actionbartabdrawer;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Liszying on 2015/3/21.
 */
public class MasRecorder extends Fragment {

    private ImageButton record = null;
    private ImageButton stop = null;
    private TextView info = null;
    private ListView reclist = null;
    private SimpleAdapter recordSimpleAdapter = null;
    private MediaRecorder mediaRecorder = null;
    private boolean sdcardExists = false; // 判斷sd卡是否存在
    private File recordAudioSaveFileDir = null; // 儲存所有音頻檔案的資料夾
    private File recordAudioSaveFile = null;    // 每次儲存音頻檔案的名稱
    private String recordAudioSaveFileName = null;    // 每次儲存音頻檔案的名稱
    private String recDir = "rec"; // 儲存的目錄名稱
    private boolean isRecord = false;    // 錄音的標志
    private List<Map<String, Object>> recordFiles = null;
    private MediaPlayer mp;
    public String uri;
    private Timer timer;
    private SeekBar seekbar;
    private SeekBarTask SBTask;
    private Button bplay, bstop;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.masrecorder_main, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.record = (ImageButton) getActivity().findViewById(R.id.record);
        this.stop = (ImageButton) getActivity().findViewById(R.id.stop);
        this.info = (TextView) getActivity().findViewById(R.id.info);
        this.recordFiles = new ArrayList<Map<String, Object>>();
        this.reclist = (ListView) getActivity().findViewById(R.id.reclist);
        // 若果存在則將狀態給了sdcardExists屬性
        if ((this.sdcardExists = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))) { // 判斷sd卡是否存在
            this.recordAudioSaveFileDir = new File(Environment
                    .getExternalStorageDirectory().toString()
                    + File.separator
                    + MasRecorder.this.recDir + File.separator);
            if (!this.recordAudioSaveFileDir.exists()) { // 資料夾不存在
                this.recordAudioSaveFileDir.mkdirs(); // 建立資料夾
            }
        }
        this.stop.setEnabled(false);    // 按鈕現在不可用
        this.record.setOnClickListener(recordOnClick);
        this.stop.setOnClickListener(stopOnClick);
        this.reclist.setOnItemClickListener(reclistOnItemClick);
        this.getRecordFiles();


    }


    private View.OnClickListener recordOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (MasRecorder.this.sdcardExists) {    // 若果sd卡存在

                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd_hh-mm-ss");
                String date = sDateFormat.format(new java.util.Date());

                MasRecorder.this.recordAudioSaveFileName = MasRecorder.this.recordAudioSaveFileDir
                        .toString()
                        + File.separator
                        + "Record_"
                        + date + ".mp3";    // 每次的錄音檔名稱都不一樣
                MasRecorder.this.recordAudioSaveFile = new File(
                        MasRecorder.this.recordAudioSaveFileName);
                MasRecorder.this.mediaRecorder = new MediaRecorder(); // 案例化物件
                // 在進行錄制之前必須組態許多個參數
                MasRecorder.this.mediaRecorder
                        .setAudioSource(MediaRecorder.AudioSource.MIC); // 音頻來源是MIC
                MasRecorder.this.mediaRecorder
                        .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                MasRecorder.this.mediaRecorder
                        .setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                MasRecorder.this.mediaRecorder
                        .setOutputFile(MasRecorder.this.recordAudioSaveFileName);
                try {    // 進入到就緒狀態
                    MasRecorder.this.mediaRecorder.prepare();
                } catch (Exception e) {
                    // Log.i("MainActivity", e.toString()) ;
                }
                MasRecorder.this.mediaRecorder.start();    // 開始錄音
                MasRecorder.this.info.setText("正在錄音中...");
                MasRecorder.this.stop.setEnabled(true); // 停止錄音按鈕可以使用了
                MasRecorder.this.record.setEnabled(false);
                MasRecorder.this.isRecord = true;    // 正在錄音
            }
        }
    };

    private View.OnClickListener stopOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (MasRecorder.this.isRecord) {    // 正在錄音
                MasRecorder.this.mediaRecorder.stop();    // 停止
                MasRecorder.this.mediaRecorder.release();    // 釋放資源
                MasRecorder.this.record.setEnabled(true);
                MasRecorder.this.stop.setEnabled(false);
                MasRecorder.this.info.setText("錄音結束，檔案路徑為："
                        + MasRecorder.this.recordAudioSaveFileName);
                MasRecorder.this.getRecordFiles();
            }
        }
    };

    private void getRecordFiles() {    // 將一個資料夾之中的全部檔案列出
        this.recordFiles = new ArrayList<Map<String, Object>>();
        if (this.sdcardExists) {    // 有sd卡存在
            File files[] = this.recordAudioSaveFileDir.listFiles();    // 列出目錄中的檔案
            for (int x = 0; x < files.length; x++) {
                Map<String, Object> fileInfo = new HashMap<String, Object>();
                fileInfo.put("filename", files[x].getName());
                this.recordFiles.add(fileInfo);
            }
            this.recordSimpleAdapter = new SimpleAdapter(getActivity(),
                    this.recordFiles, R.layout.recordfiles,
                    new String[]{"filename"}, new int[]{R.id.filename});
            this.reclist.setAdapter(this.recordSimpleAdapter);
        }
    }

    ;


    public AdapterView.OnItemClickListener reclistOnItemClick = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            if (MasRecorder.this.recordSimpleAdapter.getItem(position) instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) MasRecorder.this.recordSimpleAdapter
                        .getItem(position);

                uri = MasRecorder.this.recordAudioSaveFileDir.toString() + File.separator
                        + map.get("filename");  //按下去播放的檔案路徑
          /*      Uri uri = Uri
                        .fromFile(new File(MasRecorder.this.recordAudioSaveFileDir
                                .toString()
                                + File.separator
                                + map.get("filename")));*/

           /*     Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "audio/mp3");
                MasRecorder.this.startActivity(intent);*/
            /*
                //new一個intent物件，並指定Activity切換的class
                Intent intent1 = new Intent();
               // intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setClass(MasRecorder.this, SoundPlayer1.class);
               // intent1.putExtra("uri",uri);
                //intent1.setDataAndType(uri, "audio/mp3");
                Bundle bundle = new Bundle();
                bundle.putString("path", uri);
                intent1.putExtras(bundle);
                //切換Activity
                startActivity(intent1);*/


                Dialog PlayerDlg = new Dialog(getActivity()); //跳出播放音樂對話盒

                PlayerDlg.setContentView(R.layout.activity_sound_player1);
                PlayerDlg.setTitle("播放錄音");
                PlayerDlg.setCancelable(true);
                PlayerDlg.show();
                TextView mtxtPlayerFileName;
                mtxtPlayerFileName = (TextView) PlayerDlg.findViewById(R.id.txtPlayFileName);
                String PlayerFileName = (map.get("filename")).toString();

                Toast.makeText(getActivity(), PlayerFileName, Toast.LENGTH_LONG).show();
                mtxtPlayerFileName.setText("正在播放" + "/n" + PlayerFileName);


                bplay = (Button) PlayerDlg.findViewById(R.id.btn_Play);
                bstop = (Button) PlayerDlg.findViewById(R.id.btn_Stop);
                seekbar = (SeekBar) PlayerDlg.findViewById(R.id.seekbar);

                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        if (fromUser && mp != null) {
                            mp.seekTo(progress);

                        } else if (!fromUser) {
                            info.setText(progress + "/" + seekbar.getMax());
                        }
                    }
                });

                bplay.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if (mp == null) {
                            //產生Mediaplayer物件
                            mp = new MediaPlayer();
                            timer = new Timer();

                            try {
                                mp.reset(); // 清除緩衝區資料
                                mp.setDataSource(uri); // 設定音樂檔案路徑
                                mp.prepare(); // 緩衝音樂
                                mp.start();
                                seekbar.setMax(mp.getDuration());
                                SBTask = new SeekBarTask();
                                timer.scheduleAtFixedRate(SBTask, 0, 200);


                            } catch (IOException e) {
                                Toast.makeText(getActivity(), "錯誤: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else if (mp.isPlaying()) {
                            // 當作暫停處理
                            mp.pause();
                        } else if (!mp.isPlaying()) {
                            // 當作繼續處理
                            mp.start();
                        }

                    }
                });

                bstop.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (mp != null) {
                            mp.stop();
                            mp = null;
                            SBTask.cancel();
                            seekbar.setProgress(0);
                        } else if (mp != null && mp.isPlaying()) {
                            mp = null;
                            timer.cancel();
                            timer = null;
                            mp.reset();
                            mp = null;
                        }
                    }
                });
            }
        }
    };

    private class SeekBarTask extends TimerTask {
        @Override
        public void run() {
            seekbar.setProgress(mp.getCurrentPosition());
        }
    }

}
