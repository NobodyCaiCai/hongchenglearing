package com.caicai.myapplication.customView7_ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caicai.myapplication.R;

public class RatingBar extends View {

    private static final String TAG = "RatingBar";
    private final Bitmap mStartNormal;
    private final Bitmap mStartFocus;
    private final int mGradeNumber;
    private final int mDistanceGap;
    private int mCurGrade = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int startNormalId = typedArray.getResourceId(R.styleable.RatingBar_startNormal, R.drawable.star_normal);
        int startFocusId = typedArray.getResourceId(R.styleable.RatingBar_startFocus, R.drawable.star_pressed);
        mGradeNumber = typedArray.getInt(R.styleable.RatingBar_gradeNumber, 5);
        mStartNormal = zoomImg2(BitmapFactory.decodeResource(getResources(),startNormalId), spToDp(35), spToDp(35));
        mStartFocus = zoomImg2(BitmapFactory.decodeResource(getResources(), startFocusId), spToDp(35), spToDp(35));
        mDistanceGap = spToDp(typedArray.getDimensionPixelSize(R.styleable.RatingBar_distanceGap, 0));
        typedArray.recycle();
    }

    // 压缩bitmap，解决图片过大的问题
    private static Bitmap zoomImg2(Bitmap bm, int targetWidth, int targetHeight) {
        int srcWidth = bm.getWidth();
        int srcHeight = bm.getHeight();
        float widthScale = targetWidth * 1.0f / srcWidth;
        float heightScale = targetHeight * 1.0f / srcHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale, 0, 0);
        Bitmap bmpRet = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpRet);
        Paint paint = new Paint();
        canvas.drawBitmap(bm, matrix, paint);
        return bmpRet;
    }

    private int spToDp(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 宽度：mGradeNumber个星星的高度 + padding + 每个星星之间的间距
        int width = getPaddingLeft() + getPaddingRight() + mStartFocus.getWidth() * mGradeNumber + mDistanceGap * mGradeNumber;
        // 高度：一张图片的高度
        int height = getPaddingBottom() + getPaddingTop() + mStartFocus.getHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        for (int i = 0; i < mGradeNumber; i++) {
            int left = mDistanceGap;
            if (i != 0) left = i * (mStartNormal.getWidth() + mDistanceGap) + mDistanceGap;
            if (i >= mCurGrade) {
                canvas.drawBitmap(mStartNormal, left, getPaddingTop() ,null);
            } else {
                canvas.drawBitmap(mStartFocus, left, getPaddingTop() ,null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 处理手指的位置，根据当前位置计算出分数，然后刷新显示
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // getX: 相对应父布局的距离
                // getRawX: 相对于屏幕的距离
                performClick();
                float moveX = event.getX();
                int currentGrade = (int) (moveX / (getWidth() / 5) + 1);
                if (currentGrade != mCurGrade) {
                    mCurGrade = currentGrade;
                    invalidate();
                }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
