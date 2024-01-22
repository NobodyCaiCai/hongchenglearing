package com.caicai.myapplication.customView6_shapeView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShapeView extends View {
    private Shape mShape = Shape.Circle;
    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        switch (mShape) {
            case Circle:
                int center = getWidth() / 2;
                mPaint.setColor(Color.RED);
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                mPaint.setColor(Color.YELLOW);
                Rect rect = new Rect(0, 0, getWidth(), getHeight());
                canvas.drawRect(rect, mPaint);
                break;
            case Triangle:
                float yIndex = (float) (Math.sqrt(3) / 2 * getWidth());
                mPaint.setColor(Color.BLUE);
                mPath.moveTo((float) getWidth() / 2, 0);
                mPath.lineTo( 0, yIndex);
                mPath.lineTo(getWidth(), yIndex);
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    public enum Shape {
        Circle, Square, Triangle
    }

    public void exchangeShape() {
        switch (mShape) {
            case Circle:
                mShape = Shape.Square;
                break;
            case Square:
                mShape = Shape.Triangle;
                break;
            case Triangle:
                mShape = Shape.Circle;
                break;
        }
        invalidate();
    }
}
