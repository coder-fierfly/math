package math42;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class Task4_2 {
    public static void main(String[] args) {
        int[] accuracyDegrees = new int[]{2, 3, 4, 5, 10, 12, 15};
        int count = 1;
        List<double[]> initialValues = new ArrayList<>(), B = new ArrayList<>();
        List<double[][]> A = new ArrayList<>();
        prepareData(initialValues, B, A);

        for (int i = 0; i < initialValues.size(); i++) {
            for (int accuracyDegree : accuracyDegrees) {
                System.out.println("������� " + count + ", ������� �����������:= " + accuracyDegree);
                System.out.println("����� ������������� ������");
                doMNGS(initialValues.get(i), accuracyDegree, new Array2DRowRealMatrix(A.get(i)), new ArrayRealVector(B.get(i)));
                System.out.println();
            }
            count++;
        }
    }

    private static void doMNGS(double[] initialValue, int accuracyDegree, Array2DRowRealMatrix A, ArrayRealVector B) {
        double accuracy = Math.pow(10, -accuracyDegree), alpha;
        int countOfIterations = 1;

        ArrayRealVector currentXValues = new ArrayRealVector(initialValue), previousXValues, gradientValue, deltaX;

        do {
            previousXValues = currentXValues;
            RealVector V = A.preMultiply(previousXValues);
            gradientValue = (ArrayRealVector) V.subtract(B);

            if (gradientValue.getNorm() == 0) {
                currentXValues = previousXValues; // x(k+1) = x(k)
            } else {
                alpha = gradientValue.dotProduct(gradientValue) / A.preMultiply(gradientValue).dotProduct(gradientValue); // alpha(k) = (g(k), g(k)) / (A*g(k), g(k))
                currentXValues = previousXValues.subtract(gradientValue.mapMultiply(alpha)); // x(k+1) = x(k) - alpha * gradient(x(k))
            }
            deltaX = currentXValues.subtract(previousXValues);
            countOfIterations++;
        } while (!checkNorm(deltaX, accuracy));
        printResult(currentXValues, accuracy, countOfIterations);
    }

    public static void printResult(ArrayRealVector previousVariableValues, double epsilon, int iterationsNumber) {
        System.out.println(" e = " + epsilon + " ��������: " + iterationsNumber);
        int count = 1;
        for (double x :
                previousVariableValues.getDataRef()) {
            System.out.print("x" + count + ":= " + x + " ");
            count++;
        }
        System.out.println();
    }

    private static void prepareData(List<double[]> initialValues, List<double[]> B, List<double[][]> A) {
//        ������� 1
        A.add(new double[][]{{2, 0}, {0, 2}});
        B.add(new double[]{2, 4});
        initialValues.add(new double[]{0, 3});
//        ������� 2
        A.add(new double[][]{{4, -2}, {-2, 2}});
        B.add(new double[]{-2, 2});
        initialValues.add(new double[]{-1, 2});
//        ������� 3
        A.add(new double[][]{{2, -2, 0}, {-2, 4, 0}, {0, 0, 2}});
        B.add(new double[]{1, 0, -2});
        initialValues.add(new double[]{2, 1, -1});
//        ������� 4
        A.add(new double[][]{{2, -1, 0}, {-1, 2, 0}, {0, 0, 2}});
        B.add(new double[]{-1, 0, 2});
        initialValues.add(new double[]{0, 0, 0});
//        ������� 5
        A.add(new double[][]{
                {4, -1, 0, -1, 0, 0},
                {-1, 4, -1, 0, -1, 0},
                {0, -1, 4, 0, 0, -1},
                {-1, 0, 0, 4, -1, 0},
                {0, -1, 0, -1, 4, -1},
                {0, 0, -1, 0, -1, 4}
        });
        B.add(new double[]{0, 5, 0, 6, -2, 6});
        initialValues.add(new double[]{0, 0, 0, 0, 0, 0});
//        ������� 6
        A.add(new double[][]{{2, -0.2}, {-0.2, 2}});
        B.add(new double[]{2.2, -2.2});
        initialValues.add(new double[]{0, 0});
//        ������� 7
        A.add(new double[][]{{10, -9}, {-9, 8.15}});
        B.add(new double[]{-1, 0});
        initialValues.add(new double[]{0, 0});
//        ������� 8
        A.add(new double[][]{{400, 20, 0, -1200}, {20, 4, -96, 0}, {0, -96, 64, -10}, {-1200, 0, -10, 400}});
        B.add(new double[]{-532400, 0, -20, 20});
        initialValues.add(new double[]{-166, 89, -34, -499});
    }

    private static boolean checkNorm(ArrayRealVector vectorX, double accuracy) {
        double infinityNorm = vectorX.getL1Norm();
        double taxiNorm = vectorX.getL1Norm();
        double twoLNorm = vectorX.getNorm();
        return infinityNorm < accuracy && taxiNorm < accuracy && twoLNorm < accuracy;
    }
}
