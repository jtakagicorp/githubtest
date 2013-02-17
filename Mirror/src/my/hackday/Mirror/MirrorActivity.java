package my.hackday.Mirror;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class MirrorActivity extends Activity implements OnClickListener {
	private SensorManager manager;
	private ShakeHandler shake;
	private Pusher pusher;
	
    /** Called when the activity is first created. */	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        // ボタンにクリックイベントをつける
        ImageButton soundButton = (ImageButton) findViewById(R.id.sound_button);
        soundButton.setOnClickListener(this);
        
        pusher = new Pusher();
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        shake = new ShakeHandler(pusher);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
	
    public void onClick(View view) {
    	switch (view.getId()) {
    		case R.id.sound_button:
    			// 音楽を鳴らす
    			break;
    		default:
    			break;
    	}
    }



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			Sensor s = sensors.get(0);
			manager.registerListener(shake, s, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		manager.unregisterListener(shake);
	}
}
