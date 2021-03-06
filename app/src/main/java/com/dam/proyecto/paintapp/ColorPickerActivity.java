package com.dam.proyecto.paintapp;

import com.github.danielnilsson9.colorpickerview.view.ColorPanelView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView.OnColorChangedListener;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorPickerActivity extends AppCompatActivity implements OnColorChangedListener, View.OnClickListener {

    private ColorPickerView			mColorPickerView;
    private ColorPanelView			mOldColorPanelView;
    private ColorPanelView			mNewColorPanelView;

    private Button					mOkButton;
    private Button					mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);

        setContentView(R.layout.activity_color_picker);

        init();

    }

    private void init() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int initialColor = prefs.getInt("color_3", 0xFF000000);

        mColorPickerView = findViewById(R.id.colorpickerview__color_picker_view);
        mOldColorPanelView = findViewById(R.id.colorpickerview__color_panel_old);
        mNewColorPanelView = findViewById(R.id.colorpickerview__color_panel_new);

        mOkButton = findViewById(R.id.okButton);
        mCancelButton = findViewById(R.id.cancelButton);


        ((LinearLayout) mOldColorPanelView.getParent()).setPadding(
                mColorPickerView.getPaddingLeft(), 0,
                mColorPickerView.getPaddingRight(), 0);


        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        mOldColorPanelView.setColor(initialColor);

        mOkButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

    }

    @Override
    public void onColorChanged(int newColor) {
        mNewColorPanelView.setColor(mColorPickerView.getColor());
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.okButton:
                Log.v("asdf","COLOR pick win" + mColorPickerView.getColor());
                Intent i = new Intent();
                i.putExtra("color", mColorPickerView.getColor());
                setResult(RESULT_OK , i);
                finish();
                break;
            case R.id.cancelButton:
                finish();
                break;
        }

    }


}