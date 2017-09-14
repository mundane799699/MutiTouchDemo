package com.mundane.multitouchdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mundane.multitouchdemo.R;


/**
 * @author : mundane
 * @time : 2017/9/11 16:32
 * @description :
 * @file : DragViewFinal.java
 */

public class DragViewFinal extends View {
	String TAG = "DragViewFinal";

	Bitmap mBitmap;         // 图片
	RectF mBitmapRectF;     // 图片所在区域
	Matrix mBitmapMatrix;   // 控制图片的 matrix

	boolean canDrag = false;
	PointF lastPoint = new PointF(0, 0);
	private Paint mDeafultPaint;

	public DragViewFinal(Context context) {
		this(context, null);
	}

	public DragViewFinal(Context context, AttributeSet attrs) {
		super(context, attrs);

		mDeafultPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

		// 调整图片大小
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.outWidth = 960 / 2;
		options.outHeight = 800 / 2;

		mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.poly_test, options);
		mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		mBitmapMatrix = new Matrix();
	}

	private int mActivePointerId;
	/**
	 * A null/invalid pointer ID.
	 */
	private final int INVALID_POINTER = -1;

	// 记录活动手指的id（activePointerId），通过此ID获取move事件的坐标。
	// 在手指按下的时候，记录下activePointerId
	// 第二根手指按下的时候，更新activePointerId。（我们让第二根手指作为活动手指，忽略第一个手指的move）
	// 当其中一根手指抬起时，如果是第一根手指，那么不做处理，如果是第二根手指抬起，也就是活动手指抬起的话，将活动手指改回第一根。
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getActionMasked();
		final int actionIndex = event.getActionIndex();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				// 判断按下位置是否包含在图片区域内
				if (mBitmapRectF.contains((int) event.getX(), (int) event.getY())) {
					mActivePointerId = event.getPointerId(0);
					Log.d("ACTION_DOWN", "mActivePointerId = " + mActivePointerId);
					canDrag = true;
					lastPoint.set(event.getX(0), event.getY(0));
				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				// 将新落下来那根手指作为活动手指
				mActivePointerId = event.getPointerId(actionIndex);
				lastPoint.set(event.getX(actionIndex), event.getY(actionIndex));
				Log.d("ACTION_POINTER_DOWN", "mActivePointerId = " + mActivePointerId);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				if (mActivePointerId == event.getPointerId(actionIndex)) { // 如果松开的是活动手指, 让还停留在屏幕上的最后一根手指作为活动手指
					// This was our active pointer going up. Choose a new
					// active pointer and adjust accordingly.
					// pointerIndex都是像0, 1, 2这样连续的
					final int newPointerIndex = actionIndex == 0 ? 1 : 0;
					mActivePointerId = event.getPointerId(newPointerIndex);
					lastPoint.set(event.getX(newPointerIndex), event.getY(newPointerIndex));
					Log.d("ACTION_POINTER_UP", "松开的是活动手指");
				}
				Log.d("ACTION_POINTER_UP", "mActivePointerId = " + mActivePointerId);
				break;
			case MotionEvent.ACTION_UP: // 代表用户的最后一个手指离开了屏幕
				mActivePointerId = INVALID_POINTER;
				canDrag = false;
				Log.d("ACTION_UP", "mActivePointerId = " + mActivePointerId);
			case MotionEvent.ACTION_MOVE:
				if (mActivePointerId == INVALID_POINTER) {
					Log.e("ACTION_MOVE", "Got ACTION_MOVE event but don't have an active pointer id.");
					return false;
				}
				if (canDrag) {
					final int pointerIndex = event.findPointerIndex(mActivePointerId);
					mBitmapMatrix.postTranslate(event.getX(pointerIndex) - lastPoint.x, event.getY(pointerIndex) - lastPoint.y);
					// 更新上一次点位置
					lastPoint.set(event.getX(pointerIndex), event.getY(pointerIndex));
					// 更新图片区域
					mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
					mBitmapMatrix.mapRect(mBitmapRectF);
					invalidate();
				}
				break;
		}

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, mBitmapMatrix, mDeafultPaint);
	}
}
