package net.vvakame.gamepadsample.log;

public class Log {

	static Logger logger = LogLogger.getInstance();

	private Log() {
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public static void setLogger(Logger logger) {
		Log.logger = logger;
	}

	public static int d(int depth, String msg) {
		return logger.d(depth, msg);
	}

	public static int d(String msg) {
		return logger.d(msg);
	}

	public static int w(String msg) {
		return logger.w(msg);
	}

	public static int w(String msg, Throwable th) {
		return logger.w(msg, th);
	}

	public static int w(Throwable th) {
		return logger.w(th);
	}
}
