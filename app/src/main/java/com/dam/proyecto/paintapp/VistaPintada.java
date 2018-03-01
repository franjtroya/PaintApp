package com.dam.proyecto.paintapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.AbstractList;
import java.util.AbstractQueue;
import java.util.ArrayList;

/**
 * Created by dam on 02/02/2018.
 */

public class VistaPintada extends View {
    private Bitmap mapaDeBits;
    private Canvas canvasFondo;
    private int trazo_actual;
    private Paint pincel;
    private final int RECTANGULO = 0;
    private final int CIRCULO = 1;
    private final int LINEA_RECTA = 2;
    private final int LINEA_POLI = 3;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private Path mPath;
    private int pincelColor = Color.BLACK;
    private float pincelWidth;

    public VistaPintada(Context context) {
        super(context);
    }

    public VistaPintada(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void setCanvasFondo(Canvas canvas){
        Paint pincel = new Paint();
        pincel.setColor(Color.WHITE);
        pincel.setAntiAlias(true);
        canvas.drawRect(0, 0, getWidth(), getHeight(), pincel);
        pincel.setStrokeWidth(5);
    }

    private void init(){
        pincel = new Paint();
        pincel.setColor(pincelColor);
        pincel.setAntiAlias(true);
        pincel.setStrokeWidth(5);
        pincel.setStrokeWidth(pincelWidth);
        mPath = new Path();
        paths.add(mPath);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setCanvasFondo(canvas);
        for (Path p : paths){
            canvasFondo.drawPath(p, pincel);
        }
        // Hasta aqui va a estar siempre.
        init();

        /**
         * Aqui se dibuja el canvas de bits asociado mientras estoy dibujando de forma provisional
         */
        canvas.drawBitmap(mapaDeBits, 0 , 0, null);
        pincel.setStyle(Paint.Style.STROKE);

        switch (trazo_actual) {
            case RECTANGULO:
                if(pintando){
                    canvas.drawRect(startX, startY, stopX, stopY, pincel);
                } else {
                    canvasFondo.drawRect(startX, startY, stopX, stopY, pincel);
                }
                break;
            case CIRCULO:
                if(pintando){
                    canvas.drawLine(startX, startY, stopX, stopY, pincel);
                    canvas.drawCircle(startX, startY,radio, pincel);
                } else {
                    canvasFondo.drawCircle(startX, startY,radio, pincel);
                }
                break;
            case LINEA_RECTA:
                if (pintando){
                    canvas.drawLine(startX, startY, stopX, stopY, pincel);
                } else {
                    canvasFondo.drawLine(startX, startY, stopX, stopY, pincel);
                }
                break;
            case LINEA_POLI:
                canvas.drawLine(startX, startY, stopX, stopY, pincel);
                canvasFondo.drawLine(startX, startY, stopX, stopY, pincel);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mapaDeBits = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasFondo = new Canvas(mapaDeBits);
    }

    private float startX, startY, stopX, stopY, radio;
    private boolean pintando = false;
    private Path rectaPoligonal = new Path();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (trazo_actual){
            case RECTANGULO:
                rectanguloCoord(x, y, event);
                break;
            case CIRCULO:
                circuloCoord(x, y, event);
                break;
            case LINEA_RECTA:
                lineaRectaCoord(x, y, event);
                break;
            case LINEA_POLI:
                lineaPoliCoord(x, y, event);
                break;
        }
        invalidate();
        return true;
    }

    /** PUBLIC SETTERS*/

    public void setRectangulo(){
        trazo_actual = RECTANGULO;
    }

    public void setCirculo(){
        trazo_actual = CIRCULO;
    }

    public void setLineaRecta(){
        trazo_actual = LINEA_RECTA;
    }

    public void setLineaPoligonal(){
        trazo_actual = LINEA_POLI;
    }

    public void setColor(int color){
        pincelColor = color;
    }

    public void setWidth(float width){
        this.pincelWidth = width;
    }

    public void clearCanvas(){
        setCanvasFondo(canvasFondo);
    }

    private void rectanguloCoord(float x, float y, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downStart(x, y);
                pintando = true;
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.reset();
                mPath.moveTo(x, y);
                stopX = x;
                stopY = y;
                break;
            case MotionEvent.ACTION_UP:
                pintando = false;
                stopX = x;
                stopY = y;
                commitDraw();
                break;
        }
    }
    private void circuloCoord(float x, float y, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downStart(x, y);
                pintando = true;
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                radio = (float) Math.sqrt(Math.pow(startX - stopX,2)+Math.pow(startY - stopY,2));
                stopX = x;
                stopY = y;
                break;
            case MotionEvent.ACTION_UP:
                pintando = false;
                radio = (float) Math.sqrt(Math.pow(startX - stopX,2)+Math.pow(startY - stopY,2));
                stopX = x;
                stopY = y;
                commitDraw();
                break;
        }
    }
    private void lineaRectaCoord(float x, float y, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downStart(x, y);
                pintando = true;
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                stopX = x;
                stopY = y;
                break;
            case MotionEvent.ACTION_UP:
                pintando = false;
                stopX = x;
                stopY = y;
                commitDraw();
                break;
        }
    }
    private void lineaPoliCoord(float x, float y, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downStart(x, y);
                pintando = true;
                stopX= startX = x;
                stopY = startY = y;
                rectaPoligonal.reset();
                rectaPoligonal.moveTo(startX, startY);
                break;
            case MotionEvent.ACTION_MOVE:
                startX = stopX;
                stopX = x;
                startY = stopY;
                stopY = y;
                rectaPoligonal.quadTo(startX, startY, stopX, stopY);
                break;
            case MotionEvent.ACTION_UP:
                pintando = false;
                startX = stopX;
                stopX = x;
                startY = stopY;
                stopY = y;

                commitDraw();
                break;
        }
    }

    private void commitDraw(){
        // commit the path to our offscreen
        canvasFondo.drawPath(mPath, pincel);
        // kill this so we don't double draw
        mPath = new Path();
        paths.add(mPath);
    }

    private void downStart(float x, float y){
        mPath.reset();
        mPath.moveTo(x, y);
    }


}
