package com.caicai.myapplication.customView4_colorTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.caicai.myapplication.R;

public class ColorTrackTextView extends androidx.appcompat.widget.AppCompatTextView {
    private final Paint mOriginPaint;
    private final Paint mChangePaint;
    private float mCurrentProgress = 0f;
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT,  // changeColor 从左变化到右边
        RIGHT_TO_LEFT   // originColor 从左变化到右边
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int mOriginColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        int mChangeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());
        mOriginPaint = getPaintByColor(mOriginColor);
        mChangePaint = getPaintByColor(mChangeColor);
        typedArray.recycle();
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        // 防抖动
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas); // 必要的，否则自定义text中会有两个text文字
        int middle = (int) (getWidth() * mCurrentProgress);
        if (mDirection == Direction.LEFT_TO_RIGHT) { // 左边是changePain, 右边是originPaint
            // 绘制变色的
            drawText(canvas, 0, middle, mChangePaint);
            // 绘制不变色的
            drawText(canvas, middle, getWidth(), mOriginPaint);
        }

        if (mDirection == Direction.RIGHT_TO_LEFT) { // 左边是originPaint, 右边是changePaint
            // 绘制不变色的
            drawText(canvas, 0, middle, mOriginPaint);
            // 绘制变色的
            drawText(canvas, middle, getWidth(), mChangePaint);
        }
    }

    /**
     * 绘制变色的text
     * @param canvas 画布
     * @param start 横坐标起点
     * @param end 横坐标终点
     * @param paint 当前画笔
     */
    private void drawText(Canvas canvas, int start, int end, Paint paint) {
        canvas.save();
        // 裁剪画布，让颜色覆盖这部分文字颜色
        canvas.clipRect(new Rect(start, 0 ,end, getHeight()));
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (getWidth() -  bounds.width()) / 2;
        int y = getBaseLine(paint);
        canvas.drawText(text, x, y, paint);
        canvas.restore();
    }

    private int getBaseLine(Paint paint) {
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        return (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom + getHeight() / 2;
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }
    
    public void setCurrentProgress(float progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }

    public void setChangeColor(int color) {
        this.mChangePaint.setColor(color);
    }

    public void setOriginColor(int color) {
        this.mOriginPaint.setColor(color);
    }
}
