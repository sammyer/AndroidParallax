package com.sammyer.parallaxsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sam on 13/04/14.
 */
public class SampleView extends View {
	private float xPercent;
	private float yPercent;
	private Paint paint=new Paint();

	public SampleView(Context context) {
		super(context);
	}

	public SampleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SampleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPosition(float xPercent, float yPercent) {
		this.xPercent=xPercent;
		this.yPercent=yPercent;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w=canvas.getWidth();
		int h=canvas.getHeight();
		float r=w*0.1f;
		float dist=w-r*2;
		float cx=w*0.5f+xPercent*dist;
		float cy=h*0.5f+yPercent*dist;
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(0xFF333333);
		paint.setStrokeWidth(2);
		canvas.drawRect(1, 1, w-1, h-1, paint);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xFFEEAA22);
		canvas.drawCircle(cx,cy,r,paint);
		super.onDraw(canvas);
	}
}
