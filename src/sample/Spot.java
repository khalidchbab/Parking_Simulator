package sample;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Spot {
    public enum Row{TOP,CENTERT,CENTERD,LEFT,RIGHT}
    private int x;
    private int y;
    private int num;
    private Row row;
    private Boolean isEmpty;
    private int w;
    private int h;
    Rectangle spot;

    public Spot(Row r,int n){
        if(r==Row.CENTERD){
            this.x=291+79*n;
            this.y=328;
            this.w=30;
            this.h=40;
        }
        if(r==Row.CENTERT){
            x=291+79*n;
            y=230;
            this.w=30;
            this.h=40;
        }
        if(r==Row.TOP){
            x=291+79*n;
            y=15;
            this.w=30;
            this.h=40;
        }
        if(r==Row.LEFT){
            x=85;
            y=450-79*n;
            this.w=40;
            this.h=30;
        }
        if(r==Row.RIGHT){
            x=1035;
            y=450-79*n;
            this.w=40;
            this.h=30;
        }

        this.num=n;
        this.row=r;
        this.isEmpty=true;

        spot=new Rectangle();
        spot.setY(this.y);
        spot.setX(this.x);
        if(n%2==0)
            spot.setFill(Color.RED);
        else
            spot.setFill(Color.RED);
        spot.setHeight(this.h);
        spot.setWidth(this.w);

    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getNum() {
        return num;
    }
    public Boolean getEmpty() {
        return isEmpty;
    }
    public int getH() {
        return h;
    }
    public int getW() {
        return w;
    }
    public Rectangle getSpot() {
        return spot;
    }
    public Row getRow() {
        return row;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }
    public void setH(int h) {
        this.h = h;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void setRow(Row row) {
        this.row = row;
    }
    public void setSpot(Rectangle spot) {
        this.spot = spot;
    }
    public void setW(int w) {
        this.w = w;
    }
}
