package my.hackday.Mirror;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MirrorActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button changeColorButton = (Button) findViewById(R.id.change_color_button);
        changeColorButton.setOnClickListener(this);
    }
    
    public void onClick(View view) {
    	switch (view.getId()) {
    		case R.id.change_color_button:
    			break;
    		default:
    			break;
    	}
    }
}