package com.github.keyboard3.selfview.View;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
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

/**
 * Created by keyboard3 on 2017/9/17.
 */

public class CircleView extends View {
    int strokeWidth = 20;
    int backgroundColor = Color.parseColor("#CAEEF3");
    int foreColor = Color.parseColor("#00A5E0");
    PointF centerPointer = new PointF();
    RectF circleRectangle = new RectF();
    int openAngle = 60;
    int allNum;

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
        invalidate();
    }

    int stepNum;
    private Paint backPaint;
    private Paint forePaint;
    private Paint normalTxtPaint;
    private Paint bigTxtPaint;
    String topTxt, centerTxt, bottomTxt, numTxt;
    private Rect bigFontRect;
    private Rect normalRect;
    private int margin;
    private float radius;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        allNum = 10000;
        stepNum = 0;
        backPaint = initPaint(backgroundColor);
        forePaint = initPaint(foreColor);
        normalTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalTxtPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        normalTxtPaint.setTextSize(28);
        normalTxtPaint.setTextAlign(Paint.Align.CENTER);
        bigTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigTxtPaint.setColor(foreColor);
        bigTxtPaint.setTextSize(72);
        bigTxtPaint.setTextAlign(Paint.Align.CENTER);
        topTxt = "截止22:48已走";
        centerTxt = "8792";
        bottomTxt = "好友平均2961步";
        numTxt = "第1名";
        margin = 60;
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
                stepNum = 8920;
                Keyframe keyframe1 = Keyframe.ofInt(0, 0);
                Keyframe keyframe2 = Keyframe.ofInt(0.5f, allNum);
                Keyframe keyframe3 = Keyframe.ofInt(1f, stepNum);
                PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("stepNum", keyframe1, keyframe2, keyframe3);
                ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder);
                valueAnimator.setDuration(2000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        centerTxt = valueAnimator.getAnimatedValue() + "";
                    }
                });
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
        int avalibleAngle = (int) (sweepAngle * (1.0f * stepNum / allNum));
        canvas.drawArc(circleRectangle, startAngle, sweepAngle, false, backPaint);
        canvas.drawArc(circleRectangle, startAngle, avalibleAngle, false, forePaint);
        canvas.drawText(centerTxt, centerPointer.x, centerPointer.y + bigFontRect.height() / 2, bigTxtPaint);
        canvas.drawText(topTxt, centerPointer.x, centerPointer.y - bigFontRect.height() / 2 - margin, normalTxtPaint);
        canvas.drawText(bottomTxt, centerPointer.x, centerPointer.y + bigFontRect.height() / 2 + margin + normalRect.height(), normalTxtPaint);


        float numY = (float) ((1 + Math.abs(Math.sin(openAngle / 2))) * radius);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            normalTxtPaint.setLetterSpacing(3);
        }
        canvas.drawText("第名", centerPointer.x, numY, normalTxtPaint);
        normalTxtPaint.setColor(foreColor);
        canvas.drawText("1", centerPointer.x, numY, normalTxtPaint);
        normalTxtPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            normalTxtPaint.setLetterSpacing(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int min = Math.min(width, height);
        circleRectangle.top = circleRectangle.left = 0 + strokeWidth;
        circleRectangle.bottom = circleRectangle.right = min - strokeWidth;
        centerPointer.x = (circleRectangle.left + circleRectangle.right) / 2;
        centerPointer.y = (circleRectangle.top + circleRectangle.bottom) / 2;
        radius = (circleRectangle.right - circleRectangle.left) / 2;
    }
}
