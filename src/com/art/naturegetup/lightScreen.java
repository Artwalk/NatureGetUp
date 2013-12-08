package com.art.naturegetup;

import java.io.IOException;

import android.R.integer;
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
import android.view.Window;
import android.view.WindowManager;

public class lightScreen extends Activity {

	WakeLock wakeLock;
	PowerManager pm;
	KeyguardLock kl;
	KeyguardManager km;

	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		mp = new MediaPlayer();// 构建MediaPlayer对象

		setContentView(R.layout.light_screen);

		lightScreen();
		playMusic();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		startAutoBrightness();

		if (mp != null) {
			mp.release();
			mp = null;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
		stopAutoBrightness();
		
		unLockScreen();
		setBrightness(255);
		
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	void unLockScreen() {
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
	}

	public void setBrightness(int brightness) {
//        WindowManager.LayoutParams lp1 =  getWindow().getAttributes();
//        lp1.screenBrightness = (float)50.0;
//        getWindow().setAttributes(lp1);
        
        Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, brightness);
	}

	/** * 停止自动亮度调节 */
	public void stopAutoBrightness() {
		Settings.System.putInt(getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}
	
	public void startAutoBrightness() {
		Settings.System.putInt(getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
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

}
