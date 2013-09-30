public class ReceiverService extends Service {
	public static final String TAG = "ReceiverService";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "ReceiverService: onCreate()");
		super.onCreate();
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenBroadcastReceiver();
        registerReceiver(mReceiver, filter);
	}
}
