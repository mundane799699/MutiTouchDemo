package com.mundane.multitouchdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mundane.multitouchdemo.activity.DragViewActivity;
import com.mundane.multitouchdemo.activity.ElasticScrollViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	public static final String KEY_ID = "key_id";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btn_singletouch_dragview).setOnClickListener(this);
		findViewById(R.id.btn_upgrade_dragview).setOnClickListener(this);
		findViewById(R.id.btn_final_dragView).setOnClickListener(this);
		findViewById(R.id.btn_singletouch_elastic_scrollview).setOnClickListener(this);
		findViewById(R.id.btn_multitouch_elastic_scrollview).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		Intent intent;
		switch (id) {
			case R.id.btn_singletouch_dragview:
			case R.id.btn_upgrade_dragview:
			case R.id.btn_final_dragView:
				intent = new Intent(this, DragViewActivity.class);
				intent.putExtra(KEY_ID, id);
				startActivity(intent);
				break;
			case R.id.btn_singletouch_elastic_scrollview:
			case R.id.btn_multitouch_elastic_scrollview:
				intent = new Intent(this, ElasticScrollViewActivity.class);
				intent.putExtra(KEY_ID, id);
				startActivity(intent);
				break;
		}
	}
}
