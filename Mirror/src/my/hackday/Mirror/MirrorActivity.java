package my.hackday.Mirror;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MirrorActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        // �{�^���ɃN���b�N�C�x���g������
        ImageButton soundButton = (ImageButton) findViewById(R.id.sound_button);
        soundButton.setOnClickListener(this);
    }
    
    public void onClick(View view) {
    	switch (view.getId()) {
    		case R.id.sound_button:
    			// ���y��炷
    			break;
    		default:
    			break;
    	}
    }
}
