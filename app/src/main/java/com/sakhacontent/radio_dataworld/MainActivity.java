package com.sakhacontent.radio_dataworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sakhacontent.radio_dataworld.Services.OnClearFromRecentService;

import java.io.IOException;
import java.io.StringReader;
/*
public class MainActivity extends AppCompatActivity implements Playable{

    ImageButton play;
    TextView title;
    int position = 0;
    NotificationManager notificationManager;
    List<Track> tracks;

    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
//        title = findViewById(R.id.title);
        populateTracks();

        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CreateNotification.createNotification(MainActivity.this, tracks.get(1), R.drawable.ic_baseline_pause, 1, tracks.size() -1);
                if(isPlaying){
                    onTrackPause();
                } else{
                    onPlay();
                }
            }
        });
    }
    private void createChannel(){
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID, "KOD Dev", NotificationManager.IMPORTANCE_LOW);
            notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    //populate list with tracks
    private void populateTracks(){
        tracks = new ArrayList<>();
        tracks.add(new Track("Track1", "Artist 1 ", R.drawable.t1));
        tracks.add(new Track("Track2", "Artist 2 ", R.drawable.t4));
        tracks.add(new Track("Track3", "Artist 3 ", R.drawable.t3));
        tracks.add(new Track("Track4", "Artist 4 ", R.drawable.t2));

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            switch (action){
                case CreateNotification.ACTIONPREVIOUS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if(isPlaying) {
                        onTrackPause();
                    } else {
                        onPlay();
                    }
                    break;
            }
        }
    };

    @Override
    public void onTrackPrevious() {

    }

    @Override
    public void onTrackNext() {

    }

    @Override
    public void onPlay() {
//        CreateNotification.createNotification(MainActivity.this, tracks.get(position),
//                R.drawable.ic_baseline_pause, tracks.size()-1);
        CreateNotification.createNotification(MainActivity.this, tracks.get(position),
                R.drawable.ic_baseline_pause, position, tracks.size() - 1);

//        CreateNotification.createNotification(MainActivity.this, tracks.get(position),
//                R.drawable.ic_baseline_pause, 0, 1);
        play.setImageResource(R.drawable.ic_play_arrow);
        play.setImageResource(R.drawable.ic_baseline_pause);
        //title.setText(tracks.get(position).getTitle());
        isPlaying = true;
    }

    @Override
    public void onTrackPause() {
        CreateNotification.createNotification(MainActivity.this, tracks.get(0),
                R.drawable.ic_play_arrow, 0, 1);
        play.setImageResource(R.drawable.ic_play_arrow);
        title.setText("Pause");
        isPlaying = false;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            notificationManager.cancelAll();
        }
        unregisterReceiver(broadcastReceiver);
    }
}
 */

public class MainActivity extends AppCompatActivity implements Playable{//, Runnable {

//ssk11    ImageButton play;
//ssk11    TextView title;
//    Handler handler = new Handler();

    NotificationManager notificationManager;


    int position = 0;
    boolean isPlaying = false;
    private WebView mywebView;
    private void InitializeWebView(){
        setContentView(R.layout.activity_main);

        mywebView =(WebView)findViewById(R.id.webview);
        //mywebView.setWebViewClient(new WebViewClient());
        mywebView.setWebChromeClient(new WebChromeClient());
        mywebView.loadUrl("https://radio.dataworld.pro/");
        WebSettings webSettings=mywebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }

        InitializeWebView();

