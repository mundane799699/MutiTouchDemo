package com.mundane.multitouchdemo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 只有单点触控的弹性ScrollView
 */
public class ElasticScrollView1 extends ScrollView {
	Context mContext;
	private boolean canScroll;  
    private GestureDetector mGestureDetector;
	public ElasticScrollView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ElasticScrollView1(Context context) {
		super(context);
		init(context);
	}

	private void init(Context c) {
        mContext = c;
        mGestureDetector = new GestureDetector(new YScrollDetector());
        canScroll = true;
        setVerticalScrollBarEnabled(false);
	}
	
	private View inner;
    private float y;
    private Rect normal = new Rect();
    private boolean isCount = false;
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {  
            inner = getChildAt(0);  
        }  
    }  

    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(canScroll)  
                if (Math.abs(distanceY) >= Math.abs(distanceX))
                    canScroll = true;  
                else  
                    canScroll = false;  
            
            if (canScroll) {
            	if (e1.getAction() == MotionEvent.ACTION_DOWN) {
            		moveYY = 0;
        			startY = e1.getY();
            	}
            }
            return canScroll;  
        }  
    }

    double startY, moveYY;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null) {  
        	int action = ev.getAction();  
            switch (action) {  
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;  
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isNeedAnimation()) {  
                    animation();  
                    isCount = false;  
                }  

    			if (startY < moveYY - dp2px(100)) {
                    if (onPullListener != null) {
                        onPullListener.onDownPull();
                    }
    			}
    			else if (startY - moveYY > dp2px(100)) {
                    if (onPullListener != null) {
                        onPullListener.onUpPull();
                    }
    			}
    			
                break;  
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                int deltaY = (int) (preY - nowY);
                if (!isCount) {  
                    deltaY = 0; 
                }  

                y = nowY;  
                if (isNeedMove()) {  
                    if (normal.isEmpty()) {  
                        normal.set(inner.getLeft(), inner.getTop(),  
                                inner.getRight(), inner.getBottom());  
                    }  
                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,  
                            inner.getRight(), inner.getBottom() - deltaY / 2);  
                }  
                isCount = true;  
                moveYY = ev.getY();
            default:
                break;  
            }  
        }  

        return true;
    }  

    public void animation() {  
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
                normal.top);  
        ta.setDuration(200);  
        ta.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        inner.startAnimation(ta);  
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);  

        normal.setEmpty();  
    }  

    public boolean isNeedAnimation() {  
        return !normal.isEmpty();  
    }  

    public boolean isNeedMove() {  
        int offset = inner.getMeasuredHeight() - getHeight();  
        int scrollY = getScrollY();  
        if (scrollY == 0 || scrollY == offset) {  
            return true;  
        }  
        return false;  
    }
    
    public interface OnPullListener {
    	public void onDownPull();
    	public void onUpPull();
    }
    
    OnPullListener onPullListener = null;
    public void setOnPullListener(OnPullListener listener) {
    	onPullListener = listener;
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	if(ev.getAction() == MotionEvent.ACTION_UP)
            canScroll = true;  
        return mGestureDetector.onTouchEvent(ev);
    }

	private float dp2px(float value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}
}
	
