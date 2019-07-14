package com.pcf.randomball.bean;

import java.util.HashMap;
import java.util.Random;

public class Ball {

    int radius;//半径大小  单位px
    int color;//色值
    int alpha;//透明度
    long lifeSpan = -1;//寿命
    int speed = -1;//速度
    int x = -1;
    int y = -1;
    int degree;//角度
    int axisX;
    int axisY;
    HashMap distanceMap = new HashMap<Ball, Integer>();

    public Ball() {
    }

    public Ball(int radius, int color, int alpha, int x, int y) {
        this.radius = radius;
        this.color = color;
        this.alpha = alpha;
        this.x = x;
        this.y = y;
        //生成一个在0-359范围内的随机数 为小球的方向
        this.degree = new Random().nextInt(360);
        this.speed = new Random().nextInt(10)+1;
        if (degree == 0) {
            axisX = 0;
            axisY = 1;
        } else if (degree == 90) {
            axisX = 1;
            axisY = 0;
        } else if (degree == 180) {
            axisX = 0;
            axisY = -1;
        } else if (degree == 270) {
            axisX = -1;
            axisY = 0;
        } else if (degree > 0 && degree < 90) {
            axisX = 1;
            axisY = 1;
        } else if (degree > 90 && degree < 180) {
            axisX = 1;
            axisY = -1;
        } else if (degree > 180 && degree < 270) {
            axisX = -1;
            axisY = -1;
        } else if (degree > 270 && degree < 360) {
            axisX = -1;
            axisY = 1;
        }
    }

    public Ball(int radius, int color, int alpha, long lifeSpan) {
        this.radius = radius;
        this.color = color;
        this.alpha = alpha;
        this.lifeSpan = lifeSpan;
    }

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

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public long getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(long lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public HashMap getDistanceMap() {
        return distanceMap;
    }

    public void setDistanceMap(HashMap distanceMap) {
        this.distanceMap = distanceMap;
    }

    public int getAxisX() {
        return axisX;
    }

    public void setAxisX(int axisX) {
        this.axisX = axisX;
    }

    public int getAxisY() {
        return axisY;
    }

    public void setAxisY(int axisY) {
        this.axisY = axisY;
    }
}
