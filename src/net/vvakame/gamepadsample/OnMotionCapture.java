package net.vvakame.gamepadsample;

import android.view.KeyEvent;
import android.view.MotionEvent;

public interface OnMotionCapture {
	public void onGenericMotionEvent(MotionEvent event);

	public void onKeyEvent(KeyEvent event);
}
