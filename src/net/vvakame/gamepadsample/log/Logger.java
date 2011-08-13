package net.vvakame.gamepadsample.log;

public interface Logger {
	public abstract int d(int depth, String msg);

	public abstract int d(String msg);

	public abstract int w(String msg);

	public abstract int w(String msg, Throwable th);

	public abstract int w(Throwable th);
}
