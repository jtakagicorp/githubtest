package my.hackday.Mirror;

import java.util.List;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.Window;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import my.hackday.Mirror.Pusher;

public class MirrorActivity extends Activity implements OnClickListener {
	private GestureDetector mGestureDetector;
	private Pusher mPusher = null;
	
	private int mColorNum = 1;
	private ImageButton mSoundButton = null;
	private AnimationDrawable mADrawable;
	private AnimationDrawable mLineADrawable;
	
	private RelativeLayout mLoadView = null;
	private RelativeLayout mActiveView = null;
	
	private Handler mHandler = null;
	private Runnable mChangeView = new Runnable() {
    	public void run() {
    		mLoadView.setVisibility(View.GONE);
    		mActiveView.setVisibility(View.VISIBLE);
    	}
    };
	
	ViewFlipper viewFlipper;
	Animation inFromRightAnimation;
	Animation inFromLeftAnimation;
	Animation outToRightAnimation;
	Animation outToLeftAnimation;
  
	private SensorManager manager;
	private ShakeHandler shake;
	private Pusher pusher;
	
    /** Called when the activity is first created. */	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        mLoadView = (RelativeLayout) findViewById(R.id.loading_layout);
        mActiveView = (RelativeLayout) findViewById(R.id.active_layout);
        
        mGestureDetector = new GestureDetector(this, simpleOnGestureListener);
        mPusher = new Pusher();
        
        // 各色画面
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper);
        setAnimations();
        
        // SEボタン
        mSoundButton = (ImageButton) findViewById(R.id.sound_button);
        mSoundButton.setOnClickListener(this);

        // バブルアニメーション
        ImageView bubble = (ImageView) findViewById(R.id.animation_view);
        mADrawable = (AnimationDrawable) bubble.getBackground();
        mADrawable.start();
        
        // ラインアニメーション
        ImageView lineAnime = (ImageView) findViewById(R.id.line_animation);
        mLineADrawable = (AnimationDrawable) lineAnime.getBackground();
        mLineADrawable.start();

        pusher = new Pusher();
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        shake = new ShakeHandler(pusher);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // 2秒後に画面切り替え
        mHandler = new Handler();
    	mHandler.postDelayed(mChangeView, 2000);
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

        		    mColorNum --;
        		    if (mColorNum < 1) mColorNum = 4;
        		    mPusher.getRequest("swipe", String.valueOf(mColorNum));
                } else {
        			viewFlipper.setInAnimation(inFromRightAnimation);
        		    viewFlipper.setOutAnimation(outToLeftAnimation);
                    viewFlipper.showNext();

         		    mColorNum ++;
         		    if (mColorNum > 4) mColorNum = 1;
         		    mPusher.getRequest("swipe", String.valueOf(mColorNum));
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
