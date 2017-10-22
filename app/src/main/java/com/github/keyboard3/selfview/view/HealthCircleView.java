package com.github.keyboard3.selfview.view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

import com.github.keyboard3.selfview.R;

/**
 * 健康圈自定义View
 *
 * @author keyboard3
 * @date 2017/9/17
 */

public class HealthCircleView extends View {
    float strokeWidth = 20;
    int backColor, foreColor;
    int openAngle, allNum, stepNum, order;
    String topTxt, bottomTxt;
    private float textVerticalMargin, radius, bigTxtSize, normalTxtSize;
    private Rect bigFontRect, normalRect;
    PointF centerPointer = new PointF();
    RectF circleRectangle = new RectF();
    private Paint backPaint, forePaint, normalTxtPaint, bigTxtPaint;
    private int orderLetterSpacing;

    public HealthCircleView(Context context) {
        super(context);
        init();
    }

    public HealthCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HealthCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HealthCircleView, defStyleAttr, 0);
        backColor = typedArray.getColor(R.styleable.HealthCircleView_backColor, Color.parseColor("#CAEEF3"));
        foreColor = typedArray.getColor(R.styleable.HealthCircleView_foreColor, Color.parseColor("#00A5E0"));
        allNum = typedArray.getInteger(R.styleable.HealthCircleView_allNum, 10000);
        stepNum = typedArray.getInteger(R.styleable.HealthCircleView_stepNum, 0);
        topTxt = typedArray.getString(R.styleable.HealthCircleView_topTxt);
        bottomTxt = typedArray.getString(R.styleable.HealthCircleView_bottomTxt);
        order = typedArray.getInteger(R.styleable.HealthCircleView_order, 1);
        openAngle = typedArray.getInteger(R.styleable.HealthCircleView_openAngle, 1);
        textVerticalMargin = typedArray.getDimension(R.styleable.HealthCircleView_textVerticalMargin, 60);
        strokeWidth = typedArray.getDimension(R.styleable.HealthCircleView_strokeWidth, 20);
        bigTxtSize = typedArray.getDimension(R.styleable.HealthCircleView_bigTxtSize, 72);
        normalTxtSize = typedArray.getDimension(R.styleable.HealthCircleView_normalTxtSize, 36);
        orderLetterSpacing = typedArray.getInteger(R.styleable.HealthCircleView_orderLetterSpacing, 3);

        init();
    }

    private void init() {
        //前后圆弧笔
        backPaint = initPaint(backColor);
        forePaint = initPaint(foreColor);

        //文本笔
        normalTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalTxtPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        normalTxtPaint.setTextSize(normalTxtSize);
        normalTxtPaint.setTextAlign(Paint.Align.CENTER);
        bigTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigTxtPaint.setColor(foreColor);
        bigTxtPaint.setTextSize(bigTxtSize);
        bigTxtPaint.setTextAlign(Paint.Align.CENTER);

        //获取笔画出的文本高度
        normalRect = new Rect();
        normalTxtPaint.getTextBounds(topTxt, 0, 1, normalRect);
        bigFontRect = new Rect();
        bigTxtPaint.getTextBounds(bottomTxt, 0, bottomTxt.length(), bigFontRect);
    }

    private Paint initPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int overNum = (int) (stepNum * 1.2);
                overNum = overNum > allNum ? allNum : overNum;
                Keyframe keyframe1 = Keyframe.ofInt(0, 0);
                Keyframe keyframe2 = Keyframe.ofInt(0.5f, overNum);
                Keyframe keyframe3 = Keyframe.ofInt(1f, stepNum);
                PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("stepNum", keyframe1, keyframe2, keyframe3);
                ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder);
                valueAnimator.setDuration(2000);
                valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
                valueAnimator.start();
            }
        });
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startAngle = 90 + openAngle / 2;
        int sweepAngle = 360 - openAngle;
        int drawAngle = (int) (sweepAngle * (1.0f * stepNum / allNum));
        canvas.drawArc(circleRectangle, startAngle, sweepAngle, false, backPaint);
        canvas.drawArc(circleRectangle, startAngle, drawAngle, false, forePaint);
        canvas.drawText(stepNum + "", centerPointer.x, centerPointer.y + bigFontRect.height() / 2, bigTxtPaint);
        canvas.drawText(topTxt, centerPointer.x, centerPointer.y - bigFontRect.height() / 2 - textVerticalMargin, normalTxtPaint);
        canvas.drawText(bottomTxt, centerPointer.x, centerPointer.y + bigFontRect.height() / 2 + textVerticalMargin + normalRect.height(), normalTxtPaint);


        //计算最底部排名
        float numY = (float) ((1 + Math.abs(Math.sin(openAngle / 2))) * radius);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            normalTxtPaint.setLetterSpacing(orderLetterSpacing);
        }
        canvas.drawText("第 名", centerPointer.x, numY, normalTxtPaint);

        normalTxtPaint.setColor(foreColor);
        canvas.drawText(order + "", centerPointer.x, numY, normalTxtPaint);
        normalTxtPaint.setColor(getResources().getColor(android.R.color.darker_gray));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            normalTxtPaint.setLetterSpacing(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int min = Math.min(width, height);
        circleRectangle.top = circleRectangle.left = 0 + strokeWidth;
        circleRectangle.bottom = circleRectangle.right = min - strokeWidth;
        centerPointer.x = (circleRectangle.left + circleRectangle.right) / 2;
        centerPointer.y = (circleRectangle.top + circleRectangle.bottom) / 2;
        radius = (circleRectangle.right - circleRectangle.left) / 2;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public void setForeColor(int foreColor) {
        this.foreColor = foreColor;
    }

    public void setOpenAngle(int openAngle) {
        this.openAngle = openAngle;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public void setTopTxt(String topTxt) {
        this.topTxt = topTxt;
    }

    public void setBottomTxt(String bottomTxt) {
        this.bottomTxt = bottomTxt;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
