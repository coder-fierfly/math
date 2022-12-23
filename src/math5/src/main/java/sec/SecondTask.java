package sec;

import org.jfree.data.xy.XYSeries;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static first.FirstTask.*;

// double[] knot = {3, 4, 8, 10, 16, 32, 64, 256};
public class SecondTask {
    static final double KNOT = 16;

    public static void main(String[] args) {
        LinkedList<Func> func = new LinkedList<>();
        func.add(x -> Math.pow(x, 3) - 6.5 * Math.pow(x, 2) + 11 * x - 4);
        func.add(x -> 3 * Math.cos(Math.PI * x / 8));
        func.add(x -> Math.exp(-x / 4) * Math.sin(x / 3));
        func.add(x -> 8 * x * Math.exp(-Math.pow(x, 2) / 12));


        LinkedList<Point> segment = new LinkedList<>();
        segment.add(new Point(2, 4));
        segment.add(new Point(1 / 2, 3));
        segment.add(new Point(4, 10));
        segment.add(new Point(0, 12));

        List<List<first.Point>> poiXY = new LinkedList<>();
        for (int j = 0; j < segment.size(); j++) {
            double buffX = segment.get(j).getX();
            List<first.Point> bufXY = new LinkedList<>();
            first.Point p = new first.Point(buffX, calcY(func.get(j), buffX));
            bufXY.add(0, p);
            for (int i = 0; i < KNOT - 1; i++) {
                buffX += (segment.get(j).getY() - segment.get(j).getX()) / (KNOT - 1);
                p = new first.Point(buffX, calcY(func.get(j), buffX));
                bufXY.add((i + 1), p);
            }
            poiXY.add(j, bufXY);
        }
        List<List<first.Point>> perfectPoiXY = new LinkedList<>();
        for (int j = 0; j < segment.size(); j++) {
            double buffX = segment.get(j).getX();
            List<first.Point> buffXY = new LinkedList<>();
            first.Point p = new first.Point(buffX, calcY(func.get(j), buffX));
            buffXY.add(0, p);
            for (int i = 0; i < 32; i++) {
                buffX += (segment.get(j).getY() - segment.get(j).getX()) / (32);
                p = new first.Point(buffX, calcY(func.get(j), buffX));
                buffXY.add((i + 1), p);
            }
            perfectPoiXY.add(j, buffXY);
        }
        for (int i = 0; i < poiXY.size(); i++) {
            System.out.println("\nНомер: " + (i + 1) + ". Узлов " + KNOT);
            for (int j = 0; j < poiXY.get(i).size(); j++) {
                System.out.printf("x= %.5f", poiXY.get(i).get(j).getX());
                System.out.printf("    y= %.5f", poiXY.get(i).get(j).getY());
                System.out.println();
            }
        }
        int count = 1;
        for (List<first.Point> p :
                poiXY) {
            XYSeries lagrangePolynomialSeries = new XYSeries("");
            for (double x = p.get(0).getX(); x < p.get(p.size() - 1).getX(); x += 0.01) {
                lagrangePolynomialSeries.add(x, lagrangePolynomial(p, p.size(), x));
            }
            showGraphic(lagrangePolynomialSeries, "Полином Лагранжа. Задание " + count, "x", "y");
            table(p, count);
            count++;
        }
        count = 1;
        for (List<first.Point> p :
                perfectPoiXY) {
            XYSeries lagrangePolynomialSeries = new XYSeries("");
            for (double x = p.get(0).getX(); x < p.get(p.size() - 1).getX(); x += 0.01) {
                lagrangePolynomialSeries.add(x, lagrangePolynomial(p, p.size(), x));
            }
            showGraphic(lagrangePolynomialSeries, "График № " + count, "x", "y");
            count++;
        }
    }

    public static void table(List<first.Point> p, int num) {
        Object[][] poi = new Object[p.size()][2];
        for (int i = 0; i < poi.length; i++) {
            poi[i][0] = p.get(i).getX();
            poi[i][1] = p.get(i).getY();
        }
        String[] columnNames = {"x", "y"};
        JTable table = new JTable(poi, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        frame.setTitle("График № " + num);
        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static double calcY(Func func, double x) {
        return func.calculate(x);
    }

    interface Func {
        double calculate(double x);
    }
}
