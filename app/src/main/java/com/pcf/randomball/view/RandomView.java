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
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 随机小球View
 */
public class RandomView extends View {

    private int height;
    private int width;
    private long maxBallNumber = -1;
    private List<Ball> ballList = new ArrayList();
    private boolean runing = true;
    /**
     * 存放与小球距离最近的一个小球
     */
    private HashMap distanceMap = new HashMap<Ball, Ball>();
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
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout ");
        ballList.add(new Ball(50, Color.BLUE, 700, width / 2, height / 2));
        ballList.add(new Ball(60, Color.BLACK, 600, width / 4 * 3, height / 4 * 3));
//        ballList.add(new Ball(70, Color.RED, 800, 100, 100));
        // 记录某个小球，距离它最近的小球的距离，这个小球的下标
        for (int i = 0; i < ballList.size(); i++) {
            distanceMap.put(ballList.get(i), new Ball());
        }
//        避免重复创建线程
        if (thread == null) {
            thread = new MyThread();
        }
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < ballList.size(); i++) {
            Ball ball = ballList.get(i);
            //实例化画笔对象
            Paint paint = new Paint();
            //给画笔设置颜色
            paint.setColor(ball.getColor());
            paint.setAlpha(ball.getAlpha());
            //设置画笔属性是实心
            paint.setStyle(Paint.Style.FILL);
            //设置画笔粗细
            paint.setStrokeWidth(1);
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
        if (ball != null && ballList.size() < maxBallNumber) {
            ballList.add(ball);
        }
    }

    /**
     * 从布局移除小球
     */
    public void removeBall(Ball ball) {
        if (ball != null) {
            ballList.remove(ball);
        }
    }

    public void actionBall() {
        for (int i = 0; i < ballList.size(); i++) {
            Ball ball1 = ballList.get(i);
            int x1 = ball1.getX();
            int y1 = ball1.getY();
            int degree = ball1.getDegree();
            int xL = 0;
            int yL = 0;
            if (90 < degree && degree < 270) {
                //按照0-360度划分四个象限
                yL = ceil(Math.sin(degree / Math.PI * 2) * ball1.getSpeed());
                xL = ceil(Math.cos(degree / Math.PI * 2) * ball1.getSpeed());
            } else {
                //按照0-360度划分四个象限
                xL = ceil(Math.sin(degree / Math.PI * 2) * ball1.getSpeed());
                yL = ceil(Math.cos(degree / Math.PI * 2) * ball1.getSpeed());
            }
            //按照0-360度划分四个象限
            if (degree >= 0 && degree < 90) {
                //第一象限 碰撞到布局右边界
                if (ball1.getX() + ball1.getRadius() >= width) {
                    ball1.setDegree(360 - degree);
                    ball1.setX(x1 - xL);
                } else {
                    if (x1 + ball1.getRadius() + xL >= width) {
                        ball1.setDegree(360 - degree);
                        ball1.setX(width - ball1.getRadius());
                    } else {
                        ball1.setX(x1 + xL);
                    }
                }
                //碰撞到布局上边界
                if (ball1.getY() - ball1.getRadius() <= 0) {
                    ball1.setDegree(180 - degree);
                    ball1.setY(y1 + yL);
                } else {
                    if (y1 - ball1.getRadius() - yL <= 0) {
                        ball1.setDegree(180 - degree);
                        ball1.setY(ball1.getRadius());
                    } else {
                        ball1.setY(y1 - yL);
                    }
                }
            } else if (degree >= 90 && degree < 180) {
                //第二象限 碰撞到布局边界
                if (ball1.getX() + ball1.getRadius() >= width) {
                    ball1.setDegree(180 - degree + 180);
                    ball1.setX(x1 - xL);
                } else {
                    if (x1 + ball1.getRadius() + xL >= width) {
                        ball1.setDegree(180 - degree + 180);
                        ball1.setX(width - ball1.getRadius());
                    } else {
                        ball1.setX(x1 + xL);
                    }
                }
                //碰撞到布局边界
                if (ball1.getY() + ball1.getRadius() >= height) {
                    ball1.setDegree(90 - degree + 90);
                    ball1.setY(y1 - yL);
                } else {
                    if (y1 + ball1.getRadius() + yL >= height) {
                        ball1.setDegree(90 - degree + 90);
                        ball1.setY(height - ball1.getRadius());
                    } else {
                        ball1.setY(y1 + yL);
                    }
                }
            } else if (degree >= 180 && degree < 270) {
                //第三象限 碰撞到布局边界
                if (ball1.getX() - ball1.getRadius() <= 0) {
                    ball1.setDegree(360 - degree);
                    ball1.setX(x1 + xL);
                } else {
                    if (x1 - ball1.getRadius() - xL <= 0) {
                        ball1.setDegree(360 - degree);
                        ball1.setX(ball1.getRadius());
                    } else {
                        ball1.setX(x1 - xL);
                    }
                }
                //碰撞到布局边界
                if (ball1.getY() + ball1.getRadius() >= height) {
                    ball1.setDegree(270 + 270 - degree);
                    ball1.setY(y1 - yL);
                } else {
                    if (x1 + ball1.getRadius() + yL >= height) {
                        ball1.setDegree(270 + 270 - degree);
                        ball1.setY(height - ball1.getRadius());
                    } else {
                        ball1.setY(y1 + yL);
                    }
                }
            } else if (degree >= 270 && degree < 360) {
                //第四象限  碰撞到布局边界
                if (ball1.getX() - ball1.getRadius() <= 0) {
                    ball1.setDegree(360 - degree);
                    ball1.setX(x1 + xL);
                } else {
                    if (x1 - ball1.getRadius() - xL <= 0) {
                        ball1.setDegree(360 - degree);
                        ball1.setX(ball1.getRadius());
                    } else {
                        ball1.setX(x1 - xL);
                    }
                }
                //碰撞到布局边界
                if (ball1.getY() - ball1.getRadius() <= 0) {
                    ball1.setDegree(360 - degree + 180);
                    ball1.setY(y1 + yL);
                } else {
                    if (y1 - ball1.getRadius() - yL <= 0) {
                        ball1.setDegree(360 - degree + 180);
                        ball1.setY(ball1.getRadius());
                    } else {
                        ball1.setY(y1 - yL);
                    }
                }
            }
            countDistance(i, ball1, x1, y1);
            postInvalidate();
        }
        checkoutBall();
    }

    private void countDistance(int i, Ball ball1, int x1, int y1) {
        //拿当前小球的坐标,半径与其它小球的坐标,半径  找出距离当前小球最近的球
        Ball b = (Ball) distanceMap.get(ball1);

        for (int z = 0; z < ballList.size(); z++) {
            if (z != i) {
                Ball ball2 = ballList.get(z);
                /**
                 * 当小球坐标x,y均为-1时，说明数据刚初始化
                 * 还没保存距离最近的小球，直接保存
                 */
                if (b.getX() == -1 && b.getY() == -1) {
                    distanceMap.put(ball1, ball2);
                } else {
                    int x2 = ball2.getX();
                    int y2 = ball2.getY();
                    int distance1 = Math.abs(x1 - x2) * Math.abs(x1 - x2)
                            + Math.abs(y1 - y2) * Math.abs(y1 - y2);

                    int distance2 = Math.abs(x1 - b.getX()) * Math.abs(x1 - b.getX())
                            + Math.abs(y1 - b.getY()) * Math.abs(y1 - b.getY());
                    if (distance1 < distance2) {
                        distanceMap.put(ball1, ball2);
                    }
                }
            }
        }
    }

    /**
     * 判断是否有发生碰撞的小球
     */
    private void checkoutBall() {
        for (int i = 0; i < ballList.size(); i++) {
            Ball ball1 = ballList.get(i);
            Ball ball2 = (Ball) distanceMap.get(ball1);

            if (ball2.getX() == -1 && ball2.getY() == -1) {
                continue;
            }
            int distance2 = Math.abs(ball1.getX() - ball2.getX()) * Math.abs(ball1.getX() - ball2.getX())
                    + Math.abs(ball1.getY() - ball2.getY()) * Math.abs(ball1.getY() - ball2.getY());
            //最小距离小于阈值，发生碰撞 计算碰撞角度
            if (distance2 <= (ball1.getRadius() + ball2.getRadius())
                    * (ball1.getRadius() + ball2.getRadius())) {
                int x1 = ball1.getX();
                int y1 = ball1.getY();
                int x2 = ball2.getX();
                int y2 = ball2.getY();
                //两球的角度差为180  即两球正碰 交换运动方向
                if (Math.abs(ball1.getDegree() - ball2.getDegree()) == 180) {
                    int temp = ball1.getDegree();
                    ball1.setDegree(ball2.getDegree());
                    ball2.setDegree(temp);
                } else if (y1 == y2) {
                    //正弦值不存在   角度为90
                    if (x1 > x2) {
                        ball1.setDegree(90);
                        ball2.setDegree(270);
                    } else {
                        ball1.setDegree(270);
                        ball2.setDegree(90);
                    }
                } else {
                    //斜碰  两球圆心连接线的角度做反向运动
                    float tan = (x1 - x2) / (y1 - y2);
                    //正弦对应的角度在[-90,90]   在这取绝对值让角度值为正值
                    int angle = (int) Math.abs(Math.atan(tan));
                    if (x1 > x2 && y1 > y2) {
                        //正弦值为正数    角度为[0,90] ball1在右下   ball2在左上
                        ball1.setDegree(angle + 90);
                        ball2.setDegree(angle + 270);
                    } else if (x1 > x2 && y1 < y2) {
                        //正弦值为负数   角度为[-90,0] ball1在右上   ball2在左下
                        ball1.setDegree(90 - angle);
                        ball2.setDegree(270 - angle);
                    } else if (x1 < x2 && y1 > y2) {
                        //正弦值为负数   角度为[-90,0] ball1在右下   ball2在左上
                        ball1.setDegree(270 - angle);
                        ball2.setDegree(90 - angle);
                    } else if (x1 < x2 && y1 < y2) {
                        //正弦值为正数   角度为[0,90] ball1在左上   ball2在右下
                        ball1.setDegree(angle + 270);
                        ball2.setDegree(angle + 90);
                    } else if (x1 == x1) {
                        //正弦值为0  angle为0
                        if (y1 > y2) {
                            ball1.setDegree(180);
                            ball2.setDegree(angle);
                        } else {
                            ball1.setDegree(angle);
                            ball2.setDegree(180);
                        }
                    }
                }
                //发生碰撞 重新初始化数据
                distanceMap.put(ball2, new Ball());
                distanceMap.put(ball1, new Ball());
            }
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (runing) {
                actionBall();
                try {
//                    sleep(1000 / 36);
                    sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        runing = false;
    }

    /**
     * 对于三角函数的取值进行向上与向下取整
     * 避免出现零的情况
     * 正数向上取整
     * 负数向下取整
     */
    public int ceil(double num) {
        if (num > 0) {
            return (int) Math.ceil(num);
        } else {
            return -(int) Math.floor(num);
        }
    }
}
