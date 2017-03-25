package com.usk.infineviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.usk.infineviewpager.ViewPagerController.ViewPagerControllerCallBacks;

public class MainActivity extends ActionBarActivity {

	public static int TEMP_COUNT = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements ViewPagerControllerCallBacks{

		ViewPagerController _mController ;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			FixedTopMostLayout topMostLayout = (FixedTopMostLayout) rootView.findViewById(R.id.topMostLayer);
			_mController = new ViewPagerController();
			_mController.setData(null, this);
			MyViewFlipper viewFlipper = (MyViewFlipper) rootView.findViewById(R.id.myViewPager);
			viewFlipper.setController(_mController);
			topMostLayout.setMyViewFlipper(viewFlipper);
			PageView pageView = new PageView(getActivity(), viewFlipper);
			viewFlipper.initWithView(pageView);
			return rootView;
		}

		@Override
		public void onPageChanged(PageView currentPageView) {
			
		}
	}

}
