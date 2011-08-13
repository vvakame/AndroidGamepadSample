package net.vvakame.gamepadsample.log;

import android.util.Log;

public class StringLogger implements Logger {

	StringBuilder builder = new StringBuilder();

	public int d(int depth, String msg) {
		for (int i = 0; i < depth; i++) {
			builder.append('\t');
		}
		builder.append(msg).append('\n');
		return Log.DEBUG;
	}

	public int d(String msg) {
		builder.append(msg).append('\n');
		return Log.DEBUG;
	}

	@Override
	public int w(String msg) {
		builder.append(msg).append('\n');
		return Log.WARN;
	}

	@Override
	public int w(String msg, Throwable th) {
		builder.append(msg).append('\n').append(th.getLocalizedMessage())
				.append('\n');
		return Log.WARN;
	}

	@Override
	public int w(Throwable th) {
		builder.append(th.getLocalizedMessage()).append('\n');
		return Log.WARN;
	}

	public String toStringLog() {
		return builder.toString();
	}
}