        InitializeNotification();
//        run();
        /*
        run();*/

    }
    private void InitializeNotification(){
        //ssk11
        CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_play_arrow_38);

        isPlaying = false;

    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action){
                case CreateNotification.ACTION_PREVIUOS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (!isPause){
                        onTrackPause();
                    } else {
                        onPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    @Override
    public void onTrackPrevious() {

//        position--;
//        CreateNotification.createNotification(MainActivity.this, tracks.get(position),
//                R.drawable.ic_baseline_pause, position, tracks.size()-1);
//        title.setText(tracks.get(position).getTitle());

    }



    @Override
    public void onTrackPause() {
        loadJavascript("document.querySelector('.loading')");

    }

    @Override
    public void onTrackNext() {


    }

    @Override
    public void onPlay() {
        mywebView.loadUrl("javascript:document.querySelector('.play').click()");
        CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause_38);
        isPlaying = true;
        isPause = false;

    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

        System.out.println("ON_STOP");
//        CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause);
//        checkStatus();
//        if (isPause){
//            CreateNotification.createNotification(MainActivity.this, R.drawable.ic_play_arrow);
//
//        } else {
//            CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause);
//        }
        //Toast.makeText(getApplicationContext(), "onStop called", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        System.out.println("ON_CHANGE_FOCUS");
        if (!hasFocus)
        {
            checkStatus();
//            checkStatus();
//            if (isPause){
//                CreateNotification.createNotification(MainActivity.this, R.drawable.ic_play_arrow);
//
//            } else {
//                CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause);
//            }
//            Toast.makeText(getApplicationContext(), "onWindowFocusChange " + hasFocus, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
    }

    private void callbackfrompause(){
        mywebView.loadUrl("javascript:document.querySelector('.play').click()");
        CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_play_arrow_38);
        isPlaying = false;
        isPause = true;
    }

    private void CallBackPauseFromCheckStatus(){
        CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_play_arrow_38);
    }

    private void CallbBackPlayFromCheckStatus(){
        CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause_38);
    }


    public static boolean isTest = false;
    public static boolean isNull1 = true;
    public void loadJavascript(String javascript) {
        isNull1 = true;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // In KitKat+ you should use the evaluateJavascript method
            ValueCallback<String> callback = new ValueCallback<String>() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onReceiveValue(String s) {
                    isTest = false;
                    isNull1 = true;
                    JsonReader reader = new JsonReader(new StringReader(s));

                    // Must set lenient to parse single values
                    reader.setLenient(true);

                    try {
                        if(reader.peek() != JsonToken.NULL) {
                            isNull1 = false;
                            isTest = true;

                            if(reader.peek() == JsonToken.STRING) {
                                String msg = reader.nextString();
                                if(msg != null) {
//                                    Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            callbackfrompause();
                        }
                    } catch (IOException e) {
//                        Log.e("TAG", "MainActivity: IOException", e);
                    } finally {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            // NOOP
                        }

                    }
                }
            };
            mywebView.evaluateJavascript(javascript, callback);
        } else {

            mywebView.loadUrl("javascript:"+javascript);

        }

    }





    private boolean isPause = true;


    public void checkStatus() {
        String javascript = "homePage.audio.paused";
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // In KitKat+ you should use the evaluateJavascript method
            ValueCallback<String> callback = new ValueCallback<String>() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onReceiveValue(String s) {
                      JsonReader reader = new JsonReader(new StringReader(s));
                     // Must set lenient to parse single values
                    reader.setLenient(true);
                     try {
                        if(reader.peek() != JsonToken.NULL) {
                            if (s.equals("true")){
                                isPause = true;
                                CallBackPauseFromCheckStatus();
                            } else if ( s.equals("false")){
                                isPause = false;
                                CallbBackPlayFromCheckStatus();
                            }
                        } else {
                         }
                    } catch (IOException e) {
//                        Log.e("TAG", "MainActivity: IOException", e);
                    } finally {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            // NOOP
                        }

                    }
                }
            };
            mywebView.evaluateJavascript(javascript, callback);
        } else {

            mywebView.loadUrl("javascript:"+javascript);

        }

    }




//    @Override
//    public void run() {
//        handler.postDelayed(this, 500);
//        while (true)
//        System.out.println("RUUUUUUUUUUUUUN");
        /*
        handler.postDelayed(this, 500);
        checkStatus();
        if (isPause){
            CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause);
        } else {
            CreateNotification.createNotification(MainActivity.this, R.drawable.ic_play_arrow);
        }*/
        //homePage.audio.paused
//    }
}
