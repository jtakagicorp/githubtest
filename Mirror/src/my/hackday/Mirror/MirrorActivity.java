package my.hackday.Mirror;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.Window;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import my.hackday.Mirror.Pusher;

public class MirrorActivity extends Activity implements OnClickListener {
	private GestureDetector mGestureDetector;
	private Pusher mPusher = null;
	
	private int mColorNum = 1;
	private ImageButton mSoundButton = null;
	
	ViewFlipper viewFlipper;
	Animation inFromRightAnimation;
	Animation inFromLeftAnimation;
	Animation outToRightAnimation;
	Animation outToLeftAnimation;
  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        mGestureDetector = new GestureDetector(this, simpleOnGestureListener);
        mPusher = new Pusher();
        
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper);
        mSoundButton = (ImageButton) findViewById(R.id.sound_button);
        mSoundButton.setOnClickListener(this);
        
        setAnimations();
    }
    
	protected void setAnimations() {    
		inFromRightAnimation = 
	    AnimationUtils.loadAnimation(this, R.anim.in_from_right);
		inFromLeftAnimation = 
	    AnimationUtils.loadAnimation(this, R.anim.in_from_left);
		outToRightAnimation = 
		AnimationUtils.loadAnimation(this, R.anim.out_to_right);
		outToLeftAnimation = 
		AnimationUtils.loadAnimation(this, R.anim.out_to_left);
	}
	
	protected void changeScale() {
    	AnimationSet set = new AnimationSet(true);

		Animation scale = new ScaleAnimation(1, 1, 1, 1.1f, 100, 0);
       	Animation trans = new TranslateAnimation(0, 0, 0, -20);
    	
    	set.addAnimation(scale);
    	set.addAnimation(trans);
    	set.setDuration(200);
    	
    	mSoundButton.startAnimation(set);
	}
	
    public void onClick(View view) {
    	switch (view.getId()) {
    		case R.id.sound_button:
    			// 音楽を鳴らす
    			mPusher.getRequest("tap", "1");
    			changeScale();
    			break;
    		default:
    			break;
    	}
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	mGestureDetector.onTouchEvent(event);      
        return super.onTouchEvent(event); 
    }
    
    private SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() { 
        @Override 
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        	// フリックイベント
            float dx = Math.abs(velocityX);
            float dy = Math.abs(velocityY);
            
            if (dx > dy && dx > 150) {
                if (event1.getX() < event2.getX()) {
        			viewFlipper.setInAnimation(inFromLeftAnimation);
        		    viewFlipper.setOutAnimation(outToRightAnimation);
        		    viewFlipper.showPrevious();
        		    
        		    mPusher.getRequest("swipe", String.valueOf(mColorNum));
        		    mColorNum --;
                } else {
        			viewFlipper.setInAnimation(inFromRightAnimation);
        		    viewFlipper.setOutAnimation(outToLeftAnimation);
                    viewFlipper.showNext();
                    
         		    mPusher.getRequest("swipe", String.valueOf(mColorNum));
         		    mColorNum ++;
                }
            }
		    
            return super.onFling(event1, event2, velocityX, velocityY); 
        } 

        @Override 
        public boolean onSingleTapConfirmed(MotionEvent event) { 
        	// タップイベント
            return super.onSingleTapConfirmed(event); 
        }
    };
}
