package net.vvakame.gamepadsample;

import net.vvakame.gamepadsample.FeatureListFragment.FeatureListEventsCallback.Selected;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FeatureListFragment extends ListFragment {

	public interface FeatureListEventsCallback {
		public enum Selected {
			INPUT_DATA, LOG, ;

			public static Selected valueOf(int ord) {
				for (Selected val : Selected.values()) {
					if (val.ordinal() == ord) {
						return val;
					}
				}
				throw new IllegalArgumentException(ord + " is invalid ordinal.");
			}
		}

		public void onFeatureSelected(Selected selected);
	}

	private FeatureListEventsCallback mContainerCallback;

	public FeatureListFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof FeatureListEventsCallback) {
			mContainerCallback = (FeatureListEventsCallback) activity;
		} else {
			activity.finish();
			throw new IllegalArgumentException(activity.getClass()
					.getSimpleName()
					+ " must implement "
					+ FeatureListEventsCallback.class.getSimpleName());
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ensureList();
	}

	void ensureList() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1);

		adapter.add(getString(R.string.input_device));
		adapter.add(getString(R.string.log));

		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		mContainerCallback.onFeatureSelected(Selected.valueOf(position));
	}
}
