package net.vvakame.gamepadsample;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import net.vvakame.gamepadsample.FeatureListFragment.FeatureListEventsCallback;
import net.vvakame.gamepadsample.LogFragment.LogEventsCallback;
import net.vvakame.gamepadsample.log.Log;
import net.vvakame.gamepadsample.log.StringLogger;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends Activity implements
		FeatureListEventsCallback, LogEventsCallback {

	OnMotionCapture mGenericCapture;

	Deque<String> mEventLog = new ArrayDeque<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onFeatureSelected(Selected selected) {

		FragmentManager fm = getFragmentManager();

		FragmentTransaction ft = fm.beginTransaction();

		switch (selected) {
		case INPUT_DATA: {
			InputFragment fragment = new InputFragment();
			ft.replace(R.id.detail_container, fragment, selected.name());

			mGenericCapture = fragment;
		}
			break;
		case LOG: {
			LogFragment fragment = new LogFragment();
			ft.replace(R.id.detail_container, fragment, selected.name());

			mGenericCapture = null;
		}
			break;
		default:
			Toast.makeText(this, selected.name(), Toast.LENGTH_SHORT).show();
			return;
		}

		ft.commit();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		StringLogger logger = new StringLogger();
		JoystickUtils.dumpKeyEvent(logger, event);
		mEventLog.add(logger.toStringLog());

		if (mGenericCapture != null) {
			mGenericCapture.onKeyEvent(event);
		}

		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean dispatchGenericMotionEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		Log.d("x=" + x + ", y=" + y);

		StringLogger logger = new StringLogger();
		JoystickUtils.dumpMotionEvent(logger, event);
		mEventLog.add(logger.toStringLog());

		if (mGenericCapture != null) {
			mGenericCapture.onGenericMotionEvent(event);
		}

		return super.dispatchGenericMotionEvent(event);
	}

	@Override
	public List<String> getLogList() {
		return new ArrayList<String>(mEventLog);
	}

	@Override
	public void onLogSelected(int idx) {
		Toast.makeText(this, String.valueOf(idx), Toast.LENGTH_SHORT).show();
	}
}