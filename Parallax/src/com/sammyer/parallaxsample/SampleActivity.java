package com.sammyer.parallaxsample;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.sammyer.parallax.ParallaxSensor;
import com.sammyer.parallax.R;

public class SampleActivity extends Activity implements ParallaxSensor.ParallaxListener {
	private ParallaxSensor sensor;
	private SampleView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
		view=(SampleView)findViewById(R.id.sample_view);
		sensor=new ParallaxSensor();
    }

	@Override
	protected void onResume() {
		super.onResume();
		sensor.registerSensor(this,this,50);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensor.unregisterSensor();
	}

	@Override
	public void onParallax(float[] xy) {
		view.setPosition(xy[0],xy[1]);
	}
}
