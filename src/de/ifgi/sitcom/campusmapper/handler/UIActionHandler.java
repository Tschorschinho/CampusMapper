package de.ifgi.sitcom.campusmapper.handler;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.actionbarsherlock.view.MenuItem;

import de.ifgi.sitcom.campusmapper.views.ImageViewBase;

/*
 * defined form of classes to control ui behaviour when certain 
 * data type (such as corridor or room) is selected
 */
public abstract class UIActionHandler{
	
	
	public abstract void handleMenuAction(MenuItem item);
	
	public abstract int getMenu();
	
	public abstract void draw(Canvas canvas, float scaleFactor);
	
	public abstract boolean handleTouchEvent(MotionEvent ev, float scaleFactor, int xPos, int yPos);
	
	public abstract void init(ImageViewBase imageViewBase);
	
}
