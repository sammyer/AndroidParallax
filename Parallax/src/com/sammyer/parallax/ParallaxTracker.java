/**
 sammyer
 */
package com.sammyer.parallax;

public class ParallaxTracker {
	private Quaternion qRest;
	private Quaternion qCur=new Quaternion();
	private Quaternion qDiff=new Quaternion();

	/*
	Tracks the absolute axis-angle representation of the device rotation,
	and returns the relative axis-angle rotation (throwing away the yaw term)
	 */
	public void getRelativeRotation(float[] absoluteRotAxis, float[] relativeRotAxis) {
		if (absoluteRotAxis==null||relativeRotAxis==null||absoluteRotAxis.length!=3||relativeRotAxis.length!=3) {
			throw new IllegalArgumentException("getRelativeParallax requires 2 vectors of length 3");
		}

		qCur.setFromTriplet(absoluteRotAxis);
		if (qRest==null) {
			qRest=new Quaternion(qCur);
			relativeRotAxis[0]=0;
			relativeRotAxis[1]=0;
			relativeRotAxis[2]=0;
		} else {
			qDiff.set(qRest);
			qDiff.invert();
			qDiff.mult(qCur);

			float yaw=Math.abs(qDiff.getYaw());
			qDiff.setFromEuler(0, qDiff.getPitch(), qDiff.getRoll());

			float interpol=0.01f;
			if (yaw>0.1f) interpol*=yaw*10;

			qRest.slerp(qRest, qCur, interpol);


			relativeRotAxis[0]=qDiff.x;
			relativeRotAxis[1]=qDiff.y;
			relativeRotAxis[2]=qDiff.z;
		}
	}

	/*
	Tracks the absolute axis-angle rotation as returned by the rotation sensor and
	 returns a tuple representing relative rotation of the device as percent value
	 between -1 and 1 where (-1,-1) is rotated left and up, and (1,1) is rotated
	 right and down
	 */
	public void getParallaxXY(float[] rotationVector, float[] xy) {
		if (rotationVector==null||xy==null||rotationVector.length!=3||xy.length!=2) {
			throw new IllegalArgumentException("getRelativeParallax requires vectors of length 3 and 2");
		}

		qCur.setFromTriplet(rotationVector);
		if (qRest==null) {
			qRest=new Quaternion(qCur);
			xy[0]=0;
			xy[1]=0;
		} else {
			qDiff.set(qRest);
			qDiff.invert();
			qDiff.mult(qCur);

			float yaw=Math.abs(qDiff.getYaw());

			float interpol=0.01f;
			if (yaw>0.1f) interpol*=yaw*10;

			qRest.slerp(qRest, qCur, interpol);

			xy[0]=toPercent(qDiff.getRoll());
			xy[1]=toPercent(qDiff.getPitch());
		}
	}

	private float toPercent(float angle) {
		float x=angle/1.5f;
		if (x<-1) x=-1;
		if (x>1) x=1;
		return x;
	}


}