package my.hackday.Mirror;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class ShakeHandler implements SensorEventListener {
	private static final float THRESHOLD = 11.0f;
	private static final float THRESHOLD_BIG = 30.0f;
	private static final String SENDVAL = "1";
	private static final String SENDVAL_BIG = "2";
	private static final String SHAKEKEY = "shake";
	float x,y,z;
	Pusher pusher;
	
	public ShakeHandler(Pusher pusher){
		x = 0.00001f;
		y = 0.00001f;
		z = 0.00001f;
		this.pusher = pusher;
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	public void onSensorChanged(SensorEvent event) {
		float new_x,new_y,new_z;
		
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			new_x = event.values[0];
			new_y = event.values[1];
			new_z = event.values[2];
			float score =  norm(new_x, new_y, new_z);//1 - Math.abs(cosine(x, new_x, y, new_y, z, new_z));
			if (score > THRESHOLD_BIG) {
				Log.e("arndorid", String.valueOf(score) + " over "); 
				//pusher.getRequest(SHAKEKEY, SENDVAL_BIG);
			} else if (score > THRESHOLD) {
				Log.e("arndorid", String.valueOf(score)); 
				//pusher.getRequest(SHAKEKEY, SENDVAL);
			}
			x = new_x;
			y = new_y;
			z = new_z;
		}
	}
	
	public float cosine(float x1, float x2, float y1, float y2, float z1, float z2){
		float prod = prod(x1, x2, y1, y2, z1, z2);
		float norm1 = norm(x1, y1, z1);
		float norm2 = norm(x2, y2, z2);
		return prod/(norm1 * norm2);
	}

	public float prod(float x1, float x2, float y1, float y2, float z1, float z2){
		return (float)(x1*x2 + y1*y2 + z1*z2);
	}
	
	public float distance(float x1, float x2, float y1, float y2, float z1, float z2){
		return (float)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2));
	}
	
	public float norm(float x, float y, float z){
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

}
