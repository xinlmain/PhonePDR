package com.example.xin.simplepdr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xin on 8/25/2016.
 */
public class CanvasView extends View {
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    boolean initialized = false;
    private Point mPoint = new Point(0,0);
    private List<Point> historyPoints = new ArrayList<Point>();

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        {
            mPaint.setColor(Color.BLACK);
            mPath.moveTo(0, height/2);
            mPath.lineTo(width,height/2);
            mPath.moveTo(width/2, 0);
            mPath.lineTo(width/2, height);
            mPath.moveTo(width/2 + 50, height/2);
            mPath.lineTo(width/2 + 50, height/2 - 4);
            // draw the mPath with the mPaint on the canvas when onDraw
            canvas.drawPath(mPath, mPaint);
            mPaint.setStrokeWidth(1f);
            canvas.drawText("5m", width/2 + 45, height/2 + 14, mPaint);
            mPaint.setStrokeWidth(4f);

            initialized = true;
            // set color to green then
            mPaint.setColor(Color.GREEN);
            for (Point p : historyPoints){
                canvas.drawPoint(p.x, p.y, mPaint);
            }
        }
        {
            mPaint.setColor(Color.RED);
            canvas.drawPoint(mPoint.x, mPoint.y, mPaint);
        }
    }

    public void drawPoint(int x, int y) {
        mPoint.x = x;
        mPoint.y = y;
        historyPoints.add(new Point(x, y));
        invalidate();
    }
    /*
        // when ACTION_DOWN start touch according to the x,y values
        private void startTouch(float x, float y) {
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        // when ACTION_MOVE move touch according to the x,y values
        private void moveTouch(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOLERANCE || dy >= TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }



        // when ACTION_UP stop touch
        private void upTouch() {
            mPath.lineTo(mX, mY);
        }

        //override the onTouchEvent
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    upTouch();
                    invalidate();
                    break;
            }
            return true;
        }*/
    public void clearCanvas() {
        mPath.reset();
        historyPoints.clear();
        invalidate();
    }
}
