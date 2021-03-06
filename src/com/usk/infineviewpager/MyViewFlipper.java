package com.usk.infineviewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
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

public class MyViewFlipper extends RelativeLayout {

    private PageView _currentView,_adjacentNext,_adjacentPrev;
    private int MIN_MOVE_TO_CHANGE_PAGE = 60;
    private int NO_OF_PIXELS_TO_MOVE_FOR_ANIM ;
    private ViewPagerController _viewPagerController;
    
    public static abstract class BaseViewFlipperController
    {
    	public abstract PageView getPreviousView(PageView oldView);
    	public abstract PageView getNextView(PageView oldView);
    	public abstract void onPageChanged(PageView currentPageView);
    }
    
	public MyViewFlipper(Context context) {
		super(context);
		init(context);
	}

	public MyViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyViewFlipper(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public void setController(ViewPagerController viewPagerController)
	{
		_viewPagerController = viewPagerController;
		_viewPagerController.viewFlipperInitiated(this);
	}
	public ViewPagerController getController()
	{
		return _viewPagerController;
	}
	private void init(Context context)
	{
		NO_OF_PIXELS_TO_MOVE_FOR_ANIM = (int) (35 * context.getResources().getDisplayMetrics().density);
	}
	
	Point startTouchPoint ,endTouchPoint;
	int offsetX,offsetY;
	
	
	public boolean touch(MotionEvent e1)
	{
		switch (e1.getAction()) 
		{
			case MotionEvent.ACTION_DOWN:
				offsetX = 0;
				offsetY = 0;
				startTouchPoint = new Point((int)e1.getX() , (int)e1.getY());
				
				break;
				
			case MotionEvent.ACTION_MOVE:
				
				if(startTouchPoint.x>e1.getX())
				{
					//moving to next page
					if(_adjacentNext != null)
					{
						offsetX = (int)(e1.getX() - startTouchPoint.x);
						positionPages(offsetX,offsetY);
					}
				}
				else if(startTouchPoint.x<e1.getX())
				{
					//moving to previous page
					if(_adjacentPrev != null)
					{
						offsetX = (int)(e1.getX() - startTouchPoint.x);
						positionPages(offsetX,offsetY);
					}
				}
				
				break;
				
			case MotionEvent.ACTION_UP:
				endTouchPoint = new Point((int)e1.getX() , (int)e1.getY());
				
				if((startTouchPoint.x-endTouchPoint.x)>MIN_MOVE_TO_CHANGE_PAGE )
				{
					//flip success
	                //moving forward direction
					if(_viewPagerController.getNextView(_currentView) != null)
					{
						//complete remaining page move animation
						((Activity)getContext()).runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								completeNextPageAnim();
							}
						});
						
					}
					
				}
				else if((endTouchPoint.x-startTouchPoint.x)>MIN_MOVE_TO_CHANGE_PAGE)
				{
					//flip success
	                //moving backward direction
					if(_viewPagerController.getPreviousView(_currentView) != null)
					{
						//complete remaining page move animation
						((Activity)getContext()).runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								completePreviousPageAnim();
							}
						});
						
					}
				}
				else
				{
					offsetX = 0;
					positionPages(offsetX,offsetY);
				}
					
				break;
	
			default:
				break;
		}
		
		return true;
	}
	
	private void positionPages(final int offsetX,final int offsetY)
	{
		((Activity)getContext()).runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				if(_adjacentNext != null)
				{
					RelativeLayout.LayoutParams paramsAdjacentNext = new RelativeLayout.LayoutParams(getMeasuredWidth(), getMeasuredHeight());
					paramsAdjacentNext.leftMargin= getMeasuredWidth() + offsetX;
					paramsAdjacentNext.rightMargin = -(getMeasuredWidth() + offsetX);
					_adjacentNext.setLayoutParams(paramsAdjacentNext);
				}
				
				if(_adjacentPrev != null)
				{
					RelativeLayout.LayoutParams paramsAdjacentPrev = new RelativeLayout.LayoutParams(getMeasuredWidth(), getMeasuredHeight());
					paramsAdjacentPrev.leftMargin= -getMeasuredWidth() + offsetX;
					paramsAdjacentPrev.rightMargin = -(-getMeasuredWidth() + offsetX);
					_adjacentPrev.setLayoutParams(paramsAdjacentPrev);
				}
				if(_currentView != null)
				{
					RelativeLayout.LayoutParams paramsCurrentPage = new RelativeLayout.LayoutParams(getMeasuredWidth(), getMeasuredHeight());
					paramsCurrentPage.leftMargin= offsetX;
					paramsCurrentPage.rightMargin = -offsetX;
					_currentView.setLayoutParams(paramsCurrentPage);
				}
			}
		});
	}
	
	private void completeNextPageAnim()
	{
		offsetX = offsetX-NO_OF_PIXELS_TO_MOVE_FOR_ANIM;
		if(Math.abs(offsetX)>=getMeasuredWidth())
	    {
			//add next page remove previous page
			if(_adjacentPrev != null)
	        {
	            removeView(_adjacentPrev);
	            _adjacentPrev = null;
	        }
	        _adjacentPrev =_currentView;
	        _currentView = _adjacentNext;
	        _adjacentNext = _viewPagerController.getNextView(_currentView);
	        if(_adjacentNext != null)
	        {
	        	addView(_adjacentNext);
	        }
	        offsetX = offsetY = 0;
	        positionPages(0, 0);
	        _viewPagerController.onPageChanged(_currentView);
	    }
	    else
	    {
	    	Message msg = new Message();
	    	msg.arg1 = offsetX;
	    	msg.arg2 = offsetY;
	    	msg.what = 100;
	    	handler.sendMessage(msg);
	    	positionPages(offsetX, offsetY);
	    }
	}
	
	private void completePreviousPageAnim()
	{
		offsetX = offsetX+NO_OF_PIXELS_TO_MOVE_FOR_ANIM;
		if(Math.abs(offsetX)>=getMeasuredWidth())
	    {
	        //add previuos page remove next page
			if(_adjacentNext != null)
	        {
	            removeView(_adjacentNext);
	            _adjacentNext = null;
	        }
	        _adjacentNext = _currentView;
	        _currentView = _adjacentPrev;
	        _adjacentPrev = _viewPagerController.getPreviousView(_currentView);
	        if(_adjacentPrev != null)
	        {
	        	addView(_adjacentPrev);
	        }
	        offsetX = offsetY = 0;
        	positionPages(0, 0);
        	_viewPagerController.onPageChanged(_currentView);
	    }
	    else
	    {
	    	Message msg = new Message();
	    	msg.arg1 = offsetX;
	    	msg.arg2 = offsetY;
	    	msg.what = 200;
	    	handler.sendMessage(msg);
	    	positionPages(offsetX, offsetY);
	    }
	}
	
	

	
	Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) 
		{
			if(msg.what == 100)
			{
				int marginLeft = msg.arg1;
				int marginTop = msg.arg2;
				positionPages(marginLeft,marginTop);
				completeNextPageAnim();
			}
			else if(msg.what == 200)
			{
				int marginLeft = msg.arg1;
				int marginTop = msg.arg2;
				positionPages(marginLeft,marginTop);
				completePreviousPageAnim();
			}
			return false;
		}
	});
	
	public void initWithView(PageView view) 
	{
		removeAllViews();
		removeAllViewsInLayout();
		_adjacentNext= null;
		_adjacentPrev = null;
		_currentView = null;
		 
		addView(view,0);
		_currentView = view;
		_viewPagerController.onPageChanged(_currentView);
		_currentView.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkPageBuffer();
			}
		});
		
	}
	
	public PageView getCurrentPageView()
	{
		return _currentView;
	}
	
	public void onPageOutOfRange()
	{
//		onSwipeRight();//load last page of same chapter
	}

	public void checkPageBuffer()
	{
		if(_adjacentNext == null)
		{
			_adjacentNext = _viewPagerController.getNextView(_currentView);
			if(_adjacentNext != null)
			{
				addView(_adjacentNext);
			}
		}
		if(_adjacentPrev == null)
		{
			_adjacentPrev = _viewPagerController.getPreviousView(_currentView);
	        if(_adjacentPrev != null)
	        {
	        	addView(_adjacentPrev);
	        }
		}
        offsetX = offsetY = 0;
    	positionPages(0, 0);
		
	}
}
