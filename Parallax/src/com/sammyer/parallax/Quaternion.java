/**
 sammyer
 */
package com.sammyer.parallax;

public class Quaternion {

	public float w, x, y, z; // components of a quaternion


	// default constructor
	public Quaternion() {
		w = 1.0f;
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}

	// default constructor
	public Quaternion(Quaternion q) {
		w = q.w;
		x = q.x;
		y = q.y;
		z = q.z;
	}

// initialized constructor

	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// quaternion multiplication
	public Quaternion mult (Quaternion q) {
		float nw = w*q.w - (x*q.x + y*q.y + z*q.z);

		float nx = w*q.x + q.w*x + y*q.z - z*q.y;
		float ny = w*q.y + q.w*y + z*q.x - x*q.z;
		float nz = w*q.z + q.w*z + x*q.y - y*q.x;

		this.w = nw;
		this.x = nx;
		this.y = ny;
		this.z = nz;
		return this;
	}

	// conjugates the quaternion
	public Quaternion conjugate () {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	// inverts the quaternion
	public Quaternion invert () {
		float norme = (float)Math.sqrt(w*w + x*x + y*y + z*z);
		if (norme == 0.0) norme = 1.0f;

		float recip = 1.0f / norme;

		w = w * recip;
		x = -x * recip;
		y = -y * recip;
		z = -z * recip;

		return this;
	}

	// sets to unit quaternion
	public Quaternion normalize() {
		float norme = (float)Math.sqrt(w*w + x*x + y*y + z*z);
		if (norme == 0f) {
			w = 1.0f;
			x = y = z = 0.0f;
		}
		else {
			float recip = 1.0f/norme;

			w *= recip;
			x *= recip;
			y *= recip;
			z *= recip;
		}
		return this;
	}

	// Makes quaternion from axis
	public Quaternion fromAxis(float Angle, float ax, float ay, float az) {
		float omega, s, c;

		s = (float)Math.sqrt(ax*ax + ay*ay + az*az);

		if (Math.abs(s) > Float.MIN_VALUE)
		{
			c = 1.0f/s;

			ax *= c;
			ay *= c;
			az *= c;

			omega = -0.5f * Angle;
			s = (float)Math.sin(omega);

			x = s*ax;
			y = s*ay;
			z = s*az;
			w = (float)Math.cos(omega);
		}
		else
		{
			x = y = 0.0f;
			z = 0.0f;
			w = 1.0f;
		}
		normalize();
		return this;
	}

	// Rotates towards other quaternion
	public void slerp(Quaternion a, Quaternion b, float t)
	{
		float omega, cosom, sinom, sclp, sclq;
		int i;


		cosom = a.x*b.x + a.y*b.y + a.z*b.z + a.w*b.w;


		if ((1.0f+cosom) > Float.MIN_VALUE)
		{
			if ((1.0f-cosom) > Float.MIN_VALUE)
			{
				omega = (float)Math.acos(cosom);
				sinom = (float)Math.sin(omega);
				sclp = (float)Math.sin((1.0f-t)*omega) / sinom;
				sclq = (float)Math.sin(t*omega) / sinom;
			}
			else
			{
				sclp = 1.0f - t;
				sclq = t;
			}

			x = sclp*a.x + sclq*b.x;
			y = sclp*a.y + sclq*b.y;
			z = sclp*a.z + sclq*b.z;
			w = sclp*a.w + sclq*b.w;
		}
		else
		{
			x =-a.y;
			y = a.x;
			z =-a.w;
			w = a.z;

			sclp = (float)Math.sin((1.0f-t) * Math.PI * 0.5);
			sclq = (float)Math.sin(t * Math.PI * 0.5);

			x = sclp*a.x + sclq*b.x;
			y = sclp*a.y + sclq*b.y;
			z = sclp*a.z + sclq*b.z;
		}
	}

	public Quaternion exp()
	{
		float Mul;
		float Length = (float)Math.sqrt(x*x + y*y + z*z);

		if (Length > 1.0e-4)
			Mul = (float)Math.sin(Length)/Length;
		else
			Mul = 1.0f;

		w = (float)Math.cos(Length);

		x *= Mul;
		y *= Mul;
		z *= Mul;

		return this;
	}

	public Quaternion log()
	{
		float Length;

		Length = (float)Math.sqrt(x*x + y*y + z*z);
		Length = (float)Math.atan(Length/w);

		w = 0f;

		x *= Length;
		y *= Length;
		z *= Length;

		return this;
	}


	public Quaternion clone() {
		return new Quaternion(this);
	}
	public void set(Quaternion q) {
		w=q.w;
		x=q.x;
		y=q.y;
		z=q.z;
	}
	public void setFromTriplet(float[] rot) {
		x=rot[0];
		y=rot[1];
		z=rot[2];
		float n=1-(x*x+y*y+z*z);
		if (n<0) n=0;
		w=(float)Math.sqrt(n);
	}
	public void setFromEuler(float yaw, float pitch, float roll) {
		float c2=(float)Math.cos(yaw/2);
		float c3=(float)Math.cos(pitch/2);
		float c1=(float)Math.cos(roll/2);
		float s2=(float)Math.sin(yaw/2);
		float s3=(float)Math.sin(pitch/2);
		float s1=(float)Math.sin(roll/2);
		w=c1*c2*c3-s1*s2*s3;
		x=s1*s2*c3+c1*c2*s3;
		y=s1*c2*c3+c1*s2*s3;
		z=c1*s2*c3-s1*c2*s3;
	}

	public float getPitch()
	{
		return (float)Math.atan2(2*(y*z + w*x), w*w - x*x - y*y + z*z);
	}

	public float getRoll()
	{
		return (float)Math.asin(-2*(x*z - w*y));
	}

	public float getYaw()
	{
		return (float)Math.atan2(2*(x*y + w*z), w*w + x*x - y*y - z*z);
	}

}