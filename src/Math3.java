import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;

/* №1 Методом Гаусса найдите решение СЛАУ
   №2 Для заданных систем линейных алгебраических уравнений
реализуйте метод поиска численного решения методами простой
итерации и методом Зайделя. Разработайте приложение, которое для
заданной точности 𝜀𝑛 найдет приближенное решение СЛАУ.
*/
public class Math3 {
    static final int exponent = 4;
    public static final double EPS = java.lang.Math.pow(10, (-exponent));
    private static final int L_VALUE = 2;

    public static void main(String[] args) {
        LinkedList<double[][]> firstMatrix = new LinkedList<>();
        firstMatrix.add(
                new double[][]{{2, 2, -1, 1}, {4, 3, -1, 2}, {8, 5, -3, 4}, {3, 3, -2, 2}}
        );
        firstMatrix.add(
                new double[][]{{1, 7, -9, -8}, {-3, -18, 23, 28}, {0, -3, 6, -1}, {-1, -1, 1, 18}}
        );
        firstMatrix.add(
                new double[][]{{3, -3, 7, -4}, {-6, 9, -21, 9}, {9, -12, 30, -22}, {6, 0, 6, -31}}
        );
        firstMatrix.add(
                new double[][]{{9, -5, -6, 3}, {1, -7, 1, 0}, {3, -4, 9, 0}, {6, -1, 9, 8}}
        );
        firstMatrix.add(
                new double[][]{{-6, -5, -3, -8}, {5, -1, -5, -4}, {-6, 0, 5, 5}, {-7, -2, 8, 5}}
        );
        LinkedList<double[]> firstAns = new LinkedList<>();
        firstAns.add(
                new double[]{4, 6, 12, 6}
        );
        firstAns.add(
                new double[]{-7, 5, 8, -29}
        );
        firstAns.add(
                new double[]{0, 9, -2, 37}
        );
        firstAns.add(
                new double[]{-8, 38, 47, -8}
        );
        firstAns.add(
                new double[]{101, 51, -53, -63}
        );

        LinkedList<double[][]> secondMatrix = new LinkedList<>();
        secondMatrix.add(
                new double[][]{{12, -3, -1, 3, -31}, {5, 20, 9, 1, 90}, {6, -3, -21, -7, 119}, {8, -7, 3, -27, 71}}
        );
        secondMatrix.add(
                new double[][]{{28, 9, -3, -7, -159}, {-5, 21, -5, -3, 63}, {-8, 1, -16, 5, -45}, {0, -2, 5, 8, 24}}
        );
        secondMatrix.add(
                new double[][]{{21, 1, -8, 4, -119}, {-9, -23, -2, 4, 79}, {7, -1, -17, 6, -24}, {8, 8, -4, -26, -52}}
        );
        secondMatrix.add(
                new double[][]{{14, -4, -2, 3, 38}, {-3, 23, -6, -9, -195}, {-7, -8, 21, -5, -27}, {-2, -2, 8, 18, 142}}
        );

        gauss(firstMatrix, firstAns);
        System.out.println();
        int n = 1;
        // Для первого, второго и т.д. 1 или 3 самое оптимальные, 2 с большим количеством итераций
        int[] switchNumFirst = {3, 3, 3, 3};
        for (double[][] i : secondMatrix) {
            System.out.println();
            System.out.println("Простая итерация. номер 2.1." + n);
            //передаю матрицу и ее решение для switch
            simpleIteration(i, switchNumFirst[n - 1]);
            ++n;
        }

        n = 1;
        //одинаковое количество итераций вне зависимости от
        int[] switchNumSecond = {3, 3, 3, 3};
        for (double[][] i : secondMatrix) {
            System.out.println();
            System.out.println("Зейдель. номер 2.1." + n);
            //передаю матрицу и ее решение для switch
            Seidel(i, switchNumSecond[n - 1]);
            ++n;
        }


    }

