package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Car {
    public enum State {LEFT, RIGHT, UP, DOWN, WAIT, PARKED};

    private int x;
    private int y;

    private Group carG=new Group();
    private Rectangle car;

    private State state;

    private ArrayList<Image> images=new ArrayList<>();

    Image img;
    ImageView imgv;

    public Car(int x,int y,int h,int w) {
       this.x=195;
       this.y=700;

       car=new Rectangle();
       car.setFill(Color.color(Math.random(), Math.random(), Math.random()));
       car.setHeight(80);
       car.setWidth(60);
       car.setX(this.x);
       car.setY(this.y);
       try{
           img=new Image(new FileInputStream("C:\\Users\\KhalidCH\\Desktop\\JAVA\\Parking tries\\Parking 1\\src\\images\\Cars\\yellow.png"));
           images.add(img);
           img=new Image(new FileInputStream("C:\\Users\\KhalidCH\\Desktop\\JAVA\\Parking tries\\Parking 1\\src\\images\\Cars\\red.png"));
           images.add(img);
           img=new Image(new FileInputStream("C:\\Users\\KhalidCH\\Desktop\\JAVA\\Parking tries\\Parking 1\\src\\images\\Cars\\blue.png"));
           images.add(img);
           img=new Image(new FileInputStream("C:\\Users\\KhalidCH\\Desktop\\JAVA\\Parking tries\\Parking 1\\src\\images\\Cars\\ciel.png"));
           images.add(img);
       }catch (Exception e){
           System.out.println("file not found");
       }

       Random r =new Random();
       int n=r.nextInt(3);
       img=images.get(n);

       imgv=new ImageView(img);
       imgv.setFitHeight(80);
       imgv.setFitWidth(60);
       imgv.setX(this.x);
       imgv.setY(this.y);
        this.carG.getChildren().add(imgv);
    }

    public int getY() {
        return y;
    }

    public ImageView getImgv() {
        return imgv;
    }

    public Image getImg() {
        return img;
    }

    public State getState() {
        return state;
    }

    public int getX() {
        return x;
    }

    public Group getCarG() {
        return carG;
    }

    public Rectangle getCar() {
        return car;
    }

    public void setImgv(ImageView imgv) {
        this.imgv = imgv;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCar(Rectangle car) {
        this.car = car;
    }

    public void setCarG(Group carG) {
        this.carG = carG;
    }
}
