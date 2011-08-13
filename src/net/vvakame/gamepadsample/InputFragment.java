package net.vvakame.gamepadsample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.gamepadsample.log.Log;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.InputDevice.MotionRange;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class InputFragment extends Fragment implements OnMotionCapture {

	Map<Integer, TextView> textViewMap = new HashMap<Integer, TextView>();
	View rootView;
	TableLayout tableLayout;

	int baseViewCount;
	InputDevice recentlyDevise;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.input, container, false);

		tableLayout = (TableLayout) rootView.findViewById(R.id.table);

		baseViewCount = tableLayout.getChildCount();

		return rootView;
	}

	void onInputEvent(InputEvent event) {
		InputDevice device = event.getDevice();
		List<String> axisNames;
		int id;
		List<MotionRange> motionRanges;
		String name;
		if (device != null) {
			id = device.getId();
			motionRanges = device.getMotionRanges();
			axisNames = new ArrayList<String>();
			for (MotionRange range : motionRanges) {
				axisNames.add(MotionEvent.axisToString(range.getAxis()));
			}
			Collections.sort(axisNames);
			name = device.getName();
		} else {
			id = -1;
			motionRanges = new ArrayList<MotionRange>();
			axisNames = new ArrayList<String>();
			name = "device lost";
		}
		int source = event.getSource();

		setText(R.id.device_name, name);
		setText(R.id.support_axis, Utils.join(axisNames));
		setText(R.id.event_type, event.getClass().getSimpleName());
		setText(R.id.id, id);
		setText(R.id.source,
				Utils.join(Utils.getConstName(InputDevice.class, source)));
		setText(R.id.motion_range_size, motionRanges.size());
	}

	@Override
	public void onGenericMotionEvent(MotionEvent event) {
		Log.d(event.toString());

		clear();

		onInputEvent(event);

		int historySize = event.getHistorySize();
		int pointerCount = event.getPointerCount();
		for (int h = 0; h < historySize; h++) {
			for (int p = 0; p < pointerCount; p++) {
				Log.d(String.format("  pointer %d: (%f,%f)",
						event.getPointerId(p), event.getHistoricalX(p, h),
						event.getHistoricalY(p, h)));
			}
		}
		for (int p = 0; p < pointerCount; p++) {
			Log.d(String.format("  pointer %d: (%f,%f)", event.getPointerId(p),
					event.getX(p), event.getY(p)));
		}

		setText(R.id.history_size, historySize);
		setText(R.id.raw_x, event.getRawX());
		setText(R.id.raw_y, event.getRawY());
		setText(R.id.x, event.getX());
		setText(R.id.y, event.getY());

		InputDevice device = event.getDevice();
		setAxisValues(event, device);

		float axisGeneric1 = event.getAxisValue(MotionEvent.AXIS_GENERIC_1);
		float axisGeneric2 = event.getAxisValue(MotionEvent.AXIS_GENERIC_2);
		float axisGeneric3 = event.getAxisValue(MotionEvent.AXIS_GENERIC_3);
		float axisGeneric4 = event.getAxisValue(MotionEvent.AXIS_GENERIC_4);
		float axisGeneric5 = event.getAxisValue(MotionEvent.AXIS_GENERIC_5);
		float axisGeneric6 = event.getAxisValue(MotionEvent.AXIS_GENERIC_6);
		float axisGeneric7 = event.getAxisValue(MotionEvent.AXIS_GENERIC_7);
		float axisGeneric8 = event.getAxisValue(MotionEvent.AXIS_GENERIC_8);
		float axisGeneric9 = event.getAxisValue(MotionEvent.AXIS_GENERIC_9);
		float axisGeneric10 = event.getAxisValue(MotionEvent.AXIS_GENERIC_10);
		float axisGeneric11 = event.getAxisValue(MotionEvent.AXIS_GENERIC_11);
		float axisGeneric12 = event.getAxisValue(MotionEvent.AXIS_GENERIC_12);
		float axisGeneric13 = event.getAxisValue(MotionEvent.AXIS_GENERIC_13);
		float axisGeneric14 = event.getAxisValue(MotionEvent.AXIS_GENERIC_14);
		float axisGeneric15 = event.getAxisValue(MotionEvent.AXIS_GENERIC_15);
		float axisGeneric16 = event.getAxisValue(MotionEvent.AXIS_GENERIC_16);

		StringBuilder builder = new StringBuilder();
		builder.append("Generic Axis=");
		builder.append(axisGeneric1).append("|");
		builder.append(axisGeneric2).append("|");
		builder.append(axisGeneric3).append("|");
		builder.append(axisGeneric4).append("|");
		builder.append(axisGeneric5).append("|");
		builder.append(axisGeneric6).append("|");
		builder.append(axisGeneric7).append("|");
		builder.append(axisGeneric8).append("|");
		builder.append(axisGeneric9).append("|");
		builder.append(axisGeneric10).append("|");
		builder.append(axisGeneric11).append("|");
		builder.append(axisGeneric12).append("|");
		builder.append(axisGeneric13).append("|");
		builder.append(axisGeneric14).append("|");
		builder.append(axisGeneric15).append("|");
		builder.append(axisGeneric16);

		Log.d(builder.toString());
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		Log.d(event.toString());

		clear();

		onInputEvent(event);

		setText(R.id.key_code, event.getKeyCode());
		setText(R.id.key_code_str, KeyEvent.keyCodeToString(event.getKeyCode()));
		setText(R.id.action,
				JoystickUtils.convKeyEvent("ACTION_", event.getAction()));
	}

	public void clear() {
		for (TextView textView : textViewMap.values()) {
			textView.setText("");
		}
	}

	void setText(int resId, Object text) {
		if (!textViewMap.containsKey(resId)) {
			textViewMap.put(resId, (TextView) rootView.findViewById(resId));
		}
		textViewMap.get(resId).setText(String.valueOf(text));
	}

	void setAxisValues(MotionEvent event, InputDevice device) {
		if (device == null) {
			removeAxisViewes();
			return;
		} else if (recentlyDevise == null
				|| device.getId() != recentlyDevise.getId()) {
			addAxisViewes(device);
			recentlyDevise = device;
			Log.d("device changed.");
		}
		setAxisValuesToTextView(event, device);
	}

	void removeAxisViewes() {
		while (baseViewCount < tableLayout.getChildCount()) {
			tableLayout.removeViewAt(baseViewCount);
		}
	}

	void addAxisViewes(InputDevice device) {
		removeAxisViewes();

		final Context context = getActivity();

		List<String> axisNames = JoystickUtils.getAxisNames(device);
		Collections.sort(axisNames);
		for (String axisName : axisNames) {
			TableRow row = new TableRow(context);
			TextView text;
			{
				text = new TextView(context);
				text.setText(axisName);
				row.addView(text);
			}
			{
				text = new TextView(context);
				text.setTag(axisName);
				row.addView(text);
			}
			tableLayout.addView(row);
		}
	}

	void setAxisValuesToTextView(MotionEvent event, InputDevice device) {
		if (device == null) {
			return;
		}

		List<MotionRange> motionRanges = device.getMotionRanges();
		for (MotionRange range : motionRanges) {
			String axisName = MotionEvent.axisToString(range.getAxis());
			TextView textView = (TextView) rootView.findViewWithTag(axisName);
			textView.setText(String.valueOf(event.getAxisValue(range.getAxis())));
		}
	}
}
