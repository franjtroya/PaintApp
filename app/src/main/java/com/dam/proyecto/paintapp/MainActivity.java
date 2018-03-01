package com.dam.proyecto.paintapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private VistaPintada vistaPintada;

    private SeekBar skbWidth;
    private TextView lblWidth;
    private android.widget.ImageButton imbDelete;
    private android.widget.ImageButton imbColor;
    private android.widget.ImageButton ibRectangle;
    private android.widget.ImageButton ibCircle;
    private android.widget.ImageButton ibFreeLine;
    private android.widget.ImageButton ibStrightLine;
    private android.widget.ImageButton ibRedo;
    private android.widget.ImageButton ibUndo;
    public static final int COLOR_PICKER = 1;

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
        imbColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ifr = new Intent(MainActivity.this, ColorPickerActivity.class);
                startActivityForResult(ifr , COLOR_PICKER);
            }
        });
        imbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistaPintada.clearCanvas();
            }
        });
        skbWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                vistaPintada.setWidth(i);
                lblWidth.setText("Pincel width: " + i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void init(){
        this.ibStrightLine = findViewById(R.id.ibStrightLine);
        this.ibFreeLine = findViewById(R.id.ibFreeLine);
        this.ibCircle = findViewById(R.id.ibCircle);
        this.ibRectangle = findViewById(R.id.ibRectangle);
        this.imbColor = findViewById(R.id.imbColor);
        this.skbWidth = findViewById(R.id.skbWidth);
        this.lblWidth = findViewById(R.id.lblWidth);
        this.imbDelete = findViewById(R.id.imbDelete);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == COLOR_PICKER){
                int color = data.getIntExtra("color" , Color.BLACK);
                Log.v("asdf", "COLOR PICKER RESULT" + color);
                Log.v("asdf", "COLOR BLACK" + Color.BLACK);
                vistaPintada.setColor(color);
                imbColor.setColorFilter(color);
            }
        }
    }
}
