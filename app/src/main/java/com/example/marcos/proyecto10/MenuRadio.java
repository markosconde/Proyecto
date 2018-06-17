package com.example.marcos.proyecto10;

import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class MenuRadio extends AppCompatActivity {
    private Button europafm, maximafm, kissfm, principales, c100, cdial, cope, ser, ondacero, play;

    private MediaPlayer mp;
    private String url = "http://icecast-streaming.nice264.com/europafm";  //http://icecast-streaming.nice264.com/europafm
    private Boolean prepared = false;
    private Boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_radio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        europafm = (Button) findViewById(R.id.europafm);
        maximafm = (Button) findViewById(R.id.maximafm);
        kissfm = (Button) findViewById(R.id.kissfm);
        principales = (Button) findViewById(R.id.principales);
        c100 = (Button) findViewById(R.id.c100);
        cdial = (Button) findViewById(R.id.cdial);
        cope = (Button) findViewById(R.id.cope);
        ser = (Button) findViewById(R.id.ser);
        ondacero = (Button) findViewById(R.id.onda);
        play = (Button) findViewById(R.id.play);
        play.setEnabled(false);
        play.setText("Cargando");
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        new PlayerTask().execute(url);

        europafm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "http://icecast-streaming.nice264.com/europafm";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });
       maximafm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "http://listen.radionomy.com:80/maxima-fm-dance-revolutions"; //No encontre maximafm de verdad
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });

       kissfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "http://kissfm.kissfmradio.cires21.com/kissfm.mp3";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });

       principales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "http://19993.live.streamtheworld.com/LOS40_SC";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
       });
        c100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "http://195.55.74.212/cope/cadena100.mp3";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });
        cdial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });
       cope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });
       ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });
       ondacero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                url = "";
                started = false;
                new PlayerTask().execute(url);
                Play();

            }
        });
       play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play();
            }

        });

    }

    protected void Play(){
        if (started) {
            started = false;
            mp.pause();
            play.setText("Play");
        } else {

            started = true;
            mp.start();
            play.setText("Pause");


        }
    }








    class PlayerTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                mp.setDataSource(strings[0]);
                mp.prepare();
                prepared = true;
                mp.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aboolean) {
            super.onPostExecute(aboolean);
            play.setEnabled(true);
            play.setText("Pause");
        }
    }

        @Override
        protected void onPause(){
            super.onPause();
            if(started){
                mp.start();
            }
        }
        @Override
        protected void onResume(){
            super.onResume();
           if(started){
               mp.start();
           }
        }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(prepared){
            mp.release();
        }
    }
}







