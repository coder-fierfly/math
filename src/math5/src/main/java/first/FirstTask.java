package first;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstTask {

    public static void main(String[] args) {
        List<List<Point>> tasks = ourPoints();
        int count = 1;
        for (List<Point> task :
                tasks) {
            XYSeries lagrangePolynomialSeries = new XYSeries("");
            XYSeries newtonPolynomialSeries = new XYSeries("");
            for (double x = task.get(0).getX(); x < task.get(task.size() - 1).getX(); x += 0.1) {
                lagrangePolynomialSeries.add(x, lagrangePolynomial(task, task.size(), x));
                newtonPolynomialSeries.add(x, newtonPolynomial(task, x, task.size()));
            }
            showGraphic(lagrangePolynomialSeries, "Полином Лагранжа. Задание " + count, "x","y");
            showGraphic(newtonPolynomialSeries, "Полином Ньютона. Задание " + count, "x" , "y");
            count++;
        }
    }

    public static void showGraphic(XYSeries series, String title, String str1, String str2) {
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(title, str1, str2,
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(500, 400);
        frame.show();
    }

    public static double lagrangePolynomial(List<Point> points, int size, double requiredX) {
        double result = 0;
        for (int i = 0; i < size; i++) {
            result += points.get(i).getY() * lagrangeMultiplier(points, i, requiredX);
        }
        return result;
    }

    public static double newtonPolynomial(List<Point> points, double requiredX, int size) {
        double result = 0;
        double buff;
        result += points.get(0).getY();
        for (int i = 0; i < size; i++) {
            buff = 1;
            for (int j = 0; j < i + 1; j++) {
                buff *= (requiredX - points.get(j).getX());
            }
            if (buff != 0) {
                buff *= newtonFunction(points.subList(0, i == size - 1 ? size : i + 2));
            }
            result += buff;
        }
        return result;
    }

    public static double lagrangeMultiplier(List<Point> points, int i, double requiredX) {
        double result = 1.0;
        double divisible = 1.0;
        double divider = 1.0;
        for (int j = 0; j < points.size(); j++) {
            if (j != i) {
                //   divisible *= (требуемый x - x(j));
                //   divider *= (x(i) - x(j));
                divisible *= (requiredX - points.get(j).getX());
                divider *= (points.get(i).getX() - points.get(j).getX());
            }
        }
        result *= divisible / divider;
        return result;
    }

        public static double newtonFunction(List<Point> points) {
            double result = 0;
            if (points.size() == 2) {
                result += (points.get(1).getY() - points.get(0).getY()) / (points.get(1).getX() - points.get(0).getX());
            } else {
                double firstF = newtonFunction(new ArrayList<>(points.subList(1, points.size())));
                double secondF = newtonFunction(new ArrayList<>(points.subList(0, points.size() - 1)));
                double deltaX = points.get(points.size() - 1).getX() - points.get(0).getX();
                result += (firstF - secondF) / deltaX;
            }
            return result;
        }

    private static List<List<Point>> ourPoints() {
        List<List<Point>> point = new ArrayList<>();
        point.add(Arrays.asList(
                new Point(-1.0, 0.86603),
                new Point(0.0, 1.0),
                new Point(1.0, 0.86603),
                new Point(2.0, 0.50),
                new Point(3.0, 0.0),
                new Point(4.0, -0.50)
        ));
        point.add(Arrays.asList(
                new Point(-0.9, -0.36892),
                new Point(0.0, 0.0),
                new Point(0.9, 0.36892),
                new Point(1.8, 0.85408),
                new Point(2.7, 1.7856),
                new Point(3.6, 6.3138)
        ));
        point.add(Arrays.asList(
                new Point(1.0, 2.4142),
                new Point(1.9, 1.0818),
                new Point(2.8, 0.50953),
                new Point(3.7, 0.11836),
                new Point(4.6, -0.24008),
                new Point(5.5, -0.66818)
        ));
        return point;
    }


}

