package my.hackday.Mirror;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MirrorActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // ボタンにクリックイベントをつける
        ImageButton lightButton = (ImageButton) findViewById(R.id.light_button);
        ImageButton soundButton = (ImageButton) findViewById(R.id.sound_button);
        lightButton.setOnClickListener(this);
        soundButton.setOnClickListener(this);
    }
    
    public void onClick(View view) {
    	Intent intent;
    	switch (view.getId()) {
    		case R.id.light_button:
    			intent = new Intent(this, LightActivity.class);
    			startActivity(intent);
    			break;
    		case R.id.sound_button:
    			intent = new Intent(this, SoundActivity.class);
    			startActivity(intent);
    			break;
    		default:
    			break;
    	}
    }
}