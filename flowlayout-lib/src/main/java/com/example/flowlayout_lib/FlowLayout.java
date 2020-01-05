package com.example.flowlayout_lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

    private int widthSpace;
    private int heightSpace;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        widthSpace = (int) typedArray.getDimension(R.styleable.FlowLayout_widthspace, 0);
        heightSpace = (int) typedArray.getDimension(R.styleable.FlowLayout_heightspace, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int top = getPaddingTop();
        int left = getPaddingLeft();

        //当前行高度
        int lineHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //超出宽度，换行
            if (left + childWidth + getPaddingRight() > sizeWidth) {
                //超出高度
                if (top + lineHeight + heightSpace + childHeight + getPaddingBottom() > sizeHeight) {
                    break;
                }
                top += lineHeight + heightSpace;
                left = getPaddingLeft() + childWidth + widthSpace;
                lineHeight = childHeight;
            } else {
                //横向排列
                lineHeight = Math.max(lineHeight, childHeight);
                left += childWidth + widthSpace;
            }
        }
        setMeasuredDimension(sizeWidth, top + lineHeight + getPaddingBottom());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int sizeWidth = getMeasuredWidth();
        int sizeHeight = getMeasuredHeight();

        int top = getPaddingTop();
        int left = getPaddingLeft();

        //当前行高度
        int lineHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //超出宽度，换行
            if (left + childWidth + getPaddingRight() > sizeWidth) {
                //超出高度
                if (top + lineHeight + heightSpace + childHeight + getPaddingBottom() > sizeHeight) {
                    break;
                }
                top += lineHeight + heightSpace;
                left = getPaddingLeft();
                lineHeight = childHeight;
                child.layout(left, top, left + childWidth, top + childHeight);
                left += childWidth + widthSpace;
            } else {
                //横向排列
                child.layout(left, top, left + childWidth, top + childHeight);
                lineHeight = Math.max(lineHeight, childHeight);
                left += childWidth + widthSpace;
            }
        }
    }
}
