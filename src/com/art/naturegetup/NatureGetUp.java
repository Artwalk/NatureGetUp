package com.art.naturegetup;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NatureGetUp extends Activity {

	double hour = 0.01;
	int ms = 5;//(int)(1000*60*60*hour);
	
	Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_nature_get_up);
		bt = (Button) findViewById(R.id.Sleep);
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				wakeUp();
			}
		});
	}
	
	private void wakeUp() {
		// TODO Auto-generated method stub
		AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
         
        //Action指向我们的Reciver
        Intent intent =new Intent("WAKEUP");
 
        PendingIntent pi = PendingIntent.getBroadcast(NatureGetUp.this, 1, intent, 0);
         
        Calendar cl = Calendar.getInstance();
         
        cl.add(Calendar.SECOND, (int)ms);//设置启动时间为ms秒后
         
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
