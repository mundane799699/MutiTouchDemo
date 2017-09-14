package com.mundane.multitouchdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mundane.multitouchdemo.MainActivity;
import com.mundane.multitouchdemo.R;

public class ElasticScrollViewActivity extends AppCompatActivity {

	private FrameLayout mFrameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int id = getIntent().getIntExtra(MainActivity.KEY_ID, 0);
		switch (id) {
			case R.id.btn_singletouch_elastic_scrollview:
				setContentView(R.layout.activity_elastic_scroll_view1);
				break;
			case R.id.btn_multitouch_elastic_scrollview:
				setContentView(R.layout.activity_elastic_scroll_view);
				break;
			default:
				break;
		}
	}

}
