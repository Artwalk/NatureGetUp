package com.art.naturegetup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WakeUpBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, LightScreen.class);

		// 在Service中启动Activity，必须设置如下标志
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(i);
	}

}
