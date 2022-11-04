import java.util.LinkedList;

public class Math1 {

    public static final double epsFirst = java.lang.Math.pow(10, -4);
    public static final double epsSecond = java.lang.Math.pow(10, -8);
    public static double k = 2, n = 2;

    public static void main(String[] args) {
        LinkedList<Funk> funk = new LinkedList<>();
        funk.add(x -> java.lang.Math.sin(x) - 2 * java.lang.Math.pow(x, 2) + 0.5);
        funk.add(x -> java.lang.Math.pow(x, n) - k);
        funk.add(x -> java.lang.Math.sqrt(1 - java.lang.Math.pow(x, 2)) - java.lang.Math.exp(x) + 0.1);
        funk.add(x -> java.lang.Math.pow(x, 6) - 5 * java.lang.Math.pow(x, 3) - 2);
        funk.add(x -> java.lang.Math.log(x) - (1 / (1 + java.lang.Math.pow(x, 2))));
        funk.add(x -> java.lang.Math.pow(3, x) - 5 * java.lang.Math.pow(x, 2) + 1);
        funk.add(x -> java.lang.Math.sin(x));
        funk.add(x -> java.lang.Math.log(x) - 1);

        LinkedList<Derivative> derivatives = new LinkedList<>();
        derivatives.add(x -> java.lang.Math.cos(x) - 4 * x);
        derivatives.add(x -> java.lang.Math.pow(x, n - 1) * n);
        derivatives.add(x -> -((x + java.lang.Math.sqrt(1 - java.lang.Math.pow(x, 2)) * java.lang.Math.exp(x)) / java.lang.Math.sqrt(1 - java.lang.Math.pow(x, 2))));
        derivatives.add(x -> 6 * java.lang.Math.pow(x, 5) - 15 * java.lang.Math.pow(x, 2));
        derivatives.add(x -> 1 / x + (2 * x) / (java.lang.Math.pow(1 + java.lang.Math.pow(x, 2), 2)));
        derivatives.add(x -> java.lang.Math.log(x) * java.lang.Math.pow(3, x) - 10 * x);
        derivatives.add(x -> java.lang.Math.cos(x));
        derivatives.add(x -> 1 / x);
        LinkedList<Points> points = new LinkedList<>();

        points.add(new Points(0, 2, epsFirst, 1));
        points.add(new Points(1, 2, epsFirst, 1));
        points.add(new Points(0, 1, epsFirst, 0));
        points.add(new Points(-1, 0, epsFirst, -1));
        points.add(new Points(1, 2, epsFirst, 1));
        points.add(new Points(0, 1, epsFirst, 1));
        points.add(new Points(epsSecond, 3));
        points.add(new Points(epsSecond, 2));

        for (int i = 0; i < funk.size(); i++) {
            System.out.println("\n№" + (i + 1) + ")");
            //первый номер
            if (i < 6) {
                dih(funk.get(i), points.get(i).a, points.get(i).b, points.get(i).eps);
            }
            newton(funk.get(i), derivatives.get(i), points.get(i).newtonNum, points.get(i).eps);
        }
    }

    public static void dih(Funk funk, double a, double b, double eps) {
        int i = 0;
        while (java.lang.Math.abs(b - a) > eps) {
            double x = (a + b) / 2;
            if (funk.calculate(b) * funk.calculate(x) < 0) {
                a = x;
            } else {
                b = x;
            }
            i++;
        }
        System.out.println("Дихотомия. Кол-во итерраций: " + i + " Ответ: " + (a + b) / 2);
    }

    public static void newton(Funk funk, Derivative derivative, double newtonNum, double eps) {
        double newtNum = newtonNum;
        int i = 0;
        while (java.lang.Math.abs(funk.calculate(newtNum)) > eps) {
            newtNum = newtNum - ((funk.calculate(newtNum)) / derivative.calculate(newtNum));
            i++;
        }
        System.out.println("Метод Ньютона. Кол-во итерраций: " + i + " Ответ: " + newtNum);
    }

    static class Points {

        double a;
        double b;
        double eps;
        double newtonNum;

        public Points(double a, double b, double eps, double newtonNum) {
            this.a = a;
            this.b = b;
            this.eps = eps;
            this.newtonNum = newtonNum;
        }

        public Points(double eps, double newtonNum) {
            this.eps = eps;
            this.newtonNum = newtonNum;
        }
    }

    interface Funk {
        double calculate(double x);
    }

    interface Derivative {
        double calculate(double x);
    }
}