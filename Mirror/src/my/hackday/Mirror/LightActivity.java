package my.hackday.Mirror;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class LightActivity extends Activity implements OnClickListener {
	private SampleView mView;
    private Bitmap mBitmap;
    private boolean mIsAnime = false;
    private Handler mHandler = null;
    private Runnable mStopAnime = new Runnable() {
    	public void run() {
        	mIsAnime = false;
        	mView.invalidate();
    	}
    };
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light);
        
        // TODO: �摜�ύX
        mBitmap = BitmapFactory.decodeResource(getResources(), 
                R.drawable.ic_launcher);
        mView = new SampleView(this);
        setContentView(mView);
        
        mHandler = new Handler();
	}
	
    private class SampleView extends View {
        private Paint mPaint;
        private float imageX = 0f;
        private float imageY = 0f;

        public SampleView(Context context) {
            super(context);
            mPaint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
        	//canvas�̃J���[
        	canvas.drawColor(Color.BLACK);
        	//�A���`�G�C���A�X
        	mPaint.setAntiAlias(true);
        	//�J���[�ݒ�i�f�t�H���g��BLACK�j
        	//mPaint.setColor(Color.BLUE);
        
        	//�e�L�X�g�\��
        	//canvas.drawText(getString(R.string.hello), 0, 20, mPaint);

        	if (mIsAnime) {
        	//�摜�\��
        	canvas.drawBitmap(mBitmap, 
                imageX - mBitmap.getWidth() / 2, 
                imageY - mBitmap.getHeight() / 2, 
                mPaint);
        	}
        }
        
        @Override 
        public boolean onTouchEvent(MotionEvent event) {

            //�G��
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                imageX = event.getX();
                imageY = event.getY();
                
                /* �g��
            	ScaleAnimation scale = new ScaleAnimation(1, 1.5f, 1, 1.5f);
            	scale.setDuration(1000);
            	mView.startAnimation(scale);
            	*/
                // 1�b�����ď�Ɉړ�
                TranslateAnimation translate = new TranslateAnimation(0, 0, 0, -700);
                translate.setDuration(1000);
            	mView.startAnimation(translate);
            	
            	// 1�b��ɍĕ`��
            	mHandler.removeCallbacks(mStopAnime);
            	mHandler.postDelayed(mStopAnime, 1000);
            	
            	mIsAnime = true;
            }
            //�G�����܂܃X���C�h
            else if(event.getAction() == MotionEvent.ACTION_MOVE){
            }
            //����
            else if(event.getAction() == MotionEvent.ACTION_UP){
            }
            // �ĕ`��̎w��
            invalidate();
            
            return true;
        }
    }
    
	/*
	private class FlickTouchListener implements View.OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
	        float downX = 0;
	        float downY = 0;
	        int downLeftMargin = 0;
	        int downTopMargin = 0;
	        ViewGroup layout = (ViewGroup) findViewById(R.id.light_layout);
	        ViewGroup.MarginLayoutParams param =
                    (ViewGroup.MarginLayoutParams)v.getLayoutParams();
	        
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// ����
				downX = event.getRawX();
	            downY = event.getRawY();

	            downLeftMargin = param.leftMargin;
	            downTopMargin = param.topMargin;
                break;
			case MotionEvent.ACTION_MOVE:
				// �ړ�
				param.leftMargin = downLeftMargin + (int)(event.getRawX() - downX) -50;
				param.topMargin = downTopMargin + (int)(event.getRawY() - downY) -150;

				if (mIsFirst && param.leftMargin != 0) {
		        	// ����̏����L�^
		        	mFirstParam = param;
		        	mIsFirst = false;
		        }
				v.layout(
						param.leftMargin,
						param.topMargin,
						param.leftMargin + v.getWidth(),
						param.topMargin + v.getHeight()
						);
				break;
			case MotionEvent.ACTION_UP:
				// ����
				if (!mIsFirst && param.topMargin <= 0) {
					
					mRedButton.setVisibility(View.INVISIBLE);
				} else {
					// TODO: ���̈ʒu�ɖ߂�
					if (!mIsFirst) {
						v.layout(
								mFirstParam.leftMargin,
								mFirstParam.topMargin,
								mFirstParam.leftMargin + v.getWidth(),
								mFirstParam.topMargin + v.getHeight()
								);
					}
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				// �L�����Z��
				break;
			}
			return true;
		}
	}
	*/
    public void onClick(View view) {
    }
}