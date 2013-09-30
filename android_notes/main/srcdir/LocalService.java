public class LocalService extends Service {
	private final IBinder mBinder = new LocalBinder();
	private final Random mGenerator = new Random();
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public int getRandomNumber() {
		return mGenerator.nextInt(100);
	}

	public class LocalBinder extends Binder {
		LocalService getService() {
			return LocalService.this;
		}
	}
}