    // точный метод
    public static void gauss(LinkedList<double[][]> matrix, LinkedList<double[]> rightAns) {
        // n, m размеры матрицы
        int n = 4;
        int m = 4;
        int num = 1;
        int index = 0;
        double[][] matrixDouble = new double[m][m];
        double[] ansDouble = new double[m];
        //беру одно уравнение
        for (double[][] mainMatrix : matrix) {
            //правая часть с ответами
            double[] rightSideOfMatrix = rightAns.get(index);
            index++;
            for (int i = 0; i < n; i++) {
                //0 - позиция начальная и конечная m - длинна
                //перекопирую все элементы построчно в массив
                System.arraycopy(mainMatrix[i], 0, matrixDouble[i], 0, m);
                ansDouble[i] = rightSideOfMatrix[i];
            }

            // проход вперед
            for (int k = 0; k < n; k++) {
                int max = k;
                //нахождение максимального элемента в i элементе и помещение
                // его наверх до диагонали, потом =0 все нижестоящие элементы
                for (int i = k + 1; i < n; i++) {
                    //если элемент больше максимального, то берется он
                    if (Math.abs(matrixDouble[i][k]) > Math.abs(matrixDouble[max][k])) {
                        max = i;
                    }
                }
                //строчка матрицы
                double[] temp = matrixDouble[k];
                //максимальное значение для этой строки по текущему x
                matrixDouble[k] = matrixDouble[max];
                matrixDouble[max] = temp;
                double t = ansDouble[k];
                ansDouble[k] = ansDouble[max];
                ansDouble[max] = t;

                if (Math.abs(matrixDouble[k][k]) <= EPS) {
                    System.out.println("NO");
                    return;
                }
                for (int i = k + 1; i < n; i++) {
                    // выражение делим на коэффициент перед x
                    double alpha = matrixDouble[i][k] / matrixDouble[k][k];
                    ansDouble[i] -= alpha * ansDouble[k];
                    for (int j = k; j < n; j++) {
                        matrixDouble[i][j] -= alpha * matrixDouble[k][j];
                    }
                }
            }
            //обратный проход
            double[] x = new double[n];
            for (int i = n - 1; i >= 0; i--) {
                double sum = 0.0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrixDouble[i][j] * x[j];
                }
                x[i] = (ansDouble[i] - sum) / matrixDouble[i][i];
            }

            System.out.println("номер 1." + num);
            for (int i = 0; i < n; i++) {
                System.out.print("x" + i + " = ");
                System.out.printf("%.2f    ", x[i]);
            }
            System.out.println("\n");
            num++;
        }
    }

    public static void simpleIteration(double[][] matr, int norm) {
        double[] nextValue = new double[matr[0].length - 1];
        double norma;
        double[] currentValue = new double[nextValue.length];

        double[][] matrix = new double[matr.length][matr[0].length - 1];
        // диагональ - нули, остальное делим на диагональный элемент
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr.length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    // делю на свободный член
                    matrix[i][j] = -matr[i][j] / matr[i][i];
                }
            }
        }

        // Расчёты
        int iter = 0;
        do {
            for (int i = 0; i < matr.length; i++) {
                double sum = 0;
                for (int j = 0; j < matr.length; j++) {
                    //сумма всех x из матрицы * старый х
                    sum += matrix[i][j] * currentValue[j];
                }
                // новый х = сумма + свободный член / на диагональный элемент
                nextValue[i] = sum + matr[i][matr.length] / matr[i][i];
            }
            norma = calculatingTheNorm(currentValue, nextValue, norm);
            System.arraycopy(nextValue, 0, currentValue, 0, currentValue.length);
            iter++;
        } while (norma > EPS);
        for (int i = 0; i < currentValue.length; i++) {
            System.out.print("x" + (i + 1) + " = ");
            System.out.printf("%." + exponent + "f    ", currentValue[i]);
        }
        System.out.println();
        System.out.println("Количество итераций " + iter);

    }

    public static void Seidel(double[][] fullMatrix, int switchNum) {
        double[][] currFullMatrix;
        double[] rightAns;
        double[] currentValue = {0, 0, 0, 0};
        double[] nextValue = {0, 0, 0, 0};
        int amountUnknowns = 4;
        double sum;
        rightAns = new double[amountUnknowns];
        currFullMatrix = new double[amountUnknowns][amountUnknowns];
        //перенесение всех значений в новые матрицы
        for (int i = 0; i < amountUnknowns; i++) {
            for (int j = 0; j <= amountUnknowns; j++) {
                //сама матрица
                if (j != amountUnknowns) {
                    currFullMatrix[i][j] = fullMatrix[i][j];
                    //ответы
                } else {
                    rightAns[i] = fullMatrix[i][j];
                }
            }
        }

        double buffNorm;

        // коэффициенты перед x на главной диагонали
        double[] buffDivision = new double[4];
        for (int i = 0; i < amountUnknowns; i++) {
            buffDivision[i] = currFullMatrix[i][i];
        }
        int iter = 0;
        do {
            System.arraycopy(nextValue, 0, currentValue, 0, 4);
            for (int i = 0; i < amountUnknowns; i++) {
                sum = 0;
                for (int j = 0; j < amountUnknowns; j++) {
                    if (j != i) {
                        //сумма всех иксов кроме главной диагонали
                        // в методе Зайделя когда мы получаем текущий элемент, мы потом его используем в расчетах
                        // currentValue[j] должно быть новая пересчитанная
                        if (i > 0) {
                            currentValue[i - 1] = nextValue[i - 1];
                        }
                        sum += currFullMatrix[i][j] * currentValue[j];
                    }
                }
                //следующее значение равно (ответ после равно - сумму иксов) / первоначальный делитель
                nextValue[i] = (rightAns[i] - sum) / buffDivision[i];
            }
            // норма(невязка) для сравнения с eps
            buffNorm = calculatingTheNorm(currentValue, nextValue, switchNum);
            iter++;
        } while (buffNorm > EPS);
        for (int i = 0; i < currentValue.length; i++) {
            System.out.print("x" + (i + 1) + " = ");
            System.out.printf("%." + exponent + "f    ", currentValue[i]);
        }
        System.out.println();
        System.out.println("Количество итераций " + iter);
    }

    public static double calculatingTheNorm(double[] currentValue, double[] nextValue, int num) {
        double norm = 0;
        int size = currentValue.length;
        switch (num) {
            // Для нормы ||X||бесконечность
            case (1) -> {
                double[] values = new double[size];
                for (int i = 0; i < size; ++i) {
                    values[i] = Math.abs(nextValue[i] - currentValue[i]);
                }
                DoubleSummaryStatistics stat = Arrays.stream(values).summaryStatistics();
                return stat.getMax();
            }
            // Для нормы ||X||l
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.abs(nextValue[i] - currentValue[i]);
                }
                return norm;
            }
            // Для нормы ||X||2l
            case (3) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.pow(nextValue[i] - currentValue[i], 2 * L_VALUE);
                }
                return Math.pow(norm, 1. / (2 * L_VALUE));
            }
        }
        return 0;
    }
}
