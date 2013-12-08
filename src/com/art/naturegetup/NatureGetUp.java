package com.art.naturegetup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NatureGetUp extends Activity {

	float HOUR = 0;
	int MS = 0;

	void setMS() {
		MS = (int) (60 * 60 * HOUR);
		
		// For test
//		MS = (int) (HOUR);
	}

	Button button;
	RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_nature_get_up);
		button = (Button) findViewById(R.id.Sleep);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (HOUR != 0) {
					SimpleDateFormat sDateFormat = new SimpleDateFormat(
							"hh:mm:ss");
					wakeUp();
					Toast.makeText(
							NatureGetUp.this,
							"You'll be waked up at tomorrow "
									+ sDateFormat.format(System
											.currentTimeMillis() + MS * 1000),
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(NatureGetUp.this,
							"Please select your sleep circle",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						switch (checkedId) {
						case R.id.radioButton1:
							HOUR = 6;
							break;
						case R.id.radioButton2:
							HOUR = 7;
							break;
						case R.id.radioButton3:
							HOUR = 7.5f;
							break;
						case R.id.radioButton4:
							HOUR = 8;
							break;
						default:
							break;
						}
						setMS();
					}
				});

	}

	private void wakeUp() {
		// TODO Auto-generated method stub
		AlarmManager am = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);

		// Action指向Reciver
		Intent intent = new Intent("WAKEUP");

		PendingIntent pi = PendingIntent.getBroadcast(NatureGetUp.this, 1,
				intent, 0);

		Calendar cl = Calendar.getInstance();

		cl.add(Calendar.SECOND, MS);// 设置启动时间为MS秒后

		Log.d("art", "MS = " + MS);
		am.set(AlarmManager.RTC_WAKEUP, cl.getTimeInMillis(), pi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nature_get_up, menu);
		return true;
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onPause() {
		super.onPause();

	}
}
