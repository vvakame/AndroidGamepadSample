package net.vvakame.gamepadsample.log;

import android.util.Log;

public class LogLogger implements Logger {

	public static final String TAG = "JoysticksAndGamepads";

	private LogLogger() {
	}

	public static LogLogger getInstance() {
		return new LogLogger();
	}

	public int d(int depth, String msg) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			builder.append('\t');
		}
		builder.append(msg);
		return Log.d(TAG, builder.toString());
	}

	public int d(String msg) {
		return Log.d(TAG, msg);
	}

	@Override
	public int w(String msg) {
		return Log.w(TAG, msg);
	}

	@Override
	public int w(String msg, Throwable th) {
		return Log.w(TAG, msg, th);
	}

	@Override
	public int w(Throwable th) {
		return Log.w(TAG, th);
	}
}
