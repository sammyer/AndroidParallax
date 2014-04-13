package com.sammyer.parallax;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by sam on 13/04/14.
 */
public class ParallaxSensor implements SensorEventListener {
	private SensorManager sensorManager;
	private ParallaxTracker tracker=new ParallaxTracker();
	private float[] xy=new float[2];
	private ParallaxListener listener;

	public static interface ParallaxListener {
		public void onParallax(float[] xy);
	}

	public void registerSensor(Context context, ParallaxListener listener, int sensorRateMs) {
		this.listener=listener;
		sensorManager=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		sensorManager.registerListener(this, sensor, sensorRateMs*1000);
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		tracker.getParallaxXY(sensorEvent.values,xy);
		if (listener!=null) listener.onParallax(xy);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {

	}

	public void unregisterSensor() {
		try {
			sensorManager.unregisterListener(this);
		} catch (Exception e) {}
		listener=null;
		sensorManager=null;
	}
}
