package com.pcf.randomball.bean;

import java.util.Random;

public class Ball {

    int radius;//半径大小  单位px
    int color;//色值
    int alpha;//透明度
    long lifeSpan =-1;//寿命
    long speed =-1;//速度
    int x;
    int y;
    int degree;

    public Ball() {}

    public Ball(int radius, int color, int alpha,int x,int y) {
        this.radius = radius;
        this.color = color;
        this.alpha = alpha;
        this.x = x;
        this.y = y;
        this.degree = new Random().nextInt(360);
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

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
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
}
