package third;

import first.Point;
import org.jfree.data.xy.XYSeries;

import java.util.LinkedList;
import java.util.List;

import static first.FirstTask.showGraphic;

public class ThirdTask {
    final static double STEP = 0.1;
    final static double K2 = 0.000002;
    final static double M2 = 100000;
    final static double G3 = 9.8;
    final static double K3 = 0.002;
    final static double M3 = 10;
    final static double N1 = 2000;
    final static double N2 = 2000;
    final static double N3 = 3000;
    final static double K4 = 6.22 * Math.pow(10, -19);

    public static void main(String[] args) {
        LinkedList<FuncTX> funcTXLinkedList = new LinkedList<>();
        funcTXLinkedList.add((t, x) -> 0.1 - 3 * x / (1000 + t));
        funcTXLinkedList.add((t, x) -> K2 * (M2 - x) * x);//y=x
        funcTXLinkedList.add((t, x) -> -G3 * (K3 * x * Math.abs(x)) / M3);//v=x
        funcTXLinkedList.add((t, x) -> K4 * Math.pow((N1 - t / 2), 2) * Math.pow((N2 - t / 2), 2) * Math.pow(N3 - (3 * t) / 4, 3)); //t=x

        //отрезок по t или чему-то еще
        LinkedList<Point> segment = new LinkedList<>();
        segment.add(new Point(50, 0));//t=y
        segment.add(new Point(1000, 0));
        segment.add(new Point(8, 0));
        segment.add(new Point(0, 0));

        List<List<first.Point>> poiEiler = euler(segment, funcTXLinkedList);
        System.out.println("номер 1");
        rectangleMethod(segment.get(0), funcTXLinkedList.get(0));
        System.out.println("номер 2");
        trapezoidMethod(segment.get(1), funcTXLinkedList.get(1));
        System.out.println("номер 3");
        rectangleMethod(segment.get(2), funcTXLinkedList.get(2));
        trapezoidMethod(segment.get(2), funcTXLinkedList.get(2));
        List<List<first.Point>> poiRunge = runge(segment, funcTXLinkedList);
        int count = 1;

        for (List<Point> p : poiEiler) {
            XYSeries graf = new XYSeries("");
            for (Point point : p) {
                graf.add(point.getY(), point.getX());
            }
            showGraphic(graf, "Эйлер. График № " + count, "t", "x");
            count++;
        }


        count = 1;
        for (List<Point> p : poiRunge) {
            XYSeries graf = new XYSeries("");
            for (Point point : p) {
                graf.add(point.getY(), point.getX());
            }
            showGraphic(graf, "Рунге-Кутты 4 порядка. График № " + count, "t", "x");
            count++;
        }

    }

    public static List<List<first.Point>> runge(LinkedList<Point> segment, LinkedList<FuncTX> funcTXLinkedList) {
        List<List<first.Point>> poiXY = new LinkedList<>();
        for (int j = 0; j < funcTXLinkedList.size(); j++) {
            double buffX = segment.get(j).getX();
            double buffT = segment.get(j).getY();
            List<first.Point> bufXY = new LinkedList<>();
            first.Point p = new first.Point(buffX, buffT);
            bufXY.add(0, p);
            for (double i = 0; i < 1000; i += STEP) { //xt xy
                buffT += STEP;
                double k1 = STEP * funcTXLinkedList.get(j).calculate(buffT, buffX);
                double k2 = STEP * funcTXLinkedList.get(j).calculate(buffT + STEP / 2, buffX + k1 / 2);
                double k3 = STEP * funcTXLinkedList.get(j).calculate(buffT + STEP / 2, buffX + k2 / 2);
                double k4 = STEP * funcTXLinkedList.get(j).calculate(buffT + STEP, buffX + k3);
                double dt = (k1 + 2 * k2 + 2 * k3 + k4) / 6;
                buffX += dt;
                p = new first.Point(buffX, buffT);
                bufXY.add((int) (i + 1), p);
            }
            poiXY.add(j, bufXY);
        }
        return poiXY;
    }

    public static List<List<first.Point>> euler(LinkedList<Point> segment, LinkedList<FuncTX> funcTXLinkedList) {
        List<List<first.Point>> poiXY = new LinkedList<>();
        for (int j = 0; j < funcTXLinkedList.size(); j++) {
            double buffX = segment.get(j).getX();
            double buffT = segment.get(j).getY();
            List<first.Point> bufXY = new LinkedList<>();
            first.Point p = new first.Point(buffX, buffT);
            bufXY.add(0, p);
            for (double i = 0; i < 1000; i += STEP) {
                buffX += STEP * funcTXLinkedList.get(j).calculate(buffT, buffX);
                buffT += STEP;
                p = new first.Point(buffX, buffT);
                bufXY.add((int) (i + 1), p);
            }
            poiXY.add(j, bufXY);
        }
        return poiXY;
    }

    public static void rectangleMethod(Point segment, FuncTX funcTXLinkedList) {
        double area = 0;
        System.out.println("Метод прямоугольника");
        double buffX = segment.getX();
        double buffT = segment.getY();
        for (int i = 0; i < 100; i++) {
            area += STEP * funcTXLinkedList.calculate(i * STEP + buffT, i * STEP + buffX);
        }
        System.out.println(area);
    }

    public static void trapezoidMethod(Point segment, FuncTX funcTXLinkedList) {
        double area = 0;
        System.out.println("Метод трапеции");
        double buffX = segment.getX();
        double buffT = segment.getY();
        for (int i = 0; i < 100; i++) {
            area += 0.5 * STEP * (funcTXLinkedList.calculate(i * STEP + buffT,
                    i * STEP + buffX) + funcTXLinkedList.calculate((i + 1) * STEP + buffT, (i + 1) * STEP + buffX));
        }
        System.out.println(area);
    }


    interface FuncTX {
        double calculate(double t, double x);
    }
}
