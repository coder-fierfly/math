import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.*;

public class Task4_2 {
    static int CHOOSE = 1;
    public static void main(String[] args) {

        List<RealMatrix> matrixLinkedList = new ArrayList<>();
        List<RealMatrix> constantsLinkedList = new ArrayList<>();
        List<RealMatrix> approxLinkedList = new ArrayList<>();

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{{2, 0}, {0, 2}}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{2, 4}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{2, 3}));

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{{4, -2}, {-2, 2}}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{-2, 2}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.5, 0.5}));

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{{2, -2, 0}, {-2, 4, 0}, {0, 0, 2}}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 0, -2}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{2, 1, -1}));

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{{2, -1, 0}, {-1, 2, 0}, {0, 0, 2}}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{-1, 0, 2}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{-1, -1, 0}));

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{
                {4, -1, 0, -1, 0, 0},
                {-1, 4, -1, 0, -1, 0},
                {0, -1, 4, 0, 0, -1},
                {-1, 0, 0, 4, -1, 0},
                {0, -1, 0, -1, 4, -1},
                {0, 0, -1, 0, -1, 4}
        }));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{0, 5, 0, 6, -2, 6}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.9, 1.9, 0.9, 1.9, 0.9, 1.9}));

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{{2, -0.2}, {-0.2, 2}}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{2.2, -2.2}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.5, -0.5}));

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{{10, -9}, {-9, 8.15}}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{-1, 0}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{16, -19}));

        matrixLinkedList.add(MatrixUtils.createRealMatrix(new double[][]{{400, 20, 0, -1200}, {20, 4, -96, 0}, {0, -96, 64, -10}, {-1200, 0, -10, 400}}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{-532400, 0, -20, 20}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{-166, 89, -34, -499}));

        for (int i = 0; i < matrixLinkedList.size(); i++) {
            System.out.println("НОМЕР 4.2." + (i + 1));
            System.out.println("Скорейший спуск");
            Math4.speedyDescentAndMinimalResiduals(matrixLinkedList.get(i), approxLinkedList.get(i),constantsLinkedList.get(i),  CHOOSE, true);
        }
        System.out.println();
    }
}