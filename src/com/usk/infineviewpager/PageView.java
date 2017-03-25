package com.usk.infineviewpager;


import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Date : 2014-08-12 Mumbai ,India
 * 
 * usk.kamineni@gmail.com
 * 
 * @author Uday Sravan K
 *
 */

public class PageView extends RelativeLayout
{
	private MyViewFlipper _mMyViewPager;
	
	public PageView(Context context,MyViewFlipper myViewPager) {
		super(context);
		_mMyViewPager = myViewPager;
		init(context);
	}

	public PageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context)
	{
		setGravity(Gravity.CENTER);
		setBackgroundColor(Color.GREEN);
		TextView tv = new TextView(context);
		//MainActivity.TEMP_COUNT++;
		tv.setText(""+new Date().toString());
		tv.setTextSize(35);
		tv.setTextColor(Color.BLACK);
		addView(tv);
	}
}
