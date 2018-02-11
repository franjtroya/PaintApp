package com.dam.proyecto.paintapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private VistaPintada vistaPintada;

    private android.widget.ImageButton imOptions;
    private android.widget.ImageButton ibRectangle;
    private android.widget.ImageButton ibCircle;
    private android.widget.ImageButton ibFreeLine;
    private android.widget.ImageButton ibStrightLine;
    private android.widget.ImageButton ibRedo;
    private android.widget.ImageButton ibUndo;

    private void events(){
        ibRectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistaPintada.setRectangulo();
            }
        });
        ibCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistaPintada.setCirculo();
            }
        });
        ibStrightLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistaPintada.setLineaRecta();
            }
        });
        ibFreeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistaPintada.setLineaPoligonal();
            }
        });
    }

    private void init(){
        this.ibUndo = (ImageButton) findViewById(R.id.ibUndo);
        this.ibRedo = (ImageButton) findViewById(R.id.ibRedo);
        this.ibStrightLine = (ImageButton) findViewById(R.id.ibStrightLine);
        this.ibFreeLine = (ImageButton) findViewById(R.id.ibFreeLine);
        this.ibCircle = (ImageButton) findViewById(R.id.ibCircle);
        this.ibRectangle = (ImageButton) findViewById(R.id.ibRectangle);
        this.imOptions = (ImageButton) findViewById(R.id.imOptions);
        vistaPintada = findViewById(R.id.vVistaPintada);
        events();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        init();
    }
}
