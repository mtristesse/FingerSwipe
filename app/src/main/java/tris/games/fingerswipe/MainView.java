package tris.games.fingerswipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class MainView extends SurfaceView {

	public static final String TAG = "FingerSwipe";
	public FingerSwipeActivity context;
	public int screenWidth, screenHeight;

	public MainView(Context c) {
		super(c);
		this.context = (FingerSwipeActivity) c;
		setKeepScreenOn(true);

		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		context.thread.onTouchEvent(event);
		return true;
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh)	{
		screenWidth = w;
		screenHeight = h;
	}
}
