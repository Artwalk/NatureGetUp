package com.art.naturegetup;

import java.util.HashMap;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;

public class LightScreen extends Activity {

	WakeLock wakeLock;
	PowerManager pm;
	KeyguardLock kl;
	KeyguardManager km;

	// MediaPlayer mp;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> map;

	static String tag = "art";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// mp = new MediaPlayer();// 构建MediaPlayer对象
		map = new HashMap<Integer, Integer>();
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		
		map.put(1, soundPool.load(this, R.raw.dididi, 1));
		Log.d(tag, "oncreate");

		setContentView(R.layout.activity_light_screen);

		lightScreen();
		playMusic();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		/** Do things requiring the CPU stay active */
		if (wakeLock != null) {
			wakeLock.release();
			wakeLock = null;
		}

		startAutoBrightness();
		soundPool.stop(map.get(1));
		// if (mp != null) {
		// mp.release();
		// mp = null;
		// }
		Log.d(tag, "onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		Log.d(tag, "onPause");
		// getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	void lightScreen() {
		// 背光的亮度的设置
		unLockScreen();

		stopAutoBrightness();
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
		wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.FULL_WAKE_LOCK, "bright");
		// 点亮屏幕
		wakeLock.acquire();
	}

	public void setBrightness(int brightness) {
		// WindowManager.LayoutParams lp1 = getWindow().getAttributes();
		// lp1.screenBrightness = (float)50.0;
		// getWindow().setAttributes(lp1);

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

		 /*  @ soundID 音效池中的ID 
         *  @ leftVolume  左声道 ：0.0-1.0 
         *  @ rightVolume 右声道 ：0.0-1.0 
         *  @ priority  优先权   ： 0 表示最低权限； 
         *  @ loop : 循环  0 == 不循环  -1==永远循环  other==循环指定次数 
         *  @ rate 比率 ：playback 录音重放 rate ： 0.5-2.0 
         * */  
		for (int i = 0; i == 0;) {
			i = soundPool.play(map.get(1), 1, 1, 1, 20, 1);
			SystemClock.sleep(1000);
		}
        
		// AssetFileDescriptor afd;
		//
		// try {
		// afd = getAssets().openFd("dididi.ogg");
		//
		// mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
		// afd.getLength());
		// // mp.setOnCompletionListener(new OnCompletionListener() {
		// // int times = 0;
		// //
		// // @Override
		// // public void onCompletion(MediaPlayer mp) {
		// // // TODO Auto-generated method stub
		// //
		// // if (times < 24) {
		// // mp.start();// 循环播放25次
		// // ++times;
		// // Log.d("art", times + " times");
		// // try {
		// // Thread.sleep(3000);
		// // } catch (InterruptedException e) {
		// // // TODO Auto-generated catch block
		// // e.printStackTrace();
		// // }
		// // }
		// // }
		// // });
		// mp.setLooping(true);
		// mp.prepare();// 准备
		// mp.start();// 开始播放
		// } catch (IllegalArgumentException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SecurityException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IllegalStateException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
