package tris.games.fingerswipe;

import java.util.*;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;

public class MainThread extends Thread {

	public static final String TAG = "FingerSwipe";
	
	private MainView view;
	
	private LinkedList<Point> pointList = new LinkedList<Point>();	//to save the touch coordinator
	private final int POINT_MAX = 30;
	
	public MainThread(MainView v) {
		super();
		view = v;
	}	
	
    @Override
    public void run() {
          while (true) {
                 Canvas c = null;
                 try {
                        c = view.getHolder().lockCanvas();
                        update();
                        synchronized (view.getHolder()) {
                               draw(c);	//not use SurfaceView.draw()
                        }
                 } finally {
                        if (c != null) {
                               view.getHolder().unlockCanvasAndPost(c);
                        }
                 }
          }
    }	
    
    private void draw(Canvas c)
    {
		if (c != null) {
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			c.drawRect(new Rect(0, 0, view.screenWidth, view.screenHeight), paint);

//			c.drawCircle(200, 300, 100, paintBlue);
			
//			Point[] points = (Point[])pointList.toArray();
//			for (int i = 0; i < points.length; ++i)
//			{
//    			c.drawCircle(points[i].x, points[i].y, 5, paintBlue);
//			}

//			ListIterator<Point> ite = pointList.listIterator();
//            while (ite.hasNext()) {
//            	Point p = ite.next();
//    			c.drawCircle(p.x, p.y, 5, paintBlue);
//            }

			paint.setColor(Color.WHITE);

			Point lastPoint = null, currentPoint;
			for (int i = 0; i < pointList.size(); i++)
			{
				currentPoint = pointList.get(i);
				c.drawCircle(currentPoint.x, currentPoint.y, 1+ (i / 4), paint);
				
				if (i == 0)	//for the first element
				{
					lastPoint = currentPoint;
					continue;
				}
				
				paint.setStrokeWidth( ( 1 + (i / 4)) * 2);
				//draw a line to the last element
				c.drawLine(currentPoint.x, currentPoint.y, lastPoint.x, lastPoint.y, paint);

//				c.translate(currentPoint.x, currentPoint.y);
//				c.rotate(45);
//				c.drawLine(0, 0, lastPoint.x - currentPoint.x, lastPoint.y - currentPoint.y, paint);
//				c.rotate(-45);
//				c.translate(-currentPoint.x, -currentPoint.y);
				
/*
				paint.setColor(Color.YELLOW);
				int dx = lastPoint.x - currentPoint.x;
				int dy = lastPoint.y - currentPoint.y;
				int w = (int)Math.sqrt(dx * dx + dy * dy);
				//TODO: calculate the rotating degree
				//double t = Math.tan((double)(Math.abs(dy)) / (double)(Math.abs(dx)));
//				double t = Math.tan( (double)dy / (double)dx);
	
				double t = (double)dy / (double)dx;
				float d = (float)Math.toDegrees(Math.atan(t));
				
				Log.i(TAG, "d = " + d);

				
				//d = 45;
				//transformation
				c.translate(currentPoint.x, currentPoint.y);
				c.rotate(d);
				//c.drawLine(0, 0, lastPoint.x - currentPoint.x, lastPoint.y - currentPoint.y, paint);
				c.drawRect(new Rect(0, 0, (int)w, 1+ (i / 2)), paint);
				
				c.rotate(-d);
				c.translate(-currentPoint.x, -currentPoint.y);
*/
				
				lastPoint = currentPoint;	//for the next one
          }

			String strInstruction = view.context.getResources().getString(R.string.instruction);
			paint.setTextSize(20);
			c.drawText(strInstruction, 20, 20, paint);
		}
    }
    
    private void update()
    {
    	//TODO
    }
    
	public boolean onTouchEvent(MotionEvent event)
	{
		Log.i(TAG, "MainThread onTouchEvent");
		int eventAction = event.getAction();
		int ex = (int)event.getX();
		int ey = (int) event.getY();
		
		switch (eventAction)
		{
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_DOWN:
			pointList.clear();
			break;
		case MotionEvent.ACTION_MOVE:
			Point p = new Point(ex, ey);
			pointList.add(p);
			Log.i(TAG, "size = " + pointList.size());
			if (pointList.size() > POINT_MAX)
				pointList.remove();

			break;

		}
		if (eventAction == MotionEvent.ACTION_MOVE)
		{
			
		}
		return true;
	}

}
