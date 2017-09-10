package com.example.mac.gameclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestEtcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_etc);
    }

    class LoadJSON extends AsyncTask<String,String,String> {
        ProgressDialog dialog = new ProgressDialog(RestEtcActivity.this);
        @Override
        protected void onPreExecute() {//백그라운드 작업 전에 호출
            super.onPreExecute();
            dialog.setMessage("데이터 통신 중...");
            dialog.show();//프로그레스 다이얼로그 보여주기
        }
        @Override
        protected void onPostExecute(String s) {//백그라운드 작업 후에 호출
            super.onPostExecute(s);
            dialog.dismiss();//프로그레스 다이얼로그 닫기
            Log.i("onPostExecute",s);
        }
        @Override
        protected String doInBackground(String... params) {//실제 통신이 처리되는 부분
            StringBuilder output = new StringBuilder();
            try {//통신 부분은 반드시 try-catch로 예외처리 해주어야 한다
                URL url = new URL(params[0]);//전달받은 urlString으로 URL 객체 생성
                Log.i("url", params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    int resCode = conn.getResponseCode();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line;//웹서버로부터 수신되는 데이터를 처리
                    while((line = reader.readLine()) != null) {
                        output.append(line);//한줄씩 읽어서 StringBuilder 객체에 추가
                    }
                    reader.close();
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    public void clickAddNoticeButton(View view) {

    }

    public void clickGetNoticeButton(View view) {

    }

    public void clickEditNoticeButton(View view) {

    }

    public void clickDeleteNoticeButton(View view) {

    }

    public void clickGetNoticeListButton(View view) {
        new LoadJSON().execute("http://172.30.1.22:3000/notice/list?count=5&page=1");
    }

    public void clickAddVersionButton(View view) {

    }

    public void clickGetVersionButton(View view) {

    }
}
