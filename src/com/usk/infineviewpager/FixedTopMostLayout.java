package com.usk.infineviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Date : 2014-08-12 Mumbai ,India
 * 
 * usk.kamineni@gmail.com
 * 
 * @author Uday Sravan K
 *
 */

public class FixedTopMostLayout extends RelativeLayout 
{
	
	private MyViewFlipper _myViewFlipper= null;
	
	public FixedTopMostLayout(Context context) {
		super(context);
		init(context);
	}

	public FixedTopMostLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public FixedTopMostLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) 
	{
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		_myViewFlipper.touch(event);
		return true;
	}
	
	public void setMyViewFlipper(MyViewFlipper myViewFlipper) 
	{
		_myViewFlipper = myViewFlipper;
	}
}