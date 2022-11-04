import java.util.LinkedList;

        /* №1
        Локализуйте корни заданных уравнений и напишите программу, в которой методом
        простой итерации находится приближение к данным корням с точностью 𝜀. Программа
        должна выводить номер итерации и текущее приближение к корню.
        №2.1
        Найдите приближенное решение систем нелинейных уравнений методом Ньютона.
        Приближенное решение следует искать с точностью 𝜀.
        №2.2
        Найдите приближенное решение системы. */

public class Math2 {
    public static final double a1 = 2;
    public static final double eps = java.lang.Math.pow(10, -4);

    public static void main(String[] args) {

        LinkedList<Funk> funk = new LinkedList<>();
        funk.add(x -> Math.pow(x, 3) + Math.pow(x, 2) - x + 0.5);
        funk.add(x -> Math.exp(x) / a1 - x - 1);
        funk.add(x -> Math.pow(x, 3) - 20 * x + 1);
        funk.add(x -> Math.pow(2, x) + Math.pow(x, 2) - 2);
        funk.add(x -> x * Math.log(x + 2) - 1 + Math.pow(x, 2));
        funk.add(x -> Math.pow(x, 3) / a1 - a1 * Math.cos(x));

        LinkedList<Funk> derFirsts = new LinkedList<>();
        derFirsts.add(x -> 3 * Math.pow(x, 2) + 2 * x - 1);
        derFirsts.add(x -> Math.exp(x) / a1 - 1);
        derFirsts.add(x -> 3 * Math.pow(x, 2) - 20);
        derFirsts.add(x -> Math.log(2) * Math.pow(2, x) + 2 * x);
        derFirsts.add(x -> Math.log(x + 2) + x / (x + 2) + 2 * x);
        derFirsts.add(x -> 3 * Math.pow(x, 2) / a1 + a1 * Math.sin(x));

        LinkedList<Points> points = new LinkedList<>();
        points.add(new Points(-2, -3, 1));
        points.add(new Points(1, 1, 2));
        points.add(new Points(4, 2, 5));
        points.add(new Points(0, -1, 1));
        points.add(new Points(0, 0, 2));
        points.add(new Points(1, 0, 2));

        LinkedList<FunkSec> funkSec = new LinkedList<>();
        funkSec.add((x, y, a, alpha, betta) -> Math.tan(x * y + a) - Math.pow(x, 2));
        funkSec.add((x, y, a, alpha, betta) -> Math.pow(x, 2) * alpha + Math.pow(y, 2) * betta - 1);
        funkSec.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * y - 2 * x);
        funkSec.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * x);
        funkSec.add((x, y, a, alpha, betta) -> 2 * x * alpha);
        funkSec.add((x, y, a, alpha, betta) -> 2 * y * betta);

        LinkedList<Points> pn = new LinkedList<>();
        pn.add(new Points(0.2, 0.6, 2, 0.8, 0.5));
        pn.add(new Points(0.4, 0.8, 2, -0.3, 0.6));
        pn.add(new Points(0.3, 0.2, 3, 1, 0.5));
        pn.add(new Points(0, 0.6, 2, 0.6, 0.6));

        LinkedList<FunkSec> funkThird = new LinkedList<>();
        funkThird.add((x, y, a, alpha, betta) -> Math.pow(x, 2) + Math.pow(y, 2) - 2);
        funkThird.add(((x, y, a, alpha, betta) -> Math.exp(x - 1) + Math.pow(y, 3) - 2));
        funkThird.add((x, y, a, alpha, betta) -> 2 * x);
        funkThird.add((x, y, a, alpha, betta) -> 2 * y);
        funkThird.add((x, y, a, alpha, betta) -> Math.exp(x - 1));
        funkThird.add((x, y, a, alpha, betta) -> 3 * Math.pow(y, 3));

        LinkedList<Points> pn2 = new LinkedList<>();
        pn2.add(new Points(0, 0, 0, 1, 1));

        System.out.println("№1");
        for (int i = 0; i < funk.size(); i++) {
            System.out.println("\n№ 1." + (i + 1));
            simpleItr(funk.get(i), derFirsts.get(i), points.get(i));
        }
        System.out.println("\n\n\n№2");
        for (int i = 0; i < 4; i++) {
            System.out.println("\n№ 2.1." + (i + 1));
            newton(funkSec.get(0), funkSec.get(1), funkSec.get(2), funkSec.get(3), funkSec.get(4),
                    funkSec.get(5), pn.get(i));
        }

        System.out.println("\n\n\n№2.2");
        newton(funkThird.get(0), funkThird.get(1), funkThird.get(2), funkThird.get(3), funkThird.get(4),
                funkThird.get(5), pn2.get(0));
    }

    public static void newton(FunkSec f, FunkSec f2, FunkSec a, FunkSec b, FunkSec c, FunkSec d, Points pn) {
        int i = 0;
        double x1 = pn.x;
        System.out.println("x1: " + x1);
        double y1 = pn.y;
        System.out.println("x2: " + x1);
        double x, y;
        double a2 = pn.a;
        double alpha = pn.alpha;
        double betta = pn.betta;
        do {
            x = x1;
            y = y1;
            double ourFunk = f.calculate(x, y, a2, alpha, betta);
            double ourFunk2 = f2.calculate(x, y, a2, alpha, betta);
            double ourFunkA = a.calculate(x, y, a2, alpha, betta);
            double ourFunkB = b.calculate(x, y, a2, alpha, betta);
            double ourFunkC = c.calculate(x, y, a2, alpha, betta);
            double ourFunkD = d.calculate(x, y, a2, alpha, betta);
            double detA1 = (ourFunk  * ourFunkB) - (ourFunk2 * ourFunkD);
            double detA2 = (ourFunkA * ourFunk2) - (ourFunk * ourFunkC);
            double detJ = (ourFunkA * ourFunkD) - (ourFunkB * ourFunkC);
            x1 = x - (detA1 / detJ);
            y1 = y - (detA2 / detJ);
            i++;
        } while (Math.sqrt(Math.pow(f.calculate(x1,y1,a2,alpha,betta),2) + Math.pow(f2.calculate(x1,y1,a2,alpha,betta),2)) > eps &&
                Math.sqrt(Math.pow(x1-x,2) + Math.pow(y1-y,2)) > eps);
        System.out.println("Ответ: (" + x1 + "; " + y1 + ")");
        System.out.println("Количество итераций: " + i);
    }

    public static void simpleItr(Funk funk, Funk derFirst, Points points) {
        double funkMin = funk.calculate(points.min);
        double funkMax = funk.calculate(points.max);
        double funkX = funk.calculate(points.x);
        double x;
        if (funkMin >= funkMax && funkMin >= funkX) {
            x = points.min;
        } else if (funkMax >= funkMin && funkMax >= funkX) {
            x = points.max;
        } else {
            x = points.x;
        }
        int i = 0;
        double x0;
        double lambda = 1 / derFirst.calculate(x);
        do {
            x0 = x;
            x = x - lambda * funk.calculate(x);
            i++;
        } while (Math.abs(x - x0) >= eps);
        System.out.println("Ответ: " + x);
        System.out.println("Количество итераций: " + i);
    }

    interface Funk {
        double calculate(double x);
    }

    interface FunkSec {
        double calculate(double x, double y, double a, double alpha, double betta);
    }

    static class Points {
        double x, y, a, min, max, alpha, betta;

        public Points(double x, double min, double max) {
            this.x = x;
            this.min = min;
            this.max = max;
        }

        public Points(double a, double alpha, double betta, double x, double y) {
            this.a = a;
            this.alpha = alpha;
            this.betta = betta;
            this.x = x;
            this.y = y;
        }
    }
}
