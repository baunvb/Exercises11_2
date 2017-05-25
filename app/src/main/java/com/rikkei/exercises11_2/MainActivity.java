package com.rikkei.exercises11_2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ProviderInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText edtURl;
    private Button btnButton;
    private TextView tvResult;
    private HttpURLConnection connection;
    private Thread thread;
    private DownloadReceiver downloadReceiver;
    private MainReceiver mainReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainReceiver = new MainReceiver();
        IntentFilter f = new IntentFilter();
        f.addAction("result");
        registerReceiver(mainReceiver, f);
        initViews();
    }

    private void initViews() {
        edtURl = (EditText) findViewById(R.id.edt_url);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btnButton = (Button) findViewById(R.id.btn_getline);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = edtURl.getText().toString();
                Intent downloadIntent = new Intent();
                downloadIntent.setAction("action_download");
                downloadIntent.putExtra("link", path);
                sendBroadcast(downloadIntent);
                return;

            }
        });
    }

    private class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int c = intent.getIntExtra("count", 0);
            tvResult.setText(c+"");
        }
    }

//    public void getLine(final String path){
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(path);
//                    int count = 0;
//                    connection = (HttpURLConnection)url.openConnection();
//                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                    while (in.readLine() != null){
//                        count++;
//                    }
//                    tvResult.setText(count+"");
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e){
//                    e.printStackTrace();
//                } finally {
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
//            }
//        });
//        thread.start();
//    }

}
