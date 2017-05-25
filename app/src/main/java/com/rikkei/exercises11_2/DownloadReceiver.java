package com.rikkei.exercises11_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bau NV on 5/25/2017.
 */

public class DownloadReceiver extends BroadcastReceiver {
    private HttpURLConnection connection;
    private Context mContext;
    private int count;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String link = intent.getStringExtra("link");
        download(link);
    }

    public void download(final String urlString){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    count = 0;
                    connection = (HttpURLConnection)url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while (in.readLine() != null){
                        count++;
                    }
                    Intent resultIntent = new Intent();
                    resultIntent.setAction("result");
                    resultIntent.putExtra("count", count);
                    mContext.sendBroadcast(resultIntent);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
   
}
