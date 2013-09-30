private void releaseImageViewBitmap(ImageView iv) {
	if(iv != null) {
		Drawable draw = iv.getDrawable();
		if(draw != null && draw instanceof BitmapDrawable) {
			((BitmapDrawable)draw).getBitmap().recycle();
		}
		draw = null;
	}
}