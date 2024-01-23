package com.caicai.myapplication.customView8_LetterSideBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
 import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caicai.myapplication.R;

public class LetterSideBar extends View {

    private static final String TAG = "LetterSideBar";
    private static final String[] mLetters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private final int mPaintNormalColor;
    private final int mPaintChangeColor;
    private final int mPaintSize;
    private final int mLetterGap;
    private int currentLetterIndex = -1;
    private OnLetterTouchListener mLetterTouchListener = null;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        mPaintNormalColor = typedArray.getColor(R.styleable.LetterSideBar_paintNormalColor, Color.BLACK);
        mPaintChangeColor = typedArray.getColor(R.styleable.LetterSideBar_paintChangeColor, Color.BLUE);
        mPaintSize = typedArray.getDimensionPixelSize(R.styleable.LetterSideBar_paintSize, spToDp(10));
        mLetterGap = typedArray.getDimensionPixelSize(R.styleable.LetterSideBar_letterGap, spToDp(10));
        typedArray.recycle();
        initPaint();
    }

    private int spToDp(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mPaintNormalColor);
        mPaint.setTextSize(spToDp(mPaintSize));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int len = (int) mPaint.measureText("A");
        int width = getPaddingLeft() + getPaddingRight() + len;
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        for (int i = 0; i < mLetters.length; i++) {
            int x = getXIndex(mLetters[i]);
            int y = getYIndex(i);
            if (i == currentLetterIndex) {
                mPaint.setColor(mPaintChangeColor);
                canvas.drawText(mLetters[i], x, y, mPaint);
            } else {
                mPaint.setColor(mPaintNormalColor);
                canvas.drawText(mLetters[i], x, y, mPaint);
            }
        }
    }

    private int getXIndex(String mLetter) {
        float textLen = mPaint.measureText(mLetter);
        return (int) ((getWidth() - textLen) / 2);
    }

    private int getYIndex(int index) {
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
        int centerY = index * itemHeight + itemHeight / 2 + getPaddingTop() + mLetterGap;
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        return  centerY + (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                performClick();
                float yIndex = event.getY() - getPaddingTop();
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
                currentLetterIndex = (int) (yIndex / itemHeight);
                if (mLetterTouchListener != null && currentLetterIndex >= 0 && currentLetterIndex < mLetters.length) {
                    mLetterTouchListener.touch(mLetters[currentLetterIndex], true);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mLetterTouchListener != null) {
                    mLetterTouchListener.touch(mLetters[currentLetterIndex], false);
                }
                break;
        }

        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setOnLetterTouchListener(OnLetterTouchListener letterTouchListener) {
        this.mLetterTouchListener = letterTouchListener;
    }

    public interface OnLetterTouchListener{
        void touch(CharSequence letter, Boolean isTouch);
    }
}
