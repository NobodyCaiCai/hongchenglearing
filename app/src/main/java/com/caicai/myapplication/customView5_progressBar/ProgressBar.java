package com.caicai.myapplication.customView5_progressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caicai.myapplication.R;

public class ProgressBar extends View {

    public static final String TAG = "ProgressBar";
    private int mInnerBackground = Color.RED;
    private int mOuterBackground = Color.BLUE;
    private int mRoundWidth = 10;
    private int mProgressTextSize = 10;
    private int mProgressTextColor = Color.GREEN;
    private Paint mInnerCirclePaint;
    private Paint mOuterCirclePaint;
    private Paint mTextPaint;
    private float mCurProcess = 0f;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mInnerBackground = typedArray.getColor(R.styleable.ProgressBar_innerBackground, mInnerBackground);
        mOuterBackground = typedArray.getColor(R.styleable.ProgressBar_outerBackground, mOuterBackground);
        mRoundWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_roundWidth, spToPx(mRoundWidth));
        mProgressTextSize = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, spToPx(mProgressTextSize));
        mProgressTextColor = typedArray.getColor(R.styleable.ProgressBar_processTextColor, mProgressTextColor);
        typedArray.recycle();

        initPaint();
    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());

    }

    private void initPaint() {
        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setColor(mInnerBackground);
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setStrokeWidth(mRoundWidth);
        mInnerCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerCirclePaint.setStyle(Paint.Style.STROKE);

        mOuterCirclePaint = new Paint();
        mOuterCirclePaint.setColor(mOuterBackground);
        mOuterCirclePaint.setAntiAlias(true);
        mOuterCirclePaint.setStrokeWidth(mRoundWidth);
        mOuterCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(mProgressTextColor);
        mOuterCirclePaint.setAntiAlias(true);
        mTextPaint.setTextSize(mProgressTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = spToPx(200);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = spToPx(200);
        }
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // 1. 内部圆
        canvas.save();
        int center = canvas.getWidth() / 2;
        int radius = (canvas.getWidth() - mRoundWidth) / 2;
        canvas.drawCircle(center, center,radius, mInnerCirclePaint);
        canvas.restore();
        // 2. 外部圆弧
        canvas.save();
        RectF rectF = new RectF(mRoundWidth / 2, mRoundWidth / 2, getWidth() - mRoundWidth / 2, getWidth() - mRoundWidth / 2);
        canvas.drawArc(rectF, 0, 360 * mCurProcess, false, mOuterCirclePaint);
        // 3. 文案
        String msg = (String.valueOf(mCurProcess * 100)).split("\\.")[0] + "%";
        Log.i(TAG, "msg: " + msg);
        int textX = getTextX(msg);
        int textY = getTextY();
        canvas.drawText(msg, textX, textY, mTextPaint);
    }

    private int getTextY() {
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        return (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom + getHeight() / 2;
    }

    private int getTextX(String text) {
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        return (getWidth() - bounds.width()) / 2;
    }

    public void setCurProcess(float curProcess) {
        this.mCurProcess = curProcess;
        invalidate();
    }
}
