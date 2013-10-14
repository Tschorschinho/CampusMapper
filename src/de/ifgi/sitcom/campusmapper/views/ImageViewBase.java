package de.ifgi.sitcom.campusmapper.views;


import java.util.ConcurrentModificationException;

import de.ifgi.sitcom.campusmapper.handler.UIActionHandler;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class ImageViewBase extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {
	private static final String TAG = "Sample::SurfaceView";

	private SurfaceHolder mHolder;
	private boolean mThreadRun;
	private int mImageWidth = 0;
	private int mImageHeight = 0;
	private int mFrameWidth = 0;
	private int mFrameHeigth = 0;


	// touch stuff
	private int mImageXPosition = 0; // y value of panning
	private int mImageYPosition = 0; // x value of panning
	private float mScaleFactor = 0.5f; // indicate the scalling
	
	UIActionHandler mTouchHandler;

	

	public ImageViewBase(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	
	
	public int getFrameHeigth() {
		return mFrameHeigth;
	}

	public int getFrameWidth() {
		return mFrameWidth;
	}

	public int getImageHeight() {
		return mImageHeight;
	}
	
	public int getImageWidth() {
		return mImageWidth;
	}
	
	

	public int getImageXPosition() {
		return mImageXPosition;
	}

	public int getImageYPosition() {
		return mImageYPosition;
	}

	public float getScaleFactor() {
		return mScaleFactor;
	}

	public UIActionHandler getUIActionHandler() {
		return mTouchHandler;
	}

	public void initImage(int w, int h) {

		this.mImageWidth = w;
		this.mImageHeight = h;
		zoomToImage();
		
		if (mTouchHandler!= null) mTouchHandler.init(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		if(mTouchHandler != null) mTouchHandler.handleTouchEvent(ev, mScaleFactor, mImageXPosition, mImageYPosition);

		return true;
	} // ends onTouch

	protected abstract Bitmap processFrame();

	@Override
	public void run() {

		mThreadRun = true;
		Log.i(TAG, "Starting processing thread");
		while (mThreadRun) {
			Bitmap bmp = null;
			
			bmp = processFrame();

				Canvas canvas = mHolder.lockCanvas();
				if (canvas != null) {
					canvas.drawColor(0, Mode.CLEAR);
					canvas.scale(mScaleFactor, mScaleFactor);
					canvas.translate(mImageXPosition, mImageYPosition);
					
					if (bmp != null && !bmp.isRecycled()) {
						canvas.drawBitmap(bmp, 0, 0, null);
						try {
							if(mTouchHandler != null) mTouchHandler.draw(canvas, mScaleFactor);						
						} catch (ConcurrentModificationException e) {
							Log.e("debug", "Concurrent Modification while trying to draw");
						}
					}
			}
				mHolder.unlockCanvasAndPost(canvas);
		}
	}

	public void setFrameHeigth(int frameHeigth) {
		this.mFrameHeigth = frameHeigth;
	}

	public void setFrameWidth(int frameWidth) {
		this.mFrameWidth = frameWidth;
	}

	public void setImageHeight(int mImageHeight) {
		this.mImageHeight = mImageHeight;
	}

	public void setImageWidth(int mImageWidth) {
		this.mImageWidth = mImageWidth;
	}

	public void setImageXPosition(int mImageXPosition) {
		this.mImageXPosition = mImageXPosition;
	}

	public void setImageYPosition(int mImageYPosition) {
		this.mImageYPosition = mImageYPosition;
	}

	public void setScaleFactor(float mScaleFactor) {
		this.mScaleFactor = mScaleFactor;
	}

	public void setTouchHandler(UIActionHandler touchHandler) {
		if (touchHandler!= null) touchHandler.init(this);
		this.mTouchHandler = touchHandler;
	}

	@Override
	public void surfaceChanged(SurfaceHolder _holder, int format, int width,
			int height) {
		Log.i(TAG, "surfaceChanged");
		
			mFrameWidth = width;
			mFrameHeigth = height;
			
			if (mImageHeight != 0 && mImageWidth != 0) zoomToImage();
				
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i(TAG, "surfaceCreated");
		

		(new Thread(this)).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i(TAG, "surfaceDestroyed");
		mThreadRun = false;
	}
	


	
	/*
	 * touch stuff
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */

	/*
	 * to be called when image (size) has changed
	 */
	public void zoomToImage() {
		
		// find appropriate scaleFactor, i.e. the whole image is shown on the screen
		mScaleFactor = Math.min((float) mFrameWidth / mImageWidth, (float) mFrameHeigth / mImageHeight);
	}

}