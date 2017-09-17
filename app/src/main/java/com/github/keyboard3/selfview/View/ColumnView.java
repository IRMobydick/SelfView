package com.github.keyboard3.selfview.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.github.keyboard3.selfview.R;

/**
 * Desc:
 * Author: ganchunyu
 * Date: 2016-04-19 13:29
 */
public class ColumnView extends View {
    private int theme= Color.parseColor("#2EC3FD");
    int mRadius;
    int mHeight;
    int foreColor;
    Paint mPaint;
    public ColumnView(Context context) {
        super(context);
        init();
    }

    public ColumnView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.ColumnView,defStyleAttr,0);
        foreColor=typedArray.getColor(R.styleable.ColumnView_column_foreColor,theme);
        init();
    }
    private void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setDither(true);
        mPaint.setColor(foreColor);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRadius=getMeasuredWidth()/2;
        mHeight=getMeasuredHeight();
        mPaint.setStrokeWidth(mRadius*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
        canvas.drawCircle(mRadius,mHeight-mRadius,mRadius,mPaint);
        canvas.drawLine(mRadius,mRadius,mRadius,mHeight-mRadius,mPaint);
    }
}
