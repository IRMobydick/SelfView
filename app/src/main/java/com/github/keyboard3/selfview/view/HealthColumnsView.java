package com.github.keyboard3.selfview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.keyboard3.selfview.R;

/**
 * Created by keyboard3 on 2017/9/17.
 */

public class HealthColumnsView extends View {

    private Paint columnPaint;
    private Paint textPaint;
    private float strokeWith, textSize, topSpace;
    private int textColor, columnColor;
    private String averageTitle, recentTitle;
    private int[] columns = {3, 0, 5, 6, 5, 2, 0};
    private int startDay = 11;
    private Paint linePaint;

    public HealthColumnsView(Context context) {
        super(context);
        init();
    }

    public HealthColumnsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HealthColumnsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HealthColumnsView, defStyleAttr, 0);
        columnColor = typedArray.getColor(R.styleable.HealthColumnsView_columnColor, Color.parseColor("#00A5E0"));
        textColor = typedArray.getColor(R.styleable.HealthColumnsView_textColor, context.getResources().getColor(android.R.color.darker_gray));
        int resourceId = typedArray.getResourceId(R.styleable.HealthColumnsView_columnsValues, 0);
        if (resourceId != 0) {
            columns = getResources().getIntArray(resourceId);
        }
        averageTitle = typedArray.getString(R.styleable.HealthColumnsView_averageTitle);
        recentTitle = typedArray.getString(R.styleable.HealthColumnsView_recentTitle);//最近七天
        startDay = typedArray.getInteger(R.styleable.HealthColumnsView_startDay, 0);
        topSpace = typedArray.getDimension(R.styleable.HealthColumnsView_topSpace, 40);
        strokeWith = typedArray.getDimension(R.styleable.HealthColumnsView_strokeWith, 40);
        textSize = typedArray.getDimension(R.styleable.HealthColumnsView_textSize, 40);
        init();
    }

    private void init() {
        columnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        columnPaint.setColor(columnColor);
        columnPaint.setStrokeWidth(strokeWith);
        columnPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(0.8f);
        linePaint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画顶部文字
        Rect textReact = new Rect();
        textPaint.getTextBounds(recentTitle, 0, recentTitle.length(), textReact);
        canvas.drawText(recentTitle, 0, textReact.height(), textPaint);
        textPaint.getTextBounds(averageTitle, 0, averageTitle.length(), textReact);
        canvas.drawText(averageTitle, getWidth() - textReact.width(), textReact.height(), textPaint);

        //画虚线
        int lineHeight = (int) (topSpace + textReact.height());
        canvas.save();
        canvas.translate(0, lineHeight);
        Path path = new Path();
        path.lineTo(getRight(), 0);
        canvas.drawPath(path, linePaint);
        canvas.restore();

        //画柱子
        //底部 80 7/10*100 0点  space=(width-7*strokeWith)/8
        textPaint.setTextAlign(Paint.Align.CENTER);
        int space = (int) ((getWidth() - 7 * strokeWith) / 8);
        int bottom = lineHeight + 50;
        for (int i = 0; i < columns.length; i++) {
            int top = (int) (bottom - (1.0f * columns[i] / 10) * 70);
            int left = (int) (space * (i + 1) + strokeWith * i);
            if (columns[i] == 0) {
                columnPaint.setColor(getContext().getResources().getColor(android.R.color.darker_gray));
                canvas.drawPoint(left, bottom, columnPaint);
            } else {
                columnPaint.setColor(columnColor);
                canvas.drawLine(left, bottom, left, top, columnPaint);
            }
            canvas.drawText(startDay + i + "日", left, bottom + textReact.height() + topSpace / 2, textPaint);
        }
    }
}
