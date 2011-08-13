package net.vvakame.gamepadsample;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.vvakame.gamepadsample.log.Log;

public class Utils {

	private static final List<String> EXCLUDE_CLASSES = Arrays.asList(
			"dalvik.system.VMStack", "java.lang.Thread",
			Utils.class.getCanonicalName());

	private Utils() {
	}

	/**
	 * 呼び出し元のクラス名とメソッド名、行番号を取得し返します.
	 * 
	 * @return クラス名#メソッド名/L行番号
	 */
	public static String whereIsHere() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();

		for (StackTraceElement stack : stacks) {
			String stackClass = stack.getClassName();
			if (!EXCLUDE_CLASSES.contains(stackClass)) {

				StringBuilder stb = new StringBuilder();
				stb.append(stack.getFileName().replace(".java", ""));
				stb.append("#");
				stb.append(stack.getMethodName());
				stb.append("/L");
				stb.append(stack.getLineNumber());

				return stb.toString();
			}
		}

		return null;
	}

	public static List<String> getConstName(Class<?> clazz, int val) {
		List<String> result = new ArrayList<String>();

		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			try {
				Object obj = field.get(null);
				if (obj instanceof Integer && ((Integer) obj).intValue() == val) {
					result.add(field.getName());
				}
			} catch (IllegalArgumentException e) {
				Log.w(e);
			} catch (IllegalAccessException e) {
				Log.w(e);
			}
		}

		return result;
	}

	public static List<String> getConstName(Class<?> clazz, String prefix,
			int val) {

		List<String> result = new ArrayList<String>();

		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			try {
				Object obj = field.get(null);
				if (obj instanceof Integer && ((Integer) obj).intValue() == val
						&& field.getName().startsWith(prefix)) {
					result.add(field.getName());
				}
			} catch (IllegalArgumentException e) {
				Log.w(e);
			} catch (IllegalAccessException e) {
				Log.w(e);
			}
		}

		return result;
	}

	public static String join(List<String> list) {
		if (list == null) {
			return "null";
		} else if (list.size() == 0) {
			return "empty";
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			StringBuilder builder = new StringBuilder();
			for (String str : list) {
				builder.append(str).append("|");
			}
			builder.setLength(builder.length() - 1);

			return builder.toString();
		}
	}
}
