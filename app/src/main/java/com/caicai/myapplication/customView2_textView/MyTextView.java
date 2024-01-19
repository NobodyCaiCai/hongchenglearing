package com.caicai.myapplication.customView2_textView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caicai.myapplication.R;

public class MyTextView extends View {
    private final String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.WHITE;
    private final Paint mPaint;
    private static final String TAG = "MyTextView";

    // new的时候调用 eg. new MyTextView(context)
    public MyTextView(Context context) {
        this(context, null);
    }

    // 布局中使用
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // 布局中, 自定义样式中使用
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        mText = typedArray.getString(R.styleable.MyTextView_MyText);
        mTextColor = typedArray.getColor(R.styleable.MyTextView_MyTextColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_MyTextSize, sp2px(mTextSize));
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    private int sp2px(int sp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    /**
     * 用于测量
     * * 三种模式：
     *          MeasureSpec.AT_MOST : warp content
     *          MeasureSpec.EXACTLY : match parent , 具体值
     *          MeasureSpec.UNSPECIFIED
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 1. 高、宽给确定的值或者是match_parent，这个时候不需要计算，给的多少就是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        // 2. 高、宽给的是wrap_content, 需要计算
        // 计算的高度 与 字体的长度和有关 与字体的大小有关， 用画笔来测量
        if (widthMode == MeasureSpec.AT_MOST) {
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    /**
     * 用于绘制
     * canvas.drawArc();
     * canvas.drawCircle();
     * canvas.drawText();
     * *   drawText(@NonNull String text, float x, float y, @NonNull Paint paint)
     *          *   x: 开始的位置
     *          *   y: 基线
     */

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText, getPaddingLeft(), getBaseline(mPaint, (float) getHeight() / 2), mPaint);
    }

    /**
     * 计算绘制文字时的基线到中轴线的距离
     *
     * @param p
     * @return 基线和centerY的距离
     */
    public static float getBaseline(Paint p, Float height) {
        Paint.FontMetricsInt fontMetrics = p.getFontMetricsInt();
        return (float) (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom + height;
    }

    /**
     * 用于手指触摸事件
     * 时间分发，事件拦截
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("TAG", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }
}
