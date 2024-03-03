package com.caicai.myapplication.customView9_tagLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {

    private static final String TAG = "TagLayout";
    private final List<List<View>> mChildViews = new ArrayList<>();
    private ArrayList<View> mChilds = new ArrayList<>();

    private BaseAdapter mAdapter;

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // onMeasure会走两次，每次都要清下数据
    // https://zhuanlan.zhihu.com/p/360405914
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//        }
        // 关键点, 清数据
        mChildViews.clear();
        mChilds.clear();
        //计算 childView 宽高, 替代上面手动测量子view的操作
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom();
        int curWidth = getPaddingLeft();
        int childCount = getChildCount();
        int maxHeight = height;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int leftMargin = layoutParams.leftMargin;
            int rightMargin = layoutParams.rightMargin;
            int topMargin = layoutParams.topMargin;
            int bottomMargin = layoutParams.bottomMargin;
            curWidth = curWidth + childWidth + getPaddingRight() + leftMargin + rightMargin;
            maxHeight = Math.max(childHeight + topMargin + bottomMargin, maxHeight);
            if (curWidth > width) {
                // 换行
                height += maxHeight;
                maxHeight = 0;
                curWidth = getPaddingLeft();
                mChildViews.add(new ArrayList<>(mChilds));
                mChilds = new ArrayList<>();
            } else {
                mChilds.add(child);
            }
        }
        height += maxHeight;
        if (mChilds.size() > 0) {
            mChildViews.add(new ArrayList<>(mChilds));
            mChilds = new ArrayList<>();
        }
        setMeasuredDimension(width, height);
    }

    // 参考线性布局 相对布局，都有自己的LayoutParams
    //  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    // 用于自定义自己的属性，这里只用了margin这一个属性
//    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p)  {
        if (p instanceof MarginLayoutParams) {
            return new MarginLayoutParams(((MarginLayoutParams) p));
        }
        return new MarginLayoutParams(p);
    }

    /**
     * java.lang.ClassCastException: android.view.ViewGroup$LayoutParams
     * cannot be cast to android.view.ViewGroup$MarginLayoutParams
     * 原因：https://www.jianshu.com/p/aaa5956be917
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top, right, bottom, maxHeight, curSize, topMargin = 0, leftMargin, bottomMargin;
        top = getPaddingTop();

        for (List<View> mChildView : mChildViews) {
            curSize = mChildView.size();
            left = getPaddingLeft();
            maxHeight = 0;
            for (int i = 0; i < curSize; i++) {
                View childView = mChildView.get(i);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                leftMargin = layoutParams.leftMargin;
                topMargin = layoutParams.topMargin;
                int width = childView.getMeasuredWidth();
                int height = childView.getMeasuredHeight();
                left += leftMargin;
                if (i == 0) {
                    top += topMargin;
                }
                right = left + width + layoutParams.rightMargin;
                bottom = top + height + layoutParams.bottomMargin;
                maxHeight = Math.max(maxHeight, height);
                childView.layout(left, top, right, bottom);
                left = right;
            }
            top += maxHeight + topMargin;
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            return;
        }

        removeAllViews();
        mAdapter = adapter;
        for (int i = 0; i < adapter.getCount(); i++) {
            View childView = adapter.getView(i, this);
            addView(childView);
        }
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}









