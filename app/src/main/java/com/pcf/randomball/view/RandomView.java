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
import java.util.HashMap;
import java.util.List;

public class RandomView extends View {

    private int height;
    private int width;
    private long maxBallNumber = -1;
    private int maxBall;
    private List<Ball> ballList = new ArrayList();
    private HashMap distanceMap = new HashMap<Ball, Ball>();
    private float minDistances[][] = {};

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
        ballList.add(new Ball(50, Color.BLUE, 600, width / 2, height / 2));
        ballList.add(new Ball(50, Color.GREEN, 600, 100, 100));
        // 记录某个小球，距离它最近的小球的距离，这个小球的下标
        for (int i = 0; i < ballList.size(); i++) {
            distanceMap.put(ballList.get(i), new Ball());
        }
        //避免重复创建线程
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
            paint.setAlpha(ball.getAlpha());
            //设置画笔属性
            paint.setStyle(Paint.Style.FILL);//画笔属性是实心
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
            Ball ball1 = ballList.get(i);
            int x1 = ball1.getX();
            int y1 = ball1.getY();
            int degree = ball1.getDegree();

            //按照0-360度划分四个象限
            if (degree >= 0 && degree < 90) {
                //按10等份去分配X轴与Y轴的位移值
                int xL = degree / 9;
                int yL = 10 - degree / 9;
                //第一象限 碰撞到布局边界
                if (ball1.getX() + ball1.getRadius() >= width) {
                    ball1.setDegree(360 - degree);
                    ball1.setX(x1 - ball1.getRadius());
                } else {
                    if (x1 + ball1.getRadius() + 1 + xL >= width) {
                        ball1.setX(width - ball1.getRadius());
                    } else {
                        ball1.setX(x1 + 1 + xL);
                    }
                }
                //碰撞到布局边界
                if (ball1.getY() - ball1.getRadius() <= 0) {
                    ball1.setDegree(180 - degree);
                    ball1.setY(y1 + 1 + yL);
                } else {
                    if (y1 - ball1.getRadius() - 1 - yL <= 0) {
                        ball1.setY(ball1.getRadius());
                    } else {
                        ball1.setY(y1 - 1 - yL);
                    }
                }
            } else if (degree >= 90 && degree < 180) {
                //第二象限 碰撞到布局边界
                int xL = 10 - (degree / 9 - 10);
                int yL = (degree / 9 - 10);
                if (ball1.getX() + ball1.getRadius() >= width) {
                    ball1.setDegree(180 - degree + 180);
                    ball1.setX(x1 - ball1.getRadius());
                } else {
                    if (x1 + ball1.getRadius() + 1 + xL >= width) {
                        ball1.setX(width - ball1.getRadius());
                    } else {
                        ball1.setX(x1 + 1 + xL);
                    }
                }
                //碰撞到布局边界
                if (ball1.getY() + ball1.getRadius() >= height) {
                    ball1.setDegree(90 - degree + 90);
                    ball1.setY(y1 - 1 - yL);
                } else {
                    if (y1 + ball1.getRadius() + 1 + yL >= height) {
                        ball1.setY(height - ball1.getRadius());
                    } else {
                        ball1.setY(y1 + 1 + yL);
                    }
                }
            } else if (degree >= 180 && degree < 270) {
                //第三象限 碰撞到布局边界
                int xL = (degree / 9 - 20);
                int yL = 10 - (degree / 9 - 20);
                if (ball1.getX() - ball1.getRadius() <= 0) {
                    ball1.setDegree(360 - degree);
                    ball1.setX(x1 + 1 + xL);
                } else {
                    if (x1 - ball1.getRadius() - 1 - xL <= 0) {
                        ball1.setX(ball1.getRadius());
                    } else {
                        ball1.setX(x1 - 1 - xL);
                    }
                }
                //碰撞到布局边界
                if (ball1.getY() + ball1.getRadius() >= height) {
                    ball1.setDegree(270 + 270 - degree);
                    ball1.setY(y1 - 1 - yL);
                } else {
                    if (x1 + ball1.getRadius() + 1 + yL >= height) {
                        ball1.setY(height - ball1.getRadius());
                    } else {
                        ball1.setY(y1 + 1 + yL);
                    }
                }
            } else if (degree >= 270 && degree < 360) {
                //第四象限  碰撞到布局边界
                int xL = 10 - (degree / 9 - 30);
                int yL = (degree / 9 - 30);
                if (ball1.getX() - ball1.getRadius() <= 0) {
                    ball1.setDegree(360 - degree);
                    ball1.setX(x1 + 1 + xL);
                } else {
                    if (x1 - ball1.getRadius() - 1 - xL <= 0) {
                        ball1.setX(ball1.getRadius());
                    } else {
                        ball1.setX(x1 - 1 - xL);
                    }
                }
                //碰撞到布局边界
                if (ball1.getY() - ball1.getRadius() <= 0) {
                    ball1.setDegree(360 - degree + 180);
                    ball1.setY(y1 + 1 + yL);
                } else {
                    if (y1 - ball1.getRadius() - 1 - yL <= 0) {
                        ball1.setY(ball1.getRadius());
                    } else {
                        ball1.setY(y1 - 1 - yL);
                    }
                }
            }
            //拿当前小球的坐标,半径与其它小球的坐标,半径  判断是否发生了碰撞
            for (int z = 0; z < ballList.size(); z++) {
                if (z != i) {
                    Ball ball2 = ballList.get(z);

                    int x2 = ball2.getX();
                    int y2 = ball2.getY();
                    int distance1 = Math.abs(x1 - x2) * Math.abs(x1 - x2)
                            + Math.abs(y1 - y2) * Math.abs(y1 - y2);

                    Ball b = (Ball) distanceMap.get(ball1);

                    if (b.getX() == -1 && b.getY() == -1) {
                        distanceMap.put(ball1, ball2);
                    } else {
                        int distance2 = Math.abs(x1 - b.getX()) * Math.abs(x1 - b.getX())
                                + Math.abs(y1 - b.getY()) * Math.abs(y1 - b.getY());
                        if (distance1 < distance2) {
                            distanceMap.put(ball1, ball2);
                        }
//                      minDistances[i][0] = distance;
//                      minDistances[i][1] = z;
                    }
                }
            }
        }
        //判断是否有发生碰撞的小球
        for (int i = 0; i < ballList.size(); i++) {
            Ball ball1 = ballList.get(i);
            Ball ball2 = (Ball) distanceMap.get(ball1);
            int distance2 = Math.abs(ball1.getX() - ball2.getX()) * Math.abs(ball1.getX() - ball2.getX())
                    + Math.abs(ball1.getY() - ball2.getY()) * Math.abs(ball1.getY() - ball2.getY());
            //最小距离小于阈值，发生碰撞 交换角度
            if (distance2 <= (ball1.getRadius() + ball2.getRadius())
                    * (ball1.getRadius() + ball2.getRadius())) {
                int temp = ball1.getDegree();
                ball1.setDegree(ball2.getDegree());
                ball2.setDegree(temp);
                //发生碰撞 重新初始化数据
                distanceMap.put(ball2, new Ball());
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
                    sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
