package com.caicai.myapplication.customView3_qqStepView;

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

public class QQStepView extends View {
    private int mOutColor = Color.BLUE;
    private int mInnerColor = Color.RED;
    private int mBorderWidth = 20; // px
    private int mStepTextSize = 0;
    private int mStepTextColor = 0;
    private final Paint mOuterPaint;
    private final Paint mInnerPaint;
    private final Paint mTextViewPaint;
    private int mStepMax = 0;
    private int mCurrentStep = 0;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1. 分析效果
        //2. 确定自定义属性，编写attrs.xml
        //3. 在布局中使用
        //4. 在自定义view中获取自定义属性
        //5. 处理onMeasure
        //6. 处理onDraw
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOutColor = typedArray.getColor(R.styleable.QQStepView_innerColor, mOutColor);
        mInnerColor = typedArray.getColor(R.styleable.QQStepView_outerColor, mInnerColor);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.QQStepView_borderWidth, spToPx(mBorderWidth));
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        typedArray.recycle();

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOutColor);
        mOuterPaint.setStrokeWidth(mBorderWidth);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextViewPaint = new Paint();
        mTextViewPaint.setAntiAlias(true);
        mTextViewPaint.setColor(mStepTextColor);
        mTextViewPaint.setTextSize(mStepTextSize);
    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//          设置当Mode == MeasureSpec.AT_MOST时的情况
//        if (widthMode == MeasureSpec.AT_MOST) {
//            widthSize = spToPx(40);
//        }
//        if (heightMode == MeasureSpec.AT_MOST) {
//            heightSize = spToPx(40);
//        }
        setMeasuredDimension(Math.min(widthSize, heightSize), Math.min(widthSize, heightSize));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // 外圆弧
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBorderWidth / 2;
        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, center + radius, center + radius);
        canvas.drawArc(rectF, 135, 270, false, mOuterPaint);
        // 内圆弧
//        if (mStepMax == 0) return;
        float sweepAngle = ((float) mCurrentStep / mStepMax) * 270;
        canvas.drawArc(rectF, 135, sweepAngle, false, mInnerPaint);
        // 画文字
        String msg = String.valueOf(mCurrentStep);
        Rect rect = new Rect();
        mTextViewPaint.getTextBounds(msg, 0, msg.length(), rect);
        float x = (getWidth() - rect.width()) / 2;
        float y = getBaseline(getHeight() / 2);
        canvas.drawText(msg, x, y, mTextViewPaint);
    }

    private float getBaseline(int height) {
        Paint.FontMetricsInt fontMetricsInt = mTextViewPaint.getFontMetricsInt();
        return (float) (fontMetricsInt.bottom - fontMetricsInt.top) / 2  - fontMetricsInt.bottom + height;
    }

    public synchronized void setStepMax(int stepMax) {
        this.mStepMax = stepMax;
    }

    public synchronized void setCurrentStep(int currentStep) {
        Log.i("MainActivity", "currentStep: $currentStep");
        this.mCurrentStep = currentStep;
        invalidate();
    }
}
