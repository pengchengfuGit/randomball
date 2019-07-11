package com.pcf.randomball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.pcf.randomball.bean.Ball;

import java.util.ArrayList;
import java.util.List;

public class RandomView extends View {

    private int height;
    private int width;
    private long maxBallNumber = -1;
    private int maxBall;
    private List<Ball> ballList = new ArrayList();

    private String TAG = "RandomLayout";
    private MyThread thread;

    public RandomView(Context context) {
        super(context);
    }

    public RandomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RandomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RandomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure ");
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        Log.d(TAG, "width " + width);
        Log.d(TAG, "height " + height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout ");
        ballList.add(new Ball(50, Color.BLUE, 50, width / 2, height / 2));
        if (thread == null) {
            thread = new MyThread();
        }
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Log.d(TAG, "onDraw ");
        for (int i = 0; i < ballList.size(); i++) {
            Ball ball = ballList.get(i);
            //实例化画笔对象
            Paint paint = new Paint();
            //给画笔设置颜色
            paint.setColor(ball.getColor());
            //设置画笔属性
            //paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
            paint.setStyle(Paint.Style.FILL);//画笔属性是空心圆
            paint.setStrokeWidth(1);//设置画笔粗细
            /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔*/
            canvas.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), paint);
        }
    }

    public long getMaxBallNumber() {
        return maxBallNumber;
    }

    public void setMaxBallNumber(long maxBallNumber) {
        this.maxBallNumber = maxBallNumber;
    }

    /**
     * 往布局添加小球
     */
    public void addBall(Ball ball) {
        if (ball != null) {
//            ballList.add(ball);
        }
    }

    /**
     * 从布局移除小球
     */
    public void removeBall(Ball ball) {
        if (ball != null)
            ballList.remove(ball);
    }

    public void randomBall() {
//        addView(new BallView(getContext(),50, Color.BLUE, 50));
    }

    public void actionBall() {
        for (int i = 0; i < ballList.size(); i++) {
            Ball ball = ballList.get(i);
            int x = ball.getX();
            int y = ball.getY();
            int degree = ball.getDegree();
            Log.d(TAG, "  degree   " + degree);
            Log.d(TAG, "  degree%   " + degree / 9);

            //按照0-360度划分四个象限
            if (degree >= 0 && degree < 90) {
                //按10等份去分配X轴与Y轴的位移值
                int xL = degree / 9;
                int yL = 10 - degree / 9;
                //第一象限 碰撞到布局边界
                if (ball.getX() + ball.getRadius() >= width) {
                    ball.setDegree(360 - degree);
                    ball.setX(x - ball.getRadius());
                } else {
                    if (x + ball.getRadius() + 1 + xL >= width) {
                        ball.setX(width - ball.getRadius());
                    } else {
                        ball.setX(x + 1 + xL);
                    }
                }
                //碰撞到布局边界
                if (ball.getY() - ball.getRadius() <= 0) {
                    ball.setDegree(180 - degree);
                    ball.setY(y + 1 + yL);
                } else {
                    if (y - ball.getRadius() - 1 - yL <= 0) {
                        ball.setY(ball.getRadius());
                    } else {
                        ball.setY(y - 1 - yL);
                    }
                }
            } else if (degree >= 90 && degree < 180) {
                //第二象限 碰撞到布局边界
                int xL = 10 - (degree  / 9-10);
                int yL = (degree  / 9-10);
                if (ball.getX() + ball.getRadius() >= width) {
                    ball.setDegree(180 - degree + 180);
                    ball.setX(x - ball.getRadius());
                } else {
                    if (x + ball.getRadius() + 1 + xL >= width) {
                        ball.setX(width - ball.getRadius());
                    } else {
                        ball.setX(x + 1 + xL);
                    }
                }
                //碰撞到布局边界
                if (ball.getY() + ball.getRadius() >= height) {
                    ball.setDegree(90 - degree + 90);
                    ball.setY(y - 1-yL);
                } else {
                    if (y + ball.getRadius() + 1 + yL >= height) {
                        ball.setY(height - ball.getRadius());
                    } else {
                        ball.setY(y + 1 + yL);
                    }
                }
            } else if (degree >= 180 && degree < 270) {
                //第三象限 碰撞到布局边界
                int xL = (degree  / 9-20);
                int yL = 10 - (degree / 9-20);
                if (ball.getX() - ball.getRadius() <= 0) {
                    ball.setDegree(360 - degree);
                    ball.setX(x + 1 + xL);
                } else {
                    if (x - ball.getRadius() - 1 - xL <= 0) {
                        ball.setX(ball.getRadius());
                    } else {
                        ball.setX(x - 1 - xL);
                    }
                }
//                if (ball.getX() - ball.getRadius() <= 0) {
//                    ball.setDegree(360 - degree);
//                    ball.setX(x + 1);
//                } else {
//                    ball.setX(x - 1);
//                }
                //碰撞到布局边界
                if (ball.getY() + ball.getRadius() >= height) {
                    ball.setDegree(270 + 270 - degree);
                    ball.setY(y - 1-yL);
                } else {
                    if (x + ball.getRadius() + 1 + yL >= height) {
                        ball.setY(height - ball.getRadius());
                    } else {
                        ball.setY(y + 1 + yL);
                    }
                }
            } else if (degree >= 270 && degree < 360) {
                //第四象限  碰撞到布局边界
                int xL = 10 - (degree  / 9-30);
                int yL = (degree  / 9-30);
                if (ball.getX() - ball.getRadius() <= 0) {
                    ball.setDegree(360 - degree);
                    ball.setX(x + 1 + xL);
                } else {
                    if (x - ball.getRadius() - 1 - xL <= 0) {
                        ball.setX(ball.getRadius());
                    } else {
                        ball.setX(x - 1 - xL);
                    }
                }
                //碰撞到布局边界
                if (ball.getY() - ball.getRadius() <= 0) {
                    ball.setDegree(360 - degree + 180);
                    ball.setY(y + 1 + yL);
                } else {
                    if (y - ball.getRadius() - 1 - yL <= 0) {
                        ball.setY(ball.getRadius());
                    } else {
                        ball.setY(y - 1 - yL);
                    }
                }
            }
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                actionBall();
                postInvalidate(); //通知更新界面，会重新调用onDraw()函数
                try {
                    sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
