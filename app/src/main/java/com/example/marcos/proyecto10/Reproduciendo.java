package com.example.marcos.proyecto10;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;

import static android.R.drawable.ic_media_next;
import static android.R.drawable.ic_media_pause;
import static android.R.drawable.ic_media_play;
import static android.R.drawable.progress_horizontal;

public class Reproduciendo extends AppCompatActivity implements View.OnClickListener {
    static MediaPlayer mp;
    ArrayList<File> canciones;
    int posicion;
    Thread actualizarsb;
    Uri uri;
    TextView tiempo, tiempofin, nombre;
    ImageButton btnpv, btnrw, btnps, btnff, btnnext, playlist, back;
    SeekBar sb;
    String aux = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproduciendo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnpv = (ImageButton) findViewById(R.id.btnpv);
        btnrw = (ImageButton) findViewById(R.id.btnrw);
        btnps = (ImageButton) findViewById(R.id.btnps);
        btnff = (ImageButton) findViewById(R.id.btnff);
        btnnext = (ImageButton) findViewById(R.id.btnnext);
        sb = (SeekBar) findViewById(R.id.seekBar);
        playlist = (ImageButton) findViewById(R.id.playlist);
        nombre = (TextView) findViewById(R.id.nombre);
        tiempo = (TextView) findViewById(R.id.tiempo);
        tiempofin = (TextView) findViewById(R.id.tiempofin);
        back = (ImageButton) findViewById(R.id.back);

        btnpv.setOnClickListener(this);
        btnrw.setOnClickListener(this);
        btnps.setOnClickListener(this);
        btnff.setOnClickListener(this);
        btnnext.setOnClickListener(this);
        playlist.setOnClickListener(this);

        actualizarsb = new Thread() {
            @Override
            public void run() {
                int duracion = mp.getDuration();
                sb.setMax(duracion);
                int posicionactual = 0;
                int ejecucion = 0;

                while (posicionactual < duracion) {
                    try {
                        sleep(500);
                        posicionactual = mp.getCurrentPosition();
                        sb.setProgress(posicionactual);
                        ejecucion=sb.getProgress();
                        aux = getHRM(ejecucion);

                        tiempo.setText(aux.toString().trim());

                    } catch (Exception e) {
                        tiempo.setText(aux);

                    }
                }

            }

        };

        if (mp != null) {
            mp.stop();
        }

        try {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            canciones = (ArrayList) b.getParcelableArrayList("canciones");
            posicion = (int) b.getInt("pos", 0);

                uri = Uri.parse(canciones.get(posicion).toString());
                nombre.setText(canciones.get(posicion).getName().toString());
                mp = MediaPlayer.create(getApplication(), uri);
                actualizarsb.start();
                mp.start();

                //mp.setLooping(true); PARA ESCUCHAR EN BUCLE
                tiempo.setText(getHRM(mp.getCurrentPosition()));
                tiempofin.setText(getHRM(mp.getDuration()));

              

        } catch (Exception e) {

        }

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private String getHRM(int milliseconds) {
        int segundos = (int) (milliseconds / 1000) % 60;
        int minutos = (int) (milliseconds / (1000 * 60)) % 60;
        int horas = (int) (milliseconds / (1000 * 3600)) % 24;
        String aux = "";
        aux = ((horas < 10) ? "0" + horas : horas) + ":" + ((minutos < 10) ? "0" + minutos : minutos) + ":" + ((segundos < 10) ? "0" + segundos : segundos);
        return aux;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnps:
                if (mp.isPlaying()) {
                    btnps.setImageResource(ic_media_play);
                    mp.pause();
                } else {
                    btnps.setImageResource(ic_media_pause);
                    mp.start();
                }
                break;

            case R.id.btnff:
                mp.seekTo(mp.getCurrentPosition() + 5000);
                break;
            case R.id.btnrw:
                mp.seekTo(mp.getCurrentPosition() - 5000);
                break;
            case R.id.btnnext:
                NextCancion();
                break;
            case R.id.btnpv:
                AnteriorCancion();
                break;
            case R.id.playlist:
                startActivity(new Intent(getApplicationContext(),Reproductor.class).putExtra("pos", posicion).putExtra("canciones", canciones));
                break;

        }
    }

    public void NextCancion(){
        mp.stop();


        posicion = (posicion+1)%canciones.size();
        nombre.setText(canciones.get(posicion).getName().toString());
        uri = Uri.parse(canciones.get(posicion).toString());
        mp = MediaPlayer.create(getApplicationContext(),uri);

        mp.start();
        sb.setMax(0);
        tiempofin.setText(getHRM(mp.getDuration()));

        try{
            sb.setMax(mp.getDuration());
        }catch (Exception e){}

    }

    public void AnteriorCancion(){
        mp.stop();

        if(posicion-1<0){
            posicion=canciones.size()-1;
        }else{
            posicion=posicion-1;
        }

        nombre.setText(canciones.get(posicion).getName().toString());
        uri = Uri.parse(canciones.get(posicion).toString());
        mp = MediaPlayer.create(getApplicationContext(),uri);
        mp.start();
        sb.setMax(0);
        tiempofin.setText(getHRM(mp.getDuration()));
        try{
            sb.setMax(mp.getDuration());
        }catch (Exception e){}
    }



}
