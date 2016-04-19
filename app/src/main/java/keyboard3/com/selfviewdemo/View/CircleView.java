package keyboard3.com.selfviewdemo.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import keyboard3.com.selfviewdemo.R;

/**
 * ============================================================
 * <p/>
 * 版     权 ： keyboard3 所有
 * <p/>
 * 作     者  :  甘春雨
 * <p/>
 * 版     本 ： 1.0
 * <p/>
 * 创 建日期 ： 2016/4/16
 * <p/>
 * 描     述 ：
 * <p/>
 * <p/>
 * 修 订 历史：
 * <p/>
 * ============================================================
 */
public class CircleView extends View {
    processChangeLinstener linstener;
    private RectF mColorWheelRectangle = new RectF();

    private int theme= Color.parseColor("#2EC3FD");
    private Paint mDefaultWheelPaint;
    private Paint mColorWheelPaint;

    private float circleStrokeWidth;//笔的宽度

    //矩形的范围-圆弧的外切矩形
    private float mColorWheelLeft;
    private float mColorWheelTop;
    private float mColorWheelBottom;
    private float mColorWheelRight;

    private int defalutColor;//默认背景环颜色
    private int foreColor;//前景色
    int startAngle ;//开始的角度
    private float mSweepAnglePer;//角度进度
    private float mSweepAngle=0;//角度
    private float mDefaultSweepAngle=0;//默认角度
    private BarAnimation anim;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CircleView, defStyleAttr, 0);
        defalutColor=typedArray.getColor(R.styleable.CircleView_defalutColor,theme);
        foreColor=typedArray.getColor(R.styleable.CircleView_foreColor,theme);
        startAngle=typedArray.getInt(R.styleable.CircleView_startAngle,0);
        mSweepAngle=typedArray.getInt(R.styleable.CircleView_SweepAngle,0);
        mDefaultSweepAngle=typedArray.getInt(R.styleable.CircleView_DefaultSweepAngle,0);
        circleStrokeWidth=typedArray.getDimension(R.styleable.CircleView_circleStrokeWidth,0);
        init();
    }

    public void init(){
        mColorWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorWheelPaint.setColor(foreColor);
        mColorWheelPaint.setStyle(Paint.Style.STROKE);
        mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
        mColorWheelPaint.setDither(true);
        mColorWheelPaint.setStrokeJoin(Paint.Join.ROUND);
        mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);
        mColorWheelPaint.setPathEffect(new CornerPathEffect(10));

        mDefaultWheelPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultWheelPaint.setColor(defalutColor);
        mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
        mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);
        mDefaultWheelPaint.setDither(true);
        mDefaultWheelPaint.setStrokeJoin(Paint.Join.ROUND);
        mDefaultWheelPaint.setStrokeCap(Paint.Cap.ROUND);

        anim = new BarAnimation();
        anim.setDuration(2000);
    }


    public void startCustomAnimation(){
        this.startAnimation(anim);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mColorWheelRectangle,startAngle,mDefaultSweepAngle,false,mDefaultWheelPaint);//默认轮
        canvas.drawArc(mColorWheelRectangle, startAngle,mSweepAnglePer,false,mColorWheelPaint);//颜色轮
    }

    /**
     * 计算圆弧的角度上的点的坐标x
     * @param x 圆弧中心点坐标x
     * @param angle 偏移过的角度
     * @param radius 半径
     * @return
     */
    private float calcXLocationInWheel(float x, float angle, float radius) {
        angle=(360+angle)%360;
        float result=(float) (x+Math.cos(Math.toRadians(angle))*radius);
        if(Math.cos(Math.toRadians(angle))>1) result=x;

        return result;
    }
    /**
     * 计算圆弧角度上的点的坐标y
     * @param y 圆弧中心点的坐标y
     * @param angle 偏移过的角度
     * @param radius 半径
     * @return
     */
    private float calcYLocationInWheel(float y,float angle, float radius) {
        angle=(360+angle)%360;
        float result=(float) (y+Math.sin(Math.toRadians(angle))*radius);
        if(Math.sin(Math.toRadians(angle))>1) result=y;
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);;
        int height=getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        int min=Math.min(width,height);
        mColorWheelTop=mColorWheelLeft=circleStrokeWidth;
        mColorWheelBottom=mColorWheelRight=min-circleStrokeWidth;
        //对轮子矩形的大小确定
        mColorWheelRectangle.set(mColorWheelLeft,mColorWheelTop,mColorWheelRight,mColorWheelBottom);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private float dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    public class BarAnimation extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            linstener.onProcessChange(interpolatedTime);
            if(interpolatedTime<1.0f){
                mSweepAnglePer=mSweepAngle*interpolatedTime;
            }else{
                mSweepAnglePer=mSweepAngle;
            }
            postInvalidate();
        }
    }
    public void setProcessChangeLinstener(processChangeLinstener linstener){
        this.linstener=linstener;
    }
    public interface processChangeLinstener{
       public void onProcessChange(float interpolatedTime);
    }
}
