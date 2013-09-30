public class Book implements Parcelable{
	private String mBookName;
	private int mBookPrice;
	
	public Book(String name, int price) {
		mBookName = name;
		mBookPrice = price;
	}
	
	public void print() {
		System.out.println("Name: " + mBookName);
		System.out.println("Price: " + mBookPrice);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mBookName);
		dest.writeInt(mBookPrice);
	}
	
	public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
		
		@Override
		public Book createFromParcel(Parcel source) {
			// keep order!
			return new Book(source.readString(), source.readInt());
		}
	};
}