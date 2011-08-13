package net.vvakame.gamepadsample;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.gamepadsample.log.Log;
import net.vvakame.gamepadsample.log.Logger;
import android.view.InputDevice;
import android.view.InputDevice.MotionRange;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class JoystickUtils {
	private JoystickUtils() {
	}

	static List<Integer> getAxisIds(InputDevice device) {
		List<Integer> axisList = new ArrayList<Integer>();
		if (device == null) {
			return axisList;
		}
		for (MotionRange range : device.getMotionRanges()) {
			axisList.add(range.getAxis());
		}

		return axisList;
	}

	static void dumpMotionRange(MotionRange range) {
		final int depth = 4;

		Log.d(depth - 1, "motion range info------ " + range.toString());

		Log.d(depth, "axis=" + MotionEvent.axisToString(range.getAxis()));
		Log.d(depth, "source=" + range.getSource()); // どうしようかな
		Log.d(depth, "flat=" + range.getFlat());
		Log.d(depth, "fuzz=" + range.getFuzz());
		Log.d(depth, "max=" + range.getMax());
		Log.d(depth, "min=" + range.getMin());
		Log.d(depth, "range=" + range.getRange());
	}

	static void dumpInputDevice(InputDevice device) {
		final int depth = 3;

		Log.d(depth - 1, "input device info------ " /*
													 * + device.toString()
													 * 1行じゃないので
													 */);
		if (device == null) {
			Log.d(depth, "input device is null...");
			return;
		}

		Log.d(depth, "id=" + device.getId());
		Log.d(depth,
				"keyboardType="
						+ convDevice("KEYBOARD_TYPE", device.getKeyboardType()));
		Log.d(depth, "name=" + device.getName());
		Log.d(depth, "sources=" + device.getSources()); // 謎の値

		for (MotionRange range : device.getMotionRanges()) {
			dumpMotionRange(range);
		}
	}

	static void dumpInputEvent(InputEvent event) {
		final int depth = 2;

		Log.d(depth - 1, "input event info------ " + event.toString());

		Log.d(depth, "deviceId=" + event.getDeviceId());
		Log.d(depth, "source=" + convDevice("SOURCE_", event.getSource())); // convDevice
																			// で正しい

		dumpInputDevice(event.getDevice());
	}

	public static void dumpMotionEvent(Logger logger, MotionEvent event) {
		Logger oldLogger = Log.getLogger();
		Log.setLogger(logger);

		final int depth = 1;

		Log.d(depth - 1, "motion event info------ " + event.toString());

		dumpInputEvent(event);

		Log.d(depth, "action=" // TODO このままじゃダメそう
				+ convMotionEvent("ACTION_", event.getDeviceId()));

		Log.d(depth, "actionIndex=" + event.getActionIndex()); // 謎
		Log.d(depth, "actionMasked=" + event.getActionMasked()); // 謎

		Log.d(depth, "downTime=" + event.getDownTime());
		Log.d(depth, "eventTime=" + event.getEventTime());

		Log.d(depth,
				"edgeFlags=" + convMotionEvent("EDGE_", event.getEdgeFlags()));
		Log.d(depth, "flags=" + convMotionEvent("FLAG_", event.getFlags()));

		Log.d(depth, "metaState=" + convKeyEvent("META_", event.getMetaState())); // convKeyEventで正しい

		Log.d(depth, "rawX=" + event.getRawX());
		Log.d(depth, "rawY=" + event.getRawY());
		Log.d(depth, "xPrecision=" + event.getXPrecision());
		Log.d(depth, "yPrecision=" + event.getYPrecision());

		Log.d(depth, "pointerCount=" + event.getPointerCount());
		Log.d(depth, "historySize=" + event.getHistorySize());

		for (int p = 0; p < event.getPointerCount(); p++) {
			for (int axis : getAxisIds(event.getDevice())) {
				Log.d(depth, MotionEvent.axisToString(axis) + " p" + p + "="
						+ event.getAxisValue(axis, p));
			}
			Log.d(depth, "orientation p" + p + "=" + event.getOrientation(p));
			Log.d(depth, "pointerId p" + p + "=" + event.getPointerId(p));
			Log.d(depth, "pressure p" + p + "=" + event.getPressure(p));
			Log.d(depth, "size p" + p + "=" + event.getSize(p));
			Log.d(depth, "toolMajor p" + p + "=" + event.getToolMajor(p));
			Log.d(depth, "toolMinor p" + p + "=" + event.getToolMinor(p));
			Log.d(depth, "touchMajor" + p + "=" + event.getTouchMajor(p));
			Log.d(depth, "touchMinor p" + p + "=" + event.getTouchMinor(p));
			Log.d(depth, "x p" + p + "=" + event.getX(p));
			Log.d(depth, "y p" + p + "=" + event.getY(p));

			// event.getPointerCoords(p, null); いらんかな
		}

		for (int h = 0; h < event.getHistorySize(); h++) {
			Log.d(depth,
					"historicalOrientation h" + h + "="
							+ event.getHistoricalEventTime(h));
		}

		for (int p = 0; p < event.getPointerCount(); p++) {
			for (int h = 0; h < event.getHistorySize(); h++) {
				for (int axis : getAxisIds(event.getDevice())) {
					Log.d(depth,
							MotionEvent.axisToString(axis) + " p" + p + "h" + h
									+ "="
									+ event.getHistoricalAxisValue(axis, p, h));
				}

				Log.d(depth, "historicalOrientation p" + p + "h" + h + "="
						+ event.getHistoricalOrientation(p, h));
				Log.d(depth,
						"historicalPressure p" + p + "h" + h + "="
								+ event.getHistoricalPressure(p, h));
				Log.d(depth,
						"historicalSize p" + p + "h" + h + "="
								+ event.getHistoricalSize(p, h));

				Log.d(depth, "historicalToolMajor p" + p + "h" + h + "="
						+ event.getHistoricalToolMajor(p, h));
				Log.d(depth, "historicalToolMinor p" + p + "h" + h + "="
						+ event.getHistoricalToolMinor(p, h));
				Log.d(depth,
						"historicalTouchMajor" + p + "h" + h + "="
								+ event.getHistoricalTouchMajor(p, h));
				Log.d(depth, "historicalTouchMinor p" + p + "h" + h + "="
						+ event.getHistoricalTouchMinor(p, h));
				Log.d(depth,
						"historicalX p" + p + "h" + h + "="
								+ event.getHistoricalX(p, h));
				Log.d(depth,
						"historicalY p" + p + "h" + h + "="
								+ event.getHistoricalY(p, h));
			}
		}

		Log.setLogger(oldLogger);
	}

	public static void dumpKeyEvent(Logger logger, KeyEvent event) {
		Logger oldLogger = Log.getLogger();
		Log.setLogger(logger);

		final int depth = 1;

		Log.d(depth - 1, "key event info------ " + event.toString());

		dumpInputEvent(event);

		// まーKeyEventは若干適当…

		Log.d(depth, "action=" // TODO このままじゃダメそう
				+ convMotionEvent("ACTION_", event.getDeviceId()));

		Log.d(depth, "downTime=" + event.getDownTime());
		Log.d(depth, "eventTime=" + event.getEventTime());

		Log.d(depth, "flags=" + convKeyEvent("FLAG_", event.getFlags()));

		Log.d(depth, "metaState=" + convKeyEvent("META_", event.getMetaState()));

		Log.d(depth, "keyCode=" + event.getKeyCode());
		Log.d(depth,
				"keyCodeStr=" + KeyEvent.keyCodeToString(event.getKeyCode()));

		Log.d(depth,
				"isGamepadButton="
						+ KeyEvent.isGamepadButton(event.getKeyCode()));
		Log.d(depth, "repeatCount=" + event.getRepeatCount());

		Log.setLogger(oldLogger);
	}

	// for InputDevice

	public static String toId(InputDevice device) {
		return String.valueOf(device.getId());
	}

	public static String toHoge(InputDevice device) {
		device.getId();
		device.getKeyboardType();
		device.getMotionRanges();
		device.getName();
		device.getSources();
		device.getMotionRange(0);

		return null;
	}

	// for InputEvent

	public static String toHoge(InputEvent event) {
		event.getSource();
		event.getDeviceId();
		event.getDevice();

		return null;
	}

	// for MotionEvent

	public static String toHoge(MotionEvent event) {
		event.getAction();
		event.getActionIndex();
		event.getActionMasked();
		event.getDownTime();
		event.getEdgeFlags();
		event.getEventTime();
		event.getFlags();
		event.getHistorySize();
		event.getMetaState();
		event.getOrientation();

		return null;
	}

	// for KeyEvent

	public static String toHoge(KeyEvent event) {

		return null;
	}

	static String convDevice(String prefix, int val) {
		List<String> list = Utils.getConstName(InputDevice.class, prefix, val);
		if (list.size() == 0) {
			return String.valueOf(val);
		} else {
			return Utils.join(list);
		}
	}

	static String convInputEvent(String prefix, int val) {
		List<String> list = Utils.getConstName(InputEvent.class, prefix, val);
		if (list.size() == 0) {
			return String.valueOf(val);
		} else {
			return Utils.join(list);
		}
	}

	static String convMotionEvent(String prefix, int val) {
		List<String> list = Utils.getConstName(MotionEvent.class, prefix, val);
		if (list.size() == 0) {
			return String.valueOf(val);
		} else {
			return Utils.join(list);
		}
	}

	static String convKeyEvent(String prefix, int val) {
		List<String> list = Utils.getConstName(KeyEvent.class, prefix, val);
		if (list.size() == 0) {
			return String.valueOf(val);
		} else {
			return Utils.join(list);
		}
	}
}
