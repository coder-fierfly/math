package first;

public class Point {
    double x;
    double x1;
    double x2;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(double x1, double x2, double y) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
    }

    public Point() {

    }

    public double getX1() {
        return x1;
    }
    public double getX2() {
        return x2;
    }
    public double getX() {
        return x;
    }

    // public void setX(double x) {
    //     this.x = x;
    //}

    public double getY() {
        return y;
    }
//
//    public void setY(double y) {
//        this.y = y;
//    }
}
