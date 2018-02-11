package com.dam.proyecto.paintapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
    private final int LINEA_LIBRE = 3;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setCanvasFondo(canvas);
        // Hasta aqui va a estar siempre.

        /** Aqui se dibuja el canvas de bits asociado mientras estoy dibujando de forma provisional*/
        canvas.drawBitmap(mapaDeBits, 0 , 0, null);
        pincel.setStyle(Paint.Style.STROKE);

        switch (trazo_actual) {
            case RECTANGULO:
                canvas.drawRect(startX, startY, stopX, stopY, pincel);
                canvasFondo.drawRect(startX, startY, stopX, stopY, pincel);
                break;
            case CIRCULO:
                if(pintando){
                    canvas.drawLine(startX, startY, stopX, stopY, pincel);
                    canvas.drawCircle(startX, startY,radio, pincel);
                } else {
                    canvasFondo.drawLine(startX, startY, stopX, stopY, pincel);
                    canvasFondo.drawCircle(startX, startY,radio, pincel);
                }
                break;
            case LINEA_RECTA:
                canvas.drawLine(startX, startY, stopX, stopY, pincel);
                canvasFondo.drawLine(startX, startY, stopX, stopY, pincel);
                break;
            case LINEA_LIBRE:
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
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pintando = true;
                stopX= startX = x;
                stopY = startY = y;
                rectaPoligonal.reset();
                rectaPoligonal.moveTo(startX, startY);
                break;
            case MotionEvent.ACTION_MOVE:
                radio = (float) Math.sqrt(Math.pow(startX - stopX,2)+Math.pow(startY - stopY,2));
                startX = stopX;
                stopX = x;
                startY = stopY;
                stopY = y;
                rectaPoligonal.quadTo(startX, startY, stopX, stopY);
                break;
            case MotionEvent.ACTION_UP:
                pintando = false;
                radio = (float) Math.sqrt(Math.pow(startX - stopX,2)+Math.pow(startY - stopY,2));
                startX = stopX;
                stopX = x;
                startY = stopY;
                stopY = y;
                break;
        }
        invalidate();
        return true;
    }

    public void setRectangulo(){
        trazo_actual = RECTANGULO;
    }

    public void setCirculo(){
        trazo_actual = RECTANGULO;
    }

    public void setLineaRecta(){
        trazo_actual = RECTANGULO;
    }

    public void setLineaPoligonal(){
        trazo_actual = RECTANGULO;
    }

    private void rectanguloCoord(){

    }
    private void circuloCoord(){

    }
    private void lineaRectaCoord(){

    }
    private void lineaPoliCoord(){

    }


}
