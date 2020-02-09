package sample;
import javafx.animation.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import sample.Spot.Row;


public class Controller {
    ArrayList<Car> cars;
    ArrayList<Spot> left;
    ArrayList<Spot> right;
    ArrayList<Spot> ct;
    ArrayList<Spot> cd;
    ArrayList<Spot> top;
    ArrayList<Row> rows;
    ArrayList<Car> waiting;
    HandleAssService handle;

    @FXML
    private AnchorPane parking;

    public void initialize() throws FileNotFoundException {
        rows = new ArrayList<>();
        cars = new ArrayList<>();
        waiting=new ArrayList<>();
        handle=new HandleAssService();
        handle.restart();
        rows.add(Row.CENTERD);
        rows.add(Row.CENTERT);
        rows.add(Row.TOP);
        rows.add(Row.LEFT);
        rows.add(Row.RIGHT);
        spots();
        Car c = new Car(1, 1, 1, 1);
        parking.getChildren().add(c.getCarG());
        up(c);
    }

    // create spots and add them to parking
    public void spots() {
        left = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Spot s = new Spot(Row.LEFT, i);
            left.add(s);
        }
        for (Spot s : left) {
            parking.getChildren().add(s.spot);
            int i = parking.getChildren().indexOf(s.spot);
            parking.getChildren().get(i).setVisible(false);
        }
        right = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Spot s = new Spot(Row.RIGHT, i);
            right.add(s);
        }
        for (Spot s : right) {
            parking.getChildren().add(s.spot);
            int i = parking.getChildren().indexOf(s.spot);
            parking.getChildren().get(i).setVisible(false);
        }
        top = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Spot s = new Spot(Row.TOP, i);
            top.add(s);
        }
        for (Spot s : top) {
            parking.getChildren().add(s.spot);
            int i = parking.getChildren().indexOf(s.spot);
            parking.getChildren().get(i).setVisible(false);
        }
        cd = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Spot s = new Spot(Row.CENTERD, i);
            cd.add(s);
        }
        for (Spot s : cd) {
            parking.getChildren().add(s.spot);
            int i = parking.getChildren().indexOf(s.spot);
            parking.getChildren().get(i).setVisible(false);
        }
        ct = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Spot s = new Spot(Row.CENTERT, i);
            ct.add(s);
        }
        for (Spot s : ct) {
            parking.getChildren().add(s.spot);
            int i = parking.getChildren().indexOf(s.spot);
            parking.getChildren().get(i).setVisible(false);
        }
    }

    public ArrayList<Spot> getPlace() {
        ArrayList<Spot> rs = new ArrayList<>();
        ArrayList<Integer> rands = new ArrayList<>();
        Random r = new Random();
        int rand = r.nextInt(5);

        while (true) {
            int flag = 0;
            Row row = rows.get(rand);
           //Row row = Row.CENTERT;
            if (row == Row.CENTERD) {
                for (Spot s : cd) {
                    if (s.getEmpty()) {
                        int i = parking.getChildren().indexOf(s.spot);
                        parking.getChildren().get(i).setVisible(true);
                        s.setEmpty(false);
                        rs.add(s);
                        flag = 1;
                        break;
                    }
                }
            }
            if (row == Row.CENTERT) {
                for (Spot s : ct) {
                    if (s.getEmpty()) {
                        int i = parking.getChildren().indexOf(s.spot);
                        parking.getChildren().get(i).setVisible(true);
                        s.setEmpty(false);
                        flag = 1;
                        rs.add(s);
                        break;
                    }
                }
            }
            if (row == Row.TOP) {
                for (Spot s : top) {
                    if (s.getEmpty()) {
                        int i = parking.getChildren().indexOf(s.spot);
                        parking.getChildren().get(i).setVisible(true);
                        s.setEmpty(false);
                        rs.add(s);
                        flag = 1;
                        break;
                    }
                }
            }
            if (row == Row.LEFT) {
                for (Spot s : left) {
                    if (s.getEmpty()) {
                        int i = parking.getChildren().indexOf(s.spot);
                        parking.getChildren().get(i).setVisible(true);
                        s.setEmpty(false);
                        rs.add(s);
                        flag = 1;
                        break;
                    }
                }
            }
            if (row == Row.RIGHT) {
                for (Spot s : right) {
                    if (s.getEmpty()) {
                        int i = parking.getChildren().indexOf(s.spot);
                        parking.getChildren().get(i).setVisible(true);
                        s.setEmpty(false);
                        flag = 1;
                        rs.add(s);
                        break;
                    }
                }
            }
            if (flag == 0) {
                if (!rands.contains(rand)) {
                    rands.add(rand);
                }
                while (true) {
                    rand = r.nextInt(5);
                    if (!rands.contains(rand)) {
                        break;
                    } else {
                        rand = r.nextInt(5);
                    }
                    if (rands.size() == rows.size()) {
                        break;
                    }
                }
            }
            if (flag == 1) {
                break;
            }
            if (rands.size() == rows.size()) {
                break;
            }
        }
        return rs;
    }

    public void up(Car c) {
        TranslateTransition up = new TranslateTransition(Duration.seconds(1));
        up.setNode(c.getCarG());
        up.setToY(-100);
        up.setOnFinished(e -> {
            c.setY(c.getY() - 100);
            System.out.println("Voiture viens d'entrer");
            park(c);
        });
        up.play();
    }

    public int park(Car c) {
        ArrayList<Spot> rs = getPlace();
        Spot s;
        if (!rs.isEmpty())
            s = rs.get(0);
        else {
            if(!waiting.contains(c))
                waiting.add(c);
            System.out.println("Full parking");
            System.out.println(waiting);
            return 0;
        }
        System.out.println("Place X=" + s.getX() + " Y=" + s.getY());

        if (s.getRow() == Row.RIGHT)
            parkRight(s, c);
        if (s.getRow() == Row.LEFT)
            parkLeft(s, c);
        if (s.getRow() == Row.TOP)
            parkTop(s, c);
        if (s.getRow() == Row.CENTERD)
            parkCenterD(s, c);
        if (s.getRow() == Row.CENTERT)
            parkCenterT(s, c);

        return 1;
    }

    public void parkLeft(Spot s, Car c) {
        TranslateTransition tr = new TranslateTransition(Duration.seconds(1), c.getCarG());
        tr.setByY(s.getY() - c.getY() - 10);
        tr.setOnFinished(e -> {
            c.setY(c.getY() + (s.getY() - c.getY()));
        });
        RotateTransition rt = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt.setByAngle(-90);
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t.setByX(s.getX() - c.getX() + 10);
        SequentialTransition sq = new SequentialTransition(tr, rt, t);
        sq.setOnFinished(e -> {
            exitLeft(s, c, generateDouble());
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
            Car cr = new Car(1, 1, 1, 1);
            parking.getChildren().add(cr.getCarG());
            up(cr);
        });
        sq.play();
    }

    public void exitLeft(Spot s, Car c, double n) {
        TranslateTransition t5 = new TranslateTransition(Duration.seconds(n * 10), c.getCarG());
        t5.setByY(0);
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), c.getCarG());
        RotateTransition rt = new RotateTransition(Duration.seconds(1), c.getCarG());
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        RotateTransition rt2 = new RotateTransition(Duration.seconds(1), c.getCarG());
        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        RotateTransition rt3 = new RotateTransition(Duration.seconds(1), c.getCarG());
        TranslateTransition t4 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t.setToY(right.get(0).getY() - c.getY());
        t4.setByX(right.get(4).getX() - c.getX() - 95);
        t.setByY(s.getY() - c.getY() - 10);
        t2.setByX(-s.getX() + c.getX() - 10);
        t3.setByY(right.get(4).getY() - c.getY());
        t3.setOnFinished(e -> {
            c.setY(c.getY() + (right.get(4).getY() - c.getY()));
        });
        rt.setByAngle(90);
        rt.setOnFinished(e -> {
            s.setEmpty(true);
        });
        rt2.setByAngle(90);
        rt3.setByAngle(90);
        SequentialTransition sq = new SequentialTransition(t5, t2, rt, t3, rt2, t4, rt3, t);
        sq.setOnFinished(e -> {
           spawn();
        });
        sq.play();
    }

    public void parkRight(Spot s, Car c) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(2), c.getCarG());
        t.setByY(right.get(4).getY() - c.getY() - 10);
        t.setOnFinished(e -> {
            c.setY(c.getY() + (right.get(4).getY() - c.getY()));

        });
        TranslateTransition t4 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());

        RotateTransition rt = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt.setByAngle(90);

        RotateTransition rt2 = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt2.setOnFinished(e -> {
            t3.play();
        });
        rt2.setByAngle(90);

        RotateTransition rt3 = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt3.setOnFinished(e -> {
            t4.play();
        });
        rt3.setByAngle(-90);

        TranslateTransition t2 = new TranslateTransition(Duration.seconds(2), c.getCarG());
        t2.setByX(right.get(4).getX() - c.getX() - 95);
        t2.setOnFinished(e -> {
            c.setX(c.getX() + (right.get(4).getX() - c.getX() - 95));

        });

        t4.setToX(s.getX() - c.getX() + 10);

        t3.setToY(s.getY() - c.getY() - 110);
        t3.setOnFinished(e -> {
            c.setY(c.getY() + (s.getY() - c.getY()));
            rt3.play();
        });

        SequentialTransition sq = new SequentialTransition(t, rt, t2);
        sq.setOnFinished(e -> {
            if (c.getY() != s.getY()) {
                rt2.play();
                exitRight(s,c,generateDouble());
                Car c2 = new Car(1, 1, 1, 1);
                parking.getChildren().add(c2.getCarG());
                parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
                up(c2);
            } else {
                t4.play();
                Car c2 = new Car(1, 1, 1, 1);
                parking.getChildren().add(c2.getCarG());
                up(c2);
            }
        });
        sq.play();
    }

    public void exitRight(Spot s, Car c, double n){
        TranslateTransition t5 = new TranslateTransition(Duration.seconds(n * 10), c.getCarG());
        t5.setByY(0);
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t2.setByX(-s.getX() + c.getX() - 10);
        RotateTransition rt=new RotateTransition(Duration.seconds(1),c.getCarG());
        rt.setByAngle(90);
        TranslateTransition t3=new TranslateTransition(Duration.seconds(1), c.getCarG());
        t3.setToY(100);
        rt.setOnFinished(e->{
            s.setEmpty(true);
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
            spawn();
        });
        SequentialTransition sq=new SequentialTransition(t5,t2,rt,t3);
        sq.play();
    }

    public void parkTop(Spot s, Car c) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(2), c.getCarG());
        t.setByY(right.get(4).getY() - c.getY() - 10);
        t.setOnFinished(e -> {
            c.setY(c.getY() + (right.get(4).getY() - c.getY()));

        });

        TranslateTransition t2 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t2.setToX(s.getX() - c.getX());
        t2.setOnFinished(e -> {
            c.setX(c.getX() + (s.getX() - c.getX()));
        });

        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t3.setToY(s.getY() - c.getY() - 100);
        t3.setOnFinished(e -> {
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
        });

        RotateTransition rt = new RotateTransition(Duration.seconds(0.5), c.getCarG());
        rt.setByAngle(90);

        RotateTransition rt2 = new RotateTransition(Duration.seconds(0.5), c.getCarG());
        rt2.setByAngle(-90);
        SequentialTransition sq = new SequentialTransition(t, rt, t2, rt2, t3);
        sq.play();
        sq.setOnFinished(e -> {
            exitTop(s,c,generateDouble());
            Car c2 = new Car(1, 1, 1, 1);
            parking.getChildren().add(c2.getCarG());
            up(c2);
        });


    }

    public void exitTop(Spot s, Car c,double n){
        TranslateTransition t5 = new TranslateTransition(Duration.seconds(n * 10), c.getCarG());
        t5.setByY(0);

        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t3.setByY(-s.getY()+c.getY());

        TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t1.setByX(right.get(4).getX() - c.getX() - 95);

        TranslateTransition t4=new TranslateTransition(Duration.seconds(1), c.getCarG());
        t4.setByY(1000);

        RotateTransition rt=new RotateTransition(Duration.seconds(0.3),c.getCarG());
        rt.setByAngle(90);

        RotateTransition rt2=new RotateTransition(Duration.seconds(0.3),c.getCarG());
        rt2.setByAngle(90);
        rt2.setOnFinished(e->{
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
            s.setEmpty(true);
            spawn();
        });
        SequentialTransition sq=new SequentialTransition(t5,t3,rt,t1,rt2,t4);
        sq.play();
    }

    public void parkCenterD(Spot s, Car c) {
        TranslateTransition tr = new TranslateTransition(Duration.seconds(1), c.getCarG());
        tr.setByY(left.get(0).getY() - c.getY() - 10);

        RotateTransition rt = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt.setByAngle(90);

        TranslateTransition t2 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t2.setToX(s.getX() - c.getX());
        t2.setOnFinished(e -> {
            c.setX(c.getX() + (s.getX() - c.getX()));

        });

        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t3.setToY(s.getY() - c.getY() - 100);
        t3.setOnFinished(e -> {
            c.setY(c.getY() + (s.getY() - c.getY()));
        });

        RotateTransition rt2 = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt2.setByAngle(-90);

        SequentialTransition sq = new SequentialTransition(tr, rt, t2, rt2, t3);
        sq.play();
        sq.setOnFinished(e -> {
            Car c2 = new Car(1, 1, 1, 1);
            parking.getChildren().add(c2.getCarG());
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
            up(c2);
            exitCenterD(s,c,generateDouble());
        });


    }

    public void exitCenterD(Spot s, Car c,double n){
        TranslateTransition t5 = new TranslateTransition(Duration.seconds(n * 10), c.getCarG());
        t5.setByY(0);

        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t3.setByY(110);

        TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t1.setByX(right.get(4).getX() - c.getX() - 95);

        TranslateTransition t4=new TranslateTransition(Duration.seconds(1), c.getCarG());
        t4.setByY(1000);

        RotateTransition rt=new RotateTransition(Duration.seconds(0.3),c.getCarG());
        rt.setByAngle(90);

        RotateTransition rt2=new RotateTransition(Duration.seconds(0.3),c.getCarG());
        rt2.setByAngle(90);
        rt2.setOnFinished(e->{
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
            s.setEmpty(true);
            spawn();
        });
        SequentialTransition sq=new SequentialTransition(t5,t3,rt,t1,rt2,t4);
        sq.play();
    }

    public void parkCenterT(Spot s, Car c) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(2), c.getCarG());
        t.setByY(right.get(4).getY() - c.getY() - 10);
        t.setOnFinished(e -> {
            c.setY(c.getY() + (right.get(4).getY() - c.getY()));

        });

        TranslateTransition t2 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t2.setToX(s.getX() - c.getX());
        t2.setOnFinished(e -> {
            c.setX(c.getX() + (s.getX() - c.getX()));
        });

        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t3.setToY(s.getY() - c.getY() - 100);
        t3.setOnFinished(e -> {
            c.setY(c.getY() + (s.getY() - c.getY()));
        });

        RotateTransition rt = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt.setByAngle(90);

        RotateTransition rt2 = new RotateTransition(Duration.seconds(1), c.getCarG());
        rt2.setByAngle(90);

        SequentialTransition sq = new SequentialTransition(t, rt, t2, rt2, t3);
        sq.play();
        sq.setOnFinished(e -> {
            Car c2 = new Car(1, 1, 1, 1);
            parking.getChildren().add(c2.getCarG());
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
            up(c2);
            exitCenterT(s,c,generateDouble());
        });
    }

    public void exitCenterT(Spot s, Car c,double n){
        TranslateTransition t5 = new TranslateTransition(Duration.seconds(n * 10), c.getCarG());
        t5.setByY(0);

        TranslateTransition t3 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t3.setByY(-110);

        TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), c.getCarG());
        t1.setByX(right.get(4).getX() - c.getX() - 95);

        TranslateTransition t4=new TranslateTransition(Duration.seconds(1), c.getCarG());
        t4.setByY(1000);

        RotateTransition rt=new RotateTransition(Duration.seconds(0.3),c.getCarG());
        rt.setByAngle(-90);

        RotateTransition rt2=new RotateTransition(Duration.seconds(0.3),c.getCarG());
        rt2.setByAngle(90);
        rt2.setOnFinished(e->{
            parking.getChildren().get(parking.getChildren().indexOf(s.getSpot())).setVisible(false);
            s.setEmpty(true);
            spawn();
        });
        SequentialTransition sq=new SequentialTransition(t5,t3,rt,t1,rt2,t4);
        sq.play();
    }

    public double generateDouble(){
        Random r=new Random();
        double rn= r.nextDouble()+1;
        System.out.println("La voiture va rester "+(rn*10)+" seconds");
        return rn;
    }
    public void spawn(){
        Random r =new Random();
        if((r.nextInt(7)+1)==5) {
            Car c2 = new Car(1, 1, 1, 1);
            parking.getChildren().add(c2.getCarG());
            up(c2);
        }
    }
    private class HandleAssService extends Service {
        private HandleAssService() {
            setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent e) {
                    e.getSource().getValue();
                }
            });
        }

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    int i = 0;
                    while (true) {
                        Car c;
                        int n;
                        if(!waiting.isEmpty()){
                            System.out.println("testing again");
                            c=waiting.get(0);
                            n=park(c);
                            if(n==1)
                                waiting.clear();
                        }
                        Thread.sleep(3000);
                    }
                }
            };
        }
    }
}
