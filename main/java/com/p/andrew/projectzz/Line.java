package com.p.andrew.projectzz;

public class Line {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private float angle;

    public Line(int x1,int y1,int x2,int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        angle = (float) Math.atan((y1-y2)/(x1-x2));
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public boolean intercept(Line line){
        int demon = ((y2-y1)*(line.getX2()-line.getX1()))-
                ((x2-x1)*(line.getY2()-line.getY1()));
        return demon != 0;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


}
