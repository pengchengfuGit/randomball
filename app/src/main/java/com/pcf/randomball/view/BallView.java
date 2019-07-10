package com.pcf.randomball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 小球View
 * */
public class BallView extends View {

    int radius;//半径大小  单位px
    int color;//色值
    int bgAlpha;//透明度
    long lifeSpan =-1;//寿命
    long speed =-1;//速度
    long degree =-1;//方向值大小为 0-360

    public BallView(Context context, int radius, int color, int alpha) {
        super(context);
        this.radius = radius;
        this.color = color;
        this.bgAlpha = alpha;
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //实例化画笔对象
        Paint paint = new Paint();
        //给画笔设置颜色
        paint.setColor(Color.RED);
        //设置画笔属性
        //paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        paint.setStyle(Paint.Style.FILL);//画笔属性是空心圆
        paint.setStrokeWidth(1);//设置画笔粗细
        /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
                */
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }

    /*public BallView() {}

          public BallView(int radius, int color, int alpha) {
              this.radius = radius;
              this.color = color;
              this.alpha = alpha;
          }

          public BallView(int radius, int color, int alpha, long lifeSpan) {
              this.radius = radius;
              this.color = color;
              this.alpha = alpha;
              this.lifeSpan = lifeSpan;
          }
      */
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBgAlpha() {
        return bgAlpha;
    }

    public void setBgAlpha(int bgAlpha) {
        this.bgAlpha = bgAlpha;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getDegree() {
        return degree;
    }

    public void setDegree(long degree) {
        this.degree = degree;
    }

    public long getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(long lifeSpan) {
        this.lifeSpan = lifeSpan;
    }
}
