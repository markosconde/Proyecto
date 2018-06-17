package com.example.marcos.proyecto10;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Reproductor extends AppCompatActivity {

    ListView lv;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        lv=(ListView)findViewById(R.id.lvplaylist);
        File rutasd = new File("/storage");
        final ArrayList<File> canciones = EncontrarCanciones(rutasd);

        items = new String[canciones.size()];

        for(int i=0; i<canciones.size(); i++){
            items[i]= canciones.get(i).getName().toString().replace("mp3","").replace("vav","").toLowerCase();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.canciones,R.id.textview,items);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),Reproduciendo.class).putExtra("pos",position).putExtra("canciones",canciones));
            }
        });

    }

    public ArrayList<File> EncontrarCanciones(File root){
      // Toast.makeText(Reproductor.this, "Buscando Canciones", Toast.LENGTH_SHORT).show();
        ArrayList<File> canciones = new ArrayList<File>();
        File[] archivos=root.listFiles();

        for(File lista : archivos){
            if(lista.isDirectory() && !lista.isHidden()){
                canciones.addAll(EncontrarCanciones(lista));
            }
            else{
                if(lista.getName().endsWith(".mp3") || lista.getName().endsWith(".vav")){
                    canciones.add(lista);
                }
            }
        }
        return canciones;
    }

}
