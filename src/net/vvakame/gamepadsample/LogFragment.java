package net.vvakame.gamepadsample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LogFragment extends ListFragment {

	public interface LogEventsCallback {
		public List<String> getLogList();

		public void onLogSelected(int idx);
	}

	private LogEventsCallback mContainerCallback;

	TextView mDetail;

	List<String> mLogList;

	public LogFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.log, container, false);

		mDetail = (TextView) v.findViewById(R.id.detail);

		mLogList = mContainerCallback.getLogList();

		List<String> list = new ArrayList<String>();
		for (String str : mLogList) {
			list.add(str.split("\n", 2)[0]);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);

		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof LogEventsCallback) {
			mContainerCallback = (LogEventsCallback) activity;
		} else {
			activity.finish();
			throw new IllegalArgumentException(activity.getClass()
					.getSimpleName()
					+ " must implement "
					+ LogEventsCallback.class.getSimpleName());
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String log = mLogList.get(position);
		mDetail.setText(log);
	}
}
