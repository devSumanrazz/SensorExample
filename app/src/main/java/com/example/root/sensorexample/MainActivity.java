package com.example.root.sensorexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorManager;
    Sensor mySensor;
    AnimatedView animatedView;
    ShapeDrawable shapeDrawable = new ShapeDrawable();
    static  int x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       sensorManager= (SensorManager)getSystemService(SENSOR_SERVICE);
        mySensor =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        animatedView = new AnimatedView(MainActivity.this);
        setContentView(animatedView);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            x=x-((int)sensorEvent.values[0]);
            y=y+((int)sensorEvent.values[1]);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class AnimatedView extends ImageView{
        int a=60,b=60;
        public AnimatedView(Context context) {
            super(context);
            shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(getResources().getColor(R.color.colorAccent));
            shapeDrawable.setBounds(x,y,x+a,y+b);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            shapeDrawable.setBounds(x,y,x+a,y+b);
            shapeDrawable.draw(canvas);
            invalidate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(MainActivity.this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(MainActivity.this);
    }
}
