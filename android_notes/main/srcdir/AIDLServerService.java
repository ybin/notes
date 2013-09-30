/* AIDLServerService.java */
public class AIDLServerService extends Service {
	private IAIDLServerService.Stub mBinder = new IAIDLServerService.Stub() {
		public String sayHello() throws RemoteException {
			return "Hello from Remote.";
		}

		public Book getBook() throws RemoteException {
			Book book = new Book("Android Notes", 42);
			return book;
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
