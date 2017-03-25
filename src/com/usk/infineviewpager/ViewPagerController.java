package com.usk.infineviewpager;

import java.util.ArrayList;

import android.content.Context;

import com.usk.infineviewpager.MyViewFlipper.BaseViewFlipperController;

/**
 * Date : 2014-08-12 Mumbai ,India
 * 
 * usk.kamineni@gmail.com
 * 
 * @author Uday Sravan K
 *
 */

public class ViewPagerController extends BaseViewFlipperController
{

	private MyViewFlipper _mViewPager;
	private ViewPagerControllerCallBacks _callBackListener;
	private ArrayList<String> _data;
	
	public void setData(ArrayList<String> data,ViewPagerControllerCallBacks callBackListener)
	{
		this._data = data;
		_callBackListener = callBackListener;
	}
	
	public void viewFlipperInitiated(MyViewFlipper viewFlipper)
	{
		_mViewPager = viewFlipper;
	}
	
	private Context getContext()
	{
		return _mViewPager.getContext();
	}
	
	public interface ViewPagerControllerCallBacks
	{
		public void onPageChanged(PageView currentPageView); 
	}
	/*public void setData(ArrayList<ChapterVO> chaptersColl,MyWebView myView)
	{
		this.chaptersColl = chaptersColl;
		_my_WebView = myView;
	}*/
	
	@Override
	public PageView getPreviousView(PageView oldPage) 
	{
		
		PageView newPageView = new PageView(getContext(),_mViewPager);
		return newPageView;
	}

	@Override
	public PageView getNextView(PageView oldPage) 
	{
		PageView newPageView = new PageView(getContext(),_mViewPager);
		return newPageView;
	}
	
	@Override
	public void onPageChanged(PageView currentPageView)
	{
		_callBackListener.onPageChanged(currentPageView);
	}
}
