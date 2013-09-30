public class ScreenBroadcastReceiver extends BroadcastReceiver {
	public static final String TAG = "ScreenBroadcastReceiver";
	private PowerManager mPowerManager;
	private PowerManager.WakeLock mWakeLock;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "receive intent: " + intent.getAction());
		mPowerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);

		if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
			Log.d(TAG, "light the screen on.");
			
			mWakeLock = mPowerManager.newWakeLock(
					PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
					"ScreenBroadcastReceiver");
			mWakeLock.acquire(5000);
		}
		if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
			Log.d(TAG, "start video screen saver.");
			
			Intent i = new Intent("zte.com.cn.action.videoscreensaver");
			i.addCategory(Intent.CATEGORY_DEFAULT);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
			
			if(mWakeLock != null && mWakeLock.isHeld()) {
				Log.d(TAG, "Wake Lock released.");
				// wake lock is released automatically after 5s,
				// so, we do NOT need to release it.
				// if wake lock is released too fast, android 4.1 is behave abnormally.
				// mWakeLock.release();
			}
		}
	}
}
