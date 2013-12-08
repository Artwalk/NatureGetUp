package com.art.naturegetup;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class NatureGetUp extends Activity {

	MediaPlayer mp;
	Timer timer;
	WakeLock wakeLock;
	PowerManager pm;
	KeyguardLock kl;
	KeyguardManager km;
	TimerTask timerTask;
	
	Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//隐藏标题栏
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_nature_get_up);

		// art
		
		timerTask = new TimerTask() {
			public void run() {
				// 在此处添加执行的代码
				playMusic();
				lightScreen();
			}
		};	
		
		bt = (Button) findViewById(R.id.Sleep);
		bt.setOnClickListener(new OnClickListener() {
			double h = 6.5;
			@Override
			public void onClick(View v) {
				timer = new Timer();
				timer.schedule(timerTask, (long) (1000*60*h));// 开启定时器，delay 1s后执行task
				Log.d("art", h + " housr 唤醒");
			}
		});
		
		
		mp = new MediaPlayer();// 构建MediaPlayer对象		
		// art

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nature_get_up, menu);
		return true;
	}

	// art

	protected void onDestroy() {
		super.onDestroy();

		if (timer != null) {
			timer.cancel();// 销毁定时器
		}
		if (mp != null) {
			mp.release();
			mp = null;
		}

	}

	protected void onPause() {
		super.onPause();
		/** Do things requiring the CPU stay active */
		if (wakeLock != null) {
			wakeLock.release();
			wakeLock = null;
		}
		// getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	void lightScreen() {
		// 背光的亮度的设置

		// 得到键盘锁管理器对象
		km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		// 参数是LogCat里用的Tag
		kl = km.newKeyguardLock("unLock");
		// 解锁
		kl.disableKeyguard();
		// 获取电源管理器对象
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP, "bright");
		// 点亮屏幕
		wakeLock.acquire();
		stopAutoBrightness();
		setBrightness(255);

		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public void setBrightness(float f) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.screenBrightness = f;
		getWindow().setAttributes(lp);
	}

	/** * 停止自动亮度调节 */
	public void stopAutoBrightness() {
		Settings.System.putInt(getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	void playMusic() {

		AssetFileDescriptor afd;

		try {
			afd = getAssets().openFd("fateubw.ogg");

			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			mp.prepare();// 准备
			mp.start();// 开始播放
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// art

}
