import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.LinkedList;

public class Math4 {
    static int CHOOSE = 1;
    private static final double L_VALUE = 2;
    static final int exponent = 4;
    public static final double EPS = java.lang.Math.pow(10, (-exponent));

    public static void main(String[] args) {
        LinkedList<RealMatrix> matrixLinkedList = new LinkedList<>();
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{2, 2, -1, 1}, {4, 3, -1, 2}, {8, 5, -3, 4},
                {3, 3, -2, 2}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, 1, -1, 1}, {1, 4, -1, -1}, {-1, -1, 5, 1},
                {1, -1, 1, 3}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{2.8, 2.1, -1.3, 0.3}, {-1.4, 4.5, -7.7, 1.3},
                {0.6, 2.1, -5.8, 2.4}, {3.5, -6.5, 3.2, -7.9}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, 1, 1, 0, 1}, {1, 3, 1, 1, 0}, {1, 1, 5, -1, -1},
                {0, 1, -1, 4, 0}, {1, 0, -1, 0, 4}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, -1, 0, -1, 0, 0}, {-1, 4, -1, 0, -1, 0},
                {0, -1, 4, 0, 0, -1}, {-1, 0, 0, 4, -1, 0}, {0, -1, 0, -1, 4, -1}, {0, 0, -1, 0, -1, 4}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, -1, 0, 0, 0, 0}, {-1, 4, -1, 0, 0, 0}, {0, -1, 4, 0, 0, 0},
                {0, 0, 0, 4, -1, 0}, {0, 0, 0, -1, 4, -1}, {0, 0, 0, 0, -1, 4}}, false));
        // ответы
        LinkedList<RealMatrix> constantsLinkedList = new LinkedList<>();
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{4, 6, 12, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{-2, -1, 0, 1}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 1, 1, 1}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{6, 6, 6, 6, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{0, 5, 0, 6, -2, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{0, 5, 0, 6, -2, 6}));

        // приближения
        LinkedList<RealMatrix> approxLinkedList = new LinkedList<>();
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 1.1, -1, -1}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{-0.8, 0, 0.3, 0.7}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.1, 0.2, -0.1, 0.3}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.5, 0.7, 1.5, 1.7, 1.4}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 2, 1, 2, 1, 2}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.3, 1.5, 0.3, 1.5, 0.2, 1.5}));
        for (int i = 0; i < matrixLinkedList.size(); i++) {
            System.out.println("НОМЕР 4." + (i + 1));
            System.out.println("Скорейший спуск");
            speedyDescentAndMinimalResiduals(matrixLinkedList.get(i), approxLinkedList.get(i), constantsLinkedList.get(i), CHOOSE, true);
            System.out.println("\nМинимальная невязка");
            speedyDescentAndMinimalResiduals(matrixLinkedList.get(i), approxLinkedList.get(i), constantsLinkedList.get(i), CHOOSE, false);
            System.out.println();
        }
    }

    //скорейший спуск и минимальная невязка
    public static void speedyDescentAndMinimalResiduals(RealMatrix matrix, RealMatrix approximations, RealMatrix constants, int choose, boolean speedyBool) {
        RealMatrix oldApproximations;
        double norma;
        double stepLength;
        int k = 0;
        RealMatrix discrepancy;

        // Если матрица несимметрична, то приводим её к симметричному виду
        if (speedyBool) {
            if (!matrix.equals(matrix.transpose())) {
                //транспонированную матрицу * на ответы
                constants = matrix.transpose().multiply(constants);
                for (int i = 0; i < matrix.getRowDimension(); i++) {
                    for (int j = 0; j < matrix.getColumnDimension(); j++) {
                        matrix.setEntry(i, j, matrix.preMultiply(matrix.transpose()).getEntry(i, j));
                    }

                }
            }
        }

        do {
            // Находим вектор невязок на текущем шаге
            discrepancy = (matrix.multiply(approximations)).subtract(constants);
            // умножение матрицы на приближение
            RealMatrix mulMatrixDis = matrix.multiply(discrepancy);
            // делимое и делитель
            double divisible = 0;
            double divider = 0;
            //скорейший спуск
            if (speedyBool) {
                for (int i = 0; i < discrepancy.getRowDimension(); i++) {
                    divisible += discrepancy.getEntry(i, 0) * discrepancy.getEntry(i, 0);
                    divider += discrepancy.getEntry(i, 0) * mulMatrixDis.getEntry(i, 0);
                }
                // минимальная невязка
            } else {
                for (int i = 0; i < discrepancy.getRowDimension(); i++) {
                    divisible += mulMatrixDis.getEntry(i, 0) * discrepancy.getEntry(i, 0);
                    divider += mulMatrixDis.getEntry(i, 0) * mulMatrixDis.getEntry(i, 0);
                }
            }
            //шаг
            stepLength = divisible / divider;
            oldApproximations = approximations;
            //пересчитываю приближение
            approximations = approximations.subtract(discrepancy.scalarMultiply(stepLength));
            //норма
            norma = norm(approximations, oldApproximations, choose);
            k++;
        } while (norma > EPS);
        System.out.println("Количество итераций " + k);
      for(int i = 0; i < discrepancy.getRowDimension(); i++){
          System.out.print("x" + (i+1) + " = "+ oldApproximations.getEntry(i, 0) + ";  ");
      }
        System.out.println();
    }

    //норма
    public static double norm(RealMatrix approximations2, RealMatrix oldApproximations, int c) {
        RealMatrix buffNorm;
        buffNorm = approximations2.subtract(oldApproximations);
        int size = approximations2.getRowDimension();
        double num = 0;
        switch (c) {
            case (1) -> {
                for (int i = 0; i < size; ++i) {
                    buffNorm.setEntry(i, 0, Math.abs(buffNorm.getEntry(i, 0)));
                    if (buffNorm.getEntry(i, 0) > num) {
                        num = buffNorm.getEntry(i, 0);
                    }
                }
            }
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    num += Math.abs(buffNorm.getEntry(i, 0));
                }
            }
            case (3) -> {
                for (int i = 0; i < size; ++i) {
                    num += Math.pow((buffNorm.getEntry(i, 0)), 2 * L_VALUE);
                }
                num = Math.pow(num, 1 / (2 * L_VALUE));
            }
        }
        return num;
    }
}