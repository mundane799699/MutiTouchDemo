package com.mundane.multitouchdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mundane.multitouchdemo.MainActivity;
import com.mundane.multitouchdemo.R;
import com.mundane.multitouchdemo.widget.DragViewFinal;
import com.mundane.multitouchdemo.widget.DragViewSingleTouch;
import com.mundane.multitouchdemo.widget.DragViewUpGrade;

public class DragViewActivity extends AppCompatActivity {

	private FrameLayout mFrameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_view);
		mFrameLayout = (FrameLayout) findViewById(R.id.fl);
		int id = getIntent().getIntExtra(MainActivity.KEY_ID, 0);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		switch (id) {
			case R.id.btn_singletouch_dragview:
				DragViewSingleTouch dragViewSingleTouch = new DragViewSingleTouch(this);
				mFrameLayout.addView(dragViewSingleTouch, layoutParams);
				break;
			case R.id.btn_upgrade_dragview:
				DragViewUpGrade dragViewUpGrade = new DragViewUpGrade(this);
				mFrameLayout.addView(dragViewUpGrade, layoutParams);
				break;
			case R.id.btn_final_dragView:
				DragViewFinal dragViewFinal = new DragViewFinal(this);
				mFrameLayout.addView(dragViewFinal, layoutParams);
				break;
			default:
				break;
		}
	}
}
